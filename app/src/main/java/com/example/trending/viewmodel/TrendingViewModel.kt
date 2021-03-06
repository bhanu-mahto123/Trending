package com.example.trending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.trending.data.GitRepoData
import com.example.trending.data.network.Resource
import com.example.trending.data.repository.TrendingRepository
import javax.inject.Inject

class TrendingViewModel @Inject constructor(private val dataRepository: TrendingRepository) :
    ViewModel() {

    private val repoData = MutableLiveData<Boolean>()

    private val repositoryListLiveData: LiveData<Resource<List<GitRepoData>?>> =
        Transformations.switchMap(
            repoData,
            { input -> dataRepository.getRepositories(input) }
        )

    fun getRepositoryLiveData() = repositoryListLiveData

    fun fetchUpdatedData(forceRefresh: Boolean) {
        repoData.value = forceRefresh
    }
}
