package p

object pow {

  rewrite def power(x: Double, n: Int): Double =
    if (n == 0) 1.0
    else if (n == 1) x
    else {
      val y = power(x, n / 2)
      if (n % 2 == 0) y * y else y * y * x
    }
}
