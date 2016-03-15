<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['ADMIN_GRATEFUL_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-condensed table-hover table-valign-midle table-grid-view table-align-center table-bordered">
        <thead>
            <tr data-toggle="tooltip" data-placement="top" data-title="Click vào cột để sắp xếp theo cột đó, click lần nữa để sắp xếp ngược lại." controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.ORDER_DATA}/"/>'>
                
                <c:if test="${PAGINATION.hasPinCode==null||!PAGINATION.hasPinCode}">
                    <th class="th-checkbox external"><input class="select-all" type="checkbox" /></th>
                </c:if>
                <th class="th-id" column="id">ID <span class="${PAGINATION.orderColmn=='id'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="lastName">Tên <span class="${PAGINATION.orderColmn=='lastName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="userName">Tên đăng nhập <span class="${PAGINATION.orderColmn=='userName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="lastLoginDateUtc">Ngày kích hoạt <span class="${PAGINATION.orderColmn=='lastLoginDateUtc'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="pinCode">Mã PIN <span class="${PAGINATION.orderColmn=='pinCode'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <c:if test="${PAGINATION.hasPinCode==null||!PAGINATION.hasPinCode}">
                    <th class="th-action external">
                        <div class="btn-group btn-group-sm">
                            <a type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right dropdown-menu-multiple" role="menu">
                                <li><a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.INSERT}'/>">Cập nhật mã PIN</a></li>
                            </ul>
                        </div>
                    </th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${PAGINATION.displayList}" var="distribtor">
                <tr>
                    <c:if test="${PAGINATION.hasPinCode==null||!PAGINATION.hasPinCode}">
                        <td class="text-center">
                            <c:if test="${distribtor.pinCode==null}">
                                <input type="checkbox" value="${distribtor.id}" />
                            </c:if>
                        </td>
                    </c:if>
                    <td class="td-align-center">${distribtor.id}</td>
                    <td>${distribtor.lastName}</td>
                    <td class="bold-blue">${distribtor.userName}</td>
                    <td>${f:formatTime(distribtor.lastLoginDateUtc)}</td>
                    <td class="bold-red">${distribtor.pinCode==null?"<i>Chưa cập nhật</i>":distribtor.pinCode}</td>
                    
                    <c:if test="${PAGINATION.hasPinCode==null||!PAGINATION.hasPinCode}">
                        <td>
                            <c:if test="${distribtor.pinCode==null}">
                                <div class="btn-group btn-group-sm">
                                    <a type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"><i class="ti-settings"></i></a>
                                    <ul class="dropdown-menu dropdown-menu-right dropdown-menu-action" role="menu">
                                        <li><a controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.INSERT}/${distribtor.id}'/>">Cập nhật mã PIN</a></li>
                                    </ul>
                                </div>
                            </c:if>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table><!-- end table -->
    <c:if test="${PAGINATION.totalResult>0}">
        <div class="row">
            <div class="col-md-4 text-left">
                <c:if test="${PAGINATION.totalResult>5}">
                    <label class="control-label">Hiển thị </label>
                    <select class="form-control input-sm display-per-page" data-toggle="tooltip" data-placement="bottom" data-title="Thay đổi số kết quả đươc hiển thị trên một trang." controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.DISPLAY_PER_PAGE}/'/>">
                        <c:forEach begin="5" step="5" end="50" var="numb">
                            <option ${numb==PAGINATION.displayPerPage?'selected':''}>${numb}</option>
                        </c:forEach>
                    </select>
                    <label class="control-label">/ <i>${PAGINATION.totalResult}</i> Kết quả</label>
                </c:if>
            </div>
            <div class="col-md-4 text-center">                    
                <c:if test="${PAGINATION.totalResult>PAGINATION.displayPerPage}">
                    <form class="pagination" action='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.GO_TO}/"/>'>
                        <div class="input-group input-group-sm">
                            <div class="input-group-btn">
                                <button type="button" page="1" data-toggle="tooltip" data-placement="bottom" data-title="Đến trang đầu tiên." class="btn btn-default first ${PAGINATION.currentPage==1?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-step-backward"></span></button>
                                <button type="button" page="${PAGINATION.currentPage-1}" data-toggle="tooltip" data-placement="bottom" data-title="Lùi lại một trang." class="btn btn-default prev ${PAGINATION.currentPage==1?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-play"></span></button>
                            </div>
                            <input type="number" class="form-control" data-toggle="tooltip" data-placement="bottom" data-title="Nhập số trang cần tới." value="${PAGINATION.currentPage}" max="${PAGINATION.totalPage}" min="1" />
                            <span class="input-group-addon">/<span>${PAGINATION.totalPage}</span></span> 

                            <div class="input-group-btn">
                                <button type="button" page="${PAGINATION.currentPage+1}" data-toggle="tooltip" data-placement="bottom" data-title="Tiến lên một trang." class="btn btn-default next ${PAGINATION.currentPage==PAGINATION.totalPage?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-play"></span></button>
                                <button type="button" page="${PAGINATION.totalPage}" data-toggle="tooltip" data-placement="bottom" data-title="Đến trang cuối cùng" class="btn btn-default last ${PAGINATION.currentPage==PAGINATION.totalPage?'disabled':''}"><span aria-hidden="true" class="glyphicon glyphicon-step-forward"></span></button>
                            </div>
                        </div>
                    </form>
                </c:if>
            </div>
        </div>
    </c:if>
</c:if>