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

@WebServlet(GeneralConstants.GET_WORLD_GENERAL_INFORMATION_RESOURCE)
public class WorldGeneralInformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String worldName = req.getParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME);

        resp.getWriter().println(new Gson().toJson(engine.getGridInformation(worldName)));
    }
}
