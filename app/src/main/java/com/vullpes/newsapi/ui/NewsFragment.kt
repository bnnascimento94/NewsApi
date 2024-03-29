package com.vullpes.newsapi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.FragmentNewsBinding
import com.vullpes.newsapi.ui.adapters.UltimasNoticiasAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewsFragment : Fragment() {

    val viewModel: NewsViewModel by activityViewModels()

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
        checkOnStates()
        return binding.root
    }

    private fun subscribeObservers(){
        lifecycleScope.launch {
            viewModel.buscarUltimasNoticias().collectLatest {
                myAdapter.submitData(it)
            }
        }
    }

    private fun  checkOnStates(){
        lifecycleScope.launch {
            myAdapter.loadStateFlow.collectLatest { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading
                val isError = loadStates.refresh is LoadState.Error

                if (isLoading) {
                    binding.noContent.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE

                } else if (isError) {
                    val error = (loadStates.refresh as? LoadState.Error)?.error.toString()
                    binding.noContent.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    withContext(Dispatchers.Main){
                        Toast.makeText(requireContext(),error, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.noContent.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    // Dados carregados com sucesso
                }
            }
        }
    }


}