<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope['CUSTOMER_ID']!=null}">
    <div class="footer-top">
        <div class="container">
            <div class="row">
                <div class="col-xs-4">
                    <h3>Điều hướng</h3>
                    <ul>
                        <li><a href="#">Trang chủ</a></li>
                        <li><a href="#">Giới thiệu</a></li>
                        <li><a href="#">Tin tức</a></li>
                        <li><a href="#">Sản phẩm</a></li>
                        <li><a href="#">Tuyển dụng</a></li>
                        <li><a href="#">Liên hệ</a></li>
                    </ul>
                </div>
                <div class="col-xs-8" style="color: #1d1d1d; line-height: 24px">
                    <div><strong>CÔNG TY CỔ PHẦN NHƯỢNG QUYỀN THIÊN LỘC</strong></div>

                    <div>Người đại diện: Ông Nguyễn Đức Lộc<br>
                        Mã số doanh nghiệp : 0313542579<br>
                        Địa&nbsp;chỉ : Số 21 Lê Trung Nghĩa - Khu K300 Phường 12 - Quận Tân Bình -TP. Hồ Chí Minh</div>

                    <div>Điện thoại: 0838489838 &nbsp; &nbsp; &nbsp;</div>

                    <div>Email: info@thienlocgroup.com - Website: www.thienlocgroup.com</div>

                    <div><strong>Chi nhánh Hà Nội:</strong> Số 14, khu C, Phú Mỹ, Mỹ Đình đường Nguyễn Hoàng, Nam Từ Liêm, Hà Nội</div>

                    <div>Điện thoại: 04 3311 6666</div>
                </div>
            </div>        
        </div>
    </div>
</c:if>
<div class="footer-bottom text-center">
    <span>© 2015. Bản quyền thuộc về <a href="http://tk.thienlocgroup.com" class="external">Thiên Lộc Group</a>. Designed By LeoTeams</span>
</div>