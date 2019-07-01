<%--
  Created by IntelliJ IDEA.
  User: Johnson
  Date: 2019/7/1
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UploadPic</title>
</head>
<body>
<br>
<form enctype="multipart/form-data" method="post" id='formBox'
      name="form" action="${pageContext.request.contextPath}/food/reggg">
    <input type="file" id="img" name="img" alt="img file">
    <input type="submit" value="Upload">

</form>

</body>
</html>
