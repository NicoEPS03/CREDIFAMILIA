package com.prueba.demo.service;

import com.prueba.demo.exception.IntegridadException;
import com.prueba.demo.exception.ModelNotFoundException;

public interface ICrud<T,ID> {

    public T getOne(ID id) throws ModelNotFoundException;

    public T save(T object)  throws IntegridadException;

    public T edit(T object)  throws ModelNotFoundException, IntegridadException;

    public void delete(String idObject) throws ModelNotFoundException;
}
