package com.resources.facade;

import com.resources.entity.Module;
import com.resources.entity.ModuleInRole;
import com.resources.entity.RoleAdmin;
import com.resources.pagination.admin.AdminPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class RoleAdminFacade extends AbstractFacade {

    public RoleAdminFacade() {
        super(RoleAdmin.class);
    }

    @Override
    public List<RoleAdmin> findAll() {
        Session session = null;
        List<RoleAdmin> list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {

                list = session.createCriteria(RoleAdmin.class).add(Restrictions.eq("isActive", true)).add(Restrictions.eq("isDeleted", false)).list();
            }
        } catch (Exception e) {
            Logger.getLogger(RoleAdmin.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }
    
    public void pageData(AdminPagination adminPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(RoleAdmin.class, "r");
                List<String> listKeywords = adminPagination.getKeywords();
                cr.add(Restrictions.eq("isDeleted", false));
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(adminPagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + adminPagination.getSearchString() + "%'"));
                }
                cr.add(disj);

                cr.setProjection(Projections.rowCount());
                adminPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("isActive"), "isActive"))
                        .setResultTransformer(Transformers.aliasToBean(RoleAdmin.class));
                cr.setFirstResult(adminPagination.getFirstResult());
                cr.setMaxResults(adminPagination.getDisplayPerPage());
                cr.addOrder(adminPagination.isAsc() ? Order.asc(adminPagination.getOrderColmn()) : Order.desc(adminPagination.getOrderColmn()));
                adminPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }
    
    @Override
    public RoleAdmin find(int id) {
        Session session = null;
        RoleAdmin obj = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            obj = (RoleAdmin) session.get(RoleAdmin.class, id);
            Hibernate.initialize(obj.getModuleInRoles());
            for(ModuleInRole m:obj.getModuleInRoles()){
                Hibernate.initialize(m);
                Hibernate.initialize(m.getModuleID());
            }
            System.out.println(obj.getModuleInRoles().size());
        } catch (Exception e) {
            Logger.getLogger(RoleAdmin.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return obj;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
