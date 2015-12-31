package com.resources.facade;

import com.resources.entity.ModuleInRole;
import com.resources.entity.RoleAdmin;
import com.resources.pagination.admin.DefaultAdminPagination;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class ModuleInRoleFacade extends AbstractFacade {

    public ModuleInRoleFacade() {
        super(ModuleInRole.class);
    }

    public List findAllModuleIdByRoleId(int roleId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(ModuleInRole.class, "mr");
            cr.createAlias("roleId", "roleId");
            cr.add(Restrictions.eq("roleId.id", roleId));
            return cr.list();
        } catch (Exception e) {
            Logger.getLogger(RoleAdmin.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public int create(Integer roleId, Integer[] ids) throws Exception {
        Transaction trans = null;
        Session session = null;
        int result = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            session.createSQLQuery("delete from ModuleInRole where RoleID=:roleId").setParameter("roleId", roleId).executeUpdate();
            StringBuilder sB = new StringBuilder();
            sB.append("insert into ModuleInRole(RoleID,ModuleID) values ");
            for (Integer id : ids) {
                sB.append("(:roleId," + id + "),");
            }
            session.createSQLQuery(sB.toString().substring(0, sB.toString().length() - 1)).setParameter("roleId", roleId).executeUpdate();
            trans.commit();
            session.flush();
        } catch (HibernateException e) {
            try {
                if (trans != null) {
                    trans.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
