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
import java.util.Properties;

@WebServlet(GeneralConstants.CHANGE_REQUEST_STATUS_RESOURCE)
public class ChangeRequestStatusServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        Properties prop = new Properties();
        prop.load(req.getInputStream());

        String requestIdRaw = prop.getProperty(GeneralConstants.REQUEST_ID_PARAMETER_NAME);
        String isApprovedRaw = prop.getProperty(GeneralConstants.NEW_STATUS_PARAMETER_NAME);
        String userName = prop.getProperty(GeneralConstants.USER_NAME_PARAMETER_NAME);
        String error = null;
        int requestID = 0;
        boolean isApproved = false;

        try {
            requestID = Integer.parseInt(requestIdRaw);
            isApproved = Boolean.parseBoolean(isApprovedRaw);
        } catch (NumberFormatException nfe) {
            error = "Error ! one of the arguments is not a number";
        }

        if (error != null) {
            resp.getWriter().println(error);
        } else {
            if (isApproved) {
                engine.acceptUserRequest(requestID, userName);
            } else {
                engine.declineUserRequest(requestID);
            }
        }
    }
}
