package com.rahul.gqlformat.parser

fun main() {

    val nodeCreator = NodeCreator()
    val node1 = nodeCreator.createNode(Constants.COMMON_DIGITAL.replace("\\n", ""))
    val node1Text = nodeCreator.prettyPrint2(node1)

    println("=======================")
    val node2 = nodeCreator.createNode(Constants.FORMATTED.replace("\\n", ""))
    nodeCreator.prettyPrint2(node2)
    println("=======================")

}