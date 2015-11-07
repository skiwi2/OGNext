<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Spy reports - OLog</title>
</head>
<body>
    <p><b>Spy Reports</b></p>
    <g:each in="${spyReports}">
        ${it.key}<br />
    </g:each>
</body>
</html>