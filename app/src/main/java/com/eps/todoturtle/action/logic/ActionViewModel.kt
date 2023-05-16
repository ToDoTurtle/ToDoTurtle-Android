package com.eps.todoturtle.action.logic

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking

class ActionViewModel(private val repository: ActionRepository) : ViewModel() {

    val builder: ActionBuilder = ActionBuilder()

    val actionBuildErrors: MutableStateFlow<List<NoteActionBuilderError>> =
        MutableStateFlow(emptyList())
    private val actionCreator: Channel<NoteAction> = Channel()
    val actionCreated: Flow<NoteAction> = actionCreator.receiveAsFlow()

    fun loadActionForDevice(deviceId: String) {
        val action = getAction(deviceId)
        action?.let {
            builder.loadValuesFromAction(it)
        }
    }

    fun saveAction(deviceId: String) {
        when (val result = builder.build()) {
            is Success -> {
                runBlocking(Dispatchers.IO) {
                    repository.linkDeviceWithAction(deviceId, result.noteAction)
                    actionCreator.send(result.noteAction)
                    builder.clear()
                }
            }
            is Error -> {
                actionBuildErrors.value = result.errors.toList()
            }
        }
    }

    fun getAction(deviceId: String): NoteAction? {
        return runBlocking(Dispatchers.IO) {
            repository.getActionForDeviceWithId(deviceId)
        }
    }

    fun abortAction() {
        actionBuildErrors.value = emptyList()
        builder.clear()
    }

    fun removeAction(id: String) {
        runBlocking(Dispatchers.IO) {
            repository.removeLinkForDevice(id)
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        private class AFactory(
            val repository: ActionRepository,
        ) :
            ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ActionViewModel(repository) as T
            }
        }

        fun <T> T.getActionViewModel(
            repository: ActionRepository,
        ): ActionViewModel where T : ComponentActivity {
            val viewModel = ViewModelProvider(
                this,
                AFactory(
                    repository,
                ),
            )[ActionViewModel::class.java]
            return viewModel
        }
    }


}