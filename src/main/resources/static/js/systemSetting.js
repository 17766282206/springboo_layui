//选项配置
let numberFigure=null;
let priceFigure=null;
let moneyFigure=null;
let fck=null;
let tableSize=null;
$.ajax({
    url:'/front/TbSystemSettings/list',
    dataType: 'json',
    type: 'post',
    async: false,
    timeout: 5000,
    success: function (res) {
        if (res.code === 0) {
            numberFigure=res.data.numberFigure;
            priceFigure=res.data.priceFigure;
            moneyFigure=res.data.moneyFigure;
            fck=res.data.fck;
            tableSize=res.data.tableSize;

        } else {
            layer.msg(res.msg);
        }
    },
    error: function (res) {
        $('#loginSub').attr('disabled', false);
        layer.msg('服务器请求超时...');
    }
});