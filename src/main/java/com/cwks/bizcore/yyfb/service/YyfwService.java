package com.cwks.bizcore.yyfb.service;

import com.cwks.bizcore.daoUtil.CssnjDao;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.common.api.dto.ext.RJson;
import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.tycx.core.utils.YyfbUtils;
import com.cwks.bizcore.yyfb.dao.YyfwDao;
import com.cwks.bizcore.yyfb.mapping.pojo.YyfwPjPojo;
import com.cwks.bizcore.yyfb.mapping.pojo.YyfwXiangqingPojo;
import com.cwks.common.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("YyfwService")
public class YyfwService  {
    
    private final static Logger logger = LoggerFactory.getLogger(YyfwBqglService.class);
	UserContext ctx;
    @Autowired
    private JdbcDao jdbcDao;
	
    @Autowired
    private YyfwDao yyfwDao;
    /**
     * 初始化评价页面
     * @param requestEvent
     * @return 
     */
    public ResponseEvent initPj(RequestEvent requestEvent){
    	logger.debug("debuger "+this.getClass().getName()+"_getPj");
    	ResponseEvent resEvent=new ResponseEvent();
    	HashMap reqmap=new HashMap();
    	DecimalFormat df=new DecimalFormat("0.00000");
    	DecimalFormat df1=new DecimalFormat("0.00");
    	String fxyy_id=(String) requestEvent.getRequestMap().get("fxyyid");
    	String fxyymc=yyfwDao.fxyymc(fxyy_id);
    	reqmap.put("fxyymc",fxyymc);
    	String fxyylxdm=yyfwDao.fxyylxdm(fxyy_id);
    	String fxyylxmc=yyfwDao.fxyylxmc(fxyylxdm);
    	reqmap.put("fxyylxmc",fxyylxmc);
    	List pjdjNum=new ArrayList();
    	//不同意见评价次数
    	for(int i=1;i<=5;i++){
    		pjdjNum.add(yyfwDao.searchPjCount(String.valueOf(i),fxyy_id));
    	}
    	//累计评价次数
    	int num=yyfwDao.countPj(fxyy_id);
    	reqmap.put("total",num);
    	int sum=0;
    	float percentTotal=0;
    	//不同意见所占百分比
    	for(int j=0;j<5;j++){
    		int count=((Integer)pjdjNum.get(j)).intValue();
    		reqmap.put("pjdj"+(j+1),count);
    		sum+=(count*(j+1));
    		if(num!=0){
    		BigDecimal pjdj=new BigDecimal(df.format(count/num));
    		BigDecimal percent=new BigDecimal(df1.format(pjdj.doubleValue()*100));
    		percent.doubleValue();
    		}
    	}
    	//综合满意度
    	int zhmyd=Math.round((float)sum/num);
    	
    	String swry_dm = null;
		this.ctx = requestEvent.getCtx();
		if(ctx!=null){
			  Map userinfo = ctx.getUserinfo(); 
			  swry_dm = (String) userinfo.get("userId");
			  try{
				  YyfwPjPojo pjpojo = yyfwDao.searchPj(fxyy_id, swry_dm);
				  reqmap.put("pjxx", pjpojo);
			  }catch(Exception e){
				  
			  }
		}
		
     	
    	reqmap.put("zhmyd",zhmyd);
    	reqmap.put("fxyy_id",fxyy_id);
    	
    	resEvent.setResMap(reqmap);
    	resEvent.setFwordPath("/biz/bizcore/sjyy/tycx/tycx002/html/tycx002_pj.html");
    	return resEvent;
    }
    /**
     * 插入评价
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent insertPj(RequestEvent requestEvent) throws Exception{
		  logger.debug("debugger "+this.getClass().getName()+"_insertPj");
		  ResponseEvent resEvent=new ResponseEvent();
		  Map map=requestEvent.getRequestMap();
		  String pjjg=(String)requestEvent.getRequestMap().get("pjjg");
		  String pjyj=(String)requestEvent.getRequestMap().get("pjyj");
		  String fxyy_id=(String)requestEvent.getRequestMap().get("fxyy_id");
		  YyfwPjPojo tdp=new YyfwPjPojo();
		  String fxyypjxh= UUIDGenerator.getUUID();
		  tdp.setFxyypjxh(fxyypjxh);
		  tdp.setFxyyid(fxyy_id);
		  tdp.setPjdj_dm(pjjg);
		  tdp.setPjnr(pjyj);
		  
		  Map userinfo = null;
		  String swry_dm = null;
		  this.ctx = requestEvent.getCtx();
		  if(ctx!=null){
			  userinfo = ctx.getUserinfo(); 
			  swry_dm = (String) userinfo.get("userId");
		  }
		  tdp.setPjr_dm(swry_dm);
		  
		  yyfwDao.deletePj(fxyy_id, swry_dm);
		  
		  yyfwDao.insertPj(tdp);
		  HashMap resMap=new HashMap();
		  resMap.put("JSONDATA", RJson.UPDATE_TRUE);
		  resEvent.setResMap(resMap);
		  return resEvent;
	  }
    /**
     * 查询评价内容
     * @param requestEvent
     * @return
     * @throws Exception
     */
    public ResponseEvent selectPj(RequestEvent requestEvent) throws Exception {
        logger.debug("debugger "+this.getClass().getName()+"_selectPj");
        ResponseEvent resEvent = new ResponseEvent();
        HashMap reqmap=new HashMap();
        String pjdj=(String)requestEvent.getRequestMap().get("pjdj");
        String fxyy_id=(String) requestEvent.getRequestMap().get("fxyy_id");
        List<YyfwPjPojo> pjList=yyfwDao.selectPj(pjdj,fxyy_id);
        reqmap.put("JSONDATA", JsonUtil.toJson(pjList));
        resEvent.setResMap(reqmap);
        return resEvent;
    }
	
	
    
    public ResponseEvent getXiangqing(RequestEvent reqEvent) throws Exception{
    	logger.debug("debugger "+this.getClass().getName());
    	
        ResponseEvent resEvent = new ResponseEvent();
        HashMap<String, Object> resMap = new HashMap<String, Object>();
        
        String fxyyId=(String) reqEvent.getRequestMap().get("fxyyid");
        
     // 应用类型
     		List fxyylxList = CacheUtil.getCodeTable("DM_YYFW_FXYYLX");
     		// 单位
     		List yhdwList = CacheUtil.getCodeTable("T_YYFW_YHDW");
     		// 发布状态
     		List fbztList = CacheUtil.getCodeTable("DM_YYFW_FBZT");
        
		List<YyfwXiangqingPojo> xiangqingList=new ArrayList<YyfwXiangqingPojo>();
		String gnlj=null;
		//如果fxyyId非空，查询分析应用基本信息
		if(!YyfbUtils.isEmpty(fxyyId)){
			xiangqingList=yyfwDao.getXiangqing(fxyyId);
			//功能路径
			String folderId=xiangqingList.get(0).getGnlj();
			gnlj=getGnljMc(folderId);
			
			resMap.put("xiangqing", (xiangqingList.get(0)));
		}
        
        resMap.put("fxyylxList", fxyylxList);
        resMap.put("yhdwList", yhdwList);
        resMap.put("fbztList", fbztList);
        resMap.put("gnlj", gnlj);
        resMap.put("fxyyId", fxyyId);
        
        resEvent.setResMap(resMap);
        resEvent.setFwordPath("/biz/bizcore/sjyy/tycx/tycx002/html/tycx002Xiangqing.html");
        
    	return resEvent;
    }
    
    //获取功能路径名称
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
    
    
	//打开分享窗口
	public ResponseEvent openFenxiang(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		Map userInfo = null;
		this.ctx = reqEvent.getCtx(); 
		if(ctx!=null){
			userInfo = ctx.getUserinfo();
  		}
		
		if(userInfo!=null){
			String sql = "select swjg_dm id ,swjgmc name ,sjswjg_dm pid from dm_gy_swjg where swjg_dm like ?||'%' and YXBZ ='Y' and  XYBZ = 'Y' ";
			String znfw = (String) userInfo.get("znfw");
			ArrayList param=new ArrayList();
			param.add(znfw);
			List jgList=jdbcDao.queryforlist(sql,param);
			resMap.put("jgList", JsonUtil.toJson(CssnjDao.listLowerCase(jgList)));
		}
		
		String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		resMap.put("url", "/tykf/request_http?tld=YyunitService_openYy&fxyy_id="+fxyyid);
		resMap.put("fxyyid",fxyyid);
		resEvent.setResMap(resMap);
		resEvent.setFwordPath("/biz/bizcore/sjyy/tycx/tycx002/html/tycx002Fenxiang.html");
		return resEvent;
	}
	
	public ResponseEvent searchFxRy(RequestEvent reqEvent) throws Exception {
		
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		Map userInfo = null;
		this.ctx = reqEvent.getCtx(); 
		if(ctx!=null){
			userInfo = ctx.getUserinfo();
  		}
		
		if(userInfo==null){
			JSONDATA.put("state",0);
			resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resMap);
			return resEvent;
		}
		
		String swjg_dms =(String) reqEvent.getRequestMap().get("swjg_dms");
		String swry_dm =(String) reqEvent.getRequestMap().get("swry_dm");
		String swry_mc =(String) reqEvent.getRequestMap().get("swry_mc");
		
		ArrayList<String> param = new ArrayList<String>(); 
		
		String sql = 
			"select A.Swry_Dm,A.Rysfmc,B.Swryxm	\n" +
			"  from dm_qx_swrysf A,dm_gy_swry B \n" + 
			" where A.YXBZ ='Y'				\n" + 
			"   and A.Swry_Dm = B.Swry_Dm \n" +
			"	and sfswjg_dm like ? || '%'\n"   ;

		String znfw = (String) userInfo.get("znfw");
 		param.add(znfw);
		if(!TycxUtils.isEmpty(swjg_dms)){
			sql += " and sfswjg_dm in ('"+swjg_dms.replace(",","','")+"') " ;
		}
		if(!TycxUtils.isEmpty(swry_dm)){
			sql += " and A.swry_dm like ? || '%'\n" ;
			param.add(swry_dm);
		}
		if(!TycxUtils.isEmpty(swry_mc)){
			sql += " and B.Swryxm like '%' || ? || '%'";
			param.add(swry_mc);
		}
		
		List list = jdbcDao.queryforlist(sql,param);
		JSONDATA.put("state", 1);
		JSONDATA.put("list", list);
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		return resEvent;
	}
	
	
	//保存分享信息
	public ResponseEvent saveFenxiang(RequestEvent reqEvent) throws Exception {
		logger.debug("debugger " + this.getClass().getName());
		ResponseEvent resEvent = new ResponseEvent();
		//取权限
		ctx = reqEvent.getCtx();
		Map userInfo=null;
		String swry_dm = null;
		String rymc = "";
		
   	    if(ctx!=null){
	  	   userInfo =	ctx.getUserinfo();
	  	   swry_dm = (String) userInfo.get("userId");
	  	   rymc =  (String) userInfo.get("swryxm");
	   	}
   	    
		HashMap<String, Object> resMap = new HashMap<String, Object>();
	    String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		String fxyymc = yyfwDao.fxyymc(fxyyid);
		String title = rymc +"-分享应用-"+fxyymc;
		
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

		try{
			
			yyfwDao.insertFX(reqEvent.getRequestMap(),swry_dm,title);
			JSONDATA.put("state", 1);

		}catch (Exception e) {
			
			JSONDATA.put("state", 0);
			JSONDATA.put("message", e.getMessage());

		}
		
 		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		
		return resEvent;
	}
	
	
	/** 
	 * 打开收藏页面
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent openSc(RequestEvent reqEvent) throws Exception {
	
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
 	
		String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		
		resMap.put("fxyyid", fxyyid);
		resEvent.setResMap(resMap);
		
		resEvent.setFwordPath("/biz/bizcore/sjyy/yyfb/html/yyfw_sc.html");
		return resEvent;
		
		
	}
	
	/**
	 * 查询收藏文件夹
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent searchScFolder(RequestEvent reqEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		Map userInfo = null;
		this.ctx = reqEvent.getCtx(); 
		if(ctx!=null){
			userInfo = ctx.getUserinfo();
  		}
		
		if(userInfo==null){
			JSONDATA.put("state",0);
			resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resMap);
			return resEvent;
		}
		
		String sql = " select wjjmc,uuid from YYFW_YYSC_FOLDER where userid= ? and xybj='Y' order by xsxh  ";
		
	  	String  swry_dm = (String) userInfo.get("userId");
	  	ArrayList<String> param = new ArrayList<String>(); 
	  	param.add(swry_dm);
	  	List list = jdbcDao.queryforlist(sql,param);
	  	JSONDATA.put("state", 1);
		JSONDATA.put("list", list);
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		
		return resEvent;
		
	}
	
	/**
	 * 添加收藏文件夹
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent addScFolder(RequestEvent reqEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		String folderMc = (String) reqEvent.getRequestMap().get("folderMc");
		
		Map userInfo = null;
		this.ctx = reqEvent.getCtx(); 
		if(ctx!=null){
			userInfo = ctx.getUserinfo();
  		}
		
		if(userInfo==null){
			JSONDATA.put("state",0);
			JSONDATA.put("message","保存失败！请先登陆。。。");
			resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
			resEvent.setResMap(resMap);
			return resEvent;
		}
		
		String  swry_dm = (String) userInfo.get("userId");
		
		String sql =  
				" INSERT INTO YYFW_YYSC_FOLDER							\n" +
				"  (UUID,WJJMC,USERID,XSXH,LRRQ,LRRY_DM)				\n" + 
				" VALUES											\n" + 
				"  (?, ?, ?, (select max(nvl(xsxh,0))+1 xsxh from YYFW_YYSC_FOLDER ),  SYSDATE, ?)  \n";

	  	ArrayList<String> param = new ArrayList<String>(); 

	  	String  uuid = UUIDGenerator.getUUID();
	  	
	  	param.add(uuid)		;
	  	param.add(folderMc) ;
	  	param.add(swry_dm)  ;
	  	param.add(swry_dm)  ;
 
		jdbcDao.update(sql,param);
		
		JSONDATA.put("state",1);
 		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		return resEvent;
		
	}
	
	
	/**
	 * 作废收藏文件夹
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent zfScFolder(RequestEvent reqEvent) throws Exception {

		ResponseEvent resEvent = new ResponseEvent();
		
		
		
		return resEvent;
		
	}
	
	/**
	 * 收藏应用
	 * @param reqEvent
	 * @return
	 * @throws Exception
	 */
	public ResponseEvent scYy(RequestEvent reqEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resMap = new HashMap<String, Object>();
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();
		
		String flid = (String) reqEvent.getRequestMap().get("flid"); 
		
		String fxyyid = (String) reqEvent.getRequestMap().get("fxyyid");
		
		String sql = "select count(1) c from YYFW_YYSC where folder_uuid=? and yyid=? ";
		
		ArrayList<String> param = new ArrayList<String>(); 

		param.add(flid);
		param.add(fxyyid);
		Map map = jdbcDao.queryformap(sql, param);
		Integer c = Integer.valueOf(map.get("C").toString());
		
		if(c<1){
			
			sql = "insert  into YYFW_YYSC (uuid,Folder_Uuid,yyid,xh) values (?,?,?,(select max(nvl(xh,0))+1 from yyfw_yysc) ) ";
			
			String  uuid = UUIDGenerator.getUUID();
			
			param.clear()		;
			param.add(uuid)		;
		  	param.add(flid)		;
		  	param.add(fxyyid)   ;
	 		
		  	jdbcDao.update(sql,param);
		}
	  	
		JSONDATA.put("state",1);
 		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
		resEvent.setResMap(resMap);
		return resEvent;
		
	}
	
	public ResponseEvent renameScFl(RequestEvent reqEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resMap = new HashMap<String, Object>();
 		
		String id = (String) reqEvent.getRequestMap().get("id");
		String text = (String) reqEvent.getRequestMap().get("text");
		
		String sql = "  update yyfw_yysc_folder set wjjmc = ? where uuid =? ";
		
		ArrayList<String> param = new ArrayList<String>();
		param.add(text);
		param.add(id);

		jdbcDao.update(sql,param);
		
		resMap.put("state",1);
		
		resEvent.setResMap(resMap);
		return resEvent;
		
	}
	
	
	public ResponseEvent removeScFl(RequestEvent reqEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();

		HashMap<String, Object> resMap = new HashMap<String, Object>();
 		
		String id = (String) reqEvent.getRequestMap().get("id");
		
		
		String sql = "  delete yyfw_yysc_folder where uuid =? ";
		ArrayList<String> param = new ArrayList<String>();
		param.add(id);
		
		jdbcDao.update(sql,param);
		
		resMap.put("state",1);
		
		resEvent.setResMap(resMap);
		return resEvent;

	}
	
	
	private String formetDate(){
		// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currDate=sdf.format(new Date());
		return currDate;
	}
	
	
    
    

}
