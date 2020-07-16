package com.rahul.gqlformat.parser

import java.util.regex.Pattern
//val matcher = Pattern.compile("\\$\\$").matcher(nodeText.split("\n")[0])
fun main() {
//    val paramShopId = "paramShopId"
//    val text = """${"${"$"}"}category_id"""
//    val regexPattern = "\\}"
//    val matcher = Pattern.compile(regexPattern).matcher(text)
//    while (matcher.find()){
//        println(matcher.group())
//    }

    val nodeCreator = NodeCreator()
    val node1 = nodeCreator.createNode(Constants.COMMON_DIGITAL.replace("\\n", ""))
    val node1Text = nodeCreator.prettyPrint2(node1)
//
//    println("=======================")
//    val node2 = nodeCreator.createNode(Constants.FORMATTED.replace("\\n", ""))
//    nodeCreator.prettyPrint2(node2)
//    println("=======================")
}