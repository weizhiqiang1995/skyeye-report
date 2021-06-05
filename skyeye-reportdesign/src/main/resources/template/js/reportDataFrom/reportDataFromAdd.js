
// 表格的序号
var rowNum = 1;

layui.config({
    base: basePath,
    version: skyeyeVersion
}).extend({
    window: 'js/winui.window'
}).define(['window', 'jquery', 'winui', 'codemirror', 'xml', 'sql',], function (exports) {
    winui.renderColor();
    layui.use(['form'], function (form) {
        var index = parent.layer.getFrameIndex(window.name);
        var $ = layui.$;
        var radioTemplate = '{{#each rows}}<input type="radio" name="dataFromType" value="{{id}}" title="{{name}}" lay-filter="dataFromType" {{checked}} />{{/each}}';
        var dataFromTypeList = [];
        var xmlContent, jsonContent, sqlContent, restRequestBodyContent;

        // 数据源类型列表
        showGrid({
            id: "dataFromTypeBox",
            url: reqBasePath + "reportcommon008",
            params: {},
            pagination: false,
            template: radioTemplate,
            method: "GET",
            ajaxSendLoadBefore: function(hdb){
            },
            ajaxSendAfter:function(json){
                dataFromTypeList = [].concat(json.rows);
                $.each(dataFromTypeList, function (i, item){
                    if(item.checked){
                        initDataFromBoxContent(item.id);
                        form.render('radio');
                    }
                });
            }
        });

        matchingLanguage();
        form.render();
        form.on('submit(formAddBean)', function (data) {
            if (winui.verifyForm(data.elem)) {
                var params = {
                    name: $("#name").val(),
                    remark: $("#remark").val(),
                    type: $("input[name='dataFromType']:checked").val()
                };
                AjaxPostUtil.request({url:reqBasePath + "reportimportmodel002", params: params, type:'json', method: "POST", callback:function(json){
                    if(json.returnCode == 0){
                        parent.layer.close(index);
                        parent.refreshCode = '0';
                    }else{
                        winui.window.msg(json.returnMessage, {icon: 2,time: 2000});
                    }
                }});
            }
            return false;
        });

        form.on('radio(dataFromType)', function (data) {
            var val = data.value;
            initDataFromBoxContent(val);
        });

        function initDataFromBoxContent(val){
            var staticTplPath = getInPoingArr(dataFromTypeList, "id", val, "staticTplPath");
            var html = getFileContent(staticTplPath);
            $("#dataBox").html(html);
            // 加载字段解析信息
            $("#analysisHeader").html($("#analysisHeaderTemplate").html());
            $("#analysisTable").html("");
            initEvent();
            form.render();
        }

        function initEvent(){
            var commonOptions = getCommonCodeMirrorOptions();
            if(!isNull(document.getElementById("xmlData"))){
                xmlContent = CodeMirror.fromTextArea(document.getElementById("xmlData"), $.extend(true, commonOptions, {
                    mode: "xml",
                    theme: "eclipse"
                }));
            }
            if(!isNull(document.getElementById("jsonData"))){
                jsonContent = CodeMirror.fromTextArea(document.getElementById("jsonData"), $.extend(true, commonOptions, {
                    mode: "xml",
                    theme: "eclipse"
                }));
            }
            if(!isNull(document.getElementById("sqlData"))){
                sqlContent = CodeMirror.fromTextArea(document.getElementById("sqlData"), $.extend(true, commonOptions, {
                    mode: "sql",
                    theme: "eclipse"
                }));
            }
            if(!isNull(document.getElementById("requestBody"))){
                restRequestBodyContent = CodeMirror.fromTextArea(document.getElementById("requestBody"), $.extend(true, commonOptions, {
                    mode: "xml",
                    theme: "eclipse"
                }));
            }
        }

        function getCommonCodeMirrorOptions(){
            return {
                mode: "xml",  // 模式
                theme: "eclipse",  // CSS样式选择
                indentUnit: 4,  // 缩进单位，默认2
                smartIndent: true,  // 是否智能缩进
                tabSize: 4,  // Tab缩进，默认4
                readOnly: false,  // 是否只读，默认false
                showCursorWhenSelecting: true,
                lineNumbers: true,  // 是否显示行号
                styleActiveLine: true, //line选择是是否加亮
                matchBrackets: true,
            };
        }

        /**********************************rest数据源header--start**************************************/
        //新增行
        $("body").on("click", "#addRow", function() {
            addRow();
        });

        //删除行
        $("body").on("click", "#deleteRow", function() {
            deleteRow();
        });

        //新增行
        function addRow() {
            var par = {
                id: "row" + rowNum.toString(), //checkbox的id
                trId: "tr" + rowNum.toString(), //行的id
                headerKey: "headerKey" + rowNum.toString(), // 配置项id
                headerValue: "headerValue" + rowNum.toString(), // 配置值id
                headerDescription: "headerDescription" + rowNum.toString() // 备注id
            };
            $("#restHeaderTable").append(getDataUseHandlebars($("#headerTemplate").html(), par));
            form.render();
            rowNum++;
        }

        //删除行
        function deleteRow() {
            var checkRow = $("#restHeaderTable input[type='checkbox'][name='tableCheckRow']:checked");
            if(checkRow.length > 0) {
                $.each(checkRow, function(i, item) {
                    //移除界面上的信息
                    $(item).parent().parent().remove();
                });
            } else {
                winui.window.msg('请选择要删除的行', {icon: 2, time: 2000});
            }
        }
        /**********************************rest数据源header--end**************************************/

        /**********************************字段解析--start**************************************/
        //新增行
        $("body").on("click", "#addAnalysisRow", function() {
            addAnalysisRow();
        });

        //删除行
        $("body").on("click", "#deleteAnalysisRow", function() {
            deleteAnalysisRow();
        });

        //新增行
        function addAnalysisRow() {
            var par = {
                id: "row" + rowNum.toString(), //checkbox的id
                trId: "tr" + rowNum.toString(), //行的id
                key: "key" + rowNum.toString(), // 字段id
                title: "title" + rowNum.toString(), // 含义id
                dataType: "dataType" + rowNum.toString(), // 字段类型id
                dataLength: "dataLength" + rowNum.toString(), // 字段长度id
                dataPrecision: "dataPrecision" + rowNum.toString(), // 字段精度id
                remark: "remark" + rowNum.toString() // 备注id
            };
            $("#analysisTable").append(getDataUseHandlebars($("#analysisTemplate").html(), par));
            form.render();
            rowNum++;
        }

        //删除行
        function deleteAnalysisRow() {
            var checkRow = $("#analysisTable input[type='checkbox'][name='tableCheckRow']:checked");
            if(checkRow.length > 0) {
                $.each(checkRow, function(i, item) {
                    //移除界面上的信息
                    $(item).parent().parent().remove();
                });
            } else {
                winui.window.msg('请选择要删除的行', {icon: 2, time: 2000});
            }
        }
        /**********************************字段解析--end**************************************/

        $("body").on("click", "#cancle", function(){
            parent.layer.close(index);
        });
    });
});