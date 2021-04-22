/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.release;

import com.acidmanic.release.utilities.delegates.Function;

/**
 *
 * @author diego
 */
public class ReleaseResult {

    private boolean successful;
    private Function<Boolean> updateSourceControlRemote;

    public ReleaseResult() {
        this.successful = false;
        this.updateSourceControlRemote = () -> false;
    }

    public ReleaseResult(Function<Boolean> updateSourceControlRemote) {
        this.successful = true;
        this.updateSourceControlRemote = updateSourceControlRemote;
    }

    public ReleaseResult(boolean successful, Function<Boolean> updateSourceControlRemote) {
        this.successful = successful;
        this.updateSourceControlRemote = updateSourceControlRemote;
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
