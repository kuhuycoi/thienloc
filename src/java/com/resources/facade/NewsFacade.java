package com.resources.facade;

import com.resources.entity.News;
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

public class NewsFacade extends AbstractFacade {

    public NewsFacade() {
        super(News.class);
    }

    public void pageData(com.resources.pagination.index.NewsPagination newsPagination) {
        Session sesion = null;
        try {
            sesion = HibernateConfiguration.getInstance().openSession();
            Criteria cr = sesion.createCriteria(News.class);
            cr.createAlias("caId", "caId");
            cr.add(Restrictions.eq("caId.id", newsPagination.getNewsCategory()));
            cr.add(Restrictions.eq("isDelete", false));
            cr.add(Restrictions.eq("isShow", true));
            cr.setProjection(Projections.rowCount());
            newsPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            cr.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name")
                    .add(Projections.property("titleImg"), "titleImg")
                    .add(Projections.property("shortDescription"), "shortDescription")
                    .add(Projections.property("content"), "content")
                    .add(Projections.property("createdDate"), "createdDate"))
                    .setResultTransformer(Transformers.aliasToBean(News.class));
            cr.setFirstResult(newsPagination.getFirstResult());
            cr.setMaxResults(newsPagination.getDisplayPerPage());
            cr.addOrder(newsPagination.isAsc() ? Order.asc(newsPagination.getOrderColmn()) : Order.desc(newsPagination.getOrderColmn()));
            cr.addOrder(Order.desc("createdDate"));
            newsPagination.setDisplayList(cr.list());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(sesion);
        }
    }

    public void pageData(com.resources.pagination.admin.NewsPagination newsPagination) {
        Session sesion = null;
        try {
            sesion = HibernateConfiguration.getInstance().openSession();
            Criteria cr = sesion.createCriteria(News.class);
            cr.createAlias("caId", "caId");
            cr.createAlias("createdAdm", "createdAdm");
            cr.add(Restrictions.eq("isDelete", false));
            cr.setProjection(Projections.rowCount());
            newsPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            cr.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name")
                    .add(Projections.property("createdDate"), "createdDate")
                    .add(Projections.property("isShow"), "isShow")
                    .add(Projections.property("caId"), "caId")
                    .add(Projections.property("createdAdm"), "createdAdm"))
                    .setResultTransformer(Transformers.aliasToBean(News.class));
            cr.setFirstResult(newsPagination.getFirstResult());
            cr.setMaxResults(newsPagination.getDisplayPerPage());
            cr.addOrder(newsPagination.isAsc() ? Order.asc(newsPagination.getOrderColmn()) : Order.desc(newsPagination.getOrderColmn()));
            newsPagination.setDisplayList(cr.list());
            News news;
            for (Object obj : newsPagination.getDisplayList()) {
                news = (News) obj;
                Hibernate.initialize(news.getCreatedAdm());
                Hibernate.initialize(news.getCaId());
            }
        } catch (Exception e) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(sesion);
        }
    }

    public News find(Integer id) {
        Session session = null;
        News news = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            news = (News) session.get(News.class, id);
            Hibernate.initialize(news.getCaId());
        } catch (Exception e) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return news;
    }

    public List<News> getListPost(Integer caId, Integer firstResult, Integer totalResult) {
        Session session = null;
        List list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(News.class);
            cr.add(Restrictions.eq("caId.id", caId));
            cr.add(Restrictions.eq("isDelete", false));
            cr.add(Restrictions.eq("isShow", true));
            cr.setFirstResult(firstResult);
            if (totalResult != null) {
                cr.setMaxResults(totalResult);
            }
            cr.addOrder(Order.asc("orderNumber"));
            cr.addOrder(Order.desc("createdDate"));
            list = cr.list();
        } catch (Exception e) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public News findByName(Integer caId,String name) {
        Session session = null;
        News result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(News.class);
            cr.createAlias("caId", "caId");
            cr.add(Restrictions.eq("isDelete", false));
            cr.add(Restrictions.eq("isShow", true));
            cr.add(Restrictions.eq("seoPermalink", name));
            cr.add(Restrictions.eq("caId.id", caId));
            result = (News) cr.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, e);
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
