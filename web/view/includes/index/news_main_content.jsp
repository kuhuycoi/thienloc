<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Tin tức</h1>
<div class="panel">
    <div class="panel-heading clearfix">
        <h2>${INDEX_NEWS_CATEGORY.name}</h2>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <div class="news-content">

            <c:set var="PAGINATION" value="${sessionScope['INDEX_NEWS_PAGINATION']}"/>
            <c:if test="${f:size(PAGINATION.displayList)==0}">
                <div class="alert alert-danger">
                    Không có kết quả nào được hiển thị
                </div>
            </c:if>
            <c:if test="${f:size(PAGINATION.displayList)!=0}">
                <div class="news-list">
                    <c:forEach items="${PAGINATION.displayList}" var="news">
                        <div class="news-item">
                            <div class="row">                                
                                <div class="col-sm-3 col-xs-12">
                                    <div class="news-img">
                                        <a href="/tin-tuc/${INDEX_NEWS_CATEGORY.id}/${news.seoPermalink}" title="${news.name}">
                                            <c:if test="${news.titleImg==null}">
                                                <c:if test="${INDEX_NEWS_CATEGORY.id==1}">
                                                    <img src="/resources/img/no_image.jpg" />
                                                </c:if>
                                                <c:if test="${INDEX_NEWS_CATEGORY.id==2}">
                                                    <img src="/resources/img/thongbao.jpg" />
                                                </c:if>
                                            </c:if>
                                            <c:if test="${news.titleImg!=null}">
                                                <img src="${news.titleImg}" />
                                            </c:if>
                                        </a>
                                    </div>
                                </div>
                                <div class="col-sm-9 col-xs-12">
                                    <div class="news-description">
                                        <h3 class="news-title"><a href="/tin-tuc/${INDEX_NEWS_CATEGORY.id}/${news.seoPermalink}" title="${news.name}">${news.name}</a></h3>
                                        <div class="news-time"><i class="fa fa-calendar"></i> ${f:formatDate(news.createdDate)}</div>
                                        <div class="news-short-description">
                                            <p>${news.shortDescription}</p>
                                        </div>
                                        <div class="clearfix text-right news-detail">
                                            <a href="/tin-tuc/${INDEX_NEWS_CATEGORY.id}/${news.seoPermalink}"><i class="fa fa-caret-right"></i> Xem chi tiết</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="pagination-wrapper">
                <ul class="pagination pagination-sm">
                    <c:if test="${PAGINATION.currentPage>1}">
                        <li>
                            <a href="/tin-tuc/1/trang-1"><span class="fa fa-angle-double-left"></span></a>
                        </li>
                        <li>
                            <a href="/tin-tuc/1/trang-${PAGINATION.currentPage-1}"><span class="fa fa-angle-left"></span></a>
                        </li>
                    </c:if>
                    <c:forEach begin="${PAGINATION.currentPage-2<1?1:PAGINATION.currentPage-2}" end="${PAGINATION.currentPage-1}" var="count">
                        <li>
                            <a href="/tin-tuc/1/trang-${count}">${count}</a>
                        </li>
                    </c:forEach>
                    <li class="active">
                        <a href="javascript:;">${PAGINATION.currentPage}</a>
                    </li>
                    <c:forEach begin="${PAGINATION.currentPage+1}" end="${PAGINATION.currentPage+2>PAGINATION.totalPage?PAGINATION.totalPage:PAGINATION.currentPage+2}" var="count">
                        <li>
                            <a href="/tin-tuc/1/trang-${count}">${count}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${PAGINATION.currentPage<PAGINATION.totalPage}">
                        <li>
                            <a href="/tin-tuc/1/trang-${PAGINATION.currentPage+1}"><span class="fa fa-angle-right"></span></a>
                        </li>
                        <li>
                            <a href="/tin-tuc/1/trang-${PAGINATION.totalPage}"><span class="fa fa-angle-double-right"></span></a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
</div><!-- end panel-->
<style>
    .news-item{
        margin-bottom: 10px;
        padding-bottom: 10px;
        border-bottom: 1px dashed #DDD
    }
    .news-img{
        border: 1px solid #DDD
    }
    .news-img a{
        display: block;
    }
    .news-img a img{
        width: 100%;
    }
    .news-item .news-title{
        margin-bottom: 10px;
    }
    .news-item .news-title a{
        color: #3498db;
        font-size: 16px;
        line-height: 21px;
        display: block;
        max-height: 39px;
        overflow: hidden;
        text-overflow:ellipsis;
        text-align: justify
    }
    .news-time{
        font-size: 13px;
        color: #777
    }
    .news-detail{
        margin-top: 20px;
        font-size: 13px;
        color: #000;
    }

    .pagination-wrapper {
        border-top: 1px solid #c1beb3;
        padding-top: 20px;
    }

    .pagination {
        display: table;
        margin: 0 auto;
        width: auto;
    }

    .pagination > li > a, .pagination > li > span {
        background-color: transparent !important;
        border: 1px solid #ae9855 !important;
        color: #1d1d1d !important;
    }

    .pagination > li {
        display: inline-block;
        float: left;
        margin-right: 4px;
    }

    .pagination > .active > a, .pagination > .active > span, .pagination > .active > a:hover, .pagination > .active > span:hover, .pagination > .active > a:focus, .pagination > .active > span:focus {
        border-color: #8b0304 !important;
    }

    .pagination-sm > li:first-child > a, .pagination-sm > li:first-child > span, .pagination-sm > li:last-child > a, .pagination-sm > li:last-child > span {
        border-radius: 0;
    }
</style>