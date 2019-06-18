$(document).ready(function(){
    $("#cur-step-name-href").editable({
        type: 'text',
        title: '修改阶段名称',
        name:'stepName',
        url: updateStepHref,
        validate: validateEmpty,
    });

    $("#cur-step-description-href").editable({
        type: 'textarea',
        title: '修改阶段描述',
        name:'stepDesc',
        url: updateStepHref,
        validate: validateEmpty,
    })
});
function updateStepHref(params) {
    var d = new $.Deferred();
    const data = {
        stepIndex:CUR_STEP.stepIndex
    };
    if (params.value === 'abc') {
        return d.reject('error message'); //returning error via deferred object
    } else {
        data[params.name] = params.value;
        //async saving data in js model
        $.ajax({
            url: API.editStep+params.name,
            type: 'PUT',
            async: true,
            data:data,
            success: function () {
                CUR_STEP[params.name] = params.value;
                d.resolve();
            }
        });
        return d.promise();
    }
}
function validateEmpty(v){
    if (!$.trim(v)) {
        return '不能为空';
    }
}
function refreshStepRow(step){
    $("#stepInfoRow").show();
    $("#nodeInfoRow").hide();
    //节点的名称
    $("#cur-step-name-href").editable("setValue",step.stepName);
    //节点的描述
    $("#cur-step-description-href").editable("setValue",step.stepDesc);
    $("#cur-step-summary").html(step.summary)
    let resultChart = echarts.init(document.getElementById('cur-step-result-static'));
    let activityChart = echarts.init(document.getElementById('cur-step-activity-static'));
    let nodeData = [];
    step.nodeMap.forEach((node,nodeIndex)=>{
        nodeData.push({
            nodeName:node.nodeName,
            planStartTime:node.planStartTime,
            planEndTime:node.planEndTime,
            startTime:node.startTime,
            endTime:node.endTime
        })
    })

    resultStaticFill(resultChart,nodeData);
    activityStaticFill(activityChart,step);
}
const STEP_JS = {
    /**
     * 查看阶段
     * @param step 阶段
     */
    afterViewStep:function (step) {
        CUR_STEP = step;

        refreshStepRow(step)

    },
    /**
     * 在添加step之前
     * @param step
     * @returns {{true: *, step: *}}
     */
    beforeAddStep:function (step) {
        $.ajax({
            url:API.addStep ,
            method:"POST",
            data:step,
            async:false,
            success:function (data) {
                return true;
            },
            error:function () {
                return false;
            }
        })
    },
    /**
     * 在删除阶段之前向服务器发送请求
     * @param step
     */
    beforeRemoveStep:function (step) {
        $.ajax({
            url:API.deleteStep,
            method:"DELETE",
            async:false,
            data:{
                stepIndex: step.stepIndex
            },
            success:function (data) {
                return true;
            },
            error:function () {
                return false;
            }
        })
    }
}

function resultStaticFill($echart,nodeData){
    let option =  {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['计划完成时间', '实际完成时间']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'time'
        },
        yAxis: {
            type: 'category',
            data: nodeData.map(node=>node.nodeName)
        },
        series: [
            {
                name: '计划开始时间',
                type: 'bar',
                stack: '1',
                // label: {
                //     normal: {
                //         show: true,
                //         position: 'insideRight'
                //     }
                // },
                itemStyle: {
                    normal: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    },
                    emphasis: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: nodeData.map(node=>node.planStartTime)
            },
            {
                name: '计划完成时间',
                type: 'bar',
                stack: '1',
                // label: {
                //     normal: {
                //         show: true,
                //         position: 'insideRight'
                //     }
                // },
                data: nodeData.map(node=>node.planEndTime)
            },
            {
                name: '实际开始时间',
                type: 'bar',
                stack: '2',

                itemStyle: {
                    normal: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    },
                    emphasis: {
                        barBorderColor: 'rgba(0,0,0,0)',
                        color: 'rgba(0,0,0,0)'
                    }
                },
                data: nodeData.map(node=>node.startTime)
            },
            {
                name: '实际完成时间',
                type: 'bar',
                stack: '2',
                // label: {
                //     normal: {
                //         show: true,
                //         position: 'insideRight'
                //     }
                // },
                data: nodeData.map(node=>node.endTime)
            }
        ]
    };
    // 基于准备好的dom，初始化echarts实例
    // 使用刚指定的配置项和数据显示图表。
    $echart.setOption(option);
}


function activityStaticFill($echart,step) {
    $.ajax({
        url: API.getStepActivation,
        data:{
          stepIndex:step.stepIndex
        },
        success: function (data) {
            if (data.code == 1) {
                data=data.data;
                let option = {
                    title: {
                        top: 30,
                        text: '当前阶段活跃度',
                        left: 'center',
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        top: '30',
                        left: '100',
                        data: ['活跃度', 'Top 12'],
                        textStyle: {
                            color: '#fff'
                        }
                    },
                    calendar: [{
                        top: 100,
                        left: 'center',
                        range: ['2019-01-01','2019-06-01'],
                        yearLabel: {
                            margin: 40
                        },
                        monthLabel: {
                            nameMap: 'cn',
                            margin: 20
                        },
                        dayLabel: {
                            firstDay: 1,
                            nameMap: 'cn'
                        },
                        splitLine: {
                            show: true,
                            lineStyle: {
                                color: '#000',
                                width: 4,
                                type: 'solid'
                            }
                        },
                    }],
                    series: [
                        {
                            name: '活跃度',
                            type: 'scatter',
                            coordinateSystem: 'calendar',
                            data: data,
                            symbolSize: function (val) {
                                return val[1]*2 ;
                            },
                            itemStyle: {
                                normal: {
                                    color: '#ddb926'
                                }
                            }
                        },
                        {
                            name: 'Top 12',
                            type: 'effectScatter',
                            coordinateSystem: 'calendar',
                            data: data.sort(function (a, b) {
                                return b[1] - a[1];
                            }).slice(0, 12),
                            symbolSize: function (val) {
                                return val[1]*2 ;
                            },
                            showEffectOn: 'render',
                            rippleEffect: {
                                brushType: 'stroke'
                            },
                            hoverAnimation: true,
                            itemStyle: {
                                normal: {
                                    color: '#f4e925',
                                    shadowBlur: 10,
                                    shadowColor: '#333'
                                }
                            },
                            zlevel: 1
                        }
                    ]
                };
                $echart.setOption(option);
            } else {
                console.log(data.msg)
            }
        }
    })
}


/**
 * 更新阶段信息的函数
 * @param params
 * @returns {*}
 */

/**
 * 保存阶段总结的函数
 */
function saveStepSummary() {
    let summary = $("#cur-step-summary").html()
    $.ajax({
        url: API.editStepSummary,
        type: 'PUT',
        async: true,
        data: {
            stepIndex:CUR_STEP.stepIndex,
            summary:summary
        },
        success: function () {
            CUR_STEP['summary'] = summary;
        }
    })
}

