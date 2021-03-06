/* Copyright 2021 Better Ltd (www.better.care)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package care.better.platform.jaxb

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.openehr.am.aom.TermBindingItem
import org.openehr.am.aom.TermBindingSet
import org.openehr.base.basetypes.TerminologyId
import org.openehr.rm.composition.Composition
import org.openehr.rm.datatypes.CodePhrase
import org.openehr.rm.datatypes.DvDate
import java.io.IOException
import java.io.StringReader
import java.io.StringWriter
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
import javax.xml.bind.Unmarshaller
import javax.xml.namespace.QName
import javax.xml.transform.stream.StreamSource

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
open class SerializationTest {

    private val unmarshaller: Unmarshaller = JaxbRegistry.getInstance().unmarshaller
    private val marshaller: Marshaller = JaxbRegistry.getInstance().marshaller

    @Test
    fun testCompositionDeserialization() {
        val composition = getComposition("/composition.xml")
        assertThat(composition).isNotNull
        assertThat(composition.content).isNotEmpty
        assertThat(composition.uid?.value).isEqualTo("f74c649e-3259-4b43-9943-67c86b8ee13a::default::1")
    }

    @Test
    fun testCompositionSerialization() {
        val composition = getComposition("/composition.xml")

        val stringWriter = StringWriter()
        marshaller.marshal(composition, stringWriter)
        val compositionString = stringWriter.toString()
        assertThat(compositionString).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
        assertThat(compositionString).contains("<uid xsi:type=\"OBJECT_VERSION_ID\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><value>f74c649e-3259-4b43-9943-67c86b8ee13a::default::1</value></uid>")
    }

    @Test
    fun testDateWithJvmOveraloadsSerialization() {
        val date = DvDate(value = "2020-01-01")

        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("date"), DvDate::class.java, date), stringWriter)
        val rmString = stringWriter.toString()

        val date2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), DvDate::class.java).value
        assertThat(date2.value).isEqualTo("2020-01-01")

        val codePhrase = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        val stringWriter1 = StringWriter()
        marshaller.marshal(JAXBElement(QName("code_phrase"), CodePhrase::class.java, codePhrase), stringWriter1)
        val rmString1 = stringWriter1.toString()

        val codePhrase2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString1)), CodePhrase::class.java).value
        assertThat(codePhrase2.terminologyId).isNotNull
        assertThat(codePhrase2.codeString).isNotNull
    }

    @Test
    fun testCodePhraseWithJvmOveraloadsSerialization() {
        val codePhrase = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("code_phrase"), CodePhrase::class.java, codePhrase), stringWriter)
        val rmString = stringWriter.toString()

        val codePhrase2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), CodePhrase::class.java).value
        assertThat(codePhrase2.terminologyId).isNotNull
        assertThat(codePhrase2.codeString).isNotNull
    }

    @Test
    fun testTermBindingItemWithJvmOveraloadsSerialization() {
        val termBindingItem = TermBindingItem().apply {
            code = "code"
            value = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        }

        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("term_binding_item"), TermBindingItem::class.java, termBindingItem), stringWriter)
        val rmString = stringWriter.toString()

        val termBindingItem2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), TermBindingItem::class.java).value
        assertThat(termBindingItem2.code).isNotNull
        assertThat(termBindingItem2.value).isNotNull
    }

    @Test
    fun testTermBindingSetWithJvmOveraloadsSerialization() {
        val termBindingItem = TermBindingItem().apply {
            code = "code"
            value = CodePhrase(terminologyId = TerminologyId("test"), codeString = "x221")
        }
        val termBindingSet = TermBindingSet()
        termBindingSet.items.add(termBindingItem)
        termBindingSet.terminology = "terminology"

        val stringWriter = StringWriter()
        marshaller.marshal(JAXBElement(QName("term_binding_set"), TermBindingSet::class.java, termBindingSet), stringWriter)
        val rmString = stringWriter.toString()

        val termBindingSet2 = unmarshaller.unmarshal(StreamSource(StringReader(rmString)), TermBindingSet::class.java).value
        assertThat(termBindingSet2.terminology).isEqualTo("terminology")
        assertThat(termBindingSet2.items).isNotEmpty
        assertThat(termBindingSet2.items[0].code).isNotNull
        assertThat(termBindingSet2.items[0].value).isNotNull
    }

    @Throws(JAXBException::class, IOException::class)
    protected open fun getComposition(compositionFile: String): Composition =
        SerializationTest::class.java.getResourceAsStream(compositionFile).use { stream ->
            if (stream == null)
                throw RuntimeException("Composition resource was not found: $compositionFile.")
            else
                unmarshaller.unmarshal(StreamSource(stream), Composition::class.java).value
        }

}
