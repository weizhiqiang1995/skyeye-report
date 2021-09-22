
// 表格的序号
var rowNum = 1;

layui.config({
    base: basePath,
    version: skyeyeVersion
}).extend({
    window: 'js/winui.window'
}).define(['window', 'jquery', 'winui'], function (exports) {
    winui.renderColor();
    layui.use(['form'], function (form) {
        var index = parent.layer.getFrameIndex(window.name);
        var $ = layui.$;
        var usetableTemplate = $("#usetableTemplate").html();
        var selOption = getFileContent('tpl/template/select-option.tpl');

        // 连接池数据变化
        form.on('select(poolClass)', function(data) {
            var val = data.value;
            var options = getInPoingArr(poolList, "id", val, "options");
            options = JSON.parse(options);
            $("#useTable").html("");
            rowNum = 1;
            $.each(options, function(key, value){
                addRow();
                $("#configKey" + (rowNum - 1)).val(key);
                $("#configValue" + (rowNum - 1)).val(value);
            });
        });

        matchingLanguage();
        form.render();
        form.on('submit(formAddBean)', function (data) {
            if (winui.verifyForm(data.elem)) {
                var tableData = new Array();
                var rowTr = $("#useTable tr");
                $.each(rowTr, function(i, item) {
                    //获取行编号
                    var rowNum = $(item).attr("trcusid").replace("tr", "");
                    var row = {
                        configKey: $("#configKey" + rowNum).val(),
                        configValue: $("#configValue" + rowNum).val(),
                        remark: $("#remark" + rowNum).val()
                    };
                    tableData.push(row);
                });
                var params = {
                    name: $("#name").val(),
                    jdbcUrl: $("#jdbcUrl").val(),
                    user: $("#user").val(),
                    password: $("#password").val(),
                    dataType: $("#dataType").val(),
                    poolClass: $("#poolClass").val(),
                    comment: $("#comment").val(),
                    options: JSON.stringify(tableData),
                };
                AjaxPostUtil.request({url:reqBasePath + "reportdatabase002", params: params, type:'json', method: "POST", callback:function(json){
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

        // 新增行
        $("body").on("click", "#addRow", function() {
            addRow();
        });

        // 删除行
        $("body").on("click", "#deleteRow", function() {
            deleteRow();
        });

        // 新增行
        function addRow() {
            var par = {
                id: "row" + rowNum.toString(), // checkbox的id
                trId: "tr" + rowNum.toString(), // 行的id
                title: "title" + rowNum.toString(), // 标题id
                value: "value" + rowNum.toString(), // 属性值id
                dafaultChoose: "dafaultChoose" + rowNum.toString() // 是否默认id
            };
            $("#useTable").append(getDataUseHandlebars(usetableTemplate, par));
            form.render();
            rowNum++;
        }

        // 删除行
        function deleteRow() {
            var checkRow = $("#useTable input[type='checkbox'][name='tableCheckRow']:checked");
            if(checkRow.length > 0) {
                $.each(checkRow, function(i, item) {
                    // 移除界面上的信息
                    $(item).parent().parent().remove();
                });
            } else {
                winui.window.msg('请选择要删除的行', {icon: 2, time: 2000});
            }
        }

        $("body").on("click", "#cancle", function(){
            parent.layer.close(index);
        });
    });
});