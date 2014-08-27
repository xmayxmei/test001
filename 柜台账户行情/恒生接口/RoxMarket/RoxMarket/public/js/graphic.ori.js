
;(function($){

	// The self-propagating extend function that Backbone classes use.
  	var extend = function (protoProps, classProps) {
    	var child = inherits(this, protoProps, classProps);
    	child.extend = this.extend;
    	return child;
  	};

  	// Shared empty constructor function to aid in prototype-chain creation.
  	var ctor = function(){};

  	var inherits = function(parent, protoProps, staticProps) {
	    var child;

	    // The constructor function for the new subclass is either defined by you
	    // (the "constructor" property in your `extend` definition), or defaulted
	    // by us to simply call `super()`.
	    if (protoProps && protoProps.hasOwnProperty('constructor')) {
	      child = protoProps.constructor;
	    } else {
	      child = function(){ return parent.apply(this, arguments); };
	    }

	    // Inherit class (static) properties from parent.
	    $.extend(child, parent);

	    // Set the prototype chain to inherit from `parent`, without calling
	    // `parent`'s constructor function.
	    ctor.prototype = parent.prototype;
	    child.prototype = new ctor();

	    // Add prototype properties (instance properties) to the subclass,
	    // if supplied.
	    if (protoProps) $.extend(child.prototype, protoProps);

	    // Add static properties to the constructor function, if supplied.
	    if (staticProps) $.extend(child, staticProps);

	    // Correctly set child's `prototype.constructor`.
	    child.prototype.constructor = child;

	    // Set a convenience property in case the parent's prototype is needed later.
	    child.__super__ = parent.prototype;

	    return child;
	};

	var idCounter = 0;
	var viewOptions = ["el", "ctx"];

	$.uniqueId = function(prefix) {
	    var id = idCounter++;
	    return prefix ? prefix + id : id;
	};

	$.log = function(){
		if(window.console) console.log.apply(console, arguments)
	};

	Graphic = function(options){		
		this.cid = $.uniqueId("view"),
		this.data = {},
		this._configure(options || {}),		
		this.initialize.apply(this, arguments);
	};

	Graphic.prototype = {
		_configure: function(options){
			if (this.options) options = $.extend({}, this.options, options);

			for (var i = 0, l = viewOptions.length; i < l; i++) {
		        var attr = viewOptions[i];
		        if (options[attr]) this[attr] = options[attr];
      		}

			this.options = options;

			if(this.el && typeof this.el === "string"){    			
      			var $el = $(this.el);
      			//CanvasObject
				this.el = $el.get(0);
				//CanvasRenderingContext2D
				if(this.el && this.el.getContext) this.ctx = this.el.getContext("2d");
      		}

		},
		// Initialize is an empty function by default. Override it with your own initialization logic.
		initialize: function(){},////这些都不会执行，因为后面有重写
		render: function(){},
		refresh: function(){},
		setData: function(data){ this.data = data },////这些会执行，因为后面没有重写
		getData: function(){ return this.data },
		getCanvasContext: function(){ return this.ctx },
		getCanvasOffset: function(){ return $(this.el).offset() },
		pushData: function(object){
			/*var items = this.data.items, 
				itemsLength = items.length, 
				maxDots = this.options.maxDots || 240, 
				list = object.items,
				listLength;

			$.extend(true, this.data.stockData, object.stockData);

			if( $.isArray(items) && $.isArray(list) ) {

				listLength = list.length;

				

				for (var i = 0; i < listLength; i++) {
					items.push( list[i] )
				}

				//删除超出maxDots的元素
				if( itemsLength + listLength > maxDots){
					items.splice( 0,  itemsLength + listLength - maxDots)
				}

			}*/
		},
		popData: function(){
			var d = this.data.items.pop();
			return d;
		}


	};

	Painter = function(ctx, options){
		this.ctx = ctx,
		this.options = options,	
		this.maxDiff = 0,
		this.minDiff = 100000000000,
		this.maxVolume = 0,	
		this.initialize.apply(this, arguments);
	};

	Painter.prototype = {
		initialize: function(){},
		paint: function(data){
			/*if(data.items && data.items.length > 10){
				data.items[0].newPrice = -1;
				data.items[1].newPrice = -1;
				data.items[2].newPrice = -1;
				data.items[3].newPrice = -1;
				data.items[4].newPrice = -1;

				data.items[7].newPrice = -1;
				data.items[9].newPrice = -1;
			}*/
			if( data ) this.data = data;

			if( !this.ctx || !this.data || !this.options) return;

			this.paintStart();
			try{
				this.paintItems();
			}catch(e){
				
			}
			
			this.paintEnd();
		},
		paintStart: function(){ this.ctx.beginPath() },
		paintItems: function(){
			var dataLength = this.getDataLength(), x, y, 
			 	start = 0,
				// 截断超出画布的元素
				length = this.getMaxPaintItemsLength();
				start = length < dataLength ? dataLength - length : 0;
			// 当画布容不下所有数据时，取最新的N条
        	for ( var i = start; i < dataLength; i++) {
        		x = this.getX(i - start),
        		y = this.getY(i),
	        	this.paintItem(i, x, y);
	        };
		},
		paintItem: function(){},
		paintEnd: function(){ this.ctx.stroke() },
		getX: function(i){ return 0 },
		getY: function(i){ return 0 },
		getItems: function(){
			return this.data.items || []
		},

		getItemValue: function(i){
			if(!this.data.items[i]){
				return 0;
			}
			return this.data.items[i].newPrice
		},
		getItemAverageValue: function(i){
			if(!this.data.items[i]){
				return 0;
			}
			return this.data.items[i].average
		},
		getItemVolume: function(i){
			if(!this.data.items[i]){
				return 0;
			}
			return this.data.items[i].volume
		},
		getDataLength: function(){
			return this.getItems().length
		},
		getMiddleValue: function(){
			//取昨收价为中间值
			return this.data.stockData.zs
			// 今天的开盘价为中间值
			// return this.data.stockData.jk
		},

		getItemHighValue: function(i){
			//返回当前记录最高价
			return this.data.items[i].high;
		},
		getItemLowValue: function(i){
			//返回当前记录最低价
			return this.data.items[i].low;
		},
		getItemStartValue: function(i){
			//返回当前记录开盘价
			return this.data.items[i].open;
		},
		getItemEndValue: function(i){
			//返回当前记录收盘价
			return this.data.items[i].close;
		},
		getLowerLimitValue: function(){
			return this.data.stockData.lowerLimit;
		},
		getHighLimitValue: function(){
			return this.data.stockData.upperLimit;
		},
		getItemMACD: function(i){
			return this.data.items[i].MACD;
		},
		getItemKDJ: function(i){
			return this.data.items[i].KDJ;
		},
		getItemRSI: function(i){
			return this.data.items[i].RSI;
		},
		getItemDMA: function(i){
			return this.data.items[i].DMA;
		},
		// 画布最大容纳数据量
		getMaxPaintItemsLength: function(){
			//var o = this.options, borderWidth = 1, max;
			// 获取画布所能容纳最大数据量
			//if(typeof o.width !== 'undefined'){
			//	width = (o.region.width - borderWidth*2 + o.splitSpace*2) / (o.maxDots) - o.splitSpace*2;
			//	width = 1 + 2 * Math.floor( width/2 ); //保证width是奇数
			//	if ( width < o.minWidth ){
			//		width = o.minWidth;
			//	}else if ( width > o.maxWidth ){
			//		width = o.maxWidth;
			//	}
			//	o.width = width; //保存重新计算得出的width的值，以便在paintItem中使用
			//	max = Math.floor((o.region.width + 2) / (o.width + o.splitSpace*2));

			//	return  max > o.maxDots ? o.maxDots : max;
			//}

			//return o.maxDots || this.getDataLength() || 0;
			
			var o = this.options, borderWidth = 1, size;
			// 获取画布所能容纳最大数据量
			if(typeof o.width !== 'undefined'){
				size = Math.floor((o.region.width - borderWidth*2) / (o.width + o.splitSpace*2));
				return  size;
			}
			return o.maxDots || this.getDataLength() || 0;		
		}
	};

	
	// Set up inheritance for the graphic, painter.
	Graphic.extend = Painter.extend = extend;

})(Zepto)

;(function($){
    var MAMAX = 20; //均线最大值
    var MA = function (arr, index, splitDay, attrName) {
        var sum = 0;
        if (index < splitDay) {
            for (var i = 0; i <= index; i++) {
                sum += Number(arr[i][attrName]);
            }
            return sum / (index + 1);
        } else {
            for (var i = index; i > index - splitDay; i--) {
                sum += Number(arr[i][attrName]);
            }
            return sum / splitDay;
        }
    };
	LinePainter = Painter.extend({
		initialize: function(ctx, options){},
		paintStart: function(){
			var o = this.options, region = o.region, ctx = this.ctx;

            ctx.save();
            //映射画布上的 (0,0) 位置为曲线图的左侧中间位置。因为价格线的中间价格是不变的（一般取昨收价），曲线围绕中间价格波动
            ctx.translate(region.x, region.y + region.height / 2);

            
        	this.setMaxDiff();
            
            
	        ctx.beginPath();
	        ctx.strokeStyle = o.priceLineColor;
	        //设置线的交叉处为圆角
	        ctx.lineJoin = "round";
		},
		// 计算最大价格偏差
		setMaxDiff: function(){
			var diff, items = this.getItems(), o = this.options, region = o.region, ctx = this.ctx, max;
			//计算价格的最大偏移量（相对于中间价，即昨收价）
            for (var i = 0; i < items.length; i++) {
            	//偏移量
				if( this.getItemValue(i) > 0 ){
					diff = Math.abs( this.getMiddleValue() - this.getItemValue(i) );
					this.maxDiff = Math.max( diff, this.maxDiff );
				}
            };

            //增加一些偏移量，避免线条的最高或最低点到达图的顶端
            if( this.maxDiff == 0){
            	this.maxDiff = this.getMiddleValue() * 0.1 + 1;
            }
            this.maxDiff += this.maxDiff / (o.horizontalLineCount + 1);

            // 上下限不超过规定的上下限
            if(this.getHighLimitValue()){
            	max = this.getHighLimitValue() - this.getMiddleValue();
            	this.maxDiff = Math.min(max, this.maxDiff);
            }
		},
		paintItem: function(i, x, y){
			if(i===0 && y!==false){
				if(this.getY(i+1)===false){
					this.ctx.arc(x, y, 0.5, 0, 2*Math.PI);
				}
				this.ctx.moveTo(x, y);
			}else if( y!==false && this.getY(i-1)!==false ){
				this.ctx.lineTo(x, y);
			}else if(y!==false){
				// 如果是孤立的点则画一个圆点
				if(this.getY(i+1)===false){
					this.ctx.arc(x, y, 0.5, 0, 2*Math.PI);
				}
				
				this.ctx.moveTo(x, y);
			}else{
				this.ctx.stroke();
				this.ctx.beginPath();
			}
		},
		paintEnd: function(){
			var ctx = this.ctx;
			ctx.stroke()
			ctx.restore()	
		},
		getX: function(i){
			var o = this.options, borderWidth = 1;
			//线的x坐标用四舍五入取值，避免像素差的问题
			return Math.round( i * ( (o.region.width - borderWidth * 2) / (o.maxDots - 1) ) ) + borderWidth - 1;
		},
		getY: function(i){
			
			var o = this.options,
				//偏移量
				diff = this.getItemValue(i) - this.getMiddleValue();
				
			if(isNaN(this.getItemValue(i)) || Math.abs(diff)>Math.abs(this.maxDiff) || this.getItemValue(i)===0){
				return false;				
			}
			//点的Y坐标 = 偏移量 / 最大偏移量 * 曲线图的高度(因当前画布的工作坐标0,0为图的左侧中间位置，所以这里图的高度实际为一半)
	        return -( diff / this.maxDiff * (o.region.height / 2) )
		}
	});
	AverageLinePainter = LinePainter.extend({
		
		getY: function(i){
			var o = this.options,
				//偏移量
				diff = this.getItemAverageValue(i) - this.getMiddleValue();
			if(isNaN(this.getItemAverageValue(i)) || Math.abs(diff)>Math.abs(this.maxDiff) || this.getItemValue(i)===0 ){
				return false;				
			}
			//点的Y坐标 = 偏移量 / 最大偏移量 * 曲线图的高度(因当前画布的工作坐标0,0为图的左侧中间位置，所以这里图的高度实际为一半)
	        return -( diff / this.maxDiff * (o.region.height / 2) )
		}
	});
	MountainLinePainter = LinePainter.extend({
	    paintStart: function () {
	        var o = this.options, region = o.region, ctx = this.ctx;
	        ctx.save();
	        //映射画布上的 (0,0) 位置为曲线图的左侧中间位置。因为价格线的中间价格是不变的（一般取昨收价），曲线围绕中间价格波动
	        ctx.translate(region.x, region.y + region.height / 2);
	        this.setMaxDiff();
	        ctx.beginPath();
	        ctx.strokeStyle = o.priceLineColor;
	        ctx.fillStyle = o.MountainFillColor;
	        ctx.globalAlpha = o.MountainFillAlpha;
	        //设置线的交叉处为圆角
	        ctx.lineJoin = "round";
	    },
	    paintItem: function (i, x, y) {
	        var o = this.options, ctx = this.ctx;
	        var y0 = this.getY(i - 1), y1 = this.getY(i + 1);
	        if (y!==false && y0 === false && y1 === false) {
	            ctx.arc(x, y, 0.5, 0, Math.PI * 2);    // 如果是孤立的点则画一个圆点
	        }else if (y!==false && y0 === false) {
	            ctx.moveTo(x, y);
	            this.startPos = [x, y];
	        }else if (y!==false && y1 === false) {
	            ctx.lineTo(x, y);
	            ctx.globalAlpha = 1;
	            ctx.stroke();
	            ctx.lineTo(x, o.region.height / 2);
	            ctx.lineTo(this.startPos[0], o.region.height / 2);
	            ctx.lineTo(this.startPos[0], this.startPos[1]);
	            ctx.globalAlpha = o.MountainFillAlpha;
	            ctx.fill();
	            ctx.beginPath();
	        }else if (y!==false) {
	            ctx.lineTo(x, y);
	        }
	    }
	});
	// 坐标点在原点,均价线
	KLinePainter = Painter.extend({
		initialize: function(ctx, options){},
		paintStart: function(){
			var diff, items = this.getItems(), o = this.options, region = o.region, ctx = this.ctx;
            ctx.save();
            ctx.translate(region.x, region.y + region.height);
	        ctx.beginPath();
	        ctx.strokeStyle = o.volLineColor; 
	        ctx.lineJoin = "round";
		},
		paintItem: function(i, x, y){	
			i === 0 ? this.ctx.moveTo(x, -y) : this.ctx.lineTo(x, -y)			
		},
		paintEnd: function(){
			var ctx = this.ctx;
			ctx.stroke()
			ctx.restore()	
		},
		getX: function(i){
			var o = this.options;
			return Math.round( i * (o.splitSpace * 2 + o.width) + o.splitSpace + o.width/2) + 0.5;
		},
		getY: function(i){
			var o = this.options,
				max = o.maxVolume || o.maxDiff,
				min = o.minDiff || 0,
				splitDay = o.splitDay,
				data = this.data,
				Aver;
			//柱高 = 当前量 / 最大量 * 量图的高度
			if(o.priceLine){
				Aver = average(data.items, i, splitDay, o.priceLine);
				//保存最后一个MA数值
				if(i == data.items.length - 1){
					data.items[i]['MA' + splitDay] = Aver;
				}
				columnHeight = (Aver -min) / (max-min) * o.region.height;
			}else{
				columnHeight = average(data.items, i, splitDay, o.priceLine) / max * o.region.height;
			}
			
			function average(arr, index, splitDay, isPriceLine){
				var sum = 0, attr;
				if(isPriceLine){
					attr = 'close';
				}else{
					attr = 'volume';
				}
				if(index < splitDay){
					for(var i = 0; i <= index; i++){
						sum += Number(arr[i][attr]);
					}
					return sum/(index+1);
				}else{
					for (var i = index; i > index - splitDay; i--) {
						sum += Number(arr[i][attr]);
					}
					return sum/splitDay;
				}

			}
				
        		
        	return columnHeight;
		}
	});

	TechnicalLinePainter = Painter.extend({
	    paintStart: function () {
	        var volume, items = this.getItems(), o = this.options, region = this.options.region, ctx = this.ctx;
	        ctx.save();
			this.maxAbsTechnicalDiff = o.maxAbsTechnicalDiff || 0;
			this.maxTechnicalDiff = o.maxTechnicalDiff || 0;
			this.minTechnicalDiff = o.minTechnicalDiff || 0;
			ctx.strokeStyle = o.lineType == 0 ? o.curveLineColor1 : (o.lineType == 1 ? o.curveLineColor2 : o.curveLineColor3);
	        if(o.technicalAnalysis == 'MACD' || o.technicalAnalysis == 'DMA'){
	            ctx.translate(o.region.x, o.region.y + o.region.height / 2);
	        }
			else if(o.technicalAnalysis == 'KDJ' || o.technicalAnalysis == 'RSI'){
				ctx.translate(o.region.x, o.region.y + o.region.height - o.topAndBottomSpace);
			}
	        ctx.beginPath();
	        ctx.lineJoin = "round";
	    },
	    paintItem: function (i, x, y) {
	        i === 0 ? this.ctx.moveTo(x, y) : this.ctx.lineTo(x, y);
	    },
	    paintEnd: function () {
	        var ctx = this.ctx;
	        ctx.stroke();
	        ctx.restore();
	    },
	    getX: function (i) {
	        var o = this.options;
	        return Math.round(i * (o.splitSpace * 2 + o.width) + o.splitSpace + o.width / 2);
	    },
	    getY: function (i) {
	        var o = this.options, dataLength = this.getDataLength(), items = this.getItems();
	        var itemValue = null, y;
	        switch (o.technicalAnalysis) {
				case 'MACD':
	            	itemValue = this.getItemMACD(i)[o.lineType] || 0;
					y = -((o.region.height / 2 - o.topAndBottomSpace) / this.maxAbsTechnicalDiff * itemValue);
					break;
				case 'KDJ':
	            	itemValue = this.getItemKDJ(i)[o.lineType] || 0;
					y = -((o.region.height - o.topAndBottomSpace * 2) / (this.maxTechnicalDiff - this.minTechnicalDiff) * (itemValue - this.minTechnicalDiff));
					break;
				case 'RSI':
	            	itemValue = this.getItemRSI(i)[o.lineType] || 0;
					y = -((o.region.height - o.topAndBottomSpace * 2) / (this.maxTechnicalDiff - this.minTechnicalDiff) * (itemValue - this.minTechnicalDiff));
					break;
				case 'DMA':
	            	itemValue = this.getItemDMA(i)[o.lineType] || 0;
					y = -((o.region.height / 2 - o.topAndBottomSpace) / this.maxAbsTechnicalDiff * itemValue);
					break;
	        }
	        return y;
	    }
	});


	VolumePainter = Painter.extend({
		paintStart: function(){
			var volume, items = this.getItems(), region = this.options.region, ctx = this.ctx;

            ctx.save();
            //映射画布上的 (0,0) 位置为量图的左下角
            ctx.translate(region.x, region.y + region.height);

            //计算当前成交量的最大值
            for (var i = 0; i < items.length; i++) {
            	volume = this.getItemVolume(i);
	            this.maxVolume = Math.max(volume, this.maxVolume);
            };

		},
		paintItem: function(i, x, y){
			var ctx = this.ctx,  
				o = this.options;
			
	        ctx.beginPath();
	        ctx.moveTo( x, 0 );
	        ctx.lineTo( x, -y ); //柱型的开始坐标为量图的底部，所以Y为负值

	        if(this.getItemValue(i) > this.getMiddleValue()){

	        	ctx.strokeStyle = o.riseColor;

	        }else if(this.getItemValue(i) < this.getMiddleValue()){

	        	ctx.strokeStyle = o.fallColor;

	        }else{

	        	ctx.strokeStyle = o.equalColor;

	        }
			
			ctx.stroke();

			
		},
		paintEnd: function(){
			this.ctx.restore();
		},
		getX: function(i){
			var o = this.options, borderWidth = 1;
			//return o.region.x + i * (this.getColumnWidth() + o.spaceWidth);
			return Math.round( i * ( (o.region.width - borderWidth * 2) / (o.maxDots - 1) ) ) + borderWidth;
		},
		getY: function(i){
			var o = this.options,
				maxVolume = this.maxVolume,
				//柱高 = 当前量 / 最大量 * 量图的高度
				columnHeight = this.getItemVolume(i) / maxVolume * o.region.height;
        		
        	return columnHeight;
		}
	});

	// 成交量图
	DealVolumePainter = VolumePainter.extend({
	    paintStart: function () {
	        var volume, items = this.getItems(), region = this.options.region, ctx = this.ctx, startIndex, endIndex;
	        ctx.save();
	        //映射画布上的 (0,0) 位置为量图的左下角
	        ctx.translate(region.x, region.y + region.height);

	        //计算当前成交量的最大值
	        startIndex = items.length - this.getMaxPaintItemsLength();
	        startIndex = startIndex < 0 ? 0 : startIndex;
	        endIndex = startIndex + 5 - 2 > items.length - 1 ? items.length - 1 : startIndex + 5 - 2;
	        for (var i = startIndex; i <= endIndex; i++) {
	            var m_MA = MA(items, i, 5, 'volume');
	            this.maxVolume = Math.max(m_MA, this.maxVolume);
	        }
	        endIndex = startIndex + 10 - 2 > items.length - 1 ? items.length - 1 : startIndex + 10 - 2;
	        for (var i = startIndex; i <= endIndex; i++) {
	            var m_MA = MA(items, i, 10, 'volume');
	            this.maxVolume = Math.max(m_MA, this.maxVolume);
	        }
	        endIndex = startIndex + 20 - 2 > items.length - 1 ? items.length - 1 : startIndex + 20 - 2;
	        for (var i = startIndex; i <= endIndex; i++) {
	            var m_MA = MA(items, i, 20, 'volume');
	            this.maxVolume = Math.max(m_MA, this.maxVolume);
	        }

	        for (var i = startIndex; i < items.length; i++) {
	            volume = this.getItemVolume(i);
	            this.maxVolume = Math.max(volume, this.maxVolume);
	        };

	    },
		paintItem: function(i, x, y){
			var ctx = this.ctx,  width, //每个柱形图的宽度，包括柱形图之间的间隔
				o = this.options;
			
	        ctx.beginPath();

	        if(this.getItemStartValue(i) < this.getItemEndValue(i)){

	        	ctx.fillStyle = o.riseColor;
	        	ctx.strokeStyle = o.riseColor;

	        }else if(this.getItemStartValue(i) > this.getItemEndValue(i)){

	        	ctx.fillStyle = o.fallColor;
	        	ctx.strokeStyle = o.fallColor;

	        }else{

	        	ctx.fillStyle = o.equalColor;
	        	ctx.strokeStyle = o.equalColor;
	        }

	        //柱型的开始坐标为量图的底部，所以Y为负值
	        if(this.getItemVolume(i) == 0){

	        	ctx.moveTo(x - Math.ceil(o.width/2), y);
	        	ctx.lineTo(x + Math.floor(o.width/2), y);
	        }else{
	        	ctx.fillRect( x - Math.ceil(o.width/2), -y, o.width, y);
	        }
			
			ctx.stroke();

			
	        
		},
		getX: function(i){
			var o = this.options;
				
			//线的x坐标用四舍五入取值，避免像素差的问题。因为蜡烛图有一定的宽度，x轴中心点需要往右偏移
			return Math.round( i * (o.splitSpace * 2 + o.width) + o.splitSpace + o.width/2);

		}
	});
	
	//MACD柱形图
	MACDHistogramPainter = DealVolumePainter.extend({
		paintStart: function(){
			var volume, items = this.getItems(), o = this.options, region = this.options.region, ctx = this.ctx;
            ctx.save();
            ctx.translate(o.region.x, o.region.y + o.region.height / 2);
            this.maxAbsTechnicalDiff = o.maxAbsTechnicalDiff || 0;
		},
		paintItem: function(i, x, y){
			var ctx = this.ctx,  
				o = this.options;
	        ctx.beginPath();
	        ctx.moveTo( x, 0 );
	        ctx.lineTo( x, y );

	        if(y < 0){
	        	ctx.strokeStyle = o.riseColor;
	        }else if(y > 0){
	        	ctx.strokeStyle = o.fallColor;
	        }else{
	        	ctx.strokeStyle = o.equalColor;
	        }
			ctx.stroke();
		},
		getY: function(i){
			var o = this.options, dataLength = this.getDataLength(), items = this.getItems();
			return -((o.region.height/2 - o.topAndBottomSpace) / this.maxAbsTechnicalDiff * this.getItemMACD(i)[2]);
		}
	});

	//蜡烛图
	CandlePainter = Painter.extend({
		// initialize: function(ctx, options){},
		paintStart: function(){
			var o = this.options, region = o.region, ctx = this.ctx;

            ctx.save();
            // ctx.beginPath();
            //映射画布上的 (0,0) 位置为曲线图的左下角
            ctx.translate(region.x, region.y + region.height );
            
        	this.setDiff();
            
            
	        //ctx.strokeStyle = o.priceLineColor;
	        //设置线的交叉处为圆角
	        // ctx.lineJoin = "round";
		},
		setDiff: function(){
			var diff, items = this.getItems(), o = this.options, region = o.region, ctx = this.ctx, startIndex, endIndex, rate = 2;
			//计算价格的最大偏移量
            startIndex = items.length - this.getMaxPaintItemsLength();
            startIndex = startIndex < 0 ? 0 : startIndex;
            endIndex = startIndex + 5 - 2 > items.length - 1 ? items.length - 1 : startIndex + 5 - 2;
            for (var i = startIndex; i <= endIndex; i++) {
                var m_MA = MA(items, i, 5, 'close');
                this.maxDiff = Math.max(m_MA, this.maxDiff);
                this.minDiff = Math.min(m_MA, this.minDiff);
            }
            endIndex = startIndex + 10 - 2 > items.length - 1 ? items.length - 1 : startIndex + 10 - 2;
            for (var i = startIndex; i <= endIndex; i++) {
                var m_MA = MA(items, i, 10, 'close');
                this.maxDiff = Math.max(m_MA, this.maxDiff);
                this.minDiff = Math.min(m_MA, this.minDiff);
            }
            endIndex = startIndex + 20 - 2 > items.length - 1 ? items.length - 1 : startIndex + 20 - 2;
            for (var i = startIndex; i <= endIndex; i++) {
                var m_MA = MA(items, i, 20, 'close');
                this.maxDiff = Math.max(m_MA, this.maxDiff);
                this.minDiff = Math.min(m_MA, this.minDiff);
            }

            for (var i = startIndex; i < items.length; i++) {
            	//计算上下偏移量
            	diff = this.getItemHighValue(i);
            	lowest = this.getItemLowValue(i);
	            this.maxDiff = Math.max( diff, this.maxDiff );
	            this.minDiff = Math.min( lowest, this.minDiff);
            };

            //增加一些偏移量，避免线条的最高到达图的顶端
            //this.maxDiff += this.maxDiff*0.01;

            //减去一些偏移量，避免线条的最高到达图的底端
            //this.minDiff -= this.minDiff*0.01;
	        
	        //rate = ctx.canvas.width/(ctx.canvas.height*2/3);
	        //rate = rate < 1? 1 : rate;

	        //if(rate < 2){

	        //	this.maxDiff += 0.1*this.maxDiff*(2-rate)
	        //	this.minDiff -= 0.1*this.minDiff*(2-rate)

	        //}
		},
		paintItem: function(i, x, y){
			// y是一个数组，[开盘价,收盘价,最高价,最低价]
			//需要重写 i === 0 ? this.ctx.moveTo(x, y) : this.ctx.lineTo(x, y)	
			var ctx = this.ctx, o = this.options;
			ctx.beginPath();
			if(this.getItemStartValue(i) > this.getItemEndValue(i)){
				ctx.fillStyle = o.fallColor;
				ctx.strokeStyle = o.fallColor;
			}else if(this.getItemStartValue(i) < this.getItemEndValue(i)){
				ctx.fillStyle = o.riseColor;
				ctx.strokeStyle = o.riseColor;
			}else{
				ctx.fillStyle = o.equalColor;
				ctx.strokeStyle = o.equalColor;
			}
			//开盘价大于收盘价，因为y轴坐标是负数
			ctx.lineWidth = '1px';
			if(y[0] < y[1]){ 
				
				ctx.fillRect(x - Math.ceil(o.width/2), y[0], o.width, y[1] - y[0]);

				// x = x-1;
				// 上引线
				if(y[2] < y[0]){
					ctx.moveTo(x-0.5, y[0]);
					ctx.lineTo(x-0.5, y[2]);
				}
				// 下引线
				if(y[1] < y[3]){
					ctx.moveTo(x-0.5, y[1]);
					ctx.lineTo(x-0.5, y[3]);
				}
			}else{ //开盘价小于等于收盘价
				
				
				// 当开盘价与收盘价相等时画直线
				if(y[0] == y[1]){
					
					ctx.moveTo(x - Math.ceil(o.width/2), y[1]);
					ctx.lineTo(x + Math.floor(o.width/2), y[1]);
					
				}
				else{
					
					ctx.fillRect(x - Math.ceil(o.width/2), y[1], o.width, y[0] - y[1]);
					
				}
				
				
				// 上引线
				if(y[2] < y[1]){
					ctx.moveTo(x-0.5, y[1]);
					ctx.lineTo(x-0.5, y[2]);
				}
				// 下引线
				if(y[0] < y[3]){
					ctx.moveTo(x-0.5, y[0]);
					ctx.lineTo(x-0.5, y[3]);
				}

			}
			ctx.stroke();
			
		},
		paintEnd: function(){

			this.ctx.restore()
		},
		getX: function(i){
			var o = this.options;

			//线的x坐标用四舍五入取值，避免像素差的问题。因为蜡烛图有一定的宽度，x轴中心点需要往右偏移
			
			return Math.round( i * (o.splitSpace * 2 + o.width) + o.splitSpace + o.width/2) + 0.5;
		},
		getY: function(i){
			var o = this.options,
				//开盘价,收盘价,最高价,最低价
				startPrice = this.getItemStartValue(i),
				endPrice = this.getItemEndValue(i),
				highestPrice = this.getItemHighValue(i),
				lowestPrice = this.getItemLowValue(i),
				//当前绘画区域高度
				height = o.region.height;
				// console.log(this.maxDiff, highestPrice, lowestPrice, height);
			//点的Y坐标 = 偏移量 / 最大偏移量 * 曲线图的高度,[开盘价,收盘价,最高价,最低价]
	        return [-( Math.round((startPrice-this.minDiff) / (this.maxDiff-this.minDiff) * height) +0.5),
					-( Math.round((endPrice-this.minDiff) / (this.maxDiff-this.minDiff) * height) +0.5),
					-( Math.round((highestPrice-this.minDiff) / (this.maxDiff-this.minDiff) * height) +0.5),
					-( Math.round((lowestPrice-this.minDiff) / (this.maxDiff-this.minDiff) * height) +0.5)
					];
		}
		/*getItemHighValue: function(i){
			//返回当前记录最高价
			return this.data.items[i].high;
		},
		getItemLowValue: function(i){
			//返回当前记录最低价
			return this.data.items[i].low;
		},
		getItemStartValue: function(i){
			//返回当前记录开盘价
			return this.data.items[i].open;
		},
		getItemEndValue: function(i){
			//返回当前记录收盘价
			return this.data.items[i].close;
		}*/
		
	});

	yAxis = Painter.extend({
		paintStart: function(){
			var ctx = this.ctx, o = this.options;
	        ctx.save();
	        ctx.font = o.font;
	        ctx.translate(o.region.x, o.region.y);
	        ctx.textBaseline = o.textBaseline;
		},
		paintItem: function(i, x, y){
			var ctx = this.ctx;
			ctx.fillStyle = this.options.color || this.getTextColor(i);
			ctx.fillText(this.data[i], x, y);
		},
		//重写，因为Y轴最下面的会越界
		paintItems: function(){
			var dataLength = this.getDataLength(), x, y, 
			 	start = 0;
				// 截断超出画布的元素
				//length = this.getMaxPaintItemsLength();
				//start = length < dataLength ? dataLength - length : 0;
			// 当画布容不下所有数据时，取最新的N条
        	for ( var i = start; i < dataLength; i++) {
        		x = this.getX(i - start);
        		y = this.getY(i);
				if(i == dataLength -1){
					y -= 10;
				}
	        	this.paintItem(i, x, y);
	        };
		},
		paintEnd: function(){
			this.ctx.restore()
		},
		getX: function(i){
			var o = this.options;

			if (o.align == 'left') return 6;

	        var w = this.ctx.measureText( this.data[i] ).width;

	        return o.region.width - w - 10;
		},
		getY: function(i){
			var o = this.options, dataLength = this.getDataLength();
			if (i == 0) return 0;
	        if (i == dataLength - 1) return o.region.height - o.fontHeight;
	        return (o.region.height * i / (dataLength - 1) - o.fontHeight / 2);
		},
		getItems: function(){
			return this.data
		},
		getTextColor: function(i){
			var middleIndex = parseInt(this.getDataLength() / 2, 10),
				o = this.options;
			return i < middleIndex ? o.riseColor : (i == middleIndex ? o.normalColor : o.fallColor);
		}
	});
	
	yAxisTechnical = Painter.extend({
		paintStart: function(){
		    var ctx = this.ctx, o = this.options;
	        ctx.save();
	        ctx.font = o.font;
			this.maxAbsTechnicalDiff = o.maxAbsTechnicalDiff || 0;
	        this.maxTechnicalDiff = o.maxTechnicalDiff || 0;
			this.minTechnicalDiff = o.minTechnicalDiff || 0;
		    this.level = o.technicalAnalysis == 'KDJ' ? 100 : 1000;
			if(o.technicalAnalysis == 'MACD' || o.technicalAnalysis == 'DMA'){
	        	ctx.translate(o.region.x, o.region.y + o.region.height / 2);
			}
			else if(o.technicalAnalysis == 'KDJ' || o.technicalAnalysis == 'RSI'){
				ctx.translate(o.region.x, o.region.y + o.region.height - o.topAndBottomSpace);
			}
	        ctx.textBaseline = o.textBaseline;
		},
		paintItem: function(i, x, y){
		    var ctx = this.ctx;
			if (y === false) return;
			ctx.fillStyle = this.options.color || this.getTextColor(i);
			ctx.fillText((this.data[i] / this.level).toFixed(2) * 100 / 100, x, y);
		},
		paintItems: function(){
			var dataLength = this.getDataLength(), x, y;
        	for ( var i = 0; i < dataLength; i++) {
        		x = this.getX(i);
        		y = this.getY(i);
	        	this.paintItem(i, x, y);
	        };
		},
		paintEnd: function(){
			this.ctx.restore()
		},
		getX: function(i){
			var o = this.options;

			if (o.align == 'left') return 6;

			var w = this.ctx.measureText((this.data[i] / this.level).toFixed(2) * 100 / 100).width;

	        return o.region.width - w - 10;
		},
		getY: function(i){
			var o = this.options, dataLength = this.getDataLength(), items = this.getItems(), y;
			if(o.technicalAnalysis == 'MACD' || o.technicalAnalysis == 'DMA'){
				y = -((o.region.height/2 - o.topAndBottomSpace) / this.maxAbsTechnicalDiff * items[i]);
			}
			else if(o.technicalAnalysis == 'KDJ' || o.technicalAnalysis == 'RSI'){
				y = -((o.region.height - o.topAndBottomSpace * 2) / (this.maxTechnicalDiff - this.minTechnicalDiff) * (items[i] - this.minTechnicalDiff));
				if(y > 0 || y < -o.region.height)
					y = false;
			}
			return y;
		},
		getItems: function(){
			return this.data
		},
		getTextColor: function(i){
			//var middleIndex = parseInt(this.getDataLength() / 2, 10),
			//	o = this.options;
			//return i < middleIndex ? o.riseColor : (i == middleIndex ? o.normalColor : o.fallColor);
		}
	});
	
	xAxis = Painter.extend({
		paintStart: function(){
			var ctx = this.ctx, o = this.options;
	        ctx.save();
	        ctx.font = o.font;
	        ctx.translate(o.region.x, o.region.y);
	        ctx.textBaseline = o.textBaseline;
		},
		paintItem: function(i, x, y){
			var ctx = this.ctx;
			ctx.fillStyle = this.options.color || this.getTextColor(i);
			this.ctx.fillText(this.data[i], x, y);
		},
		paintEnd: function(){
			this.ctx.restore();
		},
		getX: function(i){
			var o = this.options, dataLength = this.getDataLength();
			if (i == 0) return 0;
	        var w = this.ctx.measureText(this.data[i]).width;
	        if (i == dataLength - 1) return o.region.width - w;
	        return (o.region.width * i / (dataLength - 1)) - w / 2;
		},
		getItems: function(){
			return this.data
		}
	});

})(Zepto)


;(function($){

	//分时图
	MinsGraphic = Graphic.extend({
		el: "#canvas",
		options: {
			maxDots: 241,
			riseColor: "#d50101", //红色
			fallColor: "#027e22", //绿色
			normalColor: "#ffffff",//可修改中间价格色
			equalColor: "#e7a91c", //黄色
			curveChart: {
				horizontalLineCount: 3, //水平线数量
				verticalLineCount: 3, //垂直线数量
				middleLineColor: '#2e3741', //中间线颜色 
				splitLineColor: '#2e3741', //分隔线颜色 灰色
				priceLineColor: '#1ca5bc', //价格线颜色 蓝色
				averageLineColor: 'yellow', //均价线颜色
				MountainFillColor: '#1ca5bc',   //山形填充色
				MountainFillAlpha: 0.3, //山形填充透明度
				borderColor: '#2e3741', //灰色
				region: {},
				scalerLeft: { font: '20px Helvetica', align: 'right', fontHeight: 16, textBaseline: 'top', region: {} },
				scalerRight: { font: '20px Helvetica', align: 'left', fontHeight: 16, textBaseline: 'top', region: {} },
				scalerTop: { font: '20px Helvetica', color: 'white', textBaseline: 'top', region: {} },
				scalerBottom: { font: '20px Helvetica', color: 'white', textBaseline: 'top', region: {}, data: ['09:30', '10:30', '11:30/13:00', '14:00', '15:00'] }
			},
			volumeChart: {
				horizontalLineCount: 1, //水平线数量
				splitLineColor: "#2e3741", //分隔线颜色
				borderColor: '#2e3741',
				priceLineColor: 'white', //价格线颜色
				region: {},
				scalerLeft: { font: '20px Helvetica', align: 'right', color: '#ffffff', fontHeight: 16, textBaseline: 'top', region: {} }
			}
		},
		pushData: function(object){
			var items = this.data.items, 
				// itemsLength = items.length, 
				maxDots = this.options.maxDots || 241, 
				list = object.items,
				index,
				listLength;

			$.extend(true, this.data.stockData, object.stockData);

			if( $.isArray(items) && $.isArray(list) ) {

				listLength = list.length;

				for(var i=0; i<listLength; i++){

					var date = new Date(Number(list[i].time));
					// 九点半之前为第一条数据
					if(date.getHours()<10 && date.getMinutes()<=30){
						
						items[0] = list[0];
					}else if(date.getHours()<11 || date.getHours()<12 && date.getMinutes()<=30){
						// 上午数据
						index = (date.getHours()-9)*60 + date.getMinutes() - 30;
						items[index] = list[i];

					}else if(date.getHours()< 15){
						// 下午数据,第一条应该是13:01
						index = (date.getHours()-13)*60 + date.getMinutes() + 120;
						items[index] = list[i];

					}else if(date.getHours()>=15 && date.getMinutes()>=0){
						// 收盘后数据为最后一条
						items[maxDots-1] = list[i];
					}
					
				}
			}
		},
		initialize: function(options){

			// $.log( "Initialize MinsGraphic" );
					
			if( !this.el || !this.ctx ) return;

			//计算画布及各个图块的区域位置、大于等
			//由于画布上的坐标并未对应上页面上的坐标，1px的线条可能存在像素差，加上0.5可以修正画布上的线条像素差问题。
			var el = this.el,
				minWidth = 600,
				minHeight = 400,
				canvas = { x: 0, y: 0, width: el.width/*Math.max(el.width, minWidth)*/, height: el.height/*Math.max(el.height, minHeight)*/ }, //画布

				leftRegion = { width: 100 },
				rightRegion = { width: options&&options.rightRegion || 90 },
				topRegion = { height: (options.hintChart ? 26 : 4) },
				centerRegion = { height: 40 },
				bottomRegion = { height: 8 }, //图形离画布底部的间隔

				curveChart = { //曲线图
					x: leftRegion.width - 0.5,
					y: topRegion.height - 0.5,
					width: canvas.width - leftRegion.width - rightRegion.width,
					//曲线图高度 / 柱图高度 = 2 / 1
					height: Math.round( ( canvas.height - topRegion.height - centerRegion.height - bottomRegion.height ) / 3 * 2)
				}, 
				volumeChart = { //柱图
					x: curveChart.x,
					y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
					width: curveChart.width,
					height: Math.round( curveChart.height / 2 )
				};
			
			leftRegion.x = 0,
			leftRegion.y = topRegion.height,
			leftRegion.height = curveChart.height;

			rightRegion.x = leftRegion.width + curveChart.width,
			rightRegion.y = leftRegion.y;
			rightRegion.height = leftRegion.height;

			topRegion.x = leftRegion.width,
			topRegion.y = 0,
			topRegion.width = curveChart.width;

			centerRegion.x = topRegion.x,
			centerRegion.y = topRegion.height + curveChart.height + 6,
			centerRegion.width = topRegion.width;

			$.extend(true, this.options.curveChart, {
				region: curveChart,
				scalerLeft: { region: leftRegion },
				scalerRight: { region: rightRegion },
				scalerTop: { region: topRegion },
				scalerBottom: { region: centerRegion }
			});

			leftRegion.y = volumeChart.y,
			leftRegion.height = volumeChart.height;

			$.extend(true, this.options.volumeChart, {
				region: volumeChart,
				scalerLeft: { region: leftRegion }
			});

			//更新画布大小
			//$(el).attr({width: canvas.width, height: canvas.height});

			//绑定相关事件
			this.initEvents();

			// this.ctx.clearRect(0, 0, this.el.width, this.el.height);

		},
		// 重新设置图形两边坐标轴的宽度
		reSetRegionWidth: function( options ){
			if( !this.el || !this.ctx ) return;

			//计算画布及各个图块的区域位置、大于等
			//由于画布上的坐标并未对应上页面上的坐标，1px的线条可能存在像素差，加上0.5可以修正画布上的线条像素差问题。
			var el = this.el,
				minWidth = 600,
				minHeight = 400,
				canvas = { x: 0, y: 0, width: el.width/*Math.max(el.width, minWidth)*/, height: el.height/*Math.max(el.height, minHeight)*/ }, //画布

				leftRegion = { width: options.leftRegion || 100 },
				rightRegion = { width: options.rightRegion || 90 },
				topRegion = { height: options.topRegion || 4 },
				centerRegion = { height: 40 },
				bottomRegion = { height: 8 }, //图形离画布底部的间隔

				curveChart = { //曲线图
					x: leftRegion.width - 0.5,
					y: topRegion.height - 0.5,
					width: canvas.width - leftRegion.width - rightRegion.width,
					//曲线图高度 / 柱图高度 = 2 / 1
					height: Math.round( ( canvas.height - topRegion.height - centerRegion.height - bottomRegion.height ) / 3 * 2)
				}, 
				volumeChart = { //柱图
					x: curveChart.x,
					y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
					width: curveChart.width,
					height: Math.round( curveChart.height / 2 )
				};
			
			leftRegion.x = 0,
			leftRegion.y = topRegion.height,
			leftRegion.height = curveChart.height;

			rightRegion.x = leftRegion.width + curveChart.width,
			rightRegion.y = leftRegion.y;
			rightRegion.height = leftRegion.height;

			topRegion.x = leftRegion.width,
			topRegion.y = 0,
			topRegion.width = curveChart.width;

			centerRegion.x = topRegion.x,
			centerRegion.y = topRegion.height + curveChart.height + 6,
			centerRegion.width = topRegion.width;

			$.extend(true, this.options.curveChart, {
				region: curveChart,
				scalerLeft: { region: leftRegion },
				scalerRight: { region: rightRegion },
				scalerTop: { region: topRegion },
				scalerBottom: { region: centerRegion }
			});

			leftRegion.y = volumeChart.y,
			leftRegion.height = volumeChart.height;

			$.extend(true, this.options.volumeChart, {
				region: volumeChart,
				scalerLeft: { region: leftRegion }
			});
		},
		//计算左右轴侧宽度
		calRegionsWidth: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				curveChart = o.curveChart,
				region = curveChart.region;

		    // 利用对象方法保持入口统一
			var linePainter = new LinePainter( ctx, $.extend({}, o.curveChart, {maxDots: o.maxDots}) );
            linePainter.data = data;
            linePainter.setMaxDiff();
            

			var preClose = data.stockData.zs, //今天开盘价
            	min = preClose - linePainter.maxDiff,
            	space = linePainter.maxDiff * 2 / (curveChart.verticalLineCount + 1),
            	val,
            	percent;

           
            var leftRegion = 0, rightRegion = 0, i;

            ctx.font = '20px Helvetica';
            //计算最大值和最大本分比即可
            i = curveChart.verticalLineCount + 1;

            val = min + i * space;

            leftRegion = ctx.measureText(val.toFixed(2)+'   ').width;

            percent = (val - preClose) * 100 / preClose;

            rightRegion = ctx.measureText(percent.toFixed(2) + '%'+ '   ').width;

            // 量图左侧宽度
            var max = 0, items = data.items, itemLength = items.length,
            	unit = '';
            for(i=0; i<itemLength; i++){
            	max = Math.max(items[i] ? items[i].volume : 0, max);
            }
            if (max / 100000000 > 1) {
                max = max / 100000000;
                unit = '亿';
            } else {
                max = max / 10000;
                unit = '万';
            }
            
            leftRegion = Math.max(leftRegion, ctx.measureText(max.toFixed(2)+unit+'   ').width);
            this.reSetRegionWidth({ leftRegion: leftRegion, rightRegion: rightRegion });
            if (this.options.onRegion && typeof this.options.onRegion == 'function') {
                this.options.onRegion();
            }
            linePainter = null;
		},
		// 按接收到的数据内包含的时间重新排序数据
		reSortData: function(acceptData){
			if(!this.data){
				this.data = {};
			}

			if(!this.data.stockData){

				this.data.stockData = {};
			}
			if(!this.data.items){

				this.data.items = [];
			}
			var items = this.data.items, 
				maxDots = this.options.maxDots || 241, 
				list = acceptData.items,
				index, date,
				listLength;

			$.extend(true, this.data.stockData, acceptData.stockData);

			if( $.isArray(items) && $.isArray(list) ) {

				listLength = list.length;

				for(var i = 0; i<listLength; i++){

					date = new Date(Number(list[i].time));
					// 九点半之前为第一条数据
					if(date.getHours()<10 && date.getMinutes()<=30){
						
						items[0] = list[i];
					}else if(date.getHours()<11 || date.getHours()<12 && date.getMinutes()<=30){
						// 上午数据
						index = (date.getHours()-9)*60 + date.getMinutes() - 30;
						items[index] = list[i];

					}else if(date.getHours()< 15){
						// 下午数据,第一条应该是13:01
						index = (date.getHours()-13)*60 + date.getMinutes() + 120;
						items[index] = list[i];

					}else if(date.getHours()>=15 && date.getMinutes()>=0){
						// 收盘后数据为最后一条
						items[maxDots-1] = list[i];
					}
					
				}
			}
		},
		render: function(data){
		    this.ctx.clearRect(0, 0, this.el.width, this.el.height);
		    this.ctx.canvas.width = this.ctx.canvas.width;
			if ( data ){
				// this.data = data; //后续添加进来的数据直接处理，此处不再处理。以免多次排序已经处理过的数据
				this.reSortData(data);
			} 
			// 票头
			this.paintTicketHead();

			if ( !this.ctx || !this.data || !this.data.items ) return;

			// $.log("render...");
			
			//主图
			this.paintCurveChart(),
			//量图
			this.paintVolumeChart();
			if (this.options.onFilish && typeof this.options.onFilish == 'function') {
			    this.options.onFilish();
			}
		},
		refresh: function(){
			if ( !this.ctx ) return;

			// $.log("refresh...")
			this.ctx.clearRect(0, 0, this.el.width, this.el.height);
			this.ctx.canvas.width = this.ctx.canvas.width;
			this.render();
		},
		initEvents: function(){
			/*var self = this, ctx = this.ctx, o = this.options, scalerTop = o.curveChart.scalerTop;

			$(this.el).bind("touchstart", function(e){

				var canvasOffset = self.getCanvasOffset();

				//$.log( e.pageX - canvasOffset.left, e.pageY - canvasOffset.top );

				if (e.touches && e.touches.length) {
			        e = e.touches[0];
			    } else if (e.changedTouches && e.changedTouches.length) {
			        e = e.changedTouches[0];
			    }

				ctx.save(),
				ctx.translate(scalerTop.region.x, scalerTop.region.y),
				ctx.clearRect(0, 0, scalerTop.region.width, scalerTop.region.height - 2),
				ctx.font = scalerTop.font,
				ctx.fillText("touchstart[x:" + (e.pageX - canvasOffset.left) + ", y:" + (e.pageY - canvasOffset.top) + "]", 0, scalerTop.region.height - 5),
				ctx.restore()

			})*/

		},
		paintTicketHead: function(){
			var data = this.data.stockData;

			$('.selectedDetail .jk').html(formatColor(formatData(data.jk), data.zde));
			$('.selectedDetail .zs').html(formatData(data.zs));
			$('.selectedDetail .mc').html(data.mc);
			$('.selectedDetail .dm').html(data.dm);
			$('.selectedDetail .zx').html(formatColor(formatData(data.zx), data.zde));
			$('.selectedDetail .zde').html(formatColor(data.zde > 0?'+'+data.zde:(data.zde<0?data.zde:'--'), data.zde));
			$('.selectedDetail .zdf').html(formatColor(data.zdf > 0 ? '+' + data.zdf : (data.zdf < 0 ? data.zdf : '--'), data.zde, '%'));
			$('.selectedDetail .zd').html(formatColor(formatData(data.zd), data.zde));
			$('.selectedDetail .zg').html(formatColor(formatData(data.zg), data.zde));

			$('.selectedDetail .ze').html(formatData(data.ze));
			$('.selectedDetail .zl').html(formatData(data.zl));
			$('.selectedDetail .hs').html(formatData(data.hs == 0? data.hs:''+data.hs+'%'));
			$('.selectedDetail .sz').html(formatData(data.sz));
			$('.selectedDetail .gb').html(formatData(data.gb));
			$('.selectedDetail .sy').html(formatData(data.sy));

			$('.selectedDetail .lb').html(formatData(data.lb));
			$('.selectedDetail .jj').html(formatData(data.jj));
			$('.selectedDetail .wb').html(formatData((data.wb.length == 0 || isNaN(data.wb)) ? data.wb : '' + data.wb + '%'));

			function formatData(str){
				if(!str || str == 0){
					return '--';
				}
				return str;
			}

			function formatColor(str, flag, unit) {
			    if (!str || isNaN(str) || !flag || isNaN(flag))
			        return str;
			    if (flag - 0 > 0) {
			        return '<font class="rise-color">' + str + (unit ? unit : '') + '</font>';
			    }
			    else if (flag - 0 < 0) {
			        return '<font class="fall-color">' + str + (unit ? unit : '') + '</font>';
			    }
			    else {
			        return '<font class="normal-color">' + str + (unit ? unit : '') + '</font>';
			    }
			}
		},
		paintCurveChart: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				curveChart = o.curveChart,
				region = curveChart.region;

            // 计算左右轴侧宽度
          	this.calRegionsWidth();

			//画主图区域外框
            this._paintRect(region.x, region.y, region.width, region.height, curveChart.borderColor);
                       
            var middleIndex = ( curveChart.horizontalLineCount + curveChart.horizontalLineCount % 2 ) / 2,
            	splitCount = curveChart.horizontalLineCount + 1,
            	lineColor,
            	x,
            	y;

            //画水平线
            for (var i = 1; i <= curveChart.horizontalLineCount; i++) {
                lineColor = (i == middleIndex ? curveChart.middleLineColor : curveChart.splitLineColor),
                y = region.y + Math.round( region.height * i / splitCount ),
                this._paintLine(region.x, y, region.x + region.width, y, lineColor)
            }

            //画垂直线
            splitCount = curveChart.verticalLineCount + 1;
            for (var i = 1; i <= curveChart.verticalLineCount; i++) {
                x = region.x + Math.round( region.width * i / splitCount ),
                this._paintLine(x, region.y, x, region.y + region.height, curveChart.splitLineColor)
            }
            //初始化平均价格线
            var averageLinePainter = new AverageLinePainter( ctx, $.extend({}, o.curveChart, {maxDots: o.maxDots, priceLineColor:'#DA298C'}) );

            //初始化最新价格线
            var mountainLinePainter = new MountainLinePainter(ctx, $.extend({}, o.curveChart, { maxDots: o.maxDots }));
           
            // 画平均线和最新价格线
			// TODO: 基本信息中添加是否画均线图标识。------------
            averageLinePainter.paint(data);
            mountainLinePainter.paint(data);

            var preClose = data.stockData.zs, //今天开盘价
            	scalersLeft = [],
            	scalersRight = [],
            	min = preClose - mountainLinePainter.maxDiff,
            	space = mountainLinePainter.maxDiff * 2 / (curveChart.verticalLineCount + 1),
            	val,
            	percent;

          	
            // var leftRegion = 0, rightRegion = 0;
            // ctx.font = '20px Helvetica';
            for (var i = curveChart.verticalLineCount + 1; i >= 0; i--) { //@1
                
                val = min + i * space;
                scalersLeft.push(val.toFixed(2));
                // leftRegion = Math.max(leftRegion, ctx.measureText(val.toFixed(2)+'  ').width);

                percent = (val - preClose) * 100 / preClose;
                scalersRight.push(percent.toFixed(2) + '%');
                // rightRegion = Math.max(rightRegion, ctx.measureText(percent.toFixed(2) + '%'+ ' ').width);

            }
            // this.reSetRegionWidth({leftRegion:leftRegion, rightRegion:rightRegion});
            
           
            
            var textColor = {riseColor: o.riseColor, fallColor: o.fallColor, normalColor: o.normalColor}, 
            	axisPainter;


            //Y轴文字
            axisPainter = new yAxis(ctx, $.extend( {}, curveChart.scalerLeft, textColor ));
            axisPainter.paint(scalersLeft);
            axisPainter = new yAxis(ctx, $.extend( {}, curveChart.scalerRight, textColor ));
            axisPainter.paint(scalersRight);

            //X轴文字
            axisPainter = new xAxis(ctx,  $.extend( {}, curveChart.scalerBottom, textColor ));
            axisPainter.paint(curveChart.scalerBottom.data);
		},
		_paintRect: function(x, y, width, height, borderColor){
			var ctx = this.ctx;
			ctx.beginPath(),
			//ctx.globalAlpha = 1,
            ctx.strokeStyle = borderColor,
            ctx.rect(x, y, width, height), 
            ctx.stroke();
		},
		_paintLine: function(x0, y0, x1, y1, lineColor){
			var ctx = this.ctx;
			ctx.beginPath(),
			ctx.globalAlpha = 0.5,			
			lineColor && ( ctx.strokeStyle = lineColor ),
			ctx.moveTo(x0, y0),
			ctx.lineTo(x1, y1),
			ctx.stroke(),
			ctx.globalAlpha = 1 //透明度重设回1，避免画其它东西时都得再次设置
		},
		paintVolumeChart: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				volumeChart = o.volumeChart,
				region = volumeChart.region;

			//区域外框
            this._paintRect(region.x, region.y, region.width, region.height, volumeChart.borderColor);

            var middleIndex = ( volumeChart.horizontalLineCount + volumeChart.horizontalLineCount % 2 ) / 2,
            	splitCount = volumeChart.horizontalLineCount + 1,
            	y;

            //水平线
            for (var i = 1; i <= volumeChart.horizontalLineCount; i++) {
                y = Math.round( region.y + region.height * i / splitCount ) + 0.5,
                this._paintLine(region.x, y, region.x + region.width, y, volumeChart.splitLineColor)
            }

            //柱图
            var volumePainter = new VolumePainter( ctx, $.extend({}, volumeChart, 
            	{maxDots: o.maxDots, riseColor: o.riseColor, fallColor: o.fallColor, equalColor: o.equalColor}) );
            volumePainter.paint(data);
			

            //Y轴
            var max = volumePainter.maxVolume, 
            	unit,
            	axisPainter;

            if (max / 100000000 > 1) {
                max = max / 100000000;
                unit = '亿';
            } else {
                max = max / 10000;
                unit = '万';
            }

            axisPainter = new yAxis(ctx, volumeChart.scalerLeft),
            axisPainter.paint( [max.toFixed(2), (max / 2).toFixed(2), '(' + unit + ')'] );
		}

	})

})(Zepto)


; (function ($) {
    var MAMAX = 20; //均线最大值
    var formatTime = function (row) {
        var da = row["da"], tm = row["tm"], result = [];
        if (!isNaN(da) && da.length >= 8) {
            var year = da.substring(0, 4), mth = da.substring(4, 6), day = da.substring(6, 8);
            result.push([year, mth, day].join('/'));
        }
        if (!isNaN(tm) && tm.length >= 3) {
            var m = tm.substring(tm.length - 2, tm.length), h = tm.substring(0, tm.length - 2);
            result.push([h, m].join(':'));
        }
        return result.join(' ');
    };
    var MACD = function (current_dataIndex, current_data, i) {
        var m_nCurves = 3, m_nParamValue = [12, 26, 9];
        current_data.MACD = [];
        for (var m_nIndex = 0; m_nIndex < m_nCurves; m_nIndex++) { current_data.MACD.push(this.Float_LIMIT_VALUE); }

        this.DI = (this.getHigh(i) + this.getLow(i) + 2 * this.getClose(i)) / 4;
        if (current_dataIndex < m_nParamValue[0] - 1) { this.AX += this.DI; }
        else if (current_dataIndex == m_nParamValue[0] - 1) { this.AX += this.DI; this.AX /= m_nParamValue[0]; }
        else { this.AX = ((((this.DI - this.AX) * 1538) / 10000) + this.AX); }
        if (current_dataIndex < m_nParamValue[1] - 1) { this.BX += this.DI; }
        else if (current_dataIndex == m_nParamValue[1] - 1) { this.BX += this.DI; this.BX /= m_nParamValue[1]; this.CX = this.AX - this.BX; current_data.MACD[0] = this.CX * this.lfMul; }
        else {
            this.BX = ((((this.DI - this.BX) * 741) / 10000) + this.BX);
            current_data.MACD[0] = ((this.AX - this.BX) * this.lfMul);
            if (current_dataIndex < m_nParamValue[1] - 1 + m_nParamValue[2] - 1) { this.CX += this.AX - this.BX; }
            else if (current_dataIndex == m_nParamValue[1] - 1 + m_nParamValue[2] - 1) {
                this.CX += this.AX - this.BX;
                this.CX /= m_nParamValue[2];
                current_data.MACD[1] = (this.CX * this.lfMul);
                current_data.MACD[2] = ((2 * (this.AX - this.BX - this.CX)) * this.lfMul);
            } else {
                this.CX = ((((this.AX - this.BX - this.CX) * 2) / 10) + this.CX);
                current_data.MACD[1] = (this.CX * this.lfMul);
                current_data.MACD[2] = ((2 * (this.AX - this.BX - this.CX)) * this.lfMul);
            }
        }
    };
    var KDJ = function (current_dataIndex, current_data, i) {
        var m_nCurves = 3, m_nParamValue = [9];
        current_data.KDJ = [];
        for (var m_nIndex = 0; m_nIndex < m_nCurves; m_nIndex++) { current_data.KDJ.push(this.Float_LIMIT_VALUE); }
        if (current_dataIndex < m_nParamValue[0] - 1) { }
        else if (current_dataIndex == m_nParamValue[0] - 1) {
            current_data.KDJ[0] = 5000;
            current_data.KDJ[1] = 5000;
            current_data.KDJ[2] = 3 * current_data.KDJ[1] - 2 * current_data.KDJ[0];
        } else {
            for (j = i + 1 - m_nParamValue[0]; j <= i; j++) {
                if (j == i + 1 - m_nParamValue[0]) { dMax = this.getHigh(j); dMin = this.getLow(j); }
                else { if (dMax < this.getHigh(j)) { dMax = this.getHigh(j); } if (dMin > this.getLow(j)) { dMin = this.getLow(j); } }
            }
            if ((dMax - dMin) != 0) { dRSV = (this.getClose(i) - dMin) * 10000 / (dMax - dMin); }
            else { dRSV = 0; }
            current_data.KDJ[0] = (((this.data.items[current_dataIndex - 1].KDJ[0] * 2 + dRSV) / 3) << 0);
            current_data.KDJ[1] = (((current_data.KDJ[0] + 2 * this.data.items[current_dataIndex - 1].KDJ[1]) / 3) << 0);
            current_data.KDJ[2] = ((3 * current_data.KDJ[0] - 2 * current_data.KDJ[1]) << 0);
        }
    };
    var RSI = function (current_dataIndex, current_data, i) {
        var m_nCurves = 2, m_nParamValue = [6, 12, 24];
        current_data.RSI = [];
        for (var m_nIndex = 0; m_nIndex < m_nCurves; m_nIndex++) { current_data.RSI.push(this.Float_LIMIT_VALUE); }
        if (i == 0) { return; }
        var Close = this.getClose(i), Open = this.getOpen(i), High = this.getHigh(i), Low = this.getLow(i);
        var prevClose = this.getClose(i - 1), prevOpen = this.getOpen(i - 1), prevHigh = this.getHigh(i - 1), prevLow = this.getLow(i - 1);

        if (i < m_nParamValue[0]) {
            if (Close > prevClose) { this.upsum1 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum1 += (prevClose - Close); }
        }
        else if (i == m_nParamValue[0]) {
            if (Close > prevClose) { this.upsum1 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum1 += (prevClose - Close); }
            AX1 = this.upsum1 * 10 / m_nParamValue[0];
            BX1 = this.downsum1 * 10 / m_nParamValue[0];
            if ((AX1 + BX1) != 0) { current_data.RSI[0] = ((((this.upsum1 * 1000 / (this.upsum1 + this.downsum1)) / 10) << 0) * this.lfMul); }
            else { current_data.RSI[0] = 0; }
        } else {
            if (Close > prevClose) { AX1 = AX1 * (m_nParamValue[0] - 1) + (Close - prevClose) * 10; BX1 = BX1 * (m_nParamValue[0] - 1); }
            else if (Close < prevClose) { AX1 = AX1 * (m_nParamValue[0] - 1); BX1 = BX1 * (m_nParamValue[0] - 1) + (prevClose - Close) * 10; }
            else { AX1 = AX1 * (m_nParamValue[0] - 1); BX1 = BX1 * (m_nParamValue[0] - 1); }
            AX1 = AX1 / m_nParamValue[0];
            BX1 = BX1 / m_nParamValue[0];
            if ((AX1 + BX1) != 0) { current_data.RSI[0] = ((((AX1 * 1000 / (AX1 + BX1)) / 10) << 0) * this.lfMul); }
            else { current_data.RSI[0] = 0; }
        }

        if (i < m_nParamValue[1]) {
            if (Close > prevClose) { this.upsum2 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum2 += (prevClose - Close); }
        } else if (i == m_nParamValue[1]) {
            if (Close > prevClose) { this.upsum2 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum2 += (prevClose - Close); }
            AX2 = this.upsum2 * 10 / m_nParamValue[1];
            BX2 = this.downsum2 * 10 / m_nParamValue[1];
            if ((AX2 + BX2) != 0) { current_data.RSI[1] = ((((this.upsum2 * 1000 / (this.upsum2 + this.downsum2)) << 0) / 10) * this.lfMul); }
            else { current_data.RSI[1] = 0; }
        } else {
            if (Close > prevClose) { AX2 = AX2 * (m_nParamValue[1] - 1) + (Close - prevClose) * 10; BX2 = BX2 * (m_nParamValue[1] - 1); }
            else if (Close < prevClose) { AX2 = AX2 * (m_nParamValue[1] - 1); BX2 = BX2 * (m_nParamValue[1] - 1) + (prevClose - Close) * 10; }
            else { AX2 = AX2 * (m_nParamValue[1] - 1); BX2 = BX2 * (m_nParamValue[1] - 1); }
            AX2 = AX2 / m_nParamValue[1];
            BX2 = BX2 / m_nParamValue[1];
            if ((AX2 + BX2) != 0) { current_data.RSI[1] = ((((AX2 * 1000 / (AX2 + BX2)) / 10) << 0) * this.lfMul); }
            else { current_data.RSI[1] = 0; }
        }
        
        if (i < m_nParamValue[2]) {
            if (Close > prevClose) { this.upsum3 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum3 += (prevClose - Close); }
        } else if (i == m_nParamValue[2]) {
            if (Close > prevClose) { this.upsum3 += (Close - prevClose); }
            else if (Close < prevClose) { this.downsum3 += (prevClose - Close); }
            AX3 = this.upsum3 * 10 / m_nParamValue[2];
            BX3 = this.downsum3 * 10 / m_nParamValue[2];
            if ((AX3 + BX3) != 0) { current_data.RSI[2] = ((((this.upsum3 * 1000 / (this.upsum3 + this.downsum3)) << 0) / 10) * this.lfMul); }
            else { current_data.RSI[2] = 0; }
        } else {
            if (Close > prevClose) { AX3 = AX3 * (m_nParamValue[2] - 1) + (Close - prevClose) * 10; BX3 = BX3 * (m_nParamValue[2] - 1); }
            else if (Close < prevClose) { AX3 = AX3 * (m_nParamValue[2] - 1); BX3 = BX3 * (m_nParamValue[2] - 1) + (prevClose - Close) * 10; }
            else { AX3 = AX3 * (m_nParamValue[2] - 1); BX3 = BX3 * (m_nParamValue[2] - 1); }
            AX3 = AX3 / m_nParamValue[2];
            BX3 = BX3 / m_nParamValue[2];
            if ((AX3 + BX3) != 0) { current_data.RSI[2] = ((((AX3 * 1000 / (AX3 + BX3)) / 10) << 0) * this.lfMul); }
            else { current_data.RSI[2] = 0; }
        }
    };
    var DMA = function (current_dataIndex, current_data, i) {
        var m_nCurves = 2, m_nParamValue = [10, 50];
        current_data.DMA = [];
        for (var m_nIndex = 0; m_nIndex < m_nCurves; m_nIndex++) { current_data.DMA.push(this.Float_LIMIT_VALUE); }
        var dCloseTotalN = 0, dCloseTotalN1 = 0;

        if (current_dataIndex < m_nParamValue[1] - 1) { return; }
        for (var j = i - m_nParamValue[0] + 1; j <= i; j++) { dCloseTotalN += this.getClose(j); }
        for (j = i - m_nParamValue[1] + 1; j <= i; j++) { dCloseTotalN1 += this.getClose(j); }
        current_data.DMA[0] = (((dCloseTotalN / m_nParamValue[0] - dCloseTotalN1 / m_nParamValue[1]) << 0) * this.lfMul);

        if (current_dataIndex < (m_nParamValue[0] + m_nParamValue[1] - 2)) { }
        else {
            dCloseTotalN = 0;
            for (j = 0; j < m_nParamValue[0]; j++) { dCloseTotalN += (this.data.items[current_dataIndex - j].DMA[0] / this.lfMul); }
            current_data.DMA[1] = (((dCloseTotalN / m_nParamValue[0]) * this.lfMul) << 0);
        }
    };
    var Float_LIMIT_VALUE = null;
    var findMaxMin = function (data, technicalName) {
        for (var i in data[technicalName]) {
            var val = (data[technicalName][i] == Float_LIMIT_VALUE ? 0 : data[technicalName][i]);
            this.maxAbsTechnicalDiff = Math.max(Math.abs(val), this.maxAbsTechnicalDiff);
			this.maxTechnicalDiff = Math.max(val, this.maxTechnicalDiff);
			this.minTechnicalDiff = Math.min(val, this.minTechnicalDiff);
        }
    };

	//K线图
	KGraphic = MinsGraphic.extend({
		options: {
			//maxWidth: 10, //最大宽度
			//minWidth: 10, //最小宽度
			splitSpace: 2, //两个柱形图之间的间隔
			//width: 1, //用于存储计算后的值
			KLineSingleWidth: 10, //一根K线的宽度
			//maxDots: 100,
			riseColor: "#d50101",
			fallColor: "#027e22",
			equalColor: '#e7a91c',
			normalColor: "white",
			technicalAnalysis: 'VOL', //VOL、MACD、KDJ、RSI、DMA
			period: 'day', //day、month、year、5min、15min、30min、60min
			curveChart: {
				horizontalLineCount: 3, //水平线数量
				verticalLineCount: 3, //垂直线数量
				middleLineColor: '#2e3741', //中间线颜色
				splitLineColor: '#2e3741', //分隔线颜色
				priceLineColor: 'blue', //价格线颜色
				borderColor: '#2e3741',
				region: {},
				scalerLeft: { font: '20px Helvetica', align: 'right', color: '#ffffff', fontHeight: 16, textBaseline: 'top', region: {} },
				scalerRight: { font: '20px Helvetica', align: 'right', fontHeight: 16, textBaseline: 'top', region: {} },
				scalerTop: { font: '20px Helvetica', color: 'white', textBaseline: 'top', region: {} },
				scalerBottom: { font: '20px Helvetica', color: 'white', textBaseline: 'top', region: {}, data: ['', ''] }
			},
			volumeChart: {
				horizontalLineCount: 1, //水平线数量
				splitLineColor: "#2e3741", //分隔线颜色
				borderColor: '#2e3741',
				priceLineColor: 'white', //价格线颜色
				region: {},
				scalerLeft: { font: '20px Helvetica', align: 'right', color: '#ffffff', fontHeight: 16, textBaseline: 'top', region: {} }
			},
			technicalChart: {
			    splitLineColor: "#2e3741",
			    borderColor: '#2e3741',
			    curveLineColor1: 'white',
			    curveLineColor2: 'yellow',
				curveLineColor3: 'purple',
				topAndBottomSpace: 10,
			    region: {},
			    scalerLeft: { font: '20px Helvetica', align: 'right', color: '#ffffff', fontHeight: 16, textBaseline: 'middle', region: {} }
			}
		},
		initialize: function (options) {
		    if (!this.el || !this.ctx) return;

		    //计算画布及各个图块的区域位置、大于等
		    //由于画布上的坐标并未对应上页面上的坐标，1px的线条可能存在像素差，加上0.5可以修正画布上的线条像素差问题。
		    var el = this.el,
				minWidth = 600,
				minHeight = 400,
				canvas = { x: 0, y: 0, width: el.width/*Math.max(el.width, minWidth)*/, height: el.height/*Math.max(el.height, minHeight)*/ }, //画布

				leftRegion = { width: 100 },
				rightRegion = { width: options && options.rightRegion || 90 },
				topRegion = { height: (options.hintChart ? 26 : 4) },
				centerRegion = { height: 40 },
				bottomRegion = { height: 8 }, //图形离画布底部的间隔

				curveChart = { //曲线图
				    x: leftRegion.width - 0.5,
				    y: topRegion.height - 0.5,
				    width: canvas.width - leftRegion.width - rightRegion.width,
				    //曲线图高度 / 柱图高度 = 2 / 1
				    height: Math.round((canvas.height - topRegion.height - centerRegion.height - bottomRegion.height) / 3 * 2)
				},
				volumeChart = { //柱图
				    x: curveChart.x,
				    y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
				    width: curveChart.width,
				    height: Math.round(curveChart.height / 2)
				},
				technicalChart = { //技术图
				    x: curveChart.x,
				    y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
				    width: curveChart.width,
				    height: Math.round(curveChart.height / 2)
				};
		    if (options.technical)
		        this.options.technicalAnalysis = options.technical;

		    leftRegion.x = 0,
			leftRegion.y = topRegion.height,
			leftRegion.height = curveChart.height;

		    rightRegion.x = leftRegion.width + curveChart.width,
			rightRegion.y = leftRegion.y;
		    rightRegion.height = leftRegion.height;

		    topRegion.x = leftRegion.width,
			topRegion.y = 0,
			topRegion.width = curveChart.width;

		    centerRegion.x = topRegion.x,
			centerRegion.y = topRegion.height + curveChart.height + 6,
			centerRegion.width = topRegion.width;

		    $.extend(true, this.options.curveChart, {
		        region: curveChart,
		        scalerLeft: { region: leftRegion },
		        scalerRight: { region: rightRegion },
		        scalerTop: { region: topRegion },
		        scalerBottom: { region: centerRegion }
		    });

		    leftRegion.y = volumeChart.y,
			leftRegion.height = volumeChart.height;

		    $.extend(true, this.options.volumeChart, {
		        region: volumeChart,
		        scalerLeft: { region: leftRegion }
		    });

		    $.extend(true, this.options.technicalChart, {
		        region: technicalChart,
		        scalerLeft: { region: leftRegion }
		    });
		    //绑定相关事件
		    this.initEvents();
		},
		popData: function (day) {
		    var o = this.options, items = this.data.items;
		    var tDay, nDay = day && new Date(Number(day)) || null;
		    if (nDay && items) {
				 while(items.length > 0){
					 tDay = new Date(Number(items[items.length - 1].time));
					 if(tDay.getFullYear() == nDay.getFullYear() && tDay.getMonth() == nDay.getMonth() && tDay.getDate() == nDay.getDate()){
						items.pop();
					 }
					 else{
						break;
					 }
				 }
		    }
		},
		pushData: function(object){

			var items = this.data.items, 
				itemsLength = items&&items.length, 
				list = object.items,
				listLength;

			$.extend(true, this.data.stockData, object.stockData);

			if( $.isArray(items) && $.isArray(list) ) {

				listLength = list.length;

				if(listLength > 0 && itemsLength>=0){
					for(var i in list){
						items.push(list[i]);
					}
				}

				/*for (var i = 0; i < listLength; i++) {
					items.push( list[i] )
				}*/

				//删除超出maxDots的元素
				//if( itemsLength + listLength > maxDots){
				//	items.splice( 0,  itemsLength + listLength - maxDots)
					// $.log( items.length )
				//}

			}
		},
		Calcu: function () {
		    //if (this.isCalcu) { return; }
		    var self = this,
		    stratIndex = 0;
		    this.getClose = function (index) { return Number(self.data.items[index]["close"]); };
		    this.getOpen = function (index) { return Number(self.data.items[index]["open"]); };
		    this.getHigh = function (index) { return Number(self.data.items[index]["high"]); };
		    this.getLow = function (index) { return Number(self.data.items[index]["low"]); };
		    this.Float_LIMIT_VALUE = null;
		    this.lfMul = 1000;
		    var paramName = ["upsum1", "downsum1", "upsum2", "downsum2", "upsum3", "downsum3", "AX", "BX", "CX", "DI", "AX1", "BX1", "AX2", "BX2", "AX3", "BX3", "dMax", "dMin", "dRSV", "j"];
		    for (var pn in paramName) { this[paramName[pn]] = 0; }
		    for (var i = stratIndex; i <= this.data.items.length; i++) {
		        if (!this.data.items[i]) { continue; }
		        var current_dataIndex = i - stratIndex; //current_dataIndex计算索引，一定要从0开始，i数据索引，可以大于0
		        MACD.call(this, current_dataIndex, this.data.items[i], i);
		        KDJ.call(this, current_dataIndex, this.data.items[i], i);
		        RSI.call(this, current_dataIndex, this.data.items[i], i);
		        DMA.call(this, current_dataIndex, this.data.items[i], i);
		    }
		    for (var pn in paramName) { this[paramName[pn]] = null; delete this[paramName[pn]]; }
		    this.isCalcu = true;
			//console.log(this.data.items);
		},
		render: function(data){
		    this.ctx.clearRect(0, 0, this.el.width, this.el.height);
		    this.ctx.canvas.width = this.ctx.canvas.width;
			var item, startDate, startDateStr, endDate, endDateStr, startIndex,
				length, o = this.options;
			
			// 日期坐标轴计算
			if ( data ) {
				this.data = data;
			}
			if (this.data && this.data.items) {
			    this.Calcu();
			}
		    // 计算左侧宽度
			this.calRegionsWidth();

			var candlePainter = new CandlePainter({}, $.extend({}, o.curveChart,
			            							{
			            							    splitSpace: o.splitSpace,
			            							    width: o.KLineSingleWidth
			            							})
            									);
			length = candlePainter.getMaxPaintItemsLength();
			candlePainter = null;
			startIndex = this.data.items.length - length;
			startIndex = startIndex < 0 ? 0 : startIndex;
			item = this.data.items[startIndex];

			startDate = new Date(Number(item.time));
			item = this.data.items[this.data.items.length - 1];
			endDate = new Date(Number(item.time));

			if (o.period == '5min' || o.period == '15min' || o.period == '30min' || o.period == '60min') {
			    startDateStr = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate() + ' ' + startDate.getHours() + ':' + startDate.getMinutes();
			    endDateStr = endDate.getFullYear() + '/' + (endDate.getMonth() + 1) + '/' + endDate.getDate() + ' ' + endDate.getHours() + ':' + endDate.getMinutes();
			}
			else {
			    startDateStr = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
			    endDateStr = endDate.getFullYear() + '/' + (endDate.getMonth() + 1) + '/' + endDate.getDate();
			}
			

			this.options.curveChart.scalerBottom.data = [startDateStr, endDateStr];


			// 票头
			this.paintTicketHead();

			if ( !this.ctx || !this.data || !this.data.items ) return;
			
			//主图
			this.paintCurveChart(),
			//技术指标
			this.paintTechnicalChart();
			if (this.options.onFilish && typeof this.options.onFilish == 'function') {
			    this.options.onFilish();
			}
		},
		paintHintChart: function(){
			var self = this,
				ctx = this.ctx, 
				o = this.options,
				items = this.data && this.data.items || [0],
				curveChart = o.curveChart,
				region = curveChart.region;
			
			ctx.font="20px Helvetica";
			ctx.textBaseline = "middle";
			ctx.fillStyle = 'white';
			ctx.fillText('MA5:' + (items[items.length - 1].MA5.toFixed(2) || 0), region.x, region.y - 10);
			ctx.fillStyle = '#008cff'; 
			ctx.fillText('MA10:' + (items[items.length - 1].MA10.toFixed(2) || 0), region.x + region.width/2 - ctx.measureText('MA10:' + (items[items.length - 1].MA10.toFixed(2) || 0)).width/2, region.y - 10);
			ctx.fillStyle = '#c047de'; 
			ctx.fillText('MA20:' + (items[items.length - 1].MA20.toFixed(2) || 0), region.x + region.width - ctx.measureText('MA20:' + (items[items.length - 1].MA20.toFixed(2) || 0)).width, region.y - 10);		
		},
	    // 重新设置图形两边坐标轴的宽度
		reSetRegionWidth: function (options) {
		    if (!this.el || !this.ctx) return;

		    //计算画布及各个图块的区域位置、大于等
		    //由于画布上的坐标并未对应上页面上的坐标，1px的线条可能存在像素差，加上0.5可以修正画布上的线条像素差问题。
		    var el = this.el,
				minWidth = 600,
				minHeight = 400,
				canvas = { x: 0, y: 0, width: el.width/*Math.max(el.width, minWidth)*/, height: el.height/*Math.max(el.height, minHeight)*/ }, //画布

				leftRegion = { width: options.leftRegion || 100 },
				rightRegion = { width: options.rightRegion || 90 },
				topRegion = { height: options.topRegion || 4 },
				centerRegion = { height: 40 },
				bottomRegion = { height: 8 }, //图形离画布底部的间隔

				curveChart = { //曲线图
				    x: leftRegion.width - 0.5,
				    y: topRegion.height - 0.5,
				    width: canvas.width - leftRegion.width - rightRegion.width,
				    //曲线图高度 / 柱图高度 = 2 / 1
				    height: Math.round((canvas.height - topRegion.height - centerRegion.height - bottomRegion.height) / 3 * 2)
				},
				volumeChart = { //柱图
				    x: curveChart.x,
				    y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
				    width: curveChart.width,
				    height: Math.round(curveChart.height / 2)
				},
				technicalChart = { //技术图
				    x: curveChart.x,
				    y: topRegion.height + curveChart.height + centerRegion.height - 0.5,
				    width: curveChart.width,
				    height: Math.round(curveChart.height / 2)
				};

		    leftRegion.x = 0,
			leftRegion.y = topRegion.height,
			leftRegion.height = curveChart.height;

		    rightRegion.x = leftRegion.width + curveChart.width,
			rightRegion.y = leftRegion.y;
		    rightRegion.height = leftRegion.height;

		    topRegion.x = leftRegion.width,
			topRegion.y = 0,
			topRegion.width = curveChart.width;

		    centerRegion.x = topRegion.x,
			centerRegion.y = topRegion.height + curveChart.height + 6,
			centerRegion.width = topRegion.width;

		    $.extend(true, this.options.curveChart, {
		        region: curveChart,
		        scalerLeft: { region: leftRegion },
		        scalerRight: { region: rightRegion },
		        scalerTop: { region: topRegion },
		        scalerBottom: { region: centerRegion }
		    });

		    leftRegion.y = volumeChart.y,
			leftRegion.height = volumeChart.height;

		    $.extend(true, this.options.volumeChart, {
		        region: volumeChart,
		        scalerLeft: { region: leftRegion }
		    });
		    $.extend(true, this.options.technicalChart, {
		        region: technicalChart,
		        scalerLeft: { region: leftRegion }
		    });
		},
		// 计算图像左侧和右侧宽度。
		calRegionsWidth: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				curveChart = o.curveChart,
            	min = 0,
            	space =0, 
            	val,
            	percent,
            	maxLength = 0,
            	startIndex = 0;

            //只为计算最大最小值
            var candlePainter = new CandlePainter( 
            	ctx, 
            	$.extend({},
				{
				    region: { width: self.el.width, height: self.el.height },   //传入canvas的宽度和高度
				    splitSpace: o.splitSpace,
					width: o.KLineSingleWidth
				}) 
			);
            maxLength = candlePainter.getMaxPaintItemsLength();
            candlePainter.data = data;
            // 设置最小值和最大值
            candlePainter.setDiff();

            min = candlePainter.minDiff;
            space = (candlePainter.maxDiff - candlePainter.minDiff) / (curveChart.verticalLineCount + 1); //应该是取垂直方向上的点，而不是horizontalLineCount 


            var leftRegion = 0, topRegion = 26;
            // 计算蜡烛图区域左侧需要的宽度
            ctx.font = '20px Helvetica';
            // 只需要计算最大值的宽度
            val = min + (curveChart.verticalLineCount + 1) * space; 
            leftRegion = ctx.measureText(val.toFixed(2)+'   ').width;
            
            // 计算量图左侧需要的宽度

            var items = data.items, maxVolume = 0, unit = '',
                level = (o.technicalAnalysis == "KDJ" ? 100 : 1000);
            
			this.maxTechnicalDiff = 0;
			this.maxAbsTechnicalDiff = 0;
			this.minTechnicalDiff = 0;
			if (o.technicalAnalysis == 'VOL') {
			    startIndex = items.length - maxLength - MAMAX;
			    startIndex = startIndex < 0 ? 0 : startIndex;
			    for (var i = startIndex; i < items.length; i++) {
                    maxVolume = Math.max(maxVolume, Number(items[i].volume) || 0);
                }

                if (maxVolume / 100000000 > 1) {
                    maxVolume = maxVolume / 100000000;
                    unit = '亿';
                } else {
                    maxVolume = maxVolume / 10000;
                    unit = '万';
                }
                leftRegion = Math.max(leftRegion, ctx.measureText(maxVolume.toFixed(2) + unit).width);
            }
			else if (o.technicalAnalysis == 'MACD' ||
					 o.technicalAnalysis == 'DMA') {
			    startIndex = items.length - maxLength;
			    startIndex = startIndex < 0 ? 0 : startIndex;
			    for (var i = startIndex; i < items.length; i++) {
			        findMaxMin.call(self, items[i], o.technicalAnalysis);
			    }
                leftRegion = Math.max(leftRegion, (this.maxAbsTechnicalDiff && ctx.measureText('-' + parseInt(this.maxAbsTechnicalDiff / level)).width || 0));
            }
			else if (o.technicalAnalysis == 'KDJ' || 
					 o.technicalAnalysis == 'RSI') {
			    startIndex = items.length - maxLength;
			    startIndex = startIndex < 0 ? 0 : startIndex;
			    for (var i = startIndex; i < items.length; i++) {
			        findMaxMin.call(self, items[i], o.technicalAnalysis);
			    }
                leftRegion = Math.max(leftRegion, (this.maxAbsTechnicalDiff && ctx.measureText(parseInt(this.maxAbsTechnicalDiff / level)).width || 0));
            }

            this.reSetRegionWidth({leftRegion:leftRegion, topRegion:topRegion, rightRegion:14});
			if(this.options.onRegion && typeof this.options.onRegion == 'function'){
				this.options.onRegion();
			}
            candlePainter = null;
            delete this.maxTechnicalDiff;
            delete this.maxAbsTechnicalDiff;
            delete this.minTechnicalDiff;
		},
		paintCurveChart: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				curveChart = o.curveChart,
				region = curveChart.region;

			//画主图区域外框
            this._paintRect(region.x, region.y, region.width, region.height, curveChart.borderColor);
                       
            var middleIndex = ( curveChart.horizontalLineCount + curveChart.horizontalLineCount % 2 ) / 2,
            	splitCount = curveChart.horizontalLineCount + 1,
            	lineColor,
            	x,
            	y;

            //画水平线
            for (var i = 1; i <= curveChart.horizontalLineCount; i++) {
                lineColor = (i == middleIndex ? curveChart.middleLineColor : curveChart.splitLineColor),
                y = region.y + Math.round( region.height * i / splitCount ),
                this._paintLine(region.x, y, region.x + region.width, y, lineColor)
            }

            //画垂直线
            splitCount = curveChart.verticalLineCount + 1;
            for (var i = 1; i <= curveChart.verticalLineCount; i++) {
                x = region.x + Math.round( region.width * i / splitCount ),
                this._paintLine(x, region.y, x, region.y + region.height, curveChart.splitLineColor)
            }

            // 初始化蜡烛图
            var candlePainter = new CandlePainter( ctx, $.extend({}, o.curveChart, 
			            							{ 	splitSpace: o.splitSpace,
			            								riseColor: o.riseColor, 
			            								fallColor: o.fallColor,
			            								equalColor: o.equalColor,
			            								width: o.KLineSingleWidth
			            							}) 
            									);
            //画蜡烛图
			candlePainter.paint(data);

			var scalersLeft = [],
            	scalersRight = [],
            	min = candlePainter.minDiff,
            	space = (candlePainter.maxDiff - candlePainter.minDiff) / (curveChart.verticalLineCount + 1), //应该是取垂直方向上的点，而不是horizontalLineCount @1
            	val,
            	percent;
            
            for (var i = curveChart.verticalLineCount + 1; i >= 0; i--) {// 
            	
        		val = min + i * space; //y轴从最小值开始
                scalersLeft.push(val.toFixed(2));
                
            }
            

            var textColor = {riseColor: o.riseColor, fallColor: o.fallColor, normalColor: o.normalColor}, 
            	axisPainter;

            //Y轴文字
            axisPainter = new yAxis(ctx, $.extend( {}, curveChart.scalerLeft, textColor));//去掉升降颜色textColor
            axisPainter.paint(scalersLeft);
            

            //X轴文字
            axisPainter = new xAxis(ctx, curveChart.scalerBottom);
            axisPainter.paint(curveChart.scalerBottom.data);
            
            
			
            //画5日价格均线
            var k5LinePainter = new KLinePainter( ctx, $.extend({}, o.curveChart, 
            		{
            			maxDiff: candlePainter.maxDiff,
            			minDiff: candlePainter.minDiff,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: 'white',
            			splitDay: 5,
            			priceLine: true
            		}) 
            );
            k5LinePainter.paint(data);

            //画10日价格均线
            var k10LinePainter = new KLinePainter( ctx, $.extend({}, o.curveChart, 
            		{
            			maxDiff: candlePainter.maxDiff,
            			minDiff: candlePainter.minDiff,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: '#008cff',
            			splitDay: 10,
            			priceLine: true
            		}) 
            );
            k10LinePainter.paint(data);

            //画20日价格均线
            var k20LinePainter = new KLinePainter( ctx, $.extend({}, o.curveChart, 
            		{
            			maxDiff: candlePainter.maxDiff,
            			minDiff: candlePainter.minDiff,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: '#c047de',
            			splitDay: 20,
            			priceLine: true
            		}) 
            );
            k20LinePainter.paint(data);
			this.paintHintChart();
		},
		paintTechnicalChart: function(){
			var self = this,
				o = this.options;
			switch(o.technicalAnalysis){
				case 'VOL':
				default:
					this.paintVolumeLine();
					break;
				case 'MACD':
					this.paintMACDLine();
					break;
				case 'KDJ':
				case 'RSI':
				case 'DMA':
					this.paintTechLine();
					break;
			}
		},
		paintVolumeLine: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data, 
				o = this.options,
				volumeChart = o.volumeChart,
				region = volumeChart.region;

			//区域外框
            this._paintRect(region.x, region.y, region.width, region.height, volumeChart.borderColor);

            var middleIndex = ( volumeChart.horizontalLineCount + volumeChart.horizontalLineCount % 2 ) / 2,
            	splitCount = volumeChart.horizontalLineCount + 1,
            	y;

            //水平线
            for (var i = 1; i <= volumeChart.horizontalLineCount; i++) {
                y = Math.round( region.y + region.height * i / splitCount ) + 0.5,
                this._paintLine(region.x, y, region.x + region.width, y, volumeChart.splitLineColor)
            }

            //成交量柱图
            var dealVolumePainter = new DealVolumePainter( ctx, $.extend({}, volumeChart, {
            															riseColor: o.riseColor, 
            															fallColor: o.fallColor,
            															equalColor: o.equalColor,
            															splitSpace: o.splitSpace,
            															width: o.KLineSingleWidth
            														}) 
            											);
            dealVolumePainter.paint(data);
			

			//画5日成交量均线
            var k5LinePainter = new KLinePainter( ctx, $.extend({}, volumeChart, 
            		{
            			maxVolume: dealVolumePainter.maxVolume,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: 'white',
            			splitDay: 5
            		}) 
            );
            k5LinePainter.paint(data);

            //画10日成交量均线
            var k10LinePainter = new KLinePainter( ctx, $.extend({}, volumeChart, 
            		{
            			maxVolume: dealVolumePainter.maxVolume,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: 'yellow',
            			splitDay: 10
            		}) 
            );
            k10LinePainter.paint(data);

            /*
            //画20日成交量均线
            var k20LinePainter = new KLinePainter( ctx, $.extend({}, volumeChart, 
            		{
            			maxVolume: dealVolumePainter.maxVolume,
            			splitSpace: o.splitSpace,
            			width: o.KLineSingleWidth,
            			volLineColor: '#4BDD80',
            			splitDay: 20
            		}) 
            );
            k20LinePainter.paint(data);
            */

            //Y轴
            var max = dealVolumePainter.maxVolume, 
            	unit,
            	axisPainter;

            if (max / 100000000 > 1) {
                max = max / 100000000;
                unit = '亿';
            } else {
                max = max / 10000;
                unit = '万';
            }

            axisPainter = new yAxis(ctx, volumeChart.scalerLeft),
            axisPainter.paint( [max.toFixed(2), (max / 2).toFixed(2), '(' + unit + ')'] );
		},
		paintMACDLine: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data,
                items = this.data.items,
				o = this.options,
				curveChart = o.curveChart,
				technicalChart = o.technicalChart,
				region = technicalChart.region,
				axisPainter,
				maxLength,
				startIndex;

			//区域外框
			this._paintRect(region.x, region.y, region.width, region.height, technicalChart.borderColor);
			
			this.maxTechnicalDiff = 0;
			this.maxAbsTechnicalDiff = 0;
			this.minTechnicalDiff = 0;
			var oPainter = new Painter({}, $.extend({}, technicalChart, {
			    splitSpace: o.splitSpace,
			    width: o.KLineSingleWidth
			})
            );
			maxLength = oPainter.getMaxPaintItemsLength();
			startIndex = items.length - maxLength;
			startIndex = startIndex < 0 ? 0 : startIndex;
			for (var i = startIndex; i < items.length; i++) {
			    findMaxMin.call(self, items[i], o.technicalAnalysis);
			}

		    //文字部分
			this.NearByNum = this._getNearbyNum('MACD', startIndex);
		    axisPainter = new yAxisTechnical(ctx, $.extend({}, technicalChart.scalerLeft, {
		        technicalAnalysis: o.technicalAnalysis,
		        maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
		        topAndBottomSpace: technicalChart.topAndBottomSpace
		    }));
            axisPainter.paint(this.NearByNum);
			
			var y;
			
			//水平线
			for (var i = 0; i < this.NearByNum.length; i++) {
			    y = region.y + region.height / 2 - ((region.height / 2 - technicalChart.topAndBottomSpace) / this.maxAbsTechnicalDiff * this.NearByNum[i]);
                this._paintLine(region.x, y, region.x + region.width, y, technicalChart.splitLineColor);
            }
			
			//画垂直线
            splitCount = curveChart.verticalLineCount + 1;
            for (var i = 1; i <= curveChart.verticalLineCount; i++) {
                x = region.x + Math.round( region.width * i / splitCount ),
                this._paintLine(x, region.y, x, region.y + region.height, technicalChart.splitLineColor)
            }

            //MACD柱图
            var oMACDHistoGramPainter = new MACDHistogramPainter(ctx, $.extend({}, technicalChart, {
                riseColor: o.riseColor,
                fallColor: o.fallColor,
                equalColor: o.equalColor,
                splitSpace: o.splitSpace,
                width: o.KLineSingleWidth,
                maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
                topAndBottomSpace: technicalChart.topAndBottomSpace
            }));
            oMACDHistoGramPainter.paint(data);
			
            //MACD DIF
            var oTechnicalLinePainter1 = new TechnicalLinePainter(ctx, $.extend({}, technicalChart, {
                splitSpace: o.splitSpace,
                width: o.KLineSingleWidth,
                maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
                topAndBottomSpace: technicalChart.topAndBottomSpace,
                technicalAnalysis: o.technicalAnalysis,
                lineType: 0
            }));
            oTechnicalLinePainter1.paint(data);
			
            //MACD DEA
            var oTechnicalLinePainter2 = new TechnicalLinePainter(ctx, $.extend({}, technicalChart, {
                splitSpace: o.splitSpace,
                width: o.KLineSingleWidth,
                maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
                topAndBottomSpace: technicalChart.topAndBottomSpace,
                technicalAnalysis: o.technicalAnalysis,
                lineType: 1
            }));
            oTechnicalLinePainter2.paint(data);
		},
		paintTechLine: function(){
			var self = this,
				ctx = this.ctx, 
				data = this.data,
                items = this.data.items,
				o = this.options,
				curveChart = o.curveChart,
				technicalChart = o.technicalChart,
				region = technicalChart.region,
				axisPainter,
                maxLength,
				startIndex;

			//区域外框
			this._paintRect(region.x, region.y, region.width, region.height, technicalChart.borderColor);

			this.maxTechnicalDiff = 0;
			this.maxAbsTechnicalDiff = 0;
			this.minTechnicalDiff = 0;
			var oPainter = new Painter({}, $.extend({}, technicalChart, {
			    splitSpace: o.splitSpace,
			    width: o.KLineSingleWidth
			})
            );
			maxLength = oPainter.getMaxPaintItemsLength();
			startIndex = items.length - maxLength;
			startIndex = startIndex < 0 ? 0 : startIndex;
			for (var i = startIndex; i < items.length; i++) {
			    findMaxMin.call(self, items[i], o.technicalAnalysis);
			}
			
		    //文字部分
			this.NearByNum = this._getNearbyNum(o.technicalAnalysis, startIndex);
		    axisPainter = new yAxisTechnical(ctx, $.extend({}, technicalChart.scalerLeft, {
		        technicalAnalysis: o.technicalAnalysis,
				maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
		        maxTechnicalDiff: this.maxTechnicalDiff,
				minTechnicalDiff: this.minTechnicalDiff,
		        topAndBottomSpace: technicalChart.topAndBottomSpace
		    }));
            axisPainter.paint(this.NearByNum);
			
			var y;
			
			//水平线
			for (var i = 0; i < this.NearByNum.length; i++) {
			    if (o.technicalAnalysis == "DMA") {
			        y = region.y + region.height / 2 - ((region.height / 2 - technicalChart.topAndBottomSpace) / this.maxAbsTechnicalDiff * this.NearByNum[i]);
			    }
			    else {
			        y = region.y + region.height - technicalChart.topAndBottomSpace - ((region.height - technicalChart.topAndBottomSpace * 2) / (this.maxTechnicalDiff - this.minTechnicalDiff) * (this.NearByNum[i] - this.minTechnicalDiff));
			    }
                this._paintLine(region.x, y, region.x + region.width, y, technicalChart.splitLineColor);
			}
			
			//画垂直线
            splitCount = curveChart.verticalLineCount + 1;
            for (var i = 1; i <= curveChart.verticalLineCount; i++) {
                x = region.x + Math.round( region.width * i / splitCount ),
                this._paintLine(x, region.y, x, region.y + region.height, technicalChart.splitLineColor)
            }
			
			//线1
            var oTechnicalLinePainter1 = new TechnicalLinePainter(ctx, $.extend({}, technicalChart, {
                splitSpace: o.splitSpace,
                width: o.KLineSingleWidth,
				maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
                maxTechnicalDiff: this.maxTechnicalDiff,
				minTechnicalDiff: this.minTechnicalDiff,
                topAndBottomSpace: technicalChart.topAndBottomSpace,
                technicalAnalysis: o.technicalAnalysis,
                lineType: 0
            }));
            oTechnicalLinePainter1.paint(data);
			
			//线2
            var oTechnicalLinePainter2 = new TechnicalLinePainter(ctx, $.extend({}, technicalChart, {
                splitSpace: o.splitSpace,
                width: o.KLineSingleWidth,
				maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
                maxTechnicalDiff: this.maxTechnicalDiff,
				minTechnicalDiff: this.minTechnicalDiff,
                topAndBottomSpace: technicalChart.topAndBottomSpace,
                technicalAnalysis: o.technicalAnalysis,
                lineType: 1
            }));
            oTechnicalLinePainter2.paint(data);
			
			//线3
			if(o.technicalAnalysis == "KDJ" || o.technicalAnalysis == "RSI"){
				var oTechnicalLinePainter3 = new TechnicalLinePainter(ctx, $.extend({}, technicalChart, {
					splitSpace: o.splitSpace,
					width: o.KLineSingleWidth,
					maxAbsTechnicalDiff: this.maxAbsTechnicalDiff,
					maxTechnicalDiff: this.maxTechnicalDiff,
					minTechnicalDiff: this.minTechnicalDiff,
					topAndBottomSpace: technicalChart.topAndBottomSpace,
					technicalAnalysis: o.technicalAnalysis,
					lineType: 2
				}));
				oTechnicalLinePainter3.paint(data);
			}
			
		},
		_cLimitNow: function (technicalName, startIndex) {
		    var m_limits = [Float_LIMIT_VALUE, Float_LIMIT_VALUE];
		    if (!this.data.items || this.data.items.length <= 0) return;
		    var tempArray = [], cLimitNowIndex = startIndex;
		    while (cLimitNowIndex < this.data.items.length) {
		        tempArray = this.data.items[cLimitNowIndex][technicalName] || [];
		        if (technicalName == 0) {
		            m_limits[0] = this._getLimit(tempArray, true, Float_LIMIT_VALUE);
		            m_limits[1] = this._getLimit(tempArray, false, Float_LIMIT_VALUE);
		        } else {
		            tempArray = tempArray.concat().concat(m_limits);
		            m_limits[0] = this._getLimit(tempArray, true, Float_LIMIT_VALUE);
		            m_limits[1] = this._getLimit(tempArray, false, Float_LIMIT_VALUE);
		        }
		        cLimitNowIndex++;
		    }
		    return m_limits;
		},
        _getLimit: function (list, max, Float_LIMIT_VALUE) {
            var fuc = (max ? Math.max : Math.min);
            var arr = [], len = list.length;
            for (var i = 0; i < len; i++) { if (list[i] != Float_LIMIT_VALUE) arr.push(list[i]); }
            if (arr.length < 2) return Float_LIMIT_VALUE;
            return fuc.apply(null, arr);
        },
        _getNearbyNum: function (technicalName, startIndex) {
            var nextnum = [], name = technicalName;
            this.__limits = this._cLimitNow(name, startIndex);
            if (name == "MACD") {
                var maxnum = (this.__limits[0] > this.__limits[1] && this.__limits[0] > this.__limits[1] * -1) ? this.__limits[0] : this.__limits[1];
                var minnum = (this.__limits[0] < this.__limits[1] && this.__limits[0] > this.__limits[1] * -1) ? this.__limits[0] : this.__limits[1];
                if (!maxnum || !minnum) { return []; }
                var firstnum = 0, basenum = 1, maxnumstr = maxnum.toString();
                if (maxnum > 0) {
                    if (maxnum > 1) {
                        firstnum = parseInt(maxnumstr.toString().substr(0, 1));
                        var baseLeng = (maxnumstr.indexOf(".") == -1) ? maxnumstr.length : 1 * maxnumstr.indexOf(".");
                        for (var i = 0; i < baseLeng - 1; i++) { basenum = basenum * 10; }
                    }
                    else { firstnum = parseInt(maxnumstr.substr(2, 1)); basenum = 0.1; }
                } else {
                    if (maxnum > -1) { firstnum = parseInt(maxnumstr.substr(3, 1)); basenum = -0.1; }
                    else {
                        firstnum = parseInt(maxnumstr.substr(1, 1));
                        basenum = -1;
                        var NegativebaseLeng = (maxnumstr.indexOf(".") == -1) ? maxnumstr.length - 1 : 1 * maxnumstr.indexOf(".") - 1;
                        for (var j = 0; j < NegativebaseLeng - 1; j++) { basenum = basenum * 10; }
                    }
                }
                var Resultone = 0;
                var Resulttwo = 0;
                if (firstnum >= 8) Resultone = 8;
                else if (firstnum >= 4) Resultone = 4;
                else if (firstnum >= 2) Resultone = 2
                else if (firstnum >= 1) Resultone = 1
                Resultone = Resultone * basenum;
                if (Resultone * -1 > minnum) Resulttwo = Resultone * -1;
                else if (Resultone / 2 * -1 > minnum) Resulttwo = Resultone * -1 / 2;
                else Resulttwo = Resultone / 2;
                nextnum.push(0);
                nextnum.push(Resultone);
                nextnum.push(Resulttwo);
            } else if (name == "RSI") {
                nextnum.push(20000);
                nextnum.push(50000);
                nextnum.push(80000);
				nextnum.push(100000);
            } else if (name == "KDJ") {
                nextnum.push(0);
                nextnum.push(2000);
                nextnum.push(5000);
                nextnum.push(8000);
                nextnum.push(10000);
            } else nextnum.push(0);
            return nextnum;
        }
	});
	
})(Zepto);








