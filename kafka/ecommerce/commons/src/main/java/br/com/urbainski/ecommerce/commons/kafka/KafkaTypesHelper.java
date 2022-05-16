package br.com.urbainski.ecommerce.commons.kafka;

public final class KafkaTypesHelper {

    private KafkaTypesHelper() {}

    public static KafkaTypes convertToKafkaTypes(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz) ? KafkaTypes.TEXT : KafkaTypes.JSON;
    }

}
