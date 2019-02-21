let USER_RESULT = {};

const RESULT_ELS = {
    toolResultModal: '#tool-result-modal',//工具数据MODAL
    toolProjectTable: '#tool-project-table',//工具数据表格
    curNodeResultTable: '#cur-node-result-table',//节点结果数据表格
    curNodeResultInfo: '#cur-node-result-info',//节点结果数据Info
    curNodeResultInfo2:'#cur-node-result-info-2'
}
$(function () {
    $(RESULT_ELS.toolProjectTable).bootstrapTable({
        height: 500,
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        pageList: [10, 20], // 设置页面可以显示的数据条数
        pageSize: 10, // 页面数据条数
        pageNumber: 1, // 首页页码,
        uniqueId:'id',
        columns: [{
            field: 'i',
            title: '项目编号',
            formatter: function (v,r,i) {
                return i+1;
            }
        },{
            field: 'id',
            title: '项目编号',
            visible:false,

        }, {
            field: 'projectName',
            title: '项目名',

        }, {
            field: 'createTime',
            title: '创建时间',
            formatter: timeFormatter
        }, {
            field: 'editTime',
            title: '更新时间',
            formatter: timeFormatter
        }, {
            field: 'op',
            title: '操作',
            formatter:function (value,row,index) {
                //绑定按钮
                const $div = $('<div/>').attr("row-id", row.id);
                document.createElement("div");
                const $preview = $('<button/>').addClass('btn btn-minier tool-project-preview').text('预览');
                const $binding = $('<button/>').addClass('btn btn-minier').text('未绑定');
                if(row.id==CUR_NODE['myResult'].resultID){
                    $binding.addClass("btn-success tool-project-unbind").text("已绑定");
                }else {
                    $binding.addClass(' btn-primary tool-project-bind').text('未绑定')
                }

                $div.append($preview).append($binding)
                return $div[0].outerHTML;
            }
        }],
        onPostBody:function () {
            const $table = $(RESULT_ELS.toolProjectTable)
            $("button.tool-project-preview").click(function () {
                let id = parseInt($(this).parent().attr("row-id"))
                showPreviewModal( $table.bootstrapTable('getRowByUniqueId',id).appResult);
            })
            $("button.tool-project-bind").click(function () {
                let id = parseInt($(this).parent().attr("row-id"))
                bindingAppResult(id, PROJECT_ID, $table.bootstrapTable('getRowByUniqueId',id).projectName, CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length));
            })
            $("button.tool-project-unbind").click(function () {
                unbindingResult();
            })

        }
    })
    $(RESULT_ELS.curNodeResultTable).bootstrapTable({
        // rowStyle:function(row,index){
        //     if(row.resultID==CUR_NODE['myResult'].resultID){
        //         return 'info'
        //     }
        // },
        columns: [{
            field: 'userID',
            title: '用户ID',

        }, {
            field: 'resultName',
            title: '项目名',

        }, {
            field: 'editTime',
            title: '更新时间',
            formatter: timeFormatter
        }, {
            field: 'message',
            title: '信息',
        }, {
            field: 'state',
            title: '状态',
            formatter: function (value, row, index) {
                //当前状态
                let state = STATE_DIV[value];
                //如果当前状态不能直接修改，那么直接返回span标签
                if (state.li.length == 0) {
                    return '<span class="label arrowed">' + state.text + '</span>'
                }
                //如果当前状态可以修改，那么返回dropDown
                const $btn = $("<button/>")
                    .addClass("btn btn-sm dropdown-toggle")
                    .addClass(state.style)
                    .attr({"data-toggle": "dropdown", "aria-expanded": "fasle"})
                    .text(state.text)
                    .append('<span class="ace-icon fa fa-caret-down icon-on-right"/>');
                let $ul = $("<ul></ul>").addClass("dropdown-menu");
                for (let i in state.li) {
                    $ul.append($("<li/>").append($("<a/>").addClass('result-state-change-btn')
                        .text(state.li[i].text)
                        .attr({
                            'result-state': state.li[i].state,
                            'result-index': index,
                        })))
                }
                const $btnView = $("<button/>").addClass("btn btn-sm btn-link result-preview-btn").text("查看").attr('result-key', row.resultKey)
                const $div = $("<div/>").addClass("btn-group").append($btn).append($ul).append($btnView);
                return $div[0].outerHTML;
            }
        },],
        height: 400,
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        pageList: [5, 10], // 设置页面可以显示的数据条数
        pageSize: 5, // 页面数据条数
        pageNumber: 1, // 首页页码,
        onPostBody: function (data) {
            $('a.result-state-change-btn').on('click', function () {
                changeResultState(this.getAttribute('result-state'), this.getAttribute('result-index'))
            })
            $('button.result-preview-btn').on('click', function () {
                viewAppResult(this.getAttribute('result-key'))
            })
        }
    });
    $(RESULT_ELS.toolResultModal).on({
        'show.bs.modal': function () {
            refreshProjectTable()

        },
        'hide.bs.modal': function () {
            refreshNodeRow()
        }
    })
})
/**
 * @method
 * @desc 加载当前阶段的结果列表
 */
function loadCurStepResult() {
    //如果如果该阶段没有绑定APP,那么没有数据加载
    if (CUR_NODE.appPath == undefined) {
        $(RESULT_ELS.curNodeResultInfo).show();
        $(RESULT_ELS.curNodeResultInfo2).hide();
        return;
    }
    $(RESULT_ELS.curNodeResultInfo).hide();
    $(RESULT_ELS.curNodeResultInfo2).show();
    //初始化显示结果的表格
    CUR_NODE['results'] = [];
    //使用Promise实现异步,完成结果数据的更新
    let bResultMap = new Map();
    let resultKeys = [];
    let bResultMap2 = new Map();
    let disableResultArr = [];
    let outOfDateResultArr = [];
    getNodeResultData().then(
        function (data) {
            //已经绑定数据的nodeResultMap
            //已经绑定数据的key数组
            data.forEach(result => {
                if (result.resultKey != undefined && result.resultKey.trim().length > 0) {
                    resultKeys.push(result.resultKey);
                    bResultMap.set(result.resultKey, result);
                } else {
                    CUR_NODE['results'].push(result);
                }
            })
            if(resultKeys.length>0){
                return getToolProjectDataKey(resultKeys);
            }
            return new Promise(((resolve, reject) => resolve([])))

        },
        function () {
            return new Promise(((resolve, reject) => reject()))

        }
    ).then(function(projectArr){
        for (let i = 0; i < projectArr.length; i++) {
            //同时填充新的结果
            bResultMap2.set(projectArr[i].resultKey, projectArr[i]);
        }
        bResultMap.forEach((result, resultKey) => {
            if (!bResultMap2.has(resultKey)) {
                //如果之前绑定的记录缺失了，那么需要更新状态
                disableResultArr.push(result);
            } else {
                //如果之前绑定的记录又发生了修改，那么需要重新进行审阅
                if (result['state'] != 0 && result['editTime'] > bResultMap['editTime']) {
                    outOfDateResultArr.push(result);
                } else {
                    CUR_NODE['results'].push(result);
                }
            }
        })
        const promiseArr = [];
        if (disableResultArr.length > 0) {
            promiseArr.push(disableNodeResult(disableResultArr))
        }
        if (outOfDateResultArr.length > 0) {
            promiseArr.push(outDateNodeResult(outOfDateResultArr))
        }
        // promiseArr.push(getMyNodeResultData())
        return Promise.all(promiseArr);
    },function () {
        return new Promise(((resolve, reject) => reject()))
    }).then(
        function () {
            function toPercent(point) {
                return Number(point * 100).toFixed(2);
            }
            // let size = CUR_NODE['results'].length;
            // $("#cur-node-result-cnt").text(CUR_NODE['results'].length);
            // $("#cur-node-finished-cnt").text(toPercent(CUR_NODE['results'].filter(result => result.state === 3).length / size));
            // $("#cur-node-pending-cnt").text(toPercent(CUR_NODE['results'].filter(result => result.state === 1).length / size));

            $(RESULT_ELS.curNodeResultTable).bootstrapTable('load', CUR_NODE['results']);
    },function (text) {
        alert("网络错误,"+text)
    })

}

// 时间格式化函数
function timeFormatter(value) {
    return new Date(value).toLocaleString();
}

function refreshProjectTable(){
    return Promise.all([getMyNodeResultData(),getToolProjectData()]).then(function (data) {
        $(RESULT_ELS.toolProjectTable).bootstrapTable('load', data[1]);
    })
}

/**
 * 向后台请求节点结果数据
 * @returns {Promise<any>}
 */
function getNodeResultData(){
    return new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeResult,
            type: "GET",
            data: {
                projectID: PROJECT_ID,
                nodeIndex: CUR_NODE.nodeIndex
            },
            success: function (data) {
                if (data.code == 1) {
                    //请求成功以后
                    resolve(data.data);
                } else {
                    reject();
                }
            },
            error: function () {
                reject();
            }
        })
    }))
}
/**
 * 通过key向projectManager请求数据
 * @returns {Promise<any>}
 */
function getToolProjectDataKey(resultKeys) {
    return new Promise((resolve,reject)=>{
        $.ajax({
            url: API.appProjectData,
            type: "GET",
            data: {
                toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),
                tProjectID: PROJECT_ID,
                resultKeys: resultKeys
            },
            success: function (data) {
                if (data.state) {

                    resolve(data.content)
                }else{
                    reject()
                }
            },
            error:function () {
                reject("获取最新的项目信息错误")
            }
        })
    })
}

/**
 * 向后台设置节点结果数据失效
 */
function disableNodeResult(disableResultArr){
    return new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeResultDisable,
            type: "PUT",
            data: {
                projectID: PROJECT_ID,
                nodeIndex: CUR_NODE.nodeIndex,
                userIDList: disableResultArr.map(result => result.username).join()
            },
            success: function (data) {
                if(data.code==1){
                    CUR_NODE['results'] = CUR_NODE['results'].concat(data);
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("设置数据失效错误");
            }
        })
    }))
}

/**
 * 向后台设置节点结果数据过期
 */
function outDateNodeResult(outOfDateResultArr){
    return new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeResultOutDate,
            type: "PUT",
            data: {
                projectID: PROJECT_ID,
                nodeIndex: CUR_NODE.nodeIndex,
                userIDList: outOfDateResultArr.map(result => result.username).join()
            },
            success: function (data) {
                if(data.code==1){
                    CUR_NODE['results'] = CUR_NODE['results'].concat(data);
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("设置结果过期错误");
            }
        })
    }))
}
/**
 * 向后台请求当前用户的节点结果数据
 * @returns {Promise<any>}
 */
function getMyNodeResultData(){
    return new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeResultMy,
            type: "get",
            data: {
                projectID: PROJECT_ID,
                nodeIndex: CUR_NODE.nodeIndex,
            },
            success: function (data) {
                if(data.code==1){
                    CUR_NODE['myResult'] = data.data
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("获取当前用户结果错误");
            }
        })
    }))
}

/**
 * 向projectManager请求APP数据
 * @returns {Promise<any>}
 */
function getToolProjectData() {
    return new Promise(((resolve, reject) => {
        $.ajax({
            type: "GET",
            url: API.projectManage,
            data: {toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length)},
            success: function (data) {
                if (data.state) {
                    resolve(data.content);
                }else {
                    reject()
                }
            },
            error:function () {
                reject()
            }
        });
    }))
}

/**
 * 向projectManager发送绑定请求
 * @param id
 * @param projectID
 * @param name
 * @param toolName
 */
function bindingAppResult(id, projectID, name, toolName) {
    $.ajax({
        url: API.appProjectBinding,
        type: "GET",
        async: false,
        data: {
            toolName: toolName,
            projectID: id,
            tProjectID: projectID,//想要绑定的项目ID
        },
        success: function (result) {
            if (result.state) {
                USER_RESULT['resultID'] = id;
                putBinding(id, name, result.content)
                refreshProjectTable();
            } else {
                alert("数据绑定失败")
            }
        }
    })
}

/**
 * 向后台发送绑定请求
 * @param projectID
 * @param projectName
 * @param resultKey
 */
function putBinding(projectID, projectName, resultKey) {
    $.ajax({
        url: API.nodeResultBinding,
        type: "PUT",
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            resultKey: resultKey,
            resultID: projectID,
            resultName: projectName
        },
        success: function (data) {
            USER_RESULT = data;
            refreshProjectTable()
        }
    })
}

/**
 * 向后台发送解绑请求
 */
function unbindingResult() {
    $.ajax({
        url: API.nodeResultUnbind,
        type: "PUT",
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
        },
        success: function (data) {
            USER_RESULT = data;
            alert("解绑成功")
            refreshProjectTable()
        }
    })

}

/**
 * 查看项目结果
 * @param resultKey
 */
function viewAppResult(resultKey) {
    $.ajax({
        url: API.appProjectData,
        type: "GET",
        data: {
            toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),
            tProjectID: PROJECT_ID,
            resultKeys: [resultKey]
        },
        success: function (data) {
            if (data.state) {
                //当请求成功后。打印请求结果
                showPreviewModal(data.content[0].appResult)
            } else {
                console.log(data.error)
            }
        }
    })

}

/**
 * 打开预览窗口
 * @param html
 */
function showPreviewModal(html) {
    const $row = $('<div class="row"/>')
    if (html == null || html == undefined || html.trim().length == 0) {
        html = "<div class=\"well\"><h1 class=\"grey lighter smaller\"><span class=\"blue bigger-125\"><i class=\"ace-icon fa fa-random\"></i></span>没有绑定数据</h1><hr><h3 class=\"lighter smaller\">使用APP后记得点击<i class=\"ace-icon fa fa-wrench icon-animated-wrench bigger-125\"></i>保存报告</h3><div class=\"space\"></div><div><h4 class=\"lighter smaller\">您可以尝试如下方式解决该问题</h4><ul class=\"list-unstyled spaced inline bigger-110 margin-15\"><li><i class=\"ace-icon fa fa-hand-o-right blue\"></i>阅读APP使用帮助</li><li><i class=\"ace-icon fa fa-hand-o-right blue\"></i>发送站内信通知对方</li></ul></div><hr><div class=\"space\"></div></div>"
    }
    $row.append($('<div class="col-sm-12"/>').append(html))
    $row.find('img').css('width','100%')

    //在打开之前隐藏其它的
    bootbox.hideAll()
    bootbox.dialog({
        title: '预览结果',
        message: $row[0].outerHTML,
        size: 'large',
        buttons: {
            cancel: {
                label: "关闭预览",
                className: 'btn-purple',
            },
            //如果有权限的话可以直接跳转到该APP查看该项目
            jump: {
                label: "跳转到APP",
                className: 'btn-success',
                callback: function () {
                    window.open('http://innovation.xjtu.edu.cn' + CUR_NODE.appPath);
                    return false;
                }
            },
            download: {
                label: "下载文档",
                className: 'btn-info',
                callback: function () {
                    alert('暂时未开发')
                    return false;
                }
            },

        }
    });

}

function showAppDataModal() {
    //在打开之前隐藏其它的
    bootbox.hideAll()
    bootbox.dialog({
        title: '绑定数据',
        message: $row[0].outerHTML,
        size: 'large',
        buttons: {
            cancel: {
                label: "关闭预览",
                className: 'btn-purple',
            },
            //如果有权限的话可以直接跳转到该APP查看该项目
            jump: {
                label: "跳转到APP",
                className: 'btn-success',
                callback: function () {
                    window.open('http://innovation.xjtu.edu.cn' + CUR_NODE.appPath);
                    return false;
                }
            },
            download: {
                label: "下载文档",
                className: 'btn-info',
                callback: function () {
                    alert('暂时未开发')
                    return false;
                }
            },

        }
    });
}
/**
 * 更新结果
 * @param result
 */
function updateResult(result) {
    $.ajax({
        url: API.nodeResult,
        type: "PUT",
        data: result,
        success: function (data) {
            loadCurStepResult();
        }
    })
}

/**
 * 修改结果状态
 * @param state
 * @param resultIndex
 */
function changeResultState(state, resultIndex) {
    CUR_NODE['results'][resultIndex].state = state;
    CUR_NODE['results'][resultIndex].editTime = undefined;
    updateResult(CUR_NODE['results'][resultIndex]);
}


