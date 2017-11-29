<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>iframe方式实现无刷新界面上传文件</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
//使用创建的方式
function create(){
	var iframe = document.createElement("iframe");
    iframe.width = 0;
    iframe.height = 0;
    iframe.border = 0;
    iframe.name = "fileUpload";
    iframe.id = "form-iframe";
    iframe.setAttribute("style", "width:0;height:0;border:none");
    return iframe;
}
function formtj(){
	var iframe=create();
	//放到document
    $("form")[0].appendChild(iframe);
    $("form")[0].target = "fileUpload";
    iframe.onload = function(){
        //获取iframe的内容，即服务返回的数据
        var responseData = this.contentDocument.body.textContent || this.contentWindow.document.body.textContent;
        console.log(responseData);
        //删掉iframe
        setTimeout(function(){
            $("#form-iframe").remove();
        }, 100);
        //如果提示submit函数不存在，请注意form里面是否有id/value为submit的控件
        $("form")[0].submit();
    }
}
$(function(){
	
})
</script>
</head>
<body onsubmit="formtj();">
<form method="post" action="${pageContext.request.contextPath }/H5Upload" enctype="multipart/form-data" 
name="fileForm" onsubmit="return false;">
    <input type="file" class="fileInput" name="fileInput">
    <input id="_submit" type="submit" value="提交" />
<!--     <iframe name="fileUpload" style="width:0;height:0;border:none"></iframe> -->
</form>
</body>
</html>