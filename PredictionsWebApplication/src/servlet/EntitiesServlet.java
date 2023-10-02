package servlet;

import com.google.gson.Gson;
import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import impl.EntityDefinitionDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(GeneralConstants.GET_ENTITIES_INFO_RESOURCE)
public class EntitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String worldName = req.getParameter(GeneralConstants.WORLD_NAME_PARAMETER_NAME);
        List<EntityDefinitionDTO> entities;

        if (worldName == null) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().println("The \"" + GeneralConstants.WORLD_NAME_PARAMETER_NAME + "\" query parameter is required");
            return;
        }

        entities = engine.getWorldDetails(worldName).getEntities();
        resp.getWriter().println(new Gson().toJson(entities));
    }
}
