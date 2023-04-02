package com.nvkhang96.dictionary.feature_dictionary.domain.repository

import com.nvkhang96.dictionary.core.util.Resource
import com.nvkhang96.dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}