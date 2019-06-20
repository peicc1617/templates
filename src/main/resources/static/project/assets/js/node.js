

const NODE_ELS = {
    nodeRoleManagerModal: "#node-role-manager-modal",
    nodeRoleTable:"#nodeRoleTable",
}

/**
 * 初始化js事件
 */
$(document).ready(function () {
    $('#state-change-btn').on('click', finish);
    $("#cur-node-name-href").editable({
        type: 'text',
        title: '修改名称',
        name: 'nodeName',
        url: updateNodeHref,
        validate: validateEmpty
    })
    $("#cur-node-description-href").editable({
        type: 'textarea',
        title: '修改描述',
        name: 'nodeDesc',
        url: updateNodeHref,
        validate: validateEmpty
    })
    $("#cur-node-goal-href").editable({
        type: 'textarea',
        title: '修改目标',
        name: 'goal',
        url: updateNodeHref,
        validate: validateEmpty
    })

    function updateNodeHref(params) {
        var d = new $.Deferred();
        if (params.value === 'abc') {
            return d.reject('error message'); //returning error via deferred object
        } else {
            data = {
                projectID: PROJECT_ID,
                nodeIndex: CUR_NODE.nodeIndex
            }
            data[params.name] = params.value
            //async saving data in js model
            $.ajax({
                url: API.editNode + params.name,
                type: 'PUT',
                async: true,
                data: data,
                success: function () {
                    CUR_NODE[params.name] = params.value;
                    d.resolve();
                }

            })
            return d.promise();
        }
    }

    function validateEmpty(value) {
        if (!$.trim(value)) {
            return '不能为空';
        }
    }

    $( NODE_ELS.nodeRoleTable).bootstrapTable({

        columns: [{
            field: 'userID',
            title: '用户ID',

        },{
            field: 'nickName',
            title: '用户名',

        }, {
            field: 'roleType',
            title: '用户权限',
            editable: {
                type: 'select',
                title: '修改用户在节点内的权限',
                source: NODE_ROLE
            }
        }],
        height: 500,
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        pageList: [5, 10], // 设置页面可以显示的数据条数
        pageSize: 5, // 页面数据条数
        pageNumber: 1, // 首页页码,
        onEditableSave(field, row, oldValue, $el) {
            data = row;
            $.ajax({
                url: API.nodeRole,
                method: "PUT",
                data: data,
                async: false,
                success: function (data) {
                    alert("修改成功")
                },
                error: function () {
                    row[field] = oldValue;
                    alert("修改失败")
                },
            })
        }
    })

    $(NODE_ELS.nodeRoleManagerModal).on({
        'show.bs.modal': function () {
            refreshNodeRoleTable()
        },
        'hide.bs.modal': function () {
            loadCurStepResult()
        }
    })
});

function refreshNodeRoleTable(){
    getNodeRoleList().then(function (data) {
        $( NODE_ELS.nodeRoleTable).bootstrapTable('load',data);
    },function () {
        
    })
}

function getNodeRoleList(){
    return new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeRoleList,
            type: "GET",
            data: {
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
 *
 * @param node
 */
function refreshNodeRow() {
    //显示区域
    $("#stepInfoRow").attr("style", "display:none");
    $("#nodeInfoRow").attr("style", "display:display");
    //节点的名称
    $("#cur-node-name-href").editable("setValue", CUR_NODE.nodeName);
    //节点的描述
    $("#cur-node-description-href").editable("setValue", CUR_NODE.nodeDesc);


    $('#date-spinner').val(CUR_NODE.workTime);
    $('#date-select').datepicker('setDate', new Date(CUR_NODE.endTime));
    $('#plan-start-time-input').val( new Date(CUR_NODE.planStartTime).toLocaleDateString());
    $('#plan-end-time-input').val( new Date(CUR_NODE.planEndTime).toLocaleDateString());
    $('#start-time-input').val( new Date(CUR_NODE.startTime).toLocaleDateString());

    const delay = CUR_NODE.delay;
    switch (delay) {
        case "S":
            $("#date-delay").attr('class','badge badge-success')
            break;
        case "W":
            $("#date-delay").attr('class','badge badge-warning')
            break;
        case "D":
            $("#date-delay").attr('class','badge badge-danger')
            break;
        default:
            $("#date-delay").attr('class','badge')
            break;
    }
    if (!CUR_NODE.templateProjectID >0) {
        //显示工具页面
        $("a[href=#tool-tab]")[0].click();
        //当前节点绑定的是创新方法
        $("#cur-node-goal-href").editable("setValue", CUR_NODE.goal);

        //显示节点所选择的创新方法工具名
        $("#cur-node-data-widget").removeClass("widget-color-blue").removeClass("widget-color-dark");

        //那么设置APP内容下的部分标签的颜色
        //设置Lock控件的参数
        let $widget = $("#cur-node-data-widget");
        $widget.removeClass("widget-color-blue widget-color-grey widget-color-dark");
        let $btn = $("#cur-node-lock-btn");
        let $i = $($btn.children()[0]);
        $i.removeClass("fa-unlock fa-lock");
        //设置当前节点的锁定状态
        if (!CUR_NODE.lockState) {
            //设置按钮的状态和对应的函数
            $i.addClass("fa-unlock");
            $i.attr("title", "当前节点处于开放状态，其他用户可以绑定数据。点击锁定当前节点")
            if (CUR_NODE.appName) {
                $widget.addClass("widget-color-blue");
            } else {
                $widget.addClass("widget-color-grey");
            }
        } else {
            $widget.addClass("widget-color-dark");
            //设置按钮的状态和对应的函数
            $i.addClass("fa-lock");
            $i.attr("title", "当前节点处于锁定状态，其他用户不可以绑定数据。点击开放当前节点")
        }
        if (CUR_NODE.appName) {
            //如果已绑定APP，显示App名称
            //显示节点所选择的创新方法工具名
            $("#app-name").text(CUR_NODE.appName).addClass("label-success")
                .attr('onclick', 'javascript:window.open(\"' + CUR_NODE.appPath + '")')
            $("#app-result-btn").removeClass("disabled")

            $("#cur-node-app-img").attr("src", CUR_NODE.appIcon);
            // $("#btn-group-app-result").css('display','');
        } else {
            //如果没有绑定APP，那么不显示该控件
            // $("#btn-group-app-result").css('display','none');
            $("#app-result-btn").addClass("disabled")
            $("#app-name").text("").removeClass("label-success").attr("onclick");
            $("#cur-node-app-img").attr("src", "/templates/project/assets/img/app.png");
        }
        //设置结果录入界面的状态，是否已完成
        $("#cur-node-summary-widget").removeClass("widget-color-blue").removeClass("widget-color-red");
        if (CUR_NODE.state == "1") {
            $('#state-change-btn').attr('checked', 'checked')
            $("#cur-node-summary-widget").addClass("widget-color-blue");
        } else {
            $('#state-change-btn').removeAttr('checked')
            $("#cur-node-summary-widget").addClass("widget-color-red");
        }
        $("#cur-node-summary").html(CUR_NODE.summary);
        loadCurStepResult();
    } else {
        //当前节点绑定的是模板
        $("a[href=#template-tab]")[0].click();
        $("#cur-node-temp-select").val(CUR_NODE.templateProjectID)
        //设置模板跳转
        // $("#template-tab btn-info").attr('onclick', 'javascript:windows.open("/templates/project/project.html?id=+' + CUR_NODE.templateProjectID + '",target="_blank")')
    }
}


/**
 * 给工作节点绑定APP
 * @param appName
 * @param appPath
 * @param appIcon
 */
function bindingApp(appName, appPath, appIcon) {
    $.ajax({
        url: API.nodeAPP,
        type: 'PUT',
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            appName: appName,
            appPath: appPath,
            appIcon: appIcon,
        },
        success: function (data) {
            CUR_NODE.appName = appName;
            CUR_NODE.appPath = appPath;
            CUR_NODE.appIcon = appIcon;
            refreshNodeRow();
        }

    })
}

function saveWorkTime() {
    
}

function swapTemplate() {
    $.ajax({
        url: API.ownedProject,
        type: "GET",
        success: function (data) {
            if (data.code == 1) {
                if (data.length > 0) {
                    let $curStepTempSelect = $("#cur-node-temp-select");
                    data.data.forEach(project => {
                        let $option = $('<option></option>').text(project.name).attr('value', project.id);
                        $curStepTempSelect.append($option);
                    })
                }
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("XMLHttpRequest请求状态码：" + XMLHttpRequest.status);
            console.log("XMLHttpRequest状态码：" + XMLHttpRequest.readyState);
            console.log("textStatus是：" + textStatus);
            console.log("errorThrown是：" + errorThrown);
        }
    })
}

const NODE_JS = {
    beforeAddNode: function (node) {
        $.ajax({
            url: API.addNode,
            type: "POST",
            data: node,
            success: function (data) {
            }
        })
    },
    beforeRemoveNode: function (node) {
        $.ajax({
            url: API.deleteNode,
            type: "DELETE",
            data: {
                nodeIndex: node.nodeIndex,
            },
            success: function (data) {

            }
        })
    },
    beforeViewNode: function (node) {
        CUR_NODE = node;
        //设置当前节点的显示区域
        refreshNodeRow();
        //加载结果列表
        // loadCurStepResult();
    },
    beforeAddPath:function (path) {
        $.ajax({
            url: API.path,
            type: "POST",
            data: path,
            success: function (data) {

            }
        })
    },
    beforeRemovePath:function (path) {
        $.ajax({
            url: API.path,
            type: "DELETE",
            data: path,
            success: function (data) {

            }
        })
    },

}


/**
 * @description 锁定节点的函数
 */
function lockNode() {
    $.ajax({
        url: API.nodeLock,
        type: "PUT",
        data: {
            projectID: PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            lockState: !CUR_NODE.lockState,
        },
        success: function (data) {
            CUR_NODE.lockState = !CUR_NODE.lockState;
            refreshNodeRow()
        }
    })

}

/**
 *
 * @param promise
 */
function updateNode(promise){
    promise.then(function () {
        refreshNodeRow();
    },function (text) {
        alert(text)
    })
}
/**
 * 当前节点状态设置为已完成
 */
function finish() {
    updateNode(new Promise(((resolve, reject) => {
        $.ajax({
            url: API.nodeState,
            type: "PUT",
            data: {
                nodeIndex: CUR_NODE.nodeIndex,
                state: !CUR_NODE.state,
            },
            success: function (data) {
                if(data.code){
                    CUR_NODE.state = !CUR_NODE.state;
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("网络错误")
            }
        })
    })))
}

function saveWorkTime() {
    updateNode(new Promise(((resolve, reject) => {
        let workTime = $("#date-spinner").val();
        let endTime =$("#date-select").datepicker('getDate');
        $.ajax({
            url: API.editWorkTime,
            type: "PUT",
            data: {
                nodeIndex: CUR_NODE.nodeIndex,
                workTime: workTime,
                endTime:endTime
            },
            success: function (data) {
                if(data.code){
                    CUR_NODE.workTime = workTime;
                    $("#date-model").modal("hide")

                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("网络错误")
            }
        })
    })))
}
/**
 * 保存当前工作节点的总结
 */
function saveNodeSummary() {
    updateNode(new Promise(((resolve, reject) => {
        let summary = $("#cur-node-summary").html();
        $.ajax({
            url: API.editNodeSummary,
            type: "PUT",
            data: {
                nodeIndex: CUR_NODE.nodeIndex,
                summary: summary,
            },
            success: function (data) {
                if(data.code){
                    CUR_NODE.summary = summary;
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("网络错误")
            }
        })
    })))
}

/**
 * 绑定模板的函数
 */
function bindingTemplate() {
    updateNode(new Promise(((resolve, reject) => {
        let templateProjectID = parseInt($("#cur-node-temp-select").val());
        $.ajax({
            url: API.nodeTemplate,
            type: "PUT",
            data: {
                nodeIndex: CUR_NODE.nodeIndex,
                templateProjectID: templateProjectID,
            },
            success: function (data) {
                if(data.code){
                    CUR_NODE.state = true;
                    CUR_NODE.templateProjectID = parseInt($("#cur-node-temp-select").val());
                    resolve()
                }else {
                    reject(data.msg)
                }
            },
            error:function () {
                reject("网络错误")
            }
        })
    })))
}


