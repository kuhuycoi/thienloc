<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Thêm mới quản trị viên</h3>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="<c:url value='/Admin/Permission/ListAdmin/Insert'/>">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Họ và Tên</label>
                        <div class="col-sm-9">
                            <input type="text" id="moduleName" required name="name" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userName" class="control-label col-sm-3">Tên đăng nhập</label>
                        <div class="col-sm-9">
                            <input type="text" id="userName" required pattern="^[\w\d]{3,100}$" data-original-title-pattern="Chỉ chấp nhận các ký tự A-Z,a-z,0-9" name="userName" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userName" class="control-label col-sm-3">Mật khẩu</label>
                        <div class="col-sm-9">
                            <input type="password" required pattern="^[\w\d]+$" data-original-title-pattern="Vui lòng các ký tự A-Z, a-z, 0-9" name="password" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Đại lý</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="provincialAgencyID">
                                <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="pro">
                                    <option value='${pro.id}'>${pro.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Quyền quản trị</label>
                        <div class="col-sm-9">
                            <select class="form-control" name="roleAdmID">
                                <c:forEach items="${f:findAllAvailableRoleAdmin()}" var="role">
                                    <option value='${role.id}'>${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Giới tính</label>
                        <div class="col-sm-9">
                            <label class="checkbox-inline"><input type="radio" name="gender" value="true">Nam</label>
                            <label class="checkbox-inline"><input type="radio" name="gender" value="false">Nữ</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Email</label>
                        <div class="col-sm-9">
                            <input type="email" id="moduleIcon" name="email" class="form-control" pattern="^[\w\d_-]+@[\w\d-_]+(.[\w\d-_]+)+$" data-original-title-pattern="Định dạng email không hợp lệ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile" class="control-label col-sm-3">Số điện thoại</label>
                        <div class="col-sm-9">
                            <input type="text" id="mobile" name="mobile" class="form-control" pattern="^0|\+[\d]+[\d]+$" data-original-title-pattern="Định dạng số điện thoại không hợp lệ">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="taxCode" class="control-label col-sm-3">Mã số thuế cá nhân</label>
                        <div class="col-sm-9">
                            <input type="text" id="taxCode" name="taxCode" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="billingAddress" class="control-label col-sm-3">Địa chỉ ngân hàng</label>
                        <div class="col-sm-9">
                            <input type="text" id="billingAddress" name="billingAddress" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="address" class="control-label col-sm-3">Địa chỉ liên hệ</label>
                        <div class="col-sm-9">
                            <textarea name="address" id="address" rows="4" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary">Lưu</button>
                        <button type="reset" class="btn btn-danger">Nhập lai</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
