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

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Backport of {@link java.lang.reflect.RecordComponent} (Java 16+) to
 * Java 8+
 * @see #handle()
 */
@NullMarked
@ApiStatus.NonExtendable
public interface RecordComponent extends AnnotatedElement {

    /**
     * Exposes actual {@link java.lang.reflect.RecordComponent} which this
     * object is wrapping. The return type is {@link AnnotatedElement} which is
     * a supertype of {@code RecordComponent} that is available in Java 8.
     */
    AnnotatedElement handle();

    /**
     * Provides the position of this component within the
     * primary constructor of the declaring record.
     * For instance, the 2nd parameter of a record's primary constructor
     * corresponds to a record component with an index of 1.
     */
    @ApiStatus.AvailableSince("0.1.1")
    int index();

    /**
     * Returns a {@code Method} that represents the accessor for
     * this record component
     * @see java.lang.reflect.RecordComponent#getAccessor()
     */
    Method getAccessor();

    /**
     * Returns an {@code AnnotatedType} object that represents
     * the use of a type to specify the declared type of this record component.
     * @see java.lang.reflect.RecordComponent#getAnnotatedType()
     */
    AnnotatedType getAnnotatedType();

    /**
     * Returns the record class which declares this record component, wrapped into
     * a {@link RecordClass}.
     * @see java.lang.reflect.RecordComponent#getDeclaringRecord()
     */
    RecordClass<?> getDeclaringRecord();

    /**
     * Returns a {@code String} that describes the generic type signature
     * for this record component.
     * @see java.lang.reflect.RecordComponent#getGenericSignature()
     */
    String getGenericSignature();

    /**
     * Returns a {@code Type} object that represents the declared type
     * for this record component.
     * @see java.lang.reflect.RecordComponent#getGenericType()
     */
    Type getGenericType();

    /**
     * Returns the name of this record component.
     * @see java.lang.reflect.RecordComponent#getName()
     */
    String getName();

    /**
     * Returns a {@code Class} that identifies the declared type
     * for this record component.
     * @see java.lang.reflect.RecordComponent#getType()
     */
    Class<?> getType();

}
