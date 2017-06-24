package pe.mar.common.utils;

import java.util.Collection;
import java.util.Map;

public class IsEmpty {
	public static boolean collection(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}

	public static <T> boolean array(T[] array) {
		return array == null || array.length == 0;
	}

	public static boolean map(Map<?, ?> map) {
		return map == null || map.size() == 0;
	}

	public static boolean string(String str) {
		return str == null || "".equals(str);
	}

	public static boolean number(Number num) {
		return num == null || num.longValue() == 0;
	}
}
