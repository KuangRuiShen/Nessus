package com.kuang.Nessus;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * @author krs 
 * @date 创建时间2017年11月6日 下午4:55:21 
 * @describe  用于nessus服务的 请求
 */
public class OkHttpUtils {

	/**
	 * 
	 *@describe post请求
	 *@param url
	 *@param param
	 *@param heardParam
	 *@return
	 */
	public static JSONObject post(String url,JSONObject param,Map<String,String> heardParam){
	        OkHttpClient client = new OkHttpClient(); 
	      //通过ssh验证
	        client.setSslSocketFactory(getSSLFactory());
	        client.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
            });        
	        String json = "";
	        Builder builder = new Builder();       
	        builder.add("content-type", "application/json;charset:utf-8");
	       //请求头的参数
	        if(heardParam != null){
	        	for(String headname :heardParam.keySet()){
	        		builder.add(headname,heardParam.get(headname));
	        	}
	        }                 
	        Request request = new Request.Builder()  
	                .url(url)
	                .headers(builder.build())
	                // 表单提交  
	                .post(RequestBody.create(  
	                        MediaType.parse("application/json; charset=utf-8"),  
	                        param.toJSONString()))// post json提交  
	                .build();         
	        try {  
	            Response response = client.newCall(request).execute();  
	            if (response.isSuccessful()) {  
	            	json =  response.body().string();    
	            }  
	        } catch (IOException e) {
	        	  e.printStackTrace();  
	        }
			return JSON.parseObject(json);  
	}
	
	/**
	 * 
	 *@describe put请求
	 *@param url
	 *@param param
	 *@param heardParam
	 *@return
	 */
	public static JSONObject put(String url,JSONObject param,Map<String,String> heardParam){
        OkHttpClient client = new OkHttpClient(); 
        client.setSslSocketFactory(getSSLFactory());
        client.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
        });        
        String json = "";
        Builder builder = new Builder();       
        builder.add("content-type", "application/json;charset:utf-8");
       //请求头的参数
        if(heardParam != null){
        	for(String headname :heardParam.keySet()){
        		builder.add(headname,heardParam.get(headname));
        	}
        }                 
        Request request = new Request.Builder()  
                .url(url)
                .headers(builder.build())        
                // 表单提交  
                .put(RequestBody.create(  
                        MediaType.parse("application/json; charset=utf-8"),  
                        param.toJSONString()))// post json提交  
                .build();         
        try {  
            Response response = client.newCall(request).execute();  
            if (response.isSuccessful()) {  
            	json =  response.body().string();    
            }  
        } catch (IOException e) {
        	  e.printStackTrace();  
        }
		return JSON.parseObject(json);  
}
	
	/**
	 * 
	 *@describe get请求
	 *@param url
	 *@param param
	 *@param heardParam
	 *@return
	 */
	public static JSONObject get(String url,Map<String,String> heardParam){
        OkHttpClient client = new OkHttpClient(); 
      //通过ssh验证
        client.setSslSocketFactory(getSSLFactory());
        client.setHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
        });        
        String json = "";
        Builder builder = new Builder();       
        builder.add("content-type", "application/json;charset:utf-8");
       //请求头的参数
        if(heardParam != null){
        	for(String headname :heardParam.keySet()){
        		builder.add(headname,heardParam.get(headname));
        	}
        }                 
        Request request = new Request.Builder()  
                .url(url)
                .headers(builder.build())      
                .build();         
        try {  
            Response response = client.newCall(request).execute();  
            if (response.isSuccessful()) {  
            	json =  response.body().string();    
            }  
        } catch (IOException e) {
        	  e.printStackTrace();  
        }
		return JSON.parseObject(json);  
}
	
	
	
	
	
	//获取ssh工厂
	private static SSLSocketFactory getSSLFactory(){
		try {
		 final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
             public void checkClientTrusted(
                     java.security.cert.X509Certificate[] chain,
                     String authType) throws CertificateException {
             }
    
             public void checkServerTrusted(
                     java.security.cert.X509Certificate[] chain,
                     String authType) throws CertificateException {
             }

             public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                 return null;
             }
         }};
		 
		 final SSLContext sslContext = SSLContext.getInstance("SSL");
         sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
         // Create an ssl socket factory with our all-trusting manager
         final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
         return sslSocketFactory;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}    
		return null;
	}
}
