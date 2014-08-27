package com.biz.abs;

import java.util.HashMap;

import com.core.IBiz;
import com.core.abs.DataHandler;
import com.util.FIDConstants;
import com.util.FUNConstants;
import com.util.FieldType;

public class GDHQueryBiz extends DataHandler implements IBiz {
	private final static int iFuncNo = FUNConstants.FUN_SECU_LIST_GDHBYKHH;

	@Override
	protected void buildField() {
		fieldMap = new HashMap<Integer, String>();
		fieldMap.put(FIDConstants.FID_KHH,  "clientId");//客户号
				
		asResponseField = new int[][] {
				{FIDConstants.FID_GDH,  FieldType.FieldString },//股东号
				{ FIDConstants.FID_JYS, FieldType.FieldString },//交易所
				{ FIDConstants.FID_BZ,  FieldType.FieldString },//币种
				{ FIDConstants.FID_ZZHBZ,  FieldType.FieldString },//主股东号标志，1表示主股东号，其它为辅助股东号
				{ FIDConstants.FID_JYQX,  FieldType.FieldString },//股东交易权限
		};
		
		iFunID = iFuncNo;
	}
}
