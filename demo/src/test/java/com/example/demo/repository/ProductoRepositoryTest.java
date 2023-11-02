package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Producto;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
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
        producto.setPrecio(2400);
        producto.setStock(2);
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
    @Test
    public void testFindByPrecio_Found() {
        int precio = 2400;
        Producto producto2 = productoRepository.findByPrecio(precio);
        assertThat(producto2).isNotNull();
        assertThat(producto2.getPrecio()).isEqualTo(precio);

    }
    @Test
    public void testFindByPrecio_NotFound(){
        int precio = 123;
        Producto producto3 = productoRepository.findByPrecio(precio);
        assertThat(producto3).isNull();
    }
    @Test
    public void testFindByStock_Found() {
        int stock = 2;
        Producto producto2 = productoRepository.findByStock(stock);
        assertThat(producto2).isNotNull();
        assertThat(producto2.getStock()).isEqualTo(stock);

    }
    @Test
    public void testFindByStock_NotFound(){
        int stock = 123;
        Producto producto3 = productoRepository.findByStock(stock);
        assertThat(producto3).isNull();
    }
}
