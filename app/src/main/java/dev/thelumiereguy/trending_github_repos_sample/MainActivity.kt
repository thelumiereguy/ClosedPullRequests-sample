package dev.thelumiereguy.trending_github_repos_sample

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .apply {
//                    replace(
//                        R.id.fl_container,
//                        AudioBooksListingFragment()
//                    )
//                }.commit()
//        }
    }

    /**
     * Added special handling for Android Q
     * see [https://issuetracker.google.com/issues/139738913]
     */
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !isTaskRoot) {
            super.onBackPressed()
            return
        }

        finishAfterTransition()
    }
}
