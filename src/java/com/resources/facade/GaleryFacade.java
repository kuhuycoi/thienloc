package com.resources.facade;

import com.resources.entity.Galery;
import com.resources.entity.Images;
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

public class GaleryFacade extends AbstractFacade {

    public GaleryFacade() {
        super(Galery.class);
    }

    public void pageData(com.resources.pagination.admin.GaleryPagination pagination) {
        Session sesion = null;
        try {
            sesion = HibernateConfiguration.getInstance().openSession();
            Criteria cr = sesion.createCriteria(Galery.class);
            cr.createAlias("createdAdm", "createdAdm");
            cr.add(Restrictions.eq("isDelete", false));
            List<String> listKeywords = pagination.getKeywords();
            Disjunction disj = Restrictions.disjunction();
            for (String k : listKeywords) {
                if (StringUtils.isEmpty(pagination.getSearchString())) {
                    break;
                }
                disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + pagination.getSearchString() + "%'"));
            }
            cr.add(disj);
            cr.setProjection(Projections.rowCount());
            pagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            cr.setProjection(Projections.projectionList()
                    .add(Projections.property("id"), "id")
                    .add(Projections.property("name"), "name")
                    .add(Projections.property("createdDate"), "createdDate")
                    .add(Projections.property("isShow"), "isShow")
                    .add(Projections.property("shortDescription"), "shortDescription")
                    .add(Projections.property("createdAdm"), "createdAdm")
                    .add(Projections.property("orderNumber"), "orderNumber")
                    .add(Projections.property("titleImg"), "titleImg"))
                    .setResultTransformer(Transformers.aliasToBean(Galery.class));
            cr.setFirstResult(pagination.getFirstResult());
            cr.setMaxResults(pagination.getDisplayPerPage());
            cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
            pagination.setDisplayList(cr.list());
        } catch (Exception e) {
            Logger.getLogger(Galery.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(sesion);
        }
    }

    @Override
    public List<Galery> findAll() {
        Session session = null;
        List<Galery> list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                list = session.createCriteria(Galery.class).add(Restrictions.eq("isDelete", false)).list();
            }
        } catch (Exception e) {
            Logger.getLogger(Galery.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public Galery findEager(int id) {
        Session session = null;
        Galery obj = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            obj = (Galery) session.get(Galery.class, id);
            Hibernate.isInitialized(obj.getImagesList());
            for (Images img : obj.getImagesList()) {
                Hibernate.isInitialized(img);
            }
        } catch (Exception e) {
            Logger.getLogger(entityClass.getName()).log(Level.SEVERE, null, e);
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
