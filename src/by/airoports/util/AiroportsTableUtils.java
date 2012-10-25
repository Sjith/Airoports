package by.airoports.util;

import java.util.Map;

import com.google.common.collect.Maps;

public final class AiroportsTableUtils {

	private static final Map<String, String> airoports = Maps.newHashMap();

	static {
		airoports.put("���������", "AMS");
		airoports.put("������", "TSE");
		airoports.put("�������", "ASB");
		airoports.put("����", "GYD");
		airoports.put("���������", "BCN");
		airoports.put("������", "BUS");
		airoports.put("������", "BEY");
		airoports.put("������", "BER");
	}

	private AiroportsTableUtils() {

	}

	public static Map<String, String> getAiroports() {
		return airoports;
	}

}
