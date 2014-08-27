package com.fortune.trading.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.fortune.trading.service.ISystemService;
import com.fortune.trading.util.IMap;
import com.fortune.trading.util.RMap;
import com.fortune.trading.util.U;

/**
 * <code>SystemServiceImpl</code>
 *
 * @author Colin, Jimmy
 * @since Trading v0.0.1
 */
@Service("systemService")
@Scope("singleton")
public class SystemServiceImpl implements ISystemService{
	/** 设置生成验证码的参数*/
	private final IMap<String, Object> hParams = RMap.build()
		.put("width", 100)
		.put("height", 30)
		.put("font", new Font("Microsoft YaHei", Font.BOLD, 26))
		.put("bg", Color.decode("#D9D9D9"))
		.put("fg", Color.decode("#4792BF"));
		
	@Override
	public Map<String, Object> captcha() {
		return U.CAPTCHA.generateCaptcha(hParams);
	}
	
}
