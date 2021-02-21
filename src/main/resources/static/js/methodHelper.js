//封装的方法
var  Util = {
    //获取登录人信息
    checkLoginStatus: function (callback) {
        $.post(apiUrl.account.current, function (res) {
            callback(res);
        }, 'json');

    },
    //获取登录人姓名和当前时间
    getLoginName: function (callback) {
        $.post(apiUrl.account.getLoginName, function (res) {
            callback(res);
        }, 'json')
    },

    //获取记住密码cookie
    getCookie: function (name) {
        let arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    },
    //打开一个新的tab
    openTabsPage: function (url, title) {
        parent.layui.index.openTabsPage(encodeURI(url), title);
    },


    //layui关闭按钮事件
    closePage: function () {
        let index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index);
    },
    show_details: function (t) {
        layer.tips('点击查看详情', t, {
            tips: 1,           // 在标签上面显示tips
            time: 1000     // 1秒消失
        })
    },
    //打开一个新窗口
    openUrlHref:function(obj){
        window.open("http://"+$(obj).text(), '_blank');
    },
    //判断字符是否为空的方法 是就返回true
    isEmpty: function (obj) {
        if (obj == undefined || obj == null || obj == "" || obj == '') {
            return true;
        } else {
            return false;
        }
    },
    //字符串转小写
    StringToLowerCase: function (n) {
        return n.toLowerCase();
    },
    //字符串转大写
    StringToUpperCase: function (n) {
        return n.toUpperCase();
    },
    //将代表金额的数字转换为大写中文
    NumToDX: function (n) {
        if (n == 0) {
            return '零元'
        }
        let unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
        let p = n.indexOf('.');
        if (p >= 0)
            n = n.substring(0, p) + n.substr(p + 1, 2);
        unit = unit.substr(unit.length - n.length);
        for (let i = 0; i < n.length; i++)
            str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
        let res = str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
        return res;
    },
    //当前月份第一天
    getCurrentMonthFirst: function () {
        let date = new Date();
        date.setDate(1);
        let month = parseInt(date.getMonth() + 1);
        let day = date.getDate();
        if (month < 10) {
            month = '0' + month
        }
        if (day < 10) {
            day = '0' + day
        }
        return date.getFullYear() + '-' + month + '-' + day;
    },
    getCurrentNewDay: function () {
        //获取当前时间
        let date = new Date();
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    },
    getCurrentMonthLast: function () {
        let date = new Date();
        let currentMonth = date.getMonth();
        let nextMonth = ++currentMonth;
        let nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1);
        let oneDay = 1000 * 60 * 60 * 24;
        let lastTime = new Date(nextMonthFirstDay - oneDay);
        let month = parseInt(lastTime.getMonth() + 1);
        let day = lastTime.getDate();
        if (month < 10) {
            month = '0' + month
        }
        if (day < 10) {
            day = '0' + day
        }
        return date.getFullYear() + '-' + month + '-' + day;
    },
    returnFloatWhereNum: function (value) {
        console.log(value.toFixed(2))
        value = Math.round(parseFloat(value) * 1000) / 1000;
        let xsd = value.toString().split(".");
        console.log(xsd);

        if (xsd.length == 1) {

            value = value.toString() + ".00";
            return value;
        }
        if (xsd.length > 1) {

            if (xsd[1].length < 2) {

                value = value.toString() + "0";
            }
            return value;
        }
    },
    //获取div id 和要延时的数字
    dataDelay: function (div, count) {
        $('#' + div + '').text(count);
        $('#' + div + '').prop('Counter', 0).animate({
            Counter: $('#' + div + '').text()
        }, {
            duration: 2000,
            easing: 'swing',
            step: function (now) {
                $('#' + div + '').text(Math.ceil(now));
            }
        });
    },
    resolvingDate: function (date) {
//date是传入的时间
        let d = new Date(date);
        let month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
        let day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
        let hours = d.getHours() < 10 ? '0' + d.getHours() : d.getHours();
        let min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
        let sec = d.getSeconds() < 10 ? '0' + d.getSeconds() : d.getSeconds();
        let times = d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + sec;
        return times
    },
    //layui 时间格式处理
    timeFormatConversion: function (val) {
        if (val == null) {
            return ' ';
        } else {
            const dateee = new Date(val).toJSON();
            const date = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
            return date;
        }
    },
    //double 乘法计算，防精度丢失
    numMulti: function (num1, num2) {
        let baseNum = 0;
        try {
            baseNum += num1.toString().split(".")[1].length;
        } catch (e) {
        }
        try {
            baseNum += num2.toString().split(".")[1].length;
        } catch (e) {
        }
        return Number(num1.toString().replace(".", ""))
            * Number(num2.toString().replace(".", ""))
            / Math.pow(10, baseNum);
    },
    //除法运算
    numDiv: function (num1, num2) {
        let baseNum1 = 0, baseNum2 = 0;
        let baseNum3, baseNum4;
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        with (Math) {
            baseNum3 = Number(num1.toString().replace(".", ""));
            baseNum4 = Number(num2.toString().replace(".", ""));
            return (baseNum3 / baseNum4) * pow(10, baseNum2 - baseNum1);
        }
    },
    //加法运算
    numAdd: function (num1, num2) {
        let baseNum, baseNum1, baseNum2;
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
        return (num1 * baseNum + num2 * baseNum) / baseNum;
    },
    //减法计算
    numSub: function (num1, num2) {
        let baseNum, baseNum1, baseNum2;
        let precision;// 精度
        try {
            baseNum1 = num1.toString().split(".")[1].length;
        } catch (e) {
            baseNum1 = 0;
        }
        try {
            baseNum2 = num2.toString().split(".")[1].length;
        } catch (e) {
            baseNum2 = 0;
        }
        baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
        precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
        return ((num1 * baseNum - num2 * baseNum) / baseNum)
            .toFixed(precision);
    },
    //传小数，取几位，不四舍五入
    formatDecimal: function (num, decimal) {
        num = num.toString()
        let index = num.indexOf('.')
        if (index !== -1) {
            num = num.substring(0, decimal + index + 1)
        } else {
            num = num.substring(0)
        }
        return parseFloat(num).toFixed(decimal)
    },
    //图片不存在显示的函数
    imgerrorfun: function () {
        let img = event.srcElement;
        img.src = API_BASE_URL + "/images/404.jpg";
        img.onerror = null;
        //控制不要一直跳动
    },
    //图片上传方法
    //参数一选择图片按钮的id  参数二 提交图片的按钮id 参数三 带参数提交 参数四 预览图片的id 参数五 返回路径要赋值的id
    uploadImg: function (choiceID, implementID, viewImg, photoPathID) {
        layui.use(["upload"], function () {
            const upload = layui.upload;
            choiceID = '#' + choiceID;
            implementID = '#' + implementID;
            let uuid = Util.generateUUID();
            //执行上传图片的方法实例
            upload.render({
                elem: choiceID
                , url: apiUrl.photo.UploadImage
                , data: {
                    uuid: uuid
                }
                , auto: false //选择文件后不自动上传
                , bindAction: implementID //指向一个按钮触发上传
                , size: 5000  //图片大小 kb
                , choose: function (obj) {//点击按钮就会执行
                    //uuid先赋值到表单

                    $('#' + photoPathID + '').val(uuid);
                    ButtonTypelet = true;
                    obj.preview(function (index, file, result) {
                        $('#' + viewImg + '').attr('src', result); //图片链接（base64）
                    });
                    return false;
                }
                , done: function (res) {
                    //如果上传失败
                    if (res.msg > 0) {
                        console.log("上传失败");
                        return false;
                    }
                    $('#' + photoPathID + '').val(res.data);
                    imgUrllet = res.data;
                    uploadTypelet = true;
                    return false;
                }
                , error: function () {

                }
            });
        });
    },
    uploadFile:function(choiceID){
        layui.use(["upload"], function () {
            const upload = layui.upload;
            choiceID = '#' + choiceID;
            upload.render({
                elem: choiceID
                , url: apiUrl.excel.excelImportData
                , data: {
                    type: 'billItem'
                }
                //, auto: true //选择文件后不自动上传
                , accept: 'file' //普通文件
                , done: function (res) {
                    if (res.code < 0) {
                        layer.msg(res.msg, {icon: 5});
                    } else {
                        //数据表格全局data
                        $.post(apiUrl.TbBillItem.dateIsExistParts, {billItemList: JSON.stringify(res.data)}, function (res) {
                            if (res.code == 0) {
                                layer.msg(res.msg, {icon: 6});
                            } else {
                                let data=res.data;
                                console.log(res);
                                layer.confirm(res.msg, {
                                    icon: 5,
                                    btn: ['确认', '取消'], btn1: function (index, layero) {
                                        layer.close(index);
                                    }
                                }, function (index, layero) {
                                    dataTable = [];
                                    for (let key in data) {
                                        let obj = {
                                            partsCode: data[key].partsCode
                                            , partsName: data[key].partsName
                                            , specification: data[key].specification
                                            , unitsName: data[key].unitsName
                                            , price: data[key].price
                                            , number: data[key].number
                                            , money: Util.numMulti(data[key].price, data[key].number)
                                            , remark: data[key].remark
                                        };
                                        dataTable.push(obj);
                                    }

                                    layer.close(index);
                                });
                            }
                        });
                    }
                    return false;
                }
                , error: function () {
                }
            });
        });
    },
    //递归遍历tree 获取id
    traverseLayuiTree: function (res) {
        let arr = [];
        for (let i = 0; i < res.length; i++) {
            function traverseTree(node) {
                if (!node) {
                    return;
                }
                arr.push(node.id);
                if (node.children && node.children.length > 0) {
                    for (let j = 0; j < node.children.length; j++) {
                        traverseTree(node.children[j]);
                    }
                }
            }

            traverseTree(res[i]);
        }
        return arr;
    },
    //UUID生成
    generateUUID: function () {
        let d = new Date().getTime();
        if (window.performance && typeof window.performance.now === "function") {
            d += performance.now(); //use high-precision timer if available
        }
        let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
            let r = (d + Math.random() * 16) % 16 | 0;
            d = Math.floor(d / 16);
            return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
        });
        return uuid;
    },
    //递归生成左侧菜单栏

    //递归生成左侧菜单栏
    traverseLayuiMenuBar: function (res) {
       // console.log(res);
        let content = '';
        $.each(res, function (i, obj) {
            content += '<li  data-name="" class="layui-nav-item">';
            if (obj.href == null || obj.href.length === 0) {
                content += '<a  id="id" href="javascript:;" lay-tips="' + obj.menuName + '" lay-direction="2">';
            } else {
                content += '<a  id="id" lay-href="' + obj.href + '" lay-tips="' + obj.menuName + '" lay-direction="2">';
            }
            content += '<i class="layui-icon">'+ obj.icon +'</i>';
            content += '<cite>' + obj.menuName + '</cite></a>';
            //这里是添加所有的子菜单
            content += loadchild(obj);
            content += '</li>';

        });

        //组装子菜单的方法
        function loadchild(obj) {

            if (obj == null) {
                return;
            }
            let content = '';
            if (obj.children != null && obj.children.length > 0) {
                content += '<dl class="layui-nav-child">';
            } else {
                content += '<dl>';
            }

            if (obj.children != null && obj.children.length > 0) {
                $.each(obj.children, function (i, note) {
                    content += '<dd ><i class="layui-icon">'+ note.icon +'</i>';
                    if (note.href == null || note.href.length === 0) {
                        content += '<a id="id" href="javascript:;" >' + note.menuName + '</a>';
                    } else {
                        //layadmin-event="refresh" 刷新页面
                        content += '<a id="id" lay-href="' + note.href + '">' + note.menuName + '</a>';
                    }

                    if (note.children == null) {
                        return;
                    }
                    content += loadchild(note);
                    content += '</dd>';
                });

                content += '</dl>';
            }
            return content;
        }

        return content;
    }
};

//封装的ajax
$createAjax = function (ajaxrequire,callback) {
    _url = ajaxrequire._urls;
    _dataType = ajaxrequire._dataType;
    _async = ajaxrequire._async
    _method = ajaxrequire._method;
    _data = ajaxrequire._data;
    _cache = ajaxrequire._cache;
    $.ajax({
        url: _url,
        dataType: _dataType,
        cache: _cache?_cache:false,
        async: _async?_async:false,//默认 false
        type: _method,
        data: _data?_data:{},
        headers: {"eweightToken": sessionStorage.getItem("eweightToken")},
        timeout:10000,
        success:function(data){
            //console.log(_async?_async:false);
            var res = typeof data=='string'?JSON.parse(data):data;
            if ((res.code + "").substr(0,1) == '5') {
               // console.log("token=========ERROR")
                window.location.href = '/html/login';
            } else {
                callback(res);
            }
        },
        complete: function( xhr,data ) {
            //存入sessionStore
            if (xhr.getResponseHeader('eweightToken')) {
                //console.log("每次更新token")
               // console.log(xhr.getResponseHeader('eweightToken'))
                sessionStorage.setItem("eweightToken", xhr.getResponseHeader('eweightToken'));
            }
        },
        error:function(data){
            console.log("服务端异常！");
        }
    });
}




function resolvingDate(date){
    //date是传入的时间
    let d = new Date(date);

    let month = (d.getMonth() + 1) < 10 ? '0'+(d.getMonth() + 1) : (d.getMonth() + 1);
    let day = d.getDate()<10 ? '0'+d.getDate() : d.getDate();
    let hours = d.getHours()<10 ? '0'+d.getHours() : d.getHours();
    let min = d.getMinutes()<10 ? '0'+d.getMinutes() : d.getMinutes();
    let sec = d.getSeconds()<10 ? '0'+d.getSeconds() : d.getSeconds();

    let times=d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + sec;

    return times
}

