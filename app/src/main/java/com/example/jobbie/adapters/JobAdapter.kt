package com.example.jobbie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.jobbie.R
import com.example.jobbie.databinding.JobLayoutAdapterBinding
import com.example.jobbie.fragments.MainFragmentDirections
import com.example.jobbie.models.Job

class JobAdapter: RecyclerView.Adapter<JobAdapter.RemoteJobViewHolder>() {
    private var binding: JobLayoutAdapterBinding? = null

    class RemoteJobViewHolder(itemBinding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object :
        DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding = JobLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)

            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .placeholder(R.drawable.ic_circle_24)
                .error(R.drawable.ic_baseline_error_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding?.ivCompanyLogo!!)



        }.setOnClickListener { mView ->
            val direction = MainFragmentDirections
                .actionMainFragmentToJobDetailsFragment(currentJob)
            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

}