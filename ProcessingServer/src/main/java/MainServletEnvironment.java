import core.ResourceManager;
import core.SessionManager;
import core.documentManager.DocumentManager;
import core.interaction.RequestExtractor;
import core.interaction.ResponsePacker;
import core.interaction.requestExtractors.BaseRequestExtractor;
import core.interaction.requestExtractors.EditContentRequestExtractor;
import core.interaction.requestExtractors.entityRequestExtractor.EntityRequestExtractor;
import core.interaction.requestExtractors.entityRequestExtractor.EntityWithViolationsRequestExtractor;
import core.interaction.responsePackers.ObjectResponsePacker;
import core.interaction.responsePackers.SessionCloseResponsePacker;
import core.interaction.responsePackers.SessionOpenResponsePacker;
import db.HibernateSessionFactory;
import org.hibernate.SessionFactory;
import core.securityManager.SecurityManager;

public class MainServletEnvironment {
    final public SessionFactory hibernateSessionFactory;
    final public SessionManager sessionManager;
    final public SecurityManager securityManager;
    final public DocumentManager documentManager;
    final public ResourceManager resourceManager;
    final public RequestExtractor baseRequestExtractor;
    final public RequestExtractor editContentRequestExtractor;
    final public EntityRequestExtractor entityRequestExtractor;
    final public EntityWithViolationsRequestExtractor entityWithViolationsRequestExtractor;
    final public ResponsePacker sessionOpenResponsePacker;
    final public ResponsePacker sessionCloseResponsePacker;
    final public ObjectResponsePacker objectResponsePacker;

    private MainServletEnvironment(SessionFactory hibernateSessionFactory, SessionManager sessionManager,
                                  SecurityManager securityManager,DocumentManager documentManager) {
        this.hibernateSessionFactory = hibernateSessionFactory;
        this.sessionManager = sessionManager;
        this.securityManager = securityManager;
        this.resourceManager = new ResourceManager();
        this.documentManager = documentManager;

        this.baseRequestExtractor = new BaseRequestExtractor();
        this.editContentRequestExtractor = new EditContentRequestExtractor();
        this.entityRequestExtractor = new EntityRequestExtractor();
        this.entityWithViolationsRequestExtractor = new EntityWithViolationsRequestExtractor();

        this.objectResponsePacker = new ObjectResponsePacker(resourceManager);
        this.sessionCloseResponsePacker = new SessionCloseResponsePacker(resourceManager);
        this.sessionOpenResponsePacker = new SessionOpenResponsePacker(resourceManager);

    }

    public static MainServletEnvironment create() {
        boolean result;
        SessionFactory hibernateSessionFactory = null;
        SessionManager sessionManager = null;
        SecurityManager securityManager = null;
        DocumentManager documentManager = null;
        try {
            hibernateSessionFactory = HibernateSessionFactory.getSessionFactory();
            sessionManager = new SessionManager();
            securityManager = new SecurityManager(hibernateSessionFactory, sessionManager);
            documentManager = new DocumentManager(hibernateSessionFactory, sessionManager);
            result = securityManager.init();
            } catch (Exception e) {
                e.printStackTrace();
                result = false;
            }
            return result ? new MainServletEnvironment(hibernateSessionFactory, sessionManager, securityManager, documentManager): null;
        }
}
