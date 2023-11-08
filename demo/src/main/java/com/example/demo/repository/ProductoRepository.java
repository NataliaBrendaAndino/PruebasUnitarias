package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Producto;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long> {

    public abstract Optional<Producto> findByNombre(String nombre);

    List<Producto> findByCategoria(String categoria);
    List<Producto> findByCalificacion(int calificacion);
}
