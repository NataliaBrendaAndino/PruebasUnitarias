package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Producto;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductoRepository productoRepository;

    Producto producto;

    @BeforeEach
    public void setUp() {
        producto = new Producto();
        producto.setNombre("Pantalon");
        entityManager.persist(producto);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindByNombre_Found() {
        String nombre = "Pantalon";

        Producto producto2 = productoRepository.findByNombre(nombre);

        assertThat(producto2).isNotNull();
        assertThat(producto2.getNombre()).isEqualTo(nombre);
    }

    @Test
    public void testFindByNombre_NotFound() {
        String nombre = "Camiseta";

        Producto producto3 = productoRepository.findByNombre(nombre);

        assertThat(producto3).isNull();
    }
//    @Test
//    public void testFindByPrecio_Found() {
//        int precio = 1000;
//
//        Producto producto2 = productoRepository.findByPrecio(precio);
//        assertThat(producto2).isNotNull();
//        assertThat(producto2.getPrecio()).isEqualTo(precio);
//
//    }
}
