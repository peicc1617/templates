<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>创新方法工作平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/jquery-ui.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace.min.css"/>
    <link href="/webresources/bootstrap/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/font-awesome/4.5.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace-rtl.min.css"/>
    <link rel="stylesheet" href="/webresources/ace-master/assets/css/ace-skins.min.css"/>
    <link rel="stylesheet" href="/webresources/bootstrap/bootstrap-table/bootstrap-table.css">
    <link rel="stylesheet" href="/webresources/bootstrap/bootstrap3-editable/css/bootstrap-editable.css">
    <script>
        PROJECT_ID = ${project.projectID};

    </script>
</head>
<body class="no-skin">
<div id="navbar" class="navbar navbar-default          ace-save-state navbar-fixed-top">
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
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">
                            <h3 class="header smaller red">权限介绍</h3>

                            <div class="row">
                                <div class="col-xs-4 col-sm-3 pricing-span-header">
                                    <div class="widget-box transparent">
                                        <div class="widget-header">
                                            <h5 class="widget-title bigger lighter">权限管理</h5>
                                        </div>

                                        <div class="widget-body">
                                            <div class="widget-main no-padding">
                                                <ul class="list-unstyled list-striped pricing-table-header">
                                                    <li>管理员指定</li>
                                                    <li>工程用户管理</li>
                                                    <li>阶段增删</li>
                                                    <li>阶段修改</li>
                                                    <li>绑定数据</li>
                                                    <li>查看数据</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-8 col-sm-9 pricing-span-body">
                                    <div class="pricing-span">
                                        <div class="widget-box pricing-box-small widget-color-red3">
                                            <div class="widget-header">
                                                <h5 class="widget-title bigger lighter">未审核</h5>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <ul class="list-unstyled list-striped pricing-table">
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>

                                                        <li>
                                                            <i class="ace-icon fa fa-times red"></i>
                                                        </li>
                                                    </ul>

                                                </div>
                                                <div>
                                                    <a href="#" class="btn btn-block btn-sm btn-danger">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="pricing-span">
                                        <div class="widget-box pricing-box-small widget-color-orange">
                                            <div class="widget-header">
                                                <h5 class="widget-title bigger lighter">普通成员</h5>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <ul class="list-unstyled list-striped pricing-table">
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-check green"></i>可配置</li>
                                                        <li>
                                                            <i class="ace-icon fa fa-check green"></i>可配置
                                                        </li>
                                                    </ul>

                                                </div>

                                                <div>
                                                    <a href="#" class="btn btn-block btn-sm btn-warning">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                                    <div class="pricing-span">
                                        <div class="widget-box pricing-box-small widget-color-green">
                                            <div class="widget-header">
                                                <h5 class="widget-title bigger lighter">超级管理员</h5>
                                            </div>

                                            <div class="widget-body">
                                                <div class="widget-main no-padding">
                                                    <ul class="list-unstyled list-striped pricing-table">
                                                        <li><i class="ace-icon fa fa-times red"></i></li>
                                                        <li><i class="ace-icon fa fa-check green"></i></li>
                                                        <li><i class="ace-icon fa fa-check green"></i></li>
                                                        <li><i class="ace-icon fa fa-check green"></i></li>
                                                        <li><i class="ace-icon fa fa-check green"></i></li>

                                                        <li>
                                                            <i class="ace-icon fa fa-check green"></i>
                                                        </li>
                                                    </ul>


                                                </div>

                                                <div>
                                                    <a href="#" class="btn btn-block btn-sm btn-success">
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-xs-12 col-sm-12 widget-box transparent">
                                <div class="widget-header widget-header-flat">
                                    <h4 class="widget-title lighter">模板权限控制</h4>
                                    <div class="widget-toolbar">
                                        <span class="badge badge-danger" data-target="#user-new-modal"
                                              data-toggle="modal">邀请用户</span>
                                    </div>
                                </div>

                                <div class="widget-body">
                                    <div class="widget-main no-padding">
                                        <table id="project-role-table">

                                        </table>

                                    </div>
                                    <!-- /.widget-main -->
                                </div>
                                <!-- /.widget-body -->
                            </div>

                        </div>
                    </div>


                </div>
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

        <div class="modal fade" id="user-new-modal" tabindex="-1" role="dialog" aria-labelledby="userListModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="userListModalLabel">选择新的用户邀请</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-12">
                                <table id="user-list-table" data-height="400"
                                       data-url="/templates/user/list">
                                    <thead>
                                    <tr>
                                        <th data-field="id">用户编号</th>
                                        <th data-field="nickName">用户昵称</th>
                                        <th data-field="jobNumber">工号</th>
                                        <th data-field="phoneNumber">电话</th>
                                        <th data-field="email">邮箱</th>
                                        <th data-field="op" data-formatter="opFormatter">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>

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
            let PROJECT_USERNAME = new Set();
            $(document).ready(function () {
                $('#user-new-modal').on('show.bs.modal',
                    function() {
                        $("#user-list-table").bootstrapTable('refresh');

                    })
                $("#project-role-table").bootstrapTable({
                    columns: [{
                        field: 'id',
                        visible: false
                    }, {
                        field: 'userID',
                        title: '用户编号',
                        formatter:function (value) {
                            PROJECT_USERNAME.add(value);
                            return value;
                        }
                    }, {
                        field: 'nickName',
                        title: '姓名',

                    }, {
                        field: 'projectRole',
                        title: '项目内角色',
                        editable: {
                            type: 'select',
                            title: '修改项目内的角色',
                            source: JSON.parse('${projectRoleMap}')
                        }
                    }, {
                        field:'op',
                        title:'删除用户',
                        formatter:function (value,row,index) {
                            $button = $("<button></button>").attr({
                                'class': 'btn btn-xs btn-danger',
                                'onclick':'removeFromProject("'+row.userID+'")'
                            });
                            return $button.append('<i class="ace-icon fa fa-trash-o bigger-120"></i>')[0].outerHTML;
                        }
                    }],
                    url: "/templates/api/project/${project.projectID}/role/userList",
                    method: "GET",
                    onEditableSave(field, row, oldValue, $el) {
                        let url = "/templates/api/project/${project.projectID}/role";
                        if (field == "memberRole") {
                            url += "/member";
                        }
                        let data = {
                            userID: row.userID,
                        }
                        data[field] = row[field];
                        $.ajax({
                            url: url,
                            method: "PUT",
                            data: data,
                            async: false,
                            success: function (data) {
                                alert("修改成功")
                            },
                            error: function () {
                                row[field] = oldValue;
                                alert("修改失败")
                            },
                        })
                    }
                })
                $("#user-list-table").bootstrapTable();
            });

            function opFormatter(value, row, index) {
                const userID = row.id;
                $button = $("<a></a>").attr({
                    user: row.id,
                });
                if (!PROJECT_USERNAME.has(userID)) {
                    $button.attr({
                        'class': 'blue',
                        'onclick':'addToProject("'+row.id+'")'
                    }).append("<i class=\"ace-icon glyphicon glyphicon-plus bigger-120\"></i>")
                    $div = $("<div></div>").addClass("action-buttons").append($button);
                    return $div[0].outerHTML;
                }
            }

            function addToProject(userID) {
                $.ajax({
                    url: '/templates/api/project/${project.projectID}/role',
                    'type': 'POST',
                    'data': {
                        userID: userID,
                        projectRole: 3,
                        memberRole: 0
                    },
                    success: function (data) {
                        alert("添加成功")
                        PROJECT_USERNAME.add(userID);
                        $("#project-role-table").bootstrapTable('refresh')
                        $("#user-list-table").bootstrapTable("refresh");
                    }
                })
            }

            function removeFromProject (userID) {
                $.ajax({
                    url: '/templates/api/project/${project.projectID}/role',
                    'type': 'DELETE',
                    'data': {
                        userID: userID,
                    },
                    success: function (data) {
                        alert("删除成功")
                        PROJECT_USERNAME.delete(userID);
                        $("#project-role-table").bootstrapTable('refresh')
                    }
                })
            }
        </script>

    </div>
</div>
</body>

</html>