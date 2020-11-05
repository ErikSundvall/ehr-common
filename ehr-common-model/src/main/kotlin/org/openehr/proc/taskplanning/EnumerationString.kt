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

/**
 * @author Primoz Delopst
 * @since 3.1.0
 */
interface EnumerationString {

    companion object {
        @JvmStatic
        fun <T : EnumerationString> fromString(enumClass: Class<T>, value: String): T =
            enumClass.enumConstants.firstOrNull { it.stringValue().equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("No such " + enumClass.simpleName + ": " + value)
    }

    @JvmDefault
    fun stringValue(): String =
        if (this is Enum<*>)
            (this as Enum<*>).name
        else
            throw AssertionError("EnumerationString " + javaClass.simpleName + " is not an Enum")
}