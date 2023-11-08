package com.example.demo.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.entity.Producto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

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
        producto.setCategoria("ropa");
        producto.setCalificacion(5);
        entityManager.persist(producto);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindByNombre_Found() {
        String nombre = "Pantalon";

        Optional<Producto> producto2 = productoRepository.findByNombre(nombre);

        assertThat(producto2).isNotNull();
        assertThat(producto2.get().getNombre()).isEqualTo(nombre);
    }

    @Test
    public void testFindByNombre_NotFound() {
        String nombre = "Camiseta";

        Optional<Producto> producto3 = productoRepository.findByNombre(nombre);

        assertThat(producto3).isEmpty();
    }

    @Test
    public void testFindByCategoria_Found(){
        String categoria = "ropa";
        List<Producto> listaXcategorias = productoRepository.findByCategoria(categoria);

        assertThat(listaXcategorias).isNotEmpty();
        for (Producto producto : listaXcategorias){
            assertThat(producto.getCategoria().equals(categoria));
        }
    }

    public void testFindByCategoria_NotFound(){
        String categoria = "automotores";
        List<Producto> listaXcatergoria = productoRepository.findByCategoria(categoria);

        assertThat(listaXcatergoria).isNotEmpty();
        for(Producto producto : listaXcatergoria){
            assertThat(producto.getCategoria().equals(categoria));
        }
    }

    public void testFindByCalificacion_Found(){
        int cali = 5;
        List<Producto> listaXcalificacion = productoRepository.findByCalificacion(cali);

        assertThat(listaXcalificacion).isNotEmpty();
        for(Producto producto : listaXcalificacion){
            assertThat(producto.getCalificacion()==cali);
        }
    }

    public void testFindByCalificacion_NotFound(){
        int cali = 3;
        List<Producto> listaXcalificacion = productoRepository.findByCalificacion(cali);

        assertThat(listaXcalificacion).isNotEmpty();
    }
}
