package sl314.util;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContext;

public class InitializeModelProperties implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String dataDirectory = context.getInitParameter("data-directory");
        
        if ( dataDirectory != null ) {
            
            context.setAttribute("sl314.model.dataDirectory", dataDirectory);
            context.log("The dataDirectory attribute has been set.");
        } else {
            context.log("The 'data-directory' context parameter was not set.");
        }
        
    } 
    
    public void contextDestroyed(ServletContextEvent event) {
       
    }
}
