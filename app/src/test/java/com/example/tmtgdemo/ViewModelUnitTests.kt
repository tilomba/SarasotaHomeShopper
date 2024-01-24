package com.example.tmtgdemo

import app.cash.turbine.test
import com.example.tmtgdemo.viewmodel.ListOfHomesAction
import com.example.tmtgdemo.viewmodel.ListOfHomesState
import com.example.tmtgdemo.viewmodel.ListOfHomesViewmodel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ViewModelUnitTests {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun listofhomes_state_change() = runTest {
        val vm = getListOfHomesViewModel()

        vm.state.test {
            vm.actions.send(ListOfHomesAction.OnHomeButtonPressed(0))
            Assertions.assertEquals(true, awaitItem() is ListOfHomesState.Success)
        }
    }

    fun getListOfHomesViewModel() : ListOfHomesViewmodel {
        return ListOfHomesViewmodel(FakeHouseDetailRepository())
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}