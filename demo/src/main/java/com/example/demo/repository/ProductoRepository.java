package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    public abstract Producto findByNombre(String nombre);

    public abstract Producto findByProducto(String color);

    public abstract List<Producto> findByProducto(float preciomin, float preciomax);

    public abstract Producto findByID(long id);
}
