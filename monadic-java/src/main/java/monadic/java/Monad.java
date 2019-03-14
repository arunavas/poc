package monadic.java;

import java.util.function.Function;

public interface Monad<M> {

	<A> M unit(A m);
	<A> M bind(Function<A, M> f);
	
}
