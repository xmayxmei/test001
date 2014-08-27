<!DOCTYPE HTML>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<title>资讯</title>
	<link rel="stylesheet" href="/RoxMarket/public/css/news${compressSuffix}.css?${version}" />
    <link href="/RoxMarket/public/css/bottom${compressSuffix}.css?${version}" rel="stylesheet" />
	<style type='text/css'>
		.layer{
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			background: rgba(0,0,0,0.3);
			display: none;
			z-index: 30;
		}
		.load-img{
			position: absolute;
			margin: auto;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
		}
        .slide-foot {
            position: absolute;
            left: 0;
            right: 0;
            bottom: 0;
            background: #1A2027;
            z-index: 2;
        }
        
        .footer .bt {
            font-size:14px;
        }
		.footer>div {
			width: 33.33%;
		}
	</style>
</head>
<body>
	<div class="container">
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='CLYPJ'><h3 class='n-title'>策略与评级</h3></a>
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='1'><h3 class='n-title'>特别提示</h3></a>
			<ul class='sub-type'>
				<li><a href="javascript:;" ntype='3' subject='YWTS'>要闻提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='TPTS'>停牌提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='XSSS'>限售上市</a></li>
				<li><a href="javascript:;" ntype='3' subject='JRTBTS'>今日特别提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='XGFXSS'>新股发行上市</a></li>
				<li><a href="javascript:;" ntype='3' subject='JJTS'>基金提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='ZQTS'>债券提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='ZFPG'>增发配股</a></li>
				<li><a href="javascript:;" ntype='3' subject='YJPL'>业绩披露</a></li>
				<li><a href="javascript:;" ntype='3' subject='YDTS'>异动提示</a></li>
				<li><a href="javascript:;" ntype='3' subject='MRTS'>明日提示</a></li>
				
			</ul>
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='GASD'><h3 class='n-title'>港澳视点</h3></a>
			
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='GSGG'><h3 class='n-title'>公司公告</h3></a>
			
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='1'><h3 class='n-title'>海内外财经</h3></a>
			<ul class='sub-type'>
				<li><a href="javascript:;" ntype='3' subject='GNCJ'>国内财经</a></li>
				<li><a href="javascript:;" ntype='3' subject='GJCJ'>国际财经</a></li>
			</ul>
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='QQGZ'><h3 class='n-title'>全球股指</h3></a>
			
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='1'><h3 class='n-title'>新闻与研究</h3></a>
			<ul class='sub-type'>
				<li><a href="javascript:;" ntype='3' subject='GSYJ'>公司研究</a></li>
				<li><a href="javascript:;" ntype='3' subject='GSBD'>公司报道</a></li>
			</ul>
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='ZJLS'><h3 class='n-title'>众家论市</h3></a>
			
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='ZLZZ'><h3 class='n-title'>主力追踪</h3></a> 
			
		</div>
		<div class='row'>
			<a href='javascript:;' ntype='3' subject='ZQYW'><h3 class='n-title'>证券要闻</h3></a> 
			
		</div>
        <div style="height:41px;"></div>
	</div>
	<div class="slide show">
		<div class="slide-head">
			<a href="javascript:;" class='slide-hide'></a>
			<h3></h3>
			<!--a href="/RoxMarket/public/page/newscontent.html" class="go-search"></a-->
		</div>
		<div class="slide-body">
			
		</div>
        
	</div>
	<footer class="footer">
        <div><div class='zixuan' onclick="window.location.href = '/RoxMarket/index.do#1////1/1';"><span class="icon"></span><span class="bt">自选</span></div></div>
        <div><div class='market' onclick="window.location.href = '/RoxMarket/index.do#0/-2/-2//1/1';"><span class="icon"></span><span class="bt">行情</span></div></div>
        <div><a href='/RoxMarket/news/newslist.do' class='zhzx selected'><span class="icon"></span><span class="bt">资讯</span></a></div>
    </footer>
	<div class='newscontent'>
		<div class="newscontent-head">
			<a href="javascript:;" onclick='hidenewscontent();' class='go-back'></a>
			<h3 style='padding-left: 20px;'></h3>
		</div>
	
		<div class='newscontent-body'>
			<h3 id='t-title' class='n-item' style='white-space:normal;line-height: 2;color:#666;'></h3>
			<pre class='n-text n-item'>
			
			</pre>
		</div>
	</div>
	<div class='layer'>
		<img class='load-img' src='/RoxMarket/public/images/loading.gif'>
	</div>

	<script type="text/javascript" src='/RoxMarket/public/js/jquery-2.1.1.min.js?${version}'></script>
	<script type="text/javascript">
		
		var url = '/RoxMarket/news/getNewsData.do', oNews, ready = false, timer, AJAX;
		oNews = {}; // sessionStorage不支持引用类型数据
		$.extend(oNews, {
			head: null, // 头指针
			tail: null, // 尾指针
			maxSize: 30, //最大容量
			currentSize: 0, // 当前容量
			bufObject: { //缓存数据结构
				/*key1: {prev: null,data: {},next: 'key2',name: 'key1'},
				key2: {prev: 'key1',data: {},next: null, name: 'key2'}*/
			}, 
			initSize: function(size){
				this.maxSize = size;
			},
			add: function(key, value){
				//return;
				if(!value) return;
				var target = this.bufObject[key], prevObj, nextObj;
				
				if( this.maxSize < 0 ) return ;
				
				if(this.currentSize == 0){
					this.bufObject[key] = {prev: null, data: value, next: null, name: key};
					this.head = this.tail = this.bufObject[key];
					this.currentSize = 1;
				
				}else if( this.currentSize < this.maxSize ){
					
					// 如果这个数据原本不存在，则添加到最后
					if(!target){
						// 找到尾节点
						prevObj = this.tail;
						// 把这个节点的prev指向原来的尾节点
						this.bufObject[key] = {prev: prevObj, data: value, next: null, name: key};
						// 把原来尾节点的next指向现在的尾节点
						prevObj.next = this.bufObject[key];
						// 重置尾指针
						this.tail = this.bufObject[key];
						
						this.currentSize += 1;
						
					}else{// 如果存在，把这个数据挪到最后
					
						if( this.currentSize == 1 ) return ;
						
						prevObj = target.prev || target;
						
						
						nextObj = target.next || target;
						// 如果目标是第一个
						if( prevObj == target ){
							// 重置head
							this.head = nextObj;
							
							nextObj.prev = null;
							
							this.tail.next = target, target.prev = this.tail, target.next = null;
							// 重置tail
							this.tail = target;
							
						}else if(nextObj != target){// 如果目标不是最后一个
						
							prevObj.next = nextObj, nextObj.prev = prevObj;
							
							this.tail.next = target, target.prev = this.tail, target.next = null;
							// 重置tail
							this.tail = target;
						}
						
						prevObj.next = nextObj, nextObj.prev = prevObj;
						
					
					}
					
				}else{// 当前容量已满
				
					// 如果这个数据原本不存在，则添加到最后,并删除头
					if(!target){
						// 找到尾节点
						tail = this.tail;
						// 把这个节点的prev指向原来的尾节点
						this.bufObject[key] = {prev: tail, data: value, next: null, name: key};
						// 把原来尾节点的next指向现在的尾节点
						tail.next = this.bufObject[key];
						// 重置尾指针
						this.tail = this.bufObject[key];
						// 找到头节点
						head = this.head;
						//释放空间
						delete this.bufObject[head.name]
						// 重置头指针
						this.head = head.next, this.head.prev = null;
						
						
					}else{// 如果存在，把这个数据挪到最后
					
						if( this.currentSize == 1 ) return ;
						
						prevObj = target.prev || target;
						
						
						nextObj = target.next || target;
						// 如果目标是第一个
						if( prevObj == target ){
							// 重置head
							this.head = nextObj;
							
							nextObj.prev = null;
							
							this.tail.next = target, target.prev = this.tail, target.next = null;
							// 重置tail
							this.tail = target;
							
						}else if(nextObj != target){// 如果目标不是最后一个
						
							prevObj.next = nextObj, nextObj.prev = prevObj;
							
							this.tail.next = target, target.prev = this.tail, target.next = null;
							// 重置tail
							this.tail = target;
						}
						
						//prevObj.next = nextObj, nextObj.prev = prevObj;
						
					
					}
				
				}
				//console.log(this.bufObject);
			},
			remove: function(){}
		});
		
		
		$(function(){
			
			$(document).ajaxSend(function(){
				$('.layer').show();
			}).ajaxStop(function(){
				window.setTimeout(function(){
					$('.layer').hide();
				},500)
			});
			$.ajax({
				url: url,
				method: 'get',
				timeout: 60000,
				data: {subject: 'GSGG'}, 
				//dataType: 'json',
				success: function(){
				    _showNewsList(arguments[2].responseText, $('a[subject=GSGG]')); //修改默认栏目
				}
			});
			//一级栏目
			$('.row a').bind('click',function(){
				var $self = $(this),
					$ul = $self.next('ul'),
					ntype = $self.attr('ntype'),
					subject = $self.attr('subject');
				if( ntype=='1' ){
					//$ul.slideToggle();
					$ul.toggleClass('h');
				}else if( ntype=='3' ){
					if(oNews.bufObject[subject]){
						_showNewsList(oNews.bufObject[subject].data, $self)
					}else{
						$.ajax({
							url: url,
							method: 'get',
							timeout: 60000,
							data: {subject: subject}, 
							//dataType: 'json',
							success: function(){
								_showNewsList(arguments[2].responseText, $self);
							}
						});
					}
					
				}else if( ntype=='2' ){
					
					showContent('subject='+$self.attr('subject'), $self.text(), 2);
					//window.loaction.href = '/RoxMarket/public/page/newscontent.html?subject='+$self.attr('subject');
				}
				
			});
  			
			//隐藏滑出板
			$('.slide-hide').bind('click', function(){
				$('.container').removeClass('hide-scroll');
				$('.slide').removeClass('show');
				
			});
			
			
			
		});
		
		function loop(){
			$('.container').addClass('hide-scroll');
			$('.slide').addClass('show');	
		
		}
		function hidenewscontent(){
			$('.newscontent').hide();
			if(!$('.slide').hasClass('show')){
				$('.container').removeClass('hide-scroll');
			}
			
		}
		function showContent(paramstr, title, type){
			if(oNews.bufObject[paramstr]){
				_showContent(oNews.bufObject[paramstr].data, title, type);
			}else{
				$.ajax({
					url: url+'?'+paramstr,
					method: 'get',
					timeout: 60000,
					success: function(){
						var content = arguments[2].responseText.slice(2);
						_showContent(content, title, type);
						if(!oNews.bufObject[paramstr])
							oNews.add(paramstr,content);
					}
				});
			}
			
		}
		function _showContent(content,title, type){
			$('.container').addClass('hide-scroll');
			if(type == '2' ){
				$('.newscontent-head h3').text(title);
				$('#t-title').hide();
			}else{
				$('#t-title').text(title).show();
				$('.newscontent-head h3').text($('.slide-head h3').text());
			}
			$('.newscontent-body .n-text').html(content);
			$('.newscontent').show();
		}
		function _showNewsList(responseText, $self){
			var resultArr, len = 0, item, sarr, html = '', subject = $self.attr('subject'),
				tpl = "<div class='n-item'>"
						+"<a href='javascript:;' n-title='$title' onclick=\"showContent(\'$subParam\', $(this).attr('n-title'))\"><h3>$title</h3></a>"
					+"</div>";
			if(({}).toString.call(responseText) === '[object Array]'){
				resultArr = responseText;
			}
			else{
				if(responseText.split('\t')[0] == 'C'){
					_showContent(responseText.slice(2), $self.text(), 2);
					return;
				}
				resultArr = responseText.split('\n');
			}
			
			len = resultArr.length;

			for(var i=0; i<len; i++){
				item = resultArr[i];
			
				sarr = item.split('\t').slice(1);
				if(sarr.length<1) break;
				html += tpl.replace(/\\$subParam/, sarr[sarr.length-1]).replace(/\\$title/g, sarr.slice(0,-1).join(' '));//jsp会转义
			}
			
			$('.slide-body').html(html);
			
			$('.slide-head h3').text($self.text());
			
			loop();
			if(!oNews.bufObject[subject])
				oNews.add(subject, resultArr.slice(0, i));
				//oNews[subject] = resultArr.slice(0, i);
		}
	</script>
</body>
</html>