package com.resources.facade;

import com.resources.bean.ProvincialAgency;
import com.resources.entity.ProvincialAgencies;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.ProvincialAgencyPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class ProvincialAgenciesFacade extends AbstractFacade {

    public ProvincialAgenciesFacade() {
        super(ProvincialAgencies.class);
    }

    public List<ProvincialAgencies> findAllAvailableProvincialAgencies() {
        List<ProvincialAgencies> list = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(ProvincialAgencies.class);
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("name"), "name"))
                        .setResultTransformer(Transformers.aliasToBean(ProvincialAgencies.class));
                cr.add(Restrictions.and(Restrictions.eq("isDeleted", false), Restrictions.eq("isShow", true)));
                cr.addOrder(Order.asc("id"));
                list = cr.list();
            }
        } catch (Exception e) {
            Logger.getLogger(ProvincialAgencies.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;

    }

    public void pageData(ProvincialAgencyPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(ProvincialAgencies.class);
                cr.add(Restrictions.eq("isDeleted", false));
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
                        .add(Projections.property("email"), "email")
                        .add(Projections.property("mobile"), "mobile")
                        .add(Projections.property("fax"), "fax")
                        .add(Projections.property("dateCreated"), "dateCreated")
                        .add(Projections.property("isShow"), "isShow"))
                        .setResultTransformer(Transformers.aliasToBean(ProvincialAgency.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(ProvincialAgencies.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public Integer create(ProvincialAgencies entity) throws Exception {
        Transaction trans = null;
        Session session = null;
        Integer id = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();

            if (entity == null) {
                throw new NullPointerException();
            }

            entity.setName(StringUtils.escapeHtmlEntity(entity.getName()));
            entity.setAddress(StringUtils.escapeHtmlEntity(entity.getAddress()));
            entity.setMobile(StringUtils.escapeHtmlEntity(entity.getMobile()));
            entity.setFax(StringUtils.escapeHtmlEntity(entity.getFax()));

            id = (Integer) session.save(entity);
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
        return id;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
    }
}
