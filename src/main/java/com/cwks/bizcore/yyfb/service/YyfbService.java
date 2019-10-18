package com.cwks.bizcore.yyfb.service;

import com.cwks.bizcore.utils.tycxUtils;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.mapping.pojo.Etl001DatasourcePojo;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.FtpFileUtil;
import com.cwks.bizcore.tycx.core.utils.YyfbUtils;
import com.cwks.bizcore.yyfb.dao.Yyfb001AYYfwFolderDao;
import com.cwks.bizcore.yyfb.dao.Yyfb001TYYfwFxyyFjDao;
import com.cwks.bizcore.yyfb.dao.Yyfb003AYYfwSjlyDao;
import com.cwks.bizcore.yyfb.dao.YyfbDao;
import com.cwks.bizcore.yyfb.mapping.pojo.FxyyPojo;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwFolderPojo;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001AYYfwSjlyPojo;
import com.cwks.bizcore.yyfb.mapping.pojo.Yyfb001TYYfwFxyyFjPojo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

@Component
@Service("YyfbService")
public class YyfbService  {
	UserContext ctx;
    private static Logger logger = LoggerFactory.getLogger(YyfbService.class);

    @Autowired
    private JdbcDao jdbcDao;
	@Autowired
	tycxUtils tycx;
    @Autowired
    private YyfbDao FxyyDao;
    @Autowired
    private Yyfb001AYYfwFolderDao yyfwFolderDao;
    @Autowired
    private Yyfb003AYYfwSjlyDao yyfwSjlyDao;
    @Autowired
    private Yyfb001TYYfwFxyyFjDao fxyyFjDao;
    public ResponseEvent getHcbxx(RequestEvent requestEvent) throws Exception {
    	  ResponseEvent resEvent = new ResponseEvent();
    	  String table=(String) requestEvent.getRequestMap().get("table");
    	  String dm=(String) requestEvent.getRequestMap().get("dm");
    	  String mc=(String) requestEvent.getRequestMap().get("mc");
    	  List yylxList= CacheUtil.getTable(table);
  		  List list =YyfbUtils.hcbListToDmb(yylxList, dm, mc);
  		  HashMap reqmap = new HashMap();
  		  reqmap.put("JSONDATA", JsonUtil.toJson(list));
  		  resEvent.setResMap(reqmap);
    	  return resEvent;
    }

    public ResponseEvent select(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_select");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap = new HashMap();
        //参数注入
        FxyyPojo fxyyPojo = new FxyyPojo(requestEvent.getRequestMap());
        //设置分页
        PageHelper.startPage(requestEvent.getRequestMap());
        List<FxyyPojo> list = FxyyDao.select(fxyyPojo);
        List<FxyyPojo> newlist =new ArrayList<FxyyPojo>();
        for(int i=0;i<list.size();i++){
        	FxyyPojo fxyypojo=list.get(i);
        	String gnlj=fxyypojo.getGnlj();
    		String lj=getGnljMc(gnlj);
    		fxyypojo.setGnlj(lj);
    		newlist.add(fxyypojo);       	
        }
        PageInfo<FxyyPojo> pages = new PageInfo<FxyyPojo>(newlist);
        reqmap.put("JSONDATA", YyfbUtils.toJsonForJqGrid(pages));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
    
    /**
     * 初始化方法
     * @param requestEvent
     * @return
     * @throws Exception
     */
	public ResponseEvent init(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		//应用类型
		List fxyylxList = CacheUtil.getCodeTable("DM_YYFW_FXYYLX");
		//单位
		List yhdwList = CacheUtil.getCodeTable("T_YYFW_YHDW");
		//发布状态
		List fbztList = CacheUtil.getCodeTable("DM_YYFW_FBZT");
		String sjksql=jdbcDao.getSql("SQL_YYFB_querySjk");
		List sjkList=jdbcDao.queryforlist(sjksql);
		reqmap.put("fxyylxList", fxyylxList);
		reqmap.put("yhdwList", yhdwList);
		reqmap.put("fbztList", fbztList);
		reqmap.put("sjkList", sjkList);
		List fxyyCodeList=YyfbUtils.idToCodeList(fxyylxList);
		List fbztCodeList=YyfbUtils.idToCodeList(fbztList);
		reqmap.put("YYLXJSON", JsonUtil.toJson(fxyyCodeList).replace("\"", "'"));
		reqmap.put("FBZTJSON", JsonUtil.toJson(fbztCodeList).replace("\"", "'"));
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfb001_main.html");
		return resEvent;
	}
	
	/**
	 * 应用详情只读页面
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent initView(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		//应用类型
		List fxyylxList = CacheUtil.getCodeTable("DM_YYFW_FXYYLX");
		//单位
		List yhdwList = CacheUtil.getCodeTable("T_YYFW_YHDW");
		//发布状态
		List fbztList = CacheUtil.getCodeTable("DM_YYFW_FBZT");
		String sjksql=jdbcDao.getSql("SQL_YYFB_querySjk");
		List sjkList=jdbcDao.queryforlist(sjksql);
		reqmap.put("fxyylxList", fxyylxList);
		reqmap.put("yhdwList", yhdwList);
		reqmap.put("fbztList", fbztList);
		reqmap.put("sjkList", sjkList);
		List fxyyCodeList=YyfbUtils.idToCodeList(fxyylxList);
		List fbztCodeList=YyfbUtils.idToCodeList(fbztList);
		reqmap.put("YYLXJSON", JsonUtil.toJson(fxyyCodeList).replace("\"", "'"));
		reqmap.put("FBZTJSON", JsonUtil.toJson(fbztCodeList).replace("\"", "'"));
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfb001_main_view.html");
		return resEvent;
	}
	
	/**
	 * 查询应用分类
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent queyfolder(RequestEvent requestEvent) throws Exception{
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		
		String sql=jdbcDao.getSql("SQL_YYFB_QUERYWJJ");
		List list=jdbcDao.queryforlist(sql);//yyfbDao.executeSql(sql, page, limit);		
		List treeList=new ArrayList();
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=(Map<String, Object>) list.get(i);
			Map<String,Object> treeMap=new HashMap<String,Object>();
			treeMap.put("id", map.get("ID"));
			treeMap.put("pid", map.get("PID"));
			treeMap.put("name", map.get("NAME"));
			treeList.add(treeMap);
		}
		
		reqmap.put("JSONDATA", JsonUtil.toJson(treeList));
		resEvent.setResMap(reqmap);
		return resEvent;
	}

	/**
     * 新增方法
     * @param requestEvent
     * @return
     * @throws Exception
     * 
     */
	public ResponseEvent initNew(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> reqmap = new HashMap<String, Object>();
		//folderid
		String folderid=(String) requestEvent.getRequestMap().get("folderid");
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		//应用类型

		List fxyylxList = tycx.getCodeTable("DM_YYFW_FXYYLX");
		//单位
		List yhdwList = tycx.getCodeTable("T_YYFW_YHDW");

		//发布状态
		List fbztList = tycx.getCodeTable("DM_YYFW_FBZT");


		String sjksql=jdbcDao.getSql("SQL_YYFB_querySjk");
		List sjkList=jdbcDao.queryforlist(sjksql);
		
		//如果fxyy_id非空，查询分析应用基本信息
		if(!YyfbUtils.isEmpty(fxyy_id)){
			
			String sql=jdbcDao.getSql("SQL_YYFB_queryJbxx");
			ArrayList param=new ArrayList();
			param.add(fxyy_id);
//			reqmap=(HashMap<String, Object>) FxyyDao.executeSql2(sql, param);
			Map map = FxyyDao.executeSql2(sql, param);
			Set<Entry<String, String>> s=map.entrySet();
			for (Entry<String, String> entry : s) {
				System.out.println(entry);
				reqmap.put(entry.getKey(),entry.getValue());
			}
			folderid = (String) reqmap.get("GNLJ");
			
		}else{
		 
			
		}
		
		if(!YyfbUtils.isEmpty(folderid)){
			 String lj=getGnljMc(folderid);
	    	 reqmap.put("gnljmc", lj);
		}
		
		reqmap.put("fxyylxList", fxyylxList);
		reqmap.put("yhdwList", yhdwList);
		reqmap.put("fbztList", fbztList);
		reqmap.put("sjkList", sjkList);
		reqmap.put("gnlj", folderid);
		resEvent.setResMap(reqmap);
		
		resEvent.setFwordPath("biz/bizcore/sjyy/yyfb/html/yyfb001_jbxx.html");
		return resEvent;
	}
	public String getGnljMc(String folderid){
		ArrayList param=new ArrayList();
		param.add(folderid);
		String sqlstr=jdbcDao.getSql("SQL_YYFB_querySjWjjByFolder");
 		List gnljlist=jdbcDao.queryforlist(sqlstr,param);
 		String lj="";
 		for(int m=0;m<gnljlist.size();m++){
 			Map<String,String> gnljMap=(Map<String, String>) gnljlist.get(m);
 			lj=">"+gnljMap.get("mc")+lj;
 		}
 		return lj;
	}
	/**
	 * 点击功能树查询应用方法
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent getTable(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		String sql=jdbcDao.getSql("SQL_YYFB_queryYyById");
		String page_str = (String) requestEvent.getRequestMap().get("page");
		String limit_str = (String) requestEvent.getRequestMap().get("limit");
		int page=Integer.parseInt(page_str);
		int limit=Integer.parseInt(limit_str);
		int end=20;
		List list=jdbcDao.queryPage(sql, page, end, limit);
		reqmap.put("JSONDATA", JsonUtil.toJson(list));
		resEvent.setResMap(reqmap);
		return resEvent;
	}	
	/**
	 * 保存分析应用
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent saveFxyy(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> reqmap = new HashMap();
		FxyyPojo fxyyPojo = new FxyyPojo(requestEvent.getRequestMap());
		
		this.ctx = requestEvent.getCtx(); 
		if(ctx!=null){
			Map<String, String> userInfo = ctx.getUserinfo();
			fxyyPojo.setFbrdm(userInfo.get("userId"));
 		}
		
		String fxyy_id = fxyyPojo.getFxyy_id();
		if (YyfbUtils.isEmpty(fxyy_id)) {
			fxyyPojo.setFxyy_id(UUIDGenerator.getUUID());
			fxyyPojo.setYxbj("Y");
			FxyyDao.insertSelective(fxyyPojo);
			reqmap.put("message", "插入数据成功");
		} else {
			FxyyDao.updateByPKeySelective(fxyyPojo);
			reqmap.put("message", "修改数据成功");
		}
		reqmap.put("fxyy_id", fxyyPojo.getFxyy_id());
		reqmap.put("JSONDATA", JsonUtil.toJson(reqmap));
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	
	/** 
	 * 发布分析应用
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent fbFxyy(RequestEvent requestEvent)throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap reqMap = requestEvent.getRequestMap();
		String fxyy_id = (String) reqMap.get("fxyy_id");
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		ctx = requestEvent.getCtx();
 		
		String swry_dm = null;
		String swryxm = null;
		
		if(ctx!=null){
			
			Map<String, Object> userInfo = ctx.getUserinfo();
			swry_dm = (String) userInfo.get("userId");
			swryxm = (String) userInfo.get("swryxm");
		} 
		
		if(YyfbUtils.isEmpty(swry_dm)){
			
			JSONDATA.put("success", "0");
			JSONDATA.put("message", "发布失败！当前系统登录失效，请重新登入");
			 
		}else{
		
			try{
				
				String sql = 
						" update t_yyfw_fxyy t \n" +
						"   set fbrdm = ?, fb_sj = sysdate, zt_bj = 1\n" + 
						" where t.fxyy_id = ? ";
			    
				ArrayList<String> params = new ArrayList<String>();
				params.add(swry_dm);
				params.add(fxyy_id);
				
				jdbcDao.update(sql,params);
				JSONDATA.put("success", "1");
				JSONDATA.put("message", "发布成功");
				JSONDATA.put("fbrdm",  swry_dm);
				JSONDATA.put("fbrmc", swryxm);
				
			}catch (Exception e) {
				 
				JSONDATA.put("success", "0");
				JSONDATA.put("message", "发布失败！后台程序执行发布异常");
				
			}
			
		}
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		
	}
	
	/**
	 * 停用分析应用
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent tyFxyy(RequestEvent requestEvent)throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap reqMap = requestEvent.getRequestMap();
		String fxyy_id = (String) reqMap.get("fxyy_id");
		
		HashMap<String, Object> resmap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		ctx = requestEvent.getCtx();
 		
		String swry_dm = null;
		String swryxm = null;
		
		if(ctx!=null){
			
			Map<String, Object> userInfo = ctx.getUserinfo();
			swry_dm = (String) userInfo.get("userId");
			swryxm = (String) userInfo.get("swryxm");
		} 
		
		if(YyfbUtils.isEmpty(swry_dm)){
			
			JSONDATA.put("success", "0");
			JSONDATA.put("message", "停用失败！当前系统登录失效，请重新登入");
			 
		}else{
		
			try{
				
				String sql = 
						" update t_yyfw_fxyy t \n" +
						"   set fbrdm = ?, fb_sj = sysdate, zt_bj = 2\n" + 
						" where t.fxyy_id = ? ";
			    
				ArrayList<String> params = new ArrayList<String>();
				params.add(swry_dm);
				params.add(fxyy_id);
				jdbcDao.update(sql,params);
				JSONDATA.put("success", "1");
				JSONDATA.put("message", "停用成功！");
				JSONDATA.put("fbrdm",  swry_dm);
				JSONDATA.put("fbrmc", swryxm);
				
			}catch (Exception e) {
				JSONDATA.put("success", "0");
				JSONDATA.put("message", "发布失败！后台程序执行发布异常");
			}
			
		}
		
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		
	}
	 
	
	/**
	 * 删除分析应用
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent deleteFxyy(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		FxyyPojo fxyyPojo = new FxyyPojo(requestEvent.getRequestMap());
		FxyyDao.deleteByPKey(fxyyPojo);
		HashMap map = new HashMap();
		map.put("message", "数据删除成功");
		map.put("state", "1");
		reqmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	/**
	 * 保存文件夹
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent saveFolder(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		Yyfb001AYYfwFolderPojo folderPojo=new Yyfb001AYYfwFolderPojo(requestEvent.getRequestMap());
		if(YyfbUtils.isEmpty(folderPojo.getFolder_id())){
			folderPojo.setFolder_id(UUIDGenerator.getUUID());
			folderPojo.setFolderlx_dm("02");
			folderPojo.setXybj("0");
			folderPojo.setMc_j(folderPojo.getMc());
			yyfwFolderDao.insertSelective(folderPojo);
		}else{
			folderPojo.setFolderlx_dm("02");
			folderPojo.setXybj("0");
			folderPojo.setMc_j(folderPojo.getMc());
			yyfwFolderDao.updateByPKeySelective(folderPojo);
		}
		
 		Map map=new HashMap(); 
		map.put("message", "保存数据成功");
		map.put("success", "1");
 		reqmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	
	/**
	 * 保存文件夹
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public ResponseEvent delFolder(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap();
		Yyfb001AYYfwFolderPojo folderPojo=new Yyfb001AYYfwFolderPojo(requestEvent.getRequestMap());
		yyfwFolderDao.deleteByPKey(folderPojo);
		
		Map map=new HashMap();
		map.put("message", "删除成功");
		map.put("success", "1");
		reqmap.put("JSONDATA", JsonUtil.toJson(map));
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	
	/**
	 * 初始化基本信息页面
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ResponseEvent queryFjxx(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		HashMap reqmap = new HashMap(); 
		String fxyyid = (String) requestEvent.getRequestMap().get("fxyy_id");
		//查询附件信息SQL_YYFB_queryFjxx
		ArrayList param=new ArrayList();
		String fjxxStr=jdbcDao.getSql("SQL_YYFB_queryFjxx");
		param.clear();
		param.add(fxyyid);
		List fjxxList=jdbcDao.queryforlist(fjxxStr, param);			
		//reqmap.put("data", fjxxList);
		PageInfo pages = new PageInfo(fjxxList);
		//reqmap.put("fjList", fjxxList);
		reqmap.put("JSONDATA", YyfbUtils.toJsonForJqGrid(pages));
	    resEvent.setResMap(reqmap);
		return resEvent;
	}
	/**
	 * 初始化基本信息页面
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ResponseEvent initJbxxView(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String fxyyid = (String) requestEvent.getRequestMap().get("fxyy_id");
		String folderid=(String) requestEvent.getRequestMap().get("folderid");		
		Map reqmap = new HashMap(); 
	
		ArrayList param=new ArrayList();
		if(!YyfbUtils.isEmpty(fxyyid)){
			
			//查询分析应用类型			
//			//查询标签
//			List bqflList = CacheUtil.getCodeTable("A_GY_BQ_FL");		
//				
			//查询基本信息
//			String fxyyJbxxStr=jdbcDao.getSql("SQL_YYFB_queryJbxx");
//			param.add(fxyyid);
//			reqmap=jdbcDao.queryformap(fxyyJbxxStr,param);
//			String gnlj=(String) reqmap.get("gnlj");			
//			if(!YyfbUtils.isEmpty(gnlj)){
//				folderid=gnlj;
//			}
			//查询开发信息
			String kfxxStr=jdbcDao.getSql("SQL_YYFB_queryKfxx");
			param.clear();
			param.add(fxyyid);
			Map<String,Object> kfxxMap=FxyyDao.executeSql2(kfxxStr, param);
			Map<String,Object> newkfxxMap=new HashMap<String,Object>();
			for (Entry<String, Object> entry : kfxxMap.entrySet()) {
				final String nameStr = entry.getKey().toLowerCase();
				final Object valueStr = entry.getValue();
				newkfxxMap.put(nameStr, valueStr);
			}			
			HashMap resultMap= new HashMap();	
			reqmap.put("kfxx",newkfxxMap);
			//reqmap.put("JSONDATA", JsonUtil.toJson(resultMap));
		}
		//查询目录
//		if(!YyfbUtils.isEmpty(folderid)){
//			String gnlj_new="";
//			param.clear();
//			param.add(folderid);
//			String sql=jdbcDao.getSql("SQL_YYFB_queryYylj");
//			List gnljList=jdbcDao.queryforlist(sql, param);
//			for(int i=0;i<gnljList.size();i++){
//				Map<String,String> gnljMap=(Map<String, String>) gnljList.get(i);
//				gnlj_new=">" + gnljMap.get("MC") + gnlj_new;
//			}	
//			reqmap.put("GNLJ", gnlj_new);
//		}
		reqmap.put("JSONDATA", JsonUtil.toJson(reqmap));
		resEvent.setResMap((HashMap) reqmap);
		return resEvent;
	}
	/**
	 * 添加附件
	 */
	public ResponseEvent addFj(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		HashMap reqmap = new HashMap(); 
		reqmap.put("fxyy_id", fxyy_id);
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfb001_addfj.html");
		return resEvent;
	}
	//上传附件
	//上传操作
	public ResponseEvent uploadFj(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		//待上传文件
		HashMap<String, Object> reqMap = reqEvent.getRequestMap();
		String filePath = reqMap.get("path").toString();
		String id = (String) reqMap.get("id");
		//ip
		String ip=null;
		//port
		Integer port=null;
		//用户名,username
		String userName=null;
		//密码,password
		String password=null;
		//上传路径
		String uploadPath=null;
		//获取ftp连接参数
		List ftpParams= CacheUtil.getCodeTable("SYS_XTCS","CSBM IN ('FTP_INFO','FTP_USERNAME','FTP_PASSWORD')");
		for(int i=0;i<ftpParams.size();i++){
			Map param=(Map) ftpParams.get(i);
			String csbm=(String) param.get("csbm");
			String csz=(String) param.get("csz");
			if(csbm.equals("FTP_INFO")){
				ip=csz;
				continue;
			}else if(csbm.equals("FTP_USERNAME")){
				userName=csz;
				continue;
			}else if(csbm.equals("FTP_PASSWORD")){
				password=csz;
				continue;
			}
		}
		port=21;
		//调用上传接口
		FtpFileUtil ftpFileUtil=new FtpFileUtil();
		String flag=ftpFileUtil.FTPUpload(ip,port,userName,password,uploadPath,filePath);
		int success;
		String fileName=null;
		//如果成功，返回为: code+fileName,失败：code
		if(flag.length()>1){
			success=Integer.parseInt(flag.substring(0, 1));
			//服务器保存的名称：UUID
			fileName=flag.substring(1);
			resMap.put("fileName", fileName);
		}else{
			success=Integer.parseInt(flag);
		}
		if(success == 0){
			resMap.put("result", true);
			resMap.put("msg", "文件上传成功！");
		}else if(success == 1){
			deleteLocalFile(filePath);
			resMap.put("result", false);
			resMap.put("msg", "文件上传失败！");
		}else if(success == 2){
			deleteLocalFile(filePath);
			resMap.put("result", false);
			resMap.put("msg", "文件服务器连接失败，请检查账户及密码……");
		}
		resMap.put("id", id);
		resMap.put("userName", userName);
		resMap.put("password", password);
		//文件原名称
		resMap.put("filePath", filePath);
		resMap.put("JSONDATA", JsonUtil.toJson(resMap));
		resEvent.setResMap(resMap);
		return resEvent;
	}
	/**
	 * 删除项目下保存的临时文件
	 * @param filePath
	 */
	private void deleteLocalFile(String filePath){
		File tempFile = new File(filePath);
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}
	//新增数据单元addSjdy
	public ResponseEvent addSjdy(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		String sql=jdbcDao.getSql("SQL_YYFB_querySjk");
		List sjkList=jdbcDao.queryforlist(sql);
		HashMap reqmap = new HashMap();
		reqmap.put("sjkList", sjkList);
		reqmap.put("fxyy_id", fxyy_id);
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfb001_sjdy.html");
		return resEvent;
	}
	/**
	 * 根据查询条件查询数据单元
	 */
	public ResponseEvent addSjdyByCxtj(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String sjk=(String) requestEvent.getRequestMap().get("sjk");
		String owner=(String) requestEvent.getRequestMap().get("owner");
		String en_name=(String) requestEvent.getRequestMap().get("en_name");
		String zh_name=(String) requestEvent.getRequestMap().get("zh_name");
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		String sql=jdbcDao.getSql("SQL_YYFB_querySjdyByCxtj");
		ArrayList param=new ArrayList();
		YyfbUtils.addSqlParam(param, sjk, Constant.SQLPARAM_EQUAL);
		YyfbUtils.addSqlParam(param, owner, Constant.SQLPARAM_EQUAL);
		YyfbUtils.addSqlParam(param, en_name, Constant.SQLPARAM_EQUAL);
		YyfbUtils.addSqlParam(param, zh_name, Constant.SQLPARAM_LIKE);
		param.add(fxyy_id);
		List sjdyList=jdbcDao.queryforlist(sql,param);
		HashMap reqmap = new HashMap();
		PageInfo<Etl001DatasourcePojo> pages = new PageInfo<Etl001DatasourcePojo>(sjdyList);
		reqmap.put("JSONDATA", YyfbUtils.toJsonForJqGrid(pages));
		//reqmap.put("sjdyList", sjdyList);
		resEvent.setResMap(reqmap);
		return resEvent;
	}
	/**
	 * 保存数据来源
	 */
	public ResponseEvent insertSjly(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		String sjlyData=(String) requestEvent.getRequestMap().get("sjlyData");
		//final List<Yyfb001AYYfwSjlyPojo> SjlysArr =JsonUtil.toListJavaBean(Yyfb001AYYfwSjlyPojo.class, sjlyData);
		final List<Map> SjlysArr = JsonUtil.toListMap(sjlyData);
		for (int i = 0; i < SjlysArr.size(); i++) {
			Yyfb001AYYfwSjlyPojo sjpojo=new Yyfb001AYYfwSjlyPojo();
			Map map=SjlysArr.get(i);
			sjpojo.setFxyy_id(fxyy_id);
			sjpojo.setSjly_dm((String)map.get("SJLY_DM"));
			sjpojo.setSjly_mc((String)map.get("EN_NAME"));
			sjpojo.setVersion((String)map.get("VERSION"));
			sjpojo.setXybj("1");
			yyfwSjlyDao.insertSelective(sjpojo);
		}
		HashMap reqmap = new HashMap();
	    reqmap.put("JSONDATA", RJson.INSERT_TRUE);
	    resEvent.setResMap(reqmap);
		return resEvent;
	}
	public ResponseEvent querySjdy(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		//查询数据单元信息
		String sjdyStr=jdbcDao.getSql("SQL_YYFB_querySjdy");
		ArrayList param=new ArrayList();
		param.add(fxyy_id);
		List sjdyList=jdbcDao.queryforlist(sjdyStr, param);
		PageInfo pages = new PageInfo(sjdyList);
		HashMap reqmap = new HashMap();
	    reqmap.put("JSONDATA", YyfbUtils.toJsonForJqGrid(pages));
	    resEvent.setResMap(reqmap);
		return resEvent;
	}
	
	/**
	 * 删除数据单元
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent delSjdy(RequestEvent requestEvent)throws Exception{
		
		ResponseEvent resEvent = new ResponseEvent();
		
		String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
		String dus = (String) requestEvent.getRequestMap().get("dus");
		
		String delSql = jdbcDao.getSql("SQL_YYFB_DEL_SJLY");
		ArrayList param=new ArrayList();
		String[] duArr = dus.split(",");
	 
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		try{
			for(String du:duArr){
				param.clear();
				param.add(fxyy_id);
				param.add(du); 
				jdbcDao.update(delSql,param);
			}
			JSONDATA.put("success", "1");
			JSONDATA.put("success", "删除成功！");
		}catch (Exception e) {
			JSONDATA.put("success", "0");
			JSONDATA.put("success", e.getMessage());
		}
		 
		HashMap resmap = new HashMap();
		resmap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resmap);
		return resEvent;
		
	}
	
	public ResponseEvent openFxyy(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		 ctx = requestEvent.getCtx();
		 String swry_dm="";
		 String swrysf_dm="";
    	 if(ctx!=null){
	  	    	Map userInfo =	ctx.getUserinfo();
	  	    	swry_dm =(String)userInfo.get("userId");
	  	    	swrysf_dm =(String)userInfo.get("swrysf_dm");
	   	  }
    	 String fxyy_id = (String) requestEvent.getRequestMap().get("fxyy_id");
    	 String sql=jdbcDao.getSql("SQL_YYFB_queryJbxx");
    	 ArrayList param=new ArrayList();
 		 param.add(fxyy_id);
    	 Map fxyyMap=jdbcDao.queryformap(sql, param);
    	 String yyurl=(String) fxyyMap.get("yyurl");
    	 String fxyymc=(String) fxyyMap.get("fxyy_mc");
    	 yyurl += yyurl.indexOf("?") != -1 ? "&" : "?";
		 yyurl += "swrydm=" + swry_dm;
		 yyurl += "&swrysfdm=" + swrysf_dm;
		 HashMap reqmap = new HashMap();
		 reqmap.put("yyurl", yyurl);
		 reqmap.put("cxdymc", fxyymc);
		resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/core/ext/yyfb/yyfb001/jsp/yyfb001_yyfw.html");
		return resEvent;
	}
	public ResponseEvent saveFjxx(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
		Yyfb001TYYfwFxyyFjPojo fxyyFjPojo=new Yyfb001TYYfwFxyyFjPojo(requestEvent.getRequestMap());
		String filePath =(String) requestEvent.getRequestMap().get("filePath");
		String fileMc=filePath.substring(filePath.lastIndexOf("\\")+1);
		String fileName =(String) requestEvent.getRequestMap().get("fileName");
		//计算文件大小
		File file=new File(filePath);
		long fileSize=file.length();
		fxyyFjPojo.setFj_mc(fileMc);
		fxyyFjPojo.setFjdx(formetFileSize(fileSize));
		fxyyFjPojo.setFjurl(fileName);
		fxyyFjPojo.setLrry_dm("cssnj");
		fxyyFjPojo.setLrrq(YyfbUtils.getNow());
		fxyyFjPojo.setFj_id(UUIDGenerator.getUUID());
		fxyyFjDao.insertSelective(fxyyFjPojo);		
		HashMap reqmap = new HashMap();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		reqmap.put("mess", "保存成功");
		resMap.put("JSONDATA", JsonUtil.toJson(reqmap));
		resEvent.setResMap(resMap);
		return resEvent;
	}
	
	private String getSwryMc(String swry_dm){
		
		String sql = " select swryxm from dm_gy_swry where swry_dm = ? ";
		ArrayList<String> param = new ArrayList<String>();
		param.add(swry_dm);
		SqlRowSet rs = jdbcDao.queryforRowset(sql,param);
		
		if(rs.next()){
 			return rs.getString("swryxm");
		}else{
			return null;
		}
		
	}
	
	private String formetFileSize(long fileSize){
		DecimalFormat df=new DecimalFormat("#.00");
		String tempSize="";
		if(fileSize < 1024){
			tempSize=df.format(new BigDecimal(fileSize))+"B";
		}else if(fileSize < 1024*1024){
			tempSize=df.format(new BigDecimal(fileSize/1024))+"K";
		}else if(fileSize < 1024*1024*1024){
			tempSize=df.format(new BigDecimal(fileSize/1024/1024))+"M";
		}else{
			tempSize=df.format(new BigDecimal(fileSize/1024/1024/1024))+"G";
		}
		return tempSize;
	}
	//下载操作
	public ResponseEvent downloadFj(RequestEvent reqEvent) throws Exception {
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String, Object> reqMap = reqEvent.getRequestMap();
		String fjid = (String) reqEvent.getRequestMap().get("fjid");
		Yyfb001TYYfwFxyyFjPojo pojo=new Yyfb001TYYfwFxyyFjPojo();
		pojo.setFj_id(fjid);
		//获取附件信息
		Yyfb001TYYfwFxyyFjPojo fxyyPojo=fxyyFjDao.selectByPKey(pojo);
		
		//ip
		String ip=null;
		// 获取ftp连接参数
		List ftpParams = CacheUtil.getCodeTable("SYS_XTCS","CSBM='FTP_INFO'");
		for(int i=0;i<ftpParams.size();i++){
			Map param=(Map) ftpParams.get(i);
			String csbm=(String) param.get("csbm");
			String csz=(String) param.get("csz");
			if(csbm.equals("FTP_INFO")){
				ip=csz;
				break;
			}
		}
		//port
		Integer port=21;
		//用户名,username
		String userName=fxyyPojo.getFtpusername();
		//密码,password
		String password=fxyyPojo.getFtppassword();
		//下载路径
		String downloadPath="";
		//文件名
		String fileName=fxyyPojo.getFjurl();
		String fileMc=fxyyPojo.getFj_mc();
		//文件保存路径
		String localPath="D:\\ftpdownload";
		
		//调用下载接口
		FtpFileUtil ftpFileUtil=new FtpFileUtil();
		String flag=ftpFileUtil.FTPDownload(ip,port,userName,password,downloadPath,fileName,fileMc,localPath);
		if(flag.equals("0")){
			resMap.put("result", true);
			resMap.put("msg", "文件下载成功！");
		}else{
			resMap.put("result", false);
			resMap.put("msg", "文件下载失败！");
		}
		resMap.put("JSONDATA", JsonUtil.toJson(resMap));
		resEvent.setResMap(resMap);
		return resEvent;
	}
	/**
	 * 初始化开发信息页面
	 * @param requestEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent initKfxxView(RequestEvent requestEvent) throws Exception{
		ResponseEvent resEvent = new ResponseEvent();
//		resEvent.setFwordPath("/biz/core/ext/yyfb/yyfb001/jsp/yyfb001_kfxx.html");
		return resEvent;
	}
	
	/**
	 * 
	 * @author dinghr
	 * @date 2018年2月9日
	 * @description:打开反馈页面
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent openFK(RequestEvent reqEvent) throws Exception{
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
//		String sqlxh = (String) reqEvent.getRequestMap().get("sqlxh");

		List wtlxList = CacheUtil.getCodeTable("T_WTFK_WTLX");
		
		List wtclztList = CacheUtil.getCodeTable("T_WTFK_WTCLZT");

//		resMap.put("sqlxh", sqlxh);
		resMap.put("wtlxList", wtlxList);
		resMap.put("wtclztList", wtclztList);
		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yywtFankui.html");

	    return resEvent;
	  }
	
	

}
