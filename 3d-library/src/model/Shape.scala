package org.blackpanther.three
package model

import time.TimeRange


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

trait Shape { this : Shape =>

  def transform(trans: Transformation): Shape

  def update(time: TimeRange): Shape

}