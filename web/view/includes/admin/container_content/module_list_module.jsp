<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['LIST_MODULE_PAGINATION']}"/>
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
                                    <li><a><label><input type="checkbox" value="module2_.name" />Tên module</label></a></li> 
                                    <li><a><label><input type="checkbox" value="action3_.actionName" />Tên action</label></a></li> 
                                    <li><a><label><input type="checkbox" value="admin1_.userName" />Admin</label></a></li>                       
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
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>
    </div><!-- end panel heading -->
    <div class="ajax-content panel-body">
        <div class="module-role-list">
            <ul>
                <li>
                    <label>
                        <span class="module-collapse"><i class="fa fa-minus-square-o"></i></span>
                        <span>ROOT</span>
                        <ul class="module-action">
                            <li><a class="btn-open-modal text-success" data-toggle="tooltip" data-placement="top" title="Thêm mới module con cho module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_INSERT}/1"><i class="fa fa-plus-circle"></i></a></li>
                        </ul>
                    </label>
                    <ul>
                        <c:forEach items="${MODULES}" var="MODULE"> 
                            <c:if test="${!MODULE.isDeleted}">
                                <li>
                                    <label>
                                        <span class="module-collapse"><i class="fa fa-minus-square-o"></i></span>
                                        <span>${MODULE.name}</span>
                                        <ul class="module-action">
                                            <li><a class="btn-open-modal text-success" data-toggle="tooltip" data-placement="top" title="Thêm mới module con cho module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_INSERT}/${MODULE.id}"><i class="fa fa-plus-circle"></i></a></li>
                                            <li><a class="btn-open-modal text-primary" data-toggle="tooltip" data-placement="top" title="Cập nhật module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_EDIT}/${MODULE.id}"><i class="fa fa-edit"></i></a></li>
                                            <li><a class="text-warning btn-send-ajax" data-toggle="tooltip" data-placement="top" title="${MODULE.isShow?'Khóa':'Mở Khóa'} module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/ChangeStatus/${MODULE.id}"><i class="fa ${MODULE.isShow?'fa-lock':'fa-unlock'}"></i></a></li>
                                            <li><a class="text-danger btn-send-ajax" data-toggle="tooltip" data-placement="top" title="Xóa module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.DELETE}/${MODULE.id}"><i class="fa fa-trash-o"></i></a></li>
                                        </ul>
                                    </label> 
                                    <ul>                                                    
                                        <c:forEach items="${MODULE.modules}" var="childrenModule">
                                            <c:if test="${!childrenModule.isDeleted}">
                                                <li>
                                                    <label>
                                                        <span>${childrenModule.name}</span>                                                        
                                                        <ul class="module-action">
                                                            <li><a class="btn-open-modal text-primary" data-toggle="tooltip" data-placement="top" title="Cập nhật module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.VIEW_EDIT}/${childrenModule.id}"><i class="fa fa-edit"></i></a></li>
                                                            <li><a class="text-warning btn-send-ajax" data-toggle="tooltip" data-placement="top" title="${childrenModule.isShow?'Khóa':'Mở Khóa'} module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/ChangeStatus/${childrenModule.id}"><i class="fa ${childrenModule.isShow?'fa-lock':'fa-unlock'}"></i></a></li>
                                                            <li><a class="text-danger btn-send-ajax" data-toggle="tooltip" data-placement="top" title="Xóa module này" controller="${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}${PAGINATION.DELETE}/${childrenModule.id}"><i class="fa fa-trash-o"></i></a></li>
                                                        </ul>
                                                    </label>
                                                </li>                                                            
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>
                        </c:forEach>                                    
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div><!-- end panel-->

<script>
    $('.module-collapse').on('click',function(){
        if($(this).children('.fa').hasClass('fa-minus-square-o')){
            $(this).children('.fa').removeClass('fa-minus-square-o').addClass('fa-plus-square-o');
        }else{
            $(this).children('.fa').removeClass('fa-plus-square-o').addClass('fa-minus-square-o');
        }
        $(this).parent('label').parent('li').children('ul').stop().toggle(300);
    });
</script>