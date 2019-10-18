package com.cwks.bizcore.comm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class ObjectAndByte {

    private static Logger logger = LoggerFactory.getLogger(ObjectAndByte.class);
    /**
     * 对象转数组
     *
     * @param obj
     * @return
     */
    public byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            logger.error("ObjectAndByte toByteArray error:",ex);
        }
        return bytes;
    }

    /**
     * 数组转对象
     *
     * @param bytes
     * @return
     */
    public Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            logger.error("ObjectAndByte toObject error1:",ex);
        } catch (ClassNotFoundException ex) {
            logger.error("ObjectAndByte toObject error2:",ex);
        }
        return obj;
    }

    public static final InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static final byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static void main(String[] args) {
        Map tb = new HashMap();
        tb.put("daqing", "123");
        tb.put("1234567890", "123");

        ObjectAndByte oa = new ObjectAndByte();
        byte[] b = oa.toByteArray(tb);
        System.out.println(new String(b));

        System.out.println("=======================================");

        Map teb = (HashMap) oa.toObject(b);
        System.out.println(teb.get("daqing"));
        System.out.println(teb.get("1234567890"));
    }

}  