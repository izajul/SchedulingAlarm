package com.example.trialtaskofcridusa.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.trialtaskofcridusa.R
import com.example.trialtaskofcridusa.retroFiles.RetroClient
import com.example.trialtaskofcridusa.utils.FunctionsUtils
import com.example.trialtaskofcridusa.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.ceil


class HomeFragment : Fragment() {

    lateinit var mName : TextInputEditText
    lateinit var mEmail : TextInputEditText
    lateinit var mPhone : TextInputEditText
    lateinit var mAddress : TextInputEditText
    lateinit var mDrug : TextInputEditText
    lateinit var mFood : TextInputEditText
    lateinit var submitBtn : Button
    lateinit var retroClient: RetroClient
    lateinit var checkBox: CheckBox
    lateinit var progressbar:ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        initViewClick(root)
        retroClient = RetroClient().getInstance()!!
        return root
    }

    private fun initViewClick(root: View){
        progressbar = root.findViewById(R.id.progressbar)
        mName = root.findViewById(R.id.contact_name)
        mEmail = root.findViewById(R.id.contact_email)
        mPhone = root.findViewById(R.id.contact_phone)
        mAddress = root.findViewById(R.id.contact_address)
        mDrug = root.findViewById(R.id.drug_alergy)
        mFood = root.findViewById(R.id.food_alergy)
        submitBtn = root.findViewById(R.id.submit_btn)
        checkBox = root.findViewById(R.id.locationCheckBox)
        submitBtn.setOnClickListener{
            submitData()
        }

    }

    private fun submitData() {

        var strName : String = FunctionsUtils.getText(mName).trim()
        var strPhone : String = FunctionsUtils.getText(mPhone).trim()
        var strEmail : String = FunctionsUtils.getText(mEmail).trim()
        var strAddress : String = FunctionsUtils.getText(mAddress).trim()
        var strDrug : String = FunctionsUtils.getText(mDrug).trim()
        var strFood : String = FunctionsUtils.getText(mFood).trim()

        var params  =  HashMap<String,Any>()

        if ( strName.isEmpty()
            or strPhone.isEmpty()
            or strEmail.isEmpty()
            or strAddress.isEmpty()){
            Toast.makeText(context, "Fields should not empty!", Toast.LENGTH_SHORT).show()
        } else {
            progressbar.visibility= View.VISIBLE
            params["emergency_contact_name"] = strName
            params["primary_care_contact_number"] = strPhone
            params["primary_care_contact_email"] = strEmail
            params["current_address"] = ""
            params["allow_current_location_finder"] = if (checkBox.isChecked) "1" else "0"
            params["drug_alergy"] = strDrug
            params["food_alergy"] = strFood
            params["allergy"] = ""

            var call: Call<JsonObject?>? = retroClient.getApi().subMitData(params)
            call!!.enqueue(object : Callback<JsonObject?> {
                override fun onResponse(
                    call: Call<JsonObject?>?,
                    response: Response<JsonObject?>?
                ) {
                    var data : JsonObject? = response?.body()
                    data?.let { Log.d("response_",Gson().toJson(data)) }
                    if (FunctionsUtils.getStrFromJsonObject(data!!,Utils.RESP_ERROR_STATUS).equals("false")){
                        FunctionsUtils.showTopSnacbar(context!!,view!!,FunctionsUtils.getStrFromJsonObject(data,Utils.RESP_MESSAGE))
                    }
                    progressbar.visibility= View.GONE
                }

                override fun onFailure(call: Call<JsonObject?>?, t: Throwable?) {
                    t?.message?.let { Log.e("response_", it) }
                    FunctionsUtils.showTopSnacbar(context!!,view!!,"Error Occurs !!!")
                    progressbar.visibility= View.GONE
                }

            })

        }
    }
}