package org.scalacheck
package rng

import scala.util.Try

trait Seed extends Serializable {
  def toBase64: String
  def next: Seed
  def reseed(n: Long): Seed
  def slide: Seed
  def long: (Long, Seed)
  def double: (Double, Seed)
}

object Seed {

  /** Generate a deterministic seed. */
  def apply(s: Long): Seed = SimpleRngSeed(s)

  /**
    * Generate a seed directly from four Long values.
    *
    * Warning: unlike Seed.apply(Long), this method just directly
    * constructs a seed from the four Long values. Prefer using
    * `Seed(Long)` if you aren't sure whether these will be good seed
    * values.
    */
  def fromLongs(a: Long, b: Long, c: Long, d: Long): Seed = new SimpleRngSeed(a, b, c, d)

  /**
    * Parse a Base-64 encoded seed, returning a Seed value.
    *
    * This method requires the exact format produced by `toBase64`
    * (i.e. a 44-character string using the web-safe Base-64
    * alphabet). Other encodings must produce precisely the same
    * outputs to be supported.
    *
    * This method will throw an IllegalArgumentException if parsing
    * fails.
    */
  def fromBase64(s: String): Try[Seed] = SimpleRngSeed.fromBase64(s)

  /** Generate a random seed. */
  def random(): Seed = SimpleRngSeed.random()

}
