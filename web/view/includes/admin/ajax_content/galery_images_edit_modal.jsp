<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Thêm mới ảnh</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
            <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/Galery/Images/Edit">
                <input type="hidden" value="${IMAGES.id}" name="id" />
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name" class="control-label col-sm-2">Tên Ảnh <i>(*)</i></label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" id="name" name="name" value="${IMAGES.name}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Album</label>
                        <div class="col-sm-10">
                            <select name="galeryId" class="form-control">
                                <c:forEach items="${f:findAllGaleryCategory()}" var="galery">
                                    <option value='${galery.id}' ${IMAGES.galeryId.id==galery.id?'selected':''}>${galery.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Đường dẫn</label>
                        <div class="col-sm-10">
                            <button type="button" class="btn btn-default" id="btn-finder" data-target="#path">Chọn ảnh</button><br><br>
                            <input type="text" readonly name="path" class="form-control" id="path" value="${IMAGES.path}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Hiển thị</label>
                        <div class="col-sm-10">
                            <input type="checkbox" name="isShow" checked="true" ${IMAGES.isShow?'checked':''} />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="orderNumber" class="control-label col-sm-2">Thứ tự</label>
                        <div class="col-sm-10">
                            <input type="number" name="orderNumber" min="1" value="${IMAGES.orderNumber}" id="orderNumber" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="description" class="control-label col-sm-2">Mô tả</label>
                        <div class="col-sm-10">
                            <textarea type="text" class="form-control" id="description" name="description" rows="5">${IMAGES.description}</textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">Cập nhật</button>
                    <button type="reset" class="btn btn-danger">Nhập lai</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(document).on('focusin', function (e) {
        e.stopImmediatePropagation();
    });
    $(document).on("click", "#btn-finder", function () {
        var target = $(this).data('target');
        var finder = new CKFinder({
            filebrowserBrowseUrl: '/ckfinder/ckfinder.html',
            filebrowserImageBrowseUrl: '/ckfinder/ckfinder.html?Type=Images',
            filebrowserFlashBrowseUrl: '/ckfinder/ckfinder.html?Type=Flash',
            filebrowserUploadUrl: '/ckfinder/core/connector/java/connector.aspx?command=QuickUpload&type=Files',
            filebrowserImageUploadUrl: '/ckfinder/core/connector/java/connector.aspx?command=QuickUpload&type=Images',
            filebrowserFlashUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images'
        });
        finder.selectActionFunction = function (fileUrl) {
            $(target).val(fileUrl);
        };
        finder.popup();
    });
</script>