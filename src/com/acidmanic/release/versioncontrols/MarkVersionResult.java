/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release.versioncontrols;

import com.acidmanic.release.utilities.delegates.Function;

/**
 *
 * @author diego
 */
public class MarkVersionResult {
    
    private boolean successful;
    private Function<Boolean> updateSourceControlRemote;

    public MarkVersionResult() {
        this.successful = false;

        this.updateSourceControlRemote = () -> false;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Function<Boolean> getUpdateSourceControlRemote() {
        return updateSourceControlRemote;
    }

    public void setUpdateSourceControlRemote(Function<Boolean> updateSourceControlRemote) {
        this.updateSourceControlRemote = updateSourceControlRemote;
    }
}
