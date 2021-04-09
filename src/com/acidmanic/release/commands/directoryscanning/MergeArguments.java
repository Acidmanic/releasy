/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.commands.directoryscanning;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public class MergeArguments {

    private String source;
    private List<String> destinations;

    public MergeArguments() {
        source = "master";
        destinations = new ArrayList<>();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

}
