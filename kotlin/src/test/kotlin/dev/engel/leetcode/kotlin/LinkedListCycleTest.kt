package dev.engel.leetcode.kotlin

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

internal class LinkedListCycleTest {

    @Test
    fun `given head is null when hasCycle is invoked then it should return false`() {
        expectThat(hasCycle(null)).isFalse()
    }

    @Test
    fun `given a linked list does not have a cycle when hasCycle is invoked then it should return false`() {
        val head = ListNode(1, ListNode(2, ListNode(3, ListNode(1))))

        expectThat(hasCycle(head)).isFalse()
    }

    @Test
    fun `given a linked list with a cycle when hasCycle is invoked then it should return true`() {
        val node1 = ListNode(1)
        val node2 = ListNode(2, node1)
        val node3 = ListNode(3, node2)
        val node4 = ListNode(4, node3)
        val head = ListNode(0, node4)
        head.next!!.next!!.next!!.next!!.next = head

        expectThat(hasCycle(head)).isTrue()
    }
}
