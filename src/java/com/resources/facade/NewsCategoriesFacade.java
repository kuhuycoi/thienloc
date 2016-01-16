package com.resources.facade;

import com.resources.entity.Module;
import com.resources.entity.News;
import com.resources.entity.NewsCategories;
import com.resources.pagination.admin.DefaultAdminPagination;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class NewsCategoriesFacade extends AbstractFacade {

    public NewsCategoriesFacade() {
        super(NewsCategories.class);
    }

    public void pageData(com.resources.pagination.admin.NewsPagination newsPagination) {
        Session sesion = null;
        try {
            sesion = HibernateConfiguration.getInstance().openSession();
            Criteria cr = sesion.createCriteria(NewsCategories.class);
            cr.createAlias("createdAdm", "createdAdm");
            cr.add(Restrictions.eq("isDelete", false));
            cr.setProjection(Projections.rowCount());
            newsPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            cr.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name")
                    .add(Projections.property("createdDate"), "createdDate")
                    .add(Projections.property("isShow"), "isShow")
                    .add(Projections.property("isShowOnMenu"), "isShowOnMenu")
                    .add(Projections.property("level"), "level")
                    .add(Projections.property("createdAdm"), "createdAdm"))
                    .setResultTransformer(Transformers.aliasToBean(NewsCategories.class));
            cr.setFirstResult(newsPagination.getFirstResult());
            cr.setMaxResults(newsPagination.getDisplayPerPage());
            cr.addOrder(newsPagination.isAsc() ? Order.asc(newsPagination.getOrderColmn()) : Order.desc(newsPagination.getOrderColmn()));
            newsPagination.setDisplayList(cr.list());
            NewsCategories newCategory;
            for (Object obj : newsPagination.getDisplayList()) {
                newCategory = (NewsCategories) obj;
                Hibernate.initialize(newCategory.getCreatedAdm());
            }
        } catch (Exception e) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(sesion);
        }
    }

    public List<NewsCategories> getTree() {
        Session session = null;
        List list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session == null) {
                return null;
            }
            Criteria cr = session.createCriteria(NewsCategories.class);
            cr.add(Restrictions.and(Restrictions.eq("isDelete", false), Restrictions.eq("level", 1)));
            list = cr.list();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public List<NewsCategories> getTreeDropdown() {
        Session session = null;
        List list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session == null) {
                return null;
            }
            Criteria cr = session.createCriteria(NewsCategories.class);
            cr.add(Restrictions.and(Restrictions.eq("isDelete", false)));
            list = cr.list();
            System.out.println(list.size());
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
