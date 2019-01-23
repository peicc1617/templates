<div id="sidebar" class="sidebar                  responsive                    ace-save-state" data-sidebar="true"
     data-sidebar-scroll="true" data-sidebar-hover="true">
    <script type="text/javascript">
        try {
            ace.settings.loadState('sidebar')
        } catch (e) {
        }
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- /.sidebar-shortcuts -->
    <#if project??>
        <#assign curProjectID=project.projectID/>
    <#else >
        <#assign curProjectID=-1/>
    </#if>
    <ul class="nav nav-list">
        <li>
            <a href="/templates/project/my.html">
                <i class="menu-icon fa fa-tachometer"></i>
                <span class="menu-text">我的模板项目</span>
            </a>
            <b class="arrow"></b>
        </li>
        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-list"></i>
                <span class="menu-text">我创建的
                 <span class="badge badge-success">
                     <#if ownedProjectList ??>
                         ${ownedProjectList?size}
                     <#else>
                         0
                     </#if>
                 </span>
                </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>
            <ul class="submenu">
                <#if ownedProjectList?? && ownedProjectList?size!=0>
                    <#list ownedProjectList as project>
                        <li class="<#if project.projectID==curProjectID>active</#if>">
                            <a href="/templates/project/${project.projectID}/view.html">
                                <i class="menu-icon fa fa-caret-right"></i>
                                ${project.projectName}
                            </a>
                        </li>
                    </#list>
                <#else >
                    <li class="">
                        <a href="elements.html">
                            <i class="menu-icon fa fa-caret-right"></i>
                            无
                        </a>
                    </li>
                </#if>
            </ul>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-list"></i>
                <span class="menu-text">我加入的
                 <span class="badge badge-primary">
                     <#if joinedProjectList ??>
                         ${joinedProjectList?size}
                     <#else>
                         0
                     </#if>
                 </span>
                </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>

            <b class="arrow"></b>
            <ul class="submenu">
                <#if joinedProjectList?? && joinedProjectList?size!=0>
                    <#list joinedProjectList as project>
                        <li class="<#if project.projectID==curProjectID>active</#if>">
                        <a href="/templates/project/${project.projectID}/view.html">
                            <i class="menu-icon fa fa-caret-right"></i>
                            ${project.projectName}
                        </a>
                        </li>
                    </#list>
                <#else >
                    <li class="">
                        <a href="#">
                            <i class="menu-icon fa fa-caret-right"></i>
                            无
                        </a>
                    </li>
                </#if>
            </ul>
        </li>

        <li class="">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon fa fa-list"></i>
                <span class="menu-text">我申请的
                    <span class="badge badge-warning">
                     <#if applyProjectList ??>
                         ${applyProjectList?size}
                     <#else>
                         0
                     </#if>
                    </span>
                </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
                <#if applyProjectList ?? && applyProjectList?size!=0>
                    <#list applyProjectList as project >
                        <li class="<#if project.projectID==curProjectID>active</#if>">
                            <a href="/templates/project/${project.projectID}/view.html">
                                <i class="menu-icon fa fa-caret-right"></i>
                                ${project.projectName}
                            </a>
                        </li>
                    </#list>
                <#else >
                    <li class="">
                        <a href="#">
                            <i class="menu-icon fa fa-caret-right"></i>
                            无
                        </a>
                    </li>
                </#if>
            </ul>
        </li>
    </ul><!-- /.nav-list -->

    <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
        <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
           data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
    </div>
    <script>

    </script>

</div>