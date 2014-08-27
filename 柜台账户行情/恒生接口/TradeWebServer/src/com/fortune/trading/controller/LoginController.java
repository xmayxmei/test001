package com.fortune.trading.controller;

import static com.fortune.trading.util.Constants.mode;
import static com.fortune.trading.util.Constants.SK.CAPTCHA_CODE;
import static com.fortune.trading.util.Constants.SK.LOGIN_PAGE_RANID;
import static com.fortune.trading.util.Constants.SK.LOGIN_REQUEST_TIMEMILLIS;
import static com.fortune.trading.util.Constants.SK.LOGIN_USER;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fortune.trading.entity.User;
import com.fortune.trading.service.ISystemService;
import com.fortune.trading.service.ITradeService;
import com.fortune.trading.util.AccountMonitor;
import com.fortune.trading.util.Constants;
import com.fortune.trading.util.U;
/**
 * <code>LoginController</code> 登录模块
 *
 * @author Colin, Jimmy
 * @since Tebon Trading v0.0.1 (April 24, 2014)
 */
@Controller("loginController")
public class LoginController {
	@Autowired(required = true)
	private ISystemService systemService;
	@Autowired(required = true)
	private ITradeService tradeService;
	/**
	 * 登录验证 并跳转
	 * @param hParams
	 * @return
	 */
	@RequestMapping("/loginAuthen.do")
	public  String loginAuthen(@RequestParam Map<String, String> hParams, HttpSession oSession, RedirectAttributes attr, HttpServletRequest oReq) {
		String sCaptcha = hParams.get("captcha");
		String sCaptchaInAttr = (String)oSession.getAttribute(CAPTCHA_CODE);
		String sResp = "";
		String sLoginType = hParams.remove("loginType");
		String sClientId = hParams.get("account_content");
		String url = hParams.get("url");
		// 验证输入的参数
		if (U.STR.isEmpty(sCaptcha) || U.STR.isEmpty(sLoginType) || U.STR.isEmpty(sClientId)) {
			attr.addAttribute("errMsg", "输入的参数不能为空.");
			attr.addAttribute("url", url);
			return "redirect:loginPage.do";
		}
		//测试模式不需要验证码
		if (mode != 0 && (sCaptchaInAttr == null || !sCaptchaInAttr.equalsIgnoreCase(sCaptcha))) {
			sResp = "R01|验证码错误;"; 
		} else {
		    //是否已经锁定
		    if (Constants.isSupportLockAcc && AccountMonitor.getInstance().isAccountLock(sClientId)) {
		        attr.addAttribute("errMsg", "账号已经锁定.请" + (Constants.accLockTimes / 1000) + "秒后登录.");
		        return "redirect:loginPage.do";
		    }
		    // 解密
		    if (Constants.isSupportEncry) {
		        String sPsw = hParams.get("password");
		        PrivateKey pk = (PrivateKey)oSession.getAttribute(Constants.SK.RSA_PRIVATE_KEY);
		        if (pk != null) {
		          try {
                    sPsw = U.RSA.decrypt(pk, sPsw);
                    hParams.put("password", sPsw);
                  } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
		        }
		    }
			if ("2".equals(sLoginType)) {
				// 深A
				hParams.put("content_type", "2");
				hParams.put("input_content", "5");
			} else if ("3".equals(sLoginType)) {
				// 泸A
				hParams.put("content_type", "1");
				hParams.put("input_content", "5");
			} else if ("1".equals(sLoginType)) {
				 hParams.put("input_content", "1");
			}
			sResp = tradeService.loginHandler(hParams);
			if (sResp == null) {
				sResp = "R01|连接交易服务器错误.";
			}
		}
		// Check whether user authentication successfully
		if (sResp.startsWith("R00")) {
			if (sResp.charAt(4) == '0') {
				User user = new User();
				user.parseToUser(sResp, sLoginType);
				oSession.setAttribute(LOGIN_USER, user);
				oSession.setAttribute("fund_account", user.getFundAccount());
				oSession.setAttribute("password", hParams.get("password"));
				oSession.setAttribute("branch_no", user.getBranchNo());
				if (Constants.isSupportLockAcc) {
					AccountMonitor.getInstance().recordAccountLogin(sClientId, false);
				}
				if(StringUtils.isNotEmpty(url)){
					return "redirect:" + url.replace(oSession.getServletContext().getContextPath(), "");
				}
				return "redirect:homePage.do";
			} 
		}  
		// 解释返回错误的消息
		String sErrMsg =  null;
		String[] aSData = sResp.split("\\|");
		if (sResp.startsWith("R00")) {
			String[] as = U.STR.fastSplit(aSData[1], ';');
			sErrMsg = as[0] + ";" + as[1];
		} else if (sResp.startsWith("R01")){
			sErrMsg = aSData[1];
			// 密码错误
			if (Constants.isSupportLockAcc && aSData[1] != null && aSData[1].startsWith("-63")) {
				  AccountMonitor.getInstance().recordAccountLogin(sClientId, true);
				  int iRemindTime = AccountMonitor.getInstance().getRemindErrorCount(sClientId);
				  if (iRemindTime == 0) {
				    sErrMsg = "账号已经锁定.请" + (Constants.accLockTimes / 1000) + "秒后登录.";
				  } else {
				    sErrMsg = sErrMsg + "(还有" + iRemindTime + "次机会)";
				  }
				}
		}
		attr.addAttribute("errMsg",  sErrMsg);
		attr.addAttribute("url",  url);
		return "redirect:loginPage.do";
	}
	/**
	 * 验证码
	 */
	@RequestMapping("/captcha.do")
	public void captcha(HttpSession oSession, HttpServletResponse oResp) {
		Map<String, Object> hData = systemService.captcha();
		String sCode = (String)hData.get("code");
		BufferedImage imgCaptcha = (BufferedImage)hData.get("img");
		
		OutputStream out = null;
		try {
			oSession.setAttribute(CAPTCHA_CODE, sCode);
			out = oResp.getOutputStream();
			ImageIO.write(imgCaptcha, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 微信交易入口
	 */
	@RequestMapping("/wechatTrade.do")
	public @ResponseBody String wechatRequest(HttpServletRequest oReq, HttpSession oSession) {
		String sCtxPath = oReq.getServletContext().getContextPath();
		String sURL = oReq.getRequestURL().toString();
		String sURI = oReq.getRequestURI();
		String sBaseURL = sURL.substring(0, sURL.length() - sURI.length());
		String sSessionRanID = U.generateUID();
		oSession.setAttribute(LOGIN_PAGE_RANID, sSessionRanID);
		oSession.setAttribute(LOGIN_REQUEST_TIMEMILLIS, System.currentTimeMillis());
		
		StringBuffer sResp = new StringBuffer()
		.append(sBaseURL)
		.append(sCtxPath)
		.append("/wechatTradeLogin.do?")
		.append("openID=")
		.append("123456789") // TODO 
		.append("&sessionRanID=")
		.append(sSessionRanID);
		return sResp.toString();
	}
	/**
	 * 微信交易登录请求
	 */
	@RequestMapping("/wechatTradeLogin.do")
	public String loginRequest(HttpServletRequest oReq, HttpServletResponse oResp, HttpSession oSession) {
		String sParamRanID = oReq.getParameter("sessionRanID");
		String sSessionRanID = (String)oSession.getAttribute(LOGIN_PAGE_RANID);
		Long lTime = (Long)oSession.getAttribute(LOGIN_REQUEST_TIMEMILLIS);
		if (sParamRanID == null || sSessionRanID == null || lTime == null || !sParamRanID.equals(sSessionRanID)) {
			oReq.setAttribute("msg", "无效的地址");
			return "invalid";
		} 
		long lCurrTime = System.currentTimeMillis();
		if ((lCurrTime - lTime) / 1000 > 15) {
			oReq.setAttribute("msg", "请在15秒内打开该链接");
			return "invalid";
		}
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
		return "/authen/login";
	}
	/**
	 *  登录请求页面
	 */
	@RequestMapping("/loginPage.do")
	public String loginRequest(@RequestParam(required=false)String errMsg, HttpServletRequest oReq, @RequestParam Map<String, String> hParams, HttpSession oSession) {
		oReq.setAttribute("errMsg", U.STR.decode(errMsg, "utf-8"));
		Object oUser = oSession.getAttribute(Constants.SK.LOGIN_USER);
		String url = oReq.getParameter("url");
		if(url == null){
			url = "homePage.do";
		}
		oReq.setAttribute("url", url);
		if (oUser != null) {
			return "redirect:homePage.do";
		}
		// Cache op_station 
		oSession.setAttribute("op_station", Constants.opStationPrefix + oReq.getRemoteAddr());
		// RSA加密，生成密钥对，公钥通过Freemarket直接传递到页面
		if (Constants.isSupportEncry) {
		    KeyPair kp = U.RSA.generateKeyPair(1024);
	        RSAPublicKey oPublicKey = (RSAPublicKey)kp.getPublic();
	        String sExponent = new String(Hex.encodeHex(oPublicKey.getPublicExponent().toByteArray()));
	        String sModulus = new String(Hex.encodeHex(oPublicKey.getModulus().toByteArray()));
	        oReq.setAttribute("md", sModulus);
	        oReq.setAttribute("exp", sExponent);
	        oSession.setAttribute(Constants.SK.RSA_PRIVATE_KEY, kp.getPrivate());
		}
        return "/authen/login";
	}
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping("/logout.do")
	public String logout(HttpSession oSession) {
		oSession.invalidate();
		return "redirect:loginPage.do";
	}
}
