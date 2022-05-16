package br.com.urbainski.ecommerce.properties;

import br.com.urbainski.ecommerce.serializer.GsonSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public final class KafkaProperties {

    private static final String KAFKA_SERVER = "localhost:9092";

    private KafkaProperties() {}

    public static Properties getSimpleKafkaProducerProperties() {
        var properties = getDefaultKafkaProducerProperties();
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    public static Properties getJsonKafkaProducerProperties() {
        var properties = getDefaultKafkaProducerProperties();
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    private static Properties getDefaultKafkaProducerProperties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        return properties;
    }

    public static Properties getKafkaConsumerProperties(String groudId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groudId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

}
