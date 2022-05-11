package br.com.urbainski.ecommerce.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.regex.Pattern;

public abstract class AbstractDefaultPatternConsumer<A> extends AbstractDefaultConsumer<A> {

    public AbstractDefaultPatternConsumer() {

    }

    public AbstractDefaultPatternConsumer(int timeoutDelay) {
        super(timeoutDelay);
    }

    @Override
    protected void subscribe(KafkaConsumer<A, A> consumer) {
        if (getTopics() == null || getTopics().isEmpty()) {
            throw new IllegalStateException("Deve haver pelo menos um topic no lista.");
        }

        if (getTopics().size() > 1) {
            getLog().warn("Será utilizado apenas o primeiro item da lista, os demais serão ignorados.");
        }

        consumer.subscribe(Pattern.compile(getTopics().get(0).getTopicName()));
    }

}
