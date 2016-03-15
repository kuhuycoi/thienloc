<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<c:set value="${f:findDistributorById(sessionScope['CUSTOMER_ID'])}" var="CUSTOMER" />
<c:set value="${f:findRankNowByCustomerID(sessionScope['CUSTOMER_ID'])}" var="RANKNOW" />
<h1>Thông tin hệ thống</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="login-title">Thông tin tài khoản</h2>
    </div>
    <div class="panel-body">
        <p>Thông tin tài khoản:</p>
        <table class="table table-bordered">
            <tr>
                <td>Tên đăng nhập</td>
                <td>${CUSTOMER.userName}</td>
            </tr>
            <tr>
                <td>Cấp bậc</td>
                <td>${RANKNOW.levelId}</td>
            </tr>
            <tr>
                <td>Mức tích lũy</td>
                <td>${CUSTOMER.rankCustomerName!=null&&CUSTOMER.isDeposited?CUSTOMER.rankCustomerName:'<i style="color:red">NPP</i>'}</td>
            </tr>
            <tr>
                <td>Đại lý</td>
                <td>${CUSTOMER.provinceAgencyName}</td>
            </tr>
            <tr>
                <td>Ngày kích hoạt</td>
                <td>${CUSTOMER.lastLoginDateUtc==null?"":f:formatTime(CUSTOMER.lastLoginDateUtc)}</td>
            </tr>
            <tr>
                <td>Hoa hồng trước thuế</td>
                <td class="redColor">${f:formatCurrency(f:getTotalOutOfCustomerId(sessionScope['CUSTOMER_ID']))}</td>
            </tr>
            <tr>
                <td>Hoa hồng sau thuế</td>
                <td class="redColor">${f:formatCurrency(f:getTotalOutOfCustomerId(sessionScope['CUSTOMER_ID'])*95/100)}</td>
            </tr>
            <tr>
                <td>Người giới thiệu</td>
                <td>${CUSTOMER.customerName}</td>
            </tr>
            <tr>
                <td>Người chỉ định</td>
                <td>${CUSTOMER.parentName}</td>
            </tr>
        </table>
        <div class="buttonBar"></div>
        <p>Thông tin cá nhân:</p>
        <table class="table table-bordered">
            <tr>
                <td>Họ và tên</td>
                <td>${CUSTOMER.firstName} ${CUSTOMER.lastName}</td>
            </tr>
            <tr>
                <td>Ngày sinh</td>
                <td>${CUSTOMER.dateOfBirth}</td>
            </tr>
            <tr>
                <td>Số CMND</td>
                <td>${CUSTOMER.peoplesIdentity}</td>
            </tr>
            <tr>
                <td>Ngày cấp CMND</td>
                <td>${CUSTOMER.lastActivityDateUtc}</td>
            </tr>
            <tr>
                <td>Địa chỉ</td>
                <td>${CUSTOMER.address}</td>
            </tr>
            <tr>
                <td>Số ĐT</td>
                <td>${CUSTOMER.mobile}</td>
            </tr>
            <tr>
                <td>Mã số thuế</td>
                <td>${CUSTOMER.taxCode}</td>
            </tr>
        </table>
        <div class="buttonBar"></div>
        <c:if test="${f:getPropertyBooleanValue(applicationScope['MAIN_PROPERTIES_FILE_PATH'], 'allowUpdateInfo')}">
            <form action="/Customer/MyAccount/Edit" method="post" novalidate class="form-insert form-horizontal">
                <fieldset>
                    <legend><b>Cập nhật thông tin:</b></legend>

                    <div class="form-group">
                        <label class="col-xs-3">Ngày sinh</label>
                        <div class="col-xs-9">
                            <input type="text" name="dateOfBirth" class="form-control date-mask" required pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" data-original-title-pattern="Định dạng ngày tháng không hợp lệ dd/mm/yyyy" value="${CUSTOMER.dateOfBirth}" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3">Số ĐT</label>
                        <div class="col-xs-9">
                            <input type="text" name="mobile" pattern="^0|\+[\d]+[\d]+$" required data-original-title-pattern="Định dạng số điện thoại không hợp lệ" class="form-control" value="${CUSTOMER.mobile}" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3">Địa chỉ</label>
                        <div class="col-xs-9">
                            <td class="col-sm-8 col-xs-12"><textarea class="form-control" rows="5" required name="address">${CUSTOMER.address}</textarea>
                        </div>
                    </div>
                    <div class="buttonBar"></div>
                    <button type="submit" class="btn btn-blue">Cập nhật</button> <button type="reset" class="btn btn-blue">Nhập lại</button>
                </fieldset>
            </form>
            <div class="buttonBar"></div>
        </c:if>

        <c:if test="${f:getPropertyBooleanValue(applicationScope['MAIN_PROPERTIES_FILE_PATH'], 'allowUpdateTaxCode')}">
            <form action="/Customer/MyAccount/EditTaxCode" method="post" novalidate class="form-insert form-horizontal">
                <fieldset>
                    <legend><b>Cập nhật mã số thuế:</b></legend>

                    <div class="form-group">
                        <label class="col-xs-3">Mã số thế: </label>
                        <div class="col-xs-9">
                            <input type="text" name="taxCode" class="form-control" required value="${CUSTOMER.taxCode}" />
                        </div>
                    </div>
                    <div class="buttonBar"></div>
                    <button type="submit" class="btn btn-blue">Cập nhật</button> <button type="reset" class="btn btn-blue">Nhập lại</button>
                </fieldset>
            </form>
        </c:if>
    </div>
</div>