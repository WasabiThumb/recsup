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
import org.jetbrains.annotations.NonNls;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Handles creation of a {@link RecordSupportInstance}.
 * Meant to be used once by {@link RecordSupport}
 * (the library entry point) which stores and
 * wraps the single canonical instance.
 */
@NullMarked
@ApiStatus.Internal
public final class RecordSupportInstanceFactory {

    private final ClassLoader entryPointClassLoader;
    private final String entryPointClassName;
    private final String entryPointPackageName;

    RecordSupportInstanceFactory(Class<?> entryPointClass) {
        ClassLoader cl = entryPointClass.getClassLoader();
        if (cl == null) cl = ClassLoader.getSystemClassLoader();
        this.entryPointClassLoader = cl;
        this.entryPointClassName = entryPointClass.getName();
        this.entryPointPackageName = entryPointClass.getPackage().getName();
    }

    //

    public RecordSupportInstance create() {
        int feature = runtimeFeatureVersion();
        boolean cache = true;
        RecordSupportInstance ret;

        if (feature >= 16 && !readBooleanProperty("noDirect")) {
            ret = construct("impl.direct.DirectRecordSupportInstance");
        } else if (feature >= 14) {
            ret = construct("impl.invoke.InvokeRecordSupportInstance");
        } else {
            ret = construct("impl.never.NeverRecordSupportInstance");
            cache = false;
        }

        if (cache && !readBooleanProperty("noCache")) {
            ret = construct("impl.caching.CachingRecordSupportInstance", ret);
        }

        return ret;
    }

    private RecordSupportInstance construct(@NonNls String path, Object... args) {
        String className = this.entryPointPackageName + "." + path;
        Class<?> cls;
        try {
            cls = Class.forName(className, true, this.entryPointClassLoader);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class at path " + path + " not found", e);
        }

        if ((cls.getModifiers() & 0x600) != 0)
            throw new IllegalStateException("Refusing to instantiate class at path " + path + " (is abstract)");
        if (!RecordSupportInstance.class.isAssignableFrom(cls))
            throw new IllegalStateException("Class at path " + path + " does not implement RecordSupportInstance");

        final Constructor<? extends RecordSupportInstance> ctor =
                findNargsCtor(cls.asSubclass(RecordSupportInstance.class), args.length);

        try {
            ctor.setAccessible(true);
        } catch (Exception ignored) { }

        try {
            return ctor.newInstance(args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null) cause = e;
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            if (cause instanceof Error) throw (Error) cause;
            throw new IllegalStateException("Checked exception raised while instantiating class at path " + path);
        } catch (Exception e) {
            throw new IllegalStateException("Disallowed exception raised while instantiating class at path " + path, e);
        }
    }

    private boolean readBooleanProperty(@NonNls String name) {
        String path = this.entryPointClassName + "." + name;
        String value = System.getProperty(path);
        if (value == null || value.isEmpty()) return false;
        return !value.equals("0") && !value.equalsIgnoreCase("false");
    }

    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> findNargsCtor(Class<T> cls, int n) {
        Constructor<?> ret = null;
        for (Constructor<?> candidate : cls.getDeclaredConstructors()) {
            if (candidate.getParameterCount() != n) continue;
            if (ret != null) {
                throw new IllegalStateException("Class " + cls.getName() +
                        " has multiple constructors which accept " + n + " parameter(s)");
            }
            ret = candidate;
        }
        if (ret == null) {
            throw new IllegalStateException("Class " + cls.getName() +
                    " has no constructors which accept " + n + " parameter(s)");
        }
        return (Constructor<T>) ret;
    }

    private static int runtimeFeatureVersion() {
        try {
            Class<?> cRuntimeVersion = Class.forName("java.lang.Runtime$Version");
            Method mRuntimeVersion = Runtime.class.getDeclaredMethod("version");
            Method mVersionFeature = cRuntimeVersion.getDeclaredMethod("feature");
            Object version = mRuntimeVersion.invoke(null);
            return (Integer) mVersionFeature.invoke(version);
        } catch (ReflectiveOperationException ignored) {
            return -1;
        }
    }

}
