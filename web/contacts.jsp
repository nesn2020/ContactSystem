<%--
  Created by IntelliJ IDEA.
  User: Cyh-yyds
  Date: 2023/11/2
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>个人通讯录系统</title>
</head>
<body>
<h1>个人通讯录</h1>
<table border="1">
    <tr>
        <th>姓名</th>
        <th>住址</th>
        <th>电话</th>
        <th>操作</th>
    </tr>
    <c:forEach var="contact" items="${contacts}" varStatus="status">
        <tr>
            <td>${contact.name}</td>
            <td>${contact.address}</td>
            <td>${contact.phone}</td>
            <td>
                <a href="${pageContext.request.contextPath}/contacts?action=edit&index=${status.index}">编辑</a>
                <a href="${pageContext.request.contextPath}/contacts?action=delete&index=${status.index}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>添加联系人</h2>
<form action="${pageContext.request.contextPath}/contacts" method="post">
    <input type="hidden" name="action" value="add">
    <label for="name" id="name">姓名:</label>
    <input type="text" name="name" required><br>
    <label for="address" id="address">住址:</label>
    <input type="text" name="address" required><br>
    <label for="phone" id="phone">电话:</label>
    <input type="text" name="phone" required><br>
    <button type="submit">添加</button>
</form>
</body>
</html>

