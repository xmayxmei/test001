package com.cfwx.rox.businesssync.market.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.log4j.Logger;

import com.cfwx.rox.businesssync.market.config.BaseConfig;
import com.cfwx.rox.businesssync.market.config.StockConfig;
import com.cfwx.rox.businesssync.market.entity.ActualMarket;
import com.cfwx.rox.businesssync.market.show.SortObj;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author J.C.J
 * 
 *         2013-9-10
 */
@SuppressWarnings({ "serial", "deprecation" })
public class MyFollowServlet extends WebSocketServlet {

	private final static Logger LOG = Logger.getLogger(MyFollowServlet.class);

	private final Set<TimeShareMI> connections = new CopyOnWriteArraySet<TimeShareMI>();

	private ScheduledExecutorService executor = null;

	@Override
	public void init() throws ServletException {
		super.init();
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (TimeUtils.canSend())
					send();
			}
		}, 1, BaseConfig.sendInterval, TimeUnit.SECONDS);
	}

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {

		int order = request.getParameter("order") == null ? 1 : Integer
				.parseInt(request.getParameter("order"));
		String sortField = request.getParameter("sortField") == null
				|| request.getParameter("sortField").trim().equals("") ? StockConfig.defaultSortField
				: request.getParameter("sortField");
		String codeStr = request.getParameter("codeStr");
		int size = request.getParameter("size") == null ? 3 : Integer
				.parseInt(request.getParameter("size"));
		int cp = request.getParameter("cp") == null ? 1 : Integer
				.parseInt(request.getParameter("cp"));
		return new TimeShareMI(codeStr, order, sortField, cp ,size);
	}

	private void send() {

		Iterator<TimeShareMI> it = connections.iterator();
		TimeShareMI ts = null;
		while (it.hasNext()) {
			ts = it.next();
			ts.send();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		if (executor != null) {
			executor.shutdown();
		}
	}

	private class TimeShareMI extends MessageInbound {

		int size = 0;
		int cp = 1;
		int allSize = 0;
		String codeStr = "";
		int order = 1;
		String sortField = "";

		public TimeShareMI(String codeStr, int order, String sortField,int cp, int size ) {
			this.codeStr = codeStr;
			this.order = order;
			this.sortField = sortField;
			this.cp = cp;
			this.size = size;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
//			 LOG.info("建立链接....");
			if (TimeUtils.canSend()){
				send();
			}
				
		}

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException {

		}

		@Override
		protected void onTextMessage(CharBuffer arg0) throws IOException {

		}

		@Override
		protected void onClose(int status) {
			try {
				connections.remove(this);
//				 LOG.info("链接已断开...");
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			} finally {

			}
		}

		public void send() {

			try {
				if (codeStr != null && !codeStr.trim().equals("")) {

					// 结果集合
					Map<String, Object> map = new HashMap<String, Object>();

					// 获取某类别下的所有行情信息
					List<ActualMarket> amList = new ArrayList<ActualMarket>();

					// 获取该关注的证券代码
					String[] codeArr = codeStr.split(",");
					allSize += codeArr.length;

					for (String s : codeArr) {
						if (!s.equals("") && MarketCache.get(s) != null) {
							amList.add(MarketCache.get(s));
						}
					}

					Map<String, Object> infoMap = new HashMap<String, Object>();
					infoMap.put("order", order);
					infoMap.put("sortField", sortField);
					infoMap.put("codeStr", codeStr);
					infoMap.put("cp", cp);
					infoMap.put("size", size);
					infoMap.put("allSize", allSize);
					
					map.put("info", infoMap);

					// 字段信息
					map.put("field", BaseStructure.fieldMap);

					if (allSize != 0 && amList != null && amList.size() > 0) {
						List<SortObj> sList = null;
//						System.out.print(order);
						if ((sortField.equals(""))) {
							// 自选股默认不进行排序
							sList = getSortList(amList, size, cp);
						} else {
							sList = sortList(amList, sortField, size, cp, order);
						}
						map.put("list", sList);
					} else {
						map.put("list", new ArrayList<SortObj>());
					}

					CharBuffer buffer = CharBuffer.wrap(JSONObject.fromObject(
							map).toString());
					this.getWsOutbound().writeTextMessage(buffer);
//					 LOG.info("推送....");
				} else {
					connections.remove(this);
//					 LOG.info("链接已断开...");
				}
			} catch (IOException ignore) {
				 LOG.error(ignore.getMessage(),ignore);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		}

		/**
		 * zx：最新价格排序
		 * 
		 * @param list
		 * @param psize
		 * @param currentPage
		 * @param order
		 *            1：降序，0：升序;
		 * @return
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private List<SortObj> sortList(List<ActualMarket> list, String filed,
				int psize, int currentPage, int order) {

			// 创建针对某个属性的升序比较
			Comparator countCompare = new BeanComparator(filed);

			// 默认的是升序，整一个降序
			if (order == 1)
				countCompare = new ReverseComparator(countCompare);
			// 开始排序
			try {
				Collections.sort(list, countCompare);
			} catch (Exception e) {
			}

			return getSortList(list, psize, currentPage);
		}

		/**
		 * 获取分页
		 * 
		 * @param list
		 * @param psize
		 * @param cp
		 * @return
		 */
		private List<SortObj> getSortList(List<ActualMarket> list, int psize,
				int cp) {

			List<SortObj> resultList = new ArrayList<SortObj>();
			// 处理条数
			for (int i = psize * (cp - 1); i < psize * (cp); i++) {
				if (list.size() > i)
					resultList.add(MathFactory.parseSortObj(list.get(i)));
			}
			return resultList;
		}

	}

}