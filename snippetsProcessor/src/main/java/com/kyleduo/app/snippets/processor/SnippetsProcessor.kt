package com.kyleduo.app.snippets.processor

import com.kyleduo.app.snippets.annotations.Snippet
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class SnippetsProcessor : AbstractProcessor() {


    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Snippet::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion? {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        roundEnv ?: return true

        val elements = roundEnv.getElementsAnnotatedWith(Snippet::class.java)
        for (element in elements) {
            logNote(element.toString())
            val enclosingElement = element.enclosingElement
            logNote((enclosingElement is TypeElement).toString())
            val classElement = enclosingElement as TypeElement
            val packageElement = enclosingElement.enclosingElement

            val dummyFile = processingEnv.filer.createSourceFile("Dummy")
            val path = dummyFile.toUri().path
            val appPath = path.substring(0, path.lastIndexOf("/build"))
            val classPath = "$appPath/src/main/java/${packageElement.toString().replace(".", "/")}/${classElement.simpleName}.kt"
            logNote(path)
            logNote(appPath)
            logNote(classPath)

            val sourceFile = File(classPath)
            if (!sourceFile.exists()) {
                logNote("file not exists. $sourceFile")
                continue
            }
            logNote("Content of file: $sourceFile")
            val bufferedReader = BufferedReader(InputStreamReader(sourceFile.inputStream()))
            bufferedReader.use {
                val readLines = it.readLines()
                for (readLine in readLines) {
                    logNote(readLine)
                }
            }
        }

        return true
    }

    private fun logNote(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, message + "\r\n")
    }

    private fun logError(message: String) {
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message + "\r\n")
    }
}