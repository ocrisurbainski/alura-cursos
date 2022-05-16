package br.com.urbainski.ecommerce.commons.kafka;

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
        var parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        var clazzKey = (Class<K>) parameterizedType.getActualTypeArguments()[0];
        var clazzValue = (Class<V>) parameterizedType.getActualTypeArguments()[1];

        return KafkaProperties.getKafkaProducerProperties(
                KafkaTypesHelper.convertToKafkaTypes(clazzKey),
                KafkaTypesHelper.convertToKafkaTypes(clazzValue));
    }

}
