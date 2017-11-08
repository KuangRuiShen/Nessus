package com.kuang.Nessus;
/**
 * @author krs
 * @date 创建时间2017年11月8日 下午12:02:27 
 * @describe 封装每个漏洞的outputs
 */
public class OutputDto {
	private String severity;
	private String plugin_output;
	//只存一个port
	private String port;
	//只存一个hostname;
	private String hostname;
	
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPlugin_output() {
		return plugin_output;
	}
	public void setPlugin_output(String plugin_output) {
		this.plugin_output = plugin_output;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	
	
}
