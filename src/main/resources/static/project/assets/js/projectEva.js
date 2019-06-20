$(() => {
    var tag_input = $('#is-tag-input');
    try {
        tag_input.tag(
            {
                placeholder: tag_input.attr('placeholder'),
                //enable typeahead by specifying the source array
                source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
                /**
                 //or fetch data from database, fetch those that match "query"
                 source: function(query, process) {
						  $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
						  .done(function(result_items){
							process(result_items);
						  });
						}
                 */
            }
        )

        //programmatically add/remove a tag
        var $tag_obj = $('#is-tag-input').data('tag');

        var index = $tag_obj.inValues('some tag');
        $tag_obj.remove(index);
    } catch (e) {
        //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
        tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        //autosize($('#form-field-tags'));
    }
    //初始化的时候隐藏指标的标准值
    $(".normalNumber").hide();
    //当切换后，显示显示或者隐藏
    $(".normal-switch").on('change', function (e) {
        const id = $(this).data('indexId')
        if (this.checked) {
            $(`[data-index-Id=${id}] .normalNumber`).show();
        } else {
            $(`[data-index-Id=${id}] .normalNumber`).hide();

        }
    })
    //初始化导入评价体系的列表
    initImportNewEvaTable();
    $('#import-new-eva').on('show.bs.modal',function (e) {
        $("#import-new-eva-table").bootstrapTable('refresh')
    })
    initIndexTable();
    initRadar();
    initEasyPieChart();
})

/**
 * 在本函数中创建新的评价体系的话会自动与当前项目进行绑定
 */
function addProjectEva() {
    const eva = {
        name: $("#is-name-input").val(),
        des: $("#is-des-input").val(),
        tags: $('#is-tag-input').val(),
        projectID: PROJECT_ID,
        open: $("#is-open-input")[0].checked
    }
    $.ajax({
        url: API.addProjectEva,
        type: 'POST',
        data: eva,
        success: function (data) {
            if (data.code == 1) {
                let evaID = data.data;
                if (confirm("是否跳转到编辑评价体系页面")) {
                    window.open(`/templates/eva/${evaID}/edit.html`);
                }
                //刷新页面
                window.location.reload();
            }
        }
    })

}

/**
 * 初始化导入新的评价体系列表
 */
function initImportNewEvaTable() {
    let importEvaTableLayout = {
        url:API.getEvaList,
        columns: [{
            checkbox: true,
            visible: true                  //是否显示复选框
        }, {
            field: 'evaID',
            title: '指标体系编号',
        }, {
            field: 'name',
            title: '指标体系名称',
        }, {
            field: 'tags',
            title: '指标体系标签',
            formatter: function (v) {
                if ($.trim(v) == '') {
                    return ''
                } else {
                    let tags = "";
                    v.split(',').forEach(tag => {
                        tags += `<span class="label label-info label-white middle">${tag}</span>`
                    })
                    return tags;
                }
            }
        }, {
            field: 'creator',
            title: '体系的创建者',
            formatter:function (v,row) {
                return `${v}:${row.nickName}`
            }

        }, {
            field: 'opt',
            title: '操作',
            formatter: function (v, row) {
                const importBtn = $('<button class="btn btn-xs btn-white btn-success bigger" onclick="importNewEva(' + row.evaID + ')"><i class="ace-icon fa fa-link"></i>导入</button>')
                const viewBtn = $('<button class="btn btn-xs btn-white btn-primary bigger" onclick="jumpEvaView(' + row.evaID + ')"><i class="ace-icon fa fa-eye"></i>查看</button>')
                const deleteBtn = $('<button class="btn btn-xs btn-white btn-danger bigger" onclick="deleteEva(' + row.evaID + ')"><i class="ace-icon fa fa-trash"></i>删除</button>')
                let res = viewBtn[0].outerHTML;
                if(row.linkID!=PROJECT_ID){
                    res+=importBtn[0].outerHTML
                }
                if(row.canEdit){
                    res+=deleteBtn[0].outerHTML
                }
                return res;
            }
        }],
        pagination: true,//启用分页
        pageNumber: 1,//默认是第一页
        pageSize: 10,
        pageList: [10, 20, 50],
        uniqueId: "evaID",
        detailView: true,
        detailFormatter: function (index, row, element) {
            const colDiv =  $('<div class="col-lg-12"/>')
            const rowDiv = $('<div class="row"/>').append(colDiv)
            const widgetBox =$("<div class='widget-box transparent '/>")
            rowDiv.append(widgetBox)
            const widgetBody = $("<div class='widget-body'/>")
            widgetBox.append(widgetBody);
            const widgetMain = $('<div class="widget-main"/>')
            widgetBody.append(widgetMain)
            widgetMain.append(`<div class="alert alert-info"><strong>体系描述：</strong>${row.des}<br></div>`)
            const p = $('<div class="row"/>')
                row.evaIndexList.forEach(evaIndex=>{
                    p.append($(`<div class="col-lg-2"><span class="label label-white middle">${evaIndex.name}</span></div>`))
                })
            widgetMain.append(p)
            return rowDiv;
        }


    }
    $("#import-new-eva-table").bootstrapTable(importEvaTableLayout)
}

/**
 * 跳转到相应的编辑页面
 * @param evaID
 */
function jumpEvaView(evaID) {
    window.open(`/templates/eva/${evaID}/edit.html`)
}

function initIndexTable() {
    let indexTableLayout = {
        uniqueId: "indexID",
        columns: [ {
            field: 'indexID',
            title: '指标编号',
        }, {
            field: 'name',
            title: '指标名称',
        }, {
            field: 'des',
            title: '指标描述',

        },{
            field: 'rang',
            title: '指标的取值范围',
            formatter: function (v, row) {
                return `[${row.rangeL},${row.rangeR}]`
            }
        }, {
            field: 'res',
            title: '指标值',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标值';
                }
            }
        }],
        onEditableSave:function (field, row, oldValue, $el) {
            $.ajax({
                url:`${API.updateIndexRes}${row.indexID}/res/${PROJECT_ID}`,
                type:"PUT",
                data:{
                    res:row[field]
                },
                success:function (data) {
                    if(data.code==1){
                        $("#refresh-btn").show();
                    }else {
                        $el.text(oldValue)
                    }
                },
                error:function () {
                    $el.text(oldValue)
                }
            })
        }
    }
    $("#index-table").bootstrapTable(indexTableLayout)
    $("#index-table").bootstrapTable('load',INDEX_LIST)

}
/**
 * 导入相应的评价指标体系
 * @param evaID
 */
function importNewEva(evaID) {
    $.ajax({
        url:`${API.bindEva}${evaID}/bind`,
        method:'POST',
        data:{
            linkID:PROJECT_ID
        },
        success:function (data) {
            if(data.code==1){
                alert("导入评价体系成功")
                window.location.reload();
            }
        }
    })
}



function initRadar() {
    EVA_LIST.forEach(eva=>{
        const table = $(`#eva-index-table-${eva.evaID}`);
        const svg = $(`#radar-${eva.evaID}`);
        svg.attr('height',table.height())
        const radar = new ImproveRadar(eva.evaIndexList,`#radar-${eva.evaID}`);
        radar.init();
    })
}

function initEasyPieChart() {
    $('.easy-pie-chart.percentage').each(function(){
        var $box = $(this).closest('.infobox');
        var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
        var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
        var size = parseInt($(this).data('size')) || 50;
        $(this).easyPieChart({
            barColor: barColor,
            trackColor: trackColor,
            scaleColor: false,
            lineCap: 'butt',
            lineWidth: parseInt(size/10),
            animate: ace.vars['old_ie'] ? false : 1000,
            size: size
        });
    })
}


function deleteEva(evaID,linkID) {
    $.ajax({
        url:`${API.bindEva}${evaID}/bind`,
        type:'DELETE',
        data:{
            linkID:linkID
        },
        success:function (data) {
            if(data.code==1){
                alert("删除成功")
                window.location.reload();
            }
        }
    })
}


function deleteEva(evaID) {
    $.ajax({
        url:`${API.deleteEva}${evaID}`,
        type:'DELETE',
        success:function (data) {
            if(data.code==1){
                alert("删除成功")
                $("#import-new-eva-table").bootstrapTable('refresh',{silent: true} )
            }
        }
    })
    
}

