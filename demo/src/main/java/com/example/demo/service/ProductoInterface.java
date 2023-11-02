package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Producto;
import com.example.demo.response.ModelResponse;

public interface ProductoInterface {
    public List<Producto> mostrarProducto();

    public Producto buscarProductoXId(Long id);

    public Producto buscarProductoXNombre(String nombre);

    public ModelResponse guardarProducto(Producto producto);

    public Producto actualizarProducto(Long id, Producto producto);

    public ModelResponse eliminarProducto(Long id);
}
