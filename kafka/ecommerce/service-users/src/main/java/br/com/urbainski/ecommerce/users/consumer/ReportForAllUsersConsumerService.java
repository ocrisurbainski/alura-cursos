package br.com.urbainski.ecommerce.users.consumer;

import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.kafka.consumer.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.users.report.producer.ReportUserProducerService;
import br.com.urbainski.ecommerce.users.repository.UsersRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReportForAllUsersConsumerService extends AbstractDefaultConsumer<String, String> {

    private static final Logger log = LoggerFactory.getLogger(ReportForAllUsersConsumerService.class);
    private final UsersRepository usersRepository;
    private final ReportUserProducerService reportUserProducerService;

    public ReportForAllUsersConsumerService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.reportUserProducerService = new ReportUserProducerService();
    }

    @Override
    public void processarRecord(ConsumerRecord<String, MyMessage<String>> record) {
        super.processarRecord(record);

        var users = usersRepository.findAll();
        users.forEach(user -> reportUserProducerService.sendAsync(
                record.value().getCorrelationId().continueWith(ReportForAllUsersConsumerService.class.getSimpleName()),
                user.getEmail(),
                user
        ));
    }

    @Override
    public String getGroupId() {
        return ReportForAllUsersConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_REPORT_FOR_ALL_USERS);
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
