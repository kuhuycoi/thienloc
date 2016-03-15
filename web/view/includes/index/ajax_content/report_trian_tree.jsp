<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Niềm tin chiến thắng</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Hệ thống niềm tin chiến thắng</h2>
    </div>
    <div class="panel-body">

        <c:if test="${TREE_TRIAN==null}">
            <div class="alert alert-danger">Bạn chưa tham gia chương trình "Niềm tin chiến thắng". Vui lòng click vào <a class="btn-change-content text-primary" controller="<c:url value="/Report/Trian/ViewInsert" />" title="Tham gia tri ân">đây</a> để tham gia!</div>
        </c:if>
        <c:if test="${TREE_TRIAN!=null}">
            <div class="text-danger" style="font-size: 24px; margin-bottom: 20px;"><i>Lưu ý: Chụp lại kết quả màn hình để giải quyết tranh chấp</i></div>
            <div id="treeDiagram">
                ${TREE_TRIAN}
            </div>
        </c:if>
    </div>
</div>