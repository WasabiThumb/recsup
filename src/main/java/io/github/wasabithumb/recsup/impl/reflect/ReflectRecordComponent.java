package io.github.wasabithumb.recsup.impl.reflect;

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import static io.github.wasabithumb.recsup.impl.reflect.ReflectRecordUtils.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@ApiStatus.Internal
final class ReflectRecordComponent extends AbstractRecordComponent<AnnotatedElement> {

    ReflectRecordComponent(@NotNull AnnotatedElement handle) {
        super(handle);
    }

    //

    @Override
    public @NotNull Method getAccessor() {
        return (Method) invoke(this.handle, M_RECORD_COMPONENT_GET_ACCESSOR);
    }

    @Override
    public @NotNull AnnotatedType getAnnotatedType() {
        return (AnnotatedType) invoke(this.handle, M_RECORD_COMPONENT_GET_ANNOTATED_TYPE);
    }

    @Override
    public @NotNull RecordClass<?> getDeclaringRecord() {
        return new ReflectRecordClass<>((Class<?>) invoke(this.handle, M_RECORD_COMPONENT_GET_DECLARING_RECORD));
    }

    @Override
    public @NotNull String getGenericSignature() {
        return (String) invoke(this.handle, M_RECORD_COMPONENT_GET_GENERIC_SIGNATURE);
    }

    @Override
    public @NotNull Type getGenericType() {
        return (Type) invoke(this.handle, M_RECORD_COMPONENT_GET_GENERIC_TYPE);
    }

    @Override
    public @NotNull String getName() {
        return (String) invoke(this.handle, M_RECORD_COMPONENT_GET_NAME);
    }

    @Override
    public @NotNull Class<?> getType() {
        return (Class<?>) invoke(this.handle, M_RECORD_COMPONENT_GET_TYPE);
    }

}
