package br.com.urbainski.ecommerce.kafka;

public final class KafkaTypesHelper {

    private KafkaTypesHelper() {}

    public static KafkaTypes convertToKafkaTypes(Class<?> clazz) {
        return String.class.isAssignableFrom(clazz) ? KafkaTypes.TEXT : KafkaTypes.JSON;
    }

}
