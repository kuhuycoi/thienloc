package com.resources.facade;

import com.resources.entity.Module;
import com.resources.pagination.admin.DefaultAdminPagination;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class ModuleFacade extends AbstractFacade {

    public ModuleFacade() {
        super(Module.class);
    }

    public List<Module> findAllModuleByLevel(int parentId) {
        Session session = null;
        List<Module> list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Module.class, "m");
                cr.add(Restrictions.eq("m.module.id", parentId));
                cr.add(Restrictions.eq("isDeleted", false));
                cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                list = cr.list();
            }
        } catch (Exception e) {
            Logger.getLogger(entityClass.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Criteria cr = session.createCriteria(Module.class);
                cr.add(Restrictions.and(Restrictions.eq("isShow", true)));
                cr.add(Restrictions.and(Restrictions.eq("isDeleted", false)));
                pagination.setDisplayList(cr.list());
                trans.commit();
            }
        } catch (Exception e) {
            try {
                if (trans != null) {
                    trans.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }
}
