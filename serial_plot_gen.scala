import java.io.PrintWriter

val plotFileName = "script.plt"
val plotTitle = "E_y"
val xRange: (String, String) = ("0", "576")
val yRange: (String, String) = ("-1.0e+12", "1.0e+12")
val imageSize: (Int, Int) = (800, 600)
val xtics: List[(String, Int)] = List("0" -> 0, "30.0" -> 186)
val ytics: List[(String, Int)] = List("0.0" -> 0, "30.0" -> 185, "60.0" -> 370, "90.0" -> 555)

def fileTemplate(arg: String) = s"""
set terminal pngcairo enhanced size ${imageSize._1},${imageSize._2} font "Arial,24"
${arg}
set terminal wxt
set output
"""

def plotTemplate(inputFileName: String, outputFileName: String) = s"""
set xrange [${xRange._1}:${xRange._2}]
set yrange [${yRange._1}:${yRange._2}]
set xtics (${xtics.map(t => "\"" + t._1 + "\" " + t._2).mkString(",")})
set ytics (${ytics.map(t => "\"" + t._1 + "\" " + t._2).mkString(",")})
set output "${outputFileName}"
plot "${inputFileName}" using 96 with lines title "${plotTitle}" lw 2 lc rgb "red"
"""

def output(o: String) = {
  val file = new PrintWriter(plotFileName)
  file.write(o)
  file.close()
}

val command = (0 to 71) map { i =>
  plotTemplate(s"Ey.vs.xy${i+10}.txt", s"Ey_vs_y${i}.png")
} reduce (_ + _)

output(fileTemplate(command))
