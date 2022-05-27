package br.com.urbainski.ecommerce.users.report.producer;

import br.com.urbainski.ecommerce.commons.kafka.MyMessage;
import br.com.urbainski.ecommerce.commons.kafka.Topics;
import br.com.urbainski.ecommerce.commons.kafka.consumer.AbstractDefaultConsumer;
import br.com.urbainski.ecommerce.commons.user.Users;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ReportUsersConsumerService extends AbstractDefaultConsumer<String, Users> {

    private static final Logger log = LoggerFactory.getLogger(ReportUsersConsumerService.class);

    @Override
    public void processarRecord(ConsumerRecord<String, MyMessage<Users>> record) {
        super.processarRecord(record);

        var user = record.value().getPayload();

        log.info("Gerando relatório para o usuário: {}", user);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Relatório gerado com sucesso para o usuário: {}", user);
    }

    @Override
    public String getGroupId() {
        return ReportUsersConsumerService.class.getSimpleName();
    }

    @Override
    public List<Topics> getTopics() {
        return List.of(Topics.ECOMMERCE_REPORT_USER);
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
