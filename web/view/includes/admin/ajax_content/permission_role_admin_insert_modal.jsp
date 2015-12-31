<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Thêm mới quyền quản trị</h3>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="<c:url value='/Admin/Permission/RoleAdmin/Insert'/>">
                    <div class="form-group">
                        <label for="name" class="control-label col-sm-3">Tên quyền</label>
                        <div class="col-sm-9">
                            <input type="text" id="name" required name="name" class="form-control" placeholder="">
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
