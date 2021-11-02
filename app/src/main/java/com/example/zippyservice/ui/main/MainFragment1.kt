package com.example.zippyservice.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.zippyservice.R
import com.example.zippyservice.ui.main.MainViewModel


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var et: EditText
    private lateinit var sb: Button
    private lateinit var rt: TextView
    private lateinit var zt: TextView
    private lateinit var ct: TextView
    private lateinit var st: TextView
    private lateinit var ctt: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("Frank", "Inside onCreateView... about to inflate")

        var myroot = inflater.inflate(R.layout.main_fragment, container, false)
        findViews(myroot)

        return myroot
    }

    fun findViews(mr: View)  {
        et = mr.findViewById(R.id.input_edittext)
        sb = mr.findViewById(R.id.submit_button)
        zt = mr.findViewById(R.id.zipcode_textview)
        ct = mr.findViewById(R.id.country_textview)
        st = mr.findViewById(R.id.state_textview)
        ctt = mr.findViewById(R.id.city_textview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        et.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(myview: View?, keyCode: Int, event: KeyEvent): Boolean {
                Log.i("Frank", "Inside onKey()")
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    viewModel.getZipInfo(et.getText().toString())
                    return true
                } else
                    return false
            }
        })

        sb.setOnClickListener {
            Log.i("Frank", "Inside onClick()")
            var serviceReturn = viewModel.getZipInfo(et.getText().toString())
            Log.i("Frank", "SUBMIT: [" + serviceReturn.toString() + "]")
        }

        viewModel.response.observe(viewLifecycleOwner, {
            zt.setText(it.zipcode.toString())
            ct.setText(it.country.toString())
            st.setText(it.places.get(0).state)
            ctt.setText(it.places.get(0).place_name)
        })
    }
}