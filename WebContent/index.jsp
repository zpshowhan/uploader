<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>普通方式实现上传进度加载</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(function(){
	var docfile = document.getElementById("in-file");
	var form =$("#dataForm");
	var pross =$("#pross");
	var file =$("#in-file");
	var into=0;
	$("#submit").click(function(event) {
		      //阻止默认事件
		      event.preventDefault();
		      //循环查看状态
		       var t = setInterval(function(){
		           $.ajax({
		            url: '${pageContext.request.contextPath }/progress',
		           	type: 'POST',
		            dataType: 'json',
		             data: {
		               filename: file[0].files[0].name,
		            },
		            success: function (responseText) {
		                var data = eval(responseText);
		                into=data.progress;
		                //前台更新进度
		               // pross.css("width",parseInt((data.progress / data.size) * 100)+"%");
		                	
		                pross.html(parseInt((data.progress / data.size) * 100));
		            },
		             error: function(){
		                 console.log("error");
		              }
		          });
		       }, 200);
		       //上传文件
		      $.ajax({
		           url: '${pageContext.request.contextPath }/upload',
		           type: 'POST',
		           dataType: 'text',
		           data: new FormData(form[0]),
		           processData: false,
		           contentType: false,
		           success: function (responseText) {
		               //上传完成，清除循环事件
		               clearInterval(t);
		              //将进度更新至100%
		             // pross.css("width","100%");
		               pross.html("100");
		         },
		           error: function(){
		             console.log("error");
		          }
		      });
		       return false;
		    });
	
});
</script>
<style type="text/css">
 #pross:after {
	content: '%';
/* 	background-color: black; */
} 
</style>
</head>
<body>
<form method="post" enctype="multipart/form-data" id="dataForm">
	<input name="file" type="file" id="in-file">
</form>
	<div style="width: 1000px;height: 20px;">
		<div id="pross" style="height: 100%;width: 100px;"></div>
	</div>
	<button type="button" id="submit">上传</button>
</body>
</html>