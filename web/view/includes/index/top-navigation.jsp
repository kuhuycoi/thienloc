<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:if test="${sessionScope['CUSTOMER_ID']!=null}">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <ul class="menu-top menu-left clearfix">
                    <c:if test="${sessionScope['CUSTOMER_ID']!=null}">
                        <li>
                            <a href="<c:url value='/Home'/>">Xin chào, ${CUSTOMER.lastName}!</a>
                        </li>
                        <li>
                            <a href="<c:url value='/thoat'/>">Thoát</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope['CUSTOMER_ID']==null}">
                        <li>
                            <a href="<c:url value='/dang-nhap'/>">Đăng nhập</a>
                        </li>
                        <li>
                            <a href="<c:url value='/dang-ky'/>">Đăng ký</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</c:if>