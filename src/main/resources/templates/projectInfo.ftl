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
        //首先获取ID
        function getQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            return null;
        }

        PROJECT_ID = ${project.projectID};

        var ANALYSIS_MSG = {
            projectID: PROJECT_ID,
            type: 0,
            content: ""
        };
        const MSG_TYPE_TABLE = {
            "PROJECT": 1,
            "NODE": 2,
            "USER": 3,
            "RESULT": 4
        }
        const STATE_ERROR = {
            button: {
                style: "btn-danger",
                text: "错误",
            },
            li: []
        }
        const STATE_DIV = {
            "0": {
                button: {
                    style: "btn-light",
                    text: "待绑定",
                },
            },
            "1": {
                button: {
                    style: "btn-grey",
                    text: "待审阅",
                },
                li: [
                    {text: "接受", state: 3},
                    {text: "待修改", state: 2},
                ]
            },
            "2": {
                button: {
                    style: "btn-warning",
                    text: "待修改"
                },
                li: [
                    {text: "接受", state: 3},
                ]
            },
            "3": {
                button: {
                    style: "btn-success",
                    text: "已接受"
                },
                li: [
                    {text: "待修改", state: 2},
                ]
            },
        };
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


                        <div class="space-12"></div>

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
                            <div class="profile-info-row">
                                <div class="profile-info-name">创建人</div>
                                <div class="profile-info-value">
                                    <span id="project-creator">${project.creatorID}</span>
                                </div>
                            </div>
                            <div class="profile-info-row">
                                <div class="profile-info-name">删除项目</div>
                                <div class="profile-info-value">
                                    <a onclick="deleteProject()"><span class="red">删除</span></a>
                                </div>
                            </div>
                        </div>
                        <div class="space-20"></div>

                        <div class="widget-box transparent">
                            <div class="widget-header widget-header-small">
                                <h4 class="widget-title blue smaller">
                                    <i class="ace-icon fa fa-rss orange"></i>
                                    项目统计
                                </h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <div class="row">
                                        <div class="center">
                                                        <span class="btn btn-app btn-sm btn-yellow no-hover">
													<span class="line-height-1 bigger-170" id="project-user-num"></span>

													<br>
													<span class="line-height-1 smaller-90"> 用户数目 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-pink no-hover">
													<span class="line-height-1 bigger-170" id="project-step-num"></span>

													<br>
													<span class="line-height-1 smaller-90"> 阶段数目 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-pink no-hover">
													<span class="line-height-1 bigger-170" id="project-step-num"></span>

													<br>
													<span class="line-height-1 smaller-90"> 创新方法个数 </span>
												</span>
                                            <span class="btn btn-app btn-sm btn-grey no-hover">
													<span class="line-height-1 bigger-170"
                                                          id="project-integration"></span>

													<br>
													<span class="line-height-1 smaller-90"> 集成度 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-success no-hover">
													<span class="line-height-1 bigger-170"
                                                          id="project-amalgamation"></span>

													<br>
													<span class="line-height-1 smaller-90"> 融合度 </span>
												</span>

                                            <span class="btn btn-app btn-sm btn-primary no-hover">
													<span class="line-height-1 bigger-170"
                                                          id="project-value"> 55 </span>

													<br>
													<span class="line-height-1 smaller-90"> 应用效果 </span>
												</span>
                                            <span class="btn btn-app btn-sm btn-primary no-hover">
													<span class="line-height-1 bigger-170"
                                                          id="project-value"> 55 </span>

													<br>
													<span class="line-height-1 smaller-90"> 活跃度 </span>
												</span>
                                        </div>
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

        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/jquery-2.1.4.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace-extra.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/webresources/ace-master/assets/js/ace-elements.min.js"></script>
        <script src="/webresources/ace-master/assets/js/jquery-ui.min.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/bootstrap-table.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="/webresources/bootstrap/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
        <script src="/webresources/bootstrap/bootstrap-table/extensions/editable/bootstrap-table-editable.js"></script>
        <script>
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
                            /**
                             //or fetch data from database, fetch those that match "query"
                             source: function(query, process) {
						  $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
						  .done(function(result_items){
							process(result_items);
						  });
						}
                             */
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
                        url:'/templates/api/project/${project.projectID}/'+params.name,
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
                            url:"/templates/api/project/${project.projectID}",
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
        </script>

    </div>
</div>
</body>

</html>