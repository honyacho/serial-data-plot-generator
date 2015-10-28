import java.io.PrintWriter

val plotFileName = "script.plt"
val plotTitle = "E_y"
val xRange: (String, String) = ("0", "576")
val yRange: (String, String) = ("-1.0e+12", "1.0e+12")
val imageSize: (Int, Int) = (800, 600)
val xtics: List[(String, Int)] = List("0.0" -> 0, "30.0" -> 185, "60.0" -> 370, "90.0" -> 555)
val xLabel = "x [micro-m]"
val lineNum = 96

def fileTemplate(arg: String) = s"""
set terminal pngcairo enhanced size ${imageSize._1},${imageSize._2} font "Arial,24"
${arg}
set terminal wxt
set output
reset
"""

def plotTemplate(inputFileName: String, outputFileName: String, title: String) = s"""
set title "${title}"
set xrange [${xRange._1}:${xRange._2}]
set yrange [${yRange._1}:${yRange._2}]
set xtics (${xtics.map(t => "\"" + t._1 + "\" " + t._2).mkString(",")})
set xlabel "${xLabel}"
set output "${outputFileName}"
plot "${inputFileName}" using ${lineNum} with lines title "${plotTitle}" lw 2 lc rgb "red"
"""

def output(o: String) = {
  val file = new PrintWriter(plotFileName)
  file.write(o)
  file.close()
}

val command = (0 to 71) map { i =>
  plotTemplate(s"Ey.vs.xy${i+10}.txt", s"Ey_vs_y${i}.png", "t = %.2f".format(6.666 * (1+i).toFloat))
} reduce (_ + _)

output(fileTemplate(command))
