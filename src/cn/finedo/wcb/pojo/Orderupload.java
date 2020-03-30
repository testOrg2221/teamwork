package cn.finedo.wcb.pojo;

import cn.finedo.common.domain.BaseDomain;

public class Orderupload extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	//宣传任务
	private String propatask;
	
	//订单编号
	private String ordernum;
	
	//订单状态
	private String orderstate;
	
	//推广人员手机号码
	private String phonenum;
	
	//订单生成时间
	private String cretime;
	
	//订单生成时间
	private String cretimebeg;
	
	//订单生成时间
	private String cretimeend;
	
	//受理员工工号
	private String staffnum;
	
	//id
	private String orderid;

	public String getPropatask() {
		return propatask;
	}

	public void setPropatask(String propatask) {
		this.propatask = propatask;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getCretime() {
		return cretime;
	}

	public void setCretime(String cretime) {
		this.cretime = cretime;
	}

	public String getStaffnum() {
		return staffnum;
	}

	public void setStaffnum(String staffnum) {
		this.staffnum = staffnum;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCretimebeg() {
		return cretimebeg;
	}

	public void setCretimebeg(String cretimebeg) {
		this.cretimebeg = cretimebeg;
	}

	public String getCretimeend() {
		return cretimeend;
	}

	public void setCretimeend(String cretimeend) {
		this.cretimeend = cretimeend;
	}
	
}
