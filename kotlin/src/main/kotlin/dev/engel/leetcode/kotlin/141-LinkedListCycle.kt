package dev.engel.leetcode.kotlin

data class ListNode(var value: Int, var next: ListNode? = null)

fun hasCycle(head: ListNode?): Boolean {
    if (head == null) return false

    var slow = head
    var fast = head.next
    while (slow != fast) {
        if (fast?.next == null) return false
        slow = slow?.next
        fast = fast.next?.next
    }
    return true
}
