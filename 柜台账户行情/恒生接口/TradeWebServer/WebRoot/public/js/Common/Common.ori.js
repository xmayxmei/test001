String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {    
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
　　　　　　　　　　　　var reg= new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}

var months = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
function formatteDate(dateStr){
	var d = new Date(),
		year = parseInt(dateStr.substring(0,4), 10),
		month = parseInt(dateStr.substring(4,6), 10),
		day = parseInt(dateStr.substring(6,8), 10);
	if(!year || !month || !day){
		return false;
	}
	if(month<1 || month>12){
		return false;
	}
	if(isLeapYear(year)){
		if(month==2 &&(day<0||day>29)){
			return false;
		}else if(day<0 || day>months[month-1]){
			return false;
		}
	}else if(day<0 || day>months[month-1]){
		return false;
	}
	
	
	d.setFullYear(year);
	d.setMonth(month-1);
	d.setDate(day);
	return d;
}
// 判断是否为闰年
function isLeapYear(num){
	return !(num%100%4);
}