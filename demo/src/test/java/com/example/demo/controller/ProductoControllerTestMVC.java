package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.Producto;
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

        this.mockMvc.perform(MockMvcRequestBuilders.get("/producto"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // completar: buscarProductoXId, buscarProductoXNombre
    // tanto caso exitoso como fallido

    @Test
    void testGuardarProductoEndpoint() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/producto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // completar el fallido

    @Test
    void testActualizarProductoEndpoint() throws Exception {
        Long id = 1L;

        this.mockMvc.perform(put("/producto/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // completar el fallido

    // completar DELETE :O

}
