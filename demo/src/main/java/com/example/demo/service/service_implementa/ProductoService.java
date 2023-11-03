package com.example.demo.service.service_implementa;

import java.util.List;

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

    @Override
    public List<Producto> mostrarProducto() {
        return (List<Producto>) productoRepository.findAll();
    }

    public ProductoService (ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public ProductoService() {
    }

    @Override
    public Producto buscarProductoXId(Long id) {
        return productoRepository.findById(id).get();

    }

    @Override
    public Producto buscarProductoXNombre(String nombre) {
        return productoRepository.findByNombre(nombre);

    }

    @Override
    public Producto buscarProductoXPrecio(int precio) {
        return productoRepository.findByPrecio(precio);
    }

    @Override
    public Producto buscarProductoXStock(int stock) {
        return productoRepository.findByStock(stock);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto actualizarProducto(Long id, Producto producto) {
        Producto productoAux = productoRepository.findById(id).get();
        productoAux.setNombre(producto.getNombre());
        productoAux.setPrecio(producto.getStock());
        productoAux.setStock(producto.getStock());
        productoRepository.save(productoAux);
        return productoAux;
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
