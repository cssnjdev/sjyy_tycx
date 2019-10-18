package com.cwks.bizcore.comm.utils;

import java.util.UUID;

/**
 * UUID生成类
 * <p>Title: UUIDGenerator.java</p>
 * <p>Description: UUID生成类 </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * <p>Company: cwks</p>
 * @author H.R
 * @version 1.0
 */
public class UUIDGenerator {

    String uuidstr;

    public UUIDGenerator() {
    }

    public String toString(){
        return uuidstr;
    }
    //获得36位UUID
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replaceAll("-","");
        return str;
    }

    //获得指定数量的UUID
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    public static void main(String[] args) {
        String[] ss = getUUID(10);
        for (int i = 0; i < ss.length; i++) {
            System.out.println("ss["+i+"]====="+ss[i]);
        }
    }
}
