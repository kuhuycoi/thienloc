<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Cập nhật chuyên mục</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
            <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/News/CategoryNews/Edit">
                <input type="hidden" value="${NEWS_CATEGORY_EDIT.id}" name="id" />
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name" class="control-label col-sm-3">Tên chuyên mục <i>(*)</i></label>
                        <div class="col-sm-9">
                            <input type="text" required class="form-control" id="name" name="name" value="${NEWS_CATEGORY_EDIT.name}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Hiển thị</label>
                        <div class="col-sm-9">
                            <input type="checkbox" name="isShow" ${NEWS_CATEGORY_EDIT.isShow?'checked':''} />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Hiển thị trên menu</label>
                        <div class="col-sm-9">
                            <input type="checkbox" name="isShowOnMenu" ${NEWS_CATEGORY_EDIT.isShowOnMenu?'checked':''} />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="level" class="control-label col-sm-3">Thứ tự hiển thị</label>
                        <div class="col-sm-9">
                            <input type="number" name="level" min="1" value="${NEWS_CATEGORY_EDIT.level}" id="level" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3">Mô tả</label>
                        <div class="col-sm-9">
                            <textarea class="form-control"  name="description" rows="4">${NEWS_CATEGORY_EDIT.description}</textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">Thêm</button>
                    <button type="reset" class="btn btn-danger">Nhập lai</button>
                </div>
            </form>
        </div>
    </div>
</div>