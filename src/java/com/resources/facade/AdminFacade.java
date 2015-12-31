package com.resources.facade;

import com.resources.entity.Admin;
import com.resources.entity.Module;
import com.resources.entity.ModuleInRole;
import com.resources.entity.ProvincialAgencies;
import com.resources.entity.RoleAdmin;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.AdminPagination;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.utils.StringUtils;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class AdminFacade extends AbstractFacade {

    public AdminFacade() {
        super(Admin.class);
    }

    public Admin login(Admin admin) throws Exception {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Criteria cr = session.createCriteria(Admin.class);
            cr.add(Restrictions.and(Restrictions.eq("userName", admin.getUserName()), Restrictions.eq("password", admin.getPassword())));
            admin = (Admin) cr.uniqueResult();
            if (admin != null) {
                Hibernate.initialize(admin.getRoleAdmID());
                Hibernate.initialize(admin.getRoleAdmID().getModuleInRoles());
                for (ModuleInRole mr : (admin.getRoleAdmID().getModuleInRoles())) {
                    Hibernate.initialize(mr.getModuleID());
                    Hibernate.initialize(mr.getModuleID().getModules());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return admin;
    }

    public void pageData(AdminPagination adminPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Admin.class, "a");
                cr.add(Restrictions.eq("a.isDelete", false));
                List<String> listKeywords = adminPagination.getKeywords();
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
                        .add(Projections.property("userName"), "userName")
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("createdOnUtc"), "createdOnUtc")
                        .add(Projections.property("provincialAgencyID"), "provincialAgencyID")
                        .add(Projections.property("roleAdmID"), "roleAdmID")
                        .add(Projections.property("isActive"), "isActive"))
                        .setResultTransformer(Transformers.aliasToBean(Admin.class));
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

    public int create(Map admin) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            if (admin == null) {
                return 1;
            }
            if (StringUtils.isEmpty(admin.get("userName").toString())
                    || StringUtils.isEmpty(admin.get("name").toString())
                    || StringUtils.isEmpty(admin.get("password").toString())) {
                return 2;
            }
            Admin a = new Admin();
            a.setName(StringUtils.escapeHtmlEntity(admin.get("name").toString()));
            a.setPassword(CustomFunction.md5(admin.get("password").toString()));
            a.setUserName(StringUtils.escapeHtmlEntity(admin.get("userName").toString()));
            a.setMobile(StringUtils.escapeHtmlEntity(admin.get("mobile") == null ? null : admin.get("mobile").toString()));
            a.setEmail(StringUtils.escapeHtmlEntity(admin.get("email") == null ? null : admin.get("email").toString()));
            a.setAddress(StringUtils.escapeHtmlEntity(admin.get("address") == null ? null : admin.get("address").toString()));
            a.setTaxCode(StringUtils.escapeHtmlEntity(admin.get("taxCode") == null ? null : admin.get("taxCode").toString()));
            a.setBillingAddress(StringUtils.escapeHtmlEntity(admin.get("billingAddress") == null ? null : admin.get("billingAddress").toString()));
            a.setRoleAdmID(new RoleAdmin(Integer.parseInt(admin.get("roleAdmID").toString())));
            a.setProvincialAgencyID(new ProvincialAgencies(Integer.parseInt(admin.get("provincialAgencyID").toString())));
            a.setIsActive(true);
            if (findAdminByUsername(a.getUserName()) != null) {
                return 3;
            }
            session.save(a);
            trans.commit();
            return 4;
        } catch (Exception e) {
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
    }

    public int edit(Map admin, Integer role) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            if (admin == null) {
                return 1;
            }
            if (StringUtils.isEmpty(admin.get("name").toString())) {
                return 2;
            }
            Admin a = (Admin) find(Integer.parseInt(admin.get("id").toString()));
            a.setName(StringUtils.escapeHtmlEntity(admin.get("name").toString()));
            if (admin.get("password") != null) {
                a.setPassword(CustomFunction.md5(admin.get("password").toString()));
            }
            a.setMobile(StringUtils.escapeHtmlEntity(admin.get("mobile") == null ? null : admin.get("mobile").toString()));
            a.setEmail(StringUtils.escapeHtmlEntity(admin.get("email") == null ? null : admin.get("email").toString()));
            a.setAddress(StringUtils.escapeHtmlEntity(admin.get("address") == null ? null : admin.get("address").toString()));
            a.setTaxCode(StringUtils.escapeHtmlEntity(admin.get("taxCode") == null ? null : admin.get("taxCode").toString()));
            if (role == 1) {
                a.setRoleAdmID(new RoleAdmin(Integer.parseInt(admin.get("roleAdmID").toString())));
                a.setProvincialAgencyID(new ProvincialAgencies(Integer.parseInt(admin.get("provincialAgencyID").toString())));
            }
            a.setBillingAddress(StringUtils.escapeHtmlEntity(admin.get("billingAddress") == null ? null : admin.get("billingAddress").toString()));
            session.update(a);
            trans.commit();
            return 3;
        } catch (Exception e) {
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
    }

    public void delete(int id) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Query query = session.createSQLQuery("update admin set isDelete=:isDelete where id=:id").setParameter("isDelete", true).setParameter("id", id);
                query.executeUpdate();
                trans.commit();
            }
        } catch (Exception e) {
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
    }

    public void block(Integer id, Boolean status) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                trans = session.beginTransaction();
                Query query = session.createSQLQuery("update admin set isActive=:isActive where id=:id").setParameter("isActive", status).setParameter("id", id);
                query.executeUpdate();
                trans.commit();
            }
        } catch (Exception e) {
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
    }

    public Admin findAdminByUsername(String userName) throws Exception {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Admin.class);
                cr.add(Restrictions.eq("userName", userName));
                cr.add(Restrictions.eq("isDelete", false));
                return (Admin) cr.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return null;
    }
    
    public Integer getAdminRoleByAdminId(int admId){
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Admin.class);
                cr.add(Restrictions.eq("id", admId));
                cr.setProjection(Projections.projectionList().add(Projections.property("roleAdmID.id")));
                return (Integer)cr.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return null;        
    } 
    
    public Integer getAdminAgencyByAdminId(int admId){
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Admin.class);
                cr.add(Restrictions.eq("id", admId));
                cr.setProjection(Projections.projectionList().add(Projections.property("provincialAgencyID.id")));
                return (Integer)cr.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return null;        
    }

    public Admin findAdminById(Integer id) {
        Session session = null;
        Admin admin = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Admin.class);
                cr.add(Restrictions.eq("id", id));
                cr.add(Restrictions.eq("isDelete", false));
                admin = (Admin) cr.uniqueResult();
                if (admin != null) {
                    Hibernate.initialize(admin.getRoleAdmID());
                    Hibernate.initialize(admin.getRoleAdmID().getModuleInRoles());
                    for (ModuleInRole mr : (admin.getRoleAdmID().getModuleInRoles())) {
                        Hibernate.initialize(mr.getModuleID());
                        Hibernate.initialize(mr.getModuleID().getModules());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return admin;
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
