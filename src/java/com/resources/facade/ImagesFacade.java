package com.resources.facade;

import com.resources.entity.Galery;
import com.resources.entity.Images;
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

public class ImagesFacade extends AbstractFacade {

    public ImagesFacade() {
        super(Images.class);
    }

    public void pageData(com.resources.pagination.admin.GaleryPagination pagination) {
        Session sesion = null;
        try {
            sesion = HibernateConfiguration.getInstance().openSession();
            Criteria cr = sesion.createCriteria(Images.class,"img");
            cr.createAlias("createdAdm", "createdAdm");
            cr.createAlias("galeryId", "galeryId");
            cr.add(Restrictions.eq("img.isDelete", false));
            cr.add(Restrictions.eq("galeryId.isDelete", false));
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
                    .add(Projections.property("img.id"), "id")
                    .add(Projections.property("img.name"), "name")
                    .add(Projections.property("img.createdDate"), "createdDate")
                    .add(Projections.property("img.isShow"), "isShow")
                    .add(Projections.property("galeryId"), "galeryId")
                    .add(Projections.property("createdAdm"), "createdAdm")
                    .add(Projections.property("img.orderNumber"), "orderNumber")
                    .add(Projections.property("img.path"), "path"))
                    .setResultTransformer(Transformers.aliasToBean(Images.class));
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
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
