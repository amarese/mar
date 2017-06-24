package pe.mar.common.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class Empty {
	public static <T> ArrayList<T> list() {
		return new ArrayList<>();
	}

	public static <T> LinkedHashSet<T> set() {
		return new LinkedHashSet<>();
	}

	public static <K, V> LinkedHashMap<K, V> map() {
		return new LinkedHashMap<>();
	}
}