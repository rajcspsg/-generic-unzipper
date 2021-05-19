package util

import scala.annotation.implicitNotFound

@implicitNotFound("Count not find a way to unzip ${A}.")
trait Unzip[A] {
  def unzip(node: A): List[A]
  def zip(node: A, children: List[A]): A
}

object Unzip extends GenericUnzipper
