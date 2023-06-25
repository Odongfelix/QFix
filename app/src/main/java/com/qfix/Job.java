package com.qfix;

import androidx.annotation.NonNull;

public class Job {
    private boolean isNew, isInProgress, isComplete;
    private Client client;
    private Electronic electronic;

    @NonNull
    @Override
    public String toString() {
        if (electronic == null) return super.toString();
        return electronic.getName();
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Electronic getElectronic() {
        return electronic;
    }

    public void setElectronic(Electronic electronic) {
        this.electronic = electronic;
    }
}
