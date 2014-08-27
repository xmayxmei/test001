package com.furtune.server.util;

public interface IStringTokenizer extends ITokenizer, ResourceConsume {
	public static final byte TOKENIZE_ORDER_FORWARD = 1;
	public static final byte TOKENIZE_ORDER_REVERSE = -1;

	public int countTokens();

	public String getSource();

	public boolean setSource(String paramString);

	public boolean setSource(CharSequence paramCharSequence);

	public boolean setSource(String paramString1, String paramString2);

	public byte getTokenizeOrder();

	public boolean setTokenizeOrder(byte paramByte);

	@Deprecated
	public String nextToken(String paramString);

	public void move(int paramInt);

	public int cursor();
}
