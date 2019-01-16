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
    <link rel="stylesheet" href="/templates/project/assets/css/step.css">
    <script>
        PROJECT_ID = ${project.projectID}
        //解析projectID，
        if(isNaN(PROJECT_ID)){
            //如果解析出错误
            alert("参数错误");//后续跳转
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
                    {text: "接受", state:3},
                    {text: "待修改", state:2},
                ]
            },
            "2": {
                button: {
                    style: "btn-warning",
                    text: "待修改"
                },
                li: [
                    {text: "接受", state:3},
                ]
            },
            "3": {
                button: {
                    style: "btn-success",
                    text: "已接受"
                },
                li: [
                    {text: "待修改", state:2},
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
                        <a href="#">${project.projectName}</a>
                    </li>
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <div class="page-content">
                <#include "/common/projectTitle.ftl">
                <!-- /.ace-settings-box -->
                <div class="row">
                    <div class="col-xs-12">

                        <div id="svgRow" center class="row " >
                            <div class="col-xs-12">

                                <div class="col-sm-6" style="height:40px;line-height:35px; ">
                                    <label class="pull-right inline">
                                        <strong class="muted smaller-90">工作路径</strong>
                                        <input id="linked-change-btn" checked="checked" type="checkbox"
                                               class="ace ace-switch ace-switch-5">
                                        <span class="lbl middle"></span>
                                    </label>
                                    <label class="pull-right inline">
                                        <strong class="muted smaller-90">阶段</strong>
                                        <input id="step-change-btn" checked="checked" type="checkbox"
                                               class="ace ace-switch ace-switch-5">
                                        <span class="lbl middle"></span>
                                    </label>
                                    <label class="pull-right  inline">
                                        <strong class="muted smaller-90">创新方法</strong>
                                        <input id="name-change-btn" type="checkbox" class="ace ace-switch ace-switch-5">
                                        <span class="lbl middle"></span>
                                    </label>
                                </div>
                                <div class="col-sm-6">
                                    <svg class="center" id="toolbar" xmlns="http://www.w3.org/2000/svg" version="1.1"
                                         preserveAspectRatio="xMidYMin meet" width="100%"
                                         height="40" viewBox="0 0 430 40">
                                        <g class="trigger menu-trigger" nodeIndex="2">
                                            <circle cx="20" r="10" cy="20" class="stepnode finished"></circle>
                                            <text x="40" y="20" class="node-text" font-size="1.25em" text-anchor="start"
                                                  dominant-baseline="middle">已完成
                                            </text>
                                        </g>
                                        <g class="trigger menu-trigger" nodeIndex="2">
                                            <circle cx="120" r="10" cy="20" class="stepnode unfinished"></circle>
                                            <text x="140" y="20" class="node-text" font-size="1.25em" text-anchor="start"
                                                  dominant-baseline="middle">未完成
                                            </text>
                                        </g>
                                        <g class="trigger menu-trigger" nodeIndex="2">
                                            <circle cx="220" r="10" cy="20" class="stepnode locked  "></circle>
                                            <text x="240" y="20" class="node-text" font-size="1.25em" text-anchor="start"
                                                  dominant-baseline="middle">锁定
                                            </text>
                                        </g>
                                        <g class="trigger menu-trigger" nodeIndex="2">
                                            <circle cx="320" r="10" cy="20" class="stepnode finished template"></circle>
                                            <text x="340" y="20" class="node-text" font-size="1.25em" text-anchor="start"
                                                  dominant-baseline="middle">已连接模板
                                            </text>
                                        </g>

                                    </svg>
                                </div>
                            </div>


                            <svg id="mySVG" xmlns="http://www.w3.org/2000/svg" version="1.1"
                                 preserveAspectRatio="xMidYMin meet" style="border-top:1px solid lightgrey;border-bottom:1px solid lightgrey">
                                <g id="filterContainer">
                                    <filter id="btnFeOffset" x="0" y="0" width="200%" height="200%">
                                        <feGaussianBlur in="SourceAlpha" stdDeviation="2.2"/>
                                        <feOffset dx="2" dy="2" result="offsetblur"/>
                                        <feFlood flood-color="rgba(0,0,0,0.5)"/>
                                        <feComposite in2="offsetblur" operator="in"/>
                                        <feMerge>
                                            <feMergeNode/>
                                            <feMergeNode in="SourceGraphic"/>
                                        </feMerge>
                                    </filter>
                                </g>

                                <g id="symbolsContainer">
                                    <symbol class="icon icon-download" id="icon-1" viewBox="0 0 1000 1000">
                                        <g xmlns="http://www.w3.org/2000/svg" transform="matrix(1 0 0 -1 0 1008)">
                                            <path d="M10,368v157.5L902.5,998l87.5-87.5L517.5,18H360v350H10z"/>
                                        </g>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-2" viewBox="0 0 1000 1000">
                                        <g xmlns="http://www.w3.org/2000/svg" transform="matrix(1 0 0 -1 0 1008)">
                                            <path d="M775.6,722.4L990,508L775.6,293.6l-91.9,15.3v107.2H500L224.4,140.5H10v183.8h137.8L331.6,508L147.8,691.8H10v183.8h214.4L500,599.9h183.8v107.2L775.6,722.4z"
                                            />
                                        </g>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-3" viewBox="0 0 1000 1000">
                                        <g xmlns="http://www.w3.org/2000/svg" transform="matrix(1 0 0 -1 0 1008)">
                                            <path d="M640,228V88h-40.8c-7.8-20.7-20.7-37.6-38.9-50.6C542.1,24.5,522,18,500,18c-22,0-42.1,6.5-60.3,19.4c-18.1,13-31.1,29.8-38.9,50.6H360v140H640z M815,683c0-59.6-15.7-114.9-47.2-165.8S693.8,427.6,640,401.1V298H360v103.1c-53.8,26.6-96.4,65.3-127.8,116.2C200.7,568.1,185,623.4,185,683c0,86.9,30.8,161.1,92.4,222.6C338.9,967.2,413.1,998,500,998c86.9,0,161.1-30.8,222.6-92.4C784.2,844.1,815,769.9,815,683z"
                                            />
                                        </g>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-4" viewBox="0 0 1000 1000">
                                        <g xmlns="http://www.w3.org/2000/svg" transform="matrix(1 0 0 -1 0 1008)">
                                            <path d="M167.5,18l-70,700h805l-70-700H167.5z M710,928h280V788H10v140h280l70,70h280L710,928z"/>
                                        </g>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-view" viewBox="0 0 1000 1000">
                                        <path d="M500,173.7C176.2,173.7,10,500,10,500s125.4,326.3,490,326.3c330.2,0,490-325.1,490-325.1S828.9,173.7,500,173.7z M500.7,703.9c-118.4,0-204-89.2-204-204c0-114.7,85.6-204,204-204c118.4,0,204,89.2,204,204C704.7,614.7,619.1,703.9,500.7,703.9z M500.7,377.6c-67.7,0.1-122.4,56.8-122.4,122.4c0,65.5,54.7,122.4,122.4,122.4c67.7,0,122.4-56.9,122.4-122.4C623.1,434.4,568.4,377.5,500.7,377.6z"></path>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-delete" viewBox="0 0 1000 1000">
                                        <path d="M458.9,10H526c58.5,7.3,114.2,41.7,141.7,94.6c14.2,24.9,18.2,53.8,21.3,81.8c59.5,0.8,119.1-1.4,178.5,0.8c47.1,3.5,73.2,67.1,44,103.5c-11,16.3-30.9,20.8-48.4,26.2c-0.1,161.5,0,323-0.1,484.5c4.8,89.5-61.7,177.8-152.7,188.4l17.4,0.1H302.7c-69.2-7-132.7-54.2-156.8-119.7c-14.3-35.1-11.6-73.9-11.6-111c0.1-147.3,0.1-294.8-0.1-442.1c-16.5-5.5-35.8-9.4-45.9-25.3c-28.6-37-3.5-101.1,44.2-104.7c62.3-2.2,124.7-0.1,187.1-0.8c2.7-29.1,7.2-58.7,22.4-84.1c27.4-51.4,81.8-84.2,138.7-92.2L458.9,10z M442.7,186.3c41,0.1,82.1,0.1,123.1,0c-3.4-32.3-27.5-65.9-62.6-65.3C469,121.9,445.8,154.6,442.7,186.3z M255,323.6c0,161.6-0.1,323.3,0,484.9c-3.1,33.5,25.6,65.2,59.4,64.4c121.4,0.8,242.8,0.1,364.2,0.3c33.4,3.2,67.1-24,66.1-58.6c0.8-163.6,0.1-327.3,0.3-491C581.7,323.6,418.3,323.6,255,323.6z M379.5,439.5c25.6-11.4,67.5,7.1,61.1,39.3c-0.3,74.9,0,149.8-0.1,224.7c2.7,41.4-71.3,52.8-80.5,11.3c-2-80.8-0.3-161.8-0.8-242.6C357.8,458.1,366.6,444.8,379.5,439.5z M569.6,441.3c25.7-15,71,4.1,65.2,37.7c-0.1,72.5-0.3,145.1,0.1,217.6c7.4,38.1-49.6,59.3-73.6,32.6c-9.7-8.3-8.2-21.9-8.7-33.3c0.5-75-0.2-150,0.3-225.1C551.7,458.4,559.1,447.2,569.6,441.3z"></path>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-move" viewBox="0 0 1000 1000">
                                        <g>
                                            <g transform="translate(0.000000,511.000000) scale(0.100000,-0.100000)">
                                                <path d="M6239.2,4988.2c-92-42.9-876.6-694.8-917.5-762.2c-53.1-83.8-49-194.1,8.2-277.9c57.2-85.8,122.6-120.6,235-120.6c108.3,0,151.2,22.5,371.9,192.1l173.7,132.8l6.1-615.1c2.1-337.2,2.1-619.2-2-623.3c-4.1-4.1-304.5-4.1-664.1-2l-658,6.1l145.1,167.6c159.4,188,188,255.4,157.3,365.8c-49,181.9-279.9,261.6-427.1,145.1c-67.4-53.1-700.9-846-727.5-911.4c-53.1-126.7-24.5-181.9,286.1-570.1c159.4-198.2,337.2-408.7,394.4-468c96-102.2,108.3-108.3,200.2-108.3c173.7,0,282,106.3,282,275.9c0,108.3-16.3,138.9-181.9,349.4c-69.5,92-128.7,173.7-128.7,179.8c0,6.1,298.4,12.3,664.1,12.3h664.1V1690c0-525.2-6.1-662.1-24.5-649.8c-14.3,8.2-108.3,77.6-208.4,155.3c-198.2,153.3-239.1,169.6-365.8,153.3c-106.3-14.3-206.4-114.4-226.8-226.8c-26.6-134.9,26.6-198.2,427.1-529.3c535.4-441.4,576.3-463.9,731.6-394.4c100.1,47,919.6,729.5,950.2,794.9c59.3,122.6-42.9,312.7-190,361.7c-89.9,30.7-165.5-2-337.2-145.1c-73.6-61.3-149.2-122.6-167.6-139c-34.7-26.6-36.8,6.1-36.8,629.4v656h602.8c331.1,0,602.8-8.2,602.8-18.4c0-8.2-55.2-89.9-124.6-177.8c-153.3-198.2-181.9-253.4-181.9-339.2c0-198.2,218.7-339.2,396.4-259.5c49,22.5,713.2,813.3,764.2,911.4c28.6,53.1,30.7,149.2,6.1,214.6c-26.6,71.5-688.6,878.7-754,919.6c-169.6,110.3-412.8-28.6-412.8-233c0-79.7,24.5-120.6,188-304.5c65.4-75.6,118.5-141,118.5-149.2c0-6.1-271.8-12.3-602.8-12.3h-602.8V3523v615.1l47-28.6c369.9-239.1,388.3-247.3,474.1-247.3c210.5,0,357.6,216.6,267.7,392.3c-20.4,34.7-210.5,181.9-523.1,398.5C6421.1,5014.8,6359.8,5043.4,6239.2,4988.2z"></path>
                                                <path d="M1410.5,3002c-87.9-38.8-153.3-118.5-165.5-198.2c-6.1-38.8,63.3-860.3,153.3-1826.8c92-966.6,247.3-2617.7,345.3-3672.1c147.1-1546.9,188-1922.9,214.6-1965.8c77.7-116.5,226.8-161.4,347.4-104.2c59.3,28.6,222.7,243.2,905.3,1187.2c455.7,633.5,839.9,1160.7,854.2,1168.9c12.3,8.2,694.8,28.6,1514.2,47c1622.5,34.7,1577.6,30.7,1657.2,151.2c49.1,75.6,59.3,198.2,20.4,271.8c-18.4,34.7-680.5,627.3-1538.7,1379.3C4888.5,163.6,3644,1250.7,2953.3,1855.6c-690.7,604.9-1277.2,1111.6-1299.6,1128C1588.3,3026.5,1480,3034.7,1410.5,3002z"></path>
                                            </g>
                                        </g>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-link" viewBox="0 0 1000 1000">
                                        <path d="M745,255H612.9c45.9,30.6,89,85.2,102.4,122.5H744c62.2,0,122.5,61.3,122.5,122.5S804.3,622.5,744,622.5H560.3c-60.3,0-122.5-61.3-122.5-122.5c0-22,6.7-43.1,17.2-61.3H323.9c-4.8,20.1-7.7,40.2-7.7,61.3c0,122.5,121.5,245,244,245s62.2,0,184.7,0s245-122.5,245-245S867.5,255,745,255z M284.7,622.5H256c-62.2,0-122.5-61.3-122.5-122.5S195.7,377.5,256,377.5h183.8c60.3,0,122.5,61.3,122.5,122.5c0,22-6.7,43.1-17.2,61.3h131.1c4.8-20.1,7.7-40.2,7.7-61.3c0-122.5-121.5-245-244-245s-62.2,0-184.7,0S10,377.5,10,500s122.5,245,245,245h132.1C341.1,714.4,298.1,659.8,284.7,622.5z"></path>
                                    </symbol>

                                    <symbol class="icon icon-download" id="icon-add" viewBox="0 0 1000 1000">
                                        <path d="M990,578V438H570V18H430v420H10v140h420v420h140V578H990z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-setting" viewBox="0 0 1000 1000">
                                        <path d="M957.6,384.5l-75.9,44.2c4.2,23.2,7.1,46.9,7.1,71.3c0,24.4-2.8,48.2-7.1,71.3l75.9,44.2c31,18,41.6,58,23.7,89.2l-64.8,113.2c-17.9,31.2-57.5,41.9-88.5,23.9l-76.6-44.6c-35.8,30.7-76.5,55.9-121.8,72v55.4c0,36.1-29,65.3-64.8,65.3H435.2c-35.8,0-64.8-29.3-64.8-65.3v-55.5c-45.2-16.1-85.9-41.3-121.8-72L172,841.8c-31,18-70.6,7.3-88.5-23.9L18.7,704.7c-17.9-31.3-7.3-71.2,23.7-89.2l75.9-44.2c-4.2-23.2-7.1-46.9-7.1-71.3c0-24.5,2.8-48.2,7.1-71.3l-75.9-44.2c-31-18-41.6-58-23.7-89.2l64.8-113.1c17.9-31.3,57.5-41.9,88.5-23.9l76.6,44.6c35.8-30.7,76.5-55.9,121.8-72V75.3c0-36.1,29-65.3,64.8-65.3h129.6c35.8,0,64.8,29.3,64.8,65.3v55.4c45.2,16.1,85.9,41.3,121.8,72l76.6-44.6c31-18,70.6-7.4,88.5,23.9l64.8,113.2C999.2,326.5,988.6,366.5,957.6,384.5L957.6,384.5z M909,299.6L876.6,243c-9-15.6-28.8-21-44.3-12l-90,52.4c-45.7-52-107.4-89.2-177.5-103.6V108c0-18.1-14.5-32.7-32.4-32.7h-64.8c-17.9,0-32.4,14.6-32.4,32.7v71.9c-70.1,14.4-131.8,51.6-177.5,103.6l-90-52.4c-15.5-9-35.3-3.7-44.3,12L91,299.6c-9,15.6-3.6,35.6,11.9,44.6l90.4,52.6c-10.8,32.5-17.2,67-17.2,103.2s6.4,70.7,17.2,103.1l-90.4,52.6c-15.5,9-20.8,29-11.9,44.6l32.4,56.6c9,15.6,28.7,21,44.3,12l90-52.4c45.7,52,107.4,89.2,177.5,103.6V892c0,18,14.5,32.7,32.4,32.7h64.8c17.9,0,32.4-14.6,32.4-32.7v-71.9c70.1-14.3,131.8-51.6,177.5-103.6l90,52.4c15.5,9,35.3,3.7,44.3-12l32.4-56.6c9-15.6,3.6-35.6-11.9-44.6l-90.4-52.6c10.8-32.5,17.2-67,17.2-103.1c0-36.2-6.4-70.7-17.2-103.2l90.4-52.6C912.6,335.2,918,315.3,909,299.6z M500,663.3c-89.5,0-162-73.1-162-163.3s72.5-163.3,162-163.3S662,409.8,662,500C662,590.2,589.5,663.3,500,663.3z M500,402c-53.7,0-97.2,43.9-97.2,98s43.5,98,97.2,98s97.2-43.9,97.2-98S553.7,402,500,402z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-pre" viewBox="0 0 1000 1000">
                                        <path d="M10,508l455,490l105-105L272.5,578H990V438H272.5L570,123L465,18L10,508z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-next" viewBox="0 0 1000 1000">
                                        <path d="M535,998l455-490L535,18L430,123l297.5,315H10v140h717.5L430,893L535,998z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-pre-2" viewBox="0 0 1000 1000">
                                        <path d="M675,18L220,508l455,490l105-105L412.5,508L780,123L675,18z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-next-2" viewBox="0 0 1000 1000">
                                        <path d="M780,508L325,18L220,123l367.5,385L220,893l105,105L780,508z"></path>
                                    </symbol>
                                    <symbol class="icon icon-download" id="icon-delete-2" viewBox="0 0 1000 1000">
                                        <path d="M745,648L605,508l140-140L640,263L500,403L360,263L255,368l140,140L255,648l105,105l140-140l140,140L745,648z M500,858c-96.6,0-179.1-34.2-247.4-102.6C184.2,687.1,150,604.6,150,508s34.2-179.1,102.6-247.4C320.9,192.2,403.4,158,500,158s179.1,34.2,247.4,102.6C815.8,328.9,850,411.4,850,508s-34.2,179.1-102.6,247.4C679.1,823.8,596.6,858,500,858z M500,998c134.8,0,250.2-48,346.1-143.9C942,758.2,990,642.8,990,508c0-134.8-48-250.2-143.9-346.1S634.8,18,500,18c-134.8,0-250.2,48-346.1,143.9C58,257.8,10,373.2,10,508c0,134.8,48,250.2,143.9,346.1C249.8,950,365.2,998,500,998z"></path>                                </symbol>
                                    <defs>
                                        <marker id="markerArrow1" markerWidth="10" markerHeight="10" refx="15" refy="3"
                                                orient="auto" markerUnits="strokeWidth">
                                            <path d="M0,0 L0,6 L9,3 z" class="finished-path-marker"/>
                                        </marker>

                                    </defs>
                                    <defs>
                                        <marker id="markerArrow2" markerWidth="10" markerHeight="10" refx="9" refy="3"
                                                orient="auto" markerUnits="strokeWidth">
                                            <path d="M0,0 L0,6 L9,3 z" class="unfinished-path-marker"/>
                                        </marker>
                                    </defs>
                                    <defs>
                                        <marker id="markerArrow3" markerWidth="10" markerHeight="10" refx="10" refy="5"
                                                orient="auto" markerUnits="strokeWidth">
                                            <line x1="0" y1="0" x2="10" y2="10" style="stroke:rgb(255,0,0);stroke-width:2"/>
                                            <line x1="0" y1="10" x2="10" y2="0" style="stroke:rgb(255,0,0);stroke-width:2"/>
                                        </marker>
                                    </defs>
                                </g>

                                <g id="stepsContainer" class="svg-container">

                                </g>
                                <g id="edgesContainer" class="svg-container">

                                </g>
                                <g id="nodesContainer" class="svg-container">
                                </g>
                                <g id="toolContainer" class="svg-container">

                                </g>
                                <g id="tipsContainer" class="svg-container">

                                </g>

                            </svg>
                        </div>

                        <div id="stepInfoRow" class="row" style="display:none">
                            <div class="col-sm-12 widget-container-col ui-sortable" >
                                <div class="widget-box transparent ui-sortable-handle">
                                    <div class="widget-header widget-header-small">
                                        <h3 class="widget-title smaller">
                                            <a id="cur-step-name-href" ></a>
                                            <small><a id="cur-step-description-href"></a></small>
                                        </h3>


                                    </div>

                                    <div class="widget-body">
                                        <div class="widget-main padding-6">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div id="cur-step-static" class="row">
                                                        <div class="col-xs-6 col-sm-6">
                                                            <div id="cur-step-result-static"  style="width: 100%;height:300px;"></div>

                                                        </div>

                                                        <div class="col-xs-6 col-sm-6">
                                                            <div id="cur-step-activity-static" style="width: 100%;height:300px;"></div>

                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="col-xs-12 col-sm-12 widget-box widget-color-blue ui-sortable-handle">
                                                    <div class="widget-header widget-header-flat">
                                                        <h3 class="widget-title lighter">阶段总结</h3>
                                                        <div class="widget-toolbar">

                                                            <div class="wysiwyg-toolbar btn-toolbar center wysiwyg-style2">
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-grey" href="#"
                                                                       data-action="fullscreen">
                                                                        <i class="ace-icon fa fa-expand"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-grey" href="#"
                                                                       data-action="collapse">
                                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                                    </a>

                                                                </div>
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-grey" onclick="saveStepSummary()" >
                                                                        <i class="ace-icon fa fa-floppy-o"></i>
                                                                        <span>保存&nbsp;</span>
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="widget-toolbar hidden-480">

                                                            <div class="wysiwyg-toolbar btn-toolbar center wysiwyg-style2">
                                                                <!-- 标题 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-default dropdown-toggle"
                                                                       data-toggle="dropdown"
                                                                       title="" data-original-title="Font"
                                                                       aria-expanded="false">
                                                                        <i class=" ace-icon fa fa-header"></i>
                                                                        <i class=" ace-icon fa fa-angle-down icon-on-right"></i>
                                                                    </a>
                                                                    <ul class="dropdown-menu dropdown-light dropdown-caret">
                                                                        <li>
                                                                            <a data-edit="formatBlock H2"
                                                                               style="font-size:32px">标题一</a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="formatBlock H3"
                                                                               style="font-size:22px">标题二</a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="formatBlock H4"
                                                                               style="font-size:16px">标题三</a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="formatBlock H5"
                                                                               style="font-size:12px">标题四</a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <!-- 字体 -->
                                                                <div class="btn-group">
                                                                    <a class="btn dropdown-toggle" data-toggle="dropdown"
                                                                       title=""
                                                                       data-original-title="Font Size">
                                                                        <i class=" ace-icon fa fa-text-height"></i>&nbsp;
                                                                        <i class=" ace-icon fa fa-angle-down icon-on-right"></i>
                                                                    </a>
                                                                    <ul class="dropdown-menu dropdown-light dropdown-caret">
                                                                        <li>
                                                                            <a data-edit="fontSize 1">
                                                                                <font size="1">8</font>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="fontSize 2">
                                                                                <font size="2">10</font>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="fontSize 3">
                                                                                <font size="3">14</font>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="fontSize 4">
                                                                                <font size="4">18</font>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="fontSize 5">
                                                                                <font size="5">24</font>
                                                                            </a>
                                                                        </li>
                                                                        <li>
                                                                            <a data-edit="fontSize 6">
                                                                                <font size="6">36</font>
                                                                            </a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <!-- 加粗 斜体 删除线 下划线 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-info" data-edit="bold" title=""
                                                                       data-original-title="Bold (Ctrl/Cmd+B)">
                                                                        <i class=" ace-icon fa fa-bold"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-info" data-edit="italic" title=""
                                                                       data-original-title="Italic (Ctrl/Cmd+I)">
                                                                        <i class=" ace-icon fa fa-italic"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-info" data-edit="strikethrough"
                                                                       title=""
                                                                       data-original-title="Strikethrough">
                                                                        <i class=" ace-icon fa fa-strikethrough"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-info" data-edit="underline"
                                                                       title=""
                                                                       data-original-title="Underline">
                                                                        <i class=" ace-icon fa fa-underline"></i>
                                                                    </a>
                                                                </div>
                                                                <!-- 编号 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-success active"
                                                                       data-edit="insertunorderedlist"
                                                                       title="" data-original-title="Bullet list">
                                                                        <i class=" ace-icon fa fa-list-ul"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-success"
                                                                       data-edit="insertorderedlist" title=""
                                                                       data-original-title="Number list">
                                                                        <i class=" ace-icon fa fa-list-ol"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-purple" data-edit="outdent"
                                                                       title=""
                                                                       data-original-title="Reduce indent (Shift+Tab)">
                                                                        <i class=" ace-icon fa fa-outdent"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-purple" data-edit="indent" title=""
                                                                       data-original-title="Indent (Tab)">
                                                                        <i class=" ace-icon fa fa-indent"></i>
                                                                    </a>
                                                                </div>
                                                                <!-- 对齐 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-primary active"
                                                                       data-edit="justifyleft"
                                                                       title="" data-original-title="Align Left (Ctrl/Cmd+L)">
                                                                        <i class=" ace-icon fa fa-align-left"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-primary" data-edit="justifycenter"
                                                                       title=""
                                                                       data-original-title="Center (Ctrl/Cmd+E)">
                                                                        <i class=" ace-icon fa fa-align-center"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-primary" data-edit="justifyright"
                                                                       title=""
                                                                       data-original-title="Align Right (Ctrl/Cmd+R)">
                                                                        <i class=" ace-icon fa fa-align-right"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-inverse" data-edit="justifyfull"
                                                                       title=""
                                                                       data-original-title="Justify (Ctrl/Cmd+J)">
                                                                        <i class=" ace-icon fa fa-align-justify"></i>
                                                                    </a>
                                                                </div>
                                                                <!--  图片 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-success dropdown-toggle"
                                                                       data-toggle="dropdown"
                                                                       title="" data-original-title="Insert picture">
                                                                        <i class=" ace-icon fa fa-picture-o"></i>
                                                                    </a>
                                                                    <div class="dropdown-menu dropdown-caret dropdown-menu-right">
                                                                        <div class="input-group">
                                                                            <input class="form-control" placeholder="Image URL"
                                                                                   type="text"
                                                                                   data-editimage="insertImage">
                                                                            <span class="input-group-btn">
														<button class="btn btn-sm btn-primary"
                                                                type="button">Insert</button></span>
                                                                        </div>
                                                                        <div class="space-2"></div>
                                                                        <label class="center block no-margin-bottom">
                                                                            <button class="btn btn-sm btn-success" type="button"
                                                                                    id="Wyimage">
                                                                                <i class=" ace-icon fa fa-file"></i>Choose Image
                                                                                …
                                                                            </button>
                                                                            <input type="file" data-editimage="insertImage"
                                                                                   accept="image/*">
                                                                        </label>
                                                                    </div>
                                                                </div>
                                                                <!--  图片大小 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-grey" title="" onclick="imgPlus()">
                                                                        <i class=" ace-icon fa fa-plus"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-grey" title=""
                                                                       onclick="imgMinus()">
                                                                        <i class=" ace-icon fa fa-minus"></i>
                                                                    </a>
                                                                </div>
                                                                <!--  撤销 -->
                                                                <div class="btn-group">
                                                                    <a class="btn btn-sm btn-grey" data-edit="undo" title=""
                                                                       data-original-title="Undo">
                                                                        <i class=" ace-icon fa fa-undo"></i>
                                                                    </a>
                                                                    <a class="btn btn-sm btn-grey" data-edit="redo" title=""
                                                                       data-original-title="Redo">
                                                                        <i class=" ace-icon fa fa-repeat"></i>
                                                                    </a>

                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="widget-body">
                                                        <div class="widget-main">
                                                            <div class="tab-pane active" >
                                                                <!--word编辑区-->
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <div class="wysiwyg-editor" id="cur-step-summary"
                                                                             contenteditable="true"
                                                                             style="height: 600px;">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <!-- /.widget-box -->
                                                        </div>
                                                    </div>
                                                    <!-- /.col -->
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="nodeInfoRow" class="row" style="display:none">
                            <div id="user-profile-2" class="user-profile">
                                <div class="tabbable">
                                    <ul class="nav nav-tabs padding-18">
                                        <li class="active">
                                            <a data-toggle="tab" href="#tool-tab" aria-expanded="true"
                                               onclick="javascript:CUR_NODE.templateProjectID=0">
                                                <i class="green ace-icon fa fa-user bigger-120"></i>
                                                创新方法工具
                                            </a>
                                        </li>

                                        <li class="">
                                            <a data-toggle="tab" href="#template-tab" aria-expanded="false" onclick="swapTemplate()">
                                                <i class="orange ace-icon fa fa-rss bigger-120"></i>
                                                模板
                                            </a>
                                        </li>




                                    </ul>

                                    <div class="tab-content no-border padding-24">
                                        <div id="tool-tab" class="tab-pane active">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-2 center">
															<span class="profile-picture">
																<img id="cur-node-app-img"class="editable img-responsive" width="100" height="100" alt="创新方法APP" id="avatar2" src="/templates/project/assets/img/app.png">
															</span>

                                                    <p>
                                                        <span id="app-name" class="label label-lg arrowed-in arrowed-in-right">无</span>
                                                    </p>
                                                </div><!-- /.col -->


                                                <div class="col-xs-12 col-sm-8">
                                                    <h4 class="blue">
                                                        <span class="middle" id="cur-node-name-href"></span>

                                                        <span class="label label-purple arrowed-in-right">
																	<i class="ace-icon fa fa-circle smaller-80 align-middle"></i>
																	online
																</span>
                                                    </h4>

                                                    <div class="profile-user-info">
                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name">描述</div>

                                                            <div class="profile-info-value" >
                                                                <span id="cur-node-description-href">alexdoe</span>
                                                            </div>
                                                        </div>

                                                        <div class="profile-info-row">
                                                            <div class="profile-info-name">目标</div>

                                                            <div class="profile-info-value">
                                                                <span id="cur-node-goal-href" >alexdoe</span>
                                                            </div>
                                                        </div>




                                                    </div>

                                                </div><!-- /.col -->
                                                <div class="col-xs-12 col-sm-2">
                                                    <a  type="button" class="btn btn-sm btn-white btn-block btn-success" data-toggle="modal" data-target="#store-modal">
                                                        <i class="ace-icon fa fa-plus-circle" ></i>
                                                        <span >应用商店</span>
                                                    </a>
                                                    <a href="#" type="button" class="btn btn-sm btn-white btn-block btn-primary" id="app-result-btn"  data-toggle="modal" data-target="#tool-result-modal">
                                                        <i class="ace-icon fa fa-info-circle" ></i>
                                                        <span >查看数据</span>
                                                    </a>
                                                    <a onclick="javascript:window.open('/templates/project/member.html?projectID='+PROJECT_ID);" type="button" class="btn btn-sm btn-white btn-block btn-purple">
                                                        <i class="ace-icon fa fa-user "></i>
                                                        <span >成员管理</span>
                                                    </a>
                                                    <a href="#" type="button" class="btn btn-sm btn-white btn-block btn-grey" id="cur-node-lock-btn" onclick="lockNode()">
                                                        <i class="ace-icon fa fa-envelope-o"></i>
                                                        <span >锁定节点</span>
                                                    </a>
                                                </div>
                                            </div><!-- /.row -->

                                            <div class="space-20"></div>

                                            <div class="row">

                                                <div class="col-xs-12 col-sm-12">
                                                    <div class="widget-box transparent">
                                                        <div class="widget-header widget-header-small header-color-blue2">
                                                            <h4 class="widget-title smaller">
                                                                <i class="ace-icon fa fa-lightbulb-o bigger-120"></i>
                                                                数据管理
                                                            </h4>
                                                        </div>

                                                        <div class="widget-body">
                                                            <div id="cur-node-result-info" class="alert alert-warning">
                                                                <button type="button" class="close" data-dismiss="alert">
                                                                    <i class="ace-icon fa fa-times"></i>
                                                                </button>
                                                                <strong>注意</strong>
                                                                该节点还没有绑定APP,请绑定APP后在查看相关数据
                                                                <br>
                                                            </div>
                                                            <div class="widget-main padding-16">
                                                                <div class="clearfix">
                                                                    <div class="grid3 center">
                                                                        <div class="easy-pie-chart percentage" data-percent="45" data-color="#CA5952" style="height: 72px; width: 72px; line-height: 71px; color: rgb(202, 89, 82);">
                                                                            <span class="percent" id="cur-node-result-cnt">45</span>
                                                                            <canvas height="72" width="72" ></canvas></div>
                                                                        <div class="space-2"></div>
                                                                        成员数
                                                                    </div>

                                                                    <div class="grid3 center">
                                                                        <div class="center easy-pie-chart percentage" data-percent="90" data-color="#59A84B" style="height: 72px; width: 72px; line-height: 71px; color: rgb(89, 168, 75);">
                                                                            <span class="percent" id="cur-node-finished-cnt">90</span>%
                                                                            <canvas height="72" width="72"></canvas></div>

                                                                        <div class="space-2"></div>
                                                                        完成数
                                                                    </div>

                                                                    <div class="grid3 center">
                                                                        <div class="center easy-pie-chart percentage" data-percent="80" data-color="#9585BF" style="height: 72px; width: 72px; line-height: 71px; color: rgb(149, 133, 191);">
                                                                            <span class="percent" id="cur-node-pending-cnt">80</span>%
                                                                            <canvas height="72" width="72"></canvas></div>

                                                                        <div class="space-2"></div>
                                                                        任务比
                                                                    </div>
                                                                </div>
                                                                <div class="hr hr-16"></div>
                                                                <table id="cur-node-result-table"></table>

                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-xs-12 col-sm-12">
                                                    <div id="cur-node-summary-widget"
                                                         class="col-xs-12 col-sm-12 widget-box widget-color-blue ui-sortable-handle">
                                                        <div class="widget-header widget-header-flat">
                                                            <h3 class="widget-title lighter">阶段总结</h3>
                                                            <div class="widget-toolbar">

                                                                <div class="wysiwyg-toolbar btn-toolbar center wysiwyg-style2">
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-grey" href="#"
                                                                           data-action="fullscreen">
                                                                            <i class="ace-icon fa fa-expand"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-grey" href="#"
                                                                           data-action="collapse">
                                                                            <i class="ace-icon fa fa-chevron-up"></i>
                                                                        </a>

                                                                    </div>
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-grey" onclick="saveNodeSummary()" >
                                                                            <i class="ace-icon fa fa-floppy-o"></i>
                                                                            <span>保存&nbsp;</span>
                                                                        </a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="widget-toolbar hidden-480">

                                                                <div class="wysiwyg-toolbar btn-toolbar center wysiwyg-style2">
                                                                    <!-- 标题 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-default dropdown-toggle"
                                                                           data-toggle="dropdown"
                                                                           title="" data-original-title="Font"
                                                                           aria-expanded="false">
                                                                            <i class=" ace-icon fa fa-header"></i>
                                                                            <i class=" ace-icon fa fa-angle-down icon-on-right"></i>
                                                                        </a>
                                                                        <ul class="dropdown-menu dropdown-light dropdown-caret">
                                                                            <li>
                                                                                <a data-edit="formatBlock H2"
                                                                                   style="font-size:32px">标题一</a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="formatBlock H3"
                                                                                   style="font-size:22px">标题二</a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="formatBlock H4"
                                                                                   style="font-size:16px">标题三</a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="formatBlock H5"
                                                                                   style="font-size:12px">标题四</a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                    <!-- 字体 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn dropdown-toggle" data-toggle="dropdown"
                                                                           title=""
                                                                           data-original-title="Font Size">
                                                                            <i class=" ace-icon fa fa-text-height"></i>&nbsp;
                                                                            <i class=" ace-icon fa fa-angle-down icon-on-right"></i>
                                                                        </a>
                                                                        <ul class="dropdown-menu dropdown-light dropdown-caret">
                                                                            <li>
                                                                                <a data-edit="fontSize 1">
                                                                                    <font size="1">8</font>
                                                                                </a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="fontSize 2">
                                                                                    <font size="2">10</font>
                                                                                </a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="fontSize 3">
                                                                                    <font size="3">14</font>
                                                                                </a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="fontSize 4">
                                                                                    <font size="4">18</font>
                                                                                </a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="fontSize 5">
                                                                                    <font size="5">24</font>
                                                                                </a>
                                                                            </li>
                                                                            <li>
                                                                                <a data-edit="fontSize 6">
                                                                                    <font size="6">36</font>
                                                                                </a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                    <!-- 加粗 斜体 删除线 下划线 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-info" data-edit="bold" title=""
                                                                           data-original-title="Bold (Ctrl/Cmd+B)">
                                                                            <i class=" ace-icon fa fa-bold"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-info" data-edit="italic" title=""
                                                                           data-original-title="Italic (Ctrl/Cmd+I)">
                                                                            <i class=" ace-icon fa fa-italic"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-info" data-edit="strikethrough"
                                                                           title=""
                                                                           data-original-title="Strikethrough">
                                                                            <i class=" ace-icon fa fa-strikethrough"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-info" data-edit="underline"
                                                                           title=""
                                                                           data-original-title="Underline">
                                                                            <i class=" ace-icon fa fa-underline"></i>
                                                                        </a>
                                                                    </div>
                                                                    <!-- 编号 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-success active"
                                                                           data-edit="insertunorderedlist"
                                                                           title="" data-original-title="Bullet list">
                                                                            <i class=" ace-icon fa fa-list-ul"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-success"
                                                                           data-edit="insertorderedlist" title=""
                                                                           data-original-title="Number list">
                                                                            <i class=" ace-icon fa fa-list-ol"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-purple" data-edit="outdent"
                                                                           title=""
                                                                           data-original-title="Reduce indent (Shift+Tab)">
                                                                            <i class=" ace-icon fa fa-outdent"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-purple" data-edit="indent" title=""
                                                                           data-original-title="Indent (Tab)">
                                                                            <i class=" ace-icon fa fa-indent"></i>
                                                                        </a>
                                                                    </div>
                                                                    <!-- 对齐 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-primary active"
                                                                           data-edit="justifyleft"
                                                                           title="" data-original-title="Align Left (Ctrl/Cmd+L)">
                                                                            <i class=" ace-icon fa fa-align-left"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-primary" data-edit="justifycenter"
                                                                           title=""
                                                                           data-original-title="Center (Ctrl/Cmd+E)">
                                                                            <i class=" ace-icon fa fa-align-center"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-primary" data-edit="justifyright"
                                                                           title=""
                                                                           data-original-title="Align Right (Ctrl/Cmd+R)">
                                                                            <i class=" ace-icon fa fa-align-right"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-inverse" data-edit="justifyfull"
                                                                           title=""
                                                                           data-original-title="Justify (Ctrl/Cmd+J)">
                                                                            <i class=" ace-icon fa fa-align-justify"></i>
                                                                        </a>
                                                                    </div>
                                                                    <!--  图片 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-success dropdown-toggle"
                                                                           data-toggle="dropdown"
                                                                           title="" data-original-title="Insert picture">
                                                                            <i class=" ace-icon fa fa-picture-o"></i>
                                                                        </a>
                                                                        <div class="dropdown-menu dropdown-caret dropdown-menu-right">
                                                                            <div class="input-group">
                                                                                <input class="form-control" placeholder="Image URL"
                                                                                       type="text"
                                                                                       data-editimage="insertImage">
                                                                                <span class="input-group-btn">
														<button class="btn btn-sm btn-primary"
                                                                type="button">Insert</button></span>
                                                                            </div>
                                                                            <div class="space-2"></div>
                                                                            <label class="center block no-margin-bottom">
                                                                                <button class="btn btn-sm btn-success" type="button"
                                                                                        id="Wyimage">
                                                                                    <i class=" ace-icon fa fa-file"></i>Choose Image
                                                                                    …
                                                                                </button>
                                                                                <input type="file" data-editimage="insertImage"
                                                                                       accept="image/*">
                                                                            </label>
                                                                        </div>
                                                                    </div>
                                                                    <!--  图片大小 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-grey" title="" onclick="imgPlus()">
                                                                            <i class=" ace-icon fa fa-plus"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-grey" title=""
                                                                           onclick="imgMinus()">
                                                                            <i class=" ace-icon fa fa-minus"></i>
                                                                        </a>
                                                                    </div>
                                                                    <!--  撤销 -->
                                                                    <div class="btn-group">
                                                                        <a class="btn btn-sm btn-grey" data-edit="undo" title=""
                                                                           data-original-title="Undo">
                                                                            <i class=" ace-icon fa fa-undo"></i>
                                                                        </a>
                                                                        <a class="btn btn-sm btn-grey" data-edit="redo" title=""
                                                                           data-original-title="Redo">
                                                                            <i class=" ace-icon fa fa-repeat"></i>
                                                                        </a>

                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="widget-toolbar">

                                                                <div class="wysiwyg-toolbar btn-toolbar center wysiwyg-style2 ">
                                                                    <label class="pull-right inline">
                                                                        <small class="muted smaller-90">是否完成</small>
                                                                        <input id="state-change-btn" checked="checked"
                                                                               type="checkbox" class="ace ace-switch ace-switch-5">
                                                                        <span class="lbl middle"></span>
                                                                    </label>
                                                                </div>

                                                            </div>

                                                        </div>
                                                        <div class="widget-body">
                                                            <div class="widget-main">
                                                                <div class="tab-pane active" id="wordEditDiv">
                                                                    <!--word编辑区-->
                                                                    <div class="row">
                                                                        <div class="col-xs-12">
                                                                            <div class="wysiwyg-editor" id="cur-node-summary"
                                                                                 contenteditable="true"
                                                                                 style="height: 600px;">
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <!-- /.widget-box -->
                                                            </div>
                                                        </div>
                                                        <!-- /.col -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div><!-- /#home -->

                                        <div id="template-tab" class="tab-pane">
                                            <div class="col-md-offset-3 col-md-6">
                                                <div>
                                                    <label for="cur-node-temp-select">链接现有模板</label>
                                                    <select class="form-control" id="cur-node-temp-select">
                                                    </select>
                                                    <div class="space-4"></div>
                                                    <div class="col-md-offset-3 col-md-9">
                                                        <button class="btn btn-info" type="button"
                                                                onclick="javascript:window.open('/templates/manager.html?projectID='+$('#cur-node-temp-select').val(),'_blank')">
                                                            进入当前模板
                                                        </button>
                                                        <button class="btn btn-success" type="button"
                                                                onclick="bangdingTemplate()">
                                                            链接模板
                                                        </button>
                                                        <button class="btn btn-primary" type="button"
                                                                onclick="javascript:window.open('/templates/createProject.html','_blank',)">
                                                            新建模板
                                                        </button>
                                                    </div>
                                                </div>
                                            </div><!-- /input-group -->

                                        </div><!-- /#feed -->

                                    </div>
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

    <!-- 绑定 modal start -->
    <div class="modal fade bs-example-modal-lg" id="tool-result-modal" tabindex="-1" role="dialog"
         aria-labelledby="tool-result-label"
         aria-hidden="true">
        <div class="modal-dialog  modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="tool-result-modal-label">绑定数据</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <table id="tool-project-table" data-toggle="table">
                            <thead>
                            <tr>
                                <th data-field="projectName">项目名</th>
                                <th data-field="createTime" data-formatter="timeFormatter">创建时间</th>
                                <th data-field="editTime" data-formatter="timeFormatter">创建时间</th>
                                <th data-field="memo">备注</th>
                                <th data-field="op" data-formatter="toolProjectPreView">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
    <div class="modal fade bs-example-modal-lg " id="tool-result-view-modal" tabindex="-1" role="dialog"
         aria-labelledby="tool-result-view-label"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="tool-result-view-modal-label">结果预览</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div id="preViewRow" class="col-xs-10"></div>
                    </div>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
    <div class="modal fade bs-example-modal-lg " id="cur-node-info-view-modal" tabindex="-1" role="dialog"
         aria-labelledby="tool-result-view-label"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="node-info-view-modal-label">结果预览</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="node-name">节点名称</label>
                                <div class="col-sm-9">
                                    <input type="text" id="node-name" name="name" required=""
                                           class="col-xs-10 col-sm-5">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right"
                                       for="node-description">节点描述</label>
                                <div class="col-sm-9">
                                        <textarea name="description" id="node-description"
                                                  class="autosize-transition form-control"
                                                  style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 52px;"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label no-padding-right" for="node-goal">节点目标</label>
                                <div class="col-sm-9">
                                        <textarea name="goal" id="node-goal" class="autosize-transition form-control"
                                                  style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 52px;"></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" onclick="saveCurStepInfo()" class="btn btn-primary">保存
                    </button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>

    <!-- 应用商店模态框（Modal） -->
    <div class="modal fade" id="store-modal" tabindex="-1" role="dialog"
         aria-labelledby="store-modal-label"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="store-modal-label">创新方法应用商店</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <div>
                                <div class="row search-page" id="search-page-1">
                                    <div class="col-xs-12">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-3">
                                                <div class="search-area well well-sm">

                                                    <div class="hr hr-dotted"></div>

                                                    <h4 class="blue smaller">
                                                        <i class="fa fa-tags"></i>
                                                        类别
                                                    </h4>

                                                    <div class="tree-container ace-scroll"
                                                         style="position: relative;">
                                                        <div class="scroll-track"
                                                             style="display: none;">
                                                            <div class="scroll-bar"></div>
                                                        </div>
                                                        <div class="scroll-content" style="">
                                                            <ul id="cat-tree"
                                                                class="tree tree-unselectable"
                                                                role="tree">
                                                                <li class="tree-branch hide"
                                                                    data-template="treebranch"
                                                                    role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label"></span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="tree-item hide"
                                                                    data-template="treeitem"
                                                                    role="treeitem">
																						<span class="tree-item-name">
																							<i class="icon-item ace-icon fa fa-times"></i>
																							<span class="tree-label"></span>
																						</span>
                                                                </li>
                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">知识工程</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">TRIZ</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>

                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">质量分析</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>

                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">企业管理</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>

                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">产品设计</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <li class="tree-branch" role="treeitem"
                                                                    aria-expanded="false">
                                                                    <div class="tree-branch-header">
																							<span class="tree-branch-name">
																								<i class="icon-folder ace-icon tree-plus"></i>
																								<span class="tree-label">创新思维</span>
																							</span>
                                                                    </div>
                                                                    <ul class="tree-branch-children"
                                                                        role="group"></ul>
                                                                    <div class="tree-loader hidden"
                                                                         role="alert">
                                                                        <div class="tree-loading">
                                                                            <i class="ace-icon fa fa-refresh fa-spin blue"></i>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>

                                                    <div class="hr hr-dotted"></div>
                                                </div>
                                            </div>

                                            <div class="col-xs-12 col-sm-9">
                                                <div class="row">
                                                    <table
                                                            id="app-store-table"
                                                            data-height="600"
                                                            data-pagination="true"
                                                            data-pagination-loop="true"
                                                            data-search="true"
                                                            data-side-pagination="client"
                                                            data-page-number="1"
                                                            data-page-size="10"
                                                            data-toggle="table"
                                                            data-smart-display="true"
                                                            data-url="/templates/project/json/data.json"
                                                    >
                                                        <thead>
                                                        <tr>
                                                            <th data-field="row"
                                                                data-formatter="rowFormatter">结果
                                                            </th>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- PAGE CONTENT ENDS -->
                        </div>
                        <!-- /.col -->
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="modalCloseBtn" type="button" class="btn btn-default"
                            data-dismiss="modal">关闭
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>

    <!-- #dialog-message -->
    <!-- 关联模板模态框 modal end -->
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

    <script src="/templates/project/assets/js/work.js"></script>
    <script src="/templates/project/assets/js/appStore.js"></script>
    <script src="/templates/project/assets/js/step.js"></script>
    <script src="/templates/project/assets/js/node.js"></script>
    <script src="/templates/project/assets/js/result.js"></script>
    <#--这里需要引用echarts的3.x版本，因为在绘制甘特图的时候，3.x的版本可以支持时间的堆叠，而4.x版本不支持。-->
    <script src="/templates/project/assets/js/echarts.js"></script>
    <#--引入日期处理插件-->
    <script src="/templates/project/assets/js/moment-with-locales.min.js"></script>
    <script>
        $(function () {
            $('.modal.aside').ace_aside();
            //显示所有提示
            $('[data-rel=tooltip]').tooltip({
                container: 'body'
            });
        });

    </script>

</div>
</div>
</body>

</html>