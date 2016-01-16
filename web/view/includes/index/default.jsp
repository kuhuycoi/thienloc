<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>

<%--<div style="color: #fff;margin-bottom: 20px;background-color: #993300;padding: 30px 15px">
    <h2 style="text-transform: uppercase;text-align: center;font-size: 20px;color: yellow;margin-bottom:20px;line-height:40px">PHẦN MỀM ĐĂNG KÍ NPP MIỄN PHÍ ĐÃ ĐƯỢC ĐÓNG. XIN TRÂN TRỌNG THÔNG BÁO!</h2>
    <h1 style="text-transform: uppercase;text-align: center;color: yellow">Thông báo!</h1>
    <p style="line-height: 24px;text-align: justify; color: #fff;font-size: 20px">
        Lời đầu tiên chúng tôi xin được gửi lời chào thân thương và lời cảm ơn sâu sắc nhất tới toàn thể quý NPP đã tin tưởng vào công ty cổ phần nhượng quyền Thiên Lộc. Công ty cổ phần nhượng quyền Thiên Lộc đặt ra mục tiêu là "tuân thủ pháp luật, kinh doanh đúng đắn" làm kim chỉ nam. Chính vì vậy để hoàn thiện công ty một cách đầy đủ nhất kính mong các NPP thực hiện đúng theo nguyên tắc của công ty. Nghiêm cấm mọi trường hợp tư vấn khách hàng và thu tiền trong thời gian công ty đang hoàn thiện. Nếu NPP tự ý tư vấn và thu tiền của khách hàng thì NPP đó tự chịu trách nhiệm trước pháp luật. Vậy kính mong NPP hợp tác để doanh nghiệp phát triển một cách bền vững và đúng pháp luật. Một lần nữa chúng tôi xin trân trọng cảm ơn!<br/>
        Ban lãnh đạo công ty cổ phần thiên lộc trân trọng thông báo!</p>
    <div class="center" style="margin-top: 20px">
        <h2 style="color: #fff; font-style: italic">Hiện tại hệ thống đang có một số mã bị lỗi, bạn vui lòng click vào <a href="<c:url value="/resources/file/report.xlsx"/>" style="color: #3498db">đây</a> để kiểm tra và chỉnh sửa lại.<br/></h2>
    </div>
    <div style="color: #fff;margin-bottom: 20px;background-color: #993300;padding: 30px 15px">
        <h1 style="text-transform: uppercase;text-align: center;color: yellow">Thông báo!</h1>
        <p style="line-height: 24px;text-align: justify; color: #fff;font-size: 20px">
            Vì lý do web đồng bộ mấy ngày vừa qua nên có một số NPP tích luỹ mức Đồng hay Bạc nhưng vẫn không được chia phần trăm Khuyến Mại. Vì vậy để công bằng cho NPP công ty đã đồng bộ lại toàn bộ những khoản hoa hồng để chia đều cho những NPP chưa được nhận hoa hồng mấy ngày qua.vì vậy những NPP được chia hoa hồng trước đó sẽ bị giảm hoa hồng của mình.
            <br><br>Trân trọng!</p>
    </div>
</div>--%>  
<div class="news-block-list">
    <div class="row">
        <div class="col-sm-6 col-xs-12">
            <div class="news-block">
                <div class="news-block-title">
                    <h3 style="font-weight:700; color:#2c87f0;"><i class="fa fa-info"></i> Tin giới thiệu</h3>
                </div>
                <ul class="news-list">
                    <c:set value="${f:getListPost(1,0,3)}" var="newsList" />
                    <c:forEach items="${newsList}" var="news">
                        <li>
                            <i class="fa fa-arrow-circle-right"></i><a href="<c:url value="/tin-tuc/${news.caId.id}/${news.seoPermalink}"/>" title="${news.seoTitle}">${news.name}</a>
                        </li>
                    </c:forEach>
                    <li class="btn-read-all"><a href="">Xem tất cả..</a></li>
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
                        <c:set value="${f:getListPost(2,0,3)}" var="newsList" />
                        <c:forEach items="${newsList}" var="news">
                            <li>
                                <i class="fa fa-arrow-circle-right"></i><a href="<c:url value="/tin-tuc/${news.caId.id}/${news.seoPermalink}"/>" title="${news.seoTitle}">${news.name}</a>
                            </li>
                        </c:forEach>
                        <li class="btn-read-all"><a href="">Xem tất cả..</a></li>
                    </ul>
                </ul>
            </div>
        </div>
    </div>
    <%--
    <div class="row" style="margin-top: 20px;">
        <div class="col-xs-12">
            <img src="<c:url value="/resources/img/tinvuixuan2.png"/>" alt=""/>
        </div>
    </div>
    --%>
</div> 
<%--<ul class="slider">
    <li>
        <a href="<c:url value="/resources/img/IMG_4338.PNG"/>" class="zoom" title="Giấy chứng nhận đăng ký doanh nghiệp công ty cổ phần">
            <img src="<c:url value="/resources/img/IMG_4338.PNG"/>" alt="" />
        </a>
        <h4>Giấy chứng nhận đăng ký doanh nghiệp công ty cổ phần</h4>
    </li>
    <li>
        <a href="<c:url value="/resources/img/IMG_4306.PNG"/>" class="zoom" title="Chứng nhận cơ sở đủ điều kiện an toàn thực phẩm">
            <img src="<c:url value="/resources/img/IMG_4306.PNG"/>" alt="" />
        </a>
        <h4>Chứng nhận cơ sở đủ điều kiện an toàn thực phẩm</h4>
    </li>
    <li>
        <a href="<c:url value="/resources/img/IMG_4307.PNG"/>" class="zoom" title="Giấy chứng nhận thực hành tốt sản xuất thực phẩm chức năng">
            <img src="<c:url value="/resources/img/IMG_4307.PNG"/>" alt="" />
        </a>
        <h4>Giấy chứng nhận thực hành tốt sản xuất thực phẩm chức năng</h4>
    </li>
    <li>
        <a href="<c:url value="/resources/img/IMG_4329.PNG"/>" class="zoom" title="Xác nhận công bố phù hợp quy định an toàn thực phẩm - năm 2014">
            <img src="<c:url value="/resources/img/IMG_4329.PNG"/>" alt="" />
        </a>
        <h4>Xác nhận công bố phù hợp quy định an toàn thực phẩm - năm 2014</h4>
    </li>
    <li>
        <a href="<c:url value="/resources/img/IMG_4395.PNG"/>" class="zoom" title="Xác nhận công bố phù hợp quy định an toàn thực phẩm - năm 2014">
            <img src="<c:url value="/resources/img/IMG_4395.PNG"/>" alt="" />
        </a>
        <h4>Xác nhận công bố phù hợp quy định an toàn thực phẩm - năm 2012</h4>
    </li>
</ul>--%>
<script>
    $(document).ready(function () {
        $('.slider').owlCarousel({
            items: 3,
            lazyLoad: true,
            autoPlay: true
        });
        $('.slider').magnificPopup({
            delegate: '.zoom',
            type: 'image',
            tLoading: 'Loading image #%curr%...',
            mainClass: 'mfp-img-mobile',
            gallery: {
                enabled: true,
                navigateByImgClick: true,
                preload: [0, 1]
            }
        });
    });
</script>
<style>
    .slider{
        margin-top: 10px;
    }
    .slider li{
        margin: 10px;
    }
    .slider li img{
        height: 400px;
        width: 100%
    }
    .slider li>a{
        display: block;
        margin-bottom: 10px;
    }
</style>