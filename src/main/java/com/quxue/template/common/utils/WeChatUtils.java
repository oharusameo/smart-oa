package com.quxue.template.common.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.quxue.template.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


@Component
@Data
@Slf4j
public class WeChatUtils {
    @Value("${weixin.app-id}")
    private String appId;
    @Value("${weixin.app-secret}")
    private String appSecret;
    @Value("${weixin.auth-url}")
    private String url;

    //    private String getAccessTokenUrl = null;
    public String getOpenId(String jsCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", jsCode);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject entries = JSONUtil.parseObj(response);
        log.info(entries.toString());
        String openid = (String) entries.get("openid");
        if (openid == null || openid.length() == 0) {
            throw new BusinessException("微信临时授权码错误");
        }
        return openid;
/*        getAccessTokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=authorization_code";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(getAccessTokenUrl).build();

        String openId = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String respParam = response.body().string();
                WeChatAccountDTO bean = JSONUtil.toBean(respParam, WeChatAccountDTO.class);
                openId = bean.getOpenid();
            }
            return openId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }


    public String getUserInfo(String registerCode, String weixinCode) {
        InputStream inputStream = null;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.connect();

            inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            connection.disconnect();

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
