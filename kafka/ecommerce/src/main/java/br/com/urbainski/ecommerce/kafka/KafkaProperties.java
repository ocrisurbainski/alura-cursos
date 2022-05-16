package br.com.urbainski.ecommerce.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public final class KafkaProperties {

    private static final String KAFKA_SERVER = "localhost:9092";

    public static final String TYPE_CLASS_CONFIG = "type.class.config";

    private KafkaProperties() {
    }

    public static Properties getKafkaProducerProperties(KafkaTypes keyType, KafkaTypes valueType) {
        var properties = getDefaultKafkaProducerProperties();
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyType.getClassSerializer());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueType.getClassSerializer());
        return properties;
    }

    private static Properties getDefaultKafkaProducerProperties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        return properties;
    }

    public static Properties getKafkaConsumerProperties(String groudId, KafkaTypes keyType, KafkaTypes valueType, String typeClass) {
        var properties = getDefaultKafkaConsumerProperties(groudId);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyType.getClassDeserializer());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueType.getClassDeserializer());
        properties.setProperty(TYPE_CLASS_CONFIG, typeClass);
        return properties;
    }

    private static Properties getDefaultKafkaConsumerProperties(String groudId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groudId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

}
