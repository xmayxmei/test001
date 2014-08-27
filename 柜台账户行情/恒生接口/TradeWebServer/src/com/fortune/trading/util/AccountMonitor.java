/*
 * FileName: AccountMonitor.java
 * Copyright: Copyright 2014-6-4 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: 账号锁定监听器
 *
 */
package com.fortune.trading.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import com.fortune.trading.entity.AccountLoginRecord;

/**
 * <code>AccountMonitor<code> 每隔一段时间检查账号的锁定情况，如果锁定的时间过期，则会释放。
 *
 * @author Jimmy
 * @since Tebon - Trading v0.0.1(June 4, 2014)
 *
 */
public class AccountMonitor {
  protected Logger L = Logger.getLogger(getClass());
  
  private static final long PERIOD = 5000;
  
  private final Map<String, AccountLoginRecord> RECORDS ;
  
  private static volatile AccountMonitor instance;
  
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  
  private ScheduledExecutorService oService;
  
  private AccountMonitor() {
    RECORDS = new ConcurrentHashMap<String, AccountLoginRecord>();
  }
  /**
   * One instance.
   * @return
   */
  public static AccountMonitor getInstance() {
    if (instance == null) {
      instance = new AccountMonitor();
    }
    return instance;
  }
  /**
   * 
   * @param sClientId
   * @param isPswError
   */
  public void recordAccountLogin(String sClientId, boolean isPswError) {
    if(!isPswError) {
      RECORDS.remove(sClientId);
    } else {
      AccountLoginRecord oRecord = RECORDS.get(sClientId);
      if (oRecord == null) {
        oRecord = new AccountLoginRecord();
        RECORDS.put(sClientId, oRecord);
      }
      oRecord.setErrorCount(oRecord.getErrorCount() + 1);
      oRecord.setLastLoginTime(System.currentTimeMillis());
    }
  }
  /**
   * 
   * @param sClientId
   * @return
   */
  public int getRemindErrorCount(String sClientId) {
    AccountLoginRecord oRecord = RECORDS.get(sClientId);
    if (oRecord == null) {
      return Constants.limitedTimes;
    }
    return Constants.limitedTimes - oRecord.getErrorCount();
  }
  /**
   * 判断指定为<code>sClientId</code>的客户号是否锁定。
   * 
   * @param sClientId
   * @return
   */
  public boolean isAccountLock(String sClientId) {
    if (sClientId == null) {
      return false;
    }
    AccountLoginRecord oRecord = RECORDS.get(sClientId);
    if (oRecord != null && oRecord.getErrorCount() >= Constants.limitedTimes) {
      return true;
    }
    return false;
  }
  /**
   * 开始监听
   */
  public void startMonitor() {
    if (isRunning.get()) {
      return ;
    }
    L.info("Start Monitor.");
    oService = Executors.newScheduledThreadPool(1);
    oService.scheduleAtFixedRate(new Monitor(), PERIOD, PERIOD, TimeUnit.MILLISECONDS);
    isRunning.set(true);
  }
  /**
   * 停止监听
   */
  public void stopMonitor() {
    if (!isRunning.get()) {
      return ;
    }
    L.info("Stop Monitor.");
    oService.shutdown();
    isRunning.set(false);
  }
  /**
   * <code>Monitor<code> 
   */
  class Monitor implements Runnable {
    @Override
    public void run() {
        Set<String> oKeys = RECORDS.keySet();
        Set<String> oKeysCopy = new HashSet<String>();
        if (oKeys == null || oKeys.isEmpty()) {
          return ;
        }
        oKeysCopy.addAll(oKeys);
        Iterator<String> iter = oKeysCopy.iterator();
        long lCurrentTime = System.currentTimeMillis();
        while (iter.hasNext()) {
          String sKey = iter.next();
          AccountLoginRecord record = RECORDS.get(sKey);
          if (record == null) {
            continue;
          }
          long lLast = record.getLastLoginTime();
          if ((lCurrentTime - lLast) > Constants.accLockTimes) {
            RECORDS.remove(sKey);
          }
        }
    }
  }
  
}
