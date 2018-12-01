package com.example.morganseielstad.politicado


import android.os.Bundle

import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().run {
            replace(R.id.main_fragment, MainMenuViewFragment())
            commit()
        }
    }
}
