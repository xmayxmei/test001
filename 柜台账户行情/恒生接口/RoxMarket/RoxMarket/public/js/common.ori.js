var common = {
    getHash: function (paramNames) {
        var paramOption = {},
        match = window.location.href.match(/#(.*)$/),
        hashList = match && match[1].split('/') || [];
        if (paramNames instanceof Array) {
            for (var i in paramNames) {
                paramOption[paramNames[i]] = hashList[i] && hashList[i] || null;
            }
        }
        return paramOption;
    },
    setHash: function (paramValues) {
        var href = window.location.href.replace(/(javascript:|#).*$/, '');
        //window.location.replace(href + '#' + paramValues.join('/'));
        if (window.history['pushState'])
            window.history['pushState']({}, document.title, href + '#' + paramValues.join('/'));
        else
            window.location.href = href + '#' + paramValues.join('/');
    },
    addStock: function (url, code) {
        var result = false, me = this;
        $.ajax({
            type: 'get',
            url: url,
            async: false,
            data: {code: code},
            dataType: 'json',
            success: function (data, status, xhr) {
                if (data && data.info == 1) {
                    result = true;
                }
                else {
                    if (me._addStockToCookies(code))
                        result = true;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (me._addStockToCookies(code))
                    result = true;
            }
        });
        return result;
    },
    delStock: function (url, code) {
        var result = false, me = this;
        $.ajax({
            type: 'get',
            url: url,
            async: false,
            data: { code: code },
            dataType: 'json',
            success: function (data, status, xhr) {
                if (data && data.info == 1) {
                    result = true;
                }
                else {
                    if (me._delStockFromCookies(code))
                        result = true;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (me._delStockFromCookies(code))
                    result = true;
            }
        });
        return result;
    },
    getStockList: function (url) {
        var result = '', me = this;
        $.ajax({
            type: 'get',
            url: url,
            async: false,
            dataType: 'json',
            success: function (data, status, xhr) {
                if (data && data.info == 1) {
                    result = data.data;
                }
                else {
                    result = me._getStockListFromCookies();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                result = me._getStockListFromCookies();
            }
        });
        return result;
    },
    _addStockToCookies: function (code) {
        var newCookie, cookies = $.cookie('ticketpcodes').length == 0 ? [] : $.cookie('ticketpcodes').split(',');
        if (cookies.length > 0) {
            newCookie = (cookies.push(code), cookies.join(','));
        } else {
            newCookie = code;
        }
        $.cookie('ticketpcodes', newCookie, 10000, '/');
        return true;
    },
    _delStockFromCookies: function (code) {
        var newCookie, cookies = $.cookie('ticketpcodes').length == 0 ? [] : $.cookie('ticketpcodes').split(',');
        for (var i = 0; i < cookies.length; i++) {
            if (cookies[i] == code) {
                cookies.splice(i, 1);
            }
        }
        newCookie = cookies.join(',');
        $.cookie('ticketpcodes', newCookie, 10000, '/');
        return true;
    },
    _getStockListFromCookies: function () {
        return $.cookie('ticketpcodes');
    },
    clearupCookies: function () {
        var oldCookies = $.cookie('ticketpcodes');
        oldCookies = (oldCookies && oldCookies.split(',')) || [];

        for (var i = oldCookies.length - 1; i >= 0; i--) {
            if (!oldCookies[i]) {
                oldCookies.splice(i, 1);
                continue;
            }
            if (oldCookies[i].indexOf('undefined') !== -1) {
                oldCookies[i] = oldCookies[i].replace('undefined', '');
            }
        }
        $.cookie('ticketpcodes', oldCookies.join(','), 10000, '/');
    }
}