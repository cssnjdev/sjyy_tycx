package com.cwks.bizcore.persistence;

import com.cwks.bizcore.comm.utils.DateUtils;
import com.cwks.bizcore.persistence.arraytable.ArrayParam;
import com.cwks.bizcore.persistence.arraytable.ArrayTableParam;
import com.cwks.bizcore.persistence.arraytable.StructParam;
import com.cwks.bizcore.persistence.outtype.BaseInOutParam;
import com.cwks.bizcore.persistence.outtype.IOuterParam;
import com.cwks.bizcore.persistence.outtype.OracleCursor;
import com.cwks.bizcore.tycx.core.utils.TycxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jdbc.rowset.CachedRowSet;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


public class SpResult implements java.io.Serializable{
    
//	private final static LogWritter logger = LogFactory.getLogger(SpResult.class);
	private static Logger logger = LoggerFactory.getLogger(SpResult.class);
	private static final String DELIMA = "~|~";//分隔符
	private static final String RESULT = "result";
	
	private String pName = null;
	
	public SpResult(String name) {
		this.pName = name;
	}

	//二维数组作为Tuxedo的返回集，第一个数组为值
	public SpResult(String[][] wtcRes,Collection jdbcRes) {

		this.wtcRes=wtcRes;
		this.jdbcRes=jdbcRes;
	}

	private String model;
	
	public String getModel(){
		return model;
	}
	
	
		
	
	private String[][] wtcRes;
	private Collection jdbcRes;
	
	private Map jdbcMapresult;
	
	private Map wtcMapResult;
	
	
	
	public Map getWtcMapResult() {
		return wtcMapResult;
	}

	public void setWtcMapResult(Map wtcMapResult) {
		this.wtcMapResult = wtcMapResult;
	}

	public Map getJdbcMapresult() {
		return jdbcMapresult;
	}

	public void setJdbcMapresult(Map jdbcMapresult) {
		this.jdbcMapresult = jdbcMapresult;
	}



	public Collection getJdbcRes() {
		return jdbcRes;
	}
	
	

	public Map getMapRes() {
		
		if(jdbcRes!=null){
			Collection coll = this.getJdbcRes();
			int index = 0;//结果集的顺序以0开始，第一行名称为row1；第二行为row1，以此类推
			//TODO 对于JDBC调用来说 存储过程会返回并且只有一个结果集
			CachedRowSet crs = null;
			if(coll != null &&  coll.size()>0){
				crs = (CachedRowSet) (coll.iterator().next());//第一个结果集
				return rowsetToMap(crs);
			}
           return null; 
			
		}else if(wtcRes!=null){
		    //return wtcRes.; 构造成 
			//TODO 暂定：WTC将返回一个二维数组，二维数组的长度为返回结果集的数量，
			//每个结果集为数组，第一行到倒数第三行，为返回记录，记录之间用分隔符分开；倒数第二行为字段名，大写，以分隔符分开；
			//最后一行为字段类型，跟java.sql.Types定义的类型一致，都是整形的,以分隔符分开
			
			for (int j = 0; j < wtcRes.length; j++) {
				
			
				String[] rowStrs = wtcRes[j];
				
				if(rowStrs.length<2){
					logger.debug("第"+j+"结果集记录出错，不是符合的格式，格式为长度大于2的数组");
				}
				if(rowStrs.length==2){
					logger.debug("第"+j+"结果集记录为空!!!");
				}
				String[] colNames = rowStrs[rowStrs.length-2].split(DELIMA);
				String[] colTypes = rowStrs[rowStrs.length-1].split(DELIMA);
				
				if(hasNull(colNames) || hasNull(colTypes)){
					logger.error("类型或字段名包含空值!!!");
					break;
				}
				Map rowRecord = new HashMap();
				for(int k = 0; k < rowStrs.length-2; k++){
					String[] values = rowStrs[k].split(DELIMA);
					if(colNames.length != values.length || colTypes.length != values.length){
						//检查格式
						logger.error("值长度与类型、字段长度不匹配!");
						break ;
					}
					for (int i = 0; i < values.length; i++) {
						//Object cellValue = this.getValue(value[i],colTypes);
					}
				}
			}
			
			//TODO
			return null;
			
		}else{
			throw new RuntimeException("没有任何返回值!!!!");
		}
		
	}


	private boolean hasNull(String[] colNames) {
		// TODO Auto-generated method stub
		for (int i = 0; i < colNames.length; i++) {
			if(TycxUtils.isEmpty(colNames[i])){
				return true;
			}
		}
		return false;
	}

	//新的传回方法,该方法将返回参数类型里增加值，
	//普通类型增加索引号和Object类型的值，游标类型增加索引号和List<Map>构造的结果集
	public Map getNewMapRes(List params) {
		Map mapTable = new HashMap();
		if(jdbcMapresult!=null){
			Map allResult = this.jdbcMapresult;
			
			if(allResult != null &&  allResult.size()>0){
				
				Set keyset = allResult.keySet();
				for (Iterator iterator = keyset.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					Object value = allResult.get(key);
					if(value!=null && value instanceof CachedRowSet){
						
						mapTable.put(key, rowsetToList((CachedRowSet) value,params));
					}else{
						//构造返回值
						logger.debug("构造返回值");
						//logger.debug(value.toString());
						//logger.debug(value.getClass().getName());
						mapTable.put(key, value);
					}
				}
			}
			//将结果赋予out参数
			
			for (int i=0; i<params.size(); i++) {
				Object object = (Object) params.get(i);
				if(object instanceof IOuterParam){
					IOuterParam oparam = (IOuterParam) object;
					oparam.setIndex(i);
					oparam.setValue(mapTable.get(RESULT+(i+1)));//参数从1定义，list从0定义
					//添加CachedRowSet的结果集
					if(object instanceof OracleCursor){
						OracleCursor ocursor = (OracleCursor) object;
						CachedRowSet crs = (CachedRowSet) allResult.get(RESULT+(i+1));
						//将游标重置回起点
						try {
							crs.beforeFirst();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							logger.error("游标位置重置失败!!", e);
						}
						ocursor.setRowset(crs);
					}
				}
			}
			
			//end
			return mapTable;
			
		}/*else if(wtcMapResult != null){
			//Tuxedo 返回值处理
			int index = 0;
			Map result = new HashMap();
			//TODO 只游标返回
			if (TFDxLink.isInvalid(wtcMapResult)==null){//判断返回的Map对象是否为空
				return result;
			}else if (!TFDxLink.isInvalid(wtcMapResult)){//判断返回Map对象是否存在错误信息，如果有则进行错误处理
				throw new RuntimeException("("+TFDxLink.getErrorCode(wtcMapResult)+")"+TFDxLink.getErrorMesage(wtcMapResult));				
			}else{			
				for(int i=0;i<params.size();i++){
					Object param = params.get(i);
					if(param instanceof OracleCursor){
						OracleCursor oc = (OracleCursor) param;
						List valueData = TFDxLink.getMapListElement(wtcMapResult, index);
						if(valueData.size()>1000){
							logErrorMessage(params,valueData.size(),pName);
						}
						oc.setValue(valueData);						
						result.put(RESULT+(i+1), valueData);
						index++;
					}
				}
			}
			/*for(int i=0;i<params.size();i++){
				Object param = params.get(i);
				if(param instanceof OracleCursor){
					OracleCursor oc = (OracleCursor) param;
					oc.setValue(TFDxLink.getMapListElement(wtcMapResult, index));
					index++;
					result.put(RESULT+(i+1), TFDxLink.getMapListElement(wtcMapResult, index));
				}
			}**/
			
//			return result;
//			
//		}
	else{
			throw new RuntimeException("没有任何返回值!!!!");
		}
	}
    private void logErrorMessage(List params, int size, String name) {
    	logger.error("!!!!!!!!!!!!!!Following Request queried more than 1000 records。Size  ==> "+size);
//		SwordDataSet dataSet = ContextAPI.getReqDataSet();
//		if(dataSet!=null){
//			logger.error("*****request tid == " +	dataSet.getReqDataObject().getViewData().get("tid"));
//			logger.error("*****request ctrl == " +	dataSet.getReqDataObject().getViewData().get("ctrl"));
//		}
		logger.error("*****request procedure name == " + pName);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<params.size();i++){
			Object param = params.get(i);
			sb.append("参数"+i+" [").append(getParamString(param)).append("]\n");
		}
		logger.error("*****request procedure params is  ==\n " + sb);
		
	}
    
  //二维数组转换
    private String getStringValueForArray2(Object[][] datas) {
		if (datas == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer("{");
		for (int i = 0; i < datas.length; i++) {
			Object[] array = datas[i];
			sb.append("row"+i+": "+getStringValueForArray(array));
			if(i < datas.length -1){//不是最后一个
				sb.append("\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}
    
    private String getParamString(Object param) {
		if (param == null) {
			return null;
		}
		if(param instanceof Date){
			return DateUtil.dateToString((Date) param,1);
		}
		if(param instanceof Calendar){
			//return DateUtil.dateToString((Date) object,1);
			return DateUtil.toDateStr((Calendar) param,1);
		}
		
		if (param.getClass().getName().startsWith("[[L") ) {
            //设置二维数组参数
			Object[][] data = (Object[][]) param;
			return getStringValueForArray2(data);
		}
		
		if (param instanceof ArrayTableParam) {
            //设置二维数组参数
			ArrayTableParam atp = (ArrayTableParam) param;
			Object[][] data = (Object[][]) atp.getData();
			StringBuffer sb = new StringBuffer("自定义类型:"+atp.getTypeName()+":");
			sb.append(getStringValueForArray2(data)) ;
			return sb.toString();
		}
		
		if (param instanceof ArrayParam) {
            //设置二维数组参数
			ArrayParam atp = (ArrayParam) param;
			Object[] data = (Object[]) atp.getData();
			StringBuffer sb = new StringBuffer("自定义类型:"+atp.getTypeName()+":");
			sb.append(getStringValueForArray(data)) ;
			return sb.toString();
			//return ;
		}
		
		if (param instanceof StructParam) {
            //设置结构体参数
			StructParam atp = (StructParam) param;
			Object[] data = (Object[]) atp.getData();
			StringBuffer sb = new StringBuffer("自定义类型:"+atp.getTypeName()+":");
			sb.append(getStringValueForArray(data)) ;
			return sb.toString();
		}
		
		if (param.getClass().getName().startsWith("[L") ) {
            //设置一维数组参数
			Object[] data = (Object[]) param;
			return getStringValueForArray(data);
		}
		
		if (param instanceof BaseInOutParam) {
			BaseInOutParam bip = (BaseInOutParam) param;			
			StringBuffer sb = new StringBuffer();
            sb.append("输入输出型参数:");
            sb.append(getParamString(bip.getValue()));
            return sb.toString();
		}
		
		if (param instanceof IOuterParam) {
			//BaseInOutParam bip = (BaseInOutParam) param;			
			StringBuffer sb = new StringBuffer();
            sb.append("输出型参数:");
            sb.append(param.getClass().getName());
            return sb.toString();
		}
		
		
		return param.toString();
	}
    
  //一维转换
	private String getStringValueForArray(Object[] array) {
		if (array == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer("{");
		//sb.append("cellCount="+array.length+" [");
		for (int i = 0; i < array.length; i++) {
			if (i<array.length -1) {
				sb.append(getParamString(array[i])+",");
			}else{
				sb.append(getParamString(array[i]));
			}
		}
		sb.append("}");
		return sb.toString();
	}

	//List<Map>
	private List rowsetToList(CachedRowSet crs, List params) {
		
		if(crs.size()>1000){
			logErrorMessage(params,crs.size(),pName);			
		}
		List listMap = new ArrayList();
		try {
			crs.beforeFirst();//移到最初
			int count = crs.getMetaData().getColumnCount();
			ResultSetMetaData metaData = crs.getMetaData();
			String columnTypeName = null;
			String colummName = null;
			while (crs.next()) {
				Map row = new HashMap();
				listMap.add(row);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					columnTypeName = metaData.getColumnTypeName(i);
					colummName = metaData.getColumnName(i);
					// logger.debug("=colummName="+colummName+":"+columnTypeName
					// +"("+metaData.getPrecision(i)+","+metaData.getScale(i)+")");
					String key = colummName.toLowerCase();//TODO 要将字段与视图属性的映射规则规范
					//改成跟VO一样的格式，下划线后首字母大写
					key = getStandardKey(key);
					
                    Object rowData = crs.getObject(colummName);
                    if(rowData == null){
                    	row.put(key, null);
                    	continue;
                    }
//                    if("blob".equalsIgnoreCase(columnTypeName)){
//                    	SerialBlob blob = (SerialBlob) rowData;
//                    	row.put(key, blob.getBuf());
//                    }else  if ("clob".equalsIgnoreCase(columnTypeName)) {
//                    	SerialClob clob = (SerialClob) rowData;
//                    	row.put(key, clob.getString());
//					}
                    else  if ("timestamp".equalsIgnoreCase(columnTypeName)) {
						Timestamp data = crs.getTimestamp(colummName);
						row.put(key, DateUtils.convSqlTimestampToUtilCalendar(data));
					} else if ("date".equalsIgnoreCase(columnTypeName)) {
						Timestamp data = crs.getTimestamp(colummName);
						row.put(key, DateUtils.convSqlTimestampToUtilCalendar(data));
					} else if ("number".equalsIgnoreCase(columnTypeName)) {
						//总长度，包括整数和小数
						int precision = metaData.getPrecision(i);
						//小数部分
						int scale = metaData.getScale(i);
						//String num = crs.getString(colummName);//TODO 数字精度丢失
						String num = crs.getObject(colummName).toString();
						if (TycxUtils.isEmpty(num)) {
							num = "0";
						}
						if (precision <= 0) {
							row.put(key, num);//这个是什么意思？
						} else {
							if (scale > 0) {
								//带小数点的
								Object data = crs.getObject(colummName);//getRealNumber(num,precision,scale);
								row.put(key, data);
							} else if (precision > 8 && precision<19) {
								Object data = new Long(num);
								row.put(key, data);
							}else if(precision>18){
								//某些主键设置为20位的数组，无法转换成Long型 超过最大值9223372036854775807
								row.put(key, num);								
							}else {
								Object data = new Integer(num);
								row.put(key, data);
							}
						}
					} else {
						//不知道类型的值直接put到map
						row.put(key, rowData);
					}
				}
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMap;
	}

	private Object getRealNumber(String num, int precision, int scale) {
		//Double 整数部分超过7位（不含7）或者小数部分超过9位（不含9） 就会使用科学计数法
		//其中在小数9位时，精度丢失 .999999999变成 .999999998
		if(TycxUtils.isEmpty(num) ){
			return null;
		}
		int posint = (precision-scale);
		if(posint>7 || scale>9){
			return new BigDecimal(num);
		}else{
			return new Double(num);
		}
		
	}

	private String getStandardKey(String key) {
		// TODO Auto-generated method stub
		if(key.indexOf("_")<0){//没有下划线
			return key;
		}
		if(key.indexOf("_")==0){//首字下划线
			return key.substring(1);
		}
		String[] allString = key.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allString.length; i++) {
			if(i==0){
				//第一个的首字母不用大写
				sb.append(allString[i]);
			}else{
			//剩下的首字母大写
			sb.append(allString[i].substring(0, 1).toUpperCase())
			.append(allString[i].substring(1));
			}
		}
		
		return sb.toString();
	}

	private Map rowsetToMap( CachedRowSet crs) {
		
		Map mapTable = new HashMap();
		try {
			int count = crs.getMetaData().getColumnCount();
			ResultSetMetaData metaData = crs.getMetaData();
			String columnTypeName = null;
			String colummName = null;
			int index = 0;
			while (crs.next()) {
				Map row = new HashMap();
				mapTable.put("row"+index, row);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					columnTypeName = metaData.getColumnTypeName(i);
					colummName = metaData.getColumnName(i);
					// logger.debug("=colummName="+colummName+":"+columnTypeName
					// +"("+metaData.getPrecision(i)+","+metaData.getScale(i)+")");
					String key = colummName.toLowerCase();//TODO 要将字段与视图属性的映射规则规范

					if ("timestamp".equalsIgnoreCase(columnTypeName)) {
						Timestamp data = crs.getTimestamp(colummName);
						row.put(key, DateUtils.convSqlTimestampToUtilCalendar(data));
					} else if ("date".equalsIgnoreCase(columnTypeName)) {
						Timestamp data = crs.getTimestamp(colummName);
						row.put(key, DateUtils.convSqlTimestampToUtilCalendar(data));
					} else if ("number".equalsIgnoreCase(columnTypeName)) {
						
						int precision = metaData.getPrecision(i);
						int scale = metaData.getScale(i);
						String num = crs.getString(colummName);
						if (TycxUtils.isEmpty(num)) {
							num = "0";
						}
						if (precision <= 0) {
							row.put(key, num);
						} else {
							if (scale > 0) {
								Object data = new Double(num);
								row.put(key, data);
							} else if (precision > 8) {
								Object data = new Long(num);
								row.put(key, data);
							} else {
								Object data = new Integer(num);
								row.put(key, data);
							}
						}
					} else {
						Object data = crs.getObject(colummName);
						row.put(key, data);
					}
				}
				index++;
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapTable;
	}
	
	

}
