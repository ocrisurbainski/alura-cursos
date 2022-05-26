package br.com.urbainski.ecommerce.commons.kafka;

import br.com.urbainski.ecommerce.commons.serializer.GsonDeserializer;
import br.com.urbainski.ecommerce.commons.serializer.GsonSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public final class KafkaProperties {

    private static final String KAFKA_SERVER = "localhost:9092";

    public static final String TYPE_CLASS_CONFIG = "type.class.config";

    private KafkaProperties() {
    }

    public static Properties getKafkaProducerProperties(KafkaTypes keyType) {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        // confirmação de todas as replicas do kafka receberão a mensagem
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyType.getClassSerializer());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    public static Properties getKafkaConsumerProperties(String groudId, KafkaTypes keyType, String typeClass) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groudId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyType.getClassDeserializer());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(TYPE_CLASS_CONFIG, typeClass);
        return properties;
    }

}
