package io.github.wasabithumb.recsup;

import io.github.wasabithumb.recsup.impl.reflect.ReflectRecordSupportInstance;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class RecordSupport {

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

    private static @Nullable RecordSupportInstance createJava16() {
        try {
            Class<?> cls = Class.forName("io.github.wasabithumb.recsup.impl.java16.Java16RecordSupportInstance");
            Constructor<?> con = cls.getDeclaredConstructor();
            return (RecordSupportInstance) con.newInstance();
        } catch (ReflectiveOperationException e) {
            Logger.getLogger("recsup")
                    .log(Level.WARNING, "Failed to initialize Java 16+ record support, using fallback", e);
            return null;
        }
    }

    private static final RecordSupportInstance INSTANCE;
    static {
        int version = runtimeFeatureVersion();
        RecordSupportInstance instance;
        if (version >= 16) {
            instance = createJava16();
            if (instance == null) {
                instance = new ReflectRecordSupportInstance(true);
            }
        } else {
            instance = new ReflectRecordSupportInstance(version >= 14);
        }
        INSTANCE = instance;
    }

    //

    @Contract(pure = true)
    public static @NotNull RecordSupportInstance instance() {
        return INSTANCE;
    }

    public static boolean isRecord(@NotNull Class<?> cls) {
        return INSTANCE.isRecord(cls);
    }

    public static <T> @NotNull RecordClass<T> asRecord(@NotNull Class<T> cls) throws IllegalArgumentException {
        return INSTANCE.asRecord(cls);
    }

}
