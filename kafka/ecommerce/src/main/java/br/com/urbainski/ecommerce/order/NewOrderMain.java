package br.com.urbainski.ecommerce.order;

import br.com.urbainski.ecommerce.email.EmailProducerService;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class NewOrderMain {

    public static void main(String[] args) {
        var random = new Random();
        try (var newOrderService = new NewOrderService()) {
            try (var emailService = new EmailProducerService()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();

                    var order = new Order(userId, orderId, BigDecimal.valueOf(random.nextDouble()));

                    newOrderService.send(userId, order);
                    emailService.send(userId, String.format("A venda da order: %s está sendo processada, aguarde...", orderId));
                }
            }
        }

    }


}
