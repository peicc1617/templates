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
                    <li class="active">新建模板</li>
                </ul><!-- /.breadcrumb -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        新建创新方法模板
                    </h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <div class="row">

                            <form id="project-form" class="form-horizontal" role="form">
                                <input type="text" name="_method" value="POST" style="display:none">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="project-name">工程名</label>
                                    <div class="col-sm-9">
                                        <input type="text" id="project-name" name="projectName" required placeholder="请填写工程名"
                                               class="col-xs-10 col-sm-5">
                                        <span class="help-inline col-xs-12 col-sm-7">
												<label class="middle">
													<input class="ace" type="checkbox" name="openState" value="true">
													<span class="lbl">公开项目</span>
												</label>
                                                <span class="help-button" data-rel="popover" data-trigger="hover"
                                                      data-placement="right" data-content="公开项目所有人都能查看，可以在项目信息中更改项目公开状态"
                                                      title="">?</span>
											</span>

                                    </div>

                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="project-creator">创建者 </label>

                                    <div class="col-sm-9">
                                        <input readonly="" type="text" class="col-xs-10 col-sm-5"
                                               value=" ${Session["userInfo"]["nickName"]}">
                                    </div>

                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="project-description">工程描述</label>
                                    <div class="col-sm-9">
                                        <textarea name="projectDesc" id="project-description"
                                                  class="autosize-transition form-control"
                                                  style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 52px;"></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="project-tags">描述关键词</label>
                                    <div class="col-sm-9">
                                        <div class="inline">
                                            <input type="text" name="projectTags" id="project-tags" value=""
                                                   placeholder="输入标签 ..."/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right"
                                           for="project-refer">模板选择</label>

                                    <div class="col-sm-9">
                                        <input type="text" class="col-xs-10 col-sm-5" id="project-refer" value="无"
                                               disabled="disabled" readonly="true">
                                        <input type="text" class="col-xs-10 col-sm-5" hidden id="project-refer-hidden"
                                               name="referID" value="0">
                                        <span class="help-inline col-xs-12 col-sm-7">
												<label class="middle">
													<input class="ace" type="checkbox" id="project-refer-check">
													<span class="lbl">模板</span>
												</label>
											</span>
                                    </div>
                                </div>


                            </form>
                            <div class="clearfix ">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn btn-info" onclick="addProject()">
                                        <i class="ace-icon fa fa-check bigger-110"></i>
                                        新建
                                    </button>

                                    <button class="btn" type="reset">
                                        <i class="ace-icon fa fa-undo bigger-110"></i>
                                        重置
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="row">

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
                <h4 class="modal-title" id="refer-choose-modal-label">选择模板</h4>
            </div>
            <div class="modal-body transparent">
                <table id="refer-table"
                       data-detail-view="true"
                       data-detail-formatter="detailFormatter"
                       data-toggle="table"
                       data-height="500"
                       data-url="/templates/api/refer">
                    <thead>
                    <tr>
                        <th data-radio="true"></th>
                        <th data-field="referID">序号</th>
                        <th data-field="referName">名称</th>
                        <!--<th data-field="description"  data-formatter="descriptionFormatter">描述</th>-->
                        <th data-field="tags">标签</th>
                        <th data-field="recommend" data-formatter="recommendFormatter">推荐度</th>
                        <th data-field="cases" data-formatter="caseFormatter">案例</th>
                        <th data-action="view" data-formatter="viewFormatter">预览</th>

                    </tr>
                    </thead>
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
    $(function () {
        var tag_input = $('#project-tags');
        $('[data-rel=popover]').popover({container: 'body'});
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
            // $tag_obj.add('Programmatically Added');

            var index = $tag_obj.inValues('some tag');
            $tag_obj.remove(index);
        }
        catch (e) {
            //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
            //autosize($('#form-field-tags'));
        }
    })

    $(function () {
        $('#project-refer-check').on('click', function () {
            var inp = $('#project-refer').get(0);
            if (inp.hasAttribute('disabled')) {
                inp.setAttribute('readonly', 'true');
                inp.removeAttribute('disabled');
                inp.value = "";
                $(inp).on('click', function () {
                    $("#refer-choose-modal").modal('show');
                    $("#refer-table").bootstrapTable('refresh');
                })
            }
            else {
                inp.setAttribute('disabled', 'disabled');
                inp.removeAttribute('readonly');
                inp.value = "无";
            }
        });
    })

    function selectRefer(e) {
        var row = $("#refer-table").bootstrapTable("getSelections")[0];
        if (row) {
            $('#project-refer').val("[" + row.referID + "]" + row.referName);
            $("#project-refer-hidden").val(row.referID);
        }
        $(e).prev().click()
    }

    function addProject() {
        let name =$("#project-name").val();
        if(name===undefined||name==null||name.trim()===""){
            alert("请输出工程名");
            return;
        }
        $.ajax({
            url:"/templates/api/project",
            type:"post",
            data:$("#project-form").serialize(),
            success:function (data) {
                switch (data.code) {
                    case 1:
                        window.location.href = data.data + "/view.html";
                        break;
                    default:
                        alert("新建失败")
                        console.log(data.msg);
                }
            }
        })
    }
</script>
</body>

</html>