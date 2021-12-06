package com.example.jobbie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jobbie.databinding.ActivityMainBinding
import com.example.jobbie.db.JobDatabase
import com.example.jobbie.repo.JobRepository
import com.example.jobbie.viewmodel.JobViewModel
import com.example.jobbie.viewmodel.JobViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: JobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setUpViewModel()

    }


    private fun setUpViewModel() {

        val remoteJobRepository = JobRepository(
            JobDatabase(this))

        val viewModelProviderFactory =
            JobViewModelFactory(
                application,
                remoteJobRepository
            )

        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(JobViewModel::class.java)

    }
}