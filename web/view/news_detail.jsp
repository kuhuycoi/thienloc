<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope['CUSTOMER_ID']==null}">
    <c:redirect url="/dang-nhap" />
</c:if>
<c:set var="CUSTOMER" value="${f:findDistributorById(sessionScope['CUSTOMER_ID'])}" />
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- Meta Keyword -->
        <meta name="keywords" content="">
        <meta property="og:description" content="${INDEX_NEWS.seoDescription}">
        <meta property="og:title" content="${INDEX_NEWS.seoTitle}">
        <meta property="og:url" content="TK.thienlocgroup.com/tin-tuc/${INDEX_NEWS.caId.id}/${INDEX_NEWS.seoPermalink}">
        <meta name="keywords" content="${INDEX_NEWS.seoKeyword}">
        <title>${INDEX_NEWS.name}</title>
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

        <!-- for old browser -->
        <script src='<c:url value="/resources/js/vendor/modernizr-2.6.2.min.js"/>'></script>
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <!-- main jQuery -->
        <script src='<c:url value="/resources/js/vendor/jquery-1.11.1.min.js"/>'></script>
        <script src='<c:url value="/resources/js/owl.carousel.min.js"/>'></script>
        <script src='<c:url value="/resources/js/jquery.magnific-popup.min.js"/>'></script>
    </head>
    <body>
        <div id="container">
            <div id="top-nav">
                <%@include file="includes/index/top-navigation.jsp" %>
            </div>
            <div id="header" style="position: relative">
                <%@include file="includes/index/header.jsp" %>
            </div>
            <div id="sticker">
                <%@include file="includes/index/sticker.jsp" %>
            </div>
            <%--<div id="banner">
                <%@include file="includes/index/banner.jsp" %>
            </div>--%>
            <div id="notice">
                <%@include file="includes/index/notice.jsp" %>
            </div>
            <div id="breadcrumb">
                <div class="container">
                    <div class="row">
                        <ol class="breadcrumb">
                            <li><i class="fa fa-home"></i></li>
                            <li><a href="/trang-chu">Trang chủ</a></li>
                            <li><a href="/tin-tuc/${INDEX_NEWS.caId.id}/trang-1">${INDEX_NEWS.caId.name}</a></li>
                            <li class="active">${INDEX_NEWS.name}</li>
                        </ol>
                    </div>
                </div>
            </div>
            <div id="content">
                <div class="container">
                    <div class="row">
                        <div id="ajax-nav" class="col-xs-3">
                            <%@include file="includes/index/ajax-navigation.jsp" %>
                        </div>
                        <div id="ajax-content" class="col-xs-9">
                            <%@include file="includes/index/news_detail_main_content.jsp" %>
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
