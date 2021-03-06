package com.example.trending.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.trending.R
import com.example.trending.data.GitRepoData
import com.example.trending.data.network.Status
import com.example.trending.databinding.TrendingFragmentBinding
import com.example.trending.ui.adapter.TrendingListAdapter
import com.example.trending.viewmodel.TrendingViewModel
import com.example.trending.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_trending.*
import java.util.*
import javax.inject.Inject

class TrendingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val TAG = "trending_fragment"
        fun newInstance() = TrendingFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: TrendingFragmentBinding
    private lateinit var viewModel: TrendingViewModel
    private lateinit var listAdapter: TrendingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_trending,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrendingViewModel::class.java)
        initialiseViews()
    }

    private fun initialiseViews() {
        binding.reposRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            listAdapter = TrendingListAdapter()
            adapter = listAdapter
            setHasFixedSize(true)
        }
        // setup pull to refresh
        swipe_refresh.setOnRefreshListener(this)
        viewModel.apply {
            fetchUpdatedData(false)
            // observe live data from repository
            getRepositoryLiveData().observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.SUCCESS -> displayReposData(it.data)
                    Status.LOADING -> displayLoadingView()
                    Status.ERROR -> displayErrorLayout()
                }
            })
        }
        // handling click for retry, hit api and show shimmer
        retry_button.setOnClickListener {
            onRefresh()
        }
    }

    // method will be called on pull to refresh
    override fun onRefresh() {
        viewModel.fetchUpdatedData(true)
    }

    // method to sort repo list by names using comparator and notify adapter
    fun sortByNames() {
        // first check if there is data in list
        viewModel.getRepositoryLiveData().value?.let {
            if (!it.data.isNullOrEmpty()) {
                // sort in ascending
                Collections.sort(it.data,
                    kotlin.Comparator { t1, t2 -> t1.name.compareTo(t2.name, ignoreCase = true) })
                listAdapter.setRepositories(it.data)
            }
        }
    }

    // method to sort repo list by stars using comparator and notify adapter
    fun sortByStars() {
        // first check if there is data in list
        viewModel.getRepositoryLiveData().value?.let {
            if (!it.data.isNullOrEmpty()) {
                // sort in ascending
                Collections.sort(it.data,
                    kotlin.Comparator { t1, t2 -> t1.stars.toInt().compareTo(t2.stars.toInt()) })
                listAdapter.setRepositories(it.data)
            }
        }
    }

    // method to display data into list after fetching from repository
    private fun displayReposData(repositories: List<GitRepoData>?) {
        repositories?.let {
            listAdapter.setRepositories(repositories)
            error_layout.visibility = View.GONE
            hideShimmer()
            binding.reposRecycler.scheduleLayoutAnimation()
        }
    }

    // stop shimmer effect in onPause
    override fun onPause() {
        hideShimmer()
        super.onPause()
    }

    // method to show loading view
    private fun displayLoadingView() {
        if (listAdapter.itemCount == 0) {
            showShimmer()
        }
        error_layout.visibility = View.GONE
    }

    // method to show error layout
    private fun displayErrorLayout() {
        if (listAdapter.itemCount == 0) {
            error_layout.visibility = View.VISIBLE
        } else {
            error_layout.visibility = View.GONE
        }
        hideShimmer()
    }

    // method to show and start shimmer effect
    private fun showShimmer() {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
    }

    // method to hide and stop shimmer affect
    private fun hideShimmer() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }
    }

}
