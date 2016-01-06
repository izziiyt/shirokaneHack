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

import fr.iscpif.gridscale.sge.SGEJobService
import fr.iscpif.gridscale.sge.SGEJobService.SGEJob
import fr.iscpif.gridscale.ssh.SSHJobService
import fr.iscpif.gridscale.tools.shell.Command

trait SrknSGEJobService extends SGEJobService {
  import SSHJobService._

  override def command(cmd: String) = new Command {
    override def toString =
      s"""
         |bash -lc bash <<EOF
         |$cmd
         |EOF
      """.stripMargin
  }

  def submit(description: SrknSGEJobDescription): J = withConnection { implicit connection ⇒
    exec("mkdir -p " + description.workDirectory)
    val outputStream = openOutputStream(sgeScriptPath(description))
    try outputStream.write(description.toSGE.getBytes)
    finally outputStream.close

    val memory = description.memoryStr match {
      case None => ""
      case Some(mem) => "-l s_vmem=" + mem + ",mem_req=" + mem
    }
    val command = "cd " + description.workDirectory + " && qsub " + memory + " " + sgeScriptName(description)
    val (ret, out, error) = execReturnCodeOutput(command)
    if (ret != 0) throw exception(ret, command, out, error)
    val jobId = out.split(" ").drop(2).head
    if (!jobId.forall(_.isDigit)) throw new RuntimeException("qsub did not return a valid JobID in " + out)
    SGEJob(description, jobId)
  }
}
