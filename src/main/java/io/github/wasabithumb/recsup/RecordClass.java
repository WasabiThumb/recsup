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

import java.lang.reflect.Constructor;

/**
 * Wraps a {@link Class} which is known to be a record.
 * Provides {@link #getRecordComponents()}, a non-nullable analog for
 * the Java 16+ {@link Class#getRecordComponents()} method.
 */
@NullMarked
@ApiStatus.NonExtendable
public interface RecordClass<T> {

    /**
     * Backing class object
     */
    Class<T> handle();

    /**
     * The components of the record referenced by the {@link #handle() backing class object},
     * analogous to the Java 16+ {@link Class#getRecordComponents()} method.
     */
    RecordComponent[] getRecordComponents();

    /**
     * Helper to locate the primary constructor (the constructor which
     * accepts all the record's components in their declared order) for the
     * record referenced by the {@link #handle() backing class object}.
     */
    default Constructor<T> getPrimaryConstructor() {
        RecordComponent[] rcs = this.getRecordComponents();
        Class<?>[] types = new Class<?>[rcs.length];
        for (int i=0; i < rcs.length; i++) {
            types[i] = rcs[i].getType();
        }

        Constructor<T> con;
        try {
            con = this.handle()
                    .getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Primary constructor of record class does not exist", e);
        }

        return con;
    }

}
