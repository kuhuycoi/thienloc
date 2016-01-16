package com.resources.facade;

import com.resources.bean.ExcelFile;
import com.resources.bean.HistoryAward;
import com.resources.entity.CheckAwards;
import com.resources.entity.HistoryAwards;
import com.resources.entity.Module;
import com.resources.entity.Triandot2;
import com.resources.function.CustomFunction;
import com.resources.pagination.admin.DefaultAdminPagination;
import com.resources.pagination.admin.HistoryPagination;
import com.resources.pagination.admin.ReportPagination;
import com.resources.utils.ArrayUtils;
import com.resources.utils.StringUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

public class HistoryAwardFacade extends AbstractFacade {

    public HistoryAwardFacade() {
        super(HistoryAwards.class);
    }

    public void pageData(HistoryPagination historyPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "a");
                cr.createAlias("a.customer", "cus");

                cr.add(Restrictions.and(Restrictions.eq("a.isDeleted", false), Restrictions.eq("cus.isDelete", false), Restrictions.eq("cus.isActive", true)));
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
                        .add(Projections.property("name"), "name")
                        .add(Projections.property("cus.userName"), "userName")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("dateCreated"), "dateCreated"))
                        .setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
                cr.setFirstResult(historyPagination.getFirstResult());
                cr.setMaxResults(historyPagination.getDisplayPerPage());
                cr.addOrder(historyPagination.isAsc() ? Order.asc(historyPagination.getOrderColmn()) : Order.desc(historyPagination.getOrderColmn()));

                historyPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageData(ReportPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "h");
                cr.createAlias("h.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.and(Restrictions.eq("h.isDeleted", false), 
                        Restrictions.eq("cus.isDelete", false), 
                        Restrictions.eq("cus.isActive", true), 
                        Restrictions.eq("cus.isDeposited", true),
                        Restrictions.eq("cus.isLock", false)));

                if (pagination.getStartDate() != null) {
                    cr.add(Restrictions.sqlRestriction("DateCreated>=?", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getStartDate()), StringType.INSTANCE));
                }

                if (pagination.getEndDate() != null) {
                    cr.add(Restrictions.sqlRestriction("dateadd(day,-1,DateCreated)<=?", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getEndDate()), StringType.INSTANCE));
                }

                if (pagination.getAgencyId() != null) {
                    cr.add(Restrictions.eq("cus.provincialAgencies.id", pagination.getAgencyId()));
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

                String queryString = "select count(*) from (select h.customerId from HistoryAwards h join Customer c on h.CustomerId=c.id where h.isDeleted=0";

                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(pagination.getSearchString())) {
                        break;
                    }
                    queryString += " and CAST(" + k + " AS VARCHAR) like '%" + pagination.getSearchString() + "%' ";
                }
                if (pagination.getStartDate() != null) {
                    queryString += " and cast(DateCreated as date)>=:startDate";
                }

                if (pagination.getEndDate() != null) {
                    queryString += " and cast(DateCreated as date)<=:endDate";
                }

                if (pagination.getAgencyId() != null) {
                    queryString += " and c.ProvincialAgencyID=:agencyId";
                }

                queryString += " and c.IsDelete=0 and c.IsActive=1 and c.isLock=0 and c.isDeposited=1 group by h.customerId)z";
                Query q = session.createSQLQuery(queryString);

                if (pagination.getStartDate() != null) {
                    q.setParameter("startDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getStartDate()), StringType.INSTANCE);
                }

                if (pagination.getEndDate() != null) {
                    q.setParameter("endDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getEndDate()), StringType.INSTANCE);
                }
                if (pagination.getAgencyId() != null) {
                    q.setParameter("agencyId", pagination.getAgencyId());
                }
                pagination.setTotalResult((Integer) q.uniqueResult());

                queryString = "select sum(h.pricePv) from HistoryAwards h join Customer c on h.CustomerId=c.id where h.isDeleted=0";

                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(pagination.getSearchString())) {
                        break;
                    }
                    queryString += " and CAST(" + k + " AS VARCHAR) like '%" + pagination.getSearchString() + "%' ";
                }
                if (pagination.getStartDate() != null) {
                    queryString += " and cast(DateCreated as date)>=:startDate";
                }

                if (pagination.getEndDate() != null) {
                    queryString += " and cast(DateCreated as date)<=:endDate";
                }

                if (pagination.getAgencyId() != null) {
                    queryString += " and c.ProvincialAgencyID=:agencyId";
                }

                queryString += " and c.IsDelete=0 and c.IsActive=1 and c.isLock=0 and c.isDeposited=1";
                q = session.createSQLQuery(queryString);

                if (pagination.getStartDate() != null) {
                    q.setParameter("startDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getStartDate()), StringType.INSTANCE);
                }

                if (pagination.getEndDate() != null) {
                    q.setParameter("endDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getEndDate()), StringType.INSTANCE);
                }
                if (pagination.getAgencyId() != null) {
                    q.setParameter("agencyId", pagination.getAgencyId());
                }

                pagination.setTotalAward((BigDecimal) q.uniqueResult());

                cr.setProjection(Projections.projectionList()
                        .add(Projections.sum("pricePv"), "pricePv")
                        .add(Projections.groupProperty("cus.id"), "cusId")
                        .add(Projections.groupProperty("cus.userName"), "userName")
                        .add(Projections.groupProperty("cus.firstName"), "firstName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName")
                        .add(Projections.groupProperty("cus.lastName"), "lastName"));// các cột lấy ra
                cr.setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
                cr.setFirstResult(pagination.getFirstResult());//kết quả đầu
                cr.setMaxResults(pagination.getDisplayPerPage());// số kết quả lấy ra
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));// sắp xếp

                pagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void setExportComissionDistributorFile(ExcelFile file, ReportPagination pagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                List<String> listKeywords = pagination.getKeywords();
                String query = "select ha.customerId,c.lastName,c.userName,c.peoplesIdentity,c.email,c.mobile,c.address,c.taxCode,"
                        + "c.billingAddress, isnull(sum(ha.PricePv),0) total, isnull(sum(ha.PricePv),0)*90/100 total1 "
                        + "from HistoryAwards ha left join Customer c "
                        + "on ha.CustomerId=c.id where ha.IsDeleted=0 and c.IsDelete=0 and c.IsActive=1 "
                        + "and c.isLock=0 and c.isDeposited=1";
                for (String k : listKeywords) {
                    if (StringUtils.isEmpty(pagination.getSearchString())) {
                        break;
                    }
                    query += " and CAST(" + k + " AS VARCHAR) like '%" + pagination.getSearchString() + "%' ";
                }
                if (pagination.getStartDate() != null) {
                    query += " and cast(DateCreated as date)>=:startDate";
                }

                if (pagination.getEndDate() != null) {
                    query += " and cast(DateCreated as date)<=:endDate";
                }

                if (pagination.getAgencyId() != null) {
                    query += " and c.ProvincialAgencyID=:agencyId";
                }

                query += " group by ha.CustomerId,c.lastName,c.Username,c.peoplesIdentity,c.Email,c.mobile,c.Address,c.TaxCode,c.BillingAddress"
                        + " order by c.peoplesIdentity,c.lastName";

                Query q = session.createSQLQuery(query)
                        .addScalar("customerId", IntegerType.INSTANCE)
                        .addScalar("lastName", StringType.INSTANCE)
                        .addScalar("userName", StringType.INSTANCE)
                        .addScalar("peoplesIdentity", StringType.INSTANCE)
                        .addScalar("email", StringType.INSTANCE)
                        .addScalar("mobile", StringType.INSTANCE)
                        .addScalar("address", StringType.INSTANCE)
                        .addScalar("taxCode", StringType.INSTANCE)
                        .addScalar("billingAddress", StringType.INSTANCE)
                        .addScalar("total", BigDecimalType.INSTANCE)
                        .addScalar("total1", BigDecimalType.INSTANCE);
                if (pagination.getStartDate() != null) {
                    q.setParameter("startDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getStartDate()), StringType.INSTANCE);
                }

                if (pagination.getEndDate() != null) {
                    q.setParameter("endDate", new SimpleDateFormat("yyyy-mm-dd").format(pagination.getEndDate()), StringType.INSTANCE);
                }

                if (pagination.getAgencyId() != null) {
                    q.setParameter("agencyId", pagination.getAgencyId());
                }
                List<String> header = new ArrayList();
                header.add("ID");
                header.add("Họ và tên");
                header.add("Tên đăng nhập");
                header.add("CMND");
                header.add("Email");
                header.add("SĐT");
                header.add("Địa chỉ liên hệ");
                header.add("Số TK ngân hàng");
                header.add("Địa chỉ ngân hàng");
                header.add("Tổng hoa hồng trước thuế");
                header.add("Tổng hoa hồng sau thuế");
                file.setTitles(header);
                List rs = q.list();
                for (Object rows : rs) {
                    Object[] row = (Object[]) rows;
                    row[9] = CustomFunction.formatCurrency((BigDecimal) row[9]);
                    row[10] = CustomFunction.formatCurrency((BigDecimal) row[10]);
                }
                file.setContents(rs);
                file.setFileName("Thong ke hoa hong NPP " + pagination.getMonth().toString() + "-" + pagination.getYear().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void setExportTrianFile(ExcelFile file) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select t.trueVT,c.firstName,c.lastName,c.title,c.mobile,c.email,c.peoplesIdentity,"
                        + "c.billingAddress,c.taxCode,t.datetimecreated,t.dem,t.levelup,null as total from Triandot2 t "
                        + "left join Customer c on t.customerID=c.id where c.isDelete=0 and c.isActive=1 order by t.trueVT asc")
                        .addScalar("trueVT", IntegerType.INSTANCE)
                        .addScalar("firstName", StringType.INSTANCE)
                        .addScalar("lastName", StringType.INSTANCE)
                        .addScalar("title", StringType.INSTANCE)
                        .addScalar("mobile", StringType.INSTANCE)
                        .addScalar("email", StringType.INSTANCE)
                        .addScalar("peoplesIdentity", StringType.INSTANCE)
                        .addScalar("billingAddress", StringType.INSTANCE)
                        .addScalar("taxCode", StringType.INSTANCE)
                        .addScalar("datetimecreated", TimestampType.INSTANCE)
                        .addScalar("dem", IntegerType.INSTANCE)
                        .addScalar("levelup", IntegerType.INSTANCE)
                        .addScalar("total", BigDecimalType.INSTANCE);
                List<String> header = new ArrayList();
                header.add("ID");
                header.add("Họ");
                header.add("Tên");
                header.add("Bí danh");
                header.add("SĐT");
                header.add("Email");
                header.add("CMND");
                header.add("Địa chỉ ngân hàng");
                header.add("Số tài khoản");
                header.add("Thời gian tri ân");
                header.add("Tích lũy");
                header.add("Mức hoàn phí");
                header.add("Thành tiền");
                file.setTitles(header);
                List rs = q.list();
                Integer money = 0;
                for (Object rows : rs) {
                    Object[] row = (Object[]) rows;
                    row[9] = CustomFunction.formatTime((Date) row[9]);
                    switch ((Integer) row[11]) {
                        case 1: {
                            money = 20;
                            break;
                        }
                        case 2: {
                            money = 120;
                            break;
                        }
                        case 3: {
                            money = 320;
                            break;
                        }
                        case 4: {
                            money = 1000;
                            break;
                        }
                    }
                    row[12] = CustomFunction.formatCurrency(BigDecimal.valueOf(money));
                }
                file.setContents(rs);
                file.setFileName("Thong ke tri an");
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public int calcSalary() throws Exception {
        Session session = null;
        int result = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                result = (Integer) session.createSQLQuery("CalcSalary").uniqueResult();
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public List<HistoryAward> pageData(HistoryPagination pagination, int cusId, int checkAwardId, int month, int year) throws Exception {
        Session session = null;
        List<HistoryAward> list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "ha");
                cr.createAlias("ha.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("ha.checkAwards", "a", JoinType.LEFT_OUTER_JOIN);
                cr.add(Restrictions.eq("isDeleted", false))
                        .add(Restrictions.eq("cus.isDelete", false))
                        .add(Restrictions.eq("cus.isActive", true))
                        .add(Restrictions.eq("a.id", checkAwardId));
                if (month != -1 && year != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(dateCreated)=" + month));
                }

                if (year != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(dateCreated)=" + year));
                }
                cr.setProjection(Projections.rowCount());
                pagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
                cr.setProjection(Projections.projectionList()
                        .add(Projections.property("id"))
                        .add(Projections.property("name"))
                        .add(Projections.property("price"))
                        .add(Projections.property("pricePv"))
                        .add(Projections.property("dateCreated")));
                cr.setFirstResult(pagination.getFirstResult());
                cr.setMaxResults(pagination.getDisplayPerPage());
                cr.addOrder(pagination.isAsc() ? Order.asc(pagination.getOrderColmn()) : Order.desc(pagination.getOrderColmn()));
                pagination.setDisplayList(cr.list());
                return cr.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public LinkedHashMap reportAllAwardByMonth(int year) {
        Session session = null;
        LinkedHashMap<String, Object[]> report = new LinkedHashMap();
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH);
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllAwardByMonth :year").setParameter("year", year);
                String key = "Thưởng";
                for (Object rows : q.list()) {
                    Object[] row = (Object[]) rows;
                    for (int i = currentMonth + 2; i <= 12; i++) {
                        row[i] = null;
                    }
                    List<CheckAwards> list = new CheckAwardsFacade().findAll();
                    for (CheckAwards ca : list) {
                        if (ca.getId() == (Integer) row[0]) {
                            key = ca.getName();
                        }
                    }
                    report.put(key, ArrayUtils.removeItem(row, 0));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return report;
    }

    public Map reportAllComissionByMonth(int year) {
        Session session = null;
        LinkedHashMap<String, Object[]> report = new LinkedHashMap();
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Calendar now = Calendar.getInstance();
            int currentMonth = now.get(Calendar.MONTH);
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllComissionByMonth :year").setParameter("year", year);
                for (Object rows : q.list()) {
                    Object[] row = (Object[]) rows;
                    for (int i = currentMonth + 2; i <= 12; i++) {
                        row[i] = null;
                    }
                    String key;
                    switch ((Integer) row[0]) {
                        case 0: {
                            key = "Tổng Thu";
                            break;
                        }
                        case 1: {
                            key = "Tổng Chi";
                            break;
                        }
                        case 2: {
                            key = "Lãi Xuất";
                            break;
                        }
                        case 3: {
                            key = "Chi/Thu(%)";
                            break;
                        }
                        default: {
                            key = "Error";
                            break;
                        }
                    }
                    report.put(key, ArrayUtils.removeItem(row, 0));
                }
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return report;
    }

    public Integer countNewUserInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return countNewUser(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
    }

    public Integer countNewUserInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return countNewUser(null, c.get(Calendar.YEAR));
    }

    public Integer countNewUser(Integer month, Integer year) {
        Session session = null;
        Integer result = 0;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("CountNewUser :month,:year")
                    .setParameter("month", month)
                    .setParameter("year", year);
            result = (Integer) q.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getTotalInInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return getTotalIn(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalInInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return getTotalIn(null, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalIn(Integer month, Integer year, Integer cusid) {
        Session session = null;
        BigDecimal result = BigDecimal.valueOf(0);
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("GetTotalIn :month,:year,:cusid")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .setParameter("cusid", cusid);
            result = (BigDecimal) q.uniqueResult();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List reportAllTotalAwardInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return reportAllTotalAwardByMonth(null, c.get(Calendar.YEAR));
    }

    public List reportAllTotalAwardByMonth(Integer month, Integer year) {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("ReportAllTotalAwardByMonth :month,:year")
                    .addScalar("Name", StringType.INSTANCE)
                    .addScalar("Total", BigDecimalType.INSTANCE)
                    .setParameter("month", month)
                    .setParameter("year", year);
            result = q.list();
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getTotalOutInCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return getTotalOut(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalOutInCurrentYear() {
        Calendar c = Calendar.getInstance();
        return getTotalOut(null, c.get(Calendar.YEAR), null);
    }

    public BigDecimal getTotalOutOfCustomerId(Integer cusid) {
        return getTotalOut(null, null, cusid);
    }

    public BigDecimal getTotalOut(Integer month, Integer year, Integer cusid) {
        Session session = null;
        BigDecimal result = BigDecimal.valueOf(0);
        try {
            session = HibernateConfiguration.getInstance().openSession();
            Query q = session.createSQLQuery("GetTotalOut :month,:year,:cusid")
                    .setParameter("month", month)
                    .setParameter("year", year)
                    .setParameter("cusid", cusid);
            result = (BigDecimal) q.uniqueResult();
        } catch (Exception e) {
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public void pageData(com.resources.pagination.index.ReportPagination reportPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(HistoryAwards.class, "a");
                cr.createAlias("a.customer", "cus", JoinType.LEFT_OUTER_JOIN);
                cr.createAlias("a.checkAwards", "ca", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.id", reportPagination.getSelectedId()));

                cr.add(Restrictions.eq("a.isDeleted", false));
                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));
                if (reportPagination.getMonth() != -1 && reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("MONTH(DateCreated)=" + reportPagination.getMonth()));
                }

                if (reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(DateCreated)=" + reportPagination.getYear()));
                }

                if (reportPagination.getAwardType() != null) {
                    cr.add(Restrictions.eq("ca.id", reportPagination.getAwardType()));
                    if (reportPagination.getLevel() == null) {
                        cr.add(Restrictions.isNull("a.level"));
                    } else {
                        cr.add(Restrictions.eq("a.level", reportPagination.getLevel()));
                    }
                }
                ProjectionList pL = Projections.projectionList();
                pL.add(Projections.property("name"), "name")
                        .add(Projections.property("pricePv"), "pricePv")
                        .add(Projections.property("dateCreated"), "dateCreated");
                cr.setProjection(pL)
                        .setResultTransformer(Transformers.aliasToBean(HistoryAward.class));
                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                reportPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataTrian(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));

                if (reportPagination.getYear() != -1) {
                    cr.add(Restrictions.sqlRestriction("YEAR(datetimecreated)=" + reportPagination.getYear()));
                }

                cr.add(Restrictions.eq("cus.id", cusId));

                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                reportPagination.setDisplayList(cr.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataTrian(ReportPagination reportPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Criteria cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));
                cr.addOrder(reportPagination.isAsc() ? Order.asc(reportPagination.getOrderColmn()) : Order.desc(reportPagination.getOrderColmn()));

                cr.setFirstResult(reportPagination.getFirstResult());
                cr.setMaxResults(reportPagination.getDisplayPerPage());

                reportPagination.setDisplayList(cr.list());
                cr = session.createCriteria(Triandot2.class, "t");
                cr.createAlias("t.customer", "cus", JoinType.LEFT_OUTER_JOIN);

                cr.add(Restrictions.eq("cus.isDelete", false));
                cr.add(Restrictions.eq("cus.isActive", true));

                cr.setProjection(Projections.rowCount());
                reportPagination.setTotalResult(((Long) cr.uniqueResult()).intValue());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public void pageDataByMonth(com.resources.pagination.index.ReportPagination reportPagination, Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllTotalAwardByMonthForCustomer :cusid,:awardType,:year").addScalar("Month", IntegerType.INSTANCE).addScalar("Total", BigDecimalType.INSTANCE);
                q.setParameter("cusid", cusId);
                q.setParameter("awardType", reportPagination.getAwardType());
                q.setParameter("year", reportPagination.getYear());
                reportPagination.setDisplayList(q.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public List reportAllTotalAwardForCustomer(Integer cusId) {
        Session session = null;
        List list = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("ReportAllTotalAwardForCustomer :cusid").addScalar("id", IntegerType.INSTANCE).addScalar("Name", StringType.INSTANCE).addScalar("Total", BigDecimalType.INSTANCE);
                q.setParameter("cusid", cusId);
                list = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return list;
    }

    public void pageDataGByMonth(com.resources.pagination.index.ReportPagination reportPagination) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("ReportTotalGByYear :cusid,:year").addScalar("Month", IntegerType.INSTANCE).addScalar("VT", BigDecimalType.INSTANCE).addScalar("VP", BigDecimalType.INSTANCE);
                q.setParameter("cusid", reportPagination.getSelectedId());
                q.setParameter("year", reportPagination.getYear());
                reportPagination.setDisplayList(q.list());
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    public List getTop5AwardInMonth() {
        Calendar c = Calendar.getInstance();
        return getTop5Award(c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR));
    }

    public List getTop5AwardInYear() {
        Calendar c = Calendar.getInstance();
        return getTop5Award(null, c.get(Calendar.YEAR));
    }

    public List getTop5Award(Integer month, Integer year) {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("GetTop5Award :month,:year")
                        .addScalar("Name", StringType.INSTANCE)
                        .addScalar("UserName", StringType.INSTANCE)
                        .addScalar("PricePv", BigDecimalType.INSTANCE);
                q.setParameter("month", month);
                q.setParameter("year", year);
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List findAllHistoryAwardYear() {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select YEAR(dateCreated) from HistoryAwards group by YEAR(dateCreated)");
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public List findAllHistoryPaymentYear() {
        Session session = null;
        List result = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("select YEAR(datetimeCreated) from HistoryPayment group by YEAR(datetimeCreated)");
                result = q.list();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return result;
    }

    public BigDecimal getSystemAwards(String peoplesIdentity) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("GetSystemAwards :peoplesIdentity").setParameter("peoplesIdentity", peoplesIdentity);
                return (BigDecimal) q.uniqueResult();
            }
        } catch (Exception e) {
            Logger.getLogger(Module.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return BigDecimal.ZERO;
    }

    public Boolean checkHaveAwards(Integer cusId) {
        Session session = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            if (session != null) {
                Query q = session.createSQLQuery("if Exists(select * from HistoryAwards where CustomerId=:cusId) begin select 1 as result end else begin select 0 as result end").addScalar("result", BooleanType.INSTANCE).setParameter("cusId", cusId);
                return (Boolean) q.uniqueResult();
            }
        } catch (Exception e) {
            Logger.getLogger(HistoryAwards.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
        return false;
    }

    public void showAllHistoryAward(Integer cusId, Integer status) {
        Session session = null;
        Transaction trans = null;
        try {
            session = HibernateConfiguration.getInstance().openSession();
            trans = session.beginTransaction();
            Query q = session.createSQLQuery("update HistoryAwards set isDeleted=:isDeleted where CustomerId=:cusId").setParameter("cusId", cusId).setParameter("isDeleted", status);
            q.executeUpdate();
            trans.commit();
        } catch (Exception e) {
            try {
                if (trans != null) {
                    trans.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            Logger.getLogger(HistoryAwards.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            HibernateConfiguration.getInstance().closeSession(session);
        }
    }

    @Override
    public void pageData(DefaultAdminPagination pagination) {
    }
}
