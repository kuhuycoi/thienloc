<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>niềm tin chiến thắng</h1>
<div class="panel">
    <div class="panel-heading clearfix">
        <h2>Tham gia chương trình "niềm tin chiến thắng"</h2>
        <div class="news-time text-danger" style="font-size: 24px; margin-bottom: 20px">
            <i class="fa fa-calendar"></i> <text id="display-trian-time"></text>
        </div>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <input type="hidden" id="trianTime" value="${f:getTrianTime(applicationScope['MAIN_PROPERTIES_FILE_PATH'])}" />
        <form id="form-insert-trian" novalidate method="post" action='<c:url value="/Report/Trian/Insert"/>'>
            <fieldset disabled>
                <div class="row">
                    <div class="col-xs-3">
                        <label>Mã PIN</label>
                    </div>
                    <div class="col-xs-6">
                        <input type="text" name="pinCode" class="form-control pinCode" required />
                    </div>
                </div> 
                <div class="row">
                    <div class="col-xs-offset-3 col-xs-9">
                        <img id="captcha_id" src="/Captcha/Trian/1"/> 
                        <a href="javascript:;" id="btn-reload-captcha" class="btn" style="color: #000 !important" title="change captcha text" onclick="document.getElementById('captcha_id').src = '/Captcha/Trian/' + Math.random(); return false">
                            <i class="fa fa-refresh"></i>
                        </a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-3">
                        <label>Nhập capcha</label>
                    </div>
                    <div class="col-md-6 col-sm-9 col-xs-12">
                        <input type="text" name="captcha" required class="form-control" maxlength="6" />
                    </div>
                </div>
                <div class="buttonBar">
                    <button type="submit" class="btn btn-default btn-submit-trian">
                        Gửi
                    </button>
                    <button type="reset" class="btn btn-default" onclick="document.getElementById('captcha_id').src = 'captcha.jpg?' + Math.random(); return false">
                        Nhập lại
                    </button>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<script>
    var trianTime = new Date(parseInt($('#trianTime').val()));
    $('#display-trian-time').countdown(trianTime).on('update.countdown', function (event) {
        var $this = $(this).html(event.strftime('Bắt đầu sau: %D Ngày %H Giờ %M Phút %S Giây'));
    }).on('finish.countdown', function (event) {
        $(this).html('Đã đến thời gian tham gia chương trình!');
        $('#form-insert-trian fieldset').removeProp("disabled");
    });
    $(document).on('submit', '#form-insert-trian', function (e) {
        e.preventDefault();
          $('#form-insert-trian fieldset').prop("disabled", "disabled");
        var error = $(this).validate();
        if (!error && typeof $(this).attr('action') !== 'undefined') {
            var customer = JSON.stringify($(this).serializeObject());
            var url = $(this).attr('action');
            sendAjaxWithJsonObj(url, 'POST', customer, function (data) {
                  $('#form-insert-trian fieldset').removeProp("disabled");
                openMessage(data);
                $('#btn-reload-captcha').trigger("click");
                $('input[name="captcha"]').val('');
            });
        }
    });
//    $(document).on('submit', '#form-insert-trian', function (e) {
//        e.preventDefault();
//        var error = $(this).validate();
//        if (!error && typeof $(this).attr('action') !== 'undefined') {
//            var customer = JSON.stringify($(this).serializeObject());
//            var url = $(this).attr('action');
//            for (var i = 0; i < 10000; i++) {
//                $.ajax({
//                    beforeSend: function () {
//                        NProgress.set(0.5);
//                    },
//                    url: url,
//                    type: 'POST',
//                    data: customer,
//                    contentType: 'application/json',
//                    success: function (data) {
//                        NProgress.done();
//                    }, error: function () {
//                        NProgress.done();
//                    }
//                });
//            }
//        }
//    });
    $('.pinCode').mask('0000-0000-0000', {selectOnFocus: true});
</script>