package io.github.wasabithumb.recsup.impl.reflect;

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import static io.github.wasabithumb.recsup.impl.reflect.ReflectRecordUtils.*;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@NullMarked
@ApiStatus.Internal
final class ReflectRecordComponent extends AbstractRecordComponent<AnnotatedElement> {

    ReflectRecordComponent(AnnotatedElement handle, int index) {
        super(handle, index);
    }

    //

    @Override
    public Method getAccessor() {
        return (Method) invoke(this.handle, M_RECORD_COMPONENT_GET_ACCESSOR);
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return (AnnotatedType) invoke(this.handle, M_RECORD_COMPONENT_GET_ANNOTATED_TYPE);
    }

    @Override
    public RecordClass<?> getDeclaringRecord() {
        return new ReflectRecordClass<>((Class<?>) invoke(this.handle, M_RECORD_COMPONENT_GET_DECLARING_RECORD));
    }

    @Override
    public String getGenericSignature() {
        return (String) invoke(this.handle, M_RECORD_COMPONENT_GET_GENERIC_SIGNATURE);
    }

    @Override
    public Type getGenericType() {
        return (Type) invoke(this.handle, M_RECORD_COMPONENT_GET_GENERIC_TYPE);
    }

    @Override
    public String getName() {
        return (String) invoke(this.handle, M_RECORD_COMPONENT_GET_NAME);
    }

    @Override
    public Class<?> getType() {
        return (Class<?>) invoke(this.handle, M_RECORD_COMPONENT_GET_TYPE);
    }

}
