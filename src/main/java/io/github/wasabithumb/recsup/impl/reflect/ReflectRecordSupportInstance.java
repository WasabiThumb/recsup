package io.github.wasabithumb.recsup.impl.reflect;

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import static io.github.wasabithumb.recsup.impl.reflect.ReflectRecordUtils.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

@ApiStatus.Internal
public final class ReflectRecordSupportInstance implements RecordSupportInstance {

    public ReflectRecordSupportInstance(boolean expectOk) {
        if (expectOk && !OK) {
            Logger.getLogger("recsup")
                    .log(Level.WARNING, "Record support is expected but not available", INIT_ERROR);
        }
    }

    //

    @Override
    public boolean isRecord(@NotNull Class<?> cls) {
        return OK && Boolean.TRUE.equals(invoke(cls, M_CLASS_IS_RECORD));
    }

    @Override
    public @NotNull <T> RecordClass<T> asRecord(@NotNull Class<T> cls) throws IllegalArgumentException {
        if (!this.isRecord(cls)) {
            throw new IllegalArgumentException("Class " + cls.getName() + " is not a record");
        }
        return new ReflectRecordClass<>(cls);
    }

}
