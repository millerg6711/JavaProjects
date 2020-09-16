package edu.ranken.gmiller.roomwordsamplelive.data;

public class AsyncTaskResult<T> {

    private final T result;
    private final Exception error;

    public AsyncTaskResult(T result) {
        this.result = result;
        this.error = null;
    }

    public AsyncTaskResult(Exception error) {
        this.error = error;
        this.result = null;
    }

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}
