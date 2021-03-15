package com.example.ikomobi_mahe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.ikomobi_mahe.model.Product
import com.example.ikomobi_mahe.repository.NetworkRepository
import com.example.ikomobi_mahe.repository.ProductRepository
import com.example.ikomobi_mahe.utils.MainCoroutineScopeRule
import com.example.ikomobi_mahe.utils.getValueForTest
import com.example.ikomobi_mahe.view.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()

    @Mock
    private val networkRepoMock = Mockito.mock(NetworkRepository::class.java)

    @Mock
    private val productRepoMock = Mockito.mock(ProductRepository::class.java)

    @Test
    fun whenDataBaseEmits_ProductsAreUpdated() {
        //GIVEN
        Mockito.`when`(productRepoMock.products).thenReturn(MutableLiveData(getDummyProducts()))
        Mockito.`when`(networkRepoMock.isNetworkAvailable).thenReturn(MutableLiveData(false))
        val viewModel = MainViewModel(networkRepoMock, productRepoMock)
        //WHEN
        val products = viewModel.products.getValueForTest()
        //THEN
        Assert.assertEquals(getDummyProducts(), products)
    }

    @Test
    fun whenNetworkAvailable_FetchDataFromServer() {
        //GIVEN
        Mockito.`when`(productRepoMock.products).thenReturn(MutableLiveData(listOf()))
        Mockito.`when`(networkRepoMock.isNetworkAvailable).thenReturn(MutableLiveData(true))
        val viewModel = MainViewModel(networkRepoMock, productRepoMock)
        //WHEN
        val products = viewModel.products.getValueForTest() //Rmq: Needs to be observed
        //THEN
        runBlocking {
            Mockito.verify(productRepoMock).fetchProducts()
        }
    }

    @Test
    fun whenNetworkUnavailable_ServerRequestIsNotTriggered() {
        //GIVEN
        Mockito.`when`(productRepoMock.products).thenReturn(MutableLiveData(getDummyProducts()))
        Mockito.`when`(networkRepoMock.isNetworkAvailable).thenReturn(MutableLiveData(false))
        val viewModel = MainViewModel(networkRepoMock, productRepoMock)
        //WHEN
        val products = viewModel.products.getValueForTest() //Rmq: Needs to be observed
        //THEN
        runBlocking {
            Mockito.verify(productRepoMock, never()).fetchProducts()
        }
    }

    @Test
    fun whenServerRequestExecuted_NetworkCallbackIsRemoved() {
        //GIVEN
        Mockito.`when`(productRepoMock.products).thenReturn(MutableLiveData(getDummyProducts()))
        Mockito.`when`(networkRepoMock.isNetworkAvailable).thenReturn(MutableLiveData(true))
        val viewModel = MainViewModel(networkRepoMock, productRepoMock)
        //WHEN
        val products = viewModel.products.getValueForTest() //Rmq: Needs to be observed
        //THEN
        runBlocking {
            Mockito.verify(productRepoMock).fetchProducts()
        }
        Mockito.verify(networkRepoMock).removeCallback()
    }

    private fun getDummyProducts() = listOf(
        Product(1, "Poisson", 28.0, "poisson.url"),
        Product(2, "Haricots", 2.0, "haricots.url")
    )
}