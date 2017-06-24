package pe.mar.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class Longs {
	public static List<Integer> toIntegers(Collection<Long> longs, boolean incudeNull) {
		if (longs == null) {
			return null;
		}

		List<Integer> integers = new ArrayList<>();
		for (Long each : longs) {
			if (incudeNull || each != null) {
				integers.add(each != null ? each.intValue() : null);
			}
		}
		return integers;
	}

	public static List<Integer> toIntegers(Collection<Long> longs) {
		return toIntegers(longs, true);
	}

	public static Long popWithout(Collection<Long> longs, Long... withouts) {
		if (longs == null) {
			return null;
		}

		for (Long each : longs) {
			if (!ArrayUtils.contains(withouts, each)) {
				return each;
			}
		}
		return null;
	}
}
