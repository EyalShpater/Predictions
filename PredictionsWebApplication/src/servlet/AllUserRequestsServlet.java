package servlet;

import com.google.gson.Gson;
import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(GeneralConstants.GET_ALL_USER_REQUESTS_RESOURCE)
public class AllUserRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String userName = req.getParameter(GeneralConstants.USER_NAME_PARAMETER_NAME);

        if (userName.equals(GeneralConstants.ALL_REQUESTS_KEY_WORD_NAME)) {
            resp.getWriter().println(gson.toJson(engine.getAllRequestsSimulationData()));
        } else {
            resp.getWriter().println(gson.toJson(engine.getRequestsSimulationDataByUser(userName)));
        }
    }
}
