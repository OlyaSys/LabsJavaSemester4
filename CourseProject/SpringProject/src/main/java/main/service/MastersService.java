package main.service;

import main.entity.Masters;

import java.util.List;

public interface MastersService {
    List<Masters> listMasters();
    Masters findById(Long id);
    Masters findByName(String name);
    Masters addMaster(Masters master);
    Masters setMaster(Long id, Masters master);
}
