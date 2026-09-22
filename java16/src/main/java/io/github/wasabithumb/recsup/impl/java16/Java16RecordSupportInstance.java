package io.github.wasabithumb.recsup.impl.java16;

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class Java16RecordSupportInstance implements RecordSupportInstance {

    public Java16RecordSupportInstance() { }

    //

    @Override
    public boolean isRecord(Class<?> cls) {
        return cls.isRecord();
    }

    @Override
    public <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException {
        if (!cls.isRecord()) {
            throw new IllegalArgumentException("Class " + cls.getName() + " is not a record");
        }
        return new Java16RecordClass<>(cls);
    }

}
