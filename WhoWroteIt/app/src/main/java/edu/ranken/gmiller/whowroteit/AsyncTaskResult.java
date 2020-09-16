package edu.ranken.gmiller.whowroteit;

public class AsyncTaskResult<T> {
    private final T result;
    private final Exception error;

    public AsyncTaskResult(T result) {
        this.result = result;
        this.error = null;
    }

    public  AsyncTaskResult(Exception error) {
        this.result = null;
        this.error = error;
    }

    public T getResult() { return result; }
    public Exception getError() { return error; }
}
