<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Tài khoản</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Đăng nhập</h2>
    </div>
    <div class="panel-body">
        <p>Nếu bạn đã có tài khoản, vui lòng đăng nhập bằng form dưới đây:</p>
        <form id="form-customer-login" method="post" novalidate action="<c:url value='/Customer/Login'/>">
            <fieldset>
                <div class="row">
                    <div class="col-xs-2">
                        <label>Tên đăng nhập *</label>
                    </div>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" name="userName" required />
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2">
                        <label>Mật khẩu *</label>
                    </div>
                    <div class="col-xs-6">
                        <input type="password" class="form-control" name="password" required placeholder="•••••••••••" />
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-offset-2 col-xs-10">
                        <img id="captcha_id" src="/Captcha/CustomerLogin/1"/> 
                        <a href="javascript:;" id="btn-reload-captcha" class="btn" style="color: #000 !important" title="Click để thay đổi captcha" onclick="document.getElementById('captcha_id').src = '/Captcha/CustomerLogin/' + Math.random(); return false">
                            <i class="fa fa-refresh"></i>
                        </a>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-2">
                        <label>Nhập capcha</label>
                    </div>
                    <div class="col-xs-10">
                        <input type="text" name="captcha" required class="form-control" maxlength="6" />
                    </div>
                </div>  
                <div class="col-xs-6 col-xs-offset-2">
                    <button class="btn-default btn" type="submit">Đăng nhập</button>
                </div>
            </fieldset>
        </form>    
        <div class="row">
            <div class=" col-xs-4">
                <a href="<c:url value='/dang-ky'/>">Chưa có tài khoản?</a>
            </div>
            <div class=" col-xs-4">
                <a class="btn-change-content" controller="<c:url value='/Customer/ResetPassword'/>">Quên mật khẩu?</a>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).on('submit', '#form-customer-login', function (e) {
        e.preventDefault();
        $('#form-customer-login fieldset').prop("disabled", "disabled");
        var error = $(this).validate();
        if (!error && typeof $(this).attr('action') !== 'undefined') {
            var customer = JSON.stringify($(this).serializeObject());
            var url = $(this).attr('action');
            sendAjaxWithJsonObj(url, 'POST', customer, function (data) {
                $('#form-customer-login fieldset').removeProp("disabled");
                openMessage(data);
                $('#btn-reload-captcha').trigger("click");
                $('input[name="captcha"]').val('');
            });
        }
    });
</script>