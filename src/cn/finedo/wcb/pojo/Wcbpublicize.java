/*
 *宣传海报表
 *
 *@version:1.0
 *@company:finedo.cn
 */
package cn.finedo.wcb.pojo;

import cn.finedo.common.domain.BaseDomain;

public class Wcbpublicize extends BaseDomain {
	private static final long serialVersionUID = 1L;

	//宣传ID
	private String publicizeid;

	//工单ID
	private String taskid;

	//宣传类型
	private String publicizetype;

	//宣传描述
	private String publicizedesc;

	//宣传图片
	private String publicizepic;

	//新增时间
	private String addtime;

	//新增时间
	private String addtimebegin;

	//新增时间
	private String addtimeend;

	//新增人
	private String adduser;

	//操作时间
	private String optime;

	//操作时间
	private String optimebegin;

	//操作时间
	private String optimeend;

	//操作人
	private String opuser;

	//工单ID 名称
	private String taskname;
	
	//工单描述
	private String taskdesc;
	
	//文件类型
	private String filetype;
	
	//文件名称
	private String filename;
	
	//文件路径
	private String filepath;
	
	//文件id
	private String fileid;
	
	//上传海报全路径,目录从项目目录开始
	private String fileFullPath;
	
	//上传海报全路径,目录从根目录开始,和数据库配置保存一致
	private String rootpath;

	//扫描积分
	private String scanintegral;
	
	//办理积分
	private String transactintegral;
	
	//当前用户
	private String userid;
	
	//当前用户电话号码
	private String phonenum;
	
	//扫描总积分
	private String totlescanintegralnum;
	
	//办理总积分
	private String totletransactintegralnum;
	
	//个人总积分
	private String codenum;
	
	//排名
	private String rowno;
	
	public void setPublicizeid(String publicizeid) {
		this.publicizeid = publicizeid;
	}

	public String getPublicizeid() {
		return this.publicizeid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getTaskid() {
		return this.taskid;
	}

	public void setPublicizetype(String publicizetype) {
		this.publicizetype = publicizetype;
	}

	public String getPublicizetype() {
		return this.publicizetype;
	}

	public void setPublicizedesc(String publicizedesc) {
		this.publicizedesc = publicizedesc;
	}

	public String getPublicizedesc() {
		return this.publicizedesc;
	}

	public void setPublicizepic(String publicizepic) {
		this.publicizepic = publicizepic;
	}

	public String getPublicizepic() {
		return this.publicizepic;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtimebegin(String addtimebegin) {
		this.addtimebegin = addtimebegin;
	}

	public String getAddtimebegin() {
		return this.addtimebegin;
	}

	public void setAddtimeend(String addtimeend) {
		this.addtimeend = addtimeend;
	}

	public String getAddtimeend() {
		return this.addtimeend;
	}

	public void setAdduser(String adduser) {
		this.adduser = adduser;
	}

	public String getAdduser() {
		return this.adduser;
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

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public String getRootpath() {
		return rootpath;
	}

	public void setRootpath(String rootpath) {
		this.rootpath = rootpath;
	}

	public String getTaskdesc() {
		return taskdesc;
	}

	public void setTaskdesc(String taskdesc) {
		this.taskdesc = taskdesc;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTotlescanintegralnum() {
		return totlescanintegralnum;
	}

	public void setTotlescanintegralnum(String totlescanintegralnum) {
		this.totlescanintegralnum = totlescanintegralnum;
	}

	public String getTotletransactintegralnum() {
		return totletransactintegralnum;
	}

	public void setTotletransactintegralnum(String totletransactintegralnum) {
		this.totletransactintegralnum = totletransactintegralnum;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getCodenum() {
		return codenum;
	}

	public void setCodenum(String codenum) {
		this.codenum = codenum;
	}

	public String getRowno() {
		return rowno;
	}

	public void setRowno(String rowno) {
		this.rowno = rowno;
	}


	
	
}
