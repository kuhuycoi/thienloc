package com.resources.facade;

import com.resources.bean.Logs;
import com.resources.entity.AdminLogs;
import com.resources.entity.Module;
import com.resources.pagination.admin.AdminLogsPagination;
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

public class AdminLogsFacade extends AbstractFacade {

    public AdminLogsFacade() {
        super(AdminLogs.class);
    }

    public void pageData(AdminLogsPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(AdminLogs.class, "a");
                cr.createAlias("a.admin", "admin");
                cr.createAlias("a.module", "module");
                cr.createAlias("a.action", "action");
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
                        .add(Projections.property("a.id"), "id")
                        .add(Projections.property("admin.userName"), "adminName")
                        .add(Projections.property("module.name"), "moduleName")
                        .add(Projections.property("action.actionName"), "actionName")
                        .add(Projections.property("a.content"), "content")
                        .add(Projections.property("a.importedDate"), "importedDate"))
                        .setResultTransformer(Transformers.aliasToBean(Logs.class));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
