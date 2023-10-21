package com.DataSaver.demo.Repocitory;

import com.DataSaver.demo.Model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepocitory extends JpaRepository<Data,Integer> {
    Data findByName(String name);
}
