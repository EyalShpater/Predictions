package servlet;

import com.google.gson.Gson;
import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import impl.RunRequestDTO;
import impl.SimulationInitDataFromUserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

@WebServlet(GeneralConstants.RUN_SIMULATION_RESOURCE)
public class RunSimulationServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        System.out.println("in RunSimulationServlet");
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String reqBody = "";
        try {
            reqBody = gerReqBody(req);
        } catch (Exception e) {
            int x = 5;
        }

        SimulationInitDataFromUserDTO simulationInitData = gson.fromJson(reqBody, SimulationInitDataFromUserDTO.class);

        int simulationSerialNumber = engine.runNewSimulation(simulationInitData);

        resp.getWriter().println(simulationSerialNumber);
    }

    private String gerReqBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }

        return requestBody.toString();
    }
}
