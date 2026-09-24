/*
 * Copyright 2026 Xavier Pedraza
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wasabithumb.recsup;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

@NullMarked
@ApiStatus.Internal
public abstract class AbstractRecordComponent<H extends AnnotatedElement> implements RecordComponent {

    protected final H handle;
    protected final int index;

    protected AbstractRecordComponent(H handle, int index) {
        this.handle = handle;
        this.index = index;
    }

    //

    @Override
    public H handle() {
        return this.handle;
    }

    @Override
    public int index() {
        return this.index;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return this.handle.isAnnotationPresent(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        return this.handle.getAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
        return this.handle.getDeclaredAnnotation(annotationClass);
    }

    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        return this.handle.getDeclaredAnnotationsByType(annotationClass);
    }

    @Override
    public <T extends Annotation> @Nullable T getAnnotation(Class<T> aClass) {
        return this.handle.getAnnotation(aClass);
    }

    @Override
    public Annotation[] getAnnotations() {
        return this.handle.getAnnotations();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
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
    public String toString() {
        return this.handle.toString();
    }

}
