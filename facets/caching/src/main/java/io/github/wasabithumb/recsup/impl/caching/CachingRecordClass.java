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

package io.github.wasabithumb.recsup.impl.caching;

import io.github.wasabithumb.recsup.AbstractRecordClass;
import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

@NullMarked
@ApiStatus.Internal
final class CachingRecordClass<T> extends AbstractRecordClass<T> {

    private final RecordComponent[] components;

    CachingRecordClass(RecordClass<T> backing) {
        super(backing.handle());
        this.components = backing.getRecordComponents();
    }

    //

    @Override
    public RecordComponent[] getRecordComponents() {
        return Arrays.copyOf(this.components, this.components.length);
    }

}
