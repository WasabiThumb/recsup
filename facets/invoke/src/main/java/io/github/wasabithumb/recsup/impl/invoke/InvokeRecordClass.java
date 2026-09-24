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

import io.github.wasabithumb.recsup.AbstractRecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;

import static io.github.wasabithumb.recsup.impl.invoke.InvokeRecordHandles.*;

@NullMarked
@ApiStatus.Internal
final class InvokeRecordClass<T> extends AbstractRecordClass<T> {

    InvokeRecordClass(Class<T> handle) {
        super(handle);
    }

    //

    @Override
    public RecordComponent[] getRecordComponents() {
        Object components;
        try {
            components = HANDLES.mClassGetRecordComponents.invoke(this.handle);
        } catch (Throwable t) {
            throw uncheck(t);
        }

        int count = Array.getLength(components);
        RecordComponent[] ret = new RecordComponent[count];
        for (int i = 0; i < count; i++) {
            AnnotatedElement ae = (AnnotatedElement) Array.get(components, i);
            ret[i] = new InvokeRecordComponent(ae, i);
        }

        return ret;
    }

}
