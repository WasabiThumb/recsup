package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

@ApiStatus.NonExtendable
public interface RecordClass<T> {

    @NotNull Class<T> handle();

    @NotNull RecordComponent @NotNull [] getRecordComponents();

    default @NotNull Constructor<T> getPrimaryConstructor() {
        RecordComponent[] rcs = this.getRecordComponents();
        Class<?>[] types = new Class<?>[rcs.length];
        for (int i=0; i < rcs.length; i++) {
            types[i] = rcs[i].getType();
        }

        Constructor<T> con;
        try {
            con = this.handle()
                    .getDeclaredConstructor(types);
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Primary constructor of record class does not exist", e);
        }

        return con;
    }

}
