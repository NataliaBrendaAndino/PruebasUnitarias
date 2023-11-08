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
//Permite probar específicamente la lógica de los controladores 
//y cómo gestionan las solicitudes y respuestas HTTP.
public class ProductoControllerTestMVC {
    //No vuelve a tester el servicio, sino que hace incapie en los entitys y endpoint

    @Autowired
    private MockMvc mockMvc;
    //la proporciona mockito y permite hacer testeos para endpoints

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

        //desde el mockMva ejecutamos .perform (performance de como funcionaria una ruta)
        this.mockMvc.perform(MockMvcRequestBuilders.get("/producto")) 
                //Hace una llamada a la ruta,detipo get

                .andExpect(MockMvcResultMatchers.status().isOk());
                //Establece el tipo de estado que espera
    }

    // completar: buscarProductoXId, buscarProductoXNombre
    // tanto caso exitoso como fallido

    @Test
    void testGuardarProductoEndpoint() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/producto")
        //Hago una peticion de tipo post a la ruta indicada
                .contentType(MediaType.APPLICATION_JSON)
        //Declaro el tipo de contenido (JSON) que se maneja
                .content(new ObjectMapper().writeValueAsString(producto)))
        /*le ingresa un objetMapper(de la libreria jackson) 
        que le indicamos el cuerpo del json de la peticion (producto)*/
                .andExpect(MockMvcResultMatchers.status().isCreated());
                //Indicamos el tipo de estado que esperamos
    }

    // completar el fallido

    @Test
    void testActualizarProductoEndpoint() throws Exception {
        Long id = 1L;

        this.mockMvc.perform(put("/producto/{id}", id)
        // import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
        //como especifico la ruta, puedo llamarla directamente
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(producto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // completar el fallido

    // completar DELETE :O

}
