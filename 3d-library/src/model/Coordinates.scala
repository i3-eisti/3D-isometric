package org.blackpanther.three
package model

import scala.math._

/**
 * Cartesian coordinate
 *
 * @author MACHIZAUD Andréa
 * @version 11/1/11
 */

class Point3D(
  val x: Float,
  val y: Float,
  val z: Float,
  private[model] val t : Float = 1f
)
{
  final def move(dx: Float = 0f, dy: Float = 0f, dz: Float = 0f) : Point3D =
    if (dx == 0f && dy == 0f && dz == 0f)
      this
    else
      new Point3D(x + dx, y + dy, z + dz)

  final def map(transf : (Float, Float, Float) => (Float, Float, Float)) : Point3D = {
    val (nx, ny, nz) = transf(x,y,z)
    new Point3D(nx, ny, nz)
  }

  def distanceTo(pt : Point3D) =
    Point3D.distanceBetween(this, pt)

  lazy val asFixedReferential : FixedReferential = new Point3D(x, y, z) with FixedReferential
  lazy val asShapeReferential : ShapeReferential = new Point3D(x, y, z) with ShapeReferential

  override final val toString = "{% .2f,% .2f,% .2f}" format(x, y, z)
}

trait FixedReferential extends Point3D {
  override lazy val asFixedReferential = this
}
trait ShapeReferential extends Point3D {
  override lazy val asShapeReferential = this
}

object Point3D {

  def apply(x : Float, y : Float, z : Float) =
    new Point3D(x, y, z)

  private[model] def apply(x : Float, y : Float, z : Float, t : Float) =
    new Point3D(x, y, z, t)

  def distanceBetween(a : Point3D, b : Point3D) : Float = {
    val dx = b.x - a.x
    val dy = b.y - a.y
    val dz = b.z - a.z
    sqrt(dx * dx + dy * dy + dz * dz).toFloat
  }

}

class Vector3D(
  val cx: Float,
  val cy: Float,
  val cz: Float)
{
  /**
   * Get a normalized version of this vector
   */
  def normalize : Vector3D =
    new Vector3D.NormalizedVector3D(cx, cy, cz)

    /**
     * Compute the vector's euclid's norm
     *
     * @return euclid's norm
     */
  val euclidNorm : Float =
    sqrt(cx * cx + cy * cy + cz * cz).toFloat

  def angle(v : Vector3D) : Double =
    Vector3D.orientedAngle(this, v)

  def dot(v : Vector3D) : Float =
    Vector3D.dotProduct(this,v)

  def ●(v : Vector3D) : Float =
    Vector3D.dotProduct(this,v)

  def cross(v : Vector3D) : Float =
    Vector3D.crossProduct(this,v)

  def ^(v : Vector3D) : Float =
    Vector3D.crossProduct(this,v)

  override final val toString = "{% .2f,% .2f,% .2f}" format(cx, cy, cz)

}

object Vector3D {

  def fromOriginTo(pt: Point3D) =
    new Vector3D(pt.x, pt.y, pt.z)

  def apply(start: Point3D, destination: Point3D) =
    new Vector3D(
      destination.x - start.x,
      destination.y - start.y,
      destination.z - start.z
    )

  def apply(cx: Float, cy: Float, cz: Float) =
    new Vector3D(cx, cy, cz)

  /**
   * Compute the oriented angle between given two vectors
   *
   * @param u - start vector
   * @param v - destination vector
   * @return oriented angle between this vector and the given one  [-PI;PI]
   */
  def orientedAngle(u : Vector3D, v : Vector3D) : Double = {
    val normU = u.normalize
    val normV = v.normalize
    // u . v / ( ||u|| * ||v|| ) -> get rid of ( ||u||* ||v|| ) because we manipulated normalized vector
    val cosa = normU ● normV
    // u ^ v / ( ||u|| * ||v|| ) -> get rid of ( ||u||* ||v|| ) because we manipulated normalized vector
    val sina = normU ^ normV
    val angle = acos(cosa)
    //FIXME Better handling where determinant == 0, is it even possible with this strategy ?
    val sign =
      if ( sina > 0 )
        1.0
      else
        -1.0

    sign * angle;
  }

  /**
   * Return the normal vector made given two vectors
   *
   * @param u - first vector
   * @param v - second vector
   * @return normal vector made of this vector and the given one
   */
  def normalTo(u : Vector3D, v : Vector3D) : Vector3D =
      new Vector3D(
              u.cy * v.cz - u.cz * v.cy,
              u.cz * v.cx - u.cx * v.cz,
              u.cx * v.cy - u.cy * v.cx
      );

  def dotProduct(u : Vector3D, v : Vector3D) : Float =
      u.cx * v.cx +
      u.cy * v.cy +
      u.cz * v.cz

  def crossProduct(u : Vector3D, v : Vector3D) : Float =
      u.cy * v.cz - u.cz * v.cy +
      u.cz * v.cx - u.cx * v.cz +
      u.cx * v.cy - u.cy * v.cx

  private final class NormalizedVector3D(
    cx: Float,
    cy: Float,
    r_cz: Float
  ) extends {
    private val rawNorm = sqrt(cx * cx + cy * cy + r_cz * r_cz).toFloat
  } with Vector3D(cx / rawNorm, cy / rawNorm, r_cz / rawNorm) {

    override val normalize = this;

    override val euclidNorm = 1f;

  }

}