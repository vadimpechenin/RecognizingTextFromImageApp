import classes.RequestCode;
import core.interaction.*;
import handlers.SessionHandler;
import handlers.UsersInfoHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Класс сервлета, через который осуществляется взаимодействие клиента и сервера в проекте.
 *
 */
@WebServlet(name = "MainServlet", urlPatterns = "/handler")
public class MainServlet extends HttpServlet {


    private static class HandlerInfo {
        RequestHandler requestHandler;
        RequestExtractor requestExtractor;
        ResponsePacker responsePacker;

        public HandlerInfo(RequestHandler handler, RequestExtractor requestExtractor, ResponsePacker responsePacker) {
            this.requestHandler = handler;
            this.requestExtractor = requestExtractor;
            this.responsePacker = responsePacker;
        }
    }

    private volatile Boolean isInitialized;
    private final Object isInitializedLock = new Object();
    private final Map<String, HandlerInfo> handlers;
    private MainServletEnvironment environment;

    public MainServlet() {
        handlers = new HashMap<>();
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //Запуск сессии
     /*   try {
            UserService userService = new UserService(HibernateSessionFactory.getSessionFactory());
            List<User> users = userService.findAll();
            for (User user: users){
                System.out.println("user = " + user.getName());
            }
        }catch (Exception e){
            System.out.println("Сессия не открылась");
        }*/
        if(isInitialized == null) {
            synchronized(isInitializedLock) {
                if(isInitialized == null) {
                    isInitialized = initializeImpl();
                }
            }
        }
        log("Method init =)");
    }

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException, IOException {
        String command = null;
        try {
            if (isInitialized) {
                command = httpServletRequest.getParameter("cmd");
                HandlerInfo handlerInfo = handlers.get(command);
                if (handlerInfo != null) {
                    Request request = handlerInfo.requestExtractor.extract(httpServletRequest);
                    InnerResponseRecipient responseRecipient = new InnerResponseRecipient();
                    handlerInfo.requestHandler.executeRequest(responseRecipient, request);
                    handlerInfo.responsePacker.pack(request, responseRecipient.response, httpServletRequest, httpServletResponse);
                } else {
                    handleError(httpServletResponse, "templates/CommandNotSupported.html");
                }
            } else {
                handleError(httpServletResponse, "templates/InitializationFailed.html");
            }
        } catch (Exception e) {
            System.out.printf("Команда не поддерживается, код %s", command);
            handleError(httpServletResponse, "templates/ExceptionOccur.html");
            e.printStackTrace();
        }
        log("Method service =)");
        //super.service(httpServletRequest, httpServletResponse);
        //httpServletResponse.getWriter().write("Method service\n");
    }

    private void handleError(HttpServletResponse httpServletResponse, String errorTemplate) throws IOException {
        String pageText = environment.resourceManager.getResource(errorTemplate);
        HttpServletResponseBuilder.onStringResponse(httpServletResponse, HttpServletResponse.SC_BAD_REQUEST, HttpServletResponseBuilder.HTMLContentType, pageText);
    }

    @Override
    public void destroy() {
        super.destroy();
        log("Method desctoy =)");
    }

    private boolean initializeImpl() {
        boolean result;
        try {
            environment = MainServletEnvironment.create();
            result = environment != null;

            if (result) {
                RequestHandler sessionHandler = new SessionHandler(environment.sessionManager, environment.securityManager);
                register(RequestCode.SESSION_OPEN, sessionHandler, environment.editContentRequestExtractor, environment.sessionOpenResponsePacker);
                register(RequestCode.SESSION_CLOSE, sessionHandler, environment.baseRequestExtractor, environment.sessionCloseResponsePacker);

                RequestHandler usersInfoHandler = new UsersInfoHandler(environment.sessionManager, environment.securityManager);
                register(RequestCode.USERS_INFO, usersInfoHandler, environment.editContentRequestExtractor, environment.objectResponsePacker);
                register(RequestCode.CURRENT_USER_INFO, usersInfoHandler, environment.baseRequestExtractor, environment.objectResponsePacker);
                register(RequestCode.REGISTRATION_USER_INFO, usersInfoHandler, environment.entityRequestExtractor, environment.sessionOpenResponsePacker);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    private void register(RequestCode code, RequestHandler handler, RequestExtractor requestExtractor, ResponsePacker responsePacker) {
        handlers.put(code.toString(), new HandlerInfo(handler, requestExtractor, responsePacker));
    }

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            return "/webapp/MainServicePage.html";
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
        private final String userName;
        private final String password;
        private final String email;

        private RequestCustomer(String firstName, String lastName, String userName, String password, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.userName = lastName;
            this.password = password;
            this.email = email;
        }

        public static RequestCustomer fromRequestParameters(HttpServletRequest request) {
            return new RequestCustomer(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("email"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("firstname", firstName);
            request.setAttribute("lastname", lastName);
            request.setAttribute("username", userName);
            request.setAttribute("password", password);
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
            if (!StringValidator.validate(userName)) {
                violations.add("username is mandatory");
            }
            if (!StringValidator.validate(password)) {
                violations.add("Password is mandatory");
            }
            if (!EmailValidator.validate(email)) {
                violations.add("Email must be a well-formed address");
            }
            return violations;
        }
    }*/



}
