package com.huapi.huapi_client_sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.huapi.huapi_client_sdk.model.TestUser;
import com.huapi.huapi_client_sdk.util.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HuApiClient {
    private static final String GATEWAY_HOST = "http://localhost:8072/";
    private String accessKey;
    private String secretKey;
    public HuApiClient(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }
    public String getNameByGet(String name){
        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("name", name);
        String result= HttpRequest.get(GATEWAY_HOST + "test").addHeaders(getHeaderMap(name)).form(paramMap).execute().body();
        return result;
    }
    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("name", name);
        String result= HttpRequest.post(GATEWAY_HOST + "test").addHeaders(getHeaderMap(name)).form(paramMap).execute().body();
        return result;
    }
    //md5加密
    public String generateSign(Map<String,String> map){
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String sign = StrUtil.toString(map) + secretKey;
        String result = md5.digestHex(sign);
        return result;
    }
    public Map<String,String> getHeaderMap(String content){
        Map<String,String> map = new HashMap<String,String>();
        map.put("accessKey",accessKey);
        map.put("content",content);
        map.put("nounce", RandomUtil.randomNumbers(4));
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign",SignUtils.GenerateSign(content,secretKey));
        return map;
    }
    public TestUser getUserNameByPost(TestUser user){
        String str = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(GATEWAY_HOST + "test/user").addHeaders(getHeaderMap(str)).body(str).execute().body();
        TestUser testUser = JSONUtil.toBean(result, TestUser.class);
        return testUser;
    }

}
