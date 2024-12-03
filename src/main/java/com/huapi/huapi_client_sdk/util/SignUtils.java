package com.huapi.huapi_client_sdk.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;


/**
 * 签名工具类
 */
public class SignUtils {
    public static String GenerateSign(String map, String secretKey){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String sign = map + secretKey;
        String result = md5.digestHex(sign);
        return result;
    }
}
