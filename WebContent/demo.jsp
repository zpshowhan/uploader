<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>html5 文件上传 支持进度条加载</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
var fd=new FormData();

function fileSelected() {
    var file = $('#fileToUpload')[0].files[0];
    fd.append("file",file);
    if (file) {
      var fileSize = 0;
      if (file.size > 1024 * 1024)
        fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
      else
        fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

      $('#fileName').html('Name: ' + file.name);
      $('#fileSize').html('Size: ' + fileSize);
      $('#fileType').html('Type: ' + file.type);
   		// 创建一个FileReader对象
      var reader = new FileReader();

      // 绑定load事件
      reader.onload = function(e) {
         console.log(e.target.result);
         
      	$(".file-show").append("<div id='"+new Date().toLocaleString()+"' ><img style='width: 100px;height: 100px;' src='"+this.result+"'> <h4>"+file.name+"</h4> <p>"+fileSize+"</p></div>  ")
      	}
      //绑定中断方法
      reader.onabort=function(e){
    	  console.log(e.target.result);
      }
      //绑定出错方法
      reader.onerror=function(e){
    	  console.log(e.target.result);
      }
      //绑定正在读取方法
      reader.onprogress=function(e){
    	  console.log(e.target.result);
      }
      //绑定读取开始方法
      reader.onloadstart=function(e){
    	  console.log(e.target.result);
      }
      //绑定读取结束方法
      reader.onloadend=function(e){
    	  console.log(e.target.result);
      }
      
      reader.readAsDataURL(file);
//       reader.readAsBinaryString(file);
//       reader.readAsText(file);  
//       reader.abort();
    }
  }
function uploadFile() {
    /* var fd = new FormData();
    fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]); */
    
    
    var xhr = new XMLHttpRequest();
    xhr.upload.addEventListener("progress", uploadProgress, false);
    xhr.addEventListener("load", uploadComplete, false);
    xhr.addEventListener("error", uploadFailed, false);
    xhr.addEventListener("abort", uploadCanceled, false);
    xhr.open("POST", "${pageContext.request.contextPath }/uploader");
    xhr.send(fd);
    //===========
    /* $.ajax({
        url:"${pageContext.request.contextPath }/uploader",
        type:"post",
        data:fd,
        async: false,  
        cache: false,  
        processData: false,
        beforeSend:function(){},
        success:function(data){
        	var json=eval(data);
        		
        },
        error:function(){
			alert("上传失败");
		}
    }); */
    //====================
  }
function uploadProgress(evt) {
    if (evt.lengthComputable) {
      var percentComplete = Math.round(evt.loaded * 100 / evt.total);
      $('#progressNumber').html(percentComplete.toString() + '%');
    }
    else {
    	$('#progressNumber').html('unable to compute');
    }
  }

  function uploadComplete(evt) {
    /* This event is raised when the server send back a response */
    alert(evt.target.responseText);
    fd=new FormData();
  }

  function uploadFailed(evt) {
    alert("There was an error attempting to upload the file.");
  }

  function uploadCanceled(evt) {
    alert("The upload has been canceled by the user or the browser dropped the connection.");
  }
  $(function(){
	  $("#add").unbind('click').click(function(){
		  $("#fileToUpload").click();
	  });
  });
</script>
</head>
<body>
<form id="form1" enctype="multipart/form-data" method="post">
<div class="row file-show">
      <label for="fileToUpload">Select a File to Upload</label><br/>
    </div>
<div id="fileName"></div>
<div id="fileSize"></div>
<div id="fileType"></div>
<div id="progressNumber"></div>
</form>
<div class="row">
<button id="add" style="width: 60px;height: 30px;border: 1px solid black;">添加</button>
<br/>
<br/>
<input type="button" onclick="uploadFile()" value="Upload" />
<input style="display: none;" type="file" name="file" id="fileToUpload" onchange="fileSelected();">
    </div>
</body>
</html>