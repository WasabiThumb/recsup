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

package io.github.wasabithumb.recsup.impl.invoke;

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import io.github.wasabithumb.recsup.util.Pinned;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import static io.github.wasabithumb.recsup.impl.invoke.InvokeRecordHandles.*;

@NullMarked
@ApiStatus.Internal
public final class InvokeRecordSupportInstance implements RecordSupportInstance {

    @Pinned
    public InvokeRecordSupportInstance() { }

    //

    @Override
    public boolean isRecord(Class<?> cls) {
        if (!OK) return false;
        try {
            return (boolean) HANDLES.mClassIsRecord.invokeExact(cls);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException {
        if (!isRecord(cls)) throw new IllegalArgumentException("Class " + cls.getName() + " is not a record");
        return new InvokeRecordClass<>(cls);
    }

    @Override
    public @Nullable <T> RecordClass<T> whenRecord(Class<T> cls) {
        if (!isRecord(cls)) return null;
        return new InvokeRecordClass<>(cls);
    }

}
