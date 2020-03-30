/**
 * 工单表查询领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbtask.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.wcb.pojo.Wcbtask;

public class WcbtaskQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	private Wcbtask wcbtask = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public Wcbtask getWcbtask() {
		return wcbtask;
	}

	public void setWcbtask(Wcbtask wcbtask) {
		this.wcbtask = wcbtask;
	}
}
