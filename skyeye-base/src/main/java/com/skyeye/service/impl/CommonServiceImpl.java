/**
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved.
 */
package com.skyeye.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import cn.hutool.json.JSONUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.skyeye.common.constans.Constants;
import com.skyeye.common.object.InputObject;
import com.skyeye.common.object.OutputObject;
import com.skyeye.common.util.ToolUtil;
import com.skyeye.dao.CommonDao;
import com.skyeye.dao.SysEveWinBgPicDao;
import com.skyeye.dao.SysEveWinLockBgPicDao;
import com.skyeye.dao.SysEveWinThemeColorDao;
import com.skyeye.jedis.JedisClientService;
import com.skyeye.service.CommonService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private SysEveWinBgPicDao sysEveWinBgPicDao;
	
	@Autowired
	private SysEveWinLockBgPicDao sysEveWinLockBgPicDao;
	
	@Autowired
	private SysEveWinThemeColorDao sysEveWinThemeColorDao;
	
	@Autowired
	public JedisClientService jedisClient;
	
	@Value("${IMAGES_PATH}")
	private String tPath;

	/**
	 * 
	     * @Title: uploadFile
	     * @Description: 上传文件
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public void uploadFile(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(inputObject.getRequest().getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(inputObject.getRequest())) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) inputObject.getRequest();
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			int type = Integer.parseInt(map.get("type").toString());
			String basePath = "";
			//决定存储路径
			switch (type) {
			case 1://小程序上传
				basePath = tPath + "\\upload\\smpropic" ;
				break;
			case 2://系统桌面背景自定义图片上传
				basePath = tPath + "\\upload\\winbgpic" ;
				break;
			case 3://系统桌面锁屏背景自定义图片上传
				basePath = tPath + "\\upload\\winlockbgpic" ;
				break;
			case 4://系统桌面背景自定义图片上传用户自定义
				basePath = tPath + "\\upload\\winbgpic";
				break;
			case 5://系统桌面锁屏背景自定义图片上传用户自定义
				basePath = tPath + "\\upload\\winlockbgpic";
				break;
			case 6://用户头像
				basePath = tPath + "\\upload\\userphoto";
				break;
			case 7://聊天群组头像
				basePath = tPath + "\\upload\\talkgroup";
				break;
			case 8://系统图片
				basePath = tPath + "\\upload\\syswin";
				break;
			case 9://聊天图片
				basePath = tPath + "\\upload\\talkpic";
				break;
			case 10://聊天附件
				basePath = tPath + "\\upload\\talkfile";
				break;
			case 11://富文本内容图片
				basePath = tPath + "\\upload\\edit";
				break;
			case 12://菜单logo图片
				basePath = tPath + "\\upload\\menulogo";
				break;
			case 18://报表基础设置背景图
				basePath = tPath + "\\upload\\reportBgImage";
				break;
			default:
				basePath = tPath;
				break;
			}
			Map<String, Object> bean = new HashMap<>();
			String trueFileName = "";
			String fileName = "";
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				fileName = file.getOriginalFilename();// 文件名称
				//得到文件扩展名
				String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (file != null) {
					File pack = new File(basePath);
					if(!pack.isDirectory()) {//目录不存在
						pack.mkdirs();//创建目录
					}
					// 自定义的文件名称
    				String newFileName = String.valueOf(System.currentTimeMillis()) + "." + fileExtName;
					String path = basePath + "\\" + newFileName;
					// 上传
					file.transferTo(new File(path));
					newFileName = Constants.FileUploadPath.getVisitPath(type) + newFileName;
					if(ToolUtil.isBlank(trueFileName)){
						trueFileName = newFileName;
					}else{
						trueFileName = trueFileName + "," + newFileName;
					}
				}

			}
			bean.put("picUrl", trueFileName);
			bean.put("type", type);
			bean.put("fileName", fileName);
			outputObject.setBean(bean);
		}
	}

	/**
	 * 
	     * @Title: uploadFileBase64
	     * @Description: 上传文件Base64
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void uploadFileBase64(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		int type = Integer.parseInt(map.get("type").toString());
		String dataPrix = "";
		String data = "";//数据
		String imgStr = map.get("images").toString();
		imgStr = imgStr.replaceAll("\\+", "%2B").replaceAll(" ", "+");
		boolean a = false;//判断后缀是否为图片
        String fileType = null;//文件后缀
        String [] d = imgStr.split("base64,");
        // 决定存储路径
		String basePath = tPath + Constants.FileUploadPath.getSavePath(type);
		//上传数据是否合法
        if(d != null && d.length == 2){
        	dataPrix = d[0];
            data = d[1];
          //获取上传图片后缀并判断数据是否合法
            if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
    			fileType = "jpg";
    			a = true;
            } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
            	fileType = "ico";
            	a = true;
            } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
            	fileType = "gif";
            	a = true;
            } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
            	fileType = "png";
            	a = true;
            }else{
            	outputObject.setreturnMessage("文件类型不正确，只允许上传jpg,png,jpeg格式的图片");
            }
            if(a){
    			try {
    				byte[] bytes = Base64.decodeBase64(new String(data).getBytes());
    				File dirname = new File(basePath);
    				if (!dirname.isDirectory()) {// 目录不存在
						dirname.mkdirs(); // 创建目录
					}
    				// 自定义的文件名称
    				String trueFileName = String.valueOf(System.currentTimeMillis()) + "." + fileType;
    				// 设置存放图片文件的路径
    				String path = basePath + "\\" + trueFileName; 
    				ByteArrayInputStream in = new ByteArrayInputStream(bytes);
    				byte[] buffer = new byte[1024];
    				FileOutputStream out = new FileOutputStream(path);
    				int byteread;
    				while ((byteread = in.read(buffer)) > 0) {
    					out.write(buffer, 0, byteread); // 文件写操作
    				}
    				out.flush();
    				out.close();
    				Map<String, Object> bean = new HashMap<>();
					trueFileName = Constants.FileUploadPath.getVisitPath(type) + trueFileName;
    				bean.put("picUrl", trueFileName);
    				bean.put("type", type);
    				outputObject.setBean(bean);
    			} catch (Exception e) {

    			}
    		} else {
    			outputObject.setreturnMessage("文件类型不正确，只允许上传jpg,png,jpeg格式的图片");
    		}
        }else{
        	outputObject.setreturnMessage("上传失败，数据不合法");
        }
	}

	/**
	 * 
	     * @Title: downloadFileByJsonData
	     * @Description: 代码生成器生成下载文件
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void downloadFileByJsonData(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		JSONArray array = JSONArray.fromObject(map.get("jsonData").toString());
		List<Map<String, Object>> inBeans = new ArrayList<>();
		Map<String, Object> user = inputObject.getLogParams();
		String zipName = ToolUtil.getSurFaceId() + ".zip";
		String strZipPath = tPath + "/" + zipName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
		byte[] buffer = new byte[1024];
		
		for(int i = 0; i < array.size(); i++){
			JSONObject object = (JSONObject) array.get(i);
			//加入压缩包
			ByteArrayInputStream stream = new ByteArrayInputStream(object.getString("content").getBytes());
			if("javascript".equals(object.getString("modelType").toLowerCase())){
				out.putNextEntry(new ZipEntry(object.getString("fileName") + ".js"));
			}else{
				out.putNextEntry(new ZipEntry(object.getString("fileName") + "." + object.getString("modelType").toLowerCase()));
			}
			int len;
			// 读入需要下载的文件的内容，打包到zip文件
			while ((len = stream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.closeEntry();
			Map<String, Object> bean = new HashMap<>();
			bean.put("id", ToolUtil.getSurFaceId());
			bean.put("tableName", object.getString("tableName"));
			bean.put("groupId", object.getString("groupId"));
			bean.put("modelId", object.getString("modelId"));
			bean.put("content", object.getString("content"));
			bean.put("createId", user.get("id"));
			bean.put("fileName", object.getString("fileName"));
			if("javascript".equals(object.getString("modelType").toLowerCase())){
				bean.put("fileType", "js");
			}else{
				bean.put("fileType", object.getString("modelType").toLowerCase());
			}
			bean.put("filePath", zipName);
			bean.put("createTime", ToolUtil.getTimeAndToString());
			inBeans.add(bean);
		}
		out.close();
		commonDao.insertCodeModelHistory(inBeans);
	}

	/**
	 * 
	     * @Title: querySysWinMationById
	     * @Description: 获取win系统桌列表信息供展示
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void querySysWinMationById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		//获取win系统桌面图片列表供展示
		List<Map<String, Object>> winBgPic = null;
		if(ToolUtil.isBlank(jedisClient.get(Constants.getSysWinBgPicRedisKey()))){
			winBgPic = sysEveWinBgPicDao.querySysEveWinBgPicListToShow(map);
			jedisClient.set(Constants.getSysWinBgPicRedisKey(), JSONUtil.toJsonStr(winBgPic));
		}else{
			winBgPic = JSONArray.fromObject(jedisClient.get(Constants.getSysWinBgPicRedisKey()).toString());
		}
		//获取win系统锁屏桌面图片列表供展示
		List<Map<String, Object>> winLockBgPic = null;
		if(ToolUtil.isBlank(jedisClient.get(Constants.getSysWinLockBgPicRedisKey()))){
			winLockBgPic = sysEveWinLockBgPicDao.querySysEveWinBgPicListToShow(map);
			jedisClient.set(Constants.getSysWinLockBgPicRedisKey(), JSONUtil.toJsonStr(winLockBgPic));
		}else{
			winLockBgPic = JSONArray.fromObject(jedisClient.get(Constants.getSysWinLockBgPicRedisKey()).toString());
		}
		//获取win系统主题颜色列表供展示
		List<Map<String, Object>> winThemeColor = null;
		if(ToolUtil.isBlank(jedisClient.get(Constants.getSysWinThemeColorRedisKey()))){
			winThemeColor = sysEveWinThemeColorDao.querySysEveWinThemeColorListToShow(map);
			jedisClient.set(Constants.getSysWinThemeColorRedisKey(), JSONUtil.toJsonStr(winThemeColor));
		}else{
			winThemeColor = JSONArray.fromObject(jedisClient.get(Constants.getSysWinThemeColorRedisKey()).toString());
		}
		map.put("winBgPic", winBgPic);
		map.put("winLockBgPic", winLockBgPic);
		map.put("winThemeColor", winThemeColor);
		outputObject.setBean(map);
		outputObject.settotal(1);
	}

	/**
	 * 
	     * @Title: queryAllPeopleToTree
	     * @Description: 人员选择获取所有公司和人
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void queryAllPeopleToTree(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		List<Map<String, Object>> beans = commonDao.queryAllPeopleToTree(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 
	     * @Title: queryCompanyPeopleToTreeByUserBelongCompany
	     * @Description: 人员选择根据当前用户所属公司获取这个公司的人
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void queryCompanyPeopleToTreeByUserBelongCompany(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		Map<String, Object> user = inputObject.getLogParams();
		Map<String, Object> company = commonDao.queryCompanyMationByUserId(user);//根据用户信息获取该用户所属的公司id。部门id，职位id
		map.put("companyId", company.get("companyId"));
		List<Map<String, Object>> beans = commonDao.queryCompanyPeopleToTreeByUserBelongCompany(map);
		outputObject.setBeans(beans);
	}
	
	/**
	 * 
	     * @Title: queryDepartmentPeopleToTreeByUserBelongDepartment
	     * @Description: 人员选择根据当前用户所属公司获取这个公司部门展示的人
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void queryDepartmentPeopleToTreeByUserBelongDepartment(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		Map<String, Object> user = inputObject.getLogParams();
		Map<String, Object> company = commonDao.queryCompanyMationByUserId(user);//根据用户信息获取该用户所属的公司id。部门id，职位id
		map.put("companyId", company.get("companyId"));
		List<Map<String, Object>> beans = commonDao.queryDepartmentPeopleToTreeByUserBelongDepartment(map);
		outputObject.setBeans(beans);
	}
	
	/**
	 * 
	     * @Title: queryJobPeopleToTreeByUserBelongJob
	     * @Description: 人员选择根据当前用户所属公司获取这个公司岗位展示的人
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void queryJobPeopleToTreeByUserBelongJob(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		Map<String, Object> user = inputObject.getLogParams();
		Map<String, Object> company = commonDao.queryCompanyMationByUserId(user);//根据用户信息获取该用户所属的公司id。部门id，职位id
		map.put("companyId", company.get("companyId"));
		List<Map<String, Object>> beans = commonDao.queryJobPeopleToTreeByUserBelongJob(map);
		outputObject.setBeans(beans);
	}
	
	/**
	 * 
	     * @Title: querySimpleDepPeopleToTreeByUserBelongSimpleDep
	     * @Description: 人员选择根据当前用户所属公司获取这个公司同级部门展示的人
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void querySimpleDepPeopleToTreeByUserBelongSimpleDep(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		Map<String, Object> user = inputObject.getLogParams();
		Map<String, Object> company = commonDao.queryCompanyMationByUserId(user);//根据用户信息获取该用户所属的公司id。部门id，职位id
		map.put("departmentId", company.get("departmentId"));
		List<Map<String, Object>> beans = commonDao.querySimpleDepPeopleToTreeByUserBelongSimpleDep(map);
		outputObject.setBeans(beans);
	}
	
	/**
	 * 
	     * @Title: queryTalkGroupUserListByUserId
	     * @Description: 根据聊天组展示用户
	     * @param @param inputObject
	     * @param @param outputObject
	     * @param @throws Exception    参数
	     * @return void    返回类型
	     * @throws
	 */
	@Override
	public void queryTalkGroupUserListByUserId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map = compareSelUserListByParams(map, inputObject);
		Map<String, Object> user = inputObject.getLogParams();
		map.put("createId", user.get("id"));
		List<Map<String, Object>> beans = commonDao.queryTalkGroupUserListByUserId(map);
		outputObject.setBeans(beans);
	}
	
	/**
	 * 获取人员列表时的参数转换
	 * @param map
	 * @param inputObject
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> compareSelUserListByParams(Map<String, Object> map, InputObject inputObject) throws Exception{
		String chooseOrNotMy = map.get("chooseOrNotMy").toString();//人员列表中是否包含自己--1.包含；其他参数不包含
		if(!"1".equals(chooseOrNotMy)){
			Map<String, Object> user = inputObject.getLogParams();
			map.put("userId", user.get("id"));
		}
		String chooseOrNotEmail = map.get("chooseOrNotEmail").toString();//人员列表中是否必须绑定邮箱--1.必须；其他参数没必要
		if("1".equals(chooseOrNotEmail)){
			map.put("hasEmail", "1");
		}
		return map;
	}
	
}
