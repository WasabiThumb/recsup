package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public abstract class AbstractRecordClass<T> implements RecordClass<T> {

    protected final Class<T> handle;

    protected AbstractRecordClass(Class<T> handle) {
        this.handle = handle;
    }

    //

    @Override
    public Class<T> handle() {
        return this.handle;
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecordClass<?>)) return false;
        return this.handle.equals(((RecordClass<?>) obj).handle());
    }

    @Override
    public String toString() {
        return this.handle.toString();
    }

}
