package pe.mar.common.utils;

import org.apache.commons.lang3.StringUtils;

public class IsNot {
	public static boolean positive(Number number) {
		return number != null && number.longValue() < 0;
	}

	public static boolean negative(Number number) {
		return number != null && number.longValue() > 0;
	}

	public static boolean numeric(CharSequence cs) {
		return !StringUtils.isNumeric(cs);
	}
}