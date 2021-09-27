/*******************************************************************************
 * Copyright 卫志强 QQ：598748873@qq.com Inc. All rights reserved. 开源地址：https://gitee.com/doc_wei01/skyeye
 ******************************************************************************/
package com.skyeye.common.constans;

import cn.hutool.core.util.ArrayUtil;
import com.skyeye.common.util.ToolUtil;

import java.util.*;

/**
 * 
 * @ClassName: Constans
 * @Description: 常量类
 * @author 卫志强
 * @date 2018年6月7日
 *
 */
public class Constants {

	/**
	 * 过滤器过滤内容请求项
	 */
	public static final String[] FILTER_FILE_CATALOG_OPTION = { "/html", "/css", "/js", "/assets", "/tpl", "/images",
			"/template", "/static", ".json", ".css", ".js", ".gif", ".jpg", ".eot", ".svg", ".ttf", ".woff", ".woff2",
			".mp4", ".rmvb", ".avi", "3gp", ".html", ".less", ".otf", ".scss", ".ico", "/upload", "/actuator",
			"/service", "/talkwebsocket", "/phonetalkwebsocket" };

	/**
	 * 服务器远程
	 */
	public static int MACHINE_USER_USED = 0; // 使用中
	public static int MACHINE_USER_USE = 1; // 可以使用
	
	/**
	 * 中文
	 */
	public static String LANGUAGE_ZH = "zh";
	
	/**
	 * 英文
	 */
	public static String LANGUAGE_CN = "cn";

	/**
	 * IP过滤
	 */
	public static final String[] FILTER_FILE_IP_OPTION = { "0:0:0:0:0:0:0:1", "127.0.0.1" };

	/**
	 * 过滤器过滤请求类型项
	 */
	public static final String[] FILTER_FILE_REQUEST_OPTION = { "/post", "/websocket", "/service" };

	/**
	 * 登录页面
	 */
	public static final String LOGIN_PAGE = "/tpl/index/login.html";

	/**
	 * 控制页面
	 */
	public static final String INDEX_PAGE = "/tpl/index/index.html";

	/**
	 * 404
	 */
	public static final String FZF_PAGE = "/tpl/sysmessage/404.html";

	/**
	 * 500
	 */
	public static final String FZZ_PAGE = "/tpl/sysmessage/500.html";

	/**
	 * 403
	 */
	public static final String FZT_PAGE = "/tpl/sysmessage/403.html";

	/**
	 * 系统请求参数集合
	 */
	public static Map<String, Map<String, Object>> REQUEST_MAPPING = null;

	/**
	 * 网页请求发送的contentType格式
	 */
	public static final String CONENT_TYPE_WEB_REQ = "application/x-www-form-urlencoded";

	/**
	 * json数据请求发送的数据格式
	 */
	public static final String CONENT_TYPE_JSON_REQ = "application/json";

	/**
	 * 菜单类型
	 */
	public static final String SYS_MENU_TYPE_IS_IFRAME = "win";
	public static final String SYS_MENU_TYPE_IS_HTML = "html";

	/**
	 * 菜单链接打开类型，父菜单默认为1.1：打开iframe，2：打开html。
	 */
	public static final String SYS_MENU_OPEN_TYPE_IS_IFRAME = "1";
	public static final String SYS_MENU_OPEN_TYPE_IS_HTML = "2";

	/**
	 * 保存模板说明的redis的key
	 */
	public static final String REDIS_CODEMODEL_EXPLAIN_EXEXPLAINTOCODEMODEL = "exexplaintocodemodel";// 代码生成器模板规范说明key
	public static final String REDIS_CODEMODEL_EXPLAIN_EXEXPLAINTODSFORMCONTENT = "exexplaintodsformcontent";// 动态表单内容项模板规范说明key
	public static final String REDIS_CODEMODEL_EXPLAIN_EXEXPLAINTORMPROPERTY = "exexplaintormproperty";// 小程序标签属性模板规范说明key
	public static final String REDIS_CODEMODEL_EXPLAIN_EXEXPLAINTODSFORMDISPLAYTEMPLATE = "exexplaintodsformdisplaytemplate";// 动态表单数据展示模板规范说明key

	/**
	 * 微信小程序页面id的序列号
	 */
	public static final String REDIS_PROJECT_PAGE_FILE_PATH = "projectpagefilepath";// 页面路径的序列号key
	public static final String REDIS_PROJECT_PAGE_FILE_NAME = "projectpagefilename";// 页面名称的序列号key
	public static final String REDIS_PROJECT_PAGE_FILE_PATH_NUM = "1000";// 页面路径的序列号默认值
	public static final String REDIS_PROJECT_PAGE_FILE_NAME_NUM = "1000";// 页面名称的序列号默认值

	/**
	 * 可以设置长些，防止读到运行此次系统检查时的cpu占用率，就不准了
	 */
	public static final int CPUTIME = 5000;
	public static final int PERCENT = 100;
	public static final int FAULTLENGTH = 10;

	// win系统桌面图片列表的redis的key
	public static final String SYS_WIN_BG_PIC_REDIS_KEY = "sys_win_bg_pic_redis_key";

	public static String getSysWinBgPicRedisKey() {
		return SYS_WIN_BG_PIC_REDIS_KEY;
	}

	// win系统锁屏桌面图片列表的redis的key
	public static final String SYS_WIN_LOCK_BG_PIC_REDIS_KEY = "sys_win_lock_bg_pic_redis_key";

	public static String getSysWinLockBgPicRedisKey() {
		return SYS_WIN_LOCK_BG_PIC_REDIS_KEY;
	}

	// win系统主题颜色列表的redis的key
	public static final String SYS_WIN_THEME_COLOR_REDIS_KEY = "sys_win_theme_color_redis_key";

	public static String getSysWinThemeColorRedisKey() {
		return SYS_WIN_THEME_COLOR_REDIS_KEY;
	}

	// 开发文档获取一级分类列表的redis的key
	public static final String SYS_DEVE_LOP_DOC_FIRST_TYPE = "sys_deve_lop_doc_first_type";

	public static String getSysDeveLopDocFirstType() {
		return SYS_DEVE_LOP_DOC_FIRST_TYPE;
	}

	// 开发文档获取二级分类列表的redis的key
	public static final String SYS_DEVE_LOP_DOC_SECOND_TYPE = "sys_deve_lop_doc_second_type_";

	public static String getSysDeveLopDocSecondType(String parentId) {
		return SYS_DEVE_LOP_DOC_SECOND_TYPE + parentId;
	}

	// 开发文档获取文档标题列表的redis的key
	public static final String SYS_DEVE_LOP_DOC_TITLE_LIST = "sys_deve_lop_doc_title_list_";

	public static String getSysDeveLopDocTitleList(String parentId) {
		return SYS_DEVE_LOP_DOC_TITLE_LIST + parentId;
	}

	// 开发文档获取文档内容的redis的key
	public static final String SYS_DEVE_LOP_DOC_CONTENT = "sys_deve_lop_doc_content_";

	public static String getSysDeveLopDocContent(String id) {
		return SYS_DEVE_LOP_DOC_CONTENT + id;
	}

	// 消息在redis中已读的key
	public static final String SYS_ALWAYS_READ_MESSAGE_BY_ID = "sys_always_read_message_by_id_";

	public static String getAlwaysReadMessageById(String id) {
		return SYS_ALWAYS_READ_MESSAGE_BY_ID + id;
	}

	// 消息在redis中已删除的key
	public static final String SYS_ALWAYS_DEL_MESSAGE_BY_ID = "sys_always_del_message_by_id_";

	public static String getAlwaysDelMessageById(String id) {
		return SYS_ALWAYS_DEL_MESSAGE_BY_ID + id;
	}

	// 消息在redis中内容的key
	public static final String SYS_ALWAYS_CONTENT_MESSAGE_BY_ID = "sys_always_content_message_by_id_";

	public static String getAlwaysContentMessageById(String id) {
		return SYS_ALWAYS_CONTENT_MESSAGE_BY_ID + id;
	}

	// 聊天获取当前登陆用户信息在redis中的key
	public static final String SYS_TALK_USER_THIS_MATN_MATION = "sys_talk_user_this_matn_mation_";

	public static String getSysTalkUserThisMainMationById(String id) {
		return SYS_TALK_USER_THIS_MATN_MATION + id;
	}

	// 聊天获取当前登陆用户拥有的群组列表在redis中的key
	public static final String SYS_TALK_USER_HAS_GROUP_LIST_MATION = "sys_talk_user_has_group_list_mation_";

	public static String getSysTalkUserHasGroupListMationById(String id) {
		return SYS_TALK_USER_HAS_GROUP_LIST_MATION + id;
	}

	// 聊天获取分组下的用户列表信息在redis中的key
	public static final String SYS_TALK_GROUP_USER_LIST_MATION = "sys_talk_group_user_list_mation_";

	public static String getSysTalkGroupUserListMationById(String id) {
		return SYS_TALK_GROUP_USER_LIST_MATION + id;
	}

	// 获取已经上线的图片类型列表的redis的key
	public static final String SYS_EVE_PIC_TYPE_UP_STATE_LIST = "sys_eve_pic_type_up_state_list";

	public static String sysEvePicTypeUpStateList() {
		return SYS_EVE_PIC_TYPE_UP_STATE_LIST;
	}

	// 获取已经上线的申诉原因的redis的key
	public static final String CHECK_WORK_REASON_UP_STATE_LIST = "check_work_reason_up_state_list";

	public static String checkWorkReasonUpStateList() {
		return CHECK_WORK_REASON_UP_STATE_LIST;
	}

	// 获取文件管理默认的文件夹
	public static final List<Map<String, Object>> getFileConsoleISDefaultFolder() {
		List<Map<String, Object>> beans = new ArrayList<>();
		Map<String, Object> favorites = new HashMap<>();
		favorites.put("id", "1");
		favorites.put("name", "收藏夹");
		favorites.put("pId", "0");
		favorites.put("isParent", 1);// 是否是文件夹 0否1是
		favorites.put("icon", "../../assets/images/my-favorites-icon.png");// 图标
		beans.add(favorites);
		Map<String, Object> documents = new HashMap<>();
		documents.put("id", "2");
		documents.put("name", "我的文档");
		documents.put("pId", "0");
		documents.put("isParent", 1);// 是否是文件夹 0否1是
		documents.put("icon", "../../assets/images/my-folder-icon.png");// 图标
		beans.add(documents);
		Map<String, Object> skyDrive = new HashMap<>();
		skyDrive.put("id", "3");
		skyDrive.put("name", "企业网盘");
		skyDrive.put("pId", "0");
		skyDrive.put("isParent", 1);// 是否是文件夹 0否1是
		skyDrive.put("icon", "../../assets/images/skydrive-icon.png");// 图标
		beans.add(skyDrive);
		return beans;
	}

	// 文件管理---目录logo图片
	public static final String SYS_FILE_CONSOLE_IS_FOLDER_LOGO_PATH = "../../assets/images/folder-show.png";
	// 文件管路---图片类型
	public static final String[] SYS_FILE_CONSOLE_IS_IMAGES = { "png", "jpg", "xbm", "bmp", "webp", "jpeg", "svgz",
			"git", "ico", "tiff", "svg", "jiff", "pjpeg", "pjp", "tif" };
	// 文件管理---office文件
	public static final String[] SYS_FILE_CONSOLE_IS_OFFICE = { "docx", "doc", "xls", "xlsx", "ppt", "pptx", "wps",
			"et", "dps", "csv", "pdf" };
	public static final String[] SYS_FILE_CONSOLE_IS_OFFICE_ICON = { "../../assets/images/doc.png",
			"../../assets/images/doc.png", "../../assets/images/xls.png", "../../assets/images/xls.png",
			"../../assets/images/ppt.png", "../../assets/images/pptx.png", "../../assets/images/wps-icon.png",
			"../../assets/images/ppt.png", "../../assets/images/xls.png", "../../assets/images/csv.png",
			"../../assets/images/pdf.png" };
	// 文件管理---视频文件
	public static final String[] SYS_FILE_CONSOLE_IS_VEDIO = { "mp4", "rm", "rmvb", "wmv", "avi", "3gp", "mkv" };
	// 文件管理---压缩包
	public static final String[] SYS_FILE_CONSOLE_IS_PACKAGE = { "zip", "rar" };
	public static final String[] SYS_FILE_CONSOLE_IS_ACE = { "txt", "sql", "java", "css", "html", "htm", "json", "js",
			"tpl" };
	public static final String[] SYS_FILE_CONSOLE_IS_ACE_ICON = { "../../assets/images/txt.png",
			"../../assets/images/sql-icon.png", "../../assets/images/java-icon.png", "../../assets/images/css-icon.png",
			"../../assets/images/html.png", "../../assets/images/html.png", "../../assets/images/json.png",
			"../../assets/images/js.png", "../../assets/images/tpl.png" };
	// 文件管理---电子书
	public static final String[] SYS_FILE_CONSOLE_IS_EPUB = { "epub" };

	// 文件分享路径
	public static final String getFileShareUrl(String id) {
		return "tpl/shareFile/shareFilepwd.html?id=" + id;
	}

	// 文件管理目录集合
	public static final String SYS_FILE_MATION_FOLDER_LIST_MATION = "sys_file_mation_folder_list_mation_";

	public static String getSysFileMationFolderListMation(String folderId, String userId) {
		return SYS_FILE_MATION_FOLDER_LIST_MATION + folderId + "_" + userId;
	}

	// 获取已经上线的轻应用类型的redis的key
	public static final String CHECK_APP_LIGHTAPPTYPE_UP_LIST = "check_app_lightapptype_up_list";

	public static String checkAppLightAppTypeUpList() {
		return CHECK_APP_LIGHTAPPTYPE_UP_LIST;
	}

	// 获取已经上线的轻应用的redis的key
	public static final String CHECK_APP_LIGHTAPP_UP_LIST_BYID = "check_app_lightapp_up_list_byid_";

	public static String checkAppLightAppUpListById(String typeId) {
		return CHECK_APP_LIGHTAPP_UP_LIST_BYID + typeId;
	}

	// 获取我的笔记默认的文件夹
	public static final List<Map<String, Object>> getFileMyNoteDefaultFolder() {
		List<Map<String, Object>> beans = new ArrayList<>();
		Map<String, Object> newnotes = new HashMap<>();
		newnotes.put("id", "1");
		newnotes.put("name", "最新笔记");
		newnotes.put("pId", "0");
		newnotes.put("isParent", 0);// 是否是文件夹 0否1是
		newnotes.put("icon", "../../assets/images/note-folder.png");// 图标
		beans.add(newnotes);
		Map<String, Object> myfiles = new HashMap<>();
		myfiles.put("id", "2");
		myfiles.put("name", "我的文件夹");
		myfiles.put("pId", "0");
		myfiles.put("isParent", 1);// 是否是文件夹 0否1是
		myfiles.put("icon", "../../assets/images/my-folder-icon.png");// 图标
		beans.add(myfiles);
		return beans;
	}

	// 笔记文件夹目录集合
	public static final String SYS_FILE_MYNOTE_LIST_MATION = "sys_file_mynote_list_mation_";

	public static String getSysFileMyNoteListMation(String folderId, String userId) {
		return SYS_FILE_MYNOTE_LIST_MATION + folderId + "_" + userId;
	}

	// 获取我的附件0级列表
	public static final List<Map<String, Object>> getSysEnclosureZeroList() {
		List<Map<String, Object>> beans = new ArrayList<>();
		Map<String, Object> favorites = new HashMap<>();
		favorites.put("id", "1");
		favorites.put("name", "我的附件");
		favorites.put("pId", "0");
		favorites.put("isParent", 1);// 是否是文件夹 0否1是
		favorites.put("icon", "../../assets/images/my-folder-icon.png");// 图标
		beans.add(favorites);
		return beans;
	}

	// 附件分块上传时的分块集合存储key
	public static final String SYS_ENCLOSURE_FILE_MODULE_MD5 = "sys_enclosure_file_module_md5_";

	public static String getSysEnclosureFileModuleByMd5(String md5) {
		return SYS_ENCLOSURE_FILE_MODULE_MD5 + md5;
	}

	// 系统读取请求配置文件的key
	public static final String SYS_EVE_MAIN_REQMAPPING_KEY = "sys_eve_main_reqmapping_key_skyeye";

    /**
	 * 
	 * @Title: WeekDay
	 * @Description: 日期获取星期
	 */
	public static enum WeekDay {
		MON("星期一", 1),
		TUE("星期二", 2),
		WED("星期三", 3),
		THU("星期四", 4),
		FRI("星期五", 5),
		SAT("星期六", 6),
		SUN("星期日", 7);

		private String name;
		private int day;

		WeekDay(String name, int day) {
			this.name = name;
			this.day = day;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public static String getWeekName(Date date) {
			Calendar cal = Calendar.getInstance();
			// 一周第一天是否为星期天
			boolean isFirstSunday = (cal.getFirstDayOfWeek() == Calendar.SUNDAY);
			cal.setTime(date);
			int weekDay = cal.get(Calendar.DAY_OF_WEEK);// 获取星期
			// 若一周第一天为星期天，则-1
			if (isFirstSunday) {
				weekDay = weekDay - 1;
				if (weekDay == 0) {
					weekDay = 7;
				}
			}
			for (WeekDay q : WeekDay.values()) {
				if (q.getDay() == weekDay) {
					return q.getName();
				}
			}
			return null;
		}
	}

	/**
	 * 
	 * @Title: ClockInTime
	 * @Description: 获取上班打卡状态
	 */
	public static enum ClockInTime {
		System("系统填充", "0"),
		Normal("正常", "1"),
		Late("迟到", "2"),
		Notclock("未打卡", "3");

		private String name;
		private String state;

		ClockInTime(String name, String state) {
			this.name = name;
			this.state = state;
		}

		public static String getClockInState(String str) {
			for (ClockInTime q : ClockInTime.values()) {
				if (q.getState().equals(str)) {
					return q.getName();
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	/**
	 * 文件上传路径
	 */
	public static enum FileUploadPath {
		SMPROPIC(new int[]{1}, "/smpropic", "/", "小程序上传"),
		WINBGPIC(new int[]{2, 4}, "/winbgpic", "/winbgpic/", "系统桌面背景自定义图片上传,系统桌面背景自定义图片上传用户自定义"),
		WINLOCKBGPIC(new int[]{3, 5}, "/winlockbgpic", "/winlockbgpic/", "系统桌面锁屏背景自定义图片上传,系统桌面锁屏背景自定义图片上传用户自定义"),
		USERPHOTO(new int[]{6}, "/userphoto", "/userphoto/", "用户头像"),
		TALKGROUP(new int[]{7}, "/talkgroup", "/talkgroup/", "聊天群组头像"),
		SYSWIN(new int[]{8}, "/syswin", "/syswin/", "系统图片"),
		TALKPIC(new int[]{9}, "/talkpic", "/talkpic/", "聊天图片"),
		TALKFILE(new int[]{10}, "/talkfile", "/talkfile/", "聊天附件"),
		EDIT(new int[]{11}, "/edit", "/edit/", "富文本内容图片"),
		MENULOGO(new int[]{12}, "/menulogo", "/menulogo/", "菜单logo图片"),
		LAYEDIT(new int[]{13}, "/layedit", "/layedit/", "富文本编辑图片"),
		ORDER(new int[]{14}, "/order", "/order/", "工单图片"),
		STUDENTPIC(new int[]{15}, "/studentPic", "/studentPic/", "学生照图片"),
		SCHOOLBANK(new int[]{16}, "/schoolBank", "/schoolBank/", "考试题库文件"),
		ACT_MODEL(new int[]{17}, "/actmodel", "/actmodel/", "流程配置图片"),
		REPORT_BG_IMAGE(new int[]{18}, "/reportBgImage", "/reportBgImage/", "报表基础设置背景图"),
		REPORT_WORD_MODEL_IMAGE(new int[]{19}, "/reportWordModel", "/reportWordModel/", "报表文字模型logo");

		private int[] type;
		// 保存地址
		private String savePath;
		// 访问地址
		private String visitPath;
		private String desc;

		FileUploadPath(int[] type, String savePath, String visitPath, String desc) {
			this.type = type;
			this.savePath = savePath;
			this.visitPath = visitPath;
			this.desc = desc;
		}

		public int[] getType() {
			return type;
		}

		public void setType(int[] type) {
			this.type = type;
		}

		public String getSavePath() {
			return savePath;
		}

		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}

		public String getVisitPath() {
			return visitPath;
		}

		public void setVisitPath(String visitPath) {
			this.visitPath = visitPath;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public static String getSavePath(int type) {
			for (FileUploadPath q : FileUploadPath.values()) {
				if (ArrayUtil.contains(q.getType(), type)) {
					return "\\upload" + q.getSavePath();
				}
			}
			return "\\upload";
		}

		public static String getVisitPath(int type) {
			for (FileUploadPath q : FileUploadPath.values()) {
				if (ArrayUtil.contains(q.getType(), type)) {
					return "/images/upload" + q.getVisitPath();
				}
			}
			return "/images/upload/";
		}
	}

	/**
	 * 
	 * @Title: ClockInTime
	 * @Description: 获取下班打卡状态
	 */
	public static enum ClockOutTime {
		System("系统填充", "0"),
		Normal("正常", "1"),
		Leaveearly("早退", "2"),
		Notclock("未打卡", "3");

		private String name;
		private String state;

		ClockOutTime(String name, String state) {
			this.name = name;
			this.state = state;
		}

		public static String getClockOutState(String str) {
			for (ClockOutTime q : ClockOutTime.values()) {
				if (q.getState().equals(str)) {
					return q.getName();
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	/**
	 * 
	 * @Title: ClockInTime
	 * @Description: 获取考勤状态
	 */
	public static enum ClockState {
		Start("早卡", "0"), 
		Normal("全勤", "1"), 
		Absence("缺勤", "2"), 
		Insufficient("工时不足", "3"), 
		Notstart("缺早卡", "4"), 
		Notend("缺晚卡", "5");

		private String name;
		private String state;

		ClockState(String name, String state) {
			this.name = name;
			this.state = state;
		}

		public static String getClockState(String str) {
			for (ClockState q : ClockState.values()) {
				if (q.getState().equals(str)) {
					return q.getName();
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}
	}

	// 获取群组成员列表
	public static final String SYS_EVE_TALK_GROUP_USER_LIST = "sys_eve_talk_group_user_list_";

	public static String checkSysEveTalkGroupUserListByGroupId(String groupId) {
		return SYS_EVE_TALK_GROUP_USER_LIST + groupId;
	}

	// 获取上线的申请类型下的上线的类型实体
	public static final String ACT_MODLE_UP_STATE_LIST = "act_modle_up_state_list";

	// 获取上线的论坛标签
	public static final String FORUM_TAG_UP_STATE_LIST = "forum_tag_up_state_list";

	// 获取上线的用品类别
	public static final String ASSET_ARTICLES_TYPE_UP_STATE_LIST = "asset_articles_type_up_state_list";

	// 获取已经上线的资产类型的redis的key
	public static final String GET_ASSETTYPE_UP_LIST_ALL = "get_assettype_up_list_all";

	public static String getAssettypeUpListAll() {
		return GET_ASSETTYPE_UP_LIST_ALL;
	}

	// 获取已经上线的资产来源的redis的key
	public static final String GET_ASSETFROM_UP_LIST_ALL = "get_assetfrom_up_list_all";

	public static String getAssetfromUpListAll() {
		return GET_ASSETFROM_UP_LIST_ALL;
	}

	// 工作计划-计划周期类型
	public static enum SysWorkPlan {
		DAY_PLAN("day", "1"),
		WEEK_PLAN("week", "2"),
		MONTH_PLAN("month", "3"),
		QUARTER_PLAN("quarter", "4"),
		HALFYEAR_PLAN("halfyear", "5"),
		YEAR_PLAN("year", "6");

		private String nameCode;
		private String num;

		SysWorkPlan(String nameCode, String num) {
			this.nameCode = nameCode;
			this.num = num;
		}

		public static String getClockInState(String nameCode) {
			for (SysWorkPlan q : SysWorkPlan.values()) {
				if (q.getNameCode().equals(nameCode)) {
					return q.getNum();
				}
			}
			return "";
		}

		public static String getClockInName(String num) {
			for (SysWorkPlan q : SysWorkPlan.values()) {
				if (q.getNum().equals(num)) {
					return q.getNameCode();
				}
			}
			return "";
		}

		public String getNameCode() {
			return nameCode;
		}

		public void setNameCode(String nameCode) {
			this.nameCode = nameCode;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

	}

	// 获取论坛敏感词的redis的key
	public static final String FORUM_SENSITIVE_WORDS_ALL = "forum_sensitive_words_all";

	public static String forumSensitiveWordsAll() {
		return FORUM_SENSITIVE_WORDS_ALL;
	}

	// 获取论坛帖子评论量的redis的key(实时的)
	public static final String FORUM_COMMENT_NUMS_BY_ = "forum_comment_nums_by_";

	public static String forumCommentNumsByForumId(String forumId) {
		return FORUM_COMMENT_NUMS_BY_ + forumId;
	}

	// 获取论坛帖子评论量的redis的key(执行上一次定时任务时的)
	public static final String FORUMYESTERDAY_COMMENT_NUMS_BYFORUMID = "forum_yesterday_comment_nums_by_";

	public static String forumYesterdayCommentNumsByForumId(String forumId) {
		return FORUMYESTERDAY_COMMENT_NUMS_BYFORUMID + forumId;
	}

	// 获取论坛帖子浏览量的redis的key(实时的)
	public static final String FORUM_BROWSE_NUMS_BYFORUMID = "forum_browse_nums_by_";

	public static String forumBrowseNumsByForumId(String forumId) {
		return FORUM_BROWSE_NUMS_BYFORUMID + forumId;
	}

	// 获取论坛帖子浏览量的redis的key(执行上一次定时任务时的)
	public static final String FORUMYESTERDAY_BROWSE_NUMS_BYFORUMID = "forum_yesterday_browse_nums_by_";

	public static String forumYesterdayBrowseNumsByForumId(String forumId) {
		return FORUMYESTERDAY_BROWSE_NUMS_BYFORUMID + forumId;
	}

	// 获取用户论坛帖子浏览信息的redis的key
	public static final String FORUM_BROWSE_MATION_BYUSERID = "forum_browse_mation_by_";

	public static String forumBrowseMationByUserid(String userId) {
		return FORUM_BROWSE_MATION_BYUSERID + userId;
	}

	// 获取论坛帖子每天被浏览过帖子的redis的key
	public static final String FORUM_EVERYDAY_BROWSE_IDS_BYTIME = "forum_everyday_browse_ids_by_";

	public static String forumEverydayBrowseIdsByTime(String time) {
		return FORUM_EVERYDAY_BROWSE_IDS_BYTIME + time;
	}

	// 获取论坛每个帖子每天的浏览和评论数的redis的key
	public static final String EVERYFORUM_EVERYDAY_NUMS_BY_ = "everyforum_everyday_nums_by_";

	public static String everyforumEverydayNumsByIdAndTime(String id, String time) {
		return EVERYFORUM_EVERYDAY_NUMS_BY_ + id + "_" + time;
	}

	// 获取solr同步数据的时间的redis的key
	public static final String FORUM_SOLR_SYNCHRONOUSTIME = "forum_solr_synchronoustime";

	public static String forumSolrSynchronoustime() {
		return FORUM_SOLR_SYNCHRONOUSTIME;
	}

	// 我的个人通讯录类型列表
	public static final String PERSON_MAIL_TYPE_LIST = "person_mail_type_list_";

	public static String getPersonMailTypeListByUserId(String userId) {
		return PERSON_MAIL_TYPE_LIST + userId;
	}

	// 获取已经上线的论坛举报类型的redis的key
	public static final String FORUM_REPORT_TYPE_UP_LIST = "forum_report_type_up_list";

	public static String forumReportTypeUpList() {
		return FORUM_REPORT_TYPE_UP_LIST;
	}

	// 获取redis中的动态表单页
	public static final String DS_FORM_CONTENT_LIST_BY_PAGE_ID = "ds_form_content_list_by_page_id_";

	public static String dsFormContentListByPageId(String pageId) {
		return DS_FORM_CONTENT_LIST_BY_PAGE_ID + pageId;
	}

	// 获取用品领用单单号
	public static String getAssetArticlesOddNumberToUse() {
		return "YPLY" + ToolUtil.getTimeStrAndToString();
	}

	// 获取用品采购单单号
	public static String getAssetArticlesOddNumberToPurchase() {
		return "YPCG" + ToolUtil.getTimeStrAndToString();
	}

	// 获取资产领用单单号
	public static String getAssetOddNumberToUse() {
		return "ZCLY" + ToolUtil.getTimeStrAndToString();
	}

	// 获取资产采购单单号
	public static String getAssetOddNumberToPurchase() {
		return "ZCCG" + ToolUtil.getTimeStrAndToString();
	}

	// 获取资产归还单单号
	public static String getAssetOddNumberToReturn() {
		return "ZCGH" + ToolUtil.getTimeStrAndToString();
	}

	// 获取证照借用单单号
	public static String getLicenceBorrowOddNumberToUse() {
		return "ZZJY" + ToolUtil.getTimeStrAndToString();
	}

	// 获取印章借用单单号
	public static String getSealBorrowOddNumberToUse() {
		return "YZJY" + ToolUtil.getTimeStrAndToString();
	}

	// 获取印章归还单单号
	public static String getSealRevertOddNumberToUse() {
		return "YZGH" + ToolUtil.getTimeStrAndToString();
	}

	// 获取证照归还单单号
	public static String getLicenceRevertOddNumberToUse() {
		return "ZZGH" + ToolUtil.getTimeStrAndToString();
	}

	// 获取用车申请单单号
	public static String getVehicleOddNumberToUse() {
		return "YCSQ" + ToolUtil.getTimeStrAndToString();
	}

	// 获取会议室预定单单号
	public static String getConferenceRoomReserveOddNumber() {
		return "HYSYD" + ToolUtil.getTimeStrAndToString();
	}

	// 获取请假申请单单号
	public static String getCheckWorkLeaveOddNumber() {
		return "QJSQ" + ToolUtil.getTimeStrAndToString();
	}

	// 获取加班申请单单号
	public static String getCheckWorkOvertimeOddNumber() {
		return "JBSQ" + ToolUtil.getTimeStrAndToString();
	}

	// 获取销假申请单单号
	public static String getCheckWorkCancelLeaveOddNumber() {
		return "XJSQ" + ToolUtil.getTimeStrAndToString();
	}

	// 获取出差申请单单号
	public static String getCheckWorkBusinessTripOddNumber() {
		return "CCSQ" + ToolUtil.getTimeStrAndToString();
	}

	// 工作流我发起的流程在redis中的存储-我的请求功能查看
	public static final String PROJECT_ACT_PROCESS_INSTANCE_ITEM = "project_act_process_instance_item_";

	public static String getProjectActProcessInstanceItemById(String processInstanceId, String userId) {
		return PROJECT_ACT_PROCESS_INSTANCE_ITEM + processInstanceId + "_" + userId;
	}

	// 工作流我的已办流程在redis中的存储-我的审批历史查看
	public static final String PROJECT_ACT_PROCESS_HIS_INSTANCE_ITEM = "project_act_process_instance_item_";

	public static String getProjectActProcessHisInstanceItemById(String processInstanceId, String userId) {
		return PROJECT_ACT_PROCESS_HIS_INSTANCE_ITEM + processInstanceId + "_" + userId;
	}

	// 工作流我的待办流程在redis中的存储-我的待办查看
	public static final String PROJECT_ACT_PROCESS_INSTANCE_USERAGENCYTASKS_ITEM = "project_act_process_instance_useragencytasks_item_";

	public static String getProjectActProcessInstanceUserAgencyTasksItemById(String processInstanceId, String userId) {
		return PROJECT_ACT_PROCESS_INSTANCE_USERAGENCYTASKS_ITEM + processInstanceId + "_" + userId;
	}

	// 获取工单派工内容字符串
	// 接收人
	public static String getNoticeServiceUserContent(String orderNum, String userName) {
		return "尊敬的" + userName + "，您好：<br/>" + "您有一份待接单工单，工单号为：" + orderNum + "，请及时接单。";
	}

	// 协助人
	public static String getNoticeCooperationUserContent(String orderNum, String userName) {
		return "尊敬的" + userName + "，您好：<br/>" + "您有一份协助工单，工单号为：" + orderNum + "，请配合工单接收人完成该售后服务。";
	}

}
