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
        //Inicio un producto en la DB, en base al cual realizare los Test
        producto = new Producto();
        producto.setNombre("Pantalon");
        producto.setPrecio(1000);
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
    public void testFindByPrecio_Found(){
        //Testear que me devuelva el producto que busco en base a un precio dado
        int precio = 1000; //establezco un precio

        Producto producto2 = productoRepository.findByPrecio(precio);
        //Busco un producto en base a ese precio

        assertThat(producto2).isNotNull();
        //No deberia devolverme un null, ya que existe un producto con ese precio

        assertThat(producto2.getPrecio()).isEqualTo(precio);
        //El precio del producto que obtuve, deberia ser igual al precio establecido
    }

    @Test
    public void testFindByPrecio_NotFound(){
        //Testear que no me devuelva un producto, en base a un precio dado

        int precio = 50; //establezco un precio

        Producto producto2 = productoRepository.findByPrecio(precio);
        //Busco un producto en base a ese precio

        assertThat(producto2).isNull();
        //Deberia devolverme un null, ya que NO existe un producto con ese precio

    }
}
