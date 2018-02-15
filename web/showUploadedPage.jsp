<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Type of xml</title>
</head>
<body>
<div>
    <table border="1">
        <tr>
            <td>type</td>
            <td>name</td>
            <td>country</td>
            <td>depositor</td>
            <td>accountId</td>
            <td>amountOnDeposit</td>
            <td>profitability</td>
        </tr>

        <c:forEach items="${entities}" var="entity">
            <tr>
                <td>${entity.type}</td>
                <td>${entity.name}</td>
                <td>${entity.country}</td>
                <td>${entity.depositor}</td>
                <td>${entity.accountId}</td>
                <td>${entity.amountOnDeposit}</td>
                <td>${entity.profitability}</td>
            </tr>
        </c:forEach>

    </table>
</div>
<div>
    <table>
        <tr>
            <c:forEach items="${pages}" var="page">
                <td>
                    <form action="page" method="GET">
                        <input type="hidden" name="command" value="getPage">
                        <input type="hidden" name="number" value=${page}>
                        <input type="submit" value=${page}>
                    </form>
                </td>
            </c:forEach>
        </tr>
    </table>
</div>

</div>
</body>
</html>