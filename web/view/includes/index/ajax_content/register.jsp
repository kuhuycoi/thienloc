<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Tài khoản</h1>
<div class="panel">
    <div class="panel-heading">
        <h2>Đăng ký</h2>
    </div>
    <div class="panel-body">
        <form class="form-insert" method="post" novalidate action="<c:url value='/Customer/Register'/>">
            <p>Thông tin bắt buộc:</p>
            <div class="row">
                <div class="col-xs-2 text-left">
                    <label>Họ và tên *</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" required name="lastName" class="form-control">
                </div>   
            </div> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Tên đăng nhập *</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" required pattern="^[\w\d]{6,15}$" data-original-title-pattern="Chỉ chấp nhận các ký tự A-Z, a-z, 0-9. Tối thiểu 6 ký tự, tối đa 15 ký tự" name="title" class="form-control">
                    <p class="help-block"><i>Chỉ chấp nhận các ký tự trong bảng chữ cái và ký tư số.</i></p>
                </div>
            </div> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Mật khẩu *</label>
                </div>
                <div class="col-xs-10">
                    <input type="password" required pattern="^[\w\d]{6,15}$" data-original-title-pattern="Chỉ chấp nhận các ký tự A-Z, a-z, 0-9. Tối thiểu 6 ký tự, tối đa 15 ký tự" name="password" id="newPassword" class="form-control">
                    <p class="help-block"><i>Chỉ chấp nhận các ký tự trong bảng chữ cái và ký tư số.</i></p>
                </div>
            </div>  
            <div class="row">
                <div class="col-xs-2">
                    <label>Nhập lại mật khẩu *</label>
                </div>
                <div class="col-xs-10">
                    <input class="form-control" required type="password" match="#newPassword">
                </div>
            </div> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Người giới thiệu *</label>
                </div>
                <div class="col-sm-10">
                    <input type="text" required id="customerName" name="customerName" class="form-control" />
                </div>
            </div>
            <div class="row">
                <div class="col-xs-2">
                    <label>Người chỉ định *</label>
                </div>
                <div class="col-sm-10">
                    <input type="text" required id="parentName" name="parentName" class="form-control"/>
                </div>
            </div>  
            <div class="row">
                <div class="col-xs-2">
                    <label>Số CMND *</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" name="peoplesIdentity" required pattern="^[\d]+$" data-original-title-pattern="Vui lòng nhập các ký tự số" class="form-control">
                </div>
            </div> 
            <p>Thông tin bổ sung:</p> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Ngày cấp CMND</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" name="lastActivityDateUtc" pattern="^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$" data-original-title-pattern="Định dạng ngày tháng không hợp lệ dd/mm/yyyy" class="form-control date-mask" />
                </div>
            </div>  
            <div class="row">
                <div class="col-xs-2">
                    <label>Đại lý *</label>
                </div>
                <div class="col-xs-10">                   
                    <select name="provinceAgencyId" required class="form-control">
                        <option value=''>-- Lựa chọn Đại lý --</option>
                        <c:forEach items="${f:findAllAvailableProvincialAgencies()}" var="provincialAgency">
                            <option value='${provincialAgency.id}'>${provincialAgency.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>  
            <div class="row">
                <div class="col-xs-2">
                    <label>Giới tính</label>
                </div>
                <div class="col-xs-10">
                    <label class="checkbox-inline"><input type="radio" name="gender" value="true">Nam</label>
                    <label class="checkbox-inline"><input type="radio" name="gender" value="false">Nữ</label>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-2">
                    <label>Email</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" name="email" pattern="^[\w\d_-]+@[\w\d-_]+(.[\w\d-_]+)+$" data-original-title-pattern="Định dạng email không hợp lệ" class="form-control">
                    <p class="help-block"><i>Sử dụng để lấy lại thông tin đăng nhập.</i></p>
                </div>
            </div> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Số điện thoại</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" name="mobile" pattern="^0|\+[\d]+[\d]+$" data-original-title-pattern="Định dạng số điện thoại không hợp lệ" class="form-control">
                </div>
            </div>
            <!--            <div class="row">
                            <div class="col-xs-2">
                                <label>Mã số thuế</label>
                            </div>
                            <div class="col-xs-10">
                                <input type="text" name="taxCode" pattern="^[\d]+$" data-original-title-pattern="Định dạng mã số thuế không hợp lệ" class="form-control">
                            </div>
                        </div>  -->
            <div class="row">
                <div class="col-xs-2">
                    <label>Địa chỉ</label>
                </div>
                <div class="col-xs-10">
                    <input type="text" class="form-control" name="address" />
                </div>
            </div>
            <div class="row">
                <div class="col-xs-2">
                    <label>Loại khách hàng</label>
                </div>
                <div class="col-xs-10">
                    <select name="customerTypeId" class="form-control">
                        <option value="">-- Để trống --</option>
                        <c:forEach items="${f:findAllAvailableCustomerType()}" var="customerType">
                            <option value='${customerType.id}'>${customerType.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div> 
            <div class="row">
                <div class="col-xs-2">
                    <label>Điều khoản sử dụng</label>
                </div>
                <div class="col-xs-10">
                    <div style="height: 300px; overflow-x: auto; border:1px solid #d1d1d1; padding: 10px;line-height: 20px" class="provision">
                        <p>Điều 1 : Đăng k&yacute; tuyển dụng</p>

                        <p>Đăng k&yacute; tuyển dụng trong &nbsp;<br />
                            Tk.thienlocgroup.com l&agrave; người đ&atilde; đăng k&yacute; t&agrave;i khoản sử dụng theo c&aacute;c bước quy định của c&ocirc;ng ty v&agrave; được c&ocirc;ng ty chấp nhận sự đăng k&yacute; đ&oacute;.</p>

                        <p>Việc đăng k&yacute; th&agrave;nh vi&ecirc;n tr&ecirc;n&nbsp;<br />
                            Tk.thienlocgroup.com l&agrave; ho&agrave;n to&agrave;n miễn ph&iacute;.<br />
                            &nbsp;</p>

                        <p>Điều 2 : Bản quy định</p>

                        <p>Tất cả c&aacute;c ứng vi&ecirc;n khi đăng k&yacute;, v&agrave; sau khi đi đăng k&yacute; đều phải tu&acirc;n thủ những quy định n&agrave;y</p>

                        <p>&nbsp;&nbsp;</p>

                        <p>Điều 3 : Tư c&aacute;ch ứng vi&ecirc;n, v&agrave; c&aacute;c bước đăng k&yacute; t&agrave;i khoản sử dụng</p>

                        <p>Tư c&aacute;ch ứng vi&ecirc;n :Ứng vi&ecirc;n sau khi đồng &yacute; quy định n&agrave;y v&agrave; kết th&uacute;c đăng k&yacute; t&agrave;i khoản sử dụng tr&ecirc;n&nbsp;<br />
                            Tk.thienlocgroup.com được coi l&agrave; ứng vi&ecirc;n tuyển dụng &nbsp;của&nbsp;<br />
                            Tk.thienlocgroup.com. Ứng vi&ecirc;n chỉ đăng k&yacute; t&agrave;i khoản sử dụng cho bản th&acirc;n, kh&ocirc;ng được đăng k&yacute; hộ cho người kh&aacute;c. Ch&uacute;ng t&ocirc;i c&oacute; thể từ chối việc đăng k&yacute; l&agrave;m ứng vi&ecirc;n của những th&agrave;nh vi&ecirc;n đ&atilde; từng bị hủy bỏ tư c&aacute;ch ứng vi&ecirc;n tr&ecirc;n Tk.thienlocgroup.com<br />
                            Bước đăng k&yacute; t&agrave;i khoản : Ứng vi&ecirc;n phải đọc r&otilde; c&aacute;c lưu &yacute;, v&agrave; điền ch&iacute;nh x&aacute;c nội dung th&ocirc;ng tin cần thiết v&agrave;o bản đăng k&yacute; mẫu.<br />
                            &nbsp;Điều 4 : Quản l&yacute; văn ph&ograve;ng v&agrave; mật khẩu</p>

                        <p>Ứng vi&ecirc;n sau khi được x&eacute;t duyệt phải c&oacute; tr&aacute;ch nhiệm tự quản l&yacute; t&ecirc;n t&agrave;i khoản v&agrave; mật khẩu.<br />
                            Ứng vi&ecirc;n phải c&oacute; nghĩa vụ thay đổi mật khẩu định kỳ, c&ocirc;ng ty ch&uacute;ng t&ocirc;i kh&ocirc;ng chịu tr&aacute;ch nhiệm về những tổn hại ph&aacute;t sinh nếu th&agrave;nh vi&ecirc;n mất mật khẩu do kh&ocirc;ng thay đổi.<br />
                            Ứng vi&ecirc;n phải c&oacute; tr&aacute;ch nhiệm tự bảo quản về t&agrave;i khoản v&agrave; mật khẩu của m&igrave;nh, nếu ứng vi&ecirc;n kh&ocirc;ng quản l&yacute; tốt để người thứ ba lấy được th&ocirc;ng tin, ch&uacute;ng t&ocirc;i kh&ocirc;ng chịu tr&aacute;ch nhiệm về bất k&yacute; những tổn thất ph&aacute;t sinh do việc để mất th&ocirc;ng tin tr&ecirc;n g&acirc;y ra.<br />
                            Ứng vi&ecirc;n kh&ocirc;ng được cho người kh&aacute;c mượn sử dụng, b&aacute;n, chuyển nhượng lại t&agrave;i khoản v&agrave; mật khẩu.<br />
                            Nếu trong một thời gian nhất định ch&uacute;ng t&ocirc;i thấy rằng t&agrave;i khoản v&agrave; mật khẩu của th&agrave;nh vi&ecirc;n kh&ocirc;ng được sử dụng, ch&uacute;ng t&ocirc;i c&oacute; thể tạm ngưng việc sử dụng văn ph&ograve;ng đ&oacute;. Trong trường hợp khẩn cấp, ch&uacute;ng t&ocirc;i c&oacute; thể &nbsp;kh&oacute;a mật khẩu của th&agrave;nh vi&ecirc;n m&agrave; kh&ocirc;ng cần được ứng vi&ecirc;n chấp thuận. V&agrave; ch&uacute;ng t&ocirc;i cũng kh&ocirc;ng chịu tr&aacute;ch nhiệm về sự tổn hại ph&aacute;t sinh từ việc ứng vi&ecirc;n kh&ocirc;ng quản l&yacute; được t&agrave;i khoản của m&igrave;nh.<br />
                            Điều 6 : Sử dụng th&ocirc;ng tin đăng k&yacute;</p>

                        <p>Th&ocirc;ng tin do c&aacute;c th&agrave;nh vi&ecirc;n đăng k&yacute; l&ecirc;n Tk.thienlocgroup.com sẽ thuộc quyền sở hữu của c&ocirc;ng ty được quản l&yacute; v&agrave; sử dụng theo c&aacute;c ch&iacute;nh s&aacute;ch về bảo vệ th&ocirc;ng tin c&aacute; nh&acirc;n của c&ocirc;ng ty.<br />
                            Một phần th&ocirc;ng tin của c&aacute;c th&agrave;nh vi&ecirc;n sẽ được sử dụng l&agrave;m số liệu thống k&ecirc; ở mức độ sẽ kh&ocirc;ng ph&acirc;n biệt được từng c&aacute; nh&acirc;n. Trong trường hợp nếu ch&uacute;ng t&ocirc;i sử dụng th&ocirc;ng tin của c&aacute;c th&agrave;nh vi&ecirc;n ở mức độ cao hơn th&igrave; ch&uacute;ng t&ocirc;i sẽ th&ocirc;ng b&aacute;o với th&agrave;nh vi&ecirc;n đ&oacute;.<br />
                            Khi ứng vi&ecirc;n c&oacute; h&agrave;nh vi g&acirc;y tổn hại đến c&aacute;c ứng vi&ecirc;n kh&aacute;c hoặc người thứ ba, ch&uacute;ng t&ocirc;i c&oacute; thể cung cấp th&ocirc;ng tin c&aacute; nh&acirc;n của ứng vi&ecirc;n đ&oacute; cho người bị hại, cảnh s&aacute;t, hoặc c&aacute;c cơ quan hữu quan.<br />
                            Khi t&ograve;a &aacute;n, viện kiểm s&aacute;t, cảnh s&aacute;t, cơ quan thuế, văn ph&ograve;ng luật sư, trung t&acirc;m bảo vệ người ti&ecirc;u d&ugrave;ng, c&aacute;c cơ quan c&oacute; thẩm quyền tương đương, hoặc c&ocirc;ng ty l&agrave;m về bảo mật y&ecirc;u</p>
                    </div>
                    <label class="help-block"><input type="checkbox" required />Tôi đã đọc và đồng ý với các điều khoản trên</label>
                </div>
            </div> 
            <div class="buttonBar">
                <button type="submit" class="btn btn-default">
                    Đăng ký
                </button>
                <button type="reset" class="btn btn-default">
                    Nhập lại
                </button>
            </div>
        </form>   
    </div>
</div>
<style>
    #ajax-content .panel .panel-body .provision{
        height: 300px; overflow-x: auto; border:1px solid #d1d1d1; padding: 10px;line-height: 20px;background-color: #fff;
    }
    #ajax-content .panel .panel-body .provision p{
        font-weight: normal;
        font-family: 'LatoWeb'
    }
</style>
