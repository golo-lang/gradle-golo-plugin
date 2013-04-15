package org.gololang.gradle

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.compile.AbstractCompile

import static org.apache.commons.io.FilenameUtils.removeExtension

class GoloCompile extends AbstractCompile {

    private static final String GOLO_COMPILER_CLASS_NAME = 'fr.insalyon.citi.golo.compiler.GoloCompiler'
    public static final String GOLO_CLASSPATH_FIELD = 'goloClasspath'
    FileCollection goloClasspath

    protected void compile() {
        def compiler = instantiateCompiler()
        source.files.each { file ->
            file.withInputStream { stream ->
                compiler.compileTo(removeExtension(file.name), stream, destinationDir)
            }
        }
    }

    private def instantiateCompiler() {
        def goloCompilerClass = loadGoloCompilerClass()
        goloCompilerClass.getConstructor().newInstance()
    }

    private Class loadGoloCompilerClass() {
        def goloClasspathUrls = getGoloClasspath().files.collect { it.toURI().toURL() } as URL[]
        def goloClassLoader = URLClassLoader.newInstance(goloClasspathUrls, getClass().getClassLoader())
        Class.forName(GOLO_COMPILER_CLASS_NAME, true, goloClassLoader)
    }
}
