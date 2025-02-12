trait Iterable[A] {
  def concat[B >: A](that: Iterable[B]): Iterable[B] = ???
  rewrite final def ++ [B >: A](that: Iterable[B]): Iterable[B] = concat(that)
}

class BitSet extends Iterable[Int] {
  def concat(that: Iterable[Int]): BitSet = ???
  rewrite final def ++ (that: Iterable[Int]): BitSet = concat(that)
}

class Test {
  def test(x: BitSet, y: Iterable[Int]): Unit = {
    val foo = x ++ y
  }
}
