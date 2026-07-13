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
            date = "2024-01-15",
            type = "EXPENSE"
        )

        assertEquals("Groceries", entity.title)
        assertEquals(50.0, entity.amount)
        assertEquals("2024-01-15", entity.date)
        assertEquals("EXPENSE", entity.type)
        assertTrue(entity.id >= 0)
    }

    @Test
    fun entityTypeConstants() {
        assertEquals("INCOME", TransactionEntity.TYPE_INCOME)
        assertEquals("EXPENSE", TransactionEntity.TYPE_EXPENSE)
    }

    @Test
    fun entityAmountPrecision() {
        val entity = TransactionEntity(
            title = "Test",
            amount = 99.99,
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
            date = "2024-01-15",
            type = "EXPENSE"
        )
        assertNotNull(entity)
        assertEquals(0, entity.id)
        assertEquals("General", entity.category)
        assertEquals(null, entity.attachmentPath)
    }
}
