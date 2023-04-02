package com.nvkhang96.dictionary.feature_dictionary.presentaion

import com.nvkhang96.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoState(
    val wordInfoItems: List<WordInfo> = emptyList(),
    val isLoading: Boolean = false
)
