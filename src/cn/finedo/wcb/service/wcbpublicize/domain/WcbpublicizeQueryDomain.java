/**
 * 宣传海报表查询领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpublicize.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.wcb.pojo.Wcbpublicize;

public class WcbpublicizeQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	private Wcbpublicize wcbpublicize = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public Wcbpublicize getWcbpublicize() {
		return wcbpublicize;
	}

	public void setWcbpublicize(Wcbpublicize wcbpublicize) {
		this.wcbpublicize = wcbpublicize;
	}
}
