<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="ADMIN" value="${f:findAdminById(sessionScope['ADMIN_ID'])}"/>
<div class="pull-left header-btns">
    <ul class="clearfix">
        <li><a id="hide-sidebar"><span class="glyphicon glyphicon-pushpin"></span></a></li>
        <li><a id="btn-expand-sidebar"><span class="glyphicon glyphicon-align-justify"></span></a></li>
        <li><a id="fullscreen-toggle"><span class="glyphicon glyphicon-fullscreen"></span></a></li>
                <c:if test="${ADMIN.roleAdmID.id==1}">
            <li style="color: red;text-transform: uppercase; padding-top: 22px;font-size: 18px;font-weight: bold">Tổng công ty</li>
            </c:if>
            <c:if test="${ADMIN.roleAdmID.id==2}">
            <li style="color: red;text-transform: uppercase; padding-top: 22px;font-size: 18px;font-weight: bold">${ADMIN.provincialAgencyID.name}</li>
            </c:if>
            <c:if test="${ADMIN.roleAdmID.id==3}">
            <li style="color: red;text-transform: uppercase; padding-top: 22px;font-size: 18px;font-weight: bold">Kế toán</li>
            </c:if>
            <c:if test="${ADMIN.roleAdmID.id==4}">
            <li style="color: red;text-transform: uppercase; padding-top: 22px;font-size: 18px;font-weight: bold">Bộ phận IT</li>
            </c:if>
    </ul>
</div>
<div class="pull-right header-btns">
    <ul class="clearfix">
        <li class="user-menu dropdown">
            <a data-toggle="dropdown" class="external">
                Xin chào, <label>${ADMIN.name}!</label>
            </a>
            <ul class="dropdown-menu checkbox-persist center dropdown-menu-right" role="menu">
                <li class="menu-arrow">
                    <div class="menu-arrow-up"></div>
                </li>
                <li class="dropdown-header">
                    ${ADMIN.userName} <span class="pull-right glyphicon glyphicon-user"></span>
                </li>
                <li>
                    <ul class="dropdown-items">
                        <li class="padding-none clearfix">
                            <div class="pull-left dropdown-edit"><i class="fa fa-edit"></i> <a class="btn-open-modal" controller="/Admin/ViewEdit/${sessionScope['ADMIN_ID']}">Edit Profile</a></div>
                            <div class="pull-right dropdown-signout"><i class="fa fa-sign-out"></i> <a href='<c:url value="/Admin/Logout.html"/>'>Sign Out</a></div>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</div>