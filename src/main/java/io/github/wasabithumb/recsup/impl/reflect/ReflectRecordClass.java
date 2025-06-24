package io.github.wasabithumb.recsup.impl.reflect;

import io.github.wasabithumb.recsup.AbstractRecordClass;
import io.github.wasabithumb.recsup.RecordComponent;
import static io.github.wasabithumb.recsup.impl.reflect.ReflectRecordUtils.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;

@ApiStatus.Internal
final class ReflectRecordClass<T> extends AbstractRecordClass<T> {

    ReflectRecordClass(@NotNull Class<T> handle) {
        super(handle);
    }

    @Override
    public @NotNull RecordComponent @NotNull [] getRecordComponents() {
        Object arr = invoke(this.handle, M_CLASS_GET_RECORD_COMPONENTS);
        int len = Array.getLength(arr);

        RecordComponent[] ret = new RecordComponent[len];
        for (int i=0; i < len; i++) {
            ret[i] = new ReflectRecordComponent((AnnotatedElement) Array.get(arr, i));
        }

        return ret;
    }

}
