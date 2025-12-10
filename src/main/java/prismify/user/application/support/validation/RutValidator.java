package prismify.user.application.support.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import prismify.user.application.support.validation.annotation.ValidRut;

/**
 * Validador de RUT que implementa la interfaz {@link ConstraintValidator}.
 * <p>
 * Esta clase permite validar que un RUT (Rol Único Tributario) tenga un formato
 * válido y
 * que su dígito verificador (DV) sea correcto según el algoritmo oficial.
 *
 * <p>
 * Ejemplo de RUT válido: <code>12.345.678-5</code>
 * <p>
 * Ejemplo de RUT válido con DV K: <code>12.345.678-K</code>
 *
 * @author Adolfo Plaza
 */
public class RutValidator implements ConstraintValidator<ValidRut, String> {

    /**
     * Verifica si un RUT es válido.
     * <p>
     * El método:
     * <ul>
     * <li>Permite valores nulos o vacíos (se consideran válidos para no interferir
     * con otras validaciones).</li>
     * <li>Elimina puntos y guiones del RUT.</li>
     * <li>Valida que el formato corresponda a 7 u 8 dígitos seguidos de un dígito
     * verificador
     * (número del 0 al 9 o la letra K).</li>
     * <li>Calcula y compara el dígito verificador real.</li>
     * </ul>
     *
     * @param rut     el RUT a validar, en formato con o sin puntos/guion.
     * @param context el contexto de validación proporcionado por Bean Validation.
     * @return {@code true} si el RUT es nulo, vacío o válido;
     *         {@code false} si el formato o el dígito verificador son incorrectos.
     */
    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
        if (rut == null || rut.isEmpty())
            return true;
        rut = rut.replace(".", "").replace("-", "").toUpperCase();
        if (!rut.matches("^\\d{7,8}[0-9K]$"))
            return false;
        String numberPart = rut.substring(0, rut.length() - 1);
        char dv = rut.charAt(rut.length() - 1);
        try {
            int rutInt = Integer.parseInt(numberPart);
            return calculateDV(rutInt) == dv;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calcula el dígito verificador (DV) correspondiente a un número de RUT.
     * <p>
     * El algoritmo oficial asigna pesos cíclicos del 2 al 7 a los dígitos del RUT
     * (de derecha a izquierda), suma los productos, y obtiene el resto de la
     * división por 11.
     * El DV resultante es:
     * <ul>
     * <li>Un número entre 0 y 9, si el cálculo lo determina.</li>
     * <li>'K' en caso de que el resultado sea 10.</li>
     * </ul>
     *
     * @param rut el número del RUT (sin incluir el dígito verificador).
     * @return el carácter correspondiente al dígito verificador calculado.
     */
    private char calculateDV(int rut) {
        int m = 0;
        int s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        return (char) (s != 0 ? s + 47 : 'K');
    }

}
