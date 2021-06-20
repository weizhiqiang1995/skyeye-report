
// 操作添加或者编辑时，判断表格是否需要刷新,为0则刷新，否则则不刷新
var refreshCode = "";

/**
 * 打开新的窗口
 * @param url
 * @param params
 * @param title
 */
function _openNewWindows(mation){
	if(isNull(mation.url)){
		winui.window.msg("页面路径不能为空", {icon: 2,time: 2000});
		return;
	}
	if(isNull(mation.pageId)){
		winui.window.msg("缺少页面ID", {icon: 2,time: 2000});
		return;
	}
	if(isNull(mation.title)){
		mation.title = "窗口";
	}
	if(!isNull(mation.params)){
		var s = "";
		for(var param in mation.params)
			s += "&" + param + "=" + mation.params[param];
		mation.url = mation.url + "?" + s.slice(1);
	}
	if(isNull(mation.area)){
		if(mation.maxmin){
			mation.area = ['100vw', '100vh'];
		}else{
			mation.area = ['90vw', '90vh'];
		}
	}
	if(isNull(mation.offset)){
		mation.offset = 'auto';
	}
	if(isNull(mation.maxmin)){//是否最大化
		mation.maxmin = false;
	}
	if(isNull(mation.shade) && mation.shade != false && mation.shade != 0){//遮罩层
		mation.shade = 0.5;
	}
	if(isNull(mation.closeBtn) && mation.closeBtn != '0'){//关闭按钮
		mation.closeBtn = 1;
	}
	if(isNull(mation.skin)){//用户自定义皮肤或者层级
		mation.skin = '';
	}
    refreshCode = "";
    var pageIndex = layer.open({
    	id: mation.pageId,
        type: 2,
        skin: mation.skin,
        title: mation.title,
        content: mation.url,
        area: mation.area,
        offset: mation.offset,
        maxmin: mation.maxmin,
        shade: mation.shade,
        zIndex: mation.zIndex,
        scrollbar: false,
        closeBtn: mation.closeBtn,
        end: function(){
        	if(typeof(mation.callBack) == "function") {
        		mation.callBack(refreshCode);
			}
        },
        success: function(){
        	var times = layui.$("#" + mation.pageId).parent().attr("times");
			var zIndex = layui.$("#" + mation.pageId).parent().css("z-index");
			layui.$("#layui-layer-shade" + times).css({'z-index': zIndex});
        	if(typeof(mation.success) == "function") {
        		mation.success();
			}
        }
    });
    if(mation.maxmin){
		layer.full(pageIndex);
    }
}

/**
 * 根据页面高度进行分页
 * @return {}
 */
function getLimits(){
	var limit = getLimit();
	var limits = new Array();
	for(var i = 1; i <= 7; i++){
		limits.push(limit * i);
	}
	return limits;
}

function getLimit(){
	var clientHeight = document.body.clientHeight;
	var toolbarHeight = $(".winui-toolbar").outerHeight(true);
	var txtcenterHeight = $(".txtcenter").outerHeight(true);
	var tabTtileHeight = $(".layui-tab-title").outerHeight(true);
	// 计算表格tbody的高度
	var realHeight = clientHeight - 100 
					- (isNull(toolbarHeight) ? 0 : toolbarHeight) 
					- (isNull(txtcenterHeight) ? 0 : txtcenterHeight) 
					- (isNull(tabTtileHeight) ? 0 : tabTtileHeight);
	// 计算limit
	return decimerFiveOrZero(Math.floor(realHeight / 35));
}

function decimerFiveOrZero(number){
	var newNum = Math.floor(number / 5);
	return newNum * 5;
}

/**
 * 非表格分页加载插件
 */
var dataGrid_setting = [];
var dataGrid = function(ele, opt) {
	this.defaults = {
		// id
		id: "",
		// 请求url
		url: null,
		// 如果url为空，则加载data数据
		data: null,
		// 模板
		template: null,
		// 请求类型
		method: "POST",
		// 参数
		params: null,
		// 是否分页
		pagination: false,
		// 页显示
		pagesize: 10,
		// 页索引
		pageindex: 1,
		// 总页数
		totalpage: null,
		// 点击分页之前的回调函数
		pageClickBefore: function(index){},
		// 点击分页之后的回调函数
		pageClickAfter: function(index){},
		// ajax请求之前的回调函数
		ajaxSendBefore:function(json){},
		// ajax请求之后的加载数据之前的回调函数
		ajaxSendLoadBefore:function(hdb, json){},
		// ajax请求之后的回调函数
		ajaxSendAfter:function(json){},
		// ajax请求之后加载错误的回调函数
		ajaxSendErrorAfter:function(json){},
		// 按钮监听事件
		options:null,
		// handlber对象
		hdb:null
	}
	this.settings = layui.$.extend({}, this.defaults, opt);
}

dataGrid.prototype = {
	_id: null,
	_op: null,
	init: function() {
		this._id = this.settings.id;
		_op = this;
		_op.settings.hdb = Handlebars;
		this.create();
		this.bindEvent();
	},
	create: function() {
		//初始化元素
		if(this.settings.pagination){
			this.InitializeElement();
			this.createBody(1);//初始化动态行
		} else{
			this.createBodyNoFoot(1);//初始化动态行
		}
		//选择是否分页
		if(this.settings.pagination) {
			this.createFoot();
		}
	},
	bindEvent: function() {
		if(this.settings.pagination){
			//每页点击事件
			this.itemClickPage();
			//添加上一页事件
			this.registerUpPage();
			//添加下一页事件
			this.registerNextPage();
			//添加首页事件
			this.registerFirstPage();
			//添加最后一页事件
			this.registerlastPage();
			//添加跳转事件
			this.registerSkipPage();
		}
		//添加鼠标悬浮事件
		this.registermousehover();
		//添加全选全不选事件
		this.registercheckall();
	},
	//初始化元素
	InitializeElement: function() {
		layui.$("#" + this._id + "").empty().html("<div class='layui-col-xs12 row-model' id='" + _op.settings.id + "showBody'></div><div class='layui-col-xs12 row-model' id='" + _op.settings.id + "showFoot' style='text-align: center;'><div class='pagec layui-col-xs12' id='pagearea'><ul class='pagination layui-col-xs6'></ul></div></div>");
	},
	//初始化元素
	createBodyNoFoot: function(pn) {
		if(typeof(_op.settings.ajaxSendBefore) == "function") {
			_op.settings.ajaxSendBefore(event);
		}
		var offset = (_op.settings.pageindex - 1) * _op.settings.pagesize;
		var pageParams = {
			offset: offset,
			limit: _op.settings.pagesize,
			page: _op.settings.pageindex
		};
		_op.settings.params = layui.$.extend({}, _op.settings.params, pageParams);
		var json = this.getAjaxDate(_op.settings.url, _op.settings.params, _op.settings.method);
		if(json.returnCode === '0' || json.returnCode === 0){
			//总页数=向上取整(总数/每页数)
			_op.settings.totalpage = Math.ceil((json.total) / _op.settings.pagesize);
			//开始页数
			var startPage = _op.settings.pagesize * (pn - 1);
			//结束页数
			var endPage = startPage + _op.settings.pagesize;
			if(typeof(_op.settings.ajaxSendLoadBefore) == "function") {
				_op.settings.ajaxSendLoadBefore(_op.settings.hdb, json);
			}
			var myTemplate = null;
			if(json.total == 0){
				myTemplate = _op.settings.hdb.compile(noBeansMation);
				layui.$("#" + _op.settings.id + "showFoot").hide();
			}else{
				layui.$("#" + _op.settings.id + "showFoot").show();
				myTemplate = _op.settings.hdb.compile(_op.settings.template);
			}
			layui.$("#" + this._id + "").empty().html(myTemplate(json));
			this.registermousehover();
			this.customClickPage(json);
			if(typeof(_op.settings.ajaxSendAfter) == "function") {
				_op.settings.ajaxSendAfter(json);
			}
		}else{
			winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
			if(typeof(_op.settings.ajaxSendErrorAfter) == "function") {
				_op.settings.ajaxSendErrorAfter(json);
			}
		}
	},
	//循环添加行
	createBody: function(pn) {
		if(typeof(_op.settings.ajaxSendBefore) == "function") {
			_op.settings.ajaxSendBefore(event);
		}
		var offset = (_op.settings.pageindex - 1) * _op.settings.pagesize;
		var pageParams = {
			offset: offset,
			limit: _op.settings.pagesize,
			page: _op.settings.pageindex
		};
		_op.settings.params = layui.$.extend({}, _op.settings.params, pageParams);
		var json = this.getAjaxDate(_op.settings.url, _op.settings.params, _op.settings.method);
		if(json.returnCode === '0' || json.returnCode === 0){
			//总页数=向上取整(总数/每页数)
			_op.settings.totalpage = Math.ceil((json.total) / _op.settings.pagesize);
			//开始页数
			var startPage = _op.settings.pagesize * (pn - 1);
			//结束页数
			var endPage = startPage + _op.settings.pagesize;
			if(typeof(_op.settings.ajaxSendLoadBefore) == "function") {
				_op.settings.ajaxSendLoadBefore(_op.settings.hdb, json);
			}
			var myTemplate = null;
			if(json.total == 0){
				myTemplate = _op.settings.hdb.compile(noBeansMation);
				layui.$("#" + _op.settings.id + "showFoot").hide();
			}else{
				_op.settings.total = json.total;
				layui.$("#" + _op.settings.id + "showFoot").show();
				myTemplate = _op.settings.hdb.compile(_op.settings.template);
			}
			layui.$("#" + _op.settings.id + "showBody").empty().html(myTemplate(json));
			this.registermousehover();
			this.customClickPage(json);
			if(typeof(_op.settings.ajaxSendAfter) == "function") {
				_op.settings.ajaxSendAfter(json);
			}
		}else{
			winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
			if(typeof(_op.settings.ajaxSendErrorAfter) == "function") {
				_op.settings.ajaxSendErrorAfter(json);
			}
		}
	},
	//初始化分页
	createFoot: function() {
		var totalsubpageTmep = "";
		if(1 === _op.settings.pageindex){
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn_dis' data-go='' id='firstPage'><<</a></li>";
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn_dis' data-go='' id='UpPage'><</a></li>";
		}else{
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn' data-go='' id='firstPage'><<</a></li>";
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn' data-go='' id='UpPage'><</a></li>";
		}
		// 页码大于等于4的时候，添加第一个页码元素
		if(_op.settings.pageindex != 1 && _op.settings.pageindex >= 4 && _op.settings.totalpage != 4) {
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >" + 1 + "</a></li>";
		}
		/* 当前页码>4, 并且<=总页码，总页码>5，添加“···”*/
		if(_op.settings.pageindex - 2 > 2 && _op.settings.pageindex <= _op.settings.totalpage && _op.settings.totalpage > 5) {
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
		}
		/* 当前页码的前两页 */
		var start = _op.settings.pageindex - 2;
		/* 当前页码的后两页 */
		var end = _op.settings.pageindex + 2;
		
		if((start > 1 && _op.settings.pageindex < 4) || _op.settings.pageindex == 1) {
			end++;
		}
		if(_op.settings.pageindex > _op.settings.totalpage - 4 && _op.settings.pageindex >= _op.settings.totalpage) {
			start--;
		}
		for(; start <= end; start++) {
			if(start <= _op.settings.totalpage && start >= 1) {
				if(_op.settings.pageindex == start){
					totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager showgrid-active' data-go='' >" + start + "</a></li>";
				}else{
					totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >" + start + "</a></li>";
				}
			}
		}
		if(_op.settings.pageindex + 2 < _op.settings.totalpage - 1 && _op.settings.pageindex >= 1 && _op.settings.totalpage > 5) {
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_' data-go='' >...</a></li>";
		}
		if(_op.settings.pageindex != _op.settings.totalpage && _op.settings.pageindex < _op.settings.totalpage - 2 && _op.settings.totalpage != 4) {
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='geraltTb_pager' data-go='' >" + _op.settings.totalpage + "</a></li>";
		}
		if(_op.settings.totalpage === _op.settings.pageindex){
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn_dis' data-go=''>></a></li>";
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn_dis' data-go=''>>></a></li>";
		}else{
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn' data-go='' id='nextPage'>></a></li>";
			totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='page_btn' data-go='' id='lastPage'>>></a></li>";
		}
		totalsubpageTmep += "<li class='ali'><a href='javascript:void(0);' class='pageBtn' data-go=''>" + systemLanguage["com.skyeye.toThe"][languageType]
			+ "&nbsp;&nbsp;<input type='text' id='pageInput' value='" 
			+ _op.settings.pageindex + "' class='pageInput'/>&nbsp;&nbsp;" + systemLanguage["com.skyeye.page2"][languageType]
			+ "<span id='skippage'>" + systemLanguage["com.skyeye.determine"][languageType]
			+ "</span>&nbsp;&nbsp;" + systemLanguage["com.skyeye.all"][languageType] + "&nbsp;&nbsp;"
			+ _op.settings.total + "&nbsp;&nbsp;" + systemLanguage["com.skyeye.total"][languageType] + "</a></li>";
		layui.$(".pagination").html(totalsubpageTmep);
	},
	//添加鼠标悬浮事件
	registermousehover: function() {
		//添加鼠标悬浮事件
	},
	//添加全选全不选事件
	registercheckall: function() {
		//添加全选全不选事件
	},
	//自定义按钮事件
	customClickPage: function(json){
		var options = _op.settings.options;
		for(var _option in options){
			if(typeof(options[_option]) == "function") {
				this.addEventHandler(layui.$(_option.split(" ")[1]), _option.split(" ")[0], this.clickCallFun, json, options[_option]);
			}
		}
	},
	//自定义点击执行事件
	clickCallFun: function(objs, json, fun){
		fun(objs.index(this), json.rows[objs.index(this)]);
	},
	/**
	 * 添加事件监听函数
	 * @param {Object} obj 要添加监听的对象或元素
	 * @param {Object} eventName 事件名
	 * @param {Object} fun 监听函数的名称
	 * @param {Object} param 给监听函数传的参数，这里就传了一个参数
	 */
	addEventHandler: function(objs, eventName, fun, param, callFun) {
		var fn = fun;
		var obj = null;
		for(var i = 0; i<objs.length; i++){
			obj = objs[i];
			if(param) {
				fn = function(e) {
					fun.call(this, objs, param, callFun); //继承监听函数,并传入参数以初始化;
				}
			}
			if(obj.attachEvent) {
				obj.attachEvent('on' + eventName, fn);
			} else if(obj.addEventListener) {
				obj.addEventListener(eventName, fn, false);
			} else {
				obj["on" + eventName] = fn;
			}
		}
	},
	//固定页点击事件
	itemClickPage: function(){
		layui.$("#" + _op.settings.id + "showFoot").delegate("a.geraltTb_pager", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			var current = parseInt(layui.$(this).text());
			if(typeof(_op.settings.pageClickBefore) == "function") {
				_op.settings.pageClickBefore(_op.settings.pageindex);
			}
			_op.settings.pageindex = current;
			_op.createBody(_op.settings.pageindex);
			_op.createFoot();
			if(typeof(_op.settings.pageClickAfter) == "function") {
				_op.settings.pageClickAfter(current);
			}
		});
	},
	//添加首页事件
	registerFirstPage: function() {
		layui.$("#" + _op.settings.id + "showFoot").delegate("#firstPage", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			if(typeof(_op.settings.pageClickBefore) == "function") {
				_op.settings.pageClickBefore(_op.settings.pageindex);
			}
			_op.settings.pageindex = 1;
			_op.createBody(_op.settings.pageindex);
			_op.createFoot();
			if(typeof(_op.settings.pageClickAfter) == "function") {
				_op.settings.pageClickAfter(_op.settings.pageindex);
			}
		});
	},
	//添加上一页事件
	registerUpPage: function() {
		layui.$("#" + _op.settings.id + "showFoot").delegate("#UpPage", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			if(typeof(_op.settings.pageClickBefore) == "function") {
				_op.settings.pageClickBefore(_op.settings.pageindex);
			}
			if(_op.settings.pageindex == 1) {
				alert("已经是第一页了");
				return;
			}
			_op.settings.pageindex = _op.settings.pageindex - 1;
			_op.createBody(_op.settings.pageindex);
			_op.createFoot();
			if(typeof(_op.settings.pageClickAfter) == "function") {
				_op.settings.pageClickAfter(_op.settings.pageindex);
			}
		});
	},
	//添加下一页事件
	registerNextPage: function() {
		layui.$("#" + _op.settings.id + "showFoot").delegate("#nextPage", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			if(typeof(_op.settings.pageClickBefore) == "function") {
				_op.settings.pageClickBefore(_op.settings.pageindex);
			}
			if(_op.settings.pageindex == _op.settings.totalpage) {
				alert("已经是最后一页了");
				return;
			}
			_op.settings.pageindex = _op.settings.pageindex + 1;
			_op.createBody(_op.settings.pageindex);
			_op.createFoot();
			if(typeof(_op.settings.pageClickAfter) == "function") {
				_op.settings.pageClickAfter(_op.settings.pageindex);
			}
		});
	},
	//添加尾页事件
	registerlastPage: function() {
		layui.$("#" + _op.settings.id + "showFoot").delegate("#lastPage", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			if(typeof(_op.settings.pageClickBefore) == "function") {
				_op.settings.pageClickBefore(_op.settings.pageindex);
			}
			_op.settings.pageindex = _op.settings.totalpage;
			_op.createBody(_op.settings.totalpage);
			_op.createFoot();
			if(typeof(_op.settings.pageClickAfter) == "function") {
				_op.settings.pageClickAfter(_op.settings.pageindex);
			}
		});
	},
	//添加页数跳转事件
	registerSkipPage: function() {
		layui.$("#" + _op.settings.id + "showFoot").delegate("#skippage", "click", function(selector) {
			_op.settings = getObject(selector.delegateTarget.id.replace('showFoot',''));
			var value = layui.$("#pageInput").val();
			if(!isNaN(parseInt(value))) {
				if(parseInt(value) <= _op.settings.totalpage){
					if(typeof(_op.settings.pageClickBefore) == "function") {
						_op.settings.pageClickBefore(_op.settings.pageindex);
					}
					_op.settings.pageindex = parseInt(value);
					_op.createBody(parseInt(_op.settings.totalpage));
					_op.createFoot();
					if(typeof(_op.settings.pageClickAfter) == "function") {
						_op.settings.pageClickAfter(value);
					}
				} 
				else alert("超出页总数");
			} else alert("请输入数字");
		});
	},
	//添加异步ajax事件
	getAjaxDate: function(url, parms, method) {
		//定义一个全局变量来接受$post的返回值
		var result;
		if(!isNull(url)){
			//用ajax的同步方式
			parms.userToken = getCookie('userToken');
			parms.loginPCIp = returnCitySN["cip"];
			layui.$.ajax({
				url: url,
				async: false, //改为同步方式
				dataType: "json",
				type: method,
				data: parms,
				success: function(data) {
					//移除请求遮罩层
			        $("body").find(".mask-req-str").remove();
					result = data;
				},
				error: function(XMLHttpRequest, textStatus, xhr){
					//移除请求遮罩层
			        $("body").find(".mask-req-str").remove();
					var sessionstatus = XMLHttpRequest.getResponseHeader('SESSIONSTATUS');
					if (sessionstatus == "TIMEOUT") {//超时跳转
						var win = window;
						while (win != win.top){
							win = win.top;
						}
						result = eval('(' + '{"returnMessage":"登录超时。","returnCode":-9999,"total":0,"rows":"","bean":""}' + ')');
						win.location.href = reqBasePath + "/tpl/index/login.html";//XMLHttpRequest.getResponseHeader("CONTEXTPATH");  
					}else if(sessionstatus == "NOAUTHPOINT"){
						result = eval('(' + '{"returnMessage":"您不具备该权限。","returnCode":-9999,"total":0,"rows":"","bean":""}' + ')');
					}
				}
			});
		} else{
			result = _op.settings.data;
		}
		return result;
	}
}

var showGrid = function (options) {
	var showGrid = new dataGrid(this, options);
	layui.$.each(dataGrid_setting, function(index, item){  
		if(!isNull(item)){
			if(item.settings.id == showGrid.settings.id){
				dataGrid_setting.splice(index, 1);
				return;
			}
		}
	});
	dataGrid_setting.push(_createObject(showGrid.settings.id, showGrid.settings));
	return showGrid.init();
}

var refreshGrid = function (id, option){
	var _option = layui.$.extend({}, getObject(id), option);
	var showGrid = new dataGrid(this, _option);
	layui.$.each(dataGrid_setting, function(index, item){  
        if(item.settings.id == showGrid.settings.id){
        	dataGrid_setting[index].settings.pageindex = 1;
        	showGrid.settings = dataGrid_setting[index].settings;
        	showGrid.settings.params = layui.$.extend({}, showGrid.settings.params, option.params);
        	dataGrid_setting.splice(index, 1, showGrid);
        	return;
		}
	});
	dataGrid_setting.push(_createObject(showGrid.settings.id, showGrid.settings));
	return showGrid.init();
}

var _createObject = function(id, settings){
    var obj = {
        id : id,
        settings : settings
    };
    return obj;
}

var getObject = function(id){
	for(var i in dataGrid_setting){
		if(dataGrid_setting[i].id == id){
			return dataGrid_setting[i].settings;
		}
	}
}

function showFilemsg(file) {
	window.open(file);
}

/**
 * 格式化js代码格式
 */
function do_js_beautify(str) {
	var js_source = str.replace(/^\s+/, '');
	if(js_source.length==0)
		return;
    var tabsize = '1';
    tabchar = '	';
    if (tabsize == 1)
    	tabchar = '\t';
    return js_beautify(js_source, tabsize, tabchar);
}

/*

JS Beautifier
---------------
 $Date: 2008-06-10 14:49:11 +0300 (Tue, 10 Jun 2008) $
 $Revision: 60 $


 Written by Einars "elfz" Lielmanis, <elfz@laacz.lv> 
     http://elfz.laacz.lv/beautify/

 Originally converted to javascript by Vital, <vital76@gmail.com> 
     http://my.opera.com/Vital/blog/2007/11/21/javascript-beautify-on-javascript-translated


 You are free to use this in any way you want, in case you find this useful or working for you.

 Usage:
   js_beautify(js_source_text);

*/


function js_beautify(js_source_text, indent_size, indent_character, indent_level)
{

   var input, output, token_text, last_type, last_text, last_word, current_mode, modes, indent_string;
   var whitespace, wordchar, punct, parser_pos, line_starters, in_case;
   var prefix, token_type, do_block_just_closed, var_line, var_line_tainted;



   function trim_output()
   {
       while (output.length && (output[output.length - 1] === ' ' || output[output.length - 1] === indent_string)) {
           output.pop();
       }
   }

   function print_newline(ignore_repeated)
   {
       ignore_repeated = typeof ignore_repeated === 'undefined' ? true: ignore_repeated;
       
       trim_output();

       if (!output.length) {
           return; // no newline on start of file
       }

       if (output[output.length - 1] !== "\n" || !ignore_repeated) {
           output.push("\n");
       }
       for (var i = 0; i < indent_level; i++) {
           output.push(indent_string);
       }
   }



   function print_space()
   {
       var last_output = output.length ? output[output.length - 1] : ' ';
       if (last_output !== ' ' && last_output !== '\n' && last_output !== indent_string) { // prevent occassional duplicate space
           output.push(' ');
       }
   }


   function print_token()
   {
       output.push(token_text);
   }

   function indent()
   {
       indent_level++;
   }


   function unindent()
   {
       if (indent_level) {
           indent_level--;
       }
   }


   function remove_indent()
   {
       if (output.length && output[output.length - 1] === indent_string) {
           output.pop();
       }
   }


   function set_mode(mode)
   {
       modes.push(current_mode);
       current_mode = mode;
   }


   function restore_mode()
   {
       do_block_just_closed = current_mode === 'DO_BLOCK';
       current_mode = modes.pop();
   }


   function in_array(what, arr)
   {
       for (var i = 0; i < arr.length; i++)
       {
           if (arr[i] === what) {
               return true;
           }
       }
       return false;
   }



   function get_next_token()
   {
       var n_newlines = 0;
       var c = '';

       do {
           if (parser_pos >= input.length) {
               return ['', 'TK_EOF'];
           }
           c = input.charAt(parser_pos);

           parser_pos += 1;
           if (c === "\n") {
               n_newlines += 1;
           }
       }
       while (in_array(c, whitespace));

       if (n_newlines > 1) {
           for (var i = 0; i < 2; i++) {
               print_newline(i === 0);
           }
       }
       var wanted_newline = (n_newlines === 1);


       if (in_array(c, wordchar)) {
           if (parser_pos < input.length) {
               while (in_array(input.charAt(parser_pos), wordchar)) {
                   c += input.charAt(parser_pos);
                   parser_pos += 1;
                   if (parser_pos === input.length) {
                       break;
                   }
               }
           }

           // small and surprisingly unugly hack for 1E-10 representation
           if (parser_pos !== input.length && c.match(/^[0-9]+[Ee]$/) && input.charAt(parser_pos) === '-') {
               parser_pos += 1;

               var t = get_next_token(parser_pos);
               c += '-' + t[0];
               return [c, 'TK_WORD'];
           }

           if (c === 'in') { // hack for 'in' operator
               return [c, 'TK_OPERATOR'];
           }
           return [c, 'TK_WORD'];
       }
       
       if (c === '(' || c === '[') {
           return [c, 'TK_START_EXPR'];
       }

       if (c === ')' || c === ']') {
           return [c, 'TK_END_EXPR'];
       }

       if (c === '{') {
           return [c, 'TK_START_BLOCK'];
       }

       if (c === '}') {
           return [c, 'TK_END_BLOCK'];
       }

       if (c === ';') {
           return [c, 'TK_END_COMMAND'];
       }

       if (c === '/') {
           var comment = '';
           // peek for comment /* ... */
           if (input.charAt(parser_pos) === '*') {
               parser_pos += 1;
               if (parser_pos < input.length) {
                   while (! (input.charAt(parser_pos) === '*' && input.charAt(parser_pos + 1) && input.charAt(parser_pos + 1) === '/') && parser_pos < input.length) {
                       comment += input.charAt(parser_pos);
                       parser_pos += 1;
                       if (parser_pos >= input.length) {
                           break;
                       }
                   }
               }
               parser_pos += 2;
               return ['/*' + comment + '*/', 'TK_BLOCK_COMMENT'];
           }
           // peek for comment // ...
           if (input.charAt(parser_pos) === '/') {
               comment = c;
               while (input.charAt(parser_pos) !== "\x0d" && input.charAt(parser_pos) !== "\x0a") {
                   comment += input.charAt(parser_pos);
                   parser_pos += 1;
                   if (parser_pos >= input.length) {
                       break;
                   }
               }
               parser_pos += 1;
               if (wanted_newline) {
                   print_newline();
               }
               return [comment, 'TK_COMMENT'];
           }

       }

       if (c === "'" || // string
       c === '"' || // string
       (c === '/' &&
       ((last_type === 'TK_WORD' && last_text === 'return') || (last_type === 'TK_START_EXPR' || last_type === 'TK_END_BLOCK' || last_type === 'TK_OPERATOR' || last_type === 'TK_EOF' || last_type === 'TK_END_COMMAND')))) { // regexp
           var sep = c;
           var esc = false;
           c = '';

           if (parser_pos < input.length) {

               while (esc || input.charAt(parser_pos) !== sep) {
                   c += input.charAt(parser_pos);
                   if (!esc) {
                       esc = input.charAt(parser_pos) === '\\';
                   } else {
                       esc = false;
                   }
                   parser_pos += 1;
                   if (parser_pos >= input.length) {
                       break;
                   }
               }

           }

           parser_pos += 1;
           if (last_type === 'TK_END_COMMAND') {
               print_newline();
           }
           return [sep + c + sep, 'TK_STRING'];
       }

       if (in_array(c, punct)) {
           while (parser_pos < input.length && in_array(c + input.charAt(parser_pos), punct)) {
               c += input.charAt(parser_pos);
               parser_pos += 1;
               if (parser_pos >= input.length) {
                   break;
               }
           }
           return [c, 'TK_OPERATOR'];
       }

       return [c, 'TK_UNKNOWN'];
   }


   //----------------------------------

   indent_character = indent_character || ' ';
   indent_size = indent_size || 4;

   indent_string = '';
   while (indent_size--) {
       indent_string += indent_character;
   }

   input = js_source_text;

   last_word = ''; // last 'TK_WORD' passed
   last_type = 'TK_START_EXPR'; // last token type
   last_text = ''; // last token text
   output = [];

   do_block_just_closed = false;
   var_line = false;
   var_line_tainted = false;

   whitespace = "\n\r\t ".split('');
   wordchar = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_$'.split('');
   punct = '+ - * / % & ++ -- = += -= *= /= %= == === != !== > < >= <= >> << >>> >>>= >>= <<= && &= | || ! !! , : ? ^ ^= |='.split(' ');

   // words which should always start on new line.
   line_starters = 'continue,try,throw,return,var,if,switch,case,default,for,while,break,function'.split(',');

   // states showing if we are currently in expression (i.e. "if" case) - 'EXPRESSION', or in usual block (like, procedure), 'BLOCK'.
   // some formatting depends on that.
   current_mode = 'BLOCK';
   modes = [current_mode];

   indent_level = indent_level || 0;
   parser_pos = 0; // parser position
   in_case = false; // flag for parser that case/default has been processed, and next colon needs special attention
   while (true) {
       var t = get_next_token(parser_pos);
       token_text = t[0];
       token_type = t[1];
       if (token_type === 'TK_EOF') {
           break;
       }

       switch (token_type) {

       case 'TK_START_EXPR':
           var_line = false;
           set_mode('EXPRESSION');
           if (last_type === 'TK_END_EXPR' || last_type === 'TK_START_EXPR') {
               // do nothing on (( and )( and ][ and ]( ..
           } else if (last_type !== 'TK_WORD' && last_type !== 'TK_OPERATOR') {
               print_space();
           } else if (in_array(last_word, line_starters) && last_word !== 'function') {
               print_space();
           }
           print_token();
           break;

       case 'TK_END_EXPR':
           print_token();
           restore_mode();
           break;

       case 'TK_START_BLOCK':
           
           if (last_word === 'do') {
               set_mode('DO_BLOCK');
           } else {
               set_mode('BLOCK');
           }
           if (last_type !== 'TK_OPERATOR' && last_type !== 'TK_START_EXPR') {
               if (last_type === 'TK_START_BLOCK') {
                   print_newline();
               } else {
                   print_space();
               }
           }
           print_token();
           indent();
           break;

       case 'TK_END_BLOCK':
           if (last_type === 'TK_START_BLOCK') {
               // nothing
               trim_output();
               unindent();
           } else {
               unindent();
               print_newline();
           }
           print_token();
           restore_mode();
           break;

       case 'TK_WORD':

           if (do_block_just_closed) {
               print_space();
               print_token();
               print_space();
               break;
           }

           if (token_text === 'case' || token_text === 'default') {
               if (last_text === ':') {
                   // switch cases following one another
                   remove_indent();
               } else {
                   // case statement starts in the same line where switch
                   unindent();
                   print_newline();
                   indent();
               }
               print_token();
               in_case = true;
               break;
           }


           prefix = 'NONE';
           if (last_type === 'TK_END_BLOCK') {
               if (!in_array(token_text.toLowerCase(), ['else', 'catch', 'finally'])) {
                   prefix = 'NEWLINE';
               } else {
                   prefix = 'SPACE';
                   print_space();
               }
           } else if (last_type === 'TK_END_COMMAND' && (current_mode === 'BLOCK' || current_mode === 'DO_BLOCK')) {
               prefix = 'NEWLINE';
           } else if (last_type === 'TK_END_COMMAND' && current_mode === 'EXPRESSION') {
               prefix = 'SPACE';
           } else if (last_type === 'TK_WORD') {
               prefix = 'SPACE';
           } else if (last_type === 'TK_START_BLOCK') {
               prefix = 'NEWLINE';
           } else if (last_type === 'TK_END_EXPR') {
               print_space();
               prefix = 'NEWLINE';
           }

           if (last_type !== 'TK_END_BLOCK' && in_array(token_text.toLowerCase(), ['else', 'catch', 'finally'])) {
               print_newline();
           } else if (in_array(token_text, line_starters) || prefix === 'NEWLINE') {
               if (last_text === 'else') {
                   // no need to force newline on else break
                   print_space();
               } else if ((last_type === 'TK_START_EXPR' || last_text === '=') && token_text === 'function') {
                   // no need to force newline on 'function': (function
                   // DONOTHING
               } else if (last_type === 'TK_WORD' && (last_text === 'return' || last_text === 'throw')) {
                   // no newline between 'return nnn'
                   print_space();
               } else if (last_type !== 'TK_END_EXPR') {
                   if ((last_type !== 'TK_START_EXPR' || token_text !== 'var') && last_text !== ':') {
                       // no need to force newline on 'var': for (var x = 0...)
                       if (token_text === 'if' && last_type === 'TK_WORD' && last_word === 'else') {
                           // no newline for } else if {
                           print_space();
                       } else {
                           print_newline();
                       }
                   }
               } else {
                   if (in_array(token_text, line_starters) && last_text !== ')') {
                       print_newline();
                   }
               }
           } else if (prefix === 'SPACE') {
               print_space();
           }
           print_token();
           last_word = token_text;

           if (token_text === 'var') {
               var_line = true;
               var_line_tainted = false;
           }

           break;

       case 'TK_END_COMMAND':

           print_token();
           var_line = false;
           break;

       case 'TK_STRING':

           if (last_type === 'TK_START_BLOCK' || last_type === 'TK_END_BLOCK') {
               print_newline();
           } else if (last_type === 'TK_WORD') {
               print_space();
           }
           print_token();
           break;

       case 'TK_OPERATOR':

           var start_delim = true;
           var end_delim = true;
           if (var_line && token_text !== ',') {
               var_line_tainted = true;
               if (token_text === ':') {
                   var_line = false;
               }
           }

           if (token_text === ':' && in_case) {
               print_token(); // colon really asks for separate treatment
               print_newline();
               break;
           }

           in_case = false;

           if (token_text === ',') {
               if (var_line) {
                   if (var_line_tainted) {
                       print_token();
                       print_newline();
                       var_line_tainted = false;
                   } else {
                       print_token();
                       print_space();
                   }
               } else if (last_type === 'TK_END_BLOCK') {
                   print_token();
                   print_newline();
               } else {
                   if (current_mode === 'BLOCK') {
                       print_token();
                       print_newline();
                   } else {
                       // EXPR od DO_BLOCK
                       print_token();
                       print_space();
                   }
               }
               break;
           } else if (token_text === '--' || token_text === '++') { // unary operators special case
               if (last_text === ';') {
                   // space for (;; ++i)
                   start_delim = true;
                   end_delim = false;
               } else {
                   start_delim = false;
                   end_delim = false;
               }
           } else if (token_text === '!' && last_type === 'TK_START_EXPR') {
               // special case handling: if (!a)
               start_delim = false;
               end_delim = false;
           } else if (last_type === 'TK_OPERATOR') {
               start_delim = false;
               end_delim = false;
           } else if (last_type === 'TK_END_EXPR') {
               start_delim = true;
               end_delim = true;
           } else if (token_text === '.') {
               // decimal digits or object.property
               start_delim = false;
               end_delim = false;

           } else if (token_text === ':') {
               // zz: xx
               // can't differentiate ternary op, so for now it's a ? b: c; without space before colon
               if (last_text.match(/^\d+$/)) {
                   // a little help for ternary a ? 1 : 0;
                   start_delim = true;
               } else {
                   start_delim = false;
               }
           }
           if (start_delim) {
               print_space();
           }

           print_token();

           if (end_delim) {
               print_space();
           }
           break;

       case 'TK_BLOCK_COMMENT':

           print_newline();
           print_token();
           print_newline();
           break;

       case 'TK_COMMENT':

           // print_newline();
           print_space();
           print_token();
           print_newline();
           break;

       case 'TK_UNKNOWN':
           print_token();
           break;
       }

       last_type = token_type;
       last_text = token_text;
   }

   return output.join('');

}

/**
 * 根据数据展示
 */
function showDataUseHandlebars(id, source, data){
	//预编译模板
	var template = Handlebars.compile(source);
	//匹配json内容
	var html = template(data);
	//输入模板
	layui.$("#" + id).html(html);
}

function getDataUseHandlebars(source, data){
	//预编译模板
	var template = Handlebars.compile(source);
	//匹配json内容
	var html = template(data);
	//输入模板
	return html;
}

var postDownLoadFile = function(options) {
	var config = layui.$.extend(true, {
		method : 'post'
	}, options);
	var $iframe = layui.$('<iframe id="down-file-iframe" />');
	var $form = layui.$('<form target="down-file-iframe" method="' + config.method + '" />');
	$form.attr('action', config.url);
	if(!isNull(config.params)){
        for (var key in config.params) {
            $form.append('<input type="hidden" name="' + key + '" value="' + config.params[key] + '" />');
        }
    }
	// 图片
	if(!isNull(config.data)){
		$form.append('<input type="hidden" name="base64Info" value="' + config.data + '" />');
	}

	$iframe.append($form);
	layui.$(document.body).append($iframe);
	$form[0].submit();
	$iframe.remove();
}

/**
 * 权限验证
 * @param urlNum
 */
function auth(urlNum){
	var authList = JSON.parse(localStorage.getItem("authpoints"));
	if(!isNull(authList)){
		for(var i = 0; i < authList.length; i++){
			if(authList[i].menuNum === urlNum){
				return true;
			}
		}
	}else{
		winui.window.msg('登录超时，即将返回登录页面.', {icon: 2,time: 2000}, function(){
			var win = window;
			while (win != win.top){
				win = win.top;
			}
			win.location.href = reqBasePath + "/tpl/index/login.html";
		});
	}
	return false;
}

function authBtn(urlNum){
	if(!auth(urlNum)){
		layui.$('[auth="' + urlNum + '"]').remove();
	}
}

/***********************************ztree节点查找开始***************************************/

/**
 * 查找子结点，如果找到，返回true，否则返回false-----ztree查询时使用
 */
function searchChildren(keyword, children) {
	if(children == null || children.length == 0) {
		return false;
	}
	for(var i = 0; i < children.length; i++) {
		var node = children[i];
		if(node.name.indexOf(keyword) != -1) {
			return true;
		}
		//递归查找子结点
		var result = searchChildren(keyword, node.children);
		if(result) {
			return true;
		}
	}
	return false;
}

/**
 * 查找当前结点和父结点，如果找到，返回ture，否则返回false
 */
function searchParent(keyword, node) {
	if(node == null) {
		return false;
	}
	if(node.name.indexOf(keyword) != -1) {
		return true;
	}
	//递归查找父结点
	return searchParent(keyword, node.getParentNode());
}
/***********************************ztree节点查找结束***************************************/

/**
 * erp单据详情获取url地址
 * @param {} data
 * @return {}
 */
function getErpDetailUrl(data){
	var url = "";
	if(data.subType == 1){//采购入库---入库
		url = "../../tpl/purchaseput/purchaseputdetails.html";
	}else if(data.subType == 4){//其他入库---入库
		url = "../../tpl/otherwarehous/otherwarehousdetails.html";
	}else if(data.subType == 2){//销售退货---入库
		url = "../../tpl/salesreturns/salesreturnsdetails.html";
	}else if(data.subType == 6){//采购退货---出库
		url = "../../tpl/purchasereturns/purchasereturnsdetails.html";
	}else if(data.subType == 9){//其他出库---出库
		url = "../../tpl/otheroutlets/otheroutletsdetails.html";
	}else if(data.subType == 5){//销售出库---出库
		url = "../../tpl/salesoutlet/salesoutletdetails.html";
	}else if(data.subType == 8){//零售出库---出库
		url = "../../tpl/retailoutlet/retailoutletdetails.html";
	}else if(data.subType == 3){//零售退货---入库
		url = "../../tpl/retailreturns/retailreturnsdetails.html";
	}else if(data.subType == 12){//拆分单---其他,一进一出
		url = "../../tpl/splitlist/splitlistdetails.html";
	}else if(data.subType == 13){//组装单---其他,一进一出
		url = "../../tpl/assemblysheet/assemblysheetdetails.html";
	}else if(data.subType == 14){//调拨单---其他,一进一出
		url = "../../tpl/allocation/allocationdetails.html";
	}else if(data.subType == 15){//验收入库单
		url = "";
	}else if(data.subType == 16){//工序验收单
		url = "";
	}else if(data.subType == 17){//加工单
		url = "../../tpl/erpMachin/erpMachinDetails.html";
	}else if(data.subType == 18){//生产计划单
		url = "../../tpl/erpProduction/erpProductionDetail.html";
	}else if(data.subType == 19){//领料单
		url = "../../tpl/erpPick/erpRequisitionDetails.html";
	}else if(data.subType == 20){//补料单
		url = "../../tpl/erpPick/erpPatchDetails.html";
	}else if(data.subType == 21){//退料单
		url = "../../tpl/erpPick/erpReturnDetails.html";
	}
	return url;
}

/**
 * echarts简单图形参数
 * @param {} title		标题
 * @param {} subtext	描述
 * @param {} xNameData	x轴数据，数组类型
 * @param {} yTitle		y轴标题
 * @param {} yNameData	y轴数据，数组类型
 * @param {} type		图形展示类型。line：折线图；bar：柱形图
 * @return {}
 */
function getOption(title, subtext, xNameData, yTitle, yNameData, type){
	return {
		color: ['#3398DB', '#FFB6C1', '#C71585', '#8B008B', '#4169E1', '#00BFFF', '#008B8B'],
		title: {
			text: title,
			x: 'center',
			subtext: subtext
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {            			// 坐标轴指示器，坐标轴触发有效
	            type: 'shadow'        			// 默认为直线，可选为：'line' | 'shadow'
	        }
		},
		toolbox: {
			show: true,							//是否显示工具栏组件
			orient: "horizontal",				//工具栏 icon 的布局朝向'horizontal' 'vertical'
			itemSize: 15,						//工具栏 icon 的大小
    		itemGap: 10,						//工具栏 icon 每项之间的间隔
    		showTitle: true,					//是否在鼠标 hover 的时候显示每个工具 icon 的标题
			feature: {
				mark: {							//'辅助线开关'
            		show: true
        		},
        		dataView : {					//数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新
		            show: true,					//是否显示该工具。
		            title: "数据视图",
		            readOnly: false,			//是否不可编辑（只读）
		            lang: ['数据视图', '关闭', '刷新'],	//数据视图上有三个话术，默认是['数据视图', '关闭', '刷新']
		            backgroundColor: "#fff",	//数据视图浮层背景色。
		            textareaColor: "#fff",		//数据视图浮层文本输入区背景色
		            textareaBorderColor: "#333",//数据视图浮层文本输入区边框颜色
		            textColor: "#000",			//文本颜色。
		            buttonColor: "#c23531",		//按钮颜色。
		            buttonTextColor: "#fff"		//按钮文本颜色。
		        },
        		magicType: {					//动态类型切换
		            show: true,
		            title: "切换",				//各个类型的标题文本，可以分别配置。
		            type: ['line', 'bar']		//启用的动态类型，包括'line'（切换为折线图）, 'bar'（切换为柱状图）, 'stack'（切换为堆叠模式）, 'tiled'（切换为平铺模式）
		        },
		        restore: {						//配置项还原。
		            show: true,					//是否显示该工具。
		            title: "还原"
		        },
		        saveAsImage: {					//保存为图片。
		            show: true,					//是否显示该工具。
		            type: "png",				//保存的图片格式。支持 'png' 和 'jpeg'。
		            name: "pic1",				//保存的文件名称，默认使用 title.text 作为名称
		            backgroundColor: "#ffffff",	//保存的图片背景色，默认使用 backgroundColor，如果backgroundColor不存在的话会取白色
		            title: "保存为图片",
		            pixelRatio: 1				//保存图片的分辨率比例，默认跟容器相同大小，如果需要保存更高分辨率的，可以设置为大于 1 的值，例如 2
		        },
		        dataZoom: {						//数据区域缩放。目前只支持直角坐标系的缩放
		            show: true,					//是否显示该工具。
		            title: "缩放",				//缩放和还原的标题文本
		            xAxisIndex: 0,				//指定哪些 xAxis 被控制。如果缺省则控制所有的x轴。如果设置为 false 则不控制任何x轴。如果设置成 3 则控制 axisIndex 为 3 的x轴。如果设置为 [0, 3] 则控制 axisIndex 为 0 和 3 的x轴
		            yAxisIndex: false			//指定哪些 yAxis 被控制。如果缺省则控制所有的y轴。如果设置为 false 则不控制任何y轴。如果设置成 3 则控制 axisIndex 为 3 的y轴。如果设置为 [0, 3] 则控制 axisIndex 为 0 和 3 的y轴
		        }
			}
		},
		xAxis: {
			type: 'category',
			axisLabel: {
				interval: 0,
				formatter: function(value) {
					var ret = ""; //拼接加\n返回的类目项  
					var maxLength = 4; //每项显示文字个数  
					var valLength = value.length; //X轴类目项的文字个数  
					var rowN = Math.ceil(valLength / maxLength); //类目项需要换行的行数  
					if(rowN > 1){ //如果类目项的文字大于3,  
						for(var i = 0; i < rowN; i++) {
							var temp = ""; //每次截取的字符串  
							var start = i * maxLength; //开始截取的位置  
							var end = start + maxLength; //结束截取的位置  
							//这里也可以加一个是否是最后一行的判断，但是不加也没有影响，那就不加吧  
							temp = value.substring(start, end) + "\n";
							ret += temp; //凭借最终的字符串  
						}
						return ret;
					} else {
						return value;
					}
				}
			},
			data: xNameData
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			name: yTitle,
			type: isNull(type) ? 'line' : type,
			smooth: true,
			data: yNameData
		}]
	};
}

// 获取今天是本月的几号
function getTodayDay(){
	var today = new Date();
	var todayDay = today.getDate();
	return todayDay;
}

// 获取本月一号的日期
function getOneYMDFormatDate(){
	 var date = new Date;
	 var year = date.getFullYear(); 
	 var month = date.getMonth() + 1;
	 month = (month < 10 ? "0" + month : month); 
	 return year.toString() + "-" + month.toString() + "-" + "01";
}

// 获取上个月一号的日期
function getTOneYMDFormatDate(){
	 var date = new Date;
	 var year = date.getFullYear(); 
	 var month = date.getMonth();
	 month = (month < 10 ? "0" + month : month); 
	 return year.toString() + "-" + month.toString() + "-" + "01";
}

//获取前一天的时间 
function getYesterdayYMDFormatDate(){
	var myDate = new Date();
    var lw = new Date(myDate - 1000 * 60 * 60 * 24 * 1);
    var lastY = lw.getFullYear();
    var lastM = lw.getMonth() + 1;
    var lastD = lw.getDate();
    return lastY + "-" + (lastM < 10 ? "0" + lastM : lastM) + "-" + (lastD < 10 ? "0" + lastD : lastD);
}

// 获取本月日期
function getOneYMFormatDate(){
	 var date = new Date;
	 var year = date.getFullYear(); 
	 var month = date.getMonth() + 1;
	 month = (month < 10 ? "0" + month : month); 
	 return year.toString() + "-" + month.toString();
}

// 移除指定value值
function removeByValue(arr, val){
	for(var i = 0; i < arr.length; i++){
		if(arr[i] == val) {
			arr.splice(i, 1);
			break;
		}
	}
}

// 取出json串的键
function getOutKey(arr){
	var jsonObj = $.parseJSON(arr);
	var a = [];
	var b = [];
	for(var i = 0; i < jsonObj.length; i++){
		for (var key in jsonObj[i])
			a.push(key); 
		b.push(a);
		a = [];
	}
	return b;
}

// B的子集是否是A的子集
function subset(A,B){
	for(var i = 0; i < B.length; i++){
		if(!isContained(B[i], A)){
			return false;
		}
	}
	return true;
}

// b是否被a包含,是返回true,不是返回false
isContained =(a, b)=>{
	if(!(a instanceof Array) || !(b instanceof Array)) 
    	return false;
    if(a.length < b.length) 
    	return false;
    var aStr = a.toString();
    for(var i = 0, len = b.length; i < len; i++){
    	if(aStr.indexOf(b[i]) == -1) 
    		return false;
    }
    return true;
}

/*****************工作计划模块开始**************/
// 获取计划周期名称
function getNowCheckTypeName(nowCheckType){
	if(nowCheckType === 'day')
		return '日计划';
	else if(nowCheckType === 'week')
		return '周计划';
	else if(nowCheckType === 'month')
		return '月计划';
	else if(nowCheckType === 'quarter')
		return '季度计划';
	else if(nowCheckType === 'halfyear')
		return '半年计划';
	else if(nowCheckType === 'year')
		return '年计划';
}
/*****************工作计划模块结束**************/

function matchingLanguage(){
	var list = layui.$("language");
	if(list.length > 0){
        $.each(list, function(i, item){
        	if(isNull($(item).html())){
	        	$(item).html(systemLanguage[$(item).attr("showName")][languageType]);
        	}
		});
	}
	list = layui.$("[matchLanguage]");
	if(list.length > 0){
        $.each(list, function(i, item){
        	try{
        		var jsonStr = $(item).attr("matchLanguage");
        		if(!isNull(jsonStr)){
        			jsonStr = jsonStr.replace(/\'/g,"\"");
	        		var _json = JSON.parse(jsonStr);
	        		$.each(_json, function(key, value){
	        			if(key === "html"){
	        				$(item).html(systemLanguage[value][languageType]);
	        			}else{
	        				$(item).attr(key, systemLanguage[value][languageType]);
	        			}
	        		});
	        		$(item).removeAttr("matchLanguage");
        		}
        	}catch(e){
        		console.error(e);
        	}
		});
	}
}

function initPasteDragImg(Editor) {
	var doc = document.getElementById(Editor.id)
	doc.addEventListener('paste', function(event) {
		var items = (event.clipboardData || window.clipboardData).items;
		var file = null;
		if(items && items.length) {
			// 搜索剪切板items
			for(var i = 0; i < items.length; i++) {
				if(items[i].type.indexOf('image') !== -1) {
					file = items[i].getAsFile();
					break;
				}
			}
		} else {
			winui.window.msg("当前浏览器不支持", {icon: 2,time: 2000});
			return;
		}
		if(!file) {
			return;
		}
		uploadImg(file, Editor);
	});

	var dashboard = document.getElementById(Editor.id)
	dashboard.addEventListener("dragover", function(e) {
		e.preventDefault()
		e.stopPropagation()
	})
	dashboard.addEventListener("dragenter", function(e) {
		e.preventDefault()
		e.stopPropagation()
	})
	dashboard.addEventListener("drop", function(e) {
		e.preventDefault()
		e.stopPropagation()
		var files = this.files || e.dataTransfer.files;
		uploadImg(files[0], Editor);
	})
}

function uploadImg(file, Editor) {
	var formData = new FormData();
	var fileName = new Date().getTime() + "." + file.name.split(".").pop();
	formData.append('editormd-image-file', file, fileName);
	$.ajax({
		url: Editor.settings.imageUploadURL,
		type: 'post',
		data: formData,
		processData: false,
		contentType: false,
		dataType: 'json',
		success: function(json) {
			if(json.returnCode == 0){
				var url = json.bean.picUrl;
				var type = url.substr(url.lastIndexOf(".") + 1);
				if($.inArray(type, imageType) >= 0){
					Editor.insertValue("![图片alt](" + url + " ''图片title'')");
				}else{
					Editor.insertValue("[下载附件](" + url + ")");
				}
			}else{
				winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
			}
		}
	});
}

/**
 * 加法
 *
 * @param num1
 * @param num2
 * @returns {string}
 */
function sum(num1, num2){
	var a1 = parseFloat(isNull(num1) ? 0 : num1);
	var a2 = parseFloat(isNull(num2) ? 0 : num2);
	return (a1 + a2).toFixed(2);
}

/**
 * 减法
 *
 * @param num1
 * @param num2
 * @returns {string}
 */
function subtraction(num1, num2){
	var a1 = parseFloat(isNull(num1) ? 0 : num1);
	var a2 = parseFloat(isNull(num2) ? 0 : num2);
	return (a1 - a2).toFixed(2);
}

/**
 * 乘法
 *
 * @param num1
 * @param num2
 * @returns {string}
 */
function multiplication(num1, num2){
	var a1 = parseFloat(isNull(num1) ? 0 : num1);
	var a2 = parseFloat(isNull(num2) ? 0 : num2);
	return (a1 * a2).toFixed(2);
}

/**
 * 除法
 *
 * @param num1
 * @param num2
 * @returns {string|number}
 */
function division(num1, num2){
	var a1 = parseFloat(isNull(num1) ? 0 : num1);
	var a2 = parseFloat(isNull(num2) ? 0 : num2);
	if(a2 == 0){
		return 0;
	}
	return (a1 / a2).toFixed(2);
}

/**
 * 获取员工状态
 * @param d 员工参数
 * @returns {string}
 */
function getStaffStateName(d){
	if(d.state == '1'){
		return "<span class='state-up'>在职</span>";
	}else if(d.state == '2'){
		return "<span class='state-down'>离职</span>";
	}else if(d.state == '3'){
		return "见习";
	}else if(d.state == '4'){
		return "试用";
	}else if(d.state == '5'){
		return "退休";
	}
}

/**
 * 判断一个值是否在指定的集合中存在
 *
 * @param array 集合
 * @param key 要比较的key
 * @param value 要比较的值
 * @returns {boolean}
 */
function judgeInPoingArr(array, key, value){
	for(var i = 0; i < array.length; i++){
		if(array[i][key] == value){
			return true;
		}
	}
	return false;
}

/**
 * 获取一个值是否在指定的集合中匹配到的值的指定key
 *
 * @param array 集合
 * @param key 要比较的key
 * @param value 要比较的值
 * @param getKey 要获取的值
 * @returns {null|*}
 */
function getInPoingArr(array, key, value, getKey){
	for(var i = 0; i < array.length; i++){
		if(array[i][key] == value){
			if(isNull(getKey)){
				return array[i];
			}
			return array[i][getKey];
		}
	}
	return null;
}

/**
 * 获取集合中一个值是否包含在指定的字符串中，如果包含，则返回
 *
 * @param array 集合
 * @param value 要比较的值
 * @returns {null|*}
 */
function getArrIndexOfPointStr(array, value){
	for(var i = 0; i < array.length; i++){
		if(value.indexOf(array[i]) > -1){
			return array[i];
		}
	}
	return "";
}

/**
 * 获取员工信息
 *
 * @param staffId 员工id
 * @returns {string}
 */
function getUserStaffHtmlMationByStaffId(staffId){
	var html = "";
	var template = getFileContent('tpl/common/userStaff/userStaffMationShowTop.tpl');
	AjaxPostUtil.request({url:reqBasePath + "staff005", params: {rowId: staffId}, type:'json', method: "GET", callback:function(json){
		if(json.returnCode == 0){
			html = getDataUseHandlebars(template, json);
		}else{
			winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
		}
	}, async: false});
	return html;
}

// 附件插件
var skyeyeEnclosure = {
	enclosureListKey: 'skyeyeJsonKey',
	enclosureBtnTemplate: '<button type="button" class="layui-btn layui-btn-primary layui-btn-xs" id="{{btnId}}">附件上传</button>',
	/**
	 * 初始化附件插件，多个使用逗号隔开，只支持id
	 *
	 * @param ids 需要初始化的附件盒子的id
	 * @param callback 回调函数
	 */
	init: function (ids, callback) {
		var idsArray = ids.split(',');
		$.each(idsArray, function (i, id) {
			// 按钮id
			var btnId = id + "Btn";
			var btnHtml = getDataUseHandlebars(skyeyeEnclosure.enclosureBtnTemplate, {btnId: btnId});
			$("#" + id).html(btnHtml);
			$("#" + id).attr(skyeyeEnclosure.enclosureListKey, JSON.stringify([]));
			skyeyeEnclosure.initClick(id, btnId, callback);
		});
	},

	/**
	 * 初始化附件插件，多个使用逗号隔开，只支持id
	 *
	 * @param param {需要初始化的附件盒子的id: 默认数据}
	 * @param callback 回调函数
	 */
	initTypeISData: function (param, callback) {
		$.each(param, function (boxId, data) {
			// 按钮id
			var btnId = boxId + "Btn";
			$("#" + boxId).attr(skyeyeEnclosure.enclosureListKey, JSON.stringify(isNull(data) ? [] : data));
			if(typeof(callback) == "function") {
				var btnHtml = getDataUseHandlebars(skyeyeEnclosure.enclosureBtnTemplate, {btnId: btnId});
				$("#" + boxId).html(btnHtml);
				callback(skyeyeEnclosure.getJSONEnclosureListByBoxId(boxId));
			}else{
				skyeyeEnclosure.loadEnclosureHTML(boxId, btnId);
			}
			skyeyeEnclosure.initClick(boxId, btnId, callback);
		});
	},

	/**
	 * 初始化点击事件
	 *
	 * @param id 盒子id
	 * @param btnId 按钮id
	 * @param callback 回调函数
	 */
	initClick: function (id, btnId, callback){
		$("body").on("click", "#" + btnId, function(){
			_openNewWindows({
				url: "../../tpl/common/enclosureupload.html?boxId=" + id,
				title: "上传附件",
				pageId: "enclosureuploadpage",
				area: ['420px', '420px'],
				callBack: function(refreshCode){
					if(typeof(callback) == "function") {
						callback(skyeyeEnclosure.getJSONEnclosureListByBoxId(id));
					}else{
						skyeyeEnclosure.loadEnclosureHTML(id, btnId);
					}
				}});
		});
	},

	/**
	 * 加载附件列表
	 *
	 * @param boxId 盒子id
	 * @param btnId 按钮id
	 */
	loadEnclosureHTML: function (boxId, btnId){
		var enclosureList = skyeyeEnclosure.getJSONEnclosureListByBoxId(boxId);
		var str = "";
		$.each(enclosureList, function(i, item){
			str += '<br><a rowid="' + item.id + '" class="enclosureItem" rowpath="' + item.fileAddress + '" href="javascript:;" style="color:blue;">' + item.name + '</a>';
		});
		var btnHtml = getDataUseHandlebars(skyeyeEnclosure.enclosureBtnTemplate, {btnId: btnId});
		$("#" + boxId).html(btnHtml + str);
	},

	/**
	 * 获取指定id的附件
	 *
	 * @param id 盒子id
	 */
	getJSONEnclosureListByBoxId: function (id){
		return [].concat(JSON.parse($("#" + id).attr(skyeyeEnclosure.enclosureListKey)));
	},

	/**
	 * 获取指定id的附件id，逗号隔开
	 *
	 * @param id 盒子id
	 */
	getEnclosureIdsByBoxId: function (id){
		var enclosureList = skyeyeEnclosure.getJSONEnclosureListByBoxId(id);
		var enclosureInfo = "";
		$.each(enclosureList, function (i, item) {
			enclosureInfo += item.id + ',';
		})
		return enclosureInfo;
	}
};

/**
 * 字符串处理
 *
 * @type {{}}
 */
var stringManipulation = {

	textAreaShow: function(str){
		// IE7-8、IE9、FF、chrome。解决textarea中输入的文字，输出到div中文字不换自动换行的问题
		return str.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>');
	}
};

/**
 * 获取指定日期是第几周
 *
 * @param sdate 日期格式yyyy-mm-dd
 * @returns {number}
 */
function weekofyear(sdate) {
	var d = new Date(sdate);
	var myYear = d.getFullYear();
	var firstDate = new Date(myYear + "-01-01");
	var dayofyear = 0;
	for (var i = 0; i < d.getMonth(); i++) {
		switch (i) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
				dayofyear += 31;
				break;
			case 1:
				if (isLeapYear(d)) {
					dayofyear += 29;
				}
				else {
					dayofyear += 28;
				}
				break;
			case 3:
			case 5:
			case 8:
			case 10:
				dayofyear += 30;
				break;
		}
	}
	dayofyear += d.getDate() + 1;
	var week = firstDate.getDay();
	var dayNum = dayofyear - (7 - week);
	var weekNum = 1;
	weekNum = weekNum + (dayNum / 7);
	if (dayNum % 7 != 0)
		weekNum = weekNum + 1;
	return parseInt(weekNum);
}

function isLeapYear(date) {
	return (0 == date.getFullYear() % 4 && ((date.getFullYear() % 100 != 0) || (date.getFullYear() % 400 == 0)));
}

/**
 * 获取指定日期是周几
 *
 * @param date 日期格式yyyy-mm-dd
 * @returns {number}
 */
function weekDay(date){
	var _date = new Date(date);
	var num = _date.getDay();
	if(num == 0){
		return 7;
	}
	return num;
}

/**
 * 计算时间差（相差分钟）
 *
 * @param startTime 开始时间，格式为HH:mm:ss
 * @param endTime 结束时间，格式为HH:mm:ss
 * @returns {number}
 */
function timeDifference(startTime, endTime){
	var start1 = startTime.split(":");
	var startAll = parseInt(start1[0] * 60) + parseInt(start1[1]);
	var end1 = endTime.split(":");
	var endAll = parseInt(end1[0] * 60) + parseInt(end1[1]);
	return endAll - startAll;
}

/**
 * 获取两个时间段重叠的时间段,参数格式为HH:mm:ss
 * @param startTime 开始时间1
 * @param endTime 结束时间1
 * @param restStartTime 开始时间2
 * @param restEndTime 结束时间2
 */
function getOverlapTime(startTime, endTime, restStartTime, restEndTime){
	var result = [];
	// 开始时间以大的为准
	if(compare_HHmmss(startTime, restStartTime)){
		result.push(startTime);
	}else{
		result.push(restStartTime);
	}
	// 结束时间以小的为准
	if(compare_HHmmss(restEndTime, endTime)){
		result.push(endTime);
	}else{
		result.push(restEndTime);
	}
	return result;
}

////////////////////////////////////////考勤班次开始//////////////////////////////////////////
var checkWorkTimeColor = ['layui-bg-gray', 'layui-bg-blue', 'layui-bg-orange'];
// 类型为1初始化单休
function resetSingleBreak(){
	$.each($(".weekDay"), function(i, item){
		var clas = getArrIndexOfPointStr(checkWorkTimeColor, $(item).attr("class"));
		$(item).removeClass(clas);
		if(i < 6){
			$(item).addClass('layui-bg-blue');
		}else{
			$(item).addClass('layui-bg-gray');
		}
	});
}

// 类型为2初始化双休
function resetWeekend(){
	$.each($(".weekDay"), function(i, item){
		var clas = getArrIndexOfPointStr(checkWorkTimeColor, $(item).attr("class"));
		$(item).removeClass(clas);
		if(i < 5){
			$(item).addClass('layui-bg-blue');
		}else{
			$(item).addClass('layui-bg-gray');
		}
	});
}

// 类型为3初始化单双休
function resetSingleAndDoubleBreak(){
	$.each($(".weekDay"), function(i, item){
		var clas = getArrIndexOfPointStr(checkWorkTimeColor, $(item).attr("class"));
		$(item).removeClass(clas);
		if(i < 5){
			$(item).addClass('layui-bg-blue');
		}else if(i == 5){
			$(item).addClass('layui-bg-orange');
		}else{
			$(item).addClass('layui-bg-gray');
		}
	});
}

// 类型为4初始化自定休
function resetCustomizeDay(days){
	resetCustomize();
	$.each(days, function(i, item){
		var _this = $("span[value='" + item.day + "']");
		var clas = getArrIndexOfPointStr(checkWorkTimeColor, _this.attr("class"));
		_this.removeClass(clas);
		if(item.type == 1){
			_this.addClass('layui-bg-blue');
		}else if(item.type == 2){
			_this.addClass('layui-bg-orange');
		}
	});
}

// 类型为4初始化自定休
function resetCustomize(){
	$.each($(".weekDay"), function(i, item){
		var clas = getArrIndexOfPointStr(checkWorkTimeColor, $(item).attr("class"));
		$(item).removeClass(clas);
		$(item).addClass('layui-bg-gray');
	});
}
////////////////////////////////////////考勤班次结束//////////////////////////////////////////

/**
 * 工作流审批状态显示颜色变更
 *
 * @param state 状态
 * @param stateName 状态中文显示
 * @returns {string}
 */
function getStateNameByState(state, stateName){
	if(state == '0'){
		stateName = "<span>" + stateName + "</span>";
	}else if(state == '1'){
		stateName = "<span class='state-new'>" + stateName + "</span>";
	}else if(state == '2'){
		stateName = "<span class='state-up'>" + stateName + "</span>";
	}else if(state == '3'){
		stateName = "<span class='state-down'>" + stateName + "</span>";
	}else if(state == '4'){
		stateName = "<span class='state-down'>" + stateName + "</span>";
	}else if(state == '5'){
		stateName = "<span class='state-error'>" + stateName + "</span>";
	}
	return stateName;
}

// ajax请求
var AjaxPostUtil = {
	// 基础选项
	options: {
		// 默认提交的方法,get post
		method: "post",
		// 请求的路径 required
		url: "",
		// 请求的参数
		params: {},
		// 默认异步
		async: true,
		// 返回的内容的类型,text,xml,json
		type: 'text',
		// 回调函数 required
		callback: function() {}
	},

	// 创建XMLHttpRequest对象
	createRequest: function() {
		var xmlhttp;
		try {
			// IE6以上版本
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e) {
			try {
				// IE6以下版本
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch(e) {
				try {
					xmlhttp = new XMLHttpRequest();
					if(xmlhttp.overrideMimeType) {
						xmlhttp.overrideMimeType("text/xml");
					}
				} catch(e) {
					alert("您的浏览器不支持Ajax");
				}
			}
		}
		return xmlhttp;
	},
	// 设置基础选项
	setOptions: function(newOptions) {
		for(var pro in newOptions) {
			this.options[pro] = newOptions[pro];
		}
	},
	// 格式化请求参数
	formateParameters: function() {
		var paramsArray = [];
		var params = this.options.params;
		for(var pro in params) {
			var paramValue = params[pro];
			paramsArray.push(pro + "=" + paramValue);
		}
		paramsArray.push("userToken=" + getCookie('userToken'));
		paramsArray.push("loginPCIp=" + returnCitySN["cip"]);
		return paramsArray.join("&");
	},

	// 状态改变的处理
	readystatechange: function(xmlhttp) {
		// 获取返回值
		var returnValue;
		if(xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
			//移除请求遮罩层
			layui.$("body").find(".mask-req-str").remove();
			var sessionstatus = xmlhttp.getResponseHeader("SESSIONSTATUS");
			var requestmation = xmlhttp.getResponseHeader("REQUESTMATION");
			if (sessionstatus == "TIMEOUT") {//超时跳转
				var win = window;
				while (win != win.top){
					win = win.top;
				}
				win.location.href = reqBasePath + "/tpl/index/login.html";//XMLHttpRequest.getResponseHeader("CONTEXTPATH");
			}else if(sessionstatus == "NOAUTHPOINT"){
				returnValue = eval('(' + '{"returnMessage":"您不具备该权限。","returnCode":-9999,"total":0,"rows":"","bean":""}' + ')');
			}
			switch(this.options.type) {
				case "xml":
					returnValue = xmlhttp.responseXML;
					break;
				case "json":
					var jsonText = xmlhttp.responseText;
					if(requestmation == 'DOWNLOAD'){
						returnValue = eval('(' + '{"returnMessage":"成功","returnCode":0,"total":0,"rows":"","bean":""}' + ')');
					}else{
						if(jsonText) {
							returnValue = eval("(" + jsonText + ")");
						}
					}
					break;
				default:
					returnValue = xmlhttp.responseText;
					break;
			}
			if(returnValue) {
				this.options.callback.call(this, returnValue);
			} else {
				this.options.callback.call(this);
			}
		}
	},

	// 发送Ajax请求
	request: function(options) {
		$("body").append(maskReqStr);
		var ajaxObj = this;
		// 设置参数
		ajaxObj.setOptions.call(ajaxObj, options);
		// 创建XMLHttpRequest对象
		var xmlhttp = ajaxObj.createRequest.call(ajaxObj);
		// 设置回调函数
		xmlhttp.onreadystatechange = function() {
			ajaxObj.readystatechange.call(ajaxObj, xmlhttp);
		};
		// 格式化参数
		var formateParams = ajaxObj.formateParameters.call(ajaxObj);
		// 请求的方式
		var method = ajaxObj.options.method;
		var url = ajaxObj.options.url;
		if("GET" === method.toUpperCase()) {
			url += "?" + formateParams;
		}else if("DELETE" === method.toUpperCase()){
			url += "?_method=" + method.toUpperCase();
		}
		// 建立连接
		/**
		 * 同步：提交请求->等待服务器处理->处理完毕返回 这个期间客户端浏览器不能干任何事
		 * 异步: 请求通过事件触发->服务器处理（这是浏览器仍然可以作其他事情）->处理完毕
		 */
		xmlhttp.open(method, url, ajaxObj.options.async);//异步
		if("GET" === method.toUpperCase()) {
			xmlhttp.send(null);
		} else if("POST" === method.toUpperCase() || "PUT" === method.toUpperCase()) {
			// 如果是POST提交，设置请求头信息
			xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp.send(formateParams);
		} else if("DELETE" === method.toUpperCase()){
			// 如果是POST提交，设置请求头信息
			xmlhttp.setRequestHeader("Content-Type", "application/json");
			xmlhttp.send(formateParams);
		}
	}
};

function returnModel(lang){
	var mode = '';
	switch (lang) {
		case 'Java':
			mode = 'text/x-java';
			break;
		case 'C/C++':
			mode = 'text/x-c++src';
			break;
		case 'Objective-C':
			mode = '';
			break;
		case 'Scala':
			mode = 'text/x-scala';
			break;
		case 'Kotlin':
			mode = 'text/x-kotlin';
			break;
		case 'Ceylon':
			mode = 'text/x-ceylon';
			break;
		case 'xml':
			mode = 'xml';
			break;
		case 'html':
			mode = 'xml';
			break;
		case 'css':
			mode = 'text/css';
			break;
		case 'htmlmixed':
			mode = 'htmlmixed';
			break;
		case 'htmlhh':
			mode = 'htmlmixed';
			break;
		case 'javascript':
			mode = 'text/javascript';
			break;
		case 'nginx':
			mode = 'text/x-nginx-conf';
			break;
		case 'solr':
			mode = 'text/x-solr';
			break;
		case 'sql':
			mode = 'text/x-sql';
			break;
		case 'vue':
			mode = 'text/x-vue';
			break;
	}
	return mode;
}

/**
 * 替换代码生成器模板内容
 * @param str
 */
function replaceModelContent(str, ControllerPackageName, ServicePackageName, ServiceImplPackageName,
							 DaoPackageName, tableZhName, tableFirstISlowerName, tableISlowerName, tableBzName){
	str = str.replace(/[$]{{controllerPackage}}/g, ControllerPackageName);
	str = str.replace(/[$]{{servicePackage}}/g, ServicePackageName);
	str = str.replace(/[$]{{serviceImplPackage}}/g, ServiceImplPackageName);
	str = str.replace(/[$]{{daoPackage}}/g, DaoPackageName);
	str = str.replace(/[$]{{tableName}}/g, tableZhName);
	str = str.replace(/[$]{{objectName}}/g, tableFirstISlowerName);
	str = str.replace(/[$]{{urlName}}/g, tableISlowerName);
	str = str.replace(/[$]{{notesName}}/g, tableBzName);
	return str;
}

function show(_object, url) {
	if (imageType.indexOf(url.substring(url.lastIndexOf(".") + 1).toLowerCase()) < 0) {
		window.open(url);
		return false;
	}

	var imgs = [];
	if(layui.$.isPlainObject(_object)){
		imgs = _object.find("input[type='hidden'][name='upload']").val().split(",");
	}else{
		imgs = layui.$(_object).find("input[type='hidden'][name='upload']").val().split(",");
	}
	showPicDisk(imgs);
}

/**
 * 展示图片,支持多张图片切换展示
 * @param {} imgs
 */
function showPicDisk(imgs){
	var data = [];
	layui.$.each(imgs, function (k, v) {
		var suffix = v.substring(v.lastIndexOf(".") + 1);
		if (imageType.indexOf(suffix.toLowerCase()) > -1) {
			var json = {
				"alt": "",
				"pid": k, //图片id
				"src": v, //原图地址
				"thumb": "" //缩略图地址
			}
			data.push(json);
		}
	})

	layer.photos({
		photos: {
			"title": "", //相册标题
			"id": 123, //相册id
			"start": 0, //初始显示的图片序号，默认0
			"data": data
		}, //格式见API文档手册页
		anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
	});
}

/**
 * 时间戳格式化函数
 * @param  {string} format    格式
 * @param  {int}    timestamp 要格式化的时间 默认为当前时间
 * @return {string}           格式化的时间字符串
 */
function date(format, timestamp){
	var a, jsdate=((timestamp) ? new Date(timestamp*1000) : new Date());
	var pad = function(n, c){
		if((n = n + "").length < c){
			return new Array(++c - n.length).join("0") + n;
		} else {
			return n;
		}
	};
	var txt_weekdays = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
	var txt_ordin = {1:"st", 2:"nd", 3:"rd", 21:"st", 22:"nd", 23:"rd", 31:"st"};
	var txt_months = ["", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
	var f = {
		d: function(){return pad(f.j(), 2)},
		D: function(){return f.l().substr(0,3)},
		j: function(){return jsdate.getDate()},
		l: function(){return txt_weekdays[f.w()]},
		N: function(){return f.w() + 1},
		S: function(){return txt_ordin[f.j()] ? txt_ordin[f.j()] : 'th'},
		w: function(){return jsdate.getDay()},
		z: function(){return (jsdate - new Date(jsdate.getFullYear() + "/1/1")) / 864e5 >> 0},
		W: function(){
			var a = f.z(), b = 364 + f.L() - a;
			var nd2, nd = (new Date(jsdate.getFullYear() + "/1/1").getDay() || 7) - 1;
			if(b <= 2 && ((jsdate.getDay() || 7) - 1) <= 2 - b){
				return 1;
			} else{
				if(a <= 2 && nd >= 4 && a >= (6 - nd)){
					nd2 = new Date(jsdate.getFullYear() - 1 + "/12/31");
					return date("W", Math.round(nd2.getTime()/1000));
				} else{
					return (1 + (nd <= 3 ? ((a + nd) / 7) : (a - (7 - nd)) / 7) >> 0);
				}
			}
		},
		F: function(){return txt_months[f.n()]},
		m: function(){return pad(f.n(), 2)},
		M: function(){return f.F().substr(0,3)},
		n: function(){return jsdate.getMonth() + 1},
		t: function(){
			var n;
			if( (n = jsdate.getMonth() + 1) == 2 ){
				return 28 + f.L();
			} else{
				if( n & 1 && n < 8 || !(n & 1) && n > 7 ){
					return 31;
				} else{
					return 30;
				}
			}
		},
		L: function(){var y = f.Y();return (!(y & 3) && (y % 1e2 || !(y % 4e2))) ? 1 : 0},
		Y: function(){return jsdate.getFullYear()},
		y: function(){return (jsdate.getFullYear() + "").slice(2)},
		a: function(){return jsdate.getHours() > 11 ? "pm" : "am"},
		A: function(){return f.a().toUpperCase()},
		B: function(){
			var off = (jsdate.getTimezoneOffset() + 60)*60;
			var theSeconds = (jsdate.getHours() * 3600) + (jsdate.getMinutes() * 60) + jsdate.getSeconds() + off;
			var beat = Math.floor(theSeconds/86.4);
			if (beat > 1000) beat -= 1000;
			if (beat < 0) beat += 1000;
			if ((String(beat)).length == 1) beat = "00"+beat;
			if ((String(beat)).length == 2) beat = "0"+beat;
			return beat;
		},
		g: function(){return jsdate.getHours() % 12 || 12},
		G: function(){return jsdate.getHours()},
		h: function(){return pad(f.g(), 2)},
		H: function(){return pad(jsdate.getHours(), 2)},
		i: function(){return pad(jsdate.getMinutes(), 2)},
		s: function(){return pad(jsdate.getSeconds(), 2)},
		O: function(){
			var t = pad(Math.abs(jsdate.getTimezoneOffset()/60*100), 4);
			if (jsdate.getTimezoneOffset() > 0) t = "-" + t; else t = "+" + t;
			return t;
		},
		P: function(){var O = f.O();return (O.substr(0, 3) + ":" + O.substr(3, 2))},
		c: function(){return f.Y() + "-" + f.m() + "-" + f.d() + "T" + f.h() + ":" + f.i() + ":" + f.s() + f.P()},
		U: function(){return Math.round(jsdate.getTime()/1000)}
	};

	return format.replace(/[\\]?([a-zA-Z])/g, function(t, s){
		if( t!=s ){
			ret = s;
		} else if( f[s] ){
			ret = f[s]();
		} else{
			ret = s;
		}
		return ret;
	});
}

/**
 * 获取当前时间
 * @returns {String}
 */
function getFormatDate(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
	var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();
	var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
	var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
	return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
}

/**
 * 获取随机值
 * @return {}
 */
function getRandomValueToString(){
	return Date.parse(new Date()) + "" + getRandom(999);
}

/**
 * 生成指定位数的随机整数
 * @param {} n
 * @return {}
 */
function getRandom(n){
	return Math.floor(Math.random() * n + 1);
}

/**
 * 获取当前的年月日
 * @returns {String}
 */
function getYMDFormatDate(){
	var nowDate = new Date();
	var year = nowDate.getFullYear();
	var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
	var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
	return year + "-" + month + "-" + date;
}

/**
 * 获取当前的时分秒
 * @returns {String}
 */
function getHMSFormatDate(){
	var nowDate = new Date();
	var hour = nowDate.getHours()< 10 ? "0" + nowDate.getHours() : nowDate.getHours();
	var minute = nowDate.getMinutes()< 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
	var second = nowDate.getSeconds()< 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
	return hour + ":" + minute + ":" + second;
}

/**
 * 获取当前时间30天之前的日期
 * @returns
 */
function getThirdDayToDate(){
	var myDate = new Date();
	//获取三十天前日期
	var lw = new Date(myDate - 1000 * 60 * 60 * 24 * 30);//最后一个数字30可改，30天的意思
	var lastY = lw.getFullYear();
	var lastM = lw.getMonth() + 1;
	var lastD = lw.getDate();
	return lastY + "-" + (lastM < 10 ? "0" + lastM : lastM) + "-" + (lastD < 10 ? "0" + lastD : lastD);//三十天之前日期
}

/**
 * 比较时间大小-时分秒
 * @param a
 * @param b
 */
function compare_hms(a, b){
	var c = new Date(a);
	var d = new Date(b);
	var i = c.getHours() * 60 * 60 + c.getMinutes() * 60 + c.getSeconds();
	var n = d.getHours() * 60 * 60 + d.getMinutes() * 60 + d.getSeconds();
	if(i > n){
		return true;
	}else if(i < n){
		return false;
	}else{
		return true;
	}
}

/**
 * 比较时间大小-时分秒，格式为HH:mm:ss,参数a大于参数b返回true
 * @param a
 * @param b
 */
function compare_HHmmss(a, b){
	var array1 = a.split(":");
	var total1 = array1[0] * 3600 + array1[1] * 60 + array1[2];
	var array2 = b.split(":");
	var total2 = array2[0] * 3600 + array2[1] * 60 + array2[2];
	return total1 - total2 > 0 ? true : false;
}

/**
 * 年月日时分秒转时分
 * @param a
 * @returns {String}
 */
function hms2hm(a){
	var d = new Date(Date.parse(a.replace(/-/g, "/")));
	var i = d.getHours() + ":" + d.getMinutes();
	return i;
}

/**
 * 根据年月日获取周几
 * @param a
 * @returns {String}
 */
function getMyDay(date){
	var week;
	if(date.getDay()==0) week="周日"
	if(date.getDay()==1) week="周一"
	if(date.getDay()==2) week="周二"
	if(date.getDay()==3) week="周三"
	if(date.getDay()==4) week="周四"
	if(date.getDay()==5) week="周五"
	if(date.getDay()==6) week="周六"
	return week;
}

/**
 * 获取今天是周几
 * @returns {String}
 */
function getThisWeekDay(){
	var date = new Date();
	if(date.getDay() == 0){
		return 7
	}
	return date.getDay();
}


/*
 *   功能:实现VBScript的DateAdd功能.
 *   参数:interval,字符串表达式，表示要添加的时间间隔.
 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
 *   参数:date,时间对象.
 *   返回:新的时间对象.
 *   var now = new Date();
 *   var newDate = DateAdd( "d", 5, now);
 *---------------   DateAdd(interval,number,date)   -----------------
 */
function DateAdd(interval, number, date) {
	switch (interval) {
		case "y": {
			date.setFullYear(date.getFullYear() + number);
			return date;
			break;
		}
		case "q": {
			date.setMonth(date.getMonth() + number * 3);
			return date;
			break;
		}
		case "m": {
			date.setMonth(date.getMonth() + number);
			return date;
			break;
		}
		case "w": {
			date.setDate(date.getDate() + number * 7);
			return date;
			break;
		}
		case "d": {
			date.setDate(date.getDate() + number);
			return date;
			break;
		}
		case "h": {
			date.setHours(date.getHours() + number);
			return date;
			break;
		}
		case "m": {
			date.setMinutes(date.getMinutes() + number);
			return date;
			break;
		}
		case "s": {
			date.setSeconds(date.getSeconds() + number);
			return date;
			break;
		}
		default: {
			date.setDate(date.getDate() + number);
			return date;
			break;
		}
	}
}

Date.prototype.format = function(format) {
	/*
	 * 使用例子:format="yyyy-MM-dd hh:mm:ss";
	 */
	var o = {
		"M+": this.getMonth() + 1, // month
		"d+": this.getDate(), // day
		"h+": this.getHours(), // hour
		"m+": this.getMinutes(), // minute
		"s+": this.getSeconds(), // second
		"q+": Math.floor((this.getMonth() + 3) / 3), // quarter
		"S": this.getMilliseconds()
		// millisecond
	}

	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 -
			RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ?
				o[k] :
				("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/**
 * 获取两个日期之间的日期，不包括头和尾
 * @param begin
 * @param end
 */
function getAll(begin, end) {
	var ab = begin.split("-");
	var ae = end.split("-");
	var db = new Date();
	db.setUTCFullYear(ab[0], ab[1] - 1, ab[2]);
	var de = new Date();
	de.setUTCFullYear(ae[0], ae[1] - 1, ae[2]);
	var unixDb = db.getTime();
	var unixDe = de.getTime();
	var thisArray = new Array();
	for(var k = unixDb + 24 * 60 * 60 * 1000; k < unixDe;) {
		thisArray.push((new Date(parseInt(k))).format('yyyy-MM-dd'));
		k = k + 24 * 60 * 60 * 1000;
	}
	return thisArray;
}

/**
 * 获取长度为len的随机字符串
 * @param len
 * @returns {String}
 */
function _getRandomString(len) {
	len = len || 32;
	var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
	var maxPos = $chars.length;
	var pwd = '';
	for (i = 0; i < len; i++) {
		pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
	}
	return pwd;
}

/**
 * 全屏
 */
function fullScreen() {
	var docElm = document.documentElement;
	//W3C
	if(docElm.requestFullscreen) {
		docElm.requestFullscreen();
	}
	//FireFox
	else if(docElm.mozRequestFullScreen) {
		docElm.mozRequestFullScreen();
	}
	//Chrome等
	else if(docElm.webkitRequestFullScreen) {
		docElm.webkitRequestFullScreen();
	}
	//IE11
	else if(docElm.msRequestFullscreen) {
		document.body.msRequestFullscreen();
	}
}

/**
 * 禁用全屏
 */
function exitFullScreen(){
	if (document.fullscreenElement) {
		if(document.exitFullscreen) {
			document.exitFullscreen();
		} else if(document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if(document.webkitCancelFullScreen) {
			document.webkitCancelFullScreen();
		} else if(document.msExitFullscreen) {
			document.msExitFullscreen();
		}
	}
}

/**
 * 判断是否是url
 * @param URL
 * @returns {Boolean}
 */
function checkURL(URL) {
	var reg= /(https?|http|ftp|file):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/g;
	URL = URL.match(reg);
	if(isNull(URL) || URL.length == 0){
		return false;
	}else{
		return true;
	}
}

/**
 * 获取 blob
 * @param  {String} url 目标文件地址
 * @return {cb}
 */
function getBlob(url,cb) {
	var xhr = new XMLHttpRequest();
	xhr.open('GET', url, true);
	xhr.responseType = 'blob';
	xhr.onload = function() {
		if (xhr.status === 200) {
			cb(xhr.response);
		}
	};
	xhr.send();
}

/**
 * 保存
 * @param  {Blob} blob
 * @param  {String} filename 想要保存的文件名称
 */
function saveAs(blob, filename) {
	if (window.navigator.msSaveOrOpenBlob) {
		navigator.msSaveBlob(blob, filename);
	} else {
		var link = document.createElement('a');
		var body = document.querySelector('body');
		link.href = window.URL.createObjectURL(blob);
		link.download = filename;
		// fix Firefox
		link.style.display = 'none';
		body.appendChild(link);
		link.click();
		body.removeChild(link);
		window.URL.revokeObjectURL(link.href);
	};
}

/**
 * 下载
 * @param  {String} url 目标文件地址
 * @param  {String} filename 想要保存的文件名称
 */
function download(url, filename) {
	getBlob(url, function(blob) {
		saveAs(blob, filename);
	});
};

//下载图片
function downloadImage(path,imgName) {
	var _OBJECT_URL;
	var request = new XMLHttpRequest();
	request.addEventListener('readystatechange', function (e) {
		if (request.readyState == 4) {
			_OBJECT_URL = URL.createObjectURL(request.response);
			var $a = $("<a></a>").attr("href", _OBJECT_URL).attr("download", imgName);
			$a[0].click();
		}
	});
	request.responseType = 'blob';
	request.open('get', path);
	request.send();
}

// 判断是否是json
function isJsonFormat(str) {
	try {
		layui.$.parseJSON(str);
	} catch(e) {
		return false;
	}
	return true;
}

/**
 * 判断str在strs中是否存在，strs的数据格式为'folder-item,select'
 * @param strs
 * @param str
 */
function judgeStrInStrs(strs, str){
	if(!isNull(strs) && !isNull(str)){
		var ss = strs.split(',');
		var strIndex = -1;
		layui.$.each(ss, function(i, item){
			if(str === item){
				strIndex = i;
				return false;
			}
		});
		return strIndex;
	}
	return 0;
}

/**
 * 对象数组根据某个字段进行排序
 * @param order 'desc':'降序', 'asc':'升序'
 * @param sortBy 排序字段
 */
function getSortFun(order, sortBy) {
	var ordAlpah = (order == 'asc') ? '>' : '<';
	var sortFun = new Function('a', 'b', 'return a.' + sortBy + ordAlpah + 'b.' + sortBy + '?1:-1');
	return sortFun;
}

/**
 * 判断字符串是否是json字符串
 * @param str
 */
function isJSON(str) {
	if (typeof str == 'string') {
		try {
			var obj = JSON.parse(str);
			if(typeof obj == 'object' && obj ){
				return true;
			}else{
				return false;
			}
		} catch(e) {
			return false;
		}
	}else{
		return false;
	}
}

/**
 * 取出字符串str中以startCode开始，以endCode结束的所有字符串
 * @param str
 * @param startCode
 * @param endCode
 */
function strMatchAllByTwo(str, startCode, endCode){
	if(str.length > 0){
		var arr = [];
		var firstStart = str.indexOf(startCode);
		var firstEnd = str.indexOf(endCode);
		arr.push(str.substr(firstStart + startCode.length, firstEnd - firstStart - endCode.length));
		return arr.concat(strMatchAllByTwo(str.substr(firstEnd + endCode.length), startCode, endCode));
	}else{
		return [];
	}
}
