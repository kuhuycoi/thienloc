<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['NOT_USED_PIN_SYS_PAGINATION']}"/>
<input type="hidden" id="reloadController" value="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}'/>">
<div class="content-title">
    <h3 class="text-center">${PAGINATION.viewTitle}</h3>
</div>
<div class="panel panel-default">
    <div class="panel-heading">
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>                       
                    <a type="button" class="navbar-brand btn-reload-content" data-toggle="tooltip" data-placement="bottom" data-title="Tải lại nội dung hiển thị">
                        <i class="fa fa-refresh"></i>
                    </a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <form class="form-search navbar-form navbar-left" novalidate method="post" action='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.SEARCH}"/>'>
                        <div class="input-group">
                            <div class="input-group-btn" data-toggle="tooltip" data-placement="left" data-title="Lựa chọn các trường tìm kiếm">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" tabindex="-1">
                                    <span class="caret"></span>
                                    <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <ul class="dropdown-menu dropdown-select dropdown-menu-left">
                                    <li><a><label><input type="checkbox" value="this_.id" />ID</label></a></li>
                                    <li><a><label><input type="checkbox" value="this_.pinNumber" />Mã PIN</label></a></li>
                                    <li><a><label><input type="checkbox" value="admin1_.Username" checked />Username Admin</label></a></li>
                                    <li class="divider"></li>
                                    <li><a><label><input class="select-all" type="checkbox" />Tất Cả</label></a></li>
                                </ul>
                            </div>
                            <input type="text" class="form-control" name="searchString" placeholder="Nhập từ khóa ..." />
                            <div class="input-group-btn">
                                <button type="submit" class="btn btn-default">                                
                                    <span class="fa fa-search"></span>
                                </button>
                            </div>
                        </div>
                    </form>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a class="btn-open-modal btn-sm btn btn-default" controller="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_INSERT}'/>">Thêm mới <i class="fa fa-barcode"></i></a></li>
                        <li><a href="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.EXPORT}'/>" class="btn-sm btn btn-default">Xuất excel <i class="fa fa-download"></i></a></li>
                    </ul>
                    <div class="navbar-form navbar-right">
                        <div class="form-group">
                            <label class="control-label">Ngày tạo: </label>
                            <select class="form-control change-date change-day" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_DATE}/"/>">
                                <option value="-1" ${PAGINATION.day==null?'selected':''}>-- Tất cả --</option>
                                <c:forEach items="${f:findAllPinSysCreatedDay()}" var="day">
                                    <option value="${f:parseDateToLong(day)}" ${PAGINATION.day==day?'selected':''}>${day}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="navbar-form navbar-left">
                        <div class="form-group">
                            <label class="control-label">Kiểu pin: </label>
                            <select class="form-control change-day change-date" controller="<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_PIN_TYPE}/"/>">
                                <option value="-1" ${PAGINATION.pinType==null?'selected':''}>-- Tất cả --</option>
                                <c:forEach items="${f:findAllRankCustomersHavingPinSys()}" var="pinType">
                                    <option value="${pinType.id}" ${PAGINATION.pinType==pinType.id?'selected':''}>${pinType.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div><!-- end panel heading -->
    <div class="ajax-content panel-body">    
        <c:import url="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.GO_TO}/${PAGINATION.currentPage}"/>
    </div>
</div><!-- end panel-->