package com.huapi.huapi_client_sdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.huapi.huapi_client_sdk.model.TestUser;
import com.huapi.huapi_client_sdk.util.SignUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HuApiClient {
    private static final String GATEWAY_HOST = "http://localhost:8072/";
    private static final String HEAD_INFO = "huapi";
    private String accessKey;
    private String secretKey;

    /**
     * 传入secretKey和accessKey
     * @param accessKey
     * @param secretKey
     */
    public HuApiClient(String accessKey, String secretKey){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 传入name，通过get请求获取用户信息
     * @param name
     * @return
     */
    public String getNameByGet(String name){
        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("name", name);
        paramMap.put("info", HEAD_INFO);
        String result= HttpRequest.get(GATEWAY_HOST + "test").addHeaders(getHeaderMap(name)).form(paramMap).execute().body();
        return result;
    }

    /**
     * 传入name，通过post请求获取用户信息
     * @param name
     * @return
     */
    public String getNameByPost(String name){
        HashMap<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("name", name);
        paramMap.put("info", HEAD_INFO);
        String result= HttpRequest.post(GATEWAY_HOST + "test").addHeaders(getHeaderMap(name)).form(paramMap).execute().body();
        return result;
    }

    /**
     * 传入要传递的内容，构造请求头
     * @param content
     * @return
     */
    public Map<String,String> getHeaderMap(String content){
        Map<String,String> map = new HashMap<String,String>();
        map.put("info", HEAD_INFO);
        map.put("accessKey",accessKey);
        map.put("content",content);
        map.put("nounce", RandomUtil.randomNumbers(4));
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign",SignUtils.GenerateSign(content,secretKey));
        return map;
    }

    /**
     * 通过post请求获取user
     * @param user
     * @return
     */
    public TestUser getUserNameByPost(TestUser user){
        String str = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(GATEWAY_HOST + "test/user").addHeaders(getHeaderMap(str)).body(str).execute().body();
        TestUser testUser = JSONUtil.toBean(result, TestUser.class);
        return testUser;
    }

    /**
     * 通过get请求获取bilibili热门top信息
     * @param top
     * @return
     */
    public String getBiliBiliTop(Integer top){
        HashMap<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("top", top);
        paramMap.put("info", HEAD_INFO);
        String result= HttpRequest.get(GATEWAY_HOST + "bilibili").addHeaders(getHeaderMap(String.valueOf(top))).form(paramMap).execute().body();
        return result;
    }

    /**
     * 通过get请求获取今日头条热门top信息
     * @return
     */
    public String getToutiao(){
        String result= HttpRequest.get(GATEWAY_HOST + "toutiao").addHeaders(getHeaderMap("toutiao")).execute().body();
        return result;
    }
}
