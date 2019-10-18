package com.cwks.bizcore.comm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 时间帮助类
 * <p>Title: DataTypeUtil.java</p>
 * <p>Description: 间帮助类</p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <p>Company: cssnj</p>
 * @author 胡锐
 * @version 1.0
 */
public class DataTypeUtil {
	private static Logger logger  = LoggerFactory.getLogger(DataTypeUtil.class);

	/**
	    *计算两个日期相差多少天
	    * @param d1
	    * @param d2
	    * @return
	    */
	   public static long calendarMinus(Calendar d1,Calendar d2){
	     if(d1 == null || d2 == null){
	       return 0;
	     }
	     return (d1.getTimeInMillis()- d2.getTimeInMillis())/(3600*24000);
	   }

//	   public static void main(String args[]){
//		   System.out.print(calendarToString(calendarCaculate(stringToCalendar("2014-04-30"),-9)));
//	   }
	
	 /**
	   * String型转为long类型
	   * @return  long
	   */
	public static  long stringTolong(String str){
		long l = 0;
	    try {
	    	l = Long.parseLong(str);
	    }catch (Exception e) {
	    	logger.error("DataTypeUtil stringTolong Exception:", e);
	    }
	    return l;
	}
	
	/**
	   * String转为double类型
	   * @return  double
	   */
	public static  double stringTodouble(String str){
		double d = 0.00;
	    try {
	    	d = Double.parseDouble(str);
	    }catch (Exception e) {
	    	logger.error("DataTypeUtil stringTodouble Exception:", e);
	    }
	    return d;
	}
	//----------------------日期、时间相关转化函数--------------------------
	
	
	
	
    /**
     * 将 String型 转到 java.sql.Date 格式
     * @param      String 格式表示的日期
     * @return     java.sql.Date 格式表示的日期
     */
    public static Date stringToDate(String str){
    	DateFormat df = null;
    	if(str == null || str.trim().length()==0)
            return null;
    	 try{
             df = new SimpleDateFormat("yyyy-MM-dd");
             return df.parse(str);
         }catch(ParseException aioe){
        	 logger.error("DataTypeUtil stringToDate Exception:", aioe);
             return null;
         }
    }
	
    /**
     * 将String类型转为Calendar类型
     * @return  Calendar
     */
    public static Calendar stringToCalendar(String str) {
    	Date date = stringToDate(str);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	return cal;
    }

	
    /**
     * 将String类型转为Timestamp类型
     * param String("yyyy-MM-dd HH:mm:ss" 格式)
     * @return  Timestamp
     */
    public static Timestamp stringToTimestamp(String str) {
    	Timestamp sdate = null;
    	if (str != null) {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dt = df.parse(str);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				return new Timestamp(cal.getTimeInMillis());
			}catch (Exception e) {
				logger.error("DataTypeUtil stringToTimestamp Exception:", e);
			}
    	}
    	return sdate;
    }
    
    
	/**
     * 将日期格式从 java.util.Calendar 转到 java.sql.Timestamp 格式
     * @param date java.util.Calendar 格式表示的日期
     * @return     java.sql.Timestamp 格式表示的日期
     */
    public  static Timestamp calendarToTimestamp(Calendar cal){
        if(cal == null)
            return null;
        else
            return new Timestamp(cal.getTimeInMillis());
    }
    
    /**
     * 将Calendar转为String类型
     * @param date java.util.Calendar 格式表示的日期
     * @return  String("yyyy-MM-dd" 格式)
     */
    public static String calendarToString(Calendar cal) {
      String sdate = "";
      if (cal != null) {
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    	  sdate = df.format(cal.getTime());
      }
      return sdate;
    }
    
    /**
     * 将Calendar转为Date类型
     * @param date java.util.Calendar 格式表示的日期
     * @return  Date
     */
    public static Date calendarToDate(Calendar cal) {
     return  stringToDate(calendarToString(cal));
    }
    
    /**
     * 将Date转为String类型
     * @param date java.util.Calendar 格式表示的日期
     * @return  String("yyyy-MM-dd" 格式)
     */
    public static String dateToString(Date dt) {
      String sdate = "";
      if (dt != null) {
    	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
    	  sdate = df.format(dt);
      }
      return sdate;
    }
    
    /**
     * 将Date转为Calendar类型
     * @param date java.util.Calendar 格式表示的日期
     * @return  Calendar
     */
    public static Calendar dateToCalendar(Date dt) {
    	return stringToCalendar(dateToString(dt));
    }
    
    /**
     * 将Date转为Timestamp类型
     * @param date java.util.Calendar 格式表示的日期
     * @return  Timestamp
     */
    public static Timestamp dateToTimestamp(Date dt) {
    	return stringToTimestamp(dateToString(dt));
    }
    
    /**
     * timestamp转为Calendar类型
     * 默认使用 yyyy-MM-dd HH:mm:ss 格式
     * @return  Calendar
     */
    public static Calendar timestampToCalendar(Timestamp timestamp) {
    	return stringToCalendar(timestampToString(timestamp));
    }
    
    
    /**
     * timestamp转为Date类型
     * 默认使用 yyyy-MM-dd HH:mm:ss 格式
     * @return  Date
     */
    public static Date timestampToDate(Timestamp timestamp) {
    	return stringToDate(timestampToString(timestamp));
    }
    
    /**
     * timestamp转为String类型
     * 默认使用 yyyy-MM-dd HH:mm:ss 格式
     * @return  String("yyyy-MM-dd HH:mm:ss" 格式)
     */
    public static String timestampToString(Timestamp timestamp) {
      String rettime = "";
      if (timestamp != null) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rettime = formatter.format(timestamp);
      }
      return rettime;
    }
    
    /**
	    * 取得系统当前时间
	    * @return String yyyy-mm-dd hh:MM:ss
	    */
	   public static String getCurrentDateTime() {
	     Calendar rightNow = Calendar.getInstance();
	     return timestampToString(calendarToTimestamp(rightNow));
	   }

	   /**
	    * 取得系统当前时间
	    * @return String yyyy-mm-dd
	    */
	   public static String getCurrentDate() {
	     Calendar rightNow = Calendar.getInstance();
	     int year = rightNow.get(Calendar.YEAR);
	     int month = rightNow.get(Calendar.MONTH) + 1;
	     int day = rightNow.get(Calendar.DATE);
	     return year + "-" + month + "-" + day;
	   }
	   
    /**
     *  获取指定年月的最后一天
     * @param 传入 "yyyy-mm"的年月格式
     * 比如 获得2014年4月份的最大日期：getMaxDate("2014-04"); = "2014-04-30"
     * @return 输出某年月的最后一天的日期
     */
  
    public static Calendar getMaxDate(String str){
    	Calendar cal = null;
    	if(str == null || str.trim().length()==0)
            return null;
    	String dateStr = str+"-01";
    	cal = stringToCalendar(dateStr);
    	int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    	cal.set(Calendar.DAY_OF_MONTH,maxDay);
    	return cal; 
    }
    
    /**
     *  根据传入的String格式日期（yyyy-mm-dd），和 数字型天数（可为负值），计算天数
     *  比如计算"2014-4-30"的前5天 calendarCaculate("2014-4-30",-5)；
     *  比如计算"2014-4-30"的后5天 calendarCaculate("2014-4-30",5)；
     * @param 传入 "yyyy-mm"的年月格式
     * @param 传入 int型计算天数
     * @return 输出计算后日期
     */
   public static Calendar calendarCaculate(String strdate,int num){
	   Calendar cal = stringToCalendar(strdate);
	   cal.add(Calendar.DAY_OF_MONTH,num);
	   return cal;
   }
 
   /**
    *  根据传入的Calendar类型，和 数字型天数（可为负值），计算天数
    *  比如计算"2014-4-30"的前5天 calendarCaculate(calendar,-5)；
    *  比如计算"2014-4-30"的后5天 calendarCaculate(calendar,5)；
    * @param 传入 "yyyy-mm"的年月格式
    * @param 传入 int型计算天数
    * @return 输出计算后日期
    */
  public static Calendar calendarCaculate(Calendar calendar,int num){
	   calendar.add(Calendar.DAY_OF_MONTH,num);
	   return calendar;
  }
  
  
  /**
   *  根据传入的String类型 如：2014-01-01 ,2014-12-31,计算他们之间年月，返回包含年月的list
   *  比如计算2014-01-01 ,2014-12-31之间相差的月份
   *  caculateDateNum("2014-01-01","2014-12-31")；返回 list 包含了{201401、201402、201403、201404、201405、201406、201407、201401、201409、201410、201411、201412}
   * @param 传入 "yyyy-mm-dd"的年月日格式
   * @param 传入 "yyyy-mm-dd"的年月日格式
   * @return 输出返回包含年月为'yyyymm'格式的list
   */
  private ArrayList<String> caculateDateNum(String startDate,String endDate){
		ArrayList<String> loop = new ArrayList<String>();
		
		//获取查询年份
		String startYear = startDate.substring(0,4);
		String endYear   = endDate.substring(0,4);
		//获取查询月份
		String startMonth = startDate.substring(5,7);
		String endMonth   = endDate.substring(5,7);
		//计算月份数量,放入数组中
		if(Long.parseLong(startYear)== Long.parseLong(endYear)){
			long loopmonth = Long.parseLong(endMonth)- Long.parseLong(startMonth);
			for(int i=0;i<=loopmonth;i++){
				long tempmonth = Long.parseLong(startMonth) + i;
				if(tempmonth<10){
					loop.add(startYear+"0"+tempmonth);
				}else{
					loop.add(startYear+tempmonth);
				}
			}
		}
		
		if(Long.parseLong(startYear)< Long.parseLong(endYear)){
			final  int maxMonth = 12;
			final  int minMonth = 1;
			long loopyear = Long.parseLong(endYear)- Long.parseLong(startYear);
			for(int i=0;i<=loopyear;i++){
				String tempYear = String.valueOf(Long.parseLong(startYear)+i);
				if(tempYear.equals(startYear)){
					long loopmonth = maxMonth - Long.parseLong(startMonth);
					for(int j = 0;j<=loopmonth;j++){
						long tempmonth = Long.parseLong(startMonth)+j;
						if(tempmonth<10){
							loop.add(tempYear+"0"+tempmonth);
						}else{
							loop.add(tempYear+tempmonth);
						}
					}
				}else if(tempYear.equals(endYear)){
					long loopmonth = Long.parseLong(endMonth);
					for(int j=minMonth;j<=loopmonth;j++){
						long tempmonth = j;
						if(tempmonth<10){
							loop.add(tempYear+"0"+tempmonth);
						}else{
							loop.add(tempYear+tempmonth);
						}
					}
				}else{
					for(int j=minMonth;j<=maxMonth;j++){
						long tempmonth = j;
						if(tempmonth<10){
							loop.add(tempYear+"0"+tempmonth);
						}else{
							loop.add(tempYear+tempmonth);
						}
					}
				}
			}
		}
		return loop;
	}
  
  /** 
   * 取得当前日期所在周的第一天 
   * 
   * @param date 
   * @return 
   */ 
   public static Date getFirstDayOfWeek(Date date) { 
    Calendar c = new GregorianCalendar(); 
    c.setFirstDayOfWeek(Calendar.MONDAY); 
    c.setTime(date); 
    int m = c.get(Calendar.MONTH);
    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
    return c.getTime(); 
   } 
   
   /** 
    * 取得当前日期所在周的第一天 
    * 如果第一天的日期是上一个月的话，就把这周的第一天设成本月一号
    * @param date 
    * @return 
    */ 
    public static Date getFirstDayOfWeekInMonth(Date date) { 
     Calendar c = new GregorianCalendar(); 
     c.setFirstDayOfWeek(Calendar.MONDAY); 
     c.setTime(date); 
     int m = c.get(Calendar.MONTH);
     c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 

   //下面的代码作用是如果第一天的日期是上一个月的话，就把这周的第一天设成本月一号
     if(c.get(Calendar.MONTH) < m ){
   //   如果月份小于最初给定的月份了的话
      c.add(Calendar.MONTH, 1);//月份加1
      c.set(Calendar.DAY_OF_MONTH, 1);//日期改为1号
     }
     return c.getTime(); 
    } 

   /** 
   * 取得当前日期所在周的最后一天 
   * 
   * @param date 
   * @return 
   */ 
   public static Date getLastDayOfWeek(Date date) { 
    Calendar c = new GregorianCalendar(); 
    c.setFirstDayOfWeek(Calendar.MONDAY); 
    int m = c.get(Calendar.MONTH);
    c.setTime(date); 
    c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
    return c.getTime(); 
   } 
   
   /** 
    * 取得当前日期所在周的最后一天 
    * 如果最后一天的日期是下一个月的话，就把这周的最后一天设成本月一号最后一天
    * @param date 
    * @return 
    */ 
    public static Date getLastDayOfWeekInMonth(Date date) { 
     Calendar c = new GregorianCalendar(); 
     c.setFirstDayOfWeek(Calendar.MONDAY); 
     int m = c.get(Calendar.MONTH);
     c.setTime(date); 
     c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday

   //下面的代码作用是如果最后一天的日期是下一个月的话，就把这周的最后一天设成本月一号最后一天
     while(c.get(Calendar.MONTH) > m){
   //   如果月份大于了最初给定的月份了的话
      c.add(Calendar.DAY_OF_MONTH, -1);//日期循环减少一天，直到月份是本月为止
     }
     return c.getTime(); 
    } 

  /**把form对象转化成为Pojo对象
   * 
   * @param objform
   * @param objectPojo
   * @return BasePoJo
   */
//  public static BasePoJo formToPojo(Object objform,Object objectPojo){
//	  Method[] formMethods = objform.getClass().getMethods();
//	  Method[] pojoMethods = objectPojo.getClass().getMethods();
//	  for(int i=0;i<formMethods.length;i++){
//		  String formMethodName = formMethods[i].getName();
//		  if(formMethodName.startsWith("get")){
//			  try {
//				String formValue = (String)formMethods[i].invoke(formMethodName,null);
//				for(int j=0;j<pojoMethods.length;j++){
//					String pojoMethodName = pojoMethods[j].getName();
//					if(pojoMethodName.startsWith("set")){
//						if(formMethodName.substring(3).equals(pojoMethodName.substring(3))){
//							Class[] paramTypes = pojoMethods[j].getParameterTypes();
//							String paramTypeName = paramTypes[0].getName();
//							if("long".equals(paramTypeName)){
//							   pojoMethods[j].invoke(pojoMethodName,new Object[]{new Long(formValue)});
//							}
//							if("double".equals(paramTypeName)){
//							   pojoMethods[j].invoke(pojoMethodName,new Object[]{new Double(formValue)});
//							}
//						}
//					}
//				}
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		  }
//	  }
//	  return (BasePoJo)objectPojo;
//  }
}
