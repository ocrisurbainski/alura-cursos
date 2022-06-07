package br.com.urbainski.escola.shared.util;

import java.util.regex.Pattern;

public final class ValidationUtils {

    private static final Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern patterCpf = Pattern.compile("^[0-9]{11}$");
    private static final Pattern patterDdd = Pattern.compile("^[0-9]{2}$");
    private static final Pattern patterNumeroTelefone = Pattern.compile("^[0-9]{8,9}$");

    private ValidationUtils() {}

    public static boolean isEmailInvalid(String enderecoEmail) {
        return isEmpty(enderecoEmail) || !patternEmail.matcher(enderecoEmail).matches();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isCpfFormatInvalid(String numero) {
        return isEmpty(numero) || !patterCpf.matcher(numero).matches();
    }

    public static boolean isDDDInvalid(String ddd) {
        return isEmpty(ddd) || !patterDdd.matcher(ddd).matches();
    }

    public static boolean isNumeroTelefoneInvalid(String numeroTelefone) {
        return isEmpty(numeroTelefone) || !patterNumeroTelefone.matcher(numeroTelefone).matches();
    }

}
