package by.airoports.util;

import java.util.Map;

import com.google.common.collect.Maps;

public final class AiroportsTableUtils {

	private static final Map<String, String> airoports = Maps.newHashMap();

	static {
		airoports.put("Амстердам", "AMS");
		airoports.put("Астана", "TSE");
		airoports.put("Ашхабад", "ASB");
		airoports.put("Баку", "GYD");
		airoports.put("Барселона", "BCN");
		airoports.put("Батуми", "BUS");
		airoports.put("Бейрут", "BEY");
		airoports.put("Берлин", "BER");
	}

	private AiroportsTableUtils() {

	}

	public static Map<String, String> getAiroports() {
		return airoports;
	}

}
