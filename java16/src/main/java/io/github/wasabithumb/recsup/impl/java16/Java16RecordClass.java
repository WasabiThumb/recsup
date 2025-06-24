package io.github.wasabithumb.recsup.impl.java16;

import io.github.wasabithumb.recsup.AbstractRecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
final class Java16RecordClass<T> extends AbstractRecordClass<T> {

    Java16RecordClass(@NotNull Class<T> handle) {
        super(handle);
    }

    @Override
    public @NotNull RecordComponent @NotNull [] getRecordComponents() {
        java.lang.reflect.RecordComponent[] backing = this.handle.getRecordComponents();
        assert backing != null;

        RecordComponent[] ret = new RecordComponent[backing.length];
        for (int i=0; i < backing.length; i++) {
            ret[i] = new Java16RecordComponent(backing[i]);
        }

        return ret;
    }

}
