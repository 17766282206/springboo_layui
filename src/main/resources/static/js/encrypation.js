
//封装的密码加密方法
let Encrypation = {
    /**
     * 加密（需要先加载lib/aes/aes.min.js文件）
     * @param word
     * @returns {*}
     */
    encrypt: function (word) {
        var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
        var srcs = CryptoJS.enc.Utf8.parse(word);
        var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
        return encrypted.toString();
    },

    /**
     * 解密
     * @param word
     * @returns {*}
     */
    decrypt: function (word) {
        var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");
        var decrypt = CryptoJS.AES.decrypt(word, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    },
    md5: function (word) {
        return md5(encodeURIComponent(word));
    },
};

