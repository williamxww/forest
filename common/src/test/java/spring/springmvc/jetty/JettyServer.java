package spring.springmvc.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年5月17日
 * @since SDP V300R003C10
 */
public class JettyServer
{
    
    public static void main(String[] args)
        throws Exception
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        connector.setHost("localhost");
        server.addConnector(connector);
        
        

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        // http://localhost:8080/hello
        context.addServlet(new ServletHolder(new HelloServlet()), "/hello");
        
        // spring
        ServletContextHandler spingMvcHandler = new ServletContextHandler();
        spingMvcHandler.setContextPath("/spring");
        XmlWebApplicationContext ac = new XmlWebApplicationContext();
        ac.setConfigLocations(new String[] {"classpath:spring/springmvc/jetty/applicationContext.xml"});
        spingMvcHandler.addEventListener(new ContextLoaderListener(ac));
        spingMvcHandler.addServlet(new ServletHolder(new DispatcherServlet(ac)), "/*");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {spingMvcHandler});
        server.setHandler(handlers);
        
        server.start();
        server.join();
    }

}
