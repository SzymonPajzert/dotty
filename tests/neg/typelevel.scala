trait HList {
  def length: Int
  def head: Any
  def tail: HList
  rewrite def isEmpty: Boolean =
    length == 0
}

// ()
case object HNil extends HList {
  rewrite def length = 0
  def head: Nothing = ???
  def tail: Nothing = ???
}

// (H, T)
case class HCons[H, T <: HList](hd: H, tl: T) extends HList {
  rewrite def length = 1 + tl.length
  def head: H = this.hd
  def tail: T = this.tl
}

object Test {
  rewrite def concat(xs: HList, ys: HList): HList =
    if xs.isEmpty then ys
    else HCons(xs.head, concat(xs.tail, ys))

  class Deco(private val as: HList) {
    rewrite def ++ (bs: HList) = concat(as, bs)
  }

  class Deco0(val as: HList) {
    println("HI")
    rewrite def ++ (bs: HList) = concat(as, bs)
  }

  class Eff {
    println("HI")
  }
  class Deco1(val as: HList) extends Eff {
    rewrite def ++ (bs: HList) = concat(as, bs)
  }

  // Test that selections from impure classes cannot be projected away

  val rr = new Deco(HCons(1, HNil)) ++ HNil
  val rra: HCons[Int, HNil.type] = rr   // ok
  val rr2 = new Deco2(HCons(1, HNil)) ++ HNil
  val rr2a: HCons[Int, HNil.type] = rr2   // ok
  val rr0 = new Deco0(HCons(1, HNil)) ++ HNil // error Maximal number of successive inlines (32) exceeded
  val rr0a: HCons[Int, HNil.type] = rr0   // error (type error because no inline)
  val rr1 = new Deco1(HCons(1, HNil)) ++ HNil
  val rr1a: HCons[Int, HNil.type] = rr1   // error (type error because no inline)

  class Deco2(val as: HList) extends java.lang.Cloneable with java.lang.Comparable[Deco2] {
    rewrite def ++ (bs: HList) = concat(as, bs)
  }
}