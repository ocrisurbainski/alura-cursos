package br.com.urbainski.ecommerce.http;

import br.com.urbainski.ecommerce.http.servlet.NewOrderServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ServiceHttpEcommerceMain {

    public static void main(String[] args) throws Exception {
        var context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new NewOrderServlet()), "/new");

        var server = new Server(8080);
        server.setHandler(context);
        server.start();
        server.join();
    }
}
