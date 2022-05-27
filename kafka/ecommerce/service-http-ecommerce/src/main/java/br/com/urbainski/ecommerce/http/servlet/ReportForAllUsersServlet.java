package br.com.urbainski.ecommerce.http.servlet;

import br.com.urbainski.ecommerce.commons.kafka.CorrelationId;
import br.com.urbainski.ecommerce.report.forall.users.producer.ReportForAllUsersProducerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class ReportForAllUsersServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ReportForAllUsersServlet.class);

    private final ReportForAllUsersProducerService reportForAllUsersProducerService;

    public ReportForAllUsersServlet() {
        this.reportForAllUsersProducerService = new ReportForAllUsersProducerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var correlationId = new CorrelationId(getClass().getSimpleName());

        log.info("CorrelationId={}", correlationId.getId());

        this.reportForAllUsersProducerService.send(correlationId, UUID.randomUUID().toString(), "");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("Relatório para todos os usuários solicitado com sucesso.");
    }

    @Override
    public void destroy() {
        super.destroy();
        this.reportForAllUsersProducerService.close();
    }

}
