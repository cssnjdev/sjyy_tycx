package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.comm.utils.JsonUtil;
//import com.cssnj.core.util.StoredProcParamObj;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx002DzcxMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002DzcxPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002FjPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002TuisongPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx002WtfkPojo;
import com.cwks.bizcore.tycx.core.vo.CXDzcxVO;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Tycx002DzcxDao {
	@Autowired
	JdbcDao jdbcDao;
    @Autowired
    private Tycx002DzcxMapper tycx002DzcxMapper;

    public List select(Tycx002DzcxPojo pojo){
        return tycx002DzcxMapper.select(pojo);
    };
 
 
   
   
    public Tycx002DzcxPojo selectByPKey(Tycx002DzcxPojo pojo){
        return  tycx002DzcxMapper.selectByPKey(pojo);
    };
    
    public void updateByPKey(Tycx002DzcxPojo pojo){
        tycx002DzcxMapper.updateByPKey(pojo);
    };
    
    public void updateByPKeySelective(Tycx002DzcxPojo pojo){
        tycx002DzcxMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx002DzcxPojo pojo){
        tycx002DzcxMapper.insert(pojo);
    };

    public void insertSelective(Tycx002DzcxPojo pojo){
        tycx002DzcxMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx002DzcxPojo pojo){
        tycx002DzcxMapper.deleteByPKey(pojo);
    };
    /**
     * 执行存储过程
     * @return
     * @throws SQLException 
     * @throws Exception 
     */
    public List executeProcedure(CXDzcxVO dzcxVo) throws Exception {
    	dzcxVo=checkProcedure(dzcxVo);
    	List procedureParam = dzcxVo.getProcedureParam();
    	ArrayList proParam = new ArrayList();
		if (!TycxUtils.isEmpty(procedureParam)) {
			String key = "";
			for (int i = 0; i < procedureParam.size(); i++) {
				key = (String) procedureParam.get(i);
				proParam.add(new StoredProcParamObj(1, key.trim(), StoredProcParamObj.IN,
						java.sql.Types.VARCHAR));
			}
		} else {
			proParam = null;
		}
    	ArrayList rList = null;
//		jdbcDao.setJdbcTemplateByJndiName(dzcxVo.getSjymc());
		DataSourceUtil dataSourceUtils = new DataSourceUtil(dzcxVo.getSjymc());
//		// 选择存储过程开始		
		String ccgcmc="{"+dzcxVo.getCcgcmc()+"}";
    	rList=dataSourceUtils.callStoreProcess(ccgcmc,proParam,false);
    	return rList;
    }
    /**
	 * 
	 * @name 中文名称
	 * @description 相关说明
	 * @time 创建时间:2015-5-11下午2:58:30
	 * @param cxdyVo
	 *            cxdyVo
	 * @param dzcxVo
	 *            dzcxVo
	 * @throws SwordBaseCheckedException
	 * @author 作者jinln
	 * @throws JSONException
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */

	public CXDzcxVO checkProcedure(CXDzcxVO dzcxVo){
		String proname = ""; // 传入的存储过程名称
		ArrayList<String> ccgccslist = new ArrayList<String>(); // 按顺序存放存储过程参数的值
		String ccgccs = "("; // 存储过程名参数部分
		String ccgcmc = dzcxVo.getCcgcmc();
		ccgcmc = ccgcmc.replace(" ", "").toUpperCase(); // 去除空格并转大写
		int start = ccgcmc.indexOf("(");
		int end = ccgcmc.indexOf(")");
		final String tj = dzcxVo.getQueryParams(); // 前台传入条件和值
		String ccgcmc1 = ""; // 执行存储过程时传入的存储过程名称
		if (start >= 0) { // 根据"("判断是否存在参数
			ccgcmc1 = ccgcmc.substring(0, start);
		} else {
			ccgcmc1 = ccgcmc;
		}
		if (start >= 0 && end >= 0 && start != (end - 1) && tj != null
				&& !tj.equals("")) {
			String[] strArray;
			if (ccgcmc.substring(start, end - 1).indexOf(",") >= 0) {
				strArray = ccgcmc.substring(start + 1, end).split(",");
			} else {
				strArray = new String[1];
				strArray[0] = ccgcmc.substring(start + 1, end);
			}
			Map<String, Object> tjMap = new HashMap<String, Object>();
			for (int i = 0; i < strArray.length; i++) {
				int csz = 0;
				String strccgccs = strArray[i];
				if (strccgccs.indexOf("@") >= 0) {// 判断存储过程是否存在参数
					start = strccgccs.indexOf("@");
					end = strccgccs.indexOf("@", start + 1);
					strccgccs = strccgccs.substring(start + 1, end);
					List<Map> tjJSONArr = JsonUtil.toListMap(tj);
					for (int j = 0; j < tjJSONArr.size(); j++) {
						tjMap =tjJSONArr.get(i);
						final String nameStr = (String) tjMap.get("name");
						if (strccgccs.equals(nameStr)) {
							ccgccslist.add((String) tjMap.get("value"));
							csz = 1;
						}
					}

				} else {
					ccgccslist.add(strccgccs);
					csz = 1;
				}
				ccgccs += "?,";
				if (csz == 0) {
					ccgccslist.add("");
				}
			}
			ccgccs += ")";
			if (ccgccs.indexOf(",)") >= 0) {
				ccgccs = ccgccs.replace(",)", ")");
			}
			proname = "call " + ccgcmc1 + ccgccs;
		} else {
			proname = "call " + ccgcmc1 + "()";
			ccgccslist = null;
		}
		dzcxVo.setCcgcmc(proname);
		dzcxVo.setProcedureParam(ccgccslist);
		return dzcxVo;
	}
    public List<Map<String, Object>> executeCount(CXDzcxVO dzcxVo)
			throws Exception {
		final String summaryParams = dzcxVo.getSummaryParams();
		List<Map<String, Object>> hjList = null;
		String sql = dzcxVo.getSql();
		if (!TycxUtils.isEmpty(summaryParams)) {
			final List<Map> summaryJsonArr = JsonUtil.toListMap(summaryParams);
			String footstr = "";
			String hjfootstr="";

			for (int i = 0; i < summaryJsonArr.size(); i++) {
				final Map<String, Object> map = summaryJsonArr.get(i);
				final String name = (String) map.get("name");
				final String summaryType = (String) map.get("summaryType");
				String str = "";
				if (Constant.HJ_SUM.equals(summaryType)) {
					str = "SUM(" + name + ")";
					footstr = "to_char(" + str + ") " + name + ", " + footstr;
				} else if (Constant.HJ_AVG.equals(summaryType)) {
					str = "  ROUND(AVG(" + name + "),2)";
					footstr = "to_char(" + str + ") " + name + ", " + footstr;
				}else if("合计".equals(summaryType)){
					hjfootstr=  " '合计' AS "+name+",";
				}
				
			}
			sql = "select " +hjfootstr+ footstr + " to_char(count(1))  TOTALCOUNT from (" + sql + ")";
		} else {
			sql = "select " + " to_char(count(1))  TOTALCOUNT from (" + sql + ")";
		}
		dzcxVo.setHjSql(sql);


//		jdbcDao.setJdbcTemplateByJndiName(dzcxVo.getSjymc());
		DataSourceUtil dataSourceUtils = new DataSourceUtil(dzcxVo.getSjymc());
		hjList = dataSourceUtils.queryforlist(sql);
		return hjList;
	}
    /**
     * 执行SQL
     * @param dzcxVo
     * @return
     * @throws Exception
     */
    
    public List executeSql(CXDzcxVO dzcxVo)
			throws Exception {
    	
		int page = dzcxVo.getPage();
		int limit = dzcxVo.getLimit();
		page=(page-1)*limit;		
		String sjymc = dzcxVo.getSjymc();

		DataSourceUtil dataSourceUtils = new DataSourceUtil(sjymc);
//		jdbcDao.setJdbcTemplateByJndiName(sjymc);
		List list=null;
		 //设置分页
        PageHelper.startPage(dzcxVo.getPage(), limit);
		if(page==0&&limit==0){
			list=dataSourceUtils.queryforlist(dzcxVo.getSql());
		}else{
			 list = dataSourceUtils.queryPage(dzcxVo.getSql(),page,limit);
		}
		dataSourceUtils.commit();
		return list;
    }
    /**
     * 执行SQL
     * @param dzcxVo
     * @return
     * @throws Exception
     */
    
    public List executeSql(String sql,String sjymc)
			throws Exception {
		List list;
		if(!TycxUtils.isEmpty(sjymc)){
//			jdbcDao.setJdbcTemplateByJndiName(sjymc);
			DataSourceUtil dataSourceUtils = new DataSourceUtil(sjymc);
			list=dataSourceUtils.queryforlist(sql);
			dataSourceUtils.commit();
		}else{
			list=jdbcDao.queryforlist(sql);
			jdbcDao.commit();
		}
		return list;
    }
    
	public List<Tycx002WtfkPojo> searchWtfk(Tycx002WtfkPojo tycx002WtfkPojo) {
		return tycx002DzcxMapper.searchWtfk(tycx002WtfkPojo);
	}
	
	public List<Tycx002FjPojo> searchFj(Tycx002FjPojo tycx002FjPojo) {
		return tycx002DzcxMapper.searchFj(tycx002FjPojo);
	}
	
	public void insertWtfk(Tycx002WtfkPojo tycx002WtfkPojo) {
		tycx002DzcxMapper.insertWtfk(tycx002WtfkPojo); 
	}
	
	public void updateWtfk(Tycx002WtfkPojo tycx002WtfkPojo) {
		tycx002DzcxMapper.updateWtfk(tycx002WtfkPojo);
	}
	
	public void insertFj(Tycx002FjPojo tycx002FjPojo) {
		tycx002DzcxMapper.insertFj(tycx002FjPojo);
	}
	
	public void insertTS(Tycx002TuisongPojo tycx002TuisongPojo) {
		tycx002DzcxMapper.insertTS(tycx002TuisongPojo);
	}
	
	public List<Tycx002TuisongPojo> queryTS(Tycx002TuisongPojo tycx002TuisongPojo) {
		return tycx002DzcxMapper.queryTS(tycx002TuisongPojo);
	}
	
	public void insertWtimg(Tycx002TuisongPojo tycx002TuisongPojo) {
		tycx002DzcxMapper.insertWtimg(tycx002TuisongPojo);
	}

	
	
}
