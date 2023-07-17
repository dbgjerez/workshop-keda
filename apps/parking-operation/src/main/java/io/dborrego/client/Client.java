package io.dborrego.client;

public interface Client<T> {
    public T findById(Long id);
}
