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

package org.openehr.rm.composition

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.ObjectRef
import org.openehr.rm.common.Participation
import org.openehr.rm.common.PartyProxy
import org.openehr.rm.datatypes.CodePhrase
import javax.xml.bind.annotation.*

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ENTRY", propOrder = [
        "language",
        "encoding",
        "subject",
        "provider",
        "otherParticipations",
        "workFlowId"])
@XmlSeeAlso(value = [AdminEntry::class, CareEntry::class])
@Open
abstract class Entry : ContentItem() {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(required = true)
    @Required
    var language: CodePhrase? = null

    @XmlElement(required = true)
    @Required
    var encoding: CodePhrase? = null

    @XmlElement(required = true)
    @Required
    var subject: PartyProxy? = null

    var provider: PartyProxy? = null

    @XmlElement(name = "other_participations")
    var otherParticipations: MutableList<Participation> = mutableListOf()

    @XmlElement(name = "work_flow_id")
    var workFlowId: ObjectRef? = null
}