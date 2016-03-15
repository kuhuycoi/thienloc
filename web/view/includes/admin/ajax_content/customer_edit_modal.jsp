<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="ROLE" value="${f:getAdminRoleByAdminId(sessionScope['ADMIN_ID'])}" />
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Cập nhật nhà phân phối</h3>
                    </div>
                </div>
            </div>
            <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/Customer/Distributor/Edit">
                <div class="modal-body">
                    <input type="hidden" name="id" value="${CUSTOMER.id}">
                    <div class="form-group">
                        <label for="lastName" class="control-label col-sm-3">Họ tên</label>
                        <div class="col-sm-9">
                            <input type="text" id="lastName" required name="lastName" value="${CUSTOMER.lastName}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <c:if test="${ROLE==1}">
                        <div class="form-group">
                            <label class="control-label col-sm-3">Đại lý</label>
                            <div class="col-sm-9">
                                <select class="form-control text-center" name="provinceAgencyId">
                                    <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="province">
                                        <option value='${province.id}' ${province.id==CUSTOMER.provinceAgencyId?'selected':''}>${province.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label for="dateOfBirth" class="control-label col-sm-3">Ngày sinh</label>
                        <div class="col-sm-9">
                            <input type="text" id="dateOfBirth" name="dateOfBirth" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" data-original-title-pattern="Định dạng ngày tháng không hợp lệ dd/mm/yyyy" value="${CUSTOMER.dateOfBirth}" class="form-control date-mask" placeholder="Định dạng yêu cầu: dd/mm/yyyy. VD: 01/12/1990">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="peoplesIdentity" class="control-label col-sm-3">Số CMND</label>
                        <div class="col-sm-9">
                            <input type="text" id="peoplesIdentity" name="peoplesIdentity" value="${CUSTOMER.peoplesIdentity}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastActivityDateUtc" class="control-label col-sm-3">Ngày cấp CMND</label>
                        <div class="col-sm-9">
                            <input type="text" id="lastActivityDateUtc" name="lastActivityDateUtc" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" data-original-title-pattern="Định dạng ngày tháng không hợp lệ dd/mm/yyyy" value="${CUSTOMER.lastActivityDateUtc}" class="form-control date-mask" placeholder="Định dạng yêu cầu: dd/mm/yyyy. VD: 01/12/1990  ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Loại khách hàng</label>
                        <div class="col-sm-9">
                            <select class="form-control text-center" name="customerTypeId">
                                <option value="">-- Để trống --</option>
                                <c:forEach items="${f:findAllAvailableCustomerType()}" var="customerType">
                                    <option value='${customerType.id}' ${customerType.id==CUSTOMER.customerTypeId?'selected':''}>${customerType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Email</label>
                        <div class="col-sm-9">
                            <input type="email" id="moduleIcon" name="email" value="${CUSTOMER.email}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Giới tính</label>
                        <div class="col-sm-9">
                            <label class="checkbox-inline"><input type="radio" name="gender" ${CUSTOMER.gender?'checked':''} value="true">Nam</label>
                            <label class="checkbox-inline"><input type="radio" name="gender" ${CUSTOMER.gender?'':'checked'} value="false">Nữ</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile" class="control-label col-sm-3">Số điện thoại</label>
                        <div class="col-sm-9">
                            <input type="text" id="mobile" name="mobile" value="${CUSTOMER.mobile}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="billingAddress" class="control-label col-sm-3">Địa chỉ ngân hàng</label>
                        <div class="col-sm-9">
                            <input type="text" id="billingAddress" name="billingAddress" value="${CUSTOMER.billingAddress}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="taxCode" class="control-label col-sm-3">Mã số thuế cá nhân</label>
                        <div class="col-sm-9">
                            <input type="text" id="taxCode" name="taxCode" value="${CUSTOMER.taxCode}" pattern="^[\d-]+$" data-original-title-pattern="Chỉ chấp nhận các ký tự số và kí tự \"-\"" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="control-label col-sm-3">Địa chỉ</label>
                        <div class="col-sm-9">
                            <textarea name="address" rows="4" id="address" class="form-control">${CUSTOMER.address}</textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer text-center">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <button type="reset" class="btn btn-danger">Nhập lai</button>
                </div>
            </form>
        </div>
    </div>
</div>
