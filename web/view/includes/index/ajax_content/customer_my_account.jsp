<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set value="${f:findDistributorById(sessionScope['CUSTOMER_ID'])}" var="CUSTOMER" />
<c:set value="${f:findRankNowByCustomerID(sessionScope['CUSTOMER_ID'])}" var="RANKNOW" />
<h1>Thông tin hệ thống</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Thông tin tài khoản</h2>
    </div>
    <div class="panel-body">
        <p>Thông tin tài khoản:</p>
        <table class="table table-bordered">
            <tr>
                <td>Tên đăng nhập</td>
                <td>${CUSTOMER.userName}</td>
            </tr>
            <tr>
                <td>Cấp bậc</td>
                <td>${RANKNOW.levelId}</td>
            </tr>
            <tr>
                <td>Gói tham gia</td>
                <td>${CUSTOMER.rankCustomerName!=null&&CUSTOMER.isDeposited?CUSTOMER.rankCustomerName:'<i style="color:red">NPP</i>'}</td>
            </tr>
            <tr>
                <td>Đại lý</td>
                <td>${CUSTOMER.provinceAgencyName}</td>
            </tr>
            <tr>
                <td>Ngày kích hoạt</td>
                <td>${CUSTOMER.lastLoginDateUtc==null?"":f:formatTime(CUSTOMER.lastLoginDateUtc)}</td>
            </tr>
            <tr>
                <td>Hoa hồng trước thuế</td>
                <td class="redColor">${f:formatCurrency(f:getTotalOutOfCustomerId(sessionScope['CUSTOMER_ID']))}</td>
            </tr>
            <tr>
                <td>Hoa hồng sau thuế</td>
                <td class="redColor">${f:formatCurrency(f:getTotalOutOfCustomerId(sessionScope['CUSTOMER_ID'])*90/100)}</td>
            </tr>
            <tr>
                <td>Người giới thiệu</td>
                <td>${CUSTOMER.customerName}</td>
            </tr>
            <tr>
                <td>Người chỉ định</td>
                <td>${CUSTOMER.parentName}</td>
            </tr>
        </table>
        <div class="buttonBar"></div>
        <p>Thông tin cá nhân:</p>
        <table class="table table-bordered">
            <tr>
                <td>Họ và tên</td>
                <td>${CUSTOMER.firstName} ${CUSTOMER.lastName}</td>
            </tr>
            <tr>
                <td>Ngày sinh</td>
                <td>${CUSTOMER.birthday==null?'':f:formatDate(CUSTOMER.birthday)}</td>
            </tr>
            <tr>
                <td>Số CMND</td>
                <td>${CUSTOMER.peoplesIdentity}</td>
            </tr>
            <tr>
                <td>Ngày cấp CMND</td>
                <td>${CUSTOMER.lastActivityDateUtc}</td>
            </tr>
            <tr>
                <td>Địa chỉ</td>
                <td>${CUSTOMER.address}</td>
            </tr>
            <tr>
                <td>Số ĐT</td>
                <td>${CUSTOMER.mobile}</td>
            </tr>
            <tr>
                <td>Mã số thuế</td>
                <td>${CUSTOMER.taxCode}</td>
            </tr>
        </table>
    </div>
</div>