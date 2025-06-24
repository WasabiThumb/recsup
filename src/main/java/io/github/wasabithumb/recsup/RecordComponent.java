package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@ApiStatus.NonExtendable
public interface RecordComponent extends AnnotatedElement {

    @NotNull AnnotatedElement handle();

    @NotNull Method getAccessor();

    @NotNull AnnotatedType getAnnotatedType();

    @NotNull RecordClass<?> getDeclaringRecord();

    @NotNull String getGenericSignature();

    @NotNull Type getGenericType();

    @NotNull String getName();

    @NotNull Class<?> getType();

}
