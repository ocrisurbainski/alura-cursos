package br.com.urbainski.ecommerce.report.forall.users.producer;

import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.kafka.producer.AbstractDefaultProducer;
import br.com.urbainski.ecommerce.commons.user.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportForAllUsersProducerService extends AbstractDefaultProducer<String, String> {

    private static final Logger log = LoggerFactory.getLogger(ReportForAllUsersProducerService.class);

    @Override
    public Topics getTopic() {
        return Topics.ECOMMERCE_REPORT_FOR_ALL_USERS;
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
