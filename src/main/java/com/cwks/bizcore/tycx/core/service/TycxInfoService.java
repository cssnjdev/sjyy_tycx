package com.cwks.bizcore.tycx.core.service;

import com.alibaba.fastjson.JSONException;
import com.cwks.common.api.dto.ext.ResponseEvent;
import com.cwks.common.api.dto.ext.RequestEvent;
import com.cwks.common.api.dto.ext.UserContext;
import com.cwks.common.service.impl.BaseService;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.bizcore.comm.utils.JsonUtil;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxdyDao;
import com.cwks.bizcore.tycx.core.dao.Tycx001CxCxjgdyDao;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxdyPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.bizcore.tycx.core.dao.Tycx002DzcxDao;
import com.cwks.bizcore.tycx.core.vo.CXDzcxVO;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.tycx.core.dao.TycxInfoDao;
import com.cwks.common.service.impl.BaseServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("TycxInfoService")
public class TycxInfoService  extends BaseServices {

    @Autowired
    private TycxInfoDao tycxInfoDao;
    
    @Autowired
    private Tycx001CxCxjgdyDao Tycx001CxCxjgdyDao;
    
    @Autowired
    private Tycx001CxCxdyDao tycx001CxCxdyDao;
    
    @Autowired
    private Tycx002DzcxDao tycx002DzcxDao;
    
    private static Logger logger = LoggerFactory.getLogger(TycxInfoService.class);
  
    public ResponseEvent getWdxx(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");// sqlxh	
    	Map map = tycxInfoDao.getWdxx(sqlxh);
    	if(map==null||map.size()==0){
			return resEvent;
		}
    	reqmap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(reqmap);
		return resEvent;
		
    } 
    
    //获取统计条
    public ResponseEvent getTjtj(RequestEvent requestEvent)  throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String, Object> reqmap = new HashMap<String, Object>();
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");// sqlxh	
    	List list = tycxInfoDao.getTjtj(sqlxh);
    	reqmap.put("JSONDATA", JsonUtil.toJson(list));
    	resEvent.setResMap(reqmap);
		return resEvent;
    	
    }
    
    public ResponseEvent getColdata(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String,Object> reqmap = new HashMap<String,Object>();
    	String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh");//sqlxh	

    	List list = tycxInfoDao.getColdata(sqlxh);
    	reqmap.put("JSONDATA", JsonUtil.toJson(list));
    	resEvent.setResMap(reqmap);
    	
		return resEvent;

    }
    
    public ResponseEvent showChart(RequestEvent requestEvent) throws Exception {
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String,Object> reqmap = new HashMap<String,Object>();
    	   //获取sqlxh
        String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");	
    	String type = (String) requestEvent.getRequestMap().get("type");// sqlxh	
     	reqmap.put("sqlxh", sqlxh);
     	reqmap.put("type", type);
    	resEvent.setResMap(reqmap);
		resEvent.setFwordPath("/biz/core/ext/tycx_jy/demo1/showChart.jsp");
		return resEvent;

    }
    
    public ResponseEvent searchSington(RequestEvent requestEvent) throws Exception{
    	
    	ResponseEvent resEvent = new ResponseEvent();
    	HashMap<String,Object> reqmap = new HashMap<String,Object>();
    	String sqlxh=(String) requestEvent.getRequestMap().get("sqlxh");	
    	Map map = tycxInfoDao.searchSington(sqlxh);
    	reqmap.put("JSONDATA", JsonUtil.toJson(map));
    	resEvent.setResMap(reqmap);
		return resEvent;
 		
    }
    
    /**
     * 
     * @param requestEvent
     * @return
     * @throws Exception
     * 测试钻取是否 有数据
     */
    public ResponseEvent testZqData(RequestEvent requestEvent) throws Exception{
   
    	ResponseEvent resEvent = new ResponseEvent();
    	
		HashMap<String,Object> resMap = new HashMap<String,Object>();			
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

 		
		final String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh"); //sqlxh		
		final String queryParams = (String) requestEvent.getRequestMap().get("queryParams");// 条件
		final String queryType = (String) requestEvent.getRequestMap().get("queryType");
  
		List<Tycx001CxCxjgdyPojo> cxjgList =Tycx001CxCxjgdyDao.selectBySqlxh(sqlxh);//查询结果列

		//取权限
		ctx = requestEvent.getCtx();
		String swry_dm="";
		String swrysf_dm="";
		Map userInfo=null;
		if(ctx!=null){
  	    	userInfo =	ctx.getUserinfo();
  	    	swry_dm =(String)userInfo.get("userId");
  	    	swrysf_dm =(String)userInfo.get("swrysf_dm");
	   	}
		 
		Boolean flag = valideCxtj(queryParams);
		String sql = null;
		if (flag) {
			// 获取查询定义信息
			Tycx001CxCxdyPojo tycx001CxCxdyPojo = new Tycx001CxCxdyPojo();
			tycx001CxCxdyPojo.setSqlxh(sqlxh);
			
			Tycx001CxCxdyPojo Tycx001CxCxdyPojo = tycx001CxCxdyDao.selectByPKey(tycx001CxCxdyPojo);
			 
			String sqlstr = Tycx001CxCxdyPojo.getSqlstr();
			
			CXDzcxVO dzcxVo = new CXDzcxVO();
			dzcxVo.setUserMap(userInfo);
			dzcxVo.setSqlxh(sqlxh);
 			dzcxVo.setQueryParams(queryParams);
 			dzcxVo.setQueryType(queryType);
			dzcxVo.setSql(sqlstr);
			dzcxVo.setSjymc(Tycx001CxCxdyPojo.getSjymc());
			dzcxVo=LoopQueryParam(dzcxVo);
			dzcxVo=LoopWrapParams(dzcxVo);
			dzcxVo = createSql(dzcxVo,cxjgList);
  			
			dzcxVo.setPage(1);
			dzcxVo.setLimit(1);

			Integer t =0;
			try{
				List list = tycx002DzcxDao.executeSql(dzcxVo);
				t = list.size();
				if(t>0){
					JSONDATA.put("state", "1");
				}else{
					JSONDATA.put("state", "0");
					JSONDATA.put("message", "没有数据了，已完成下钻！");
				}
			}catch(Exception e){
				JSONDATA.put("state", "0");
				JSONDATA.put("message", "查询异常！");
			}
			
			
		}else{
			JSONDATA.put("state", "0");
			JSONDATA.put("message", "查询条件校验失败！");
		}
		
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));
    	resEvent.setResMap(resMap);
    	return resEvent;
    }
    
    /**
     * 通用查询执行存储过程查询		
     * @param requestEvent  
     * @return				
     * @throws Exception    
     * 
     */
    public ResponseEvent executeQueryPro (RequestEvent requestEvent) throws Exception {
    	ResponseEvent resEvent = new ResponseEvent();
    	
    	
		return resEvent;	
    }
    
	/**
	 * 
	 * @ 
	 * 
	 */
	public ResponseEvent getSelect(RequestEvent requestEvent) throws Exception {
		
		ResponseEvent resEvent = new ResponseEvent();
    	
    	final String sqlxh = (String) requestEvent.getRequestMap().get("sqlxh"); //sqlxh		
		final String lmc = (String) requestEvent.getRequestMap().get("lmc");     //条件
	    
		Map map = tycxInfoDao.getOneJgl(sqlxh,lmc);
		
		String table = (String) map.get("DMSQL");
		
		
		List hcbList = CacheUtil.getCodeTable("T_XT_HCBXX","table_name='"+table+"'");
		Map<String,Object> hcbmap=(Map<String, Object>) hcbList.get(0);
		
		String dataQuerySql = (String) hcbmap.get("dataQuerySql");
		
		String mc = (String) hcbmap.get("defaultValueColumn");
		String dm = (String) hcbmap.get("indexColumns");

		List list =  tycxInfoDao.queryforlist(dataQuerySql);
		
		HashMap<String,Object> JSONDATA = new HashMap<String, Object>();

		JSONDATA.put("list", list);
		
		JSONDATA.put("state",1);

		HashMap<String,Object> resMap = new HashMap<String,Object>();			
		
		resMap.put("JSONDATA", JsonUtil.toJson(JSONDATA));

		resEvent.setResMap(resMap);
		
		return resEvent;	
			
    }
	
	
    public List<Tycx001CxCxjgdyPojo> queryCxjgdyListFilterByFzyj(CXDzcxVO dzcxVO) throws Exception{
		final List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
		//维度和指标,只保留维度和指标指定的列,其他移除(Constant.queryType_TJ 查询类型_统计查询的时候),格式为:LMC1,LMC2,LMC3...
		List<Tycx001CxCxjgdyPojo> list=dzcxVO.getCxjgList();
        List<Tycx001CxCxjgdyPojo> tempCxjgdyList = new ArrayList<Tycx001CxCxjgdyPojo>();
        boolean isHasWdOrZb = false;
        final String wd = dzcxVO.getWd();
        if(!TycxUtils.isEmptyString(wd)){
            isHasWdOrZb = true;
            final String[] wdArray = wd.split(",");
            for(String wdFieldName : wdArray){
                for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                    if(wdFieldName.equals(cxjgdyInfo.getLmc())){
                        tempCxjgdyList.add(cxjgdyInfo);
                        break;
                    }
                }
            }
        }
        
        final String zb = dzcxVO.getZb();
        if(!TycxUtils.isEmptyString(zb)){
            isHasWdOrZb = true;
            final String[] zbArray = zb.split(",");
            for(String zbFieldName : zbArray){
                for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                    if(zbFieldName.equals(cxjgdyInfo.getLmc())){
                        tempCxjgdyList.add(cxjgdyInfo);
                        break;
                    }
                }
            }
        }
        final String cxjgl_checked=dzcxVO.getCxjgl_checked();
        if(!TycxUtils.isEmptyString(cxjgl_checked)){
       	 final String[] cxjgl_CheckArray = cxjgl_checked.split(",");
            for(String CheckCgl : cxjgl_CheckArray){
                for(Tycx001CxCxjgdyPojo cxjgdyInfo : list){
                    if(CheckCgl.equals(cxjgdyInfo.getLmc())){
                        tempCxjgdyList.add(cxjgdyInfo);
                        break;
                    }
                }
            }
        }
        if(isHasWdOrZb){
            list = tempCxjgdyList;
        }
		 return list;
	 }
	
    
    
    
    /**
	 * 
	 * @name 循环查询条件,判断是否包含分支语句、是否有维度、指标
	 * @description 相关说明
	 * @time 创建时间:2015-6-3下午3:21:05
	 * @param dzcxVo
	 *            dzcxVo
	 * @return
	 * @throws 
	 *             json异常
	 * @throws SwordBaseCheckedException
	 *             SwordBaseCheckedException
	 * @author 作者jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO LoopQueryParam(CXDzcxVO dzcxVo) throws Exception{
		// 调用cxtjBO,根据sqlxh查询是否有分支语句
		final String sqlxh = dzcxVo.getSqlxh();
//		final List<CXCxtjdyFzyjInfoVO> vo = fzyjBO
//				.queryCxtjdyFzyjListBySqlxh(sqlxh);
//		if (!OdsUtils.isEmpty(vo)) {
//			dzcxVo.setTjlmc(vo.get(0).getTjlmc());
//		}
		final String queryParams = dzcxVo.getQueryParams();
		List tjJSONArr = null;
		if (!TycxUtils.isEmpty(queryParams)) {
			tjJSONArr = JsonUtil.toListMap(queryParams);
			String zb = "";
			String wd = "";
			String cxjgl_checked="";
			for (int i = 0; i < tjJSONArr.size(); i++) {
				Map<String, Object> tjMap  = (Map<String, Object>) tjJSONArr.get(i);
				final String nameStr = (String) tjMap.get("name");
				final String valueStr = (String) tjMap.get("value");
//				if (nameStr.equalsIgnoreCase(dzcxVo.getTjlmc())) {
//					dzcxVo.setTjlz(valueStr);
//					dzcxVo.setTjfzyj(fzyjBO
//							.queryCxtjdyFzyjInfoBySqlxhAndTjlmcTjlz(sqlxh,
//									nameStr, valueStr).getSqlstr());
//					break;
//				}

				// 如果是交叉表查询或者是统计查询，循环出指标和维度

				if (Constant.WRAPPARAMS_ZB.equals(nameStr)) {
					zb = zb + valueStr + ",";
				}
				if (Constant.WRAPPARAMS_WD.equals(nameStr)) {
					wd = wd + valueStr + ",";
				}
				if(Constant.CXJGL_CHECKED.equals(nameStr)){
					cxjgl_checked=valueStr;
				}

			}
			if (!TycxUtils.isEmpty(zb)) {
				zb = zb.substring(0, zb.length() - 1);
			}
			if (!TycxUtils.isEmpty(wd)) {
				wd = wd.substring(0, wd.length() - 1);
			}
			if (!TycxUtils.isEmpty(cxjgl_checked)) {
				cxjgl_checked = cxjgl_checked.substring(0, cxjgl_checked.length() - 1);
			}
			dzcxVo.setZb(zb);
			dzcxVo.setWd(wd);
			dzcxVo.setCxjgl_checked(cxjgl_checked);
		}
		return dzcxVo;
	}
	

	
	/**
	 * 查询条件校验
	 * 
	 * @name
	 * @description 查询条件校验:1.校验条件中是否存在SELECT等关键字 2.如果是纳税人端查询,校验条件中是否包含djxh
	 * @time 创建时间:2015-11-6下午4:26:48
	 * @param cxtjVo
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author mahongtao
	 * @throws Exception 
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private Boolean valideCxtj(String queryParams) throws Exception {
		/**
		 * 常规校验:校验条件中是否存在SELECT等关键字
		 */
		Boolean commonCheckFlag = this.isHave(queryParams);
		if (!commonCheckFlag) {
			throw new Exception("1010330020100036");
		}
		return true;
	}

	public boolean isHave(String queryParams) {
		// TODO Auto-generated method stub
		if (!TycxUtils.isEmpty(queryParams)) {
			String a="[{name:'SQLMC',value:'',type:'string',displayValue:''}]";
			final String[] strs = { "SELECT", "DELETE", "DROP", "UPDATE", "CREATE" };// 定义字符串数组
			final List<Map> queryParamsArr = JsonUtil.toListMap(queryParams);
			for (int j = 0; j < strs.length; j++) {
				for (int i = 0; i < queryParamsArr.size(); i++) {
					//final Map<String, Object> queryParamsMap = ((JSONObject) queryParamsArr.get(i)).getMap();
					Map queryParamsMap=queryParamsArr.get(i);
					final String tjValue = ((String) queryParamsMap.get("value"));
					if (!TycxUtils.checkCxtj(tjValue)) {
						return false;
					}

				}
			}

		}
		return true;
	}
	
	/**
	 * 
	 * @name 
	 *       循环包装条件,包装条件主要包含统计列，指标；模糊查询条件；{statisticParams:[{name:xxx,type:GROUP}
	 *       ,{name:xxx,type:SUM},...],resultQueryParams:[.....]}
	 * @description 相关说明
	 * @time 创建时间:2015-5-11下午2:37:20
	 * @param dzcxVo
	 *            dzcxVo
	 * @author jinln
	 * @throws SwordBizCheckedException
	 * @throws JSONException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO LoopWrapParams(CXDzcxVO dzcxVo)	throws Exception {
		String wrapParams = dzcxVo.getWrapParams();
		
		if (!TycxUtils.isEmpty(wrapParams)) {
			List wrapParamList= JsonUtil.toListMap(wrapParams);
			//wrapParamMap = (Map<String, Object>) JsonUtil.toMap(wrapParams);
			// 如果是统计功能查询
			if (Constant.queryType_TJGN.equals(dzcxVo.getQueryType())
					|| Constant.queryType_EXPORT_TJ.equals(dzcxVo
							.getQueryType())) {
				String tjSelect = "";
				String tjGroup = "";
				String tjOrder = "";
				List<Map<String, Object>> tjlList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < wrapParamList.size(); i++) {
					final Map<String, Object> param =(Map<String, Object>) wrapParamList.get(i);
					Map<String, Object> tjlMap = new HashMap<String, Object>();
					final String name = (String) param.get("name");
					final String type = (String) param.get("type");
					final String text = (String) param.get("text");
					if (type.equals(Constant.TJ_WDL)) {// 维度列
						tjSelect = tjSelect + name + ",";
						tjGroup = tjGroup + name + ",";
						tjOrder = tjOrder + name + ",";
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_COUNT)) {
						tjSelect = tjSelect + "COUNT(*) " + '"' + name + '"'
								+ ",";
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_SUM)) {
						tjSelect = tjSelect + " DECODE((" + "SUM(" + name + ")"
								+ "),null,0,(" + "SUM(" + name + ")" + ")) "
								+ name + ",";// ROUND(DECODE((AVG(ZCZB)),null,0,(AVG(ZCZB))),2)
						tjlMap.put("lmc", name);
					} else if (type.equals(Constant.TJLX_AVG)) {
						tjSelect = tjSelect + " DECODE((" + "AVG(" + name + ")"
								+ "),null,0,(" + "AVG(" + name + ")" + ")) "
								+ name + ",";// +"AVG("+lmc+")"+lmc+"_AVG"+",";
						tjlMap.put("lmc", name);

					}
					tjlMap.put("lms", text);
					tjlList.add(tjlMap);
				}
				if (tjSelect.length() > 0) {
					tjSelect = tjSelect.substring(0, tjSelect.length() - 1);
				}
				if (tjGroup.length() > 0) {
					tjGroup = tjGroup.substring(0, tjGroup.length() - 1);
				}
				if (tjOrder.length() > 0) {
					tjOrder = tjOrder.substring(0, tjOrder.length() - 1);
				}
				dzcxVo.setTjSelect(tjSelect);
				dzcxVo.setTjGroup(tjGroup);
				dzcxVo.setTjOrder(tjOrder);
				dzcxVo.setTjlList(tjlList);
			} else if(Constant.queryType_JS.equals(dzcxVo.getQueryType())){
				String jsbds="";
				for (int i = 0; i < wrapParamList.size(); i++) {
				final Map<String, Object> param =(Map<String, Object>) wrapParamList.get(i);
				final String name = (String) param.get("name");
				final String text = (String) param.get("text");
				jsbds=jsbds+name+",";				
				}
				if(!TycxUtils.isEmpty(jsbds)){
					jsbds=jsbds.substring(0, jsbds.length()-1);
					dzcxVo.setJsbds(jsbds);
				}
			}
		}
		return dzcxVo;
	}
	
	/**
	 * 
	 * @name 创建SQL
	 * @description 相关说明
	 * @time 创建时间:2015-5-8上午10:27:12
	 * @param dzcxVo
	 *            dzcxVo
	 * @param cxdyVo
	 *            cxdyVo
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author jinln
	 * @throws Exception 
	 * @throws SQLException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO createSql(CXDzcxVO dzcxVO,List<Tycx001CxCxjgdyPojo> cxjgList) throws Exception{
		// TODO Auto-generated method stub
		String sql = dzcxVO.getSql();
		// 将sql并去掉回车换行
		sql = sql.replaceAll("\\s+", " ").replaceAll("\\s+and\\s+", " AND ").replaceAll("\\s+or\\s+", " OR ");
		// SQL防注入检查
		if (!TycxUtils.checkSql(sql)) {
			throw new Exception("1010330020100136");
		}
		// 处理sql中包含[* *]、[# #]begin
		if (sql.contains("[*") || sql.contains("[#")) {
			dzcxVO = getTjcxSql(dzcxVO);
		}
		dzcxVO.setSql(sql);
		// 如果传入的查询条件非空，封装SQL
		dzcxVO = getExecuteSql(dzcxVO);
		sql=dzcxVO.getSql();
		 
		if (Constant.queryType_TJ.equals(dzcxVO.getQueryType())) {
			sql = TycxUtils.addWdAndzbToSql(sql, dzcxVO.getWd(), dzcxVO.getZb(),cxjgList);
			dzcxVO.setSql(sql);
		}		
		// 如果是统计功能（queryType_TJGN="4"），根据统计条件，封装SQL
		if (Constant.queryType_TJGN.equals(dzcxVO.getQueryType())
				|| Constant.queryType_EXPORT_TJ.equals(dzcxVO.getQueryType())) {
			sql = "SELECT " + dzcxVO.getTjSelect() + " FROM (" + sql + ")"
					+ " GROUP BY " + dzcxVO.getTjGroup() + " ORDER BY "
					+ dzcxVO.getTjOrder();
			dzcxVO.setSql(sql);
		}
		//如果是计算
		if(Constant.queryType_JS.equals(dzcxVO.getQueryType())){
			sql="SELECT "+dzcxVO.getJsbds()+" FROM ("+sql+")";
			dzcxVO.setSql(sql);
		}
		return dzcxVO;
	}
	
	/**
	 * 
	 * @name 统计查询SQL包装
	 * @description 相关说明
	 * @time 创建时间:2015-5-7上午10:20:43
	 * @param sql
	 *            sql
	 * @param wrapParamsVo
	 *            包装参数
	 * @return
	 * @author jinln
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO getTjcxSql(CXDzcxVO dzcxVo) {
		String sql = dzcxVo.getSql();
		while (sql.contains("[*")) {
			dzcxVo.setQueryType_Tj_bj(Constant.QueryType_Tj_bj);
			final String varStr = sql.substring(sql.indexOf("[*"), sql
					.indexOf("*]") + 2);
			final String zb = dzcxVo.getZb();
			sql = sql.replace(varStr, zb);
		}
		while (sql.contains("[#")) {
			final String varStr = sql.substring(sql.indexOf("[#"), sql
					.indexOf("#]") + 2);
			final String wd = dzcxVo.getWd();
			sql = sql.replace(varStr, wd);
		}
		dzcxVo.setSql(sql);
		return dzcxVo;
	}
	
	/**
	 * 
	 * @name 获得可执行的SQL
	 * @description 1、如果valueStr非空，则将@nameStr@替换成valueStr。
	 *              2、如果valueStr为空，先查找select和where直接的结果列是否包含
	 *              {....@nameStr@....}这样的字符，
	 *              如果包含，替换成""，再查找条件中[.....@nameStr@...]，将它替换成1=1。 3、最后把(AND
	 *              1=1,OR 1=1,(),{,},[,])这些符号替换成""。
	 *              4、如果排序列非空，按排序字段、排序类型把SQL包装成可执行的SQL。
	 * @time 创建时间:2015-5-8上午10:27:38
	 * @param dzcxVo
	 *            dzcxVo
	 * @param cxdyVo
	 *            cxdyVo
	 * @author jinln
	 * @throws SwordBaseCheckedException
	 * @throws SQLException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public CXDzcxVO getExecuteSql(CXDzcxVO dzcxVO)  {
		//JSONArray tjJSONArr = null;
		final String queryParams = dzcxVO.getQueryParams();
		String sqlStr = dzcxVO.getSql().toUpperCase();
		if (!TycxUtils.isEmpty(queryParams)) {
			//tjJSONArr = JSONArray.fromObject(queryParams);
			List<Map> list= JsonUtil.toListMap(queryParams);
			for (int i = 0; i < list.size(); i++) {
				//Map<String, Object> tjMap = new HashMap<String, Object>();
				Map tjMap =  list.get(i);
				final String nameStr = ((String) tjMap.get("name"))
						.toUpperCase();
				final String typeStr = (String) tjMap.get("type");
				String valueStr = (String) tjMap.get("value");
				// 如果有特殊字符[ 或]则替换为别的
				// 如果是多个参数用逗号隔开，例如in中的参数，需要每个参数加''后再替换
				if ((!TycxUtils.isEmpty(valueStr)) && valueStr.indexOf(",") > 0) {
					valueStr = "'" + valueStr.replaceAll(",", "','") + "'";
				}
				// 替换SQL中@nameStr@
				if (!TycxUtils.isEmpty(valueStr)) {
					if (sqlStr.indexOf("@" + nameStr + "@") >= 0) {
						if (("string".equals(typeStr) || "date".equals(typeStr))
								&& valueStr.indexOf("'") < 0) {
							valueStr = "'" + valueStr + "'";
						}
						sqlStr = sqlStr.replace("@" + nameStr + "@", valueStr);
					}
				} else {// 值为空，SQL中包含@nameStr@，则将[XXXX@nameStr@]部分替换为1=1
					final int index = sqlStr.indexOf("@" + nameStr + "@");// 找到@nameStr@位置
					int start;
					int end;
					if (sqlStr.lastIndexOf("{", index) > -1) {// 如果包含{}符号，表示在查询结果中存在条件变量
						start = sqlStr.lastIndexOf("{", index);// 找{的位置
						end = sqlStr.indexOf("}", index);// 找}的位置
						if (end >= 0) {
							final String str = sqlStr.substring(start, end + 1);// 截取{......@@.....}字符串
							sqlStr = sqlStr.replace(str, "''");// 替换{......@@.....}为""
						}
					} else if (sqlStr.lastIndexOf("[", index) > -1) {
						start = sqlStr.lastIndexOf("[", index);// 从index位置向前找[位置
						end = sqlStr.indexOf("]", index);// 从index位置向后找]位置
						if (end >= 0) {
							final String s = sqlStr.substring(start, end + 1);// 截取[XXX@nameStr@]
							sqlStr = sqlStr.replace(s, " 1=1 ");// 把[XXX@nameStr@]替换成1=1
						}
					}
				}

			}
		}
		
		String[] sqlArr = sqlStr.split("\\[");
		String real_sql = sqlArr[0];
		if (sqlArr.length > 1) {
			for (int i = 1; i < sqlArr.length; i++) {
				String[] sqlArr_mx = sqlArr[i].split("\\]");
				for (int j = 0; j < sqlArr_mx.length; j++) {
					if (sqlArr_mx[j].indexOf("@") < 0) {
						// 处理LIKE条件,20120601，林全加
						sqlArr_mx[j] = sqlArr_mx[j].replaceAll("\\s+[Ll][Ii][Kk][Ee]\\s+", " LIKE ");// 关键字LIKE改为大写
						real_sql = real_sql + sqlArr_mx[j];
					} else {
						real_sql = real_sql + " 1=1 ";
					}
				}
			}
		}

		real_sql = real_sql.replaceAll(
				"\\[[^\\[|\\]]*@[^\\[|\\]]*@(.*?)[^\\[|\\]]*\\]", " 1 = 1 ");
		real_sql = real_sql.replaceAll("\\[|\\]", "");
		real_sql = real_sql.replaceAll("@(.*?)@", "''");
		// 用正则表达式删除掉1=1条件,经典，林全，2012-03-31，山重水复疑无路，柳暗花明又一村
		real_sql = real_sql.replaceAll("AND\\s+1=1", " ").replaceAll(
				"OR\\s+1=1", " ").replaceAll("\\s+", " ");
		// 去掉所有的"["和"]"
		real_sql = real_sql.replaceAll("\\[", "").replaceAll("\\]", "");
		// 去掉空的(),((()))
		real_sql = real_sql.replaceAll("\\(+\\s+\\)+", "");
		// 去掉SQL中的{}，暂时不会考虑结果变量为空的情况 add by lxn 20141101
		real_sql = real_sql.replaceAll("\\{", "");
		real_sql = real_sql.replaceAll("\\}", "");

		dzcxVO.setSql(real_sql);
		return dzcxVO;
	}
	

	
}