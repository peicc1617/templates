function rowFormatter(value, row, index, field){
return '<div class="media search-media">' +
    '<div class="media-left">' +
    '<a href="#">' +
    '<img class="media-object" data-src="" alt="32x32" src="/templates/img/app.png" data-holder-rendered="true" style="width: 32px; height: 32px;">' +
    '</a></div>' +
    '<div class="media-body">' +
    '<div>' +
    '<h5 class="media-heading">' +
    '<a href="'+value.appPath+'" target="_blank" class="blue">'+value.displayName+'</a>' +
    '</h5>' +
    '</div>' +
    '<small>'+value.webAppDescription+'</small>' +
    '<div class="search-actions text-center">' +
    '<span class="text-info">热度</span>' +
    '<span class="blue bolder">'+value.visitNum+'</span>' +
    '<a class="search-btn-action btn btn-sm btn-block btn-info" onclick="chooseThis(\''+value.appPath+'\',\''+value.displayName+'\')">选择该方法</a>' +
    '</div>' +
    '</div></div>';
}
function chooseThis(path,name) {
    console.log("选择的创新方法"+name);
    CUR_NODE.appName = name;
    CUR_NODE.appPath = path;
    $("#store-modal").modal("hide");
    viewNode();
    updateNode(true,function () {
        ANALYSIS_MSG.type=MSG_TYPE_TABLE.NODE
        ANALYSIS_MSG.content="为节点["+CUR_NODE.name+"]绑定了新的APP["+name+"]"
        Analysis.send();
    })
}
$(function () {
    $("#store-modal").on('shown.bs.modal', function (e) {
        $("#app-store-table").bootstrapTable('resetView');
    })
})