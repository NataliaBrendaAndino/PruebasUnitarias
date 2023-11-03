package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.Producto;
import com.example.demo.response.ModelResponse;
import com.example.demo.service.service_implementa.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ProductoControllerTestMVC {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProductoController productoController;

    @MockBean
    private ProductoService productoService;

    private List<Producto> productos;
    Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producto = new Producto(1L, "Pintorcito");
        productos = new ArrayList<>();
        productos.add(producto);
        productos.add(new Producto(2L, "Babero"));
    }

    @Test
    public void testListarProductosEndpoint() throws Exception {
        
        when(productoService.mostrarProducto()).thenReturn(productos);
        ResponseEntity<?> responseEntity = productoController.listarProductos();
        assertThat(responseEntity.getBody()).isEqualTo(productos);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/producto"))
                .andExpect(MockMvcResultMatchers.status().isOk());
                //para el fallido, podemos seguir poniendo .andExpected
    }

    // completar el fallido de testListarProductosEndpoint con la pista anterior
    //// completar: buscarProductoXId, buscarProductoXNombre
    // tanto caso exitoso como fallido

    @Test
    void testGuardarProductoEndpoint() throws Exception {

        // Crear un ModelResponse
        ModelResponse modelResponse = new ModelResponse("Operación exitosa", "Success");

        // Configurar el servicio para que devuelva un ResponseEntity con
        // HttpStatus.CREATED
        when(productoService.guardarProducto(producto))
                .thenReturn(modelResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/producto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // completar el fallido

    @Test
    void testActualizarProductoEndpoint() throws Exception {
        Long id = 1L;
        // Configurar el servicio para que devuelva el Producto modificado
        when(productoService.actualizarProducto(id, producto))
                .thenReturn(producto);

        // Realizar la solicitud PUT
        mockMvc.perform(put("/producto/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // completar el fallido

    // completar DELETE :O

}
