package com.rahul.gqlformat.parser

class Node(val word:String, val index:Int) {
    var hasValueNode = false
    val nodes = arrayListOf<Node>()
    var valueNode: Node? = null
    var variable: Node? = null
}