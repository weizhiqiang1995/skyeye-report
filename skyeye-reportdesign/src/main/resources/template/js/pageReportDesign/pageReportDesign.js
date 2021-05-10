layui.config({
    base: basePath,
    version: skyeyeVersion
}).extend({
    window: 'js/winui.window',
}).define(['window', 'jquery', 'winui', 'form'], function (exports) {
    winui.renderColor();
    var $ = layui.$,
        form = layui.form;

    $.skyeyeReportDesigner({
        mouseLineColor: "blue"
    });

    form.render();

});