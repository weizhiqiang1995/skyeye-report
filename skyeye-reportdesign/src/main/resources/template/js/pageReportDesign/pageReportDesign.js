layui.config({
    base: basePath,
    version: skyeyeVersion
}).extend({
    window: 'js/winui.window',
    echarts: '../echarts/echarts',
    echartsTheme: '../echarts/echartsTheme'
}).define(['window', 'jquery', 'winui', 'form', 'echarts'], function (exports) {
    winui.renderColor();
    var $ = layui.$,
        form = layui.form;

    $.skyeyeReportDesigner({
        mouseLineColor: "blue",
        headerMenuJson: [{
            "icon": " fa fa-area-chart fa-fw",
            "title": "图表",
            "children": [{
                "id": "111",
                "image": "../../assets/report/images/base1.png",
                "title": "基础折线图",
                "attr": {
                    "title.text": {
                        "value": "折线图",
                        "edit": true,
                        "desc": "主标题文本，支持使用 \\n 换行。",
                        "title": "主标题",
                        "editor": "2",
                        "editorChooseValue": ""
                    },
                    "xAxis.type": {
                        "value": "category",
                        "edit": false,
                        "desc": "category 类目轴，适用于离散的类目数据。为该类型时类目数据可自动从 series.data 或 dataset.source 中取，或者可通过 xAxis.data 设置类目数据。",
                        "title": "X坐标轴类型",
                        "editor": "",
                        "editorChooseValue": ""
                    },
                    "xAxis.data": {
                        "value": ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                        "edit": false,
                        "desc": "X坐标轴数据",
                        "title": "X坐标轴数据",
                        "editor": "",
                        "editorChooseValue": ""
                    },
                    "xAxis.splitLine.show": {
                        "value": "true",
                        "edit": true,
                        "desc": "是否显示分隔线。默认数值轴显示，类目轴不显示。",
                        "title": "是否显示分隔线",
                        "editor": "1",
                        "editorChooseValue": "[{\"id\": \"true\",  \"name\": \"是\"}, {\"id\": \"false\",  \"name\": \"否\"}]"
                    },
                    "yAxis.type": {
                        "value": "value",
                        "edit": false,
                        "desc": "category 类目轴，适用于离散的类目数据。为该类型时类目数据可自动从 series.data 或 dataset.source 中取，或者可通过 yAxis.data 设置类目数据。",
                        "title": "Y坐标轴类型",
                        "editor": "",
                        "editorChooseValue": ""
                    },
                    "series.data": {
                        "value": [150, 230, 224, 218, 135, 147, 260],
                        "edit": false,
                        "desc": "Y坐标轴数据",
                        "title": "Y坐标轴数据",
                        "editor": "",
                        "editorChooseValue": ""
                    },
                    "series.type": {
                        "value": "line",
                        "edit": false,
                        "desc": "报表类型为折线",
                        "title": "报表类型",
                        "editor": "",
                        "editorChooseValue": ""
                    }
                }
            }, {
                "image": "../../assets/report/images/base2.png",
                "title": "基础平滑折线图",
            }, {
                "image": "../../assets/report/images/base3.png",
                "title": "基础面积图",
            }]
        }, {
            "icon": " fa fa-table fa-fw",
            "title": "表格",
            "children": [{
                "icon": " fa fa-table fa-fw",
                "title": "简单表格",
            }, {
                "icon": " fa fa-list-alt fa-fw",
                "title": "复杂表格",
            }]
        }]
    });

    form.render();

});