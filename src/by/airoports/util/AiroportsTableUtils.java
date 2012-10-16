package by.airoports.util;

import java.util.Map;

import com.google.common.collect.Maps;

public final class AiroportsTableUtils {

	private static final Map<String, String> airoports = Maps.newHashMap();

	static {
		airoports.put("Амстердам", "AMS");
		airoports.put("Астана", "TSE");
	}

	private AiroportsTableUtils() {

	}

	public static Map<String, String> getAiroports() {
		return airoports;
	}

}
