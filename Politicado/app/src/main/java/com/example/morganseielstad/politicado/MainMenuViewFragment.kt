package com.example.morganseielstad.politicado

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


class MainMenuViewFragment :  Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
            R.layout.menu_layout,
            container, false)

        var nationalButton: Button = rootView.findViewById(R.id.national_button)

        nationalButton.setOnClickListener {
                Log.i("Button", "National Button selected.")

                val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
                //Log.d("ft:", ft.toString())
                ft.replace(R.id.main_fragment, PoliticianListFragment())
                ft.addToBackStack(ft.toString())

                ft.commit()
                Log.i("Commit", "Complete")
        }



        return rootView
    }
}


