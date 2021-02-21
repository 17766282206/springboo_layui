$(function () {

    layui.use(['layer', 'element'], function () {
        let layer = layui.layer
            , element = layui.element;
        let loadIndex = layer.load(2);
        $.post(apiUrl.menu.getListByLoginInfo, function(res){
            layer.close(loadIndex);
            if (res.code !== 200 && res.status === false) return;
                $("#LAY-system-side-menu").append(Util.traverseLayuiMenuBar(res.data));
                element.render('nav');

        });
        // let ajaxrequire = {
        //     _urls: apiUrl.menu.listLoginInfoMenu,
        //     _method: 'post',
        //     _dataType: 'json'
        // };
        // $createAjax(ajaxrequire, function (res) {
        //     layer.close(loadIndex);
        //     if (res.code === 0) {
        //         $("#LAY-system-side-menu").append(Util.traverseLayuiMenuBar(res.data));
        //         element.render('nav');
        //
        //     }
        // });



        // Util.checkLoginStatus(function (res) {
        //     if (res.code === 0) {
        //         //生成当前登录人的菜单栏
        //         $.post(apiUrl.TbMenuItem.listLogPersonMenuItems, function (res) {
        //
        //         });
        //
        //         $('#head-userExpireTime').text(res.data.expireTime);
        //         //加载登录用户数据
        //         $('#head-username').text(res.data.name);
        //         //修改基本信息
        //         $('#btn-basicData').click(function () {
        //
        //         });
        //         //修改密码
        //         $('#btn-securitySetting').click(function () {
        //             layer.open({
        //                 type: 2,
        //                 title: '修改密码',
        //                 content: apiUrl.account.changePasswordPage,
        //                 skin: 'layui-layer-molv',
        //                 area: ['350px', '350px'],
        //                 closeBtn: 1,//不显示则closeBtn: 0
        //                 shade: [0.9, '#393D49'],
        //
        //             });
        //
        //         });
        //         //退出
        //         $('#btn-logout').click(function () {
        //             layer.confirm('您确定要退出登录吗？', function (index) {
        //                 $.post(apiUrl.account.logout, function (res) {
        //                     if (res.code === 0) {
        //                         layer.msg(res.msg, {
        //                             offset: '15px'
        //                             , icon: 6
        //                             , time: 1000
        //                         }, function () {
        //                             window.location.href = 'login'; //后台主页
        //                         });
        //                     } else {
        //                         layer.msg(res.msg, {
        //                             offset: '15px'
        //                             , icon: 5
        //                             , time: 1000
        //                         });
        //                     }
        //                 })
        //             })
        //         });
        //     } else {
        //         layer.msg(res.msg, {
        //             offset: '15px'
        //             , icon: 5
        //             , time: 1000
        //         }, function () {
        //             window.location.href = 'login'; //后台主页
        //         });
        //
        //     }
        // });
    });
//初始化结束
});

