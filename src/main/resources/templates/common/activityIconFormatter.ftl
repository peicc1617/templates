
<div class="timeline-info">

    <#if activity.logMethod=="GET">
        <#assign classs="info"/>
    <#elseIf activity.logMethod=="ADD">
        <#assign classs="success"/>
    <#elseIf activity.logMethod=="UPDATE">
        <#assign classs="primary"/>
    <#elseIf activity.logMethod=="DELETE">
        <#assign classs="danger"/>
    <#elseIf activity.logMethod=="USE">
        <#assign classs="purple"/>
    </#if>

    <#if activity.logType=="PROJECT"> 
        <i class="timeline-indicator ace-icon fa fa-bullhorn btn btn-${classs} no-hover"></i>
    <#elseIf activity.logType=="STEP">
        <i class="timeline-indicator ace-icon fa fa-leaf btn btn-${classs} no-hover"></i>
    <#elseIf activity.logType=="NODE">
        <i class="timeline-indicator ace-icon fa fa-users btn btn-${classs} no-hover"></i>
    <#elseIf activity.logType=="RESULT">
        <i class="timeline-indicator ace-icon fa fa-folder-o btn btn-${classs} no-hover"></i>
    <#elseIf activity.logType=="USER">
        <i class="timeline-indicator ace-icon fa fa-lightbulb-o btn btn-${classs} no-hover"></i>
    </#if>
</div>