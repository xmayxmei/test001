package com.fortune.trading.controller.account;

import static com.fortune.trading.util.Constants.SK.CHANGEPSW_PAGE_RANID;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fortune.trading.annotation.AutoInject;
import com.fortune.trading.entity.ClientResponse;
import com.fortune.trading.service.ITradeConverter;
import com.fortune.trading.service.ITradeService;
import com.fortune.trading.service.impl.HandsunConverter;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;
/**
 * <code>AccountController</code> 账号管理、银证转账模块前端控制器.
 * 
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1
 */
@Controller("accountController")
public class AccountController {
	@Autowired(required = true)
	private ITradeService tradeService;
	@Autowired(required = true)
	private ITradeConverter tradeConverter;
	/**
	 *  修改密码请求页面
	 */
	@RequestMapping("/changePsw")
	public String changePwd(HttpServletRequest oReq, HttpSession oSession) {
		String sRanID = U.generateUID();
		oSession.setAttribute(CHANGEPSW_PAGE_RANID, sRanID);
		oReq.setAttribute("ranID", sRanID);
		// 加密，公钥通过Freemarket直接传递到页面
        if (Constants.isSupportEncry) {
            KeyPair kp = U.RSA.generateKeyPair(1024);
            RSAPublicKey oPublicKey = (RSAPublicKey)kp.getPublic();
            String sExponent = new String(Hex.encodeHex(oPublicKey.getPublicExponent().toByteArray()));
            String sModulus = new String(Hex.encodeHex(oPublicKey.getModulus().toByteArray()));
            oReq.setAttribute("md", sModulus);
            oReq.setAttribute("exp", sExponent);
            oSession.setAttribute(Constants.SK.RSA_PRIVATE_KEY, kp.getPrivate());
        }
		return "/account/passwordchange";
	}
	/**
	 * 修改密码 并跳转
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/changePassword")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "op_station"})
	public String changePassword(@RequestParam Map<String, String> hParams, RedirectAttributes attr, HttpSession oSession) {
		ClientResponse<?> sResp = null;
		String sMsg = null;
		    // 解密
        if (Constants.isSupportEncry) {
            String sPsw = hParams.get("password");
            String sNewPsw = hParams.get("new_password");
            PrivateKey pk = (PrivateKey)oSession.getAttribute(Constants.SK.RSA_PRIVATE_KEY);
            if (pk != null) {
              try {
                sPsw = U.RSA.decrypt(pk, sPsw);
                sNewPsw = U.RSA.decrypt(pk, sNewPsw);
                hParams.put("password", sPsw);
                hParams.put("new_password", sNewPsw);
              } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
        }
		sResp = modiTPwd(hParams);
		sMsg = sResp.getErrMsg();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)sResp.getData();
			if (lstData.size() < 1) {
				sMsg = "修改失败，请确保输入的参数无误.";
			} else {
				String[] asData = lstData.get(0);
				try {
					sMsg = asData[1];
					if (Integer.parseInt(asData[0]) >= 0 && U.STR.isEmpty(sMsg)) {
						sMsg = "修改成功!";
						attr.addAttribute("isSuccess", "true");
						// 更新密码
						oSession.setAttribute("password", hParams.get("new_password"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		attr.addAttribute("msg", sMsg);
		return "redirect:info.do";
	}
	/**
	 * 修改资金密码
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/modiFPwd")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> modiFPwd(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.modiFPwdHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "modiFPwd", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 修改交易密码
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/modiTPwd")
	@AutoInject(paramName="hParams",keys={"fund_account", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> modiTPwd(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.modiTPwdHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "modiTPwd", U.STR.fastSplit2I(sCustomFields, ','));
		return oClientRsp;
	}
	/**
	 * 银证转帐 证券到银行
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/zqToBank")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public String bondToBank(@RequestParam Map<String, String> hParams, RedirectAttributes attrs) {
		String sBz = hParams.remove("bz");
		if (sBz != null) {
			hParams.put("money_type", (String)HandsunConverter.hReMoneyType.getAs(sBz));
		}
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.zjToBank(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "zqToBank", U.STR.fastSplit2I(sCustomFields, ','));
		String sMsg = oClientRsp.getErrMsg();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)oClientRsp.getData();
			if (lstData.size() < 1) {
				sMsg = "交易服务器返回的数据为空.";
			} else {
				String[] asData = lstData.get(0);
				try {
					sMsg = asData[1];
					if (U.STR.isEmpty(sMsg) && Float.parseFloat(asData[0]) >= 0) {
						sMsg = "转账成功!";
						attrs.addAttribute("isSuccess", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		attrs.addAttribute("msg", sMsg);
		return "redirect:info.do";
	}
	/**
	 * 资金归转
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/zjNZ")
	public @ResponseBody String zjNZ(@RequestParam Map<String, String> hParams) {
		String sResp = tradeService.zjNZBiz(hParams);
		return sResp;
	}
	/**
	 * 银证转帐 银行到证券
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/bankToZQ")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public String bankToBond(@RequestParam Map<String, String> hParams, RedirectAttributes attrs) {
		String sBz = hParams.remove("bz");
		if (sBz != null) {
			hParams.put("money_type", (String)HandsunConverter.hReMoneyType.getAs(sBz));
		}
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.bankToZQHandler(hParams);
		ClientResponse<?> oClientRsp = tradeConverter.convertResp2ClientResp(sResp, "bankToZQ", U.STR.fastSplit2I(sCustomFields, ','));
		
		String sMsg = oClientRsp.getErrMsg();
		if (U.STR.isEmpty(sMsg)) {
			List<String[]> lstData = (List<String[]>)oClientRsp.getData();
			if (lstData.size() < 1) {
				sMsg = "交易服务器返回的数据为空.";
			} else {
				String[] asData = lstData.get(0);
				try {
					sMsg = asData[1];
					if (U.STR.isEmpty(sMsg) && Float.parseFloat(asData[0]) >= 0) {
						sMsg = "转账成功!";
						attrs.addAttribute("isSuccess", "true");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		attrs.addAttribute("msg", sMsg);
		return "redirect:info.do";
	}
	/**
	 * @param isSuccess
	 * @param msg
	 * @param oReq
	 * @return
	 */
	@RequestMapping(value="/info", method={RequestMethod.GET})
	public String passwordinfoRequest(@RequestParam(required=false) String isSuccess, @RequestParam String msg, HttpServletRequest oReq) {
		if (isSuccess != null) {
			oReq.setAttribute("isSuccess", U.STR.decode(isSuccess, "utf-8"));
		}
		oReq.setAttribute("msg", U.STR.decode(msg, "utf-8"));
		return "/result/info";
	}
	/**
	 *  银证转账结果消息页面
	 * @return
	 */
	/*@RequestMapping("/bankZqInfo")
	public String  bankZqInfo(@RequestParam(required=false) String isSuccess, @RequestParam String msg, HttpServletRequest oReq) {
		if (isSuccess != null) {
			oReq.setAttribute("isSuccess", U.STR.decode(isSuccess, "utf-8"));
		}
		oReq.setAttribute("msg", U.STR.decode(msg, "utf-8"));
		return "/account/bankZqInfo";
	}*/
	/**
	 * 银行转证券请求页面
	 * @return
	 */
	@RequestMapping("/bankToZQPage")
	public String bankToZQPage() {
		return "/account/transferZQ";
	}
	/**
	 *  证券转银行请求页面
	 * @return
	 */
	@RequestMapping("/ZQToBankPage")
	public String zqToBankPage() {
		return "/account/transferBank";
	}
	/**
	 *  银证业务查询 请求页面
	 * @return
	 */
	@RequestMapping("/dayFundsFlow")
	public String dayfundsflow() {
		return "/account/dayfundsflow";
	}
	/**
	 * 
	 * @param hParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/bankZqQuery")
	@AutoInject(paramName="hParams",keys={"fund_account", "password", "branch_no", "op_station"})
	public @ResponseBody ClientResponse<?> zjToBankQuery(@RequestParam Map<String, String> hParams) {
		String sCustomFields = hParams.remove("cusFields");
		String sResp = tradeService.zjToBankQuery(hParams);
		List<Integer> lstCusFields = U.STR.fastSplit2I(sCustomFields, ',');
		ClientResponse<String[]> oClientRsp = (ClientResponse<String[]>)tradeConverter.convertResp2ClientResp(sResp, "zqToBankQuery", U.STR.fastSplit2I(sCustomFields, ','));
		if (lstCusFields != null) {
			//排序 根据日期
			Integer iSortColumn = lstCusFields.indexOf(11);
			if (iSortColumn != null) {
				U.ARR.sort(oClientRsp.getData(), iSortColumn, false);
			}
		}
		return oClientRsp;
	}
}
