<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <div class="row">
        <ul class="sticker-menu clearfix">
            <li><a href="/trang-chu">Trang chủ</a></li>
                <c:if test="${sessionScope['CUSTOMER_ID']==null}">            
                <li>
                    <a href="<c:url value='/dang-nhap'/>">Đăng nhập</a>
                </li>
                <li>
                    <a href="<c:url value='/dang-ky'/>">Đăng ký</a>
                </li>
            </c:if>
            <c:if test="${sessionScope['CUSTOMER_ID']!=null}">
                <li><a href="#">Giới thiệu</a></li> 
                <li><a href="#">Tin tức</a></li> 
                <li><a href="#">Sản phẩm</a></li> 
                <li><a href="#">Tuyển dụng</a></li> 
                <li><a href="#">Liên hệ</a></li>  
                </c:if>
        </ul>
    </div>
</div>