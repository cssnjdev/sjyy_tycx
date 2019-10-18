package com.cwks.bizcore.tycx.core.vo;

import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;

import java.util.List;
import java.util.Map;

public class CXDzcxVO {
	private static final long serialVersionUID = -8720762420527276032L;
	// sqlxh
	private String sqlxh;
	// 查询条件
	private String queryParams;
	// 分页起始
	private int page;
	// 分页大小
	private int limit;
	// 总条数
	private int total;
	// 统计结果
	private String summary;
	// 统计条件
	private String summaryParams;
	// 排序条件
	private String sortParams;
	// 查询类型
	private String queryType;
	// 包装条件
	private String wrapParams;
	// 超时时间
	private int queryTimeout;
	// 是否取count
	private boolean doTotal;
	// 合计SQL
	public String hjSql;
	// 查询记录数
	public String count;
	// 执行时间
	public String executeTime;
	// 日志表UUID
	public String rzbUUID;
	// 存储过程参数
	public List procedureParam;
	// 代码转名称列
	public String code2name;
	// 分支条件列名称
	public String tjlmc;
	// 分支条件列值
	public String tjlz;
	// 条件分支语句
	public String tjfzyj;
	// 维度
	public String wd;
	// 指标
	public String zb;
	// 统计SELECT部分
	public String tjSelect;
	// 统计GROUP部分
	public String tjGroup;
	// 统计ORDER部分
	public String tjOrder;
	// 模糊查询条件
	public String fuzzyQueryTj;
	// 统计查询标记
	public String queryType_Tj_bj;
	// 导出最大数
	public int max;
	// 隐藏列
	public String hiddenColumns;
	// 交叉表横向扩展列
	public String jcbHxkzl;
	// 交叉表统计字段
	public String jcbTjzd;
	// 交叉表统计类型
	public String jcbTjlx;
	// 交叉表展现方式
	public String jcbZxfs;
	// 查询SQL
	public String sql;
	// 数据源名称
	public String sjymc;
	// headSQL
	public String headsql;
	/**
	 * 查询插件
	 */
	public String queryPlugin;
	// 动态参数
	public String dyParams;
	public String sqlmc;
	public String gwssswjg;
	// 统计导出时结果列
	public List<Map<String, Object>> tjlList;
	public String fileType;
	// excel每页允许导出最大数
	public int pagesize;
	// 查询结果LIST
	public List<Tycx001CxCxjgdyPojo> cxjgList;
	public List<Map<String, Object>> yjList;
	// 订阅查询保存目录地址
	public String dycx_zipPath;
	// 订阅查询
	public String dycx;
	// 查询分类:1. 局端人员登陆 ; 2. 纳税人端人员登录
	public String cxfl;
	// 合计List
	public List<Map<String, Object>> hjList;
	//国地税共享查询
	public String sjly;
	//对方系统起始行号
	public int dfQshh;
	//对方系统终止行号
	public int dfZzhh;
	//类型
	public String lx;
	//存储过程
	public String ccgcmc;
	//查询结果列设置
	public String cxjgl_checked;
	//计算表达式
	public String jsbds;
	//权限MAP
	public Map userMap;
	

	public Map getUserMap() {
		return userMap;
	}

	public void setUserMap(Map userMap) {
		this.userMap = userMap;
	}

	public String getJsbds() {
		return jsbds;
	}

	public void setJsbds(String jsbds) {
		this.jsbds = jsbds;
	}

	public String getCxjgl_checked() {
		return cxjgl_checked;
	}

	public void setCxjgl_checked(String cxjglChecked) {
		cxjgl_checked = cxjglChecked;
	}

	public String getCcgcmc() {
		return ccgcmc;
	}

	public void setCcgcmc(String ccgcmc) {
		this.ccgcmc = ccgcmc;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public int getDfQshh() {
		return dfQshh;
	}

	public void setDfQshh(int dfQshh) {
		this.dfQshh = dfQshh;
	}

	public int getDfZzhh() {
		return dfZzhh;
	}

	public void setDfZzhh(int dfZzhh) {
		this.dfZzhh = dfZzhh;
	}

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
	}

	public List<Map<String, Object>> getHjList() {
		return hjList;
	}

	public void setHjList(List<Map<String, Object>> hjList) {
		this.hjList = hjList;
	}

	public String getDycx() {
		return dycx;
	}

	public void setDycx(String dycx) {
		this.dycx = dycx;
	}

	public String getDycx_zipPath() {
		return dycx_zipPath;
	}

	public void setDycx_zipPath(String dycx_zipPath) {
		this.dycx_zipPath = dycx_zipPath;
	}

	public List<Map<String, Object>> getYjList() {
		return yjList;
	}

	public void setYjList(List<Map<String, Object>> yjList) {
		this.yjList = yjList;
	}

	public List<Tycx001CxCxjgdyPojo> getCxjgList() {
		return cxjgList;
	}

	public void setCxjgList(List<Tycx001CxCxjgdyPojo> cxjgList) {
		this.cxjgList = cxjgList;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getGwssswjg() {
		return gwssswjg;
	}

	public List<Map<String, Object>> getTjlList() {
		return tjlList;
	}

	public void setTjlList(List<Map<String, Object>> tjlList) {
		this.tjlList = tjlList;
	}

	public void setGwssswjg(String gwssswjg) {
		this.gwssswjg = gwssswjg;
	}

	public String getSqlmc() {
		return sqlmc;
	}

	public void setSqlmc(String sqlmc) {
		this.sqlmc = sqlmc;
	}

	public String getDyParams() {
		return dyParams;
	}

	public void setDyParams(String dyParams) {
		this.dyParams = dyParams;
	}

	public String getHeadsql() {
		return headsql;
	}

	public void setHeadsql(String headsql) {
		this.headsql = headsql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSjymc() {
		return sjymc;
	}

	public void setSjymc(String sjymc) {
		this.sjymc = sjymc;
	}

	public String getJcbHxkzl() {
		return jcbHxkzl;
	}

	public void setJcbHxkzl(String jcbHxkzl) {
		this.jcbHxkzl = jcbHxkzl;
	}

	public String getJcbTjzd() {
		return jcbTjzd;
	}

	public void setJcbTjzd(String jcbTjzd) {
		this.jcbTjzd = jcbTjzd;
	}

	public String getJcbTjlx() {
		return jcbTjlx;
	}

	public void setJcbTjlx(String jcbTjlx) {
		this.jcbTjlx = jcbTjlx;
	}

	public String getJcbZxfs() {
		return jcbZxfs;
	}

	public void setJcbZxfs(String jcbZxfs) {
		this.jcbZxfs = jcbZxfs;
	}

	public String getHiddenColumns() {
		return hiddenColumns;
	}

	public void setHiddenColumns(String hiddenColumns) {
		this.hiddenColumns = hiddenColumns;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getQueryType_Tj_bj() {
		return queryType_Tj_bj;
	}

	public void setQueryType_Tj_bj(String queryType_Tj_bj) {
		this.queryType_Tj_bj = queryType_Tj_bj;
	}

	public String getFuzzyQueryTj() {
		return fuzzyQueryTj;
	}

	public void setFuzzyQueryTj(String fuzzyQueryTj) {
		this.fuzzyQueryTj = fuzzyQueryTj;
	}

	public String getTjSelect() {
		return tjSelect;
	}

	public void setTjSelect(String tjSelect) {
		this.tjSelect = tjSelect;
	}

	public String getTjGroup() {
		return tjGroup;
	}

	public void setTjGroup(String tjGroup) {
		this.tjGroup = tjGroup;
	}

	public String getTjOrder() {
		return tjOrder;
	}

	public void setTjOrder(String tjOrder) {
		this.tjOrder = tjOrder;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	public String getZb() {
		return zb;
	}

	public void setZb(String zb) {
		this.zb = zb;
	}

	public String getTjlmc() {
		return tjlmc;
	}

	public void setTjlmc(String tjlmc) {
		this.tjlmc = tjlmc;
	}

	public String getTjlz() {
		return tjlz;
	}

	public void setTjlz(String tjlz) {
		this.tjlz = tjlz;
	}

	public String getTjfzyj() {
		return tjfzyj;
	}

	public void setTjfzyj(String tjfzyj) {
		this.tjfzyj = tjfzyj;
	}

	public String getCode2name() {
		return code2name;
	}

	public void setCode2name(String code2name) {
		this.code2name = code2name;
	}

	public List getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureParam(List procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getRzbUUID() {
		return rzbUUID;
	}

	public void setRzbUUID(String rzbUUID) {
		this.rzbUUID = rzbUUID;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public String getHjSql() {
		return hjSql;
	}

	public void setHjSql(String hjSql) {
		this.hjSql = hjSql;
	}

	public boolean isDoTotal() {
		return doTotal;
	}

	public void setDoTotal(boolean doTotal) {
		this.doTotal = doTotal;
	}

	public int getQueryTimeout() {
		return queryTimeout;
	}

	public void setQueryTimeout(int queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * @constructor 构造方法
	 */
	public CXDzcxVO() {
	}

	/**
	 * 
	 * @time 创建时间:2015-5-5上午9:48:46
	 * @return sqlxh
	 */
	public String getSqlxh() {
		return sqlxh;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:51:40
	 * @param sqlxh
	 */
	public void setSqlxh(String sqlxh) {
		this.sqlxh = sqlxh;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:58:02
	 * @return queryParams
	 */
	public String getQueryParams() {
		return queryParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param queryParams
	 */
	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param start
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param start
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param total
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param summaryParams
	 */
	public String getSummaryParams() {
		return summaryParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param summaryParams
	 */
	public void setSummaryParams(String summaryParams) {
		this.summaryParams = summaryParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param sortParams
	 */
	public String getSortParams() {
		return sortParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param sortParams
	 */
	public void setSortParams(String sortParams) {
		this.sortParams = sortParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param wrapParams
	 */
	public String getWrapParams() {
		return wrapParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param wrapParams
	 */
	public void setWrapParams(String wrapParams) {
		this.wrapParams = wrapParams;
	}

	/**
	 * @time 创建时间:2015-5-5上午9:59:41
	 * @param serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 
	 * @name 中文名称
	 * @description 相关说明
	 * @time 创建时间:2015-6-1下午04:52:40
	 * @return
	 * @author 作者
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public String getQueryPlugin() {
		return queryPlugin;
	}

	/**
	 * 
	 * @name 中文名称
	 * @description 相关说明
	 * @time 创建时间:2015-6-1下午04:52:46
	 * @param queryPlugin
	 *            查询插件
	 * @author 作者
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public void setQueryPlugin(String queryPlugin) {
		this.queryPlugin = queryPlugin;
	}

	/**
	 * 查询分类:1. 局端人员登陆 ; 2. 纳税人端人员登录
	 * 
	 * @return
	 */
	public String getCxfl() {
		return cxfl;
	}

	/**
	 * 查询分类
	 * 
	 * @param cxfl
	 *            查询分类
	 */
	public void setCxfl(String cxfl) {
		this.cxfl = cxfl;
	}

}
