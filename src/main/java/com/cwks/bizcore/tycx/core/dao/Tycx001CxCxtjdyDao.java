package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx001CxCxtjdyMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxtjdyPojo;
import com.cwks.bizcore.tycx.core.utils.Constant;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.tycx.core.utils.tycxUtil;
import com.cwks.common.core.cache.CacheUtil;
import com.cwks.common.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Tycx001CxCxtjdyDao {
	@Autowired
	JdbcDao jdbcDao;
    @Autowired
    private Tycx001CxCxtjdyMapper tycx001CxCxtjdyMapper;

    public List select(Tycx001CxCxtjdyPojo pojo){
     
    	String sql = 
    			" select  dmsql, \n" +
				"        fzzdbz, \n" + 
				"        jgcj,   \n" + 
				"        jglx,   \n" + 
				"        jssjzd, \n" + 
				"        jylx,   \n" + 
				"        jys,    \n" + 
				"        jyzh,   \n" + 
				"        llx,    \n" + 
				"        lmc,    \n" + 
				"        to_char(lrrq,'yyyy-mm-dd hh24:mi:ss') lrrq, \n" + 
				"        lrr_dm, \n" + 
				"        mbbz,   \n" + 
				"        mrz,    \n" + 
				"        mrzxsbz,\n" + 
				"        sjgsdq, \n" + 
				"        sjtjl,  \n" + 
				"        sm,     \n" + 
				"        sqlxh,  \n" + 
				"        swjgtreescgz,   \n" + 
				"        tjmc,  		 \n" + 
				"        tjxylx, 		 \n" + 
				"        uuid,  		 \n" + 
				"        to_char(xgrq,'yyyy-mm-dd hh24:mi:ss') xgrq, \n" + 
				"        xgr_dm, \n" + 
				"        xh,     \n" + 
				"        xsgs,   \n" + 
				"        xsxh,   \n" + 
				"        zdycs,  \n" + 
				"        zdykd,  \n" + 
				"        znxz\n" + 
				"     FROM  CX_CXTJDY t   where 1=1  and  t.sqlxh = ?  order by t.xsxh ";

    	String sqlxh = pojo.getSqlxh();
    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(sqlxh);
    	
    	return jdbcDao.queryforlist(sql, param, Tycx001CxCxtjdyPojo.class);
     
    };

    public Tycx001CxCxtjdyPojo selectByPKey(Tycx001CxCxtjdyPojo pojo){
    	
    	String sql =
    			" select dmsql,\n" +
				"       fzzdbz,\n" + 
				"       jgcj,\n" + 
				"       jglx,\n" + 
				"       jssjzd,\n" + 
				"       jylx,\n" + 
				"       jys,\n" + 
				"       jyzh,\n" + 
				"       llx,\n" + 
				"       lmc,\n" + 
				"       to_char(lrrq, 'yyyy-mm-dd hh24:mi:ss') lrrq,\n" + 
				"       lrr_dm,\n" + 
				"       mbbz,\n" + 
				"       mrz,\n" + 
				"       mrzxsbz,\n" + 
				"       sjgsdq,\n" + 
				"       sjtjl,\n" + 
				"       sm,\n" + 
				"       sqlxh,\n" + 
				"       swjgtreescgz,\n" + 
				"       tjmc,\n" + 
				"       tjxylx,\n" + 
				"       uuid,\n" + 
				"       to_char(xgrq, 'yyyy-mm-dd hh24:mi:ss') xgrq,\n" + 
				"       xgr_dm, \n" + 
				"       xh,     \n" + 
				"       xsgs,   \n" + 
				"       xsxh,   \n" + 
				"       zdycs,  \n" + 
				"       zdykd,  \n" + 
				"       znxz    \n" + 
				"  from CX_CXTJDY \n" + 
				" where 1 = 1 and uuid = ? ";
    	 
    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(pojo.getUuid());
    	
    	return (Tycx001CxCxtjdyPojo) jdbcDao.queryForObject(sql, param, Tycx001CxCxtjdyPojo.class);
    	
       // return tycx001CxCxtjdyMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx001CxCxtjdyPojo pojo){
        tycx001CxCxtjdyMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx001CxCxtjdyPojo pojo){
        tycx001CxCxtjdyMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx001CxCxtjdyPojo pojo){
        tycx001CxCxtjdyMapper.insert(pojo);
    };

    public void insertSelective(Tycx001CxCxtjdyPojo pojo){
        tycx001CxCxtjdyMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx001CxCxtjdyPojo pojo){
        tycx001CxCxtjdyMapper.deleteByPKey(pojo);
    };
    /**
	 * 
	 * @name 处理查询条件，mrz等
	 * @description 相关说明
	 * @time 创建时间:2015-5-25上午10:30:40
	 * @param cxtjList
	 *            cxtjList
	 * @return map map
	 * @throws SwordBaseCheckedException
	 * @author 作者
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public List<Tycx001CxCxtjdyPojo> handleCxtj(
			List<Tycx001CxCxtjdyPojo> cxtjList, Map<String, String> map)
			throws Exception {
		final List<Tycx001CxCxtjdyPojo> realCxtjList = new ArrayList<Tycx001CxCxtjdyPojo>();
		// 循环条件
		for (int i = 0; i < cxtjList.size(); i++) {
			final Tycx001CxCxtjdyPojo cxtjMap = cxtjList.get(i);
			// 取默认值
			final String mrz = (String) cxtjMap.getMrz();
			if (!TycxUtils.isEmpty(mrz)) {
				// 如果默认值中包含@，取参数表，BZ中如果包含SQL，是要执行的结果；包含SESSION，是取session的值
				if (mrz.substring(0, 1).equals("@")) {
					final String realMrz = this.getRealValue(Constant.CSFL_MRZ,
							mrz, map).get("realMrz");
					cxtjMap.setMrz(realMrz);
				}

			}

			realCxtjList.add(cxtjMap);
		}
		return realCxtjList;
	}
	/**
	 * 
	 * @name 转换真实值
	 * @description 相关说明
	 * @time 创建时间:2015-5-25下午3:53:14
	 * @param csfl
	 *            (参数表中的CSFL)
	 * @param csz
	 *            (参数表中的CSZ)
	 * @param map
	 *            (map中包含orgid，userid，gwxh，gwssswjg，zndm，gndm)
	 * @return
	 * @throws SwordBaseCheckedException
	 * @author 作者
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	public Map<String, String> getRealValue(String csfl, String csz,
			Map<String, String> map) throws Exception {
		final Map<String, String> resultMap = new HashMap<String, String>();
		String realMrz = "";
		tycxUtil tycxUtil = new tycxUtil();
		final List<Map> mrzList = jdbcDao.queryforlist("select * from CX_CS_CSB where 1=1 and  csfl like  '"+csfl +"%' and csz='"+csz+"'");
//		final List<Map> mrzList = tycxUtil.getCodeTable("CX_CS_CSB", "csfl like '"+csfl+"%' and csz='"+csz+"'",sjy);
		final String bz = (String) mrzList.get(0).get("bz");
		// 判断从数据库取值还是session中取值
		final String[] bzArr = bz.split(":");
		
		if (bzArr.length == 2) {
			
			if(bzArr[0].equals(Constant.MRZ_SFSQL)){// 带 税务人员身份代码 查询条件的sql
				
				final String sql = bzArr[1];				
				String swrysf_dm = map.get("swrysf_dm");
				ArrayList<Object> param = new ArrayList<Object>();
				param.add(swrysf_dm);
				final List<Map<String, Object>> result = jdbcDao.queryforlist(sql,param);
				realMrz = result.get(0).get("CSZ").toString();
				
			}else if (bzArr[0].equals(Constant.MRZ_SQL)) {
				
				final String sql = bzArr[1];				
				final List<Map<String, Object>> result = jdbcDao.queryforlist(sql);
				realMrz = result.get(0).get("CSZ").toString();
				
			} else if (bzArr[0].equals(Constant.MRZ_SESSION)) {
				if (bzArr[1].equals(Constant.MRZ_SESSION_ORGID)) {
					realMrz =""; //SwordSessionUtils.getOrgID();
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_SWRYDM)) {
					realMrz =map.get(Constant.MRZ_SESSION_SWRYDM); 
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_GWXH)) {
					realMrz = map.get(Constant.MRZ_SESSION_GWXH);
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_SWRYSFJG)) {// 岗位序号
					realMrz = map.get(Constant.MRZ_SESSION_SWRYSFJG);
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_ZNDM)) {// 职能代码
					realMrz = map.get(Constant.MRZ_SESSION_ZNDM);
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_GNDM)) {// 功能代码
					realMrz = map.get(Constant.MRZ_SESSION_GNDM);
				} else if (bzArr[1].equals(Constant.MRZ_SESSION_ZNFW)) {// 职能范围
					realMrz = map.get(Constant.MRZ_SESSION_ZNFW);
				} else {
					realMrz = map.get(bzArr[1]);// 暂不支持其它SESSION取值
				}
			}
			resultMap.put("bzArr", bzArr[1]);
		} else {
			realMrz = (String) mrzList.get(0).get("csz");
		}
		resultMap.put("realMrz", realMrz);
		return resultMap;
	}
}
