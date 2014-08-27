<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=1, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="../public/css/common${compressSuffix}.css?${version}" />
<link rel="stylesheet" type="text/css" href="../public/css/f10${compressSuffix}.css?${version}" />
<style type="text/css" media="all">
#wrapper {
	position:absolute; z-index:1;
	top:71px; bottom:0; left:0;
	width:100%;
	overflow:auto;
}
#scroller {
	position:absolute; z-index:1;
/*	-webkit-touch-callout:none;*/
	-webkit-tap-highlight-color:rgba(0,0,0,0);
	width:100%;
	padding:5px;
	
}
.nav_l{ overflow:hidden; height:35px; font-weight:bold; background:#1f87bd}
.nav_l a.first{ background:#0964AB; color:#fff;   }
.nav_l a{ font-size:14px; color:#90d9fc ;  display:inline-block; width:20%; float:left; text-align:center;height:35px; line-height:35px;}
#wrapper{ color:#000;}
    pre{  
    white-space:pre-wrap;  
    white-space:-moz-pre-wrap;  
    white-space:-pre-wrap;  
    white-space:-o-pre-wrap;  
    word-wrap:break-word;  
    } 
.aling{ text-align:justify;
                text-justify:distribute-all-lines;/*ie6-8*/
                text-align-last:justify;/* ie9*/
                -moz-text-align-last:justify;/*ff*/
                -webkit-text-align-last:justify;/*chrome 20+*/
				line-height:15px
				} 
#content{
	font-size:14px;
}
</style>
<title>F10</title>
</head>
<body>
        <div class="nav_l current" style="border-bottom:solid 1px #0964AB">
             <a class="first" data_id="GSGK" secucode="1"  href="#" onclick="shownews(this);" id="new_menu">公司概况</a>
             <a class="" data_id="CWZB" secucode="1" href="#" onclick="shownews(this);" id="new_menu">财务指标</a>
             <a class="" data_id="ZYGC" secucode="1" href="#" onclick="shownews(this);" id="new_menu">主营构成</a>
             <a class="" data_id="ZYGD" secucode="1" href="#" onclick="shownews(this);" id="new_menu">主要股东</a> 
              <a class="" data_id="GDRS" secucode="1" href="#" onclick="shownews(this);" id="new_menu">股东人数</a> 
             
        </div>
        <div class="nav_l">
             <a  class="1" data_id="GBJG" secucode="1" href="#" onclick="shownews(this)" id="new_menu">股本结构</a>
             <a class="" data_id="FHKG" secucode="1" href="#" onclick="shownews(this);" id="new_menu">分红扩股</a>
             <a class="" data_id="GATJ" secucode="1" href="#" onclick="shownews(this);" id="new_menu">投资评级</a>
             
          <a class="" data_id="GGTS" secucode="1" href="#" onclick="shownews(this);" id="new_menu">公告提示</a>
             <a class="" data_id="XXLD" secucode="1" href="#" onclick="shownews(this);" id="new_menu">信息雷达</a>
        </div>

	<div id="wrapper">
		<div id="scroller">
	        <div id="content" width="100%" border="0">
			</div>
	  </div>
	</div>

</body>

<script type="text/javascript" src="../public/js/zepto.min.js?${version}"></script>
<script>
var stockCode="${code}";
$(function(){
	// 初试化函数
		function initPage(){
			getData(stockCode,"GSGK");
		}
		initPage();
});
function shownews(opt){
       $(".first").attr("class",'');
       $(opt).attr("class",'first');
     	//console.log(myscroll);	
   var page_newid = $(opt).attr("data_id");  
       getData(stockCode,page_newid);
}
function getData(code,subject){
	$.ajax({
		data: {
			code:code,
			subject:subject
		},
		url:"getFSData.do",
		type: 'get',
		dataType: 'json',
		beforeSend: function(){
		},
		success: function(data){
			//console.log("data:"+data.fsData);
			var dataString=data && data.fsData;
			if(null!=dataString&&dataString!=""){
				$("#content").html(dataString);
			}
		},
		complete: function(){
			//alert("complete");
		}
	});
}
</script>
</html>
