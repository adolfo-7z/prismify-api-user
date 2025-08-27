package com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation;

import com.ufro.dci.etransparency.etransparency_api_user.user.application.support.validation.annotation.ValidRut;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<ValidRut, String> {

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
