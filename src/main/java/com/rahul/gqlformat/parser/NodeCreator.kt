package com.rahul.gqlformat.parser

import com.rahul.gqlformat.parser.Node
import java.util.*
import kotlin.text.StringBuilder

var spaceCount = 0
const val INDENT = 4

class NodeCreator {

    fun createNodeFromResource(resource: String) {
        val unformattedQuery = this::class.java.classLoader.getResource("raw/${resource}")!!.readText()
        val formattedQuery = unformattedQuery.replace("\\n", "")
        val root = createNode(formattedQuery)
        spaceCount = 0
        prettyPrint2(root)
    }

    fun prettyPrint2(root: Node, defaultIndent: Int = 0): String {
        val sb = StringBuilder()
        val stackSpace = Stack<Int>()
        stackSpace.push(0)

        val stack = Stack<Node>()


        fun printIndent() {
            for (i in 0 until stackSpace.peek() + defaultIndent) {
                sb.append(" ")
                print(" ")
            }
        }

        fun innerPrint(index: Int, node: Node) {
            stack.push(node)

            val word = node.word
            var printNextLineForCurrentWord = false

            //space reduce
            if (word == "}" || word == "]") {
                stackSpace.pop()
            }

            //current word
            if (word == "}" || word == "," || word == "]") {
                printNextLineForCurrentWord = true
            }

            if (!printNextLineForCurrentWord) {
                if (index > 0 && node.variable == null) {
                    printNextLineForCurrentWord = true
                }
            }

            if (printNextLineForCurrentWord) {
                sb.append("\n")
                println()
                printIndent()
            }

            //add space in current line
            if (word == ":") {
                sb.append(" ")
                print(" ")
            } else if (word.contains("mutation")) {
                sb.append(" ")
                print(" ")
            } else if (node.variable != null) {

                var printSpace = false
                while (stack.isNotEmpty()) {
                    val node = stack.peek()
                    if (node.word.contains("query") || node.word.contains("mutation")) {
                        printSpace = true
                        break
                    }
                    if (node.word == "{") {
                        break
                    }
                    stack.pop()
                }

                if (printSpace) {
                    sb.append(" ")
                    print(" ")
                }
            }

            if (word != "ROOT") {
                sb.append(word)
                print(word)
            }

            //for next word
            if (word == "[" || word == "{") {
                stackSpace.push(stackSpace.peek() + INDENT)
                sb.append("\n")
                println()
                printIndent()
            }

            if (node.hasValueNode) {
                innerPrint(-1, node.valueNode!!)
            }

            if (node.variable != null) {
                innerPrint(-1, node.variable!!)
            }

            node.nodes.forEachIndexed { i, n ->
                innerPrint(i, n)
            }
        }
        innerPrint(-1, root)
        return sb.toString()
    }

    fun createNode(query: String): Node {

        var currentNode = Node("ROOT", -1)
        val root = currentNode
        val curWord = StringBuilder()

        val stack = Stack<Node>()
        stack.push(currentNode)

        var index = 0
        val length = query.length

        while (index < length) {
            val c = query[index]

            if (c == '[') {
                val node = Node(c.toString(), index)
                stack.peek().nodes.add(node)
                stack.push(node)
                currentNode = node

            } else if (c == ']') {
                val node = Node(c.toString(), index)
                stack.pop()
                currentNode = stack.peek()
                currentNode.nodes.add(node)

            } else if (c == '(') {

                val node = Node(curWord.toString(), index)
                currentNode.nodes.add(node)
                stack.push(node)

                curWord.clear()

                //search till ')'
                var innerIndex = index
                while (innerIndex < length) {
                    val innerC = query[innerIndex]
                    curWord.append(innerC)

                    if (innerC == ')') {
                        val variableNode = Node(curWord.toString(), innerIndex)
                        node.variable = variableNode
                        index = innerIndex

                        curWord.clear()
                        break
                    }
                    innerIndex += 1
                }

            } else if (c == '}') {
                val node = Node(c.toString(), index)

                var foundOpen = 0
                while (!stack.empty()) {
                    if (stack.peek().word.contains("{")) {
                        foundOpen += 1

                        if (foundOpen == 1) {
                            stack.pop()
                            stack.peek().nodes.add(node)
                        }
                    }
                    if (stack.peek().word.contains("{") && foundOpen > 1) {
                        currentNode = stack.peek()
                        break
                    }
                    if (stack.peek().word.contains("[") && foundOpen > 0) {
                        currentNode = stack.peek()
                        break
                    }
                    stack.pop()
                }
            } else if (c == '{') {

                if (curWord.isNotEmpty()) {
                    val node = Node(curWord.toString(), index)
                    curWord.clear()
                    currentNode.nodes.add(node)

                    stack.push(node)
                }

                //create node
                curWord.append(c)

                val node = Node(curWord.toString(), index)

                //check if parent has valueKey
                if (stack.peek().hasValueNode) {
                    stack.peek().valueNode = node
                } else {
                    while (stack.size > 0 && stack.peek().word == ",") {
                        stack.pop()
                    }
                    stack.peek().nodes.add(node)
                }

                currentNode = node
                stack.push(node)

                curWord.clear()
            } else if (c.toString().trim().isEmpty() && curWord.isNotEmpty()) {

                val node = Node(curWord.toString(), index)
                curWord.clear()
                currentNode.nodes.add(node)

                stack.push(node)

            } else if (c == ':' && curWord.isNotEmpty()) {
                val node = Node(curWord.toString(), index - curWord.toString().length)

                val childNodes = currentNode.nodes
                childNodes.add(node)
                node.hasValueNode = true

                curWord.clear()
                stack.push(node)

                //create a node for ':'
                val nodeColon = Node(c.toString(), index)
                node.valueNode = nodeColon
                stack.push(nodeColon)

                currentNode = nodeColon
            } else if (c == ':' && curWord.isEmpty()) {
                val nodeColon = Node(c.toString(), index)
                stack.peek().hasValueNode = true
                stack.peek().valueNode = nodeColon
                stack.push(nodeColon)

                currentNode = nodeColon
            } else if (c.toString().trim().isNotEmpty()) {
                curWord.append(c)
            }
            index += 1
        }
        return root
    }
}