package io.github.wasabithumb.recsup.impl.java16;

import io.github.wasabithumb.recsup.AbstractRecordComponent;
import io.github.wasabithumb.recsup.RecordClass;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;

@NullMarked
@ApiStatus.Internal
final class Java16RecordComponent extends AbstractRecordComponent<RecordComponent> {

    Java16RecordComponent(RecordComponent handle, int index) {
        super(handle, index);
    }

    //

    @Override
    public Method getAccessor() {
        return this.handle.getAccessor();
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return this.handle.getAnnotatedType();
    }

    @Override
    public RecordClass<?> getDeclaringRecord() {
        return new Java16RecordClass<>(this.handle.getDeclaringRecord());
    }

    @Override
    public String getGenericSignature() {
        return this.handle.getGenericSignature();
    }

    @Override
    public Type getGenericType() {
        return this.handle.getGenericType();
    }

    @Override
    public String getName() {
        return this.handle.getName();
    }

    @Override
    public Class<?> getType() {
        return this.handle.getType();
    }

}
