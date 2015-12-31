<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set value="${f:getAdminRoleByAdminId(sessionScope['ADMIN_ID'])}" var="ROLE" />
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Cập nhật quản trị viên</h3>
                    </div>
                </div>
            </div>
            <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="<c:url value='/Admin/Permission/ListAdmin/Edit'/>">
                <div class="modal-body">
                    <input type="hidden" name="id" value="${ADMIN_EDIT.id}">
                    <div class="form-group">
                        <label for="name" class="control-label col-sm-3">Họ và tên</label>
                        <div class="col-sm-9">
                            <input type="text" id="name" name="name" value="${ADMIN_EDIT.name}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label col-sm-3">Password</label>
                        <div class="col-sm-9">
                            <input type="password" id="password" name="password" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Đại lý</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="provincialAgencyID">
                                <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="pro">
                                    <option value='${pro.id}' ${ADMIN_EDIT.provincialAgencyID.id==pro.id?'selected':''}>${pro.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>     
                    <div class="form-group">
                        <label class="control-label col-sm-3">Quyền quản trị</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="roleAdmID">
                                <c:forEach items="${f:findAllAvailableRoleAdmin()}" var="role">
                                    <option value='${role.id}' ${ADMIN_EDIT.roleAdmID.id==role.id?'selected':''}>${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>            
                    <div class="form-group">
                        <label class="control-label col-sm-3">Giới tính</label>
                        <div class="col-sm-9">
                            <label class="checkbox-inline"><input type="radio" name="gender" value="true" ${ADMIN_EDIT.gender?'checked':''}>Nam</label>
                            <label class="checkbox-inline"><input type="radio" name="gender" value="false" ${!ADMIN_EDIT.gender?'checked':''}>Nữ</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Email</label>
                        <div class="col-sm-9">
                            <input type="email" id="moduleIcon" name="email" value="${ADMIN_EDIT.email}" class="form-control" placeholder="" pattern="^[\w\d_-]+@[\w\d-_]+(.[\w\d-_]+)+$" data-original-title-pattern="Định dạng email không hợp lệ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile" class="control-label col-sm-3">Số điện thoại</label>
                        <div class="col-sm-9">
                            <input type="text" id="mobile" name="mobile" value="${ADMIN_EDIT.mobile}" class="form-control" placeholder="" pattern="^0|\+[\d]+[\d]+$" data-original-title-pattern="Định dạng số điện thoại không hợp lệ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="taxCode" class="control-label col-sm-3">Mã số thuế cá nhân</label>
                        <div class="col-sm-9">
                            <input type="text" id="taxCode" name="taxCode" value="${ADMIN_EDIT.taxCode}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="billingAddress" class="control-label col-sm-3">Địa chỉ ngân hàng</label>
                        <div class="col-sm-9">
                            <input type="text" id="billingAddress" name="billingAddress" value="${ADMIN_EDIT.billingAddress}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="taxCode" class="control-label col-sm-3">Địa chỉ</label>
                        <div class="col-sm-9">
                            <textarea name="address" rows="4" id="address" class="form-control">${ADMIN_EDIT.address}</textarea>
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
