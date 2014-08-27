/**
 * 
 */
package com.cfwx.rox.businesssync.market.util;

import java.util.List;

import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.show.ResultInfo;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;

/**
 * @author W.W.B
 *
 * 2014-5-19
 */
public class StockDataUtils {
	/**
	 *只获取股票的信息
	 * @param code 如：600900，000001
	 * @return ActualMarket
	 */
	public static ActualMarket getAM(String code){
		List<ResultInfo> list = BaseStructure.codeMap.get(code);
		ActualMarket am=null;
		for(int i=0;i<list.size();i++){
			ResultInfo info=list.get(i);
			am = MarketCache.get(info.getPcode());
			if(am.getType().equals("A股")||am.getType().equals("B股")){
				break;
			}else if(am.getType().equals("成份指数")){
				break;
			}else{
				am=null;
			}
		}
		return am;
	}

}
