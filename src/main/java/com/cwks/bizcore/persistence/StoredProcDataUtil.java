package com.cwks.bizcore.persistence;

import com.cwks.bizcore.persistence.arraytable.BaseStoredProcParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jdbc.rowset.CachedRowSet;

import java.sql.*;
import java.util.*;
//import com.css.sword.platform.comm.log.LogFactory;
//import com.css.sword.platform.comm.log.LogWritter;
//import com.cwks.bizcore.persistence.PersistenceCheckedException;


/**
 * 对存储过程执行的封装类。该类封装了对存储过程的调用过程。<br>
 * 所执行的存储过程都是已经写好的，存放在数据库上面的存储过程。调用的时候只<br>
 * 需要输入存储过程的名字，就可以得到其执行结果。另外，存储到数据库里面的存<br>
 * 储过程应该不涉及到返回select结果集。还有，最好不要写一些涉及到业务逻辑<br>
 * 的存储过程，业务处理应该放到BP层去实现。
 * <p>Title: StoredProcManage </p>
 *
 * @author Administrator
 * @version 1.0
 */

public final class StoredProcDataUtil {

//	private final static LogWritter logger = LogFactory.getLogger(StoredProcDataUtil.class);
	private static Logger logger = LoggerFactory.getLogger(SpManager.class);
    public StoredProcDataUtil() {
    }

    /**
     * 执行数据库中以storeProcessName命名的存储过程。方法中会调用
     * createCallableStatement方法生成CallableStatement实例，
     * 调用prepareParams方法为其设置参数;执行存储过程结束后，调用
     * setResult方法来来生成一个Collection类型的结果，并返回结果。最后
     * 要调用callableStatementClose方法关闭。该方法会将捕获到的异
     * 常封装成PersistenceCheckedException异常抛出。
     *
     * @param con:Connection        - 与数据库的连接。
     * @param storedProcName:String - 存储过程的名称。
     * @param params:ArrayList      - 封装了存储过程的所有输入、输出参
     *                              数，每个参数用BaseStoredProcParam来封装。
     * @return Object -  封装的执行结果。
     * @throws PersistenceCheckedException
     */
    public static Collection callStoreProcess(Connection con,
                                              String storedProcName,
                                              List params)
            throws Exception {

        CallableStatement cs = null;
        try {
            cs = con.prepareCall(storedProcName);
            prepareParams(cs, params); //设置参数
            cs.execute(); //执行存储过程
            Collection result = setResult(cs, params); //生成存储过程的结果
            return result;
        }
        catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);

//            PersistenceCheckedException e =
//                    new PersistenceCheckedException("002064", ex);
////            e.addParam(storedProcName);
//            e.setSqlCode(ex.getErrorCode());
//            e.setSqlErrorMessage(ex.getMessage() );
            throw ex;
        }
        finally {
            try {
                if (cs != null) {
                    cs.close();
                }
            }
            catch (SQLException ex1) {
                logger.error(ex1.getMessage(), ex1);
            }
        }
    }
    /**
     * 因为上面的方法丢失了回复参数的索引信息，特此增加此方法
     * 
     * 执行数据库中以storeProcessName命名的存储过程。方法中会调用
     * createCallableStatement方法生成CallableStatement实例，
     * 调用prepareParams方法为其设置参数;执行存储过程结束后，调用
     * setResult方法来来生成一个Collection类型的结果，并返回结果。最后
     * 要调用callableStatementClose方法关闭。该方法会将捕获到的异
     * 常封装成PersistenceCheckedException异常抛出。
     * 
     * @param con:Connection        - 与数据库的连接。
     * @param storedProcName:String - 存储过程的名称。
     * @param params:ArrayList      - 封装了存储过程的所有输入、输出参
     *                              数，每个参数用BaseStoredProcParam来封装。
     * @return Map -  封装的执行结果，普通对象用result+index(传入的索引顺序)的key存储在Map对象中
     * @throws PersistenceCheckedException
     */
    public static Map callStoreProcedure(Connection con, String storedProcName,	List params) throws Exception {

    	CallableStatement cs = null;
    	try {
    		cs = con.prepareCall(storedProcName);
    		prepareParams(cs, params); //设置参数
    		cs.execute(); //执行存储过程
    		Map result = setMapResult(cs, params); //生成存储过程的结果
    		return result;
    	}
    	catch (SQLException ex) {
    		logger.error(ex.getMessage(), ex);

//    		PersistenceCheckedException e =
//    			new PersistenceCheckedException(ex.getErrorCode()+"", ex);
//    		//e.addParam(storedProcName);
//    		e.setSqlCode(ex.getErrorCode());
//    		e.setSqlErrorMessage(ex.getMessage() );
    		throw ex;
    	}
    	finally {
    		try {
    			if (cs != null) {
    				cs.close();
    			}
    		}
    		catch (SQLException ex1) {
    			logger.error(ex1.getMessage(), ex1);
    		}
    	}
    }

    private static Map setMapResult(CallableStatement cs, List params) throws SQLException {
    	Map result = new HashMap();
        Iterator ii = params.iterator();
        BaseStoredProcParam spp = null;

        while (ii.hasNext()) {
            spp = (BaseStoredProcParam) ii.next();
            if (spp.getParamType().equals(BaseStoredProcParam.OUT) 
            		|| spp.getParamType().equals(BaseStoredProcParam.INOUT)) {
                Object obj = cs.getObject(spp.getIndex());
                
                if (obj != null && obj instanceof ResultSet) {
                    CachedRowSet crs = new CachedRowSet();
                    crs.populate((ResultSet) obj);
                    result.put("result"+spp.getIndex(),crs);
                } else {              
                    //TODO 可能要在这里构造返回值
                	result.put("result"+spp.getIndex(),obj);
                }

            }
        }

        return result;
	}

	/**
     * 根据参数的类型进行参数的注册（输出参数）和赋值（输入参数）。
     *
     * @param cs:CallableStatement 被设置参数的CallableStatement实例。
     * @param params:ArrayList     - 封装了存储过程的所有输入、输出参数，每个
     *                             参数用StoredProcParamObj来封装。
     */
    private static CallableStatement prepareParams(
            CallableStatement cs, List params)
            throws SQLException {
        Iterator ii = params.iterator();
        BaseStoredProcParam spp = null;
        while (ii.hasNext()) {
        	//捕获异常 打出信息
        	try {
        		spp = (BaseStoredProcParam) ii.next();
        		
        		//有三种类型参数
        		
        		if (spp.getParamType().equals(BaseStoredProcParam.IN)
        				|| spp.getParamType().equals(BaseStoredProcParam.INOUT)) {
        			if (spp.getValue() == null) {
        				cs.setObject(spp.getIndex(), null, spp.getDataType());
        				//增加对空参数的in/out判断
        				if(spp.getParamType().equals(BaseStoredProcParam.INOUT)){
        					logger.debug("注册In out型返回参数!");
        					if(spp.getUserDefineType() != null){
        						cs.registerOutParameter(spp.getIndex(), spp.getDataType(), spp.getUserDefineType());
        					}else{
        						cs.registerOutParameter(spp.getIndex(), spp.getDataType());
        					}
        				}
        				continue;
        			}
        			//如果是二维数组类型，不能调用setObject方法            	
        			if (isTwoDimStringType(spp)) {
        				logger.debug("参数是二维数组类型!!!");
        				cs.setArray(spp.getIndex(), (Array) spp.getValue());
        			} else {
        				logger.debug("传入参数的类型:" + spp.getValue().getClass());
        				//TODO sql的date型时分秒被截 好像只有setTimestamp可以传过去
        				if (spp.getValue() instanceof java.util.Date) {
        					//cs.setDate(spp.getIndex(), (java.sql.Date) spp.getValue());
        					long time = ((java.util.Date) spp.getValue()).getTime();
        					cs.setTimestamp(spp.getIndex(),	new Timestamp(time));
        					spp.setDataType(Types.TIMESTAMP);//供inout使用

        				} else if (spp.getValue() instanceof Calendar) {
        					logger.debug("设置Clanedar类型参数");
        					long time = ((Calendar) spp.getValue()).getTimeInMillis();
        					cs.setTimestamp(spp.getIndex(),	new Timestamp(time));
        					spp.setDataType(Types.TIMESTAMP);//供inout使用

        				} else if (spp.getValue() instanceof Blob) {
        					logger.debug("设置Blob参数");
        					cs.setBlob(spp.getIndex(), (Blob) spp.getValue());

        				} else if (spp.getValue() instanceof Clob) {
        					logger.debug("设置Clob参数");
        					cs.setClob(spp.getIndex(), (Clob) spp.getValue());

        				} else {
        					cs.setObject(spp.getIndex(), spp.getValue(), spp
        							.getDataType());
        				}
        				
        			}
        		}else {
        			logger.debug("注册返回参数!");
        			if(spp.getUserDefineType() != null){
        				cs.registerOutParameter(spp.getIndex(), spp.getDataType(), spp.getUserDefineType());
        			}else{
        				cs.registerOutParameter(spp.getIndex(), spp.getDataType());

        			}

        		}
        		
        		//对in/out类型单独设置out
        		//IN OUT型参数需要分别注册In与Out，但index应为一样 类型要一致
				if(spp.getParamType().equals(BaseStoredProcParam.INOUT)){
					//注册inout型
					logger.debug("注册In out型返回参数!");
					if(spp.getUserDefineType() != null){
						cs.registerOutParameter(spp.getIndex(), spp.getDataType(), spp.getUserDefineType());
					}else{
						cs.registerOutParameter(spp.getIndex(), spp.getDataType());

					}
				}	
        	//end of 设置输入输出参数	
        		
			} catch (SQLException e) {
				logger.error("=====================================");
				logger.error("存储过程参数设置错误！参数序号为【"+spp.getIndex()+"】");
				logger.error("=====================================");
				throw e;
			}
        }

        return cs;
    }
    //如果每个数组的元素也是数组，就认为是二维数组
    private static boolean isTwoDimStringType(BaseStoredProcParam spp) {
		// TODO 这个转换不是很灵活
    	if(spp instanceof SpParam){
    		SpParam sp = (SpParam) spp;
    		return ((SpParam) spp).getArrayType() == 2;
    	}
		return false;
	}

	/**
     * 根据存储过程的执行结果，生成一个Map类型，用来封装存储过程的执行<br>
     * 结果。结果被封装在一个Collection类型的实例中，按照结果的顺序依<br>
     * 次放到Collection 实例中。
     *
     * @param cs:CallableStatement 执行存储过程后得到的CallableStatement。
     * @return Collection 封装的执行结果
     */
    private static Collection setResult(CallableStatement cs,
                                        List params)
            throws SQLException {
        Collection result = new ArrayList();
        Iterator ii = params.iterator();
        BaseStoredProcParam spp = null;

        while (ii.hasNext()) {
            spp = (BaseStoredProcParam) ii.next();
            if (spp.getParamType().equals(BaseStoredProcParam.OUT) ||
                    spp.getParamType().equals(BaseStoredProcParam.INOUT)) {
                Object obj = cs.getObject(spp.getIndex());
                if (obj != null && obj instanceof ResultSet) {
                    try {
						CachedRowSet crs = new CachedRowSet();
						crs.populate((ResultSet) obj);
						result.add(crs);
					} finally {
						((ResultSet) obj).close();//必须关闭
					}
                } else {
                    result.add(obj);
                }

            }
        }

        return result;
    }

}