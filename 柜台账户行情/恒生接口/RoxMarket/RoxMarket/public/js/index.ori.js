// document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
var socketChannel = {
	channelGate: null,
	connect: function (host) {
	    try {
	        socketChannel.close();
	        if ('WebSocket' in window) {
	            socketChannel.websocket = new WebSocket(host);
	        } else if ('MozWebSocket' in window) {
	            socketChannel.websocket = new MozWebSocket(host);
	        } else {
	            // alert('Error: WebSocket is not supported by this browser.');
	            return;
	        }
	        socketChannel.websocket.onopen = function () {
	            // console.log('socket open.');
	            // socketChannel.websocket.send(JSON.stringify(pageObject.options.condition));
	        };

	        socketChannel.websocket.onclose = function () {
	            // console.log('socket closed.');
	        };
	        socketChannel.websocket.onmessage = function (message) {
	            // console.log('accept data...');
	            var data = JSON.parse(message.data);
	            //console.log(data);
	            pageObject.renderData(data.list, false);
	            if (data.szList) pageObject.renderSelectedTickets(data.szList);
	        };
	    }
	    catch (e) {
	        socketChannel.close();
	    }
	    finally {

	    }
	},
	initConnection: function(selfSelect){
		var querystr = '', url, condition;

		// selfSelect是否为自选
		if (selfSelect) {
			url = '/RoxMarket/myFollow?';
			condition = pageObject.options.selfCondition;
			querystr = 'size=' + pageObject.options.pageSize + '&';
		} else {
			url = '/RoxMarket/market?';
			condition = pageObject.options.condition;

		}
		// 拼接通道查询参数
		for (e in condition) {
			querystr += e + '=' + condition[e] + '&';
		}
		querystr = querystr.slice(0, -1);
		try {
			socketChannel.connect('ws://' + window.location.host + url + querystr);
		} catch(e) {
			socketChannel.connect('wss://' + window.location.host + url + querystr);
		}
	},
	open: function(){
		if (('MozWebSocket' in window || 'WebSocket' in window) && !$.isAndroidOS()) {
			socketChannel.initConnection(pageObject.options.selfSelect);
		} else {
			//这里不使用setInterval，是因为searchForTargetPageData()有多处入口，比如查询等，都需要清除定时器
			socketChannel.channelGate = window.setTimeout(function() {
				var cp = 1,
				options = pageObject.options;
				if (options.selfSelect) {
					cp = options.selfCondition.cp;
				} else {
					cp = options.condition.cp;
				}
				//查询数据的时候，会再次调用open()，以模拟轮询
				pageObject.searchForTargetPageData({cp: cp}, false, false);
			}, 5000)
		}
	},
	close: function () {
	    try {
	        if (socketChannel && socketChannel.websocket /*&& socketChannel.websocket.readyState == WebSocket.OPEN*/) {
	            socketChannel.websocket.close();
	        } else if (socketChannel && socketChannel.channelGate) {
	            window.clearTimeout(socketChannel.channelGate);
	        }
	    }
	    catch (e) {

	    }
	    finally {
	        socketChannel.websocket = null;
	        socketChannel.channelGate = null;
	    }
	}
};

var pageObject = {
	options: {
		a: '',
		//记录股票的分类名称。如：大盘、沪深等
		refresh: false,
		dataLineHeight: 50,
		//每行数据的高度，用于计算页面最大能存放多少条数据
		//minStockLength: 10,//每页显示的最小股票数量
		// pageSize: 0,
		// currentPage: 0,
		// 查询参数
		pageSize: 0,
		condition: {
			size: 0,
			//加载数据量
			cp: 1,
			//当前页面
			type: '-2',
			//股票类型，默认全部
			order: 1,
			//0升序，1降序
			sortField: '' //排序字段
		},
		selfCondition: {
			cp: 1,
			order: 1,
			sortField: '',
			codeStr: ''
		},
		pageNumber: 1,
		cp: 1,
		//自选股与行情某些参数应该合并
		selfSelect: false,
	    //true为自选，false为市场行情
        market: '-2'
	},
	reSetCondition: function() {
		this.options.market = '-2';
		$.extend(this.options.condition, {
			pageNumber: 1,
			cp: 1,
			//当前页面
			type: '-2',
			//股票类型，默认全部
			order: 1,
			//0升序，1降序
			sortField: '' //排序字段
		});
		$.extend(this.options.selfCondition, {
			cp: 1,
			order: 1,
			sortField: '',
			codeStr: ''
		});
	},
	getParams: function () {
	    var o = this.options, paramObject = common.getHash(['selfSelect', 'market', 'sort', 'order', 'direction', 'page']);
	    if (paramObject.selfSelect == '1') {
	        o.selfSelect = true;
	        if (paramObject.page && !isNaN(paramObject.page) && paramObject.page > 0) 
				o.selfCondition.cp = paramObject.page;
	        if (paramObject.direction && paramObject.direction == '1') 
				o.selfCondition.order = '1';
	        else if (paramObject.direction && paramObject.direction == '0')
				o.selfCondition.order = '0';
	        if (paramObject.order) o.selfCondition.sortField = paramObject.order;
	    }
	    else {
			o.selfSelect = false;
	        if (paramObject.page && !isNaN(paramObject.page) && paramObject.page > 0) 
				o.condition.cp = paramObject.page;
	        if (paramObject.sort) o.condition.type = paramObject.sort;
	        if (paramObject.direction && paramObject.direction == '1') 
				o.condition.order = '1';
	        else if (paramObject.direction && paramObject.direction == '0')
				o.condition.order = '0';
	        if (paramObject.order) o.condition.sortField = paramObject.order;
	        if (paramObject.market) o.market = paramObject.market;
	    }
	},
	setContent: function () {
	    var o = this.options, ticketSort = '', tpl = "<li data-type='$type' style='width:$width' $class>$name</li>";
	    this.getTicketSorts(false);
	    $('.footer>div>div').removeClass('selected');
	    if (!o.selfSelect) {
	        $('.market-title, .selected-ticket').show();
	        $('.ticket-name li').eq(0).html('大盘指数');
	        $('#trend').show();
	        $('#favorite').hide();
	        $(".info-scroller").css('min-width', '900px');
	        $('.market').addClass('selected');
	        $("body").addClass("page-SCHQ"); 
	        $('.market-sort li').removeClass('selected hasChild');
			$('.market-sort li').each(function(){
				if($(this).attr('data-type') === o.market){
					$(this).addClass('selected');
					if($(this).data('childs')){
						$(this).addClass('hasChild');
						data = $(this).data('childs') && $(this).data('childs').split(',') || [];
	
						width = Math.floor(1 / data.length * 10000) / 100;
						tpl = tpl.replace(/\$width/, width + '%');
						for (var i = 0, max = data.length; i < max; i++) {
	
							n = data[i].split('|') || [];
							ticketSort += tpl.replace(/\$name/, n[0]).replace(/\$type/, n[1]).replace(/\$class/, o.condition.type == n[1] ? "class='selected'" : "");
						}
						$('.tickets-sort').html(ticketSort);
					}
				}
			});
			if($('.market-sort li.selected').length == 0)
				$('.market-sort li').eq(0).addClass('selected');
	        $(".market-title").addClass("bottom-line");	        
	        $('#trend .first div').removeClass('selected order-up order-down');
	        if (o.condition.sortField){
	            $('#trend .first div').filter(function () {
	                return $(this).attr('field') == o.condition.sortField;
	            }).attr('order', o.condition.order).addClass('selected').addClass(o.condition.order == '1' ? 'order-down' : 'order-up');
	        }
	    }
	    else {
	        $('.market-title, .selected-ticket').hide();
	        $('.tickets-sort').css({height:0});
	        $('.ticket-name li').eq(0).html('我的自选');
	        $('#trend').hide();
	        $('#favorite').show();
	        $(".info-scroller").css('min-width', '300px');
	        $('.zixuan').addClass('selected');
	        $("body").removeClass("page-SCHQ");
	        $('#favorite .first div').removeClass('selected order-up order-down');
	        if (o.selfCondition.sortField) {
	            $('#favorite .first div').filter(function () {
	                return $(this).attr('field') == o.selfCondition.sortField;
	            }).attr('order', o.selfCondition.order).addClass('selected').addClass(o.selfCondition.order == '1' ? 'order-down' : 'order-up');
	        }
	    }
	    // 设置四大指数宽度与绑定事件
	    this.setSelectedTicketsWidth();
	},
	init: function () {
	    this.getParams();
	    this.setContent();
	    this.initDataArea();
		// 设置默认的自选股
		this.setDefaultSelfTickets();
		this.bindAllEvents();
		// 查询数据
		this.searchForTargetPageData();
	},
	setDefaultSelfTickets: function(value) {
	    var value = value || 'sz399001,sh000001', openid = $.cookie('Quoetions_Openid'), oldFllows = common.getStockList(arrFllowUrl[2] + '?openId=' + escape(openid));
		if (oldFllows === null) {
		    common.addStock(arrFllowUrl[0] + '?openId=' + escape(openid), value);
		} else {
		    common.clearupCookies();
		}
	},
	// 设置四大指数宽度与绑定事件
	setSelectedTicketsWidth: function() {
		var totalWidth = $('body').width(), n;

		totalWidth = totalWidth < 100 ? 100 : totalWidth;
		n = Math.floor(totalWidth / 100);

		if (totalWidth < 400) {
		    $('#selected-ticket ul').css('width', 4 * totalWidth / n + 'px');

		}
		else {
		    $('#selected-ticket ul').css('width', totalWidth + 'px');
		}
		$('.selected-ticket').delegateClick('li', function(e) {

			timeEnd = new Date();
			if (timeEnd - timeStart < 100 && $(this).attr('pcode') && !moving) {

				window.location = '/RoxMarket/detail/view.do' + '?code=' + $(this).attr('pcode');
			}

		});
	},
	// 拖动数据区域，左右滚动箭头显示与隐藏
	initDataArea: function() {
	    var size = 0,
	        headerHeight = $('.ticket-info').filter(function () { return $(this).css('display') != 'none' }).find('.first').height();

		//初试化页面pageSize
		//size = Math.floor($('.data-area').height() / this.options.dataLineHeight);
	    size = Math.floor(($('.data-area').height() - headerHeight) / this.options.dataLineHeight);

		this.options.pageSize = size;
		this.options.condition.size = this.options.pageSize;
		$('#info-container').css({
		    'min-height': $('#wrapper').height()
		});
	},
	adjustItemHeight: function () {
	    var size = this.options.pageSize,
            ticketInfo = $('.ticket-info').filter(function () { return $(this).css('display') != 'none' }),
	        headerHeight = ticketInfo.find('.first').height();
	        //adjustHeight = $('.data-area').height() - headerHeight - this.options.dataLineHeight * size;
	    this.itemHeight = Math.round(($('.data-area').height() - headerHeight) / size); //手机某些浏览器不支持小数像素
	    //$('.ticket-name li').not(function (i) {
        //    return i == 0
	    //}).css({
	    //    'height': ((adjustHeight / size) + pageObject.options.dataLineHeight) + 'px'
	    //});
	    //ticketInfo.find('li').not(function (i) {
	    //    return i == 0
	    //}).css({
	    //    'height': ((adjustHeight / size) + pageObject.options.dataLineHeight) + 'px'
	    //});
	},
	// 获取股票分类
	getTicketSorts: function(async) {
	    //var tpl = "<li data-type='$type' style='width:$width'>$name</li>",
	    var tpl = "<li data-type='$type'>$name</li>",
			marketSort = '',
			ticketSort = '',
			width = 0;
		
		$.ajax({
		    url: '/RoxMarket/sync/getType.do',
			type: 'post',
			dataType: 'json',
			async: async === false ? false: true,
			success: function(data, status, xhr) {
				if (data.length < 1) {
					return;
				}
				// 获取市场分类数量，平均分配宽度
				//width = Math.floor(1 / data.length * 10000) / 100;
				//tpl = tpl.replace(/\$width/, width + '%');

				for (var i = 0, max = data.length; i < max; i++) {
					marketSort += tpl.replace(/\$name/, data[i].name).replace(/\$type/, data[i].type);
					// alert(data[i]['child'].name);
				}
				$('.market-sort').html(marketSort);
				$('.tickets-sort').html('');
				// 将每个市场的子类存储到该jQuery对象上.格式： "name-type,name-type"
				$('.market-sort li').each(function(i) {
					var childstr = '',
					list = data[i].child;

					for (var j = 0, max = list.length; j < max; j++) {
						childstr += list[j].name + '|' + list[j].type + ',';
					}
					childstr = childstr.slice(0, -1);
					$(this).data('childs', childstr);
				});

			}
		});
	},
	// 查看股票分时，K线详情
	getTicketMinsKInfo: function() {
		//$('.ticket-name, .ticket-info').delegateClick('li', function(e) {
		//	var e = e.touches && e.touches[0] || e,
		//	pcode;

		//	timeEnd = new Date();

		//	pcode = $(e.target).attr('pcode') || $(e.target).closest('li').attr('pcode');

		//	if (!$(e.target).hasClass('delete') && !$(e.target).is('img')) {
		//	    if (timeEnd - timeStart < 140 && pcode) {
		//	        window.location = '/RoxMarket/public/page/detail.html' + '?code=' + pcode;
		//	    }
		//	}

		//});
	    var e = this.touches && this.touches[0] || this,
			pcode,
	        options = pageObject.options;

		timeEnd = new Date();

		pcode = $(e.target).attr('pcode') || $(e.target).closest('li').attr('pcode');

		if (!$(e.target).hasClass('delete') && !$(e.target).is('img')) {
		    if (pcode && options.selfSelect == true) {
		        window.location = '/RoxMarket/detail/view.do' + '?code=' + pcode + '&order=' + options.condition.order + '&sortField=' + options.condition.sortField;
		    }
		    else if (pcode && options.selfSelect == false) {
		        window.location = '/RoxMarket/detail/view.do' + '?code=' + pcode + '&type=' + options.condition.type + '&order=' + options.condition.order + '&sortField=' + options.condition.sortField;
		    }
		}

    },
	//getTicketMinsKInfo: function () {
	//    $('.ticket-name, .ticket-info').delegate('li', "click", function (e) {
	//        var e = e.touches && e.touches[0] || e,
	//		pcode;

	//        timeEnd = new Date();

	//        pcode = $(e.target).attr('pcode') || $(e.target).closest('li').attr('pcode');

	//        if (pcode && !$(e.target).hasClass('delete') && !$(e.target).is('img')) {
	//            window.location = '/RoxMarket/public/page/detail.html' + '?code=' + pcode;
	//        }

	//    });

	//},
	bindAllEvents: function() {
		var self = this;
		// 绑定选择股票类型事件
		$('.market-sort, .tickets-sort').delegateClick('li', function() {
			var tpl = "<li data-type='$type' style='width:$width'>$name</li>",
			ticketSort = '',
			width = 0,
			data, n;
			var ticketsSort = $('.tickets-sort');
			
			if ($(this).parent('.market-sort').length > 0) {
				if (!$(this).hasClass('selected')) {
					// 分类改变，查询条件还原
					pageObject.reSetCondition();
					$('#trend .first div').attr('order', '');
					$('#trend .first div').removeClass('selected order-up order-down');
					data = $(this).data('childs') && $(this).data('childs').split(',') || [];
	
					width = Math.floor(1 / data.length * 10000) / 100;
					tpl = tpl.replace(/\$width/, width + '%');
					for (var i = 0, max = data.length; i < max; i++) {
	
						n = data[i].split('|') || [];
						ticketSort += tpl.replace(/\$name/, n[0]).replace(/\$type/, n[1]);
					}
					$('.tickets-sort').html(ticketSort);
					pageObject.options.a = $(this).html();
					$('.ticket-name li:first-child').html($(this).html());
					pageObject.options.condition.type = $(this).attr('data-type');
					if ($('.tickets-sort').height() > 1) {
						$('.tickets-sort').animate({
							'height': 0
						},
						300);
						$(this).removeClass('open');
					}
					pageObject.options.market = $(this).attr('data-type');
					pageObject.searchForTargetPageData();
				}
				else{
					if ($(this).attr('data-childs')) {
						$('.tickets-sort').animate({
							'height': 40
						},
						300);
						$(this).addClass('open').siblings().removeClass('open');
					}
					if ($('.tickets-sort').height() > 1) {
						$('.tickets-sort').animate({
							'height': 0
						},
						300);
						$(this).removeClass('open');
					}
				}
				if ($(this).attr('data-childs')) {
					$(this).addClass('hasChild').siblings().removeClass('hasChild');
				} else {
					$(this).siblings().removeClass('hasChild');
				}
				$(this).addClass('selected').siblings().removeClass('selected');
			}
			
			if ($(this).parent('.tickets-sort').length > 0) {
				// 分类改变，查询条件还原
				pageObject.reSetCondition();
				$('#trend .first div').attr('order', '');
				$('#trend .first div').removeClass('selected order-up order-down');
				$(this).addClass('selected').siblings().removeClass('selected');
				// 修改表头
				var title = pageObject.options.a + $(this).html();
	
				if (title.length > 4 && $('.ticket-name').width() === 80) {
					title = title.slice(0, 3) + '...';
				}
				$('.ticket-name li:first-child').html(title);
	
				$('.tickets-sort').animate({
					'height': 0
				},
				300);
				$(".market-sort li").removeClass('open');
	
				pageObject.options.condition.type = $(this).attr('data-type');
				pageObject.options.market = $('.market-sort li.selected').attr('data-type');
				pageObject.searchForTargetPageData();
	
			}
		});
		
		$(document).addClickEvent(function(e){
			var e = e.touches && e.touches[0] || e,
			target = e.target || e.srcElement;
			//e.preventDefault();
			if($(target).parent('.tickets-sort').length > 0 || $(target).parent('.market-sort').length > 0)
				return;
			$('.tickets-sort').animate({
				'height': 0
			},
			300);
			$(".market-sort li").removeClass('open');
		});

		// 自选与行情切换
		$('.footer > div > div').addClickEvent(function(e){
			e.preventDefault();
			//var target = e.target;
			if($(this).hasClass('selected')){
				return false;
			}
			pageObject.reSetData();
		    //切换body样式，以便控制内容区域的样式
			$("body").toggleClass("page-SCHQ");
			pageObject.initDataArea();
			pageObject.reSetCondition();//恢复默认
			$('div', '.footer').removeClass('selected');
			$(this).addClass('selected');
			if($(this).hasClass("zixuan")){
				$('.market-title, .selected-ticket').hide();
				$('.tickets-sort').css({height:0});
				$('.ticket-name li').eq(0).html('我的自选');
				$('#trend').hide();
				$('#favorite').show();
				$('#favorite .first div').attr('order', '');
				$('#favorite .first div').removeClass('selected order-up order-down');
				$(".info-scroller").css('min-width', '300px');
				pageObject.options.selfSelect = true;
			}else{
				$('.market-title, .selected-ticket').show();
				$('.ticket-name li').eq(0).html('大盘指数');    //恢复默认
				$('#trend').show();
				$('#favorite').hide();
				$('#trend .first div').attr('order', '');
				$('#trend .first div').removeClass('selected order-up order-down');
				$(".info-scroller").css('min-width', '900px');

				//pageObject.getTicketSorts(false);
				$('.market-sort li').removeClass('selected hasChild').eq(0).addClass('selected');
				$('.tickets-sort').html('');
				$(".market-title").addClass("bottom-line");
				pageObject.options.selfSelect = false;
			}
			pageObject.searchForTargetPageData();
		});
		
		//绑定自选股删除事件
		$('.ticket-info').delegateClick('.delete', function (e) {
		    var result, openid = $.cookie('Quoetions_Openid');
		    e.preventDefault();
		    result = common.delStock(arrFllowUrl[1] + '?openId=' + escape(openid), $(this).closest('li').attr('pcode'));
		    if (result) {
		        jMessage.open({ title: '', content: '取消自选成功', model: 'alert', autoHide: true });
		        pageObject.searchForTargetPageData();
		    }
		});

				
		// 查看股票分时、K线详情
	    //this.getTicketMinsKInfo();
		$('.ticket-name, .ticket-info').delegateClick('li', function (e) {
		    self.getTicketMinsKInfo.call(e);
		});

		// 按字段排序
		$('.ticket-info').delegateClick('.first div', function(e) {
			// 获取查询参数对象
			var condition;
			if (pageObject.options.selfSelect) {

				condition = pageObject.options.selfCondition;
			} else {
				condition = pageObject.options.condition;
			}

			e.preventDefault();

			condition.sortField = $(this).attr('field');

			$(this).siblings().removeClass('selected order-up order-down');
			// 如果当前字段不是排序字段，则恢复上一次点击时的排序方式，否则切换排序方式（升或降）
			if (!$(this).hasClass('selected')) {
				// 如果是第一次以此字段排序，则按升序方式排序
				condition.order = $(this).attr('order') || 0;
				$(this).attr('order', condition.order);
				$(this).addClass('selected ' + (condition.order == 0 ? 'order-up': 'order-down'));

			} else {
				if ($(this).attr('order') == '0') {
					condition.order = 1;
					$(this).attr('order', '1').removeClass('order-up').addClass('order-down');
				} else {
					condition.order = 0;
					$(this).attr('order', '0').removeClass('order-down').addClass('order-up');
				}
			}

			pageObject.searchForTargetPageData();

		});
	},
	searchForTargetPageData: function (obj, showloading, isInit) {
		var pcodes, url, ticketpcodes, options = pageObject.options,
		data = {}, openid = $.cookie('Quoetions_Openid');
		
		// 判断是否加载自选股
		if (options.selfSelect === true) {

			$.extend(options.selfCondition, obj);

		} else {
			// 合并查询参数
			$.extend(options.condition, obj);
		}

		if (options.selfSelect) {
		    ticketpcodes = common.getStockList(arrFllowUrl[2] + '?openId=' + escape(openid));
			if (ticketpcodes) {
			    data.codeStr = ticketpcodes;
				//pcodes = ticketpcodes.split(',');
				//data = {
				//	'codeStr': pcodes.slice(options.condition.size * (options.selfCondition.cp - 1), options.condition.size * options.selfCondition.cp).join(',')
				//};
			} else {
				data.codeStr = '';
			}
			data.size = options.pageSize;
			options.selfCondition.codeStr = data.codeStr;

			data = $.extend({},
			data, options.selfCondition);
			url = '/RoxMarket/follow/myFollow.do?';
			common.setHash(['1', '', '', options.selfCondition.sortField, options.selfCondition.order, options.selfCondition.cp])
			//common.setHash(['selfSelect', 'market', 'sort', 'order', 'direction', 'page'])
		} else {
			data = pageObject.options.condition;
			url = '/RoxMarket/sync/market.do?';
			common.setHash(['0', options.market, options.condition.type, options.condition.sortField, options.condition.order, options.condition.cp])
			//common.setHash(['selfSelect', 'market', 'sort', 'order', 'direction', 'page'])
		}
		
		// 关闭socket通道
		socketChannel.close();
		// 请求数据
		$.ajax({
			type: 'get',
			url: url,
			data: data,
			dataType: 'json',
			beforeSend: function(){
				if (showloading !== false) {
					$('.loading').show();
					pageObject.reSetData();
					myScroll.refresh();
					iscroller.refresh();
				}
			},
			success: function (_data, status, xhr) {
			    var indexShow = $('#trend').css('display') == 'none' ? 0 : 1;
				$('.loading').hide();
				if (_data && typeof _data.list !== 'undefined') {
					if (options.selfSelect) {
						options.cp = options.selfCondition.cp;
						options.pageNumber = (ticketpcodes && Math.ceil(ticketpcodes.split(',').length / options.pageSize)) || 1;

					} else {
						options.cp = options.condition.cp;
						options.pageNumber = _data.info.allSize || 1;
					}
					// 渲染数据
					pageObject.renderData(_data.list, isInit);
					if (!pageObject.options.selfSelect) pageObject.renderSelectedTickets(_data.szList);
					socketChannel.close();//再关闭一次，防止并发错误
				    // 初始化通道
					socketChannel.open();
					$('#info-container').css({
						'min-height': $('#wrapper').height(),
						'height': $('.ticket-info').eq(indexShow).height()
					});//解决加载时清空数据完始终有个高度
                    //回调回来时不需要刷新
					//if (isInit != false)
					//    options.refresh = true;
					//else
				    //    options.refresh = false;
					if (isInit != false) {
					    myScroll.scrollTo(0, -pullDownOffset);
					    myScroll.refresh();
					    //options.refresh = false;
					    iscroller.refresh();
					    selectedTicket.refresh();
					    myScroll.maxScrollY = myScroll.maxScrollY + pullUpOffset;
					}
				}
			},
			error: function(error) {
				console.log(arguments);
			}
		});
	},
	// 渲染数据区域，包括左侧股票名称代码、中间数据以及自选股删除用的遮罩层（初试化一次）
	renderData: function (dataList, isFirst) {
	    var ticketNameTpl = '<li pcode="$pcode" $style><div>$name</div><div>$code</div></li>',
		ticketInfoTpl = '<li pcode="$pcode" $style>' + '<div>&nbsp;</div>' + '<div class="$upOrDown">$zx</div>' + '<div class="$zxdeUpOrDown">$zde</div>' + '<div class="$zxdfUpOrDown">$zdf</div>' + '<div>$ze</div>' + '<div>$zl</div>' + '<div>$zs</div>' + '<div class="$upOrDown">$jk</div>' + '<div class="$upOrDown">$zg</div>' + '<div class="$upOrDown">$zd</div>' + '</li>',
        favoriteTpl = '<li pcode="$pcode" $style>' + '<div>&nbsp;</div>' + '<div class="$upOrDown">$zx</div>' + '<div class="$zxdfUpOrDown"><span>$zdf</span></div>' + '<div class="delete"><img src="public/images/delete.png" /></div>' + '</li>',
		//oprationTpl = '<li><div pcode="$pcode"><img src="public/images/delete.png" /></div></li>',
		ticketName = '',
		ticketInfo = '',
        favoriteInfo = '';
		//opration = '';
	    if (isFirst !== false) {
	        this.adjustItemHeight();
	    }
		for (var i = 0, max = dataList.length; i < max; i++) {
			//opration += oprationTpl.replace(/\$pcode/, dataList[i].pcode);
		    ticketName += ticketNameTpl.replace(/\$style/, "style='height:" + (this.itemHeight || this.options.dataLineHeight) + "px'").replace(/\$name/, dataList[i].mc).replace(/\$code/, dataList[i].dm).replace(/\$pcode/, dataList[i].pcode);
		    ticketInfo += ticketInfoTpl.replace(/\$style/, "style='height:" + (this.itemHeight || this.options.dataLineHeight) + "px'").replace(/\$zx/, formatData(dataList[i].zx)).replace(/\$zde/, formatData(dataList[i].zde)).replace(/\$zdf/, formatData(dataList[i].zdf)).replace(/\$ze/, formatData(dataList[i].ze)).replace(/\$zl/, formatData(dataList[i].zl)).replace(/\$zs/, formatData(dataList[i].zs)).replace(/\$jk/, formatData(dataList[i].jk)).replace(/\$zg/, formatData(dataList[i].zg)).replace(/\$zd/, formatData(dataList[i].zd)).replace(/\$upOrDown/g, (dataList[i].zde < 0 || dataList[i].zdf < 0) ? 'down' : 'up').replace(/\$zxdeUpOrDown/, dataList[i].zde < 0 ? 'down' : 'up').replace(/\$zxdfUpOrDown/, dataList[i].zdf < 0 ? 'down' : 'up').replace(/\$pcode/, dataList[i].pcode);
		    favoriteInfo += favoriteTpl.replace(/\$style/, "style='height:" + (this.itemHeight || this.options.dataLineHeight) + "px'").replace(/\$zx/, formatData(dataList[i].zx)).replace(/\$zdf/, formatData(dataList[i].zdf)).replace(/\$upOrDown/g, (dataList[i].zde < 0 || dataList[i].zdf < 0) ? 'down' : 'up').replace(/\$zxdfUpOrDown/, dataList[i].zdf < 0 ? 'down' : 'up').replace(/\$pcode/, dataList[i].pcode);
		}

		var arrLasts = [], whichShow = $('#trend').css('display') == 'none' ? 'favorite' : 'trend';
		if (isFirst === false) {
		    $('#' + whichShow + ' li').each(function (i) {
		        if (i == 0) return;
		        var last = $(this).children('div').eq(1).text(), lastnew = dataList && (dataList[i - 1].zx).toString();
		        arrLasts[i - 1] = false;
		        if (last.length > 0 && lastnew.length > 0 && !isNaN(last) && !isNaN(lastnew) && (Number(lastnew) != Number(last))) {
		            arrLasts[i - 1] = true;
		        }
		    });
		}

		if (whichShow == 'favorite') {
		    $('#favorite').html($('#favorite li').get(0).outerHTML + favoriteInfo);
		}
		else {
		    $('#trend').html($('#trend li').get(0).outerHTML + ticketInfo);
		}
		$('.ticket-name').html($('.ticket-name li').get(0).outerHTML + ticketName);

		if (isFirst === false) {
		    $('#' + whichShow + ' li').each(function (i) {
		        if (i == 0) return;
		        if (arrLasts[i - 1]) {
		            var olast = $(this).children('div').eq(1).addClass('pricechangeshow'), divSelf = this;
		            setTimeout(function () { $(divSelf).children('div').eq(1).removeClass('pricechangeshow').addClass('pricechangehide'); }, 1000);
		        }
		    });
		}
		
		function formatData(str) {
			if (!str || str == 0) {
				return '--';
			}
			return str;
		}
		//myScroll.scrollTo(0, -pullDownOffset);
		myScroll.refresh();
		myScroll.maxScrollY = myScroll.maxScrollY + pullUpOffset;
	},
	// 填充四大块数据
	renderSelectedTickets: function(list) {
		var tpl = '<li pcode="$pcode">' + '<span  class="$upOrDown new">' + '<div>$name</div>' + '<div class="change">$zx</div>' + '<div class="pctchange">$zde($zdf)</div>' + '</span>' + '</li>',
			resulteStr = '';
		for (var i = 0; i < list.length; i++) {

			resulteStr += tpl.replace(/\$name/, list[i].name).replace(/\$zx/, list[i].zx).replace(/\$zde/, list[i].zde == 0 ? '--': (list[i].zde > 0 ? '+' + list[i].zde: list[i].zde)).replace(/\$zdf/, list[i].zdf == 0 ? '--': (list[i].zdf > 0 ? '+' + list[i].zdf: list[i].zdf) + '%').replace(/\$upOrDown/, list[i].zde < 0 ? 'down': 'up').replace(/\$pcode/, list[i].pcode);
		}
		$('.selected-ticket ul').html(resulteStr);
	},
	//清空数据
	reSetData: function() {
		//$('.opration ul').html('<li></li>');
		$('.ticket-name').html($('.ticket-name li').get(0).outerHTML);
		$('#trend').html($('#trend li').get(0).outerHTML);
		$('#favorite').html($('#favorite li').get(0).outerHTML);
	}
};


$(document).ready(function() {
    pageObject.init();
    $(window).bind('resize', function () {
        pageObject.initDataArea();
        pageObject.setSelectedTicketsWidth();
        pageObject.searchForTargetPageData();
    })
});



