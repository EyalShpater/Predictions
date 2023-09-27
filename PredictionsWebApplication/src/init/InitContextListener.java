package init;

import constans.Constants;
import execution.simulation.api.PredictionsLogic;
import execution.simulation.impl.PredictionsLogicImpl;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import javax.xml.bind.JAXBException;

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


