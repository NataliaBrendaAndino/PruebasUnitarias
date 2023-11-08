package com.example.demo.service.service_implementa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.response.ModelResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoServiceTest {

    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    AutoCloseable autoCloseable;
    Producto productoFalseado;
    List<Producto> productos;
    Long id;
    Long id2;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        // instanciamos productoService, mandándole como argumento el mock de
        // ProductoRepository
        productoService = new ProductoService(productoRepository);
        // instanciamos un producto de prueba
        id = 1L;
        id2 = 2L;
        productoFalseado = new Producto(id, "Pintorcito", 122, 120);
        // creamos una lista con dos productos
        productos = new ArrayList<>();
        productos.add(productoFalseado);
        productos.add(new Producto(id2, "Babero", 122, 80));
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
        assertThat(productoPrueba.getId()).isEqualTo(1L);
    }

    // @Test
    void testBuscarProductoXNombre() {
        // Completar
    }

@Test
void testGuardarProducto() {
    // Configura el comportamiento del repositorio mock
    when(productoRepository.save(productoFalseado)).thenReturn(productoFalseado);

    // Llama al método guardarProducto
    ModelResponse response = productoService.guardarProducto(productoFalseado);

    // Verifica que se devuelva un mensaje de éxito
    assertThat(response.getMensaje()).isEqualTo("Producto guardado");
    assertThat(response.getTipo()).isEqualTo("Operación: guardarProducto");
}

@Test
void testGuardarProductoError() {
    // Configuramos el comportamiento del repositorio mock para simular una excepción al guardar
    //podría ser una excepción creada por nosotros o cualquier unchecked
    when(productoRepository.save(productoFalseado)).thenThrow(new RuntimeException("Error al guardar el producto"));

    // Llama al método guardarProducto
    ModelResponse response = productoService.guardarProducto(productoFalseado);

    // Verifica que se devuelva un mensaje de error
    assertThat(response.getMensaje()).isEqualTo("Error al guardar el producto");
    assertThat(response.getTipo()).isEqualTo("Tipo: java.lang.RuntimeException"); // El tipo de excepción
}

@Test
void testActualizarProducto() {
    // Configuramos el comportamiento del repositorio mock
    when(productoRepository.findById(id)).thenReturn(Optional.of(productoFalseado));

    // Producto de prueba con un nombre modificado
    String piluso = "piluso";
    Producto productoModificado = new Producto(id, piluso, 100, 2333);

    // Llamamos al método actualizarProducto del servicio
    Producto resultado = productoService.actualizarProducto(id, productoModificado);

    // Verificamos que se obtenga un resultado no nulo
    assertThat(resultado).isNotNull();

    // Verificamos que el nombre del producto sea igual al nuevo nombre
    assertThat(resultado.getNombre()).isEqualTo("piluso");

    // Verificamos que el mock del repositorio se haya llamado con el ID correcto, 
    // como queremos verificar el mock, usamos verify, que es una función de Mockito
    verify(productoRepository).findById(id);
}

    @Test
    void testEliminarProducto() {
        Long idAEliminar = 1L;

        // doNothing() es el método que trae Mockito asociado a operaciones
        // de tipo delete, como las de CRUDRepository o JPARepository
        doNothing().when(productoRepository).deleteById(idAEliminar);

        // Llama al método eliminarProducto del servicio
        ModelResponse response = productoService.eliminarProducto(idAEliminar);

        // Verifica que se obtenga una respuesta no nula
        assertThat(response).isNotNull();

        // Verifica que el mensaje de la respuesta sea correcto para el caso exitoso
        assertThat(response.getMensaje()).isEqualTo("Producto eliminado exitosamente");

        // Verifica que el tipo de la respuesta sea correcto para el caso exitoso
        assertThat(response.getTipo()).isEqualTo("Operación: eliminar recurso");

        // Verifica que el método deleteById del repositorio se haya llamado con el ID
        // correcto
        verify(productoRepository).deleteById(idAEliminar);
    }

    // completar el caso fallido de delete

}
