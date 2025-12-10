package prismify.user.application.support;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import prismify.user.infrastructure.controllers.exception.custom.UpdateMapperException;

/**
 * Clase utilitaria para realizar actualizaciones parciales en objetos
 * JavaBeans.
 * <p>
 * Esta clase permite copiar todos los campos no nulos de un objeto fuente
 * hacia un objeto destino del mismo tipo, usando reflexión e introspección
 * de JavaBeans.
 *
 * <p>
 * <b>Ejemplo de uso:</b>
 * 
 * <pre>
 * User source = new User();
 * source.setName("Nombre");
 *
 * User target = new User();
 * target.setEmail("nombre@ejemplo.com");
 *
 * PartialUpdateMapper.copyNonNullFields(source, target);
 * // Ahora target tiene name="Nombre" y email="nombre@ejemplo.com"
 * </pre>
 *
 * @author Adolfo Plaza
 */
public class PartialUpdateMapper {

    /**
     * Constructor privado para evitar la instanciación.
     * <p>
     * Como esta es una clase utilitaria, no debe ser instanciada.
     * Cualquier intento de hacerlo arrojará una
     * {@link UnsupportedOperationException}.
     */
    private PartialUpdateMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Copia todos los campos no nulos desde el objeto {@code source} hacia el
     * objeto {@code target}.
     * <p>
     * Se utiliza introspección para acceder a las propiedades expuestas por métodos
     * getter y setter. Solo se copian aquellos valores que no sean nulos en el
     * objeto fuente.
     *
     * @param <T>    el tipo de los objetos a copiar (deben ser del mismo tipo)
     * @param source el objeto fuente del cual se obtendrán los valores no nulos
     * @param target el objeto destino en el cual se asignarán los valores no nulos
     * @throws UpdateMapperException    si ocurre un error durante la introspección
     *                                  o la invocación de métodos
     * @throws IllegalArgumentException si {@code source} o {@code target} son
     *                                  {@code null}
     */
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
