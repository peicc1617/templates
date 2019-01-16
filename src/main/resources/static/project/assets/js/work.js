var stage;
var SVG_DOM = document.getElementsByTagNameNS('http://www.w3.org/2000/svg', 'svg')[1];
$(document).ready(function () {

    //当前选中的节点
    CUR_NODE = {};
    SVG_DOM.setAttribute('width', '100%');

    var WorkFlow = function (el, options) {
        this.options = options;
        this.el = el;
        this.$el = $(el);
        this.scaleArr = [0.25, 0.33, 0.5, 0.67, 0.75, 0.8, 0.9, 1];
        this.scale = 1;
        this.init();
    }


    WorkFlow.DEFAULTS = {
        width: undefined,
        height: undefined,
        nodeWidth: 75,
        nodeHeight: 30,
        nodeR: 20,
        "name": "new stage",
        "info": "info about this stage",
        'symbolsContainer': "#symbolsContainer", //图标容器
        'stepsContainer': "#stepsContainer", //阶段容器
        'edgesContainer': '#edgesContainer', //边容器
        'nodesContainer': '#nodesContainer', //节点容器
        'toolContainer': '#toolContainer', //提示容器
        'tipsContainer': '#tipsContainer', //提示容器
        'nodeMenu': {
            data: [
                {
                    name: "查看工作节点",
                    icon: '#icon-view',
                    func: function (workFlow, workNode) {
                        workFlow.viewNode(workNode.node);
                    }
                },
                {
                    name: "删除工作节点",
                    icon: '#icon-delete',
                    func: function (workFlow, workNode) {
                        workFlow.removeNode(workNode.node);
                    }
                }, {
                    name: "创建工作路径",
                    icon: "#icon-link",
                    func: function (workFlow, workNode) {
                    }
                }
            ]
        },
        'stepMenu': {
            data: [
                [{
                    name: "阶段前移",
                    icon: '#icon-pre',
                    func: function (workFlow,workStep) {
                        workFlow.updateStepPos(workStep.step,workStep.step.pos-1);
                    }
                }, {
                    name: "阶段后移",
                    icon: "#icon-next",
                    func: function (workFlow,workStep) {
                        workFlow.updateStepPos(workStep.step,workStep.step.pos+1);
                    }
                }],
                [{
                    name: "添加前序阶段",
                    icon: '#icon-pre-2',
                    func: function (workFlow,workStep) {
                        let step = {
                            stepIndex: UUID(),
                            name: "阶段",
                            description: "描述",
                            projectID: PROJECT_ID,
                            pos:workStep.step.pos,
                        };
                        workFlow.addStep(step)
                    }
                }, {
                    name: "添加后序阶段",
                    icon: "#icon-next-2",
                    func: function (workFlow,workStep) {
                        let step = {
                            stepIndex: UUID(),
                            name: "阶段",
                            description: "描述",
                            projectID: PROJECT_ID,
                            pos:workStep.step.pos+1,
                        };
                        workFlow.addStep(step)
                    }
                }],
                [{
                    name: "新建工作节点",
                    icon: '#icon-add',
                    func: function (workFlow, workStep) {
                        workFlow.addNode(workStep.step.stepIndex);
                    }
                }, {
                    name: "删除阶段",
                    icon: '#icon-delete',
                    func: function (workFlow, workStep) {
                        workFlow.removeStep(workStep.step.stepIndex)
                    }
                },],
                [{
                    name: "查看阶段",
                    icon: '#icon-view',
                    func: function (workFlow, workStep) {
                        workFlow.viewStep(workStep.step)
                    }
                },]

            ]
        },
        'pathMenu': {
            name: "删除工作路径",
            icon: '#icon-delete-2',
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
        beforeViewNode:function(step){

        },
        afterViewNode:function(step){

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
        beforeUpdateStepPos:function () {

        },
        afterUpdateStepPos:function () {

        }
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
            'before.view-node.work.flow': 'beforeViewNode',
            'after.view-node.work.flow': 'afterViewNode',
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
            /**
             * 查看连线触发的事件
             */
            'before.update-step-pos.work.flow': 'beforeUpdateStepPos',
            'after.update-step-pos.work.flow': 'afterUpdateStepPos',
        };

    WorkFlow.prototype = {
        constructor: WorkFlow,
        init: function () {
            this.refreshData();
            this.initView();
            this.bindToolListener()
        },
        /**
         * 初始化视图
         */
        initView: function () {
            this.initNodeMenu();
            this.initStepMenu();
            this.initPathMenu();
            this.refreshView();
        },
        clearStage: function () {
            $(this.options.stepsContainer + "," + this.options.edgesContainer + "," + this.options.nodesContainer).empty();
        },
        refreshView: function () {
            this.clearStage();
            this.getPath();//获取路径
            this.planPath();//调整节点
            this.initGrid();//初始化网格
            this.drawSteps();
            this.drawNodes();//绘制节点
            this.drawPath();//绘制工作路径
            this.refreshNodeMenu();//初始化节点工具菜单
            this.refreshStepMenu();//初始化阶段工具菜单
            this.refreshPathMenu();//初始化工作路径的俺就
            this.bindListener();
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
         * 根据节点之间的关系得到所有的路径
         */
        getPath: function () {
            this.options.stepMap = new Map(Array.from(this.options.stepMap).sort((e1,e2)=>e1[1].pos-e2[1].pos));
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
                preStepIndex = stepIndex;
            });
            this.stepStartMap = stepStartMap;
            this.paths = paths;
            this.rowN = paths.length;
        },
        /**
         * 根据路径，计算整个网格的大小
         */
        initGrid: function () {
            this.nodeHorDis = this.options.nodeWidth * 3; //节点间距
            this.nodeVerDis = this.options.nodeHeight * 2 //节点垂直间
            this.stepBarHeight = this.options.nodeHeight; //阶段标题高度
            this.refreshViewBox();
            const start = {
                x: this.nodeHorDis / 2,
                y: this.nodeVerDis + this.stepBarHeight,
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
            this.nodeWidth = this.options.nodeWidth;
            this.nodeHeight = this.options.nodeHeight;
        },
        planPath: function () {
            //为每一个节点安排坐标
            for (let i = 0; i < this.paths.length; i++) {
                //按照每条路径分别处理
                let path = this.paths[i];
                for (let j = 0, x = 0; j < path.length; j++, x++) {
                    //获取节点
                    let node = path[j];
                    //如果节点有x坐标，说明之前已经安排过了，不再进行安排
                    //否则安排节点的y坐标
                    node.y = i;
                    //获取节点所在阶段的最左边界
                    let left = this.stepStartMap.get(node.stepIndex);
                    if (x < left) {
                        //如果节点的坐标小于最左边界，那么把节点x调整到边界内
                        x = left;
                    }
                    node.x = x;
                }
            }
        },
        drawSteps: function () {
            const stepStartArr = Array.from(this.stepStartMap);
            this.workStepMap = new Map()
            for (let i = 0; i < stepStartArr.length; i++) {
                let stepIndex = stepStartArr[i][0];
                let start = stepStartArr[i][1];
                let end;
                if (i < stepStartArr.length - 1) {
                    end = stepStartArr[i + 1][1] - 1;
                } else {
                    end = this.columnN - 1;
                }
                let stepXY = {
                    xy1: {
                        x: this.grid[0][start].x - this.nodeHorDis / 8,
                        y: this.grid[0][start].y - this.nodeVerDis / 2 - this.stepBarHeight
                    },
                    xy2: {
                        x: this.grid[this.grid.length - 1][end].x + this.nodeWidth + this.nodeHorDis / 8,
                        y: this.grid[this.grid.length - 1][start].y + this.nodeVerDis
                    }
                }
                const workStep = new WorkStep(stepXY.xy1, stepXY.xy2, this.options.stepMap.get(stepIndex), this, this.stepBarHeight);
                this.workStepMap.set(stepIndex, workStep);
            }
        },
        /**
         * 更新视图
         */
        drawNodes: function () {
            this.workNodeMap = new Map();
            this.options.nodeMap.forEach((node, nodeIndex) => {
                const workNode = new WorkNode(node, this.grid[node.y][node.x], this.nodeWidth, this.nodeHeight, this.options.nodesContainer);
                this.workNodeMap.set(nodeIndex, workNode);
                workNode.initNode();
            });
            this.options.nodeMap.forEach((node, nodeI) => {
                if (node.nextNodeIndexList != null && node.nextNodeIndexList != undefined && node.nextNodeIndexList.length > 0) {
                    node.nextNodeIndexList.forEach(nodeJ => {
                        const workNodeI = this.workNodeMap.get(nodeI);
                        const workNodeJ = this.workNodeMap.get(nodeJ);
                        workNodeI.nextWorkNodeMap.set(nodeJ, workNodeJ);
                        workNodeJ.preWorkNodeMap.set(nodeI, workNodeI);
                    })
                }
            })

        },
        /**
         * 绘制工作流路径
         */
        drawPath: function () {
            this.workPathList = [];
            //绘制工作流路径
            this.workNodeMap.forEach((workNodeI, nodeI) => {
                workNodeI.nextWorkNodeMap.forEach((workNodeJ, nodeJ) => {
                    const path = new WorkPath(workNodeI, workNodeJ, this.options.edgesContainer)
                    this.workPathList.push(path);
                    workNodeI.outPathMap.set(nodeJ, path);
                })
            })

        },
        /**
         * 初始化工作节点菜单
         * 初始化的内容包括，
         *  1.新建dom
         *  2.设定class
         *  3.设定filter
         *  4.按钮的图标
         */
        initNodeMenu: function () {
            this.nodeMenuID = "#nodeMenu";
            const $nodeToolGroup = $(svg("g", $(this.options.toolContainer), this.nodeMenuID)),
                n = this.options.nodeMenu.data.length;   //按钮的个数
            for (let i = 0; i < n; i++) {
                let btnOption = this.options.nodeMenu.data[i];
                let workFlow = this;
                const $btnG = $(svg("g", $nodeToolGroup), undefined, {
                    class: "btn"
                }).on("click", function (event) {
                    btnOption.func(workFlow, workFlow.workNodeMap.get($nodeToolGroup.attr('nodeindex')));
                    workFlow.nodeMenuClose();
                })
                const $btn = $(svg("circle", $btnG, undefined, {
                    "filter": "url(#btnFeOffset)",
                }))
                const $icon = svg('use', $btnG, undefined, {
                    "xlink:href": btnOption.icon,
                });
                svg('title', $btnG, undefined, undefined)
                    .appendChild(document.createTextNode(btnOption.name));
                $btnG.html($btnG.html());
            }
        },
        /**
         * 更新工作节点的菜单，更新的内容包括
         * 1.按钮的大小
         * 2.按钮的相对位置
         */
        refreshNodeMenu: function () {
            const r = this.options.nodeHeight / 3,         //圆形按钮的半径
                n = this.options.nodeMenu.data.length,   //按钮的个数
                R = this.options.nodeWidth / 2 + r * 2,        //环型布局的半径
                yo = this.options.nodeHeight / 2,            //环型布局的圆心
                xo = this.options.nodeWidth / 2;
            //计算每一个按钮的位置，目前是写死的，动态拓展的需要调整
            const rArr = [];//角度数组
            if ((n & 1) === 1) {
                //如果个数是奇数
                rArr.push(0);
                for (let i = 1; i < n; i += 2) {
                    rArr.unshift(i / 6 * Math.PI);
                    rArr.push(-i / 6 * Math.PI);
                }
            } else {
                //如果个数是偶数
                for (let i = 0; i < n; i += 2) {
                    rArr.unshift((1 + i) / 12 * Math.PI);
                    rArr.push(-(1 + i) / 12 * Math.PI);
                }
            }
            const posArr = rArr.map(ra => [R * Math.cos(ra), (R * Math.sin(ra))]);
            const btnGArr = $(this.nodeMenuID).children();
            for (let i = 0; i < n; i++) {
                const x = xo + posArr[i][0], y = yo + posArr[i][1];
                const $btn = $(btnGArr[i]);
                $btn.children("circle").attr({
                    cx: x,
                    cy: y,
                    r: r
                })
                $btn.children("use").attr({
                    "width": r * 1.5,
                    "height": r * 1.5,
                    "x": x - r * 1.5 / 2,
                    "y": y - r * 1.5 / 2
                })

            }
        },
        /**
         * 初始化阶段菜单按钮图标
         */
        initStepMenu: function () {
            this.stepMenuID = "#stepMenu";
            const stepToolOption = this.options.stepMenu;
            const $stepToolGroup = $(svg("g", $(this.options.toolContainer), this.stepMenuID));
            for (let i = 0; i < stepToolOption.data.length; i++) {
                let btnOptions = stepToolOption.data[i];
                const $btnGG = $(svg("g", $stepToolGroup))
                for (let j = 0; j < btnOptions.length; j++) {
                    const $btnG = $(svg("g", $btnGG, undefined, {
                        stepindex: undefined
                    })).on("click", {
                        workFlow: this,
                    }, function (event) {
                        let workFlow = event.data.workFlow;
                        btnOptions[j].func(workFlow, workFlow.workStepMap.get($stepToolGroup.attr('stepindex')));
                        workFlow.stepMenuClose();//每次点击以后关闭阶段菜单
                    });
                    const $btn = $(svg("rect", $btnG, undefined, {"filter": "url(#btnFeOffset)"}))
                    const $icon = svg('use', $btnG, undefined, {"xlink:href": btnOptions[j].icon});
                    svg('title', $btnG).appendChild(document.createTextNode(btnOptions[j].name));
                    $btnG.html($btnG.html());
                }
            }
        },
        /**
         * 绑定菜单按钮对应的事件
         */
        bindStepMenuListener: function () {

        },
        refreshStepMenu: function () {
            const width = this.stepBarHeight * 2 / 3,//图标的宽度
                xStart = width / 3,//按钮组的开始坐标
                yStart = this.stepBarHeight / 2 - width / 2,
                n = this.options.stepMenu.data.length;
            const btnGArr = $(this.stepMenuID).children();
            for (let i = 0; i < n; i++) {
                let m = this.options.stepMenu.data[i].length;
                const btnArr = $(btnGArr[i]).children();
                for (let j = 0; j < m; j++) {
                    const $btn = $(btnArr[j]);
                    $btn.children("rect").attr({
                        width: width,
                        height: width,
                        rx: width / 4,
                        ry: width / 4,
                        x: xStart * (j + 1) + width * j,
                        y: yStart + this.stepBarHeight * i,
                    })
                    $btn.children("use").attr({
                        "width": width * 0.75,
                        "height": width * 0.75,
                        x: xStart * (j + 1) + width * j + width * 0.25 / 2,
                        y: yStart + this.stepBarHeight * i + width * 0.25 / 2,
                    });
                }
            }

        },

        /**
         * 初始化工作路径按钮菜单，主要初始化的功能如下
         * 1.新建DOM
         * 2.设置滤镜
         * 3.设置图标
         */
        initPathMenu: function () {
            this.pathMenu = "#pathMenu";
            const pathMenuOption = this.options.pathMenu;
            const $pathMenuGroup = $(svg("g", $(this.options.toolContainer), this.pathMenu));
            const $btnG = $(svg("g", $pathMenuGroup))
            const $btn = $(svg("circle", $btnG, undefined, {"filter": "url(#btnFeOffset)"}))
            const $icon = svg('use', $btnG, undefined, {"xlink:href": pathMenuOption.icon,});
            svg('title', $btnG).appendChild(document.createTextNode(pathMenuOption.name));
            $btnG.html($btnG.html());
        },
        /**
         * 绘制工作路径按钮菜单，主要绘制的内容如下
         * 1.确定图标的半径
         * 2.确定菜单的相对位置
         * 3.确定按钮的
         */
        refreshPathMenu: function () {
            const r = this.stepBarHeight / 3;//图标的半径
            const xStart = 0, yStart = 0;
            $(this.pathMenu).find("circle").attr({
                cx: xStart,
                cy: yStart - r,
                r: r
            })
            $(this.pathMenu).find("use").attr({
                "width": r * 1.5,
                "height": r * 1.5,
                "x": xStart - r * 1.5 / 2,
                "y": yStart - r - r * 1.5 / 2
            })

        },
        /**
         * 绑定界面的工具栏的响应事件
         * #linked-change-btn 工作路径切换按钮
         * #step-change-btn 阶段显示切换按钮
         * #name-change-btn 节点名称显示切换按钮
         */
        bindToolListener: function () {
            let workFlow = this;
            //关系切换绑定相关单机事件
            $("#linked-change-btn").bind('click', function () {
                if (this.checked) {
                    workFlow.showAllLinked();
                } else {
                    workFlow.hideAllLinked();
                }
            });
            //绑定阶段显示按钮事件
            $("#step-change-btn").on('click',function () {
                if (this.checked) {
                    workFlow.showAllSteps();
                } else {
                    workFlow.hideAllSteps();
                }
            });
            //绑定创新方法显示按钮事件
            $("#name-change-btn").on('click',function () {
                if (this.checked) {
                    workFlow.showAllNodesApp();
                } else {
                    workFlow.hideAllNodesApp();
                }
            })
            let viewMove = false;
            this.$el.on({
                mousewheel:function (e) {
                    if (e.originalEvent.wheelDelta > 0) {
                        console.log("缩小", workFlow.scale)
                        //缩小
                        workFlow.scale -= 0.1;
                        if (workFlow.scale < 0.1) {
                            workFlow.scale = 0.1;
                        }
                    } else {
                        console.log("放大", workFlow.scale)
                        //放大
                        workFlow.scale += 0.1;
                    }
                    workFlow.refreshViewBox();
                    e = e || window.event;
                    if(e.preventDefault) {
                        // Firefox
                        e.preventDefault();
                        e.stopPropagation();
                    } else {
                        // IE
                        e.cancelBubble=true;
                        e.returnValue = false;
                    }
                    return false;
                },
                mousedown:function(){
                    viewMove=true;
                    workFlow.startMoveView();
                },
                mouseup:function () {
                    if(viewMove){
                        workFlow.stopMoveView();
                    }
                },
                mouseleave:function () {
                    if(viewMove){
                        workFlow.stopMoveView();
                    }
                },
                mousemove:function (e) {
                    //获取svg相对于浏览器的坐标
                    const svgPos = {
                        x: workFlow.el.getBoundingClientRect().x,
                        y: workFlow.el.getBoundingClientRect().y,
                    };
                    //获取鼠标在svg内的坐标
                    workFlow.mousePos = {
                        x: workFlow.viewBoxX + ((e.originalEvent.x || e.originalEvent.layerX || 0) - svgPos.x) * workFlow.scale,
                        y: workFlow.viewBoxY + ((e.originalEvent.y || e.originalEvent.layerY || 0) - svgPos.y) * workFlow.scale
                    }
                }
            })
        },
        /**
         * 绑定事件监听器
         * 1.绑定workNode的onclick事件，mousedown事件，mouseup事件
         * 2.绑定workStep的onclick事件
         * 3.绑定path的onclick事件
         */
        bindListener: function () {
            let workFlow = this;
            this.workNodeMap.forEach((workNode, nodeIndex) => {
                $(workNode).on({
                    mousedown: function () {
                        workFlow.startMoveNode(workNode);
                    },
                    mouseup: function () {
                        workFlow.stopMoveNode(workNode);
                    },
                    onclick:function () {
                        workFlow.clickWorkNode(nodeIndex);
                    }
                });
            })
            this.workStepMap.forEach((workStep, stepIndex) => {
                $(workStep).on("onclick", {stepIndex: stepIndex, workFlow: this}, function (event) {
                    event.data.workFlow.clickWorkStep(stepIndex);
                })
            })
            this.workPathList.forEach((workPath) => {
                $(workPath).on("onclick", {stepIndex: workPath, workFlow: this}, function (event) {
                    event.data.workFlow.clickWorkPath(workPath);
                })
            })
        },
        /**
         * 开始移动节点
         */
        startMoveNode: function (workNode) {
            workNode.translate(0, 0);
            let workFlow = this;
            // $(this.options.stepsContainer)
            this.$el.on('mousemove', moveNode = function () {
                const pos = workFlow.getMousePos();
                const dPos = {
                    x: pos.x - workNode.x-workNode.width/2,
                    y: pos.y - workNode.y-workNode.height/2
                }
                workNode.translate(dPos.x, dPos.y);
            });
        },
        /**
         * 停止移动节点
         * @param workNode
         */
        stopMoveNode: function (workNode) {
            if (workNode.transform) {
                //如果workNode的transform不为空，说明发生了移动，那么判断当前位置处于哪个阶段内
                //首先取消鼠标移动事件的绑定函数
                this.$el.off('mousemove', moveNode);
                const x = workNode.newX;//首先获取当前节点的最新坐标
                const y = workNode.newY;
                //判断当前节点所处的节点
                this.workStepMap.forEach((workStep, stepIndex) => {
                    if (x > workStep.xy1.x && y > workStep.xy1.y && y < workStep.xy2.y && x < workStep.xy2.x) {
                        if (stepIndex === workNode.stepIndex) {
                            //没有发生改变，那么直接取消移动即可
                            workNode.transform == undefined;
                            return;
                        } else {
                            console.log("当前节点成功移动到了" + stepIndex);
                            //工作节点所处的阶段发生改变，更新stepIndex
                            workNode.node.stepIndex = stepIndex;
                            //重新绘制
                            this.refreshView();
                        }
                    }
                });
            } else {
                //如果workNode的transfrom为空，说明没有触发长按移动事件，所以这个时候需要触发点按事件
                this.clickWorkNode(workNode.node.nodeIndex);
            }
        },
        getMousePos: function () {
            //获取当前鼠标在svg坐标系中的位置
            return this.mousePos;
        },
        clickWorkNode: function (nodeIndex) {
            let preNodeIndex = $(this.nodeMenuID).attr("nodeindex");//获取之前的节点
            if (nodeIndex == undefined || preNodeIndex != undefined && $(this.nodeMenuID).attr('class') === "active") {
                //首先关闭,并且删除
                this.nodeMenuClose();
                if (preNodeIndex !== nodeIndex) {
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkNode(nodeIndex);
                    }, 10);
                }
            } else if (preNodeIndex == undefined && $(this.nodeMenuID).attr('class') !== "active") {
                //如果之前没有点击过节点并且处于关闭状态
                const workNode = this.workNodeMap.get(nodeIndex);
                this.nodeMenuOpen(nodeIndex, workNode.x, workNode.y)
            }

        },
        clickWorkStep: function (stepIndex) {
            let preStepIndex = $(this.stepMenuID).attr("stepindex");//获取之前的节点
            if (stepIndex == undefined || preStepIndex != undefined && $(this.stepMenuID).attr('class') === "active") {
                //首先关闭,并且删除
                this.stepMenuClose();
                if (preStepIndex !== stepIndex) {
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkStep(stepIndex);
                    }, 10);
                }
            } else if (preStepIndex == undefined && $(this.stepMenuID).attr('class') !== "active") {
                //如果之前没有点击过节点并且处于关闭状态
                const workStep = this.workStepMap.get(stepIndex);
                this.stepMenuOpen(stepIndex, workStep.xy2.x, workStep.xy1.y);
            }
        },
        clickWorkPath: function (workPath) {
            let pathid = $(this.pathMenu).attr("pathid");
            if (workPath == undefined || pathid != undefined && $(this.pathMenu).attr('class') === "active") {
                //首先关闭,并且删除
                this.pathMenuClose();
                if (pathid !== (workPath.nodeI + workPath.nodeJ)) {
                    let workFlow = this;
                    setTimeout(function () {
                        workFlow.clickWorkPath(workPath);
                    }, 10);
                }
            } else if (pathid == undefined && $(this.pathMenu).attr('class') !== "active") {
                //如果之前没有点击过节点并且处于关闭状态
                this.pathMenuOpen(workPath.nodeI + workPath.nodeJ, workPath.midPos.x, workPath.midPos.y)
            }
        },
        stepMenuOpen: function (stepindex, x, y) {
            $(this.stepMenuID).attr({
                class: "active",
                "stepindex": stepindex,
                transform: "translate(" + x + ", " + y + ")",
            });
            this.nodeMenuClose();
            this.pathMenuClose();
        },
        stepMenuClose: function () {
            $(this.stepMenuID).removeAttr("class").removeAttr("stepindex");
        },
        nodeMenuOpen: function (nodeindex, x, y) {
            $(this.nodeMenuID).attr({
                class: "active",
                "nodeindex": nodeindex,
                transform: "translate(" + x + ", " + y + ")",
            });
            this.stepMenuClose();
            this.pathMenuClose();
        },
        nodeMenuClose: function () {
            $(this.nodeMenuID).removeAttr("class").removeAttr("nodeindex",);
        },
        pathMenuOpen: function (pathid, x, y) {
            $(this.pathMenu).attr({
                class: "active",
                "pathid": pathid,
                transform: "translate(" + x + ", " + y + ")",
            });
            this.nodeMenuClose();
            this.stepMenuClose();
        },
        pathMenuClose: function () {
            $(this.pathMenu).removeAttr("class").removeAttr("pathid");
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

        refreshViewBox: function () {
            //计算舞台高度
            this.stageHeight = (this.rowN + 2) * this.nodeVerDis;
            //计算控件高度，为了更好的显示，控件高度不会大于浏览器窗口高度
            this.height = this.stageHeight<window.innerWidth?this.stageHeight:window.innerHeight;
            //计算舞台宽度
            this.stageWidth = (this.columnN + 1) * this.nodeHorDis;
            //获取svg控件宽度
            this.width = this.$el.width();
            let startX = 0;
            if(this.stageWidth<this.width){
                //如果舞台宽度小于控件宽度，那么调整舞台显示宽度为svg的宽度
                //并且为了保证布局在中心部分显示，所以需要调整viewBox的x1,y1
                startX=(this.stageWidth-this.width)/2
                    this.stageWidth=this.width;
            }
            //计算viewBox的高度
            this.viewBoxHeight = this.height * this.scale;
            //计算viewBox宽度
            this.viewBoxWidth = this.width * this.scale;
            //如果viewbox中心坐标没有初始化，那么初始化中心坐标
            if (this.viewBoxCX == undefined && this.viewBoxCY == undefined) {
                this.viewBoxCX = startX + this.viewBoxWidth / 2;
                this.viewBoxCY = 0 + this.viewBoxHeight / 2;
            }
            //根据中心坐标计算viewbox的左上角起始坐标
            this.viewBoxX = this.viewBoxCX - this.viewBoxWidth / 2;
            this.viewBoxY = this.viewBoxCY - this.viewBoxHeight / 2;
            //防止viewbox的边界超过舞台边界
            // if(this.viewBoxX<0){
            //     //防止超过右边界
            //     this.viewBoxX =0;
            // }
            // if(this.viewBoxY<0){
            //     //防止超过下边界
            //     this.viewBoxY = 0;
            // }
            // if(this.viewBoxX+this.viewBoxWidth>this.stageWidth){
            //     //防止超过左边界
            //     this.viewBoxX = this.stageWidth-this.viewBoxWidth;
            // }
            // if(this.viewBoxY+this.viewBoxHeight>this.stageHeight){
            //     //防止超过左边界
            //     this.viewBoxY = this.stageHeight-this.viewBoxHeight;
            // }
            this.viewBox = [
                this.viewBoxX,
                this.viewBoxY,
                this.viewBoxWidth,
                this.viewBoxHeight
            ];
            this.el.setAttribute('viewBox', this.viewBox.join(' '));
            this.$el.height(this.height);
        },
        startMoveView: function () {
            console.log("开始")
            let workFlow = this;
            const prePos = workFlow.getMousePos();//获取鼠标在svg坐标系内的坐标
            const initViewBoxCX = this.viewBoxCX,initViewBoxCY = this.viewBoxCY;
            this.$el.on("mousemove", viewMove = function (e) {
                const curPos = workFlow.getMousePos();//获取鼠标在SVG内的当前的坐标
                //计算得到鼠标的坐标变化值
                let dx = curPos.x-prePos.x;
                let dy = curPos.y-prePos.y;
                console.log(dx,dy);
                workFlow.viewBoxCX= initViewBoxCX-dx;
                workFlow.viewBoxCY= initViewBoxCY-dy;
                workFlow.refreshViewBox()
            })
        },
        stopMoveView: function () {
            this.$el.off("mousemove",viewMove);
        },
        /**
         * 重设舞台的Viewbox显示区域
         * 内部函数包括计算viewbox的中心
         */
        resetViewBox: function () {
            let width = this.$el.width();//首先获取svg的宽度
            const viewBoxWidth = width;
            const viewBoxHeight = height;
            this.viewBox = []
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
        /**
         * 隐藏所有的关系
         */
        hideAllLinked: function () {
            $(this.options.edgesContainer).hide()
        },
        /**
         * 显示所有的关系
         */
        showAllLinked: function () {
            $(this.options.edgesContainer).show();
        },
        /**
         * 隐藏所有的阶段
         */
        hideAllSteps: function () {
            $(this.options.stepsContainer).hide();
        },
        /**
         * 显示所有的阶段
         */
        showAllSteps: function () {
            $(this.options.stepsContainer).show();
        },
        /**
         * 显示所有阶段的APP
         */
        showAllNodesApp: function () {
            this.workNodeMap.forEach(function (workNode) {
                workNode.showAppName();
            })
        },
        /**
         * 隐藏所有阶段的APP
         */
        hideAllNodesApp: function () {
            this.workNodeMap.forEach(function (workNode) {
                workNode.showNodeName();
            })
        },
        /**
         * 添加阶段
         */
        addStep: function (step) {
            //触发事件
            this.trigger('before.add-step', step)
            //执行函数
            this.options.stepMap.forEach((steppp,stepIndex)=>{
                if(steppp.pos>=step.pos&&steppp.stepIndex!=step.stepIndex){
                    steppp.pos++;
                }
            })
            this.options.stepMap.set(step.stepIndex,step);
            //触发事件
            this.trigger('after.add-step', step);
            this.refreshView();
        },

        /**
         * 删除阶段
         * @param stepIndex 待删除的阶段索引
         */
        removeStep: function (stepIndex) {
            let containNode = false;
            this.options.nodeMap.forEach((node,nodeIndex)=>{
                containNode=containNode||node.stepIndex==stepIndex;
            })

            if (containNode) {
                //如果当前阶段存在节点,需要删除当前所有的
                alert("请删除当前所有的节点在执行操作");
                return;
            }
            let step = this.options.stepMap.get(stepIndex);
            //触发事件
            this.trigger('before.remove-step', step)
            console.log("执行删除阶段函数")
            //执行函数
            this.options.stepMap.delete(stepIndex)
            //完成删除
            //触发事件
            this.trigger('after.remove-step', step)
            //重新绘制
            this.refreshView();
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
            $(".work.node").removeAttr("active")
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
            this.trigger('before.add-node', node);
            //添加当前节点
            this.options.nodeMap.set(node.nodeIndex, node);
            //触发事件
            this.trigger('after.add-node', node);
            this.refreshView();
        },
        /**
         * 删除节点
         * @param nodeIndex
         */
        removeNode: function (node) {
            if (node.lockState) {
                alert("当前节点处于锁定阶段，无法删除");
                return false;
            }
            if (node.isFinished) {
                alert("当前为已完成阶段，无法删除");
                return false;
            }
            if (node.nextNodeIndexList!=null && node.nextNodeIndexList.length > 0) {
                alert("当前节点包含后序节点，无法删除")
                return false;
            }
            this.trigger('before.remove-node', node);
            this.options.nodeMap.delete(node.nodeIndex);
            this.trigger('after.remove-node', node);
            this.refreshView();
        },
        /**
         * 查看节点
         * @param nodeIndex 节点索引
         */
        viewNode: function (node) {
            this.trigger('before.view-node', node);
            $(".work.node").removeAttr("active")
            $(this.workNodeMap.get(node.nodeIndex).nodeGID).attr({
                "active": "true"
            });
            this.trigger('after.view-node', node);
        },
        /**
         * 更新节点所属的阶段索引
         * @param oldStepIndex 旧的阶段索引
         * @param newStepIndex 新的阶段索引
         */
        updateStepPos(step, newStepPos) {
            if(newStepPos<0||newStepPos>=this.stepStartMap.size){
                alert("无法移动")
                return;
            }
            this.trigger('before.update-step-pos', step,newStepPos);
            const d = step.pos-newStepPos;
            this.options.stepMap.forEach((step,stepIndex)=>{
                if(step.pos==newStepPos){
                    step.pos+=d;
                }
            })
            step.pos=newStepPos;
            this.trigger('after.update-step-pos', step,newStepPos);
            this.refreshView();
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
    var WorkStep = function (xy1, xy2, step, stage, stepBarHeight) {
        this.xy1 = xy1; //起始坐标
        this.xy2 = xy2; //结束坐标
        this.step = step; //
        this.barHeight = stepBarHeight;
        this.stage = stage; //主舞台
        this.newX = 0;
        this.newY = 0;
        this.initStep();
    }
    WorkStep.prototype = {
        initStep: function () {
            //ID前缀
            const stepIndexPre = "#step-" + this.step.stepIndex;
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
            })[0].textContent = this.step.name;

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
        bindListener: function () {
            $(this.toolbar).on("click", {workStep: this}, function (event) {
                $(event.data.workStep).trigger("onclick");
            })
        },
    }
    var CUR_STEP_NODE;
    var CUR_NODE;
    var WorkNode = function (node, xy, width, height, container) {
        this.node = node;
        this.x = xy.x; //x坐标
        this.y = xy.y; //y坐标
        this.id = node.nodeIndex;
        this.width = width;//宽
        this.height = height;//长
        // this.r = stage.options.nodeR; //宽
        this.nextWorkNodeMap = new Map(); //下一个节点
        this.preWorkNodeMap = new Map(); //上一个节点
        this.container = container;
        this.outPathMap = new Map();//向外发散的边
        this.menu = {
            r: this.r * 2.5,
            menuArr: [{
                name: "查看",
                icon: '#icon-view',
                func: function (workFlow, workNode) {
                    workFlow.viewNode(workNode.node);
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
                func: function (workFlow, workNode) {

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
        this.timeoutNameSet = new Set();

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
            const $nodeG = $(svg('g', $(this.container), this.nodeGID, {
                class: 'work node ' + (this.node.isFinished ? 'finished ' : "unFinished") + (this.node.lockState ? "locked" : ""),
            }));
            const $rect = $(svg('rect', $nodeG, undefined, {
                rx: 10,
                ry: 10,
                width: this.width,
                height: this.height,
            }))
            const textAttr = {
                'text-anchor': "middle",
                'font-size': (this.height / 30).toFixed(2) + "em"
            }
            const name = svg('text', $nodeG, undefined, {
                class: 'work node name',
            });
            name.textContent = this.node.name;
            $(name).attr(textAttr);
            const app = svg('text', $nodeG, undefined, {
                class: 'work node appName',
            });
            app.textContent = this.node.appName;
            $(app).attr(textAttr).hide();//隐藏app
        },
        matrix: function (matrix) {
            $(this.nodeGID).attr({
                transform: "matrix(" + matrix.join(",") + ")"
            });
        },
        translate: function (x, y) {
            this.transform = "translate(" + x + "," + y + ")";
            $(this.nodeGID).attr({
                transform: this.transform
            });
            this.newX = this.x + x;
            this.newY = this.y + y;
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
        showAppName: function () {
            $(this.nodeGID).find(".name").hide();//隐藏app
            $(this.nodeGID).find(".appName").show();//隐藏app
        },
        showNodeName: function () {
            $(this.nodeGID).find(".appName").hide();//隐藏app
            $(this.nodeGID).find(".name").show();//隐藏app
        },
        /**
         * 设置节点处于按压状态的函数
         */
        appendPressState: function () {
            $(this.nodeGID).attr("pressed", true);
            $(this.container).append( $(this.nodeGID));
        },
        removePressState:function(){
            $(this.nodeGID).attr("pressed", false);
        },
        /**
         * 绑定相关事件
         */
        bindListener: function () {
            let workNode = this;
            let isDown = false;
            let isMove = false;
            $(this.nodeGID).on({
                "mousedown": function () {
                    //设置按下时间为2秒
                    isDown =true;
                    nodeMove = setTimeout(function () {
                        workNode.appendPressState();
                        isMove=true;
                        $(workNode).trigger("mousedown");
                    }, 1000);
                },
                "mouseup": function () {
                    if(isDown||isMove){
                        clearTimeout(nodeMove);
                        workNode.removePressState();
                    }
                    if(isMove){
                        $(workNode).trigger("mouseup");
                    }else if(isDown){
                        $(workNode).trigger("onclick");
                    }
                    isDown=false;
                    isMove=false;
                },
                mouseleave:function () {
                    if(isDown&&!isMove){
                        clearTimeout(nodeMove);
                        workNode.removePressState();
                    }

                }
            });
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
    var WorkPath = function (workNodeI, workNodeJ, containerID) {
        this.workNodeI = workNodeI;
        this.workNodeJ = workNodeJ;
        this.nodeI = this.workNodeI.node.nodeIndex;
        this.nodeJ = this.workNodeJ.node.nodeIndex;
        this.midPos = {};
        this.$container = $(containerID)
        this.initDom();
    }
    WorkPath.prototype = {
        initDom: function () {
            const path = svg("path", this.$container, undefined, {
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
        bindListener: function () {
            $('[nodei="' + this.nodeI + '"][nodej="' + this.nodeJ + '"]').on("click", {workPath: this}, function (event) {
                console.log("点击了边")
                $(event.data.workPath).trigger("onclick");
            })
        },
        getPathClass: function () {
            return pathClass = "work path " + (this.workNodeJ.node.isFinished ? "finished" : "unFinished");
        },
        getPathD: function () {
            let x1 = this.workNodeI.x + this.workNodeI.width / 2,
                y1 = this.workNodeI.y + this.workNodeI.height / 2,
                x2 = this.workNodeJ.x + this.workNodeJ.width / 2,
                y2 = this.workNodeJ.y + this.workNodeI.height / 2;
            this.midPos = {
                x: (x2 + x1) / 2,
                y: (y2 + y1) / 2,
            }
            return [this.getSVGPath("M", x = x1, y = y1),
                this.getSVGPath("L", x = x2, y = y2)];//定义路径数组;
        },
        getSVGPath: function (op, x1, y1, x2, y2, x, y) {
            return "" + op + (x1 ? x1 : "") + " " + (y1 ? y1 : "") + " " + (x2 ? x2 : "") + " " + (y2 ? y2 : "") + " " + (x ? x : "") + " " + (y ? y : "");
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
            switch (data.code) {
                case 1:
                    data=data.data;
                    workOptions['nodeMap'] = new Map();
                    workOptions['stepMap'] = new Map();
                    for (let nodeIndex in data.nodeMap) {
                        workOptions['nodeMap'].set(nodeIndex, data.nodeMap[nodeIndex]);
                    }
                    for (let stepIndex in data.stepMap) {
                        workOptions['stepMap'].set(stepIndex, data.stepMap[stepIndex]);
                    }
                    $(SVG_DOM).workFlow(workOptions);
                    break;
            }

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