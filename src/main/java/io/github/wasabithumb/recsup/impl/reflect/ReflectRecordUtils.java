package io.github.wasabithumb.recsup.impl.reflect;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ApiStatus.Internal
final class ReflectRecordUtils {

    static final boolean OK;
    static final Method M_CLASS_GET_RECORD_COMPONENTS;
    static final Method M_CLASS_IS_RECORD;
    static final Method M_RECORD_COMPONENT_GET_ACCESSOR;
    static final Method M_RECORD_COMPONENT_GET_ANNOTATED_TYPE;
    static final Method M_RECORD_COMPONENT_GET_DECLARING_RECORD;
    static final Method M_RECORD_COMPONENT_GET_GENERIC_SIGNATURE;
    static final Method M_RECORD_COMPONENT_GET_GENERIC_TYPE;
    static final Method M_RECORD_COMPONENT_GET_NAME;
    static final Method M_RECORD_COMPONENT_GET_TYPE;
    static final Throwable INIT_ERROR;
    static {
        boolean ok = true;
        Class<?> cRecordComponent;
        Method mClassGetRecordComponents = null;
        Method mClassIsRecord = null;
        Method mRecordComponentGetAccessor = null;
        Method mRecordComponentGetAnnotatedType = null;
        Method mRecordComponentGetDeclaringRecord = null;
        Method mRecordComponentGetGenericSignature = null;
        Method mRecordComponentGetGenericType = null;
        Method mRecordComponentGetName = null;
        Method mRecordComponentGetType = null;
        Throwable initError = null;

        try {
            cRecordComponent = Class.forName("java.lang.reflect.RecordComponent");
            mClassGetRecordComponents = Class.class.getDeclaredMethod("getRecordComponents");
            mClassIsRecord = Class.class.getDeclaredMethod("isRecord");
            mRecordComponentGetAccessor = cRecordComponent.getDeclaredMethod("getAccessor");
            mRecordComponentGetAnnotatedType = cRecordComponent.getDeclaredMethod("getAnnotatedType");
            mRecordComponentGetDeclaringRecord = cRecordComponent.getDeclaredMethod("getDeclaringRecord");
            mRecordComponentGetGenericSignature = cRecordComponent.getDeclaredMethod("getGenericSignature");
            mRecordComponentGetGenericType = cRecordComponent.getDeclaredMethod("getGenericType");
            mRecordComponentGetName = cRecordComponent.getDeclaredMethod("getName");
            mRecordComponentGetType = cRecordComponent.getDeclaredMethod("getType");
        } catch (Throwable err) {
            initError = err;
            ok = false;
        }

        OK = ok;
        M_CLASS_GET_RECORD_COMPONENTS = mClassGetRecordComponents;
        M_CLASS_IS_RECORD = mClassIsRecord;
        M_RECORD_COMPONENT_GET_ACCESSOR = mRecordComponentGetAccessor;
        M_RECORD_COMPONENT_GET_ANNOTATED_TYPE = mRecordComponentGetAnnotatedType;
        M_RECORD_COMPONENT_GET_DECLARING_RECORD = mRecordComponentGetDeclaringRecord;
        M_RECORD_COMPONENT_GET_GENERIC_SIGNATURE = mRecordComponentGetGenericSignature;
        M_RECORD_COMPONENT_GET_GENERIC_TYPE = mRecordComponentGetGenericType;
        M_RECORD_COMPONENT_GET_NAME = mRecordComponentGetName;
        M_RECORD_COMPONENT_GET_TYPE = mRecordComponentGetType;
        INIT_ERROR = initError;
    }

    //

    static @UnknownNullability Object invoke(
            @NotNull Object target,
            @NotNull @MagicConstant(valuesFromClass = ReflectRecordUtils.class) Method method
    ) {
        try {
            return method.invoke(target);
        } catch (InvocationTargetException | ExceptionInInitializerError e) {
            Throwable cause = e.getCause();
            if (cause == null) cause = e;
            if (cause instanceof RuntimeException) throw (RuntimeException) cause;
            throw new AssertionError("Unexpected checked exception", cause);
        } catch (ReflectiveOperationException | SecurityException e) {
            throw new AssertionError("Unexpected reflection error", e);
        }
    }

}
