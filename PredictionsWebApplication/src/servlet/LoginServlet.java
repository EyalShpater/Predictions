package servlet;

import constants.Constants;
import execution.simulation.api.PredictionsLogic;
import general.constants.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import utils.ServletUtils;

import java.io.IOException;

@WebServlet(GeneralConstants.LOGIN_RESOURCE)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PredictionsLogic engine = (PredictionsLogic) getServletContext().getAttribute(Constants.PREDICTIONS_OBJECT_NAME);
        String userName = req.getParameter(GeneralConstants.USER_NAME_PARAMETER_NAME);

        if (userName == null || userName.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            userName = userName.trim();
            synchronized (this) {
                if (engine.isUserLoggedIn(userName)) {
                    String err = "Username: " + userName + " already logged in the system";
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.getOutputStream().print(err);
                } else {
                    engine.loginUser(userName);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println(userName + " successfully logged in!");
                }
            }
        }
    }
}
