<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Info - OLog</title>
</head>
<body>
<p><b>Info</b></p>
Version: ${info.version}<br />
Commit hash: ${info.commitHash}<br />
<g:if test="${info.uncommittedChanges}">
    <br />
    Built with the following modifications:
    <pre>
${info.gitDiff}
    </pre>
</g:if>
</body>
</html>