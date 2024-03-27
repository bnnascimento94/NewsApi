package com.vullpes.newsapi.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.FragmentNewsBinding
import com.vullpes.newsapi.databinding.FragmentSearchNewsBinding
import com.vullpes.newsapi.ui.adapters.NoticiasBuscadasAdapter
import com.vullpes.newsapi.ui.adapters.UltimasNoticiasAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentSearchNewsBinding
    private val myAdapter : NoticiasBuscadasAdapter by lazy{
        NoticiasBuscadasAdapter(
            openArticle = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(browserIntent)
            },
            saveArticle = {
                viewModel.salvarNoticias(it)
                Snackbar.make(requireView(),"Successfully Saved", Snackbar.LENGTH_LONG).apply {
                    show()
                }
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_news, container,false)

        binding.rvSearchedNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }
        
        binding.btnPesquisar.setOnClickListener {
            lifecycleScope.launch {
                binding.noContent.visibility = View.GONE
                viewModel.buscarNoticiasBuscadas(binding.txtPesquisar.text.toString()).collectLatest {
                    myAdapter.submitData(it)
                }
            }

        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.txtPesquisar.setText("")
    }




}