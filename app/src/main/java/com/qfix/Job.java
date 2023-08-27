package com.qfix;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.Random;

public class Job implements Serializable {
    private boolean isNew = true, isInProgress, isComplete,rejected;
    private Client client;
    private Electronic electronic;

    private Technician technician;

    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    private transient DocumentReference docRef;

    public Job() {
        id = System.nanoTime() + new Random().nextLong() + hashCode(); //probability of being unique is not soo high
    }

    public void setDocRef(DocumentReference docRef) {
        this.docRef = docRef;
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
        if (obj instanceof Job) {
            return id == ((Job) obj).getId();
        }
        return false;
    }
}
