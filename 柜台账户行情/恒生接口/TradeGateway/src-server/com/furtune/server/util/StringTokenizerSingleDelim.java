package com.furtune.server.util;


import java.util.NoSuchElementException;

class StringTokenizerSingleDelim extends AbstractStringTokenizer {
	protected static byte NOT_FOUND = -1;
	protected static byte REVERSE_INCREMENT_AMOUNT = -2;
	protected static byte FORWARD_INCREMENT_AMOUNT = 1;
	protected int m_iCurrentPosition;
	protected int m_iNewPosition;
	protected int m_iMaxPosition;
	protected int m_iTotalToken;
	protected byte m_bAmount = FORWARD_INCREMENT_AMOUNT;
	protected char m_cDelim;

	public StringTokenizerSingleDelim(String str, String sDelim) {
		super(str, sDelim);
	}

	public int countTokens() {
		if (this.m_iTotalToken == NOT_FOUND) {
			int iOldPosition = this.m_iCurrentPosition;
			int iOldNewPosition = this.m_iNewPosition;

			resetCursor();

			int iCount = 0;
			while (hasMoreTokens()) {
				skipToken();
				iCount++;
			}
			this.m_iTotalToken = iCount;

			this.m_iCurrentPosition = iOldPosition;
			this.m_iNewPosition = iOldNewPosition;
		}
		return this.m_iTotalToken;
	}

	protected void sourceChanged(String str, String sDelim) {
		resetCursor();

		this.m_cDelim = (StringUtilities.isEmpty(sDelim) ? null : Character.valueOf(sDelim.charAt(0))).charValue();

		this.m_iTotalToken = NOT_FOUND;
	}

	protected void tokenizeOrderChanged(byte bTokenizeOrder) {
		resetCursor();
	}

	private void resetCursor() {
		this.m_iNewPosition = -1;

		if (this.m_bMode == -1) {
			this.m_iCurrentPosition = this.m_sSrc.length();
			this.m_iMaxPosition = 0;
			this.m_bAmount = REVERSE_INCREMENT_AMOUNT;
		} else {
			this.m_iCurrentPosition = 0;
			this.m_iMaxPosition = this.m_sSrc.length();
			this.m_bAmount = FORWARD_INCREMENT_AMOUNT;
		}

		if (this.m_iCurrentPosition == this.m_iMaxPosition)
			this.m_iNewPosition = this.m_iMaxPosition;
	}

	public boolean hasMoreTokens() {
		return this.m_bMode == -1 ? hasMoreTokensReverse() : hasMoreTokensForward();
	}

	private boolean hasMoreTokensReverse() {
		if (this.m_iCurrentPosition <= 0)
			return false;

		if (this.m_iNewPosition != NOT_FOUND)
			return true;

		int iNextPos = this.m_sSrc.lastIndexOf(this.m_cDelim, this.m_iCurrentPosition);
		if (iNextPos == NOT_FOUND) {
			iNextPos = 0;
		}

		this.m_iNewPosition = (iNextPos + 1);
		return true;
	}

	private boolean hasMoreTokensForward() {
		if (this.m_iCurrentPosition >= this.m_iMaxPosition)
			return false;

		if (this.m_iNewPosition != NOT_FOUND)
			return true;

		int iNextPos = this.m_sSrc.indexOf(this.m_cDelim, this.m_iCurrentPosition);

		if (iNextPos == NOT_FOUND) {
			iNextPos = this.m_iMaxPosition;
		}
		this.m_iNewPosition = iNextPos;
		return true;
	}

	public String nextToken() {
		if (this.m_iNewPosition == NOT_FOUND) {
			if (!hasMoreTokens())
				throw new NoSuchElementException();
		}
		String sResult;
		if (this.m_bMode == -1)
			sResult = this.m_sSrc.substring(this.m_iNewPosition, this.m_iCurrentPosition);
		else {
			sResult = this.m_sSrc.substring(this.m_iCurrentPosition, this.m_iNewPosition);
		}

		nextPosition();
		return sResult;
	}

	public boolean skipToken() {
		if (hasMoreTokens()) {
			nextPosition();
			return true;
		}
		return false;
	}

	public void move(int iIdx) {
		this.m_iCurrentPosition = iIdx;
		this.m_iNewPosition = NOT_FOUND;
	}

	public int cursor() {
		return this.m_iCurrentPosition;
	}

	protected void nextPosition() {
		this.m_iCurrentPosition = (this.m_iNewPosition + this.m_bAmount);
		this.m_iNewPosition = NOT_FOUND;
	}
}