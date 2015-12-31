<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_HISTORY_AWARD_PAGINATION']}"/>
<c:if test="${f:size(PAGINATION.displayList)==0}">
    <div class="alert alert-danger">
        Không có kết quả nào được hiển thị
    </div>
</c:if>
<c:if test="${f:size(PAGINATION.displayList)!=0}">
    <table class="table table-hover table-bordered table-valign-midle table-grid-view table-align-center">
        <thead>
            <tr controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.ORDER_DATA}/'/>">
                <th class="external">STT</th>
                <th class="col-xs-6 text-left" column="name">Tên thưởng <span class="${PAGINATION.orderColmn=='name'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-xs-3 text-right" column="dateCreated">Thời gian thưởng <span class="${PAGINATION.orderColmn=='dateCreated'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
                <th class="col-xs-3" column="pricePv">Tiền thưởng(PV) <span class="${PAGINATION.orderColmn=='pricePv'?(PAGINATION.asc?'fa fa-caret-up':'fa fa-caret-down'):''}"></span></th>
            </tr>
        </thead>
        <tbody>
            <c:set var="total" value="0"/>
            <c:set var="no" value="1"/>
            <c:forEach items="${PAGINATION.displayList}" var="award">
                <tr>
                    <td>${no}</td>
                    <td class="text-left">${award.name}</td>
                    <td class="text-right">${f:formatTime(award.dateCreated)}</td>
                    <td class="curency">${f:formatCurrency(award.pricePv)}</td>
                </tr>
                <c:set var="total" value="${total+award.pricePv}"/>
                <c:set var="no" value="${no+1}"/>
            </c:forEach>
            <tr class="total-number" style="color: #fff;background-color: #3498db">
                <td colspan="3">Tổng hoa hồng:</td>
                <td style="color: #fff;font-weight: bold;text-align: center">${f:formatCurrency(total)}</td>
            </tr>
        </tbody>
    </table><!-- end table -->
    <script>
        $(document).ready(function(){
            $('.total-number').prependTo('.table>thead');
        });
    </script>


    <c:if test="${PAGINATION.totalResult>0}">
        <div class="row">
            <div class="col-xs-4">
                <c:if test="${PAGINATION.totalResult>5}">
                    <select class="form-control input-sm display-per-page" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.DISPLAY_PER_PAGE}/'/>">
                        <c:forEach begin="5" step="5" end="50" var="numb">
                            <option ${numb==PAGINATION.displayPerPage?'selected':''}>${numb}</option>
                        </c:forEach>
                    </select>
                    <label class="control-label">/ <i>${PAGINATION.totalResult}</i> Kết quả</label>
                </c:if>
            </div>
            <div class="col-xs-4 text-center">                    
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
            <div class="col-xs-4 text-right">
            </div>
        </div>
    </c:if>
    <div class="buttonBar"></div>
</c:if>