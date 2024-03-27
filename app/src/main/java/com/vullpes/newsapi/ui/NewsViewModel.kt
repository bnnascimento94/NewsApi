package com.vullpes.newsapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vullpes.newsapi.domain.model.Article
import com.vullpes.newsapi.domain.usecases.DeleteArticleUseCase
import com.vullpes.newsapi.domain.usecases.GetLatestNewsUsecase
import com.vullpes.newsapi.domain.usecases.GetSavedNewsUsecase
import com.vullpes.newsapi.domain.usecases.SaveNewsUserCase
import com.vullpes.newsapi.domain.usecases.SearchNewsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNewsUsecase: GetLatestNewsUsecase,
    private val getSavedNewsUsecase: GetSavedNewsUsecase,
    private val saveNewsUserCase: SaveNewsUserCase,
    private val searchNewsUsecase: SearchNewsUsecase,
    private val deleteArticleUseCase: DeleteArticleUseCase
): ViewModel() {

    private var subject:String = ""

    fun buscarUltimasNoticias() = getLatestNewsUsecase.invoke().cachedIn(viewModelScope)


    fun salvarNoticias(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        saveNewsUserCase.invoke(article)
    }

    fun deletarNoticia(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        deleteArticleUseCase.invoke(article)
    }

    fun buscarNoticiasBuscadas(query: String) = searchNewsUsecase.invoke(query)

    fun buscarNoticiasSalvas() = getSavedNewsUsecase.invoke().asLiveData()


}