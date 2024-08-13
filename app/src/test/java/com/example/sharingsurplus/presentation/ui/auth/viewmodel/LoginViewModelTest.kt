package com.example.sharingsurplus.presentation.ui.auth.viewmodel

import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(authRepository = mockk())
    }

    @Test
    fun `validateForm should return true when both email and password are non-empty`() {
        val email = "test@example.com"
        val password = "password"

        val result = viewModel.validateForm(email, password)

        assertTrue(result)
    }

    @Test
    fun `validateForm should return false when both email and password are empty`() {
        val email = ""
        val password = ""

        val result = viewModel.validateForm(email, password)

        assertFalse(result)
    }
}