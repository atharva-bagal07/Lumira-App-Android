package com.example.lumira.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReadingViewModel : ViewModel() {

    private val _selectedStyle = MutableStateFlow("")
    val selectedStyle: StateFlow<String> = _selectedStyle

    private val _selectedArea = MutableStateFlow("")
    val selectedArea: StateFlow<String> = _selectedArea

    private val _selectedMood = MutableStateFlow("")
    val selectedMood: StateFlow<String> = _selectedMood

    private val _question = MutableStateFlow("")
    val question: StateFlow<String> = _question

    private val _readingResult = MutableStateFlow("")
    val readingResult: StateFlow<String> = _readingResult

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    fun setStyle(style: String) {
        _selectedStyle.value = style
    }

    fun setArea(area: String) {
        _selectedArea.value = area
    }

    fun setMood(mood: String) {
        _selectedMood.value = mood
    }

    fun setQuestion(q: String) {
        _question.value = q
    }

    fun setReadingResult(result: String) {
        _readingResult.value = result
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    fun setError(err: String) {
        _error.value = err
    }

    fun reset() {
        _selectedStyle.value = ""
        _selectedArea.value = ""
        _selectedMood.value = ""
        _question.value = ""
        _readingResult.value = ""
        _isLoading.value = false
        _error.value = ""
    }
}