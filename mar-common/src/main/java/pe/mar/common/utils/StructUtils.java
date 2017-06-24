package pe.mar.common.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;

public class StructUtils {
	public interface Supplier<T> {
		T get();
	}

	@SuppressWarnings("unchecked")
	public static <C> C cast(Object o, Class<C> clazz) {
		return (C) o;
	}

	@SafeVarargs
	public static <T> ArrayList<T> L(T... items) {
		ArrayList<T> list = new ArrayList<>();
		if (items == null || items.length == 0) {
			return list;
		}
		for (T each : items) {
			list.add(each);
		}
		return list;
	}

	@SafeVarargs
	public static <T> HashSet<T> S(T... items) {
		HashSet<T> set = new HashSet<>();
		if (items == null || items.length == 0) {
			return set;
		}
		for (T each : items) {
			set.add(each);
		}
		return set;
	}

	public static <T> T ifnull(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public static <T> T ifnull(T value, Supplier<T> supplier) {
		return value == null ? supplier.get() : value;
	}

	public static <T extends Number> T ifNullOrZero(T value, T defaultValue) {
		return value == null || value.longValue() == 0L ? defaultValue : value;
	}

	public static <T extends Number> T ifNullOrNotPositive(T value, T defaultValue) {
		return value == null || value.longValue() < 0L ? defaultValue : value;
	}

	public static String ifempty(String value, String defaultValue) {
		return value == null || "".equals(value) ? defaultValue : value;
	}

	public static long ifNumeric(String maybeNumeric, long defaultValue) {
		if (Is.numeric(maybeNumeric)) {
			try {
				return NumberFormat.getInstance().parse(maybeNumeric).longValue();
			} catch (ParseException ignore) {}
		}
		return defaultValue;
	}

	public static int ifNumeric(String maybeNumeric, int defaultValue) {
		return (int) ifNumeric(maybeNumeric, (long) defaultValue);
	}
}