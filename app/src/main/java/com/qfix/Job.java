package com.qfix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class Job implements Serializable {
    private boolean isNew, isInProgress, isComplete;
    private Client client;
    private Electronic electronic;

    private Technician technician;

    private transient DocumentReference docRef;

    public void setDocRef(DocumentReference docRef) {
        this.docRef = docRef;
        docRef.getId();
    }

    public DocumentReference getDocRef() {
        return docRef;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public Technician getTechnician() {
        return technician;
    }

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (docRef == null) return false;
        if (obj instanceof Job) {
            return docRef.getId().equals(((Job) obj).getDocRef().getId());
        }
        return false;
    }
}
