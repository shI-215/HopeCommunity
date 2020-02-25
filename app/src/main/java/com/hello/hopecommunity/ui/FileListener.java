package com.hello.hopecommunity.ui;

public interface FileListener<T> {
    void onUpload(T t);

    void onError(T t);
}
