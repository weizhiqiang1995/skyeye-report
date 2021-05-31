
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
        var radioTemplate = '{{#each rows}}<input type="radio" name="dataFromType" value="{{id}}" title="{{name}}" lay-filter="dataFromType"/>{{/each}}';

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

        $("body").on("click", "#cancle", function(){
            parent.layer.close(index);
        });
    });
});