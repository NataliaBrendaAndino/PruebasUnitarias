package com.example.demo.service.service_implementa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {

    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    AutoCloseable autoCloseable;
    Producto productoFalseado;
    List<Producto> productos;
    Long id;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        // instanciamos productoService, mandándole como argumento el mock de
        // ProductoRepository
        productoService = new ProductoService(productoRepository);
        // instanciamos un producto de prueba
        id = 1L;
        productoFalseado = new Producto(id, "Pintorcito");
        // creamos una lista con dos productos
        productos = new ArrayList<>();
        productos.add(productoFalseado);
        productos.add(new Producto(2L, "Babero"));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();// Limpieza después de cada prueba
    }

    @Test
    void testMostrarProducto() {
        // Configura el comportamiento del repositorio mockeado
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> productosTest = productoService.mostrarProducto();
        

        // Verifica que se devuelva una lista no vacía
        assertThat(productosTest).isNotEmpty();
    }

    @Test
    void testBuscarProductoXId() {
        // Configura el comportamiento del mock repositorio 
        //"cuando se llame a este método, simulá que hay un objeto producto con un id idéndico"
        //when(métodoTesteado(parámetro).thenReturn(Optional.of(objetoFalseado)))
        when(productoRepository.findById(id)).thenReturn(Optional.of(productoFalseado));

        Producto productoPrueba = productoService.buscarProductoXId(1L);

        // Verifica que se obtenga un producto con el ID especificado
        assertThat(productoPrueba).isNotNull();
        assertThat(productoPrueba.getId()).isEqualTo(id);
    }

    // @Test
    void testBuscarProductoXNombre() {
        // Completar
    }

    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(1L, "Producto1");

        // Configura el comportamiento del repositorio mock
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto savedProducto = productoService.guardarProducto(producto);

        // Verifica que el producto se haya guardado correctamente
        assertThat(savedProducto).isNotNull();
        assertThat(savedProducto.getId()).isEqualTo(1L);
        assertThat(savedProducto.getNombre()).isEqualTo("Producto1");
    }

    // Agrega más pruebas para otros métodos

}
