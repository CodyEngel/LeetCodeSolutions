package dev.engel.leetcode.kotlin

class LRUCache(private val capacity: Int) {

    private val lookup = HashMap<Int, Node>()
    private val doublyLinkedList = DoublyLinkedList()
    private var size = 0

    /**
     * Retrieves an item from the cache. If the item does not exist, this will return -1.
     */
    fun get(key: Int): Int {
        val node = lookup[key] ?: return -1

        moveToFront(node)
        return node.value
    }

    /**
     * Adds an item to the list using the [key]. If an item already exists with that [key] then the value will be
     * updated.
     */
    fun put(key: Int, value: Int) {
        val node = lookup[key]

        if (node == null) {
            val newNode = Node(value = value, key = key)
            lookup[key] = newNode
            doublyLinkedList.add(newNode)

            size++

            if (size > capacity) {
                lookup.remove(doublyLinkedList.popLast().key)
                size--
            }
        } else {
            node.value = value
            moveToFront(node)
        }
    }

    /**
     * Moves a node to the front of the list. This helps keep the LRU cache in the correct order after an item has been
     * retrieved or updated.
     */
    private fun moveToFront(node: Node) {
        doublyLinkedList.remove(node)
        doublyLinkedList.add(node)
    }

    /**
     * An implementation of a DoublyLinkedList that guarantees a first and last node exist.
     */
    private class DoublyLinkedList(
        private val first: Node = Node(),
        private val last: Node = Node()
    ) {
        init {
            first.next = last
            last.previous = first
        }

        /**
         * Retrieves the last node from the list and then removes it.
         */
        fun popLast(): Node {
            return last.previous!!.also { remove(it) }
        }

        /**
         * Adds a new node to the beginning of the list.
         */
        fun add(node: Node) {
            node.previous = first
            node.next = first.next

            val next = first.next
            next!!.previous = node
            first.next = node
        }

        /**
         * Removes a node from the list.
         */
        fun remove(node: Node) {
            val previous = node.previous
            val next = node.next
            previous!!.next = next
            next!!.previous = previous
        }
    }

    /**
     * Node is a specialized data structure to this LRU Cache.
     *
     * @property key is used to retrieve the value. Defaults to -1.
     * @property value is used to store the value. Defaults to -1.
     * @property previous points to the previous node. Defaults to null.
     * @property next points to the next node. Defaults to null.
     */
    private data class Node(
        var key: Int = -1,
        var value: Int = -1,
        var previous: Node? = null,
        var next: Node? = null
    )
}
