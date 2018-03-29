package com.roy.wenda.wendaUtil;





import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPObject;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;


public class WendaUtil {
    private static final Logger logger = LoggerFactory.getLogger(WendaUtil.class);

    public static int ANONYMOUS_USERID = 3;
    public static int SYSTEM_USERID = 4;


    public static String getJSONString(int code){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        return JSON.toJSONString(map);
    }

    public static String getJSONString(int code,String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("msg",msg);
        return JSON.toJSONString(map);
    }

    public static String MD5(String key){
        char hexDigits[] = {
                '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F'
        };
        try{
            byte[] btInput = key.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j*2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0>>>4&0xf];
                str[k++] = hexDigits[byte0&0xf];
            }
            return new String(str);
        }catch (Exception e){
            logger.error("生成MD5失败",e);
            return null;
        }
    }
}
