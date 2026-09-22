package io.github.wasabithumb.recsup.impl.reflect;

import io.github.wasabithumb.recsup.RecordClass;
import io.github.wasabithumb.recsup.RecordSupportInstance;
import static io.github.wasabithumb.recsup.impl.reflect.ReflectRecordUtils.*;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.logging.Level;
import java.util.logging.Logger;

@NullMarked
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
    public boolean isRecord(Class<?> cls) {
        return OK && Boolean.TRUE.equals(invoke(cls, M_CLASS_IS_RECORD));
    }

    @Override
    public <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException {
        if (!this.isRecord(cls)) {
            throw new IllegalArgumentException("Class " + cls.getName() + " is not a record");
        }
        return new ReflectRecordClass<>(cls);
    }

}
