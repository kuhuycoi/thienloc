package com.resources.listener;

import com.resources.facade.HibernateConfiguration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
public class GeneralContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("MAIN_PROPERTIES_FILE_PATH", sce.getServletContext().getRealPath("/WEB-INF/Config.properties"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateConfiguration.getInstance().shutdown();
    }
}
