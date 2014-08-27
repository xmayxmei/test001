package com.furtune.server.util;

public class TokenizerFactory {
	private static TokenizerFactory SINGLETON = new TokenizerFactory();

	static TokenizerFactory getSingletonInstance() {
		return SINGLETON;
	}

	public static IStringTokenizer createTokenizer(String str, String sDelim) {
		return SINGLETON.createTokenizerImpl(str, sDelim);
	}

	public static ITokenizer createTokenizer(String sDelim) {
		return SINGLETON.createTokenizerImpl("", sDelim);
	}

	public static IStringTokenizer createJavaTokenizer(String str, String sDelim) {
		return SINGLETON.createJavaTokenizerImpl(str, sDelim);
	}

	public static IStringTokenizer createSingleDelimTokenizer(String str, String sDelim) {
		return new StringTokenizerSingleDelim(str, sDelim);
	}

	protected IStringTokenizer createTokenizerImpl(String str, String sDelim) {
		if (sDelim.length() > 1) {
			return createJavaTokenizer(str, sDelim);
		}

		return createSingleDelimTokenizer(str, sDelim);
	}

	protected IStringTokenizer createJavaTokenizerImpl(String str, String sDelim) {
		return new StringTokenizerBasic(str, sDelim);
	}

	protected ITokenizer createSingleDelimTokenizerImpl(String str, String sDelim) {
		return new StringTokenizerSingleDelim(str, sDelim);
	}
}