package com.bron24.bron24_android.screens.util

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.ScreenModelEntryPoint
import cafe.adriel.voyager.hilt.ScreenModelFactory
import dagger.hilt.android.EntryPointAccessors
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.Syntax
import kotlin.reflect.KClass

interface AppViewModel<STATE : Any, SIDE_EFFECT : Any> : ContainerHost<STATE, SIDE_EFFECT>, ScreenModel {
    fun <STATE : Any, SIDE_EFFECT : Any> container(
        initialState: STATE,
        onCreate: (Syntax<STATE,SIDE_EFFECT>)-> Unit
    ) = screenModelScope.container(initialState, onCreate = onCreate)
}

@Composable
inline fun <reified T : ScreenModel> Screen.hiltScreenModel(
    tag: String? = null
): T {
    val context = LocalContext.current
    return rememberScreenModel(tag) {
        val screenModels = EntryPointAccessors
            .fromActivity(context.componentActivity, ScreenModelEntryPoint::class.java)
            .screenModels()

        val impl = T::class.java.getDeclaredAnnotation(ScreenModelImpl::class.java) ?: error("Annotation ScreenModelImpl not found")

        val model = screenModels[impl.value.java]?.get()
            ?: error(
                "${impl.value.java.canonicalName} not found in hilt graph.\nPlease, check if you have a Multibinding " +
                        "declaration to your ScreenModel using @IntoMap and " +
                        "@ScreenModelKey(${T::class.qualifiedName}::class)"
            )
        model as T
    }
}
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenModelImpl(val value: KClass<out ScreenModel>)

@Composable
inline fun <reified T : ScreenModel, reified F : ScreenModelFactory> Screen.hiltScreenModel(
    tag: String? = null,
    noinline factory: (F) -> T
): T {
    val context = LocalContext.current
    return rememberScreenModel(tag) {
        val screenFactories = EntryPointAccessors
            .fromActivity(context.componentActivity, ScreenModelEntryPoint::class.java)
            .screenModelFactories()


        val screenFactory = screenFactories[F::class.java]?.get()

            ?: error(
                "${F::class.java} not found in hilt graph.\nPlease, check if you have a Multibinding " +
                        "declaration to your ScreenModelFactory using @IntoMap and " +
                        "@ScreenModelFactoryKey(${F::class.qualifiedName}::class)"
            )
        factory.invoke(screenFactory as F)
    }
}

internal inline fun <reified T> findOwner(context: Context): T? {
    var innerContext = context
    while (innerContext is ContextWrapper) {
        if (innerContext is T) {
            return innerContext
        }
        innerContext = innerContext.baseContext
    }
    return null
}

@PublishedApi
internal val Context.componentActivity: ComponentActivity
    get() = findOwner<ComponentActivity>(this)
        ?: error("Context must be a androidx.activity.ComponentActivity. Current is $this")

@PublishedApi
internal val Context.defaultViewModelProviderFactory: ViewModelProvider.Factory
    get() = componentActivity.defaultViewModelProviderFactory