package net.novate.base.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.properties.Delegates

/**
 * BaseViewModel
 */
abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseViewAction>(initialState: ViewState) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()

    val viewState = _viewState as LiveData<ViewState>

    protected var state: ViewState by Delegates.observable(initialState) { _, _, new ->
        _viewState.value = new
    }
        private set

    fun sendAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    /**
     * 从 [viewAction] 中复原 ViewState
     */
    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}

interface BaseViewState

interface BaseViewAction