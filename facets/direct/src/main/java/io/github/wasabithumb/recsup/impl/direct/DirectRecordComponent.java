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

package io.github.wasabithumb.recsup.impl.direct;

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupport;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;

@NullMarked
@ApiStatus.Internal
final class DirectRecordComponent extends AbstractRecordComponent<RecordComponent> {

    DirectRecordComponent(RecordComponent handle, int index) {
        super(handle, index);
    }

    //

    @Override
    public Method getAccessor() {
        return this.handle.getAccessor();
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return this.handle.getAnnotatedType();
    }

    @Override
    public RecordClass<?> getDeclaringRecord() {
        return RecordSupport.asRecord(this.handle.getDeclaringRecord());
    }

    @Override
    public String getGenericSignature() {
        return this.handle.getGenericSignature();
    }

    @Override
    public Type getGenericType() {
        return this.handle.getGenericType();
    }

    @Override
    public String getName() {
        return this.handle.getName();
    }

    @Override
    public Class<?> getType() {
        return this.handle.getType();
    }

}
