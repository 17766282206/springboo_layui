listLoginInfoMenu//统一api接口
const host = window.location.host;
const API_BASE_URL = "http://" + host;
const apiUrl = {
    API_BASE : API_BASE_URL+'/',
    //系统设置接口
    base: {
        selectDictByType: API_BASE_URL + '/api/auth/sys/dict/selectDictByType',//根据类型，获取字典数据
        uploadImg: API_BASE_URL + '/sys/uploadImg',//图片上传
    },
    account: {
        login: API_BASE_URL + '/api/auth/account/_login',//登录
        auth: API_BASE_URL + '/auth',//获取token
        isLogin: API_BASE_URL + '/api/auth/isLogin',//检验是否登陆
        isPermission: API_BASE_URL + '/api/auth/isPermission',
        logout: API_BASE_URL + '/api/auth/account/_logout',//退出
        getLoginInfo: API_BASE_URL + '/api/auth/account/getLoginInfo',//获取登录人信息

    },
    user: {
        addPage: API_BASE_URL + '/html/system/user/addPage', //跳转新增页面
        updatePage: API_BASE_URL + '/html/system/user/updatePage', //跳转修改页面
        pageList: API_BASE_URL + '/api/auth/user/pageList',//分页显示和查询
        AllList: API_BASE_URL + '/api/auth/user/AllList',//显示和查询
        deletes: API_BASE_URL + '/api/auth/user/deletes',//批量删除
        save: API_BASE_URL + '/api/auth/user/save',//新增和修改
        saveNewPassword: API_BASE_URL + '/api/auth/user/saveNewPassword',//修改密码页面

    },
    role: {
        addPage: API_BASE_URL + '/html/system/role/addPage',//新增页面
        updatePage: API_BASE_URL + '/html/system/role/updatePage',//修改页面
        listRole: API_BASE_URL + '/api/auth/role/listRole',      //下拉列表获取当前公司启用状态的角色
        pageList: API_BASE_URL + '/api/auth/role/pageList',//分页显示和查询
        AllList: API_BASE_URL + '/api/auth/role/AllList',//显示和查询
        save: API_BASE_URL + '/api/auth/role/save',//新增和修改
        delete: API_BASE_URL + '/api/auth/role/delete',//删除和批量删除
        deletes: API_BASE_URL + '/api/auth/role/deletes',//删除和批量删除

    },
    menu: {
        iconList: API_BASE_URL + '/html/system/menu/icon',//跳转图标页面
        addPage: API_BASE_URL + '/html/system/menu/addPage',//新增页面
        updatePage: API_BASE_URL + '/html/system/menu/updatePage',//修改页面

        save: API_BASE_URL + '/api/auth/menu/save',//新增和修改
        delete: API_BASE_URL + '/api/auth/menu/delete',//删除
        deletes: API_BASE_URL + '/api/auth/menu/deletes',//批量删除
        getDTreeList: API_BASE_URL + '/api/auth/menu/getDTreeList',//获取dtree需要的data数据
        listFromPid: API_BASE_URL + '/api/auth/menu/listFromPid',//根据pid对象查询，返回一个对象
        getListByLoginInfo: API_BASE_URL + '/api/auth/menu/getListByLoginInfo',//显示登录人拥有的权限菜单
        getListById: API_BASE_URL + '/api/auth/menu/getListById',//根据id获取所有子节点

    },
    company: {
        datalist: API_BASE_URL + '/api/auth/sys/company/list', //查询所有部门
        addPage: API_BASE_URL + '/html/system/company/AddPage', //跳转新增页面
        updatePage: API_BASE_URL + '/html/system/company/UpdatePage', //跳转修改页面
        addcompany: API_BASE_URL + '/api/auth/sys/company/addcompany', //新增部门
        updatecompany: API_BASE_URL + '/api/auth/sys/company/Updatecompany', //修改部门
        delete: API_BASE_URL + '/api/auth/sys/company/delete',//删除和批量删除
        tree: API_BASE_URL + '/sys/company/tree', //修改部门
        getDTreeList: API_BASE_URL + '/sys/auth/sys/company/getDTreeList', //树状图

    },
    log: {
        pageList: API_BASE_URL + '/api/auth/log/pageList',//分页显示和查询
        deletes: API_BASE_URL + '/api/auth/log/deletes', //删除日志
    },
    dict: {
        addPage: API_BASE_URL + '/html/system/dict/addPage',//新增页面
        updatePage: API_BASE_URL + '/html/system/dict/updatePage',//修改页面
        pageList: API_BASE_URL + '/api/auth/dict/pageList',//分页显示和查询
        save: API_BASE_URL + '/api/auth/dict/save', //新增和修改字典
        deletes: API_BASE_URL + '/api/auth/dict/deletes',//批量删除
    },
    dictComment: {
        addPage: API_BASE_URL + '/html/system/dictComment/addPage',//新增页面
        updatePage: API_BASE_URL + '/html/system/dictComment/updatePage',//修改页面
        pageList: API_BASE_URL + '/api/auth/dictComment/pageList',//分页显示和查询
        save: API_BASE_URL + '/api/auth/dictComment/save', //新增和修改字典
        deletes: API_BASE_URL + '/api/auth/dictComment/deletes',//批量删除
    },
    //定时任务
    job: {

        addPage: API_BASE_URL + '/html/system/job/addPage', //跳转新增页面
        updatePage: API_BASE_URL + '/html/system/job/updatePage', //跳转修改详情页面

        pageList: API_BASE_URL + '/api/auth/job/pageList', //查询定时任务列表
        addJob: API_BASE_URL + '/api/auth/system/job/add', //新增定时任务
        deletes: API_BASE_URL + '/api/auth/job/deletes', //删除定时任务
        save: API_BASE_URL + '/api/auth/job/save', //保存定时任务

        updateJob: API_BASE_URL + '/api/auth/system/job/update',//修改定时任务信息
        stops: API_BASE_URL + '/api/auth/job/stops',//暂停一个任务
        restarts: API_BASE_URL + '/api/auth/job/restarts',//重启一个任务

    },
    photo: {
        UploadImage: API_BASE_URL + '/PhotoUpload/UploadImage',
    },
    excel: {
        excelExport: API_BASE_URL + '/front/file/excelExport',
        excelImport: API_BASE_URL + '/front/file/excelImport',//数据导入
        excelImportData: API_BASE_URL + '/front/file/excelImportData',//数据显示
        excelImportBill: API_BASE_URL + '/front/file/excelImportBill',//单据类型excel导入
        excelImportBom: API_BASE_URL + '/front/file/excelImportBom',
    },

    //广告管理接口
    carousel: {
        datalist: API_BASE_URL + '/api/auth/eweight/carousel/datalist', //查询广告列表
        addPage: API_BASE_URL + '/html/carousel/AddPage', //跳转新增页面
        addCarousel: API_BASE_URL + '/api/auth/eweight/carousel/add', //新增索引
        delete: API_BASE_URL + '/api/auth/eweight/carousel/delete', //删除广告
        updatePage: API_BASE_URL + '/html/carousel/UpdatePage', //跳转修改详情页面
        updateCarousel: API_BASE_URL + '/api/auth/eweight/carousel/update',//修改广告信息
    },
    app: {
        userList: API_BASE_URL + '/api/auth/app/user/list',  //app用户列表
    },
    instructionTag: {
        datalist: API_BASE_URL + '/api/auth/eweight/instruction/tag/datalist', //查询说明书列表
        addPage: API_BASE_URL + '/html/tag/add', //跳转新增页面
        addTag: API_BASE_URL + '/api/auth/eweight/instruction/tag/add', //新增标签
        delete: API_BASE_URL + '/api/auth/eweight/instruction/tag/delete', //删除标签
        updatePage: API_BASE_URL + '/html/tag/update', //跳转修改详情页面
        updateCarousel: API_BASE_URL + '/api/auth/eweight/instruction/tag/update',//修改标签信息
    }
};

