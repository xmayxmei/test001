var g_DatePickers = [];
$.fn.datepicker = function(vOptions){	
	var selfArguments = arguments;
	return $(this).each(function(){
		var self = $(this);
		var datepicker = {
			option : (function(){
				if(typeof vOptions == 'object'){
					return $.extend({},$.fn.datepicker.defaults, vOptions);
				}
				return {};
			})(),
			init : function(){
				this._target = self;			
				this._picker = (function(){
					var arr = [];
					arr.push('<div class="picker picker--focused picker--opened">');
					arr.push('	<div class="picker__holder">');
					arr.push('		<div class="picker__frame">');
					arr.push('			<div class="picker__wrap">');
					arr.push('				<div class="picker__box">');
					arr.push('					<div class="picker__header">');
					arr.push('						<div class="picker__title"></div>');
					arr.push('						<div class="picker__nav--prev" title="Previous month"> </div>');
					arr.push('						<div class="picker__nav--next" title="Next month"> </div>');
					arr.push('					</div>');
					arr.push('					<table class="picker__table">');
					arr.push('						<thead>');
					arr.push('							<tr>');
					arr.push('								<th class="picker__weekday" scope="col" title="Monday">一</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Tuesday">二</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Wednesday">三</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Thursday">四</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Friday">五</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Saturday">六</th>');
					arr.push('								<th class="picker__weekday" scope="col" title="Sunday">日</th>');
					arr.push('							</tr>');
					arr.push('						</thead>');
					arr.push('						<tbody>');
					arr.push('						</tbody>');
					arr.push('					</table>');
					arr.push('					<div class="picker__footer">');
					arr.push('						<button class="picker__button--today" type="button">今天</button>');
					arr.push('						<button class="picker__button--clear" type="button">清除</button>');
					arr.push('					</div>');
					arr.push('				</div>');
					arr.push('			</div>');
					arr.push('		</div>');
					arr.push('	</div>');
					return $(arr.join(''));
				})();
	
				var p = this;
				this._picker.find('.picker__nav--prev,.picker__nav--next').on('touchstart', function(){
					$(this).addClass('hover');
				}).on('touchend', function(){
					$(this).removeClass('hover');
				}).click(function(){
					if($(this).hasClass('picker__nav--prev')){
						p._date.setMonth(p._date.getMonth() - 1);
					} else {
						p._date.setMonth(p._date.getMonth() + 1);
					}
					p._renderDays();
				});	
				this._picker.find('.picker__button--clear').click(function(){
					p._fillDate('');
					p.hide();
					if(typeof p.option.onSelect == 'function'){
						p.option.onSelect(null);
					}
				});
				this._picker.find('.picker__button--today').click(function(){
					p._fillDate(new Date());
					p.hide();
					if(typeof p.option.onSelect == 'function'){
						p.option.onSelect(new Date());
					}
				});
				
				this._picker.click(function(e){
					e.preventDefault();
					e.stopPropagation();
				});
				
				$(document).click(function(e){
					if(e.target != p._picker[0] && e.target != p._target[0]){
						p.hide();
					}
				});
				//this._picker.insertAfter(this._target).hide(); 层级堆顺有问题
				this._picker.appendTo($('body')).hide();
				return this;
			},
			show : function(){
				this._picker.removeClass('hide').addClass('show');
				this._picker.show();
			},
			hide : function(){
				//this._picker.hide();
				var p = this;
				this._picker.removeClass('show').addClass('hide');
				setTimeout(function(){p._picker.hide();}, 500);
			},
			_renderDays : function(){
				var year = this._date.getFullYear()
					, month = this._date.getMonth() + 1
					, day = this._date.getDate()	//当天
					, days = new Date(new Date(year, month, 1) - 24*60*60*1000).getDate()	//本月最后一天
					, today = new Date()
					, week = (function(){
							var tDate = new Date(year, month-1, 1);
							var week = tDate.getDay();
							if(week == 0){
								week = 7;
							}
							return week;
						})();	//本月第一天是星期几
						
				this._picker.find('.picker__header>.picker__title').html(year + ' 年 ' + month + ' 月');
		
				var arr = []
					, d = 2 - week, rows = Math.ceil((week - 1 + days) / 7);
					
				for(var i = 0; i < rows; i++){
					arr.push('<tr>');
					for(var j = 1; j < 8; j++, d++){
						if(d <= 0 || d > days){
							arr.push('<td>&nbsp;</td>');
						}
						else{
							arr.push('<td><div class="picker__day picker__day--infocus');
							if(this.option.minDate && (new Date(year, month - 1, d).getTime() < new Date(this.option.minDate.getFullYear(), this.option.minDate.getMonth(), this.option.minDate.getDate()).getTime())){
								arr.push(' picker__day--disabled');
							}
							else if(this.option.maxDate && (new Date(year, month - 1, d).getTime() > new Date(this.option.maxDate.getFullYear(), this.option.maxDate.getMonth(), this.option.maxDate.getDate()).getTime())){
								arr.push(' picker__day--disabled');
							}
							else{
								if(new Date(year, month - 1, d).getTime() == new Date(today.getFullYear(), today.getMonth(), today.getDate()).getTime()){
									arr.push(' picker__day--today');
								}
								if(d == day){
									arr.push(' picker__day--highlighted');
								}
								if(this._selectedDate && (new Date(year, month - 1, d).getTime() == new Date(this._selectedDate.getFullYear(), this._selectedDate.getMonth(), this._selectedDate.getDate()).getTime())){
									arr.push(' picker__day--selected');
								}
							}
							arr.push('">');
							arr.push(d);
							arr.push('</div></td>');
						}
					}
					arr.push('</tr>');
				}
				
				this._picker.find('tbody')[0].innerHTML = arr.join('');
				
				var p = this;
				this._picker.find('.picker__day').unbind().on('touchstart', function(){
					$(this).addClass('hover');
				}).on('touchend', function(){
					$(this).removeClass('hover');
				}).not('.picker__day--disabled').click(function(){
					var day = parseInt($(this).text(), 10);
					p.hide();
					p._fillDate(new Date(year, month - 1, day));
					if(typeof p.option.onSelect == 'function'){
						p.option.onSelect(new Date(year, month - 1, day));
					}
				});
			},
			_fillDate: function(vdate){
				//var weekDays = ['日', '一', '二', '三', '四', '五', '六'];
				if(vdate)
					this._target.text(vdate.getFullYear() + ('00' + (vdate.getMonth() + 1)).slice(-2) + ('00' + vdate.getDate()).slice(-2)).css('color', '#fff');
				else
					this._target.text('yyyymmdd').css('color', '#ccc');
				this._target.removeClass('outline');
			}
		};
		if(typeof vOptions == 'string' && vOptions == 'option'){
			$.each(g_DatePickers, function(i, n){
				if(n['id'] == self.attr('id')){
					n['value'].option[selfArguments[1]] = selfArguments[2];
				}
			})
		}
		else{
			datepicker.init();	
			self.click(function(){
				datepicker._selectedDate = $.trim(self.text()).length > 0 ? formatteDate($.trim(self.text())) : null;
				datepicker._date = datepicker._selectedDate ? new Date(datepicker._selectedDate.getFullYear(),datepicker._selectedDate.getMonth(),datepicker._selectedDate.getDate()) : new Date();
				datepicker._renderDays();
				datepicker.show();
				self.addClass('outline');
			});
			g_DatePickers.push({'id':self.attr('id'),'value':datepicker});
		}
	});
	
};

$.fn.datepicker.defaults = {
	
};