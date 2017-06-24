package pe.mar.common.utils;

import lombok.Getter;

public class Length {
	@Getter
	private int value;

	public Length(int len) {
		this.value = len;
	}

	public static Length of(String str) {
		if (str == null) {
			return new Length(0);
		}
		return new Length(str.length());
	}

	public boolean between(int left, int right) {
		return value >= left && value <= right;
	}

	public boolean outOf(int left, int right) {
		return value < left || value > right;
	}

	public boolean is(int size) {
		return value == size;
	}
}
