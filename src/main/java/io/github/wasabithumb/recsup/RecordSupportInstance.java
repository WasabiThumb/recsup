package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface RecordSupportInstance {

    /**
     * Returns true if the given class is a record.
     * Identical to {@link Class#isRecord()} from Java 16+.
     */
    boolean isRecord(@NotNull Class<?> cls);

    /**
     * Wraps the given class, exposing record
     * support methods.
     * @throws IllegalArgumentException Class is not a {@link #isRecord(Class) record}
     */
    <T> @NotNull RecordClass<T> asRecord(@NotNull Class<T> cls) throws IllegalArgumentException;

}
