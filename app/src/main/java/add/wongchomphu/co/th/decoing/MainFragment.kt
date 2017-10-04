package add.wongchomphu.co.th.decoing


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import android.widget.CheckBox
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.R.attr.key




class MainFragment : Fragment() {

    @BindView(R.id.tv_show_input) lateinit var tvShowEncrypt: TextView
    @BindView(R.id.tv_show_output) lateinit var tvShowOutput: TextView
    @BindView(R.id.edt_encode) lateinit var edtEncode: EditText
    @BindView(R.id.edt_input_k) lateinit var edtValueK: EditText
    @BindView(R.id.btn_encrypt) lateinit var btnEncode: Button
    @BindView(R.id.btn_decrypt) lateinit var btnDecode: Button
    @BindView(R.id.cb_ShiftCaesar) lateinit var cbSiftCaesar: CheckBox
    @BindView(R.id.cb_rail_fence_cipher) lateinit var cbRailFenceCipher: CheckBox
    @BindView(R.id.cb_OTP) lateinit var cbOTP: CheckBox

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_mian, container, false)
        ButterKnife.bind(this, view)
        selectCheckbox()
        return view
    }

    private val indexOfChar = 26
    private fun Freevalue() {
        Toast.makeText(context, getString(R.string.Error_Message), Toast.LENGTH_LONG).show()
    }

    private fun selectCheckbox() {
        cbSiftCaesar.setOnClickListener {
            cbRailFenceCipher.isChecked = false
            cbOTP.isChecked = false
            ShiftCaesar()
        }
        cbRailFenceCipher.setOnClickListener {
            cbSiftCaesar.isChecked = false
            cbOTP.isChecked = false
            RailFenceCipher()
        }
        cbOTP.setOnClickListener {
            cbSiftCaesar.isChecked = false
            cbRailFenceCipher.isChecked = false
            OneTimePad()

        }
    }

    private fun ShiftCaesar() {
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Freevalue()
            } else {
                val key = edtValueK.text.toString()
                val encoded = ShiftCaesarEncryptElse(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = edtEncode.text.toString()
                tvShowOutput.text = ShiftCaesarDecrypt(encoded, key.toInt())

            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Freevalue()
            } else {
                val key = edtValueK.text.toString()
                val encoded = ShiftCaesarEncrypt(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = edtEncode.text.toString()
                tvShowOutput.text = encoded
            }
        }
    }

    private fun RailFenceCipher() {
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Freevalue()
            } else {
                val key = edtValueK.text.toString()
                val encoded = railFenceCipherEncrypt(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = edtEncode.text.toString()
                tvShowOutput.text = encoded
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Freevalue()
            } else {
                val key = edtValueK.text.toString()
                val encoded = railFenceCipherDecrypt(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = edtEncode.text.toString()
                tvShowOutput.text = encoded
            }
        }
    }

    private fun OneTimePad() {
        btnEncode.setOnClickListener {
            val key = edtValueK.text.toString()
            val encoded = Onetimepadencryptcipher().Encryption(edtEncode.text.toString(),key)
            tvShowEncrypt.text = edtEncode.text.toString()
            tvShowOutput.text = encoded
        }
    }


    private fun ShiftCaesarEncrypt(input: String, key: Int): String {
        val offset = key % indexOfChar
        if (offset == 0) return input
        var output: Char
        val chars = CharArray(input.length)
        for ((index, position) in input.withIndex()) {
            if (position in 'A'..'Z') {
                output = position + offset
                if (output > 'Z') output -= indexOfChar
            } else if (position in 'a'..'z') {
                output = position + offset
                if (output > 'z') output -= indexOfChar
            } else
                output = position
            chars[index] = output
        }
        return chars.joinToString("")
    }

    private fun ShiftCaesarEncryptElse(input: String, key: Int): String {
        val offset = (key % indexOfChar) - key
        if (offset == 0) return input
        var output: Char
        val chars = CharArray(input.length)
        for ((index, position) in input.withIndex()) {
            if (position in 'A'..'Z') {
                output = position + offset
                if (output > 'Z') output += indexOfChar
            } else if (position in 'a'..'z') {
                output = position + offset
                if (output > 'z') output += indexOfChar
            } else
                output = position
            chars[index] = output
        }
        return chars.joinToString("")
    }

    private fun ShiftCaesarDecrypt(input: String, key: Int): String = ShiftCaesarEncrypt(input, indexOfChar - key)
    private fun railFenceCipherEncrypt(text: String, key: Int): String {
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

    private fun railFenceCipherDecrypt(text: String, key: Int): String {

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

        class otpp {
            var a = 97
            var all = CharArray(27)
            init {
                for (i in 0..25) {
                    all[i] = a.toChar()
                    a++
                }
            }
            fun Ipos(c: Char): Int {
                var i = 0
                while (i < 26) {
                    if (all[i] == c) {
                        break
                    }
                    i++
                }
                return i
            }
            fun Cpos(c: Int): Char {
                var i = 0
                while (i < c) {
                    i++

                }
                return all[i]
            }
        }

        class Onetimepadencryptcipher {
            fun Encryption(plaintext: String, key: String): String {
                var plaintext = plaintext
                plaintext = plaintext.toLowerCase()
                val m1 = otpp()
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

            fun Decryption(ciphertext: String, key: String): String {
                var plaintext = ""
                val m1 = otpp()
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


    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
