<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1><a href="/tin-tuc/${INDEX_NEWS.caId.id}/trang-1">${INDEX_NEWS.caId.name}</a></h1>
<div class="panel">
    <div class="panel-heading clearfix">
        <h2>${INDEX_NEWS.name}</h2>
        <div class="news-time">
            <i class="fa fa-calendar"></i>
            <span>${f:formatTime(INDEX_NEWS.createdDate)}</span>
        </div>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <div class="news-detail-content text-justify">
            <div class="news-body">
                ${INDEX_NEWS.content}
            </div>
        </div>
    </div>
</div><!-- end panel-->
<style>
    #ajax-content .panel .panel-heading{
        border-bottom: 1px solid #e5e5e5;
        margin: 10px;
    }
    #ajax-content .panel .panel-heading h2{
        margin-bottom: 0;
        display: inline-block;
        width: auto;
        border: none;
        float: left;
		max-width:calc(100% - 200px);
		line-height:24px;
    }
    #ajax-content .panel .panel-heading .news-time{
        font-size: 14px;
        display: inline-block;
        float: right;
		max-width:200px;
    }
</style>