<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <div class="img-logo col-xs-4">
            <a href="/trang-chu">
                <img src="<c:url value='/resources/img/logo.jpg'/>" alt="logo" />
            </a>
        </div>
        <c:if test="${sessionScope['CUSTOMER_ID']!=null}">
            <div class="col-xs-4">
                <div class="support">
                    <p class="col-xs-2"><i class="fa fa-phone"></i></p>
                    <ul class="col-xs-10">
                        <li>HỖ TRỢ: <small>(04).33111.666</small></li>
                        <li><span>Thứ 2 - Thứ 6: 9:00AM - 05:00PM</span></li>
                    </ul>
                </div>
            </div>
            <div class="col-xs-4">
                <div class="support">
                    <p class="col-xs-2"><i class="fa fa-envelope"></i></p>
                    <ul class="col-xs-10">
                        <li>EMAIL: <small>hotro.thienloc@gmail.com</small></li>
                        <li><span>Mọi ngày trong tuần (24/7)</span></li>
                    </ul>
                </div>
            </div>
        </c:if>
    </div>
</div>