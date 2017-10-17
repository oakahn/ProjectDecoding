package add.wongchomphu.co.th.decoing.algarithm

import add.wongchomphu.co.th.decoing.MainFragment

open class OneTimePassword {
    fun encryption(plaintext: String, key: String): String {
        var plaintext = plaintext
        plaintext = plaintext.toLowerCase()
        val m1 = MainFragment.otpp()
        val pt = IntArray(plaintext.length)
        val k = IntArray(key.length)
        val ct = IntArray(plaintext.length)

        for (i in 0 until plaintext.length) {
            pt[i] = m1.Ipos(plaintext[i])
        }
        for (i in 0 until key.length) {
            k[i] = m1.Ipos(key[i])
        }
        var j = 0
        for (i in 0 until plaintext.length) {
            ct[i] = pt[i] + k[j]
            j++
            if (j == key.length)
                j = 0
            if (ct[i] > 26)
                ct[i] = ct[i] % 26
        }
        var cipher = ""
        for (i in 0 until plaintext.length) {
            cipher += m1.Cpos(ct[i])
        }

        return cipher
    }

    fun decryption(ciphertext: String, key: String): String {
        var plaintext = ""
        val m1 = MainFragment.otpp()
        val pt = IntArray(ciphertext.length)
        val k = IntArray(key.length)
        val ct = IntArray(ciphertext.length)

        for (i in 0 until ciphertext.length) {
            ct[i] = m1.Ipos(ciphertext[i])
        }
        for (i in 0 until key.length) {
            k[i] = m1.Ipos(key[i])
        }
        var j = 0
        for (i in 0 until ciphertext.length) {
            pt[i] = ct[i] - k[j]
            j++
            if (j == key.length)
                j = 0
            if (pt[i] < 0)
                pt[i] += 26
        }
        val cipher = ""
        for (i in 0 until ciphertext.length) {
            plaintext += m1.Cpos(pt[i])
        }
        return plaintext
    }
}