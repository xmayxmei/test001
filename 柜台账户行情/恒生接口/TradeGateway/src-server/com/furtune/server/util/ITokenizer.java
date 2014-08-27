package com.furtune.server.util;

interface ITokenizer {
	public boolean hasMoreElements();

	public boolean hasMoreTokens();

	public Object nextElement();

	public String nextToken();

	public float nextToken2F();

	public double nextToken2D();

	public long nextToken2L();

	public int nextToken2I();

	public boolean skipToken();

	public int skipToken(int paramInt);
}