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
    resultStaticFill(resultChart,0);
    activityStaticFill(activityChart,0);
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

function resultStaticFill($echart,stepIndex){
    let option =  {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['计划时间', '实际时间']
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
            data: ['周一','周二','周三']
        },
        series: [
            {
                name: '计划开始时间',
                type: 'bar',
                stack: '1',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
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
                data: [new Date("2018/11/2"),
                    new Date("2018/11/15"),
                    new Date("2018/11/15"),
                ]
            },
            {
                name: '计划时间',
                type: 'bar',
                stack: '1',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [ new Date("2018/11/12"),
                    new Date("2018/11/20"),
                    new Date("2018/11/25")]
            },
            {
                name: '计划完成时间',
                type: 'bar',
                stack: '2',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
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
                data: [ new Date("2018/11/2"),
                    new Date("2018/11/15"),
                    new Date("2018/11/15")]
            },
            {
                name: '实际时间',
                type: 'bar',
                stack: '2',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [ new Date("2018/11/6"),
                    new Date("2018/11/20"),
                    new Date("2018/11/27")]
            },
            {
                name:'平均温度',
                type:'line',
                data:[new Date(),new Date(),new Date()]
            }
        ]
    };
    // 基于准备好的dom，初始化echarts实例
    // 使用刚指定的配置项和数据显示图表。
    $echart.setOption(option);
}
function getVirtulData(year) {
    year = year || '2017';
    var date = +echarts.number.parseDate(year + '-01-01');
    var end = +echarts.number.parseDate((+year + 1) + '-01-01');
    var dayTime = 3600 * 24 * 1000;
    var data = [];
    for (var time = date; time < end; time += dayTime) {
        data.push([
            echarts.format.formatTime('yyyy-MM-dd', time),
            Math.floor(Math.random() * 10000)
        ]);
    }
    return data;
}

function dateRange() {
    let date = new Date();
    return [moment().subtract(3,"M").format("YYYY-MM-DD"),moment().add(1,"M").format("YYYY-MM-DD")]
}
function activityStaticFill($echart,stepIndex){
    let data = getVirtulData(2018);
    let option = {
        title: {
            top: 30,
            text: '当前阶段活跃度',
            left: 'center',
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            top: '30',
            left: '100',
            data:['活跃度', 'Top 12'],
            textStyle: {
                color: '#fff'
            }
        },
        calendar: [{
            top: 100,
            left: 'center',
            range:dateRange(),
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
        series : [
            {
                name: '步数',
                type: 'scatter',
                coordinateSystem: 'calendar',
                data: data,
                symbolSize: function (val) {
                    return val[1] / 500;
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
                    return val[1] / 500;
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

