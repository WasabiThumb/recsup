/*
 * Copyright 2026 Xavier Pedraza
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

package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@ApiStatus.NonExtendable
public interface RecordSupportInstance {

    /**
     * Returns true if the given class is a record.
     * Identical to {@link Class#isRecord()} from Java 16+.
     */
    boolean isRecord(Class<?> cls);

    /**
     * Wraps the given class, exposing record
     * support methods.
     * @throws IllegalArgumentException Class is not a {@link #isRecord(Class) record}
     */
    <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException;

    /**
     * Wraps the given class if it is a record class,
     * otherwise returns null.
     */
    @ApiStatus.AvailableSince("0.2.0")
    <T> @Nullable RecordClass<T> whenRecord(Class<T> cls);

}
