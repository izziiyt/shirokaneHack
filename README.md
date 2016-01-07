# shirokaneHack

This program submits qsub to [shirokane3](https://supcom.hgc.jp/japanese/sys_const/system-main_s3.html) by scala language based on [GridScala](https://github.com/openmole/gridscale).  
If you use neither Scala language nor shirokane3, this program makes no sense to you.

###INSTALL

###Example

ls command at home directory with requiring 1G byte memory

```scala
import java.io.File
import fr.iscpif.gridscale.ssh.SSHPrivateKeyAuthentication
import org.scalatest.FunSuite

class GridTest extends FunSuite{
  test("lsAtHome"){
    val service = new SrknSGEJobService with SSHPrivateKeyAuthentication {
      def user = "username"
      def privateKey = new File("somewhere/id_rsa")
      def password = ""
    }

    val description = new SrknSGEJobDescription {
      def executable = "ls"
      def arguments = ""
      def workDirectory = service.home
      def memoryStr = "1G"
    }
    val j = service.submit(description)
  }
}

```


###LICENSE

Under the GNU Affero GPLv3 software licence see LICENNSE.txt

###Developer's Information & Contact
Yuto Ichikawa  
ichikaway{at}cb.k.u-tokyo.ac.jp