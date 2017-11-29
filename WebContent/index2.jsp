<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iframe方式实现无刷新界面上传文件</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath }/H5Upload" enctype="multipart/form-data" name="fileForm" target="fileUpload">
    <input type="file" class="fileInput" name="fileInput">
    <input type="submit" value="提交" />
    <iframe name="fileUpload" style="width:0;height:0;border:none"></iframe>
</form>
</body>
</html>