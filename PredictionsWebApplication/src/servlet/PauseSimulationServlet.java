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

@WebServlet(GeneralConstants.PAUSE_SIMULATION_RESOURCE)
public class PauseSimulationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String simulationIdRaw = req.getParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME);
        int simulationId;

        try {
            simulationId = Integer.parseInt(simulationIdRaw);
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().println("Simulation id must be an integer!");

            return;
        }

        resp.getWriter().println(new Gson().toJson(engine.isPaused(simulationId)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String simulationIdRaw = req.getParameter(GeneralConstants.SIMULATION_SERIAL_NUMBER_PARAMETER_NAME);
        int simulationId;

        try {
            simulationId = Integer.parseInt(simulationIdRaw);
        } catch (Exception exception) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().println("Simulation id must be an integer!");

            return;
        }

        engine.pauseSimulationBySerialNumber(simulationId);
    }
}
