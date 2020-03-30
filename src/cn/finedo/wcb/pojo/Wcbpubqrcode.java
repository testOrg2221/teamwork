/*
 *员工宣传海报表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.wcb.pojo;

import cn.finedo.common.domain.BaseDomain;

public class Wcbpubqrcode extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//宣传海报ID
	private String publicizeid;

	//二维码图片存放表的ID
	private String qrcodeid;

	//工单id
	private String taskid;

	//与海报合成的二维码的员工工号
	private String userid;

	//合成的图片
	private String picture;

	//操作人
	private String opuser;

	//操作时间
	private String opdate;

	//操作时间
	private String opdatebegin;

	//操作时间
	private String opdateend;

	//宣传海报ID 名称
	private String publicizename;

	//二维码ID 名称
	private String qrcodename;

	//工单id 名称
	private String taskname;

	//员工工号 名称
	private String username;
	
	//二维码存放图片路径
	private String qrcodefilepath;
	
	//二维码图片类型
	private String qrcodefiletype;
	
	//二维码图片存放表的ID
	private String qrcodefileid;
	
	//宣传海报类型
	private String publicizetype;

	//宣传海报描述
	private String publicizedesc;

	public void setPublicizeid(String publicizeid) {
		this.publicizeid = publicizeid;
	}

	public String getPublicizeid() {
		return this.publicizeid;
	}

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

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setOpuser(String opuser) {
		this.opuser = opuser;
	}

	public String getOpuser() {
		return this.opuser;
	}

	public void setOpdate(String opdate) {
		this.opdate = opdate;
	}

	public String getOpdate() {
		return this.opdate;
	}

	public void setOpdatebegin(String opdatebegin) {
		this.opdatebegin = opdatebegin;
	}

	public String getOpdatebegin() {
		return this.opdatebegin;
	}

	public void setOpdateend(String opdateend) {
		this.opdateend = opdateend;
	}

	public String getOpdateend() {
		return this.opdateend;
	}

	public void setPublicizename(String publicizename) {
		this.publicizename = publicizename;
	}

	public String getPublicizename() {
		return this.publicizename;
	}

	public void setQrcodename(String qrcodename) {
		this.qrcodename = qrcodename;
	}

	public String getQrcodename() {
		return this.qrcodename;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTaskname() {
		return this.taskname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public String getQrcodefilepath() {
		return qrcodefilepath;
	}

	public void setQrcodefilepath(String qrcodefilepath) {
		this.qrcodefilepath = qrcodefilepath;
	}

	public String getQrcodefiletype() {
		return qrcodefiletype;
	}

	public void setQrcodefiletype(String qrcodefiletype) {
		this.qrcodefiletype = qrcodefiletype;
	}

	public String getQrcodefileid() {
		return qrcodefileid;
	}

	public void setQrcodefileid(String qrcodefileid) {
		this.qrcodefileid = qrcodefileid;
	}

	public String getPublicizetype() {
		return publicizetype;
	}

	public void setPublicizetype(String publicizetype) {
		this.publicizetype = publicizetype;
	}

	public String getPublicizedesc() {
		return publicizedesc;
	}

	public void setPublicizedesc(String publicizedesc) {
		this.publicizedesc = publicizedesc;
	}

	
	
	
	
}
