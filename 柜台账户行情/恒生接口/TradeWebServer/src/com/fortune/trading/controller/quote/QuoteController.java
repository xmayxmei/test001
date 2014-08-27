package com.fortune.trading.controller.quote;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fortune.trading.service.IQuoteService;
/**
 * <code>QuoteController</code> 行情获取前端控制器.
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
@Controller("quoteController")
public class QuoteController {
	@Autowired(required=true)
	private IQuoteService quoteService;
	/**
	 * @return
	 */
	@RequestMapping("getFData")
	public @ResponseBody String quote(@RequestParam Map<String, String> hParams) {
		String sResp = quoteService.getData(hParams);
		return sResp;
	}
	/**
	 * 行情
	 * @param hParams
	 * @return
	 */
	/*@RequestMapping("/quote")
	public @ResponseBody ClientResponse<?> quote(@RequestParam Map<String, String> hParams, HttpSession oSession) {
		injectClientID(hParams, oSession);
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.quoteHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "quote", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}*/
}
