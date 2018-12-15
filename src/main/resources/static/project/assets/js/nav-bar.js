function initNavbarView() {
    USERNAME = getCookie('cai_username');
    let userDom;
    if(USERNAME==null||USERNAME==undefined||USERNAME.trim().length==0){
        isLogin = false;
        userDom = [{
            tag: 'li',
            children: [{
                tag: 'a',
                attr: {href: '/webresources/userLogin.jsp'},
                children: [{tag: 'i', attr: {class: 'ace-icon fa fa-key'}}],
                innerText: '登录'
            }]
        },{
            tag: 'li',
            children: [{
                tag: 'a',
                attr: {href: '/webresources/userRegister.jsp'},
                children: [{tag: 'i', attr: {class: 'ace-icon fa fa-laptop'}}],
                innerText: '注册'
            }]
        }];
    }else {
        isLogin = true;
        userDom =  [{
            tag: 'li',
            children: [{
                tag: 'a',
                attr: {href: '/user/'+USERNAME,id:'user-index-link'},
                children: [{tag: 'i', attr: {class: 'ace-icon fa fa-user'}}],
                innerText: '个人首页'
            }]
        },{
            tag: 'li',
            children: [{
                tag: 'a',
                attr: {onclick:'javascript:userLogout()'},
                children: [{tag: 'i', attr: {class: 'ace-icon fa fa-power-off'}}],
                innerText: '退出'
            }]
        }];
    }
    const dataJson = {
        tag: 'div',
        attr: {
            class: 'navbar-container ace-save-state',
            id: 'navbar-container',
        },
        children: [{
            tag: 'button',
            attr: {
                type: 'button',
                class: 'navbar-toggle menu-toggler pull-left',
                id: 'menu-toggler',
                'data-target': '#sidebar',
            },
            children: [{tag: 'span', attr: {class: 'sr-only', innerText: 'Toggle sidebar'}},
                {tag: 'span', attr: {class: 'icon-bar'}},
                {tag: 'span', attr: {class: 'icon-bar'}},
                {tag: 'span', attr: {class: 'icon-bar'}}],
        }, {
            tag: 'div',
            attr: {
                class: 'navbar-header pull-left'
            },
            children: [{
                tag: 'a',
                attr: {href: '#', class: 'navbar-brand'},
                children: [{
                    tag: 'small',
                    children: [{
                        tag: 'i',
                        attr: {class: 'fa fa-leaf'},
                        innerText: '小微企业多创新方法融合与集成应用平台'
                    }]
                }]
            }, {
                tag: 'a',
                attr: {href: '#', class: 'navbar-brand'},
                children: [{
                    tag: 'small',
                    children: [{
                        tag: 'i',
                        children: [{tag: 'small', innerText: document.title}]
                    }]
                }]
            }]
        }, {
            tag: 'div',
            attr: {
                class: 'navbar-buttons navbar-header pull-right',
                role: 'navigation'
            },
            children: [{
                tag: 'ul',
                attr: {
                    class: 'nav ace-nav',
                },
                children: [{
                    tag: 'li',
                    attr: {class: 'grey dropdown-modal'},
                    children: [{
                        tag: 'a',
                        attr: {
                            class: 'dropdown-toggle',
                            'data-toggle': 'dropdown',
                            href: '#',
                            'aria-expanded': 'true'
                        },
                        children: [{
                            tag: 'i',
                            attr: {class: 'ace-icon fa fa-tasks'}
                        }, {
                            tag: 'span',
                            innerText: '导航'
                        }]
                    }, {
                        tag: 'ul',
                        attr: {class: 'dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close'},
                        children: [{
                            tag: 'li',
                            attr: {class: 'dropdown-header'},
                            children: [{
                                tag: 'i',
                                attr: {class: 'ace-icon fa fa-check'},
                                innerText: '平台功能导航'
                            }]
                        }, {
                            tag: 'li',
                            attr: {class: 'dropdown-content ace-scroll', style: 'position: relative;'},
                            children: [{
                                tag: 'ul',
                                attr: {class: 'dropdown-menu dropdown-navbar'},
                                children: [{
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: '首页'}]
                                        }]
                                    }]
                                }, {
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: 'LCUE'}]
                                        }]
                                    }]
                                }, {
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: '柔性模板'}]
                                        }]
                                    }]
                                }, {
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: '应用商店'}]
                                        }]
                                    }]
                                }, {
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: '知识服务'}]
                                        }]
                                    }]
                                }, {
                                    tag: 'li',
                                    children: [{
                                        tag: 'a',
                                        attr: {href: '#'},
                                        children: [{
                                            tag: 'div',
                                            attr: {class: 'clearfix'},
                                            children: [{tag: 'span', attr: {class: 'pull-left'}, innerText: '培训'}]
                                        }]
                                    }]
                                }]
                            }]
                        }]
                    }]
                }, {
                    tag: 'li',
                    attr: {class: 'light-blue dropdown-modal'},
                    children: [{
                        tag: 'a',
                        attr: {
                            'data-toggle': 'dropdown',
                            'href': '#',
                            'class': 'dropdown-toggle'
                        },
                        children: [{
                            tag: 'img',
                            attr: {
                                class: 'nav-user-photo',
                                src: '/webresources/ace-master/assets/images/avatars/user.jpg',
                                alt: 'Jason\'s Photo'
                            },
                        }, {
                            tag: 'span',
                            attr: {class: 'user-info'},
                            children: [{tag: 'small', innerText: '欢迎光临'}],
                            innerText:USERNAME
                        }, {
                            tag: 'i',
                            attr: {class: 'ace-icon fa fa-caret-down'}
                        }]
                    }, {
                        tag: 'ul',
                        attr: {class: 'user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close"'},
                        children:userDom
                    }]
                }]
            }]
        }]

    }
    let navbar = document.getElementById("navbar");
    navbar.setAttribute("class","navbar navbar-default ace-save-state navbar-fixed-top");
    navbar.appendChild(createEl(dataJson));
    //获取用户名
}

function createEl(dataObj) {
    let e = document.createElement(dataObj.tag);

    if (dataObj.attr) {
        for (let x in dataObj.attr) {
            e.setAttribute(x, dataObj.attr[x]);
        }
    }
    if (dataObj.children && dataObj.children.length > 0) {
        dataObj.children.forEach(child => e.appendChild(createEl(child)));
    }
    if (dataObj.innerText) {
        e.appendChild(document.createTextNode(dataObj.innerText))
    }
    return e;

}
function userLogout(){
    $.ajax({
        url: '/webresources/user/logout',
        type: 'get',
        success: function (data) {
            location.reload(true);
        }
    })
}

function getCookie(name){
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg)){
        return unescape(arr[2]);
    }else{
        return null;
    }
}
initNavbarView();
// let navbarContainer = document.createElement("div");
// navbarContainer.setAttribute("class", "navbar-container ace-save-state");
//
// let menutoggler = document.createElement("button");
// menutoggler.setAttribute("type", "button");
// menutoggler.setAttribute("class", "navbar-toggle menu-toggler pull-left");
// menutoggler.setAttribute("id", "menu-toggler");
// menutoggler.setAttribute("data-target", "#sidebar");
// menutoggler.innerHTML = '<span class="sr-only">Toggle sidebar</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>'
//
// navbarContainer.append(menutoggler)
//
// let navBarHeader = document.createElement("div");
// navBarHeader.setAttribute("class", "navbar-header pull-left");
// let indexHref = document.createElement('a');
// indexHref.setAttribute('href', '/');
// indexHref.setAttribute('class', 'navbar-brand');
// indexHref.innerHTML = '<small><i class="fa fa-leaf"></i>小微企业多创新方法融合与集成应用平台</small>'
// let curHref = document.createElement('a');
// curHref.setAttribute('href', '#')
// curHref.setAttribute('class', 'navbar-brand');
// curHref.innerHTML = '<small><small>' + document.title + '</small></small>'
//
// navBarHeader.appendChild(indexHref)
// navBarHeader.appendChild(curHref)
//
// navbarContainer.appendChild(navBarHeader)
//
// let navbarButtons = document.createElement('div');
// navbarButtons.setAttribute('class', 'navbar-buttons navbar-header pull-right');
// navbarButtons.setAttribute('role', 'navigation');
//
// let aceNav = document.createElement('ul');
// aceNav.setAttribute('class', 'nav ace-nav');
//
// //平台导航开始
// let ul_li = document.createElement('li');
// ul_li.setAttribute('class', 'grey dropdown-modal');
// //a标签开始
// let ul_li_a = document.createElement('a');
// ul_li_a.setAttribute('data-toggle', 'dropdown');
// ul_li_a.setAttribute('class', 'dropdown-toggle');
// ul_li_a.setAttribute('href', '#');
// ul_li_a.setAttribute('aria-expanded', 'false')
// ul_li_a.innerHTML = '<i class="ace-icon fa fa-tasks"></i><span >导航</span>'
// ul_li.appendChild(ul_li_a);
// //a标签结束
//
// //ul标签开始
// let ul_li_ul = document.createElement('ul');
// ul_li_ul.setAttribute('class', 'dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close')
// let ul_li_ul_header = document.createElement('li');
// ul_li_ul_header.setAttribute('class','dropdown-header');
// ul_li_ul_header.innerHTML = '<i class="ace-icon fa fa-check"></i>平台功能导航'
// ul_li_ul.appendChild(ul_li_ul_header)
// //li标签开始
// let ul_li_ul_li = document.createElement('li');
// ul_li_ul_li.setAttribute('class', 'dropdown-content')
//
// let ul_li_ul_li_div = document.createElement('div');
// ul_li_ul_li_div.setAttribute('class','scroll-content')
//
// //ul标签开始
// let ul_li_ul_li_div_ul = document.createElement('ul');
// ul_li_ul_li_div_ul.setAttribute('class', 'dropdown-menu dropdown-navbar navbar-pink');
//
// //导航平台首页开始
// let li1 = document.createElement('li');
// li1.innerHTML = '<a href="#"><i class="btn btn-xs btn-primary fa fa-user"></i>&nbsp;平台首页</a>'
// ul_li_ul_li_div_ul.appendChild(li1);
// //导航平台首页结束
//
// //LCUE开始
// let li2 = document.createElement('li');
// li2.innerHTML = '<a href="#"><i class="btn btn-xs btn-primary fa fa-user"></i>&nbsp;LCUE</a>'
// ul_li_ul_li_div_ul.appendChild(li2);
// //LCUE结束
//
// //模板开始
// let li3 = document.createElement('li');
// li3.innerHTML = '<a href="#"><i class="btn btn-xs btn-primary fa fa-user"></i>&nbsp;柔性模板</a>'
// ul_li_ul_li_div_ul.appendChild(li3);
// //模板结束
//
// //应用商店开始
// let li4 = document.createElement('li');
// li4.innerHTML = '<a href="#"><i class="btn btn-xs btn-primary fa fa-user"></i>应用商店</a>'
// ul_li_ul_li_div_ul.appendChild(li4);
// //应用商店结束
//
// //培训开始
// let li5 = document.createElement('li');
// li5.innerHTML = '<a href="#"><i class="btn btn-xs btn-primary fa fa-user"></i>培训</a>'
// ul_li_ul_li_div_ul.appendChild(li5);
// //培训结束
//
// ul_li_ul_li_div.appendChild(ul_li_ul_li_div_ul);
// ul_li_ul_li.appendChild(ul_li_ul_li_div)
// ul_li_ul.appendChild(ul_li_ul_li);
// ul_li.appendChild(ul_li_ul);
// //ul结束
// aceNav.appendChild(ul_li);
// //平台导航结束
//
// //用户部分开始
// let userNavLi = document.createElement('li');
// userNavLi.setAttribute('class', 'light-blue dropdown-modal');
//
// let li_a = document.createElement('a');
// li_a.setAttribute('data-toggle','dropdown');
// li_a.setAttribute('class','dropdown-toggle');
// let li_a_img = document.createElement('img');
// li_a_img.setAttribute('class','nav-user-photo');
// li_a.appendChild(li_a_img);
// let li_a_span = document.createElement('span');
// li_a_span.setAttribute('class','user-info');
// li_a_span.innerHTML('<small>欢迎</small>')
// li_a.appendChild(li_a_span)
//
// let li_a_i = document.createElement('i');
// li_a_i.setAttribute('class','ace-icon fa fa-caret-down');
// li_a.appendChild(li_a_i);
//
// userNavLi.appendChild(li_a);
// let li_ul = document.createElement('ul');
// li_ul.setAttribute('class','user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close');
//
// let li_ul_li1 = document.createElement('li');
//
// let li_ul_li1_a = document.createElement('a');
//
//
// aceNav.appendChild(userNavLi);
// //用户部分结束
// navbarButtons.appendChild(aceNav);
// //右侧导航结束
// navbarContainer.appendChild(navbarButtons)
// navbar.appendChild(navbarContainer);
// //导航结束
