<%-- Messages 处理--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script>
<c:if test="${not empty messages}">
    <c:forEach var="msg" items="${messages}">      
        amsg += "<c:out value="${msg}"/>" + "\n"
    </c:forEach>
    <c:remove var="messages" scope="session"/>
    showInfo(amsg);
</c:if>
</script>
