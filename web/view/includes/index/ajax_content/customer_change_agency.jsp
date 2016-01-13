<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set value="${f:findDistributorById(sessionScope['CUSTOMER_ID'])}" var="CUSTOMER" />
<h1>Thông tin hệ thống</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Cập nhật đại lý nhận hoa hồng</h2>
    </div>
    <div class="panel-body">
        <form class="form-insert" novalidate method="post" action='<c:url value="/Customer/ChangeAgency"/>'>
            <div class="row">
                <div class="col-xs-3">
                    <label>Đại lý</label>
                </div>
                <div class="col-xs-9">
                    <select name="provinceAgencyId" required class="form-control">
                        <option value="">-- Lựa chọn Đại lý --</option>
                        <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="provincialAgency">
                            <option value='${provincialAgency.id}' ${CUSTOMER.provinceAgencyId==provincialAgency.id?'selected':''}>${provincialAgency.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="buttonBar">
                <button type="submit" class="btn btn-default">
                    Cập nhật
                </button>
                <button type="reset" class="btn btn-default">
                    Nhập lại
                </button>
            </div> 
        </form>
    </div>
</div>