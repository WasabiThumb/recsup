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

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import io.github.wasabithumb.recsup.util.Pinned;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.locks.StampedLock;

@NullMarked
@ApiStatus.Internal
public final class CachingRecordSupportInstance implements RecordSupportInstance {

    private final RecordSupportInstance backing;
    private final StampedLock lock;
    private final Map<Class<?>, Optional<RecordClass<?>>> map;

    @Pinned
    public CachingRecordSupportInstance(
            RecordSupportInstance backing
    ) {
        this.backing = backing;
        this.lock = new StampedLock();
        this.map = new WeakHashMap<>();
    }

    //

    @Override
    public boolean isRecord(Class<?> cls) {
        return this.entry(cls).isPresent();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException {
        Optional<RecordClass<?>> entry = this.entry(cls);
        if (!entry.isPresent()) throw new IllegalArgumentException("Class " + cls.getName() + " is not a record");
        return (RecordClass<T>) entry.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable <T> RecordClass<T> whenRecord(Class<T> cls) {
        return (RecordClass<T>) this.entry(cls).orElse(null);
    }

    private Optional<RecordClass<?>> entry(Class<?> cls) {
        long stamp = this.lock.readLock();
        try {
            Optional<RecordClass<?>> value = this.map.get(cls);
            if (value != null) return value;

            long s2 = this.lock.tryConvertToWriteLock(stamp);
            if (s2 == 0L) {
                this.lock.unlock(stamp);
                stamp = this.lock.writeLock();
                value = this.map.get(cls);
                if (value != null) return value;
            } else {
                stamp = s2;
            }

            RecordClass<?> qual = this.backing.whenRecord(cls);
            value = qual == null ? Optional.empty() : Optional.of(new CachingRecordClass<>(qual));
            this.map.put(cls, value);
            return value;
        } finally {
            this.lock.unlock(stamp);
        }
    }

}
