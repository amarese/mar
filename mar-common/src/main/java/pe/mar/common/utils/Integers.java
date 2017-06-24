package pe.mar.common.utils;

import static pe.mar.common.utils.StructUtils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Integers {
	public static List<Long> toLongs(Collection<Integer> integers, boolean excludeNull) {
		if (integers == null) {
			return null;
		}

		List<Long> longs = new ArrayList<>();
		for (Integer each : integers) {
			if (!excludeNull || each != null) {
				longs.add(each != null ? each.longValue() : null);
			}
		}

		return longs;
	}

	public static List<Long> toLongs(Collection<Integer> integers) {
		return toLongs(integers, false);
	}

	public static int sum(Collection<Integer> integers) {
		int sum = 0;
		if (integers != null) {
			for (Integer each : integers) {
				sum += ifnull(each, 0);
			}
		}
		return sum;
	}
}
