package monadic.java;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MList<A> implements Monad<MList<A>> {

	final A head;
	final MList<A> tail;
	
	private MList() {
		this.head = null;
		this.tail = null;
	}
	public MList(A value) {
		this.head = value;
		this.tail = new MList<A>();
	}
	private MList(A value, MList<A> tail) {
		this.head = value;
		this.tail = tail;
	}
	
	//Monad's unit/return
	public static <T> MList<T> singletonList(T v) {
		return new MList<T>(v);
	}

	//Monad's bind
	public <B> MList<B> flatmap(Function<A, MList<B>> f) {
		return foldr((e, acc) -> f.apply(e).append(acc), new MList<B>());
	}
	
	public <B> MList<B> map(Function<A, B> f) {
		return foldr((e, b) -> b.cons(f.apply(e)), new MList<B>());
	}
	
	public MList<A> filter(Function<A, Boolean> f) {
		return foldr((e, acc) -> f.apply(e) ? acc.cons(e) : acc, new MList<A>());
	}

	public <B> B foldr(BiFunction<A, B, B> f, B b) {
		if (isEmpty()) return b;
		else return f.apply(head, tail.foldr(f, b));
	}

	public <B> B foldl(BiFunction<B, A, B> f, B b) {
		if (isEmpty()) return b;
		else return tail.foldl(f, f.apply(b, head));
	}
	
	public MList<A> cons(A v) {
		return v == null ? this : new MList<A>(v, this);
	}

	public MList<A> append(MList<A> l) {
		return l == null || l.isEmpty() ? this : foldr((e, acc) -> acc.cons(e), l);
	}
	
	public MList<A> reverse() {
		return foldl((acc, e) -> acc.cons(e), new MList<A>());
	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public int size() {
		return isEmpty() ? 0 : 1 + tail.size();
	}
	
	public A head() throws Exception {
		if (head == null) throw new Exception("head called on empty list!");
		else return head;
	}
	public Optional<A> headSafe() {
		if (head == null) return Optional.empty();
		else return Optional.of(head);
	}
	public MList<A> tail() {
		if (tail == null) return new MList<A>();
		else return tail;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		MList<A> l = this;
		while (l.head != null) {
			sb.append(l.head);
			l = l.tail;
		}
		return sb.subSequence(0, sb.length()) + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean res = false;
		if (obj instanceof MList) {
			MList that = (MList) obj;
			MList<A> current = this;
			res = current.size() == that.size();
			while (res && that.head != null && current.head != null) {
				res = current.head.equals(that.head);
				current = current.tail;
				that = that.tail;
			}
		}
		return res;
	}
	@Override
	public <A> MList unit(A m) {
		return MList.singletonList(m);
	}
	@Override
	public <N> Monad<N> bind(Function<MList<A>, N> f) {
		// TODO Auto-generated method stub
		return null;
	}
}