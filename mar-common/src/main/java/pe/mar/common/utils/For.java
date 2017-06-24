package pe.mar.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;

public class For {
	public interface Index {
		int get();

		int size();

		boolean isFirst();

		boolean isLast();

		void stop();
	}

	@ToString
	public static class IndexImpl implements Index {
		private boolean stop;

		private int i = -1;

		private final int size;

		private IndexImpl(int size) {
			this.size = size;
		}

		@Getter
		private boolean first = true;

		@Getter
		private boolean last = false;

		@Override
		public int get() {
			return i;
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public void stop() {
			stop = true;
		}

		private IndexImpl at() {
			i++;
			this.first = i == 0;
			this.last = i == size - 1;

			return this;
		}
	}

	private IndexImpl index;

	private For() {}

	private For(int size) {
		index = new IndexImpl(size);
	}

	public interface Consumer<U> {
		void accept(U u);
	}

	public interface MultipleConsumer<U1, U2> {
		void accept(U1 u1, U2 u2);
	}

	public interface TripleConsumer<U1, U2, U3> {
		void accept(U1 u1, U2 u2, U3 u3);
	}

	public interface IndexedConsumer<U> {
		void accept(Index i, U u);
	}

	public interface IndexedMultipleConsumer<U1, U2> {
		void accept(Index i, U1 u1, U2 u2);
	}

	public interface IndexedTripleConsumer<U1, U2, U3> {
		void accept(Index i, U1 u1, U2 u2, U3 u3);
	}

	public interface KeyValueConsumer<K, V> {
		void accept(K key, V value);
	}

	public interface IndexedKeyValueConsumer<K, V> {
		void accept(Index i, K key, V value);
	}

	public static <U> void each(U[] array, Consumer<U> consumer) {
		if (IsEmpty.array(array)) {
			return;
		}

		for (U each : array) {
			consumer.accept(each);
		}
	}

	public static <U> void each(U[] array, IndexedConsumer<U> consumer) {
		if (IsEmpty.array(array)) {
			return;
		}

		For f = new For(array.length);

		for (U each : array) {
			//			consumer.accept(f.index.at(i, i == 0, i == array.length - 1), each);
			if (!f.index.stop) {
				consumer.accept(f.index.at(), each);
			}
		}
	}

	public static <U> void each(Collection<U> collection, Consumer<U> consumer) {
		if (IsEmpty.collection(collection)) {
			return;
		}

		Iterator<U> itr = collection.iterator();
		while (itr.hasNext()) {
			consumer.accept(itr.next());
		}
	}

	public static <U1, U2> void each(Collection<U1> collection1, Collection<U2> collection2, MultipleConsumer<U1, U2> consumer) {
		if (IsEmpty.collection(collection1)) {
			return;
		}

		if (IsEmpty.collection(collection2) || collection1.size() != collection2.size()) {
			throw new IllegalArgumentException("two collections should have same size");
		}

		Iterator<U1> itr1 = collection1.iterator();
		Iterator<U2> itr2 = collection2.iterator();
		while (itr1.hasNext()) {
			consumer.accept(itr1.next(), itr2.next());
		}
	}

	public static <U1, U2, U3> void each(Collection<U1> collection1, Collection<U2> collection2, Collection<U3> collection3, TripleConsumer<U1, U2, U3> consumer) {
		if (IsEmpty.collection(collection1)) {
			return;
		}

		if (IsEmpty.collection(collection2) || IsEmpty.collection(collection3) || collection1.size() != collection2.size() || collection2.size() != collection3.size()) {
			throw new IllegalArgumentException("All collections should have same size");
		}

		Iterator<U1> itr1 = collection1.iterator();
		Iterator<U2> itr2 = collection2.iterator();
		Iterator<U3> itr3 = collection3.iterator();
		while (itr1.hasNext()) {
			consumer.accept(itr1.next(), itr2.next(), itr3.next());
		}
	}

	public static <U> void each(Collection<U> collection, IndexedConsumer<U> consumer) {
		if (IsEmpty.collection(collection)) {
			return;
		}

		For f = new For(collection.size());

		Iterator<U> itr = collection.iterator();
		while (itr.hasNext()) {
			//			consumer.accept(f.index.at(i, i == 0, i == size - 1), itr.next());
			if (!f.index.stop) {
				consumer.accept(f.index.at(), itr.next());
			}
		}
	}

	public static <U1, U2> void each(Collection<U1> collection1, Collection<U2> collection2, IndexedMultipleConsumer<U1, U2> consumer) {
		if (IsEmpty.collection(collection1)) {
			return;
		}

		if (IsEmpty.collection(collection2) || collection1.size() != collection2.size()) {
			throw new IllegalArgumentException("two collections should have same size");
		}

		For f = new For(collection1.size());

		Iterator<U1> itr1 = collection1.iterator();
		Iterator<U2> itr2 = collection2.iterator();
		while (itr1.hasNext()) {
			if (!f.index.stop) {
				consumer.accept(f.index.at(), itr1.next(), itr2.next());
			}
		}
	}

	public static <U1, U2, U3> void each(Collection<U1> collection1, Collection<U2> collection2, Collection<U3> collection3, IndexedTripleConsumer<U1, U2, U3> consumer) {
		if (IsEmpty.collection(collection1)) {
			return;
		}

		if (IsEmpty.collection(collection2) || IsEmpty.collection(collection3) || collection1.size() != collection2.size() || collection2.size() != collection3.size()) {
			throw new IllegalArgumentException("All collections should have same size");
		}

		For f = new For(collection1.size());

		Iterator<U1> itr1 = collection1.iterator();
		Iterator<U2> itr2 = collection2.iterator();
		Iterator<U3> itr3 = collection3.iterator();
		while (itr1.hasNext()) {
			if (!f.index.stop) {
				consumer.accept(f.index.at(), itr1.next(), itr2.next(), itr3.next());
			}
		}
	}

	public static <K, V> void each(Map<K, V> map, KeyValueConsumer<K, V> consumer) {
		if (IsEmpty.map(map)) {
			return;
		}

		Iterator<Map.Entry<K, V>> itr = map.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<K, V> entry = itr.next();
			consumer.accept(entry.getKey(), entry.getValue());
		}
	}

	public static <K, V> void each(Map<K, V> map, IndexedKeyValueConsumer<K, V> consumer) {
		if (IsEmpty.map(map)) {
			return;
		}

		For f = new For(map.size());

		Iterator<Map.Entry<K, V>> itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<K, V> entry = itr.next();
			if (!f.index.stop) {
				consumer.accept(f.index.at(), entry.getKey(), entry.getValue());
			}
		}
	}
}