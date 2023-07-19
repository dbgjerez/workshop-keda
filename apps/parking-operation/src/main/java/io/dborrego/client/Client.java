package io.dborrego.client;

public interface Client<T> {
    public String getHost();

    public T findById(Long id);

    public T create(T obj);

    public void update(T obj, Long id);
}
