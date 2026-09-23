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

import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

/**
 * Entry point for RecSup.
 * Holds an {@link #instance() instance} and
 * provides static helper methods.
 * @see #isRecord(Class)
 * @see #asRecord(Class)
 */
@NullMarked
public final class RecordSupport {

    private static final RecordSupportInstance INSTANCE;
    static {
        final RecordSupportInstanceFactory factory = new RecordSupportInstanceFactory(RecordSupport.class);
        INSTANCE = factory.create();
    }

    //

    /**
     * Reports the record support provider.
     */
    @Contract(pure = true)
    public static RecordSupportInstance instance() {
        return INSTANCE;
    }

    /**
     * Alias for {@code .instance().isRecord(...)}
     * @see RecordSupportInstance#isRecord(Class)
     */
    public static boolean isRecord(Class<?> cls) {
        return INSTANCE.isRecord(cls);
    }

    /**
     * Alias for {@code .instance().asRecord(...)}
     * @see RecordSupportInstance#asRecord(Class)
     */
    public static <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException {
        return INSTANCE.asRecord(cls);
    }

    /**
     * Alias for {@code .instance().whenRecord(...)}
     * @see RecordSupportInstance#whenRecord(Class)
     */
    public static <T> @Nullable RecordClass<T> whenRecord(Class<T> cls) throws IllegalArgumentException {
        return INSTANCE.whenRecord(cls);
    }

    //

    private RecordSupport() {
        throw new UnsupportedOperationException();
    }

}
