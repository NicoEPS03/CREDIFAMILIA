package com.prueba.demo.service;

import com.prueba.demo.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientService extends ICrud<Client, String>{
    Page<Client> getAll(Pageable pageable);
}
