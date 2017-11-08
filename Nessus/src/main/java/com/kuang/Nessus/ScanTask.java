package com.kuang.Nessus;
/**
 * @author krs
 * @date 创建时间2017年11月7日 下午4:50:31 
 * @describe 扫描的任务的实体
 */
public class ScanTask {
	//任务的id
	private String id;
	//任务名
	private String name;
	//任务的状态
	private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ScanTask [id=" + id + ", name=" + name + ", status=" + status + "]";
	}
	
}
