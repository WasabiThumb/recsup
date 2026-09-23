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

package io.github.wasabithumb.recsup.impl.invoke;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;
import org.jspecify.annotations.NullMarked;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;

@NullMarked
@ApiStatus.Internal
final class InvokeRecordHandles {

    static final boolean OK;
    static final @UnknownNullability InvokeRecordHandles HANDLES;
    static {
        boolean ok = false;
        InvokeRecordHandles handles = null;
        try {
            final MethodHandles.Lookup lookup = MethodHandles.publicLookup();
            final Class<?> rcc = lookup.findClass("java.lang.reflect.RecordComponent");
            handles = new InvokeRecordHandles(
                    lookup.findVirtual(Class.class, "isRecord", MethodType.methodType(Boolean.TYPE)),
                    lookup.findVirtual(Class.class, "getRecordComponents", MethodType.methodType(rcc.arrayType())),
                    lookup.findVirtual(rcc, "getAccessor", MethodType.methodType(Method.class)),
                    lookup.findVirtual(rcc, "getAnnotatedType", MethodType.methodType(AnnotatedType.class)),
                    lookup.findVirtual(rcc, "getDeclaringRecord", MethodType.methodType(Class.class)),
                    lookup.findVirtual(rcc, "getGenericSignature", MethodType.methodType(String.class)),
                    lookup.findVirtual(rcc, "getGenericType", MethodType.methodType(Type.class)),
                    lookup.findVirtual(rcc, "getName", MethodType.methodType(String.class)),
                    lookup.findVirtual(rcc, "getType", MethodType.methodType(Class.class))
            );
            ok = true;
        } catch (Exception e) {
            Logger.getLogger("recsup")
                    .log(Level.WARNING, "Failed to initialize record support", e);
        }
        OK = ok;
        HANDLES = handles;
    }

    static RuntimeException uncheck(Throwable t) {
        if (t instanceof RuntimeException) return (RuntimeException) t;
        if (t instanceof Error) throw (Error) t;
        return new IllegalStateException("Unexpected checked exception", t);
    }

    //

    final MethodHandle mClassIsRecord;
    final MethodHandle mClassGetRecordComponents;
    final MethodHandle mRecordComponentGetAccessor;
    final MethodHandle mRecordComponentGetAnnotatedType;
    final MethodHandle mRecordComponentGetDeclaringRecord;
    final MethodHandle mRecordComponentGetGenericSignature;
    final MethodHandle mRecordComponentGetGenericType;
    final MethodHandle mRecordComponentGetName;
    final MethodHandle mRecordComponentGetType;

    private InvokeRecordHandles(
            MethodHandle mClassIsRecord,
            MethodHandle mClassGetRecordComponents,
            MethodHandle mRecordComponentGetAccessor,
            MethodHandle mRecordComponentGetAnnotatedType,
            MethodHandle mRecordComponentGetDeclaringRecord,
            MethodHandle mRecordComponentGetGenericSignature,
            MethodHandle mRecordComponentGetGenericType,
            MethodHandle mRecordComponentGetName,
            MethodHandle mRecordComponentGetType
    ) {
        this.mClassIsRecord = mClassIsRecord;
        this.mClassGetRecordComponents = mClassGetRecordComponents;
        this.mRecordComponentGetAccessor = mRecordComponentGetAccessor;
        this.mRecordComponentGetAnnotatedType = mRecordComponentGetAnnotatedType;
        this.mRecordComponentGetDeclaringRecord = mRecordComponentGetDeclaringRecord;
        this.mRecordComponentGetGenericSignature = mRecordComponentGetGenericSignature;
        this.mRecordComponentGetGenericType = mRecordComponentGetGenericType;
        this.mRecordComponentGetName = mRecordComponentGetName;
        this.mRecordComponentGetType = mRecordComponentGetType;
    }

}
