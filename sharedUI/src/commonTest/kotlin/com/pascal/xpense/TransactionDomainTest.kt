package com.pascal.xpense

import com.pascal.xpense.data.local.entity.TransactionEntity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TransactionDomainTest {

    @Test
    fun createTransactionEntity() {
        val entity = TransactionEntity(
            title = "Groceries",
            amount = 50.0,
            category = "Food",
            date = "2024-01-15",
            type = "EXPENSE",
            note = "Weekly groceries"
        )

        assertEquals("Groceries", entity.title)
        assertEquals(50.0, entity.amount)
        assertEquals("Food", entity.category)
        assertEquals("2024-01-15", entity.date)
        assertEquals("EXPENSE", entity.type)
        assertEquals("Weekly groceries", entity.note)
        assertTrue(entity.id >= 0)
    }

    @Test
    fun entityTypeExpense() {
        val expense = TransactionEntity(
            title = "Coffee",
            amount = 5.0,
            category = "Food",
            date = "2024-01-15",
            type = "EXPENSE"
        )

        assertEquals("EXPENSE", expense.type)
    }

    @Test
    fun entityAmountPrecision() {
        val entity = TransactionEntity(
            title = "Test",
            amount = 99.99,
            category = "Other",
            date = "2024-01-15",
            type = "EXPENSE"
        )
        assertEquals(99.99, entity.amount)
        assertTrue(entity.amount > 0)
    }

    @Test
    fun entityWithDefaultValues() {
        val entity = TransactionEntity(
            title = "Minimal",
            amount = 10.0,
            category = "Other",
            date = "2024-01-15",
            type = "EXPENSE"
        )
        assertNotNull(entity)
        assertEquals(0, entity.id)
        assertEquals(null, entity.note)
        assertEquals(null, entity.attachmentPath)
    }
}
