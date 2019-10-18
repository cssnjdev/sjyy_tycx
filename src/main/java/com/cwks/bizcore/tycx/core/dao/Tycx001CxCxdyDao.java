package com.cwks.bizcore.tycx.core.dao;

import com.cwks.bizcore.comm.utils.UUIDGenerator;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx001CxCxdyMapper;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx001CxCxjgdyMapper;
import com.cwks.bizcore.tycx.core.mapping.mapper.Tycx001CxCxtjdyMapper;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxdyPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxjgdyPojo;
import com.cwks.bizcore.tycx.core.mapping.pojo.Tycx001CxCxtjdyPojo;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import com.cwks.bizcore.utils.DataSourceUtil;
import com.cwks.common.dao.JdbcDao;
import com.raqsoft.report.model.ReportDefine;
import com.raqsoft.report.usermodel.ParamMetaData;
import com.raqsoft.report.util.ReportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@SuppressWarnings("all")
public class Tycx001CxCxdyDao {
	@Autowired
	JdbcDao jdbcDao;
    @Autowired
    private Tycx001CxCxdyMapper tycx001CxCxdyMapper;
    @Autowired
    private Tycx001CxCxjgdyMapper tycx001CxCxjgdyMapper;
    @Autowired
    private Tycx001CxCxtjdyMapper Tycx001CxCxtjdyMapper;
    public List selectCxdy(Tycx001CxCxdyPojo pojo){
        return tycx001CxCxdyMapper.selectCxdy(pojo);
    };

    public Tycx001CxCxdyPojo selectByPKey(Tycx001CxCxdyPojo pojo){
    	
    	String sql =  " select \n"+
    		      " t.*,t1.ds_sid sjymc from\n"+
    		       " CX_CXDY t,sys_datasource t1\n"+
    		       " where t.sjylx=t1.datasource_id(+)\n"+
    		      " and sqlxh = ? \n";
    	ArrayList<Object> param = new ArrayList<Object>();
    	param.add(pojo.getSqlxh());
    	
    	return (Tycx001CxCxdyPojo) jdbcDao.queryForObject(sql, param, Tycx001CxCxdyPojo.class);
    	
       // return tycx001CxCxdyMapper.selectByPKey(pojo);
    };

    public void updateByPKey(Tycx001CxCxdyPojo pojo){
        tycx001CxCxdyMapper.updateByPKey(pojo);
    };

    public void updateByPKeySelective(Tycx001CxCxdyPojo pojo){
        tycx001CxCxdyMapper.updateByPKeySelective(pojo);
    };

    public void insert(Tycx001CxCxdyPojo pojo){
        tycx001CxCxdyMapper.insert(pojo);
    };

    public void insertSelective(Tycx001CxCxdyPojo pojo){
        tycx001CxCxdyMapper.insertSelective(pojo);
    };
    
    public String getSjymc(String sjylx){
    	return tycx001CxCxdyMapper.getSjymc(sjylx);
    };


    public void deleteByPKey(Tycx001CxCxdyPojo pojo){
        tycx001CxCxdyMapper.deleteByPKey(pojo);
    };
    
    public Map<String,Object> queryJbxx(String uuid,String sql){		
		ArrayList param =new ArrayList();		
		param.add(uuid);		
		Map<String,Object> data=jdbcDao.queryformap(sql,param);
		return data;
		
	}
    
    public Map<String,Object> querySQL(String sql){		
		ArrayList param =new ArrayList();				
		Map<String,Object> data=jdbcDao.queryformap(sql,param);
		return data;
		
	}
    
    public Map queryCxdy(String sqlxh){
    	ArrayList param = new ArrayList<String>();
    	param.add(sqlxh);
    	return this.queryMap("SQL_CX_QUERYCXDY", param);
   }
    
  private Map queryMap(String sqlid,ArrayList param){
    	
    	String sql = jdbcDao.getSql(sqlid);
    	
    	Map map = null;
    	
    	if(param == null){
    		map = jdbcDao.queryformap(sql);
    	}else{
    		map = jdbcDao.queryformap(sql,param);
    	}
    	
		return map;
    }
    public List initResultColumns(String sqlxh,String sql,String sjymc) throws Exception {
    	//this.setJdbcTemplateByJndiName(sjymc);
    	//先通过sqlxh查询结果列
    	Tycx001CxCxjgdyPojo cxjgPojo=new Tycx001CxCxjgdyPojo();
    	cxjgPojo.setSqlxh(sqlxh);
    	List cxjgList=tycx001CxCxjgdyMapper.select(cxjgPojo);
//		jdbcDao.setJdbcTemplateByJndiName(sjymc);
		DataSourceUtil dataSourceUtils = new DataSourceUtil(sjymc);
    	SqlRowSet rowSet=dataSourceUtils.queryforRowset(sql);
    	SqlRowSetMetaData metaData=rowSet.getMetaData();
    	int columnCount=metaData.getColumnCount();
    	List<Tycx001CxCxjgdyPojo> list=new ArrayList();
    	for(int i=1;i<=columnCount;i++){
    		String lmc=(String)metaData.getColumnName(i);
    		String xh=String.valueOf(i);
    		 //查找当前列是否保存过
            boolean isContain = false;
    		for(int j=0;j<cxjgList.size();j++){
    			Tycx001CxCxjgdyPojo cxjgpojo=(Tycx001CxCxjgdyPojo) cxjgList.get(j);
    			if(lmc.equals(cxjgpojo.getLmc())){
    				String llx=metaData.getColumnTypeName(i);
    				if(llx.equals("CHAR")){
    					llx="VARCHAR2";
    				}
    				cxjgpojo.setLlx(llx);
    				cxjgpojo.setXh(Double.parseDouble(xh));
    				cxjgpojo.setXsxh(Double.parseDouble(xh));
    				list.add(cxjgpojo);
    				isContain=true;
    				
    				break;
    			}
    		}
    		
    		if(!isContain){
    			String llx=metaData.getColumnTypeName(i);
				if(llx.equals("CHAR")){
					llx="VARCHAR2";
				}
    			Tycx001CxCxjgdyPojo cxjgpojo=new Tycx001CxCxjgdyPojo();
    			cxjgpojo.setUuid(UUIDGenerator.getUUID());
    			cxjgpojo.setLmc(lmc);
    			cxjgpojo.setLlx(llx);
    			cxjgpojo.setXh(Double.parseDouble(xh));
    			cxjgpojo.setLkd("100");
    			cxjgpojo.setLms(lmc);
    			cxjgpojo.setTjlx("0");
    			cxjgpojo.setXsxh(Double.parseDouble(xh));
    			cxjgpojo.setYcbj("1");
    			cxjgpojo.setSdbj("0");
    			cxjgpojo.setLrr_dm("cssnj");
    			//cxjgpojo.setLrrq("2017-11-29");
    			cxjgpojo.setSjgsdq("0000");
    			cxjgpojo.setSqlxh(sqlxh);
    			cxjgpojo.setDqfs("0");
    			list.add(cxjgpojo);
    		} 		
    	}
    	
    	//先删除已有结果列
		tycx001CxCxjgdyMapper.deleteByPKey(cxjgPojo);
		//再插入新的查询结果列
		for(int i=0;i<list.size();i++){
			Tycx001CxCxjgdyPojo pojo=list.get(i);
			tycx001CxCxjgdyMapper.insertSelective(pojo);
		}
		
    	return  list;
    	
    }
    public List initCxtj(String sqlxh,String sql,String sjymc,String procname, Boolean isBB) throws Exception{	
    	//查询已有查询条件
    	Tycx001CxCxtjdyPojo cxtjPojo=new Tycx001CxCxtjdyPojo();
    	cxtjPojo.setSqlxh(sqlxh);
    	List cxtjList=Tycx001CxCxtjdyMapper.select(cxtjPojo);
    	List<Tycx001CxCxtjdyPojo> list=new ArrayList();
    	if (!TycxUtils.isEmpty(isBB) && isBB) {// 报表
			// 获取报表文件绝对路径
			String reportFileAbsolutePath = "D:/wordspace/.";
			if(TycxUtils.isEmpty(reportFileAbsolutePath)){
				reportFileAbsolutePath="/reportFiles";
			}

			final String reportFile = reportFileAbsolutePath + "/" + sql;
				ReportDefine rd = (ReportDefine) ReportUtils.read(reportFile);
				ParamMetaData pmd = rd.getParamMetaData(); // 从报表定义中取得参数元对象
				
				// ParamMetaData
				String paramOrMocrName = "";
				if (pmd != null) {
					for (int i = 0, count = pmd.getParamCount(); i < count; i++) { // 讲究优化的写法						
						paramOrMocrName = pmd.getParam(i).getParamName(); // 获取参数名
						// 判断是否已保存过
						boolean isContain = false;
						if (!TycxUtils.isEmpty(cxtjList)) {
							for (int j=0;j<cxtjList.size();j++) {
								Tycx001CxCxtjdyPojo cxtjpojo=(Tycx001CxCxtjdyPojo) cxtjList.get(j);
								if (cxtjpojo.getLmc().equalsIgnoreCase(
										paramOrMocrName)) {
									cxtjpojo.setLmc(paramOrMocrName);
									list.add(cxtjpojo);
									isContain = true;
									break;
								}
							}
						}
						if (!isContain) {
							final Tycx001CxCxtjdyPojo cxtjdyTempInfo = new Tycx001CxCxtjdyPojo();
							cxtjdyTempInfo.setUuid(UUIDGenerator.getUUID());
							cxtjdyTempInfo.setLmc(paramOrMocrName);
							cxtjdyTempInfo.setSqlxh(sqlxh);
							cxtjdyTempInfo.setTjmc(paramOrMocrName);
							cxtjdyTempInfo.setLrr_dm("cssnj");
							cxtjdyTempInfo.setLrrq("2017-11-29");
							cxtjdyTempInfo.setSjgsdq("0000");
							cxtjdyTempInfo.setLlx("VARCHAR2");
							cxtjdyTempInfo.setTjxylx("0");
							list.add(cxtjdyTempInfo);
						}
					}
				}
		
		}else {
			if (!TycxUtils.isEmpty(sql)) {
				StringBuffer sqlBuffer = new StringBuffer(sql);
				String tjxxAllStr = "";
				int m=0;
				
				while (sqlBuffer.indexOf("[") > 0) {
					m++;
					String xh=String.valueOf(m);
					final int start1 = sqlBuffer.indexOf("[");
					final int end1 = sqlBuffer.indexOf("]") + 1;

					final String cxtjSql = sqlBuffer.substring(start1, end1);
					final int start2 = cxtjSql.indexOf("@");
					int end2 = cxtjSql.indexOf("@", start2 + 1);
					if (start2 == end2) {
						end2 = cxtjSql.lastIndexOf("]");
					}
					final String key = cxtjSql.substring(start2 + 1, end2);
					// 判断是否重复，上面已定义，则去掉重复
					if (tjxxAllStr.indexOf("@" + key + "@") < 0) {
						tjxxAllStr = tjxxAllStr + "@" + key + "@";
						// 判断是否已保存过
						boolean isContain = false;
						if (!TycxUtils.isEmpty(cxtjList)) {
							for (int j=0;j<cxtjList.size();j++) {
								Tycx001CxCxtjdyPojo cxtjpojo=(Tycx001CxCxtjdyPojo) cxtjList.get(j);
								if (cxtjpojo.getLmc().equalsIgnoreCase(key)) {
									cxtjpojo.setLmc(key.toUpperCase());
									cxtjpojo.setTjmc(key.toUpperCase());
									cxtjpojo.setXh(Double.parseDouble(xh));
									cxtjpojo.setXsxh(Double.parseDouble(xh));
									list.add(cxtjpojo);
									isContain = true;
									break;
								}
							}
						}

						if (!isContain) {
							final Tycx001CxCxtjdyPojo cxtjdyTempInfo = new Tycx001CxCxtjdyPojo();
							cxtjdyTempInfo.setUuid(UUIDGenerator.getUUID());
							cxtjdyTempInfo.setLmc(key.toUpperCase());
							cxtjdyTempInfo.setTjmc(key.toUpperCase());
							cxtjdyTempInfo.setSqlxh(sqlxh);
							cxtjdyTempInfo.setLrr_dm("cssnj");
							cxtjdyTempInfo.setLrrq("2017-11-29");
							cxtjdyTempInfo.setSjgsdq("0000");
							cxtjdyTempInfo.setLlx("VARCHAR2");
							cxtjdyTempInfo.setTjxylx("0");
							cxtjdyTempInfo.setXh(Double.parseDouble(xh));
							cxtjdyTempInfo.setXsxh(Double.parseDouble(xh));
							list.add(cxtjdyTempInfo);
						}
					}
					sqlBuffer = sqlBuffer.replace(start1, end1, "(1=1)");
				}
			}

			// 处理存储过程涉及的条件
			if (!TycxUtils.isEmpty(procname)) {
				StringBuffer procNameBuffer = new StringBuffer(procname);
				while (procNameBuffer.indexOf("@") > 0) {
					final int start = procNameBuffer.indexOf("@");
					final int end = procNameBuffer.indexOf("@", start + 1);
					final String key = procNameBuffer.substring(start + 1, end);
					// 如果存储过程中的条件在sql中已经定义, 则不去处理
					boolean flag = true;
					if (!TycxUtils.isEmpty(cxtjList) && !TycxUtils.isEmpty(key)) {
						for (int j=0;j<cxtjList.size();j++) {
							Tycx001CxCxtjdyPojo cxtjpojo=(Tycx001CxCxtjdyPojo) cxtjList.get(j);
							if (key.equals(cxtjpojo.getLmc())) {
								flag = false;
								break;
							}
						}
					}
					if (flag) {
						final Tycx001CxCxtjdyPojo cxtjdyTempInfo = new Tycx001CxCxtjdyPojo();
						cxtjdyTempInfo.setLmc(key.toUpperCase());
						cxtjdyTempInfo.setTjmc(key.toUpperCase());
						cxtjdyTempInfo.setSqlxh(sqlxh);
						cxtjdyTempInfo.setLrr_dm("cssnj");
						cxtjdyTempInfo.setLrrq("2017-11-29");
						cxtjdyTempInfo.setSjgsdq("0000");
						cxtjdyTempInfo.setLlx("VARCHAR2");
						cxtjdyTempInfo.setTjxylx("0");
						list.add(cxtjdyTempInfo);
					}
					procNameBuffer = procNameBuffer
							.replace(start, end + 1, " ");
				}
			}
		}
    	//删除查询条件
    	Tycx001CxCxtjdyMapper.deleteByPKey(cxtjPojo);
    	//添加新的查询条件
    	for(int m=0;m<list.size();m++){
    	
    		Tycx001CxCxtjdyPojo cxtjpojo=list.get(m);
//    		cxtjpojo.setXh(Double.valueOf(xh));
//    		cxtjpojo.setXsxh(Double.valueOf(xh));
    		Tycx001CxCxtjdyMapper.insertSelective(cxtjpojo);
    	}
    	return list;
    }
}
