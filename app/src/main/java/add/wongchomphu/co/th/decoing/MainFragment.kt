package add.wongchomphu.co.th.decoing


import add.wongchomphu.co.th.decoing.algarithm.OneTimePassword
import add.wongchomphu.co.th.decoing.algarithm.RailFenceCihpher
import add.wongchomphu.co.th.decoing.algarithm.ShiftCaeSar
import android.R.attr.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import android.widget.CheckBox
import android.text.InputFilter
import android.widget.EditText

class MainFragment : Fragment(){

    @BindView(R.id.tv_show_input) lateinit var tvShowEncrypt: TextView
    @BindView(R.id.tv_show_output) lateinit var tvShowOutput: TextView
    @BindView(R.id.tv_show_k) lateinit var tvShowK: TextView
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
    private fun freeValue() {
        Toast.makeText(context, getString(R.string.Error_Message), Toast.LENGTH_LONG).show()
    }

    private fun decryptShiftCaeSar() {
        val key = edtValueK.text.toString()
        val encoded = ShiftCaeSar().shiftCaesarEncrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }

    private fun decryptOTP() {
        val key = edtValueK.text.toString()
        val encoded = OneTimePassword().Decryption(edtEncode.text.toString(), key)
        textViewShow(encoded, key)
    }

    private fun decryptRailFenceCipher() {
        val key = edtValueK.text.toString()
        val encoded = RailFenceCihpher().railFenceCipherDecrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }

    private fun encryptShiftCaeSar() {
        val key = edtValueK.text.toString()
        val encodedElse = ShiftCaeSar().shiftCaesarEncryptElse(edtEncode.text.toString(), key.toInt())
        val encoded = ShiftCaeSar().shiftCaesarDecrypt(encodedElse, key.toInt())
        textViewShow(encoded, key)
    }

    private fun encryptRailFenceCipher() {
        val key = edtValueK.text.toString()
        val encoded = RailFenceCihpher().railFenceCipherEncrypt(edtEncode.text.toString(), key.toInt())
        textViewShow(encoded, key)
    }
    private fun encryptOTP() {
        val key = edtValueK.text.toString()
        val encoded = OneTimePassword().Encryption(edtEncode.text.toString(), key)
        textViewShow(encoded, key)
    }

    private fun textViewShow(encoded: String, key: String) {
        tvShowEncrypt.text = edtEncode.text.toString()
        tvShowOutput.text = encoded
        tvShowK.text = key
    }
    private fun selectCheckbox() {
        cbSiftCaesar.setOnClickListener {
            checkBoxShiftTrue()
            clearEditText()
            shiftCaesar()
        }
        cbRailFenceCipher.setOnClickListener {
            checkBoxRailFenceTrue()
            clearEditText()
            railFenceCipher()
        }
        cbOTP.setOnClickListener {
            checkBoxOTPTrue()
            clearEditText()
            oneTimePad()
        }
    }

    private fun clearEditText() {
        edtEncode.setText("")
        edtValueK.setText("")
    }

    private fun checkBoxShiftTrue() {
        cbRailFenceCipher.isChecked = false
        cbOTP.isChecked = false
    }

    private fun checkBoxRailFenceTrue() {
        cbSiftCaesar.isChecked = false
        cbOTP.isChecked = false
    }

    private fun checkBoxOTPTrue() {
        cbSiftCaesar.isChecked = false
        cbRailFenceCipher.isChecked = false
    }

    private fun setEditTextMaxLength(editText: EditText, length: Int) {
        val FilterArray = arrayOfNulls<InputFilter>(1)
        FilterArray[0] = InputFilter.LengthFilter(length)
        editText.filters = FilterArray
    }

    private fun shiftCaesar() {
        edtValueK.inputType = numberPickerStyle
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptShiftCaeSar()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptShiftCaeSar()
            }
        }
    }

    private fun railFenceCipher() {
        edtValueK.inputType = numberPickerStyle
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptRailFenceCipher()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptRailFenceCipher()
            }
        }
    }

    private fun oneTimePad() {
        edtValueK.inputType = text
        setEditTextMaxLength(edtValueK, 10)
        setEditTextMaxLength(edtEncode, 10)
        btnEncode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                encryptOTP()
            }
        }
        btnDecode.setOnClickListener {
            if (edtEncode.length() == 0 || edtValueK.length() == 0) {
                freeValue()
            } else {
                decryptOTP()
            }
        }
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



    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
