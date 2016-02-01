<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Tri ân</h1>
<div class="panel">
    <div class="panel-heading clearfix">
        <h2>Tham gia chương trình tri ân</h2>
        <div class="news-time">
            <i class="fa fa-calendar"></i>
            <span id="display-trian-time"></span>
        </div>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <input type="hidden" id="trianTime" value="${f:getTrianTime(applicationScope['MAIN_PROPERTIES_FILE_PATH'])}" />
        <form class="form-insert" novalidate method="post" action='<c:url value="/Report/Trian/Insert"/>'>
            <div class="row">
                <div class="col-xs-3">
                    <label>Mã PIN</label>
                </div>
                <div class="col-xs-9">
                    <input type="text" name="pin" class="form-control" />
                </div>
            </div> 
            <div class="row">
                <div class="col-xs-offset-3 col-xs-9">
                    <img id="captcha_id" src="/captcha.jpg"/> 
                    <a href="javascript:;" title="change captcha text" onclick="document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random();  return false">
                        <i class="fa fa-refresh"></i>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-3">
                    <label>Nhập capcha</label>
                </div>
                <div class="col-sm-6 col-md-9 col-xs-12">
                    <input type="text" name="captcha" required class="form-control" />
                </div>
            </div>
            <div class="buttonBar">
                <button type="submit" class="btn btn-default">
                    Gửi
                </button>
                <button type="reset" class="btn btn-default" onclick="document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random();  return false">
                    Nhập lại
                </button>
            </div>
        </form>
    </div>
</div>
<script>
    var trianTime = new Date(parseInt($('#trianTime').val()));
    setInterval(function () {
        var dateFuture = trianTime;
        var dateNow = new Date();

        var seconds = Math.floor((dateFuture - (dateNow)) / 1000);
        var minutes = Math.floor(seconds / 60);
        var hours = Math.floor(minutes / 60);
        var days = Math.floor(hours / 24);

        hours = hours - (days * 24);
        minutes = minutes - (days * 24 * 60) - (hours * 60);
        seconds = seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
        $('#display-trian-time').html("Thời gian tri ân sẽ bắt đầu sau: " + days + " ngày " + hours + " giờ " + minutes + " phút " + seconds + " giây");
    }, 1000);
</script>