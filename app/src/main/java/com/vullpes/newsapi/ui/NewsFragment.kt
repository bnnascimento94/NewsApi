package com.vullpes.newsapi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.FragmentNewsBinding
import com.vullpes.newsapi.ui.adapters.UltimasNoticiasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()

    private val myAdapter : UltimasNoticiasAdapter by lazy{
        UltimasNoticiasAdapter(
            openArticle = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            },
            saveNews = {
                viewModel.salvarNoticias(it)
                Snackbar.make(requireView(),"Successfully Saved", Snackbar.LENGTH_LONG).apply {
                    show()
                }
            }
        )
    }

    private lateinit var binding: FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news, container,false)

        binding.newsApi.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter

        }

        subscribeObservers()
        return binding.root
    }

    private fun subscribeObservers(){
        lifecycleScope.launch {
            viewModel.buscarUltimasNoticias().collectLatest {
                myAdapter.submitData(it)
            }
        }
    }

}