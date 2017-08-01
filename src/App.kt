import org.w3c.dom.CanvasRenderingContext2D
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Math


fun draw() {
    val canvas = document.getElementById("mainCanvas")!!
    println("inner: ${window.innerWidth}x${window.innerHeight} client: ${document.documentElement?.clientWidth}x${document.documentElement?.clientHeight}")
    canvas.setAttribute("width", window.innerWidth.toString())
    canvas.setAttribute("height", window.innerHeight.toString())
    val ctx = canvas.asDynamic().getContext("2d") as CanvasRenderingContext2D
    val width = canvas.clientWidth
    val height = canvas.clientHeight

    
    for (y in 0..height - 1) {
        for (x in 0..width - 1) {
            val m = mandelbrot(x, y, width, height)
            ctx.fillStyle = getColor(m)
            ctx.fillRect(x.toDouble(), y.toDouble(), 1.0, 1.0)
        }
    }
    println(colorsSet)
}

val colorsSet = HashSet<String>()
fun getColor(m: Double): String {
//    val colors = intArrayOf(0x7f2c2c2e, 0x7f2d2c2e, 0x7f2e2c2e, 0x7f2f2c2e, 0x7f302c2e, 0x7f312c2e, 0x7f322c2e, 0x7f332c2d, 0x7f342c2d, 0x7f362c2d, 0x7f372c2d, 0x7f382c2d, 0x7f3a2c2d, 0x7f3b2c2d, 0x7f3d2c2d, 0x7f3e2c2c, 0x7f402c2c, 0x7f422c2c, 0x7f432c2c, 0x7f452c2c, 0x7f472c2c, 0x7f482c2c, 0x7f4a2d2c, 0x7f4c2d2b, 0x7f4e2d2b, 0x7f502d2b, 0x7f522d2b, 0x7f542d2b, 0x7f562d2b, 0x7f582e2b, 0x7f5a2e2b, 0x7f5c2e2b, 0x7f5e2e2b, 0x7f602f2b, 0x7f622f2b, 0x7f642f2b, 0x7f66302b, 0x7f68302b, 0x7f69302b, 0x7f6a312b, 0x7f6b312b, 0x7f6c312b, 0x7f6d312b, 0x7f6e312b, 0x7f70322b, 0x7f71322b, 0x7f72322b, 0x7f73322b, 0x7f74322b, 0x7f76332b, 0x7f77332c, 0x7f78332c, 0x7f7a332c, 0x7f7b332c, 0x7f7c342c, 0x7f7e342c, 0x7f7f342c, 0x7f80342c, 0x7f82352c, 0x7f83352c, 0x7f84352c, 0x7f86352c, 0x7f87352c, 0x7f88362c, 0x7f8a362c, 0x7f8b362c, 0x7f8d362c, 0x7f8e372c, 0x7f90372d, 0x7f91372d, 0x7f92382d, 0x7f94382d, 0x7f95382d, 0x7f97382d, 0x7f98392d, 0x7f99392d, 0x7f9b392d, 0x7f9c3a2d, 0x7f9e3a2d, 0x7f9f3a2e, 0x7fa13b2e, 0x7fa23b2e, 0x7fa33b2e, 0x7fa53c2e, 0x7fa63c2e, 0x7fa83d2e, 0x7fa93d2e, 0x7faa3d2e, 0x7fac3e2f, 0x7fad3e2f, 0x7fae3f2f, 0x7fb03f2f, 0x7fb1402f, 0x7fb2402f, 0x7fb44130, 0x7fb54130, 0x7fb64230, 0x7fb74230, 0x7fb94330, 0x7fba4330, 0x7fbb4431, 0x7fbc4431, 0x7fbd4531, 0x7fbf4631, 0x7fc04631, 0x7fc14732, 0x7fc24832, 0x7fc34832, 0x7fc44932, 0x7fc54a33, 0x7fc64a33, 0x7fc74b33, 0x7fc84c33, 0x7fc94d34, 0x7fca4d34, 0x7fcb4e34, 0x7fcc4f34, 0x7fcd5034, 0x7fce5135, 0x7fcf5135, 0x7fcf5235, 0x7fd05335, 0x7fd15436, 0x7fd25536, 0x7fd35636, 0x7fd45736, 0x7fd55837, 0x7fd65937, 0x7fd65a37, 0x7fd75b37, 0x7fd85c38, 0x7fd95d38, 0x7fda5e38, 0x7fdb5f38, 0x7fdb6039, 0x7fdc6139, 0x7fdd6239, 0x7fde6339, 0x7fde643a, 0x7fdf653a, 0x7fe0663a, 0x7fe1673a, 0x7fe1683b, 0x7fe26a3b, 0x7fe36b3b, 0x7fe46c3c, 0x7fe46d3c, 0x7fe56e3c, 0x7fe66f3d, 0x7fe6703d, 0x7fe7723d, 0x7fe8733e, 0x7fe8743e, 0x7fe9753e, 0x7fea763f, 0x7fea783f, 0x7feb793f, 0x7feb7a40, 0x7fec7b40, 0x7fed7c41, 0x7fed7e41, 0x7fee7f41, 0x7fee8042, 0x7fef8142, 0x7fef8243, 0x7ff08443, 0x7ff08544, 0x7ff18644, 0x7ff18745, 0x7ff28845, 0x7ff28a46, 0x7ff38b46, 0x7ff38c47, 0x7ff48d47, 0x7ff48e48, 0x7ff59048, 0x7ff59149, 0x7ff6924a, 0x7ff6934a, 0x7ff6944b, 0x7ff7964c, 0x7ff7974c, 0x7ff7984d, 0x7ff8994e, 0x7ff89a4e, 0x7ff99b4f, 0x7ff99c50, 0x7ff99d50, 0x7ff99e50, 0x7ff99e51, 0x7ff99f51, 0x7ff99f51, 0x7ff9a052, 0x7ff9a052, 0x7ff9a152, 0x7ff9a153, 0x7ff9a253, 0x7ff9a254, 0x7ff9a354, 0x7ff9a354, 0x7ff9a455, 0x7ff9a455, 0x7ff9a555, 0x7ff9a556, 0x7ff9a656, 0x7ff9a657, 0x7ff9a757, 0x7ff9a757, 0x7ff9a858, 0x7ff9a858, 0x7ff9a959, 0x7ff9a959, 0x7ff9aa59, 0x7ff9aa5a, 0x7ff9ab5a, 0x7ff9ab5b, 0x7ff9ac5b, 0x7ff9ac5c, 0x7ff9ad5c, 0x7ff9ad5d, 0x7ff9ae5d, 0x7ff9af5d, 0x7ff9af5e, 0x7ff9b05e, 0x7ff9b05f, 0x7ff9b15f, 0x7ff9b160, 0x7ff9b260, 0x7ff9b261, 0x7ff9b361, 0x7ff9b462, 0x7ff9b462, 0x7ff9b563, 0x7ff9b563, 0x7ff9b664, 0x7ff9b664, 0x7ff9b765, 0x7ff9b765, 0x7ff9b866, 0x7ff9b966, 0x7ff9b967, 0x7ff9ba67, 0x7ff9ba68, 0x7ff9bb68, 0x7ff9bb69, 0x7ff9bc69, 0x7ff9bd6a, 0x7ff9bd6a, 0x7ff9be6b, 0x7ff9be6b, 0x7ff9bf6c, 0x7ff9bf6d, 0x7ff9c06d, 0x7ff9c06e, 0x7ff9c16e, 0x7ff9c26f, 0x7ff9c26f, 0x7ff9c370, 0x7ff9c370, 0x7ff9c471, 0x7ff9c471, 0x7ff9c572, 0x7ff9c672, 0x7ff9c673, 0x7ff9c774, 0x7ff9c774, 0x7ff9c875, 0x7ff9c875, 0x7ff9c976, 0x7ff9ca76, 0x7ff9ca77, 0x7ff9cb77, 0x7ff9cb78, 0x7ff9cc78, 0x7ff9cc79, 0x7ff9cd7a, 0x7ff9cd7a, 0x7ff9ce7b, 0x7ff9cf7b, 0x7ff9cf7c, 0x7ff9d07c, 0x7ff9d07d, 0x7ff9d17d, 0x7ff9d17e, 0x7ff9d27e, 0x7ff9d27f, 0x7ff9d380, 0x7ff9d380, 0x7ff9d481, 0x7ff9d481, 0x7ff9d582, 0x7ff9d682, 0x7ff9d683, 0x7ff9d783, 0x7ff9d784, 0x7ff9d884, 0x7ff9d885, 0x7ff9d985, 0x7ff9d986, 0x7ff9da86, 0x7ff9da87, 0x7ff9db87, 0x7ff9db88, 0x7ff9dc89, 0x7ff9dc89, 0x7ff9dd8a, 0x7ff9dd8a, 0x7ff9de8b, 0x7ff9de8b, 0x7ff9df8c, 0x7ff9df8c, 0x7ff9e08d, 0x7ff9e08d, 0x7ff9e18e, 0x7ff9e18e, 0x7ff9e28f, 0x7ff9e28f, 0x7ff9e38f, 0x7ff9e390, 0x7ff9e490, 0x7ff9e491, 0x7ff9e491, 0x7ff9e592, 0x7ff9e592, 0x7ff9e693, 0x7ff9e693, 0x7ff8e794, 0x7ff8e794, 0x7ff8e895, 0x7ff8e895, 0x7ff8e895, 0x7ff8e996, 0x7ff8e996, 0x7ff8ea97, 0x7ff8ea97, 0x7ff8ea98, 0x7ff8eb98, 0x7ff8eb98, 0x7ff8ec99, 0x7ff8ec99, 0x7ff8ec9a, 0x7ff8ed9a, 0x7ff8ed9a, 0x7ff8ee9b, 0x7ff8ee9b, 0x7ff8ee9b, 0x7ff8ef9c, 0x7ff8ef9c, 0x7ff7ef9d, 0x7ff7f09d, 0x7ff7f09d, 0x7ff7f09e, 0x7ff7f19e, 0x7ff7f19e, 0x7ff7f19f, 0x7ff7f29f, 0x7ff7f29f, 0x7ff7f2a0, 0x7ff7f3a0, 0x7ff7f3a0, 0x7ff7f3a0, 0x7ff7f4a1, 0x7ff7f4a1, 0x7ff7f4a1, 0x7ff7f4a2, 0x7ff7f5a2, 0x7ff7f5a2, 0x7ff7f5a2, 0x7ff7f6a3, 0x7ff7f6a3, 0x7fa74132, 0x7fa6412f, 0x7fa6412f, 0x7fa6412f, 0x7fa6412f, 0x7fa74230, 0x7fa74230, 0x7fa74230)
//    val colors = IntArray(255) { it * 10 }
    val colors = intArrayOf(-5947087, -5947087, -13882322, -13816786, -13751250, -13685714, -13620178, -13554642, -13489106, -13423571, -13358035, -13226963, -13161427, -13095891, -12964819, -12899283, -12768211, -12702676, -12571604, -12440532, -12374996, -12243924, -12112852, -12047316, -11915988, -11784917, -11653845, -11522773, -11391701, -11260629, -11129557, -10998229, -10867157, -10736085, -10605013, -10473685, -10342613, -10211541, -10080213, -9949141, -9883605, -9817813, -9752277, -9686741, -9621205, -9555669, -9424341, -9358805, -9293269, -9227733, -9162197, -9030869, -8965332, -8899796, -8768724, -8703188, -8637396, -8506324, -8440788, -8375252, -8243924, -8178388, -8112852, -7981780, -7916244, -7850452, -7719380, -7653844, -7522772, -7456980, -7325907, -7260371, -7194579, -7063507, -6997971, -6866899, -6801107, -6735571, -6604499, -6538707, -6407635, -6342098, -6210770, -6145234, -6079698, -5948370, -5882834, -5751506, -5685970, -5620434, -5489105, -5423569, -5357777, -5226705, -5160913, -5095377, -4964048, -4898512, -4832720, -4767184, -4635856, -4570320, -4504527, -4438991, -4373199, -4241871, -4176335, -4110542, -4044750, -3979214, -3913422, -3847629, -3782093, -3716301, -3650509, -3584716, -3519180, -3453388, -3387596, -3321804, -3256011, -3190475, -3190219, -3124427, -3058634, -2992842, -2927050, -2861258, -2795465, -2729673, -2729417, -2663625, -2597832, -2532040, -2466248, -2400456, -2400199, -2334407, -2268615, -2202823, -2202566, -2136774, -2070982, -2005190, -2004933, -1938885, -1873093, -1807300, -1807044, -1741252, -1675459, -1675203, -1609155, -1543362, -1543106, -1477314, -1411521, -1411009, -1345217, -1344960, -1279168, -1213375, -1212863, -1147071, -1146814, -1081022, -1080765, -1014717, -1014460, -948668, -948411, -882619, -882106, -816314, -816057, -750265, -750008, -683960, -683703, -617910, -617654, -617397, -551348, -551092, -550835, -485042, -484786, -418993, -418736, -418480, -418224, -418223, -417967, -417967, -417710, -417710, -417454, -417453, -417197, -417196, -416940, -416940, -416683, -416683, -416427, -416426, -416170, -416169, -415913, -415913, -415656, -415656, -415399, -415399, -415143, -415142, -414886, -414885, -414629, -414628, -414372, -414371, -414115, -413859, -413858, -413602, -413601, -413345, -413344, -413088, -413087, -412831, -412574, -412574, -412317, -412317, -412060, -412060, -411803, -411803, -411546, -411290, -411289, -411033, -411032, -410776, -410775, -410519, -410262, -410262, -410005, -410005, -409748, -409747, -409491, -409490, -409234, -408977, -408977, -408720, -408720, -408463, -408463, -408206, -407950, -407949, -407692, -407692, -407435, -407435, -407178, -406922, -406921, -406665, -406664, -406408, -406407, -406150, -406150, -405893, -405637, -405636, -405380, -405379, -405123, -405122, -404866, -404865, -404608, -404608, -404351, -404351, -404094, -403838, -403837, -403581, -403580, -403324, -403323, -403067, -403066, -402810, -402809, -402553, -402552, -402295, -402295, -402038, -402038, -401781, -401781, -401524, -401524, -401267, -401267, -401010, -401010, -400753, -400753, -400497, -400496, -400240, -400239, -400239, -399982, -399982, -399725, -399725, -465004, -465004, -464747, -464747, -464747, -464490, -464490, -464233, -464233, -464232, -463976, -463976, -463719, -463719, -463718, -463462, -463462, -463205, -463205, -463205, -462948, -462948, -528483, -528227, -528227, -528226, -527970, -527970, -527969, -527713, -527713, -527712, -527456, -527456, -527456, -527199, -527199, -527199, -527198, -526942, -526942, -526942, -526685, -526685, -5816014, -5881553, -5881553, -5881553, -5881553, -5815760, -5815760, -5815760)
    val index = (m * (colors.size - 1)).toInt().coerceAtMost(colors.size - 1)
    val g = colors[index]
    val hex = g.toHex()
    colorsSet.add(hex)
    return "#$hex"
}

fun mandelbrot(x: Int, y: Int, width: Int, height: Int): Double {
    val a = (x.toDouble() - width * 1.5) / 2400
    val b = (y.toDouble() - height * 2) / 2400
    fun nextA(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curA - curB * curB + firstA
    fun nextB(curA: Double, curB: Double, firstA: Double, firstB: Double) = curA * curB + curA * curB + firstB

    var zA = 0.0
    var zB = 0.0

    repeat(64) {
        val zA2 = nextA(zA, zB, a, b)
        val zB2 = nextB(zA, zB, a, b)
        zA = zA2
        zB = zB2
        if (zA * zA + zB * zB > 4) {
            return it / 64.0
        }
    }
    return 1.0
}

fun main(args: Array<String>) {
    println("hello world")
    window.onload = { draw() }
    window.onresize= { draw() }
}

data class Complex(val a: Double, val b: Double) {
    operator fun times(o: Complex): Complex = Complex(a * o.a - b * o.b, a * o.b + b * o.a)
    operator fun plus(o: Complex): Complex = Complex(a + o.a, b + o.b)
    fun module(): Double = Math.sqrt(a * a + b * b)
}

fun mandelbrot2(x: Int, y: Int, width: Int, height: Int): Int {
    val a = (x.toDouble() - width / 2) / 300
    val b = (y.toDouble() - height / 2) / 300
    val z0 = Complex(a, b)
    var z = Complex(0.0, 0.0)
    repeat(255) {
        z = z * z + z0
        if (z.module() > 2) return it
    }
    return 255
}

//    (a + ib) * (c + id) = ac + iad + ibc + iibd = (ac - bd) + i(ad + bc)
// new = old * old + first

//    val img = ctx.createImageData(width.toDouble(), height.toDouble())
//    val data: Uint8ClampedArray = img.data
//    var i = 0
//    while (i < data.length) {
//        val x = i / 4 % width
//        val y = i / 4 / width
//        val color: dynamic = if (((x / 10) + (y / 10)) % 2 == 0) 0 else 255
//        val alpha: dynamic = 255
//        data[i + 0] = color
//        data[i + 1] = color
//        data[i + 2] = color
//        data[i + 3] = alpha
//        i += 4
//    }
//    ctx.putImageData(img, 0.5, 0.5)
//    ctx.translate(0.5, 0.5)


fun Int.toHex(): String =
        listOf(b2(), b3(), b4()).map(::smallHex).joinToString("")

fun smallHex(v: Int): String = hexDigit(v / 16).toString() + hexDigit(v % 16)

fun hexDigit(v: Int): Char = charArrayOf('0' , '1' , '2' , '3' , '4' , '5' , '6' , '7' , '8' , '9' , 'a' , 'b' , 'c' , 'd' , 'e' , 'f')[v]


inline fun Int.b1(): Int = (this shr 24).lastByte()
inline fun Int.b2(): Int = (this shr 16).lastByte()
inline fun Int.b3(): Int = (this shr 8).lastByte()
inline fun Int.b4(): Int = lastByte()
inline fun Int.lastByte(): Int = this and 0x000000ff

