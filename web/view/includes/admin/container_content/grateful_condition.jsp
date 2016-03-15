<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set var="PAGINATION" value="${sessionScope['ADMIN_GRATEFUL_CONDITION_PAGINATION']}"/>
<input type="hidden" id="reloadController" value="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}'/>">
<style>
    .the-legend {
        border-style: none;
        border-width: 0;
        font-size: 18px;
        line-height: 20px;
        margin-bottom: 0;
    }
    .the-fieldset {
        border: 2px groove threedface #444;
        -webkit-box-shadow:  0px 0px 0px 0px #000;
        box-shadow:  0px 0px 0px 0px #000;
        margin: 30px 0 !important;
    }
    .form-condition-action button{
        display: none;
    }
    .form-condition-action #btn-edit-condition{display: inline-block}
    .form-condition-action.active button{
        display: inline-block
    }
    .form-condition-action.active #btn-edit-condition{
        display: none
    }
</style>
<div class="content-title">
    <h3 class="text-center">${PAGINATION.viewTitle}</h3>
</div>
<div class="panel panel-default">
    <div class="ajax-content panel-body">
        <form class="col-sm-6 col-sm-offset-3 col-xs-12" id="form-edit-condition" action="<c:url value='${PAGINATION.ROOT_CONTROLLER}${PAGINATION.childrenController}${PAGINATION.grandController}/Edit'/>" novalidate>
            <fieldset class="well the-fieldset need-disabled" disabled="">
                <legend class="the-legend">Điều kiện tham gia tri ân</legend>

                <div class="form-group">
                    <label for="activeStartDate" class="control-label">Ngày bắt đầu</label>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group date">
                                <input type="text" class="form-control" required id="trianTimeStart" name="activeStartDate" value="${GRATEFUL_CONDITION.activeStartDate}" />
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>       
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="activeFinishDate" class="control-label">Ngày kết thúc</label>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <input type="text" class="form-control" required id="trianTimeFinish" name="activeFinishDate" value="${GRATEFUL_CONDITION.activeFinishDate}" />
                                <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>       
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="well the-fieldset need-disabled" disabled="">
                <legend class="the-legend" class="control-label">Điều kiện bắt đầu tri ân</legend>
                <div class="form-group">
                    <label for="joinGratefulDate">Đếm ngược</label>
                    <div class="row">
                        <div class="col-xs-12">
                            <input type="text" class="form-control" required id="joinGratefulDate" name="joinGratefulDate" value="${GRATEFUL_CONDITION.joinGratefulDate}" />
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="form-group text-center form-condition-action">
                <button type="button" class="btn btn-danger" id="btn-edit-condition">Sửa</button>
                <button type="submit" class="btn btn-primary">Cập nhật</button>
                <button type="reset" class="btn btn-default" id="btn-cancel-edit-condition">Hủy</button>
            </div> 
        </form>
    </div>
</div><!-- end panel-->
<script>
    $('#btn-edit-condition').click(function () {
        $('#form-edit-condition').find('fieldset').removeProp("disabled");
        $('.form-condition-action').addClass('active');
    });
    $('#btn-cancel-edit-condition').click(function () {
        $('#form-edit-condition').find('fieldset').prop("disabled", "disabled");
        $('.form-condition-action').removeClass('active');
    });
    $('#form-edit-condition').submit(function (e) {
        e.preventDefault();
        debugger;
        var error = $(this).validate();
        if (!error) {
            var customer = JSON.stringify($(this).serializeObject());
            var url = $(this).attr('action');
            $.ajax({
                beforeSend: function () {
                    NProgress.set(0.5);
                },
                url: url,
                type: 'POST',
                data: customer,
                contentType: 'application/json',
                success: function (data) {
                    openMessage(data, function () {
                        reloadAjaxContent();
                    });
                    NProgress.done();
                }, error: function () {
                    reloadAjaxContent();
                    NProgress.done();
                }
            });
        }
    });

    $('#trianTimeStart,#trianTimeFinish').datepicker({
        autoclose: true,
        orientation: "bottom left",
        todayHighlight: true,
        language: "vi",
        toggleActive: true,
        format: "dd/mm/yyyy"
    });
    $('#trianTimeStart,#trianTimeFinish').mask('00/00/0000');
    $('#joinGratefulDate').mask('00/00/0000 00:00:00');
</script>