package com.furtune.server.util;



abstract class AbstractStringTokenizer implements IStringTokenizer {
	protected String m_sDelim;
	protected String m_sSrc;
	protected byte m_bMode = 1;

	public AbstractStringTokenizer(String str, String sDelim) {
		setSource(str, sDelim);
		setTokenizeOrder((byte) 1);
	}

	public String getSource() {
		return this.m_sSrc;
	}

	public byte getTokenizeOrder() {
		return this.m_bMode;
	}

	public boolean setSource(String str) {
		setSource(str, this.m_sDelim);
		return false;
	}

	public boolean setSource(CharSequence cs) {
		if ((cs instanceof String)) {
			return setSource((String) cs);
		}
		return false;
	}

	public boolean setSource(String str, String sDelim) {
		this.m_sSrc = str;
		this.m_sDelim = sDelim;
		sourceChanged(str, sDelim);
		return true;
	}

	protected abstract void sourceChanged(String paramString1, String paramString2);

	public boolean setTokenizeOrder(byte bTokenizeOrder) {
		if (this.m_bMode != bTokenizeOrder) {
			this.m_bMode = bTokenizeOrder;

			setSource(this.m_sSrc, this.m_sDelim);

			tokenizeOrderChanged(this.m_bMode);
			return true;
		}
		return false;
	}

	protected abstract void tokenizeOrderChanged(byte paramByte);

	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	public Object nextElement() {
		return nextToken();
	}

	public double nextToken2D() {
		return StringUtilities.parseDouble(nextToken());
	}

	public float nextToken2F() {
		return StringUtilities.parseFloat(nextToken());
	}

	public int nextToken2I() {
		return StringUtilities.parseInteger(nextToken());
	}

	public long nextToken2L() {
		return StringUtilities.parseLong(nextToken());
	}

	public boolean skipToken() {
		if (!hasMoreTokens()) {
			return false;
		}
		nextToken();
		return true;
	}

	public int skipToken(int iN) {
		if (iN <= 0) {
			throw new IllegalArgumentException("The number of skip token must greater than zero.");
		}
		int iCount = 0;
		while ((iCount < iN) && (skipToken())) {
			iCount++;
		}

		return iCount;
	}

	@Override
	public void clearResource() {
		this.m_sDelim = null;
		this.m_sSrc = null;
	}

	@Deprecated
	public String nextToken(String delim) {
		return null;
	}
}