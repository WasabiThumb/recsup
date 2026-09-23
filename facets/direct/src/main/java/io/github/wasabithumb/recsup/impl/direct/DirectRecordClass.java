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

import io.github.wasabithumb.recsup.AbstractRecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
final class DirectRecordClass<T> extends AbstractRecordClass<T> {

    DirectRecordClass(Class<T> handle) {
        super(handle);
    }

    @Override
    public RecordComponent[] getRecordComponents() {
        java.lang.reflect.RecordComponent[] backing = this.handle.getRecordComponents();
        assert backing != null;

        RecordComponent[] ret = new RecordComponent[backing.length];
        for (int i=0; i < backing.length; i++) {
            ret[i] = new DirectRecordComponent(backing[i], i);
        }

        return ret;
    }

}
