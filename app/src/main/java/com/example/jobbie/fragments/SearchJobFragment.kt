package com.example.jobbie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobbie.MainActivity
import com.example.jobbie.R
import com.example.jobbie.adapters.JobAdapter
import com.example.jobbie.databinding.FragmentSearchJobBinding
import com.example.jobbie.utils.Constants
import com.example.jobbie.viewmodel.JobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchJobFragment : Fragment(R.layout.fragment_search_job) {

    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: JobViewModel
    private lateinit var jobAdapter: JobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchJobBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        if (Constants.isNetworkAvailable(requireContext())) {
            searchJob()
        } else {
            Toast.makeText(activity,"No internet connection", Toast.LENGTH_SHORT).show()
        }
    }


    private fun searchJob() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchJob(editable.toString())
                    }
                }
            }
        }
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        jobAdapter = JobAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdapter
        }

        viewModel.searchResult().observe(viewLifecycleOwner, { remoteJob ->
            jobAdapter.differ.submitList(remoteJob.jobs)
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}