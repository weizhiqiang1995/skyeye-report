/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.common.util;

import cn.hutool.json.JSONUtil;
import com.skyeye.common.constans.Constants;
import com.skyeye.common.object.ObjectConstant;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 工具类
 */
public class ToolUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ToolUtil.class);
	
	/**
	 * 
	     * @Title: useLoop
	     * @Description: 检查数组是否包含某个值的方法
	     * @param @param arr
	     * @param @param targetValue
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean useLoop(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.equals(targetValue))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	     * @Title: useLoopIndex
	     * @Description: 检查数组是否包含某个值的方法
	     * @param @param arr
	     * @param @param targetValue
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static int useLoopIndex(String[] arr, String targetValue) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(targetValue))
				return i;
		}
		return -1;
	}

	/**
	 * 
	     * @Title: getUrlParams
	     * @Description: 将url参数转换成map
	     * @param @param param aa=11&bb=22&cc=33
	     * @param @return    参数
	     * @return Map<String,Object>    返回类型
	     * @throws
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<>();
		if (ToolUtil.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=", 2);
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}
	
	/**
	 * 
	     * @Title: isBlank
	     * @Description: 判断字符串是否为空
	     * @param @param str
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * 
	     * @Title: isNumeric
	     * @Description: 判断是不是数字
	     * @param @param str
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean isNumeric(String str) {
		return match(ObjectConstant.VerificationParams.NUM.getRegular(), str);
	}
	
	/**
	 * 
	     * @Title: isEmail
	     * @Description: 验证邮箱
	     * @param @param str
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean isEmail(String str) {
		return match(ObjectConstant.VerificationParams.EMAIL.getRegular(), str);
	}
	
	/**
	 * 
	     * @Title: isDate
	     * @Description: 验证日期时间
	     * @param @param str
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean isDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 设置lenient为false,否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/**
	 * 
	     * @Title: match
	     * @Description: 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	     * @param @param regex
	     * @param @param str
	     * @param @return    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 
	     * @Title: getTimeAndToString
	     * @Description: 获取当前日期(2016-12-29 11:23:09)
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 
	     * @Title: getTimeStrAndToString
	     * @Description: 获取当前日期(20161229112309123)
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getTimeStrAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 
	     * @Title: getYmdTimeAndToString
	     * @Description: 获取当前年月日(2016-12-29)
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getYmdTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = df.format(dt);
		return nowTime;
	}

	/**
	 * 获取指定月后各N个月的月份日期(包含指定月)
	 *
	 * @param yearMonth 月，格式为yyyy-MM
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> getPointMonthAfterMonthList(String yearMonth, int n) throws Exception {
		List<String> list = new ArrayList<>();
		list.add(yearMonth);
		for(int i = 1; i <= n; i++){
			// 获取往后的日期
			list.add(ToolUtil.getSpecifiedDayMation(yearMonth, "yyyy-MM", 1, i, 10));
		}
		return list;
	}

	/**
	 * 获取指定月份的所有日期
	 *
	 * @param months 月份集合，格式为yyyy-MM
	 * @return
	 */
	public static List<String> getDaysByMonths(List<String> months){
		List<String> monthDays = new ArrayList<>();
		for (String month: months){
			monthDays.addAll(ToolUtil.getMonthFullDay(Integer.parseInt(month.split("-")[0]), Integer.parseInt(month.split("-")[1])));
		}
		return monthDays;
	}

	/**
	 * 
	 * @Title: getWeek
	 * @Description: 判断指定日期（yyyy-MM-dd）是周几
	 * @param dates
	 * @return
	 * @return: int
	 * @throws
	 */
	public static int getWeek(String dates) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(d);
		int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekDay == 0){
			weekDay = 7;
		}
		return weekDay;
	}
	
	/**
	 * 判断指定日期（yyyy-MM-dd）是单周还是双周
	 *
	 * @param dates 指定日期
	 * @return 1是双周，0是单周
	 */
	public static int getWeekType(String dates) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = f.parse(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(d);
		int weekNum = cal.get(Calendar.WEEK_OF_YEAR);
		if(weekNum % 2 == 0){
			// 双周
			return 1;
		}else{
			// 单周
			return 0;
		}
	}
	
	/**
	 * 
	     * @Title: getHmsTimeAndToString
	     * @Description: 获取当前时分秒(11:23:09)
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getHmsTimeAndToString() {
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String nowTime = df.format(dt);
		return nowTime;
	}
	
	/**
	 * 
	 * @Title: getDays
	 * @Description: 获取两个日期之间的所有日期
	 * @param startTime 开始日期
	 * @param endTime 结束日期
	 * @return
	 * @return: List<String>
	 * @throws
	 */
	public static List<String> getDays(String startTime, String endTime) {
		// 返回的日期集合
		List<String> days = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date start = dateFormat.parse(startTime);
			Date end = dateFormat.parse(endTime);

			Calendar tempStart = Calendar.getInstance();
			tempStart.setTime(start);

			Calendar tempEnd = Calendar.getInstance();
			tempEnd.setTime(end);
			tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
			while (tempStart.before(tempEnd)) {
				days.add(dateFormat.format(tempStart.getTime()));
				tempStart.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
	
	/**
	 * 
	     * @Title: getSurFaceId
	     * @Description: 获取ID
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getSurFaceId() {
		UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	/**
	 * 
	     * @Title: MD5
	     * @Description: MD5加密技术
	     * @param @param str
	     * @param @return
	     * @param @throws Exception    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String MD5(String str) throws Exception {
		byte[] bt = str.getBytes();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] bt1 = md.digest(bt);
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < bt1.length; i++) {
			int val = ((int) bt1[i]) & 0xff;
			if (val < 16)
				sbf.append("0");
			sbf.append(Integer.toHexString(val));
		}
		return sbf.toString();
	}
	
	/**
     * 使用递归方法建树
     * @param allMenu allMenu
     * @return
     */
	public static List<Map<String, Object>> allMenuToTree(List<Map<String, Object>> allMenu){
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(Map<String, Object> bean : allMenu){
			if ("0".equals(bean.get("parentId").toString())) {
				resultList.add(findChildren(bean, allMenu, 0));
            }
		}
		return resultList;
	}
	
	/**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> findChildren(Map<String, Object> treeNode, List<Map<String, Object>> treeNodes, int level) {
    	List<Map<String, Object>> child = null;
        for (Map<String, Object> it : treeNodes) {
        	if(Integer.parseInt(it.get("menuLevel").toString()) == level + 1){
        		if(treeNode.get("id").toString().equals(it.get("parentId").toString().split(",")[level])) {
        			child = (List<Map<String, Object>>) treeNode.get("childs");
        			if (child == null) {
        				child = new ArrayList<>();
        			}
        			child.add(findChildren(it, treeNodes, level + 1));
        			treeNode.put("childs", child);
        		}
        	}
        }
        return treeNode;
    }
    
    /**
     * 使用递归建树
     * @param deskTops
     * @return
     */
    public static List<Map<String, Object>> deskTopsTree(List<Map<String, Object>> deskTops){
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(Map<String, Object> bean : deskTops){
			if ("0".equals(bean.get("parentId").toString())) {
				resultList.add(findChildren(bean, deskTops, 0));
            }
		}
		for(Map<String, Object> bean : deskTops){
			if(!findChildren(resultList, bean.get("id").toString())){
				resultList.add(bean);
			}
		}
		return resultList;
	}
    
    /**
     * 递归判断id是否在集合中存在
     * @param treeNode
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	public static boolean findChildren(List<Map<String, Object>> treeNode, String id){
    	List<Map<String, Object>> child = null;
    	for(Map<String, Object> bean : treeNode){
    		if(id.equals(bean.get("id").toString())){
    			return true;
    		}else{
    			child = (List<Map<String, Object>>) bean.get("childs");
    			if(child != null){
    				boolean in = findChildren(child, id);
    				if(in){
    					return in;
    				}
    			}
			}
		}
    	return false;
    }
	
    /**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				logger.info("delete file success: {}", fileName);
				return true;
			} else {
				logger.warn("delete file fail: {}", fileName);
				return false;
			}
		} else {
			logger.warn("delete file fail, because this file is not exit: {}", fileName);
			return false;
		}
	}
	
	/**
	 * 随机不重复的6-8位
	 * @return
	 */
	public static int card() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++) {
			result = result * 10 + array[i];
		}
		return result;
	}
	
	/**
	 * 将表名转为Java经常使用的名字，如code_model转CodeModel
	 * @param str
	 * @return
	 */
	public static String replaceUnderLineAndUpperCase(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		int count = sb.indexOf("_");
		while (count != 0) {
			int num = sb.indexOf("_", count);
			count = num + 1;
			if (num != -1) {
				char ss = sb.charAt(count);
				char ia = (char) (ss - 32);
				sb.replace(count, count + 1, ia + "");
			}
		}
		String result = sb.toString().replaceAll("_", "");
		return StringUtils.capitalize(result);
	}
	
	/**
	 * 首字母转小写
	 *
	 * @param str 要转换的字符串
	 * @return
	 */
	public static String toLowerCaseFirstOne(String str) {
		if (Character.isLowerCase(str.charAt(0))){
			return str;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
		}
	}
	
	/**
	 * 
	     * @Title: getDateStr
	     * @Description: 将日期转化为正常的年月日时分秒
	     * @param @param timeStmp
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getDateStr(long timeStmp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date(timeStmp));
	}
	
	/**
	 * 根据request获取ip
	 * @param request
	 * @return
	 */
	public static String getIpByRequest(HttpServletRequest request){
		String ip;
		ip = request.getHeader("x-forwarded-for");
		// 针对IP是否使用代理访问进行处理
		if (ToolUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ToolUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ToolUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(0, ip.indexOf(","));
		}
		if(!isBlank(request.getParameter("loginPCIp"))){
			for(String str : Constants.FILTER_FILE_IP_OPTION) {
				if (ip.indexOf(str) != -1) {
					ip = request.getParameter("loginPCIp");
					break;
				}
			}
		}
		return ip;
	}
	
	/**
	 * 获得CPU使用率.
	 * @return
	 */
	public static double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "//system32//wbem//wmic.exe process get Caption,CommandLine,"
					+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(Constants.PERCENT * (busytime) / (busytime + idletime)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}
	
	/**
	 * 读取CPU信息
	 * @param proc
	 * @return
	 */
	public static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < Constants.FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				String caption = Bytes.substring(line, capidx, cmdidx - 1).trim();
				String cmd = Bytes.substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					idletime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx - 1).replaceAll(" ", "").trim()).longValue();
					idletime += Long.valueOf(Bytes.substring(line, umtidx, wocidx - 1).replaceAll(" ", "").trim()).longValue();
					continue;
				}
				if(!isBlank(Bytes.substring(line, kmtidx, rocidx - 1).trim())){
					kneltime += Long.valueOf(Bytes.substring(line, kmtidx, rocidx - 1).replaceAll(" ", "").trim()).longValue();
				}
				if(!isBlank(Bytes.substring(line, umtidx, wocidx - 1).trim())){
					usertime += Long.valueOf(Bytes.substring(line, umtidx, wocidx - 1).replaceAll(" ", "").trim()).longValue();
				}
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * listToTree
	 * <p>方法说明<p>
	 * 将JSONArray数组转为树状结构
	 * @param arr 需要转化的数据
	 * @param id 数据唯一的标识键值
	 * @param pId 父id唯一标识键值
	 * @param child 子节点键值
	 * @return JSONArray
	 */
	public static List<Map<String, Object>> listToTree(List<Map<String, Object>> arr, String id, String pId, String child) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, Object> hash = new HashMap<>();
		// 将数组转为Object的形式，key为数组中的id
		for (int i = 0; i < arr.size(); i++) {
			Map<String, Object> json = arr.get(i);
			hash.put(json.get(id).toString(), json);
		}
		// 遍历结果集
		for (int j = 0; j < arr.size(); j++) {
			// 单条记录
			Map<String, Object> aVal = arr.get(j);
			// 在hash中取出key为单条记录中pid的值
			Map<String, Object> hashVP = (Map<String, Object>) hash.get(aVal.get(pId).toString());
			// 如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
			if (hashVP == null) {
				aVal.put(child, listToTreeTem(arr, id, pId, child, aVal.get(id).toString()));
				result.add(aVal);
			}
		}
		return result;
	}

	private static List<Map<String, Object>> listToTreeTem(List<Map<String, Object>> list, String id, String pId, String child, String idValue){
		List<Map<String, Object>> resultList = new ArrayList<>();
		for(Map<String, Object> bean : list){
			if (idValue.equals(bean.get(pId).toString())) {
				bean.put(child, listToTreeTem(list, id, pId, child, bean.get(id).toString()));
				resultList.add(bean);
			}
		}
		return resultList;
	}

	/**
	 * javaBean2Map
	 * @param javaBean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> javaBean2Map(Object javaBean) {
		Map<K, V> ret = new HashMap<>();
		try {
			Method[] methods = javaBean.getClass().getDeclaredMethods();// 获取所有的属性
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(javaBean, (Object[]) null);// invoke(调用)就是调用Method类代表的方法。它可以让你实现动态调用
					ret.put((K) field, (V) (null == value ? "" : value));
				}
			}
		} catch (Exception ee) {
			logger.warn("covert {} to map failed.", javaBean.getClass().toString(), ee);
		}
		return ret;
	}
	
	/**
	 * 
	     * @Title: randomStr
	     * @Description: 获取指定的随机值
	     * @param @param minLen
	     * @param @param maxLen
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String randomStr(int minLen, int maxLen) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		int length = random.nextInt(maxLen - minLen) + minLen;
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 
	     * @Title: getTime
	     * @Description: 转化cron
	     * @param @param date
	     * @param @param foramt
	     * @param @return    参数
	     * @return int    返回类型
	     * @throws
	 */
	public static int getTime(Date date, String foramt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if ("y".equals(foramt)) {
			return cal.get(Calendar.YEAR);// 获取年份
		} else if ("M".equals(foramt)) {
			return cal.get(Calendar.MONTH) + 1;// 获取月
		} else if ("d".equals(foramt)) {
			return cal.get(Calendar.DAY_OF_MONTH);// 获取日
		} else if ("H".equals(foramt)) {
			return cal.get(Calendar.HOUR_OF_DAY);// 获取时
		} else if ("m".equals(foramt)) {
			return cal.get(Calendar.MINUTE);// 获取分
		} else if ("s".equals(foramt)) {
			return cal.get(Calendar.SECOND);// 获取秒
		} else {
			return -1;
		}
	}
	
	/**
	 * 获取指定日期 前/后n天|分钟的日期
	 *
	 * @param date 日期
	 * @param n N天/分钟
	 * @param format 标识：d代表天，m代表分钟
	 * @return
	 */
	public static Date getAfDate(Date date, int n, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if ("d".equals(format)) {
			calendar.add(Calendar.DAY_OF_MONTH, n);// 天
		} else if ("m".equals(format)) {
			calendar.add(Calendar.MINUTE, n);// 分钟
		}
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 
	     * @Title: formatDateByPattern
	     * @Description: 日期转换cron表达式  e.g:yyyy-MM-dd HH:mm:ss
	     * @param @param date
	     * @param @param dateFormat
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String formatDateByPattern(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	/**
	 * 日期转换cron表达式
	 *
	 * @param date yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static String getCrons1(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateFormat = "ss mm HH dd MM ? yyyy";
		return formatDateByPattern(df.parse(date), dateFormat);
	}
	
	/**
	 * 两个时间之间相差距离多少分
	 * 
	 * @param str1 时间参数 1，格式：yyyy-MM-dd HH:mm:ss：
	 * @param str2 时间参数 2，格式：yyyy-MM-dd HH:mm:ss：
	 * @return 相差天数
	 */
	public static long getDistanceDays(String str1, String str2) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long min = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			min = diff / (1000 * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return min;
	}
	
	/**
	 * 时间字符串比较大小，字符串格式为：yyyy-MM-dd hh:mm:ss
	 *
	 * @param time1 日期1
	 * @param time2 日期2
	 * @return 日期1早于日期2，返回true,反之返回false
	 * @throws ParseException
	 */
	public static boolean compare(String time1, String time2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return compareTime(sdf, time1, time2);
	}
	
	/**
	 * 时间字符串比较大小，字符串格式为：HH:mm
	 *
	 * @param time1 日期1
	 * @param time2 日期2
	 * @return 日期1早于日期2，返回true,反之返回false
	 * @throws ParseException
	 */
	public static boolean compareTime(String time1, String time2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return compareTime(sdf, time1, time2);
	}
	
	/**
	 * 
	     * @Title: compareTime
	     * @Description: 时分秒字符串比较大小
	     * @param @param time1
	     * @param @param time2
	     * @param @return
	     * @param @throws ParseException    参数
	     * @return boolean    返回类型
	     * @throws
	 */
	public static boolean compareTimeHMS(String time1, String time2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return compareTime(sdf, time1, time2);
	}
	
	public static boolean compareTime(SimpleDateFormat sdf, String time1, String time2) throws ParseException {
		// 将字符串形式的时间转化为Date类型的时间
		Date t1 = sdf.parse(time1);
        Date t2 = sdf.parse(time2);
		// Date类的一个方法，如果t1早于t2返回true，否则返回false
		if (!t1.after(t2))
			return true;
		else
			return false;
	}
	
	/**
	 * 
	     * @Title: getTalkGroupNum
	     * @Description: 获取聊天群号
	     * @param @return    参数
	     * @return String    返回类型
	     * @throws
	 */
	public static String getTalkGroupNum() {
		String numStr = "";
		String trandStr = String.valueOf((Math.random() * 9 + 1) * 10000);
		String dataStr = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
		numStr = trandStr.toString().substring(0, 5);
		numStr = numStr + dataStr;
		return numStr;
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static long getTimeStampAndToString() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 
	 	 * @Title: getSpecifiedDayMation
	 	 * @Description: 根据指定时间获取各种时间乐行之后的时间
		 * @param dateStr 指定时间
		 * @param dateType 时间格式：yyyy-MM-dd hh:mm:ss
		 * @param beforeOrAfter 往前计算还是往后计算 0:时间往前推;1：时间往后推
		 * @param num 时间长度
		 * @param remindType 往前计算或者往后计算是按照天还是小时还是分钟计算[1,2,3,4]:分钟;[5,6]:小时;[7,8,9]:天;[10]:月
		 * @return
	 */
	public static String getSpecifiedDayMation(String dateStr, String dateType, int beforeOrAfter, int num, int remindType) throws Exception{
		Calendar c = Calendar.getInstance();
		Date date = null;
		date = new SimpleDateFormat(dateType).parse(dateStr); 
		c.setTime(date);
		if(beforeOrAfter == 0)
			num = -num;
		if(remindType == 1 || remindType == 2 || remindType == 3 || remindType == 4){//分钟
			c.add(Calendar.MINUTE, num);
		}else if(remindType == 5 || remindType == 6){//小时
			c.add(Calendar.HOUR_OF_DAY, num);
		}else if(remindType == 7 || remindType == 8 || remindType == 9){//天
			c.add(Calendar.DAY_OF_MONTH, num);
		}else if(remindType == 10){//月
			c.add(Calendar.MONTH, num);
		}else{
			return null;
		}
		String dayAfter = new SimpleDateFormat(dateType).format(c.getTime());
		return dayAfter;
	}
	
	/**
	 * 获取视频截图
	 * @param videoLocation
	 * @param imageLocation
	 * @return
	 */
	public static boolean take(String videoLocation, String imageLocation, String ffmpegGPath){
		// 低精度
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegGPath);//视频提取工具的位置
		commend.add("-i");
		commend.add(videoLocation);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("08.010");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("352x240");
		commend.add(imageLocation);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * bytes转M或者KB
	 * @param size
	 * @return
	 */
	public static String sizeFormatNum2String(long size) {
		String s = "";
		if (size > 1024 * 1024)
			s = String.format("%.2f", (double) size / 1048576) + "M";
		else
			s = String.format("%.2f", (double) size / (1024)) + "KB";
		return s;
	}
	
	/**
	 * 两个时间之间相差距离多少时分秒
	 * 
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差时分秒数
	 */
	public static String getDistanceHMS(String str1, String str2) throws Exception {
		long diff = getDistanceMillisecondHMS(str1, str2);
		long hour = diff / (1000 * 60 * 60);
		long minute = (diff - hour * 60 * 60 * 1000) / (1000 * 60);
		long second = (diff - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		return hour + ":" + minute + ":" + second;
	}

	/**
	 * 两个时间之间相差距离多少分钟
	 *
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差分钟
	 * @throws Exception
	 */
	public static String getDistanceMinuteHMS(String str1, String str2) throws Exception {
		long diff = getDistanceMillisecondHMS(str1, str2);
		return CalculationUtil.divide(
				String.valueOf(diff),
				CalculationUtil.multiply("1000", "60", 2), 2);
	}

	/**
	 * 两个时间之间相差距离多少毫秒
	 *
	 * @param str1 时间参数 1：
	 * @param str2 时间参数 2：
	 * @return 相差分钟
	 * @throws Exception
	 */
	public static long getDistanceMillisecondHMS(String str1, String str2) throws Exception {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		try {
			Date one = df.parse(str1);
			Date two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			return diff;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取四位随机码
	 * @return
	 */
	public static String getFourWord() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		return sb.toString();
	}
	
	/**
	 * 将文件父id变换
	 * @param folderNew
	 * @param folderId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void FileListParentISEdit(List<Map<String, Object>> folderNew, String folderId) {
		for(Map<String, Object> folder : folderNew){
			folder.put("newParentId", folderId + ",");
			if(folder.get("children") != null){
				List<Map<String, Object>> child = (List<Map<String, Object>>) folder.get("children");
				FileListParentISEdit(child, folder.get("newParentId").toString() + folder.get("newId").toString());
			}
		}
	}
	
	/**
	 * 将树转化为list
	 * @param folderNew
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> FileTreeTransList(List<Map<String, Object>> folderNew) {
		List<Map<String, Object>> beans = new ArrayList<>();
		for(Map<String, Object> folder : folderNew){
			if(folder.get("children") != null){
				List<Map<String, Object>> child = (List<Map<String, Object>>) folder.get("children");
				beans.addAll(FileTreeTransList(child));
				folder.remove("children");
			}
			beans.add(folder);
		}
		return beans;
	}

	/**
	 * 文件复制
	 * @param source 源文件
	 * @param target 拷贝后的文件地址
	 * @throws Exception
	 */
	public static void NIOCopyFile(String source, String target) throws Exception {
		// 1.采用RandomAccessFile双向通道完成，rw表示具有读写权限
		RandomAccessFile fromFile = new RandomAccessFile(source, "rw");
		FileChannel fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile(target, "rw");
		FileChannel toChannel = toFile.getChannel();
		long count = fromChannel.size();
		while (count > 0) {
			long transferred = fromChannel.transferTo(fromChannel.position(), count, toChannel);
			count -= transferred;
		}
		if (fromFile != null) {
			fromFile.close();
		}
		if (fromChannel != null) {
			fromChannel.close();
		}
		if (toFile != null) {
			toFile.close();
		}
		if (toChannel != null) {
			toChannel.close();
		}
	}
	
	/**
	 * 往数组中添加新元素
	 * @param arr
	 * @param num
	 * @return
	 */
	public static int[] addElementToArray(int[] arr, int num) {
		int[] result = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		result[result.length - 1] = num;
		return result;
	}
	
	/**
	 * 往数组中添加新元素
	 * @param arr
	 * @param num
	 * @return
	 */
	public static String[] addElementToArray(String[] arr, String num) {
		String[] result = new String[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			result[i] = arr[i];
		}
		result[result.length - 1] = num;
		return result;
	}
	
	/**
	 * 文件夹打包
	 * @param zipOut 压缩包流
	 * @param beans 树结构文件
	 * @param baseDir 扩展路径
	 * @param fileBath 压缩包输出路径
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	public static void recursionZip(ZipOutputStream zipOut, List<Map<String, Object>> beans, String baseDir, String fileBath) throws Exception {
		String[] zipFileNames = {};
		int[] zipFileNamesNum = {};
		for(Map<String, Object> bean : beans){
			// 文件压缩包中的文件名
			String name = bean.get("fileName").toString();
			if(ToolUtil.useLoop(zipFileNames, name)){
				zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)] = zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)] + 1;
				if ("folder".equals(bean.get("fileType").toString())) {//文件夹
					name = name + "(" + String.valueOf(zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)]) + ")";
				}else{//文件
					name = name.substring(0, name.lastIndexOf(".")) + "(" + String.valueOf(zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)]) + ")." + bean.get("fileType").toString();
				}
			}
			zipFileNames = ToolUtil.addElementToArray(zipFileNames, name);
			zipFileNamesNum = ToolUtil.addElementToArray(zipFileNamesNum, 0);
			if ("folder".equals(bean.get("fileType").toString())) {//文件夹
				// 空文件夹的处理
				zipOut.putNextEntry(new ZipEntry(baseDir + name + "/"));
				// 没有文件，不需要文件的copy
				zipOut.closeEntry();
				if(bean.containsKey("children") && !isBlank(bean.get("children").toString())){
					recursionZip(zipOut, (List<Map<String, Object>>) bean.get("children"), baseDir + name + "/", fileBath);
				}
			} else {//文件
				byte[] buf = new byte[1024];
				InputStream input = new FileInputStream(new File(fileBath + bean.get("fileAddress").toString()));
				zipOut.putNextEntry(new ZipEntry(baseDir + name));
				int len;
				while ((len = input.read(buf)) != -1) {
					zipOut.write(buf, 0, len);
				}
				zipOut.closeEntry();
				input.close();
			}
		}
	}
	
	/**
	 * 获取倒数第num个后面的内容
	 */
	public static String getSubStr(String str, int num) {
		String result = "";
		int i = 0;
		while (i < num) {
			int lastFirst = str.lastIndexOf('/');
			result = str.substring(lastFirst) + result;
			str = str.substring(0, lastFirst);
			i++;
		}
		return result.substring(1);
	}
	
	/**
	 * 获取集合中的文件夹
	 */
	public static List<Map<String, Object>> getFolderByList(List<Map<String, Object>> beans) {
		List<Map<String, Object>> items = new ArrayList<>();
		for(Map<String, Object> bean : beans){
			if("folder".equals(bean.get("fileExtName").toString())){
				items.add(bean);
			}
		}
		return items;
	}
	
	/**
	 * 获取集合中的文件
	 */
	public static List<Map<String, Object>> getFileByList(List<Map<String, Object>> beans) {
		List<Map<String, Object>> items = new ArrayList<>();
		for(Map<String, Object> bean : beans){
			if(!"folder".equals(bean.get("fileExtName").toString())){
				items.add(bean);
			}
		}
		return items;
	}

	/**
	 * 判断是否是json串
	 * @param content
	 * @return
	 */
	public static boolean isJson(String content) {
		try {
			JSONUtil.toBean(content, null);
			return true;
		} catch (Exception e) {
			try {
				JSONUtil.toList(content, null);
				return true;
			} catch (Exception e2) {
				return false;
			}
		}
	}
	
	/**
	 * 获取中文首字母
	 * 
	 * @param c 中文字符串
	 * @return
	 */
	public static String chineseToFirstLetter(String c) {
		String string = "";
		int a = c.length();
		for (int k = 0; k < a; k++) {
			String d = String.valueOf(c.charAt(k));
			String str = converterToFirstSpell(d);
			String s = str.toUpperCase();
			char h;
			for (int y = 0; y <= 0; y++) {
				h = s.charAt(0);
				string += h;
			}
		}
		return string;
	}

	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			String s = String.valueOf(nameChar[i]);
			if (s.matches("[\\u4e00-\\u9fa5]")) {
				try {
					String[] mPinyinArray = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
					pinyinName += mPinyinArray[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 纳秒时间戳和随机数生成唯一的随机数
	 * 
	 * @return
	 */
	public static String getUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(900000000) + 100000000;
		return System.currentTimeMillis() + String.valueOf(number);
	}
	
	/**
	 * 小程序/H5下载打包
	 * @param zipOut
	 * @param beans
	 * @param baseDir
	 * @param fileBath
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	public static void recursionSmProZip(ZipOutputStream zipOut, List<Map<String, Object>> beans, String baseDir, String fileBath) throws Exception {
		String[] zipFileNames = {};
		int[] zipFileNamesNum = {};
		for(Map<String, Object> bean : beans){
			String name = bean.get("fileName").toString();//文件压缩包中的文件名
			if(ToolUtil.useLoop(zipFileNames, name)){
				zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)] = zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)] + 1;
				if ("folder".equals(bean.get("fileType").toString())) {//文件夹
					name = name + "(" + String.valueOf(zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)]) + ")";
				}else{//文件
					name = name.substring(0, name.lastIndexOf(".")) + "(" + String.valueOf(zipFileNamesNum[ToolUtil.useLoopIndex(zipFileNames, name)]) + ")." + bean.get("fileType").toString();
				}
			}
			zipFileNames = ToolUtil.addElementToArray(zipFileNames, name);
			zipFileNamesNum = ToolUtil.addElementToArray(zipFileNamesNum, 0);
			if ("folder".equals(bean.get("fileType").toString())) {//文件夹
				// 空文件夹的处理
				zipOut.putNextEntry(new ZipEntry(baseDir + name + "/"));
				// 没有文件，不需要文件的copy
				zipOut.closeEntry();
				if(bean.containsKey("children") && !isBlank(bean.get("children").toString())){
					recursionSmProZip(zipOut, (List<Map<String, Object>>) bean.get("children"), baseDir + name + "/", fileBath);
				}
			} else {//文件
				byte[] buf = new byte[1024];
				int len;
				if(bean.containsKey("content") && !isBlank(bean.get("content").toString())){
					ByteArrayInputStream stream = new ByteArrayInputStream(bean.get("content").toString().getBytes());
					zipOut.putNextEntry(new ZipEntry(baseDir + name + "." + bean.get("fileType").toString()));
					while ((len = stream.read(buf)) != -1) {
						zipOut.write(buf, 0, len);
					}
					stream.close();
				}else{
					InputStream input = new FileInputStream(new File(fileBath + bean.get("filePath").toString()));
					zipOut.putNextEntry(new ZipEntry(baseDir + name));
					while ((len = input.read(buf)) != -1) {
						zipOut.write(buf, 0, len);
					}
					input.close();
				}
				zipOut.closeEntry();
			}
		}
	}
	
	/**
	 * 获取文件内容
	 * @param fileName
	 * @return
	 */
	public static String readFileContent(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		StringBuffer sbf = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempStr;
			while ((tempStr = reader.readLine()) != null) {
				sbf.append(tempStr);
			}
			reader.close();
			return sbf.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sbf.toString();
	}
	
	/**
	 * 通过身份证号码获取出生日期、性别、年龄
	 * 
	 * @param certificateNo
	 * @return 返回的出生日期格式：1990-01-01 性别格式：F-女，M-男
	 */
	public static Map<String, String> getBirAgeSex(String certificateNo) {
		String birthday = "";
		String age = "";
		String sexCode = "";

		int year = Calendar.getInstance().get(Calendar.YEAR);
		char[] number = certificateNo.toCharArray();
		boolean flag = true;
		if (number.length == 15) {
			for (int x = 0; x < number.length; x++) {
				if (!flag)
					return new HashMap<String, String>();
				flag = Character.isDigit(number[x]);
			}
		} else if (number.length == 18) {
			for (int x = 0; x < number.length - 1; x++) {
				if (!flag)
					return new HashMap<String, String>();
				flag = Character.isDigit(number[x]);
			}
		}
		if (flag && certificateNo.length() == 15) {
			birthday = "19" + certificateNo.substring(6, 8) + "-" + certificateNo.substring(8, 10) + "-"
					+ certificateNo.substring(10, 12);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length()))
					% 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
		} else if (flag && certificateNo.length() == 18) {
			birthday = certificateNo.substring(6, 10) + "-" + certificateNo.substring(10, 12) + "-"
					+ certificateNo.substring(12, 14);
			sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1))
					% 2 == 0 ? "F" : "M";
			age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("birthday", birthday);
		map.put("age", age);
		map.put("sexCode", sexCode);
		return map;
	}
	
	/**
	 * 
	     * @Title: timeFormat
	     * @Description: 时间转换
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */ 
	public static String timeFormat(String time) throws Exception {
		String returnTime = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dateTime = formatter.parse(time);
		long interval = (new Date().getTime() - dateTime.getTime())/1000;
		if(interval <= 0){
            returnTime = "刚刚";
        }else if(interval > 0 && interval < 60){
			returnTime = interval + "秒前";
		}else if(interval >= 60 && interval < 3600){
			returnTime = interval/60 + "分钟前";
		}else if(interval >= 3600 && interval <= 3600 * 24){
			returnTime = interval/3600 + "小时前";
		}else if(interval >= 3600 * 24 && interval <= 3600 * 48){
			returnTime = "昨天 " + hm.format(dateTime);
		}else if(interval >= 3600 * 48 && interval <= 3600 * 72){
			returnTime = "前天 " + hm.format(dateTime);
		}else{
			returnTime = ymdhm.format(dateTime);
		}
		return returnTime;
	}
	
	/**
	 * 判断保存附件的文件夹是否存在，不存在则创建
	 * @param basePath
	 */
	public static void createFolder(String basePath){
		// 创建目录
		File pack = new File(basePath);
		if(!pack.isDirectory()){
			// 目录不存在 
			try {
				// 创建目录
				pack.mkdirs();
			} catch (Exception e) {
				logger.warn("create folder [{}] failed.", basePath);
			}
		}
	}
	
	/**
	 * 判断该邮件在邮箱中是否包含
	 * @param emailHasMail
	 * @param messageId
	 * @return false 不存在；true 存在
	 */
	public static boolean judgeInListByMessage(List<Map<String, Object>> emailHasMail, String messageId){
		if(emailHasMail != null && !emailHasMail.isEmpty() && !ToolUtil.isBlank(messageId)){
			for(Map<String, Object> bean : emailHasMail){
				if(bean.containsKey("messageId") && messageId.equals(bean.get("messageId").toString())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	    * @Title: createFile
	    * @Description: 创建文件并写入内容
	    * @param @param basePath 基础路径
	    * @param @param fileType 文件类型
	    * @param @param content 文件内容
	    * @param @return
	    * @param @throws IOException    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String createFile(String basePath, String fileType, String content) throws IOException {
	    createFolder(basePath);
	    String fileName = String.valueOf(System.currentTimeMillis());
	    File checkFile = new File(basePath + "/" + fileName + "." + fileType);
	    FileWriter writer = null;
	    try {
	        // 二、检查目标文件是否存在，不存在则创建
	        if (!checkFile.exists()) {
	            checkFile.createNewFile();// 创建目标文件
	        }
	        // 三、向目标文件中写入内容
	        // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
	        writer = new FileWriter(checkFile, true);
	        writer.append(content);
	        writer.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (null != writer)
	            writer.close();
	    }
	    return basePath.replaceAll("\\\\", "/") + fileName + "." + fileType;
	}
	
	/**
	 * 
	 * @Title: checkOverlap
	 * @Description: 比较时分时间段是否有重复
	 * @param list ->[08:00-09:00,13:00-16:30]
	 * @return
	 * @throws ParseException
	 * @return: boolean
	 * @throws
	 */
	public static boolean checkOverlap(List<String> list) throws ParseException {
		// 排序ASC
		Collections.sort(list);
		// 是否重叠标识
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			// 跳过第一个时间段不做判断
			String[] itime = list.get(i).split("-");
			for (int j = (i + 1); j < list.size(); j++) {
				// 如果当前遍历的i开始时间小于j中某个时间段的结束时间那么则有重叠，反之没有重叠
				String[] jtime = list.get(j).split("-");
				// 此处compare为日期比较(返回true:date1小/相等、返回false:date1大)
				boolean compare = compare((getYmdTimeAndToString() + " " + itime[1] + ":00"),
						(getYmdTimeAndToString() + " " + jtime[0] + ":00"));
				if (!compare) {
					flag = true;
					// 只要存在一个重叠则可退出内循环
					break;
				}
			}
			// 当标识已经认为重叠了则可退出外循环
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 获取上个月的年月，格式yyyy-MM
	 * @return
	 */
	public static String getLastMonthDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return sdf.format( cal.getTime());
	}

	/**
	 * 获取某年某月的所有日期（yyyy-mm-dd格式字符串）
	 * @param year 年
	 * @param month 月
	 * @return
	 */
    public static List<String> getMonthFullDay(int year, int month) {
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        // 清除信息
        cal.clear();
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month - 1);
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return fullDayList;
    }

	/**
	 * 计算两个日期相差多少天
	 *
	 * @param startDate 开始日期 yyyy-MM-dd/yyyy-MM-dd HH:mm:ss
	 * @param endDate 结束日期 yyyy-MM-dd/yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(String startDate, String endDate) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		// 开始日期
		cal.setTime(sdf.parse(startDate));
		long time1 = cal.getTimeInMillis();
		// 结束日期
		cal.setTime(sdf.parse(endDate));
		long time2 = cal.getTimeInMillis();
		// 计算
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * java将字符串转换成可执行代码 工具类
	 *
	 * @param jexlExp
	 * @param map
	 * @return
	 */
	public static Object convertToCode(String jexlExp, Map<String, String> map) {
		JexlEngine jexl = new JexlEngine();
		Expression expression = jexl.createExpression(jexlExp);
		JexlContext jc = new MapContext();
		for (String key : map.keySet()) {
			jc.set(key, map.get(key));
		}
		if (null == expression.evaluate(jc)) {
			return "";
		}
		return expression.evaluate(jc);
	}

}
