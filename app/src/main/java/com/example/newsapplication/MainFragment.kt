package com.example.newsapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.newsapplication.databinding.FragmentMainBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.newsapplication.adapters.MainFragmentAdapter
import com.example.newsapplication.models.Article
import com.example.newsapplication.utlis.NetworkResult
import com.example.newsapplication.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: MainFragmentAdapter
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mAdapter= MainFragmentAdapter(this,::onBookMarkClicked,::onBookMarkDelete)
        _binding=FragmentMainBinding.inflate(inflater,container,false)
        //mainViewModel.getNews()
        bindObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.getNews()
        binding.recyclerView.adapter= mAdapter
        binding.recyclerView.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.imgFavourite.setOnClickListener {
           // Log.d("pp1",mainViewModel.getBookMarks().toString())
            mainViewModel.getBookMarks()

        }

    }
    private fun bindObservers() {
        mainViewModel.dbLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("pp",it.toString())
            mAdapter.submitList(it)
        })
        mainViewModel.NewsLiveData.observe(viewLifecycleOwner, Observer {
            when (it){
                is NetworkResult.Success ->{
                    mAdapter.submitList(it.data?.articles)
                    Log.d("pp",it.data.toString())
                }
                is NetworkResult.Error -> {
                    Log.d("pp",it.data.toString())
                }
                is NetworkResult.Loading ->{
                    Log.d("pp",it.data.toString())
                }
            }
        })

    }
    private fun onBookMarkClicked(article: Article){
        mainViewModel.addBookMark(article)
    }
    private fun onBookMarkDelete(article: Article){
        //mainViewModel.deleteBookMark(id)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
        context?.startActivity(intent)
    }
}