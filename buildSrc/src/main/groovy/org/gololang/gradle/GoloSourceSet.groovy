package org.gololang.gradle

import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver

import static org.gradle.util.ConfigureUtil.configure

class GoloSourceSet {
    private final SourceDirectorySet golo;

    GoloSourceSet(String displayName, FileResolver fileResolver) {
        golo = new DefaultSourceDirectorySet(String.format('%s Golo source', displayName), fileResolver);
        golo.filter.include('**/*.golo');
    }

    SourceDirectorySet getGolo() {
        golo
    }

    GoloSourceSet golo(Closure closure) {
        configure(closure, golo)
        this
    }
}
