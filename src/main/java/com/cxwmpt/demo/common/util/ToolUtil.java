package com.cxwmpt.demo.common.util;

import com.alibaba.fastjson.JSON;

import com.xiaoleilu.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工具类
 * @author Administrator
 */
public class ToolUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(ToolUtil.class);


	/**
	 * 获取客户端的ip信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		LOGGER.info("ipadd : " + ip);
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		LOGGER.info(" ip --> " + ip);
		return ip;
	}
	
	/**
     * 将bean转换成map
     * @param condition
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> convertBeanToMap(Object condition) {
		if (condition == null) {
			return null;
		}
		if (condition instanceof Map) {
			return (Map<String, Object>) condition;
		}
		Map<String, Object> objectAsMap = new HashMap<String, Object>();
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(condition.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
			Method reader = pd.getReadMethod();
			if (reader != null&&!"class".equals(pd.getName()))
				try {
					objectAsMap.put(pd.getName(), reader.invoke(condition));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		}
		return objectAsMap;
	}


	/**
	 * 判断请求是否是ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }

	/***
	 * @param ip
	 * @return
	 */
	public static Map<String,String> getAddressByIP(String ip) {
		String area = "";
		String province = "";
		String city = "";
		String isp = "";
		HashMap finalMap = new HashMap();
		//Map finalMap = Maps.newHashMap();
		try{
			if("0:0:0:0:0:0:0:1".equals(ip)){
				ip = "0.0.0.0";
			}
			StringBuilder sb = new StringBuilder("http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=");
			sb.append(ip);
			String result= HttpUtil.get(sb.toString(), "GBK");
			Map resultMap = JSON.parseObject(result,Map.class);
			String status = (String) resultMap.get("err");

			province = (String) resultMap.get("pro");
			city = (String) resultMap.get("city");
			if("noprovince".equalsIgnoreCase(status)){
				area = (String) resultMap.get("addr");
			}else{
				area = "中国";
				String addr = (String) resultMap.get("addr");
				if(StringUtils.isNotBlank(addr)){
					isp = addr.split(" ")[1];
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}


		finalMap.put("area",area);
		finalMap.put("province",province);
		finalMap.put("city",city);
		finalMap.put("isp",isp);
		return finalMap;
	}

	/**
	 * 获取操作系统,浏览器及浏览器版本信息
	 * @param request
	 * @return
	 */
	public static Map<String,String> getOsAndBrowserInfo(HttpServletRequest request){
		Map<String,String> map = new HashMap<>();
		String  browserDetails  =   request.getHeader("User-Agent");

		String userAgent = browserDetails;

		if (browserDetails == null || browserDetails.trim().length() == 0) {
			map.put("os","智能柜C#");
			map.put("browser","智能柜C#");
		} else {
			if (userAgent.toLowerCase() == null || userAgent.toLowerCase().trim().length() == 0) {
				map.put("os","智能柜C#");
				map.put("browser","智能柜C#");
			}

			String  user  =   userAgent.toLowerCase();

			String os = "";
			String browser = "";

			//=================OS Info==================== ===
			if (userAgent.toLowerCase().contains("windows"))
			{
				os = "Windows";
			} else if(userAgent.toLowerCase().contains("mac"))
			{
				os = "Mac";
			} else if(userAgent.toLowerCase().contains("x11"))
			{
				os = "Unix";
			} else if(userAgent.toLowerCase().contains("android"))
			{
				os = "Android";
			} else if(userAgent.toLowerCase().contains("iphone"))
			{
				os = "IPhone";
			}else{
				os = "UnKnown, More-Info: "+userAgent;
			}
			//===============Browser===========================
			if (user.contains("edge"))
			{
				browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
			} else if (user.contains("msie"))
			{
				String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
				browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
			} else if (user.contains("safari") && user.contains("version"))
			{
				browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
						+ "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if ( user.contains("opr") || user.contains("opera"))
			{
				if(user.contains("opera")){
					browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
							+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
				}else if(user.contains("opr")){
					browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
							.replace("OPR", "Opera");
				}

			} else if (user.contains("chrome"))
			{
				browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
			} else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  ||
					(user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
					(user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) )
			{
				browser = "Netscape-?";

			} else if (user.contains("firefox"))
			{
				browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
			} else if(user.contains("rv"))
			{
				String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
				browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
			} else
			{
				browser = "UnKnown, More-Info: "+userAgent;
			}
			map.put("os",os);
			map.put("browser",browser);
		}
		return map;
	}


	/**
	 * 生成一个UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	/**
	 * 时间戳转时间
	 * @param s
	 * @return
	 */
	public static String stampToDate(String s){

		String res;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long lt = new Long(s);

		Date date = new Date(lt);

		res = simpleDateFormat.format(date);

		return res;

	}

	/**
	 * 时间转时间戳
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static String dateToStamp(String s) throws ParseException {

		String res;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = simpleDateFormat.parse(s);

		long ts = date.getTime();

		res = String.valueOf(ts);

		return res;

	}

	public static String getSerialNumber() {
		long cnt = new Random().nextInt(8999999) + 1000000L;
		String num = System.currentTimeMillis() + "" + cnt +"";
		return num;
	}

	public static void main(String args[]) throws Exception {

		System.out.println(getSerialNumber());
	}
}
