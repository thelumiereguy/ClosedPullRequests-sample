package dev.thelumiereguy.closed_pull_requests_sample

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dev.thelumiereguy.feature_closed_pr_list.ui.ClosedPRListingFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .apply {
                    replace(
                        R.id.fl_container,
                        ClosedPRListingFragment()
                    )
                }.commit()
        }
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
