package init;

import constants.Constants;
import execution.simulation.impl.PredictionsLogicImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InitContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute(Constants.PREDICTIONS_OBJECT_NAME, new PredictionsLogicImpl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("shout down");
    }
}


