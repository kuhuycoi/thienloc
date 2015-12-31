<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_HISTORY_AWARD_G_PAGINATION']}"/>
<h1>Thống kê thưởng</h1>
<div class="panel">
    <div class="panel-heading">
        <h2>${PAGINATION.viewTitle}</h2>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <div class="row">
            <div class="col-xs-4"><label class="control-label">Danh sách mã chủ & mã con</label></div>
            <div class="col-xs-8">
                <select class="form-control change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_CUSTOMER}/"/>">
                    <option value="-1" ${PAGINATION.month==-1?'selected':''}>-- Tất cả --</option>
                </select>
            </div>
        </div>
        <div class="ajax-content">
            <%@include file="../ajax_content/report_award_g.jsp" %>
        </div>
    </div>
</div><!-- end panel-->