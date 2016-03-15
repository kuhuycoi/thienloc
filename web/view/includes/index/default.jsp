<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="news-block-list">
    <div class="row">
        <div class="col-sm-6 col-xs-12">
            <div class="news-block">
                <div class="news-block-title">
                    <h3 style="font-weight:700; color:#2c87f0;"><i class="fa fa-info"></i> Tin giới thiệu</h3>
                </div>
                <ul class="news-list">
                    <c:set value="${f:getListPost(1,0,10)}" var="newsList" />
                    <c:forEach items="${newsList}" var="news">
                        <li>
                            <i class="fa fa-arrow-circle-right"></i><a href="<c:url value="/tin-tuc/${news.caId.id}/${news.seoPermalink}"/>" title="${news.seoTitle}">${news.name}</a>
                        </li>
                    </c:forEach>
                    <li class="btn-read-all"><a href="/tin-tuc/1/trang-1">Xem tất cả..</a></li>
                </ul>
            </div>
        </div>
        <div class="col-sm-6 col-xs-12">
            <div class="news-block">
                <div class="news-block-title">
                    <h3 style="font-weight:700; color:#2c87f0;"><i class="fa fa-calendar"></i> Thông báo <img src="/resources/img/icon_new.gif"></h3>
                </div>
                <ul class="news-list">
                    <ul class="news-list">
                        <c:set value="${f:getListPost(2,0,10)}" var="newsList" />
                        <c:forEach items="${newsList}" var="news">
                            <li>
                                <i class="fa fa-arrow-circle-right"></i><a href="<c:url value="/tin-tuc/${news.caId.id}/${news.seoPermalink}"/>" title="${news.seoTitle}">${news.name}</a>
                            </li>
                        </c:forEach>
                        <li class="btn-read-all"><a href="/tin-tuc/2/trang-1">Xem tất cả..</a></li>
                    </ul>
                </ul>
            </div>
        </div>
    </div>
</div>