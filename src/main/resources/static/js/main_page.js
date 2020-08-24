//事件处理
layui.use(["element", "layer"], function () {
    var $ = layui.jquery,
        element = layui.element,
        layer = layui.layer;
        $(function () {
        var l_o = $(".left-menu"),
            tab = "top-tab",
            l_m = "left-menu",
            t_m = "top-menu";
        var mainHeight = $(window).height() - 60;
        l_o.on("click", "li", function () {
            $(this).siblings().removeClass("layui-nav-itemed")
        });

        // 本地存储
        l_o.find("a[data-id]").click(function () {
            sessionStorage .dataId = $(this).attr('data-id')
        });

        $(".menu-flexible").click(function () {
            $(".left-menu-all").toggle();
            $(".layui-body,.layui-footer").css("left", ($(".left-menu-all").is(":hidden")) ? "0" : "200px")
        });

        // 判断是否有本地存储
        if (sessionStorage .getItem('dataId')) {
            var dataId = sessionStorage .getItem('dataId')
            var getNowId = l_o.find("a[data-id=" + dataId + "]")
            var getNowParent = $(getNowId).parent()

            $(getNowParent).addClass('layui-this')
            console.log($(getNowParent)[0]);
            // 判断是否需要展开
            if ($(getNowParent)[0].tagName == 'DD') {
                $(getNowId).parents('.layui-nav-item').addClass('layui-nav-itemed');
            }
        }
        // 目录
        $(document).on("click", ".mulu,.masked", function () {
            $('body').toggleClass('mulu-hide');
        });

        document.body.addEventListener('touchstart', function () { });

    });



    // 搜索
    $(document).on('click', '#search-btn', function () {
        $('.search-res-mask').show();
        $(this).parent().siblings('.search-fix').addClass('cur');

    })

    $(document).on('click', '#search-close', function () {
        $('.search-res-mask').hide();
        $(this).parents('.search-fix').removeClass('cur');

    })

    $(document).on('click', '.search-res-mask', function () {
        $('#search-close').trigger('click');
    })

    // 移动端下拉
    $(document).on('click', '.layui-table .layui-table-first', function () {
        if ($(window).width() > 992) {
            return;
        }
        if ($(this).hasClass('cur')) {
            $(this).siblings().hide();
            $(this).removeClass('cur');
        } else {
            $($(this).siblings()).css('display', '-webkit-box');
            $(this).addClass('cur');
        }
    })

    
});

//界面搭建

var header = {
    template:`  
        <!-- 头部 -->
        <div class="layui-header">
            <div class="layui-main">
                <div class="top-left">
                    <!-- logo -->
                    <a href="javascript:;" class="mulu"><img src="img/mulu.png" alt=""></a>
                    <a href="javascript:;" class="logo">设备管理系统</a>
                    <a href="javascript:;" class="menu-flexible ml10">
                        <i class="layui-icon">&#xe635;</i> 
                    </a>
                </div>
                <!-- 头部右侧操作 -->
                <ul class="layui-nav operate">
                    <li class="layui-nav-item">
                        <a href="javascript:;">137****6116</a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="修改密码.html" class="edit-password">修改密码</a>
                            </dd>
                            <dd>
                                <a href="登录.html">退出登录</a>
                            </dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </div>
        `
};


var leftSlide =
    {
        template: `   
        <!-- 左侧菜单 -->
        <div class="layui-side layui-bg-black left-menu-all ">
            <div class="layui-side-scroll">
                <div class="info">
                    <div class="name">13800138000，欢迎您！</div>
                    <div class="oper box">
                        <a class="flex edit-password" href="修改密码.html">修改密码</a>
                        <a class="flex" href="登录.html">退出登录</a>
                    </div>
                </div>
                <ul class="layui-nav layui-nav-tree left-menu" lay-filter="left-menu">
                    <li class="layui-nav-item ">
                        <a href="page_main.html" data-id="1">系统首页</a>
                    </li>

                    <li class="layui-nav-item ">
                        <a href="page_monitoring.html" data-id="2">实时监控</a>
                    </li>

					 <li class="layui-nav-item ">
                        <a href="page_main.html" data-id="3">历史数据</a>
                    </li>
					
					 <li class="layui-nav-item ">
                        <a href="page_main.html" data-id="3">曲线记录</a>
                    </li>

					<li class="layui-nav-item ">
                        <a href="page_main.html" data-id="3">日志记录</a>
                    </li>

                </ul>
            </div>
        </div>
        `
    }

// 创建根实例
new Vue({
    el: '#header',
    components: {
        'header-component': header
    }

})

new Vue({
    el: '#leftSlide',
    components: {
        'leftslide-component': leftSlide
    }
})