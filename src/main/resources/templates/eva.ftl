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
    </style>
    <script>
        const API = {
            evaIndexAddAndGet:"/templates/api/eva/${eva.evaID}/index",
            editIndex:"/templates/api/index",
            importIndex:"/templates/api/eva/${eva.evaID}/index",
            editIndexW:"/templates/api/eva/${eva.evaID}/index/",
            editEva:"/templates/api/eva/${eva.evaID}",
            deleteIndex:"/templates/api/index/",
            getAllIndex:"/templates/api/index/list?evaID=${eva.evaID}",
        }
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
                <div class="page-header">
                    <h1>
                        评价体系
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            <a href="#" id="eva-name">${eva.name!''}</a>
                        </small>
                    </h1>
                </div>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="alert alert-info">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                            <strong>指标体系描述：</strong>
                            <a href="#" id="eva-des">${eva.des!''}</a>
                            <br>
                        </div>
                    </div><!-- /.col -->

                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12">
                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h3 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    评价指标
                                </h3>
                                <div class="widget-toolbar no-border">

                                    <button class="btn btn-xs btn-white  btn-primary bigger" onclick="addDiyIndex()">
                                        <i class="ace-icon fa fa-plus"></i>
                                        新建评价指标
                                    </button>

                                    <button class="btn btn-xs btn-white  bigger btn-danger " onclick="deleteDiyIndex()">
                                        <i class="ace-icon fa fa-link"></i>
                                        删除评价指标
                                    </button>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <#--自定义评价指标table-->
                                        <table id="diyIndexTable">

                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12">
                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h3 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    可选的评价指标
                                </h3>
                                <div class="widget-toolbar no-border">

                                    <button class="btn btn-xs btn-white  btn-primary bigger" onclick="importIndex()">
                                        <i class="ace-icon fa fa-plus"></i>
                                        导入
                                    </button>

                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <#--自定义评价指标table-->
                                        <table id="import-table">

                                        </table>
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
                    <button type="button" class="btn btn-success" onclick="saveProjectIndex()">创建新的评价体系</button>
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

        <script src="/webresources/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="/webresources/bootstrap/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>

        <script src="/templates/project/assets/js/eva.js"></script>


    </div>
</div>

</body>

</html>