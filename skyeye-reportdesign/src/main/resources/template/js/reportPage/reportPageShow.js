
var rowId;

layui.config({
    base: basePath,
    version: skyeyeVersion
}).extend({
    window: 'js/winui.window',
    echarts: '../echarts/echarts',
    echartsTheme: '../echarts/echartsTheme'
}).define(['window', 'jquery', 'winui', 'form', 'echarts', 'colorpicker'], function (exports) {
    winui.renderColor();
    var $ = layui.$,
        form = layui.form;

    rowId = GetUrlParam("rowId");

    var content = $(document.body);
    var initData = {};
    // 获取初始化数据
    AjaxPostUtil.request({url:reqBasePath + "reportpage006", params: {rowId: rowId}, type:'json', method: "GET", callback:function(json){
        if(json.returnCode == 0){
            initData = JSON.parse(json.bean.content);
            var modelList = initData.modelList;
            var widthScale = getScale(initData.contentWidth, content.width());
            var heightScale = getScale(initData.contentHeight, content.height());
            if(!isNull(modelList)){
                $.each(modelList, function(i, item) {
                    var leftNum = multiplication(item.attrMation.attr["custom.move.x"].value, widthScale);
                    var topNum = multiplication(item.attrMation.attr["custom.move.y"].value, heightScale);
                    item.attrMation.attr["custom.move.x"].value = leftNum;
                    item.attrMation.attr["custom.move.y"].value = topNum;
                    item.attrMation.attr = getDataFromRest(item.attrMation.attr);
                    // 加载模型
                    var boxId = addNewModel(item.modelId, item.attrMation);
                    $("#" + boxId).css({
                        left: leftNum + "px",
                        top: topNum + "px",
                        width: multiplication(item.width, widthScale),
                        height: multiplication(item.height, heightScale)
                    });
                    setBoxAttrMation("custom.box.background", boxId, item.attrMation.attr["custom.box.background"].value);
                    setBoxAttrMation("custom.box.border-color", boxId, item.attrMation.attr["custom.box.border-color"].value);
                });
            }
            if(!isNull(initData.bgImage)){
                content.css({
                    "background-image": "url(" + initData.bgImage + ")",
                    "background-size": content.width() + "px " + content.height() + "px"
                });
            }
        }else{
            winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
        }
    }});

    function getDataFromRest(attr){
        var fromId = attr['custom.dataBaseMation'].value.id;
        var xData = attr['xAxis.data'].pointValue;
        var yData = attr['series.data'].pointValue;
        if(isNull(fromId) || (isNull(xData) && isNull(yData))){
            return attr;
        }
        var needGetData = {};
        needGetData[xData] = attr['xAxis.data'].value;
        needGetData[yData] = attr['series.data'].value;
        var params = {
            fromId: fromId,
            needGetDataStr: JSON.stringify(needGetData)
        };
        AjaxPostUtil.request({url:reqBasePath + "reportdatafrom007", params: params, type:'json', method: "POST", callback:function(json){
            if(json.returnCode == 0){
                console.log(json)
                attr['xAxis.data'].value = json.bean[xData];
                attr['series.data'].value = json.bean[yData];
            }else{
                winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
            }
        }, async: false});
        return attr;
    }

    function addNewModel(modelId, echartsMation){
        if(!isNull(echartsMation)){
            var option = getEchartsOptions(echartsMation);
            // 获取boxId
            var boxId = modelId + getRandomValueToString();
            // 获取echarts图表id
            var echartsId = getEchartsBox(boxId, modelId);
            // 加载图表
            var newChart = echarts.init(document.getElementById(echartsId));
            newChart.setOption(option);
            $("#" + echartsId).resize(function () {
                newChart.resize();
            });
            // 加入页面属性
            inPageEcharts[boxId] = $.extend(true, {}, echartsMation);
            inPageEchartsObject[boxId] = newChart;
            return boxId;
        }
        return "";
    }

    function getEchartsBox(boxId, modelId){
        var echartsId = "echarts" + boxId;
        var echartsBox = document.createElement("div");
        // 为div设置类名
        echartsBox.className = "echarts-box";
        echartsBox.id = echartsId;
        var box = createBox(boxId, modelId);
        box.appendChild(echartsBox);
        return echartsId;
    }

    function createBox(id, modelId){
        // 创建一个div
        var div = document.createElement("div");
        // 为div设置类名
        div.className = "kuang";
        div.id = id;
        div.dataset.boxId = id;
        div.dataset.modelId = modelId;
        div.style.top = "0px";
        div.style.left = "0px";
        $(document.body)[0].appendChild(div);
        return div;
    }

    form.render();

});