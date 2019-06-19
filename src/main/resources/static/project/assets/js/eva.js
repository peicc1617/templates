$(()=>{
    initDiyIndexTable();
    initImportTable();
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
                validate: function (v) {
                    if (!v) return '指标名称不能为空';
                }
            }
        }, {
            field: 'w',
            title: '指标权重',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标权重不能为空';
                }
            }
        }, {
            field: 'rangeL',
            title: '指标最小值',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标范围不能为空';
                }
            }
        }, {
            field: 'rangeR',
            title: '指标最大值',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标范围不能为空';
                }
            }
        }, {
            field: 'des',
            title: '指标说明',
            editable: {
                type: "text",
            }
        },{
            field: 'creator',
            title: '创建者'
        }],
        pagination: true,//启用分页
        pageNumber: 1,//默认是第一页
        pageSize: 10,
        pageList: [10, 20,50],
        uniqueId:"indexID",
        onEditableSave: function (field, row, oldValue, $el) {
            if(field=='w'){
                $.ajax({
                    url:`${API.editIndexW}${row.indexID}/w`,
                    type:"PUT",
                    data:{
                        w:row[field]
                    },
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
            }else {
                $.ajax({
                    url:`${API.editIndex}/${row.indexID}`,
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

        },
        onEditableShown(field, row, $el,t){
            if(!row.canEdit){
                t.disable();
                t.hide();
            }
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
        rangeL:'0',
        rangeR:'1',
        canEdit:true
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
    const rArr = $("#diyIndexTable").bootstrapTable("getSelections");
    const indexIDs = rArr.map(r=>r.indexID)
    $.ajax({
        url:API.importIndex,
        type:"DELETE",
        data:{
            indexIDs:indexIDs
        },
        success:function (data) {
            if(data.code==1){
                $("#diyIndexTable").bootstrapTable('refresh')
                $("#import-table").bootstrapTable('refresh')
            }
        }
    })

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

/**
 * 刷新
 * @param rowIndex
 */
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


/**
 * 初始化导入指标的表格
 */
function initImportTable() {
    let importTableLayout = {
        url:API.getAllIndex,
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
                validate: function (v) {
                    if (!v) return '指标名称不能为空';
                }
            }
        }, {
            field: 'rangeL',
            title: '指标最小值',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标范围不能为空';
                }
            }
        }, {
            field: 'rangeR',
            title: '指标最大值',
            editable: {
                type: "text",
                validate: function (v) {
                    if (!v) return '指标范围不能为空';
                }
            }
        }, {
            field: 'des',
            title: '指标说明',
            editable: {
                type: "text",
            }
        },{
            field: 'creator',
            title: '创建者'
        },{
            field: 'op',
            title: '操作',
            formatter:function (value,row,index) {
                if(row.canEdit){
                    return                `<button class="btn btn-xs btn-white btn-danger bigger" onclick="deleteIndex(${row.indexID})"><i class="ace-icon fa fa-trash"></i>删除</button>`

                }
            }
        }],
        pagination: true,//启用分页
        pageNumber: 1,//默认是第一页
        pageSize: 10,
        pageList: [10, 20,50],
        uniqueId:"indexID",
        onEditableSave: function (field, row, oldValue, $el) {
            $.ajax({
                url:`${API.editIndex}/${row.indexID}`,
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
        },
        onEditableShown(field, row, $el,t){
            if(!row.canEdit){
                t.disable();
                t.hide();
            }
        }
    }
    $("#import-table").bootstrapTable(importTableLayout)

}

/**
 * 导入选中的指标
 */
function importIndex() {
    const rArr = $("#import-table").bootstrapTable("getSelections");
    const indexIDs = rArr.map(r=>r.indexID)
    $.ajax({
        url:API.importIndex,
        type:"PUT",
        data:{
            indexIDs:indexIDs
        },
        success:function (data) {
            if(data.code==1){
                $("#diyIndexTable").bootstrapTable('append',rArr)
                $("#import-table").bootstrapTable('refresh')
            }
        }
    })
}

/**
 * 删除指标
 */
function deleteIndex(indexID) {
    $.ajax({
        url:`${API.deleteIndex}${indexID}`,
        type:"DELETE",
        success:function (data) {
            if(data.code==1){
                alert("删除成功")
                $("#import-table").bootstrapTable('refresh')
            }
        }
    })
}

