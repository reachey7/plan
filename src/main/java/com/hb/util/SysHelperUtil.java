package com.hb.util;

import com.alibaba.fastjson.JSONObject;
import com.hb.entity.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysHelperUtil {

    public SysHelperUtil() {
    }



    /**
     * Description :判断关键字是否为空
     * @return String
     */
    public static R check(List<String> list , Map<String, Object> map)  {
        for(String  str : list){
            if(StringUtils.isEmpty(map.get(str))){
                return new R(false, str+"需要传入", "");
            }
        }
        return new R(true, "OK", "");
    }

    /**
     * <p>Description:调用activity</p>
     * <p>Company: 和邦科技</p>
     * @author lirc
     * @date 上午11:53:03
     */
    public static  R callActivity(JSONObject jsonObj, RestTemplate restTemplate, String url) {

        String eurekaServerString =  eurekaServerForJson("http://ACTIVITY-SERVER/"+url, jsonObj, restTemplate);
        // 判断是否调用成功
        try {
            JSONObject resultJson = JSONObject.parseObject(eurekaServerString);
            if ((boolean) resultJson.get("state")) {
                return new R(true, "成功!", resultJson.getString("result"));
            } else {
                return new R(false, "失败!", eurekaServerString);
            }
        } catch (Exception e) {
            return new R(false, "失败!", eurekaServerString);
        }
    }

    public static String eurekaServerForJson(String url, JSONObject jsonObj, RestTemplate rt){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        String result = "";
        try{
        	 result = rt.postForObject(url, formEntity, String.class);
        }catch (Exception e) {
			System.out.println("=============="+e.getMessage());
		}
        
        return result;
    }
}
