<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lgs">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Thêm mới tin tức</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
            <form id="form-insert" class="form-insert form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/News/ListPost/Update">

                <input type="hidden" id="id" name="id" value="${NEWS.id}" />
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name" class="control-label col-sm-2">Tên bài viết <i>(*)</i></label>
                        <div class="col-sm-10">
                            <input type="text" required class="form-control" id="name" name="name" value="${NEWS.name}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="seoPermalink" class="control-label col-sm-2">SEO Permalink</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="seoPermalink" name="seoPermalink" value="${NEWS.seoPermalink}"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="seoDescription" class="control-label col-sm-2">SEO Description</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="seoDescription" name="seoDescription" value="${NEWS.seoDescription}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="seoTitle" class="control-label col-sm-2">SEO Title</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="seoTitle" name="seoTitle" value="${NEWS.seoTitle}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="seoKeyword" class="control-label col-sm-2">SEO Keyword</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="seoKeyword" name="seoKeyword" value="${NEWS.seoKeyword}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="seoMeta" class="control-label col-sm-2">SEO Meta</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="seoMeta" name="seoMeta" value="${NEWS.seoMeta}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tags" class="control-label col-sm-2">Tags</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="tags" name="tags" value="${NEWS.tags}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Hình đại diện</label>
                        <div class="col-sm-10">
                            <button type="button" class="btn btn-default" id="btn-finder" data-target="#titleImg">Chọn hình đại diện</button><br><br>
                            <input type="text" readonly name="titleImg" class="form-control" id="titleImg" value="${NEWS.titleImg}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Chuyên mục</label>
                        <div class="col-sm-10">
                            <select name="caId" class="form-control">
                                <c:forEach items="${f:getNewsCategoryDropdown()}" var="ca">
                                    <option value="${ca.id}" ${ca.id==NEWS.caId.id?"selected":""}>${ca.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Hiển thị</label>
                        <div class="col-sm-10">
                            <input type="checkbox" name="isShow" ${NEWS.isShow?"checked":""} />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Nổi bật</label>
                        <div class="col-sm-10">
                            <input type="checkbox" name="isHot" ${NEWS.isHot?"checked":""} />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="orderNumber" class="control-label col-sm-2">Thứ tự</label>
                        <div class="col-sm-10">
                            <input type="number" name="orderNumber" min="1" value="${NEWS.orderNumber}" id="orderNumber" class="form-control" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="shortDescription" class="control-label col-sm-2">Mô tả</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="shortDescription" name="shortDescription" value="${NEWS.shortDescription}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2">Nội dung</label>
                        <div class="col-sm-10">
                            <textarea class="ckeditor" required id="ckeditor-content" name="content">${NEWS.content}</textarea>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="isDelete" value="${NEWS.isDelete}" />
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
    CKEDITOR.replace('ckeditor-content', {
        filebrowserBrowseUrl: '/ckfinder/ckfinder.html',
        filebrowserImageBrowseUrl: '/ckfinder/ckfinder.html?Type=Images',
        filebrowserFlashBrowseUrl: '/ckfinder/ckfinder.html?Type=Flash',
        filebrowserUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
        filebrowserImageUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
        filebrowserFlashUploadUrl: '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
        language: 'vi',
        height: 500
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