<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['HISTORY_NEVER_UP_RANK_PAGINATION']}"/>
<c:set value="${f:getAdminRoleByAdminId(sessionScope['ADMIN_ID'])}" var="ROLE" />
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-condensed table-hover table-valign-midle table-grid-view table-align-center table-bordered">
        <thead>
            <tr controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.ORDER_DATA}/'/>">
                <th class="th-checkbox external"><input class="select-all" type="checkbox" /></th>
                <th class="th-id" column="id">ID <span class="${PAGINATION.orderColmn=='id'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-4" column="lastName">Tên NPP <span class="${PAGINATION.orderColmn=='lastName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="userName">Username NPP <span class="${PAGINATION.orderColmn=='userName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="parentName">Người chỉ định <span class="${PAGINATION.orderColmn=='parentName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                    <c:if test="${ROLE==3||ROLE==4}">                
                    <th class="col-md-2" column="rankCustomerName">Gói PV <span class="${PAGINATION.orderColmn=='rankCustomerName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                    </c:if>
                    <c:if test="${ROLE==3}">
                    <th class="col-md-2" column="isAdmin">Có lỗi <span class="${PAGINATION.orderColmn=='isAdmin'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                    </c:if>
                <th class="th-action external">
        <div class="btn-group btn-group-sm">
            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></button>
            <ul class="dropdown-menu dropdown-menu-right dropdown-menu-multiple" role="menu">
                <c:if test="${ROLE==2}">
                    <li>
                        <a class="btn-open-modal-multiple" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/ViewInsertMultiple'/>">Kích hoạt</a>
                    </li>
                </c:if>
                <c:if test="${ROLE==3}">
                    <li>
                        <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/ActiveRank'/>">Duyệt</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </th>
</tr>
</thead>
<tbody>
    <c:forEach items="${PAGINATION.displayList}" var="cus">
        <c:if test="${ROLE==3}">
            <tr class="${cus.isAdmin?"alert alert-danger":""}">
            </c:if>
            <c:if test="${ROLE!=3}">
            <tr>
            </c:if>
            <td class="text-center">
                <c:if test="${!cus.isAdmin}">
                    <input type="checkbox" value="${cus.id}" />
                </c:if>
            </td>
            <td>${cus.id}</td>
            <td>${cus.firstName} ${cus.lastName}</td>
            <td class="bold-blue">${cus.userName}</td>
            <td>${cus.parentName}</td>
            <c:if test="${ROLE==3||ROLE==4}"> 
                <td class="bold-red">${cus.rankCustomerName}</td>               
            </c:if>

            <c:if test="${ROLE==3}">
                <td>${cus.isAdmin?"Có lỗi":""}</td>
            </c:if>
            <td>
                <c:if test="${ROLE!=3&&ROLE!=4}">
                    <div class="btn-group btn-group-sm">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></button>
                        <ul class="dropdown-menu dropdown-menu-right dropdown-menu-action" role="menu">
                            <c:if test="${ROLE==1}">
                                <li>
                                    <a class="btn-open-modal" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_INSERT}/${cus.userName}'/>">Nạp tiền</a>
                                </li>
                            </c:if>
                            <c:if test="${ROLE==2}">
                                <li>
                                    <a class="btn-open-modal" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_INSERT}/${cus.userName}'/>">Gửi yêu cầu kích hoạt</a>
                                </li>
                            </c:if>
                            <c:if test="${ROLE==4}">
                                <li>
                                    <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}/NeverUpRank/Insert/${cus.id}'/>">Nạp tiền</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </c:if>

                <c:if test="${ROLE==3}">
                    <c:if test="${!cus.isAdmin}">
                        <div class="btn-group btn-group-sm">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></button>
                            <ul class="dropdown-menu dropdown-menu-right dropdown-menu-action" role="menu">
                                <li>
                                    <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}/NeverUpRank/ActiveRank/${cus.id}'/>">Duyệt</a>
                                </li>
                                <li>
                                    <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}/NeverUpRank/Deny/${cus.id}'/>">Báo lỗi</a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </c:if>

                <c:if test="${ROLE==4}">
                    <c:if test="${!cus.isAdmin}">
                        <div class="btn-group btn-group-sm">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></button>
                            <ul class="dropdown-menu dropdown-menu-right dropdown-menu-action" role="menu">
                                <li>
                                    <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}/NeverUpRank/Insert/${cus.id}'/>">Nạp tiền</a>
                                </li>
                                <li>
                                    <a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}/NeverUpRank/Deny/${cus.id}'/>">Báo lỗi</a>
                                </li>
                            </ul>
                        </div>
                    </c:if>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</tbody>
</table><!-- end table -->
<c:if test="${PAGINATION.totalResult>0}">
    <div class="row">
        <div class="col-md-4">
            <c:if test="${PAGINATION.totalResult>5}">
                <select class="form-control input-sm display-per-page" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.DISPLAY_PER_PAGE}/'/>">
                    <c:forEach begin="5" step="5" end="50" var="numb">
                        <option ${numb==PAGINATION.displayPerPage?'selected':''}>${numb}</option>
                    </c:forEach>
                </select>
                <label class="control-label">/ <i>${PAGINATION.totalResult}</i> Kết quả</label>
            </c:if>
        </div>
        <div class="col-md-4 text-center">                    
            <c:if test="${PAGINATION.totalResult>PAGINATION.displayPerPage}">
                <form class="pagination" action="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.GO_TO}/'/>">
                    <div class="input-group input-group-sm">
                        <div class="input-group-btn">
                            <button type="button" page="1" class="btn btn-default first ${PAGINATION.currentPage==1?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-step-backward"></span></button>
                            <button type="button" page="${PAGINATION.currentPage-1}" class="btn btn-default prev ${PAGINATION.currentPage==1?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-play"></span></button>
                        </div>
                        <input type="number" class="form-control" value="${PAGINATION.currentPage}" max="${PAGINATION.totalPage}" min="1" />
                        <span class="input-group-addon">/<span>${PAGINATION.totalPage}</span></span> 

                        <div class="input-group-btn">
                            <button type="button" page="${PAGINATION.currentPage+1}" class="btn btn-default next ${PAGINATION.currentPage==PAGINATION.totalPage?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-play"></span></button>
                            <button type="button" page="${PAGINATION.totalPage}" class="btn btn-default last ${PAGINATION.currentPage==PAGINATION.totalPage?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-step-forward"></span></button>
                        </div>
                    </div>
                </form>
            </c:if>
        </div>
    </div>
</c:if>
</c:if>