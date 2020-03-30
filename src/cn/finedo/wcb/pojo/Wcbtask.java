/*
 *工单表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.wcb.pojo;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.common.domain.BaseDomain;

public class Wcbtask extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//任务ID
	private String taskid;

	//任务名称
	private String taskname;

	//工单描述
	private String taskdesc;

	//操作时间
	private String optime;

	//操作时间
	private String optimebegin;

	//操作时间
	private String optimeend;

	//操作人
	private String opuser;

	List<Wcbpublicize> wcbpublicizelist=new ArrayList<Wcbpublicize>();
	
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
	}

	public String getTaskdesc() {
		return this.taskdesc;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getOptime() {
		return this.optime;
	}

	public void setOptimebegin(String optimebegin) {
		this.optimebegin = optimebegin;
	}

	public String getOptimebegin() {
		return this.optimebegin;
	}

	public void setOptimeend(String optimeend) {
		this.optimeend = optimeend;
	}

	public String getOptimeend() {
		return this.optimeend;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public String getOpuser() {
		return this.opuser;
	}

	public List<Wcbpublicize> getWcbpublicizeList() {
		return wcbpublicizelist;
	}

	public void setWcbpublicizeList(List<Wcbpublicize> wcbpublicizeList) {
		wcbpublicizelist = wcbpublicizeList;
	}

	
	
}
