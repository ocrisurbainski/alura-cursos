package br.com.urbainski.ecommerce.log;

import br.com.urbainski.ecommerce.commons.kafka.producer.AbstractDefaultPatternConsumer;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LogConsumerService extends AbstractDefaultPatternConsumer<String, String> {

    private static final Logger log = LoggerFactory.getLogger(LogConsumerService.class);

    public LogConsumerService() {}

    @Override
    public String getGroupId() {
        return LogConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_ALL);
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
