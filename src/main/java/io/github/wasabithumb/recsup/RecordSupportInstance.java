package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.NonExtendable
public interface RecordSupportInstance {

    /**
     * Returns true if the given class is a record.
     * Identical to {@link Class#isRecord()} from Java 16+.
     */
    boolean isRecord(Class<?> cls);

    /**
     * Wraps the given class, exposing record
     * support methods.
     * @throws IllegalArgumentException Class is not a {@link #isRecord(Class) record}
     */
    <T> RecordClass<T> asRecord(Class<T> cls) throws IllegalArgumentException;

}
