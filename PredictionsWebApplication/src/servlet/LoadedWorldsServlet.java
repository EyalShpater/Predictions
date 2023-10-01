package servlet;

import com.google.gson.Gson;
import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import impl.WorldDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(GeneralConstants.GET_LOADED_WORLDS_RESOURCE)
public class LoadedWorldsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        List<WorldDTO> worlds = engine.getAllWorldsDTO();

        resp.getWriter().println(new Gson().toJson(worlds));
    }
}
