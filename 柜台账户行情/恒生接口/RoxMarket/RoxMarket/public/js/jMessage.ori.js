var jMessage = {
	el: function(){
		if($('.msg').length <= 0){
		    return $("<div class='msg'><div class='msg-info'><span /></div><div class='msg-title'></div><div class='msg-content'></div><div class='msg-foot'><ul id='alertMsg' class='msg-buttons'><li><a href='javascript:;' class='button'></a></li></ul></div></div>").appendTo($('body'));
		}
		return $('.msg');
	},
	createMask: function () {
	    if ($('.overlay').length <= 0) {
	        return $("<div class='overlay'></div>").appendTo($('body'));
	    }
	    return $('.overlay');
	},
	open: function(option, callback1, callback2){
		var def = {
			title: '提示',
			content: '必须输入',
			model: 'alert',
			icon: 'info',
			autoHide: true,
			width: 200,
            mask: true
		};
		var $self = this, $el = this.el();
		var m_option = $.extend(def, option);
		if(m_option.title)
			$el.find(".msg-title").html(m_option.title).show();
		if(m_option.content)
		    $el.find(".msg-content").html(m_option.content).show();
		if (m_option.icon && m_option.icon == 'info')
		    $el.find(".msg-info").removeClass('success').addClass('info').show();
		if (m_option.icon && m_option.icon == 'success')
		    $el.find(".msg-info").removeClass('info').addClass('success').show();
		if(m_option.button){
			$el.find(".msg-foot").show().find(".button").each(function(i){
				$(this).text(m_option.button.text[i]);
			})
			if(typeof callback1 == 'function'){
				if(m_option.model == 'alert' || m_option.model == 'confirm'){
					$el.find(".button").first().bind('click', function(){
						callback1();
					})
				}
			}
			else if(typeof callback2 == 'function'){
				if(m_option.model == 'confirm'){
					$el.find(".button").last().bind('click', function(){
						callback2();
					})
				}
			}
		}
		$el.css({
			width: m_option.width + 'px',
			top: m_option.top ? m_option.top + 'px' : ($(window).height() - $el.height()) / 2 + 'px',
			left: ($(window).width() - m_option.width) / 2 + 'px'
		});
		$el.removeClass("hide").addClass("show");
		if(m_option.autoHide)
			setTimeout(function(){$el.removeClass("show").addClass("hide");}, 500);
		if(m_option.focusId){
			//setTimeout(function(){$('#' + m_option.focusId).focus()}, 3000);
		}
	},
	close: function(){
		var $el = this.el();
		$el.removeClass("show").addClass("hide");
	}
}