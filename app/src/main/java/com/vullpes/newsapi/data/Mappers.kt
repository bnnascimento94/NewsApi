package com.vullpes.newsapi.data

import com.vullpes.newsapi.data.local.ArticleDb
import com.vullpes.newsapi.data.remote.dto.ArticleDto
import com.vullpes.newsapi.domain.model.Article


fun ArticleDb.toArticle() = Article(
    articleId, author, content, description, publishedAt, title, url, urlToImage
)

fun ArticleDto.toArticleDb() = ArticleDb(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage
)

fun ArticleDto.toArticle() = Article(
    author = author,
    content = content,
    description = description,
    publishedAt = publishedAt,
    title = title,
    url = url,
    urlToImage = urlToImage
)