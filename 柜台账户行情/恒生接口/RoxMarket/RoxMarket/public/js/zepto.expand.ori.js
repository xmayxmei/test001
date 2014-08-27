;(function($){

	var $ = window.$ || function(){};

	function addClickEvent(callback){
		
		if('ontouchstart' in window){
			 $(this).bind('touchend', callback);
		}else{
			 $(this).bind('click', callback);
		}
	}

	function delClickEvent(callback) {

	    if ('ontouchstart' in window) {
	        $(this).unbind('touchend', callback);
	    } else {
	        $(this).unbind('click', callback);
	    }
	}

	function delegateClick(selector, callback){
		
		if('ontouchstart' in window){
			 $(this).delegate(selector, 'touchend', callback);
		}else{
			 $(this).delegate(selector, 'click', callback);
		}
	}

	function undelegateClick(selector, callback) {

	    if ('ontouchstart' in window) {
	        $(this).undelegate(selector, 'touchend', callback);
	    } else {
	        $(this).undelegate(selector, 'click', callback);
	    }
	}

	function setCookie(c_name, value, expiredays, path, domain, secure) {
		// {name: '',value:'',expires:'',path:'',domain:'',secure:''}
		var exdate;
		if(window.localStorage){
			window.localStorage[c_name] = escape(value);
		}else{
			exdate=new Date()
			exdate.setDate(exdate.getDate() + expiredays)
			document.cookie = c_name + "=" + escape(value) +
		    ((expiredays) ? ";expires=" + exdate.toUTCString() : "") +
		    ((path) ? ";path=" + (path || '/') : "") +
		    ((domain) ? ";domain=" + (domain || location.hostname) : '') +
		    ((secure) ? ";secure" : "");
		}
		
	}

	function getCookie(c_name){
		var c_start, c_end, c_result;
		if(window.localStorage){
			c_result = window.localStorage[c_name] === undefined ? null : unescape(window.localStorage[c_name])
			return c_result;
		}
		else if(document.cookie.length > 0){
			c_start = document.cookie.indexOf(c_name+ '=');
			if(c_start !== -1){

				c_end = document.cookie.indexOf(';', c_start);
				
				if(c_end !==-1){
					return unescape(document.cookie.substring(c_start+c_name.length+1, c_end));
				}else{
					return unescape(document.cookie.substring(c_start+c_name.length+1));
				}

			}
		}
		return null;
	}
	
	

	$.extend(Zepto.fn, {

		addClickEvent : function(callback){
			return this.each(function(){
				addClickEvent.apply(this, [callback]);
			});
		},
		delClickEvent: function (callback) {
		    return this.each(function () {
		        delClickEvent.apply(this, [callback]);
		    });
		},
		delegateClick : function(selector, callback){
			return this.each(function(){
				delegateClick.apply(this, [selector, callback]);
			});
		},
		undelegateClick: function (selector, callback) {
		    return this.each(function () {
		        undelegateClick.apply(this, [selector, callback]);
		    });
		}
	});

	$.extend($, {
		cookie: function(){
			if(arguments.length > 1){
				setCookie.apply(this, arguments);
				return this;
			}else{
				return getCookie.apply(this, arguments);
			}
			
		},
		isAndroidOS: function() {  
    		var sUserAgent = navigator.userAgent; 
    		return sUserAgent.toLowerCase().match(/android/i) == "android";
		}
	});
})(Zepto);