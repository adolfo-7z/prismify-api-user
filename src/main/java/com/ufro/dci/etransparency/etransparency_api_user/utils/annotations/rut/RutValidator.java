package com.ufro.dci.etransparency.etransparency_api_user.utils.annotations.rut;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<ValidRut, String> {

    @Override
    public void initialize(ValidRut constraintAnnotation) {
    }

    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }
        return isValidRUT(rut);
    }

    private boolean isValidRUT(String rut) {
        try {
            rut = rut.toUpperCase().replace(".", "").replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }

            char expectedDv = (s != 0) ? (char) (s + 47) : 'K';
            return dv == expectedDv;

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return false;
        }
    }
}
