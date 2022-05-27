package br.com.urbainski.ecommerce.users.report.producer;

import br.com.urbainski.ecommerce.commons.kafka.consumer.ConsumerRunner;

public class ServiceReportUsersConsumerMain {

    public static void main(String[] args) {
        new ConsumerRunner<>(ReportUsersConsumerService::new).start(3);
    }

}
