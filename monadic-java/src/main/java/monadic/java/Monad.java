package monadic.java;

import java.util.function.Function;

public interface Monad<M> {

	<A> M unit(A m);
	<N> Monad<N> bind(Function<M, N> f);
	
}
