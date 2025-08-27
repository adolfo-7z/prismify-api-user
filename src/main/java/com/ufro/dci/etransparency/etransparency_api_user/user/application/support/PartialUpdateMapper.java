package com.ufro.dci.etransparency.etransparency_api_user.user.application.support;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import com.ufro.dci.etransparency.etransparency_api_user.user.infrastructure.controllers.exception.custom.UpdateMapperException;

public class PartialUpdateMapper {

    private PartialUpdateMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> void copyNonNullFields(T source, T target) {
        try {
            for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(source.getClass(), Object.class)
                    .getPropertyDescriptors()) {

                var readMethod = propertyDescriptor.getReadMethod();
                var writeMethod = propertyDescriptor.getWriteMethod();

                if (readMethod != null && writeMethod != null) {
                    Object value = readMethod.invoke(source);
                    if (value != null) {
                        writeMethod.invoke(target, value);
                    }
                }
            }
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new UpdateMapperException();
        }
    }
    
}
