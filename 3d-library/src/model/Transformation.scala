package org.blackpanther.three
package model

/**
 * @author MACHIZAUD Andréa
 * @version 11/1/11
 */

trait Transformation extends ((Point3D) => Point3D) {

  def apply(pt: Point3D) : Point3D

  def compose(nextTransformation: Transformation): Transformation

}

object Transformations {

  trait MatrixBased extends Transformation {

    val m: Array[Array[Float]]

    def apply(p: Point3D) : Point3D =
      new Point3D(
        m(0)(0) * p.x + m(0)(1) * p.y + m(0)(2) * p.z + m(0)(3) * p.t,
        m(1)(0) * p.x + m(1)(1) * p.y + m(1)(2) * p.z + m(1)(3) * p.t,
        m(2)(0) * p.x + m(2)(1) * p.y + m(2)(2) * p.z + m(2)(3) * p.t,
        m(3)(0) * p.x + m(3)(1) * p.y + m(3)(2) * p.z + m(3)(3) * p.t
      )

    def compose(nextTransformation: Transformation): Transformation =
      throw new UnsupportedOperationException("Not yet implemented")

    override lazy val toString =
          "[% .2f % .2f % .2f % .2f]%n" +
          "[% .2f % .2f % .2f % .2f]%n" +
          "[% .2f % .2f % .2f % .2f]%n" +
          "[% .2f % .2f % .2f % .2f]" format (
            m(0)(0), m(0)(1), m(0)(2), m(0)(3),
            m(1)(0), m(1)(1), m(1)(2), m(1)(3),
            m(2)(0), m(2)(1), m(2)(2), m(2)(3),
            m(3)(0), m(3)(1), m(3)(2), m(3)(3)
          )
  }

  object Identity extends Transformation {

    def apply(pt: Point3D) = pt

    def compose(nextTransformation: Transformation) = nextTransformation

  }

  class Rotation(
    mx: Float, my: Float, mz: Float,
    nx: Float, ny: Float, nz: Float,
    ox: Float, oy: Float, oz: Float
  ) extends Transformation with MatrixBased {
    override val m =
      Array(
        Array(mx, my, mz, 0f),
        Array(nx, ny, nz, 0f),
        Array(ox, oy, oz, 0f),
        Array(0f, 0f, 0f, 1f)
      )
  }

  class Translation(
    cx: Float, cy: Float, cz: Float
  ) extends Transformation {

    def apply(pt: Point3D) = pt.move(cx, cy, cz)

    override val toString =
      "Translation [% .2f % .2f % .2f % .2f]" format (cx, cy, cz)

    def compose(nextTransformation: Transformation): Transformation =
      throw new UnsupportedOperationException("Not yet implemented")

  }

}