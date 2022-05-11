package br.com.urbainski.ecommerce.fraude;

import br.com.urbainski.ecommerce.kafka.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FraudeDetectorService extends AbstractDefaultConsumer<String> {

    private static final Logger log = LoggerFactory.getLogger(FraudeDetectorService.class);

    public static void main(String[] args) {
        var consumer = new FraudeDetectorService();
        consumer.consume();
    }

    @Override
    public String getGroupId() {
        return FraudeDetectorService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_NEW_ORDER);
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
