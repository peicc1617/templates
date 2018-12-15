function rowFormatter(value, row, index, field){
    let appIcon =row.webAppIcon;
    if(row.webAppIcon==undefined||row.webAppIcon==null||row.webAppIcon.trim().length==0){
        appIcon="app.png";
    }
return '<div class="media search-media">' +
    '<div class="media-left">' +
    '<a href="#">' +
    '<img class="media-object" data-src="" alt="32x32" src="'+appIcon+'" data-holder-rendered="true" style="width: 32px; height: 32px;">' +
    '</a></div>' +
    '<div class="media-body">' +
    '<div>' +
    '<h5 class="media-heading">' +
    '<a href="'+row.appPath+'" target="_blank" class="blue">'+row.displayName+'</a>' +
    '</h5>' +
    '</div>' +
    '<small>'+row.webAppDescription+'</small>' +
    '<div class="search-actions text-center">' +
    '<span class="text-info">热度</span>' +
    '<span class="blue bolder">'+row.visitNum+'</span>' +
    '<a class="search-btn-action btn btn-sm btn-block btn-info" onclick="chooseThis(\''+row.appPath+'\',\''+row.displayName+'\',\''+appIcon+'\')">选择该方法</a>' +
    '</div>' +
    '</div></div>';
}
function chooseThis(path,name,icon) {
    bindingApp(name,path,icon);
}
$(function () {
    $("#store-modal").on('shown.bs.modal', function (e) {
        $("#app-store-table").bootstrapTable('resetView');
    })
})