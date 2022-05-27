package br.com.urbainski.ecommerce.users.report.producer;

import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.kafka.producer.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.commons.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportUserProducerService extends AbstractDefaultProducer<String, Users> {

    private static final Logger log = LoggerFactory.getLogger(ReportUserProducerService.class);

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_REPORT_USER;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
