package dev.engel.leetcode.kotlin

import dev.engel.leetcode.kotlin.testcommons.generateInts
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal abstract class LRUCacheTest<T : LRUCache> {

    protected abstract fun createSubject(capacity: Int): T

    @Test
    fun `given an lru cache with a capacity of 3 when 4 items are added in a row then the first item should be removed`() {
        val subject = createSubject(3)
        val items = generateInts(4)

        items.forEachIndexed { key, item -> subject.put(key, item) }

        expectThat(subject.get(0)).isEqualTo(-1)
    }

    @Test
    fun `given an lru cache with a capacity of 3 when 4 items are added in a row then the last 3 items should exist`() {
        val subject = createSubject(3)
        val items = generateInts(4)

        items.forEachIndexed { key, item -> subject.put(key, item) }

        expectThat(subject) {
            get { subject.get(1) }.isEqualTo(items[1])
            get { subject.get(2) }.isEqualTo(items[2])
            get { subject.get(3) }.isEqualTo(items[3])
        }
    }

    @Test
    fun `given an lru cache that has not reached capacity when items are retrieved then all of them should exist`() {
        val subject = createSubject(3)
        val items = generateInts(3)

        items.forEachIndexed { key, item -> subject.put(key, item) }

        expectThat(subject) {
            get { subject.get(0) }.isEqualTo(items[0])
            get { subject.get(1) }.isEqualTo(items[1])
            get { subject.get(2) }.isEqualTo(items[2])
        }
    }

    @Test
    fun `given an lru cache when an item is added then it should be available for retrieval after it has been added`() {
        val subject = createSubject(5)
        val items = generateInts(100)

        items.forEachIndexed { key, item ->
            subject.put(key, item)
            expectThat(subject.get(key)).isEqualTo(item)
        }
    }

    @Test
    fun `given an lru cache when an item is overwritten then it should be available for retrieval after it has been added`() {
        val subject = createSubject(5)
        val key = 420

        expect {
            (0..10).forEach { value ->
                subject.put(key, value)
                that(subject.get(key)).isEqualTo(value)
            }
        }
    }
}

internal class RegularLRUCacheTest : LRUCacheTest<RegularLRUCache>() {
    override fun createSubject(capacity: Int) = RegularLRUCache(capacity)
}

internal class LinkedHashMapLRUCacheTest : LRUCacheTest<LinkedHashMapLRUCache>() {
    override fun createSubject(capacity: Int) = LinkedHashMapLRUCache(capacity)
}
