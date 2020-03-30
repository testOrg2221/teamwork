/**
 * 员工宣传海报表查询领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpubqrcode.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.wcb.pojo.Wcbpubqrcode;

public class WcbpubqrcodeQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	private Wcbpubqrcode wcbpubqrcode = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public Wcbpubqrcode getWcbpubqrcode() {
		return wcbpubqrcode;
	}

	public void setWcbpubqrcode(Wcbpubqrcode wcbpubqrcode) {
		this.wcbpubqrcode = wcbpubqrcode;
	}
}
