package com.furtune.server.util;



import java.util.NoSuchElementException;
import java.util.StringTokenizer;


class StringTokenizerBasic extends AbstractStringTokenizer {
	private StringTokenizer m_stDelegates;
	private String[] m_asTokenCache = null;
	private int m_iTokenRemaining;

	StringTokenizerBasic(String str, String sDelim) {
		super(str, sDelim);
	}

	public int countTokens() {
		if (this.m_bMode == -1) {
			return this.m_iTokenRemaining + 1;
		}
		return this.m_stDelegates.countTokens();
	}

	protected void sourceChanged(String str, String sDelim) {
		this.m_stDelegates = new StringTokenizer(str, sDelim);

		if (this.m_bMode == -1)
			buildReverseTokenCache();
	}

	protected void tokenizeOrderChanged(byte bMode) {
		if (bMode == 1) {
			this.m_asTokenCache = null;
			this.m_iTokenRemaining = -1;
		} else {
			buildReverseTokenCache();
		}
	}

	private void buildReverseTokenCache() {
		this.m_iTokenRemaining = this.m_stDelegates.countTokens();
		this.m_asTokenCache = new String[this.m_iTokenRemaining];

		int iCount = 0;
		while (this.m_stDelegates.hasMoreTokens()) {
			this.m_asTokenCache[(iCount++)] = this.m_stDelegates.nextToken();
		}

		this.m_iTokenRemaining -= 1;
	}

	public boolean hasMoreTokens() {
		if (this.m_bMode == -1) {
			return this.m_iTokenRemaining >= 0;
		}
		return this.m_stDelegates.hasMoreTokens();
	}

	public String nextToken() {
		if (this.m_bMode == -1) {
			if (this.m_iTokenRemaining < 0) {
				throw new NoSuchElementException();
			}
			return this.m_asTokenCache[(this.m_iTokenRemaining--)];
		}

		return this.m_stDelegates.nextToken();
	}

	public void move(int iIdx) {
		ExceptionUtilities.throwUnsupportedOp("Unsupported operation");
	}

	public int cursor() {
		ExceptionUtilities.throwUnsupportedOp("Unsupported operation");
		return -1;
	}

	public void clearResource() {
		super.clearResource();
		this.m_asTokenCache = null;
		this.m_stDelegates = null;
	}
}