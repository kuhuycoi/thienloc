package com.resources.facade;

import com.resources.bean.SystemTrianBean;
import com.resources.bean.TrianTree;
import com.resources.entity.Module;
import com.resources.entity.SystemTrian;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.GratefulPagination;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class SystemTrianFacade extends AbstractFacade {
    
    public SystemTrianFacade() {
        super(SystemTrian.class);
    }
    
    public Boolean checkJoinedTrian(int cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(SystemTrian.class);
            cr.createAlias("customerID", "customerID");
            cr.add(Restrictions.eq("customerID.id", cusId));
            
            cr.setProjection(Projections.rowCount());
            if (((Long) cr.uniqueResult()).intValue() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return true;
    }
    
    public List<TrianTree> getTreeTrian(Integer firstPos) {
        Session session = null;
        List list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session == null) {
                return null;
            }
            Query q = session.createSQLQuery("Tree_Trian :firstPos").addEntity(TrianTree.class);
            q.setParameter("firstPos", firstPos);
            list = q.list();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }
    
    public SystemTrian findByCustomerId(Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session == null) {
                return null;
            }
            Criteria cr = session.createCriteria(SystemTrian.class);
            cr.add(Restrictions.eq("customerID.id", cusId));
            return (SystemTrian) cr.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }
    
    public void pageData(GratefulPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(SystemTrian.class, "s");
                cr.createAlias("s.customerID", "customerID");
                cr.add(Restrictions.and(Restrictions.eq("customerID.isDelete", false), Restrictions.eq("customerID.isActive", true), Restrictions.eq("customerID.isDeposited", true)));
//                cr.add(Restrictions.gt("customerID.id", 167345));
                if (pagination.getAgencyId() != null) {
                    cr.add(Restrictions.eq("customerID.provincialAgencies.id", pagination.getAgencyId()));
                }
                
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
                        .add(Projections.property("customerID.userName"), "customerUserName")
                        .add(Projections.property("pos"), "pos")
                        .add(Projections.property("parentPos"), "parentPos")
                        .add(Projections.property("levetree"), "levetree")
                        .add(Projections.property("dateCreated"), "dateCreated")
                        .add(Projections.property("totalChildren"), "totalChildren")
                        .add(Projections.property("levelRank"), "levelRank"))
                        .setResultTransformer(Transformers.aliasToBean(SystemTrianBean.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
                
                Query q = session.createSQLQuery("GetTotalAwardGrateful");
                pagination.setTotalAward((BigDecimal) q.uniqueResult());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }
    
    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
