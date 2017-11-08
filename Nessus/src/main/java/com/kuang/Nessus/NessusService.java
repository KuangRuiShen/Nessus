package com.kuang.Nessus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author krs
 * @date 创建时间2017年11月6日 下午1:58:56 
 * @describe nessusService服务器
 */
public class NessusService {
	
	public static final String URL = "https://192.168.248.136:8834";	
	public static final String RESUME = "/resume";//恢复
	public static final String PAUSE = "/pause";//停止
	public static final String LAUNCH="/launch";//扫描
	public static final String QUERY_SCAN_ID="/editor/scan/templates";//扫描的任务id
	
	
	public static void main(String[] args) {
	
		//获取token
		Map<String,String> heardParam = init();
//		startUp(37+"", heardParam);
		Map<String, Object> data = getVulnerabilities("37", heardParam);
//		System.out.println(data);
		queryByPluginId("37", "10386", (String)data.get("history_id"), heardParam);
		int time = 1510128016;
		//查询所有的扫描		
//		JSONObject his= OkHttpUtils.get(URL+"/scans/25/hosts/2?history_id=27",heardParam);
//		System.out.println(his);
//		List<ScanTask> scanTasks= queryTask(heardParam);
//		System.out.println(scanTasks.toString());
//		getVulnerabilities(25+"", heardParam);
	}
	
	//加载token
	private static Map<String,String> init(){
		JSONObject user = new JSONObject();
		user.put("username", "admin");
		user.put("password", "admin");	
		Map<String,String> heardParam = new HashMap<String, String>();
		try {
			String token = OkHttpUtils.post(URL+"/session", user, null).getString("token");	
			heardParam.put("X-Cookie", "token="+token);	
		} catch (Exception e) {
			System.out.println("nessus服务器链接失败");
		}
		
		//获取secretKey,accessKey
//		JSONObject keys = OkHttpUtils.put(URL+"/session/keys", user, heardParam);
//		String secretKey = keys.getString("secretKey");
//		String accessKey = keys.getString("accessKey");
//		heardParam.put("X-ApiKeys", "secretKey="+secretKey+";"+"accessKey="+accessKey);	
		return heardParam;
	}
	
	@SuppressWarnings("unchecked")
	private static List<ScanTask> queryTask(Map<String,String> heardParam){
		JSONObject scans= OkHttpUtils.get(URL+"/scans?folder_id=3",heardParam);
		JSONArray dates = scans.getJSONArray("scans");
		List<ScanTask> tasks = new ArrayList<ScanTask>();
		ListIterator<Object> s = dates.listIterator();
			while(s.hasNext()){
				Map<String,Object> o = (Map<String,Object>) s.next();
				ScanTask st = new ScanTask();
				st.setId(o.get("id")+"");
				st.setName((String)o.get("name"));
				st.setStatus((String)o.get("status"));	
				tasks.add(st);
			}
		return tasks;
	}
	
	//创建一个json数据,用于修改和新增任务
	@SuppressWarnings("unused")
	private static JSONObject getObject(){
		JSONObject scan = new JSONObject();
//		scan.put("uuid", "ad629e16-03b6-8c1d-cef6-ef8c9dd3c658d24bd260ef5f9e66");//Advanced Scan
		scan.put("uuid", "c3cbcd46-329f-a9ed-1077-554f8c2af33d0d44f09d736969bf");//Web Application Tests
		JSONObject settings = new JSONObject();
		settings.put("name", "192.168.6.73");
		settings.put("description", "192.168.6.73");
		settings.put("enabled", "false");
		settings.put("launch_now", "true");
		settings.put("folder_id", "3");
		settings.put("text_targets", "192.168.6.73");
		scan.put("settings", settings);	
		return scan;
	}
	
	//添加任务
	private static void addTask(Map<String,String> heardParam,JSONObject scan){	
		OkHttpUtils.post(URL+"/scans",scan,heardParam);
	}
	//修改任务
	private static void updateTask(String id,Map<String,String> heardParam,JSONObject scan){	
		OkHttpUtils.put(URL+"/scans/"+id,scan,heardParam);	
	}

	//启动扫描
	private static void startUp(String id,Map<String,String> heardParam){
		OkHttpUtils.post(URL+"/scans/"+id+LAUNCH, new JSONObject(), heardParam);
	}
	
	//获取扫描的漏洞
	@SuppressWarnings("unchecked")
	private static Map<String, Object> getVulnerabilities(String id,Map<String,String> heardParam){
		Map<String,Object> dates = new HashMap<String, Object>();
		JSONObject scans = OkHttpUtils.get(URL+"/scans/"+id, heardParam);
		//获取当前的history_id
		JSONArray historys = scans.getJSONArray("history");
		Map<String,Object> his = (Map<String, Object>) historys.get(historys.size()-1);
		//hisId
		String history_id = his.get("history_id")+"";
		dates.put("history_id", history_id);
		//创建时间
		String bTime = his.get("creation_date")+"";
		dates.put("bTime",bTime);
		//结束时间
		String eTime = his.get("last_modification_date")+"";
		String status = his.get("last_modification_date")+"";
		dates.put("status",status);
		if(status.equals("completed")){
			dates.put("eTime",eTime);
		}	
		//获取查询的漏洞详情
		List<Vulnerabilitie> vulnerabilities = JSON.parseArray(scans.getString("vulnerabilities"), Vulnerabilitie.class);
		dates.put("vulnerabilities", vulnerabilities);
		return dates;
	}
	
	//查询该漏洞的信息
	public static LoopholeDetails queryByPluginId(String id,String pid,String hid,Map<String,String> heardParam){
		//漏洞的详情
		LoopholeDetails ld = new LoopholeDetails();
		
		JSONObject scans= OkHttpUtils.get(URL+"/scans/"+id+"/plugins/"+pid+"?history_id="+hid,heardParam);
		JSONArray outputs = scans.getJSONArray("outputs");	
		//封装所有的端口信息
		List<OutputDto> outputsDates = new ArrayList<OutputDto>();
		for(Object output :outputs){	
			OutputDto outputdto = new OutputDto();
			Map<String,Object> op = (Map<String,Object>)output;		
			outputdto.setSeverity(op.get("severity")+"");
			outputdto.setPlugin_output((String) op.get("plugin_output"));			
			Map<String ,Object> ports = (Map<String,Object>) op.get("ports");						
			for(String port:ports.keySet()){	
				outputdto.setHostname(ports.get(port)+"");
				outputdto.setPort(port);
			}
			outputsDates.add(outputdto);
		}
		ld.setOutputs(outputsDates);
		//封装主要信息
		JSONObject plug = scans.getJSONObject("info").getJSONObject("plugindescription");			
		ld.setPluginfamily((String)plug.get("pluginfamily"));
		
		ld.setSeverity(plug.get("severity")+"");
		ld.setPluginname((String)plug.get("pluginname"));
		ld.setPluginid((String)plug.get("pluginid"));		
		//描述
		JSONObject  plugbutes = plug.getJSONObject("pluginattributes");
		ld.setDescription((String)plugbutes.get("description"));
		//解决办法
		String solution = (String) plugbutes.get("solution");
		if(solution != null){
			ld.setSolution(solution); 	
		}
		System.out.println(ld.toString());
		return ld;
	}
	
}
