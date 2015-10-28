import java.io.PrintWriter

val plotFileName = "script.plt"
val plotTitle = "E_y"

def fileTemplate(arg: String) = s"""
set terminal pngcairo enhanced size 800,600 font "Arial,24"
${arg}
set terminal wxt
set output
"""

def plotTemplate(inputFileName: String, outputFileName: String) = s"""
set xrange [1:577]
set yrange [-1.0e+12:1.0e+12]
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
