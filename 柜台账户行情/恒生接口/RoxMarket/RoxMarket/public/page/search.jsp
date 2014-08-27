<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name='viewport' content='width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no'>
	<meta name="format-detection" content="telephone=no">
	<title>股票查询</title>
    <link rel="styleSheet" type="text/css" href="/RoxMarket/public/css/keyboard${compressSuffix}.css?${version}">
</head>

<style type="text/css">
  *{
  margin: 0;
  font-size: 14px;
  font-family: 微软雅黑;
  -webkit-tap-highlight-color: rgba(255, 255, 255, 0) !important;
  -webkit-focus-ring-color: rgba(255, 255, 255, 0) !important;
  outline: none !important;
  }
  html, body{
  width: 100%;
  background: #171a1f;
  color: white;
  }
  .search{
  /*background: none repeat scroll 0 0 rgba(101, 102, 110, 0.63);*/
  height: 50px;
  left: 0;
  position: fixed;
  right: 0;
  top: 0;
  z-index: 10;
  }
  .search:before {
  background: url("/RoxMarket/public/images/icons.png") no-repeat 0px 0px rgba(0, 0, 0, 0);
  background-size: 91px;
  bottom: 0;
  content: "";
  height: 20px;
  width: 20px;
  left: 10px;
  margin: auto;
  position: absolute;
  top: 0;
  }
  .search-overlay{
  height: 40px;
  left: 0;
  position: fixed;
  right: 0;
  top: 0;
  z-index: 1;
  background: #171a1f;
  }
  .search>div{
  bottom: 0;
  left: 40px;
  position: absolute;
  right: 76px;
  top: 0;
  border-right: 1px solid #000000;
  }
  .search input{
  border: 0 none;
  bottom: 0;
  height: 30px;
  left: 0;
  margin: auto;
  padding: 0;
  position: absolute;
  right: 0;
  top: 0;
  width: 100%;
  background: transparent;
  color: white;
  font-size: 18px;
  text-indent: 10px;
  -moz-user-select: none;
  -webkit-user-select: none;
  -ms-user-select: none;
  }
  .table{
  padding: 0 5px;
  position: absolute;
  top: 50px;
  left: 0;
  right: 0;
  overflow: auto;
  }
  table{
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;

  }
  tr{
  border-bottom: 1px solid black;
  height: 36px;
  }

  tr:first-child {
  border-top: 1px solid #000000;
  }
  td{
  width: 33.333%;
  height: 35px;
  }
  td:first-child{
  text-align: left;
  }
  td:nth-child(2){
  text-align: center;
  }
  td:last-child{
  text-align: right;
  }
  .collected {
  background: url("/RoxMarket/public/images/icon.png") no-repeat scroll -292px 0 rgba(0, 0, 0, 0);
  display: inline-block;
  height: 30px;
  margin-top: 1px;
  width: 30px;
  }
  .search .reset{
  background: url("/RoxMarket/public/images/icon.png") no-repeat scroll -252px 3px transparent;
  height: 36px;
  position: absolute;
  right: 0;
  width: 36px;
  }
  .search .back {
  font-size: 18px;
  line-height: 50px;
  margin: auto;
  position: absolute;
  right: 20px;
  left: auto;
  top: 0;
  text-decoration: none;
  color: white;
  display: inline-block;
  }
  @media only screen and (max-height: 480px){
  .search{
  height: 40px;
  }
  .table{
  top: 40px;
  }
  .back{
  line-height: 40px;
  }
  }
</style>
<body>
	<div class="search">
		<div>
			<input  type='text' placeholder='股票代码/拼音/简称' id='search-input'  readonly='readonly'/>

		</div>
		<div class="back">返回</div>
		
	</div>
	<div class="search-overlay"></div>
	<div class='table' >
		<table>
			<!-- <tr>
				<td>600001</td>
				<td>浦发银行</td>
				<td><span class="collected"></span></td>
			</tr> -->
			<tr><td style="text-align:center">没有数据</td></tr>
		</table>
	</div>


	<div class='body'>
		<div class="keybord-content">
			<div class="tickets-code w20th">
				<ul>
					<li><div>600</div></li>
					<li><div>601</div></li>
					<li><div>000</div></li>
					<li><div>002</div></li>
					<li><div>300</div></li>
				</ul>
			</div>
			<div class='keyboard w80th'>
				<ul class='w4th-child'>
					<li><div>1</div></li>
					<li><div>2</div></li>
					<li><div>3</div></li>
					<li><div class='blue default delete'>DEL</div></li>
				</ul>
				<ul class='w4th-child'>
					<li><div>4</div></li>
					<li><div>5</div></li>
					<li><div>6</div></li>
					<li><div class='blue default hide-keyboard'>隐藏</div></li>
				</ul>
				<ul class='w4th-child'>
					<li><div>7</div></li>
					<li><div>8</div></li>
					<li><div>9</div></li>
					<li><div class='blue default reset'>清空</div></li>
				</ul>
				<ul class='w3rd-child'>
					<li><div class='blue default switch-keyboard'>ABC</div></li>
					<li><div>0</div></li>
					<li><div class='blue default'>搜索</div></li>
				</ul>

			</div>
		</div>
	</div>
	<div class='body' >
		<div class="keybord-content">
			
			<div class='keyboard w100th'>
				<ul class='w10th-child'>
					<li><div>Q</div></li>
					<li><div>W</div></li>
					<li><div>E</div></li>
					<li><div>R</div></li>
					<li><div>T</div></li>
					<li><div>Y</div></li>
					<li><div>U</div></li>
					<li><div>I</div></li>
					<li><div>O</div></li>
					<li><div>P</div></li>
				</ul>
				<ul class='w9th-child w95th'>
					<li><div>A</div></li>
					<li><div>S</div></li>
					<li><div>D</div></li>
					<li><div>F</div></li>
					<li><div>G</div></li>
					<li><div>H</div></li>
					<li><div>J</div></li>
					<li><div>K</div></li>
					<li><div>L</div></li>
					
				</ul>
				<ul class='w9th-child'>
					<li><div id='jiantou' class='default'>↑</div></li>
					<li><div>Z</div></li>
					<li><div>X</div></li>
					<li><div>C</div></li>
					<li><div>V</div></li>
					<li><div>B</div></li>
					<li><div>N</div></li>
					<li><div>M</div></li>
					<li><div class='blue default delete' style='letter-spacing:normal'>DEL</div></li>
				</ul>
				<ul class='w3rd-child'>
					<li><div class='blue default switch-keyboard'>123</div></li>
					<li><div class='space default'>SPACE</div></li>
					<li><div class='blue default'>搜索</div></li>
				</ul>

			</div>
		</div>
	</div>
</body>


<script type='text/javascript' src='/RoxMarket/public/js/zepto.min.js?${version}'></script>
<script type="text/javascript" src='/RoxMarket/public/js/zepto.expand${compressSuffix}.js?${version}'></script>
<script>
	// document.cookie = 'ticketpcodes=sdd11111ws;expires=1;path=/';
	// $.cookie('the_cookie', 'the_value11111', {expires: 7, path: '/'});
	// console.log($.cookie('the_cookie'));
	$(function(){
		var $pageObject = {
			options:{
				cp: 1,
				key: "",
				size: 20
			},
			init: function(){
				this.bindAllEvents();
			},
			bindAllEvents: function(){
				var self = this, timer;
				// 搜索框获取焦点事件
				$('#search-input').bind({
					focus:function(e){
						e.preventDefault();
						self._toggleKeyboard();
						this.blur();
					},
					valueChange:function(changeCondition){
						var _this = this;
						if(changeCondition !== false){
							$.extend($pageObject.options, {cp:1});
						}
						if(timer){
							window.clearTimeout(timer);
						}
						if(this.value.length>0){
							
							timer = window.setTimeout(function(){
								self.getTicketsByAjax({key: _this.value}, changeCondition);
							}, 1000);
						}else{
							$('.table table').html('<tr><td style="text-align:center">没有数据</td></tr>');
						}
					}
				});
				this._bindKeyboardEvents();
				//隐藏键盘
				$('.table').addClickEvent(function(e){
						
					$('.body').hide();
					
				});
				//添加自选股
				
				$('.table').delegateClick('.collected', function(e){
					
				    window.location = '/RoxMarket/detail/view.do' + '?code=' + $(this).attr('pcode');
					//取消添加自选股
					/*
					var cookie = $.cookie('ticketpcodes');
					$(this).removeClass('collected').html('已添加');
					if(cookie.length > 0){
						cookie += ','+$(this).attr('pcode');
					}else{
						cookie = $(this).attr('pcode');
					}
					
					$.cookie('ticketpcodes', cookie, null, '/');
					*/
					
				}).delegateClick('tr', function(e){
					timeStart = new Date();

				},function(e){
					timeEnd = new Date();

					e = e.touches&&e.touches[0]||e;
					
					//if(e.target.nodeName.toLowerCase() ==='span') return ;
						
					
					if(timeEnd - timeStart < 140 && $(this).attr('pcode')){
					    window.location = '/RoxMarket/detail/view.do' + '?code=' + $(this).attr('pcode');
					}
				});
				
				// 绑定滚动加载
				$(window).bind({
					scroll: function(e){
						var options = $pageObject.options,
							scrollTop = document.documentElement.scrollTop || document.body.scrollTop,
							scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight,
							clientHeight = document.documentElement.clientHeight || document.body.clientHeight
						// console.log(scrollTop, scrollHeight, clientHeight);
						if( scrollTop + clientHeight === scrollHeight){

							$pageObject.getTicketsByAjax({cp: options.cp+1}, false);
						}
						

						
					}
				});
                //从detail.html点击进来有事件重叠
				setTimeout(function () {
				    $('.back').bind({
				        click: function () {
				            window.location.href = '/RoxMarket/';
				        }
				    });
				}, 500);
			},
			getTicketsByAjax: function(condition, changeCondition){

				var condition = $.extend(this.options, condition);
				$.ajax({
					type: 'post',
					url: '/RoxMarket/search/search.do?',
					data: condition,
					dataType: 'json',
					success: function(data, status, xhr){
						var trTpl = '<tr pcode="$pcode">'
										+'<td>$code</td>'
										+'<td>$name</td>'
										+'<td><span class="$class" pcode="$pcode">$text</span></td>'
									+'</tr>',
							temp = '';
						if(data&&data.items){
							for(var i=0,max=data.items.length; i<max; i++){
								temp += trTpl.replace(/\\$code/, data.items[i].code).replace(/\\$name/, data.items[i].name)
								.replace(/\\$pcode/g, data.items[i].pcode)
								.replace(/\\$class/, 'collected')
								.replace(/\\$text/, '');
								/*
								.replace(/\\$class/, inCookie(data.items[i].pcode) === -1? 'collected':'')
								.replace(/\\$text/, inCookie(data.items[i].pcode) === -1?'':'已添加');*/
							}
							if( changeCondition === false){
								$(temp).appendTo('.table table');
							}else{
								$('.table table').html(temp);
								document.documentElement.scrollTop = 0;
								document.body.scrollTop = 0;
							}
							
						}else if( changeCondition !== false){
							$('.table table').html('<tr><td style="text-align:center">没有数据</td></tr>');
						}
					},
					error: function(){

					}
				});

				function inCookie(str){
					// var cookie = localStorage.ticketpcodes || '';
					// return cookie.indexOf(str);
					var cookie = $.cookie('ticketpcodes') || '' ;//document.cookie || '';
					return cookie.indexOf(str);

				}
			},
			
			_bindKeyboardEvents:function(){
				var searchInput = $('#search-input');

				$('li div').bind({
					'touchstart': function(e){
						$(this).addClass('mousedown');
					},
					'touchend': function(){
						$(this).removeClass('mousedown');
					},
					'mousedown': function(e){
						$(this).addClass('mousedown');
					},
					'mouseup': function(){
						$(this).removeClass('mousedown');
					},
					'mouseout': function(){
						$(this).removeClass('mousedown');
					}
				});
				// 点击键盘，数字或者英文字母
				$('.keybord-content li>div:not(.default)').addClickEvent(function(){
						var self = this;
						searchInput.val(function(){

							if($(self).closest('.keybord-content').hasClass('lowercase')){

								return $(this).val()+$(self).html().toLowerCase();
							}
								
							return $(this).val()+$(self).html();
						}).trigger('valueChange');
					
				});
				// delete事件
				$('.delete').addClickEvent(function(){
						
					searchInput.val(searchInput.val().slice(0, -1)).trigger('valueChange');
				});
				//空格space
				// $('.space').bind({
				// 	'click': function(){
				// 		return ;
				// 		var self = this;
				// 		searchInput.val(function(){
				// 			return $(this).val()+' ';
				// 		});
				// 	}
				// });
				// 大小写转换
				$('#jiantou').addClickEvent(function(){
					$(this).closest('.keybord-content').toggleClass('lowercase');
				});

				// 清空
				$('.reset').addClickEvent(function(){
					searchInput.val('');
					$('.table table').html('<tr><td style="text-align:center">没有数据</td></tr>');
				});
				// 隐藏键盘
				$('.hide-keyboard').addClickEvent(function(){
					$(this).closest('.body').hide();
					
				});
				// 键盘切换
				$('.switch-keyboard').addClickEvent(function(){
					$('.body').show();
					$(this).closest('.body').hide();
					
				});
			},
			// 隐藏和显示键盘
			_toggleKeyboard: function(){
				$('.body').eq(0).show();
			},
			scrollToFresh: function(){
				// $('')
			}

		};

		$pageObject.init();
		$('#search-input').focus();
	});
	

	function delegateClick(selector, callback, callback1){
		var callback1 = callback1 || callback;
		if('ontouchstart' in window){
			$(this).delegate(selector, 'touchstart', callback);
			$(this).delegate(selector, 'touchend', callback1);
		}else{
			$(this).delegate(selector, 'mousedown', callback);
			$(this).delegate(selector, 'click', callback1);
		}
	}

	$.extend($(window).__proto__, {

		
		delegateClick : function(selector, callback, callback1){
			return this.each(function(){

				delegateClick.apply(this, [selector, callback, callback1]);
			});
		}
	});


	
</script>	
</html>