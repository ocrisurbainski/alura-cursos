package br.com.urbainski.ecommerce.kafka;

import br.com.urbainski.ecommerce.properties.KafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDefaultConsumer<A> {

    private final int TIMEOUT_DELAY_DEFAULT = 5000;

    private final int timeoutDelay;

    public AbstractDefaultConsumer() {

        this.timeoutDelay = TIMEOUT_DELAY_DEFAULT;
    }

    public AbstractDefaultConsumer(int timeoutDelay) {

        this.timeoutDelay = timeoutDelay;
    }

    public void consume() {

        try (var consumer = new KafkaConsumer<A, A>(KafkaProperties.getKafkaConsumerProperties(getGroupId()))) {

            subscribe(consumer);

            consumirMensagens(consumer);
        }
    }

    protected void subscribe(KafkaConsumer<A, A> consumer) {
        consumer.subscribe(getTopics().stream().map(Topics::getTopicName).collect(Collectors.toList()));
    }

    private void consumirMensagens(KafkaConsumer<A, A> consumer) {

        while (true) {

            var records = consumer.poll(Duration.ofMillis(1000));

            processarMensagens(records);

            try {
                Thread.sleep(timeoutDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processarMensagens(ConsumerRecords<A, A> records) {

        if (records.isEmpty()) {

            getLog().info("Nenhuma mensagem encontrada");
            return;
        }

        getLog().info(String.format("Encontrei %s registros.", records.count()));

        for (var record : records) {
            getLog().info("----------------------------------------------------");
            getLog().info("Processando nova mensagem");
            getLog().info(getClass().getName());
            getLog().info(String.format("Topic=%s", record.topic()));
            getLog().info(record.key().toString());
            getLog().info(record.value().toString());
            getLog().info(String.valueOf(record.partition()));
            getLog().info(String.valueOf(record.offset()));

            try {
                Thread.sleep(timeoutDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            getLog().info("Mensagem processada");
        }
    }

    public abstract String getGroupId();

    public abstract List<Topics> getTopics();

    public abstract Logger getLog();
}
