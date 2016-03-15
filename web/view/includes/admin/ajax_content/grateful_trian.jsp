<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['ADMIN_GRATEFUL_TRIAN_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-condensed table-hover table-valign-midle table-grid-view table-align-center table-bordered">
        <thead>
            <tr data-toggle="tooltip" data-placement="top" data-title="Click vào cột để sắp xếp theo cột đó, click lần nữa để sắp xếp ngược lại." controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.ORDER_DATA}/"/>'>
                <th class="th-id" column="id">ID <span class="${PAGINATION.orderColmn=='id'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-1" column="pos">Vị trí <span class="${PAGINATION.orderColmn=='pos'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="parentPos">Vị trí cha <span class="${PAGINATION.orderColmn=='parentPos'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="customerUserName">Tên đăng nhập <span class="${PAGINATION.orderColmn=='customerUserName'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-1" column="totalChildren">Số con <span class="${PAGINATION.orderColmn=='totalChildren'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-1" column="levelRank">Rank <span class="${PAGINATION.orderColmn=='levelRank'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="levelRank">Thành tiền <span class="${PAGINATION.orderColmn=='levelRank'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-md-2" column="dateCreated">Thời gian tham gia <span class="${PAGINATION.orderColmn=='dateCreated'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${PAGINATION.displayList}" var="distribtor">
                <tr>
                    <td class="td-align-center">${distribtor.id}</td>
                    <td class="td-align-center">${distribtor.pos}</td>
                    <td class="td-align-center">${distribtor.parentPos}</td>
                    <td>${distribtor.customerUserName}</td>
                    <td class="td-align-center">${distribtor.totalChildren}</td>
                    <td class="td-align-center bold-red">${distribtor.levelRank}</td>
                    <c:set value="0" var="money" />
                    <c:choose>
                        <c:when test="${distribtor.levelRank==1}">
                            <c:set value="900" var="money" />
                        </c:when>
                        <c:when test="${distribtor.levelRank==2}">
                            <c:set value="2700" var="money" />
                        </c:when>
                        <c:when test="${distribtor.levelRank==3}">
                            <c:set value="5400" var="money" />
                        </c:when>
                        <c:when test="${distribtor.levelRank==4}">
                            <c:set value="12600" var="money" />
                        </c:when>
                        <c:when test="${distribtor.levelRank==5}">
                            <c:set value="27000" var="money" />
                        </c:when>
                    </c:choose>
                    <td class="td-align-center bold-red">${f:formatCurrency(money)}</td>    
                    <td>${f:formatTime(distribtor.dateCreated)}</td>
                </tr>
            </c:forEach>

            <tr style="background-color: #ccc;font-weight: bold;"> 
                <td colspan="7" class="text-right bold-blue">Tổng cộng: </td>
                <td class="text-right bold-red">${f:formatCurrency(PAGINATION.totalAward)}</td>
            </tr>
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