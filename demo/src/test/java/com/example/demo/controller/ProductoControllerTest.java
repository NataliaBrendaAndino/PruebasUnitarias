package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Producto;
import com.example.demo.exceptions.RecursoNoEncontrado;
import com.example.demo.service.service_implementa.ProductoService;

public class ProductoControllerTest {

    @InjectMocks
    private ProductoController productoController;

    @Mock
    private ProductoService productoService;

    private List<Producto> productos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productos = new ArrayList<>();
        productos.add(new Producto(1L, "Producto1"));
        productos.add(new Producto(2L, "Producto2"));
    }

    @Test
    void testListarProductos() {
        when(productoService.mostrarProducto()).thenReturn(productos);
        ResponseEntity<?> responseEntity = productoController.listarProductos();
        assertThat(responseEntity.getBody()).isEqualTo(productos);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testListarProductos_Error() {
    // Simula una excepción en el servicio cuando se llama a mostrarProducto
    when(productoService.mostrarProducto()).thenThrow(new RuntimeException(new RecursoNoEncontrado().getMessage()));

    ResponseEntity<?> responseEntity = productoController.listarProductos();

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isEqualTo(new RecursoNoEncontrado().getMessage()); // Ajusta esto según el mensaje de error real
    }

    //completar: buscarProductoXId, buscarProductoXNombre
    //tanto caso exitoso como fallido

}
