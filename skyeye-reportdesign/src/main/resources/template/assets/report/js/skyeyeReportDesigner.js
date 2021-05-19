var form;

// 已经添加上的echarts图表
var inPageEcharts = {};
var inPageEchartsObject = {};
layui.define(["jquery", 'form'], function(exports) {
	var jQuery = layui.jquery;
	form = layui.form;
	(function($) {
		$.skyeyeReportDesigner = function(params) {
			var defaults = {
				'rulerColor': "RGB(135, 221, 252)", // 标尺颜色
				'rulerFontColor': "burlywood", // 标尺字体颜色
				'headerBgColor': 'burlywood', // 菜单栏背景颜色
				'headerMenuJson': [], // 菜单栏
				// excel配置
				excelConfig: {
					def:{
						width: '70px',
						row: 2,
						col: 8
					}
				}
			};
			params = $.extend({}, defaults, params);

			var flag = $("#skyeyeScaleBox").size() === 0 ? true : false;
			// 支持的编辑器类型
			var editorType = {};

			// box拖拽的八个点
			var editoptions = {
				left_top: true,
				left: true,
				right: true,
				top: true,
				bottom: true,
				right_top: true,
				left_bottom: true,
				right_bottom: true,
			};

			var f = {
				box: function() {
					var width = $(window).width();
					var height = $(window).height();
					// 整个box的宽高
					x.height(height).width(width);
				},

				ui: function() {
					rh.html("");
					rv.html("");
					// 创建标尺数值
					for(var i = 0; i < $(window).width(); i += 1) {
						if(i % 50 === 0) {
							$('<span class="n">' + i + '</span>').css("left", i + 2).appendTo(rh);
						}
					}
					// 垂直标尺数值
					for(var i = 0; i < $(window).height(); i += 1) {
						if(i % 50 === 0) {
							$('<span class="n">' + i + '</span>').css("top", i + 2).appendTo(rv);
						}
					}
					rh.css({
						'background': 'url(../../assets/report/images/ruler_h.png), linear-gradient(' + params.rulerColor + ', ' + params.rulerColor + ')'
					});
					rv.css({
						'background': 'url(../../assets/report/images/ruler_v.png), linear-gradient(' + params.rulerColor + ', ' + params.rulerColor + ')'
					});
					// 标尺字体颜色
					$(".n").css("color", params.rulerFontColor);
				},

				// ie6的支持
				ie6: function() {
					if(!window.XMLHttpRequest) {
						$(window).scroll(function() {
							var t = $(document).scrollTop();
							x.css("top", t);
						});
						if(flag) {
							$(window).trigger("scroll");
						}
					}
				},

				// 判断是否为空
				isNull: function(str){
					if(str == null || str == ""){
						return true;
					}
					return false;
				},

				// 加载菜单栏
				loadHeader: function(){
					var str = "";
					$.each(params.headerMenuJson, function(i, item) {
						if(f.isNull(item.children)){
							str += '<a class="def-nav" href="">' +
								'<i class="icon ' + item.icon + '"></i>' +
								'<span>' + item.title + '</span>' +
								'</a>';
						}else{
							str += '<div class="def-nav has-pulldown-special">' +
								'<a class="pulldown-nav" href="javascript:void(0);">' +
								'<em class="f-icon arrow-bottom"></em>' +
								'<i class="icon' + item.icon + '"></i>' +
								'<span>' + item.title + '</span>' +
								'</a>' +
								'<div class="pulldown app-url">' +
								'<div class="child-menu">';
							$.each(item.children, function(j, bean) {
								if(f.isNull(bean.icon)){
									str += '<a class="li disk layui-col-xs3" href="javascript:void(0);" rowId="' + bean.id + '">' +
										'<img class="image" src="' + bean.image + '"/>' +
										'<span class="text">' + bean.title + '</span>' +
										'</a>';
								}else{
									str += '<a class="li disk layui-col-xs3" href="javascript:void(0);" rowId="' + bean.id + '">' +
										'<i class="icon' + bean.icon + '"></i>' +
										'<span class="text">' + bean.title + '</span>' +
										'</a>';
								}
							});
							str += '</div>' +
								'</div>' +
								'</div>';

						}
						str += '<span class="separate"></span>';
					});
					skyeyeHeader.find(".navs").html(str);
					skyeyeHeader.css({
						"background-color": params.headerBgColor
					});
					skyeyeHeader.find(".separate").css({
						"background-color": params.headerBgColor
					});
					$(".def-nav").hover(function(e){
						$(this).find(".pulldown-nav").addClass("hover");
						$(this).find(".pulldown-nav").find(".f-icon").removeClass("arrow-bottom");
						$(this).find(".pulldown-nav").find(".f-icon").addClass("arrow-top");
						$(this).find(".pulldown").show();
					}, function(e){
						$(this).find(".pulldown").hide();
						$(this).find(".pulldown-nav").removeClass("hover");
						$(this).find(".pulldown-nav").find(".f-icon").addClass("arrow-bottom");
						$(this).find(".pulldown-nav").find(".f-icon").removeClass("arrow-top");
					});
					f.setEchartsClickEvent();
				},

				// echarts报表点击事件
				setEchartsClickEvent: function (){
					skyeyeHeader.find(".disk").click(function () {
						var modelId = $(this).attr("rowId");
						var echartsMation = f.getEchartsMationById(modelId);
						if(!f.isNull(echartsMation)){
							var option = getEchartsOptions(echartsMation);
							// 获取boxId
							var boxId = modelId + getRandomValueToString();
							// 获取echarts图表id
							var echartsId = f.getEchartsBox(boxId, modelId);
							// 加载图表
							var newChart = echarts.init(document.getElementById(echartsId));
							newChart.setOption(option);
							$("#" + echartsId).resize(function () {
								newChart.resize();
							});
							// 加入页面属性
							inPageEcharts[boxId] = $.extend(true, {}, echartsMation);
							inPageEchartsObject[boxId] = newChart;
						}
					});
				},

				// 根据id获取echarts信息
				getEchartsMationById: function(id){
					var echartsMation;
					$.each(params.headerMenuJson, function(i, item) {
						if(!f.isNull(item.children)){
							echartsMation = getInPoingArr(item.children, "id", id);
							return false;
						}
					});
					return echartsMation;
				},

				getEchartsBox: function (boxId, modelId){
					var echartsId = "echarts" + boxId;
					var echartsBox = document.createElement("div");
					// 为div设置类名
					echartsBox.className = "echarts-box";
					echartsBox.id = echartsId;
					var box = f.createBox(boxId, modelId);
					box.appendChild(echartsBox);
					echartsBox.onmousedown = ee => {
						var id = $("#" + echartsId).parent().attr("id");
						f.setMoveEvent(ee, $("#" + id));
						// 阻止事件冒泡（针对父元素的move）
						ee.stopPropagation();
					};
					return echartsId;
				},

				createBox: function(id, modelId){
					f.removeEchartsEditMation();
					// 创建一个div
					var div = document.createElement("div");
					// 为div设置类名
					div.className = "kuang active";
					div.id = id;
					div.dataset.boxId = id;
					div.dataset.modelId = modelId;
					div.style.top = "0px";
					div.style.left = "0px";
					skyeyeReportContent[0].appendChild(div);
					// 遍历this.editoptions
					for (let attr in editoptions) {
						if (editoptions[attr]) {
							// 循环创建左，左上，左下，右，右上，右下，上，下方位的小点
							var dian = document.createElement("div");
							dian.className = "dian " + attr;
							dian.dataset.belongId = id;
							// 设置类型为对应的attr
							dian.dataset.type = attr;
							// 将类名为”dian“的div添加到div中
							div.appendChild(dian);
							// 当按下对应方位的小点时
							dian.onmousedown = ee => {
								var ee = ee || window.event;
								// 先获取鼠标距离屏幕的left与top值
								var clientXY = {
									x: ee.clientX,
									y: ee.clientY
								}
								var id = ee.target.dataset.belongId;
								var that = $("#" + id);
								// 获取鼠标按下时ele的宽高
								var eleWH = {
									width: $("#" + id).width(),
									height: $("#" + id).height(),
								}
								// 阻止事件冒泡（针对父元素的move）
								ee.stopPropagation();
								// 通过e.target获取精准事件源对应的type值
								var type = ee.target.dataset.type;
								// 鼠标按下对应方位小点移动时，调用mousemove
								document.onmousemove = function (e) {
									// 查找type中是否包含”right“
									if (type.indexOf('right') > -1) {
										// 如果拖拽后的宽度小于最小宽度，就return出去
										if (50 > eleWH.width + e.clientX - clientXY.x) {
											return;
										}
										// ele拖拽后的宽度为：初始width+拖拽后鼠标距离屏幕的距离 - 第一次按下时鼠标距离屏幕的距离
										that.width(eleWH.width + e.clientX - clientXY.x);
									}
									// 与”right“相同原理
									if (type.indexOf("bottom") > -1) {
										if (35 > eleWH.height + e.clientY - clientXY.y) {
											return;
										}
										that.css({
											height: (eleWH.height + e.clientY - clientXY.y) + "px"
										});
									}

									if (type.indexOf("top") > -1) {
										if (35 > eleWH.height - e.clientY + clientXY.y) {
											return;
										}
										var top = e.clientY - 78;
										top = top < 0 ? 0 : top;
										// ele拖拽后的高度为：初始height-拖拽后鼠标距离屏幕的距离 + 第一次按下时鼠标距离屏幕的距离
										// 重新设置ele的top值为此时鼠标距离屏幕的y值
										that.css({
											height: (eleWH.height - e.clientY + clientXY.y) + "px",
											top: top + "px"
										});
									}
									// 与”top“相同原理
									if (type.indexOf("left") > -1) {
										if (50 > eleWH.width - e.clientX + clientXY.x) {
											return;
										}
										var left = e.clientX - 18;
										var maxLeft = skyeyeReportContent.width() - that.width();
										left = left < 0 ? 0 : left;
										left = left > maxLeft ? maxLeft : left;
										// 重新设置ele的left值为此时鼠标距离屏幕的x值
										that.css({
											width: (eleWH.width - e.clientX + clientXY.x) + "px",
											left: left + "px"
										});
									}
								}
								document.onmouseup = function () {
									document.onmousemove = null;
									document.onmouseup = null;
								}
							}
						}
					}
					// 添加移动事件
					$("#" + id).on('mousedown', function (ee){
						var id = ee.target.dataset.boxId;
						f.setMoveEvent(ee, $("#" + id));
					});
					return div;
				},

				setMoveEvent: function (ee, that){
					// 获取事件对象
					var ee = ee || window.event;
					// 鼠标按下时，鼠标相对于元素的x坐标
					var x = ee.offsetX;
					// 鼠标按下时，鼠标相对于元素的y坐标
					var y = ee.offsetY;
					var maxLeft = skyeyeReportContent.width() - that.width();
					// 鼠标按下移动时调用mousemove
					document.onmousemove = e => {
						// 元素ele移动的距离l
						var left = e.clientX - x - 44;
						// 元素ele移动的距离t
						var top = e.clientY - y - 104;
						left = left < 0 ? 0 : left;
						top = top < 0 ? 0 : top;
						left = left > maxLeft ? maxLeft : left;
						that.css({
							left: left + "px",
							top: top + "px"
						});
					}
					// 当鼠标弹起时，清空onmousemove与onmouseup
					document.onmouseup = () => {
						document.onmousemove = null;
						document.onmouseup = null;
					}
				},

				// 快捷键
				tableKeyDown: function(e) {
					let eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
					if (e.ctrlKey && eCode === 90) {
						// 撤销

					}
				},

				// 记录缓存，方便撤销
				recordData: function() {

				},

				initExcelEvent: function(){
					// 图表点击事件
					$("body").on('click', ".echarts-box", function(e){
						f.removeEchartsEditMation();
						// 被选中项
						$(this).parent().find(".dian").show();
						$(this).parent().css({
							"border": "1px solid #0f0",
							"z-index": 200
						});
						$(this).parent().addClass("active");
						f.loadEchartsEditor();
						e.stopPropagation();
					});

					// 内容点击
					$("body").on('click', skyeyeReportContent, function(){
						f.removeEchartsEditMation();
					});

					// 编辑器点击防止触发父内容事件
					$("body").on('click', ".form-box", function(e){
						e.stopPropagation();
					});
				},

				// 移除所有图表的编辑信息
				removeEchartsEditMation: function(){
					$(".dian").hide();
					$(".kuang").css({
						"border": "1px solid darkgray",
						"z-index": 100
					});
					$(".kuang").removeClass("active");
					$("#showForm").parent().remove();
				},

				// 加载echarts报表编辑器
				loadEchartsEditor: function (){
					var chooseEcharts = skyeyeReportContent.find(".active").eq(0);
					var boxId = chooseEcharts.data("boxId");
					var echartsMation = inPageEcharts[boxId];
					f.addNewFormBox();
					if(!f.isNull(echartsMation)) {
						var attr = echartsMation.attr;
						$("#showForm").html("");
						$.each(attr, function(key, val) {
							if(val.edit){
								// 可以编辑
								var formItem = editorType[val.editor];
								if(!f.isNull(formItem)){
									var data = f.getFormItemData(key, val, boxId);
									if(!f.isNull(formItem.showValueTemplate)){
										var showValueTemplate = getDataUseHandlebars(formItem.showValueTemplate, {rows: data.bean.editorChooseValue});
										data.bean.showValueTemplate = showValueTemplate;
									}
									// 如果表单类型中支持的编辑器类型存在，则去解析
									var html = getDataUseHandlebars('{{#bean}}' + formItem.html + '{{/bean}}', data);
									$(html).appendTo($("#showForm").get(0));
									if(!f.isNull(formItem.js)){
										var js = getDataUseHandlebars('{{#bean}}' + formItem.js + '{{/bean}}', data);
										var jsCon = '<script>layui.define(["jquery"], function(exports) {var jQuery = layui.jquery;(function($) {' + js + '})(jQuery);});</script>';
										$("#showForm").append(jsCon);
									}
								}
							}else{
								var formItem = editorType["100"];
								var data = f.getFormItemData(key, val, boxId);
								// 如果表单类型中支持的编辑器类型存在，则去解析
								var html = getDataUseHandlebars('{{#bean}}' + formItem.html + '{{/bean}}', data);
								$(html).appendTo($("#showForm").get(0))
							}
						});
						form.render();
					}
				},

				// 将echarts的数据格式转化为form表单的数据格式
				getFormItemData: function(key, val, boxId){
					var editorChooseValue = []
					if(isJSON(val.editorChooseValue)){
						editorChooseValue = JSON.parse(val.editorChooseValue);
						$.each(editorChooseValue, function(i, item){
							item["layFilter"] = boxId;
						});
					}
					return {
						"bean": {
							modelKey: key,
							boxId: boxId,
							defaultWidth: "layui-col-xs12",
							labelContent: val.title,
							context: val.value,
							editorChooseValue: editorChooseValue
						}
					};
				},

				// 添加一个新的form表单
				addNewFormBox: function(){
					var editForm = '<div class="form-box">' +
						'<fieldset class="layui-elem-field layui-field-title">' +
						'  <legend>属性</legend>' +
						'</fieldset>' +
						'<form class="layui-form" action="" id="showForm" autocomplete="off"></form>' +
						'</div>';
					skyeyeReportContent.append(editForm);
				},

				// 加载编辑器类型
				initEditorType: function (){
					$.getJSON("../../assets/report/json/skyeyeEditor.json", function (data) {
						editorType = data;
					});
				},

				// reportContent的八个角
				getEightCape: function(){
					var capeResult = '<div class="cape cape_left1"></div>' +
						'<div class="cape cape_left1_top"></div>' +
						'<div class="cape cape_right1"></div>' +
						'<div class="cape cape_right1_top"></div>' +
						'<div class="cape cape_left2"></div>' +
						'<div class="cape cape_left2_bottom"></div>' +
						'<div class="cape cape_right2"></div>' +
						'<div class="cape cape_right2_bottom"></div>';
					return capeResult;
				},

				// 初始化执行
				init: function() {
					f.box();
					f.ui();
					f.ie6();
					f.loadHeader();
					f.initExcelEvent();
					// 加载编辑器类型
					f.initEditorType();
				}
			};

			if(flag) {
				$('<div class="skyeyeScaleBox" id="skyeyeScaleBox" onselectstart="return false;">' +
					'<div id="skyeyeScaleRulerH" class="skyeyeScaleRuler_h"></div>' +
					'<div id="skyeyeScaleRulerV" class="skyeyeScaleRuler_v"></div>' +
					'</div>' +
					'<div class="hd-main clearfix" id="skyeyeHeader">' +
					'<font class="logo-title">Skyeye系列-报表设计器</font>' +
					'<div class="navs"></div>' +
					'</div>' +
					f.getEightCape() +
					'<div class="report-content" id="reportContent">' +
					'</div>').appendTo($("body"));
			} else {
				$("#skyeyeScaleBox").show();
			}
			//整个标尺盒子对象，垂直标尺与水平标尺对象，虚线对象，弹出框对象，单选对象，文本对象，按钮对象
			var x = $("#skyeyeScaleBox"),
				rh = $("#skyeyeScaleRulerH"),
				rv = $("#skyeyeScaleRulerV"),
				skyeyeHeader = $("#skyeyeHeader"), // 头部菜单栏
				skyeyeReportContent = $("#reportContent"); // 编辑器内容

			// 初始化
			f.init();

			/*浏览器拉伸时，标尺自适应*/
			$(window).resize(function() {
				f.box();
				f.ui();
			});

		};

	})(jQuery);
	exports('skyeyeReportDesigner', null);
});

// 编辑器变化事件
function dataValueChange(value, _this){
	var modelKey = _this.parents('.layui-form-item').attr('modelKey');
	var boxId = _this.parents('.layui-form-item').attr('boxId');
	layui.$.each(inPageEcharts[boxId].attr, function(key, val){
		if(modelKey == key){
			val.value = getVal(value);
		}
	});
	var echartsMation = inPageEcharts[boxId];
	var option = getEchartsOptions(echartsMation);
	// 加载图表
	var newChart = inPageEchartsObject[boxId];
	newChart.setOption(option);
}

// 获取echarts的配置信息
function getEchartsOptions(echartsMation){
	var echartsJson = {};
	layui.$.each(echartsMation.attr, function(key, val) {
		echartsJson = layui.$.extend(true, echartsJson, calcKeyHasPointToJson(key, val.value));
	});
	return echartsJson;
}

function calcKeyHasPointToJson(key, value){
	var keyArr = key.split(".");
	var resultValue = value;
	if(keyArr.length > 1){
		resultValue = calcKeyHasPointToJson(key.substr(key.indexOf(".") + 1), value);
	}
	var result = {};
	result[keyArr[0]] = resultValue;
	return result;
}

function getVal(value){
	if("true" == value){
		return true;
	} else if("false" == value){
		return false;
	}
	return value;
}