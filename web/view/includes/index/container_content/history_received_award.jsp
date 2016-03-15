<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="/WEB-INF/tlds/functions.tld" %>
<h1>Thống kê thưởng</h1>
<div class="panel">
    <div class="panel-heading">
        <h2 class="blueColor">Lịch sử nhận thưởng</h2>
    </div><!-- end panel heading -->
    <div class="panel-body">
        <div class="ajax-content">
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th class="redColor">Kiểu thưởng</th>
                        <th class="redColor">Ngày</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${f:reportHistoryReceiveAwardForCustomer(sessionScope['CUSTOMER_ID'])}" var="ha">
                        <tr>
                            <td><b>${ha[0]}</b></td>
                            <td style="color: blue;text-align: center">${f:formatTime(ha[1])}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div><!-- end panel-->