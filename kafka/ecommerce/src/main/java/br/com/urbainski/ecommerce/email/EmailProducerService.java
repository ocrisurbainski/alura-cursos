package br.com.urbainski.ecommerce.email;

import br.com.urbainski.ecommerce.kafka.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.kafka.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailProducerService extends AbstractDefaultProducer<String> {

    private static final Logger log = LoggerFactory.getLogger(EmailProducerService.class);
    
    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_SEND_EMAIL;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
