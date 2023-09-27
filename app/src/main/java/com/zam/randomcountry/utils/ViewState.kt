/*
 * Copyright (C) 2023 Zokirjon Mamadjonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zam.randomcountry.utils

sealed class ViewState<T>(
    val value: T? = null,
    val error: String? = null,
    val throwable: Throwable? = null
) {
    class Idle<T> : ViewState<T>()

    class Loading<T> : ViewState<T>()

    class Success<T>(data: T) : ViewState<T>(data)

    class Error<T>(error: String? = null, throwable: Throwable? = null) :
        ViewState<T>(error = error, throwable = throwable)
}
