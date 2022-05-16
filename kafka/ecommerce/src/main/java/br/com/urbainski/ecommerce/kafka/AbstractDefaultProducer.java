package br.com.urbainski.ecommerce.kafka;

import br.com.urbainski.ecommerce.properties.KafkaProperties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public abstract class AbstractDefaultProducer<K, V> implements AutoCloseable {

    private final KafkaProducer<K, V> producer;

    public AbstractDefaultProducer() {

        this.producer = new KafkaProducer<K, V>(getProperties());
    }

    public void send(K key, V message) {
        try {
            var record = new ProducerRecord<>(getTopic().name(), key, message);
            producer.send(record, getDefaultCallback()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected Callback getDefaultCallback() {
        return (recordMetadata, e) -> {
            if (e != null) {
                e.printStackTrace();
                return;
            }

            var logMessage = String.format(
                    "kafka mensagem enviada com sucesso :::topic=%s :::partition=%s :::offset=%s :::timestamp=%s",
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp());

            getLog().info(logMessage);
        };
    }

    public abstract Topics getTopic();

    public abstract Logger getLog();

    @Override
    public void close() {
        this.producer.close();
    }

    @SuppressWarnings("unchecked")
    private Properties getProperties() {
        Class<V> clazz = (Class<V>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        if (String.class.isAssignableFrom(clazz)) {
            return KafkaProperties.getSimpleKafkaProducerProperties();
        }

        return KafkaProperties.getJsonKafkaProducerProperties();
    }

}
