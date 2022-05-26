package br.com.urbainski.ecommerce.commons.kafka.producer;

import br.com.urbainski.ecommerce.commons.kafka.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public abstract class AbstractDefaultProducer<K, V> implements AutoCloseable {

    private final KafkaProducer<K, MyMessage<V>> producer;

    public AbstractDefaultProducer() {

        this.producer = new KafkaProducer<K, MyMessage<V>>(getProperties());
    }

    public void send(CorrelationId correlationId, K key, V message) {
        try {
            var myMessage = new MyMessage<V>(correlationId, message);
            var record = new ProducerRecord<>(getTopicName(), key, myMessage);
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

    protected String getTopicName() {
        return getTopic().getTopicName();
    }

    @SuppressWarnings("unchecked")
    private Properties getProperties() {
        var parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        var clazzKey = (Class<K>) parameterizedType.getActualTypeArguments()[0];

        return KafkaProperties.getKafkaProducerProperties(KafkaTypesHelper.convertToKafkaTypes(clazzKey));
    }

}
