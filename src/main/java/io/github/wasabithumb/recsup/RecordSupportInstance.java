package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface RecordSupportInstance {

    boolean isRecord(@NotNull Class<?> cls);

    <T> @NotNull RecordClass<T> asRecord(@NotNull Class<T> cls) throws IllegalArgumentException;

}
