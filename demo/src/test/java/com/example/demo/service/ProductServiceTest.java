package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.demo.entity.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.response.ModelResponse;
import com.example.demo.service.service_implementa.ProductoService;

public class ProductServiceTest {
    
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;

    AutoCloseable autoCloseable;
    Producto productoSimulado;
    List<Producto> productos;
    Long id;

    /*En este caso los productos son instanciados de manera manual
    para evitar la carga de todo el contexto de spring mediante
    el uso de la etiqueta @Autowired en la inyeccion del 
    repositorio, esta configuracion se ve explicitamente en la
    configuracion del mock en el metodo setUp() y en el metedo 
    tearDown() que se encarga de liberar y limpiar los recursos*/

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        //intancia del servicio al repositorio intanciado manualmente
        productoService = new ProductoService(productoRepository);
        //configuracion de los atributos de la simulacion del repositorio
        productos = new ArrayList<>();
        productos.add(new Producto(1L, "producto_1","cat1",5));
        productos.add(new Producto(2L, "producto_2","cat2",3));
        productoSimulado = productos.get(0);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void mostrarProducto_AfirmativeTest() {
        //configuraciondelmokito
        when(productoRepository.findAll()).thenReturn(productos);
        List<Producto> productTest = productoService.mostrarProducto();
        assertThat(productTest).isNotEmpty();
    }

    @Test
    void mostrarProducto_NegativeTest() {
        when(productoRepository.findAll()).thenReturn(Collections.emptyList());
        List<Producto> productosSimulados = productoService.mostrarProducto();
        assertThat(productosSimulados).isEmpty();
    }
    
    @Test
    void buscarXid_AfirmativeTest() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoSimulado));
        Optional<Producto> productoTest = productoService.buscarProductoXId(1L);
        assertThat(productoTest).isNotNull();
        assertThat(productoTest.get().getId()).isEqualTo(productoSimulado.getId());
    }

    @Test
    void buscarXid_NegativeTest() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Producto> productoTest = productoService.buscarProductoXId(2L);
        assertThat(productoTest).isEmpty();
    }

    @Test
    void buscarProductoXNombre_AfirmativeTest() {
        String nombreBuscado = "producto_1";
        when(productoRepository.findByNombre(nombreBuscado)).thenReturn(Optional.of(productoSimulado));
        Optional<Producto> productoEncontrado = productoService.buscarProductoXNombre(nombreBuscado);
        assertThat(productoEncontrado).isNotNull();
        assertThat(productoEncontrado.get().getNombre()).isEqualTo(nombreBuscado);  
    }

    @Test
    void buscarProductoXNombre_NegativeTest() {
        String nombreBuscado = "producto_1";
        when(productoRepository.findByNombre(nombreBuscado)).thenReturn(Optional.empty());
        Optional<Producto> productoTest = productoService.buscarProductoXNombre(nombreBuscado);
        assertThat(productoTest).isEmpty();

    }

    //estan bien configurados los mokitos???
    //en este caso de configuracion no importa el contenido del string categoria?
    //es importante esto?
    //los casos de uso reales serian lista vacia y lista con elementos
    //esta bien que la diferencia entre estos test sea solo el assertthat()?
    //la comprobacion de que los productos en la lista pertenecen todos a la misma cat debe ser una prueba aparte o debe estar dentro de esta?


    @Test
    void buscarXcategoriaTest_ListIsNotEmpty() {
        String categoria = "cat1";
        //podria crear una lista con elementos de la misma cat para poder hacer una comprobacion mas pero tendria sentido?
        //si las conficiones simuladas estan determinadas para que todo ocurra de una determinada manera tiene sentido?
        when(productoRepository.findByCategoria(categoria)).thenReturn(productos);
        List<Producto> productosSimulados =(List<Producto>) productoService.buscarXcategoria(categoria);
        assertThat(productosSimulados).isNotEmpty();
    }

    @Test
    void buscarXcategoriaTest_ListIsEmpty() {
        String categoria = "cat2";
        when(productoRepository.findByCategoria(categoria)).thenReturn(Collections.emptyList());
        List<Producto> productosSimulados = (List<Producto>) productoService.buscarXcategoria(categoria);
        assertThat(productosSimulados).isEmpty();
    }

    @Test
    void buscarXcalificacionTest_ListIsNotEmpty() {
        int calificacion = 3;
        when(productoRepository.findByCalificacion(calificacion)).thenReturn(productos);
        List<Producto> productosSimulados = (List<Producto>) productoService.buscarXcalificacion(calificacion);
        assertThat(productosSimulados).isNotEmpty();
    }

    @Test
    void buscarXcalificacionTest_ListIsEmpty() {
        int calificacion = 3;
        when(productoRepository.findByCalificacion(calificacion)).thenReturn(Collections.emptyList());
        List<Producto> productosSimulados = (List<Producto>) productoService.buscarXcalificacion(calificacion);
        assertThat(productosSimulados).isEmpty();
    }

    @Test
    void guardarProducto_AfirmativeTest() {
        when(productoRepository.save(productoSimulado)).thenReturn(productoSimulado);
        Optional<?> productoGuardado = productoService.guardarProducto(productoSimulado);
        assertThat(productoGuardado).isNotEmpty();
        assertThat(productoGuardado.get().equals(productoSimulado));
    }

    @Test
    void guardarProducto_NegativeTest() {
        when(productoRepository.save(productoSimulado)).thenThrow(new RuntimeException("Error al guardar el producto"));
        //assertThrows metodo proporcionado por JUnit utilizado para verificar que una determinada operacion lanza una excepcion
        //se utiliza este tipo de manejo de excepciones ya que el servicio no contiene un manejo de error propio 
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            productoService.guardarProducto(productoSimulado);
        });
        assertThat(exception.getMessage()).isEqualTo("Error al guardar el producto");
    }

    @Test
    void actualizarProducto_AfirmativeTest() {
        Producto productoModificado = new Producto(1L,"piluso","gorras",5);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoModificado));
        Optional<?> productoActualizado = productoService.actualizarProducto(1L, productoModificado);
        assertThat(productoActualizado).isNotEmpty();
        assertThat(productoActualizado.get()).isEqualTo(productoModificado);
    }

    @Test
    void actualizarProducto_NegativeTest() {
        Producto productoModificado = new Producto(1L,"piluso","gorras",5);
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());
        //nuevamente es necesario capturar la excepcion dentro del test ya que el servicio no cuenta con manejo de errores propio
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            productoService.actualizarProducto(id, productoModificado);
        });
        assertThat(exception).isNotNull();
    }

    @Test
    void eliminarProducto_AfirmativeTest() {
        doNothing().when(productoRepository).deleteById(1L);
        ModelResponse response = productoService.eliminarProducto(1L);
        assertThat(response.getMensaje()).isEqualTo("Producto eliminado exitosamente");
    }

    @Test
    void eliminarProducto_NegaativeTest() {
        doThrow(EmptyResultDataAccessException.class).when(productoRepository).deleteById(1L);
        ModelResponse response = productoService.eliminarProducto(1L);
        assertThat(response.getMensaje()).isEqualTo("Error al eliminar el producto");
        assertThat(response.getTipo()).isEqualTo("Id no encontrado");
    }

}



/* Deteccion prematura de errores: esta practica fue que mientras ESCRIBIA las pruebas unitarias,
 * ya que los servicios que debian devolver una lista de elementos estaban devolviendo una unica
 * entidad, entonces sin correr necesariamente la prueba podria decir que en el proceso de
 * creacion de los test tambien se pueden observar errores que deben ser corregidos.
 * 
 * Manejo de errores en la etapa de desarrollo: Muchos de los servicios no contaban con un sistema
 * propio de manejo de errores por lo que fue necesario la incorporacion del metodo assertThrows del
 * framework JUnit, me parecio mas dinamico e importante no corregir el manejo de errores en la capa
 * de servicios si no adaptar las pruebas a estos escenarios para resaltar la importancia de cada 
 * etapa de desarrollo y de como las pruebas unitarias hacen visibles este tipo de situaciones. 
 * 
 * Resumiendo: al contrastar los resultados esperados con los resultados obtenidos incluso
 * dentro del desarrollo de los test podemos detectar errores de diferente etapas de desarrollo
 * forma prematura, incluso antes de correr un test */