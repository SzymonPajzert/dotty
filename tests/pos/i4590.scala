package test

class ArrayDeque {
  rewrite def isResizeNecessary(len: Int) = len > ArrayDeque.StableSize
}

object ArrayDeque {
  private val StableSize = 256
}

class List {
  rewrite def foo(x: List.Cons): Unit = {
    x.next = this
  }
}
object List {
  case class Cons(head: Int, private[List] var next: List)
}

