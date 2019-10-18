package com.cwks.bizcore.persistence;


import com.cwks.bizcore.persistence.arraytable.ArrayParam;
import com.cwks.bizcore.persistence.arraytable.ArrayTableParam;
import com.cwks.bizcore.persistence.arraytable.IArray;
import com.cwks.bizcore.persistence.arraytable.StructParam;
import com.cwks.bizcore.persistence.lob.BlobParam;
import com.cwks.bizcore.persistence.lob.ClobParam;
import com.cwks.bizcore.persistence.outtype.BaseInOutParam;
import com.cwks.bizcore.persistence.outtype.BaseOutParam;
import com.cwks.bizcore.persistence.outtype.OracleCursor;
import oracle.sql.StructDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;



//import com.css.sword.cssnj.bizcore.persistence.arraytable.ArrayParam;
//import com.css.sword.cssnj.bizcore.persistence.arraytable.ArrayTableParam;
//import com.css.sword.cssnj.bizcore.persistence.arraytable.IArray;
//import com.css.sword.cssnj.bizcore.persistence.arraytable.StructParam;
//import com.css.sword.cssnj.bizcore.persistence.exception.StoredProcRuntimeException;
//import com.css.sword.cssnj.bizcore.persistence.exception.TfBaseRuntimeException;
//import com.css.sword.cssnj.bizcore.persistence.lob.BlobParam;
//import com.css.sword.cssnj.bizcore.persistence.lob.ClobParam;
//import com.css.sword.cssnj.bizcore.persistence.outtype.BaseInOutParam;
//import com.css.sword.cssnj.bizcore.persistence.outtype.BaseOutParam;
//import com.css.sword.cssnj.bizcore.persistence.outtype.OracleCursor;
//import com.css.sword.cssnj.bizcore.util.DateUtil;
//import com.css.sword.platform.comm.conf.ConfManager;
//import com.css.sword.platform.comm.log.LogFactory;
//import com.css.sword.platform.comm.log.LogWritter;
//import com.css.sword.platform.persistence.PersistenceCheckedException;
//import com.css.sword.platform.web.context.ContextAPI;

/**
 * 存储过程调用类
 * 根据配置文件 sword.xml 的 <property name="wtc-model" value="false" /> 节点
 * 如果value="true" 走WTC调Tuxedo再调用存储过程；
 * 如果value="false" 走JDBC直接调用存储过程；
 * <p/>
 * 注意，导入的weblogic.jdbc.extensions.WLConnection;类在wlfullclient5.jar包里，
 * 1.6与1.5版本jdk会有不同的jar包，详细参考：
 * http://download.oracle.com/docs/cd/E15051_01/wls/docs103/client/jarbuilder.html
 * <p/>
 * 该包用于Java客户端调用weblogic10以上的版本
 * <p/>
 * 对于字符数组型的参数，需要在数据库定义一个类型，如下所示：
 * <p/>
 * create or replace type VarcharArray as table of varchar2(200)
 * 名称为：VarcharArray
 *
 * @author Administrator
 */
public class SpManager {

    public static final String TWODIMVARCHARARRAY = "TYPE_SA2";

    public static final String VARCHARARRAY = "TYPE_SA";

//    private final static LogWritter logger = LogFactory.getLogger(SpManager.class);
	private static Logger logger = LoggerFactory.getLogger(SpManager.class);
    /**
     * 供网税调用的方法，适合在企业的登录码 机关码等信息不可获取时，如果可获取，就直接调用
     * public static Map callProc (Connection con, String pName, List params)
     * 
     * @Method callProc
     * @Function 功能描述：
     * @param con		数据库链接
     * @param pName		存储过程或交易码
     * @param params	参数列表
     * @param iSwjbm	税务局编码
     * @param czydlm	企业登录码
     * @param clientIP  客户端IP
     * @return
     * @Date 2010-12-24
     */
   /** public static Map callProc (Connection con, String pName, List params,int iSwjbm,String czydlm,String clientIP){
    	String paramStr = params2str(params);
        logger.debug("通过wtc执行的存储过程【" + pName + "】，参数字符串为：【" + paramStr + "】");
    	Map res = TFDxLink.TFDxLinkCallByListForMap2(pName,iSwjbm+"",czydlm,clientIP,params);
        
        //DBUtil.getResult(ContextAPI.getReq(),code,paramStr);
        SpResult spRes = new SpResult(pName);
        spRes.setWtcMapResult(res);
        return spRes.getNewMapRes(params);
    }*/
    
    //配置文件使用wtc的标记
    protected static final String MODEL_WTC = "wtc-model";
    //protected static final String model_jdbc="jdbc";

    //路由

//    public static boolean wtcModel() {
//        //默认没写这个标记是走JDBC的
//        String model_wtc = (String) ConfManager.getValueByKey(MODEL_WTC);
//        return model_wtc != null && "true".equalsIgnoreCase(model_wtc);
//    }

    /**
     * 调用数据库方法，输入参数为params
     * 返回参数为Object类型，目前只返回字符串，但在数据库方法中可以定义返回其他类型
     *
     * @param functionName 方法名
     * @param params       输入参数
     * @return 执行方法的返回值
     */
    public static Object callFunction(Connection conn, String functionName, Object[] params) throws  Exception{
        List ps = new ArrayList();
        for (int i = 0; i < params.length; i++) {
            ps.add(params[i]);
        }
        return callFunction(conn, functionName, ps);
    }

    /**
     * 调用数据库方法，输入参数为params，
     * 返回参数为Object类型
     *
     * @param functionName 方法名
     * @param params       输入参数
     * @param returnType   返回类型，在java.sql.Types中定义,
     *                     因为调用数据库的返回类型是通过传入的参数类型定义出来的，这个参数无法省略
     * @return 执行方法的返回值
     */
    public static Object callFunction(Connection conn, String functionName, List params, int returnType) throws  Exception{
        //获取 ? = call functionname (?,?)
        String callStr = prepareCallString(functionName, params);
        List allParams = new ArrayList();
        BaseOutParam out = new BaseOutParam();
        out.setType(returnType);
        //返回参数一般是第一个
        allParams.add(out);
        allParams.addAll(params);
        //将普通参数转换成存储过程或方法能识别的参数
        List finalParams = getCallParameters(conn, allParams);

        //调用数据库方法

        logger.debug("通过jdbc调用Oracle的Function【" + functionName + "】的执行语句为：【" + callStr + "】，参数为：【" + finalParams + "】");
        //执行后，返回参数将带上返回值
        Map returnValue = new HashMap();
        try {
            returnValue = StoredProcDataUtil.callStoreProcedure(conn, callStr, finalParams);
            //以下方法好像多此一举
            //out.setIndex(1);
            //out.setValue(returnValue.get("result1"));
        } catch (Exception e) {
        	// TODO: handle exception
//        	StoredProcRuntimeException ex = new StoredProcRuntimeException("执行存储过程出现异常!!",e);
//        	TfBaseRuntimeException tre = new TfBaseRuntimeException(ex.getTfErrorCode(),e);
//        	tre.setDebugMes(ex.getDebugMsg());
//        	tre.setBizMessage(ex.getMessage());
//        	tre.setTfErrorCode(ex.getTfErrorCode());
//        	tre.addParam(params);//将存储过程参数返回到异常展现里
//        	tre.setProcedureName(functionName);
        	throw e;
        }
        
        //将值赋予括号内传入的out参数
        SpResult sp = new SpResult(callStr);
        sp.setJdbcMapresult(returnValue);
        sp.getNewMapRes(allParams);//这个方法会将返回值设置到out参数里
        
        //将方法返回值返回
        //return returnValue.get("result1");
        //OracleThinBlob blob = null;
        //oracle.sql.BLOB
        Object value = out.getValue();
        try{
        	if(value instanceof Blob){
        		BlobParam bp = new BlobParam();
        		ByteArrayOutputStream bos = new ByteArrayOutputStream();
        		Blob blob = (Blob) value;
        		InputStream is = blob.getBinaryStream();
        		try{
  		
        		byte[] buffer = new byte[10240];
        		int len = 0;
        		while(true){
        			len = is.read(buffer);
        			if(len <= 0){
        				break;
        			}
        			bos.write(buffer, 0, len);
        		}
        		
        		bp.setBytes(bos.toByteArray());
        		}finally{
        			is.close();
        		}
        		return bp;
        	}else
            //clob
        	if(value instanceof Clob){
        		ClobParam bp = new ClobParam();
        		Clob clob = (Clob) value;
        		
        		Reader clobReader = clob.getCharacterStream();
                if (clobReader == null) {
                    return null;
                }

                String str = new String();
                BufferedReader bufferedClobReader = new BufferedReader(clobReader);
                try {
                    String line = null;
                    while ((line = bufferedClobReader.readLine()) != null) {
                        str += line;
                    }
                    bufferedClobReader.close();
                }
                catch (IOException e) {
                    throw new SQLException(e.toString());
                }
        		bp.setData(str);
        		return bp;
        	}	
        }catch (Exception e) {
			logger.error("转换出错",e);
		}
        
        return out.getValue();
    }

    /**
     * 调用数据库方法，输入参数为params，
     * 返回参数为Object类型，目前只返回字符串，但在数据库方法中可以定义返回其他类型
     *
     * @param functionName 方法名
     * @param params       输入参数
     * @return 执行方法的返回值
     */
    public static Object callFunction(Connection conn, String functionName, List params) throws  Exception{

        return callFunction(conn, functionName, params, java.sql.Types.CHAR);
    }


    private static String prepareCallString(String functionName, List params) {
        StringBuffer sb = new StringBuffer();
        sb.append("{? = call ").append(functionName).append("(");
        for (int i = 0; i < params.size(); i++) {
            if (i == params.size() - 1) {
                sb.append("?");
            } else {
                sb.append("?,");
            }
        }
        sb.append(")}");
        logger.debug("调用方法的语句是:" + sb);
        
        return sb.toString();
    }


    private static int getType(Object object) {
		//如果传入的类型为空，暂时以空类型回复
		if(object == null){
			logger.debug("判断的类型为:"+"java.sql.Types.NULL");
			return java.sql.Types.NULL;
		}
		// TODO Auto-generated method stub
		Class clazz = object.getClass();
		String classType = clazz.getName();
		
		//下面肯定是有类型的
		try {
			if (classType != null ) {
                //数组类型 只支持字符串 一维二维差别在于前面多少个[ 自定义二维数组类型也算数组类型
				if(classType.startsWith("[L")||classType.startsWith("[[L") || object instanceof IArray){
					logger.debug("判断的类型为:"+"java.sql.Types.ARRAY");
					return java.sql.Types.ARRAY;
				}
				
				if (classType.endsWith("String")) {
					logger.debug("判断的类型为:"+"java.sql.Types.VARCHAR");
					return java.sql.Types.VARCHAR;
					
				} else if (classType.endsWith("Double")) {
					logger.debug("判断的类型为:"+"java.sql.Types.Double");
					return java.sql.Types.DOUBLE;
				} else if (classType.endsWith("Long")) {
					logger.debug("传入Long, 判断的类型为:"+"java.sql.Types.BIGINT");
					return java.sql.Types.BIGINT;//TODO 类型定义得查下文档
				} else if (classType.endsWith("Integer")) {
					logger.debug("判断的类型为:"+"java.sql.Types.INTEGER");
					return java.sql.Types.INTEGER;
				} else if (classType.endsWith("double")) {
					logger.debug("判断的类型为:"+"java.sql.Types.DOUBLE");
					return java.sql.Types.DOUBLE;
				} else if (classType.endsWith("int")) {
					logger.debug("判断的类型为:"+"java.sql.Types.INTEGER");
					return java.sql.Types.INTEGER;
				} else if (classType.endsWith("long")) {
					logger.debug("判断的类型为:"+"java.sql.Types.INTEGER");
					return java.sql.Types.INTEGER;
				} else if (classType.endsWith("BigDecimal")) {
					logger.debug("判断的类型为:"+"java.sql.Types.DECIMAL");
					return java.sql.Types.DECIMAL;
				} else if (classType.endsWith("Calendar")) {
					logger.debug("判断的类型为:"+"java.sql.Types.DATE");
					return java.sql.Types.DATE;
				} else if (classType.endsWith("Date")) {
					logger.debug("判断的类型为:"+"java.sql.Types.DATE");
					return java.sql.Types.DATE;
				} else if (classType.endsWith("Timestamp")) {
					logger.debug("判断的类型为:"+"java.sql.Types.TIMESTAMP");
					return java.sql.Types.TIMESTAMP;
				}
			}
		} catch (Exception ex) {
			logger.error("获取类型失败：", ex);
		}
		return 0;
	}

    private static String dealPname(String pname, List params) {
        StringBuffer sb = new StringBuffer("{ call ");
        sb.append(pname).append("(");

        //拼参数。。
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                sb.append("?");
                if (i != params.size() - 1) {//不是最后一项
                    sb.append(",");
                }
            }
        }
        sb.append(") }");
        return sb.toString();
    }


    //wtc方式执行存储过程
 /**   private static SpResult wtcProcess(String pName, List params) throws Exception {
        String code = pName.split("_")[pName.split("_").length - 1];
        String paramStr = params2str(params);
        logger.debug("通过wtc执行的存储过程【" + pName + "】的编号为：【" + code + "】，参数字符串为：【" + paramStr + "】");

        //todo httpRequest 在blh层是不能获得的，暂时在调试模式下获得，将不能发布成ejb模式
        //注释这个方法
        //WtcResult theResult = DBUtil.getResult(ContextAPI.getReq(),code,paramStr);
        String swjbm = null;
		try {
			swjbm = UserSessionItaxManager.getSwjbm(null);
		} catch (Exception e) {
		
		}
		if(swjbm == null){
			logger.error("没有登录信息!!使用默认"+pName);
			swjbm = "330000";
		}
		String dlm = null;
		try {
			dlm = UserSessionItaxManager.getQydlm(null);
		} catch (Exception e) {
			
		}
		if(dlm == null){
			logger.error("没有登录信息!!使用默认"+pName);
			dlm = "tfd330000";
		}
        
        //String dlm = UserSessionItaxManager.getQydlm(null)==null?"tfd330000":UserSessionItaxManager.getQydlm(null);
        String ip = getRemoteIP();
        Map res = TFDxLink.TFDxLinkCallByListForMap2(code,swjbm,dlm,ip,params);
        
        //DBUtil.getResult(ContextAPI.getReq(),code,paramStr);
        SpResult spRes = new SpResult(pName);
        spRes.setWtcMapResult(res);
        return spRes;

    }*/

//    private static String getRemoteIP() {
//    	String ip = ContextAPI.getReq()==null?"127.0.0.1":ContextAPI.getReq().getRemoteHost();
//    	ip = (ip==null?"":ip);
//    	if(ip.equals("0:0:0:0:0:0:0:1")){
//    		return "127.0.0.1";
//    	}
//    	return ip;
//	}

	private static String params2str(List params) {

        if (params == null) return "";

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof String) {
                sb.append("'").append(param).append("'");
            } else {
                sb.append(param);
            }
        }

        if (sb.length() == 0) return "";
        return sb.substring(0, sb.length() - 1) + "~@~";

    }

    private static SpResult jdbcProcess(Connection con, String pName, List paramsinput) throws Exception {

        //将参数转换成调用存储过程的模式，都是输入参数，在最后添加一个回复的游标类型，
        List finalParams = getCallParameters(con, paramsinput);

        String excStr = dealPname(pName, finalParams);

        logger.debug("Execute Procedure :: [" + pName + "], SQL :: [" + excStr + "]!!!");
        //Collection collRes=StoredProcDataUtil.callStoreProcess(con, excStr, finalParams);
        //SpResult spRes=new SpResult(null,collRes);
        
        Map collRes = StoredProcDataUtil.callStoreProcedure(con, excStr, finalParams);
        SpResult spRes = new SpResult(excStr);
        spRes.setJdbcMapresult(collRes);

        return spRes;
    }

    /**
     * @param con
     * @param paramsinput
     * @return
     * @throws RuntimeException
     */
    private static List getCallParameters(Connection con, List paramsinput) {
        List finalParams = new ArrayList();
        int index = 1;
        for (Iterator iterator = paramsinput.iterator(); iterator.hasNext();) {
            Object object = iterator.next();
            int type = getType(object);
            //logger.debug("判断的类型为:" + type);
            SpParam spParam = null;

            if (type == java.sql.Types.ARRAY) {
                //ArrayDescriptor 数组单独处理 数据库必须要有对应的数组类型  目前只处理字符串
                try {
                    //如果从数据源获取，如weblogic，必须转换成原生的Oracle链接
                    logger.debug("The Class Type of con is :: "+con.getClass());
                	/*if (con instanceof WLConnection) {
                        //这里将实际的连接类型转换
                        con = ((WLConnection) con).getVendorConnection();
                        //logger.debug("After WLConnection Convert, The Class Type of con is :: "+con.getClass());
                        con = con.getMetaData().getConnection();
                        logger.debug("After WLConnection Convert And getMetaData.getConnection, The Class Type of con is :: "+con.getClass());
                    } else {
                        //通过元数据取的原生connection
                        con = con.getMetaData().getConnection();
                        if(con instanceof weblogic.jdbc.wrapper.Connection){
                        	con = ((weblogic.jdbc.wrapper.Connection)con).getVendorConnection();
                        	logger.debug("After getVendorConnection, The Class Type of con is :: "+con.getClass());
                        }
                    }*/
                    con = DbUtils.getPrimaryConnection(con);
                	//logger.debug("Now The ClassLoader of Connection is :: "+con.getClass().getClassLoader());
                	//logger.debug("Now The ClassLoader of oracle.jdbc.OracleConnection is :: "+oracle.jdbc.OracleConnection.class.getClassLoader());
                	/*if(!(con instanceof oracle.jdbc.OracleConnection)){
                		con = con.getMetaData().getConnection();
                		logger.debug("Now The Class Type of con is :: "+con.getClass());
                	}else{
                		logger.debug("Now The Class Type of con is :: The Perfect Connection In The World!");
                	}*/
                    //TODO 代码有点乱 四处判断
                    if (object.getClass().getName().startsWith("[[L") || object instanceof ArrayTableParam) {
                        logger.debug("设置二维数组描述信息!!");
                        oracle.sql.ARRAY array2 = null;
                        if (object instanceof ArrayTableParam) {
                            //如果类型为ArrayTableParam 描述信息可能会有开发传进来
                            ArrayTableParam atp = (ArrayTableParam) object;
                            oracle.sql.ArrayDescriptor descvarchar = oracle.sql.ArrayDescriptor
                                    .createDescriptor(atp.getTypeName().toUpperCase(), con);// 必须大写
                            array2 = new oracle.sql.ARRAY(descvarchar, con, atp.getData());
                            logger.debug("The String Value Of Param is :: "+getStringValueForArray2(atp.getData()));

                        } else {
                            //字符串二维数组  默认传入的格式
                            oracle.sql.ArrayDescriptor descvarchar = oracle.sql.ArrayDescriptor
                                    .createDescriptor(TWODIMVARCHARARRAY, con);// 必须大写
                            array2 = new oracle.sql.ARRAY(descvarchar, con, object);
                            logger.debug("The String Value Of Param is :: "+getStringValueForArray2((Object[][]) object));
                        }
                        spParam = new SpParam(index, array2, SpParam.IN, type);
                        spParam.setArrayType(2);

                    } else {

                        if (object instanceof ArrayParam) {
                            logger.debug("设置一维数组描述信息!!");
                            ArrayParam ap = (ArrayParam) object;
                            //StructDescriptor sd = StructDescriptor.createDescriptor(ap.getTypeName().toUpperCase(), con);

                            /*oracle.sql.ArrayDescriptor descvarchar = oracle.sql.ArrayDescriptor
                               .createDescriptor(ap.getTypeName().toUpperCase(), con);*/// 必须大写
                            //array2 = new oracle.sql.ARRAY(descvarchar, con, ap.getData());
                            oracle.sql.ArrayDescriptor descvarchar = oracle.sql.ArrayDescriptor
                            .createDescriptor(ap.getTypeName().toUpperCase(), con);// 必须大写
                            oracle.sql.ARRAY array = new oracle.sql.ARRAY(
                                    descvarchar, con, ap.getData());
                            logger.debug("The String Value Of Param is :: "+getStringValueForArray(ap.getData()));
                            spParam = new SpParam(index, array, SpParam.IN, java.sql.Types.ARRAY);
                            spParam.setArrayType(1);//?

                        }else if(object instanceof StructParam) {
                            logger.debug("设置结构体描述信息!!");
                            StructParam ap = (StructParam) object;
                            StructDescriptor sd = StructDescriptor.createDescriptor(ap.getTypeName().toUpperCase(), con);
                            logger.debug("The String Value Of Param is :: "+getStringValueForArray(ap.getData()));
                            oracle.sql.STRUCT array = new oracle.sql.STRUCT(sd, con, ap.getData());
                            spParam = new SpParam(index, array, SpParam.IN, java.sql.Types.STRUCT);
                            spParam.setArrayType(1);//?

                        }else {
                            //一维字符串数组
                            logger.debug("设置一维字符串数组描述信息!!");
                            oracle.sql.ArrayDescriptor descvarchar = oracle.sql.ArrayDescriptor
                                    .createDescriptor(VARCHARARRAY, con);// 必须大写

                            oracle.sql.ARRAY array2 = new oracle.sql.ARRAY(
                                    descvarchar, con, object);
                            logger.debug("The String Value Of Param is :: "+getStringValueForArray((Object[]) object));
                            //TODO 使用Oracle11G的方法
                            /*OracleConnection conn = (OracleConnection) con;
                           oracle.sql.ARRAY array2 = conn.createARRAY("VARCHARARRAY", object);*/
                            spParam = new SpParam(index, array2, SpParam.IN, type);
                            spParam.setArrayType(1);//?

                        }

                    }

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new RuntimeException("转换成数组类型的存储过程参数出错！！！", e);
                }


            } else if (object instanceof OracleCursor) {
                //如果传入的参数是这种类型，就表示这个位置要构造一个回复的游标类型参数
                logger.debug("========>传入一个OUT游标参数 index" + index);
                OracleCursor oc = (OracleCursor) object;
                oc.setIndex(index);
                spParam = new SpParam(index, null, SpParam.OUT, oracle.jdbc.OracleTypes.CURSOR);

            } else if (object instanceof BaseOutParam) {
                //如果传入的参数是这种类型，就表示这个位置要构造一个回复的基本类型参数
                logger.debug("========>传入一个OUT普通参数 index" + index);
                BaseOutParam bp = (BaseOutParam) object;
                bp.setIndex(index);
                spParam = new SpParam(index, null, SpParam.OUT, bp.getType());
                //增加对自定义类型的判断 用于回复数据
                if(bp.getUserDefineType() != null){
                	spParam.setUserDefineType(bp.getUserDefineType());
                }

            }else if (object instanceof BaseInOutParam) {
                //如果传入的参数是这种类型，就表示这个位置要构造一个回复的基本类型参数
                logger.debug("========>传入一个IN/OUT参数 index" + index);
                BaseInOutParam bp = (BaseInOutParam) object;
                bp.setIndex(index);
                logger.debug("The String Value Of Param is :: "+getStringValue(bp.getValue()));
                spParam = new SpParam(index, bp.getValue(), SpParam.INOUT, bp.getType());
                //增加对自定义类型的判断 用于回复数据
                if(bp.getUserDefineType() != null){
                	spParam.setUserDefineType(bp.getUserDefineType());
                }

//            } else if (object instanceof BlobParam) {
//                //Blob类型的传入参数，会构造成Blob对象
//                logger.debug("========>传入一个BLOB参数 index" + index);
//                BlobParam bp = (BlobParam) object;
//                //bp.setIndex(index);
//                spParam = new SpParam(index, bp.getBlob(con), SpParam.IN, java.sql.Types.BLOB);

//            } else if (object instanceof ClobParam) {
//                //Clob类型的传入参数，会构造成Clob对象
//                logger.debug("========>传入一个CLOB参数 index" + index);
//                ClobParam bp = (ClobParam) object;
//                logger.debug("The String Value Of Param is :: "+getStringValue(bp.getData()));
//                //bp.setIndex(index);
//                spParam = new SpParam(index, bp.getClob(con), SpParam.IN, java.sql.Types.CLOB);

            } else {
                spParam = new SpParam(index, object, SpParam.IN, type);//构造一个存储过程的参数
                logger.debug("The String Value Of Param is :: "+getStringValue(object));
            }
            index++;
            finalParams.add(spParam);
        }


        return finalParams;
    }
    //二维数组转换
    private static String getStringValueForArray2(Object[][] datas) {
		if (datas == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < datas.length; i++) {
			Object[] array = datas[i];
			sb.append("\nrow"+i+"["+getStringValueForArray(array)+"]");			
		}
		return sb.toString();
	}
    //一维转换
	private static String getStringValueForArray(Object[] array) {
		if (array == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("cellCount="+array.length+" [");
		for (int i = 0; i < array.length; i++) {
			if (i<array.length -1) {
				sb.append(getStringValue(array[i])+",");
			}else{
				sb.append(getStringValue(array[i]));
			}
		}
		sb.append("]");
		return sb.toString();
	}
	//对象转换 只处理时间
	private static String getStringValue(Object object) {
		if (object == null) {
			return null;
		}
		if(object instanceof Date){
			return DateUtil.dateToString((Date) object,1);
		}
		if(object instanceof Calendar){
			//return DateUtil.dateToString((Date) object,1);
			return DateUtil.toDateStr((Calendar) object,1);
		}
		return object.toString();
	}

	/**
     * 存储过程调用的统一入口 重载方法:传入数组参数返回Map
     *
     * @param con    数据库链接
     * @param pName  存储过程名称
     * @param params 存储过程参数，List列表的顺序与存储过程的参数顺序一致
     * @return
     */
    public static Map callProc(Connection con, String pName, Object[] params)throws  Exception{
        List ps = null;

        if (params != null && params.length > 0) {
            ps = new ArrayList();
            for (int i = 0; i < params.length; i++)
                ps.add(params[i]);
        }

        return callProc(con, pName, ps);
    }

    /**
     * 存储过程调用的统一入口，
     * <br/>
     * 1、该方法返回Map对象存储结果集<br/>
     * 2、参数支持数组（只字符串数组）和普通Java类型，如数字、字符串、日期等类型<br/>
     * 3、通过构造返回参数如OracleCursor或BaseOutParam 对象去获取返回值 <br/>
     *
     * @param con    数据库链接
     * @param pName  存储过程名称
     * @param params 存储过程参数，List列表的顺序与存储过程的参数顺序一致
     * @return
     */

    public static Map callProc(Connection con, String pName, List params) throws  Exception{
        SpResult res = null;

        res = dealProcedure(con, pName, params, res);
        //return res.getMapRes();
        return res.getNewMapRes(params);
    }

    /**
     * @param con
     * @param pName
     * @param params
     * @param res
     * @return
     */
    private static SpResult dealProcedure(Connection con, String pName,
                                          List params, SpResult res) throws  Exception{

        //解析 pName
        //可能是存储过程，如果不包含括号如EP_DZSB_0001,表示存储过程名称跟交易号一样
        //可能是存储过程名加交易号，格式如：EP_DZSB_0001(0001111) 这时调用存储过程是传EP_DZSB_0001，WTC传0001111

        String transactionCode = null;
        String procName = null;

        if (pName.indexOf("(") < 0) {
            //不包含括号
            transactionCode = pName;
            procName = pName;
        } else {
            int start = pName.indexOf("(");
            int end = pName.indexOf(")");

            procName = pName.substring(0, start);
            transactionCode = pName.substring(start + 1, end);
        }

        try {
            if (false ) {
                //TODO 只取后七位 EP_DZSB_0001(0001111)
//                res = wtcProcess(transactionCode, params);

            } else {

                res = jdbcProcess(con, procName, params);

            }
        } catch (Exception e) {
//            // TODO: handle exception
//        	StoredProcRuntimeException ex = new StoredProcRuntimeException("执行存储过程出现异常!!",e);
//        	TfBaseRuntimeException tre = new TfBaseRuntimeException(ex.getTfErrorCode(),e);
//        	tre.setDebugMes(ex.getDebugMsg());
//        	tre.setBizMessage(ex.getMessage());
//        	tre.setTfErrorCode(ex.getTfErrorCode());
//        	tre.addParam(params);//将存储过程参数返回到异常展现里
//        	tre.setProcedureName(pName);
        	throw e;
        }
        return res;
    }

}
