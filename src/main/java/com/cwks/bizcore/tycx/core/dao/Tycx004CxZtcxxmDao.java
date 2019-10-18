package com.cwks.bizcore.tycx.core.dao;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx004CxZtcxxmMapper;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx004RYhsJcxxMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx004CxZtcxxmPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.dao.JdbcDao;
import com.cwks.common.util.db.oracl.StoredProcManager;
import com.cwks.common.util.db.oracl.StoredProcParamObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Tycx004CxZtcxxmDao {
	@Autowired
	JdbcDao jdbcDao;
    @Autowired
    private Tycx004CxZtcxxmMapper tycx004CxZtcxxmMapper;
    @Autowired
    private Tycx004RYhsJcxxMapper tycx004RYhsJcxxMapper;

    public List select(Tycx004CxZtcxxmPojo pojo){
        return tycx004CxZtcxxmMapper.select(pojo);
    };
    public List selectCxxmBySjdm(Tycx004CxZtcxxmPojo pojo){
        return tycx004CxZtcxxmMapper.selectCxxmBySjdm(pojo);
    };

    public Tycx004CxZtcxxmPojo selectByPKey(Tycx004CxZtcxxmPojo pojo){
        return tycx004CxZtcxxmMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx004CxZtcxxmPojo pojo){
        tycx004CxZtcxxmMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx004CxZtcxxmPojo pojo){
        tycx004CxZtcxxmMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx004CxZtcxxmPojo pojo){
        tycx004CxZtcxxmMapper.insert(pojo);
    };

    public void insertSelective(Tycx004CxZtcxxmPojo pojo){
        tycx004CxZtcxxmMapper.insertSelective(pojo);
    };

    public void deleteByPKey(Tycx004CxZtcxxmPojo pojo){
        tycx004CxZtcxxmMapper.deleteByPKey(pojo);
    };
    /**
     * 查询基本信息
     * @param map
     * @return
     * @throws SQLException
     */
    public List queryJbxx(Map<String,Object> map) throws Exception {
		// 获取参数
		String nsrsbh = (String) map.get("nsrsbh");
		String djxh = (String) map.get("djxh");
		String AS_SUCCESS = "";
		String AS_MESSAGE = "";
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ArrayList rList = null;
		ArrayList sqlParams = new ArrayList();
		// 传入参数
		sqlParams.add(new StoredProcParamObj(1, djxh, StoredProcParamObj.IN,
				java.sql.Types.VARCHAR));

		// 传出参数
		sqlParams.add(new StoredProcParamObj(2, AS_SUCCESS,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(3, AS_MESSAGE,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		DataSourceUtil dataSourceUtils = new DataSourceUtil("hcq105");
//		jdbcDao.setJdbcTemplateByJndiName("hcq105");
		// 选择存储过程开始		
		rList=dataSourceUtils.callStoreProcess("{call P_YHS_JCXX(?,?,?)}",sqlParams,false);
		String isSuccess = rList.get(1).toString();		
		List list=new ArrayList();
		if ("0".equals(isSuccess)){
			String sql="SELECT * FROM R_YHS_JCXX";
			list = dataSourceUtils.queryforlist(sql);
			dataSourceUtils.commit();
		}
		return list;
    }
    /**
     * 查询标签画像
     * @param map
     * @return
     * @throws SQLException
     */
    public Map<String,Object> queryBqhx(String djxh) throws Exception {
    	Map<String,Object> resultMap=new HashMap<String,Object>();
    	Connection conn = null;
    	ArrayList rList = null;
		ArrayList sqlParams = new ArrayList();
		//---------分税种情况-----------
		// 传入参数
		String fszqkStr="";
		sqlParams.add(new StoredProcParamObj(1, djxh, StoredProcParamObj.IN,
				java.sql.Types.VARCHAR));

		// 传出参数
		sqlParams.add(new StoredProcParamObj(2, fszqkStr,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		DataSourceUtil dataSourceUtils = new DataSourceUtil("hcq105");
//		jdbcDao.setJdbcTemplateByJndiName("hcq105");
		conn = dataSourceUtils.getDataSource().getConnection();
		// 选择存储过程开始		
		rList = (ArrayList) StoredProcManager.callStoreProcess(conn,
				"{p_yhs_fszjnqk(?,?)}", sqlParams);
		String fszqk_str="";
		if(!TycxUtils.isEmpty(rList.get(0))){
		 fszqk_str=rList.get(0).toString();
		}
		resultMap.put("fszqk", fszqk_str);
		//--------月缴纳情况-------------
		sqlParams.clear();
		String xData="";
		String yData="";
		String yData_shui="";
		String yData_sb="";
		String Data="";
		sqlParams.add(new StoredProcParamObj(1, djxh, StoredProcParamObj.IN,
				java.sql.Types.VARCHAR));
		// 传出参数
		sqlParams.add(new StoredProcParamObj(2, xData,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(3, yData,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(4, yData_shui,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(5, yData_sb,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(6, Data,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		rList = (ArrayList) StoredProcManager.callStoreProcess(conn,
				"{p_yhs_yjnqk(?,?,?,?,?,?)}", sqlParams);
		String yjnqk_xData="";
		String yjnqk_yData="";
		String yjnqk_yData_shui="";
		String yjnqk_yData_shebao="";
		if(!TycxUtils.isEmpty(rList.get(0))){
			yjnqk_xData=rList.get(0).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(1))) {
			yjnqk_yData=rList.get(1).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(2))) {
			yjnqk_yData_shui=rList.get(2).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(3))) {
			yjnqk_yData_shebao=rList.get(3).toString();
		}
		resultMap.put("yjnqk_xData", yjnqk_xData);
		resultMap.put("yjnqk_yData", yjnqk_yData);
		resultMap.put("yjnqk_yData_shui", yjnqk_yData_shui);
		resultMap.put("yjnqk_yData_shebao", yjnqk_yData_shebao);
		//----------申报时段分布----------
		sqlParams.clear();
		sqlParams.add(new StoredProcParamObj(1, djxh, StoredProcParamObj.IN,
				java.sql.Types.VARCHAR));
		// 传出参数
		sqlParams.add(new StoredProcParamObj(2, xData,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(3, yData,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(4, yData_shui,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(5, yData_sb,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		sqlParams.add(new StoredProcParamObj(6, Data,
				StoredProcParamObj.OUT, java.sql.Types.VARCHAR));
		rList = (ArrayList) StoredProcManager.callStoreProcess(conn,
				"{p_yhs_sbsdfb(?,?,?,?,?,?)}", sqlParams);
		String sbsdfb_xData = "";
		String yData_qcsb = "";
		String yData_qmsb = "";
		String yData_yqsb = "";
		String yData_yqwsb = "";
		if (!TycxUtils.isEmpty(rList.get(0))) {
			sbsdfb_xData = rList.get(0).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(1))) {
			yData_qcsb = rList.get(1).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(2))) {
			yData_qmsb = rList.get(2).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(3))) {
			yData_yqsb = rList.get(3).toString();
		}
		if (!TycxUtils.isEmpty(rList.get(4))) {
			yData_yqwsb = rList.get(4).toString();
		} 
		resultMap.put("sbsdfb_xData", sbsdfb_xData);
		resultMap.put("yData_qcsb", yData_qcsb);
		resultMap.put("yData_qmsb", yData_qmsb);
		resultMap.put("yData_yqsb", yData_yqsb);
		resultMap.put("yData_yqwsb", yData_yqwsb);
    	return resultMap;
    }
}
