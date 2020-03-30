/**
 * 员工二维码表列表领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbqrcode.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.wcb.pojo.Wcbqrcode;

public class WcbqrcodeListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 员工二维码表list
	private List<Wcbqrcode> wcbqrcodelist=new ArrayList<Wcbqrcode>();

	public List<Wcbqrcode> getWcbqrcodelist() {
		return wcbqrcodelist;
	}

	public void setWcbqrcodelist(List<Wcbqrcode> wcbqrcodelist) {
		this.wcbqrcodelist = wcbqrcodelist;
	}
}
