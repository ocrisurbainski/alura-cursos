package br.com.urbainski.ecommerce.commons.kafka.producer.impl;

import br.com.urbainski.ecommerce.commons.kafka.producer.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadLetterProducer extends AbstractDefaultProducer<String, Object> {

    private static final Logger log = LoggerFactory.getLogger(DeadLetterProducer.class);

    private final Topics topics;

    public DeadLetterProducer(Topics topics) {
        this.topics = topics;
    }

    @Override
    public Topics getTopic() {
        return topics;
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    protected String getTopicName() {
        return getTopic().getDeadLetterTopicName();
    }

}
