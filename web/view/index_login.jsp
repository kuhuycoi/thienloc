<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope['CUSTOMER_ID']!=null}">
    <c:redirect url="/trang-chu" />
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Meta Keyword -->
        <meta name="keywords" content="">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <title>Đăng nhập hệ thống Thiên Lộc Group</title>
        <link rel="shortcut icon" href='<c:url value="/resources/img/web-icon.jpg"/>'>
        <link rel="stylesheet" href="<c:url value="/resources/css/font-open-sans.css"/>" type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/font-awesome.min.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/themify-icons.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/latofonts.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap.min.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-dialog.min.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/bootstrap-datepicker3.min.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/animate.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/nprogress.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/reset-css.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/message.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/main_index.css"/>' type="text/css">
        <link rel="stylesheet" href='<c:url value="/resources/css/responsive_index.css"/>' type="text/css">
    </head>
    <body>
        <div id="container">
            <div id="top-nav">
                <%@include file="includes/index/top-navigation.jsp" %>
            </div>
            <div id="header">
                <%@include file="includes/index/header.jsp" %>
            </div>
            <div id="sticker">
                <%@include file="includes/index/sticker.jsp" %>
            </div>
            <%--<div id="banner">
                <%@include file="includes/index/banner.jsp" %>
            </div>--%>
            <div id="content">
                <div class="container">
                    <div class="row">
                        <div id="ajax-content" class="col-xs-12">
                            <%@include file="includes/index/ajax_content/login.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
            <div id="footer">
                <%@include file="includes/index/footer.jsp" %>
            </div>      
            <a id="gotoTop"></a>
            <div id="ajax-loading" class="hidden">
                <div class="progress">
                    <div class="progress-bar" role="progressbar" style="width: 0">
                    </div>
                </div>
            </div>
        </div>
        <!-- for old browser -->
        <script src='<c:url value="/resources/js/vendor/modernizr-2.6.2.min.js"/>'></script>
        <!-- main jQuery -->
        <script src='<c:url value="/resources/js/vendor/jquery-1.11.1.min.js"/>'></script>
        <!-- Bootstrap -->
        <script src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
        <!-- Bootbox -->
        <script src='<c:url value="/resources/js/bootstrap-dialog.min.js"/>'></script>
        <!-- date picker -->
        <script src='<c:url value="/resources/js/bootstrap-datepicker.min.js"/>'></script>
        <script src='<c:url value="/resources/js/bootstrap-datepicker.vi.min.js"/>'></script>
        <!-- Calendar -->
        <script src='<c:url value="/resources/js/jquery.calendar.js"/>'></script>
        <!-- MD5 script -->    
        <script src='<c:url value="/resources/js/jquery.md5.js"/>'></script>
        <!-- WOW script -->
        <script src='<c:url value="/resources/js/wow.min.js"/>'></script>
        <!-- ajax progress -->
        <script src='<c:url value="/resources/js/nprogress.js"/>'></script>
        <!-- input mask -->
        <script src='<c:url value="/resources/js/jquery.mask.min.js"/>'></script>
        <!-- draw diagram -->
        <script src='<c:url value="/resources/js/go.js"/>'></script>
        <!-- format number -->
        <script src='<c:url value="/resources/js/jquery.number.min.js"/>'></script>
        <!-- theme custom scripts -->
        <script src='<c:url value="/resources/js/main_index.js"/>'></script>
    </body>
</html>
