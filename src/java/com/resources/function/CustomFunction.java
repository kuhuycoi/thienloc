package com.resources.function;

import com.resources.facade.CustomerFacade;
import com.resources.facade.CustomerTypeFacade;
import com.resources.facade.HistoryAwardFacade;
import com.resources.facade.ProvincialAgenciesFacade;
import com.resources.facade.RankCustomersFacade;
import com.resources.facade.RankNowFacade;
import com.google.gson.Gson;
import com.resources.bean.CustomerNonActive;
import com.resources.bean.CustomerTree;
import com.resources.entity.Admin;
import com.resources.entity.Customer;
import com.resources.entity.ModuleInRole;
import com.resources.entity.RankNow;
import com.resources.facade.AdminFacade;
import com.resources.facade.CheckAwardsFacade;
import com.resources.facade.GaleryFacade;
import com.resources.facade.NewsCategoriesFacade;
import com.resources.facade.NewsFacade;
import com.resources.facade.PinSysFacade;
import com.resources.facade.PromotionFacade;
import com.resources.facade.RoleAdminFacade;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CustomFunction {

    public static String formatCurrency(BigDecimal input) {
        return input == null ? null : new DecimalFormat("###,###,### VNĐ").format(input.multiply(BigDecimal.valueOf(10000)));
    }

    public static boolean isZero(BigDecimal input) {
        return BigDecimal.ZERO.compareTo(input) == 0;
    }

    public static String formatPV(BigDecimal input) {
        return input == null ? null : new DecimalFormat("###,###,### <i>(PV)</i>").format(input);
    }

    public static String formatPercent(Integer input) {
        return input == null ? null : String.valueOf(input) + "%";
    }

    public static String formatPercentTwoInput(BigDecimal input1, BigDecimal input2) {
        if (BigDecimal.ZERO.compareTo(input2) == 0) {
            return "0%";
        }
        return input1.multiply(BigDecimal.valueOf(100)).divide(input2, 2, RoundingMode.CEILING).toString() + "%";
    }

    public static Admin findAdminById(int id) {
        return new AdminFacade().findAdminById(id);
    }

    public static Date addDate(Date date, Integer dateAdd) {
        Calendar c = Calendar.getInstance();
        if (date == null) {
            c.add(Calendar.DATE, -100);
        } else {
            c.setTime(date);
            c.add(Calendar.DATE, dateAdd);
        }
        return c.getTime();
    }

    public static int compareCurrentDate(Date date1) {
        if (date1 == null) {
            return 1;
        }
        if (currentTime().before(date1)) {
            return -1;
        } else if (currentTime().after(date1)) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int size(Collection c) {
        return c == null ? 0 : c.size();
    }

    public static Date currentTime() {
        return new Date();
    }

    public static String[] split(String input, String match) {
        return input.split(match);
    }

    public static String replace(String input, String match, String replace) {
        return input.replace(match, replace);
    }

    public static boolean equal(String input1, String input2) {
        return input1.equals(input2);
    }

    public static boolean equalIgnoreCase(String input1, String input2) {
        return input1.equalsIgnoreCase(input2);
    }

    public static String checkNameLength(String input, Integer length) {
        return input.length() > length + 3 ? input.substring(0, length) + "..." : input;
    }

    public static String tranformStatus(Integer status) {
        return status == 0 ? "Unavailable" : "Available";
    }

    public static String formatTime(Date date) {
        return date == null ? "Unavailable" : new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(date);
    }

    public static String formatDate(Date date) {
        return date == null ? "Unavailable" : new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static Integer checkHavePromotion(Integer cusId) {
        return new PromotionFacade().checkHavePromotion(cusId);
    }

    public static String getOrderTrClass(Integer status) {
        switch (status) {
            case 0: {
                return "warning";
            }
            case 1: {
                return "info";
            }
            case 2: {
                return "success";
            }
            default: {
                return "danger";
            }
        }
    }

    public static String tranformOrderStatus(Integer status) {
        switch (status) {
            case 0: {
                return "Waiting";
            }
            case 1: {
                return "Processing";
            }
            case 2: {
                return "Done";
            }
            default: {
                return "Expired";
            }
        }
    }

    public static String getJSON(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static List<ModuleInRole> filterModuleInRoleList(List<ModuleInRole> moduleInRoles, Integer parentId) {
        return moduleInRoles.stream().filter(m -> {
            return m.getModuleID().getModule().getId() == parentId;
        }).collect(Collectors.toList());
    }

    public static List<CustomerTree> filterCustomerList(List<CustomerTree> customerList, Integer parentId) {
        return customerList.stream().filter(c -> {
            return Objects.equals(c.getBoss(), parentId);
        }).collect(Collectors.toList());
    }

    public static String buildTreeCustomer(List<CustomerTree> customerList, Integer first_id, String rootController) {
        List<CustomerTree> list = filterCustomerList(customerList, first_id);
        StringBuilder sB = new StringBuilder();
        sB.append("<ul>");
        list.stream().forEach((c) -> {
            String popOver = "<span class='popover-dismiss' data-toggle='popover' data-placement='right' data-title='" + c.getName() + "' ";
            popOver += "data-content='<b>Username:</b> " + c.getUserName() + "<br/>"
                    + "<b>Chức vụ:</b> " + c.getLevelName() + "</br>" + "'>";
//                    + "<b>Chu kỳ:</b> " + c.getCircle() + "</br>"
//                    + "<b>Nhánh trái:</b> " + formatCurrency(c.getpVLeft()) + "<br/>"
//                    + "<b>Nhánh phải:</b> " + formatCurrency(c.getpVRight()) + "'>";
            if (c.getTotalChildren() > 0) {
                if (c.getRelativeLevel() == 6) {
                    sB.append("<li class='next-item'><a>");
                    sB.append("<span class='fa fa-plus-square-o' controller='")
                            .append(rootController).append("/Next/");
                    sB.append(c.getKey());
                    sB.append("/");
                    sB.append(c.getKey());
                    sB.append("'></span>&nbsp;");
                } else {
                    sB.append("<li><a>");
                    sB.append("<span class='fa fa-minus-square-o'></span>&nbsp;");
                }
            } else {
                sB.append("<li><a>");
                sB.append("<span class='fa fa-user'></span>&nbsp;");
            }
            sB.append(popOver);
            sB.append(c.getName()).append(" - ").append(c.getUserName())
                    .append(" <i class='text-danger'>(T ").append(c.getLevel()).append(")</i>").append("</span></a>");
            if (c.getRelativeLevel() != 6) {
                sB.append(buildTreeCustomer(customerList, c.getKey(), rootController));
            }
            sB.append("</li>");
        });
        sB.append("</ul>");
        return sB.toString();
    }

    public static boolean checkModuleInrole(List<ModuleInRole> moduleInRoles, int moduleId) {
        return moduleInRoles.stream().filter(m -> {
            return Objects.equals(m.getModuleID().getId(), moduleId);
        }).collect(Collectors.toList()).size() > 0;
    }

    public static CustomerNonActive findDistributorById(Integer id) {
        return new CustomerFacade().findDistributorById(id);
    }

    public static RankNow findRankNowByCustomer(Customer customer) {
        return new RankNowFacade().findRankNowByCusID(customer);
    }

    public static RankNow findRankNowByCustomerID(Integer id) {
        return new RankNowFacade().findRankNowByCusID(new Customer(id));
    }

    public static BigDecimal getTotalOutInCurrentMonth() {
        return new HistoryAwardFacade().getTotalOutInCurrentMonth();
    }

    public static BigDecimal getTotalOutOfCustomerId(Integer cusid) {
        return new HistoryAwardFacade().getTotalOutOfCustomerId(cusid);
    }

    public static List findAllAvailableCustomerType() {
        return new CustomerTypeFacade().findAllAvailableCustomerType();
    }

    public static List findAllAvailableProvincialAgencies() {
        return new ProvincialAgenciesFacade().findAllAvailableProvincialAgencies();
    }

    public static List findAllRankCustomersHavingPinSys() {
        return new RankCustomersFacade().findAllRankCustomersHavingPinSys();
    }

    public static List findAllAvailableRankCustomer() {
        return new RankCustomersFacade().findAllAvailableRankCustomer();
    }

    public static Long parseDateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    public static List findAllHistoryAwardYear() {
        return new HistoryAwardFacade().findAllHistoryAwardYear();
    }

    public static List findAllHistoryPaymentYear() {
        return new HistoryAwardFacade().findAllHistoryPaymentYear();
    }

    public static List findAllPinSysCreatedDay() {
        return new PinSysFacade().findAllPinSysCreatedDay();
    }

    public static List findAllAvailableCheckAwards() {
        return new CheckAwardsFacade().findAll();
    }

    public static List getTotalChildren(Integer cusid, int datediff) {
        return new CustomerFacade().getTotalChildren(cusid, datediff);
    }

    public static List findAllAvailableRoleAdmin() {
        return new RoleAdminFacade().findAll();
    }

    public static Integer getAdminRoleByAdminId(Integer adminId) {
        return new AdminFacade().getAdminRoleByAdminId(adminId);
    }

    public static List reportAllTotalAwardByMonthForCustomer(Integer cusId) {
        return new HistoryAwardFacade().reportAllTotalAwardForCustomer(cusId);
    }

    public static BigDecimal getSystemAwards(String peoplesIdentity) {
        return new HistoryAwardFacade().getSystemAwards(peoplesIdentity);
    }

    public static List getNewsCategoryDropdown() {
        return new NewsCategoriesFacade().getTreeDropdown();
    }

    public static List getListPost(Integer caId, Integer firstResult, Integer totalResult) {
        return new NewsFacade().getListPost(caId, firstResult, totalResult);
    }
    
    public static List findAllGalery() {
        return new GaleryFacade().findAll();
    }

    public static List findAllGaleryCategory() {
        return new GaleryFacade().findAll();
    }

    public static String md5(String input) {
        MessageDigest md;
        StringBuffer sB;
        String output = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            sB = new StringBuffer();
            for (byte b : digest) {
                sB.append(String.format("%02x", b & 0xff));
            }
            output = sB.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CustomFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
}
