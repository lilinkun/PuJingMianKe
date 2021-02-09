package com.lzy.okgo.model;

public final class Result<T> {
    @SuppressWarnings("ConstantConditions")
    public static <T> Result<T> error(Throwable error) {
        if (error == null) throw new NullPointerException("error == null");
        return new Result<>(null, error);
    }

    @SuppressWarnings("ConstantConditions")
    public static <T> Result<T> response(Response<T> response) {
        if (response == null) throw new NullPointerException("response == null");
        return new Result<>(response, null);
    }

    private final Response<T> response;
    private final Throwable error;

    private Result(Response<T> response, Throwable error) {
        this.response = response;
        this.error = error;
    }


    public Response<T> response() {
        return response;
    }


    public Throwable error() {
        return error;
    }


    public boolean isError() {
        return error != null;
    }

    @Override
    public String toString() {
        if (error != null) {
            return "Result{isError=true, error=\"" + error + "\"}";
        }
        return "Result{isError=false, response=" + response + '}';
    }
}
