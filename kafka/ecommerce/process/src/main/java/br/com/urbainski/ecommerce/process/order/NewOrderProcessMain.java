package br.com.urbainski.ecommerce.process.order;

import br.com.urbainski.ecommerce.commons.email.Email;
import br.com.urbainski.ecommerce.commons.order.Order;
import br.com.urbainski.ecommerce.email.EmailProducerService;
import br.com.urbainski.ecommerce.order.NewOrderProducerService;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class NewOrderProcessMain {

    public static void main(String[] args) {
        var random = new Random();
        try (var newOrderService = new NewOrderProducerService()) {
            try (var emailService = new EmailProducerService()) {
                for (var i = 0; i < 10; i++) {
                    var orderId = UUID.randomUUID().toString();
                    var emailAddress = Math.random() + "@gmail.com";

                    var order = new Order(orderId, emailAddress, getValorAletorioOrder(random));

                    newOrderService.send(emailAddress, order);

                    var email = new Email(
                            orderId,
                            "Nova venda",
                            String.format("A venda da order: %s está sendo processada, aguarde...", orderId),
                            emailAddress);

                    emailService.send(emailAddress, email);
                }
            }
        }
    }

    private static BigDecimal getValorAletorioOrder(Random random) {

        var primeiro = random.nextInt(10000);
        var segundo = random.nextInt(99);

        return new BigDecimal(primeiro + "." + segundo);
    }

}