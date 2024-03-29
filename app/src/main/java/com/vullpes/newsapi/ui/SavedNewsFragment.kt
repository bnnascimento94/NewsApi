package com.vullpes.newsapi.ui

import android.content.Intent
import android.icu.lang.UCharacter
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vullpes.newsapi.R
import com.vullpes.newsapi.databinding.FragmentNewsBinding
import com.vullpes.newsapi.databinding.FragmentSavedNewsBinding
import com.vullpes.newsapi.ui.adapters.NoticiasSalvasAdapter
import kotlinx.coroutines.launch


class SavedNewsFragment : Fragment() {

    val viewModel: NewsViewModel by activityViewModels()
    private lateinit var binding: FragmentSavedNewsBinding

    private val myAdapter : NoticiasSalvasAdapter by lazy{
        NoticiasSalvasAdapter{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_saved_news, container,false)

        binding.rvSaved.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
        subscribeObservers()
        return binding.root
    }

    private val itemTouchCallback = object: ItemTouchHelper.SimpleCallback(
        0, UCharacter.IndicPositionalCategory.LEFT or UCharacter.IndicPositionalCategory.RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = myAdapter.noticiasSalvas[pos]
            viewModel.deletarNoticia(item)
            Snackbar.make(requireView(),"Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo"){
                    viewModel.salvarNoticias(item)
                }
                show()
            }
        }
    }

    private fun subscribeObservers(){
        lifecycleScope.launch {
            viewModel.buscarNoticiasSalvas().observe(viewLifecycleOwner){
                myAdapter.submitData(it)
            }
        }
    }


}