/**
 * 
 */
package com.cfwx.rox.businesssync.market.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao;
import com.cfwx.rox.businesssync.market.show.CommonLine;
import com.cfwx.rox.businesssync.market.show.DayLine;
import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.util.U;

/**
 * <code>QuoteCacheDaoImpl</code> 实现通过Redis把日、周、月K历史数据保存到内存.
 * </p>
 * <b>Note</b> 保存到redis里面的key，每一类的数据都应该区分开，避免key重复.<br>
 * 这里之所以没有用到<code>RedisTemplate</code>
 * 
 * @author Jimmy
 * @since RoxMarket (June 18, 2014)
 * 
 * @TODO To be refactor by Jimmy
 */
@Repository("quoteCacheDaoImpl")
public class QuoteCacheDaoImpl implements IQuoteCacheDao{
	/** 5分K key前缀 */
	public static final String MIN5_PREFIX = "min5_";
	/** 10分K key前缀 */
	public static final String MIN10_PREFIX = "min10_";
	/** 15分K key前缀 */
	public static final String MIN15_PREFIX = "min15_";
	/** 30分K key前缀 */
	public static final String MIN30_PREFIX = "min30_";
	/** 60分K key前缀 */
	public static final String 	MIN60_PREFIX = "min60_";
	/** 日K key前缀 */
	public static final String DAILY_PREFIX = "daily_";
	/** 周K key前缀 */
	public static final String WEEKLY_PREFIX = "weekly_";
	/** 月K key前缀 */
	public static final String MONTHLY_PREFIX = "monthly_";
	/** 年K key前缀 */
	public static final String YEAR_PREFIX = "yearly_";
	/** 股票基本信息 key前缀 */
	public static final String STOCK_BASEINFO = "stock_baseinfo_";
	
	private static final String ENCODING = "UTF-8";
	
	private static final String FOLLOW_PREFIX = "follow_";
	/* @Autowired
	 protected RedisTemplate<String, DayLine> dayLineTemplate ;*/
	
	 @Autowired(required=true)
	 private JedisPool pool;
	 @Autowired
	 private JdkSerializationRedisSerializer oSerial;
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addDaily(java.lang.String, java.util.List)
	 */
	@Override
	public void addDaily(final String sCode, final List<DayLine> lstDaily) {
        String sKey = DAILY_PREFIX + sCode;
        try {
			rpush(sKey, lstDaily);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getDaily(java.lang.String)
	 */
	@Override
	public List<DayLine> getDaily(final String sCode) {
		String sKey = DAILY_PREFIX + sCode;
		List<DayLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getLastDaily(java.lang.String)
	 */
	@Override
	public DayLine getLastDaily(String sCode) {
		Jedis jedis = null;
		DayLine oLine = null;
        try {
            jedis = pool.getResource();
            String sKey = DAILY_PREFIX + sCode;
              byte[] aBKey = sKey.getBytes(ENCODING);
            byte[] aData = jedis.lindex(aBKey, -1);
            oLine = (DayLine)oSerial.deserialize(aData);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return oLine;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addWeekly(java.lang.String, java.util.List)
	 */
	@Override
	public void addWeekly(String sCode, List<CommonLine> lstNewWeekly) {
         String sKey = WEEKLY_PREFIX + sCode;
         try {
 			rpush(sKey, lstNewWeekly);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getWeekly(java.lang.String)
	 */
	@Override
	public List<CommonLine> getWeekly(String sCode) {
		String sKey = WEEKLY_PREFIX + sCode;
		List<CommonLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getLastWeekly(java.lang.String)
	 */
	@Override
	public CommonLine getLastWeekly(String sCode) {
		Jedis jedis = null;
		CommonLine oLine = null;
        try {
            jedis = pool.getResource();
            String sKey = WEEKLY_PREFIX + sCode;
              byte[] aBKey = sKey.getBytes(ENCODING);
            byte[] aData = jedis.lindex(aBKey, -1);
            oLine = (CommonLine)oSerial.deserialize(aData);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return oLine;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addMonthly(java.lang.String, java.util.List)
	 */
	@Override
	public void addMonthly(String sCode, List<CommonLine> lstNewMonthly) {
        String sKey = MONTHLY_PREFIX + sCode;
        try {
 			rpush(sKey, lstNewMonthly);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getMonthly(java.lang.String)
	 */
	@Override
	public List<CommonLine> getMonthly(String sCode) {
		String sKey = MONTHLY_PREFIX + sCode;
		List<CommonLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getLastMonthly(java.lang.String)
	 */
	@Override
	public CommonLine getLastMonthly(String sCode) {
		Jedis jedis = null;
		CommonLine oLine = null;
        try {
            jedis = pool.getResource();
            String sKey = MONTHLY_PREFIX + sCode;
            byte[] aBKey = sKey.getBytes(ENCODING);
            byte[] aData = jedis.lindex(aBKey, -1);
            oLine = (CommonLine)oSerial.deserialize(aData);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return oLine;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addYearly(java.lang.String, java.util.List)
	 */
	@Override
	public void addYearly(String sCode, List<CommonLine> lstNewYearly) {
        String sKey = YEAR_PREFIX + sCode;
        try {
  			rpush(sKey, lstNewYearly);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getYearly(java.lang.String)
	 */
	@Override
	public List<CommonLine> getYearly(String sCode) {
		String sKey = YEAR_PREFIX + sCode;
		List<CommonLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getLastYearly(java.lang.String)
	 */
	@Override
	public CommonLine getLastYearly(String sCode) {
		Jedis jedis = null;
		CommonLine oLine = null;
        try {
            jedis = pool.getResource();
            String sKey = YEAR_PREFIX + sCode;
              byte[] aBKey = sKey.getBytes(ENCODING);
            byte[] aData = jedis.lindex(aBKey, -1);
            oLine = (CommonLine)oSerial.deserialize(aData);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return oLine;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#add5Min(java.lang.String, java.util.List)
	 */
	@Override
	public void add5Min(String sCode, List<MinLine> lst5Min) {
        String sKey = MIN5_PREFIX + sCode;
        try {
  			rpush(sKey, lst5Min);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#get5Min(java.lang.String)
	 */
	@Override
	public List<MinLine> get5Min(String sCode) {
		String sKey = MIN5_PREFIX + sCode;
		List<MinLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clearAllCache()
	 */
	@Override
	public void clearAllKLineCache() {
		String sMinPatter = MIN5_PREFIX + "*";
		String sMin15Patter = MIN15_PREFIX + "*";
		String sMin30Patter = MIN30_PREFIX + "*";
		String sMin60Patter = MIN60_PREFIX + "*";
		String sDailyPattern = DAILY_PREFIX + "*";
		String sWeeklyPattern = WEEKLY_PREFIX + "*";
		String sMonthlyPattern = MONTHLY_PREFIX + "*";
		String sYearlyPattern = YEAR_PREFIX + "*";
		del(sMinPatter, sMin15Patter, sMin30Patter, sMin60Patter,sDailyPattern, sWeeklyPattern, sMonthlyPattern, sYearlyPattern);
	}
	/*
	 * @param sPattern
	 */
	private void del(String... sPattern) {
		int iLen = sPattern.length;
		 Jedis jedis = null;
         try {
             jedis = pool.getResource();
             for (int i = 0; i < iLen; i++) {
            	 Set<byte[]> aBKey = jedis.keys(sPattern[i].getBytes());
            	 Iterator<byte[]> iter = aBKey.iterator();
            	 while (iter.hasNext()) {
            		 jedis.del(iter.next());
            	 }
     		}
         } catch (Exception e) {
             //释放redis对象
             pool.returnBrokenResource(jedis);
             e.printStackTrace();
         } finally {
             //返还到连接池
             pool.returnResourceObject(jedis);
         }
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clear5MinCache()
	 */
	@Override
	public void clear5MinCache() {
		String sMinPatter = "*" + MIN5_PREFIX + "*";
		del(sMinPatter);
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clear5MinCache()
	 */
	@Override
	public void clear10MinCache() {
		String sMinPatter = "*" + MIN10_PREFIX + "*";
		del(sMinPatter);
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#add10Min(java.lang.String, java.util.List)
	 */
	@Override
	public void add10Min(String sCode, List<MinLine> lst10Min) {
		 String sKey = MIN10_PREFIX + sCode;
	        try {
	  			rpush(sKey, lst10Min);
	  		} catch (Exception e) {
	  			e.printStackTrace();
	  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#get10Min(java.lang.String)
	 */
	@Override
	public List<MinLine> get10Min(String sCode) {
		String sKey = MIN10_PREFIX + sCode;
		List<MinLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/**
	 * 
	 * @param sKey
	 * @param lstT
	 * @throws Exception
	 */
	public <T extends Serializable> void rpush(String sKey, List<T> lstT) throws Exception {
		 Jedis jedis = null;
         try {
             jedis = pool.getResource();
             byte[] aBKey = sKey.getBytes(ENCODING);
             int iLen = lstT.size();
             for (int i = 0; i < iLen; i++) {
             	jedis.rpush(aBKey, oSerial.serialize(lstT.get(i)));
             }
         } catch (Exception e) {
             //释放redis对象
             pool.returnBrokenResource(jedis);
             throw e;
         } finally {
             //返还到连接池
             pool.returnResourceObject(jedis);
         }
	}
	/**
	 * @param sKey
	 * @param iStart
	 * @param iEnd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Serializable> List<T> lrange(String sKey, int iStart, int iEnd) {
		List<T> lstRst = new ArrayList<T>();
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
              byte[] aBKey = sKey.getBytes(ENCODING);
            List<byte[]> lstData = jedis.lrange(aBKey, 0, -1);
            if (lstData == null) {
            	return null;
            }
            int iLen = lstData.size();
            for (int i = 0; i < iLen; i++) {
            	T oRst = (T)oSerial.deserialize(lstData.get(i));
            	lstRst.add(oRst);
            }
            return lstRst;
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clear15MinCache()
	 */
	@Override
	public void clear15MinCache() {
		String sMinPatter = "*" + MIN15_PREFIX + "*";
		del(sMinPatter);
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#add15Min(java.lang.String, java.util.List)
	 */
	@Override
	public void add15Min(String sCode, List<MinLine> lst15Mins) {
        String sKey = MIN15_PREFIX + sCode;
        try {
  			rpush(sKey, lst15Mins);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#get15Min(java.lang.String)
	 */
	@Override
	public List<MinLine> get15Min(String sCode) {
		String sKey = MIN15_PREFIX + sCode;
		List<MinLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clear30MinCache()
	 */
	@Override
	public void clear30MinCache() {
		String sMinPatter = "*" + MIN30_PREFIX + "*";
		del(sMinPatter);
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#add30Min(java.lang.String, java.util.List)
	 */
	@Override
	public void add30Min(String sCode, List<MinLine> lst30Mins) {
        String sKey = MIN30_PREFIX + sCode;
        try {
  			rpush(sKey, lst30Mins);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#get30Min(java.lang.String)
	 */
	@Override
	public List<MinLine> get30Min(String sCode) {
		String sKey = MIN30_PREFIX + sCode;
		List<MinLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#get60Min(java.lang.String)
	 */
	@Override
	public List<MinLine> get60Min(String sCode) {
		String sKey = MIN60_PREFIX + sCode;
		List<MinLine> lstRst = lrange(sKey, 0, -1);
		return lstRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#add60Min(java.lang.String, java.util.List)
	 */
	@Override
	public void add60Min(String sCode, List<MinLine> lst60Mins) {
        String sKey = MIN60_PREFIX + sCode;
        try {
  			rpush(sKey, lst60Mins);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#clear60MinCache()
	 */
	@Override
	public void clear60MinCache() {
		String sMinPatter = "*" + MIN60_PREFIX + "*";
		del(sMinPatter);
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addStockBaseInfo(java.lang.String)
	 */
	@Override
	public void addStockBaseInfo(String sData) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String sKey = STOCK_BASEINFO;
            byte[] aBKey = sKey.getBytes(ENCODING);
            jedis.set(aBKey, oSerial.serialize(sData));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getStockBaseInfo()
	 */
	@Override
	public String getStockBaseInfo() {
		Jedis jedis = null;
		String sRst = null;
        try {
            jedis = pool.getResource();
            String sKey = STOCK_BASEINFO;
            byte[] aBKey = sKey.getBytes(ENCODING);
            byte[] aRst = jedis.get(aBKey);
            sRst = (String)oSerial.deserialize(aRst);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
        return sRst;
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#addOptionStock(java.lang.String, java.lang.String)
	 */
	@Override
	public void addOptionStock(String sOpenId, String sStockCode) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String sKey = FOLLOW_PREFIX + sOpenId;
            byte[] aBKey = sKey.getBytes(ENCODING);
            jedis.sadd(aBKey, oSerial.serialize(sStockCode));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#delOptionStock(java.lang.String, java.lang.String)
	 */
	@Override
	public void delOptionStock(String sOpenId, String sStockCode) {
		Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String sKey = FOLLOW_PREFIX + sOpenId;
            byte[] aBKey = sKey.getBytes(ENCODING);
            jedis.srem(aBKey, oSerial.serialize(sStockCode));
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
	}
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#getOptionStocks(java.lang.String)
	 */
	@Override
	public String getOptionStocks(String sOpenId) {
		Jedis jedis = null;
		StringBuilder oSb = new StringBuilder("");
        try {
            jedis = pool.getResource();
            String sKey = FOLLOW_PREFIX + sOpenId;
            byte[] aBKey = sKey.getBytes(ENCODING);
            Set<byte[]> oSets = jedis.smembers(aBKey);
            if (oSets != null) {
            	Iterator<byte[]> iter = oSets.iterator();
            	while (iter.hasNext()) {
            		String str = (String)oSerial.deserialize(iter.next());
            		if (U.STR.isEmpty(str)) {
            			continue;
            		}
            		oSb.append(str).append(",");
            	}
            	if (oSb.length() > 0 && oSb.charAt(oSb.length() - 1) == ',') {
            		oSb.deleteCharAt(oSb.length() - 1);
            	}
            }
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            //返还到连接池
            pool.returnResourceObject(jedis);
        }
		return oSb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.cfwx.rox.businesssync.market.dao.IQuoteCacheDao#isContainOpenIdKey(java.lang.String)
	 */
	@Override
	public boolean isContainOpenIdKey(String sOpenId) {
		 String sKey = FOLLOW_PREFIX + sOpenId;
		 Jedis jedis = null;
	        try {
	            jedis = pool.getResource();
	            byte[] aBKey = sKey.getBytes(ENCODING);
	            Set<byte[]> oSets = jedis.keys(aBKey);
	            if (oSets != null && !oSets.isEmpty()) {
	            	return true;
	            }
	        } catch (Exception e) {
	            //释放redis对象
	            pool.returnBrokenResource(jedis);
	            e.printStackTrace();
	        } finally {
	            //返还到连接池
	            pool.returnResourceObject(jedis);
	        }
		return false;
	}
}
