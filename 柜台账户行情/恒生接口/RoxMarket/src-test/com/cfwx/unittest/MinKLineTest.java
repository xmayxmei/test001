/**
 * 
 */
package com.cfwx.unittest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.cfwx.rox.businesssync.market.show.MinLine;
import com.cfwx.rox.businesssync.market.show.TimeShare;
import com.cfwx.util.U;

import junit.framework.TestCase;

/**
 * @author Jimmy
 *
 * 2014-7-7
 */
public class MinKLineTest extends TestCase{
	
	public static List<TimeShare> getTimeShareData(int iPoints) {
		List<TimeShare> lst = new ArrayList<TimeShare>();
		long lS = 1404783020237l;
		long lSPM = 1404882000984L;
		for (int i = 0; i < iPoints && i < 120; i++) {
			TimeShare ts = new TimeShare();
			ts.setAmount("2000");
			ts.setVolume("1000");
			ts.setNewPrice(String.valueOf(4.2 + new Random().nextInt(4)));
			ts.setTime(String.valueOf(lS + 60000 * i));
			ts.setZe(100);
			ts.setZl(1000);
			lst.add(ts);
			System.out.println(ts.getTime() + "," + ts.getNewPrice() + "," + ts.getAmount() + "," + ts.getZe() + "," + ts.getZl());
		}
		for (int i = 0; i < iPoints - 120; i++) {
			TimeShare ts = new TimeShare();
			ts.setAmount("2000");
			ts.setVolume("1000");
			ts.setNewPrice(String.valueOf(4.2 + new Random().nextInt(4)));
			ts.setTime(String.valueOf(lSPM + 60000 * i));
			ts.setZe(100);
			ts.setZl(1000);
			lst.add(ts);
			System.out.println(new Date(Long.parseLong(ts.getTime())) + "," + ts.getTime() + "," + ts.getNewPrice() + "," + ts.getAmount() + "," + ts.getZe() + "," + ts.getZl());
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}
	@Test
	public void testGenerate5MinKLine() {
		int iPoint = 3;
		List<MinLine> lst = U.K.generate5MinLine(getTimeShareData(iPoint), true);
		assertNotNull(lst);
		for (MinLine min : lst) {
			System.err.println(min.getTime() + "," + min.getDate() + ",dateTime:" + min.getDateTime() + ",open:" + min.getOpen() 
					+ ",close" + min.getClose() + ",vol:" + min.getVolume() + ",amount:" + min.getAmount() 
					+ ",high:" + min.getHigh() + ",low:"+min.getLow());
		}
	}
	@Test
	public void testGenerate15MinKLine() {
		int iPoint = 121;
		List<MinLine> lst = U.K.generate15MinLine(getTimeShareData(iPoint), true);
		assertNotNull(lst);
		
		for (MinLine min : lst) {
			System.err.println(min.getTime() + "," + min.getDate() + ",dateTime:" + min.getDateTime() + ",open:" + min.getOpen() 
					+ ",close" + min.getClose() + ",vol:" + min.getVolume() + ",amount:" + min.getAmount() 
					+ ",high:" + min.getHigh() + ",low:"+min.getLow());
		}
	}
	@Test
	public void testGenerate30MinKLine() {
		int iPoint = 60;
		List<MinLine> lst = U.K.generate30MinLine(getTimeShareData(iPoint), true);
		assertNotNull(lst);
		
		for (MinLine min : lst) {
			System.err.println(min.getTime() + "," + min.getDate() + ",dateTime:" + min.getDateTime() + ",open:" + min.getOpen() 
					+ ",close" + min.getClose() + ",vol:" + min.getVolume() + ",amount:" + min.getAmount() 
					+ ",high:" + min.getHigh() + ",low:"+min.getLow());
		}
	}
	@Test
	public void testGenerate60MinKLine() {
		int iPoint = 240;
		List<MinLine> lst = U.K.generate60MinLine(getTimeShareData(iPoint), true);
		assertNotNull(lst);
		
		for (MinLine min : lst) {
			System.err.println(min.getTime() + "," + min.getDate() + ",dateTime:" + min.getDateTime() + ",open:" + min.getOpen() 
			+ ",close" + min.getClose() + ",vol:" + min.getVolume() + ",amount:" + min.getAmount() 
			+ ",high:" + min.getHigh() + ",low:"+min.getLow());
		}
	}
	
	
}
