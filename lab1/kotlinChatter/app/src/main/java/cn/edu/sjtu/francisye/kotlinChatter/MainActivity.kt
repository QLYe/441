package cn.edu.sjtu.francisye.kotlinChatter

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.edu.sjtu.francisye.kotlinChatter.ChattStore.chatts
import cn.edu.sjtu.francisye.kotlinChatter.ChattStore.getChatts
import cn.edu.sjtu.francisye.kotlinChatter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var view: ActivityMainBinding
    private lateinit var chattListAdapter: ChattListAdapter

    private fun refreshTimeline() {
        getChatts(applicationContext) {
            runOnUiThread {
                // inform the list adapter that data set has changed
                // so that it can redraw the screen.
                chattListAdapter.notifyDataSetChanged()
            }
            // stop the refreshing animation upon completion:
            view.refreshContainer.isRefreshing = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityMainBinding.inflate(layoutInflater)
        view.root.setBackgroundColor(Color.parseColor("#E0E0E0"))
        setContentView(view.root)

        chattListAdapter = ChattListAdapter(this, chatts)
        view.chattListView.setAdapter(chattListAdapter)

        // setup refreshContainer here later
        view.refreshContainer.setOnRefreshListener {
            refreshTimeline()
        }

        refreshTimeline()
    }
    fun startPost(view: View?) = startActivity(Intent(this, PostActivity::class.java))

}