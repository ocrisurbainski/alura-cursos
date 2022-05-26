package br.com.urbainski.ecommerce.http.servlet;

import br.com.urbainski.ecommerce.commons.kafka.CorrelationId;
import br.com.urbainski.ecommerce.commons.order.Order;
import br.com.urbainski.ecommerce.order.NewOrderProducerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class NewOrderServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(NewOrderServlet.class);

    private final NewOrderProducerService newOrderProducerService;

    public NewOrderServlet() {
        this.newOrderProducerService = new NewOrderProducerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var random = new Random();

        var emailAddress = req.getParameter("email");
        if (emailAddress == null || emailAddress.isEmpty()) {
            emailAddress = Math.random() + "@gmail.com";
        }

        var value = BigDecimal.ZERO;
        var valueStr = req.getParameter("value");
        if (valueStr == null || valueStr.isEmpty()) {
            value = getValorAletorioOrder(random);
        } else {
            value = new BigDecimal(valueStr);
        }

        var correlationId = new CorrelationId(this.getClass().getSimpleName());

        log.info("CorrelationId={}", correlationId.getId());

        var orderId = UUID.randomUUID().toString();

        var order = new Order(orderId, emailAddress, value);

        newOrderProducerService.send(correlationId, emailAddress, order);

        log.info("Nova ordem enviada com sucesso!");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Nova ordem enviada.");
    }

    @Override
    public void destroy() {
        super.destroy();

        this.newOrderProducerService.close();
    }

    private BigDecimal getValorAletorioOrder(Random random) {

        var primeiro = random.nextInt(10000);
        var segundo = random.nextInt(99);

        return new BigDecimal(primeiro + "." + segundo);
    }

}
