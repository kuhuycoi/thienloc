<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['INDEX_CUSTOMER_FOR_CUSTOMER_PAGINATION']}"/>
<h1>Thông tin hệ thống</h1>
<div class="panel">
    <div class="panel-heading">
        <h2>Cây thư mục chỉ định</h2>
    </div><!-- end panel heading -->
    <div class="ajax-content panel-body">
        <%@include file="../ajax_content/customer_tree_folder_customer.jsp" %>
    </div>
</div><!-- end panel-->