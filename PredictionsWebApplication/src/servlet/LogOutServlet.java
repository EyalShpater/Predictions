package servlet;

import constants.Constants;
import execution.simulation.impl.PredictionsLogicImpl;
import general.constants.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(GeneralConstants.LOGOUT_RESOURCE)
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogicImpl engine = (PredictionsLogicImpl) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String userName = req.getParameter(Constants.USERNAME);

        if (engine.isUserLoggedIn(userName)) {
            engine.logOutUser(userName);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(userName + " successfully logged out");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("This usr does not logged in!");
        }
    }
}
