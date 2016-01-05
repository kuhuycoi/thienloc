<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Thêm mới module</h3>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="<c:url value='/Admin/Module/ListModule/Edit'/>">
                    <input type="hidden" name="id" value="${MODULE_EDIT.id}">
                    <div class="form-group">
                        <label class="control-label col-sm-3">Tên Module</label>
                        <div class="col-sm-9">
                            <input type="text" id="name" required name="name" value="${MODULE_EDIT.name}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Controller</label>
                        <div class="col-sm-9">
                            <input type="text" id="controller" required name="controller" value="${MODULE_EDIT.controller}" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Icon class</label>
                        <div class="col-sm-9">
                            <input type="text" id="icon" name="icon" class="form-control" value="${MODULE_EDIT.icon}" placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Css Class</label>
                        <div class="col-sm-9">
                            <input type="text" id="cssClass" name="cssClass" class="form-control" value="${MODULE_EDIT.cssClass}" placeholder="">
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                        <button type="reset" class="btn btn-danger">Nhập lai</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
