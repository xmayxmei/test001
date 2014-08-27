/*
 * FileName: AccountLoginRecord.java
 * Copyright: Copyright 2014-6-4 Cfwx Tech. Co. Ltd.All right reserved.
 * Description: 
 *
 */
package com.fortune.trading.entity;

/**
 * <code>AccountLoginRecord<code> 
 *
 * @author Jimmy
 * @since Tebon - Trading v0.0.1(June 4, 2014)
 *
 */
public class AccountLoginRecord {
  /** 客户号 */
  private String clientId;
  /** 最后一次登录时间 */
  private long lastLoginTime;
  /** 输入错误的次数 */
  private int errorCount = 0;
  
  public String getClientId() {
    return clientId;
  }
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }
  public long getLastLoginTime() {
    return lastLoginTime;
  }
  public void setLastLoginTime(long lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }
  public int getErrorCount() {
    return errorCount;
  }
  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }
}
