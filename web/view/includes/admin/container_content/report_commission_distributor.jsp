<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['COMISSION_DISTRIBUTOR_PAGINATION']}"/>
<c:set value="${f:findAdminById(sessionScope['ADMIN_ID'])}" var="ADMIN" />
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
                                    <li><a><label><input type="checkbox" value="lastName" />Tên NPP</label></a></li>
                                    <li><a><label><input type="checkbox" value="userName" checked />Username NPP</label></a></li>
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
                        <c:if test="${PAGINATION.exportable}">
                            <li>
                                <a class="btn btn-default btn-sm" href='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.EXPORT}/"/>'>
                                    Xuất Excel<i class="fa fa-download"></i>
                                </a>
                            </li>
                        </c:if>
                    </ul>
                    <div class="navbar-form navbar-right">
                        <c:if test="${ADMIN.roleAdmID.id!=2||ADMIN.provincialAgencyID.id==0}">
                            <div class="form-group form-group-sm">
                                <label class="control-label">Đại lý: </label>
                                <div class="input-group ">
                                    <select class="form-control change-time" controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/ChangeAgency/"/>'>                                        
                                        <option value="-1">-- Tất cả --</option>
                                        <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="provin">
                                            <option value="${provin.id}">${provin.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </c:if>
                        <div class="form-group form-group-sm">
                            <label class="control-label">Ngày bắt đầu: </label>
                            <div class="input-group datepicker">
                                <input type="text" name="startDate" readonly="" class="form-control change-time" controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_DAY}/0/"/>' />
                                <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                            </div>
                        </div>
                        <div class="form-group form-group-sm">
                            <label class="control-label">Ngày kết thúc: </label>                            
                            <div class="input-group datepicker">
                                <input type="text" name="endDate" readonly="" class="form-control change-time" controller='<c:url value="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.CHANGE_DAY}/1/"/>' />
                                <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                            </div>
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