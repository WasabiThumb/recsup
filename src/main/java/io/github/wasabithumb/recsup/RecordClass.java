package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * Wraps a {@link Class} which is known to be a record.
 * Provides {@link #getRecordComponents()}, a non-nullable analog for
 * the Java 16+ {@link Class#getRecordComponents()} method.
 */
@ApiStatus.NonExtendable
public interface RecordClass<T> {

    /**
     * Backing class object
     */
    @NotNull Class<T> handle();

    /**
     * The components of the record referenced by the {@link #handle() backing class object},
     * analogous to the Java 16+ {@link Class#getRecordComponents()} method.
     */
    @NotNull RecordComponent @NotNull [] getRecordComponents();

    /**
     * Helper to locate the primary constructor (the constructor which
     * accepts all the record's components in their declared order) for the
     * record referenced by the {@link #handle() backing class object}.
     */
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
