package pe.mar.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Pair<A, B> {
	private A left;

	private B right;

	public static Pair<String, Object> P(String left, Object right) {
		return new Pair<>(left, right);
	}
}