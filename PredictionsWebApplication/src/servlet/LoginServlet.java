package servlet;

import constants.Constants;
import general.constants.GeneralConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.api.UserManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(GeneralConstants.LOGIN_PAGE)
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserManager userManager = ServletUtils.getUserManager(getServletContext());
        String usernameFromSession = SessionUtils.getUsername(req);

        if (usernameFromSession == null) {
            String userNameQueryParam = req.getParameter(Constants.USERNAME);

            if (userNameQueryParam == null || userNameQueryParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                System.out.println("1");
            } else {
                userNameQueryParam = userNameQueryParam.trim();
                System.out.println("2");
            }

            synchronized (this) {
                if (userManager.isUserExists(userNameQueryParam)) {
                    String err = "Username: " + userNameQueryParam + " already exist in the system";
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.getOutputStream().print(err);
                    System.out.println("3");
                } else {
                    userManager.addUser(userNameQueryParam);
                    req.getSession(true).setAttribute(Constants.USERNAME, userNameQueryParam);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    System.out.println("4");
                }
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            System.out.println("5");
        }
    }
}
