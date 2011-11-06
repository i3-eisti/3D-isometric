package org.blackpanther.three
package model

/**
 * @author MACHIZAUD Andréa
 * @version 11/5/11
 */



/**
 *   _____   F   G
 *  /|    | E   H
 * | |____|  B   C
 * |/____/  A   D
 */
class Box(
  val vertices : Vector[Point3D]
) extends Shape {

  require(vertices.length == 8,"Box must have exactly eight vertices")

  val A : Point3D = vertices(Box.A)
  val B : Point3D = vertices(Box.B)
  val C : Point3D = vertices(Box.C)
  val D : Point3D = vertices(Box.D)
  val E : Point3D = vertices(Box.E)
  val F : Point3D = vertices(Box.F)
  val G : Point3D = vertices(Box.G)
  val H : Point3D = vertices(Box.H)


  val width = B distanceTo C

  val height = B distanceTo F

  val depth = B distanceTo A

  def normalFromFace(faceCode : Int) : Vector3D =
    Box.normalFromFace(vertices, faceCode)

  def transform(trans: Transformation) : Box =
    new Box(vertices map trans)

  override val toString =
    "Box{%n\tA %s%n\tB %s%n\tC %s%n\tD %s%n\tE %s%n\tF %s%n\tG %s%n\tH %s%n}%n" format (
      A.toString,
      B.toString,
      C.toString,
      D.toString,
      E.toString,
      F.toString,
      G.toString,
      H.toString
    )

  override lazy val hashCode = Box.getID() + vertices.##

}

object Box {

  private var counter = 0

  @inline private[Box] def getID() = {
    counter += 1
    counter
  }

  @inline def apply(
    width : Float = Box.DefaultWidth,
    height : Float = Box.DefaultHeight,
    depth : Float = Box.DefaultDepth
  ) : Box =  {
    val mw = width / 2f
    val mh = height / 2f
    val md = depth / 2f

    new Box(Vector(
      Point3D(-mw, -mh, -md), //A
      Point3D(-mw, -mh, +md), //B
      Point3D(+mw, -mh, +md), //C
      Point3D(+mw, -mh, -md), //D
      Point3D(-mw, +mh, -md), //E
      Point3D(-mw, +mh, +md), //F
      Point3D(+mw, +mh, +md), //G
      Point3D(+mw, +mh, -md)  //H
    ))
  }

  private val DefaultWidth = 1f
  private val DefaultHeight = 1f
  private val DefaultDepth = 1f

  //vertices
  val A = 0;
  val B = 1;
  val C = 2;
  val D = 3;
  val E = 4;
  val F = 5;
  val G = 6;
  val H = 7;

  lazy val EdgesCode = List(
    (A,B),
    (B,F),
    (F,E),
    (E,A),
    (A,D),
    (D,C),
    (D,H),
    (C,B),
    (C,G),
    (G,H),
    (G,F),
    (H,E)
  )

  @inline def labelFromVertices(verticeCode : Int) : String = verticeCode match {
    case A => "A"
    case B => "B"
    case C => "C"
    case D => "D"
    case E => "E"
    case F => "F"
    case G => "G"
    case H => "H"
  }

  //faces
  val ABFE = 0;
  val ABCD = 1;
  val BCGF = 2;
  val CDHG = 3;
  val EHGF = 4;
  val DAEH = 5;

  lazy val FacesCode = List(ABFE, ABCD, BCGF, CDHG, EHGF, DAEH)

  @inline def egdesCodeFromFace(faceCode : Int) : List[(Int,Int)] = faceCode match {
    case ABFE =>
      List((A, B), (B, F), (F, E), (E, A))
    case ABCD =>
      List((A, B), (B, C), (C, D), (D, A))
    case BCGF =>
      List((F, B), (B, C), (C, G), (G, F))
    case CDHG =>
      List((H, D), (D, C), (C, G), (G, H))
    case EHGF =>
      List((H, E), (E, F), (F, G), (G, H))
    case DAEH =>
      List((H, E), (E, A), (A, D), (D, H))
  }

  @inline def normalFromFace(vertices : IndexedSeq[Point3D], faceCode : Int) : Vector3D = faceCode match {
    case ABCD =>
      Vector3D(
        start = vertices(F),
        destination = vertices(B)
      )
    case ABFE =>
      Vector3D(
        start = vertices(C),
        destination = vertices(B)
      )
    case BCGF =>
      Vector3D(
        start = vertices(A),
        destination = vertices(B)
      )
    case CDHG =>
      Vector3D(
        start = vertices(B),
        destination = vertices(C)
      )
    case EHGF =>
      Vector3D(
        start = vertices(B),
        destination = vertices(F)
      )
    case DAEH =>
      Vector3D(
        start = vertices(B),
        destination = vertices(A)
      )
  }

  @inline def labelFromFaces(faceCode : Int) : String = faceCode match {
    case ABFE => "ABFE"
    case ABCD => "ABCD"
    case BCGF => "BCGF"
    case CDHG => "CDHG"
    case EHGF => "EHGF"
    case DAEH => "DAEH"
  }

}