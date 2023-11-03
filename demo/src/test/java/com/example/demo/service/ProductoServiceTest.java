package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.service_implementa.ProductoService;

public class ProductoServiceTest {
    
    @Autowired
    ProductoService productoService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductoRepository productoRepository;

    Producto p1;
    Producto p2;
    Producto p3;

    @BeforeEach
    public void setUp() {
        //Inicio un producto en la DB, en base al cual realizare los Test
        p1 = new Producto();
        p1.setNombre("Pantalon");
        p1.setPrecio(1500);
        entityManager.persist(p1);

        p2 = new Producto();
        p2.setNombre("Camisa");
        p2.setPrecio(1000);
        entityManager.persist(p2);

        p3 = new Producto();
        p3.setNombre("Gorra");
        p3.setPrecio(300);
        entityManager.persist(p3);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    public void buscarProductoXRangoDePrecio(){

        //Establezco precios para el rango
        int precio1 = 700;
        int precio2 = 2000;

        List<Producto> productos = productoService.buscarProductoXRangoDePrecio(precio1, precio2);

        assertThat(productos.contains(p1));
        assertThat(productos.contains(p2));
    }

    


    //Abrimos el mock
    //


}
