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
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/fullcalendar.min.css"/>

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
        span.label.MEMBER {
            background: MediumAquaMarine
        }

        ,
        span.label.CREATOR {
            background: LimeGreen
        }

        ,
        span.label.SUPER_ADMIN {
            background: LightSeaGreen
        }

        ,
    </style>
    <script>
        const API = {
            addProjectEva: "/templates/api/eva",
            deleteProject: "/templates/api/project/${project.projectID}",
            updateProjectInCode: "/templates/api/project/${project.projectID}/invitationCode",
            startProject: "/templates/api/project/${project.projectID}/doStart",
            updateProjectIndex: "/templates/api/project/${project.projectID}/index",
            getEvaList:"/templates/api/eva/user",
            bindEva:'/templates/api/eva/'
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
                                    项目评价
                                </h4>
                                <div class="widget-toolbar no-border">
                                    <button class="btn btn-xs btn-white  btn-info bigger" data-toggle="modal" data-target="#newRateModal">
                                        <i class="ace-icon fa fa-plus"></i>
                                        创建新的评价体系
                                    </button>

                                    <button class="btn btn-xs btn-white  bigger btn-success" data-toggle="modal" data-target="#import-new-eva">
                                        <i class="ace-icon fa fa-link"></i>
                                        导入新的评价体系
                                    </button>

                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="row">
                                    <div class="space-6"></div>

                                    <div class="col-sm-12 infobox-container">
                                        <div class="infobox infobox-green">
                                            <div class="infobox-icon">
                                                <i class="ace-icon fa fa-comments"></i>
                                            </div>

                                            <div class="infobox-data">
                                                <span class="infobox-data-number">${project.dea}</span>
                                                <div class="infobox-content">规模效率</div>
                                            </div>
                                        </div>

                                        <div class="infobox infobox-green">
                                            <div class="infobox-icon">
                                                <i class="ace-icon fa fa-twitter"></i>
                                            </div>

                                            <div class="infobox-data">
                                                <span class="infobox-data-number">11</span>
                                                <div class="infobox-content">new followers</div>
                                            </div>

                                            <div class="badge badge-success">
                                                +32%
                                                <i class="ace-icon fa fa-arrow-up"></i>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 widget-container-col ui-sortable" id="widget-container-col-3">
                        <div class="widget-box  ui-sortable-handle" id="widget-box-3">
                            <div class="widget-header ">
                                <h4 class="widget-title">
                                    规模效率
                                </h4>

                                <div class="widget-toolbar">
                                    <label class="inline">
                                        <small class="muted smaller-90">显示标准值</small>
                                        <input checked="checked"
                                               type="checkbox"
                                               data-index-id="0"
                                               class="ace ace-switch ace-switch-5 normal-switch">
                                        <span class="lbl middle"></span>
                                    </label>
                                    <div class="btn-group">
                                        <a class="btn btn-sm btn-white  btn-black" href="#">
                                            <span>编辑指标体系&nbsp;</span>
                                        </a>
                                    </div>
                                    <div class="btn-group">
                                        <a class="btn btn-sm btn-white  btn-black" href="#">
                                            <span>删除指标体系&nbsp;</span>
                                        </a>
                                    </div>
                                    <div class="btn-group">
                                        <a class="btn btn-sm btn-white  btn-default" data-action="collapse"">
                                        <i class="ace-icon fa fa-chevron-up"></i>
                                        </a>
                                    </div>

                                </div>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="row" data-index-id="0">
                                        <div class="col-xs-12">
                                            <h5 class="header smaller lighter blue">资源</h5>
                                            <div class="col-xs-6">
                                                <div class="input-group">
                                    <span class="input-group-addon">
                                        客户数量
                                    </span>

                                                    <input type="number"
                                                           class="form-control search-query  projectIndex-input"
                                                           id="customerN-input" name="customerN"
                                                           value="${projectIndex.customerN}">
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

                                                    <input type="number"
                                                           class="form-control search-query  projectIndex-input" readonly
                                                           id="memberN-input" name="memberN"
                                                           value="${projectIndex.memberN}">
                                                    <span class="input-group-addon normalNumber">
                                        ${projectIndex.memberNNorm}
                                    </span>
                                                </div>
                                            </div>

                                        </div><!-- /.btn-group -->
                                        <div class="col-xs-12">
                                            <h5 class="header smaller lighter green">投入</h5>
                                            <div class="col-xs-6">
                                                <div class="input-group">
                                    <span class="input-group-addon ">
                                        活动经费
                                    </span>

                                                    <input type="text" class="form-control search-query projectIndex-input"
                                                           id="funds-input" name="funds" value="${projectIndex.funds}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input" readonly
                                                           id="nodeN-input" name="nodeN" value="${projectIndex.nodeN}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="trainN-input" name="trainN" value="${projectIndex.trainN}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="meetingN-input" name="meetingN"
                                                           value="${projectIndex.meetingN}">


                                                </div>
                                            </div>


                                        </div><!-- /.btn-group -->
                                        <div class="col-xs-12">
                                            <h5 class="header smaller lighter orange">产出</h5>
                                            <div class="col-xs-6">
                                                <div class="input-group">
                                    <span class="input-group-addon ">
                                        经济收益
                                    </span>

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="income-input" name="income" value="${projectIndex.income}">
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

                                                    <input type="text" class="form-control search-query projectIndex-input"
                                                           id="satisfaction-input" name="satisfaction"
                                                           value="${projectIndex.satisfaction}">
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

                                                    <input type="text" class="form-control search-query projectIndex-input"
                                                           readonly id="completionRate-input" name="completionRate"
                                                           value="${projectIndex.completionRate}">
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

                                                    <input type="text" class="form-control search-query projectIndex-input"
                                                           readonly id="participationIndex-input" name="participationIndex"
                                                           value="${projectIndex.participationIndex}">
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

                                                    <input type="text" class="form-control search-query projectIndex-input"
                                                           id="proficiency-input" name="proficiency"
                                                           value="${projectIndex.proficiency}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="caseN-input" name="caseN" value="${projectIndex.caseN}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="patentN-input" name="patentN"
                                                           value="${projectIndex.patentN}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="paperN-input" name="paperN" value="${projectIndex.paperN}">
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

                                                    <input type="number"
                                                           class="form-control search-query projectIndex-input"
                                                           id="proposalN-input" name="proposalN"
                                                           value="${projectIndex.proposalN}">
                                                    <span class="input-group-addon normalNumber">
                                        ${projectIndex.proposalNNorm}
                                    </span>
                                                </div>
                                            </div>


                                        </div><!-- /.btn-group -->
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
        <div class="modal fade" id="newRateModal" tabindex="-1" role="dialog" aria-labelledby="newRateModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">

                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="newRateModalLabel">创建新的评价体系</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="is-name-input">评价体系的名称</label>

                                    <div class="col-sm-9">
                                        <input type="text" id="is-name-input" class="col-xs-10 col-sm-7">
                                        <span class="help-inline col-xs-12 col-sm-4">
												<label class="middle">
													<input class="ace" type="checkbox" id="is-open-input">
													<span class="lbl">公开指标体系</span>
												</label>
											</span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="is-des-input">评价体系的说明</label>

                                    <div class="col-sm-9">
                                        <textarea id="is-des-input" placeholder="您可以在此处输入评价体系的相关备注，包括适合什么项目" class="col-xs-10 col-sm-7"></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="is-tag-input">评价体系的标签</label>

                                    <div class="col-sm-9">
                                        <div class="inline">
                                            <input type="text" name="tags" id="is-tag-input"  placeholder="输入评价体系的标签..." />
                                        </div>                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-success" onclick="addProjectEva()">创建新的评价体系</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
        <div class="modal fade" id="import-new-eva" tabindex="-1" role="dialog" aria-labelledby="import-new-eva-label"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="import-new-eva-label">创建新的评价体系</h4>
                    </div>
                    <div class="modal-body">
                        <table id="import-new-eva-table">
                        </table>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-success" onclick="addProjectEva()">创建新的评价体系</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->

        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/jquery-2.1.4.min.js"></script>
        <script src="/webresources/ace-master/assets/js/bootstrap-tag.min.js"></script>

        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace-extra.min.js"></script>
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

        <script src="/templates/project/assets/js/projectEva.js"></script>
    </div>
</div>

</body>

</html>