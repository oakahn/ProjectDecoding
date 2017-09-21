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


class MainFragment : Fragment() {

    @BindView(R.id.tv_show_decoding) lateinit var tvShowCode: TextView
    @BindView(R.id.edt_encode) lateinit var edtEncode: EditText
    @BindView(R.id.edt_input_k) lateinit var edtValueK: EditText
    @BindView(R.id.btn_decode) lateinit var btnDecode: Button
    @BindView(R.id.tv_show_encrypt) lateinit var tvShowEncrypt: TextView
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
        cbSiftCaesar.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                cbRailFenceCipher.isChecked = false
                cbOTP.isChecked = false
            }
        }
        cbRailFenceCipher.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                cbSiftCaesar.isChecked = false
                cbOTP.isChecked = false
            }
        }
        cbOTP.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                cbSiftCaesar.isChecked = false
                cbRailFenceCipher.isChecked = false
            }
        }
    }

    private fun ShiftCaesar() {
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                Toast.makeText(context, "ต้องใส่ code หรือ key ให้ครบ", Toast.LENGTH_LONG).show()
            } else {
                val key = edtValueK.text.toString()
                val encoded = encrypt(edtEncode.text.toString(), key.toInt())
                tvShowEncrypt.text = encoded
                tvShowCode.text = decrypt(encoded, key = key.toInt())
            }
        }
    }

    private fun encrypt(s: String, key: Int): String {
        val offset = key % 26
        if (offset == 0) return s
        var d: Char
        val chars = CharArray(s.length)
        for ((index, c) in s.withIndex()) {
            if (c in 'A'..'Z') {
                d = c + offset
                if (d > 'Z') d -= 26
            } else if (c in 'a'..'z') {
                d = c + offset
                if (d > 'z') d -= 26
            } else
                d = c
            chars[index] = d
        }
        return chars.joinToString("")
    }

    private fun decrypt(s: String, key: Int): String = encrypt(s, 26 - key)

    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
