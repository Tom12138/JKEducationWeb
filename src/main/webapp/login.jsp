<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<head>
  <meta charset="UTF-8">
  <title>Login Page | Amaze UI Example</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="alternate icon" type="image/png" href="static/i/favicon.png">
  <link rel="stylesheet" href="static/css/amazeui.min.css"/>
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
  <base href="<%=basePath%>">  
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>HEHE</h1>
    <p>Integrated Development Environment<br/>代码编辑，代码生成，界面设计，调试，编译</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <h3>登录</h3>
    <hr>
    <div class="am-btn-group">
      <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
      <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
      <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
    </div>
    <br>
    <br>

	<form class="am-form" action="<%=basePath%>j_spring_security_check" method="post">
		<label for="email">用户名：</label>
		<input type="text" name="j_username"  />
		<br>
		<label for="password">密码：</label>
		<input type="password" name="j_password" />
		<br>
		<label for="remember-me">
		<input type='checkbox' name='_spring_security_remember_me'/>记住我
		</label>
		<br/>  
		<div class="am-cf">
		<input type="submit" name="log" value="登录" class="am-btn am-btn-primary am-btn-sm am-fl">
		&nbsp;&nbsp;
		<a href = "register/admin" class="am-btn am-btn-primary am-btn-sm am-fr">注册</a>
		</div>
	</form>
   <hr>
    <p>© 2014 AllMobilize, Inc. Licensed under MIT license.</p>
  </div>
</div>
</body>
</html>
