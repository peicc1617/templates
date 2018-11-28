$(document).ready(function(){

    $("#cur-step-name-href").editable({
        type: 'text',
        title: '修改阶段名称',
        name:'name',
        url: updateStepHref,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },

    })

    $("#cur-step-description-href").editable({
        type: 'textarea',
        title: '修改阶段描述',
        name:'description',
        url: updateStepHref,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },
    })
});

function viewStep() {
    $("#stepInfoRow").attr("style", "display:display");
    $("#nodeInfoRow").attr("style", "display:none");

    //节点的名称
    $("#cur-step-name-href").html(CUR_STEP.name);
    //节点的描述
    $("#cur-step-description-href").html(CUR_STEP.description);
    $("#cur-step-summary").html(CUR_STEP.summary)

    let resultChart = echarts.init(document.getElementById('cur-step-result-static'));
    let activityChart = echarts.init(document.getElementById('cur-step-activity-static'));

    resultStaticFill(resultChart,0);
    activityStaticFill(activityChart,0);

}

function resultStaticFill($echart,stepIndex){
    option = {
        title: {
            top: 30,
            text: '当前阶段创新方法统计',
            left: 'center',
        },
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: ['未绑定', '已绑定','待修改','已接受',]
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis:  {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data: ['方法1','方法2','方法3','方法4']
        },
        series: [
            {
                name: '未绑定',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [320, 302, 301, 334]
            },
            {
                name: '已绑定',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [120, 132, 101, 134]
            },
            {
                name: '待修改',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [220, 182, 191, 234]
            },
            {
                name: '已接受',
                type: 'bar',
                stack: '总量',
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight'
                    }
                },
                data: [150, 212, 201, 154]
            },

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
    return [date.getFullYear()+'-'+ (date.getMonth()+12-6)%12+'-01',date.getFullYear()+'-'+date.getMonth()+'-'+date.getDay()]
}
function activityStaticFill($echart,stepIndex){
    let data = getVirtulData(2018);
    option = {
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
            data:['步数', 'Top 12'],
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


function updateStepHref(params) {
    var d = new $.Deferred();
    if (params.value === 'abc') {
        return d.reject('error message'); //returning error via deferred object
    } else {
        data = {
            projectID:PROJECT_ID,
            stepIndex:CUR_STEP.stepIndex
        }
        data[params.name] = params.value
        //async saving data in js model
        $.ajax({
            url: '/templates/api/project/step/'+params.name,
            type: 'PUT',
            async: true,
            data: data,
            success: function () {
                CUR_STEP[params.name] = params.value;
                stage.refreshGrid();
                d.resolve();
            }

        })
        return d.promise();
    }
}

function saveStepSummary() {
    let summary = $("#stepSummary").html()
    $.ajax({
        url: '/templates/api/project/step/summary',
        type: 'PUT',
        async: true,
        data: {
            projectID:PROJECT_ID,
            stepIndex:CUR_STEP.stepIndex,
            summary:summary
        },
        success: function () {
            CUR_STEP['summary'] = summary;
        }
    })
}
/**
 * 新建阶段
 */
function addStep() {
    let newStep = {
        stepIndex: stage.option.steps[stage.option.steps.length-1].stepIndex+1,
        name:"新阶段",
        description:"描述",
        projectID:PROJECT_ID
    };

    $.ajax({
        url:"/templates/api/project/step/",
        method:"POST",
        data:newStep,
        success:function (data) {
            stage.option.steps.push(newStep)
            stage.refreshGrid();
        }
    })
}
