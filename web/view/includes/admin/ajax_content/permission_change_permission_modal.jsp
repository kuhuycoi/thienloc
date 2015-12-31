<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="modal modal-insert-customer" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title">
                    <div class="content-title">
                        <h3 class="text-center">Phân quyền quản trị viên</h3>
                    </div>
                </div>
            </div>
            <div class="modal-body">
                <form class="change-permission-form" action="<c:url value="/Admin/Permission/RoleAdmin/ChangePermission/${CURRENT_ROLE}" />">
                    <div class="module-role-list">
                        <ul>
                            <li>
                                <label>
                                    <span class="module-checkbox"><input type="checkbox" class="external" value="" disabled=""/></span>
                                    <span>ROOT</span>
                                </label>
                                <ul>
                                    <c:forEach items="${MODULES}" var="MODULE"> 
                                        <c:if test="${!MODULE.isDeleted}">
                                            <li>
                                                <label>
                                                    <span class="module-checkbox"><input type="checkbox" value="${MODULE.id}" ${f:checkModuleInrole(MODULE_IN_ROLES_CHECK,MODULE.id)?'checked':''} /></span>
                                                    <span>${MODULE.name}</span>
                                                </label> 
                                                <ul>                                                    
                                                    <c:forEach items="${MODULE.modules}" var="childrenModule">
                                                        <c:if test="${!childrenModule.isDeleted}">
                                                            <li>
                                                                <label>
                                                                    <span class="module-checkbox"><input type="checkbox" value="${childrenModule.id}" ${f:checkModuleInrole(MODULE_IN_ROLES_CHECK,childrenModule.id)?'checked':''} /></span>
                                                                    <span>${childrenModule.name}</span>
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
                    <div class="module-role-action text-right">
                        <button class="btn btn-primary btn-change-permission" type="button">Phân quyền</button>
                        <button class="btn btn-danger" type="reset">Hủy</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('li>label input[type=checkbox]').on('change', function () {
            if ($(this).is(':checked')) {
                $(this).parents('label').parent('li').children('ul').find('input[type=checkbox]').prop('checked', true);
            } else {
                $(this).parents('label').parent('li').children('ul').find('input[type=checkbox]').prop('checked', false);
            }
        });

    });
</script>