/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.gitbump;

import com.acidmanic.io.file.FileIOHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class ScriptedGitbumpBuilder {

    private final List<String> lines;

    public ScriptedGitbumpBuilder(File file) {

        this.lines = new FileIOHelper().tryReadAllLines(file);
    }

    public ScriptedGitbumpBuilder(String path) {

        this.lines = new FileIOHelper().tryReadAllLines(path);

    }

    public List<GitBumpStep> build() {

        GitBumpStepFacttory factory = new GitBumpStepFacttory();

        List<GitBumpStep> ret = new ArrayList<>();

        for (String line : this.lines) {

            line = line.replaceAll("\\s+", " ");

            String[] args = line.split("\\s");

            GitBumpStep step = factory.make(args);

            ret.add(step);
        }
        return ret;
    }

}
