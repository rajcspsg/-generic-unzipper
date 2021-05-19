package util

import shapeless.{Generic, HList}
import shapeless.ops.hlist.{Replacer, Selector}
import scala.collection.generic.CanBuildFrom

/** A helper for deriving [[util.Unzip]] instances for collections other than List */
object For {
  def apply[A, Coll[X] <: Seq[X]] = new For[A, Coll]
}

class For[A, Coll[X] <: Seq[X]] {
  /** Derive an instance of `Unzip[A]` */
  def derive[L <: HList](
                          implicit generic: Generic.Aux[A, L],
                          select: Selector[L, Coll[A]],
                          replace: Replacer.Aux[L, Coll[A], Coll[A], (Coll[A], L)],
                          cbf: CanBuildFrom[Coll[A], A, Coll[A]]
                        ): Unzip[A] = new Unzip[A] {
    def unzip(node: A): List[A] = select(generic.to(node)).toList
    def zip(node: A, children: List[A]): A = generic.from(replace(generic.to(node), (cbf() ++= children).result())._2)
  }
}
