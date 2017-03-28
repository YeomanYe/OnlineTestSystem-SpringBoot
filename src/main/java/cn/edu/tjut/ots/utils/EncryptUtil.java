package cn.edu.tjut.ots.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by KINGBOOK on 2017/3/28.
 */
public class EncryptUtil {

    /**
     * md5单向加密
     * @param message
     * @return
     */
    public static String MD5(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        //更新摘要
        md.update(message.getBytes());
        //完成计算得到结果
        return md.digest().toString();
    }
}
