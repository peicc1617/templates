<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>创新方法工作平台</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/bootstrap.min.css"/>

    <link rel="stylesheet" href="/webresources/ace-master/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/fullcalendar.min.css" />

    <!-- text fonts -->

    <!-- ace styles -->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace.min.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
    <link rel="stylesheet" href="/webresources/bootstrap/bootstrap-table/bootstrap-table.css">

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet"/>
    <![endif]-->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace-rtl.min.css"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="/webresources/ace-master/assets/js/ace-extra.min.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->
    <link href="/webresources/bootstrap/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/webresources/bootstrap/bootstrap-table/bootstrap-table.css">
    <link rel="stylesheet" href="/webresources/bootstrap/bootstrap3-editable/css/bootstrap-editable.css">
    <style>
        span.label.MEMBER{
            background:MediumAquaMarine
        },
        span.label.CREATOR{
            background:LimeGreen
        },
        span.label.SUPER_ADMIN{
            background:LightSeaGreen
        },
    </style>
    <script>
        const API={
            updateProjectInfo:"/templates/api/project/${project.projectID}/",
            deleteProject:"/templates/api/project/${project.projectID}",
            updateProjectInCode:"/templates/api/project/${project.projectID}/invitationCode",
            startProject:"/templates/api/project/${project.projectID}/doStart",
            updateProjectIndex:"/templates/api/project/${project.projectID}/index"
        }
        ProcessStaticDate = ${processStatic!"[]"};
        CalendarData = ${calendarData!"[]"};
        DayLog = ${dayLog!"[]"};

        PROJECT_ID = ${project.projectID};
    </script>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default  ace-save-state">
    <script src="/webresources/common/js/nav-bar.js"></script>
</div>
<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.loadState('main-container')
        } catch (e) {
        }
    </script>
    <#include "/common/navList.ftl">
    <div class="main-content">
        <div class="main-content-inner">
            <div class="breadcrumbs ace-save-state fixed" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">创新方法工具平台</a>
                    </li>

                    <li>
                        <a href="#">柔性模板</a>
                    </li>
                </ul>
                <!-- /.breadcrumb -->
            </div>
            <div class="page-content">
                <#include "/common/projectTitle.ftl">
                <!-- /.ace-settings-box -->
                <div class="row">
                    <div class="col-xs-12 col-sm-12">


                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h4 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    项目信息
                                </h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <div class="row">
                                        <div class="center">
                                                        <span class="btn btn-app btn-sm btn-default  no-hover">
													<span class="line-height-1 bigger-170" id="project-user-num">${memberCnt}</span>
													<br>
													<span class="line-height-1 smaller-90"> 用户数目 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-primary no-hover">
													<span class="line-height-1 bigger-170" id="project-step-num">${stepCnt}</span>

													<br>
													<span class="line-height-1 smaller-90"> 阶段数目 </span>
												</span>
                                            <span class="btn btn-app btn-sm btn-success no-hover">
													<span class="line-height-1 bigger-170" id="project-step-num">${nodeCnt}</span>

													<br>
													<span class="line-height-1 smaller-90"> 工作节点数 </span>
												</span>
                                            <span class="btn btn-app btn-sm btn-yellow no-hover">
													<span class="line-height-1 bigger-170" id="project-step-num">${appCnt}</span>

													<br>
													<span class="line-height-1 smaller-90"> 创新方法 </span>
												</span>
                                            <span class="btn btn-app btn-sm btn-inverse  no-hover">
													<span class="line-height-1 bigger-170">${activity}</span>

													<br>
													<span class="line-height-1 smaller-90"> 活跃度 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-light  no-hover" onclick="jaavascript:window.location='/templates/project/${project.projectID}/eva.html#'">
													<span class="line-height-1 bigger-170"
                                                          id="project-value">${project.dea?string("#.##")}</span>

													<br>
													<span class="line-height-1 smaller-90"> 应用效果 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-light  no-hover" onclick="jaavascript:window.location='/templates/project/${project.projectID}/eva.html#'">
													<span class="line-height-1 bigger-170"
                                                          id="project-value">${project.dea?string("#.##")}</span>

													<br>
													<span class="line-height-1 smaller-90"> 自定义指标 </span>
												</span>

                                        </div>
                                    </div>


                                </div>
                            </div>
                        </div>
                        <div class="space-20"></div>

                        <div class="profile-user-info profile-user-info-striped">
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 项目名</div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click"
                                          id="project-name">${project.projectName}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 项目描述</div>
                                <div class="profile-info-value">
                                    <span class="editable editable-click"
                                          id="project-description">${project.projectDesc}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 项目标签</div>
                                <div class="profile-info-value">
                                    <input type="text" name="project-tags" id="project-tags"
                                           placeholder="Enter tags ..." value="${project.projectTags?if_exists}"/>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name"> 创建时间</div>

                                <div class="profile-info-value">
                                    <span id="project-createTime">${project.createTime?string('yyyy-MM-dd hh:mm:ss')}</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">更新时间</div>

                                <div class="profile-info-value">
                                    <span id="project-editTime">${project.editTime?string('yyyy-MM-dd hh:mm:ss')}</span>
                                </div>
                            </div>
                            <#if project.startTime??>
                                <div class="profile-info-row">
                                    <div class="profile-info-name">监控开始时间</div>

                                    <div class="profile-info-value">
                                        <span id="project-editTime">${project.startTime?string('yyyy-MM-dd')}</span>
                                    </div>
                                </div>
                            </#if>

                            <div class="profile-info-row">
                                <div class="profile-info-name">创建人</div>
                                <div class="profile-info-value">
                                    <span id="project-creator">${project.creatorID}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name">项目管理</div>
                                <div class="profile-info-value">
                                    <#--<a onclick="startProject()"><span class="blue">开始执行</span></a>-->
                                    <a onclick="deleteProject()"><span class="red">删除项目</span></a>
                                </div>
                            </div>

                        </div>
                        <div class="space-20"></div>
                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h4 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    项目进度
                                </h4>

                                <div class="widget-toolbar">
                                    <a onclick="startProject()">
                                        <span class="badge badge-purple" >开始进度管理</span>
                                    </a>
                                </div>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <div class="col-xs-12">
                                        <div id="process-static"
                                             style="width: 100%;height:300px;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="space-20"></div>

                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h4 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    项目成员
                                </h4>

                                <div class="widget-toolbar">
                                    <a onclick="javascript:location='user.html'">
                                        <span class="badge badge-info">用户管理</span>
                                    </a>
                                    <a data-toggle="modal" data-target="#invitationCodeModal">
                                        <span class="badge badge-success">生成邀请码</span>
                                    </a>
                                </div>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <div class="clearfix" id="person-div">
                                        <#list project.members as member>
                                            <div class="itemdiv memberdiv">
                                                <div class="user">
                                                    <img src="/webresources/ace-master/assets/images/avatars/user.jpg">
                                                </div>
                                                <div class="body">
                                                    <div class="name">
                                                        <a href="#">${member.userID}</a>
                                                    </div>

                                                    <div>
                                                        <span class="label label-sm ${member.projectRole.toString()}">${member.projectRole.getName()}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </#list>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="space-20"></div>
                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h4 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    最近活动
                                </h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="tabbable">
                                                <ul class="nav nav-tabs" id="myTab">
                                                    <li class="active">
                                                        <a data-toggle="tab" href="#calendar-view">
                                                            <i class="green ace-icon fa fa-home bigger-120"></i>
                                                            日历视图
                                                        </a>
                                                    </li>

                                                    <li>
                                                        <a data-toggle="tab" href="#time-view">
                                                            时间轴视图
                                                        </a>
                                                    </li>

                                                </ul>

                                                <div class="tab-content">
                                                    <div id="calendar-view" class="tab-pane fade in active">
                                                        <div class="row">
                                                            <div class="col-sm-12">
                                                                <div class="col-sm-4">
                                                                    <div class="space"></div>

                                                                    <div id="calendar"></div>
                                                                </div>

                                                                <div class="col-sm-8">
                                                                    <div class="widget-box transparent">
                                                                        <div class="widget-header">
                                                                            <h4>事件详情</h4>
                                                                        </div>

                                                                        <div class="widget-body">
                                                                            <div class="widget-main no-padding">
                                                                                <div>
                                                                                    <div class="col-xs-12">

                                                                                        <ul class="list-unstyled spaced2"  id="external-events">
                                                                                            <#if todayLog?? && todayActivity?size!=0>
                                                                                                <#list todayLog as log>
                                                                                                    <li><i class="ace-icon fa fa-circle green"></i>${log.content}</li>

                                                                                                </#list>
                                                                                            </#if>



                                                                                        </ul>
                                                                                    </div>


                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>

                                                    <div id="time-view" class="tab-pane fade">
                                                        <div class="row">
                                                            <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                                                                <#--今天记录-->
                                                                <#if todayActivity?? && todayActivity?size!=0>
                                                                    <div class="timeline-container">
                                                                        <div class="timeline-label">
													<span class="label label-primary arrowed-in-right label-lg">
														<b>今天</b>
													</span>
                                                                        </div>
                                                                        <div class="timeline-items">
                                                                            <#list todayActivity as activity>
                                                                                <div class="timeline-item clearfix">
                                                                                    <#include "/common/activityIconFormatter.ftl">
                                                                                    <div class="widget-box transparent">
                                                                                        <div class="widget-body">
                                                                                            <div class="widget-main">
                                                                                                <span class="bolder blue">${activity.userID}:</span>
                                                                                                ${activity.content?replace("[", "<span class=\"green bolder\">")?replace("]", "</span>")}
                                                                                                <div class="pull-right">
                                                                                                    <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                                                    今天,${activity.operateDate?string('hh:mm:ss')}
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                            </#list>
                                                                        </div>
                                                                    </div>
                                                                </#if>
                                                                <#--昨天的记录-->
                                                                <#if yesterdayActivity?? && yesterdayActivity?size!=0>
                                                                    <div class="timeline-container">
                                                                        <div class="timeline-label">
													<span class="label label-primary arrowed-in-right label-lg">
														<b>昨天</b>
													</span>
                                                                        </div>
                                                                        <div class="timeline-items">
                                                                            <#list yesterdayActivity as activity>
                                                                                <div class="timeline-item clearfix">
                                                                                    <#include "/common/activityIconFormatter.ftl">
                                                                                    <div class="widget-box transparent">
                                                                                        <div class="widget-body">
                                                                                            <div class="widget-main">
                                                                                                <span class="bolder blue">${activity.userID}:</span>
                                                                                                ${activity.content?replace("[", "<span class=\"green bolder\">")?replace("]", "</span>")}
                                                                                                <div class="pull-right">
                                                                                                    <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                                                    昨天,${activity.operateDate?string('hh:mm:ss')}
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </#list>
                                                                        </div>
                                                                        <!-- /.timeline-items -->
                                                                    </div>
                                                                </#if>
                                                                <#--更早的记录-->
                                                                <#if beforeActivity?? && beforeActivity?size!=0>
                                                                    <div class="timeline-container">
                                                                        <div class="timeline-label">
                                                <span class="label label-primary arrowed-in-right label-lg">
                                                    <b>更早</b>
                                                </span>
                                                                        </div>
                                                                        <div class="timeline-items">
                                                                            <#list yesterdayActivity as activity>
                                                                                <div class="timeline-item clearfix">
                                                                                    <#include "/common/activityIconFormatter.ftl">
                                                                                    <div class="widget-box transparent">
                                                                                        <div class="widget-body">
                                                                                            <div class="widget-main">
                                                                                                <span class="bolder blue">${activity.userID}:</span>
                                                                                                ${activity.content?replace("[", "<span class=\"green bolder\">")?replace("]", "</span>")}
                                                                                                <div class="pull-right">
                                                                                                    <i class="ace-icon fa fa-clock-o bigger-110"></i>
                                                                                                    ${activity.operateDate?string('yyyy-MM-dd hh:mm:ss')}
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </#list>
                                                                        </div>
                                                                        <!-- /.timeline-items -->
                                                                    </div>
                                                                </#if>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div><!-- /.col -->

                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->
        <div class="modal fade" id="invitationCodeModal" tabindex="-1" role="dialog" aria-labelledby="invitationCodeModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="invitationCodeModalLabel">生成邀请码</h4>
                    </div>
                    <div class="modal-body">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <i class="ace-icon fa fa-key "></i>
                            </span>
                            <input id="project-incode"type="text" class="form-control search-query" value="${project.invitationCode}">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-inverse btn-white" onclick="generateKey()">
                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                    生成
                                </button>
                            </span>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
        <div class="modal fade" id="rateModal" tabindex="-1" role="dialog" aria-labelledby="ratModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="ratModalLabel">录入项目评价数据</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="center">
                                <div class="col-xs-12">
                                    <div class="col-xs-6">
                                        <div class="easy-pie-chart percentage" data-percent=" ${projectIndex.dea*100}" data-color="#87CEEB">
                                            <span class="percent"> ${project.dea*100}</span>%
                                        </div>

                                    </div>
                                    <div class="col-xs-6">
<span class="btn btn-app btn-sm btn-light  no-hover" >
													  <label>
                                    <input name="switch" id="normalSwitch" class="ace ace-switch ace-switch-2" type="checkbox" />
                                    <span class="lbl"></span>
                                </label>
													<br>
													<span class="line-height-1 smaller-90 "> 标准值 </span>
												</span>
                                    </div>
                                </div>




                            </div>

                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <h3 class="header smaller lighter blue">资源</h3>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        客户数量
                                    </span>

                                        <input type="number" class="form-control search-query  projectIndex-input" id="customerN-input" name="customerN" value="${projectIndex.customerN}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.customerNNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                <div class="input-group">

                                    <span class="input-group-addon">
                                        成员数量
                                    </span>

                                    <input type="number"  class="form-control search-query  projectIndex-input" readonly id="memberN-input" name="memberN" value="${projectIndex.memberN}">
                                    <span class="input-group-addon normalNumber">
                                        ${projectIndex.memberNNorm}
                                    </span>
                                </div>
                                </div>

                            </div><!-- /.btn-group -->
                            <div class="col-xs-12">
                                <h3 class="header smaller lighter green">投入</h3>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon ">
                                        活动经费
                                    </span>

                                        <input type="text" class="form-control search-query projectIndex-input" id="funds-input" name="funds" value="${projectIndex.funds}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.fundsNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">

                                    <span class="input-group-addon">
                                        工作节点数目
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input" readonly id="nodeN-input" name="nodeN" value="${projectIndex.nodeN}">
                                        <span class="input-group-addon normalNumber">
                                         ${projectIndex.nodeNNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        培训数目
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input" id="trainN-input" name="trainN" value="${projectIndex.trainN}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.trainNNorm}
                                    </span>
                                    </div>

                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        会议数目
                                    </span>

                                        <input type="number" class="form-control search-query projectIndex-input" id="meetingN-input" name="meetingN"  value="${projectIndex.meetingN}">


                                    </div>
                                </div>


                            </div><!-- /.btn-group -->
                            <div class="col-xs-12">
                                <h3 class="header smaller lighter orange">产出</h3>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon ">
                                        经济收益
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input" id="income-input" name="income" value="${projectIndex.income}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.incomeNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">

                                    <span class="input-group-addon">
                                        客户满意度
                                    </span>

                                        <input type="text" class="form-control search-query projectIndex-input" id="satisfaction-input" name="satisfaction" value="${projectIndex.satisfaction}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.satisfactionNorm}
                                    </span>
                                    </div>
                                </div>
                                
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        项目完工率
                                    </span>

                                        <input type="text" class="form-control search-query projectIndex-input" readonly id="completionRate-input" name="completionRate" value="${projectIndex.completionRate}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.completionRateNorm}
                                    </span>

                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        人员参与指数
                                    </span>

                                        <input type="text" class="form-control search-query projectIndex-input" readonly id="participationIndex-input" name="participationIndex" value="${projectIndex.participationIndex}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.participationIndexNorm}
                                    </span>

                                    </div>
                                </div>

                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        创新方掌握程度
                                    </span>

                                        <input type="text" class="form-control search-query projectIndex-input" id="proficiency-input" name="proficiency" value="${projectIndex.proficiency}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.proficiencyNorm}
                                    </span>

                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        案例数
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input" id="caseN-input" name="caseN"value="${projectIndex.caseN}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.caseNNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        专利数
                                    </span>

                                        <input type="number" class="form-control search-query projectIndex-input" id="patentN-input" name="patentN"value="${projectIndex.patentN}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.patentNNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        论文数
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input" id="paperN-input" name="paperN" value="${projectIndex.paperN}" >
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.paperNNorm}
                                    </span>
                                    </div>
                                </div>
                                <div class="col-xs-12">
                                    <div class="input-group">
                                    <span class="input-group-addon">
                                        有效提案数
                                    </span>

                                        <input type="number"  class="form-control search-query projectIndex-input"  id="proposalN-input"  name="proposalN" value="${projectIndex.proposalN}">
                                        <span class="input-group-addon normalNumber">
                                        ${projectIndex.proposalNNorm}
                                    </span>
                                    </div>
                                </div>









                            </div><!-- /.btn-group -->
                        </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-success" onclick="saveProjectIndex()">保存修改</button>

                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>
    <div class="modal fade" id="rateModal2" tabindex="-1" role="dialog" aria-labelledby="ratModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">

                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="ratModalLabel">自定义评价指标</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <button type="button" class="btn btn-primary btn-xs" onclick="addDiyIndex()">新建评价指标</button>
                        <button type="button" class="btn btn-danger btn-xs" onclick="deleteDiyIndex()">删除评价指标</button>
                        <#--自定义评价指标table-->
                        <table id="diyIndexTable">

                        </table>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-success" onclick="saveProjectIndex()">保存自定义评价指标</button>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/jquery-2.1.4.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace.min.js"></script>
        <script type="text/javascript"   src="/webresources/ace-master/assets/js/ace-extra.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace-elements.min.js"></script>
        <#--moment是时间插件，必须在fullcalendaer之前-->
        <script src="/templates/project/assets/js/moment-with-locales.min.js"></script>

        <script src="/webresources/ace-master/assets/js/jquery-ui.min.js"></script>
        <script src="/webresources/ace-master/assets/js/fullcalendar.min.js"></script>
    <script src="/webresources/ace-master/assets/js/jquery.easypiechart.min.js"></script>


    <script src="/webresources/bootstrap/bootstrap-table/bootstrap-table.js"></script>

        <#--这里需要引用echarts的3.x版本，因为在绘制甘特图的时候，3.x的版本可以支持时间的堆叠，而4.x版本不支持。-->
        <script src="/templates/project/assets/js/echarts.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="/webresources/bootstrap/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>

        <script src="/templates/project/assets/js/projectInfo.js"></script>


    </div>
</div>

</body>

</html>