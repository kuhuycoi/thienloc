<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="RANKNOW" value="${f:findRankNowByCustomerID(PAGINATION.selectedId)}" />
<table class="table table-hover table-bordered table-valign-midle table-grid-view table-align-center">
    <thead>
        <tr>
            <th class="col-xs-3 external"></th>
            <th class="col-xs-3 external">Nhánh trái</th>
            <th class="col-xs-3 external">Nhánh phải</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td class="text-left">Tổng doanh số trong tháng</td>
            <td>${f:formatPV(RANKNOW.pvmonth)}</td>
            <td>${f:formatPV(RANKNOW.pvmonth1)}</td>
        </tr>
        <tr>
            <td class="text-left">Tổng doanh số bảo lưu</td>
            <td>${f:formatPV(RANKNOW.pvuser)}</td>
            <td>${f:formatPV(RANKNOW.pvuser1)}</td>
        </tr>
        <tr>
            <td class="text-left">Tổng doanh số tích lũy</td>
            <td>${f:formatPV(RANKNOW.pricePvuser)}</td>
            <td>${f:formatPV(RANKNOW.pricePvuser1)}</td>
        </tr>
    </tbody>
</table><!-- end table -->

<div class="buttonBar"></div>
<p>Danh sách 1000 thành viên mới nhất trong 1 tuần</p>
<table class="table table-bordered table-condensed">
    <tr>
        <th class="col-xs-6"><b>Nhánh trái</b></th>
        <th class="col-xs-6"><b>Nhánh phải</b></th>
    </tr>
    <tr>
        <td>
            <ul>
                <c:forEach items="${f:getTotalChildren(RANKNOW.userId, 7)}" var="user">
                    <li>${user[0]} ${user[1]=='CTV'?"<i style='color: red'>(Chưa kích hoạt)</i>":''} ${user[2]==null?'':f:formatTime(user[2])}</li>
                </c:forEach>
            </ul>
        </td>
        <td>
            <ul>
                <c:forEach items="${f:getTotalChildren(RANKNOW.userId1, 7)}" var="user">
                    <li>${user[0]} ${user[1]=='CTV'?"<i style='color: red'>(Chưa kích hoạt)</i>":''} ${user[2]==null?'':f:formatTime(user[2])}</li>
                </c:forEach>
            </ul>
        </td>
    </tr>
</table>