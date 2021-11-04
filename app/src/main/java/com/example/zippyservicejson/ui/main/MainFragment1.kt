package com.example.zippyservicejson.ui.main

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
import com.example.zippyservicejson.R
import kotlinx.coroutines.delay


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

    private lateinit var lat: TextView
    private lateinit var long: TextView

    /* ***********************************************************
     onCreateView() - When fragment is first created
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("Frank", "Inside onCreateView... about to inflate")

        var myroot = inflater.inflate(R.layout.main_fragment, container, false)
        findViews(myroot)

        return myroot
    }

    /* ***********************************************************
     * findViews() - convenience method
     */
    fun findViews(mr: View)  {
        et = mr.findViewById(R.id.input_edittext)
        sb = mr.findViewById(R.id.submit_button)
        zt = mr.findViewById(R.id.zipcode_textview)
        ct = mr.findViewById(R.id.country_textview)
        st = mr.findViewById(R.id.state_textview)
        ctt = mr.findViewById(R.id.city_textview)

        lat = mr.findViewById(R.id.latitude_textview)
        long = mr.findViewById(R.id.longitude_textview)

    }

    /* ***********************************************************
    * onActivityCreated() - When parent Activity created
    */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Check for <CR> on entered Zipcode ---------------------------------
        et.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(myview: View?, keyCode: Int, event: KeyEvent): Boolean {
                Log.i("Frank", "Inside onKey()")
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    sb.callOnClick()        // see below
                    return true
                } else
                    return false
            }
        })

        // When Submit button clicked/touched ---------------------------------
        sb.setOnClickListener {
            Log.i("Frank", "Inside onClick()")
            var serviceReturn = viewModel.getZipInfo(et.getText().toString())
            sb.setText("working...")
        }

        // What to do when ViewModel's data changes (when service response arrives) -------
        viewModel.response.observe(viewLifecycleOwner, {
            zt.setText(it.zipcode.toString())
            ct.setText(it.country.toString())
            st.setText(it.places.get(0).state)
            ctt.setText(it.places.get(0).place_name)
            lat.setText(it.places.get(0).latitude)
            long.setText(it.places.get(0).longitude)
            sb.setText("SUBMIT")
        })
    }
}