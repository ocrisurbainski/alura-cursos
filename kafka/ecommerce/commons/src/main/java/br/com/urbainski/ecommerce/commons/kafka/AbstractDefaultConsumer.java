package br.com.urbainski.ecommerce.commons.kafka;

import br.com.urbainski.ecommerce.commons.kafka.impl.DeadLetterProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public abstract class AbstractDefaultConsumer<K, V> {

    private DeadLetterProducer deadLetterProducer;

    public AbstractDefaultConsumer() {

    }

    public void consume() {

        try (var consumer = new KafkaConsumer<K, MyMessage<V>>(getProperties())) {

            subscribe(consumer);

            consumirMensagens(consumer);
        }
    }

    protected void subscribe(KafkaConsumer<K, MyMessage<V>> consumer) {
        consumer.subscribe(getTopics().stream().map(Topics::getTopicName).collect(Collectors.toList()));
    }

    private void consumirMensagens(KafkaConsumer<K, MyMessage<V>> consumer) {

        while (true) {

            var records = consumer.poll(Duration.ofMillis(100));

            processarMensagens(records);
        }
    }

    protected void processarMensagens(ConsumerRecords<K, MyMessage<V>> records) {

        if (records.isEmpty()) {

            getLog().info("Nenhuma mensagem encontrada");
            return;
        }

        getLog().info(String.format("Encontrei %s registros.", records.count()));

        for (var record : records) {

            var message = record.value();

            try {
                getLog().info("----------------------------------------------------");
                getLog().info("Processando nova mensagem");
                getLog().info(getClass().getName());
                getLog().info(String.format("Topic=%s", record.topic()));
                getLog().info(String.format("Chave=%s", record.key().toString()));
                getLog().info(String.format("Valor=%s", message.toString()));
                getLog().info(String.format("Partição=%s", record.partition()));
                getLog().info(String.format("Offset=%s", record.offset()));

                processarRecord(record);

                getLog().info("Mensagem processada");
            } catch (Exception ex) {

                ex.printStackTrace();

                getDeadLetterProducer().send(
                        message.getCorrelationId().continueWith(getClass().getSimpleName()),
                        record.key().toString(),
                        message.getPayload());
            }
        }
    }

    protected DeadLetterProducer getDeadLetterProducer() {

        if (deadLetterProducer == null) {
            deadLetterProducer = createDeadLetterProducer();
        }
        return deadLetterProducer;
    }

    protected DeadLetterProducer createDeadLetterProducer() {
        var firstTopic = getTopics().get(0);
        return new DeadLetterProducer(firstTopic);
    }

    public abstract String getGroupId();

    public abstract List<Topics> getTopics();

    public abstract Logger getLog();

    public void processarRecord(ConsumerRecord<K, MyMessage<V>> record) {

    }

    @SuppressWarnings("unchecked")
    private Properties getProperties() {
        var parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        var clazzKey = (Class<K>) parameterizedType.getActualTypeArguments()[0];
        var clazzValue = (Class<V>) parameterizedType.getActualTypeArguments()[1];

        return KafkaProperties.getKafkaConsumerProperties(
                getGroupId(),
                KafkaTypesHelper.convertToKafkaTypes(clazzKey),
                clazzValue.getName());
    }

}
