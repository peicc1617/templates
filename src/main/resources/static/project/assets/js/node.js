$(function () {

    $('#state-change-btn').on('click', finish);
});
// function getProjectData() {
//     $.ajax({
//         url:"/templates/api/project",
//         type:"get",
//         async:false,
//         data:{
//             id:PROJECT_ID
//         },
//         success:function (data) {
//             console.log(data);
//             GRAPH.nodes=data.nodes;
//             GRAPH.edges =data.edges
//         }
//     })
// }


//查看某个节点所触发的函数
/**
 * @description 查看某个节点的函数
 */
function viewNode() {
    //设置当前节点的显示区域
    updateView();
    //加载结果列表
    loadCurStepResult();
}

/**
 *
 * @param node
 */
function updateView() {
    //显示区域
    $("#nodeInfoRow").attr("style", "display:display");
    //节点的名称
    $("#cur-step-name-href").html(CUR_NODE.name);
    //节点的描述
    $("#cur-step-describe-href").html(CUR_NODE.description);
    if(CUR_NODE.templateProjectID===0){
        //显示工具页面
        $("a[href=#tool-tab]")[0].click();
        //当前节点绑定的是创新方法
        $("#cur-step-goal-href").html(CUR_NODE.goal);

        //显示节点所选择的创新方法工具名
        $("#cur-step-data-widget").removeClass("widget-color-blue").removeClass("widget-color-dark");

        //那么设置APP内容下的部分标签的颜色
        //设置Lock控件的参数
        let $widget = $("#cur-step-data-widget");
        $widget.removeClass("widget-color-blue widget-color-grey widget-color-dark");
        let $btn = $("#cur-step-lock-btn");
        let $i = $($btn.children()[0]);
        $i.removeClass("fa-unlock fa-lock");
        //设置当前节点的锁定状态
        if (!CUR_NODE.lockState) {
            //设置按钮的状态和对应的函数
            $i.addClass("fa-unlock");
            $i.attr("title", "当前节点处于开放状态，其他用户可以绑定数据。点击锁定当前节点")
            if(CUR_NODE.appName){
                $widget.addClass("widget-color-blue");
            }else {
                $widget.addClass("widget-color-grey");
            }
        } else {
            $widget.addClass("widget-color-dark");
            //设置按钮的状态和对应的函数
            $i.addClass("fa-lock");
            $i.attr("title", "当前节点处于锁定状态，其他用户不可以绑定数据。点击开放当前节点")
        }
        if(CUR_NODE.appName){
            //如果已绑定APP，显示App名称
            //显示节点所选择的创新方法工具名
            $("#tool-name").text(CUR_NODE.appName)
                .attr('onclick', 'javascript:window.open(\"' + CUR_NODE.appPath + '")')
            $("#btn-group-app-result").css('display','');
        }else {
            //如果没有绑定APP，那么不显示该控件
            $("#btn-group-app-result").css('display','none');
        }

        //设置结果录入界面的状态，是否已完成
        $("#cur-step-review-widget").removeClass("widget-color-blue").removeClass("widget-color-red");
        if (CUR_NODE.state == "1") {
            $('#state-change-btn').attr('checked', 'checked')
            $("#cur-step-review-widget").addClass("widget-color-blue");
        } else {
            $('#state-change-btn').removeAttr('checked')
            $("#cur-step-review-widget").addClass("widget-color-red");
        }
        $("#WYeditor").html(CUR_NODE.review);
    }else {
        //当前节点绑定的是模板
        $("a[href=#template-tab]")[0].click();
        $("#cur-step-temp-select").val(CUR_NODE.templateProjectID)
        //设置模板跳转
        $("#template-tab btn-info").attr('onclick', 'javascript:windows.open("/templates/project.html?id=+' + CUR_NODE.templateProjectID + '",target="_blank")')
    }

}

// function postState() {
//     //更新节点状态
//     updateNode(false,function (){
//         resetGRID();
//     })
// }


/**
 * @description 删除某个节点的函数
 * @param nodeIndex 要删除节点的nodeIndex
 */
function deleteNode(nodeIndex) {
    removeNode(false, function () {
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        ANALYSIS_MSG.content = "删除了节点[" + nodeIndex + "]"
        Analysis.send();
        alert("删除成功")
    }, nodeIndex)
}

/**
 * @description 弹出修改节点的模态框
 */
function editNodeInfo() {
    $("#node-name").val(CUR_NODE.name);
    $("#node-description").val(CUR_NODE.description);
    $("#node-goal").val(CUR_NODE.goal);
    $("#cur-node-info-view-modal").modal('show');
}

/**
 * @description 修改某个节点的信息
 */
function saveCurStepInfo() {

    CUR_NODE.name = $("#node-name").val();
    CUR_NODE.description = $("#node-description").val();
    CUR_NODE.goal = $("#node-goal").val();

    updateNode(false, function () {
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        ANALYSIS_MSG.content = "修改了节点[" + CUR_NODE.name + "]的信息"
        Analysis.send();
        resetGRID()
    })
    viewNode();
    $("#cur-node-info-view-modal").modal('hide');
}


//向后台请求修改节点
function updateNode(async, successFunction) {
    //更新阶段信息，不涉及阶段个数，所以不需要修改edges
    $.ajax({
        url: "/templates/api/project/node",
        type: "POST",
        data: {
            _method: "PUT",
            projectID: PROJECT_ID,
            node: JSON.stringify(CUR_NODE),
        },
        success: successFunction
    })

}

//向后台发送添加节点的请求
function addNode(node, preNodeIndex, async, successFunction) {
    $.ajax({
        url: "/templates/api/project/node",
        type: "POST",
        data: {
            _method: "POST",
            id: PROJECT_ID,
            node: JSON.stringify(node),
            preNodeIndex: preNodeIndex,
        },
        success: function () {
            ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
            ANALYSIS_MSG.content = "添加了新的节点[" + node.name + "]"
            Analysis.send();
            successFunction();

        }
    })

}

//向后台发送删除节点的请求
function removeNode(async, successFunction, nodeIndex) {
    $.ajax({
        url: "/templates/api/project/node",
        type: "POST",
        async: async,
        data: {
            _method: "DELETE",
            id: PROJECT_ID,
            nodeIndex: nodeIndex,
            edges: JSON.stringify(GRAPH.edges)
        },
        success: successFunction
    })
}

//锁定当前节点
/**
 * @description 锁定节点的函数
 */
function lockNode() {
    CUR_NODE.lockState = !CUR_NODE.lockState;
    updateNode(false, function () {
        if (CUR_NODE.lockState) {
            ANALYSIS_MSG.content = "锁定了节点[" + CUR_NODE.name + "]"
        } else {
            ANALYSIS_MSG.content = "解锁了节点[" + CUR_NODE.name + "]"
        }
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        Analysis.send();
        updateView()
    });

}

function finish() {
    if (CUR_NODE.state == 0) CUR_NODE.state = 1;
    else CUR_NODE.state = 0;
    if (CUR_NODE.state == 0) {
        ANALYSIS_MSG.content = "更新了节点[" + CUR_NODE.name + "]的状态，节点未完成"
    } else {
        ANALYSIS_MSG.content = "更新了节点[" + CUR_NODE.name + "]的状态，节点已完成"
    }
    updateNode(false, function () {
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        Analysis.send();
        updateView()

    })
}

function saveNodeReview(){
    CUR_NODE.review = $("#WYeditor").html();
    updateNode(false, function () {
        ANALYSIS_MSG.content = "更新了节点[" + CUR_NODE.name + "的总结]"
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        Analysis.send();
        updateView()
    });
}
/**
 * 绑定模板的函数
 */
function bangdingTemplate() {
    CUR_NODE.templateProjectID = $("#cur-step-temp-select").val()
    CUR_NODE.state = 1;
    updateNode(true, function () {
        alert("绑定成功");
        ANALYSIS_MSG.type = MSG_TYPE_TABLE.NODE
        ANALYSIS_MSG.content = "节点[" + CUR_NODE.name + "]绑定了模板[" + $("#cur-step-temp-select").val() + "]"
        Analysis.send();
    })
    viewNode();
}


