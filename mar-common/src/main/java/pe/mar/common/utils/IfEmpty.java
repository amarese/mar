package pe.mar.common.utils;

import java.util.Collection;
import java.util.Map;

public class IfEmpty {
	public static <T extends Collection<?>> T collection(T collection, T defaultCollection) {
		return collection == null || collection.size() == 0 ? defaultCollection : collection;
	}

	public static <T extends Map<?, ?>> T map(T map, T defaultMap) {
		return map == null || map.size() == 0 ? defaultMap : map;
	}

	public static String string(String str, String defaultStr) {
		return IsNotEmpty.string(str) ? str : defaultStr;
	}
}