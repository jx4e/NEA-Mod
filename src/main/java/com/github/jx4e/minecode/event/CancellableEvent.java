package com.github.jx4e.minecode.event;

public class CancellableEvent {
    private boolean cancelled = false;

    protected CancellableEvent() {}

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }
}
