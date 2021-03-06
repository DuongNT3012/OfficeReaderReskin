package com.document.officereader.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.amazic.ads.util.Admod
import com.amazic.ads.util.AppOpenManager
import com.document.officereader.BuildConfig
import com.document.officereader.R
import kotlinx.android.synthetic.main.activity_about_screen.*

class AboutScreenActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // set status bar gradient
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            if (window != null) {
                val background =
                    resources.getDrawable(R.drawable.bg_gradient_toolbar) //bg_gradient is your gradient.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = resources.getColor(android.R.color.transparent)
                window.setBackgroundDrawable(background)
            }
        }
        setContentView(R.layout.activity_about_screen)
        back_about.setOnClickListener {
            finish()
        }
        val link =
            "https://firebasestorage.googleapis.com/v0/b/office-reader-92c28.appspot.com/o/Privacy-Policy.html?alt=media&token=b66cf643-a75e-484e-a40d-4aee34901cae"
        tv_privacy_policy.setOnClickListener {
            AppOpenManager.getInstance()
                .disableAppResumeWithActivity(AboutScreenActivity::class.java)
            val intentPrivacy = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intentPrivacy)
        }
        val versionName = BuildConfig.VERSION_NAME
        tv_ver.text = resources.getString(R.string.Version) + " " + versionName
        //ads
        Admod.getInstance().loadBanner(this@AboutScreenActivity, getString(R.string.banner_all));
    }

    override fun onResume() {
        super.onResume()
        AppOpenManager.getInstance().enableAppResumeWithActivity(AboutScreenActivity::class.java)
    }
}