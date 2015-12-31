<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <h4 class="modal-title">Gửi yêu cầu kích hoạt NPP</h4>
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                </div>
            </div>                           
            <form id="form-insert" confirm="true" class="form-insert-normal form-horizontal" novalidate method="POST" action="${pageContext.servletContext.contextPath}/Admin/History/NeverUpRank/InsertMultiple">
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-3">
                            <label class="control-label">Gói PV</label>
                        </div>
                        <div class="col-sm-9">
                            <select class="form-control text-center" name="rankCustomerId">
                                <c:forEach items="${f:findAllAvailableRankCustomer()}" var="rank">
                                    <option value='${rank.id}'>${rank.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">Gửi yêu cầu</button>
                    <button type="reset" class="btn btn-danger">Nhập lai</button>
                </div>
            </form>
        </div>
    </div>
</div>