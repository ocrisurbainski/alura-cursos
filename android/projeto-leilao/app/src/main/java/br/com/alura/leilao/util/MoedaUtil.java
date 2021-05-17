package br.com.alura.leilao.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Cristian Urbainski
 * @since 1.0 (20/09/20)
 */
public abstract class MoedaUtil {

    private static final Locale brasil = new Locale("pt", "BR");

    public static String format(Double valor) {

        return NumberFormat.getCurrencyInstance(brasil).format(valor);
    }

}