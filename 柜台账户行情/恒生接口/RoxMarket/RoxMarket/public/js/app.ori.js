var debug = false; //是否开启调试状态
(function($){
    var Stock = window.Stock = {},
        kLinePeriod = ['timeshare', 'day', 'week', 'month', '5min', '15min', '30min', '60min'],
        socketUrl = ['/RoxMarket/timeshare',
        '/RoxMarket/klinedaily',
        '/RoxMarket/klineweekly',
        '/RoxMarket/klinemonthly',
        '/RoxMarket/kline5min',
        '/RoxMarket/kline15min',
        '/RoxMarket/kline30min',
        '/RoxMarket/kline60min'];

    Stock.websocket = null;
    Stock.channelGate = null;

    Stock.connect = (function (host) {
        try {
            Stock.close();
            if ('WebSocket' in window) {
                Stock.websocket = new WebSocket(host);
            } else if ('MozWebSocket' in window) {
                Stock.websocket = new MozWebSocket(host);
            } else {
                console.log('Error: WebSocket is not supported by this browser.');
                return;
            }

            Stock.websocket.onopen = function () {
                // console.log('Info: WebSocket connection opened.');
            };

            Stock.websocket.onclose = function () {
                // console.log('Info: WebSocket closed.');
            };

            Stock.websocket.onmessage = function (message) {
                try {
                    var data = JSON.parse(message.data), day;
                    // console.log(data);
                    if (Stock.graphic) {
                        //TODO
                        if (!$('.line-scroller .minuts-line').hasClass('selected')) {
                            if (data && data.items && data.items.length > 0) {
                                day = data.items[0].time;
                                Stock.graphic.popData(day);
                            }
                        }
                        Stock.graphic.pushData(data),
                        Stock.graphic.refresh();
                    }
                } catch (e) {
                    console.log(e);
                }
            };
        }
        catch (e) {
            Stock.close();
        }
        finally {

        }
    });

    Stock.initConnection = function(url) { 
        if (window.location.protocol == 'http:') {
            Stock.connect('ws://' + window.location.host + url);
        } else {
            Stock.connect('wss://' + window.location.host + url);
        }
    };

    Stock.graphic = null;
    Stock.load = function (url, options, noWebSocket) {
        try {
            if (options.flag == 0) {
                Stock.graphic = new MinsGraphic({ el: ".selectedDetail .canvas0", period: kLinePeriod[options.flag], onRegion: function () { window.pageObject.setTechnical(); }, onFilish: function () { window.pageObject.filishGraphic(); } });
            }
            else {
                Stock.graphic = new KGraphic({ el: ".selectedDetail .canvas0", period: kLinePeriod[options.flag], rightRegion: 14, hintChart: true, technical: options.tech, onRegion: function () { window.pageObject.setTechnical(); }, onFilish: function () { window.pageObject.filishGraphic(); } });
            }
            //$('.selectedDetail .canvas').each(function (i) {
            //    if (i == options.flag)
            //        $(this).show();
            //    else
            //        $(this).hide();
            //});

            // 初始化历史数据
            Stock.graphic.render(options.data);

            // 加载完整数据后，建立websocket通信，实时更新
            if (!noWebSocket) {
                Stock.initConnection(url + '&size=' + options.size);
            }
        }
        catch (e) {
            jMessage.open({ title: '', content: (debug ? e.message : '数据加载异常，请稍后再试!'), model: 'alert', autoHide: false });
            window.pageObject.bindKlineButton();
        }
    };
    Stock.open = function (url, options) {
		if (('MozWebSocket' in window || 'WebSocket' in window) && !$.isAndroidOS()) {
		    Stock.load(socketUrl[options.flag] + url.substring(url.indexOf("?")), options);
        }else{
		    Stock.load('', options, true);
            Stock.channelGate = window.setTimeout(function(){
                pageObject.bindStock(url, options, false);
            }, 5000)
        }
    }
    Stock.close = function () {
        try
        {
            if (Stock.websocket /*&& Stock.websocket.readyState == WebSocket.OPEN*/) {
                Stock.websocket.close();
            } else if (Stock.channelGate) {
                window.clearTimeout(Stock.channelGate);
            }
        }
        catch(e){
            
        }
        finally{
            Stock.websocket = null;
            Stock.channelGate = null;
        }
    }

})(Zepto);

(function($){
	var PREPENDSIZE = 50; //附加数据长度，计算DMA指标需要50条初始数据
    var Stock = window.Stock,
        requestUrl = ['/RoxMarket/ts/getData.do?code=',
            '/RoxMarket/daily/getKline.do?code=',
            '/RoxMarket/quote/getWeeklyKline.do?code=',
            '/RoxMarket/quote/getMonthlyKline.do?code=',
            '/RoxMarket/quote/get5MinKLine.do?code=',
            '/RoxMarket/quote/get15MinKLine.do?code=',
            '/RoxMarket/quote/get30MinKLine.do?code=',
            '/RoxMarket/quote/get60MinKLine.do?code='],
    pageObject = window.pageObject = {
       init: function (v_Option) {
		   var o = v_Option;
		   this.el = o.el && $(o.el) || $('');
		   this.options = o;
		   this.setContent();
		   this.unBindEvents();
		   this.bindEvents();
		   var lineIndex = 'lineIndex' in this.options ? this.options.lineIndex : 0;
		   var techName = 'tech' in this.options ? this.options.tech : 'VOL';
		   this.bindStock(requestUrl[lineIndex] + this.options.stockCode, { flag: lineIndex, tech: techName });
       },
       setContent: function () {
           var lineIndex = 'lineIndex' in this.options ? this.options.lineIndex : 0;
           $('.line-scroller>div>div').removeClass('selected').eq(lineIndex).addClass('selected');
           this.setCanvasHeight();
	       this.getSiblingStockCode();//获取前后股票代码
       },
	   setMaxSize: function(){
		   var maxSize;
		   var oKGraphic = new KGraphic({ el: ".selectedDetail .canvas0", hintChart: true });
		   var oPainter = new Painter({}, $.extend({}, {region: {width: oKGraphic.el.width, height:oKGraphic.el.height},
														splitSpace: oKGraphic.options.splitSpace,
			            								width: oKGraphic.options.KLineSingleWidth
			            							}) 
            									);
			maxSize = oPainter.getMaxPaintItemsLength();
			this.options.size = maxSize && maxSize + PREPENDSIZE || 0;
			oKGraphic = null;
			oPainter = null;
	   },
       setTechnical: function () {
           var lineIndex = 'lineIndex' in this.options ? this.options.lineIndex : 0,
               oTech = this.el.find('.technical');
           if (lineIndex > 0) {
               if(Stock.graphic){
				   oTech.css({
				       left: (Stock.graphic.el.width - Stock.graphic.options.curveChart.region.width / 2) / 2 - 50,
				       top: (Stock.graphic.options.curveChart.region.height + Stock.graphic.options.curveChart.scalerTop.region.height + (Stock.graphic.options.curveChart.scalerBottom.region.height - 30) / 2) / 2
				   })
				   oTech.show();
			   }
           }
           else {
               oTech.hide();
           }
       },
	   setCanvasHeight: function(){
	       $('.main-container,.canvas-container').css('height', ($(window).height() - $('.top-container').height() - bottomHeight) + 'px');
			//重设canvas大小
			var $canvasContainer = this.el.find(".canvas-container"),
				w = $canvasContainer.width(),
				h = $canvasContainer.height();
			$canvasContainer.find("canvas")
				.attr({
					width: w * 2,
					height: h * 2
				})
				.css({
					width: w,
					height: h
				});
		},
	   getSiblingStockCode: function () {
	       var data = {}, ticketpcodes;
	       if (selfSelect == '1') {
	           data.order = order;
	           data.sortField = sortField;
	           data.code = curCode;
	           ticketpcodes = common.getStockList(arrFllowUrl[2]);
	           if (ticketpcodes) {
	               data.codeStr = ticketpcodes;
	           } else {
	               data.codeStr = '';
	           }
	       }
	       else {
	           data.order = order;
	           data.sortField = sortField;
	           data.code = curCode;
	           data.type = type;
	       }
	       $.ajax({
	           url: '/RoxMarket/detail/getNeighbor.do',
	           type: 'post',
               data: data,
               dataType: 'json',
               beforeSend: function () {
                   myScroll.disable();
               },
	           success: function (data, status, xhr) {
	               if (data && data.list && data.list.length > 0) {
	                   curCode = data.list[1].pcode || '';
	                   prevCode = data.list[0].pcode || '';
	                   nextCode = data.list[2].pcode || '';
	                   myScroll.enable();
	               }
	           }
	       });
	   },
       setHeaderAutoWidth: function () {
           var totalWidth = $(window).width(),
               midWidth = this.el.find('.header .mid').width() * 1.3,
               btnWidth = this.el.find('.left .btn-def').width(),
               marginWidth = Math.floor((totalWidth - midWidth) / 2 / 3 - btnWidth - 4);
           this.el.find('.header .left div').css('margin', ['10px', marginWidth + 'px', 0, 0].join(' '));
           this.el.find('.header .left div').first().css('margin', ['10px', marginWidth + 'px', 0, '10px'].join(' '));
           this.el.find('.header .right div').css('margin', ['10px', 0, 0, marginWidth + 'px'].join(' '));
           this.el.find('.header .right div').last().css('margin', ['10px', '10px', 0, marginWidth + 'px'].join(' '));
       },
       bindStock: function (url, options, showloading) {
           try {
               var self = this;
               this.setMaxSize();
               options.size = this.options.size;
               Stock.close();
               this.unBindKlineButton();
               jMessage.close();
               $.ajax({
                   url: url + '&size=' + (options.size || 0),
                   type: 'post',
                   async: true,
                   dataType: 'json',
                   timeout: 60000,
                   beforeSend: function () {
                       if (showloading !== false) {
                           $('.waiting').show();
                       }
                   },
                   error: function (XMLHttpRequest, textStatus, errorThrown) {
                       // 通常情况下textStatus和errorThown只有其中一个有值 
                       //throw textStatus || errorThrown; //异常捕获不到(Uncaught error)
                       jMessage.open({ title: '', content: (textStatus == 'timeout' ? '数据加载超时，请稍后再试!' : '数据加载异常，请稍后再试!'), model: 'alert', autoHide: false });
                       window.pageObject.bindKlineButton();
                   },
                   success: function (data, status, xhr) {
                       Stock.close();//异步时间差的原因前面的未必关闭
                       Stock.open(url, $.extend({}, options, { data: data }));
                       pageObject.setHeaderAutoWidth();
                   }
               });
           }
           catch (e) {
               jMessage.open({ title: '', content: (debug ? e.message : (e == 'timeout' ? '数据加载超时，请稍后再试!' : '数据加载异常，请稍后再试!')), model: 'alert', autoHide: false });
               window.pageObject.bindKlineButton();
           }
        },
        filishGraphic: function () {
            $('.waiting').hide();
			this.bindKlineButton();
        },
		unBindKlineButton: function () {
			$('.line-scroller>div>div').unbind('click');
		},
		bindKlineButton: function () {
			var self = this;
			$('.line-scroller>div>div').bind({
			    click: function () {
			        if ($(this).hasClass('selected')) return;
					$('.line-scroller>div>div').removeClass('selected');
					$(this).addClass('selected');
					self.options.lineIndex = $(this).parent().index();
					self.bindStock(requestUrl[self.options.lineIndex] + self.options.stockCode, { flag: self.options.lineIndex, tech: self.options.tech });
                }
            });
		},
        bindEvents: function(){
            var pcode = this.options.stockCode, self = this,
                onOrientName = 'onorientationchange' in window ? 'orientationchange' : 'resize',
                ticketpcodes = common.getStockList(arrFllowUrl[2]);
			
            $(window).bind(onOrientName, function () {
				$('.page-detail,.top-container,.main-container').css('width', $(window).width() + 'px');
				$('.scroller').css('width', $(window).width() * 3 + 'px');
				myScroll.refresh();
				myScroll.scrollToPage(1, 0, 0);
                pageObject.setHeaderAutoWidth();
                pageObject.setCanvasHeight();
                self.bindStock(requestUrl[self.options.lineIndex] + self.options.stockCode, { flag: self.options.lineIndex, tech: self.options.tech });
            });
			
            if (ticketpcodes && ticketpcodes.indexOf(pcode) !== -1) {
			    this.el.find('.addMyBox').removeClass("add").addClass('del');
			}
			else {
			    this.el.find('.addMyBox').removeClass("del").addClass('add');
			}

            this.el.find('.addMyBox').addClickEvent(function(){
                var newCookie, follow = ticketpcodes.length == 0 ? [] : ticketpcodes.split(','), result;
                if ($(this).hasClass("add")) {
                    result = common.addStock(arrFllowUrl[0], pcode);
                    if (result) { 
                        $(this).removeClass("add").addClass('del');
                        jMessage.open({ title: '', content: '添加自选成功', model: 'alert', icon: 'success', autoHide: true });
                    }
                }
                else {
                    result = common.delStock(arrFllowUrl[1], pcode);
                    if (result) {
                        $(this).removeClass("del").addClass('add');
                        jMessage.open({ title: '', content: '删除自选成功', model: 'alert', icon: 'info', autoHide: true });
                    }
                }
            });

            //图表切换
            this.bindKlineButton();

            this.el.find('.f10').addClickEvent(function(){      
                window.location.href = '/RoxMarket/news/fs.do?code=' + self.options.stockCode.replace('sz', '').replace('sh', '');
            });

            this.el.find('.search').addClickEvent(function (e) {
                window.location.href = '/RoxMarket/search/searchPage.do';
            });
			
			this.el.find('.left .btn-def').addClickEvent(function(){
				window.location.href = tradeServerHomePath + '/limitBuyPage.do?code=' + self.options.stockCode.replace('sz', '').replace('sh', '');
			});
			
			this.el.find('.right .btn-def').addClickEvent(function(){
				window.location.href = tradeServerHomePath + '/limitSellPage.do?code=' + self.options.stockCode.replace('sz', '').replace('sh', '');
			});
			
			this.el.find('.technical').addClickEvent(function(e){
				var $tech = self.el.find('.technical');
				$('.mBox').show();
				$('.technicalBox').css({
					opacity: "1",
					left: $tech.offset().left - ($('.technicalBox').width() - $tech.width()) / 2 + "px",
					top: $tech.offset().top - $('.technicalBox').height() + "px"
				});
				$('.technicalBox').children("div").each(function () {
					$(this).html() == curTechnical && $(this).addClass("curr").siblings().not(this).removeClass("curr"),
					$(this).delClickEvent().addClickEvent(function (e) {
						$(this).addClass("curr").siblings().not(this).removeClass("curr");
						myScroll.enable();
						$('.technicalBox').css({
							opacity: "0"
						});
						$('.mBox').hide();
						curTechnical = $(this).html();
						self.options.tech = curTechnical;
						//Stock.graphic && Stock.graphic.options.technicalAnalysis && (Stock.graphic.options.technicalAnalysis = curTechnical);
						//Stock.graphic.render();
						pageObject.bindStock(requestUrl[self.options.lineIndex] + self.options.stockCode, { flag: self.options.lineIndex, tech: self.options.tech });
						e.stopPropagation();
					})
				});
				setTimeout(function () {
					$('.mBox').delClickEvent().addClickEvent(function (e) {
						myScroll.enable();
						$('.technicalBox').css({
							opacity: "0"
						});
						$('.mBox').hide();
						$('.mBox').off('click touchend touchstart');
						e.stopPropagation();
					})
				},
				750)
				e.stopPropagation();
			}).off("touchstart").on("touchstart", function () {
                myScroll.refresh(),
                myScroll.disable();
            });
        },
        unBindEvents: function () {
            var onOrientName = 'onorientationchange' in window ? 'orientationchange' : 'resize';
            $(window).unbind(onOrientName);
            this.el.find('.addMyBox').unbind();
            this.unBindKlineButton();
            this.el.find('.f10').unbind();
            this.el.find('.search').unbind();
            this.el.find('.left .btn-def,.right .btn-def').unbind();
            this.el.find('.technical').unbind();
        }
    }

})(Zepto);

Zepto(function($){
    window.pageObject.init({ el: '.selectedDetail', stockCode: curCode, lineIndex: 0, tech: curTechnical });
});

