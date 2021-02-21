$(function () {
    $.ajax({
        type: "get",
        url: apiUrl.account.isLogin,
        async: false,
        headers: { "eweightToken": sessionStorage.getItem("eweightToken")},
        success: function (res) {

            var json = typeof res=='string'?JSON.parse(res):res;

            if(json.code != 0) {
                window.location.href = '/html/login';
            }
        },
        complete: function( xhr,data ) {
           // console.log("isLogin1");
          //  console.log(xhr.getResponseHeader('eweightToken'));
            //存入sessionStore
            if (xhr.getResponseHeader('eweightToken')) {
                sessionStorage.setItem("eweightToken", xhr.getResponseHeader('eweightToken'));
            }

        },
        error: function (txt) {}
    });
})

