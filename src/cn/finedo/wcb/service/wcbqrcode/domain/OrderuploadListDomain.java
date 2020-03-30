package cn.finedo.wcb.service.wcbqrcode.domain;

import java.util.ArrayList;
import java.util.List;

import cn.finedo.common.domain.BaseDomain;
import cn.finedo.wcb.pojo.Orderupload;

public class OrderuploadListDomain extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	// 员工二维码表list
		private List<Orderupload> orderuploadlist=new ArrayList<Orderupload>();

		public List<Orderupload> getOrderuploadlist() {
			return orderuploadlist;
		}

		public void setOrderuploadlist(List<Orderupload> orderuploadlist) {
			this.orderuploadlist = orderuploadlist;
		}
}
