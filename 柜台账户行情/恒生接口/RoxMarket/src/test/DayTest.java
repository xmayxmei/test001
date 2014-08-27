package test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.cfwx.rox.businesssync.market.show.TimeShare;

/**
 * @author J.C.J
 *         2013-12-3
 */
public class DayTest
{

	// @org.junit.Test
	public void test1()
	{
		DataInputStream is = null;
		try
		{
			int row = 0;
			is = new DataInputStream(new FileInputStream("D://day/000002.day"));
			while (is.available() > 0)
			{
				row++;
				System.out.print(is.readInt()); // 日期 Date Unsigned long 4
				System.out.print("  ");
				System.out.print(is.readInt()); // 开盘指数 Open long 4 X1000
												// 0—2147483点
				System.out.print("  ");
				System.out.print(is.readInt()); // 最高指数 High long 4 X1000
												// 0—2147483点
				System.out.print("  ");
				System.out.print(is.readInt()); // 最低指数 Low long 4 X1000
												// 0—2147483点
				System.out.print("  ");
				System.out.print(is.readInt()); // 收盘指数 Close long 4 X1000
												// 0—2147483点
				System.out.print("  ");
				System.out.print(is.readInt()); // 成交金额 Amount Unsigned long 4
												// 千元 0—42949亿元
				System.out.print("  ");
				System.out.print(is.readInt()); // 成交量 Volume Unsigned long 4 百股
												// 0—4294亿股
				System.out.print("  ");
				System.out.print(is.readInt()); // 成交笔数 Record Unsigned long 4
				System.out.print("  ");
				System.out.print(is.readUnsignedShort()); // 上涨家数 Rise Unsigned
															// int 2
				System.out.print("  ");
				System.out.print(is.readUnsignedShort()); // 下跌家数 Down Unsigned
															// int 2
				System.out.print("  ");
				System.out.print(is.readInt()); // 指数基准 Base long 4 X1000
												// 0—2147483点
				System.out.println("");
			}
			System.out.println("行数:" + row);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// @Test
	public void test2()
	{
		InputStream is = null;
		try
		{
			is = new FileInputStream("D://day/000002.day");
			while (is.read() != -1)
			{
				// 1 日期 Date Unsigned long 4
				byte[] buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 2 开盘指数 Open long 4 X1000 0—2147483点
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 3 最高指数 High long 4 X1000 0—2147483点
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 4 最低指数 Low long 4 X1000 0—2147483点
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 5 收盘指数 Close long 4 X1000 0—2147483点
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 6 成交金额 Amount Unsigned long 4 千元 0—42949亿元
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 7 成交量 Volume Unsigned long 4 百股 0—4294亿股
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 8 成交笔数 Record Unsigned long 4
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 9 上涨家数 Rise Unsigned int 2
				buf = new byte[2];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 10 下跌家数 Down Unsigned int 2
				buf = new byte[2];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.print(" ");

				// 11 指数基准 Base long 4 X1000 0—2147483点
				buf = new byte[4];
				is.read(buf, 0, buf.length);
				System.out.print(new String(buf));
				System.out.println("");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// @Test
	public void test3()
	{
		// 文件追加
		TimeShare ts = new TimeShare();
		ts.setAverage("15454343");
		ts.setNewPrice("2048.555");
		ts.setTime("46846546546");
		ts.setVolume("56456465445");
		ts.setZe(54564454564D);
		ts.setZl(6545646456454D);
		FileWriter fw = null;
		try
		{
			long s = System.currentTimeMillis();
			for (int i = 0; i < 5000; i++)
			{
				String a = "a" + i;
				fw = new FileWriter("D://test/" + a + ".txt", true);
				StringBuffer sb = new StringBuffer();
				sb.append("123123123").append(",");
				sb.append("2048.555").append(",");
				sb.append("123123123").append(",");
				sb.append("46846546546").append(",");
				sb.append("54564454564D").append(",");
				sb.append("6545646456454D");
				sb.append("\r\n");
				fw.write(sb.toString());
				fw.close();
			}
			long e = System.currentTimeMillis();
			System.out.println(e - s);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{

		}

	}

	/*
	 * @Test
	 * public void test4() {
	 * //文件追加
	 * try {
	 * long s =System.currentTimeMillis();
	 * for(int i=0;i<5000;i++){
	 * String a ="a"+i;
	 * BufferedReader br = new BufferedReader(new FileReader("D://test/"+a+".txt"));
	 * String ob = "";
	 * long ss =System.currentTimeMillis();
	 * while(br.read()!=-1){
	 * ob += br.readLine();
	 * }
	 * br.close();
	 * long ee =System.currentTimeMillis();
	 * System.out.println(ee-ss);
	 * }
	 * long e =System.currentTimeMillis();
	 * System.out.println(e-s);
	 * } catch (Exception e) {
	 * e.printStackTrace();
	 * }finally{
	 * }
	 * }
	 */
}
