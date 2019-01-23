let TOOL_PROJECT_RESULT = {};
let USER_RESULT = {};

const RESULT_ELS = {
    toolResultModal: '#tool-result-modal',//工具数据MODAL
    toolProjectTable: '#tool-project-table',//工具数据表格
    curNodeResultTable :'#cur-node-result-table',//节点结果数据表格
    curNodeResultInfo:'#cur-node-result-info',//节点结果数据Info
}
$(function () {
    $(RESULT_ELS.toolProjectTable).bootstrapTable({
        height: 400,
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        pageList: [10, 20], // 设置页面可以显示的数据条数
        pageSize: 10, // 页面数据条数
        pageNumber: 1, // 首页页码,
    })
    $(RESULT_ELS.curNodeResultTable).bootstrapTable({
        columns: [{
            field: 'userID',
            title: '用户ID',
            formatter: function (value) {
                return "<b class=\"blue\">" + value + "</b>";
            }
        }, {
            field: 'resultName',
            title: '项目名',

        }, {
            field: 'createTime',
            title: '创建事件',
            formatter: function (value) {
                return new Date(value).toLocaleString();
            }
        }, {
            field: 'editTime',
            title: '更新时间',
            formatter: function (value) {
                return new Date(value).toLocaleString();
            }
        }, {
            field: 'message',
            title: '信息',
        }, {
            field: 'state',
            title: '状态',
            formatter: function (value, row, index) {
                if(row.resultKey==null){
                    return '<span class="label arrowed">未绑定数据</span>'
                }
                let state = STATE_DIV[value];
                if (state == undefined) {
                    state = STATE_ERROR;
                    value = -1;
                }
                const btn = $("<button></button>")
                    .addClass("btn btn-sm dropdown-toggle")
                    .addClass(state.button['style'])
                    .attr({"data-toggle": "dropdown", "aria-expanded": "fasle"})
                    .text(state.button.text);
                let ul;
                if (state.li != undefined) {
                    ul = $("<ul></ul>").addClass("dropdown-menu");
                    for (let i in state.li) {
                        ul.append(
                            $("<li></li>")
                                .append($("<a></a>")
                                    .text(state.li[i].text).attr("onclick", "changeResultState(" + state.li[i].state + "," + index + ")")
                                )
                        )
                    }
                    btn.append($("<span></span>")
                        .addClass("ace-icon fa fa-caret-down icon-on-right")
                    )
                }
                const div = $("<div></div>")
                    .addClass("btn-group");
                const btnView = $("<button></button>").addClass("btn btn-sm btn-link").text("查看").attr("onclick", "viewAppResult('" + row.resultKey + "')")
                div.append(btn)
                div.append(ul)
                if (value > 0) {
                    div.append(btnView);
                }
                return div[0].outerHTML;
            }
        },],
        height: 400,
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        pageList: [5, 10], // 设置页面可以显示的数据条数
        pageSize: 5, // 页面数据条数
        pageNumber: 1, // 首页页码,
    });
    $(RESULT_ELS.toolResultModal).on({
        'show.bs.modal': function () {
            $.ajax({
                url: API.projectManage,
                type: "GET",
                async: true,
                data: {toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length)},
                success: function (data) {
                    if (data.state) {
                        data = data.content;
                        //对APP数据进行处理
                        $(RESULT_ELS.toolProjectTable).bootstrapTable('load', data);
                        TOOL_PROJECT_RESULT = new Map();
                        for (let obj in data) {
                            TOOL_PROJECT_RESULT.set(data[obj].id, data[obj])
                        }
                    }
                }
            });
            // $(RESULT_ELS.toolProjectTable).bootstrapTable("refreshOptions", {
            //     height: 400,
            //     striped: true,  //表格显示条纹，默认为false
            //     pagination: true, // 在表格底部显示分页组件，默认false
            //     pageList: [10, 20], // 设置页面可以显示的数据条数
            //     pageSize: 10, // 页面数据条数
            //     pageNumber: 1, // 首页页码,
            //     ajax: function (request) {
            //         $.ajax({
            //             type: "GET",
            //             url: API.projectManage,
            //             data: {toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length)},
            //             success: function (data) {
            //                 //获取APP数据
            //                 data = data.content;
            //                 request.success({
            //                     row: data
            //                 });
            //
            //                 $(".tool-project-preview .preview").click(function () {
            //                     let id = parseInt($(this).parent().attr("row-id"))
            //                     previewAppResult(TOOL_PROJECT_RESULT.get(id).appResult, true);
            //                 })
            //                 $(".tool-project-preview .btn-primary").click(function () {
            //                     let id = parseInt($(this).parent().attr("row-id"))
            //                     bindingAppResult(id,PROJECT_ID,TOOL_PROJECT_RESULT.get(id).projectName,CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length));
            //                 })
            //                 $(".tool-project-preview .btn-success").click(function () {
            //                     unbindingResult();
            //                 })
            //             },
            //         });
            //     },
            // })
        },
        'hide.bs.modal': function () {

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
        $(RESULT_ELS.curNodeResultTable).hide();
        return;
    }
    $(RESULT_ELS.curNodeResultInfo).hide();
    $(RESULT_ELS.curNodeResultTable).show();
    //初始化显示结果的表格
    //向后台发送服务器的result请求，获取用户名和对应的结果数据
    let bindedResult = new Map();//已经绑定的结果字典
    let resultKeys = [];
    $.ajax({
        url: API.nodeResult,
        type: "GET",
        async: false,
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex
        },
        success: function (data) {
            if (data.code == 1) {
                data = data.data;
                CUR_NODE['results'] = [];
                data.forEach(result => {
                    if (result.resultKey != undefined && result.resultKey.trim().length > 0) {
                        resultKeys.push(result.resultKey);
                        bindedResult.set(result.resultKey, result);
                    } else {
                        CUR_NODE['results'].push(result);
                    }
                })
            }
        }
    })

    let newbindingResults = new Map();
    //向projectManager发送请求，已绑定数据的最新状态
    if (resultKeys.length > 0) {
        $.ajax({
            url: API.appProjectBinding + CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),
            type: "GET",
            async: false,
            data: {
                tProjectID: PROJECT_ID,
                resultKeys: resultKeys
            },
            success: function (data) {
                if (data.state) {
                    //当请求成功后。打印请求结果
                    console.log(data.content)
                    var projectArr = data.content;
                    for (let i = 0; i < projectArr.length; i++) {
                        //同时填充新的结果
                        newbindingResults.set(projectArr[i].resultKey, projectArr[i]);
                    }
                } else {
                    console.log(data.error)
                }
            }
        })

        let disableResultArr = [];
        let outOfDateResultArr = [];
        //对比绑定记录的状态变化，更新状态信息
        bindedResult.forEach((value, resultKey) => {
            if (!newbindingResults.has(resultKey)) {
                disableResultArr.push(value);
            } else {
                if (value['state'] != 0 && value['editTime'] > bindedResult['editTime']) {
                    outOfDateResultArr.push(value);
                } else {
                    CUR_NODE['results'].push(value);
                }
            }
        })
        if (disableResultArr.length > 0) {
            $.ajax({
                url: API.nodeResultDisable,
                type: "PUT",
                async: false,
                data: {
                    projectID: PROJECT_ID,
                    nodeIndex: CUR_NODE.nodeIndex,
                    usernameList: disableResultArr.map(result => result.username).join()
                },
                success: function (data) {
                    CUR_NODE['results'] = CUR_NODE['results'].concat(data);
                }
            })
        }
        if (outOfDateResultArr.length > 0) {
            $.ajax({
                url: API.nodeResultOutDate,
                type: "PUT",
                async: false,

                data: {
                    projectID: PROJECT_ID,
                    nodeIndex: CUR_NODE.nodeIndex,
                    usernameList: outOfDateResultArr.map(result => result.username).join()
                },
                success: function (data) {
                    CUR_NODE['results'] = CUR_NODE['results'].concat(data);

                }
            })
        }

    }

    function toPercent(point) {
        let str = Number(point * 100).toFixed(2);
        return str;
    }

    let size = CUR_NODE['results'].length;
    $("#cur-node-result-cnt").text(CUR_NODE['results'].length);
    $("#cur-node-finished-cnt").text(toPercent(CUR_NODE['results'].filter(result => result.state === 3).length / size));
    $("#cur-node-pending-cnt").text(toPercent(CUR_NODE['results'].filter(result => result.state === 1).length / size));
    $("#cur-node-result-table").bootstrapTable('load', CUR_NODE['results']);


}


function toolProjectPreView(value, row, index) {
    //预览按钮
    //绑定按钮
    var div = document.createElement("div");
    $(div).attr("row-id", row.id);
    $(div).addClass("tool-project-preview")
    var preview = document.createElement("button");
    $(preview).addClass("btn btn-minier preview").text("预览");
    var binding = document.createElement("button");
    $(binding).addClass("btn btn-minier btn-primary");
    if (USER_RESULT['resultKey'] && USER_RESULT['resultID'] == row.id) {
        //如果该数据已绑定,显示已绑定,点击后进行解绑
        $(binding).removeClass("btn-primary")
            .addClass("btn-success")
            .text("已绑定");
    } else {
        $(binding).text("未绑定");
    }
    $(div).append($(preview)).append($(binding))
    return div.outerHTML;
}

function bindingAppResult(id, projectID, name, toolName) {
    $.ajax({
        url: API.appProjectBinding + toolName,
        type: "GET",
        async: false,
        data: {
            projectID: PROJECT_ID,//当前模板的ID
            id: id,//想要绑定的项目ID
        },
        success: function (result) {
            if (result.state) {
                USER_RESULT['resultID'] = id;
                putBinding(id, name, result.content)
            } else {
                alert("数据绑定失败")
            }
        }
    })
}

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
            $("#tool-project-table").bootstrapTable("refresh");
        }
    })
}

function unbindingResult() {
    $.ajax({
        url: "/templates/api/project/node/result",
        type: "PUT",
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
        },
        success: function (data) {
            USER_RESULT = data;
            alert("解绑成功")
            $("#tool-project-table").bootstrapTable("refresh");
        }
    })

}


function previewAppResult(text, resultModalIsOpen) {
    if (resultModalIsOpen) {
        $("#tool-result-modal").modal("hide");
        $("#tool-result-view-modal").on("hidden.bs.modal", function () {
            $("#tool-result-modal").modal("show");
        })
    } else {
        $("#tool-result-view-modal").unbind("hidden.bs.modal")
    }
    if (text == undefined || text.trim().length == 0) {
        text = "<div class=\"well\"><h1 class=\"grey lighter smaller\"><span class=\"blue bigger-125\"><i class=\"ace-icon fa fa-random\"></i></span>没有绑定数据</h1><hr><h3 class=\"lighter smaller\">使用APP后记得点击<i class=\"ace-icon fa fa-wrench icon-animated-wrench bigger-125\"></i>保存报告</h3><div class=\"space\"></div><div><h4 class=\"lighter smaller\">您可以尝试如下方式解决该问题</h4><ul class=\"list-unstyled spaced inline bigger-110 margin-15\"><li><i class=\"ace-icon fa fa-hand-o-right blue\"></i>阅读APP使用帮助</li><li><i class=\"ace-icon fa fa-hand-o-right blue\"></i>发送站内信通知对方</li></ul></div><hr><div class=\"space\"></div></div>"
    }

    $("#preViewRow").html(text)

    $("#tool-result-view-modal div.modal-dialog").css({
        "width": document.body.clientWidth - 100,
    });

    $("#tool-result-view-modal").modal("show");
}

function viewAppResult(resultKey) {
    $.ajax({
        url: API.appProjectBinding + CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),
        type: "GET",
        async: false,
        data: {
            projectID: PROJECT_ID,
            resultKeys: [resultKey]
        },
        success: function (data) {
            if (data.state) {
                //当请求成功后。打印请求结果
                previewAppResult(data.content[0].appResult, false)
            } else {
                console.log(data.error)
            }
        }
    })
}

function closePreView() {
    $("#tool-project-data-preview").find("[data-action=fullscreen]")[0].click();
    $("#tool-project-data-preview").addClass("hide");
    $("#preViewRow").html("");
}

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

function changeResultState(state, resultIndex) {
    CUR_NODE['results'][resultIndex].state = state;
    CUR_NODE['results'][resultIndex].editTime = undefined;
    updateResult(CUR_NODE['results'][resultIndex], viewNode);
}


