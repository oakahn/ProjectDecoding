package add.wongchomphu.co.th.decoing


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import android.widget.CheckBox


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

    private fun selectCheckbox() {
        cbSiftCaesar.setOnClickListener {
            cbRailFenceCipher.isChecked = false
            cbOTP.isChecked = false
            ShiftCaesar()
        }
        cbRailFenceCipher.setOnClickListener {
            cbSiftCaesar.isChecked = false
            cbOTP.isChecked = false
        }
        cbOTP.setOnClickListener {
            cbSiftCaesar.isChecked = false
            cbRailFenceCipher.isChecked = false
        }
    }

    private fun ShiftCaesar() {
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Toast.makeText(context, getString(R.string.Error_Message), Toast.LENGTH_LONG).show()
            } else {
                val key = edtValueK.text.toString()
                val encoded = encryptElse(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = encoded
                tvShowOutput.text = decrypt(encoded, key.toInt())

            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Toast.makeText(context, getString(R.string.Error_Message), Toast.LENGTH_LONG).show()
            } else {
                val key = edtValueK.text.toString()
                val encoded = encrypt(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = decrypt(encoded, key.toInt())
                tvShowOutput.text = encoded
            }
        }
    }
    private fun encrypt(input: String, key: Int): String {
        val indexOfChar = 26
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
    private fun encryptElse(input: String, key: Int): String {
        val indexOfChar = 26
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
    private fun decrypt(input: String, key: Int): String = encrypt(input, 26 - key)
    private fun railFenceCipherEncrypt(text: String, rows: Int): String {
            if (rows < 2 || rows >= text.length) return text
            val sb = StringBuilder()
            var step1: Int
            var step2: Int
            for (row in 0 until rows) {
                if (row == 0 || row == rows - 1) {
                    step2 = (rows - 1) * 2
                    step1 = step2
                } else {
                    step1 = (rows - 1) * 2 - row * 2
                    step2 = row * 2
                }
                var x = 0
                var y = row
                while (y < text.length) {
                    if (x == 0) {
                        sb.append(text[row])
                        y += step1
                    } else {
                        if (x % 2 != 0) {
                            sb.append(text[y])
                            y += step2
                        } else {
                            sb.append(text[y])
                            y += step1
                        }
                    }
                    x++
                }
            }
            return sb.toString()
    }
    private fun otp() {

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
