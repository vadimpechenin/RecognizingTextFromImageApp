import entities.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import validators.EmailValidator;
import validators.StringValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс сервлета, через который осуществляется взаимодействие клиента и сервера в проекте.
 */
@WebServlet(name = "MainServlet", urlPatterns = "/handler")
public class MainServlet extends HttpServlet {

    private List<Customer> customers;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        customers = new ArrayList<>();
        customers.add(new Customer("Егор", "Печенин", "e@mai.ru"));
        log("Method init =)");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        super.service(req, resp);
        resp.getWriter().write("Method service\n");
    }

    @Override
    public void destroy() {
        super.destroy();
        log("Method desctoy =)");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        /*     resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello!");
        printWriter.close();
        resp.setStatus(200);*/

        PrintWriter printWriter = resp.getWriter();
        printWriter.println("<html>");
        printWriter.println("<h1>Hello!</h1>");
        printWriter.println("</html>");

        //redirect
        //resp.sendRedirect("/ProcessingServer/testtt.html");
        //forward
        RequestDispatcher dispatcher = req.getRequestDispatcher("/testtt.html");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestCustomer customer = RequestCustomer.fromRequestParameters(request);
        customer.setAsRequestAttributes(request);
        List<String> violations = customer.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        String url = determineUrl(violations);
        forwardResponse(url, request, response);
    }

    private String determineUrl(List<String> violations) {
        if (!violations.isEmpty()) {
            return "/";
        } else {
            return "/WEB-INF/views/customerinfo.jsp";
        }
    }

    private void forwardResponse(String url, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestCustomer {

        private final String firstName;
        private final String lastName;
        private final String email;

        private RequestCustomer(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public static RequestCustomer fromRequestParameters(HttpServletRequest request) {
            return new RequestCustomer(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("email"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("email", email);
        }

        public List<String> validate() {
            List<String> violations = new ArrayList<>();
            if (!StringValidator.validate(firstName)) {
                violations.add("First Name is mandatory");
            }
            if (!StringValidator.validate(lastName)) {
                violations.add("Last Name is mandatory");
            }
            if (!EmailValidator.validate(email)) {
                violations.add("Email must be a well-formed address");
            }
            return violations;
        }
    }
}
