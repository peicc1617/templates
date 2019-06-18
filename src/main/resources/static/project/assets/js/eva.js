$(()=>{
    initDiyIndexTable();
    $("#eva-name").editable({
        type: 'text',
        title: '修改评价体系名称',
        name:'name',
        url: updateEvaInfo,
        validate:validateEvaInfo
    });
    $("#eva-des").editable({
        type: 'text',
        title: '修改评价体系描述',
        name:'des',
        url: updateEvaInfo,
        validate:validateEvaInfo
    });

})
function initDiyIndexTable() {
    let diyIndexTableLayout = {
        url:API.evaIndexAddAndGet,
        columns: [{
            checkbox: true,
            visible: true                  //是否显示复选框
        }, {
            field: 'indexID',
            title: '指标编号',
        }, {
            field: 'name',
            title: '指标名称',
            editable: {
                type: "text",
                onblur: "submit",
                showbuttons: false,
                validate: function (v) {
                    if (!v) return '姓名不能为空';
                }
            }
        }, {
            field: 'w',
            title: '指标权重',
            editable: {
                type: "text",
                onblur: "submit",
                showbuttons: false,
                validate: function (v) {
                    if (!v) return '姓名不能为空';
                }
            }
        }, {
            field: 'rangeL',
            title: '指标最小值',
            editable: {
                type: "text",
                onblur: "submit",
                showbuttons: false,
                validate: function (v) {
                    if (!v) return '姓名不能为空';
                }
            }
        }, {
            field: 'rangeR',
            title: '指标最大值',
            editable: {
                type: "text",
                onblur: "submit",
                showbuttons: false,
                validate: function (v) {
                    if (!v) return '姓名不能为空';
                }
            }
        }, {
            field: 'des',
            title: '指标说明',
            editable: {
                type: "text",
                onblur: "submit",
                showbuttons: false,
            }
        },{
            field: 'opt',
            title: '操作',
            formatter:function (v,row) {
                return '<button class="btn btn-xs btn-white btn-success bigger" onclick="refreshEvaIndexRange('+row.indexID+')">\n' +
                    '                                        <i class="ace-icon fa fa-refresh"></i>\n' +
                    '                                        自动调整指标的区间\n' +
                    '                                    </button>'
            }
        }],
        pagination: true,//启用分页
        pageNumber: 1,//默认是第一页
        pageSize: 10,
        pageList: [10, 20,50],
        uniqueId:"indexID",
        onEditableSave: function (field, row, oldValue, $el) {
            $.ajax({
                url:API.editEvaIndex+"/"+row.indexID,
                type:"PUT",
                data:row,
                success:function (data) {
                    if(data.code==1){

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
    $("#diyIndexTable").bootstrapTable(diyIndexTableLayout)



}

/**
 * 添加评价指标
 */
function addDiyIndex(){
    const rowData={
        name:'修改指标名称',
        des:'修改指标描述',
        w:'0',
        rangeL:'0',
        rangeR:'0'
    }
    $.ajax({
        url:API.evaIndexAddAndGet,
        type:"POST",
        data:rowData,
        success:function (data) {
            if(data.code==1){
                rowData.indexID = data.data;
                $("#diyIndexTable").bootstrapTable('append',rowData)
            }else {
                alert("创建评价指标失败")
            }
        }
    })
}

/**
 * 删除评价指标
 */
function deleteDiyIndex(){
    $("#diyIndexTable").bootstrapTable("getSelections").forEach(r=>{
        $.ajax({
            url:API.editEvaIndex+"/"+r.indexID,
            type:"DELETE",
            success:function (data) {
                if(data.code==1){
                    $("#diyIndexTable").bootstrapTable('removeByUniqueId',r.indexID)
                }
            }
        })
    });
}

/**
 * 保存自定义指标
 * @param indexID
 * @param rowData
 * @returns {string}
 */
function saveDiyIndex(rowData) {
    let flag = false;

    return flag;
}

function validateEvaInfo(v) {
    if (!$.trim(v)) {
        return '不能为空';
    }
}
function updateEvaInfo(params) {
    let d = new $.Deferred();
    const data = {};
    data[params.name]=params.value
    //async saving data in js model
    $.ajax({
        url: API.editEva,
        type: 'PUT',
        async: true,
        data:data,
        success: function (data) {
            if(data.code==1){
                d.resolve();
            }else{
                d.reject()
            }
        }
    });
    return d.promise();
}

function refreshEvaIndexRange(rowIndex) {
    $.ajax({
        url:API.editEvaIndex+"/"+rowIndex+"/refreshRange",
        type:"PUT",
        success:function (data) {
            if(data.code==1){
                $("#diyIndexTable").bootstrapTable('removeByUniqueId',r.indexID)
            }
        }
    })
}

