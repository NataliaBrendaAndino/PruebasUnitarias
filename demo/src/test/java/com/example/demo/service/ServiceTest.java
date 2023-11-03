package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.response.ModelResponse;
import com.example.demo.service.service_implementa.ProductoService;

public class ServiceTest {
    //testeamos el flujo de informacion entre servicio y repositorio, y no la persistencia en si, que lo testeamos en el test del servicio.
    
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    AutoCloseable autoCloseable;
    Producto productoFalseado;
    List<Producto> productos;
    Long id;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);//Abrimos el mock

        // instanciamos productoService, mandándole como argumento el mock de ProductoRepository
        //Al servicio, le asignamos el repositoroio mockeado, a traves del constructor.
        productoService = new ProductoService(productoRepository); //
        // instanciamos un producto de prueba, de manera manual

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
        //Quiero probar el metodo .mostrarProducto
        //Este metodo (al observarlo en el ProductoService) hace uso del metodo .findAll del repositorio

        /*Por esto, primero debemos simular ese metodo (.findAll), para indicarle a nuestro mock del repositorio 
        como deberia comportarse si se llama a ese metodo. 
        En este caso le indicamos que cuando se llame a la funcion .findAll, deberia ejecutar .thenReturn(productos); */
        // Configura el comportamiento del repositorio mockeado
        when(productoRepository.findAll()).thenReturn(productos);

        //Una vez indicado el comportamiento del mock del repositorio, probamos y ejecutamos el metodo del servicio en si (.mostrarProducto).
        List<Producto> productosTest = productoService.mostrarProducto();

        //Luego, indicamos lo que esperamos de la ejecucion del servicio, con assertThat ()
        assertThat(productosTest).isNotEmpty(); // Verifica que se devuelva una lista no vacía
        
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
    assertThat(response.getMensaje()).isEqualTo("Error al guardar producto");
    assertThat(response.getTipo()).isEqualTo("Tipo: java.lang.RuntimeException"); // El tipo de excepción
}

@Test
void testActualizarProducto() {
    // Configuramos el comportamiento del repositorio mock
    when(productoRepository.findById(id)).thenReturn(Optional.of(productoFalseado));

    // Producto de prueba con un nombre modificado
    String piluso = "piluso";
    Producto productoModificado = new Producto(id, piluso);

    // Llamamos al método actualizarProducto del servicio
    Producto resultado = productoService.actualizarProducto(id, productoModificado);

    // Verificamos que se obtenga un resultado no nulo
    assertThat(resultado).isNotNull();

    // Verificamos que el nombre del producto sea igual al nuevo nombre actualizado
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
    
}
