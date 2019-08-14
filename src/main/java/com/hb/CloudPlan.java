package com.hb;

import com.alibaba.fastjson.JSONObject;
import com.hb.util.PlugDateUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author lina
 */
public class CloudPlan {
	/**
	* <p>Description: </p>
	*/
	public static void main(String[] args) throws Exception {

		post(planAdd());
		
		//post(planQuery());

		//post(planStart());

		//post(studentSubmit());
	}
 
	public static void post(JSONObject jsonObject) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String url = jsonObject.getString("url");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
		StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		String json2 = EntityUtils.toString(response.getEntity(), "utf-8");

		System.out.println(json2);
	}


	public static JSONObject planAdd() {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("planCode","planCode"); 
		jsonParam.put("planName","planName");
		jsonParam.put("partitionNumber",5);
		jsonParam.put("distributeNumber",6);
		jsonParam.put("createTime", PlugDateUtil.getCurrentDate());
		jsonParam.put("createPersonId","createPersonId");
		jsonParam.put("createPersonName","createPersonName");
		jsonParam.put("description","description");
		jsonParam.put("planStatus",1);
		jsonParam.put("planStuNumber",2);
		jsonParam.put("hfOverNumber",3);
		jsonParam.put("fpOverNumber",4);
		jsonParam.put("currentPersonId","currentPersonId");
		jsonParam.put("currentPersonName","currentPersonName");
		jsonParam.put("stuType","stuType");
		jsonParam.put("url", "http://localhost:8083/dormitoryPlan/add");
		return jsonParam;
	}
	public static JSONObject planQuery() {
		JSONObject jsonParam = new JSONObject();

		jsonParam.put("id","1908062004425999697");
		jsonParam.put("planCode","planCode");
		jsonParam.put("planName","planName");
		jsonParam.put("current",0);
		jsonParam.put("size",1);
		jsonParam.put("startTime", "2019-01-01");
		jsonParam.put("endTime", "2019-11-01");
		jsonParam.put("stuType","stuType");
		jsonParam.put("url", "http://localhost:8083/dormitoryPlan/query");
		return jsonParam;
	}

	public static JSONObject planStart() {
		JSONObject jsonParam = new JSONObject();

		jsonParam.put("planId","1908062004425999697");
		jsonParam.put("currentPersonId","reachey");
		jsonParam.put("nextPersonId","lirc");
		jsonParam.put("currentPersonName","reachey");
		jsonParam.put("nextPersonName","lirc");
		jsonParam.put("url", "http://localhost:8083/dormitoryPlan/planSubmit");
		return jsonParam;
	}

	public static JSONObject studentSubmit() {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("planId","1908062004425999697");
		jsonParam.put("activityId","40001");
		jsonParam.put("nextPersonId","sutdent");
		jsonParam.put("nextPersonName","sutdent");
		jsonParam.put("url", "http://localhost:8083/dormitoryPart/studentSubmit");
		return jsonParam;
	}
}
