package com.cfwx.rox.businesssync.market.servlet;

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
import com.cfwx.rox.businesssync.market.show.ZhiShu;
import com.cfwx.rox.businesssync.market.structure.BaseStructure;
import com.cfwx.rox.businesssync.market.structure.MarketCache;
import com.cfwx.rox.businesssync.market.util.MathFactory;
import com.cfwx.rox.businesssync.market.util.TimeUtils;

/**
 * @author J.C.J
 * 
 *         2013-9-10
 */
@SuppressWarnings("serial")
public class MarketServlet extends WebSocketServlet {

	private final static Logger LOG = Logger.getLogger(MarketServlet.class);

	private final Set<TimeShareMI> connections = new CopyOnWriteArraySet<TimeShareMI>();

	private ScheduledExecutorService executor = null;
	@Override
    public void init() throws ServletException {
        super.init();
        executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if(TimeUtils.canSend())
					send();
			}
		}, 1, BaseConfig.sendInterval, TimeUnit.SECONDS);
    }
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {

		int size = request.getParameter("size")==null?10:Integer.parseInt(request.getParameter("size"));
		int cp = request.getParameter("cp")==null?1:Integer.parseInt(request.getParameter("cp"));
		String type =request.getParameter("type")==null?"-1":request.getParameter("type");
		int order = request.getParameter("order")==null?1:Integer.parseInt(request.getParameter("order"));
		String sortField = request.getParameter("sortField")==null?"":request.getParameter("sortField").trim();
		
		return new TimeShareMI(size ,cp,type,order,sortField);
	}
	
	private void send(){
		
		Iterator<TimeShareMI> it = connections.iterator();
		TimeShareMI ts = null;
		while(it.hasNext()){
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
		
		int size = 10;
		int cp = 1;
		String type = "";
		int order = 1;
		String sortField = "";

		public TimeShareMI(int size ,int cp,String type,int order ,String sortField) {
			this.size = size;
			this.cp = cp;
			this.type = type;
			this.order = order;
			this.sortField = sortField;
		}

		@Override
		protected void onOpen(WsOutbound outbound) {
			connections.add(this);
//			LOG.info("建立链接....");
			if(TimeUtils.canSend())
				send();
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
//				LOG.info("链接已断开...");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}

		public void send() {
				try {
					//结果集合
					Map<String, Object> map = new HashMap<String, Object>();
					// 获取某类别下的所有行情信息
					List<ActualMarket> amList = new ArrayList<ActualMarket>();
	
					// 获取该类型下的证券代码
					List<String> list = BaseStructure.typeMap.get(type);
	
					for (String s : list) {
						if(MarketCache.get(s)!=null)
						amList.add(MarketCache.get(s));
					}
	
					Map<String, Object> infoMap = new HashMap<String, Object>();
					infoMap.put("psize", size);
					infoMap.put("cp", cp);
					infoMap.put("order", order);
					infoMap.put("sortField", sortField);
					infoMap.put("type", type);
					
					int allSize = amList.size()/size;
					if(amList.size()%size>0){
						allSize+=1;
					}
					infoMap.put("allSize", allSize);
					
					map.put("info", infoMap);
	
					// 获取默认指数
					List<ZhiShu> szList = new ArrayList<ZhiShu>();
					String[] sArr= StockConfig.dzs.split(",");
					
					ActualMarket am = null;
					for(String s :sArr){
						am = MarketCache.get(s);
						if(am!= null){
							szList.add(MathFactory.parseZhiShu(am));
						}
					}
					map.put("szList", szList);
	
					// 字段信息
					map.put("field", BaseStructure.fieldMap);
	
					List<SortObj> sList = null;
					if((sortField.equals("")) && type.equals(StockConfig.notSortType)){
						//东方自定义需求指数,如果是不排序的类别，则不进行排序
						 sList = getSortList(amList,size,cp);
					}else{
						if(sortField.equals(""))
							 sortField = StockConfig.defaultSortField;
						 
						 sList = sortList(amList,sortField,size,cp,order);
					}
					map.put("list", sList);
					
					CharBuffer buffer = CharBuffer.wrap(JSONObject.fromObject(map).toString());
					this.getWsOutbound().writeTextMessage(buffer);
//					LOG.info("推送....");
				} catch (IOException ignore) {
//					LOG.error(ignore.getMessage(),ignore);
				}catch (Exception e) {
					LOG.error(e.getMessage(),e);
				}
		}
		
		/**
		 * zx：最新价格排序
		 * @param list
		 * @param psize
		 * @param currentPage
		 * @param order 1：降序，0：升序;
		 * @return
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private List<SortObj> sortList(List<ActualMarket> list,String filed,int psize ,int currentPage,int order){
			
			//创建针对某个属性的升序比较
	        Comparator countCompare = new BeanComparator(filed);
	       
	        //默认的是升序，整一个降序
	        if(order==1)
	            countCompare=new ReverseComparator(countCompare);
	        //开始排序
	        try {
	        	 Collections.sort(list,countCompare);
			} catch (Exception e) {
			}
	        
	        return getSortList(list,psize,currentPage);
		}
		
		/**
		 * 降序列表
		 * @param list
		 * @param psize
		 * @param cp
		 * @return
		 */
		private List<SortObj> getSortList(List<ActualMarket> list,int psize,int cp){
			
			List<SortObj> resultList = new ArrayList<SortObj>();
			//处理条数
			for(int i=psize*(cp-1);i<psize*(cp);i++){
				if(list.size()>i)
				resultList.add(MathFactory.parseSortObj(list.get(i)));
			}
			return resultList;
		}
		
	}

}