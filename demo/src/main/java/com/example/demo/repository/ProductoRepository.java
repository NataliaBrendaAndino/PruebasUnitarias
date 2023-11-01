package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    public abstract Producto findByNombre(String nombre);
}
