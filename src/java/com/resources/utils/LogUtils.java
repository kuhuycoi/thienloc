/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resources.utils;

import com.resources.entity.Action;
import com.resources.entity.Admin;
import com.resources.entity.AdminLogs;
import com.resources.entity.Module;
import com.resources.facade.HibernateConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author HiepNguyen
 */
public class LogUtils {

    public static void logs(int adminID,int actionID, int moduleID, String content){
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            AdminLogs log = new AdminLogs();
            log.setAdmin(session.get(Admin.class, adminID));
            log.setAction(session.get(Action.class, actionID));
            log.setModule(session.get(Module.class, moduleID));
            log.setContent(content);
            session.save(log);
            trans.commit();
        } catch (Exception e) {
            try {
                if (trans != null) {
                    trans.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            e.printStackTrace();
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

}
