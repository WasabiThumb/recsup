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

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupport;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static io.github.wasabithumb.recsup.impl.invoke.InvokeRecordHandles.*;

@NullMarked
@ApiStatus.Internal
final class InvokeRecordComponent extends AbstractRecordComponent<AnnotatedElement> {

    InvokeRecordComponent(AnnotatedElement handle, int index) {
        super(handle, index);
    }

    //

    @Override
    public Method getAccessor() {
        try {
            return (Method) HANDLES.mRecordComponentGetAccessor.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        try {
            return (AnnotatedType) HANDLES.mRecordComponentGetAnnotatedType.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public RecordClass<?> getDeclaringRecord() {
        try {
            return RecordSupport.asRecord((Class<?>) HANDLES.mRecordComponentGetDeclaringRecord.invoke(this.handle));
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public String getGenericSignature() {
        try {
            return (String) HANDLES.mRecordComponentGetGenericSignature.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public Type getGenericType() {
        try {
            return (Type) HANDLES.mRecordComponentGetGenericType.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public String getName() {
        try {
            return (String) HANDLES.mRecordComponentGetName.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

    @Override
    public Class<?> getType() {
        try {
            return (Class<?>) HANDLES.mRecordComponentGetType.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }
    }

}
