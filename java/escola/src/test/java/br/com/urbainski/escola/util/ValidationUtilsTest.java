package br.com.urbainski.escola.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationUtilsTest {

    @Test
    public void test_isEmailInvalid() {
        Assertions.assertFalse(ValidationUtils.isEmailInvalid("fula@example.com"));
        Assertions.assertFalse(ValidationUtils.isEmailInvalid("fula@example.com.br"));
        Assertions.assertTrue(ValidationUtils.isEmailInvalid(null));
        Assertions.assertTrue(ValidationUtils.isEmailInvalid(""));
        Assertions.assertTrue(ValidationUtils.isEmailInvalid("fulano"));
        Assertions.assertTrue(ValidationUtils.isEmailInvalid("fula@example"));
    }

    @Test
    public void test_isNotEmpty() {
        Assertions.assertFalse(ValidationUtils.isNotEmpty(null));
        Assertions.assertFalse(ValidationUtils.isNotEmpty(""));
        Assertions.assertTrue(ValidationUtils.isNotEmpty(" "));
        Assertions.assertTrue(ValidationUtils.isNotEmpty("valor"));
    }

    @Test
    public void test_isEmpty() {
        Assertions.assertTrue(ValidationUtils.isEmpty(null));
        Assertions.assertTrue(ValidationUtils.isEmpty(""));
        Assertions.assertFalse(ValidationUtils.isEmpty(" "));
        Assertions.assertFalse(ValidationUtils.isEmpty("valor"));
    }

    @Test
    public void test_isCpfFormatInvalid() {
        Assertions.assertFalse(ValidationUtils.isCpfFormatInvalid("04553775026"));
        Assertions.assertTrue(ValidationUtils.isCpfFormatInvalid(null));
        Assertions.assertTrue(ValidationUtils.isCpfFormatInvalid(""));
        Assertions.assertTrue(ValidationUtils.isCpfFormatInvalid("0455377502"));
        Assertions.assertTrue(ValidationUtils.isCpfFormatInvalid("045537750262"));
        Assertions.assertTrue(ValidationUtils.isCpfFormatInvalid("abcdefghijk"));
    }

    @Test
    public void test_isDDDInvalid() {
        Assertions.assertTrue(ValidationUtils.isDDDInvalid(null));
        Assertions.assertTrue(ValidationUtils.isDDDInvalid(""));
        Assertions.assertTrue(ValidationUtils.isDDDInvalid("aa"));
        Assertions.assertTrue(ValidationUtils.isDDDInvalid("aaa"));
        Assertions.assertTrue(ValidationUtils.isDDDInvalid("111"));
        Assertions.assertFalse(ValidationUtils.isDDDInvalid("49"));
    }

    @Test
    public void test_isNumeroTelefoneInvalid() {
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid(null));
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid(""));
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid("a"));
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid("aaaaaa"));
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid("1"));
        Assertions.assertTrue(ValidationUtils.isNumeroTelefoneInvalid("9999999999999"));
        Assertions.assertFalse(ValidationUtils.isNumeroTelefoneInvalid("999999999"));
        Assertions.assertFalse(ValidationUtils.isNumeroTelefoneInvalid("99999999"));
    }

}