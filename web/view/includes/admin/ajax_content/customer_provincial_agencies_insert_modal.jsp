<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Thêm mới đại lý</h3>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="<c:url value='/Admin/Customer/ProvincialAgencies/Insert'/>">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Tên đại lý</label>
                        <div class="col-sm-9">
                            <input type="text" id="name" required name="name" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Địa chỉ</label>
                        <div class="col-sm-9">
                            <textarea class="form-control" name="address"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Số ĐT</label>
                        <div class="col-sm-9">
                            <input type="text" id="mobile" name="mobile" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Fax</label>
                        <div class="col-sm-9">
                            <input type="text" id="fax" name="fax" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Email</label>
                        <div class="col-sm-9">
                            <input type="email" id="email" name="email" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Hiển thị</label>
                        <div class="col-sm-9">
                            <input type="checkbox" id="isShow" checked name="isShow">
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary">Thêm</button>
                        <button type="reset" class="btn btn-danger">Nhập lai</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
