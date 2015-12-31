<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="ADMIN" value="${f:findAdminById(sessionScope['ADMIN_ID'])}"/>
<div class="logo">
    <a href="<c:url value="/Admin"/>" class="text-uppercase">
        <span class="wow fadeInLeft" data-wow-delay="0.1s">T</span><span class="wow fadeInRightBig" data-wow-delay="10ms">hiên Lộc</span>
    </a>
</div>
<div class="scroll-bar">
    <ul class="nav sidebar-nav">
        <c:set var="MODULE_IN_ROLES" value="${f:filterModuleInRoleList(ADMIN.roleAdmID.moduleInRoles,1)}" />
        <c:set var="MODULE" value=""/>
        <c:forEach items="${MODULE_IN_ROLES}" var="MODULE_IN_ROLE"> 
            <c:set var="MODULE" value="${MODULE_IN_ROLE.moduleID}"/>
            <c:if test="${MODULE.isShow&&!MODULE.isDeleted}">
                <li>
                    <a class="external">
                        <span class="fa ${MODULE_IN_ROLE.moduleID.icon} item-icon"></span>
                        <span class="sidebar-title">${MODULE_IN_ROLE.moduleID.name}</span>
                        <c:if test="${f:size(MODULE_IN_ROLE.moduleID.modules)>0}">
                            <span class="fa fa-angle-down item-expand"></span>
                        </c:if>
                    </a> 
                    <ul>
                        <c:forEach items="${f:filterModuleInRoleList(ADMIN.roleAdmID.moduleInRoles,MODULE.id)}" var="childrenModuleInRole">
                            <c:set value="${childrenModuleInRole.moduleID}" var="childrenModule" />
                            <c:if test="${childrenModule.isShow&&!childrenModule.isDeleted}">
                                <li>
                                    <a class="${childrenModule.cssClass}" controller='<c:url value="/Admin${MODULE_IN_ROLE.moduleID.controller}${childrenModule.controller}"/>'>${childrenModule.name}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </c:if>
        </c:forEach>
    </ul>
</div>