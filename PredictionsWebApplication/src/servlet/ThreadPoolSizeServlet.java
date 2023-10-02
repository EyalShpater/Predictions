package servlet;

import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(GeneralConstants.SET_THREAD_POOL_SIZE_RESOURCE)
public class ThreadPoolSizeServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);

        try {
            int size = Integer.parseInt(req.getParameter(GeneralConstants.THREAD_POOL_SIZE_PARAMETER_NAME));

            engine.setThreadPoolSize(size);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("succeed");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("something went wrong");
        }
    }
}
