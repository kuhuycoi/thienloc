package com.resources.facade;

import com.resources.bean.ExcelFile;
import com.resources.bean.HistoryCustomerRank;
import com.resources.entity.Customer;
import com.resources.entity.CustomerRankCustomer;
import com.resources.entity.Module;
import com.resources.entity.RankCustomes;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.utils.LeoConstants;
import com.resources.utils.LogUtils;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;

public class CustomerRankCustomerFacade extends AbstractFacade {

    public CustomerRankCustomerFacade() {
        super(CustomerRankCustomer.class);
    }

    public void pageData(HistoryPagination historyPagination, Integer roleId, Integer agencyId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(CustomerRankCustomer.class, "c");
                cr.createAlias("c.rankCustomes", "rank", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("c.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.provincialAgencies", "pA", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.customerByParentId", "customerByParentId", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.customerByCustomerId", "customerByCustomerId", JoinType.LEFT_OUTER_JOIN);
                cr.add(Restrictions.and(Restrictions.eq("c.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));
                if (roleId == 2 && agencyId != 0) {
                    cr.add(Restrictions.eq("pA.id", agencyId));
                } else if (historyPagination.getAgencyId() != null) {
                    cr.add(Restrictions.eq("pA.id", historyPagination.getAgencyId()));
                }

                if (historyPagination.getStartDate() != null) {
                    cr.add(Restrictions.sqlRestriction("this_.DateCreated>=?", new SimpleDateFormat("yyyy-MM-dd").format(historyPagination.getStartDate()), StringType.INSTANCE));
                }

                if (historyPagination.getEndDate() != null) {
                    cr.add(Restrictions.sqlRestriction("dateadd(day,-1,this_.DateCreated)<=?", new SimpleDateFormat("yyyy-MM-dd").format(historyPagination.getEndDate()), StringType.INSTANCE));
                }

                List<String> listKeywords = historyPagination.getKeywords();
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(historyPagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + historyPagination.getSearchString() + "%'"));
                }
                cr.add(disj);

                cr.setProjection(Projections.rowCount());
                historyPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("cus.userName"), "userName")
                        .add(Projections.property("cus.lastName"), "lastName")
                        .add(Projections.property("cus.firstName"), "firstName")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("c.dateCreated"), "dateCreated")
                        .add(Projections.property("pA.name"), "provincialAgencyName")
                        .add(Projections.property("rank.name"), "rankName")
                        .add(Projections.property("customerByParentId.userName"), "parentName")
                        .add(Projections.property("customerByCustomerId.userName"), "customerName"))
                        .setResultTransformer(Transformers.aliasToBean(HistoryCustomerRank.class));
                cr.setFirstResult(historyPagination.getFirstResult());
                cr.setMaxResults(historyPagination.getDisplayPerPage());
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));
                historyPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageData(com.resources.pagination.index.HistoryPagination historyPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(CustomerRankCustomer.class, "c");
                cr.createAlias("c.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("c.provincialAgencies", "pA", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("c.rankCustomes", "rank", JoinType.LEFT_OUTER_JOIN);
                cr.add(Restrictions.and(Restrictions.eq("c.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));
                cr.add(Restrictions.eq("cus.id", cusId));
                cr.setProjection(Projections.rowCount());
                historyPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("rank.name"), "rankName")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("pA.name"), "provincialAgencyName")
                        .add(Projections.property("dateCreated"), "dateCreated"))
                        .setResultTransformer(Transformers.aliasToBean(HistoryCustomerRank.class));
                cr.setFirstResult(historyPagination.getFirstResult());
                cr.setMaxResults(historyPagination.getDisplayPerPage());
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));
                historyPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public int depositPv(String userName, Integer rankCustomerId, Integer multipleGrateful, int adminId) throws Exception {
        Transaction trans = null;
        Session session = null;
        Integer result;
        try {
            if (userName == null || rankCustomerId == null) {
                return 0;
            }
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            CustomerFacade cFacade = new CustomerFacade();
            Customer c = cFacade.findCustomerByUsername(userName);
            Query q = session.createSQLQuery("DepositPV :userName,:rankCustomerId").setParameter("userName", userName).setParameter("rankCustomerId", rankCustomerId);

            result = (Integer) q.uniqueResult();      

            if (result == 2 && rankCustomerId != 2) {
                q = session.getNamedQuery("TotalParent");
                q.setParameter("listCustomerId", c.getListCustomerId())
                        .setParameter("cusId", c.getId())
                        .setParameter("rankId", rankCustomerId);
                q.executeUpdate();
            }
            RankCustomes rc = (RankCustomes) new RankCustomersFacade().find(rankCustomerId);
            LogUtils.logs(adminId, LeoConstants.ActionConstants.ACTION_NAPTIEN, 4, "Nạp PV tại người dùng: '" + userName + "', Gói: '" + rc.getName() + "'");
            trans.commit();
            return result;
        } catch (Exception e) {
            try {
                if (trans != null) {
                    trans.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            e.printStackTrace();
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public BigDecimal getNewestDeposit() {
        Session session = null;
        BigDecimal result = BigDecimal.ZERO;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createQuery("select pricePv from CustomerRankCustomer where isDeleted=0 order by dateCreated DESC");
                q.setMaxResults(1);
                result = (BigDecimal) q.uniqueResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getNewestAward() {
        Session session = null;
        BigDecimal result = BigDecimal.ZERO;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createQuery("select pricePv from HistoryAwards where isDeleted=0 order by dateCreated DESC");
                q.setMaxResults(1);
                result = (BigDecimal) q.uniqueResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List getTop5DepositPVInMonth() {
        Calendar c = Calendar.getInstance();
        return getTop5DepositPV(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
    }

    public List getTop5DepositPVInYear() {
        Calendar c = Calendar.getInstance();
        return getTop5DepositPV(null, c.get(Calendar.YEAR));
    }

    public List getTop5DepositPV(Integer month, Integer year) {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("GetTop5DepositPV :month,:year")
                        .addScalar("Name", StringType.INSTANCE)
                        .addScalar("UserName", StringType.INSTANCE)
                        .addScalar("PricePv", BigDecimalType.INSTANCE);
                q.setParameter("month", month);
                q.setParameter("year", year);
                result = q.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public void pageDataWaitActiveRank(HistoryPagination historyPagination, Integer agencyId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Customer.class, "c");
                cr.add(Restrictions.eq("c.isDelete", false));
                cr.add(Restrictions.eq("c.isActive", true));
                cr.add(Restrictions.isNotNull("c.rankCustomerId"));
                if (agencyId != 0) {
                    cr.add(Restrictions.eq("c.provincialAgencies.id", agencyId));
                }
                if (historyPagination.getAccepted() != null) {
                    cr.add(Restrictions.eq("c.isAccountantApproved", historyPagination.getAccepted()));
                }
                List<String> listKeywords = historyPagination.getKeywords();
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(historyPagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + historyPagination.getSearchString() + "%'"));
                }
                cr.add(disj);

                cr.setProjection(Projections.rowCount());
                historyPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("c.id"), "id")
                        .add(Projections.property("c.userName"), "userName")
                        .add(Projections.property("c.lastName"), "lastName")
                        .add(Projections.property("c.rankCustomerId"), "rankCustomerId")
                        .add(Projections.property("c.provincialAgencies"), "provincialAgencies")
                        .add(Projections.property("c.isAccountantApproved"), "isAccountantApproved")
                        .add(Projections.property("c.isAdmin"), "isAdmin"))
                        .setResultTransformer(Transformers.aliasToBean(Customer.class));
                cr.setFirstResult(historyPagination.getFirstResult());
                cr.setMaxResults(historyPagination.getDisplayPerPage());
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));
                historyPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void setExportCustomerRankCustomerFile(ExcelFile file, HistoryPagination historyPagination, Integer roleId, Integer agencyId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {

                Criteria cr = session.createCriteria(CustomerRankCustomer.class, "c");
                cr.createAlias("c.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("c.rankCustomes", "rankCustomes", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.provincialAgencies", "pA", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.customerByParentId", "customerByParentId", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("cus.customerByCustomerId", "customerByCustomerId", JoinType.LEFT_OUTER_JOIN);
                cr.add(Restrictions.and(Restrictions.eq("c.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));
                if (roleId == 2 && agencyId != 0) {
                    cr.add(Restrictions.eq("pA.id", agencyId));
                } else if (historyPagination.getAgencyId() != null) {
                    cr.add(Restrictions.eq("pA.id", historyPagination.getAgencyId()));
                }

                if (historyPagination.getStartDate() != null) {
                    cr.add(Restrictions.sqlRestriction("this_.DateCreated>=?", new SimpleDateFormat("yyyy-MM-dd").format(historyPagination.getStartDate()), StringType.INSTANCE));
                }

                if (historyPagination.getEndDate() != null) {
                    cr.add(Restrictions.sqlRestriction("dateadd(day,-1,this_.DateCreated)<=?", new SimpleDateFormat("yyyy-MM-dd").format(historyPagination.getEndDate()), StringType.INSTANCE));
                }

                List<String> listKeywords = historyPagination.getKeywords();
                Disjunction disj = Restrictions.disjunction();
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(historyPagination.getSearchString())) {
                        break;
                    }
                    disj.add(Restrictions.sqlRestriction("CAST(" + k + " AS VARCHAR) like '%" + historyPagination.getSearchString() + "%'"));
                }
                cr.add(disj);

                cr.setProjection(Projections.rowCount());
                historyPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"), "id")
                        .add(Projections.property("cus.lastName"), "lastName")
                        .add(Projections.property("cus.userName"), "userName")
                        .add(Projections.property("customerByCustomerId.userName"), "customerName")
                        .add(Projections.property("customerByParentId.userName"), "parentName")
                        .add(Projections.property("pA.name"), "provincialAgencyName")
                        .add(Projections.property("rankCustomes.name"), "rankCustomerName")
                        .add(Projections.property("c.dateCreated"), "dateCreated"));
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));
                List<String> header = new ArrayList();
                header.add("ID");
                header.add("Họ và tên");
                header.add("Tên đăng nhập");
                header.add("Người giới thiệu");
                header.add("Người chỉ định");
                header.add("Đại lý");
                header.add("Gói đầu vào");
                header.add("Ngày tham gia");
                file.setTitles(header);
                List rs = cr.list();
                file.setContents(rs);
                Calendar c = Calendar.getInstance();
                file.setFileName("Dach sach nap PV tinh den ngay " + c.get(Calendar.DAY_OF_MONTH) + " thang " + c.get(Calendar.MONTH));
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
    }
}
