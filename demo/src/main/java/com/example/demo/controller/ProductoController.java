package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Producto;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.response.ModelResponse;
import com.example.demo.service.service_implementa.ProductoService;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> listarProductos() {
        try {
            return new ResponseEntity<>(productoService.mostrarProducto(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new RecursoNoEncontrado().getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProductoXId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productoService.buscarProductoXId(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ModelResponse("Error al buscar producto: verificar ID",
                            e.getClass().getName()));
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarProductoXNombre(@PathVariable String nombre) {
        try {
            return new ResponseEntity<>(productoService.buscarProductoXNombre(nombre), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new RecursoNoEncontrado().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> buscarProductoXColor(@PathVariable String color) {
        try{
            return new ResponseEntity<>(productoService.buscarProductoXColor(color), HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(new RecursoNoEncontrado().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ModelResponse> guardarProducto(@RequestBody Producto producto) {
        try {
            productoService.guardarProducto(producto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ModelResponse("Operación exitosa", "Success"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ModelResponse("Error al crear producto",
                            e.getClass().getName()));

        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            return new ResponseEntity<>(productoService.actualizarProducto(id, producto), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ModelResponse("Error al actualizar producto",
                            e.getClass().getName()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productoService.eliminarProducto(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ModelResponse("Error al eliminar producto",
                            e.getClass().getName()));
        }
    }

}
