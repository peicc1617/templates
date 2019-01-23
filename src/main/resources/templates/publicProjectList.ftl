<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <title>创新方法柔性模板</title>

    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>

    <!-- page specific plugin styles -->

    <!-- text fonts -->
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/fonts.googleapis.com.css"/>

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
    <script>

    </script>
</head>

<body class="no-skin">
<div id="navbar" class="navbar navbar-default          ace-save-state">
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
            <div class="breadcrumbs ace-save-state" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">创新方法柔性模板</a>
                    </li>
                    <li class="active">公开模板项目</li>
                </ul><!-- /.breadcrumb -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        公开模板项目
                    </h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <table id="project-table">
                            </table>

                        </div>

                    </div>

                </div>

            </div><!-- /.page-content -->
        </div>
    </div><!-- /.main-content -->

    <#include "/common/footer.ftl">


    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<div class="modal fade" id="refer-choose-modal" tabindex="-1" role="dialog"
     aria-labelledby="refer-choose-modal-label" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="refer-choose-modal-label">选择参照模板</h4>
            </div>
            <div class="modal-body transparent">
                <p id="tool-bar">
                    <a class="btn btn-sm btn-success" href="new.html">新建模板项目</a>
                </p>
                <table id="project-table">
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="selectRefer(this)">确定</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
<!-- basic scripts -->

<!--[if !IE]> -->
<script src="/webresources/ace-master/assets/js/jquery-2.1.4.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write(
        "<script src='/webresources/ace-master/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/webresources/ace-master/assets/js/bootstrap.min.js"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="assets/js/excanvas.min.js"></script>
<![endif]-->
<script src="/webresources/ace-master/assets/js/jquery-ui.custom.min.js"></script>
<script src="/webresources/ace-master/assets/js/jquery.ui.touch-punch.min.js"></script>
<!-- ace scripts -->
<script src="/webresources/ace-master/assets/js/ace-elements.min.js"></script>
<script src="/webresources/ace-master/assets/js/ace.min.js"></script>
<script src="/webresources/ace-master/assets/js/bootstrap-tag.min.js"></script>
<script src="/webresources/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script src="/webresources/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script>
    const API = {
        publicProjectList: "/templates/api/project/public",
        joinProject:"/templates/api/project/apply",
        quitProject:"/templates/api/project/quit"
    }
    $(function () {
        $("#project-table").bootstrapTable({
            url: API.publicProjectList,
            method: "get",
            toolbar: "#tool-bar",
            striped: true,                      //是否显示行间隔色
            // pagination: true,                   //是否显示分页（*）
            // sortable: true,                     //是否启用排序
            // sortOrder: "asc",                   //排序方式
            // sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            // pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            // pageSize: 10,                     //每页的记录行数（*）
            // pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: true,                      //是否显示表格搜索
            // strictSearch: true,
            // showRefresh: true,                  //是否显示刷新按钮
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            // showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表,
            //得到查询的参数
            columns: [
                {
                    field: 'projectID',
                    title: '项目编号',
                    sortable: true
                },
                {
                    field: 'projectName',
                    title: '项目名称',
                    sortable: true,
                    formatter: function (v, r, i) {
                        return '<a target="_blank" href="' + r.projectID + '/view.html">' + v + '</a>'
                    }
                }, {
                    field: 'projectDesc',
                    title: '描述',
                    sortable: true,
                    formatter: function (v, r, i) {
                        if(v!=null&&v!=undefined&&v.trim().length!=0){
                            return v.substring(0, 20) + "...";
                        }

                    }
                }, {
                    field: 'projectTags',
                    title: '标签',
                    formatter: function (v) {
                        return v.split(',').map(tag => {
                            return '<span class="label label-warning label-white middle">' + tag + '</span>'
                        }).join('');
                    }
                }, {
                    field: 'creator',
                    title: '创建者'
                }, {
                    field: 'editTime',
                    title: '更新时间',
                    formatter: function (value) {
                        return new Date(value).toLocaleString();
                    },
                    sortable: true
                }, {
                    field: 'op',
                    title: '操作',
                    formatter: function (v, r, i) {
                        if(r.projectRole==null||r.projectRole==undefined){
                            return '<button class="btn btn-minier btn-info" onclick="joinProject('+r.projectID+')">申请加入</button>';
                        }
                        if(r.projectRole==="APPLY"){
                            return '<button class="btn btn-minier btn-warning" onclick="quitFromProject('+r.projectID+')">取消申请</button>';
                        }
                        return '<button class="btn btn-minier btn-danger" onclick="quitFromProject('+r.projectID+')">退出项目</button>';

                    }
                }],
        })
    });

    function joinProject(projectID) {
        $.ajax({
            url:API.joinProject,
            type:"POST",
            data:{
                projectID:projectID
            },
            success:function (data) {
                if(data.code==1){
                    alert("申请成功");
                    $("#project-table").bootstrapTable("refresh");
                }
            }
        })
    }

    function quitFromProject(projectID) {
        $.ajax({
            url:API.quitProject,
            type:"DELETE",
            data:{
                projectID:projectID
            },
            success:function (data) {
                if(data.code==1){
                    alert("操作成功");
                    $("#project-table").bootstrapTable("refresh");
                }
            }
        })
    }
</script>
</body>

</html>