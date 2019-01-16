$(document).ready(function(){
    $('#state-change-btn').on('click', finish);


    $("#cur-node-name-href").editable({
        type: 'text',
        title: '修改名称',
        name:'name',
        url: updateNodeHref,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },

    })

    $("#cur-node-description-href").editable({
        type: 'textarea',
        title: '修改描述',
        name:'description',
        url: updateNodeHref,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },
    })
    $("#cur-node-goal-href").editable({
        type: 'textarea',
        title: '修改目标',
        name:'goal',
        url: updateNodeHref,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },
    })
});
function updateNodeHref(params) {
    var d = new $.Deferred();
    if (params.value === 'abc') {
        return d.reject('error message'); //returning error via deferred object
    } else {
        data = {
            projectID:PROJECT_ID,
            nodeIndex:CUR_NODE.nodeIndex
        }
        data[params.name] = params.value
        //async saving data in js model
        $.ajax({
            url: '/templates/api/project/node/'+params.name,
            type: 'PUT',
            async: true,
            data: data,
            success: function () {
                CUR_NODE[params.name] = params.value;
                stage.refreshGrid();
                d.resolve();
            }

        })
        return d.promise();
    }
}
//查看某个节点所触发的函数
/**
 * @description 查看某个节点的函数
 */
function viewNode() {

}

/**
 *
 * @param node
 */
function updateView() {
    //显示区域
    $("#stepInfoRow").attr("style", "display:none");
    $("#nodeInfoRow").attr("style", "display:display");
    //节点的名称
    $("#cur-node-name-href").html(CUR_NODE.name);
    //节点的描述
    $("#cur-node-description-href").html(CUR_NODE.description);
    if(CUR_NODE.templateProjectID===0){
        //显示工具页面
        $("a[href=#tool-tab]")[0].click();
        //当前节点绑定的是创新方法
        $("#cur-node-goal-href").html(CUR_NODE.goal);

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
            $("#app-name").text(CUR_NODE.appName).addClass("label-success")
                .attr('onclick', 'javascript:window.open(\"' + CUR_NODE.appPath + '")')
            $("#app-result-btn").removeClass("disabled")

            $("#cur-node-app-img").attr("src",CUR_NODE.appIcon);
            // $("#btn-group-app-result").css('display','');
        }else {
            //如果没有绑定APP，那么不显示该控件
            // $("#btn-group-app-result").css('display','none');
            $("#app-result-btn").addClass("disabled")
            $("#app-name").text("").removeClass("label-success").attr("onclick");
            $("#cur-node-app-img").attr("src","/templates/project/assets/img/app.png");


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
    }else {
        //当前节点绑定的是模板
        $("a[href=#template-tab]")[0].click();
        $("#cur-node-temp-select").val(CUR_NODE.templateProjectID)
        //设置模板跳转
        // $("#template-tab btn-info").attr('onclick', 'javascript:windows.open("/templates/project/project.html?id=+' + CUR_NODE.templateProjectID + '",target="_blank")')
    }
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
function bindingApp(appName,appPath,appIcon){
    $.ajax({
        url: '/templates/api/project/node/app',
        type: 'PUT',
        data: {
            projectID:PROJECT_ID,
            nodeIndex:CUR_NODE.nodeIndex,
            appName:appName,
            appPath:appPath,
            appIcon:appIcon,
        },
        success: function (data) {
            CUR_NODE.appName=appName;
            CUR_NODE.appPath = appPath;
            CUR_NODE.appIcon = appIcon;
            updateView();
        }

    })
}

function swapTemplate(){
    $.ajax({
        url:"/templates/api/project/owned",
        type:"GET",
        success:function(data){
            if(data.length>0){
                let $curStepTempSelect= $("#cur-node-temp-select");
                data.forEach(project=>{
                    let $option = $('<option></option>').text(project.name).attr('value',project.id);
                    $curStepTempSelect.append($option);
                })
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

var NODE_JS = {
    beforeAddNode:function (node) {
        $.ajax({
            url: "/templates/api/project/node",
            type: "POST",
            data: node,
            success: function (data) {
            }
        })
    },
    beforeRemoveNode:function (node) {
        $.ajax({
            url: "/templates/api/project/node",
            type: "DELETE",
            data: {
                projectID: PROJECT_ID,
                nodeIndex: node.nodeIndex,
            },
            success: function (data) {

            }
        })
    },
    beforeViewNode:function(node){
        CUR_NODE = node;
        //设置当前节点的显示区域
        updateView();
        //加载结果列表
        loadCurStepResult();
    }
    
}



//锁定当前节点
/**
 * @description 锁定节点的函数
 */
function lockNode() {
    $.ajax({
        url:"/templates/api/project/node/lock",
        type:"PUT",
        data:{
            projectID:PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            lockState:!CUR_NODE.lockState,
        },
        success:function (data) {
            CUR_NODE.lockState = !CUR_NODE.lockState;
            updateView()
        }
    })

}

function finish() {
    $.ajax({
        url:"/templates/api/project/node/state",
        type:"PUT",
        data:{
            projectID:PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            state:!CUR_NODE.lockState,
        },
        success:function (data) {
            CUR_NODE.state = !CUR_NODE.state;
            updateView()
        }
    })
}

function saveNodeSummary(){

    let summary = $("#cur-node-summary").html();
    $.ajax({
        url:"/templates/api/project/node/summary",
        type:"PUT",
        data:{
            projectID:PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            summary:summary,
        },
        success:function (data) {
            CUR_NODE.summary = summary;
        }
    });
}
/**
 * 绑定模板的函数
 */
function bangdingTemplate() {

    $.ajax({
        url:"/templates/api/project/node/template",
        type:"PUT",
        data:{
            projectID:PROJECT_ID,
            nodeIndex: CUR_NODE.nodeIndex,
            templateProjectID:parseInt($("#cur-node-temp-select").val()),
        },
        success:function (data) {
            CUR_NODE.state = 1;
            CUR_NODE.templateProjectID = parseInt($("#cur-node-temp-select").val());
            updateView()
        }
    });

    CUR_NODE.state = 1;

    viewNode();
}


