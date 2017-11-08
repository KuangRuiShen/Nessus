package com.kuang.Nessus;

import java.util.List;

/**
 * @author krs
 * @date 创建时间2017年11月8日 上午11:53:05 
 * @describe 漏洞的详情
 */
public class LoopholeDetails {
	
	private String pluginid;
	private String pluginname;
	private String pluginfamily;
	//解决办法
	private String solution;
	//描述
	private String description;
	//风险等级
	private String severity;
	//输出
	List<OutputDto> outputs;
	
	public String getPluginid() {
		return pluginid;
	}
	public void setPluginid(String pluginid) {
		this.pluginid = pluginid;
	}
	public String getPluginname() {
		return pluginname;
	}
	public void setPluginname(String pluginname) {
		this.pluginname = pluginname;
	}
	public String getPluginfamily() {
		return pluginfamily;
	}
	public void setPluginfamily(String pluginfamily) {
		this.pluginfamily = pluginfamily;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public List<OutputDto> getOutputs() {
		return outputs;
	}
	public void setOutputs(List<OutputDto> outputs) {
		this.outputs = outputs;
	}
	@Override
	public String toString() {
		return "LoopholeDetails [pluginid=" + pluginid + ", pluginname=" + pluginname + ", pluginfamily=" + pluginfamily
				+ ", solution=" + solution + ", description=" + description + ", severity=" + severity + ", outputs="
				+ outputs + "]";
	}
	 
}
