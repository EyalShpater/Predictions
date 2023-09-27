package init;

import execution.simulation.impl.PredictionsLogicImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet
@WebListener
public class InitContextListener extends HttpServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // getServletContext().setAttribute("engine", new PredictionsLogicImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("shout down");
    }
}


