/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */





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

    protected instantiateCompiler() {
        def goloCompilerClass = loadGoloCompilerClass()
        goloCompilerClass.getConstructor().newInstance()
    }

    protected Class loadGoloCompilerClass() {
        def goloClasspathUrls = getGoloClasspath().files.collect { it.toURI().toURL() } as URL[]
        def goloClassLoader = URLClassLoader.newInstance(goloClasspathUrls, getClass().classLoader)
		goloClassLoader.loadClass(GOLO_COMPILER_CLASS_NAME, true)
    }
}
