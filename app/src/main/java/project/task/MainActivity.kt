package example.task

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*

import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val oneTimeBtn = findViewById<Button>(R.id.oneTimeBtn)
        val periodicBtn = findViewById<Button>(R.id.periodicBtn)
        val statusText = findViewById<TextView>(R.id.statusText)

        // One-time task
        oneTimeBtn.setOnClickListener {

            statusText.text = "Starting One-Time Task..."

            val data = Data.Builder()
                .putString("task", "API Update")
                .build()

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workRequest = OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()

            val wm = WorkManager.getInstance(this)
            wm.enqueue(workRequest)

            wm.getWorkInfoByIdLiveData(workRequest.id)
                .observe(this, Observer { workInfo ->

                    if (workInfo != null) {
                        when (workInfo.state) {
                            WorkInfo.State.RUNNING -> {
                                statusText.text = "Running..."
                            }
                            WorkInfo.State.SUCCEEDED -> {
                                val result = workInfo.outputData.getString("result")
                                statusText.text = "Success: $result"
                            }
                            WorkInfo.State.FAILED -> {
                                statusText.text = "Failed ❌"
                            }
                            else -> {}
                        }
                    }
                })
        }

        // Periodic task
        periodicBtn.setOnClickListener {

            statusText.text = "Periodic Task Started..."

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val periodicWork = PeriodicWorkRequestBuilder<MyWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(this).enqueue(periodicWork)
        }
    }
}