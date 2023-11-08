package com.example.demo.service.service_implementa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.response.ModelResponse;
import com.example.demo.service.ProductoInterface;

@Service
public class ProductoService implements ProductoInterface {

    @Autowired
    private ProductoRepository productoRepository;

    //Este constructor existe unicamente con el proposito de facilitar la inyeccion en pruebas unitarias sin cargar todo el contexto de spring
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> mostrarProducto() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarProductoXId(Long id) {
        return productoRepository.findById(id);
    }
    
    @Override
    public Optional<Producto> buscarProductoXNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
        
    }
    
    @Override
    public List<Producto> buscarXcategoria(String categoria) {
        try {
            List<Producto> lista = new ArrayList<>();
            lista = productoRepository.findByCategoria(categoria);
            return lista;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'buscarXcategoria'");
        }
    }

    @Override
    public List<Producto> buscarXcalificacion(int calificacion) {
        try {
            List<Producto> lista = new ArrayList<>();
            lista = productoRepository.findByCalificacion(calificacion);
            return lista;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'buscarXcalificacion'");
        }
    }
    

    @Override
    public Optional<?> guardarProducto(Producto producto) {
        Producto productoGuardado = productoRepository.save(producto);
        return Optional.of(productoGuardado);
    }

    @Override
    public Optional<?> actualizarProducto(Long id, Producto producto) {
        Producto productoAux = productoRepository.findById(id).get();
        productoAux.setNombre(producto.getNombre());
        productoRepository.save(productoAux);
        return Optional.of(productoAux);
    }

    @Override
    public ModelResponse eliminarProducto(Long id) {
        try {
            productoRepository.deleteById(id);
            return new ModelResponse("Producto eliminado exitosamente", "Operación: eliminar recurso");
        } catch (Exception e) {
            return new ModelResponse("Error al eliminar el producto", "Id no encontrado");
        }
    }


}
