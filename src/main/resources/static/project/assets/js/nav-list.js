$(document).ready(function(){
    //首先获取用户当前的LCUE项目
    // $.ajax({
    //     url:"",
    //     type:"",
    //     data:"",
    //     success:function(data){

    //     }
    // })
    //然后获取用户当前的不包含在LCUE中的所有模板项目

    $("#sideList").append('<li class="open">\n' +
        '\t\t\t\t\t\t<a href="#" class="dropdown-toggle">\n' +
        '\t\t\t\t\t\t\t<i class="menu-icon fa fa-list"></i>\n' +
        '<span class="menu-text">我创建的模板</span>' +
        '<b class="arrow fa fa-angle-down"></b>\</a><b class="arrow"></b>' +
        '<ul class="submenu nav-show" id="my-project-list"style="display: block;"></ul></li>')
        .append('<li class="open">\n' +
            '\t\t\t\t\t\t<a href="#" class="dropdown-toggle">\n' +
            '\t\t\t\t\t\t\t<i class="menu-icon fa fa-list"></i>\n' +
            '<span class="menu-text">我加入的模板</span>' +
            '<b class="arrow fa fa-angle-down"></b>\</a><b class="arrow"></b>' +
            '<ul class="submenu nav-show" id="joined-project-list"style="display: block;"></ul></li>')

    $.ajax({
        url:"/templates/api/project/owned",
        type:"GET",
        success:function(data){
            if(data.length>0){
                data.forEach(project=>{
                    let temp = {
                        'text':project.name,
                        'href':'/templates/project/project.html?projectID='+project.id
                    }
                    createEl(temp,$("#my-project-list"));
                })
            }


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("XMLHttpRequest请求状态码：" + XMLHttpRequest.status);
            console.log("XMLHttpRequest状态码：" + XMLHttpRequest.readyState);
            console.log("textStatus是：" + textStatus);
            console.log("errorThrown是：" + errorThrown);
        }
    })
    $.ajax({
        url:"/templates/api/project/joined",
        type:"GET",
        success:function(data){
            if(data.length>0) {
                data.forEach(project => {
                    let temp = {
                        'text': project.name,
                        'href': '/templates/project/project.html?projectID=' + project.id
                    }
                    createEl(temp, $("#joined-project-list"));
                })
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("XMLHttpRequest请求状态码：" + XMLHttpRequest.status);
            console.log("XMLHttpRequest状态码：" + XMLHttpRequest.readyState);
            console.log("textStatus是：" + textStatus);
            console.log("errorThrown是：" + errorThrown);
        }
    })


})
/**
 * 新建节点
 */
function createEl(element,$ul){
        //如果submenu有数据的话
        let $li = $("<li></li>");
        let $i = $("<i></i>").addClass("menu-icon fa fa-caret-right");
        let $a = $("<a></a>").attr("href",element.href);
        $li.append($a.append($i)).append('<b class="arrow"></b>');
        if(element.isLCUE){
            $a.append($("<b></b>").text(element.text));
        }else{
            $a.append(document.createTextNode(element.text));
        }
        if(element.subMenu&&element.subMenu.length>0){
            $a.addClass("dropdown-toggle").append('<b class="arrow fa fa-angle-down"></b>');
            //如果当前元素还有下一级菜单
            let $ul = $('<ul class="submenu nav-hide" style="display: none;"></ul>');
            element.subMenu.forEach(subElement=>{
                    let $li = $("<li></li>");
                    let $a = $("<a></a>").attr("href",subElement.href);
                    let $i = $("<i></i>").addClass("menu-icon fa fa-caret-right");
                    $li.append($a.append($i).append(document.createTextNode(subElement.text))).append('<b class="arrow"></b>');
                    $ul.append($li);
            })
            $li.append($ul);
        }
        $ul.append($li);
}

//
// var LIST_SIDE_JSON_ARR = [
//
//     {
//         text:"我的模板",//导航名
//         href:"/CAI-templates/index.html",//链接
//         class:"dropdown-toggle ",
//         icon:"menu-icon fa fa-list",//图标
//         subMenu:[
//             {
//                 text:"我创建的模板",//导航名
//                 href:"",
//                 isLCUE:true,//代表是LCUE
//                 subMenu:[
//                     // {
//                     //     text:"我的模板1",
//                     //     href:"",
//                     // },
//                     // {
//                     //     text:"我的模板2",
//                     //     href:"",
//                     // }
//                 ],
//             },
//             {
//                 text:"我加入的模板",
//                 href:"",
//                 isLCUE:true,//代表是LCUE
//                 subMenu:[
//
//                 ],
//             },
//
//         ]
//     },
// ]


