package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public abstract class AbstractRecordClass<T> implements RecordClass<T> {

    protected final Class<T> handle;

    protected AbstractRecordClass(@NotNull Class<T> handle) {
        this.handle = handle;
    }

    //

    @Override
    public @NotNull Class<T> handle() {
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
    public @NotNull String toString() {
        return this.handle.toString();
    }

}
