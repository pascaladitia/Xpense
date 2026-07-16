package com.pascal.xpense

import com.pascal.xpense.data.local.entity.CategoryTotal
import com.pascal.xpense.data.local.entity.TransactionEntity
import com.pascal.xpense.data.repository.local.LocalRepository
import com.pascal.xpense.domain.usecase.local.LocalUseCaseImpl
import com.pascal.xpense.ui.screen.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class, kotlin.experimental.ExperimentalNativeApi::class)
class DashboardViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialLoadState() = runTest {
        val repo = FakeLocalRepository()
        val useCase = LocalUseCaseImpl(repo)
        val viewModel = DashboardViewModel(useCase)

        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(0.0, state.totalBalance)
        assertEquals(0.0, state.totalIncome)
        assertEquals(0.0, state.totalExpense)
        assertEquals(emptyList(), state.transactions)
    }

    @Test
    fun balanceCalculation() = runTest {
        val repo = FakeLocalRepository()
        val useCase = LocalUseCaseImpl(repo)
        val viewModel = DashboardViewModel(useCase)

        repo.addTx(
            TransactionEntity(
                title = "Salary",
                amount = 3000.0,
                date = "2024-01-15",
                type = TransactionEntity.TYPE_INCOME
            )
        )

        repo.addTx(
            TransactionEntity(
                title = "Rent",
                amount = 1000.0,
                date = "2024-01-15",
                type = TransactionEntity.TYPE_EXPENSE
            )
        )

        viewModel.refresh()

        val state = viewModel.uiState.value
        assertEquals(2000.0, state.totalBalance)
        assertEquals(3000.0, state.totalIncome)
        assertEquals(1000.0, state.totalExpense)
        assertEquals(2, state.transactions.size)
    }

    @Test
    fun transactionGrouping() = runTest {
        val repo = FakeLocalRepository()
        val useCase = LocalUseCaseImpl(repo)
        val viewModel = DashboardViewModel(useCase)

        repo.addTx(
            TransactionEntity(
                title = "Coffee",
                amount = 5.0,
                date = "2024-01-15",
                type = TransactionEntity.TYPE_EXPENSE
            )
        )

        repo.addTx(
            TransactionEntity(
                title = "Groceries",
                amount = 50.0,
                date = "2024-01-14",
                type = TransactionEntity.TYPE_EXPENSE
            )
        )

        viewModel.refresh()

        val state = viewModel.uiState.value
        assertEquals(2, state.groupedTransactions.size)
        assert(state.groupedTransactions.containsKey("2024-01-15"))
        assert(state.groupedTransactions.containsKey("2024-01-14"))
    }

    @Test
    fun deleteTransaction() = runTest {
        val repo = FakeLocalRepository()
        val useCase = LocalUseCaseImpl(repo)
        val viewModel = DashboardViewModel(useCase)

        val id = repo.addTx(
            TransactionEntity(
                title = "Test",
                amount = 10.0,
                date = "2024-01-15",
                type = TransactionEntity.TYPE_EXPENSE
            )
        )

        viewModel.refresh()
        assertEquals(1, viewModel.uiState.value.transactions.size)

        viewModel.deleteTransaction(id)
        assertEquals(0, viewModel.uiState.value.transactions.size)
    }
}

class FakeLocalRepository : LocalRepository {
    private val transactions = mutableListOf<TransactionEntity>()
    private var nextId = 1L
    private val _transactionsFlow = MutableStateFlow<List<TransactionEntity>>(emptyList())
    private val incomeFlow = MutableStateFlow(0.0)
    private val expenseFlow = MutableStateFlow(0.0)

    fun addTx(entity: TransactionEntity): Long {
        val withId = entity.copy(id = nextId)
        transactions.add(withId)
        nextId++
        refreshFlows()
        return withId.id
    }

    private fun refreshFlows() {
        _transactionsFlow.value = transactions.toList()
        incomeFlow.value = transactions.filter { it.type == TransactionEntity.TYPE_INCOME }.sumOf { it.amount }
        expenseFlow.value = transactions.filter { it.type == TransactionEntity.TYPE_EXPENSE }.sumOf { it.amount }
    }

    override suspend fun addTransaction(transaction: TransactionEntity): Long {
        return addTx(transaction)
    }

    override suspend fun removeTransaction(transaction: TransactionEntity) {
        transactions.removeAll { it.id == transaction.id }
        refreshFlows()
    }

    override fun observeAllTransactions() = _transactionsFlow

    override suspend fun getTransactionById(id: Long) = transactions.find { it.id == id }

    override fun observeTotalIncome() = incomeFlow

    override fun observeTotalExpense() = expenseFlow

    override fun observeTransactionsByMonth(yearMonth: String) = flowOf(
        transactions.filter { it.date.startsWith(yearMonth) }
    )

    override fun observeMonthlyExpense(yearMonth: String) = flowOf(
        transactions.filter { it.date.startsWith(yearMonth) && it.type == TransactionEntity.TYPE_EXPENSE }
            .sumOf { it.amount }
    )

    override fun observeExpenseByCategory() = flowOf(
        transactions.filter { it.type == TransactionEntity.TYPE_EXPENSE }
            .groupBy { it.category }
            .map { (category, items) -> CategoryTotal(category, items.sumOf { it.amount }) }
    )
}
