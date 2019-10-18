package com.cwks.bizcore.tycx.core.utils;

/**
 * 
 *
 * @project 金税三期工程核心征管及应用总集成项目
 * @package com.css.sword.qs.constants
 * @file Constant.java 创建时间:2015-6-26下午05:07:58
 * @title 常量类
 * @description 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @copyright Copyright (c) 2015 中国软件与技术服务股份有限公司
 * @company 中国软件与技术服务股份有限公司
 * @module 模块: 模块名称
 * @author   作者
 * @reviewer 审核人
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 *
 */
public class Constant {
    /**
     * 文件类型
     */
    public static final String FILE_CONTENTTYPE_ZIP = "application/x-zip-compressed";
    /**
     * 查询参数表_机构层级_总局级
     */
    public static String CS_CX_CSB_JGCJ_ZJJ = "01";
    /**
     * 查询参数表_机构层级_省局级
     */
    public static String CS_CX_CSB_JGCJ_SJJ = "02";
    /**
     * 查询参数表_机构层级_市州局级
     */
    public static String CS_CX_CSB_JGCJ_SZJJ = "03";
    /**
     * 查询参数表_机构层级_县区局级
     */
    public static String CS_CX_CSB_JGCJ_XQJJ = "04";
    /**
     * 查询参数表_机构层级_基层分局级
     */
    public static String CS_CX_CSB_JGCJ_JCFJJ = "05";
    /**
     * 查询参数表_机构层级_股级
     */
    public static String CS_CX_CSB_JGCJ_GJ = "06";
    /**
     * 查询参数表_税务机构树条件根节点生成规则_默认上朔规则:根据GWSSSWJG/SLJG上朔(SWJGBZ='0' OR SWJGBZ='1' AND GHBZ='Y')
     */
    public static String CS_CX_CSB_SWJGTREESCGZ_MRSSGZ = "1";
    /**
     * 查询参数表_税务机构树条件根节点生成规则_主管税务局上朔规则
     */
    public static String CS_CX_CSB_SWJGTREESCGZ_ZGSWJSSGZ = "2";
    /**
     * 查询参数表_税务机构树条件根节点生成规则_不上朔
     */
    public static String CS_CX_CSB_SWJGTREESCGZ_BSS = "9";
    /**
     * 查询参数表_税务机构树条件根节点生成规则_默认上朔规则:根据GWSSSWJG/SLJG上朔(SWJGBZ='0')
     */
    public static String CS_CX_CSB_SWJGTREESCGZ_MRSSGZ_3 = "3";

    /**
     * 机构级次，对应参数表中的参数分类
     */
    public static String JGLX = "JGLX";

    /**
     * 查询参数表_机构类型_全部
     */
    public static String CS_CX_CSB_JGLX_ALL = "0";
    /**
     * 查询参数表_机构类型_机关
     */
    public static String CS_CX_CSB_JGLX_JG = "1";
    /**
     * 查询参数表_机构类型_机关+大厅
     */
    public static String CS_CX_CSB_JGLX_JGORDT = "2";
    /**
     * 查询参数表_机构类型_本下级具有管户的机关
     */
    public static String CS_CX_CSB_JGLX_GHJG = "3";
    /**
     * 查询参数表_系统参数_下钻查询标记:csz=on表示电子税务局调用newaddTabItem，csz=off表示调用addTabItem
     */
    public static String CS_CX_CSB_XZCX_BJ = "XZCX_BJ";
    /**
     * 系统参数
     */
    public static String CS_CX_CSB_XTCS = "XTCS";
    /**
     * 查询参数表_主题查询权限设置标志
     */
    public static final String CS_CX_CSB_ZTCXQXSZBZ = "ZTCXQXSZBZ";

    /**
     * 列信息_列类型_VARCHAR2
     */
    public static String CX_LXX_VARCHAR2 = "VARCHAR2";
    /**
     * 列信息_列类型_DATE
     */
    public static String CX_LXX_DATE = "DATE";
    /**
     * 列信息_列类型_NUMBER
     */
    public static String CX_LXX_NUMBER = "NUMBER";
    /**
     * 列信息_列类型_单选
     */
    public static String CX_LXX_SELECT = "SELECT";
    /**
     * 列信息_列类型_多选
     */
    public static String CX_LXX_MULTI = "MULTI";
    /**
     * 列信息_列类型_单选树
     */
    public static String CX_LXX_SINGLETREE = "SINGLETREE";
    /**
     * 列信息_列类型_树
     */
    public static String CX_LXX_TREE = "TREE";
    /**
     * 列信息_列类型_税务机构树
     */
    public static String CX_LXX_SWJGTREE = "SWJGTREE";
    /**
     * 列信息_列类型_核算机构树
     */
    public static String CX_LXX_HSJGTREE = "HSJGTREE";
    /**
     * 列信息_列类型_主管税务局
     */
    public static String CX_LXX_ORGTREE = "ORGTREE";
    /**
     * 列信息_列类型_职能机构树
     */
    public static String CX_LXX_ZNJGTREE = "ZNJGTREE";
    /**
     * 列信息_列类型_主管税务所
     */
    public static String CX_LXX_SWSSELECT = "SWSSELECT";
    /**
     * 列信息_列类型_主管税务所
     */
    public static String CX_LXX_SWSDXSELECT = "SWSDXSELECT";

    /**
     * 自定义条件-在参数表里面对应的分类值
     */
    public static String CUSTOM_TJ = "CUSTOM_TJ";
    /**
     * 条件默认值
     */
    public static String TJ_DEFAULT_VALUES = "TJ_DEFAULT_VALUES";
    /**
     * 条件默认值(日期)
     */
    public static String TJ_DEFAULT_VALUES_DATE = "TJ_DEFAULT_VALUES_DATE";
    /**
     * 条件默认值（代码）
     */
    public static String TJ_DEFAULT_VALUES_DM = "TJ_DEFAULT_VALUES_DM";
    /**
     * 显示格式
     */
    public static String XSGS = "XSGS";
    /**
     * 日期显示格式
     */
    public static String XSGS_DATE = "XSGS_DATE";

    /**
     * 税务机构树条件根节点生成规则
     */
    public static String SWJGTREESCGZ = "SWJGTREESCGZ";

    /**
     * 动态SQL中的Where条件里面的参数为空的情况下 前缀
     */
    public static String DYNAMIC_SQL_PARAM_IS_EMPTY_PREFIX = "1";
    /**
     * 动态SQL中的Where条件里面的参数为非空的情况下 前缀
     */
    public static String DYNAMIC_SQL_PARAM_IS_NOT_EMPTY_PREFIX = "0";
    /**
     * 动态SQL中的Where条件里面的参数为空的情况下 后缀
     */
    public static String DYNAMIC_SQL_PARAM_IS_EMPTY_SUFFIX = "1";
    /**
     * 动态SQL中的Where条件里面的参数为空的情况下,且操作符为In时， 后缀
     */
    public static String DYNAMIC_SQL_PARAM_IS_EMPTY_SUFFIX_FOR_MACRO = "'1'";
    /**
     * 百分比符号
     */
    public static String PERCENTAGE_SYMBOL = "%";

    /**
     * SQL条件的操作符_等于
     */
    public static String SQLPARAM_EQUAL = "1";
    /**
     * SQL条件的操作符_LIKE
     */
    public static String SQLPARAM_LIKE = "2";
    /**
     * SQL条件的操作符_IN
     */
    public static String SQLPARAM_IN = "3";
    /**
     * SQL条件的操作符_IS NULL
     */
    public static String SQLPARAM_ISNULL = "4";
    /**
     * 定制查询条件json的参数_指标项目
     */
    public static String WRAPPARAMS_ZB = "zbxm";
    /**
     * 定制查询条件json的参数_维度项目
     */
    public static String WRAPPARAMS_WD = "fzxm";
    /**
     * 超时时间
     */
    public static String QUERY_TIMEOUT = "QUERY_TIMEOUT";
    /**
     * 页数
     */
    public static int PAGE = 1;
    //每页条数----------------后期删除
    public static int LIMIT = 20;
    /**
     * 合计类型_求和
     */
    public static String HJ_SUM = "sum";
    /**
     * 合计类型_求平均
     */
    public static String HJ_AVG = "average";
    /**
     * 查询类型_清册查询
     */
    public static String queryType_QC = "1";
    /**
     * 查询类型_统计查询
     */
    public static String queryType_TJ = "2";
    /**
     * 查询类型_交叉表查询
     */
    public static String queryType_JCB = "3";
    /**
     * 查询类型_统计功能
     */
    public static String queryType_TJGN = "4";
    /**
     * 查询类型_模糊查询
     */
    public static String queryType_MHCX = "5";
    /**
     * 查询类型_导出
     */
    public static String queryType_EXPORT = "6";
    /**
     * 查询类型_统计导出
     */
    public static String queryType_EXPORT_TJ = "7";
    /**
     * 查询类型_交叉表导出
     */
    public static String queryType_EXPORT_JCB = "8";
    /**
     * 查询类型_订阅查询
     */
    public static String queryType_EXPORT_DY = "9";
    /**
     * 查询类型_客户端
     */
    public static String queryType_KHD = "0";
    /**
     * 查询类型_计算
     */
    public static String queryType_JS="10";
    /**
     * 分隔符：逗号
     */
    public static String SEPARATOR_COMMA = ",";
    /**
     * 统计类型_COUNT
     */
    public static String TJLX_COUNT = "count";
    /**
     * 统计类型_SUM
     */
    public static String TJLX_SUM = "sum";
    /**
     * 统计类型_AVG
     */
    public static String TJLX_AVG = "avg";
    /**
     * 统计类型_2
     */
    public static String TJLX_SUM_2="2";
    /**
     * 统计类型_3
     */
    public static String TJLX_AVG_3="3";
    /**
     * 维度列
     */
    public static String TJ_WDL = "group";

    /**
     * 税务机构标志_机关
     */
    public static String SWJG_SWJGBZ_JIGUAN = "0";
    /**
     * 税务机构标志_部门
     */
    public static String SWJG_SWJGBZ_BUMEN = "1";
    /**
     * 税务机构标志_机关
     */
    public static String SWJG_GHBZ_Y = "Y";
    /**
     * 税务机构标志_部门
     */
    public static String SWJG_GHBZ_N = "N";
    /**
     * 职能代码_受理职能
     */
    public static String ZNDM_SLZN = "01";

    /**
     * QueryType_Tj_bj 统计查询标记
     */
    public static String QueryType_Tj_bj = "1";
    /**
     * 参数表参数分类_导出
     */
    public static String EXPORT = "EXPORT";
    /**
     * 导出类型_ZIP
     */
    public static String FILETYPE_ZIP = "zip";
    /**
     * 导出类型_EXCEL
     */
    public static String FILETYPE_EXECL = "excel";
    /**
     * 导出类型_WORD
     */
    public static String FILETYPE_DOC = "doc";
    /**
     * 
     */
    public static String FILETYPE_EXCEL_NEW="excel_new";
    /**
     * 单元格合并标记_合并
     */
    public static String DYGHBBJ = "1";
    /**
     * 查询结果导出模板_页眉左
     */
    public static String YM_LEFT = "11";
    /**
     * 查询结果导出模板_页眉中
     */
    public static String YM_CENTER = "12";
    /**
     * 查询结果导出模板_页眉右
     */
    public static String YM_RIGHT = "13";
    /**
     * 查询结果导出模板_页脚左
     */
    public static String YJ_LEFT = "21";
    /**
     * 查询结果导出模板_页脚中
     */
    public static String YJ_CENTER = "22";
    /**
     * 查询结果导出模板_页脚右
     */
    public static String YJ_RIGHT = "23";
    /**
     * 每页允许导出最大数，对应参数表参数分类EXCEL,参数名FILE_EXCEL_MAXNROW
     */
    public static String EXPORT_MAX_FILE = "FILE_EXCEL_MAXNROW";
    /**
     * 订阅查询导出最大记录数，对应参数表参数分类EXCEL,参数名DYCX_MAXNROW
     */
    public static String EXPORT_DYCX_MAX_ROW_NUM = "DYCX_MAXNROW";
    /**
     * 参数表参数分类,订阅查询文件下载根目录,对应参数表参数分类_DYCX_WJXZ
     */
    public static String CS_CX_CSB_CSFL_DYCX_WJXZ = "DYCX_WJXZ";
    /**
     * 参数表参数名称,订阅查询文件下载根目录,Window下.对应参数表参数分类_DYCX_WJXZ，参数名_winxzdz
     */
    public static String CS_CX_CSB_CSM_DYCX_WJXZ_WINDOW_FOLDER = "winxzdz";
    /**
     * 参数表参数名称,订阅查询文件下载根目录,Linux下。对应参数表参数分类_DYCX_WJXZ，参数名linxzdz
     */
    public static String CS_CX_CSB_CSM_DYCX_WJXZ_LINUX_FOLDER = "linxzdz";
    /**
     * 结果列字段扩展类型（交叉表）-全部扩展
     */
    public static String KZLX_QBKZ = "0";
    /**
     * 结果列字段扩展类型（交叉表）-仅扩展有数据
     */
    public static String KZLX_JKZYSJ = "1";
    /**
     * 结果列字段扩展类型（交叉表）-自定义sql扩展
     */
    public static String KZLX_ZDYSQL = "2";
    /**
     * 结果列字段扩展类型（交叉表）-自定义扩展
     */
    public static String KZLX_ZDYKZ = "3";
    /**
     * 交叉表_横向扩展字段
     */
    public static String JCB_HXKZZD = "1";
    /**
     * 交叉表_普通横向字段
     */
    public static String JCB_PTHXZD = "0";
    /**
     * 主题查询项目代码起始值
     */
    public static String ZTCX_CXXMDM_INITIAL_VALUE = "0001";
    /**
     * 主题查询_主题类型代码_一户式
     */
    public static String ZTCX_ZTLXDM_YHS = "01";
    /**
     * 主题查询_主题类型代码_一员式
     */
    public static String ZTCX_ZTLXDM_YYS = "02";
    /**
     * 主题查询_主题类型代码_一案式
     */
    public static String ZTCX_ZTLXDM_YAS = "03";
    /**
     * 主题查询_序号名称_登记序号
     */
    public static String ZTCX_XHMC_DJXH = "DJXH";
    /**
     * 主题查询_序号名称_纳税人识别号
     */
    public static String ZTCX_XHMC_NSRSBH = "NSRSBH";
    /**
     * 主题查询_序号名称_税收管理员代码
     */
    public static String ZTCX_XHMC_SSGLYDM = "SSGLY_DM";
    /**
     * 主题查询_序号名称_稽查案卷uuid
     */
    public static String ZTCX_XHMC_JCAJXXUUID = "JCAJXXUUID";
    /**
     * 主题查询_查询类别_文件夹
     */
    public static String ZTCX_JDLB_WJJ = "1";
    /**
     * 主题查询_查询类别_查询
     */
    public static String ZTCX_JDLB_CX = "0";
    /**
     * 行标签的分隔符
     */
    public static String rowFG = ":";
    /**
     * 参数表-按钮
     */
    public static String BUTTON = "BUTTON";
    /**
     * 参数分类_默认值
     */
    public static String CSFL_MRZ = "TJ_DEFAULT_VALUES";
    /**
     * 默认值_SQL
     */
    public static String MRZ_SQL = "SQL";
    
    /**
     * 默认值_SFSQL
     */
    public static String MRZ_SFSQL = "SFSQL";
    
    /**
     * 默认值_SESSION
     */
    public static String MRZ_SESSION = "SESSION";
    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_组织机构代码
     */
    public static String MRZ_SESSION_ORGID = "orgid";
    /**
     * 默认值_SESSION_用户ID
     */
    public static String MRZ_SESSION_USERID = "userid";
    /**
     * 默认值_SESSION_岗位序号
     */
    public static String MRZ_SESSION_GWXH = "gwxh";
    /**
     * 默认值_SESSION_登记序号
     */
    public static String MRZ_SESSION_DJXH="djxh";
    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_岗位所属税务机构
     */
    public static String MRZ_SESSION_GWSSSWJG = "gwssswjg";
    
    /**
     * 权限所属机关
     */
    public static String MRZ_SESSION_SWRYSFJG  = "SWRYSFJG";

    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_职能代码
     */
    public static String MRZ_SESSION_ZNDM = "zndm";
    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_岗位代码
     */
    public static String MRZ_SESSION_GNDM = "gndm";
    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_权限机构
     */
    public static String MRZ_SESSION_QXJG = "qxjg";
    /**
     * session取值支持以下几种类型
     * 默认值_SESSION_税务人员代码
     */
    public static String MRZ_SESSION_SWRYDM = "SWRY_DM";
    /**
     * 
     */
    public static String DMSQL_MRZ = "CUSTOM_TJ";
    /**
     * 查看SQL，对应参数分类VIEW_SQL
     */
    public static String VIEW_SQL = "VIEW_SQL";
    /**
     * 查看SQL_系统统一配置，对应参数分类VIEW_SQL，参数名SYSTEM
     */
    public static String VIEW_SQL_SYS = "SYSTEM";
    /**
     * 查看SQL_系统统一配置，对应参数分类VIEW_SQL，参数名POWER
     */
    public static String VIEW_SQL_POWER = "POWER";
    /**
     * 查看SQL开关_ 启用，对应参数分类VIEW_SQL，参数值ON
     */
    public static String VIEW_SQL_ON = "ON";
    /**
     * 查看SQL按钮名称
     */
    public static String VIEW_SQL_BOTTON_NAME = "chakansql";

    /**
     * 主题查询项目默认显示方式_显示
     */
    public static String ZTCX_MRXSBZ_DISPLAY = "1";
    /**
     * 主题查询项目默认显示方式_不显示
     */
    public static String ZTCX_MRXSBZ_NOTDISPLAY = "0";
    /**
     * 主题查询_展示方式_列表展示,即一个主题查询项目一行
     */
    public static String ZTCX_ZSFS_LIST = "1";
    /**
     * 主题查询_展示方式_纵向展示,即将主题查询的各结果字段纵向展示, 一个主题查询有多少个结果列,这个查询项目就显示多少行
     */
    public static String ZTCX_ZSFS_ZONGXIANG = "2";
    /**
     * 主题查询_展示方式_稽查表单列表展示, 专用于稽查
     */
    public static String ZTCX_ZSFS_JCBDLB = "3";
    /**
     * 参数表_参数分类_主题查询查询日期起
     */
    public static String CS_CX_CSB_CSFL_ZTCX_CXRQQ = "ZTCX_CXRQQ";
    /**
     * 参数表_参数分类_主题查询查询日期止
     */
    public static String CS_CX_CSB_CSFL_ZTCX_CXRQZ = "ZTCX_CXRQZ";
    /**
     * 参数表_参数分类_主题查询查询日期起条件列名称
     */
    public static String CS_CX_CSB_CSFL_ZTCX_CXRQQTJMC = "ZTCX_CXRQQTJMC";
    /**
     * 参数表_参数分类_主题查询查询日期止条件列名称
     */
    public static String CS_CX_CSB_CSFL_ZTCX_CXRQZTJMC = "ZTCX_CXRQZTJMC";
    /**
     * word导出计算单元格宽度，10000是总宽
     */
    public static int word_width = 10000;

    /**
     * 后台Job定时器回调用的服务名称
     * 订阅查询定时任务设置的定制查询服务
     */
    public static String EXECUTE_DYCX_DSRW_DZCX_SERVICE_NAME = "CX.DYCX.ExecuteDycxDsrwDzcx";
    /**
     * 下钻查询时，设置数据源作为参数
     */
    public static String SJYMC="SJYMC";
    /**
     * 报表类型---自定义XML,XSLT
     */
    public static String BBLX_ZDY="1";
    /**
     * 报表类型---原始XML，自定义XSLT
     */
    public static String BBLX_XML_ZDYXSLT="2";
    /**
     * 报表类型---原始XML,XSLT
     */
    public static String BBLX_XML_XSLT="3";
    /**
     * 查询分类---局端查询1
     */
    public static String CXFL_JD="1";
    /**
     * 查询分类---纳税人2
     */
    public static String CXFL_NSR="2";
    /**
     * 参数表-按钮-纳税人
     */
    public static String NSR_BUTTON = "NSR_BUTTON";
    /**
     * 下钻查询标记
     */
    public static String XZCX_BJ="XZCX_BJ";
    /**
     * 国地税共享标志_不共享
     */
	public static String GDSGXBZ_BGX = "0";
	/**
	 * 国地税共享标志_国税从地税获取共享数据
	 */
	public static String GDSGXBZ_DSTOGS = "1";
	/**
	 * 国地税共享标志_不共享
	 */
	public static String GDSGXBZ_GSTODS = "2";
	/**
	 * 主题查询项目类型_通用查询项目
	 */
	public static String CX_ZTCXXM_CXLX_COMMON = "0";
	/**
	 * 主题查询项目类型_本系统专用查询项目
	 */
	public static String CX_ZTCXXM_CXLX_FOR_BXT = "1";
	/**
	 * 主题查询项目类型_国地税共享专用查询项目
	 */
	public static String CX_ZTCXXM_CXLX_FOR_GDSGX = "2";
	/**
	 * 一户式查询_本系统
	 */
	public static String ZTCX_YHSLX_BXT = "1";
	/**
	 * 一户式查询_国地税共享
	 */
	public static String ZTCX_YHSLX_GDSGX = "2";
	/**
	 * 主题查询不统计
	 */
	public static String ZTCX_BTJ = "0";
	/**
	 * 主题查询执行统计
	 */
	public static String ZTCX_ZXTJ = "1";
	
	/**
     * 参数表_参数分类_主题查询_数据源名称_税务机构代码前3位
     */
    public static String CS_CX_CSB_ZTCX_SJYMC_SWJGDM3 = "ZTCX_SJYMC_SWJGDM3";
  /**
   * 国地税查询条件
   */
  	public static String GDS_GXCX="SJLY";
  	/**
  	 * 查询对方系统数据
  	 */
  	public static String GDS_DFXT="2";
  	/**
  	 * 查询本地系统数据
  	 */
  	public static String GDS_BDXT="1";
  	/**
  	 * 国地税共享查询
  	 */
  	public static String GDS_GDSGXCX="3";
  	/**
  	 * 国地税共享查询
  	 */
  	public static String GDSGXCX="GDSGX";
  	/**
  	 * 国地税共享查询，查询对方系统的ZGSWJ_DM
  	 */
  	public static String ZGSWJ_DM="ZGSWJ_DM";
  	/**
  	 * 职能范围
  	 */
  	public static String MRZ_SESSION_ZNFW="znfw";
  	/**
  	 * 图形展示-柱形图
  	 */
  	public static String TXZS_BAR="bar";
  	/**
  	 * 图形展示-饼图
  	 */
  	public static String TXZS_PIE="pie";
  	/**
  	 * 图形展示-折线图
  	 */
  	public static String TXZS_LINE="line";
  	/**
  	 * 图形展示-雷达图
  	 */
  	public static String TXZS_RADAR="radar";
  	/**
  	 * 图形展示-柱图和折线图
  	 */
  	public static String TXZS_BAR_LINE="barAndLine";
  	/**
  	 * 图形展示-漏斗图
  	 */
  	public static String TXZS_FUNNEL="funnel";
  	/**
  	 * 图形展示-仪表盘
  	 */
  	public static String TXZS_GAUGE="gauge";
  	public static String CXJGL_CHECKED="cxjgl_checked";
  	/**
  	 * 查询源--查询
  	 */
  	public static String CXY_CX="1";
  	/**
  	 * 查询源--导出
  	 */
  	public static String CXY_DC="2";

}
