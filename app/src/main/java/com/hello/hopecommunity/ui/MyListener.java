package com.hello.hopecommunity.ui;

public interface MyListener<T> {
    void onSuccess(T t);

    void onFaile(T t);
}
