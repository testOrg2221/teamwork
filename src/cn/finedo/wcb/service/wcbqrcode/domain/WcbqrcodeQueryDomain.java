/**
 * 员工二维码表查询领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbqrcode.domain;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.common.domain.PageParamDomain;
import cn.finedo.wcb.pojo.Wcbqrcode;

public class WcbqrcodeQueryDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 分页信息
	private PageParamDomain pageparam = null;
	
	private Wcbqrcode wcbqrcode = null;

	public PageParamDomain getPageparam() {
		return pageparam;
	}

	public void setPageparam(PageParamDomain pageparam) {
		this.pageparam = pageparam;
	}

	public Wcbqrcode getWcbqrcode() {
		return wcbqrcode;
	}

	public void setWcbqrcode(Wcbqrcode wcbqrcode) {
		this.wcbqrcode = wcbqrcode;
	}
}
