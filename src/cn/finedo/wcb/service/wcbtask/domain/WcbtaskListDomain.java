/**
 * 工单表列表领域对象
 *
 * @version 1.0
 * @since 2018-11-30
 */
package cn.finedo.wcb.service.wcbtask.domain;

import java.util.List;
import java.util.ArrayList;
import cn.finedo.common.domain.BaseDomain;
import cn.finedo.wcb.pojo.Wcbtask;

public class WcbtaskListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 工单表list
	private List<Wcbtask> wcbtasklist=new ArrayList<Wcbtask>();

	public List<Wcbtask> getWcbtasklist() {
		return wcbtasklist;
	}

	public void setWcbtasklist(List<Wcbtask> wcbtasklist) {
		this.wcbtasklist = wcbtasklist;
	}
}
