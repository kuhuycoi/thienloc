$(document).ready(function () {

    $(document).on('click', '#message .close', function () {
        $('#message').fadeOut(300).remove();
    });
    $(document).on('submit', '#form-login-admin', function (e) {
        e.preventDefault();
        var error = $(this).validate();
        if (!error) {
            var admin = JSON.stringify($(this).serializeObject());
            var url = $(this).attr('action');
            $.ajax({
                beforeSend: function () {
                    NProgress.set(0.5);
                },
                url: url,
                type: 'POST',
                data: admin,
                contentType: 'application/json',
                success: function (data) {
                    NProgress.done();
                    openMessage(data);
                    $('#btn-reload-captcha').trigger("click");
                    $('input[name="captcha"]').val('');
                },
                error:function(){
                    $('#btn-reload-captcha').trigger("click");
                    $('input[name="captcha"]').val('');
                }
            });
        }
    });
});
function sendAjax(url, type, data, handle) {
    $.ajax({
        beforeSend: function () {
            NProgress.set(0.5);
        },
        url: url,
        type: type,
        data: data,
        headers: {'Content-Type': undefined},
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        success: function (data) {
            if (typeof handle !== "undefined") {
                handle(data);
            }
            NProgress.done();
        },
        error: function () {
            NProgress.done();
        }
    });
}
var intervalDisplayMessage;
function openMessage(data) {
    clearInterval(intervalDisplayMessage);
    $('#message').remove();
    if (typeof data !== "undefined") {
        $('body').append(data);
    }
    intervalDisplayMessage = setInterval(function () {
        $('#message').remove();
    }, 5000);
    $('#message').fadeIn(300);
}
$.fn.serializeObject = function () {
    var o = {};
    $.each($(this).find('input:not(.external),select:not(.external),textarea:not(.external)'), function () {
        var v;
        if ($(this).is(':checkbox')) {
            v = $(this).is(':checked');
        } else if ($(this).val() === '') {
            v = null;
        } else if ($(this).attr('data-json')) {
            v = {};
            v[$(this).attr('data-json')] = $(this).val();
        } else {
            v = $(this).val();
        }
        o[$(this).attr('name')] = v;
    });
    return o;
};

$.fn.validate = function () {
    var error = false;
    var msg = {
        'required': 'Vui lòng nhập trường này',
        'pattern': 'Yêu cầu thỏa mãn định dạng ',
        'min': 'Giá trị yêu cầu phải lớn hơn ',
        'max': 'Giá trị yêu cầu phải nhỏ hơn '
    };
    var props = {'data-toggle': "tooltip", 'data-placement': "top", 'data-original-title': "Error"};
    $.each($(this).find('.form-control:not(.external)'), function () {
        $(this).removeClass('error');
        if ($(this).attr('required') && $(this).val().trim().length === 0) {
            $(this).addClass('error');
            props['data-original-title'] = msg['required'];
            if ($(this).attr('data-original-title-required')) {
                props['data-original-title'] = $(this).attr('data-original-title-required');
            }
            $(this).attr(props);
            error = true;
        } else if ($(this).attr('pattern')
                && $(this).val().trim().length !== 0
                && !new RegExp($(this).attr('pattern')).test($(this).val())) {
            $(this).addClass('error');
            props['data-original-title'] = msg['pattern'];
            if ($(this).attr('data-original-title-pattern')) {
                props['data-original-title'] = $(this).attr('data-original-title-pattern');
            }
            $(this).attr(props);
            error = true;
        } else if ($(this).attr('type') === 'number' && $(this).val().trim().length !== 0
                && $(this).attr('min')
                && parseInt($(this).val()) < parseInt($(this).attr('min'))) {
            $(this).addClass('error');
            props['data-original-title'] = msg['min'] + $(this).attr('min');
            $(this).attr(props);
            error = true;
        } else if ($(this).attr('type') === 'number' && $(this).val().trim().length !== 0
                && $(this).attr('max')
                && parseInt($(this).val()) > parseInt($(this).attr('max'))) {
            $(this).addClass('error');
            props['data-original-title'] = msg['max'] + $(this).attr('max');
            $(this).attr(props);
            error = true;
        }
        if ($(this).hasClass('error')) {
            $(this).tooltip('show');
        } else {
            $(this).tooltip('hide');
            $(this).tooltip('destroy');
        }
    });
    return error;
};