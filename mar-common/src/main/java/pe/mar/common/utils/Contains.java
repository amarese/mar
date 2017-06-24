package pe.mar.common.utils;

import java.util.Collection;
import java.util.Map;

public class Contains {
	public static <T> boolean collection(Collection<T> collection, T item) {
		return collection != null && collection.contains(item);
	}

	public static <K, V> boolean map(Map<K, V> map, K key) {
		return map != null && map.containsKey(key);
	}

	public static <K, V> boolean map(Map<K, V> map, K key, V value) {
		if (map == null) {
			return false;
		}
		if (key == null || !map.containsKey(key)) {
			return false;
		}

		V stored = map.get(key);
		if (stored == null && value == null) {
			return true;
		}

		return value != null && value.equals(stored);
	}
}
