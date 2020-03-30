/**
 * 员工宣传海报表列表领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpubqrcode.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.wcb.pojo.Wcbpubqrcode;

public class WcbpubqrcodeListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 员工宣传海报表list
	private List<Wcbpubqrcode> wcbpubqrcodelist=new ArrayList<Wcbpubqrcode>();

	public List<Wcbpubqrcode> getWcbpubqrcodelist() {
		return wcbpubqrcodelist;
	}

	public void setWcbpubqrcodelist(List<Wcbpubqrcode> wcbpubqrcodelist) {
		this.wcbpubqrcodelist = wcbpubqrcodelist;
	}
}
