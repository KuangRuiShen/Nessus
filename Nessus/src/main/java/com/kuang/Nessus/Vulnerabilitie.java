package com.kuang.Nessus;
/**
 * @author krs
 * @date 创建时间2017年11月7日 下午3:23:06 
 * @describe 漏洞的实体
 */
public class Vulnerabilitie {
	private String plugin_id;
	private String plugin_name;
	private String plugin_family;
	private String count;
	private String severity;
	private String severity_index;
	private String vuln_index;
	public String getPlugin_id() {
		return plugin_id;
	}
	public void setPlugin_id(String plugin_id) {
		this.plugin_id = plugin_id;
	}
	public String getPlugin_name() {
		return plugin_name;
	}
	public void setPlugin_name(String plugin_name) {
		this.plugin_name = plugin_name;
	}
	public String getPlugin_family() {
		return plugin_family;
	}
	public void setPlugin_family(String plugin_family) {
		this.plugin_family = plugin_family;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getSeverity_index() {
		return severity_index;
	}
	public void setSeverity_index(String severity_index) {
		this.severity_index = severity_index;
	}
	public String getVuln_index() {
		return vuln_index;
	}
	public void setVuln_index(String vuln_index) {
		this.vuln_index = vuln_index;
	}
	@Override
	public String toString() {
		return "Vulnerabilitie [plugin_id=" + plugin_id + ", plugin_name=" + plugin_name + ", plugin_family="
				+ plugin_family + ", count=" + count + ", severity=" + severity + ", severity_index=" + severity_index
				+ ", vuln_index=" + vuln_index + "]";
	}
	

}
