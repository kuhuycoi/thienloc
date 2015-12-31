package com.resources.facade;

import com.resources.entity.Admin;
import com.resources.entity.Agency;
import com.resources.entity.Module;
import com.resources.pagination.admin.AgencyPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class AgencyFacade extends AbstractFacade{


    public AgencyFacade() {
        super(Agency.class);
    }
    
    public void pageData(AgencyPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Agency.class, "a");
                cr.createAlias("customerId", "customerId");
                cr.createAlias("rankAgencyID", "rankAgencyID");
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
                        .add(Projections.property("customerId"), "customerId")
                        .add(Projections.property("rankAgencyID"), "rankAgencyID")
                        .add(Projections.property("dateCreated"), "dateCreated")
                        .add(Projections.property("dateEnd"), "dateEnd"))
                        .setResultTransformer(Transformers.aliasToBean(Agency.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Agency.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }
    
    
    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
