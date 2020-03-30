package cn.finedo.wcb.service.wcborderupload.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.wcb.pojo.Orderupload;

public class OrderuploadQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	private Orderupload orderupload = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public Orderupload getOrderupload() {
		return orderupload;
	}

	public void setOrderupload(Orderupload orderupload) {
		this.orderupload = orderupload;
	}
	
}
