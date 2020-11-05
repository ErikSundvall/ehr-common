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

import care.better.platform.annotation.Open
import care.better.platform.annotation.Required
import org.openehr.base.basetypes.UidBasedId
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlType

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
@XmlType(
    name = "CALLBACK_NOTIFICATION", propOrder = [
        "taskId",
        "requestId",
        "subjectId",
        "manuallyNotified"])
@Open
class CallbackNotification() : PlanEvent(), TaskReferencingEvent {
    companion object {
        private const val serialVersionUID: Long = 1L
    }

    @XmlElement(name = "task_id", required = true)
    @Required
    private var taskId: UidBasedId? = null

    @XmlElement(name = "request_id")
    var requestId: String? = null

    @XmlElement(name = "subject_id")
    var subjectId: String? = null

    @XmlElement(name = "manually_notified")
    var manuallyNotified: Boolean? = null

    constructor(taskId: UidBasedId?) : this() {
        this.taskId = taskId
    }

    constructor(taskId: UidBasedId?, requestId: String?) : this(taskId) {
        this.requestId = requestId
    }

    override fun getTaskId(): UidBasedId? = taskId

    fun setTaskId(taskId: UidBasedId?) {
        this.taskId = taskId
    }

    override fun toString(): String =
        "CallbackNotification{" +
                "taskId=" +
                ", requestId='$requestId'" +
                ", subjectId='$subjectId'" +
                ", manuallyNotified=$manuallyNotified'" +
                "} ${super.toString()}"
}