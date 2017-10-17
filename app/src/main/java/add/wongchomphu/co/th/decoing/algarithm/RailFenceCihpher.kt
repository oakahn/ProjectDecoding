package add.wongchomphu.co.th.decoing.algarithm

open class RailFenceCihpher {

     fun railFenceCipherEncrypt(text: String, key: Int): String {
        if (key < 2 || key >= text.length) return text
        val sb = StringBuilder()
        var step1: Int
        var step2: Int
        for (row in 0 until key) {
            if (row == 0 || row == key - 1) {
                step2 = (key - 1) * 2
                step1 = step2
            } else {
                step1 = (key - 1) * 2 - row * 2
                step2 = row * 2
            }
            var x = 0
            var y = row
            while (y < text.length) {
                y += if (x == 0) {
                    sb.append(text[row])
                    step1
                } else {
                    if (x % 2 != 0) {
                        sb.append(text[y])
                        step2
                    } else {
                        sb.append(text[y])
                        step1
                    }
                }
                x++
            }
        }
        return sb.toString()
    }

    fun railFenceCipherDecrypt(text: String, key: Int): String {

        val boundaries: Int

        val ikey = key - 2

        val rowLengths = IntArray(key)

        if (text.length % (key - 1) != 0) {
            boundaries = text.length / (key - 1) + 1
        } else
            boundaries = text.length / (key - 1)

        val minRowLen = boundaries - 1

        for (i in rowLengths.indices) {
            rowLengths[i] = minRowLen
        }
        val remainder = text.length - (boundaries + ikey * minRowLen)

        if (boundaries % 2 == 0) {
            rowLengths[0] = boundaries / 2
            rowLengths[key - 1] = boundaries / 2
            for (i in key - 2 downTo key - 2 - remainder + 1) {
                rowLengths[i]++
            }
        } else {
            rowLengths[0] = boundaries / 2 + 1
            rowLengths[key - 1] = boundaries / 2
            for (i in 1..remainder) {
                rowLengths[i]++
            }
        }

        val steps = IntArray(key - 1)
        steps[0] = rowLengths[0]
        for (i in 1 until key - 1) {
            steps[i] = rowLengths[i] + steps[i - 1]
        }

        val sb = StringBuilder()

        var lastBackward = 1

        var backwardCounter = steps.size - 2

        var frw = true

        var x = 0
        var direction = 0
        while (x < text.length - 1) {

            if (x == 0) {
                sb.append(text[0])
            }
            if (direction >= key - 1) {
                direction = 0
                if (frw) {
                    frw = false
                    steps[steps.size - 1]++
                } else {
                    frw = true
                    lastBackward++
                    backwardCounter = steps.size - 2
                }
                for (i in 0 until steps.size - 1) {
                    steps[i]++
                }
            }
            if (frw) {
                if (direction == key - 2) {
                    sb.append(text[steps[direction]])
                } else {
                    sb.append(text[steps[direction]])
                }
            } else {
                if (direction == key - 2) {
                    sb.append(text[lastBackward])
                } else {
                    sb.append(text[steps[backwardCounter]])
                }
                backwardCounter--
            }
            x++
            direction++
        }
        return sb.toString()
    }
}