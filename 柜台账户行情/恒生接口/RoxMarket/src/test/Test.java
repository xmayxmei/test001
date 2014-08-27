/**
 * 
 */
package test;


/**
 * @author J.C.J
 *         2013-11-4
 */
public class Test
{

	public static void main(String[] args)
	{
		// System.out.println("\u4f01\u4e1a\u503a,\u56fd\u503a\u56de\u8d2d,\u5730\u65b9\u653f\u5e9c\u503a,\u53ef\u8f6c\u6362\u503a\u5238,\u79c1\u52df\u503a,\u56fd\u503a\u73b0\u8d27,\u516c\u53f8\u503a;2");
		System.out.println(toUnicode("可交换私募债"));
		//
	}

	// 债券=企业债,国债回购,地方政府债,可转换债券,私募债,国债现货,公司债|2
	// A股=A股|2
	// B股=B股|2
	// 指数=指数|2
	public static String toUnicode(String s)
	{
		String as[] = new String[s.length()];
		String s1 = "";
		for (int i = 0; i < s.length(); i++)
		{
			as[i] = Integer.toHexString(s.charAt(i) & 0xffff);
			s1 = s1 + "\\u" + as[i];
		}
		return s1;
	}

}
