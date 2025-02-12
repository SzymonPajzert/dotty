import scala.quoted._

import scala.tasty._
import scala.tasty.util.TreeTraverser

object Macros {

  implicit rewrite def foo(i: Int): String =
    ~impl('(i))

  def impl(i: Expr[Int])(implicit tasty: Tasty): Expr[String] = {
    value(i).toString.toExpr
  }

  rewrite implicit def value[X](e: Expr[X])(implicit tasty: Tasty, ev: Valuable[X]): Option[X] = ev.value(e)

  trait Valuable[X] {
    def value(e: Expr[X])(implicit tasty: Tasty): Option[X]
  }

  implicit def intIsEvalable: Valuable[Int] = new Valuable[Int] {
    override def value(e: Expr[Int])(implicit tasty: Tasty): Option[Int] = {
      import tasty._

      e.toTasty.tpe match {
        case Type.SymRef(sym, pre) =>
          sym.tree match {
            case Some(ValDef(_, tpt, _)) =>
              tpt.tpe match {
                case Type.ConstantType(Constant.Int(i)) => Some(i)
                case _ => None
              }
            case _ => None
          }

        case Type.ConstantType(Constant.Int(i)) => Some(i)
        case _ => None
      }
    }
  }
}
