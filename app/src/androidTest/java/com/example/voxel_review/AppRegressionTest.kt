package com.example.voxel_review

import android.util.Log
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.voxel_review.data.dataSource.impl.firestore.UserFirestoreDataSourceImpl
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

/** Pruebas reales optativas: -e liveChecks true, con una cuenta de prueba ya iniciada. */
@RunWith(AndroidJUnit4::class)
class AppRegressionTest {
    @get:Rule val ui = createAndroidComposeRule<MainActivity>()
    private val db get() = FirebaseFirestore.getInstance()
    private val uid get() = FirebaseAuth.getInstance().currentUser!!.uid
    private fun <T> read(task: Task<T>): T = Tasks.await(task, 30, TimeUnit.SECONDS)
    private fun waitFor(matcher: SemanticsMatcher) {
        ui.waitUntil(30_000) { ui.onAllNodes(matcher).fetchSemanticsNodes().isNotEmpty() }
    }
    private fun text(value: String) = hasText(value)
    private fun clickText(value: String) {
        waitFor(text(value))
        val node = ui.onNodeWithText(value)
        try {
            node.assertIsDisplayed()
        } catch (error: AssertionError) {
            node.performScrollTo()
        }
        node.performClick()
    }
    private fun nav(route: String) {
        waitFor(hasContentDescription(route))
        ui.onNodeWithContentDescription(route).performClick()
    }
    private fun back() {
        waitFor(hasContentDescription("Volver"))
        ui.onNodeWithContentDescription("Volver").performClick()
    }
    private fun persisted(message: String, check: () -> Boolean) {
        val deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30)
        while (!check()) {
            if (System.nanoTime() > deadline) fail(message)
            Thread.sleep(250)
        }
    }
    private fun passed(name: String) { Log.i("VOXEL_QA", "PASS: $name") }

    @Before fun requireExplicitLiveRun() {
        Assume.assumeTrue(InstrumentationRegistry.getArguments().getString("liveChecks") == "true")
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        for (permission in listOf("android.permission.NEARBY_WIFI_DEVICES", "android.permission.ACCESS_LOCAL_NETWORK", "android.permission.POST_NOTIFICATIONS")) {
            instrumentation.uiAutomation.executeShellCommand("pm grant com.example.voxel_review $permission").close()
        }
        ui.waitUntil(15_000) { FirebaseAuth.getInstance().currentUser != null }
        waitFor(hasContentDescription("discover"))
    }

    @Test fun navigationSearchSettingsAndNotifications() {
        nav("discover")
        waitFor(hasSetTextAction())
        ui.onAllNodes(hasSetTextAction())[0].performTextReplacement("zzzz_qa_sin_resultados")
        waitFor(hasText("No encontramos juegos", substring = true))
        ui.onNode(hasText("No encontramos juegos", substring = true)).assertIsDisplayed()
        ui.onAllNodes(hasSetTextAction())[0].performTextClearance()
        waitFor(text("En tendencia"))
        passed("Buscar y limpiar búsqueda")
        ui.onNodeWithContentDescription("Notificaciones").performClick()
        waitFor(text("DESTACADOS"))
        ui.onNodeWithText("Sugerencias de seguimiento").assertExists()
        back()
        nav("perfilUser")
        waitFor(text("Cambiar imagen"))
        ui.onNodeWithContentDescription("Configuración").performClick()
        waitFor(text("AJUSTES GENERALES"))
        ui.onNodeWithText("Notificaciones").assertExists()
        ui.onNodeWithText("PREFERENCIAS GAMER").assertExists()
        back()
        waitFor(text("Cambiar imagen"))
        passed("Navegación a Notificaciones, Perfil y Configuración y regreso")
        nav("reviews")
        waitFor(text("Novedades y Reseñas"))
        waitFor(hasText("VIDEOJUEGO"))
        passed("Novedades con juegos y puntuaciones")
    }

    @Test fun usersFollowAndUnfollow() {
        val selfRef = db.collection("users").document(uid)
        val following = read(selfRef.collection("following").get(Source.SERVER)).documents.map { it.id }
        val target = read(db.collection("users").get(Source.SERVER)).documents.first {
            it.id != uid && it.id !in following && !it.getString("username").isNullOrBlank()
        }
        val targetRef = target.reference
        val initialSelfCount = read(selfRef.get(Source.SERVER)).getLong("followingCount") ?: 0
        val initialTargetCount = target.getLong("followersCount") ?: 0
        val followingRef = selfRef.collection("following").document(target.id)
        val followerRef = targetRef.collection("followers").document(uid)
        try {
            nav("rankingsUser")
            waitFor(hasText(target.getString("username")!!))
            clickText(target.getString("username")!!)
            clickText("seguir")
            waitFor(text("siguiendo"))
            assertTrue(read(followingRef.get(Source.SERVER)).exists())
            assertTrue(read(followerRef.get(Source.SERVER)).exists())
            assertEquals(initialSelfCount + 1, read(selfRef.get(Source.SERVER)).getLong("followingCount"))
            assertEquals(initialTargetCount + 1, read(targetRef.get(Source.SERVER)).getLong("followersCount"))
            passed("Ver usuario, seguir, relación recíproca y contadores")
            clickText("siguiendo")
            waitFor(text("seguir"))
            assertFalse(read(followingRef.get(Source.SERVER)).exists())
            assertFalse(read(followerRef.get(Source.SERVER)).exists())
            assertEquals(initialSelfCount, read(selfRef.get(Source.SERVER)).getLong("followingCount") ?: 0)
            assertEquals(initialTargetCount, read(targetRef.get(Source.SERVER)).getLong("followersCount") ?: 0)
            passed("Dejar de seguir y restaurar contadores")
        } finally {
            if (read(followingRef.get(Source.SERVER)).exists()) {
                runBlocking { UserFirestoreDataSourceImpl(db).followOrUnfollow(uid, target.id) }
            }
        }
    }

    @Test fun editProfileAndRestoreOriginalValues() {
        val ref = db.collection("users").document(uid)
        val original = read(ref.get(Source.SERVER))
        val oldBio = original.getString("bio").orEmpty()
        val oldUsername = original.getString("username").orEmpty()
        val marker = "QA_PERFIL_${System.currentTimeMillis()}"
        try {
            nav("perfilUser")
            clickText("Editar Perfil")
            waitFor(hasSetTextAction())
            ui.waitUntil(15_000) { ui.onAllNodes(hasSetTextAction()).fetchSemanticsNodes().size == 2 }
            ui.onAllNodes(hasSetTextAction())[0].performTextReplacement(marker)
            ui.onAllNodes(hasSetTextAction())[1].performTextReplacement("QA_perfil")
            clickText("Guardar cambios")
            persisted("El perfil no se guardó") {
                val saved = read(ref.get(Source.SERVER))
                saved.getString("bio") == marker && saved.getString("username") == "QA_perfil"
            }
            passed("Editar biografía y username desde la pantalla")
        } finally {
            read(ref.update(mapOf("bio" to oldBio, "username" to oldUsername)))
            assertEquals(oldBio, read(ref.get(Source.SERVER)).getString("bio"))
            assertEquals(oldUsername, read(ref.get(Source.SERVER)).getString("username"))
            passed("Restaurar biografía y username originales")
        }
    }

    @Test fun otherAuthorProfileAndReviewPermissions() {
        val games = read(db.collection("video-Games").get(Source.SERVER)).documents.associateBy { it.id }
        val reviews = read(db.collection("reviews").get(Source.SERVER)).documents
        val review = reviews.first { candidate ->
            candidate.getString("userId") != uid &&
                games.containsKey(candidate.getString("videoGameId")) &&
                !candidate.getString("content").isNullOrBlank() &&
                reviews.count { it.getString("content") == candidate.getString("content") } == 1
        }
        val name = games.getValue(review.getString("videoGameId")!!).getString("name")!!
        nav("discover")
        waitFor(hasSetTextAction())
        ui.onAllNodes(hasSetTextAction())[0].performTextReplacement(name)
        val gameCard = hasText(name) and hasClickAction() and !hasSetTextAction()
        waitFor(gameCard)
        ui.onNode(gameCard).performScrollTo().performClick()
        waitFor(hasContentDescription("Escribir reseña"))
        val content = review.getString("content")!!
        clickText(content)
        waitFor(hasContentDescription("Foto del autor"))
        ui.onNodeWithContentDescription("Editar reseña").assertDoesNotExist()
        ui.onNodeWithContentDescription("Eliminar reseña").assertDoesNotExist()
        passed("Reseña ajena sin controles de editar ni eliminar")
        ui.onNodeWithContentDescription("Foto del autor").performScrollTo().performClick()
        waitFor(hasText("seguir") or hasText("siguiendo"))
        passed("Abrir perfil del autor desde su reseña")
    }

    @Test fun loginValidationKeepsExistingSession() {
        val originalUid = uid
        val viewModel = com.example.voxel_review.ui.screens.start.StartViewModel(
            com.example.voxel_review.data.repository.AuthRepository(
                com.example.voxel_review.data.dataSource.AuthRemoteDataSource(FirebaseAuth.getInstance())
            )
        )
        viewModel.login { fail("No debe autenticar con campos vacíos") }
        assertEquals("Por favor, completa todos los campos", viewModel.uiState.value.errorMessage)
        viewModel.updateCorreo("qa@example.com")
        viewModel.updatePassword("123")
        viewModel.login { fail("No debe autenticar con contraseña demasiado corta") }
        assertEquals("La contraseña debe ser minimo de 6 caracteres", viewModel.uiState.value.errorMessage)
        viewModel.updateMostrarContrasena()
        assertTrue(viewModel.uiState.value.mostrarContrasena)
        assertEquals(originalUid, uid)
        passed("Validaciones de inicio de sesión sin alterar la sesión actual")
    }

    @Test fun createEditLikeAndDeleteReview() {
        val marker = "QA_REGRESION_${System.currentTimeMillis()}"
        val edited = "${marker}_EDITADA"
        val existing = read(db.collection("reviews").whereEqualTo("userId", uid).get(Source.SERVER))
        val ownGames = existing.documents.mapNotNull { it.getString("videoGameId") }
        val game = read(db.collection("video-Games").get(Source.SERVER)).documents.first { it.id !in ownGames }
        var createdId: String? = null
        try {
            nav("discover")
            waitFor(hasSetTextAction())
            val name = game.getString("name")!!
            ui.onAllNodes(hasSetTextAction())[0].performTextReplacement(name)
            waitFor(hasText(name) and hasClickAction() and !hasSetTextAction())
            ui.onNode(hasText(name) and hasClickAction() and !hasSetTextAction()).performScrollTo().performClick()
            waitFor(hasContentDescription("Escribir reseña"))
            ui.onNodeWithContentDescription("Escribir reseña").performClick()
            waitFor(text("PUBLICAR REVIEW"))
            // Esperar a que finalice la consulta de reseña existente antes de rellenar.
            Thread.sleep(1500)
            clickText("PUBLICAR REVIEW")
            waitFor(text("La descripción no puede estar vacía"))
            passed("Validación de reseña vacía")
            val sliders = ui.onAllNodes(SemanticsMatcher.keyIsDefined(SemanticsActions.SetProgress))
            for (index in 0..2) {
                sliders[index].performSemanticsAction(SemanticsActions.SetProgress) { it(4f) }
            }
            ui.onAllNodes(hasSetTextAction())[0].performTextReplacement(marker)
            ui.activityRule.scenario.onActivity {
                (it.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager)
                    .hideSoftInputFromWindow(it.window.decorView.windowToken, 0)
            }
            clickText("PUBLICAR REVIEW")
            persisted("La reseña no se guardó") {
                val docs = read(db.collection("reviews").whereEqualTo("content", marker).get(Source.SERVER)).documents
                createdId = docs.firstOrNull()?.id
                createdId != null
            }
            val ref = db.collection("reviews").document(createdId!!)
            var saved = read(ref.get(Source.SERVER))
            assertEquals(uid, saved.getString("userId"))
            assertEquals(game.id, saved.getString("videoGameId"))
            assertEquals(4L, saved.getLong("gameplayRating"))
            assertNotNull(saved.get("videoGame"))
            assertNotNull(saved.get("user"))
            passed("Crear reseña y conservar juego y autor")
            waitFor(hasContentDescription("Escribir reseña"))
            waitFor(hasText(marker))
            clickText(marker)
            waitFor(hasContentDescription("Editar reseña"))
            ui.onNodeWithContentDescription("Dar like").performScrollTo().performClick()
            val likeRef = ref.collection("likes").document(uid)
            persisted("El like no se guardó") { read(likeRef.get(Source.SERVER)).exists() }
            persisted("El contador no aumentó") { read(ref.get(Source.SERVER)).getLong("cantidadLikes") == 1L }
            passed("Dar like en reseña completa")
            ui.onNodeWithContentDescription("Dar like").performScrollTo().performClick()
            persisted("El like no se retiró") { !read(likeRef.get(Source.SERVER)).exists() }
            persisted("El contador no se restauró") { read(ref.get(Source.SERVER)).getLong("cantidadLikes") == 0L }
            passed("Retirar like y restaurar contador")
            back()
            waitFor(hasContentDescription("Escribir reseña"))
            ui.onNodeWithText(marker).performScrollTo()
            val gameLike = hasContentDescription("Dar like") and hasAnyAncestor(hasText(marker))
            waitFor(gameLike)
            ui.onNode(gameLike).performClick()
            persisted("El like desde detalle del juego no se guardó") { read(likeRef.get(Source.SERVER)).exists() }
            ui.onNode(gameLike).performClick()
            persisted("El like desde detalle del juego no se retiró") { !read(likeRef.get(Source.SERVER)).exists() }
            persisted("El contador no volvió a cero") { read(ref.get(Source.SERVER)).getLong("cantidadLikes") == 0L }
            passed("Dar y retirar like también desde detalle del juego")
            clickText(marker)
            waitFor(hasContentDescription("Editar reseña"))
            ui.onNodeWithContentDescription("Editar reseña").performClick()
            waitFor(text("Actualizar reseña"))
            ui.onAllNodes(hasSetTextAction())[0].performTextReplacement(edited)
            ui.activityRule.scenario.onActivity {
                (it.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager)
                    .hideSoftInputFromWindow(it.window.decorView.windowToken, 0)
            }
            clickText("Actualizar reseña")
            persisted("La edición no se guardó") { read(ref.get(Source.SERVER)).getString("content") == edited }
            saved = read(ref.get(Source.SERVER))
            assertEquals(game.id, saved.getString("videoGameId"))
            assertNotNull(saved.get("videoGame"))
            assertNotNull(saved.get("user"))
            waitFor(hasContentDescription("Editar reseña"))
            waitFor(text(edited))
            passed("Editar reseña y actualización en vivo")
            ui.onNodeWithContentDescription("Eliminar reseña").performClick()
            clickText("Cancelar")
            assertTrue(read(ref.get(Source.SERVER)).exists())
            ui.onNodeWithContentDescription("Eliminar reseña").performClick()
            clickText("Aceptar")
            persisted("La reseña no se eliminó") { !read(ref.get(Source.SERVER)).exists() }
            passed("Cancelar borrado, confirmar borrado y persistencia")
        } finally {
            // Solo limpiar documentos identificados por el marcador de esta prueba.
            val docs = (read(db.collection("reviews").whereEqualTo("content", marker).get(Source.SERVER)).documents +
                read(db.collection("reviews").whereEqualTo("content", edited).get(Source.SERVER)).documents)
            docs.forEach { doc ->
                read(doc.reference.collection("likes").get(Source.SERVER)).documents.forEach { read(it.reference.delete()) }
                read(doc.reference.delete())
            }
        }
    }
}
