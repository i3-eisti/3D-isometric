package org.blackpanther.three
package model

/**
 * Define abstraction for modeling a shape :
 *  - metadata
 *  - general methods
 *  - transformation
 *
 *  Shape is monadic
 *
 * @author MACHIZAUD Andréa
 * @version 11/1/11
 */

trait Shape {

  def transform(trans : Transformation) : Shape

}