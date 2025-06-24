package io.github.wasabithumb.recsup.impl.java16;

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;

@ApiStatus.Internal
final class Java16RecordComponent extends AbstractRecordComponent<RecordComponent> {

    Java16RecordComponent(@NotNull RecordComponent handle) {
        super(handle);
    }

    //

    @Override
    public @NotNull Method getAccessor() {
        return this.handle.getAccessor();
    }

    @Override
    public @NotNull AnnotatedType getAnnotatedType() {
        return this.handle.getAnnotatedType();
    }

    @Override
    public @NotNull RecordClass<?> getDeclaringRecord() {
        return new Java16RecordClass<>(this.handle.getDeclaringRecord());
    }

    @Override
    public @NotNull String getGenericSignature() {
        return this.handle.getGenericSignature();
    }

    @Override
    public @NotNull Type getGenericType() {
        return this.handle.getGenericType();
    }

    @Override
    public @NotNull String getName() {
        return this.handle.getName();
    }

    @Override
    public @NotNull Class<?> getType() {
        return this.handle.getType();
    }

}
