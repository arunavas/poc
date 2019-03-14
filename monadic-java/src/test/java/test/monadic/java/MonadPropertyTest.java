package test.monadic.java;

import java.util.function.Function;

import monadic.java.MList;

public class MonadPropertyTest {
	
	public static void main(String[] args) {
		mlistTest();
		MList<Integer> l = MList.singletonList(1).cons(2).cons(3).cons(4).cons(5).reverse();
		MList<Integer> l1 = MList.singletonList(5).cons(6).cons(7).cons(8).cons(9).reverse();
		MList<Integer> ll = l.append(l1);
		System.out.println(l + " ++ " + l1 + " = " + ll);
		//MList's flatmap is equal to Monad's bind
		System.out.println("flatmap == bind => " + ll.flatmap(x -> MList.singletonList(x - 1)).equals(ll.bind(x -> MList.singletonList(((int)x) - 1))));
		System.out.println(ll.flatmap(x -> MList.singletonList(x - 1)).map(x -> x + 1));
	}
	
	//Source: https://wiki.haskell.org/Monad_laws
	private static void mlistTest() {
		Integer a = 1;
		MList<Integer> list = MList.singletonList(a);
		Function<Integer, MList<Integer>> f = x -> MList.singletonList(x * 2);
		Function<Integer, MList<Integer>> g = y -> MList.singletonList(y * 3);
		
		//Left Identity Law: return a >>= f      ≡     f a
		boolean leftId = list.flatmap(i -> f.apply(i)).equals(f.apply(a));
		//Right Identity Law: m >>= return       ≡     m
		boolean rightId = list.flatmap(x -> MList.singletonList(x)).equals(list);
		//Associativity Law: (m >>= f) >>= g     ≡     m >>= (\x -> f x >>= g)
		MList<Integer> l = list.flatmap(x -> f.apply(x)).flatmap(y -> g.apply(y));
		MList<Integer> r = list.flatmap(x -> f.apply(x).flatmap(y -> g.apply(y)));
		boolean assoc = l.equals(r);
		System.out.println("Left Identity: " + leftId);
		System.out.println("Right Identity: " + rightId);
		System.out.println("Associativity: " + assoc);
		System.out.println("Monad Laws: " + (leftId && rightId && assoc));
	}
	
}