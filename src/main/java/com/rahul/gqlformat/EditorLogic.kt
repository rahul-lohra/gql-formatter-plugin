package com.rahul.gqlformat

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.impl.EditorImpl
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.impl.source.PsiFieldImpl
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.impl.source.tree.java.PsiLiteralExpressionImpl
import com.rahul.gqlformat.parser.NodeCreator
import org.jetbrains.kotlin.lexer.KtToken
import org.jetbrains.kotlin.nj2k.postProcessing.type
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.KtStringTemplateExpressionManipulator
import org.jetbrains.kotlin.psi.psiUtil.findDescendantOfType
import org.jetbrains.kotlin.types.KotlinType
import java.lang.RuntimeException
import java.lang.StringBuilder
import java.util.regex.Pattern
import javax.swing.JTextArea
import kotlin.math.exp

class EditorLogic {

    val nodeCreator = NodeCreator()
    var expressionElement: PsiElement? = null

    fun replace(variableName: String, textArea: JTextArea) {

        if (expressionElement == null) {
            return
        }
        expressionElement?.let { psiElement ->
            performReplace(textArea, psiElement)
        }
    }

    fun performReplace(textArea: JTextArea, psiElement: PsiElement) {
        val editor = FileEditorManager.getInstance(psiElement.project).selectedTextEditor
        val col = (editor as EditorImpl).offsetToLogicalPosition(psiElement.textOffset).column

        val node = nodeCreator.createNode(textArea.text)
        val nodeText = nodeCreator.prettyPrint2(node, col)
        val sb = StringBuilder()
        sb.append("\"\"")
        sb.append(nodeText.replace("$","\${\"$\"}"))
        sb.append("\"\"")
        val t = sb.toString().trimEnd()
//        val t = sb.toString().replace("$","\$\\{\"$\"}").trimEnd()
        val finalText1 = t.replace("\"", "\\\"").replace("\\\"", "")
        val finalText2 = t
        val e1 = KtPsiFactory(psiElement.project).createStringTemplate(finalText2)


        WriteCommandAction.runWriteCommandAction(expressionElement?.project) {
            expressionElement?.replace(e1)
        }
    }

    fun getTextFromKtStringTemplateExpression(element: KtStringTemplateExpression): String {

        val firstChildType = (element.firstChild as LeafPsiElement).elementType
        val lastChildType = (element.lastChild as LeafPsiElement).elementType
        if (firstChildType.toString() == "OPEN_QUOTE" && lastChildType.toString() == "CLOSING_QUOTE") {
            val prefixLength = (element.firstChild as LeafPsiElement).text.length
            val assignedValuePre = element.text.removeRange(0, prefixLength)
            return assignedValuePre.removeRange(assignedValuePre.length - prefixLength, assignedValuePre.length)
                .trimEnd()
        }
        return ""
    }

    fun getTextFromKtStringTemplateExpression2(element: KtStringTemplateExpression): String {
        return element.text.replace("\"", "").trimEnd()
    }

    fun findTextInVariable(psiElement: PsiElement): String {
        when (psiElement) {
            is KtStringTemplateExpression -> {
                val text = getTextFromKtStringTemplateExpression(psiElement)
                val node = nodeCreator.createNode(text.replace("\\n", ""))
                return nodeCreator.prettyPrint2(node)
            }
            is KtBinaryExpression -> {
                val array = psiElement.text.split("+")
                val sb = StringBuilder()
                for (item in array) {
                    sb.append(
                        item.replaceFirst("\"", "")
                            .replace("\\t", "")
                            .replace("\\n", "")
                            .replace("\\", "")
                            .trimEnd()
                            .removeSuffix("\"")
                    )
                }
                val node = nodeCreator.createNode(sb.toString().replace("\\n", ""))
                return nodeCreator.prettyPrint2(node)
            }
            is KtDotQualifiedExpression -> {
                if (psiElement.firstChild is KtStringTemplateExpression) {
                    return findTextInVariable(psiElement.firstChild)
                }
            }
        }
        //todo Rahul throw exception - not supported
        return "Type not supported"
    }

    fun kotlinVisitor(variableName: String, textArea: JTextArea, psiFile: PsiFile) {

        psiFile.accept(object : KtTreeVisitorVoid() {

            override fun visitProperty(property: KtProperty) {
                super.visitProperty(property) //yes
                val localVariableName = property.name
                if (property.type().toString() == "String" && localVariableName == variableName) {
                    val text = property.lastChild.text
                    val expressionElement = property.lastChild
                    this@EditorLogic.expressionElement = expressionElement
                    textArea.text = findTextInVariable(expressionElement)
                }
            }
        })
    }

    fun printSelectedFileName(variableName: String, project: Project, textArea: JTextArea) {
        reset()

        val fileEditorManager = FileEditorManager.getInstance(project)
        val virtualFile = fileEditorManager.openFiles

        if (!virtualFile.isNullOrEmpty()) {

            val vf = fileEditorManager.selectedEditor?.file
            vf?.let { file ->
                val psiFile = PsiManager.getInstance(project).findFile(file)
                if (psiFile != null) {
                    psiFile.accept(javaVisitor(textArea))
                    kotlinVisitor(variableName, textArea, psiFile)
                }
            }
        }
    }

    fun javaVisitor(textArea: JTextArea): JavaRecursiveElementVisitor {
        return object : JavaRecursiveElementVisitor() {
            override fun visitDeclarationStatement(statement: PsiDeclarationStatement) {
                super.visitDeclarationStatement(statement)
            }

            override fun visitLocalVariable(variable: PsiLocalVariable) {
                super.visitLocalVariable(variable)
                println("Found a variable at offset " + variable.textRange.startOffset)
            }

            override fun visitVariable(variable: PsiVariable) {
                super.visitVariable(variable)
                val dataType = ((variable as PsiFieldImpl).type as? PsiClassReferenceType)?.className
                if (!dataType.isNullOrEmpty() && dataType == "String") {
                    val assignedText = (variable.initializer as? PsiLiteralExpressionImpl)?.canonicalText
                    if (!assignedText.isNullOrEmpty()) {
                        textArea.append(assignedText)
                    }
                }
            }

            override fun visitAssignmentExpression(expression: PsiAssignmentExpression) {
                super.visitAssignmentExpression(expression)
            }
        }
    }

    fun reset() {
        expressionElement = null
    }
}
//KtPsiFactory(psiElement.project).createStringTemplate("m S ('\$promoCode': String!) {\\n" +
//"S(promoCode: \$promoCode) {\\n"+
//"Success" +
//"}"+
//"}").text

//KtPsiFactory(psiElement.project).createStringTemplate("""[{
//    \nquery""".replace("\n","")).text

//val sb = StringBuilder()
//val items = textArea.text.split("\n")
//for(item in items){
//    sb.append(item)
//    sb.append("\\n")
//}
//sb.toString().trimEnd()

//KtPsiFactory(expressionElement!!.project).createStringTemplate("""{
//            "category_id" :%s
//        }""".replace("\n","").replace("\"","\\\"").replace("\\\"","")).text

//val sb = StringBuilder()
//val items = textArea.text.split("\n")
//for(item in items){
//    sb.append(item)
//}
//val t = sb.toString().trimEnd()
//
//KtPsiFactory(psiElement.project).createStringTemplate(t.replace("\"","\\\"").replace("\\\"","")).text

//val singleLine = "\"\"\nhello \nworld\n\"\""
//val singleLineTripleQuote = """hello${"\n"}world""".trimMargin()
//val multiLineTripleQuote = """hello
//world""".trimMargin()
//
//val e1 = KtPsiFactory(psiElement.project).createStringTemplate(singleLine)
////val e1 = KtPsiFactory(psiElement.project).createExpression(multiLine)
//e1.text
////singleLine
////val e2 = CodeStyleManager.getInstance(psiElement.project).reformat(e1)
//
////singleLine.toCharArray()
