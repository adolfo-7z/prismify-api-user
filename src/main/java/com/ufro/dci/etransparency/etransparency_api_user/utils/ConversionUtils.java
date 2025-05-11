package com.ufro.dci.etransparency.etransparency_api_user.utils;

import java.util.*;

import org.springframework.beans.*;

import java.beans.PropertyDescriptor;

import static com.ufro.dci.etransparency.etransparency_api_user.utils.Constants.*;
import com.ufro.dci.etransparency.etransparency_api_user.exceptions.custom.CustomConversionException;

public class ConversionUtils {

    /**
     * La clase `ConversionUtils` proporciona métodos para operaciones de
     * conversión.
     * Esta clase no se puede instanciar ya que solo contiene métodos estáticos.
     */
    private ConversionUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Convierte un objeto entidad a un objeto DTO.
     *
     * @param <D>      el tipo del objeto DTO
     * @param <E>      el tipo del objeto entidad
     * @param entity   el objeto entidad a convertir
     * @param dtoClass la clase del objeto DTO
     * @return el objeto DTO convertido
     */
    public static <D, E> D convertToDTO(E entity, Class<D> dtoClass) {
        D dtoInstance = null;
        try {
            dtoInstance = dtoClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, dtoInstance);
        } catch (Exception e) {
            throw new CustomConversionException(OPERATION_FAILED, RESOURCE_CONVERSION);
        }
        return dtoInstance;
    }

    /**
     * Convierte un DTO a una entidad.
     *
     * @param <D>         el tipo del DTO
     * @param <E>         el tipo de la entidad
     * @param dto         el DTO a convertir
     * @param entityClass la clase de la entidad
     * @return la entidad convertida
     */
    public static <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        E entityInstance = null;
        try {
            entityInstance = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entityInstance);
        } catch (Exception e) {
            throw new CustomConversionException(OPERATION_FAILED, RESOURCE_CONVERSION);
        }
        return entityInstance;
    }

    /**
     * Copia propiedades no nulas de un objeto a otro.
     *
     * @param src    el objeto fuente
     * @param target el objeto objetivo
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * Obtiene los nombres de las propiedades que son nulas en el objeto fuente.
     *
     * @param source el objeto fuente
     * @return un arreglo de nombres de propiedades que son nulas
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
