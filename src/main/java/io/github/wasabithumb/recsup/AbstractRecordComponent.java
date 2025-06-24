package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

@ApiStatus.Internal
public abstract class AbstractRecordComponent<H extends AnnotatedElement> implements RecordComponent {

    protected final H handle;

    protected AbstractRecordComponent(@NotNull H handle) {
        this.handle = handle;
    }

    //

    @Override
    public @NotNull H handle() {
        return this.handle;
    }

    @Override
    public boolean isAnnotationPresent(@NotNull Class<? extends Annotation> annotationClass) {
        return this.handle.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> @NotNull T @NotNull [] getAnnotationsByType(@NotNull Class<T> annotationClass) {
        return this.handle.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(@NotNull Class<T> annotationClass) {
        return this.handle.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> @NotNull T @NotNull [] getDeclaredAnnotationsByType(@NotNull Class<T> annotationClass) {
        return this.handle.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> @Nullable T getAnnotation(@NotNull Class<T> aClass) {
        return this.handle.getAnnotation(aClass);
    }

    @Override
    public @NotNull Annotation @NotNull [] getAnnotations() {
        return this.handle.getAnnotations();
    }

    @Override
    public @NotNull Annotation @NotNull [] getDeclaredAnnotations() {
        return this.handle.getDeclaredAnnotations();
    }

    @Override
    public int hashCode() {
        return this.handle.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecordComponent)) return false;
        return this.handle.equals(((RecordComponent) obj).handle());
    }

    @Override
    public @NotNull String toString() {
        return this.handle.toString();
    }

}
