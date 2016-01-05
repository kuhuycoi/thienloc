package com.resources.facade;

import com.resources.entity.Module;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
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
                for (Module m : list) {
                    Hibernate.initialize(m.getModules());
                }
            }
        } catch (Exception e) {
            Logger.getLogger(entityClass.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public Integer create(Map module) throws Exception {
        Transaction trans = null;
        Session session = null;
        Integer result = null;
        try {
            Module obj = new Module();
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            obj.setName(module.get("name") == null ? null : StringUtils.escapeHtmlEntity(module.get("name").toString()));
            obj.setController(module.get("controller") == null ? null : StringUtils.escapeHtmlEntity(module.get("controller").toString()));
            obj.setModule(new Module(Integer.valueOf(module.get("module").toString())));
            obj.setIcon(module.get("icon") == null ? null : StringUtils.escapeHtmlEntity(module.get("icon").toString()));
            obj.setCssClass(module.get("cssClass") == null ? null : StringUtils.escapeHtmlEntity(module.get("cssClass").toString()));
            obj.setIsShow(Boolean.valueOf(module.get("isShow").toString()));
            result = (Integer) session.save(obj);
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

    public void edit(Map module) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            Module obj = (Module) find(Integer.valueOf(module.get("id").toString()));
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            obj.setName(module.get("name") == null ? null : StringUtils.escapeHtmlEntity(module.get("name").toString()));
            obj.setController(module.get("controller") == null ? null : StringUtils.escapeHtmlEntity(module.get("controller").toString()));
            obj.setIcon(module.get("icon") == null ? null : StringUtils.escapeHtmlEntity(module.get("icon").toString()));
            obj.setCssClass(module.get("cssClass") == null ? null : StringUtils.escapeHtmlEntity(module.get("cssClass").toString()));
            session.update(obj);
            trans.commit();
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
