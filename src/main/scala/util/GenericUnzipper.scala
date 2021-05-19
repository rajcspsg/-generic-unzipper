package util

import shapeless._
import shapeless.ops.hlist._
import scala.language.higherKinds

trait GenericUnzipper {
  implicit def `Unzip List-based`[A, L <: HList](
                                                  implicit generic: Generic.Aux[A, L],
                                                  select: Selector[L, List[A]],
                                                  replace: Replacer.Aux[L, List[A], List[A], (List[A], L)]
                                                ): Unzip[A] = new Unzip[A] {
    def unzip(node: A): List[A] = select(generic.to(node))
    def zip(node: A, children: List[A]): A = generic.from(replace(generic.to(node), children)._2)
  }


}
