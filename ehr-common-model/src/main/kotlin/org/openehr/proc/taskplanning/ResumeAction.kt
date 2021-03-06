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

package org.openehr.proc.taskplanning

import care.better.openehr.rm.RmObject
import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.UidBasedId
import java.io.Serializable
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "RESUME_ACTION", propOrder = [
        "resumeType",
        "resumeLocation"])
@Open
class ResumeAction() : RmObject(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 0L
    }

    @XmlElement(name = "resume_type", required = true)
    @Required
    var resumeType: ResumeType? = null

    @XmlElement(name = "resume_location")
    var resumeLocation: UidBasedId? = null

    constructor(resumeType: ResumeType) : this() {
        this.resumeType = resumeType
    }

    constructor(resumeType: ResumeType, resumeLocation: UidBasedId?) : this(resumeType) {
        this.resumeLocation = resumeLocation
    }

    override fun toString(): String =
        "ResumeAction{" +
                "resumeType=$resumeType" +
                ", resumeLocation=$resumeLocation" +
                '}'
}
