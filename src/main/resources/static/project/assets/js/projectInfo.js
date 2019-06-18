
const projectInex = {};
$(function () {
    $("#project-name").editable({
        type: 'text',
        title: '输入工程名',
        name:'projectName',
        url: updateProject,
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },

    })
    $("#project-description").editable({
        type: 'text',
        title: '输入工程描述',
        name:'projectDesc',
        validate: function (value) { //字段验证
            if (!$.trim(value)) {
                return '不能为空';
            }
        },
        url: updateProject,
    })
    var tag_input = $('#project-tags');
    try {
        tag_input.tag(
            {
                placeholder: tag_input.attr('placeholder'),
                //enable typeahead by specifying the source array
                source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
            }
        )

        //programmatically add/remove a tag
        var $tag_obj = $('#project-tags').data('tag');
        $tag_obj.add('质量问题');

        var index = $tag_obj.inValues('some tag');
        $tag_obj.remove(index);
    }
    catch (e) {
        //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
        tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
        //autosize($('#form-field-tags'));
    }

    let resultChart = echarts.init(document.getElementById('process-static'));
    processFill(resultChart,ProcessStaticDate)

    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();


    var calendar = $('#calendar').fullCalendar({
        //isRTL: true,
        //firstDay: 1,// >> change first day of week

        buttonHtml: {
            prev: '<i class="ace-icon fa fa-chevron-left"></i>',
            next: '<i class="ace-icon fa fa-chevron-right"></i>'
        },

        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        events:CalendarData
        ,

        eventClick: function(calEvent, jsEvent, view) {
            $("#external-events").html('')


            DayLog[calEvent.start.format()].forEach(log=>{
                $("#external-events").append(`<li><i class="ace-icon fa fa-circle green"></i>${log.content}</li>`)
            })
        }

    });

    $(".projectIndex-input").on('change',function (e) {
        projectInex[$(this).attr("name")]=$(this).val();
    })
    
    $(".edit-input").on('click',function (e) {
        $($(this).data().target).removeAttr("readonly")
    })

    $('.easy-pie-chart.percentage').each(function(){
        $(this).easyPieChart({
            barColor: $(this).data('color'),
            trackColor: '#EEEEEE',
            scaleColor: false,
            lineCap: 'butt',
            lineWidth: 8,
            animate: ace.vars['old_ie'] ? false : 1000,
            size:75
        }).css('color', $(this).data('color'));
    });

})
function updateProject(params) {
    let data = {}
    var d = new $.Deferred();
    if (params.value === 'abc') {
        return d.reject('error message'); //returning error via deferred object
    } else {
        data[params.name] = params.value
        //async saving data in js model
        $.ajax({
            url:API.updateProjectInfo+params.name,
            type: 'PUT',
            async: true,
            data: data,
            success: function (data) {
                if(data.code===1){
                    d.resolve();
                }
            }
        })
        return d.promise();
    }
}
function deleteProject() {
    let name = prompt("是否确认删除该项目(删除后不可恢复,该项目的所有数据即将清空),请在输入框输入项目全名并点击删除?");
    if(name===$("#project-name").text()){
        if(confirm("再一次确认")){
            $.ajax({
                url:API.deleteProject,
                type:"DELETE",
                success:function (data) {
                    if(data.code===1){
                        alert("删除成功,即将跳转到新建项目页面")
                        window.location.href="../new.html"
                    }else {
                        alert("删除失败")
                    }
                }
            })
        }
    }
}
function startProject() {
    if(confirm("确定开始项目(开始后无法停止)")){
        $.ajax({
            url:API.startProject,
            type:"PUT",
            success:function (data) {
                if(data.code===1){
                    alert("开始项目成功")
                }else {
                    alert("删除失败")
                }
            }
        })
    }
}
function generateKey() {
    $.ajax({
        url:API.updateProjectInCode,
        type:"PUT",
        success:function (data) {
            if(data.code==1){
                $("#project-incode").val(data.data[0])
            }
        }
    })
}
function saveProjectIndex() {
    $.ajax({
        url:API.updateProjectIndex,
        data:projectInex,
        type:"PUT",
        success:function (data) {
            if(data.code===1){
                alert("保存成功")
            }else {
                alert("保存成功")
            }
        }
    })
}
function processFill($echart,nodeData){
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

