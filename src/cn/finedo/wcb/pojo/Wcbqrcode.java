/*
 *员工二维码表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.wcb.pojo;

import cn.finedo.common.domain.BaseDomain;

public class Wcbqrcode extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//二维码ID
	private String qrcodeid;

	//工单id
	private String taskid;

	//海报ID
	private String publicizeid;

	//二维码图片
	private String qrcodepic;

	//员工编号
	private String userid;

	//操作时间
	private String optime;

	//操作时间
	private String optimebegin;

	//操作时间
	private String optimeend;

	//操作人
	private String opuser;

	//工单id 名称
	private String taskname;

	//海报ID 名称
	private String publicizename;

	//员工编号 名称
	private String username;
	
	//二维码解析后的url
	private String urlpath;
	
	//二维码扫描次数
	private String codenum;
	
	//手机号
	private String phone;
	
	//主键id
	private String cid;
	
	//扫描积分
	private String scanintegral;
	
	//办理积分
	private String transactintegral;

	public void setQrcodeid(String qrcodeid) {
		this.qrcodeid = qrcodeid;
	}

	public String getQrcodeid() {
		return this.qrcodeid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setPublicizeid(String publicizeid) {
		this.publicizeid = publicizeid;
	}

	public String getPublicizeid() {
		return this.publicizeid;
	}

	public void setQrcodepic(String qrcodepic) {
		this.qrcodepic = qrcodepic;
	}

	public String getQrcodepic() {
		return this.qrcodepic;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return this.userid;
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

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setPublicizename(String publicizename) {
		this.publicizename = publicizename;
	}

	public String getPublicizename() {
		return this.publicizename;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public String getUrlpath() {
		return urlpath;
	}

	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}

	public String getCodenum() {
		return codenum;
	}

	public void setCodenum(String codenum) {
		this.codenum = codenum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getScanintegral() {
		return scanintegral;
	}

	public void setScanintegral(String scanintegral) {
		this.scanintegral = scanintegral;
	}

	public String getTransactintegral() {
		return transactintegral;
	}

	public void setTransactintegral(String transactintegral) {
		this.transactintegral = transactintegral;
	}

	
	
}
