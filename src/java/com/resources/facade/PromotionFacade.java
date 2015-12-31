package com.resources.facade;

import com.resources.entity.Promotion;
import com.resources.pagination.admin.DefaultAdminPagination;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class PromotionFacade extends AbstractFacade {

    public PromotionFacade() {
        super(Promotion.class);
    }

    public Integer checkHavePromotion(Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(Promotion.class);
            cr.add(Restrictions.eq("customerId.id", cusId));
            cr.setProjection(Projections.rowCount());
            return ((Long) cr.uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Promotion.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return 0;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
