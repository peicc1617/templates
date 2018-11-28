// ctx.fillStyle = "#FF0000";
// ctx.fillRect(0, 0, 10, 10);

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
NODE_ARRAY = [

];

//网格
GRID = [];
//当前选中的节点
CUR_NODE = {

};

var stage;
const SVG_JSON = {
    symbolsContainer: "#symbolsContainer", //图标容器
    stepsContainer: "#stepsContainer", //阶段容器
    edgesContainer: '#edgesContainer', //边容器
    nodesContainer: '#nodesContainer', //节点容器
    tipsContainer: '#tipsContainer', //提示容器
    width: undefined,
    height: undefined,
    nodeR: 25,
    nodes: [{
        "appName": "鱼骨图",
        "appPath": "/fishbone",
        "description": "主要方法有：胜任力模型、行为事件访谈（BEIs）、专家小组法、问卷调查法、全方位评价法、专家系统数据库和观察法等。",
        "goal": "确定员工的知识、技能和素质等方面的关键需求，并识别需求改进的培训项目或培训管理流程，并将改进的内容界定在合理的范围内。",
        "lockState": false,
        "name": "sssssssss",
        "nodeIndex": 2,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "state": 1,
        "templateProjectID": 0,
        "step": 0,
    }, {
        "description": "主要方法有：AFP法、模糊综合评判法、直方图、矩阵数据分析图等。",
        "goal": "通过对现有培训流程的测量，辨别核心流程和辅助流程；识别影响培训流程输出的输入要素，并对测量系统的有效性作出评价。",
        "lockState": false,
        "name": "节点1",
        "nodeIndex": 3,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "state": 1,
        "templateProjectID": 0,
        "step": 0,
    }, {
        "description": "主要方法有：鱼骨图、柏拉图、回归分析、因子分析等。",
        "goal": "通过数据分析，确定影响培训流程输出的关键因素，即确定培训过程的关键影响因素。",
        "lockState": false,
        "name": "节点2",
        "nodeIndex": 4,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "state": 1,
        "templateProjectID": 0,
        "step": 1,
    }, {
        "description": "主要方法有：流程再造等。",
        "goal": "寻找优化培训流程并消除或减少关键输入因素影响的方案，使流程的缺陷或变异降低到最小程度。",
        "lockState": false,
        "name": "节点3",
        "nodeIndex": 5,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "state": 1,
        "templateProjectID": 0,
        "step": 2,
    }, {
        "description": "主要方法有：标准化、程序化、制度化等。",
        "goal": "使改进后的流程程序化，并通过有效的监测手段，确保流程改进的成果。",
        "lockState": false,
        "name": "节点4",
        "nodeIndex": 6,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "state": 1,
        "templateProjectID": 0,
        "step": 3,
    }, {
        "createTime": 1527644408000,
        "editTime": 1527644417000,
        "goal": "",
        "lockState": false,
        "name": "节点5",
        "nodeIndex": 19,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "review": "",
        "state": 1,
        "templateProjectID": 0,
        "step": 0,
    }, {
        "createTime": 1527645799000,
        "editTime": 1527645799000,
        "goal": "",
        "lockState": false,
        "name": "节点6",
        "nodeIndex": 20,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "review": "",
        "state": 0,
        "templateProjectID": 0,
        "step": 1,
    }, {
        "createTime": 1527645813000,
        "editTime": 1527645813000,
        "goal": "",
        "lockState": false,
        "name": "节点7",
        "nodeIndex": 21,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "review": "",
        "state": 0,
        "templateProjectID": 0,
        "step": 2,
    }, {
        "createTime": 1527647775000,
        "editTime": 1527647775000,
        "goal": "",
        "lockState": false,
        "name": "节点8",
        "nodeIndex": 27,
        "personArrayList": [],
        "projectID": 2,
        "result": [],
        "review": "",
        "state": 0,
        "templateProjectID": 0,
        "step": 3,
    }],
    edges: [{
        "nodeI": 2,
        "nodeJ": 3,
    }, {
        "nodeI": 3,
        "nodeJ": 4,
    }, {
        "nodeI": 4,
        "nodeJ": 5,
    }, {
        "nodeI": 5,
        "nodeJ": 6,
    }, {
        "nodeI": 19,
        "nodeJ": 3,
    }, {
        "nodeI": 19,
        "nodeJ": 5,
    },
        {
            "nodeI": 19,
            "nodeJ": 20,
        },
        {
            "nodeI": 19,
            "nodeJ": 21,
        }, {
            "nodeI": 20,
            "nodeJ": 21,
        }, {
            "nodeI": 20,
            "nodeJ": 6,
        }
    ],
    steps: [{
        "id": 0,
        "name": "定义",
        "decription": "描述",
    }, {
        "id": 1,
        "name": "测量",
        "decription": "描述",
    }, {
        "id": 2,
        "name": "分析",
        "decription": "描述",
    }, {
        "id": 3,
        "name": "改进",
        "decription": "描述",
    }, {
        "id": 4,
        "name": "控制",
        "decription": "描述",
    }]
}
$(function () {
    SVG_DOM.setAttribute('width', '100%');
    // SVG_DOM.setAttribute('height', '1000');
    // resetGRID();
    $.ajax({
        url:"/templates/api/project",
        data:{
            projectID:PROJECT_ID,
        },
        success:function (data) {
            SVG_JSON.nodes = data.nodes;
            SVG_JSON.steps = data.steps;
            SVG_JSON.edges = data.edges;
            stage = new Stage(SVG_DOM,SVG_JSON);
        }
    })
})


/**
 * 图的基本结构
 */
GRAPH = {
    num: 0,
    nodes: [],
    edges: []

};

var SVG_DOM = document.getElementsByTagNameNS('http://www.w3.org/2000/svg', 'svg')[1];


var Stage = function (el, option) {
    this.option = option;
    this.el = el;
    this.$el = $(el);
    this.scaleArr = [0.25, 0.33, 0.5, 0.67, 0.75, 0.8, 0.9, 1];
    this.scale = 7;
    this.init();
}

Stage.DEFAULT = {
    "name": "new stage",
    "info": "info about this stage",
}
Stage.prototype = {
    constructor: Stage,
    init: function () {
        this.resetView();
        this.bindMouseWheel();
        this.toolbarBind();
    },
    /**
     * 重设视图
     */
    resetView: function () {
        this.refreshData();
        this.refreshGrid();
    },
    /**
     * 重新绘制工具栏
     */
    resetToolBar: function () {

    },
    resetCSS: function () {

    },
    /**
     * 重新计算视图
     */
    refreshGrid: function () {
        //更新路径和网格
        const nodeMap = new Map(); //节点Map
        const freeNodeSet = new Set(); //自由节点
        this.option.nodes.forEach(node => {
            nodeMap.set(node.nodeIndex, node);
            freeNodeSet.add(node.nodeIndex);
        });
        //关联矩阵
        //初始化关联矩阵
        const dj = new Map();
        nodeMap.forEach(function (value, nodeIndex, map) {
            dj.set(nodeIndex, []);
        });
        //得到所有的起始节点
        this.option.edges.forEach(edge => {
            dj.get(edge.nodeI).push(edge.nodeJ);
            freeNodeSet.delete(edge.nodeJ);
        });
        // console.log("打印所有的起始节点", freeNodeSet);
        const stack = [];
        freeNodeSet.forEach(function (nodeIndex) {
            stack.push([nodeMap.get(parseInt(nodeIndex))]);
        });
        //生成路径
        const paths = [];
        while (stack.length > 0) {
            let cur = stack.pop();
            //获取节点
            let curNodeIndex = cur[cur.length - 1].nodeIndex;
            if (dj.get(curNodeIndex).length === 0) {
                //如果没有后继节点，说明当前路径结束
                paths.push(cur);
            } else {
                dj.get(curNodeIndex).forEach(nextNodeIndex => {
                    stack.push(cur.concat([nodeMap.get(nextNodeIndex)]));
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
                    if (arr1[index].step < arr2[index].step) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
                return 0;
            }
        });
        // console.log("打印排序后的路径", paths);
        //分别计算每个阶段的最长路径
        //初始化
        const stepNum = this.option.steps.length;
        const stepArr = Array.apply(null, Array(stepNum)).map(() => 1)
        //初始化数据
        this.option.nodes.forEach(node => node.col = 0);
        paths.forEach(path => {
            const stepArrTemp = Array.apply(null, Array(stepNum)).map(() => 0);
            path.forEach(node => {
                //初始化节点在阶段内所处的层。
                //获取节点在阶段内所处的最大层
                node.col = Math.max(node.col, stepArrTemp[node.stepIndex]);
                //计算每个阶段的层数
                stepArrTemp[node.step] += 1;
            })
            for (let i = 0; i < stepNum; i++) {
                //获取每个阶段的最大层数
                stepArr[i] = Math.max(stepArrTemp[i], stepArr[i]);
            }
        })
        // 计算列数目
        for (let i = 1; i < stepArr.length; i++) {
            stepArr[i] += stepArr[i - 1];
        }
        console.log("stepArr", stepArr);
        //计算每个阶段所处的层
        let pathMaxLen = 0;
        this.option.nodes.forEach(node => {
            if (node.step !== 0) {
                node.col += stepArr[node.stepIndex]-1;
                pathMaxLen = Math.max(pathMaxLen, node.col + 1);
            }
        });
        // console.log("输出节点nodes", this.option.nodes);
        const columnN = stepArr[stepArr.length - 1];
        // console.log("输出列数 columnN", columnN);
        // console.log("输出路径最大长度 pathMaxLen", pathMaxLen);
        //生成带有虚拟节点的虚拟路径
        const pathsTemp = [];
        paths.forEach(path => {
            const pathTemp = Array.apply(null, Array(pathMaxLen)).map(() => undefined);
            path.forEach(node => {
                pathTemp[node.col] = node.nodeIndex;
            });
            //去除后续的虚拟节点
            let i = pathMaxLen - 1;
            for (; i >= 0; i--) {
                if (pathTemp[i] !== undefined) {
                    break;
                }
            }
            pathsTemp.push(pathTemp.splice(0, i + 1));
        });
        const nodeMapTemp = new Map();
        const colArrTemp = Array.apply(null, Array(pathMaxLen)).map(() => new Set());
        //虚拟节点的数目应该是至多每一层包含一个虚拟节点，所以虚拟节点的nodeIndex可以定义为(-(层数+1));
        pathsTemp.forEach(path => {
            var preNode = undefined;
            var isStart = false;
            for (let i = 0; i < path.length; i++) {
                var nodeIndex = path[i];
                if (nodeIndex) {
                    //如果出现实体节点，那么开始
                    isStart = true;
                }
                //如果还没有出现实体节点，继续
                if (!isStart) continue;
                if (!nodeIndex) {
                    nodeIndex = -(i + 1);
                    //如果当前节点是虚拟节点，那么首先判断该层有没有虚拟节点
                }
                if (!nodeMapTemp.has(nodeIndex)) {
                    //如果当前节点没有创建，那么首先建立节点
                    node = {
                        nodeIndex: nodeIndex,
                        nextNodeArr: [],
                        preNodeArr: []
                    }
                    nodeMapTemp.set(nodeIndex, node);
                }
                node = nodeMapTemp.get(nodeIndex);
                //如果当前节点已经创建，那么直接push
                if (preNode) {
                    //如果有前序节点，那么生成前后关系
                    if (preNode.nextNodeArr.indexOf(node) === -1) {
                        preNode.nextNodeArr.push(node);
                    }
                    if (node.preNodeArr.indexOf(preNode) === -1) {
                        node.preNodeArr.push(preNode);
                    }
                }
                colArrTemp[i].add(node);
                path[i] = node.nodeIndex;
                preNode = node;
            }
        });
        console.log("打印带有虚拟节点的路径", pathsTemp);

        //对每一层进行处理，将Set转化为数组
        let rowN = 0;
        colArrTemp.forEach(function (e, i, a) {
            const arr = [];
            e.forEach(function (e2) {
                e2.index = arr.length;
                arr.push(e2);
            })
            a[i] = arr;
            rowN = Math.max(rowN, a[i].length);
        });

        //使用重心启发算法逐层清理
        var preX = 0;
        var x = 10; //x代表线的交叉数，目标是将x优化到最小，终止条件是两次迭代之间x不发生变化
        var sortFunction = function (col, attr) {
            col.forEach(node => {
                node.index = eval(node[attr].map(n => n.index).join("+")) / node[attr].length;
                if (node[attr].length === 0) node.index = col.length;
            })
            col.sort(function (node1, node2) {
                // if(node1.nodeIndex<0) return 1;
                // if(node2.nodeIndex<0) return -1;
                // if(node1.index==node2.index){
                //     return  Math.random() -0.5;
                // }
                return node1.index - node2.index;
            });
            col.forEach(function (e, i, arr) {
                e.index = i;
            })
        };
        while ((x--) > 0) {
            //首先从前向后开始遍历,不变动第一层
            for (let i = 1; i < colArrTemp.length; i++) {
                sortFunction(colArrTemp[i], 'preNodeArr');
            }
            //然后从后向前遍历
            for (let i = colArrTemp.length - 2; i >= 0; i--) {
                sortFunction(colArrTemp[i], 'nextNodeArr');
            }
        }

        //使用优先顺序法指定具体坐标
        //首先初始化坐标
        colArrTemp.forEach(function (e, i, a) {
            e.forEach(function (e2, i2, a2) {
                e2.x = i;
                e2.y = i2;
            })
        })
        //然后从前向后调整
        var moveFunction = function (i, colArrTemp) {
            var toMoveArr = colArrTemp[i - 1]; //待移动的列 
            var noMoveArr = colArrTemp[i]; //无需移动的列
            var attr = "nextNodeArr";
            if (colArrTemp[i].length <= colArrTemp[i - 1].length) {
                toMoveArr = colArrTemp[i];
                noMoveArr = colArrTemp[i - 1];
                attr = "preNodeArr";
            }
            //根据优先值排序
            toMoveArr.sort(function (node1, node2) {
                //虚拟节点的优先值永远是最大的
                if (node1.nodeIndex < 0)
                    return -1;
                if (node2.nodeIndex < 0)
                    return 1;
                //两个实体节点的优先值取决于所连接的节点数目
                return node1[attr].length - node2[attr].length;
            });
            //定义y轴
            const y = new Array(noMoveArr.length);
            toMoveArr.forEach(node => {
                let temp = parseInt(eval(node[attr].map(n => n.y).join("+")) / node[attr].length);
                if (node[attr].length === 0) temp = toMoveArr.length - 1;
                let j = -2;
                let v = y[temp];
                //如果重心已经被占用了，那么按照移动量最小的原则进行调整
                while (v !== undefined) {
                    j = -(j + 1);
                    temp += j;
                    v = y[temp];
                    if (j < 0) v = true;
                    if (j >= y.length) v = true;
                }
                y[temp] = true;
                node.y = temp;
            })
        };
        //从前向后调整
        for (let i = 1; i < colArrTemp.length; i++) {
            // moveFunction(i,colArrTemp);
        }
        console.log("每一层对应的节点，包括虚拟节点 colArrTemp", colArrTemp);
        //计算行数目
        console.log("行数,列数", rowN, columnN);
        this.rowN = rowN;
        this.columnN = columnN;
        this.colArrTemp = colArrTemp;
        this.stepArr = stepArr;
        this.nodeMap = nodeMap;
        this.refreshView();

    },
    /**
     * 更新视图
     */
    refreshView: function () {
        $(".svg-container").empty();
        //从后向前调整
        // for(let i= colArrTemp.length-1;i>0;i--){
        //     moveFunction(i,colArrTemp);
        // }
        //
        this.nodeHorDis = this.option.nodeR * 5; //节点间距
        this.nodeVerDis = this.option.nodeR * 5 //节点垂直间距
        const stepBarHeight = this.option.nodeR; //阶段标题高度
        let width2 = this.columnN * this.nodeHorDis; //计算实际宽度
        let height2 = (this.rowN + 1) * this.nodeVerDis; //计算实际高度
        this.viewbox = [0, 0, width2, height2];
        this.viewBox = this.viewbox.concat();

        // if(this.viewBox==undefined){
        //     this.viewBox = this.viewbox.concat();
        // }
        this.resetViewBox();
        this.$el.height(height2);
        console.log("实际宽度", width2, "实际高度", height2)

        this.svgxc = this.$el.offset().left + this.$el.width() / 2;
        this.svgyc = this.$el.offset().top + this.$el.height() / 2;
        console.log('svg的中心屏幕坐标', this.svgxc, this.svgyc)

        // // 首先判断图形是否需要缩放
        // const widthTemp = 4 * columnN * this.option.nodeR;
        // this.option.width = this.$el.width(); //设置高度

        // if (widthTemp <= this.option.width) {
        //     //如果当前节点大小和间距满足布局，不用考虑缩放
        //     //在不缩放的情况下，节点的大小是一定的，根据节点的大小和宽度计算节点间距
        //     this.option.nodeHorDis = (this.option.width / columnN) + 1; //得到节点间的水平间距
        // } else {
        //     //需要考虑缩放
        //     // 在考虑缩放的情况下需要需要计算缩放的比例
        // }
        // this.nodeVerDis = this.option.nodeR * 4;
        // console.log("节点之间的距离:", this.option.nodeHorDis, this.option.nodeVerDis)
        // // view部分的高度
        // const viewHeight = this.option.nodeVerDis * (rowN + 1);
        // //得到主舞台的高度
        // this.option.height = toolbarHeight + viewHeight;
        // this.$el.height(this.option.height);
        //阶段标题高度
        //计算第一个网格的起始的位置
        const start = {
            x: this.nodeHorDis / 2,
            y: this.nodeVerDis,
            step: undefined,
        };
        //初始化阶段的坐标
        const stepXYs = Array.apply(null, Array(this.option.steps.length)).map(() => o = {
            "xy1": {
                x: Number.MAX_SAFE_INTEGER,
                y: Number.MAX_SAFE_INTEGER
            },
            "xy2": {
                x: 0,
                y: 0
            }
        });
        //生成网格
        this.grid = new Array(this.rowN);
        for (let i = 0; i < this.rowN; i++) {
            this.grid[i] = new Array(this.columnN);
            for (let j = 0, step = 0; j < this.columnN; j++) {
                if (j >= this.stepArr[step]) {
                    step++;
                }
                this.grid[i][j] = {
                    x: start.x + j * this.nodeHorDis,
                    y: start.y + i * this.nodeVerDis,
                    step: step,
                }
            }
        }
        console.log("网格", this.grid);
        //根据坐标绘制节点
        this.stepNodeMap = new Map();
        this.colArrTemp.forEach(col => {
            col.forEach(node => {
                if (node.nodeIndex > 0) {
                    const stepNode = new StepNode(this.nodeMap.get(node.nodeIndex), this.grid[node.y][node.x], this);
                    this.stepNodeMap.set(node.nodeIndex, stepNode);
                    stepNode.initNode();
                }
            })
        });

        for (let i = 0; i < this.rowN; i++) {
            for (let j = 0; j < this.columnN; j++) {
                let step = this.grid[i][j].step;
                if (step !== undefined) {
                    stepXYs[step].xy1.x = Math.min(stepXYs[step].xy1.x, this.grid[i][j].x);
                    stepXYs[step].xy1.y = Math.min(stepXYs[step].xy1.y, this.grid[i][j].y);
                    //右边界坐标取x,y的
                    stepXYs[step].xy2.x = Math.max(stepXYs[step].xy2.x, this.grid[i][j].x);
                    stepXYs[step].xy2.y = Math.max(stepXYs[step].xy2.y, this.grid[i][j].y);
                }
            }
        }
        console.log("stepXYs", stepXYs);
        this.steps = new Map();
        for (let index = 0; index < stepXYs.length; index++) {
            let stepXY = stepXYs[index];
            stepXY.xy1.x -= this.nodeHorDis / 3;
            stepXY.xy1.y -= this.nodeVerDis / 3 + stepBarHeight;
            stepXY.xy2.x += this.nodeHorDis / 3;
            stepXY.xy2.y += this.nodeVerDis / 3;
            const step = new Step(stepXY.xy1, stepXY.xy2, this.option.steps[index], this, stepBarHeight);
            step.initStep();
            this.steps.set(this.option.steps[index].stepIndex, step);
        }

        this.option.edges.forEach(edge => {
            this.stepNodeMap.get(edge.nodeI).nextStepNodeArray.push(this.stepNodeMap.get(edge.nodeJ));
            this.stepNodeMap.get(edge.nodeJ).preStepNodeArray.push(this.stepNodeMap.get(edge.nodeI));
        })
        this.stepNodeMap.forEach(function (stepNode) {
            stepNode.drawPath();

        })
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
    createNS(tag, e) {
        const child = document.createAttributeNS("http://www.w3.org/2000/svg", tag);
        if (typeof e === "object") {
            e.append(child);
        }
        return child;
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
        this.$el.bind("mousedown", startViewBoxMove = function (e) {
            $(this).bind("mouseup", cancelMoveViewBox = function (e) {
                //取消绑定的所有函数
                // $(this).unbind("mousedown", moveViewBox);
                $(this).unbind("mouseup", cancelMoveViewBox);
                $(this).unbind("mousemove", moveViewBox);
            })
            var pos = {
                x: stage.mousePos.x,
                y: stage.mousePos.y
            }
            $(this).bind('mousemove', {
                pos: pos
            }, moveViewBox = function (e2) {
                const dx = (stage.mousePos.x - e2.data.pos.x);
                const dy = (stage.mousePos.y - e2.data.pos.y);
                // const dy = 0;
                console.log('x变动', dx, 'y变动', dy)
                pos.x = stage.mousePos.x;
                pos.y = stage.mousePos.y;
                stage.viewBox = [stage.viewBox[0] - dx, stage.viewBox[1] - dy, stage.viewBox[2], stage.viewBox[3]];
                stage.viewbox = stage.viewBox.concat();
                stage.resetViewBox();
            });
            // console.log(this, e)
        }).bind("mousemove", {
            stage: this
        }, mouseMove = function (e) {
            const stage = e.data.stage;
            //绑定鼠标移动事件
            stage.mousePos = {
                x: (e.clientX - stage.svgxc) * (1 / stage.scaleArr[stage.scale]) + stage.viewboxxc,
                y: (e.clientY - stage.svgyc) * (1 / stage.scaleArr[stage.scale]) + stage.viewboxyc
            };
            // console.log(stage.mousePos)
        })
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
        this.stepNodeMap.forEach(function(stepNode){
            stepNode.showAppText();
        })
    },
    /**
     * 隐藏所有阶段的APP
     */
    hideAllNodesApp: function () {
        this.stepNodeMap.forEach(function(stepNode){
            stepNode.hideAppText();
        })
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

function drawTips() {
    $("#tipsContainer").html("");
    TIPS_TABLE.forEach(function (e, index, array) {
        var g = document.createElementNS('http://www.w3.org/2000/svg', 'g');
        var circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        g.appendChild(circle);

        var text = document.createElementNS('http://www.w3.org/2000/svg', 'text');
        g.appendChild(text);
        document.getElementById("tipsContainer").appendChild(g);
        $(circle).attr({
            'cx': e.cx,
            'r': e.r,
            'cy': e.cy,
            'class': e.class
        });
        $(text).attr('text-anchor', "right")
            .attr('x', e.cx + 2 * e.r)
            .attr("y", e.cy + e.r / 2)
            .attr("class", "node-text")
            .attr("font-size", (e.r / 10).toFixed(2) + "em")
            .html(e.text);
    })

}
/**
 * 
 * @param {*} xy1 
 * @param {*} xy2 
 * @param {*} id 
 * @param {*} stageDom 
 */
var Step = function (xy1, xy2, option, stage, stepBarHeight) {
    this.xy1 = xy1; //起始坐标
    this.xy2 = xy2; //结束坐标
    this.option = option; //
    this.barHeight = stepBarHeight;
    this.r = 10; //矩形的圆角
    this.stage = stage; //主舞台
    this.drawerHeight = 50;
}
Step.prototype = {
    initStep: function () {
        //ID前缀
        const stepIDPre = "#step-" + this.option.stepIndex;
        //颜色
        this.color = "#ffffff";
        //边框色
        this.stokeColor = "#00868B";

        //新建阶段边框
        this.border = stepIDPre + "-border";
        svg("rect", $(this.stage.option.stepsContainer), this.border, undefined);
        //新建阶段工具栏
        this.toolbar = stepIDPre + "-toolbar";
        svg("g", $(this.stage.option.stepsContainer), this.toolbar, {
            "class": "stage-step-title"
        });
        //新建工具栏填充
        this.toolbarRect = stepIDPre + "-toolbarRect";
        svg("rect", $(this.toolbar), this.toolbarRect, undefined);
        //新建设置按钮
        // this.settingBtn = stepIDPre + "-setting-btn";
        // svg("rect", $(this.toolbar), this.settingBtn, undefined);
        //新建添加按钮
        this.addBtn = stepIDPre + "-add-btn";
        svg("rect", $(this.toolbar), this.addBtn, undefined);
        this.iconG = stepIDPre + "-iconG";
        svg("g", $(this.toolbar), this.iconG, undefined);
        //新建设置图标
        // this.settingIcon = stepIDPre + "-setting-icon";
        // svg("use", $(this.iconG), this.settingIcon, {
        //     "xlink:href": "#icon-setting",
        // });
        //新建添加图标
        this.addIcon = stepIDPre + "-add-icon";
        svg("use", $(this.iconG), this.addIcon, {
            "xlink:href": "#icon-add"
        });
        //新建文字
        this.text = stepIDPre + "-title";
        svg("text", $(this.toolbar), this.text, {
            'text-anchor': "middle",
            "dominant-baseline": "middle",
        });
        this.drawStep();
    },
    drawStep: function () {
        $(this.border).attr({
            x: this.xy1.x,
            y: this.xy1.y,
            width: this.xy2.x - this.xy1.x,
            height: this.xy2.y - this.xy1.y,
            fill: this.color,
            ry: this.r,
            rx: this.r,
            stroke: this.stokeColor,
            class: 'drawer'
        });
        this.drawText();
        this.drawToolBar();
        this.bindClickListener();
    },
    drawText: function () {
        $(this.text).attr({
            x: (this.xy1.x + this.xy2.x) / 2,
            y: this.xy1.y + this.barHeight / 2,
            "font-size": (this.barHeight / 2).toFixed(2) + "px",
        })[0].textContent = this.option.name;

    },
    drawToolBar: function () {
        const x = this.xy2.x - this.r / 2;
        const y = this.xy1.y + this.barHeight / 4;
        $(this.addIcon).attr({
            x: x - this.barHeight / 2,
            y: y,
            width: this.barHeight / 2,
            height: this.barHeight / 2,
        });
        $(this.addBtn).attr({
            x: x - this.barHeight / 2,
            y: y,
            width: this.barHeight / 2,
            height: this.barHeight / 2,
            opacity: 0.1,
        });
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
    bindClickListener: function () {
        //向当前阶段中添加节点
        $(this.addBtn).on('click', {
            stepIndex: this.option.stepIndex
        }, function (event) {
            let newNode = {
                goal: "",
                lockState: false,
                name: "节点" + stage.option.nodes[stage.option.nodes.length - 1].nodeIndex + 1,
                nodeIndex: stage.option.nodes[stage.option.nodes.length - 1].nodeIndex + 1,
                state: 0,
                stepIndex: event.data.stepIndex,
                projectID:PROJECT_ID
            }
            addNode(newNode);
        });
        $(this.text).on('click', {
            stepIndex: this.option.stepIndex
        }, function (event) {
            CUR_STEP = stage.option.steps[event.data.stepIndex]
            viewStep(event.data.stepIndex)
            // const step = stage.steps.get(event.data.stepIndex);
            // newname = prompt("请输入新的名称", step.option.name);
            // if (newname) {
            //     //如果用户输入的符合要求
            //     step.option.name = newname;
            //     step.drawText();
            // }
        })
    }
}
var CUR_STEP_NODE;
var CUR_NODE;
var StepNode = function (node, xy, stage) {
    this.node = node;
    this.x = xy.x; //x坐标
    this.y = xy.y; //y坐标
    this.id = node.nodeIndex;
    this.r = stage.option.nodeR; //宽
    this.nextStepNodeArray = []; //下一个节点
    this.preStepNodeArray = []; //上一个节点
    this.stage = stage;
    this.outLines = [];
    this.menu = {
        r: this.r * 2.5,
        menuArr: [{
            name: "查看",
            icon: '#icon-view',
            func: function (stepNode) {
                CUR_STEP_NODE = stepNode;
                CUR_NODE = stepNode.node;
                viewNode();
            }
        }, {
            name: "移动",
            icon: '#icon-move',
            func: function (stepNode) {
                const x = stepNode.x;
                const y = stepNode.y;
                const stage = stepNode.stage;
                $(stage.option.stepsContainer).bind('mousemove', moveNode = function (e) {
                    stepNode.x = stepNode.stage.mousePos.x;
                    stepNode.y = stepNode.stage.mousePos.y;
                    stepNode.refreshNode();
                });
                $(stepNode.trigger).bind('contextmenu', cancelNoveNode = function (e) {
                    $(stage.option.stepsContainer).unbind('mousemove', moveNode);
                    $(stepNode.trigger).unbind('contextmenu', cancelNoveNode);
                    stepNode.x = x;
                    stepNode.y = y;
                    stepNode.refreshNode();
                }).bind('click', applyMove = function (e) {
                    $(stage.option.stepsContainer).unbind('mousemove', moveNode);
                    $(stepNode.trigger).unbind('contextmenu', cancelNoveNode);
                    const x = stepNode.stage.mousePos.x;
                    const y = stepNode.stage.mousePos.y;
                    stepNode.stage.steps.forEach(function (step, id) {
                        if (x > step.xy1.x && y > step.xy1.y && y < step.xy2.y && x < step.xy2.x) {
                            stepNode.node.stepIndex = id;
                            console.log(stepNode.node)
                        }
                    });
                    console.log("完成移动")
                    stepNode.stage.refreshGrid();
                })
            }
        }, {
            name: "删除",
            icon: '#icon-delete',
            func: function (stepNode) {
                $(stepNode).trigger('onDelete')
            }
        }, {
            name: "链接",
            icon: "#icon-link",
            func: function (stepNode) {
                const line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
                $(stepNode.stage.option.edgesContainer).append(line);
                $(line).attr({
                    x1: stepNode.x,
                    y1: stepNode.y,
                    x2: stepNode.x + 1,
                    y2: stepNode.y + 1,
                    class: "line-path",
                });
                stepNode.stage.$el.bind('mousemove', drawline = function (e) {
                    $(line).attr({
                        x2: stepNode.stage.mousePos.x,
                        y2: stepNode.stage.mousePos.y
                    });
                });
                $(".trigger").bind('click',{nodeIndex:stepNode.node.nodeIndex} ,addPreNode = function (e) {
                    $(this).trigger('appendPre',[e.data.nodeIndex]);
                });
                stepNode.stage.$el.bind('contextmenu', cancelDrawline = function (e) {
                    $(SVG_DOM).unbind('mousemove', drawline);
                    $(".trigger").unbind('click',addPreNode);
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

StepNode.prototype = {
    constructor: StepNode,
    initNode: function () {
        console.log("该节点对象的起始坐标(x,y)=(" + this.x + "," + this.y + ")");
        this.createDom();
        this.drawNode();
        this.bind();
    },
    createDom: function () {
        //新建Node组
        this.nodeG = '#node' + this.node.nodeIndex;
        const $nodeG = $(svg('g', $(this.stage.option.nodesContainer), this.nodeG));
        //新建菜单组，添加到node组内
        this.itemsG = '#node-items-' + this.node.nodeIndex;
        const $itemsG = svg('g', $nodeG, this.itemsG, {
            'class': 'closeMenu init'
        });
        this.itemsArr = Array.apply(null, Array(this.menu.menuArr.length)).map(() => new Object());
        for (let i = 0; i < this.menu.menuArr.length; i++) {
            //新建Item节点
            this.itemsArr[i].itemA = '#node-item-a' + this.node.nodeIndex + '-' + i;
            const $itemA = $(svg('a', $itemsG, this.itemsArr[i].itemA, {
                "class": "item",
                "role": "link",
                "tabindex": "0",
                "target": "_parent",
                "xlink:href": ""
            }));
            //新建扇形背景
            this.itemsArr[i].itemPath = '#node-item-path' + this.node.nodeIndex + '-' + i;
            svg('path', $itemA, this.itemsArr[i].itemPath, {
                'fill': "none",
                "stoke": "#111",
                "stroke-width": "1",
            });
            //新建扇形遮罩层
            //新建图标层
            this.itemsArr[i].itemIcon = '#node-item-icon' + this.node.nodeIndex + '-' + i;
            svg('use', $itemA, this.itemsArr[i].itemIcon, {
                "xlink:href": this.menu.menuArr[i].icon
            });
            //新建文字提示层
            this.itemsArr[i].itemTitle = '#node-item-title' + this.node.nodeIndex + '-' + i;
            svg('title', $itemA, this.itemsArr[i].itemTitle, undefined)
                .appendChild(document.createTextNode(this.menu.menuArr[i].name));
        }
        //新建按钮触发组
        this.trigger = '#node-trigger-' + this.node.nodeIndex;
        const $trigger = svg('g', $nodeG, this.trigger, {
            'class': 'trigger menu-trigger'
        })
        //新建按钮中心
        this.triggerCircle = '#node-trigger-circle' + this.node.nodeIndex;
        svg('circle', $trigger, this.triggerCircle, undefined);

        this.textG = '#node-trigger-textG' + this.node.nodeIndex;
        const $textG = $(svg('g', $trigger, this.textG, undefined));
        //文字节点，显示节点名称
        this.nameText = '#node-name-text' + this.node.nodeIndex;
        svg('text', $textG, this.nameText, {
            'text-anchor': "middle",
        })
        //新建节点文字
        this.appText = '#node-app-text' + this.node.nodeIndex;
        $(svg('text', $textG, this.appText, {
            'text-anchor': "middle",
        })).hide()
        //新建节点提示
        this.triggerTitle = '#node-trigger-title' + this.node.nodeIndex;
        svg('title', $trigger, this.triggerTitle, undefined).appendChild(document.createTextNode(""));
    },
    //绘制当前节点函数
    drawNode: function () {
        $(this.itemsG)[0].style.setProperty('--curNodex', this.x);
        $(this.itemsG)[0].style.setProperty('--curNodeY', this.y);
        for (let i = 0; i < this.menu.menuArr.length; i++) {
            $(this.itemsArr[i].itemA).attr({
                "transform": "rotate(" + (360 / this.menu.menuArr.length * i) + " " + this.x + " " + this.y + ")",
                "data-svg-origin": this.x + " " + this.y
            });
            $(this.itemsArr[i].itemPath).attr({
                "d": "M" + this.x + "," + this.y + " l" + this.menu.r + ",0 A" + this.menu.r + "," + this.menu.r + " 0 0,0 " + (this.x + this.menu.r * Math.cos(2 * Math.PI / this.menu.menuArr.length)) + "," + (this.y - this.menu.r * Math.sin(2 * Math.PI / this.menu.menuArr.length)) + " z",
                "class": "sector"
            });
            $(this.itemsArr[i].itemIcon).attr({
                "width": this.r,
                "height": this.r,
                "x": this.x + this.menu.r / 2 - this.r / 2,
                "y": this.y - this.menu.r / 2 - this.r / 2,
                "transform": "rotate(" + (360 / this.menu.menuArr.length * i) + "," + (this.x + this.menu.r / 2) + " " + (this.y - this.menu.r / 2) + ")",
            });
            $(this.itemsArr[i].itemA).html($(this.itemsArr[i].itemA).html());
        }
        $(this.triggerCircle).attr({
            'cx': this.x,
            'r': this.r,
            'cy': this.y,
            'class': this.typeOfCircle[this.node.state] + ' ' + (this.node.templateProjectID == 0 ? ' ' : 'template ') + (this.node.lockState ? 'locked' : ''),
        });
        $(this.nameText).attr({
            'x':this.x,
            'y':this.y,
            'font-size':(this.r / 20).toFixed(2) + "em"
        })[0].textContent = this.node.name;
        $(this.appText).attr({
            'x':this.x,
            'y':this.y,
            'font-size':(this.r / 20).toFixed(2) + "em"
        })[0].textContent = this.node.appName==undefined?"无":this.node.appName;
    },
    drawPath: function () {
        this.outLines.forEach(line => {
            line.remove();
        });
        this.nextStepNodeArray.forEach(nextNode => {
            const line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
            this.stage.el.children.edgesContainer.appendChild(line);
            $(line).attr({
                'class': this.typeOfPath[nextNode.node.state],
                'nodeI': this.node.nodeIndex,
                'nodeJ': nextNode.node.nodeIndex,
                'x1': this.x,
                'y1': this.y,
                'x2': nextNode.x,
                'y2': nextNode.y,
                'stroke': "black"
            });
            $(line).attr("onmouseover", "showDelete(this)");
            $(line).attr("onmouseout", "hideDelete(this)");
            this.outLines.push(line);
        })
    },
    refreshNode: function () {
        this.drawNode();
        this.drawPath();
        this.preStepNodeArray.forEach(node => {
            node.refreshNode();
        });
    },
    /**
     * 绑定相关事件
     */
    bind: function () {
        for (let i = 0; i < this.menu.menuArr.length; i++) {
            $(this.itemsArr[i].itemA).bind('click',{stepNode:this}, function(event){
                event.data.stepNode.menu.menuArr[i].func(event.data.stepNode)
                event.data.stepNode.menuSwitch()
            });
        }
        $(this.trigger).bind('click', {
            el: this
        }, function (event) {
            event.data.el.menuSwitch();
        });
        //绑定删除事件
        $(this).bind('onDelete',{stepNode:this},function(event){
            event.data.stepNode.delete();
        });
        $(this.trigger).bind('appendPre',{stepNode:this},function(event,preNodeIndex){
            const stepNode = event.data.stepNode;
            if(stepNode.node.nodeIndex===preNodeIndex){
                alert('无法链接自身');
            }else{
                stepNode.stage.option.edges.push({
                    "nodeI": preNodeIndex,
                    "nodeJ": stepNode.node.nodeIndex,
                });
                stepNode.stage.refreshGrid();
            }
        })
    },
    menuSwitch:function(){
        let $itemsG = $(this.itemsG);
        if (!$itemsG.attr('class') || $itemsG.attr('class') === "openMenu") {
            $itemsG.attr('class', "closeMenu")
        } else {
            $(".openMenu").attr('class', 'closeMenu');
            $itemsG.attr("class", "openMenu")
        }
    },
    /**
     * 显示绑定的APP名称，同时隐藏节点的名称
     */
    showAppText: function () {
        $(this.appText).show();
        $(this.nameText).hide();
    },
    /**
     * 隐藏节点绑定的APP名称，同时显示节点的名称
     */
    hideAppText:function(){
        $(this.appText).hide();
        $(this.nameText).show();
        
    },
    /**
     * 删除当前节点
     */
    delete:function(){
        if(this.nextStepNodeArray.length>0){
            alert('当前节点包含后续节点，无法删除')
        }else if(this.node.isFinised){
            alert('当前节点状态为已完成，无法删除')
        }else if (confirm("是否确认删除该节点")) {
                const nodes = this.stage.option.nodes;
                //首先删除节点
                nodes.splice(nodes.indexOf(this.node), 1);
                const edges = this.stage.option.edges;
                for (let index = 0; index < edges.length; index++) {
                    if(edges[index].nodeI==this.node.nodeIndex||edges[index].nodeJ==this.node.nodeIndex){
                        edges.splice(index,1);
                        index--;
                    }
                }
                this.stage.refreshGrid();
            }
    },
   
};


var NodePath = function (startNode, endNode, stageObject) {
    this.startNode = startNode;
    this.endNode = endNode;
}
NodePath.prototype = {
    initPath: function () {
        this.line = document.createElementNS('http://www.w3.org/2000/svg', 'line');
        this.stageObject.el.children
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

function svg(tagName, $parent, id, attr) {
    const el = document.createElementNS(svgns, tagName);
    if (id) $(el).attr("id", id.substring(1, id.length));
    if (attr) $(el).attr(attr);
    if ($parent) $parent.append(el);
    return el;
}

