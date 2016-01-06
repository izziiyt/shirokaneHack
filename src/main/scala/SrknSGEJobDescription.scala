/**
  * Copyright (C) 2016 Yuto Ichikawa
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU Affero General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  *
  *  This program is distributed in the hope that it will be useful,
  *  but WITHOUT ANY WARRANTY; without even the implied warranty of
  *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  *  GNU Affero General Public License for more details.
  *
  *  You should have received a copy of the GNU Affero General Public License
  *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */

import fr.iscpif.gridscale.sge.SGEJobDescription
import fr.iscpif.gridscale.tools.ScriptBuffer

trait SrknSGEJobDescription extends SGEJobDescription {
  implicit def t2o[T](t: T): Option[T] = Some(t)
  def memoryStr: Option[String]
  override def toSGE = {
    val buffer = new ScriptBuffer
    buffer += "#!/bin/bash"
    buffer += "#$ -S /bin/bash"
    buffer += "#$ -o " + output
    buffer += "#$ -e " + error
    buffer += "#$ -cwd"
    queue.foreach(q ⇒ buffer += "#$ -q " + q)
    buffer += executable + " " + arguments
    buffer.toString
  }
}
