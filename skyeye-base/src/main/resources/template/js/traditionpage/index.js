
var active;

layui.config({
    base: basePath, //指定 winui 路径
    version: skyeyeVersion
}).extend({  //指定js别名
    window: 'js/winui.window'
}).define(['window', 'radialin', 'form', 'element', 'tautocomplete'], function (exports) {
	
    var $ = layui.jquery,
    	form = layui.form,
    	element = layui.element;
    
    $(document).attr("title", 'skyeye云系列');
    $(".tradition-left-top").find("span").html('skyeye云系列');
    
    //顶部桌面模板
	var desktopTemplate = $("#desktopTemplate").html();
	//菜单盒子模板
	var menuBoxTemplate = $("#menuBoxTemplate").html();
	//二级菜单模板
	var menuMoreTemplate = $("#menuMoreTemplate").html();
	//一级菜单模板
	var menuTemplate = $("#menuTemplate").html();
	
    $(function () {
    	//获取用户信息
    	AjaxPostUtil.request({url:reqBasePath + "login002", params:{}, type:'json', callback:function(json){
   			if(json.returnCode == 0){
   				$("#userPhoto").attr("src", fileBasePath + json.bean.userPhoto);
    			$("#userName").html(json.bean.userCode + '(' + json.bean.userName + ')');
   				
    			winui.window.open({
		            id: '公告',
		            type: 1,
		            title: '公告',
		            content: '<p style="padding:20px;">您的支持是对我们最大的动力！！！！！！^_^</p>'
		            			+'<img src="1.png" style="width: 350px;float: left;margin-left: 10px;"/>'
		            			+'<img src="2.png" style="width: 350px;float: right;margin-right: 10px;"/>',
		            area: ['800px', '600px']
		        });
    			
   				//加载首页
   				initDefaultPage();
   				//加载菜单数据
   				loadMenuListToShow();
    		} else {
    			location.href = "login.html";
    		}
   		}});
    });
    
    //搜索自动补充数据来源
    var data = new Array();
    
    //加载菜单数据
    function loadMenuListToShow(){
    	var str = "";//顶部桌面字符串
    	var menuBoxStr = "";//多个菜单的字符串
    	var jsonStr = {};
    	jsonStr = {
			bean: {
				id: 'winfixedpage00000000',
				name: '默认桌面',
				show: 'block',
				chooseDeskTop: ' select'
			}
		};
    	str += getDataUseHandlebars(desktopTemplate, jsonStr);
    	menuBoxStr += getDataUseHandlebars(menuBoxTemplate, jsonStr);
    	$(".desktop-menu-box").find("ul").html(str);
    	$("#sysMenuListBox").html(menuBoxStr);
    	
    	//重新计算头部宽度
		initDeskTopMenuBox();
		
		//加载菜单
		AjaxPostUtil.request({url:reqBasePath + "login004", params:{}, type:'json', callback:function(json){
   			if(json.returnCode == 0){
   				var menuStr;
   				$.each(json.rows, function(i, row){
   					menuStr = "";
   					if(row.menuIconType === '1'){//icon
   						row.icon = '<i class="fa ' + row.icon + ' fa-fw"></i>';
   					}else if(row.menuIconType === '2'){//图片
   						row.icon = '<img src="' + fileBasePath + row.menuIconPic + '" />';
   					}
   					if(row.pageURL != '--'){
   						//一级菜单
   						menuStr = getDataUseHandlebars(menuTemplate, {bean: row});
   						if(isNull(row.deskTopId)){
   							$("ul[menurowid='winfixedpage00000000']").append(menuStr);
   						}else{
   							$("ul[menurowid='" + row.deskTopId + "']").append(menuStr);
   						}
   						data.push({id: row.id, name: row.name, pageURL: row.pageURL, winName: isNull(row.deskTopId) ? "默认桌面" : $(".desktop-menu-box").find("li[rowid='" + row.deskTopId + "']").find('span').html()});
   					}else{
   						//二级菜单
   						if(!isNull(row.childs)){
	   						$.each(row.childs, function(j, child){
	   							if(child.menuIconType === '1'){//icon
	   								child.icon = '<i class="fa ' + child.icon + ' fa-fw"></i>';
			   					}else if(child.menuIconType === '2'){//图片
			   						child.icon = '<img src="' + fileBasePath + child.menuIconPic + '" />';
			   					}
			   					data.push({id: child.id, name: child.name, pageURL: child.pageURL, winName: isNull(row.deskTopId) ? "默认桌面" : $(".desktop-menu-box").find("li[rowid='" + row.deskTopId + "']").find('span').html()});
	   						});
   						}
   						menuStr = getDataUseHandlebars(menuMoreTemplate, {bean: row});
   						if(isNull(row.deskTopId)){
   							$("ul[menurowid='winfixedpage00000000']").append(menuStr);
   						}else{
   							$("ul[menurowid='" + row.deskTopId + "']").append(menuStr);
   						}
   					}
   				});
   				
   				var text2 = $("#Text2").tautocomplete({
					width: "500px",
					placeholder: $("#Text2").attr("placeholder"),
					columns: [{field: 'name', title: '菜单名称'}, {field: 'winName', title: '所属桌面'}],
					data: function() {
						var filterData = [];
						var searchData = eval("/" + text2.searchdata() + "/gi");
						$.each(data, function(i, v) {
							if(v.name.search(new RegExp(searchData)) != -1) {
								filterData.push(v);
							}
						});
						return filterData;
					},
					onchange: function() {
						if(!isNull(text2.id())){
							var dataid = $("#sysMenuListBox").find("a[data-id='" + text2.id() + "']");
					    	//这时会判断右侧#LAY_app_tabsheader属性下的有lay-id属性的li的数目，即已经打开的tab项数目
					    	if($("#LAY_app_tabsheader li[lay-id]").length <= 0) {
					    		//如果比零小，则直接打开新的tab项
					    		active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
					    	} else {
					    		//否则判断该tab项是否以及存在
					    		var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
					    		$.each($("#LAY_app_tabsheader li[lay-id]"), function() {
					    			//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
					    			if($(this).attr("lay-id") === dataid.attr("data-id")) {
					    				isData = true;
					    			}
					    		})
					    		if(isData == false) {
					    			//标志为false 新增一个tab项
					    			active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
					    		}
					    	}
					    	//最后不管是否新增tab，最后都转到要打开的选项页面上
					    	active.tabChange(dataid.attr("data-id"));
						}
					}
				});
    		} else {
    			winui.window.msg(json.returnMessage, {shift: 6});
    		}
   		}});
    }
    
    //计算头部宽度
    function initDeskTopMenuBox(){
    	var items = $(".desktop-menu-box").find('li');
    	var maxWidth = 0;
    	$.each(items, function(i, item){
    		maxWidth += $(item).width();
    	});
    	$(".desktop-menu-box").find('ul').css({'width': maxWidth + 'px'});
    }
    
	//触发事件
    active = {
    	//在这里给active绑定几项事件，后面可通过active调用这些事件
    	tabAdd: function(url, id, name) {
    		//新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
    		//关于tabAdd的方法所传入的参数可看layui的开发文档中基础方法部分
    		element.tabAdd('menubox', {
    			title: name,
    			content: '<iframe data-frameid="' + id + '" scrolling="auto" frameborder="0" src="' + url + '"></iframe>',
    			id: id //规定好的id
    		})
    		CustomRightClick(id); //给tab绑定右击事件
    	},
    	tabChange: function(id) {
    		//切换到指定Tab项
    		element.tabChange('menubox', id); //根据传入的id传入到指定的tab项
    	},
    	tabDelete: function(id) {
    		element.tabDelete("menubox", id); //删除
    	},
    	tabDeleteAll: function(ids) { //删除所有
    		$.each(ids, function(i, item) {
    			element.tabDelete("menubox", item); //ids是一个数组，里面存放了多个id，调用tabDelete方法分别删除
    		})
    	}
    };

    //当点击有page-item-click属性的标签时，即左侧菜单栏中内容 ，触发点击事件
    $("body").on("click", ".page-item-click", function(e){
    	var dataid = $(this);
    	if("win" === dataid.attr("data-type")){
    		window.open(dataid.attr("data-url"));
    	}else{
    		//这时会判断右侧#LAY_app_tabsheader属性下的有lay-id属性的li的数目，即已经打开的tab项数目
	    	if($("#LAY_app_tabsheader li[lay-id]").length <= 0) {
	    		//如果比零小，则直接打开新的tab项
	    		active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
	    	} else {
	    		//否则判断该tab项是否以及存在
	    		var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
	    		$.each($("#LAY_app_tabsheader li[lay-id]"), function() {
	    			//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
	    			if($(this).attr("lay-id") === dataid.attr("data-id")) {
	    				isData = true;
	    			}
	    		})
	    		if(isData == false) {
	    			//标志为false 新增一个tab项
	    			active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
	    		}
	    	}
	    	//最后不管是否新增tab，最后都转到要打开的选项页面上
	    	active.tabChange(dataid.attr("data-id"));
    	}
    });

    //右键事件
    function CustomRightClick(id) {
    	//取消右键  rightmenu属性开始是隐藏的 ，当右击的时候显示，左击的时候隐藏
    	$('#LAY_app_tabsheader li').on('contextmenu', function() {
    		return false;
    	})
    	$('#LAY_app_tabsheader, #LAY_app_tabsheader li').click(function() {
    		$('.rightmenu').hide();
    	});
    	//桌面点击右击 
    	$('#LAY_app_tabsheader li').on('contextmenu', function(e) {
    		var popupmenu = $(".rightmenu");
    		popupmenu.find("li").attr("data-id", id); //在右键菜单中的标签绑定id属性
    		//判断右侧菜单的位置 
    		l = ($(document).width() - e.clientX) < popupmenu.width() ? (e.clientX - popupmenu.width()) : e.clientX;
    		t = ($(document).height() - e.clientY) < popupmenu.height() ? (e.clientY - popupmenu.height()) : e.clientY;
    		popupmenu.css({
    			left: l,
    			top: t
    		}).show(); //进行绝对定位
    		return false;
    	});
    }
    
    //加载初始化界面-首页
    function initDefaultPage(){
    	active.tabAdd("../../tpl/mainPage/mainPage.html", "initDefaultPageId", '<i class="layui-icon layui-icon-home"></i>');
    	active.tabChange("initDefaultPageId");
    }
    
    //菜单点击
    $("body").on("click", ".menu-box-none", function(e){
    	if($(this).parent().hasClass("layui-nav-itemed")){
    		$(this).parent().removeClass("layui-nav-itemed");
    	}else{
    		$(this).parent().addClass("layui-nav-itemed");
    	}
    });

    //关闭操作
    $("body").on("click", ".rightmenu li, .right-close-operator dd a", function(e){
    	//右键菜单中的选项被点击之后，判断type的类型，决定关闭所有还是关闭当前。
    	if($(this).attr("data-type") == "closethis") {
    		var choosePage = $("#LAY_app_tabsheader li[class='layui-this']").attr('lay-id');
    		if(isNull(choosePage)){
    			winui.window.msg('请选中当前标签页~', {shift: 6});
    			return;
    		}
    		//如果关闭当前，即根据显示右键菜单时所绑定的id，执行tabDelete
    		if(choosePage != 'initDefaultPageId'){
    			active.tabDelete(choosePage);
    		}else{
    			winui.window.msg('首页不能关闭~', {shift: 6});
    		}
    	} else if($(this).attr("data-type") == "closeall") {
    		var tabtitle = $("#LAY_app_tabsheader li");
    		var ids = new Array();
    		$.each(tabtitle, function(i) {
    			if($(this).attr("lay-id") != 'initDefaultPageId'){
    				ids.push($(this).attr("lay-id"));
    			}
    		})
    		//如果关闭所有 ，即将所有的lay-id放进数组，执行tabDeleteAll
    		active.tabDeleteAll(ids);
    	} else if($(this).attr("data-type") == "closeother") {//关闭其他标签页
    		var choosePage = $("#LAY_app_tabsheader li[class='layui-this']").attr('lay-id');
    		if(isNull(choosePage)){
    			winui.window.msg('请选中当前标签页~', {shift: 6});
    			return;
    		}
    		var tabtitle = $("#LAY_app_tabsheader li");
    		var ids = new Array();
    		$.each(tabtitle, function(i) {
    			if($(this).attr("lay-id") != 'initDefaultPageId' && $(this).attr("lay-id") != choosePage){
    				ids.push($(this).attr("lay-id"));
    			}
    		})
    		//如果关闭所有 ，即将所有的lay-id放进数组，执行tabDeleteAll
    		active.tabDeleteAll(ids);
    	}
    	$('.rightmenu').hide(); //最后再隐藏右键菜单
    });
    
    // 全屏操作
    var isFullScreen = false;// 全屏参数
    $("body").on("click", "#isFullScreen", function(e){
    	if(!isFullScreen){// 非全屏状态
    		isFullScreen = true;
    		fullScreen();
    	}else{// 全屏状态
    		isFullScreen = false;
    		exitFullScreen();
    	}
    });

    // 个人中心
    $("body").on("click", ".winui-start-syspersonal", function(e){
    	// 否则判断该tab项是否以及存在
		var isData = false; // 初始化一个标志，为false说明未打开该tab项 为true则说明已有
		$.each($("#LAY_app_tabsheader li[lay-id]"), function() {
			// 如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
			if($(this).attr("lay-id") === "syspersonal") {
				isData = true;
			}
		})
		if(isData == false) {
			// 标志为false 新增一个tab项
	    	active.tabAdd("../../tpl/syspersonal/syspersonal.html", "syspersonal", '<i class="fa fa-user"></i>个人中心');
		}
    	active.tabChange("syspersonal");
    });
    
    // 菜单收缩按钮
    var notOrContraction = false;// 菜单收缩参数
    $("body").on("click", "#notOrContraction", function(e){
    	if(!notOrContraction){// 展开状态
    		notOrContraction = true;
    		$(".tradition-left").animate({width: "0px"});
    		$(".tradition-right").animate({width: "100%"});
    	}else{// 收缩状态
    		notOrContraction = false;
    		$(".tradition-left").animate({width: "220px"});
    		var _width = $("body").width();
    		$(".tradition-right").animate({width: (_width - 220) + "px"}, function () {
    			$(this).css({width:"calc(100% - 220px)"})
    		});
    	}
    });
    
    // 退出
    $("body").on("click", ".logout", function(e){
    	winui.window.confirm('确认注销吗?', {id: 'exit-confim', icon: 3, title: '提示', skin: 'msg-skin-message', success: function(layero, index){
			var times = $("#exit-confim").parent().attr("times");
			var zIndex = $("#exit-confim").parent().css("z-index");
			$("#layui-layer-shade" + times).css({'z-index': zIndex});
		}}, function (index) {
        	AjaxPostUtil.request({url:reqBasePath + "login003", params:{}, type:'json', callback:function(json){
 	   			if(json.returnCode == 0){
	 	   			location.href = "../../tpl/index/login.html";
 	   			}else{
 	   				location.href = "../../tpl/index/login.html";
 	   			}
 	   		}});
        });
    });
    
    //头部桌面列表滚动事件
    $("body").on("wheel", ".desktop-menu-box", function(e){
    	var _this = $(this);
    	var right = _this.find("ul").width() - _this[0].offsetWidth;
    	if (_this.scrollLeft() < right && e.originalEvent.deltaY > 0) {
            //禁止事件默认行为（此处禁止鼠标滚轮行为关联到"屏幕滚动条上下移动"行为）  
            var left = (_this.scrollLeft() + 50);
            _this.scrollLeft(left) 
        }
        if (_this.scrollLeft() > 0 && e.originalEvent.deltaY < 0) {
            //禁止事件默认行为（此处禁止鼠标滚轮行为关联到"屏幕滚动条上下移动"行为）  
            var left = (_this.scrollLeft() - 50);
            _this.scrollLeft(left) 
        }
    });
    
    //头部桌面列表点击事件
    $("body").on("click", ".desktop-menu-box ul .layui-nav-item", function(e){
    	$("#sysMenuListBox").find(".layui-nav-tree").hide();
    	$(".desktop-menu-box ul .layui-nav-item").removeClass('select');
    	$(this).addClass('select');
    	var rowId = $(this).attr("rowid");
    	$("#sysMenuListBox").find("ul[menurowid='" + rowId + "']").show();
    });
    
    //消息中心
    $("body").on("click", "#messageCenter", function(e){
    	var dataid = $(this);
    	if($("#LAY_app_tabsheader li[lay-id]").length <= 0) {
			//如果比零小，则直接打开新的tab项
			active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
		} else {
			//否则判断该tab项是否以及存在
			var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
			$.each($("#LAY_app_tabsheader li[lay-id]"), function() {
				//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
				if($(this).attr("lay-id") === dataid.attr("data-id")) {
					isData = true;
				}
			})
			if(isData == false) {
				//标志为false 新增一个tab项
				active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), dataid.attr("data-title"));
			}
		}
		//最后不管是否新增tab，最后都转到要打开的选项页面上
		active.tabChange(dataid.attr("data-id"));
    });
    
    //左侧底部功能
    $("body").on("click", ".tradition-left-bottom .other-item", function(e){
    	var dataid = $(this);
    	var icon = dataid.find(".other-item-img").html();
    	if($("#LAY_app_tabsheader li[lay-id]").length <= 0) {
			//如果比零小，则直接打开新的tab项
			active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), icon + dataid.attr("data-title"));
		} else {
			//否则判断该tab项是否以及存在
			var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
			$.each($("#LAY_app_tabsheader li[lay-id]"), function() {
				//如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
				if($(this).attr("lay-id") === dataid.attr("data-id")) {
					isData = true;
				}
			})
			if(isData == false) {
				//标志为false 新增一个tab项
				active.tabAdd(dataid.attr("data-url"), dataid.attr("data-id"), icon + dataid.attr("data-title"));
			}
		}
		//最后不管是否新增tab，最后都转到要打开的选项页面上
		active.tabChange(dataid.attr("data-id"));
    });
    
	$(window).resize(function () {
	});
    
    exports('traditionpageindex', {});
});
