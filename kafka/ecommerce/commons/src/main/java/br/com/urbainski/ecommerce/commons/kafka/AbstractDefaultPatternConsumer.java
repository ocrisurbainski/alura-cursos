package br.com.urbainski.ecommerce.commons.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.regex.Pattern;

public abstract class AbstractDefaultPatternConsumer<K, V> extends AbstractDefaultConsumer<K, V> {

    public AbstractDefaultPatternConsumer() {

    }

    @Override
    protected void subscribe(KafkaConsumer<K, MyMessage<V>> consumer) {
        if (getTopics() == null || getTopics().isEmpty()) {
            throw new IllegalStateException("Deve haver pelo menos um topic no lista.");
        }

        if (getTopics().size() > 1) {
            getLog().warn("Será utilizado apenas o primeiro item da lista, os demais serão ignorados.");
        }

        consumer.subscribe(Pattern.compile(getTopics().get(0).getTopicName()));
    }

}
