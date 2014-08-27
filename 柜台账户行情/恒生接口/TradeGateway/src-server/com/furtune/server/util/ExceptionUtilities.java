package com.furtune.server.util;

import java.io.IOException;

public class ExceptionUtilities {
	public static void throwUnsupportedOp(String sMsg) {
		throw new UnsupportedOperationException(sMsg);
	}

	public static void throwRunTimeException(String sMsg) {
		throw new RuntimeException(sMsg);
	}

	public static void throwIAE(String sMessage) {
		throw new IllegalArgumentException(sMessage);
	}

	public static void throwRT(String sMessage) {
		throw new RuntimeException(sMessage);
	}

	public static void throwIOE(String sMessage) throws IOException {
		throw new IOException(sMessage);
	}

	public static void throwIGS(String sMessage) {
		throw new IllegalStateException(sMessage);
	}

}
