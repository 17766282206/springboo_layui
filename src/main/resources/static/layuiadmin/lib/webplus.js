/**
 * 核心JS公用组件接口
 */
layui.define( function(exports) {
	var setter = layui.setter
	 ,$ = layui.$
	, table = layui.table
	,form = layui.form;
	var webplus = {

		/**
		 * [cookieFun description] 判断是否是ie true返回IE false返回其他
		 */
		isIE : function() {
			var userAgent = navigator.userAgent; // 取得浏览器的userAgent字符串
			var isIE = userAgent.indexOf("compatible") > -1
					&& userAgent.indexOf("MSIE") > -1; // 判断是否IE<11浏览器
			var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; // 判断是否IE的Edge浏览器
			var isIE11 = userAgent.indexOf('Trident') > -1
					&& userAgent.indexOf("rv:11.0") > -1;
			if (isIE || isEdge || isIE11) {
				return true;
			} else {
				return false;// 不是ie浏览器
			}
		},
		/**
		 * 判断是否为空 @str 字符串
		 */
		isEmpty : function(v) {
			switch (typeof v) {
			case 'undefined':
				return true;
			case 'string':
				if (webplus.trim(v).length == 0)
					return true;
				break;
			case 'boolean':
				if (!v)
					return true;
				break;
			case 'number':
				if (0 === v)
					return true;
				break;
			case 'object':
				if (null === v)
					return true;
				if (undefined !== v.length && v.length == 0)
					return true;
				for ( var k in v) {
					return false;
				}
				return true;
				break;
			}
			return false;

		},
		/**
		 * 去掉空格字符串
		 */
		trim:function(str){
			
			return str.replace(/(^\s*)|(\s*$)/g, '');
		},
		/* [cookieFun description]
		 *  提供增改          set    默认3小时
		 * 	       增改  	  setCookieMin   1分钟
		 *      删            del
		 *      查            get
		 *      1分钟时间限制 setmin
		 */
		setCookieYear : function(name,value){
			var houer = 4; 
		    var exp  = new Date(); 
		    exp.setTime(exp.getTime() + houer*60*60*1000);
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		},
		/*	
		 * 根据名称获取
		 */
		getName : function(name) {
			if(webplus.isIE()) {
				return webplus.getCookie(name);
			} else {
				return localStorage.getItem(name);
			}
		},
		/*	
		 * 设置本地缓存
		 */
		setName : function(key,value) {
			if(webplus.isIE()) {
				webplus.setCookieYear(key,value);
			} else {
				localStorage.setItem(key,value);
			}
		},
		getCookie : function(name){
		    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		    if(arr != null) 
		        return unescape(arr[2]); 
		    return null;
		},
		delCookie : function(name){
		    var exp = new Date();
		    exp.setTime(exp.getTime() - 1);
		    var cval=cookieFun.get(name);
		    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		},
		/**
		 *获取token值,用于请求接口
		 */
		getToken : function(){
		    if(webplus.isIE()){
		    	return webplus.getCookie('token');
		    }else{
		    	return localStorage.getItem("token");
		    }
		},
		/**
		 *删除token值
		 */
		delToken : function(){
		    if(webplus.isIE()){
		    	webplus.delCookie('token');
		    }else{
		    	localStorage.setItem("token",'');
		   }
		},
		/**
		 * 显示loading加载动画 num:1/2/3 1,2兼容ie10以上 3兼容ie8 parentElem 加载动画显示的区域 #id
		 */
		showLoading : function(num, parentElem) {
			var index = num, elem = parentElem;
			if (webplus.isEmpty(num)) {
				index = 2;
			}

			$(elem).find(".loading-bg").remove();
			if (webplus.isEmpty(parentElem)) {
				elem = 'body';
				$(elem).append(webplus.loadingArr(index));
			} else {
				var str = elem.substring(1);
				$(elem).append(webplus.loadingArr(index));
			}
		},
		/*
		 * 隐藏loading加载
		 */
		hideLoading : function(){
			$("body").find('[loader-box="loader"]').remove();   
		},
		/*
		 * 点击分页按钮加载动画
		 */
		pageSilderLoading:function(){
			$('.layui-laypage > a').click(function(){
				if($(this).attr('class').indexOf('layui-disabled')<0){
					webplus.showLoading();			
				}
	        })
		},
		/*
		 * 设表单高度
		 */
		initTableAutoHeight:function(){
			//table box 区域
			var $table = $('body').find(".layui-card-body>.layui-table-view");
		
			//body高度
			var bodyH = $("body").outerHeight(true);
			//顶部查询form高度
			var formH = $('body').find(".layui-form").outerHeight(true);
		  //table表头按钮组高度
			var tableToolH = $table.find(".layui-table-tool").outerHeight(true);
			//table表单头部高度
			var tableHeadH = $table.find(".layui-table-box>.layui-table-header").outerHeight(true);
			//table分页组高度
			var tablePageH = $table.find(".layui-table-page").outerHeight(true);
			//table合计组高度
			var tableTotalH = $table.find(".layui-table-total").outerHeight(true);
			
			if(webplus.isEmpty(tableToolH)){
				tableToolH = 0;
			}
			if(webplus.isEmpty(tablePageH)){
				tablePageH = 0;
			}
			if(webplus.isEmpty(tableTotalH)){
				tableTotalH = 0;
			}
			//tableBody高度
			var tableBodyH = bodyH-formH-tableToolH-tableHeadH-tablePageH-tableTotalH-15;
			
			$table.css({"height":bodyH-formH-15+"px"});
			$table.find(".layui-table-box>.layui-table-body").css({"height":tableBodyH+"px"});
			var fixedTableH = tableBodyH-16;
			$table.find(".layui-table-box>.layui-table-fixed>.layui-table-body").css({"height":fixedTableH+"px"});
		},
		loadingArr : function(num) {
			var loader1 = '<div class="loading-bg" loader-box="loader">'
					+ '<div id="loader1" class="spinner">'
					+ '<div class="spinner-container container1">'
					+ '<div class="circle1"></div>'
					+ '<div class="circle2"></div>'
					+ '<div class="circle3"></div>'
					+ '<div class="circle4"></div>' + '</div>'
					+ '<div class="spinner-container container2">'
					+ '<div class="circle1"></div>'
					+ '<div class="circle2"></div>'
					+ '<div class="circle3"></div>'
					+ '<div class="circle4"></div>' + '</div>'
					+ '<div class="spinner-container container3">'
					+ '<div class="circle1"></div>'
					+ '<div class="circle2"></div>'
					+ '<div class="circle3"></div>'
					+ '<div class="circle4"></div>' + '</div>' + '</div>'
					+ '</div>';
			var loader2 = '<div class="loading-bg" loader-box="loader">'
					+ '<div id="loader2" class="spinner">'
					+ '<div class="rect1"></div>' + '<div class="rect2"></div>'
					+ '<div class="rect3"></div>' + '<div class="rect4"></div>'
					+ '<div class="rect5"></div>' + '</div>' + '</div>';
			var loader3 = '<div class="loading-bg" loader-box="loader">'
					+ '<div id="loader3" class="spinner">' + '</div>'
					+ '</div>';
			num = this.isIE() ? 3 : num;
			switch (num) {
			case 1:
				return loader1;
				break;
			case 2:
				return loader2;
				break;
			case 3:
				return loader3;
				break;
			default:
				return loader2;
				break;
			}
		},
		/*
   		 * 成功信息提示框
		 *@info  提示信息  
		 *@callback 回调函数
		 */
		alertSuccess: function (info,callback) {
			if(!webplus.isEmpty(callback) &&  typeof(callback) === "function"){
				layer.msg(info, {icon: 1,time: 500},function(){
					callback();
				});
			}else{
				layer.msg(info, {icon: 1,time: 1000});   				
			}
	  },
	    
		/*
		 * 失败信息提示框
	  *@info  提示信息  
	  */
		alertWarn: function (info) {
			layer.alert(info, function(index){
				 layer.close(index);
			});  
		},
		/*
		 * 警告信息提示框
		 * @info  提示信息
		 */
		alertError: function (info) {
				layer.msg(info, {icon: 2,time: 1000});   
		},
		/*
		 *tip层提示
		 * @obj
		 * {
		 *  logTips:  提示信息 
		 *  id:   对象
		 * index: 1 成功提示  错误提示
		 * }
		 */
		alertTips:function(obj){
			if(obj.index==1){
				layer.tips(obj.logTips,obj.id,{tips:[3,'#67C23A'],tipsMore:true,time:500});
			}else{
				layer.tips(obj.logTips,obj.id,{tips:[3,'#f20'],tipsMore:true,time:2000});
			}
		},
		/**
		 * 检验编辑模式 当editMode=0 不能进行编辑、删除、更新
		 * 只有超级管理员才能编辑
		 */
		checkFormEditMode:function(formObj){
			// 获取当前控件                                                                  
			var selectIfKey=formObj.othis;                                                 
			 // 获取当前所在行                                                                 
			var parentTr = selectIfKey.parents("tr"); 
			var editMode = $(parentTr).find("[data-field=editMode]").find(".layui-table-cell").text(); 
			var loginAccount=webplus.getName("loginAccount");
			if(editMode=='0'&&loginAccount!='super'){
				formObj.elem.checked=!(formObj.elem.checked);
				form.render();
				webplus.alertError("当前数据模式为只读模式，只读数据不允编辑和删除等更新操作,只有超级管理员[super]才能处理");
				return false;
			}
			return true;
		},
		/**
		 * 检验编辑模式 当editMode=0 不能进行编辑、删除、更新
		 * 只有超级管理员才能编辑
		 */
		checkRowEditMode:function(rowObj){
			var editMode = rowObj.data.editMode;
			var loginAccount=webplus.getName("loginAccount");
			if(editMode=='0'&&loginAccount!='super'){
				webplus.alertError("当前数据模式为只读模式，只读数据不允编辑、删除、授权等操作,只有超级管理员[super]才能处理");
				return false;
			}
			return true;
		},
		/**
		 * 初始化表格 
		 * searchForm表达搜索框Form的ID，默认值searchForm
		 * searchSubmit 搜索框Form 中查询按钮的lay-filter 默认值searchSubmit
		 * tableId 表格的编号 默认值dataList
		 */
		init:function(url,cols,searchForm,searchSubmit,tableId,toolbar,params,height,callback){
		
			webplus.initSearchForm(searchForm,searchSubmit,tableId);
			webplus.initTable(url,cols,tableId,toolbar,params,height,'','',callback);
		},
		/** 
		 * 初始化搜索框
		 */
		initSearchForm : function(searchForm,searchSubmit,tableId) {
			
			// 初始化下拉字典框
			webplus.initSelect(searchForm);
			webplus.showLoading();
			webplus.initFormOn(searchSubmit,tableId)
			webplus.initRowClickClass(tableId);
		},
		/**
		 * 初始化单击按钮
		 */
		initFormOn:function(searchSubmit,tableId){
			if(webplus.isEmpty(searchSubmit)){
				searchSubmit='searchSubmit'
			}
			if(webplus.isEmpty(tableId)){
				tableId='dataList'
			}
			/* 加载表单 */
			form.on('submit('+searchSubmit+')', function(data) {
				//加载动画
				webplus.showLoading();
				webplus.loadTable(data.field,tableId);
	   		});
		},
		/**
		 * 初始化单击按钮
		 */
		initRowClickClass:function(tableId){
			if(webplus.isEmpty(tableId)){
				tableId='dataList'
			}
			table.on('row('+tableId+')', function(obj){
				obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		     });
		},
		/**
		 * 初始化表格
		 * url 加载数据url
		 * cols
		 * tableId,
		 * limit 每页分页条数
		 * limits 
		 */
		initTable:function(url,cols,tableId,toolbar,params,height,limit,limits,callback){
			if(webplus.isEmpty(url)){
				webplus.alertWarn("表格数据查询url不能为空");
				return false;
			}
             if(webplus.isEmpty(cols)){
				webplus.alertWarn("表格列数据模型cols不能为空")
				return false;
			}
             if(url.indexOf(cxt)==-1){
 	    		url=cxt+url;
 	    	}
			if(webplus.isEmpty(tableId)){
				tableId='#dataList';
			}else{
				if(tableId.indexOf("#")==-1){
					tableId="#"+tableId;
				}
			}
			if(webplus.isEmpty(params)){
			   params={};
			}
			if(webplus.isEmpty(limit)){
				limit=20;
			}
			if(webplus.isEmpty(limits)){
				limits=[ 10, 20, 50, 100, 200 ];
			}
			if(webplus.isEmpty(toolbar)){
				toolbar="#toolbar";
			}else{
				if(toolbar.indexOf("#")==-1){
					toolbar="#"+toolbar;
				}
			}
			var isOpen=false;
			if(webplus.isEmpty(height)){
				//height='full-67';
				isOpen=true;
			}
			params.token=webplus.getToken();
			table.render({
				elem : tableId,//表id
				url : url,
				where:params,
				method : 'post',   //请求方式
				height : height,//控制表格高度
				toolbar: toolbar,//引入表头按钮
				defaultToolbar:[],//不开启工具栏['filter', 'print']    @@@@    filter: 显示筛选图标  print: 显示打印图标
				cellMinWidth : 80, //每列最小宽度
				loading:false,
				page : true,//开启分页
				limit : limit,//默认一页显示20条信息
				limits : limits , //配置每页显示条数下拉框选项
				cols :cols,
				done:function(res){//表格加载成功后执行的函数
					
					//关闭动画
					webplus.hideLoading();
					//开启点击分页加载动画   
					webplus.pageSilderLoading();
					//设置高度
					if(isOpen){
						webplus.initTableAutoHeight();
					}
					if(typeof(callback) === "function"){
				          callback(res);
				    }
				}
			});
		},
		/**
		 * 根据键值获取选择的数据
		 * cellKey 列键
		 * tableId 表格ID
		 */
		getRowsValue:function(cellKey,tableId){
			if(webplus.isEmpty(tableId)){
				tableId="dataList";
			}
			
			var rowsData =webplus.getRowsData(tableId);
			var arr = [];
			if(rowsData.length==0){
				return arr;
			}
			$.each(rowsData,function(i){
				if(rowsData[i][cellKey] != undefined ){
					arr.push(rowsData[i][cellKey]);
				}
			});
			return arr.join(',');
		},
		/***
		 * 根据表格ID获取选择的行数据
		 * tableId 表单编号
		 */
		getRowsData:function(tableId){
			if(webplus.isEmpty(tableId)){
				tableId="dataList";
			}
			var rowsData = table.checkStatus(tableId).data;
			console.info(rowsData);
			return rowsData;
		},
		/**
		 * 初始化登记表单
		 * formId 表单ID
		 * submitId 提交按钮
		 */
		initForm:function(url,formId,submitId,callback){
			if(webplus.isEmpty(formId)){
				formId='saveForm';
			}
			webplus.initSelect(formId);
			webplus.submitFormData(url,submitId,callback);
		},
		/**
		 * 表单提交数据函数
		 * url 数据保存地址
		 * 
		 */
		submitFormData:function(url,submitId,callback){
			if(webplus.isEmpty(url)){
				webplus.alertWarn("提交保存数据请求地址[url]不能为空");
				return false;
			}

			if(url.indexOf(cxt)==-1){
	    		url=cxt+url;
	    	}
			if(webplus.isEmpty(submitId)){
				submitId='saveSubmit';
			}
			 form.on('submit('+submitId+')', function(data){
					webplus.doAjax(url,data.field,'','','1',callback)
					//阻止表单默认提交
				    return false;
			 }); 
			
		},
		/**
		 * 初始化树
		 */
		initTree:function(url,setting,treeId,params,selectId){
			if(webplus.isEmpty(url)){
				webplus.alertWarn("ztree请求地址[url]不能为空");
				return false;
			}
			if(webplus.isEmpty(setting)){
				webplus.alertWarn("ztree树配置[setting]不能为空");
				return false;
			}
			if(webplus.isEmpty(treeId)){
				treeId='ztree';
			}
			if(treeId.indexOf("#")==-1){
				treeId="#"+treeId;
			}
			if(webplus.isEmpty(params)){
				params={};
			}
			webplus.doAjax(url,params,'','0','',function(data){
				$(treeId).empty();
				var treeObj=$.fn.zTree.init($(treeId), setting, data);
				if(!webplus.isEmpty(selectId)){
					var selectNode = treeObj.getNodeByParam("id", selectId);//根据ID找到该节点
					 treeObj.selectNode(selectNode);//根据该节点选中
				}
			});
		},
		/**
		 * 初始化select,加载字典项
		 */
		initSelect : function(searchForm) {
			if(webplus.isEmpty(searchForm)){
				searchForm='#searchForm'
			}else{
				if(searchForm.indexOf("#")==-1){
					searchForm="#"+searchForm;
				}
			}
			var searchFormObj=$(searchForm);
			var $select = searchFormObj.find('select');
			if ($select.length < 1)
				return false;
			$.each($select, function() {
				var _this = this;
				var dict = $(this).attr('lay-select-dict');
				var title = $(this).attr('lay-select-title');
				// lay-filter-code = '2,3' 过滤值以","分隔
				var filter = $(this).attr('lay-filter-code');
				// 默认选中的的option lay-select-code为option的value值
				var index = $(this).attr('lay-select-code');
				// 是否禁用 true 禁用 false 不禁用 默认false
				var disabled = $(this).attr('lay-select-disabled');

				if (webplus.isEmpty(index)) {
					index = -1;
				}
				if (webplus.isEmpty(title)) {
					title = '请选择';
				}
				if (webplus.isEmpty(filter)) {
					filter = '';
				}
				if (webplus.isEmpty(disabled)) {
					disabled = false;
				}
				// 如果有配置属性select-dict
				if (!webplus.isEmpty(dict)) {
					var params={};
					params.typeCode=dict;
					params.filterCode=filter,
					params.token=webplus.getToken();
					$.post(
							cxt + "system/common/listItem",
							params,
							function(res) {
						var str = title == "NONE" ? '' : '<option value="">'
								+ title + '</option>';
						$.each(res, function(i) {
							if (index == res[i].itemCode) {
								str += "<option selected value='"
										+ res[i].itemCode + "'>"
										+ res[i].itemName + "</option>";
							} else {
								str += "<option value='" + res[i].itemCode
										+ "'>" + res[i].itemName + "</option>";
							}
						});
						$(_this).html(str);
						if (disabled) {
							$(_this).prop("disabled", "disabled");
						}
						form.render('select');
					},
					'json'
					);
				}
			});
		},
		   /*
	     *初始化表单 
	     *@table  表格对象
	     *@params   Object
	     *@tableId  表单ID  不要传 # 号
	     *@tableUrl  String 
	     */
	    loadTable: function(params,tableId,url){
	    	if(webplus.isEmpty(params)){
	    		params = {};
	    	}
	    	if(webplus.isEmpty(tableId)){
	    	     tableId='dataList';
	    	}
	    	if(webplus.isEmpty(url)){
    			table.reload(tableId, {
    				page: 1,
    				where : params
    			});	    			    			
    		}else{
    			
    			if(url.indexOf(cxt)==-1){
    	    		url=cxt+url;
    	    	}
    			table.reload(tableId, {
    				url:url,
    				page: 1,
    				where : params
    			});	
    		}
	    
	    	
	    },
	    /**
	     * 刷新列表 reloadWay 
	     * 1：弹出框调用父页面刷新 
	     * 默认刷新当前的table
	     */
        reloadTable: function(reloadWay,submitId){
        	if(reloadWay=='1'){
        		
        		//调用父页面的查询按钮刷新数据
        		if(webplus.isEmpty(submitId)){
        			parent.$("body").find('[lay-submit]').click();
        		}else{
        			var findStr='[lay-filter='+submitId+']'
        			parent.$("body").find(findStr).click();
        		}
        		
        		setTimeout(function(){
        			parent.layer.closeAll();
        		},500)
        		
        	}else if(reloadWay=='2'){ //只关闭不刷新
        		setTimeout(function(){
        			parent.layer.closeAll();
        		},500)
        	}else{
        		if(webplus.isEmpty(submitId)){
        			$("body").find('[lay-submit]').click();
        		}else{
        			var findStr='[lay-filter='+submitId+']';
        			$("body").find(findStr).click();
        		}
        		
        	}
        	
	    },
	    /**
	     * 打开编辑窗口
	     * url 请求地址
	     * title 标题
	     * width 宽度
	     * height 长度
	     */
	    openEditWindow:function(url,title,rowObj,cellKey,paramKey,width,height)	{
	    	webplus.openEditOrDetailWindow(url,title,rowObj,cellKey,paramKey,width,height);
	    },
	    /**
	     * 打开详情窗口
	     * url 请求地址
	     * title 标题
	     * width 宽度
	     * height 长度
	     */
	    openDetailWindow:function(url,title,rowObj,cellKey,paramKey,width,height){
	    	
	    	webplus.openEditOrDetailWindow(url,title,rowObj,cellKey,paramKey,width,height,'1');
	    },
	    /**
	     * 打开修改或详情窗口
	     */
	    openEditOrDetailWindow:function(url,title,rowObj,cellKey,paramKey,width,height,openWay){
	    	if(webplus.isEmpty(url)){
				webplus.alertWarn("请求地址url不能为空");
				return false;
			}
	    	if(webplus.isEmpty(rowObj)){
				webplus.alertWarn("列数据对象[rowObj]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(cellKey)){
				webplus.alertWarn("列键参数不能为空[cellKey]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(paramKey)){
	    		paramKey="id";
	    	}
	    	var data=rowObj.data;
	    	var urlParam=paramKey+"="+data[cellKey];
	    	 if(url.indexOf("?")>-1){
		        url=url+"&"+urlParam;
		       }else{
		        url=url+"?"+urlParam;
		     }
	    	webplus.openWindowBase(url,title,width,height,openWay);
	    },
	    
	    /**
	     * 获取多个数据打开
	     * url 请求地址
	     * title 标题
	     * width 宽度
	     * height 长度
	     * openWay 打开方式
	     */
	    openMoreWindow:function(url,tableId,cellKey,paramKey,title,width,height,openWay){
	    	if(webplus.isEmpty(url)){
				webplus.alertWarn("请求地址url不能为空");
				return false;
			}
	    	if(webplus.isEmpty(cellKey)){
				webplus.alertWarn("列键参数不能为空[cellKey]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(paramKey)){
	    		paramKey="ids";
	    	}
	    	var values=webplus.getRowsValue(cellKey,tableId);
	    	if(webplus.isEmpty(values)){
	    		webplus.alertError("请选择数据");
				return false;
	    	}
	    	if(url.indexOf("?")>-1){
	        	url=url+"&"+paramKey+"="+values;
	        }else{
	        	url=url+"?"+paramKey+"="+values;
	        }
	    	
	    	webplus.openWindowBase(url,title,width,height,openWay);
	    },
	    /**
	    /**
	     * 打开窗口
	     * url 请求地址
	     * title 标题
	     * width 宽度
	     * height 长度
	     */
	    openWindow:function(url,title,width,height,openWay)	{
	    	webplus.openWindowBase(url,title,width,height,openWay);
	    },
	    /**
	     * 公用打开弹出模板
	     * url 请求地址
	     * title 标题
	     * width 宽度
	     * height 长度
	     * openWay 1打开详情 默认打开增加、编辑
	     */
		openWindowBase:function(url,title,width,height,openWay){
			if(webplus.isEmpty(url)){
				webplus.alertWarn("请求地址url不能为空");
				return false;
			}
	        if(webplus.isEmpty(title)) {
	            title=false;
	        };
	        if (webplus.isEmpty(width)) {
	        	width=($(window).width()*0.98);
	        };
	        if (webplus.isEmpty(height)) {
	        	height=($(window).height() - 20);
	        };
	       var  token=webplus.getToken();
	       if(url.indexOf(cxt)==-1){
	    		url=cxt+url;
	    	}
	        if(url.indexOf("?")>-1){
	        	url=url+"&token="+token
	        }else{
	        	url=url+"?token="+token
	        }
	        if(openWay=='1'){
	        	layer.open({
		            type: 2,
		            area: [width+'px', height +'px'],//配置宽高
		            shade:0.4, //遮罩透明度
		            id:'addinfo-iframe',  //iframeID值
		            title: title, //弹出框头部信息
		            content: url,  //打开的页面地址
		            btnAlign: 'c',  //底部按钮居中
		            btn: ['取消'],  //底部按钮信息
		            success:function (layero,index) {  
		            	//给底部按钮加上小图标和颜色
		            	$("body").find(".layui-layer-btn0").html('<i class="layui-icon layui-icon-close"></i> 取消').css({"backgroundColor":"#FF5722","color":"#fff","borderColor":"#ff5720"});
		            
		               
		            },
		            yes:function(index){
		            	layer.close(index);
		            }
		        });
	        }else{
	        	layer.open({
		            type: 2,
		            area: [width+'px', height +'px'],//配置宽高
		            shade:0.4, //遮罩透明度
		            id:'addinfo-iframe',  //iframeID值
		            title: title, //弹出框头部信息
		            content: url,  //打开的页面地址
		            btnAlign: 'c',  //底部按钮居中
		            btn: ['确定', '取消'],  //底部按钮信息
		            success:function (layero,index) {  
		            	//给底部按钮加上小图标和颜色
		            	$("body").find(".layui-layer-btn0").html('<i class="layui-icon layui-icon-ok"></i> 提交');
		            	$("body").find(".layui-layer-btn1").html('<i class="layui-icon layui-icon-close"></i> 取消').css({"backgroundColor":"#FF5722","color":"#fff","borderColor":"#ff5720"});
		            },
		            yes:function(index){
		            	//点击提交按钮触发子页面的提交表单事件
		            	$("#addinfo-iframe>iframe").contents().find('[lay-submit]').click();
		            },
		            btn2:function(index){
		            	layer.close(index);
		            }
		        });
	        }
	        
	    },
	    /**
	     * 关闭当前窗口
	     */
	    closeCurrentWindow:function(){
	    	 var index = parent.layer.getFrameIndex(window.name);
			  parent.layer.close(index);//关闭当前
	    },
	    /**
	     * 删除列表的一行
	     * rowObj 行对象
	     * cellKey 列键
	     * paramKey 参数键跟cellKey传值使用
	     * params 参数数组
	     * confirmMsg 确认信息
	     * callback 回调函数
	     */
	    removeRow:function(url,rowObj,cellKey,paramKey,params,confirmMsg,callback){
	    	if(webplus.isEmpty(rowObj)){
				webplus.alertWarn("列数据对象[rowObj]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(cellKey)){
				webplus.alertWarn("列键参数不能为空[cellKey]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(paramKey)){
	    		paramKey="id";
	    	}
	    	if(webplus.isEmpty(params)){
	    		params = {};
	    	}
	    	if(webplus.isEmpty(confirmMsg)){
	    		confirmMsg='你确认删除当前数据吗？';
	    	}
	    	var data=rowObj.data;
	    	params[paramKey]=data[cellKey];
	    	webplus.doAjax(url,params,confirmMsg,'','0',callback);
	    	
	    },
	    /**
	     * 批量删除多行数据
	     */
	    batchRemoveRow:function(url,tableId,cellKey,paramKey,params,confirmMsg,callback){
	    	if(webplus.isEmpty(cellKey)){
				webplus.alertWarn("列键参数不能为空[cellKey]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(paramKey)){
	    		paramKey="ids";
	    	}
	    	if(webplus.isEmpty(params)){
	    		params = {};
	    	}
	    	if(webplus.isEmpty(confirmMsg)){
	    		confirmMsg='你确认删除当前数据吗？';
	    	}
	    	var values=webplus.getRowsValue(cellKey,tableId);
	    	if(webplus.isEmpty(values)){
	    		webplus.alertError("请选择你要删除数据");
				return false;
	    	}
	    	params[paramKey]=values;
	    	webplus.doAjax(url,params,confirmMsg,'','0',callback);
	    },
	    /**
	     * 批量操作
	     */
	    batchOperate:function(url,tableId,cellKey,paramKey,params,confirmMsg,callback){
	    	if(webplus.isEmpty(cellKey)){
				webplus.alertWarn("列键参数不能为空[cellKey]不能为空");
				return false;
			}
	    	if(webplus.isEmpty(paramKey)){
	    		paramKey="ids";
	    	}
	    	if(webplus.isEmpty(params)){
	    		params = {};
	    	}
	    	
	    	var values=webplus.getRowsValue(cellKey,tableId);
	    	if(webplus.isEmpty(values)){
	    		webplus.alertError("请选择你要操作数据");
				return false;
	    	}
	    	params[paramKey]=values;
	    	webplus.doAjax(url,params,confirmMsg,'','0',callback);
	    },
	    /**
	     * 执行ajax方法
	     * url    请求地址
	     * params 请求参数数组
	     * confirmMsg 是否执行确认操作
	     * resultWay 完成方式 0 直接回调处理 默认当前处理
	     * reloadWay 刷新方式 1父页面刷新列表 0默认刷新当前table列表
	     * callback 回调函数
	     */
	    doAjax:function(url,params,confirmMsg,resultWay,reloadWay,callback){
	    	if(webplus.isEmpty(confirmMsg)){
	    		webplus.ajaxBase(url,params,'','',function(data){
	    			webplus.doAjaxResult(data,resultWay,reloadWay,callback);
	    		})
	    	}else{
	    		layer.confirm(confirmMsg, function(index){
			      	  layer.close(index);
			      	  webplus.ajaxBase(url,params,'','',function(data){
			      		
			      		webplus.doAjaxResult(data,resultWay,reloadWay,callback);
		    		})
			    });
	    	}
	    	
	    },
	    /**
	     * 处理ajax结果
	     * data  返回结果集
	     * resultWay 完成方式 0 直接回调处理 默认当前处理
	     * reloadWay 是否刷新列表 1父页面刷新列表 0默认刷新当前table列表
	     * callback 回调函数
	     */
	    doAjaxResult:function(data,resultWay,reloadWay,callback){
	    	
      			if(resultWay=='0'){
      				if(typeof(callback) === "function"){  
      					 
	        				callback(data);
				     }
    			}else{
    				if(data){
    					if (data.appcode == "1") {
    						if(reloadWay=='3'){  //不等于3的时候成功不提醒 这种一般使用开关按钮ajax
    							 if(typeof(callback) === "function"){ 
    	   								
    	   	  	        				callback(data);
    	       					  }
    						}else{
    							webplus.alertSuccess(data.appmsg);
   							 if(typeof(callback) === "function"){ 
   	   								
   	   	  	        				callback(data);
   	       					  }else{
   	       						if(!webplus.isEmpty(reloadWay)){
   	            					  webplus.reloadTable(reloadWay);
   	            				 }
   	       					  }
    						}
          					
        				}else if(data.appcode=="0"){
        					webplus.alertWarn(data.appmsg);
        				}else {
        					webplus.alertError(data.appmsg);
        				}
    				}
    				
    		 }
      		
	    },
	    /**
	     * ajaxBase执行方法
	     */
	    ajaxBase:function(url,params,type,dataType,callback){
	    	if(webplus.isEmpty(url)){
				webplus.alertWarn("请求地址url不能为空");
				return false;
			}
	    	if(webplus.isEmpty(params)){
	    		params = {};
	    	}
	    	if(webplus.isEmpty(type)){
	    		type='post';
	    	}
	    	if(webplus.isEmpty(dataType)){
	    		dataType='json';
	    	}
	    	if(url.indexOf(cxt)==-1){
	    		url=cxt+url;
	    	}
	    	params.token=webplus.getToken();
	    	$.ajax({
					type :type,
					url : url,
					data : params,
					timeout:30000,
					beforeSend:webplus.showLoading(),
					dataType :dataType,
					success : function(data) {
						if( typeof(callback) === "function"){
						       
					          callback(data);
					    }
					},
					error : function(jqXHR, textStatus, errorThrown) {
						webplus.alertError('操作失败,网络超时或程序处理异常：'+textStatus);
					},
					complete:function(){
		    			//隐藏动画
		    			webplus.hideLoading();
		    		}
			})
	    },
	    initMain:function(){
	    	$("#LAY-system-side-menu>li").on('mouseenter',function(){
				if($("#LAY_app").attr("class") == 'layadmin-side-shrink'){
					var index = $("#LAY-system-side-menu>li").index(this);
					$(this).find("dl").css(getNavPositionStyle(index));
				}
		});
		$("#LAY-system-side-menu>li").on('mouseleave',function(){
			if($("#LAY_app").attr("class") == 'layadmin-side-shrink'){
					$(this).find("dl").css({'top': '0px'});
			}
		});
		$("#LAY-system-side-menu>li>a").click(function(event){
		     $(this).next("dl").css({'top': '0px'});
			 $(this).parent("li").addClass('layui-nav-itemed').siblings("li").removeClass('layui-nav-itemed');
		
		});
		function getNavPositionStyle(index){
			var top = 50;
			if(index>0){
				top = parseInt(index*40)+parseInt(top);
			}
			return {'top': top+'px'};
		}
		webplus.lockPage(false);
	    },
	    
	    lockPage:function(c){
	    	if (c || sessionStorage.getItem('lockStatus') === '1') {
	    		layer.prompt({
	                formType: 1,
	                closeBtn: 0,
	                title: '请输入登录密码解锁',
	                btn: ['解锁']
	                },function(value, index){
	              	var params={};
	              	params.password=hex_md5(value);;
	              	webplus.doAjax("system/main/unlockScreen",params,"","","3",function(data){
	              		sessionStorage.removeItem('lockStatus');
	              		layer.close(index);
	  		    	 });
	  			  
	  			});
	    	}
	    }
	}
	exports("webplus", webplus);
});