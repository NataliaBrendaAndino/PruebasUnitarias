package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Producto;
import com.example.demo.response.ModelResponse;

public interface ProductoInterface {
    public List<Producto> mostrarProducto();

    public Optional<Producto> buscarProductoXId(Long id);

    public Optional<Producto> buscarProductoXNombre(String nombre);

    public List<Producto> buscarXcategoria(String categoria);

    public List<Producto> buscarXcalificacion(int calificacion);

    public Optional<?> guardarProducto(Producto producto);

    public Optional<?> actualizarProducto(Long id, Producto producto);

    public ModelResponse eliminarProducto(Long id);
}
