﻿<!doctype html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0,minimum-scale=1.0" />
    <meta name="format-detection" content="telephone=no">
      <title>微行情</title>
      <link rel="stylesheet" type="text/css" href="../public/css/style${compressSuffix}.css?${version}">
</head>
  <body onselectstart="return false;">
    <div id="wrapper" class="wrapper">
      <div class="scroller">
        <div class="page-detail" d="0">
          <div class="top-container">
            <div class="header">
              <div class="left">
                <div class="btn back" onClick="history.back();"></div>
                <div class="btn f10"></div>
              </div>
              <div class="right">
                <div class="btn add addMyBox"></div>
                <div class="btn search"></div>
              </div>
              <div class="title">
                <div class="mid">
                  <span class='mc'>--</span>
                  <span class='dm'>--</span>
                </div>
              </div>
            </div>
            <div class="middle">
              <div class="left">
                <div class="newprice">
                  <span class='zx'>--</span>
                </div>
                <div class="change">
                  <span class='zde'>--</span>
                  <span class='zdf'>--</span>
                </div>
              </div>
              <div class="right">
                <div class="row">
                  <div class="col">
                    <span class="item">今开</span>
                    <span class="jk value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">昨收</span>
                    <span class="zs value">--</span>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <span class="item">成交量</span>
                    <span class="zl value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">换手率</span>
                    <span class="hs value">--</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="bottom">
              <table class="t-data">
                <tr>
                  <td height="2"></td>
                </tr>
                <tr>
                  <td class="c0">最低</td>
                  <td class="c1">
                    <span class='zd'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">最高</td>
                  <td class="c4">
                    <span class='zg'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">成交额</td>
                  <td class="c7">
                    <span class='ze'>--</span>
                  </td>
                </tr>
                <tr>
                  <td class="c0">量比</td>
                  <td class="c1">
                    <span class='lb'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">均价</td>
                  <td class="c4">
                    <span class='jj'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">委比</td>
                  <td class="c7">
                    <span class='wb'>--</span>
                  </td>
                </tr>
				<tr>
                  <td class="c0">市盈</td>
                  <td class="c1">
                    <span class='sy'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">市值</td>
                  <td class="c4">
                    <span class='sz'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">股本</td>
                  <td class="c7">
                    <span class='gb'>--</span>
                  </td>
                </tr>
                <tr>
                  <td height="2"></td>
                </tr>
              </table>
            </div>
          </div>
          <div class="main-container">
            <div class="canvas-container">
              <canvas class="canvas0 canvas"></canvas>
              <div class="technical">指标</div>
            </div>
          </div>
        </div>

        <div class="page-detail selectedDetail" d="1">
          <div class="top-container">
            <div class="header">
              <div class="left">
                <div class="btn back" onClick="history.back();"></div>
                <div class="btn f10"></div>
              </div>
              <div class="right">
                <div class="btn add addMyBox"></div>
                <div class="btn search"></div>
              </div>
              <div class="title">
                <div class="mid">
                  <span class='mc'>----</span>
                  <span class='dm'>--</span>
                </div>
              </div>
            </div>
            <div class="middle">
              <div class="left">
                <div class="newprice">
                  <span class='zx'>--</span>
                </div>
                <div class="change">
                  <span class='zde'>--</span>
                  <span class='zdf'>--</span>
                </div>
              </div>
              <div class="right">
                <div class="row">
                  <div class="col">
                    <span class="item">今开</span>
                    <span class="jk value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">昨收</span>
                    <span class="zs value">--</span>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <span class="item">成交量</span>
                    <span class="zl value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">换手率</span>
                    <span class="hs value">--</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="bottom">
              <table class="t-data">
                <tr>
                  <td height="2"></td>
                </tr>
                <tr>
                  <td class="c0">最低</td>
                  <td class="c1">
                    <span class='zd'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">最高</td>
                  <td class="c4">
                    <span class='zg'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">成交额</td>
                  <td class="c7">
                    <span class='ze'>--</span>
                  </td>
                </tr>
				<tr>
                  <td class="c0">量比</td>
                  <td class="c1">
                    <span class='lb'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">均价</td>
                  <td class="c4">
                    <span class='jj'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">委比</td>
                  <td class="c7">
                    <span class='wb'>--</span>
                  </td>
                </tr>
                <tr>
                  <td class="c0">市盈</td>
                  <td class="c1">
                    <span class='sy'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">市值</td>
                  <td class="c4">
                    <span class='sz'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">股本</td>
                  <td class="c7">
                    <span class='gb'>--</span>
                  </td>
                </tr>
                <tr>
                  <td height="2"></td>
                </tr>
              </table>
            </div>
          </div>
          <div class="main-container">
            <div class="canvas-container">
              <canvas class="canvas0 canvas"></canvas>
              <div class="technical">指标</div>
            </div>
          </div>
        </div>

        <div class="page-detail" d="2">
          <div class="top-container">
            <div class="header">
              <div class="left">
                <div class="btn back" onClick="history.back();"></div>
                <div class="btn f10"></div>
              </div>
              <div class="right">
                <div class="btn add addMyBox"></div>
                <div class="btn search"></div>
              </div>
              <div class="title">
                <div class="mid">
                  <span class='mc'>----</span>
                  <span class='dm'>--</span>
                </div>
              </div>
            </div>
            <div class="middle">
              <div class="left">
                <div class="newprice">
                  <span class='zx'>--</span>
                </div>
                <div class="change">
                  <span class='zde'>--</span>
                  <span class='zdf'>--</span>
                </div>
              </div>
              <div class="right">
                <div class="row">
                  <div class="col">
                    <span class="item">今开</span>
                    <span class="jk value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">昨收</span>
                    <span class="zs value">--</span>
                  </div>
                </div>
                <div class="row">
                  <div class="col">
                    <span class="item">成交量</span>
                    <span class="zl value">--</span>
                  </div>
                  <div class="col">
                    <span class="item">换手率</span>
                    <span class="hs value">--</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="bottom">
              <table class="t-data">
                <tr>
                  <td height="2"></td>
                </tr>
                <tr>
                  <td class="c0">最低</td>
                  <td class="c1">
                    <span class='zd'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">最高</td>
                  <td class="c4">
                    <span class='zg'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">成交额</td>
                  <td class="c7">
                    <span class='ze'>--</span>
                  </td>
                </tr>
				<tr>
                  <td class="c0">量比</td>
                  <td class="c1">
                    <span class='lb'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">均价</td>
                  <td class="c4">
                    <span class='jj'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">委比</td>
                  <td class="c7">
                    <span class='wb'>--</span>
                  </td>
                </tr>
                <tr>
                  <td class="c0">市盈</td>
                  <td class="c1">
                    <span class='sy'>--</span>
                  </td>
                  <td class="c2">&nbsp;</td>
                  <td class="c3">市值</td>
                  <td class="c4">
                    <span class='sz'>--</span>
                  </td>
                  <td class="c5">&nbsp;</td>
                  <td class="c6">股本</td>
                  <td class="c7">
                    <span class='gb'>--</span>
                  </td>
                </tr>
                <tr>
                  <td height="2"></td>
                </tr>
              </table>
            </div>
          </div>
          <div class="main-container">
            <div class="canvas-container">
              <canvas class="canvas0 canvas"></canvas>
              <div class="technical">指标</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <footer class="footer">
      <div id="line-container" class="line-container">
        <div class="line-scroller">
          <div>
            <div class="minuts-line">分时</div>
          </div>
          <div>
            <div class='day-k'>日K</div>
          </div>
          <div>
            <div class='week-k'>周K</div>
          </div>
          <div>
            <div class='month-k'>月K</div>
          </div>
          <div>
            <div class="minuts5-line">5分</div>
          </div>
          <div>
            <div class="minuts15-line">15分</div>
          </div>
          <div>
            <div class="minuts30-line">30分</div>
          </div>
          <div>
            <div class="minuts60-line">60分</div>
          </div>
        </div>
      </div>
    </footer>
    <div class="mBox" id="mBox">
      <div class="technicalBox">
        <div class="curr">VOL</div>
        <div class="">MACD</div>
        <div class="">KDJ</div>
        <div class="">DMA</div>
        <div class="">RSI</div>
      </div>
    </div>
    <div class="waiting"></div>
	<script type="text/javascript" src="../public/js/common${compressSuffix}.js?${version}"></script>
    <script src="../public/js/zepto.min.js?${version}" type="text/javascript"></script>
    <script src="../public/js/zepto.expand${compressSuffix}.js?${version}" type="text/javascript"></script>
    <script src="../public/js/graphic${compressSuffix}.js?${version}" type="text/javascript"></script>
    <script src="../public/js/app_noTrade${compressSuffix}.js?${version}" type="text/javascript"></script>
    <script src="../public/js/jMessage${compressSuffix}.js?${version}" type="text/javascript"></script>
    <script src='../public/js/iscroll.min.js?${version}' type="text/javascript"></script>
    <script type="text/javascript">
      document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	  var openid = $.cookie('Quoetions_Openid'), 
	  arrFllowUrl = ['/RoxMarket/follow/addFollow.do?openId=' + openid,
      '/RoxMarket/follow/delFollow.do?openId=' + openid,
      '/RoxMarket/follow/getFollow.do?openId=' + openid];
      var myScroll, curTechnical = 'VOL', currentIndex = 1, bottomHeight = 42, tradeServerHomePath = '${tradeServerHomePath}';
      var selfSelect = '${type}' ? '0' : '1', //自选股没有type
      type = '${type}', order = '${order}', sortField = '${sortField}', curCode = '${code}', prevCode = '', nextCode = '';
      //var selfSelect = '0', //自选股没有type
      //type = '-1', order = '1', sortField = '', curCode = 'sh600755', prevCode = '', nextCode = '';
      function loaded() {
      $('.page-detail,.top-container,.main-container').css('width', $(window).width() + 'px');
      $('.scroller').css('width', $(window).width() * 3 + 'px');
      $('.main-container,.canvas-container').css('height', ($(window).height() - $('.top-container').height() - bottomHeight) + 'px');
      myScroll = new iScroll("wrapper", {
      hScroll: true,
      hScrollbar: false,
      vScroll: false,
      vScrollbar: false,
      lockDirection: true,
      bounce: false,
      useTransition: true,
      scroll_ing: false,
      onScrollEnd: function () {
      if (this.scroll_ing) {
      this.scroll_ing = false;
      var index = Math.ceil(Math.abs(this.x) / $('.wrapper').width());
      if (currentIndex == index) return;
      stockDetailChange(index);
      }
      },
      onTouchEnd: function () {
      this.scroll_ing = true;
      },
      snap: true,
      momentum: false
      //snapThreshold: $(window).width() / 2
      });
      myScroll.scrollToPage(1, 0, 0);

      lineScroll = new iScroll('line-container', {
      vScroll: false,
      vScrollbar: false,
      hScrollbar: false,
      lockDirection: true
      });
      }

      function stockDetailChange(index){
      var seletedIndex = $('.line-scroller div div.selected').parent().index();
      $('.page-detail').eq(index).addClass('selectedDetail').siblings().removeClass('selectedDetail');
      if(index > currentIndex){
      curCode = nextCode;
      window.pageObject.init({el: '.selectedDetail', stockCode: nextCode, lineIndex: seletedIndex, tech: curTechnical});
      $('.page-detail').first().appendTo($('.scroller'));
      clearData($('.page-detail').first());
      }
      else if(index < currentIndex){
				curCode = prevCode;
				window.pageObject.init({el: '.selectedDetail', stockCode: prevCode, lineIndex: seletedIndex, tech: curTechnical});
				$('.page-detail').last().prependTo($('.scroller'));
				clearData($('.page-detail').last());
			}
			myScroll && myScroll.scrollToPage(1, 0, 0);
      }

      function clearData(oDetail){
      if(oDetail){
      oDetail.find('.top-container').find('.mc,.dm,.zx,.zde,.zdf,.jk,.zs,.zl,.hs,.zd,.sy,.zg,.sz,.ze,.gb,.lb,.jj,.wb').text('--');
      oDetail.find('.main-container canvas').each(function(i){
      var ctx = this.getContext && this.getContext('2d');
      ctx && ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);
      });
      }
      }
      loaded();
    </script>
  </body>
</html>
