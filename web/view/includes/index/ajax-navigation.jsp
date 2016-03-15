<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseOne">
            Thông tin hệ thống            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseOne">
        <ul>
            <li><a class="btn-change-content blueColor" controller="<c:url value="/Customer/MyAccount" />">Thông tin tài khoản</a></li> 
            <li><a class="btn-change-content blueColor" controller="<c:url value="/Customer/ChangePassword" />">Thay đổi mật khẩu</a></li> 
                <c:if test="${f:getPropertyBooleanValue(applicationScope['MAIN_PROPERTIES_FILE_PATH'], 'allowUpdateAgency')}">
                <li><a class="btn-change-content blueColor" controller="<c:url value="/Customer/ViewChangeAgency" />">Đại lý nhận hoa hồng</a></li> 
                </c:if>
                <%--<li><a class="btn-open-diagram" controller="<c:url value="/Customer/TreeCustomer" />">Cây phả hệ chỉ định</a></li>--%> 
            <li><a class="btn-change-content blueColor" controller="<c:url value="/Customer/TreeFolderCustomer" />">Cây thư mục chỉ định</a></li>
            <li><a class="btn-change-content blueColor" controller="<c:url value='/Customer/CustomerForCustomer'/>">Danh sách bảo trợ F1</a></li>
            <li>
                <a class="btn-change-content blueColor" controller="<c:url value="/Report/AwardG" />">Thống kê doanh số nhóm</a>
            </li>
        </ul>
    </div>
</div>
<%--<c:if test="${f:checkTrianTime(applicationScope['MAIN_PROPERTIES_FILE_PATH'], sessionScope['CUSTOMER_ACTIVE_TIME'])}">
    <div class="panel">
        <div class="panel-heading">
        </div>
        <div class="panel-body collapse in" id="collapseTwo">
            <ul>
                <c:set var="checkJoinedTrian" value="${!f:checkJoinedTrian(sessionScope['CUSTOMER_ID'])}" />
                <li class="item-tree-trian ${!checkJoinedTrian?"":"hidden"}"><a class="btn-change-content blueColor" controller="<c:url value="/Report/Trian/TreeTrian" />">Niềm tin chiến thắng</a></li>
                <li class="item-join-trian ${!checkJoinedTrian?"hidden":""}">
                    <a class="btn-change-content redColor" controller="<c:url value="/Report/Trian/ViewInsert" />">Niềm tin chiến thắng</a>
                </li>
            </ul>
        </div>
    </div>
</c:if>
<div class="panel">
    <div class="panel-heading">
        <a data-toggle="collapse" href="#collapseThree">
            Thống kê thưởng            
        </a>
    </div>
    <div class="panel-body collapse in" id="collapseThree">
        <ul>
            <c:if test="${f:checkHavePromotion(sessionScope['CUSTOMER_ID'])>0}">
                <li class="select-promotion">
                    <a class="btn-change-content" controller="<c:url value="/Report/Award/15/8" />" style="background-color: red;font-size: 16px; color:#fff;">Chương trình khuyến mãi</a>
                </li>
            </c:if>
            <c:if test="${f:checkHavePromotion(sessionScope['CUSTOMER_ID'])>0}">
                <li>
                    <a class="btn-change-content blueColor">Chương trình khuyến mãi</a>
                </li>
            </c:if>
            <li><a class="btn-change-content blueColor" controller="/view/includes/index/container_content/history_received_award.jsp">Lịch sử nhận thưởng</a></li>
            <li>Chọn loại hoa hồng:</li>
            <li>
                <a class="btn-change-content blueColor" controller="/view/includes/index/container_content/report_award_customer.jsp">Tổng hợp hoa hồng</a>
            </li>
            <c:if test="${CUSTOMER.isDeposited}">
                <li>
                    <select id="cbAwardType" class="form-control" controller="<c:url value="/Report/Award/" />">
                        <option value="4/-1">Hoa hồng trực tiếp</option>           
                        <option value="1/-1">Hoa hồng cộng hưởng</option>           
                        <option value="13/-1">Hoa hồng cân nhánh</option>    
                        <option value="14/-1">Hoa hồng lãnh đạo</option>       
                        <option value="6/-1">Lương hệ thống</option>  
                        <option value="3/-1">Hoa hồng lên cấp</option>  
                        <option value="2/-1">Hoa hồng phần trăm đồng hưởng công ty</option> 
                        <option value="31/-1">Hoa hồng hoàn vốn</option> 
                    </select>
                </li>
                <li>
                    <a class="btn btn-default btn-sm" id="btn-view-award">Xem</a>
                </li>
                <li><hr/></li>
                    <c:set var="totalOutOfCustomerId" value="${f:getTotalOutOfCustomerId(sessionScope['CUSTOMER_ID'])}" />
                    <c:if test="${totalOutOfCustomerId==f:getSystemAwards(CUSTOMER.peoplesIdentity)}">
                    <li><b>Tổng hoa hồng:</b> <i>${f:formatCurrency(totalOutOfCustomerId)}</i></li>     
                    </c:if>
                    <c:if test="${totalOutOfCustomerId!=f:getSystemAwards(CUSTOMER.peoplesIdentity)}">
                    <li><b>Tổng hoa hồng:</b> <i>${f:formatCurrency(totalOutOfCustomerId)}</i></li>           
                    <li><b>Tổng hệ thống:</b> <i>${CUSTOMER.peoplesIdentity==null?f:formatCurrency(totalOutOfCustomerId):f:formatCurrency(f:getSystemAwards(CUSTOMER.peoplesIdentity))}</i></li> 
                    </c:if>
                </c:if>     
                <c:if test="${!CUSTOMER.isDeposited}">
                <li>
                    <select id="cbAwardType" class="form-control" controller="<c:url value="/Report/Award/" />">
                        <option value="4/100">Hoa hồng trực tiếp</option>           
                        <option value="1/100">Hoa hồng cộng hưởng</option>           
                        <option value="13/100">Hoa hồng cân nhánh</option>           
                        <!--<option value="13/2">Hoa hồng Silver</option>          
                        <option value="13/3">Hoa hồng Gold</option>            
                        <option value="13/4">Hoa hồng Shaphia</option>           
                        <option value="13/5">Hoa hồng Ruby</option>           
                        <option value="13/6">Hoa hồng Diamond</option>           
                        <option value="13/7">Hoa hồng Crown</option>          
                        <option value="13/8">Hoa hồng Crown VIP</option> -->      
                        <option value="14/100">Hoa hồng lãnh đạo</option>       
                        <option value="6/100">Lương hệ thống</option>     
                        <!--<option value="1/-1">Hoa hồng hoàn vốn</option>  -->
                        <option value="3/100">Hoa hồng lên cấp</option>  
                        <option value="2/100">Hoa hồng phần trăm đồng hưởng công ty</option> 
                        <option value="31/100">Hoa hồng hoàn vốn</option> 
                    </select>
                </li>
                <li>
                    <a class="btn btn-default btn-sm" id="btn-view-award">Xem</a>
                </li>
                <li><hr/></li>
                <li><b>Tổng hoa hồng:</b> <i>0 VND</i></li>   
                </c:if>    

            <!--<li>
                <a class="btn-change-content" controller="<c:url value="/Report/AwardTotal" />">Tổng thu nhập theo tháng</a>
            </li>-->
        </ul>
    </div>
</div>--%>