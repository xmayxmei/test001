/**
 * 
 */
package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

import com.cfwx.rox.businesssync.market.show.TimeShare;

/**
 * @author J.C.J
 *         2013-11-5
 */
public class TestA
{

	public static void main(String args[])
	{

		try
		{
			NumberFormat nf3 = NumberFormat.getInstance();
			nf3.setRoundingMode(RoundingMode.HALF_UP);
			nf3.setMaximumFractionDigits(2);
			nf3.setMinimumFractionDigits(2);
			nf3.setGroupingUsed(false);

			int i = 0;

			double b = 8 / 3.21;

			System.out.println(i++ > 0);
			System.out.println(b);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static boolean isCanRead()
	{
		try
		{
			File fos = new File("D://marketcache/timeshare.ghost");
			return fos.canExecute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private static void read()
	{
		try
		{
			FileInputStream fis = new FileInputStream("D://a.ghost");

			ObjectInputStream oin = new ObjectInputStream(fis);

			List<TimeShare> list = (List<TimeShare>) oin.readObject();

			System.out.println(list.get(0).getAverage());
			System.out.println(list.get(0).getNewPrice());
			System.out.println(list.get(0).getTime());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void main1(String[] args) throws Exception
	{
		double aa = 548.9093444909345;
		String bb = "0.009";
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setMaximumFractionDigits(0);
		// nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(false);

		// System.out.println(nf.format(nf.parse(a)));
		//
		BigDecimal a = new BigDecimal(aa);
		BigDecimal b = new BigDecimal(bb);

		BigDecimal zero = new BigDecimal(0);

		System.out.println(b.intValue());

		System.out.println(nf.parse(a.divide(b, 6).multiply(new BigDecimal(1000)) + ""));
		// System.out.println(ba.ROUND_UNNECESSARY);
		// System.out.println(bb);
		// System.out.println(bb.ROUND_UNNECESSARY);
		// System.out.println(nf.parse(a.divide(b,6).multiply(new BigDecimal(1000))+""));
	}
}
