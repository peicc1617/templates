var stage;
var SVG_DOM = document.getElementsByTagNameNS('http://www.w3.org/2000/svg', 'svg')[1];
$(document).ready(function () {

    //定义一些全局变量
    //div的宽度
    DIV_WIDTH = 800;
    //div的高度
    DIV_HEIGHT = 500;
    //起始节点x坐标
    BEGIN_X = 200;
    //起始节点y左边
    BEGIN_Y = DIV_HEIGHT / 2;

    //圆形节点的直径
    NODE_D = 50;
    //节点之间的x间隔
    NODE_WIDTH_SPACE = 0;
    //节点之间的y间隔
    NODE_HEIGHT_SPACE = NODE_D * 2;
    //节点数组
    NODE_ARRAY = [];
    //网格
    GRID = [];
    //当前选中的节点
    CUR_NODE = {};

    SVG_DOM.setAttribute('width', '100%');

    var WorkFlow = function (el, options) {
        this.options = options;
        this.el = el;
        this.$el = $(el);
        this.scaleArr = [0.25, 0.33, 0.5, 0.67, 0.75, 0.8, 0.9, 1];
        this.scale = 7;
        this.init();
    }


    WorkFlow.DEFAULTS = {
        width: undefined,
        height: undefined,
        nodeWidth: 100,
        nodeHeight: 40,
        nodeR: 20,
        "name": "new stage",
        "info": "info about this stage",
        'symbolsContainer': "#symbolsContainer", //图标容器
        'stepsContainer': "#stepsContainer", //阶段容器
        'edgesContainer': '#edgesContainer', //边容器
        'nodesContainer': '#nodesContainer', //节点容器
        'toolContainer': '#toolContainer', //提示容器
        'tipsContainer': '#tipsContainer', //提示容器
        'nodeToolBtns':{
            r:15,
            data:[
                {
                    name: "查看",
                    icon: '#icon-view',
                    func: function (workNode) {
                        alert("查看当前节点")
                    }
                }, {
                    name: "移动",
                    icon: '#icon-move',
                    func: function (workNode) {
                        alert("移动当前节点")
                    }
                }, {
                    name: "删除",
                    icon: '#icon-delete',
                    func: function (workNode) {
                        alert("删除当前节点")
                    }
                }, {
                    name: "连接",
                    icon: "#icon-link",
                    func: function (workNode) {
                        alert("连接后续节点")
                    }
                }
            ]
        },
        'stepToolBtns':{
            data:[
                [{
                    name: "阶段前移",
                    icon: '#icon-pre',
                    func: function (workNode) {
                        alert("查看当前节点")
                    }
                }, {
                    name: "阶段后移",
                    icon: "#icon-next",
                    func: function (workNode) {
                        alert("连接后续节点")
                    }
                }],
                [{
                    name: "添加前序阶段",
                    icon: '#icon-pre-2',
                    func: function (workNode) {
                        alert("查看当前节点")
                    }
                }, {
                    name: "添加后序阶段",
                    icon: "#icon-next-2",
                    func: function (workNode) {
                        alert("连接后续节点")
                    }
                }],
                [{
                    name: "新建节点",
                    icon: '#icon-add',
                    func: function (workNode) {
                        alert("移动当前节点")
                    }
                }, {
                    name: "删除",
                    icon: '#icon-delete',
                    func: function (workNode) {
                        alert("删除当前节点")
                    }
                },],
                [ {
                    name: "查看",
                    icon: '#icon-view',
                    func: function (workNode) {
                        alert("移动当前节点")
                    }
                },]

            ]
        },
        'pathToolBtn':{
            name: "删除工作路径",
            icon: '#icon-delete-2',
            func: function (workNode) {
                alert("移动当前节点")
            }
        },
        beforeAll: function () {
            return true;
        },
        afterAll: function () {

        },
        beforeAddNode: function (node) {

        },
        afterAddNode: function (node) {

        },
        beforeRemoveNode: function () {

        },
        afterRemoveNode: function () {

        },
        beforeDataNode: function () {

        },
        afterDataNode: function (node) {

        },
        beforeRefreshNode: function (node) {

        },
        afterRefreshNode: function (node) {

        },
        beforeAddStep: function (step) {

        },
        afterAddStep: function (step) {

        },
        beforeRemoveStep: function (step) {

        },
        afterRemoveStep: function (step) {

        },
        beforeDataStep: function (step) {

        },
        afterDataStep: function (step) {

        },
        beforeRefreshStep: function (step) {

        },
        afterRefreshStep: function (step) {

        },
        beforeViewStep: function (step) {
        },
        afterViewStep: function (step) {
        },
        beforeAddLink: function () {

        },
        afterAddLink: function () {

        },
        beforeRemoveLink: function () {

        },
        afterRemoveLink: function () {

        },
        beforeViewLink: function () {

        },
        afterViewLink: function () {

        },
    }

    WorkFlow.EVENTS =
        {
            /**
             * 所有的事件都会响应
             */
            'before.all.work.flow': 'beforeAll',
            'after.all.work.flow': 'afterAll',
            /**
             * 添加新的节点触发的事件
             */
            'before.add-node.work.flow': 'beforeAddNode',
            'after.add-node.work.flow': 'afterAddNode',
            /**
             * 删除节点触发的事件
             */
            'before.remove-node.work.flow': 'beforeRemoveNode',
            'after.remove-node.work.flow': 'afterRemoveNode',
            /**
             * 更新节点数据触发的事件
             */
            'before.data-node.work.flow': 'beforeDataNode',
            'after.data-node.work.flow': 'afterDataNode',
            /**
             * 更新节点样式触发的事件
             */
            'before.refresh-node.work.flow': 'beforeRefreshNode',
            'after.refresh-node.work.flow': 'afterRefreshNode',
            /**
             * 查看节点触发的事件
             */
            'before.view-node.work.flow': 'beforeRefreshNode',
            'after.view-node.work.flow': 'afterRefreshNode',
            /**
             * 添加新的阶段触发的事件
             */
            'before.add-step.work.flow': 'beforeAddStep',
            'after.add-step.work.flow': 'afterAddStep',
            /**
             * 删除阶段触发的事件
             */
            'before.remove-step.work.flow': 'beforeRemoveStep',
            'after.remove-step.work.flow': 'afterRemoveStep',
            /**
             * 更新阶段数据触发的函数
             */
            'before.data-step.work.flow': 'beforeDataStep',
            'after.data-step.work.flow': 'afterDataStep',
            /**
             * 更新阶段样式触发的函数
             */
            'before.refresh-step.work.flow': 'beforeRefreshStep',
            'after.refresh-step.work.flow': 'afterRefreshStep',
            /**
             * 查看阶段触发的函数
             */
            'before.view-step.work.flow': 'beforeViewStep',
            'after.view-step.work.flow': 'afterViewStep',
            /**
             * 添加连线触发的事件
             */
            'before.add-link.work.flow': 'beforeAddLink',
            'after.add-link.work.flow': 'afterAddLink',
            /**
             * 删除连线触发的事件
             */
            'before.remove-link.work.flow': 'beforeRemoveLink',
            'after.remove-link.work.flow': 'afterRemoveLink',
            /**
             * 查看连线触发的事件
             */
            'before.view-link.work.flow': 'beforeViewLink',
            'after.view-link.work.flow': 'afterViewLink',
        };

    WorkFlow.prototype = {
        constructor: WorkFlow,
        init: function () {

            this.resetView();

            this.bindMouseWheel();
            this.toolbarBind();
        },
        trigger: function (name) {
            var args = Array.prototype.slice.call(arguments, 1);
            name += '.work.flow';
            this.options[WorkFlow.EVENTS[name]].apply(this.options, args);
            this.$el.trigger($.Event(name), args);
        },
        /**
         * 重设视图
         */
        resetView: function () {
            this.refreshData();
            this.refreshView();
        },
        /**
         * 重新绘制工具栏
         */
        resetToolBar: function () {

        },
        resetCSS: function () {

        },
        getPath: function () {
            const nodeMap = this.options.nodeMap;
            const stepMap = this.options.stepMap;
            const freeNodeSet = new Set();//自由节点Set
            nodeMap.forEach((node, nodeIndex) => {
                if (node.preNodeIndexList == null || node.preNodeIndexList.length == 0) {
                    freeNodeSet.add(node);
                }
            });
            const stack = [];
            freeNodeSet.forEach(node => stack.push([node]));
            //生成路径
            const paths = [];
            while (stack.length > 0) {
                let cur = stack.pop();
                let node = cur[cur.length - 1]
                if (node.nextNodeIndexList == null || node.nextNodeIndexList.length == 0) {
                    //如果没有后继节点，说明当前路径结束
                    paths.push(cur);
                } else {
                    //如果有后继节点，那么继续
                    node.nextNodeIndexList.forEach(nodeIndex => {
                        stack.push(cur.concat([nodeMap.get(nodeIndex)]));
                    })
                }
            }
            //定义路径排序规则
            paths.sort(function (arr1, arr2) {
                let val1 = arr1.length;
                let val2 = arr2.length;
                if (val1 > val2) {
                    return -1;
                } else if (val1 < val2) {
                    return 1;
                } else {
                    //如果两者长度相同，那么根据节点对应的step顺序
                    for (let index = 0; index < arr1.length; index++) {
                        if (arr1[index].stepIndex < arr2[index].stepIndex) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                    return 0;
                }
            });
            const maxLenInStep = new Map();
            stepMap.forEach((step, stepIndex) => maxLenInStep.set(stepIndex, 1));
            paths.forEach(path => {
                const lenInStep = new Map();
                path.forEach(node => {
                    if (lenInStep.has(node.stepIndex)) {
                        lenInStep.set(node.stepIndex, lenInStep.get(node.stepIndex) + 1);
                    } else {
                        lenInStep.set(node.stepIndex, 1);
                    }
                })
                lenInStep.forEach((len, stepIndex) => {
                    if (len > maxLenInStep.get(stepIndex)) {
                        maxLenInStep.set(stepIndex, len);
                    }
                })
            })
            this.columnN = 0;
            maxLenInStep.forEach((v, k) =>
                this.columnN += v);//求和得到长度之和,作为网格的列个数
            //获得每个阶段的起始点
            const stepStartMap = new Map();
            let preStepIndex;
            maxLenInStep.forEach((len, stepIndex) => {
                if (maxLenInStep.has(preStepIndex)) {
                    stepStartMap.set(stepIndex, stepStartMap.get(preStepIndex) + maxLenInStep.get(preStepIndex));//后面的阶段起始点为前一个阶段的起始点加上前一阶段的长度
                } else {
                    stepStartMap.set(stepIndex, 0);//第一个阶段起始点为0
                }
                preStepIndex=stepIndex;
            });
            this.stepStartMap = stepStartMap;
            this.paths = paths;
            this.rowN = paths.length;
        },
        planNodes: function () {
            for (let i = 0; i < this.paths.length; i++) {
                let path = this.paths[i];
                // this.options.steps.forEach(step=>stepCnt.set(step.stepIndex,0));
                for (let j = 0, x = 0; j < path.length; j++, x++) {
                    let node = path[j];
                    node.y = i;
                    let left = this.stepStartMap.get(node.nodeIndex);
                    if (j < left) {
                        //如果节点没有出现在阶段内，那么把该节点的坐标平移到阶段内，这样后续的该阶段内的节点都不会出现越界的情况
                        x = left;
                    }
                    node.x = x;
                }
            }
        },
        refreshView: function () {
            this.getPath();//获取路径
            this.planNodes();//调整节点
            this.initGrid();//初始化网格
            this.drawSteps();
            this.drawNodes();//绘制节点
            this.drawPath();//绘制工作路径
            this.initNodeToolGroup();//初始化节点工具菜单
            this.initStepToolGroup();//初始化阶段工具菜单
            this.initPathToolGroup();//初始化工作路径的俺就
            this.bindListener();
        },
        initGrid: function () {
            this.nodeHorDis = this.options.nodeWidth*3; //节点间距
            this.nodeVerDis = this.options.nodeHeight*2 //节点垂直间
            this.stepBarHeight = this.options.nodeHeight; //阶段标题高度
            let width2 = (this.columnN+1) * this.nodeHorDis; //计算实际宽度
            let height2 = (this.rowN + 2) * this.nodeVerDis; //计算实际高度
            this.viewbox = [0, 0, width2, height2];
            this.viewBox = this.viewbox.concat();
            this.resetViewBox();
            this.$el.height(height2);
            console.log("实际宽度", width2, "实际高度", height2)
            this.svgxc = this.$el.offset().left + this.$el.width() / 2;
            this.svgyc = this.$el.offset().top + this.$el.height() / 2;
            console.log('svg的中心屏幕坐标', this.svgxc, this.svgyc)
            const start = {
                x: this.nodeHorDis / 2,
                y: this.nodeVerDis+this.stepBarHeight,
            };
            //生成网格
            this.grid = new Array(this.rowN);
            for (let i = 0; i < this.rowN; i++) {
                this.grid[i] = new Array(this.columnN);
                for (let j = 0; j < this.columnN; j++) {
                    this.grid[i][j] = {
                        x: start.x + j * this.nodeHorDis,
                        y: start.y + i * this.nodeVerDis,
                    }
                }
            }
            this.nodeWidth = 100;
            this.nodeHeight = 40;
        },
        drawSteps: function () {
            const stepStartArr = Array.from(this.stepStartMap);
            this.workStepMap = new Map()
            for(let i = 0;i<stepStartArr.length;i++){
                let stepIndex = stepStartArr[i][0];
                let start = stepStartArr[i][1];
                let end;
                if(i<stepStartArr.length-1){
                    end = stepStartArr[i+1][1]-1;
                }else {
                    end = this.columnN-1;
                }
                let stepXY  ={
                    xy1:{
                        x:this.grid[0][start].x-this.nodeHorDis/8,
                        y:this.grid[0][start].y-this.nodeVerDis / 2 - this.stepBarHeight
                    },
                    xy2:{
                        x:this.grid[this.grid.length-1][end].x+this.nodeWidth+this.nodeHorDis/8,
                        y:this.grid[this.grid.length-1][start].y+this.nodeVerDis
                    }
                }
                const workStep = new WorkStep(stepXY.xy1, stepXY.xy2, this.options.stepMap.get(stepIndex), this, this.stepBarHeight);
                this.workStepMap.set(stepIndex,workStep);
            }
        },
        /**
         * 更新视图
         */
        drawNodes: function () {
            this.workNodeMap = new Map();
            this.options.nodeMap.forEach((node, nodeIndex) => {
                const workNode = new WorkNode(node, this.grid[node.y][node.x], this.nodeWidth, this.nodeHeight, this);
                this.workNodeMap.set(nodeIndex, workNode);
                workNode.initNode();
            });
            this.options.nodeMap.forEach((node,nodeI)=>{
                if (node.nextNodeIndexList != null && node.nextNodeIndexList != undefined && node.nextNodeIndexList.length > 0) {
                    node.nextNodeIndexList.forEach(nodeJ=>{
                        const workNodeI = this.workNodeMap.get(nodeI);
                        const workNodeJ = this.workNodeMap.get(nodeJ);
                        workNodeI.nextWorkNodeMap.set(nodeJ,workNodeJ);
                        workNodeJ.preWorkNodeMap.set(nodeI, workNodeI);
                    })
                }
            })

        },
        /**
         * 绘制工作流路径
         */
        drawPath:function(){
            this.workPathList = [];

            //绘制工作流路径
            this.workNodeMap.forEach((workNodeI,nodeI)=>{
                workNodeI.nextWorkNodeMap.forEach((workNodeJ,nodeJ)=>{
                    const path = new WorkPath(workNodeI, workNodeJ,this.options.edgesContainer)
                    this.workPathList.push(path);
                    workNodeI.outPathMap.set(nodeJ,path);
                })
            })

        },
        initNodeToolGroup:function(){
            this.nodeToolID = "#nodeTool";
            const $nodeToolGroup = $(svg("g",$(this.options.toolContainer), this.nodeToolID));
            const r =  this.options.nodeHeight/3;
            let n = this.options.nodeToolBtns.data.length;
            const R = this.options.nodeWidth/2+r*2;
            const yo =this.options.nodeHeight/2;
            let xo= this.options.nodeWidth/2;
            const radinas=[3/12*Math.PI,1/12*Math.PI,-1/12*Math.PI,-3/12*Math.PI]
            const posArr =radinas.map(ra=> [R*Math.cos(ra),(R*Math.sin(ra))]);
            for(let i =0;i<n;i++){
                const x = xo+posArr[i][0],  y=yo+posArr[i][1];
                let btnOption = this.options.nodeToolBtns.data[i];
                const $btnG = $(svg("g",$nodeToolGroup),undefined,{
                    class:"btn"
                })
                const $btn = $(svg("circle",$btnG,undefined,{
                    "filter":"url(#btnFeOffset)",
                    // style:"box-shadow: 10px 10px 5px #888888;",
                    cx:x,
                    cy:y,
                    r:r
                }))
                const $icon = svg('use', $btnG, undefined, {
                    "xlink:href": btnOption.icon,
                    "width": r*1.5,
                    "height": r*1.5,
                    "x": x-r*1.5/2,
                    "y": y-r*1.5/2
                });
                svg('title', $btnG, undefined, undefined)
                    .appendChild(document.createTextNode(btnOption.name));
                $btnG.html($btnG.html());
            }
        },
        initStepToolGroup:function(){
            this.stepToolID = "#stepTool";
            const stepToolOption = this.options.stepToolBtns;
            const $stepToolGroup = $(svg("g",$(this.options.toolContainer), this.stepToolID));
            const width =  this.stepBarHeight*2/3;//图标的宽度
            const xStart =width/3,yStart = this.stepBarHeight/2-width/2;
            for(let i =0;i< stepToolOption.data.length;i++){
                let btnOptions = stepToolOption.data[i];
                for (let j = 0; j < btnOptions.length; j++) {
                    const $btnG = $(svg("g",$stepToolGroup))
                    const $btn = $(svg("rect",$btnG,undefined,{
                        "filter":"url(#btnFeOffset)",
                        // style:"box-shadow: 10px 10px 5px #888888;",
                        width:width,
                        height:width,
                        rx:width/4,
                        ry:width/4,
                        x:xStart*(j+1)+width*j,
                        y:yStart+this.stepBarHeight*i,
                    }))
                    const $icon = svg('use', $btnG, undefined, {
                        "xlink:href": btnOptions[j].icon,
                        "width":width*0.75,
                        "height":width*0.75,
                        x:xStart*(j+1)+width*j+width*0.25/2,
                        y:yStart+this.stepBarHeight*i+width*0.25/2,
                    });
                    svg('title', $btnG, undefined, undefined)
                        .appendChild(document.createTextNode(btnOptions[j].name));
                    $btnG.html($btnG.html());
                }
            }
        },
        initPathToolGroup:function(){
            this.pathTool = "#pathTool";
            const pathToolOption = this.options.pathToolBtn;
            const $pathToolGroup = $(svg("g",$(this.options.toolContainer), this.pathTool));
            const r =  this.stepBarHeight/3;//图标的半径
            const xStart =0,yStart = 0;
            const $btnG = $(svg("g",$pathToolGroup))
            const $btn = $(svg("circle",$btnG,undefined,{
                "filter":"url(#btnFeOffset)",
                cx:xStart,
                cy:yStart-r,
                r:r
            }))
            const $icon = svg('use', $btnG, undefined, {
                "xlink:href": pathToolOption.icon,
                "width": r*1.5,
                "height": r*1.5,
                "x": xStart-r*1.5/2,
                "y": yStart-r-r*1.5/2
            });
            svg('title', $btnG, undefined, undefined)
                .appendChild(document.createTextNode(pathToolOption.name));
            $btnG.html($btnG.html());
        },
        /**
         * 绑定事件监听器
         */
        bindListener:function(){
            this.workNodeMap.forEach((workNode,nodeIndex)=>{
                $(workNode).on("onclick",{nodeIndex:nodeIndex,workFlow:this},function (event) {
                    event.data.workFlow.clickWorkNode(nodeIndex);
                })
            })
            this.workStepMap.forEach((workStep,stepIndex)=>{
                $(workStep).on("onclick",{stepIndex:stepIndex,workFlow:this},function (event) {
                    event.data.workFlow.clickWorkStep(stepIndex);
                })
            })
            this.workPathList.forEach((workPath)=>{
                $(workPath).on("onclick",{stepIndex:workPath,workFlow:this},function (event) {
                    event.data.workFlow.clickWorkPath(workPath);
                })
            })
        },
        clickWorkNode:function(nodeIndex){
            let preNodeIndex = $(this.nodeToolID).attr("nodeindex");//获取之前的节点
            if(nodeIndex==undefined||preNodeIndex!=undefined&&$(this.nodeToolID).attr('class')==="active"){
                //首先关闭,并且删除
                $(this.nodeToolID).removeAttr("class").removeAttr("nodeindex",);
                if(preNodeIndex!==nodeIndex){
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkNode(nodeIndex);
                    }, 10);
                }
            }else if(preNodeIndex==undefined&&$(this.nodeToolID).attr('class')!=="active"){
                //如果之前没有点击过节点并且处于关闭状态
                const workNode = this.workNodeMap.get(nodeIndex);
                $(this.nodeToolID).attr({
                    class:"active",
                    "nodeindex":nodeIndex,
                    transform:"translate("+(workNode.x)+", "+(workNode.y)+")",
                });
            }

        },
        clickWorkStep:function(stepIndex){
            let preStepIndex = $(this.stepToolID).attr("stepindex");//获取之前的节点
            if(stepIndex==undefined||preStepIndex!=undefined&&$(this.stepToolID).attr('class')==="active"){
                //首先关闭,并且删除
                $(this.stepToolID).removeAttr("class").removeAttr("stepindex",);
                if(preStepIndex!==stepIndex){
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkStep(stepIndex);
                    }, 10);
                }
            }else if(preStepIndex==undefined&&$(this.stepToolID).attr('class')!=="active"){
                //如果之前没有点击过节点并且处于关闭状态
                const workStep = this.workStepMap.get(stepIndex);
                $(this.stepToolID).attr({
                    class:"active",
                    "stepindex":stepIndex,
                    transform:"translate("+(workStep.xy2.x)+", "+(workStep.xy1.y)+")",
                });
            }
        },
        clickWorkPath:function(workPath){
            let pathid = $(this.pathTool).attr("pathid");
            if(workPath==undefined||pathid!=undefined&&$(this.pathTool).attr('class')==="active"){
                //首先关闭,并且删除
                $(this.pathTool).removeAttr("class").removeAttr("pathid",);
                if(pathid!==(workPath.nodeI+workPath.nodeJ)){
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkPath(workPath);
                    }, 10);
                }
            }else if(pathid==undefined&&$(this.pathTool).attr('class')!=="active"){
                //如果之前没有点击过节点并且处于关闭状态
                $(this.pathTool).attr({
                    class:"active",
                    "pathid":workPath.nodeI+workPath.nodeJ,
                    transform:"translate("+(workPath.midPos.x)+", "+(workPath.midPos.y)+")",
                });
            }
        },
        refreshData: function () {
            //更新数据
            return false;
        },
        /**
         * 更新节点。
         */
        refreshNode: function () {

        },
        editInfo: function () {

        },
        /**
         * 绑定鼠标滚轮事件函数
         */
        bindMouseWheel: function () {
            //暂时取消缩放函数
            // this.el.onmousewheel = function (e) {
            //     if (e.wheelDelta < 0) {
            //         //缩小
            //         stage.scale--;
            //         if (stage.scale < 0) {
            //             stage.scale = 0;
            //         }
            //     } else {
            //         //放大
            //         stage.scale++;
            //         if (stage.scale >= stage.scaleArr.length) {
            //             stage.scale = stage.scaleArr.length - 1;
            //         }
            //     }
            //     console.log("当前缩放比例", stage.scaleArr[stage.scale])
            //     // const xc = (stage.viewbox[0] + stage.viewbox[2]) / 2
            //     // const yc = (stage.viewbox[1] + stage.viewbox[3]) / 2
            //     $("#scal-value-input").val(stage.scaleArr[stage.scale])
            //     stage.resetViewBox();
            //     return false;
            //     // e.preventDefault();
            // };
            // this.$el.bind("mousedown", startViewBoxMove = function (e) {
            //     $(this).bind("mouseup", cancelMoveViewBox = function (e) {
            //         //取消绑定的所有函数
            //         // $(this).unbind("mousedown", moveViewBox);
            //         $(this).unbind("mouseup", cancelMoveViewBox);
            //         $(this).unbind("mousemove", moveViewBox);
            //     })
            //     var pos = {
            //         x: stage.mousePos.x,
            //         y: stage.mousePos.y
            //     }
            //     $(this).bind('mousemove', {
            //         pos: pos
            //     }, moveViewBox = function (e2) {
            //         const dx = (stage.mousePos.x - e2.data.pos.x);
            //         const dy = (stage.mousePos.y - e2.data.pos.y);
            //         // const dy = 0;
            //         console.log('x变动', dx, 'y变动', dy)
            //         pos.x = stage.mousePos.x;
            //         pos.y = stage.mousePos.y;
            //         stage.viewBox = [stage.viewBox[0] - dx, stage.viewBox[1] - dy, stage.viewBox[2], stage.viewBox[3]];
            //         stage.viewbox = stage.viewBox.concat();
            //         stage.resetViewBox();
            //     });
            //     // console.log(this, e)
            // }).bind("mousemove", {
            //     stage: this
            // }, mouseMove = function (e) {
            //     const stage = e.data.stage;
            //     //绑定鼠标移动事件
            //     stage.mousePos = {
            //         x: (e.clientX - stage.svgxc) * (1 / stage.scaleArr[stage.scale]) + stage.viewboxxc,
            //         y: (e.clientY - stage.svgyc) * (1 / stage.scaleArr[stage.scale]) + stage.viewboxyc
            //     };
            //     // console.log(stage.mousePos)
            // })
        },
        /**
         * 重设舞台的Viewbox显示区域
         * 内部函数包括计算viewbox的中心
         */
        resetViewBox: function () {
            const h = this.viewbox[3] * (1 / this.scaleArr[this.scale]);
            const w = this.viewbox[2] * (1 / this.scaleArr[this.scale]);
            this.viewBox = [
                this.viewbox[0] + (this.viewbox[2] - w) / 2,
                this.viewbox[1] + (this.viewbox[3] - h) / 2,
                w,
                h
            ];
            this.viewboxxc = this.viewbox[0] + this.viewbox[2] / 2;
            this.viewboxyc = this.viewbox[1] + this.viewbox[3] / 2;
            console.log('svg的viewbox中心', this.viewboxxc, this.viewboxyc);
            this.el.setAttribute('viewBox', this.viewBox.join(' '));
        },
        toolbarBind: function () {
            //关系切换绑定相关单机事件
            $("#linked-change-btn").bind('click', function () {
                $(this).trigger('stateChange');
            }).bind('stateChange', {
                stage: this
            }, function (event) {
                if (this.checked) {
                    event.data.stage.showAllLinked();
                } else {
                    event.data.stage.hideAllLinked();
                }
            });
            $("#step-change-btn").bind('click', function () {
                $(this).trigger('stateChange');
            }).bind('stateChange', {
                stage: this
            }, function (event) {
                if (this.checked) {
                    event.data.stage.showAllSteps();
                } else {
                    event.data.stage.hideAllSteps();
                }
            });
            $("#name-change-btn").bind('click', function () {
                $(this).trigger('stateChange');
            }).bind('stateChange', {
                stage: this
            }, function (event) {
                if (this.checked) {
                    event.data.stage.showAllNodesApp();
                } else {
                    event.data.stage.hideAllNodesApp();
                }
            })

            $("#step-create-btn").bind('click', {object: this}, function (event) {
                event.data.object.addStep();
            })
        },
        /**
         * 隐藏所有的关系
         */
        hideAllLinked: function () {
            this.$el.find('line').hide();
        },
        /**
         * 显示所有的关系
         */
        showAllLinked: function () {
            this.$el.find('line').show();
        },
        /**
         * 隐藏所有的阶段
         */
        hideAllSteps: function () {
            this.$el.find('#stepsContainer').hide();
        },
        /**
         * 显示所有的阶段
         */
        showAllSteps: function () {
            this.$el.find('#stepsContainer').show();
        },
        /**
         * 显示所有阶段的APP
         */
        showAllNodesApp: function () {
            this.workNodeMap.forEach(function (workNode) {
                workNode.showAppText();
            })
        },
        /**
         * 隐藏所有阶段的APP
         */
        hideAllNodesApp: function () {
            this.workNodeMap.forEach(function (workNode) {
                workNode.hideAppText();
            })
        },
        /**
         * 添加阶段
         */
        addStep: function () {
            let step = {
                stepIndex: this.options.steps[this.options.steps.length - 1].stepIndex + 1,
                name: "阶段",
                description: "描述",
                projectID: PROJECT_ID
            };
            //触发事件
            this.trigger('before.add-step', step)
            //执行函数
            this.options.steps.push(step);
            //触发事件
            this.trigger('after.add-step', step);
            //
            this.refreshGrid();
        },

        /**
         * 删除阶段
         * @param stepIndex 待删除的阶段索引
         */
        removeStep: function (stepIndex) {
            let node = this.options.nodes.find(function (node) {
                node.stepIndex == stepIndex
            })
            if (node) {
                //如果当前阶段存在节点,需要删除当前所有的
                alert("请删除当前所有的节点在执行操作");
                return;
            }
            let step = this.options.steps[stepIndex];
            //触发事件
            this.trigger('before.remove-step', step)
            console.log("执行删除阶段函数")
            //执行函数
            for (let i = 0; i < this.options.steps; i++) {
                if (this.options.steps[i].stepIndex === stepIndex) {
                    //执行删除
                    this.options.steps[i].splice(i, 1);
                }
            }
            //完成删除
            //触发事件
            this.trigger('after.remove-step', step)
            //重新绘制
            this.refreshGrid();
        },
        /**
         * 查看阶段
         * @param stepIndex 阶段索引
         */
        viewStep: function (step) {
            //触发事件
            this.trigger('before.view-step', step)
            //执行函数
            console.log("当前查看的阶段", step)
            //触发事件
            this.trigger('after.view-step', step)
        },
        /**
         *
         * @param oldStepIndex 旧的阶段索引
         * @param newStepIndex 新的阶段索引
         */
        updateStepIndex: function (oldStepIndex, newStepIndex) {

        },
        /**
         * 添加节点
         * @param 新节点所属的阶段
         */
        addNode: function (stepIndex) {
            let node = {
                lockState: false,
                name: "节点",
                nodeIndex: UUID(),
                state: 0,
                stepIndex: stepIndex,
                projectID: PROJECT_ID
            }
            //触发事件
            this.trigger('before.add-node', node)
            //触发事件
            this.trigger('after.add-node', node)
        },
        /**
         * 删除节点
         * @param nodeIndex
         */
        removeNode: function (nodeIndex) {
            let node = this.options.nodes[nodeIndex];
            if (node.lockState) {
                alert("当前节点处于锁定阶段，无法删除");
                return false;
            }
            if (node.isFinished) {
                alert("当前为已完成阶段，无法删除");
                return false;
            }
            this.trigger('before.remove-node', node);
            this.options.nodeMap.delete(nodeIndex);
            this.trigger('after.remove-node', node);
        },
        /**
         * 查看节点
         * @param nodeIndex 节点索引
         */
        viewNode: function (node) {
            this.trigger('before.view-node', node);

            this.trigger('after.view-node', node);
        },
        /**
         * 更新节点所属的阶段索引
         * @param oldStepIndex 旧的阶段索引
         * @param newStepIndex 新的阶段索引
         */
        updateStepIndexOfNode(oldStepIndex, newStepIndex) {

        }

    }
    var TIPS_TABLE = [{
        'cx': 30,
        'r': 10,
        'cy': 20,
        'class': "finished-node",
        text: "已完成"
    },
        {
            'cx': 30,
            'r': 10,
            'cy': 50,
            'class': "unfinished-node",
            text: "已完成"
        },
        {
            'cx': 30,
            'r': 10,
            'cy': 80,
            'class': "finished-node template",
            text: "已完成"
        }, {
            'cx': 30,
            'r': 10,
            'cy': 110,
            'class': "locked",
            text: "已完成"
        }
    ]

    /**
     *
     * @param {*} xy1
     * @param {*} xy2
     * @param {*} id
     * @param {*} stageDom
     */
    var WorkStep = function (xy1, xy2, options, stage, stepBarHeight) {
        this.xy1 = xy1; //起始坐标
        this.xy2 = xy2; //结束坐标
        this.options = options; //
        this.barHeight = stepBarHeight;
        this.stage = stage; //主舞台
        this.initStep();
    }
    WorkStep.prototype = {
        initStep: function () {
            //ID前缀
            const stepIndexPre = "#step-" + this.options.stepIndex;
            //新建阶段边框
            this.border = stepIndexPre + "-border";
            svg("rect", $(this.stage.options.stepsContainer), this.border, {
                class: 'work step border'

            });
            //新建阶段工具栏
            this.toolbar = stepIndexPre + "-toolbar";
            svg("g", $(this.stage.options.stepsContainer), this.toolbar, {
                "class": "stage-step-title"
            });
            //新建工具栏填充
            this.toolbarRect = stepIndexPre + "-toolbarRect";
            svg("rect", $(this.toolbar), this.toolbarRect, undefined);

            //新建设置按钮
            //新建文字
            this.text = stepIndexPre + "-title";
            svg("text", $(this.toolbar), this.text, {
                'text-anchor': "middle",
                "dominant-baseline": "middle",
            });
            this.drawStep();
            this.bindListener();
        },
        drawStep: function () {
            $(this.border).attr({
                x: this.xy1.x,
                y: this.xy1.y,
                width: this.xy2.x - this.xy1.x,
                height: this.xy2.y - this.xy1.y,
                ry: this.r,
                rx: this.r,
            });
            this.drawText();
            this.drawToolBar();
        },
        drawText: function () {
            $(this.text).attr({
                x: (this.xy1.x + this.xy2.x) / 2,
                y: this.xy1.y + this.barHeight / 2,
                "font-size": (this.barHeight / 2).toFixed(2) + "px",
            })[0].textContent = this.options.name;

        },
        drawToolBar: function () {
            $(this.toolbarRect).attr({
                x: this.xy1.x,
                y: this.xy1.y,
                width: this.xy2.x - this.xy1.x,
                height: this.barHeight,
                stroke: this.stokeColor,
                rx: this.r,
                ry: this.r
            });
            $(this.iconG).html($(this.iconG).html());
        },
        bindListener:function(){
            $(this.toolbar).on("click",{workStep:this},function (event) {
                $(event.data.workStep).trigger("onclick");
            })
        },
    }
    var CUR_STEP_NODE;
    var CUR_NODE;
    var WorkNode = function (node, xy, width, height, stage) {
        this.node = node;
        this.x = xy.x; //x坐标
        this.y = xy.y; //y坐标
        this.id = node.nodeIndex;
        this.width = width;//宽
        this.height = height;//长
        this.r = stage.options.nodeR; //宽
        this.nextWorkNodeMap = new Map(); //下一个节点
        this.preWorkNodeMap = new Map(); //上一个节点
        this.stage = stage;
        this.outPathMap = new Map();//向外发散的边
        this.menu = {
            r: this.r * 2.5,
            menuArr: [{
                name: "查看",
                icon: '#icon-view',
                func: function (workNode) {
                    CUR_STEP_NODE = workNode;
                    CUR_NODE = workNode.node;
                    viewNode();
                }
            }, {
                name: "移动",
                icon: '#icon-move',
                func: function (workNode) {
                    const x = workNode.x;
                    const y = workNode.y;
                    const stage = workNode.stage;
                    $(stage.options.stepsContainer).bind('mousemove', moveNode = function (e) {
                        workNode.x = workNode.stage.mousePos.x;
                        workNode.y = workNode.stage.mousePos.y;
                        workNode.refreshNode();
                    });
                    $(workNode.trigger).bind('contextmenu', cancelNoveNode = function (e) {
                        $(stage.options.stepsContainer).unbind('mousemove', moveNode);
                        $(workNode.trigger).unbind('contextmenu', cancelNoveNode);
                        workNode.x = x;
                        workNode.y = y;
                        workNode.refreshNode();
                    }).bind('click', applyMove = function (e) {
                        $(stage.options.stepsContainer).unbind('mousemove', moveNode);
                        $(workNode.trigger).unbind('contextmenu', cancelNoveNode);
                        const x = workNode.stage.mousePos.x;
                        const y = workNode.stage.mousePos.y;
                        workNode.stage.steps.forEach(function (step, id) {
                            if (x > step.xy1.x && y > step.xy1.y && y < step.xy2.y && x < step.xy2.x) {
                                workNode.node.stepIndex = id;
                                console.log(workNode.node)
                            }
                        });
                        console.log("完成移动")
                        workNode.stage.refreshGrid();
                    })
                }
            }, {
                name: "删除",
                icon: '#icon-delete',
                func: function (workNode) {
                    workNode.stage.removeNode(workNode);
                }
            }, {
                name: "链接",
                icon: "#icon-link",
                func: function (workNode) {
                    const line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
                    $(workNode.stage.options.edgesContainer).append(line);
                    $(line).attr({
                        x1: workNode.x,
                        y1: workNode.y,
                        x2: workNode.x + 1,
                        y2: workNode.y + 1,
                        class: "line-path",
                    });
                    workNode.stage.$el.bind('mousemove', drawline = function (e) {
                        $(line).attr({
                            x2: workNode.stage.mousePos.x,
                            y2: workNode.stage.mousePos.y
                        });
                    });
                    $(".trigger").bind('click', {nodeIndex: workNode.node.nodeIndex}, addPreNode = function (e) {
                        $(this).trigger('appendPre', [e.data.nodeIndex]);
                    });
                    workNode.stage.$el.bind('contextmenu', cancelDrawline = function (e) {
                        $(SVG_DOM).unbind('mousemove', drawline);
                        $(".trigger").unbind('click', addPreNode);
                        $(".line-path").remove();
                        $(SVG_DOM).unbind('contextmenu', cancelDrawline);
                        return false;
                    })
                }
            }],
        };
        this.groupDom = "";
        this.typeOfCircle = {
            true: 'stepnode finished',
            false: 'stepnode unfinished',
        };
        this.typeOfPath = {
            true: 'finished-path',
            false: 'unfinished-path'
        }
    }

    WorkNode.prototype = {
        constructor: WorkNode,
        initNode: function () {
            console.log("该节点对象的起始坐标(x,y)=(" + this.x + "," + this.y + ")");
            this.initDom();
            this.refreshNode();
            this.bindListener();
            // this.drawPath();
            // this.drawNode();
            // this.bind();
        },
        initDom: function () {
            this.nodeGID = "#" + this.node.nodeIndex;
            const $nodeG = $(svg('g', $(this.stage.options.nodesContainer), this.nodeGID, {class: 'work-node'}));
            const $trigger = svg('g', $nodeG, undefined, {
                class: 'work node ' + (this.node.isFinished ? 'finished ' : "unFinished") + (this.node.lockState ? "locked" : ""),
            })
            const $rect = $(svg('rect', $trigger, undefined, {
                rx: 10,
                ry: 10,
                width: this.width,
                height: this.height,
            }))
            const textAttr = {
                'text-anchor': "middle",
                'font-size': (this.height / 30).toFixed(2) + "em"
            }
            const name = svg('text', $trigger, undefined, {
                class: 'work node name',
            });
            name.textContent = this.node.name;
            $(name).attr(textAttr);
            const app = svg('text', $trigger, undefined, {
                class: 'work node appName',
            });
            app.textContent = this.node.appName;
            $(app).attr(textAttr).hide();//隐藏app


        },
        refreshNode: function () {
            $('rect', this.nodeGID).attr({
                x: this.x,
                y: this.y,
                // "transform":"translate("+this.x+","+this.y+")"
            })
            $('text', this.nodeGID).attr({
                x: this.x + this.width / 2,
                y: this.y + this.height / 3 * 2,
                // "transform":"translate("+this.x+","+this.y+")"
            })
            $('.name', this.nodeGID)[0].textContent = this.node.name;
            $('.appName', this.nodeGID)[0].textContent = this.node.appName;
        },
        click:function(){
            console.log("点击了节点",this)
            $(this).trigger("onclick");
        },
        /**
         * 绑定相关事件
         */
        bindListener: function () {
            $(this.nodeGID).on('click',{workNode:this},function(event){
                console.log("点击了节点",event.data.workNode.node.nodeIndex)
                $(event.data.workNode).trigger("onclick");
            })
        },

    };


    /**
     * 路径的构造函数
     * @param nodeI 开始节点索引
     * @param nodeJ 结束节点索引
     * @param pathClass 路径的属性
     * @param dArr 路径的绘制数组
     * @constructor
     */
    var WorkPath = function (workNodeI, workNodeJ,containerID) {
        this.workNodeI = workNodeI;
        this.workNodeJ = workNodeJ;
        this.nodeI= this.workNodeI.node.nodeIndex;
        this.nodeJ=  this.workNodeJ.node.nodeIndex;
        this.midPos ={};
        this.$container =$(containerID)
        this.initDom();
    }
    WorkPath.prototype = {
        initDom: function () {
            const path = svg("path",  this.$container, undefined, {
                nodeI: this.nodeI,
                nodeJ: this.nodeJ,
                class: this.getPathClass(),
                d: this.getPathD(),
                'stroke': "#838B8B",
                'stroke-width': 4
            })
            this.bindListener();
        },
        setPathPos: function (dArr) {
            $('[nodei="' + this.nodeI + '"][nodej="' + this.nodeJ + '"]').attr({
                d: this.getPathClass()
            })
        },
        bindListener:function () {
            $('[nodei="' + this.nodeI + '"][nodej="' + this.nodeJ + '"]').on("click",{workPath:this},function (event) {
                console.log("点击了边")
                $(event.data.workPath).trigger("onclick");
            })
        },
        getPathClass:function(){
            return  pathClass = "work path " + ( this.workNodeJ.node.isFinished ? "finished" : "unFinished");
        },
        getPathD:function(){
            let x1 = this.workNodeI.x+ this.workNodeI.width/2,
                y1=this.workNodeI.y+  this.workNodeI.height/2,
                x2=this.workNodeJ.x+ this.workNodeJ.width/2,
                y2=this.workNodeJ.y+  this.workNodeI.height/2;
            this.midPos={
                x:(x2+x1)/2,
                y:(y2+y1)/2,
            }
            return [this.getSVGPath("M", x = x1, y =  y1),
                this.getSVGPath("L", x = x2 , y =  y2)];//定义路径数组;
        },
        getSVGPath:function (op, x1, y1, x2, y2, x, y) {
            return "" + op  + (x1 ? x1 : "") + " " + (y1 ? y1 : "") + " " + (x2 ? x2 : "") + " " + (y2 ? y2 : "") + " " + (x ? x : "") + " " + (y ? y : "");
        }

    }

    function showDelete(e) {
        $(e).attr('class', $(e).attr('class') + ' to-delete-path')
        $(e).attr('onclick', 'deleteEdge(this)')
    }


    function hideDelete(e) {
        $(e).attr('class', $(e).attr('class').replace('to-delete-path', '').trim())
    }


    const svgns = "http://www.w3.org/2000/svg";

    /**
     * 创建SVG对象
     * @param tagName 标签名称
     * @param $parent 父节点
     * @param id dom对象的ID
     * @param attr attr
     * @returns {HTMLElement | SVGAElement | SVGCircleElement | SVGClipPathElement | SVGComponentTransferFunctionElement | SVGDefsElement | SVGDescElement | SVGEllipseElement | SVGFEBlendElement | SVGFEColorMatrixElement | SVGFEComponentTransferElement | SVGFECompositeElement | SVGFEConvolveMatrixElement | SVGFEDiffuseLightingElement | SVGFEDisplacementMapElement | SVGFEDistantLightElement | SVGFEFloodElement | SVGFEFuncAElement | SVGFEFuncBElement | SVGFEFuncGElement | SVGFEFuncRElement | SVGFEGaussianBlurElement | SVGFEImageElement | SVGFEMergeElement | SVGFEMergeNodeElement | SVGFEMorphologyElement | SVGFEOffsetElement | SVGFEPointLightElement | SVGFESpecularLightingElement | SVGFESpotLightElement | SVGFETileElement | SVGFETurbulenceElement | SVGFilterElement | SVGForeignObjectElement | SVGGElement | SVGImageElement | SVGGradientElement | SVGLineElement | SVGLinearGradientElement | SVGMarkerElement | SVGMaskElement | SVGPathElement | SVGMetadataElement | SVGPatternElement | SVGPolygonElement | SVGPolylineElement | SVGRadialGradientElement | SVGRectElement | SVGSVGElement | SVGScriptElement | SVGStopElement | SVGStyleElement | SVGSwitchElement | SVGSymbolElement | SVGTSpanElement | SVGTextContentElement | SVGTextElement | SVGTextPathElement | SVGTextPositioningElement | SVGTitleElement | SVGUseElement | SVGViewElement | SVGElement | Element}
     */
    function svg(tagName, $parent, id, attr) {
        const el = document.createElementNS(svgns, tagName);
        if (id) $(el).attr("id", id.substring(1, id.length));
        if (attr) $(el).attr(attr);
        if ($parent) $parent.append(el);
        return el;
    }


    $.fn.workFlow = function (option) {
        let value, args = Array.prototype.slice.call(arguments, 1);
        this.each(function () {
            var $this = $(this),
                data = $this.data('workFlow'),
                options = $.extend({}, WorkFlow.DEFAULTS, $this.data(),
                    typeof option === 'object' && option);
            if (!data) {
                $this.data('workFlow', (new WorkFlow(this, options)));
            }
        })
        return typeof value === 'undefined' ? this : value;
    }
})
$(document).ready(function () {
    let workOptions = {
        afterViewStep: function (step) {
            STEP_JS.afterViewStep(step);
        },
        beforeRemoveStep: function (step) {
            STEP_JS.beforeRemoveStep(step);
        },
        beforeAddStep: function (step) {
            STEP_JS.beforeAddStep(step);
        },
        beforeAddNode: function (node) {
            NODE_JS.beforeAddNode(node);
        },
        beforeRemoveNode: function (node) {
            NODE_JS.beforeRemoveNode(node);
        },
        beforeViewNode: function (node) {
            NODE_JS.beforeViewNode(node);
        }
    }
    $.ajax({
        url: "/templates/api/project",
        data: {
            projectID: PROJECT_ID,
        },
        async: false,
        success: function (data) {
            workOptions['nodeMap'] = new Map();
            workOptions['stepMap'] = new Map();
            for (let nodeIndex in data.nodeMap) {
                workOptions['nodeMap'].set(nodeIndex, data.nodeMap[nodeIndex]);
            }
            for (let stepIndex in data.stepMap) {
                workOptions['stepMap'].set(stepIndex, data.stepMap[stepIndex]);
            }
            $(SVG_DOM).workFlow(workOptions);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("出现错误")

        }

    })
})

function UUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}