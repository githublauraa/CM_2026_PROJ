package com.example.voxel_review.viewModels

import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.junit.Before
import com.google.firebase.firestore.firestore
import com.example.voxel_review.data.dataSource.AuthRemoteDataSource
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.map

class RegisterViewModelIntegrationTest {

    private lateinit var viewModel: CreateAccountViewModel
    private lateinit var authRepository: AuthRepository

    @Before
    fun setup() {

        try {
            Firebase.auth.useEmulator("10.0.2.2", 9099)
            Firebase.firestore.useEmulator("10.0.2.2", 8080)
        } catch (e: Exception) {
        }

        authRepository = AuthRepository(AuthRemoteDataSource(Firebase.auth))
        val userRepository = UserRepository(UserFirestoreDataSourceImpl(Firebase.firestore), AuthRepository(AuthRemoteDataSource(Firebase.auth)))

        viewModel = CreateAccountViewModel(authRepository, userRepository)
    }

    @After
    fun tearDown() = runTest {
        val user = Firebase.auth.currentUser
        if (user != null){
            Firebase.auth.signOut()
        }
        user?.delete()?.await()
    }

    @Test
    fun register_success_CreateUserAndUpdateUI() = runTest {
        // Arrange
        // Permite esperar a que el VM ejecute onSuccess antes de verificar el resultado.
        val registroExitoso = MutableStateFlow(false)

        viewModel.updateUsername("Juan")
        viewModel.updateEmail("juan@voxel.com")
        viewModel.updatePassword("123456")
        viewModel.updateBio("Hola, soy Juan")
        viewModel.updateTerminosAceptados()

        // Act
        viewModel.createAcount {
            registroExitoso.value = true
        }

        // Esperar hasta que se ejecute el callback.
        val resultado = registroExitoso.first { it }

        // Assert
        assertThat(resultado).isTrue()

        val state = viewModel.uiState.value

        assertThat(state.errorMessage).isEmpty()
        assertThat(state.mostrarMensaje).isFalse()
    }

    //correo en uso
    @Test
    fun register_alredyUsedEmail_showError() = runTest {
        authRepository.signUp("juan@voxel.com", "123456")
        // Arrange
        // Permite esperar a que el VM ejecute onSuccess antes de verificar el resultado.
        val registroExitoso = MutableStateFlow(false)

        viewModel.updateUsername("Juan")
        viewModel.updateEmail("juan@voxel.com")
        viewModel.updatePassword("123456")
        viewModel.updateBio("Hola, soy Juan")
        viewModel.updateTerminosAceptados()

        // Act
        viewModel.createAcount {
            registroExitoso.value = true
        }

        // Esperar hasta que se ejecute el callback.
        var resultado = viewModel.uiState.map { it.loading }.first { it }
        assertThat(resultado).isTrue()

        resultado = viewModel.uiState.map { it.loading }.first { !it }
        // Assert
        assertThat(resultado).isFalse()

        val state = viewModel.uiState.value

        assertThat(state.errorMessage).isNotEmpty()
        assertThat(state.errorMessage).isEqualTo("El correo electrónico ya está en uso")
    }


    //correo en uso
    @Test
    fun register_emailInvalid_showError() = runTest {
        // Arrange
        // Permite esperar a que el VM ejecute onSuccess antes de verificar el resultado.
        val registroExitoso = MutableStateFlow(false)

        viewModel.updateUsername("Juan")
        viewModel.updateEmail("juanvoxel.com")
        viewModel.updatePassword("123456")
        viewModel.updateBio("Hola, soy Juan")
        viewModel.updateTerminosAceptados()

        // Act
        viewModel.createAcount {
            registroExitoso.value = true
        }

        // Esperar hasta que se ejecute el callback.
        var resultado = viewModel.uiState.map { it.loading }.first { it }
        assertThat(resultado).isTrue()

        resultado = viewModel.uiState.map { it.loading }.first { !it }
        // Assert
        assertThat(resultado).isFalse()

        val state = viewModel.uiState.value

        assertThat(state.errorMessage).isNotEmpty()
        assertThat(state.errorMessage).isEqualTo("El correo no es valido")
    }

}