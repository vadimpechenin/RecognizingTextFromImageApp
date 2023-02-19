import core.ResourceManager;
import core.SessionManager;
import core.interaction.RequestExtractor;
import core.interaction.ResponsePacker;
import core.interaction.requestExtractors.BaseRequestExtractor;
import core.interaction.requestExtractors.EditContentRequestExtractor;
import core.interaction.responsePackers.SessionCloseResponsePacker;
import core.interaction.responsePackers.SessionOpenResponsePacker;
import db.HibernateSessionFactory;
import org.hibernate.SessionFactory;
import core.securityManager.SecurityManager;

public class MainServletEnvironment {
    final public SessionFactory hibernateSessionFactory;
    final public SessionManager sessionManager;
    final public SecurityManager securityManager;
    final public ResourceManager resourceManager;
    final public RequestExtractor baseRequestExtractor;
    final public RequestExtractor editContentRequestExtractor;
    final public ResponsePacker sessionOpenResponsePacker;
    final public ResponsePacker sessionCloseResponsePacker;

    private MainServletEnvironment(SessionFactory hibernateSessionFactory, SessionManager sessionManager,
                                  SecurityManager securityManager) {
        this.hibernateSessionFactory = hibernateSessionFactory;
        this.sessionManager = sessionManager;
        this.securityManager = securityManager;
        this.resourceManager = new ResourceManager();

        this.baseRequestExtractor = new BaseRequestExtractor();
        this.editContentRequestExtractor = new EditContentRequestExtractor();
        this.sessionCloseResponsePacker = new SessionCloseResponsePacker(resourceManager);
        this.sessionOpenResponsePacker = new SessionOpenResponsePacker(resourceManager);
    }

    public static MainServletEnvironment create() {
        boolean result;
        SessionFactory hibernateSessionFactory = null;
        SessionManager sessionManager = null;
        SecurityManager securityManager = null;
        try {
            hibernateSessionFactory = HibernateSessionFactory.getSessionFactory();
            sessionManager = new SessionManager();
            securityManager = new SecurityManager(hibernateSessionFactory, sessionManager);
            result = securityManager.init();
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
            return result ? new MainServletEnvironment(hibernateSessionFactory, sessionManager, securityManager): null;
        }
}
