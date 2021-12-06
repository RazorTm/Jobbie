package com.example.jobbie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobbie.models.JobToSave
import com.example.jobbie.repo.JobRepository
import kotlinx.coroutines.launch

class JobViewModel (
    app: Application,
    private val remoteJobRepository: JobRepository
) : AndroidViewModel(app) {

    fun remoteJobResult() =
        remoteJobRepository.getRemoteJobResponseLiveData()

    fun searchJob(query: String?) =
        remoteJobRepository.searchRemoteJob(query)

    fun searchResult() = remoteJobRepository.getSearchJobResponseLiveData()

    fun insertJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.insertJob(job)
    }

    fun deleteJob(job: JobToSave) = viewModelScope.launch {
        remoteJobRepository.deleteJob(job)
    }

    fun getAllJob() = remoteJobRepository.getAllJobs()

}