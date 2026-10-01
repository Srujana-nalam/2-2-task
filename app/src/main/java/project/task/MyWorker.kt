package example.task

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {

        return try {

            // Simulate API call
            Thread.sleep(3000)

            // Get input data
            val input = inputData.getString("task") ?: "No Task"

            // Prepare output
            val output = androidx.work.Data.Builder()
                .putString("result", "Completed: $input")
                .build()

            Result.success(output)

        } catch (e: Exception) {
            Result.failure()
        }
    }
}