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
                    <li class="active">我的模板项目</li>
                </ul><!-- /.breadcrumb -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        我的模板项目
                    </h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <div class="col-xs-12">

                                <p id="tool-bar">
                                    <a class="btn btn-sm btn-success" href="new.html">新建模板项目</a>

                                    <a class="btn btn-sm btn-primary" data-toggle="modal"
                                       data-target="#join-project-modal">加入模板项目</a>
                                    <a class="btn btn-sm btn-info" href="public.html">浏览公开项目</a>
                                </p>
                                <table id="project-table">

                                </table>
                                <#--<div class="tabbable">-->
                                    <#--<ul class="nav nav-tabs" id="myTab">-->
                                        <#--<li class="active">-->
                                            <#--<a data-toggle="tab" href="#owned" aria-expanded="true">-->
                                                <#--我创建的-->
                                                <#--<span class="badge badge-success">-->
                                                     <#--<#if ownedProjectList ??>-->
                                                         <#--${ownedProjectList?size}-->
                                                     <#--<#else>-->
                                                            <#--0-->
                                                     <#--</#if>-->
                                                <#--</span>-->
                                            <#--</a>-->
                                        <#--</li>-->

                                        <#--<li class="">-->
                                            <#--<a data-toggle="tab" href="#joined" aria-expanded="false">-->
                                                <#--我加入的-->
                                                <#--<span class="badge badge-primary">-->
                                                     <#--<#if joinedProjectList ??>-->
                                                         <#--${joinedProjectList?size}-->
                                                     <#--<#else>-->
                                                            <#--0-->
                                                     <#--</#if>-->
                                                <#--</span>-->
                                            <#--</a>-->
                                        <#--</li>-->
                                        <#--<li class="">-->
                                            <#--<a data-toggle="tab" href="#apply" aria-expanded="false">-->
                                                <#--我申请的-->
                                                <#--<span class="badge badge-warning">-->
                                                     <#--<#if applyProjectList ??>-->
                                                         <#--${applyProjectList?size}-->
                                                     <#--<#else>-->
                                                            <#--0-->
                                                     <#--</#if>-->
                                                <#--</span>-->
                                            <#--</a>-->
                                        <#--</li>-->
                                    <#--</ul>-->

                                    <#--<div class="tab-content">-->
                                        <#--<div id="owned" class="tab-pane fade active in">-->
                                             <#--<#if ownedProjectList?? && ownedProjectList?size != 0 >-->
                                                 <#--<#list ownedProjectList as project>-->
                                                     <#--<div class="media search-media">-->
                                                         <#--<div class="media-body">-->
                                                             <#--<div>-->
                                                                 <#--<h3 class="media-heading">-->
                                                                     <#--<a href="#" class="blue"><b>${project.projectName}</b></a>-->
                                                                 <#--</h3>-->
                                                             <#--</div>-->

                                                             <#--<p>-->
                                                                 <#--<#if project.projectDesc?? && project.projectDesc!="">-->
                                                                     <#--${project.projectDesc}-->
                                                                 <#--</#if>-->
                                                             <#--</p>-->
                                                             <#--<p>-->
                                                                 <#--<#if project.projectTags?? && project.projectTags!="">-->
                                                                     <#--<#list project.projectTags?split(",") as s>-->
                                                                         <#--<a href="#"><code>${s}</code></a>-->
                                                                     <#--</#list>-->
                                                                 <#--</#if>-->
                                                             <#--</p>-->
                                                             <#--<div>-->
                                                                 <#--<p>-->
                                                                     <#--<small>创建时间：${project.createTime?string('yyyy-MM-dd hh:mm:ss')}</small>-->
                                                                     <#--<small>更新时间：${project.editTime?string('yyyy-MM-dd hh:mm:ss')}</small>-->
                                                                 <#--</p>-->
                                                             <#--</div>-->
                                                             <#--<div class="search-actions text-center">-->
                                                                 <#--<span class="text-info"></span>-->
                                                                 <#--<span class="blue bolder bigger-125"></span>-->
                                                                 <#--<div class="action-buttons bigger-125">-->
                                                                     <#--<a href="#">-->
                                                                         <#--<i class="ace-icon fa fa-exclamation-circle green"></i>-->
                                                                     <#--</a>-->

                                                                     <#--<a href="#">-->
                                                                         <#--<i class="ace-icon fa fa-users red"></i>-->
                                                                     <#--</a>-->

                                                                     <#--<a href="#">-->
                                                                         <#--<i class="ace-icon fa fa-external-link blue"></i>-->
                                                                     <#--</a>-->
                                                                 <#--</div>-->
                                                                 <#--<a class="search-btn-action btn btn-sm btn-block btn-info" href="${project.projectID}/view.html">进入</a>-->
                                                             <#--</div>-->
                                                         <#--</div>-->
                                                     <#--</div>-->
                                                     <#--<hr>-->
                                                 <#--</#list>-->
                                             <#--<#else>-->
                                                 <#--<div class="alert alert-warning">-->
                                                     <#--<button type="button" class="close" data-dismiss="alert">-->
                                                         <#--<i class="ace-icon fa fa-times"></i>-->
                                                     <#--</button>-->
                                                     <#--暂时没有创建项目-->
                                                     <#--<br>-->
                                                 <#--</div>-->
                                             <#--</#if>-->
                                        <#--</div>-->

                                        <#--<div id="joined" class="tab-pane fade">-->
                                              <#--<#if joinedProjectList ?? && joinedProjectList?size != 0 >-->
                                                  <#--<#list joinedProjectList as project>-->
                                                      <#--<div class="media search-media">-->
                                                          <#--<div class="media-body">-->
                                                              <#--<div>-->
                                                                  <#--<h4 class="media-heading">-->
                                                                      <#--<a href="#" class="blue">${project.projectName}</a>-->
                                                                  <#--</h4>-->
                                                              <#--</div>-->
                                                              <#--<p>-->
                                                                  <#--<#if project.projectDesc?? && project.projectDesc!="">-->
                                                                      <#--${project.projectDesc}-->
                                                                  <#--<#else>-->
                                                                      <#--该项目没有描述-->
                                                                  <#--</#if>-->
                                                              <#--</p>-->

                                                              <#--<div class="search-actions text-center">-->
                                                                  <#--<span class="text-info">创建者</span>-->
                                                                  <#--&lt;#&ndash;<span class="blue bolder bigger-125">${project.creatorID}</span>&ndash;&gt;-->
                                                                  <#--<div class="action-buttons bigger-125">-->
                                                                      <#--<a href="#">-->
                                                                          <#--<i class="ace-icon fa fa-phone green"></i>-->
                                                                      <#--</a>-->

                                                                      <#--<a href="#">-->
                                                                          <#--<i class="ace-icon fa fa-heart red"></i>-->
                                                                      <#--</a>-->

                                                                      <#--<a href="#">-->
                                                                          <#--<i class="ace-icon fa fa-star orange2"></i>-->
                                                                      <#--</a>-->
                                                                  <#--</div>-->
                                                                  <#--<a class="search-btn-action btn btn-sm btn-block btn-info"-->
                                                                     <#--href="${project.projectID}/view.html">管理项目</a>-->
                                                              <#--</div>-->
                                                          <#--</div>-->
                                                      <#--</div>-->
                                                  <#--</#list>-->
                                              <#--<#else>-->
                                                  <#--<div class="alert alert-warning">-->
                                                      <#--<button type="button" class="close" data-dismiss="alert">-->
                                                          <#--<i class="ace-icon fa fa-times"></i>-->
                                                      <#--</button>-->
                                                      <#--暂时没有加入项目-->
                                                      <#--<br>-->
                                                  <#--</div>-->

                                              <#--</#if>-->
                                        <#--</div>-->
                                        <#--<div id="apply" class="tab-pane fade">-->
                                               <#--<#if applyProjectList ?? && applyProjectList?size != 0 >-->
                                                   <#--<#list applyProjectList as project>-->
                                                       <#--<div class="media search-media disabled">-->
                                                           <#--<div class="media-body">-->
                                                               <#--<div>-->
                                                                   <#--<h4 class="media-heading">-->
                                                                       <#--<a href="#" class="blue">${project.projectName}</a>-->
                                                                   <#--</h4>-->
                                                               <#--</div>-->
                                                               <#--<p>-->
                                                                   <#--<#if project.projectDesc?? && project.projectDesc!="">-->
                                                                       <#--${project.projectDesc}-->
                                                                   <#--<#else>-->
                                                                       <#--该项目没有描述-->
                                                                   <#--</#if>-->
                                                               <#--</p>-->

                                                               <#--<div class="search-actions text-center">-->
                                                                   <#--<span class="grey">$</span>-->

                                                                   <#--<span class="grey bolder bigger-125">250</span>-->
                                                                   <#--monthly-->
                                                                   <#--<div class="action-buttons bigger-125">-->
                                                                       <#--<a href="#">-->
                                                                           <#--<i class="ace-icon fa fa-phone green"></i>-->
                                                                       <#--</a>-->

                                                                       <#--<a href="#">-->
                                                                           <#--<i class="ace-icon fa fa-heart red"></i>-->
                                                                       <#--</a>-->

                                                                       <#--<a href="#">-->
                                                                           <#--<i class="ace-icon fa fa-star orange2"></i>-->
                                                                       <#--</a>-->
                                                                   <#--</div>-->
                                                                   <#--<a class="search-btn-action btn btn-sm btn-block btn-grey disabled">Unavailable!</a>-->
                                                               <#--</div>-->
                                                           <#--</div>-->
                                                       <#--</div>-->
                                                   <#--</#list>-->
                                               <#--<#else>-->
                                                   <#--<div class="alert alert-warning">-->
                                                       <#--<button type="button" class="close" data-dismiss="alert">-->
                                                           <#--<i class="ace-icon fa fa-times"></i>-->
                                                       <#--</button>-->
                                                       <#--暂时没有加入项目-->
                                                       <#--<br>-->
                                                   <#--</div>-->
                                               <#--</#if>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                            </div>
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
<div class="modal fade" id="join-project-modal" tabindex="-1" role="dialog"
     aria-labelledby="join-project-modal-label" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="join-project-modal-label">加入模板项目</h4>
            </div>
            <div class="modal-body transparent">
                <div class="row">
                    <div class="col-xs-12 col-sm-12">
                        <div class="input-group">
                            <input id="project-input" type="text" class="form-control search-query" placeholder="输入项目编号/邀请码">
                            <span class="input-group-btn">
                                <button type="button" class="btn btn-purple btn-sm" id="search-project-id" onclick="searchProject()">
                                    <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                    查询
                                </button>
                            </span>
                        </div>
                        <div class="hr"></div>
                        <div id="project-show-alert" class="alert alert-warning" style="display: none">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                            <strong>未查询到记录</strong>
                            <span id="project-show-alert-msg"></span>
                            <br>
                        </div>
                        <div id="project-show-alert-success" class="alert alert-success" style="display: none">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="ace-icon fa fa-times"></i>
                            </button>
                            <span id="project-show-alert-msg-success"></span>
                            <br>
                        </div>
                        <div id="project-show" class="profile-user-info profile-user-info-striped" style="display: none">
                            <div class="profile-info-row">
                                <div class="profile-info-name">项目名称</div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-name-show"></span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">项目描述 </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-desc-show">项目描述</span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">项目标签 </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-tags-show"></span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name"> 创建时间 </div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-create-time-show"></span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">修改时间</div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-edit-time-show"></span>
                                </div>
                            </div>

                            <div class="profile-info-row">
                                <div class="profile-info-name">创建者</div>

                                <div class="profile-info-value">
                                    <span class="editable editable-click" id="project-creator-show"></span>
                                </div>
                            </div>
                        </div>
                </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="join-project-btn"  style="display: none">加入</button>
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
        "myProjectList":"/templates/api/project/my",
        "searchProject":"/templates/api/project/info",
        "joinProject":"/templates/api/project/apply"
    }
    $(function () {
        $("#project-table").bootstrapTable({
            url: API.myProjectList,
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
            detailView: true,                  //是否显示父子表,
            detailFormatter: function (index, row) {

            },
            //得到查询的参数
            columns: [
                {
                field: 'projectID',
                title: '项目编号',
                sortable: true
            }, {
                field: 'projectName',
                title: '项目名称',
                sortable: true,
                formatter:function(v, r, i){
                    return '<a target="_blank" href="'+r.projectID+'/view.html">'+v+'</a>'
                }
            }, {
                field: 'projectDesc',
                title: '描述',
                sortable: true,
            }, {
                field: 'projectTags',
                title: '标签',
            }, {
                field: 'creator',
                title: '创建者'
            }, {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return new Date(value).toLocaleString();
                },
                sortable: true
            }, {
                field: 'op',
                title: '操作',
                formatter: function (v, r, i) {
                    if(r.projectRole=="CREATOR"){
                        return '<button class="btn btn-minier btn-default" disabled>离开项目</button>'
                    }
                    if(r.projectRole=='APPLY'){
                        return '<button class="btn btn-minier btn-warning" >取消申请</button>'
                    }
                    return '<button class="btn btn-minier btn-danger">离开项目</button>'
                }
            }],
        })
    });


    function searchProject() {
        const val = $("#project-input").val();
        let data ={};
        if (val.indexOf('-')>0) {
            data['invitationCode']=val;
            $("#join-project-btn").text("加入")
            $('#join-project-btn').attr('onclick','joinProject(undefined,"'+val+'")')

        } else {
            data['projectID']=val;
            $("#join-project-btn").text("申请加入")
            $('#join-project-btn').attr('onclick', 'joinProject("'+val+'",undefined)')
        }
        $.ajax({
            url:API.searchProject,
            type:"get",
            data:data,
            success:function (data) {
                switch (data.code) {
                    case 1:
                        showProject(data.data);
                        break;
                    case 70001:
                        showWarning("项目未公开");
                        break;
                    case 50001:
                        showWarning("项目不存在");
                        break;
                }
            }
        })
    }

    /**
     * 展示项目
     * @param project
     */
    function showProject(project) {
        $("#project-show").show()
        $("#project-show-alert").hide();
        $("#project-show-alert-success").hide();
        $("#project-name-show").text(project['projectName']);
        $("#project-desc-show").text(project['projectDesc']);
        $("#project-tags-show").text(project['projectTags']);
        $("#project-create-time-show").text(new Date(project['createTime']).toLocaleString());
        $("#project-edit-time-show").text(new Date(project['editTime']).toLocaleString());
        $("#project-creator-show").text(project['creator']);

        if(project.projectRole==null){
            $("#join-project-btn").show();
        }else {
            showInfo("您已经在项目内")
        }
    }

    function showInfo(msg){
        $("#project-show-alert-success").show();
        $("#project-show-alert-msg-success").text(msg);
    }
    /**
     * 显示警告
     * @param msg
     */
    function showWarning(msg){
        $("#join-project-btn").hide();
        $("#project-show").hide()
        $("#project-show-alert-success").hide();
        $("#project-show-alert").show();
        $("#project-show-alert-msg").text(msg);
    }
    function joinProject(projectID,invitationCode) {
        const data = {}
        if(projectID==undefined){
            data['invitationCode']=invitationCode;
        }else {
            data['projectID']=projectID;
        }
        $.ajax({
            url:API.joinProject,
            type:"POST",
            data:data,
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