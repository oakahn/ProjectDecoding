package add.wongchomphu.co.th.decoing


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

class MainFragment : Fragment() {
    @BindView(R.id.tv_show_decoding) lateinit var tvShowCode : TextView
    @BindView(R.id.edt_encode) lateinit var  edtEncode : EditText
    @BindView(R.id.edt_input_k) lateinit var edtValueK : EditText
    @BindView(R.id.btn_decode) lateinit var btnDecode : Button
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_mian, container, false)
        ButterKnife.bind(this, view)
        Encode()
        return view
    }
    fun Encode() {
        val a = listOf('A'..'Z').flatten()
        tvShowCode.text = a[25].toString()
    }
    companion object {
        fun newInstance() : MainFragment {
            val bundle = Bundle()
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
