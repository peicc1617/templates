var TOOL_PROJECT_RESULT = {}
var USER_RESULT;
$(function () {
    $('#tool-result-modal').on('show.bs.modal', function (e) {
        $("#tool-project-table").bootstrapTable("refreshOptions", {
            height: 400,
            striped: true,  //表格显示条纹，默认为false
            pagination: true, // 在表格底部显示分页组件，默认false
            pageList: [10, 20], // 设置页面可以显示的数据条数
            pageSize: 10, // 页面数据条数
            pageNumber: 1, // 首页页码,
            ajax: function (request) {
                $.ajax({
                    type: "GET",
                    url: "/projectManager/api/v1/project",
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    data: {toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length)},
                    success: function (data) {
                        data = data.content;
                        request.success({
                            row: data
                        });
                        for (let obj in data) {
                            TOOL_PROJECT_RESULT[data[obj].id] = data[obj].appResult;
                        }
                        $('#tool-project-table').bootstrapTable('load', data);

                        $(".tool-project-preview .preview").click(function () {
                            previewAppResult(TOOL_PROJECT_RESULT[$(this).parent().attr("row-id")], true);
                        })
                        $(".tool-project-preview .btn-primary").click(function () {
                            bindingAppResult(parseInt($(this).parent().attr("row-id")),PROJECT_ID,CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length));
                        })
                        $(".tool-project-preview .btn-success").click(function () {
                            unbindingResult();
                        })
                    },
                    error: function () {
                        alert("错误");
                    }
                });
            },
        })
    })
    $('#tool-result-modal').on('hide.bs.modal', function (e) {
        viewNode()
    });
})

/**
 * @method
 * @desc 加载当前阶段的结果列表
 */
function loadCurStepResult() {
    //如果如果该阶段没有绑定APP,那么没有数据加载
    if (CUR_NODE.appPath == undefined) {
        $("#cur-step-result-info").removeClass("hidden");
        $("#cur-step-result-table").bootstrapTable("destroy");
        return;
    } else {
        $("#cur-step-result-info").addClass("hidden");
        //初始化显示结果的表格
        $("#cur-step-result-table").bootstrapTable({
            columns: [{
                field: 'username',
                title: '用户名',
                formatter: function (value, row, index) {
                    if (value == USERNAME) {
                        USER_RESULT = row;
                        return "<b class=\"green\">" + value + "</b>";
                    }
                    return "<b class=\"blue\">" + value + "</b>";
                }
            }, {
                field: 'projectName',
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
                    const btnView = $("<button></button>").addClass("btn btn-sm btn-link").text("查看").attr("onclick", "viewAppResult(" + index + ")")
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

    }
    //向后台发送服务器的result请求，获取用户名和对应的结果数据
    $.ajax({
        url: "/templates/api/project/node/result",
        type: "GET",
        async: false,
        data: {
            id: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex
        },
        success: function (data) {
            CUR_NODE['results'] = data;
        }
    })
    //判断用户结果中有哪些是已经绑定数据的。
    let hasbindingResults = new Map();//已经绑定的结果字典
    /*
    * 放弃字符串拼接，修改为使用JSON格式传递
    * */
    let resultKeys = [];
    // var resultKeys = "";
    for (let i in CUR_NODE['results']) {
        if (CUR_NODE['results'][i].resultKey != undefined && CUR_NODE['results'][i].resultKey.trim().length > 0) {
            let key = CUR_NODE['results'][i].resultKey;
            // resultKeys+=(key+";");
            resultKeys.push(key);
            hasbindingResults.set(key, CUR_NODE['results'][i]);
        }
    }
    let newbindingResults = new Map();
    //向projectManager发送请求，已绑定数据的最新状态
    $.ajax({
        url: "/projectManager/api/v1/project/binding",
        type: "GET",
        async: false,
        data: {
            projectID:PROJECT_ID,
            toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),
            // resultKeys:resultKeys
            resultKeys: JSON.stringify(resultKeys)
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
    //对比绑定记录的状态变化，更新状态信息
    hasbindingResults.forEach((value, resultKey) => {
        let isChange = false;
        if (!newbindingResults.has(resultKey)) {
            value['message'] = "原数据已丢失，请重新绑定。";
            value['resultKey'] = ""
            value['state'] = 0;
        } else {
            if(value['state']!=0 && value['editTime']>hasbindingResults['editTime']){
                value['message'] = "数据已更新，请重新审阅。";
                value['state'] = 1;
            }
            value['projectName'] = newbindingResults.get(resultKey)['projectName'];
            value['appResult'] = newbindingResults.get(resultKey)['appResult'];
        }
        if(isChange){
            updateResult(value)
        }
    })
    $("#cur-step-result-table").bootstrapTable('load', CUR_NODE['results']);

}

//创建时间格式化函数
function timeFormatter(value, row, index) {
    if (value == undefined) return "无";
    var oDate = new Date(value);
    return oDate.toLocaleDateString() + ',' + oDate.getHours() + ':' + oDate.getMinutes()
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
    if (USER_RESULT.resultKey && USER_RESULT['resultID'] == row.id) {
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

function bindingAppResult(id,projectID,toolName){
    $.ajax({
        url: "/projectManager/api/v1/project/binding",
        type: "POST",
        async: false,
        data: {
            toolName: toolName,//工具名
            projectID:PROJECT_ID,//当前模板的ID
            id:id,//想要绑定的项目ID

        },
        success: function (result) {
            if (result.state) {
                USER_RESULT['resultKey'] = result.content;
                USER_RESULT.state = 1;
                USER_RESULT['resultID'] = id;
                updateResult(USER_RESULT,function () {
                    $("#tool-project-table").bootstrapTable("refresh");
                });
            } else {
                alert("数据绑定失败")
            }
        }
    })
}

function unbindingResult(){
    USER_RESULT.state = 0;
    USER_RESULT['resultKey'] = null;
    USER_RESULT['resultID']=null;
    updateResult(USER_RESULT,function () {
        alert("解绑成功")
    });
}

//
// //绑定APP的数据
// function bindingAppResult(projectID, newID, newResultKey) {
//     //向projectManager发送请求,绑定新的记录
//     var is = false;
//     $.ajax({
//         url: "/projectManager/api/v1/project/binding",
//         type: "POST",
//         async: false,
//         data: {
//             toolName: CUR_NODE.appPath.substring(1, CUR_NODE.appPath.length),//toolName
//             // oldResultKey: oldResultKey,
//             // newID: newID,
//             // resultKey: newResultKey
//             projectID:PROJECT_ID,//当前模板的ID
//             id:1,
//
//         },
//         success: function (result) {
//             if (result.state) {
//                 is = true;
//                 USER_RESULT['resultKey'] = newResultKey;
//                 USER_RESULT['resultKey'] = newResultKey;
//             } else {
//                 alert("数据绑定失败")
//             }
//         }
//     })
//
//     if (is) {
//         ANALYSIS_MSG.type = MSG_TYPE_TABLE.RESULT
//         var msg = "";
//         if (newResultKey == "") {
//             USER_RESULT.state = 0;
//             USER_RESULT.projectName = ""
//             msg = "数据解绑成功"
//             ANALYSIS_MSG.content = "解绑了位于[" + CUR_NODE.name + "]的数据"
//         } else {
//             msg = "数据绑定成功"
//             USER_RESULT.projectName = ""
//             USER_RESULT.state = 1;
//             ANALYSIS_MSG.content = "在节点[" + CUR_NODE.name + "]绑定了的数据"
//
//         }
//         updateResult(USER_RESULT);
//         $("#tool-project-table").bootstrapTable("refresh");
//         // $.ajax({
//         //     url: "/templates/api/project/node/result",
//         //     type: "POST",
//         //     async: false,
//         //     data: {
//         //         _method: "PUT",
//         //         result: JSON.stringify(USER_RESULT)
//         //     },
//         //     success: function (result) {
//         //         alert(msg)
//         //         Analysis.send();
//         //
//         //     }
//         // })
//     }
// }

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

function viewAppResult(resultIndex) {
    // $('#tool-result-modal').modal("show")
    previewAppResult(CUR_NODE['results'][resultIndex]['appResult'], false)
}

function closePreView() {
    $("#tool-project-data-preview").find("[data-action=fullscreen]")[0].click();
    $("#tool-project-data-preview").addClass("hide");
    $("#preViewRow").html("");
}

function updateResult(result,callFunction) {
    $.ajax({
        url: "/templates/api/project/node/result",
        type: "POST",
        data: {
            _method:"PUT",
            result: JSON.stringify(result)
        },
        success: function (data) {
            ANALYSIS_MSG.type = MSG_TYPE_TABLE.RESULT
            ANALYSIS_MSG.content = "用户[" + result.username + "]位于节点[" + CUR_NODE.name + "]的数据状态已变更"
            Analysis.send();
            console.log(data);
            callFunction();
        }
    })
}

function changeResultState(state,resultIndex) {
    CUR_NODE['results'][resultIndex].state=state;
    if(state==2){
        CUR_NODE['results'][resultIndex].message="审阅状态变为待修改，请及时处理"
    }else {
        CUR_NODE['results'][resultIndex].message=""
    }
    updateResult(CUR_NODE['results'][resultIndex],viewNode);
}

function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    return s.join("");
}
