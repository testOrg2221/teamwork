/**
 * 宣传海报表列表领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbpublicize.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.wcb.pojo.Wcbpublicize;

public class WcbpublicizeListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 宣传海报表list
	private List<Wcbpublicize> wcbpublicizelist=new ArrayList<Wcbpublicize>();

	public List<Wcbpublicize> getWcbpublicizelist() {
		return wcbpublicizelist;
	}

	public void setWcbpublicizelist(List<Wcbpublicize> wcbpublicizelist) {
		this.wcbpublicizelist = wcbpublicizelist;
	}
}
