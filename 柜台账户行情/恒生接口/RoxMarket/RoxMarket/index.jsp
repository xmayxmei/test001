<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="format-detection" content="telephone=no">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>微行情</title>
    <link href="public/css/main${compressSuffix}.css?${version}" rel="stylesheet" />
    <link href="public/css/bottom${compressSuffix}.css?${version}" rel="stylesheet" />
    <style type='text/css'>
		.market-sort{
			display: block;
		}
		.selected-ticket{
			display: block;
		}
	</style>
</head>
<body class='page-SCHQ'>
    <!--<div style="width:100px; height:100px; background:red; position:fixed; top:50px; z-index:999;" onclick="window.location.reload();"></div>-->
    <div class='loading'>
        <div></div>
    </div>
    <div class="market-nav">
        <div class="market-refresh"></div>
        <div class="market-title">
            <ul class="market-sort">
                <!-- <li class='selected'>大盘</li>
                <li data-type='-1'>沪深</li>
                <li data-type='1'>深市</li>
                <li data-type='0'>沪市</li>
                <li>环球</li> -->
            </ul>
        </div>
        <div class="market-search"><a href="search/searchPage.do" class="search-glass"></a></div>
    </div>

    <ul class="tickets-sort"></ul>
    <div class="selected-ticket" id='selected-ticket'>
        <!-- <div> -->
        <ul>
            <!-- 指数 -->
        </ul>
        <!-- </div> -->
    </div>

    <div class="overlay"></div>
    <div class="data-area" id='wrapper'>

        <div id="scroller">

            <div class="idle" id='pullDown'>
                <span class="pullDownIcon"></span>
                <div class="right">
                    <span class="pullDownLabel">下拉可以刷新</span>
                    <span class="timeLabel">数据下载于:</span>
                </div>
            </div>

            <ul class="ticket-name" id='ticket-name'>
                <li>大盘指数</li>
            </ul>
            <!--<div class="opration">
                <ul>
                    <li></li>
                </ul>
            </div>-->
            <div class='info-container' id='info-container'>
                <div class="info-scroller">
                    <ul id="favorite" class="ticket-info">
                        <li class='first'>
                            <div>&nbsp;</div>
                            <div field='zx'>最新价</div>
                            <div field='zdf'>涨跌幅%</div>
                            <div>&nbsp;</div>
                        </li>
                    </ul>
                    <ul id="trend" class="ticket-info">
                        <li class='first'>
                            <div>&nbsp;</div>
                            <div field='zx'>最新价</div>
                            <div field='zde'>涨跌</div>
                            <div field='zdf'>涨跌幅%</div>
                            <div field='ze'>总额</div>
                            <div field='zl'>总量</div>
                            <div field='zs'>昨收</div>
                            <div field='jk'>今开</div>
                            <div field='zg'>最高</div>
                            <div field='zd'>最低</div>
                        </li>
                    </ul>

                </div>
            </div>

            <div class="idle" id='pullUp'>
                <span class="pullUpIcon"></span>
                <div class="right">
                    <span class="pullUpLabel">上拉可以刷新</span>
                    <span class="timeLabel">数据下载于:</span>
                </div>
            </div>

        </div>

    </div>

    <footer class="footer">
        <div><div class='zixuan'><span class="icon"></span><span class="bt">自选</span></div></div>
        <div><div class='selected market'><span class="icon"></span><span class="bt">行情</span></div></div>
        <div><a href="news/newslist.do" class='zhzx'><span class="icon"></span><span class="bt">资讯</span></a></div>
        <div><a href="${tradeServerHomePath}/homePage.do" class='trade'><span class="icon"></span><span class="bt">交易</span></a></div>
    </footer>
    <script type="text/javascript" src="public/js/common${compressSuffix}.js?${version}"></script>
    <script type="text/javascript" src='public/js/iscroll.min.js?${version}'></script>
    <script type="text/javascript" src='public/js/zepto.min.js?${version}'></script>
    <script type="text/javascript" src='public/js/zepto.expand${compressSuffix}.js?${version}'></script>
    <script type="text/javascript" src='public/js/index${compressSuffix}.js?${version}'></script>
    <script type="text/javascript" src="public/js/jMessage${compressSuffix}.js?${version}"></script>
    <script type="text/javascript">
      var arrFllowUrl = ['/RoxMarket/follow/addFollow.do',
      '/RoxMarket/follow/delFollow.do',
      '/RoxMarket/follow/getFollow.do'];
      function getparam() {
      var s = location.search && location.search.slice(1),
            arr = s.split('&'),
            item = [],
            obj = {};
            for (var i = 0; i < arr.length; i++) {
                item = arr[i].split('=');
                obj[item[0]] = item[1];
            }
            if ('zixuan' in obj) {
                return obj['zixuan'].slice(0);
            }
        }
        document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

        var myScroll, iscroller, selectedTicket,
            pullDownEl, pullDownOffset,
            pullUpEl, pullUpOffset,
            generatedCount = 0,
            timeStart, timeEnd,
            moving = false;

        function loaded() {
            pullDownEl = document.getElementById('pullDown');
            pullDownOffset = pullDownEl.offsetHeight;
            pullUpEl = document.getElementById('pullUp');
            pullUpOffset = pullUpEl.offsetHeight;


            var infoContainer = $('#info-container'),
                ticketName = $('#ticket-name');


            myScroll = new iScroll('wrapper', {
                vScrollbar: false,
                hScrollbar: false,
                useTransition: true,
                topOffset: pullDownOffset,
                lockDirection: true,
                bounce: true,
                //snap: 'li',
                onRefresh: function () {
                    var now = new Date;
                    var dateText = [now.getMonth() + 1, '-', now.getDate(), ' ', now.getHours(), ':', now.getMinutes(), ':', now.getSeconds()].join('');
                    $('.timeLabel').text('数据下载于:' + dateText);

                    /*
                    var options;

                    if(window.pageObject){

                        options = window.pageObject.options;

                        if( options.refresh || $('#ticket-name li').length < options.pageSize){
                            this.y = 0;
                        }

                    }*/



                },
                onBeforeScrollStart: function (e) {
                    e.preventDefault();
                    timeStart = new Date();
                    moving = this.moved;

                },
                onScrollMove: function () {
                    var options = pageObject.options;
                    if (this.y > 5 && !$(pullDownEl).hasClass('flip')) {
                        $('.pullDownLabel').text('松手加载上一页...');
                        $(pullDownEl)[0].className = 'flip';
                        this.minScrollY = -pullDownOffset;

                    } else if (this.y < 5 && $(pullDownEl).hasClass('flip')) {
                        $(pullDownEl)[0].className = 'idle';
                        $('.pullDownLabel').text('下拉可以刷新');
                        this.minScrollY = -pullDownOffset;

                    } else if (this.y < (this.maxScrollY - pullUpOffset - 5) && !$(pullUpEl).hasClass('flip')) {
                        $('.pullUpLabel').text('松手加载下一页...');
                        $(pullUpEl)[0].className = 'flip';
                        this.maxScrollY = this.maxScrollY;

                    } else if (this.y > (this.maxScrollY - pullUpOffset - 5) && $(pullUpEl).hasClass('flip')) {
                        $('.pullUpLabel').text('上拉加载更多');
                        $(pullUpEl)[0].className = 'idle';
                        this.maxScrollY = this.maxScrollY;
                    }
                },
                onScrollEnd: function () {
                    var object = window.pageObject, options = window.pageObject.options;
                    if ($(pullDownEl).hasClass('flip')) {
                        $(pullDownEl)[0].className = 'idle';
                        $('.pullDownLabel').text('下拉可以刷新');
                        if (options.cp <= 1 || options.cp > options.pageNumber) {
                            //myScroll.refresh();
                            return;
                        }
                        if (options.selfSelect) {
                            object.searchForTargetPageData({ cp: options.selfCondition.cp - 1 }, true);
                        } else {
                            object.searchForTargetPageData({ cp: options.condition.cp - 1 }, true);
                        }

                    } else if ($(pullUpEl).hasClass('flip')) {
                        $('.pullUpLabel').text('上拉加载更多');
                        $(pullUpEl)[0].className = 'idle';
                        if (options.cp < 1 || options.cp >= options.pageNumber) {
                            //myScroll.refresh();
                            return;
                        }
                        if (options.selfSelect) {
                            object.searchForTargetPageData({ cp: Number(options.selfCondition.cp) + 1 }, true);
                        } else {
                            object.searchForTargetPageData({ cp: Number(options.condition.cp) + 1 }, true);
                        }
                    }

                }
            });

            setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);

            iscroller = new iScroll('info-container', {
                vScroll: false,
                vScrollbar: false,
                hScrollbar: false,
                onScrollMove: function () {
                    $('.ticket-name, .ticket-info').undelegateClick('li');
                },
                onScrollEnd: function () {
                    var object = window.pageObject;
                    $('.ticket-name, .ticket-info').delegateClick('li', function (e) {
                        object.getTicketMinsKInfo.call(e);
                    });
                }

            });

            selectedTicket = new iScroll('selected-ticket', {
                vScroll: false,
                vScrollbar: false,
                hScrollbar: false,
                onBeforeScrollStart: function () {
                    timeStart = new Date();
                    moving = this.moved;
                }
            });
            
            '${openId}' && $.cookie('Quoetions_Openid', '${openId}', 10000);
        }

        loaded();
    </script>
</body>
</html>
