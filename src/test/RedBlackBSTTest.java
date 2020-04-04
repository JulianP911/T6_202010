package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import model.data_structures.RedBlackBST;

class RedBlackBSTTest 
{

	/**
	 * Arbol Rojo - Negro con llave de tipo entero y valor entero
	 */
	private RedBlackBST<Integer, Integer> arbolRojoNegro1;

	/**
	 *  Arbol Rojo - Negro con llave de tipo entero y valor string
	 */
	private RedBlackBST<Integer, String> arbolRojoNegro2;

	/**
	 *  Arbol Rojo - Negro con llave de tipo string y valor string
	 */
	private RedBlackBST<String, String> arbolRojoNegro3;

	/**
	 * Verifica que los arboles Rojo - Negro si se esten creando
	 */
	@Test
	void testRedBlackBST() 
	{
		arbolRojoNegro1 = new RedBlackBST<Integer, Integer>();
		arbolRojoNegro2 = new RedBlackBST<Integer, String>();
	}

	/**
	 * Escenario 1: Crea un Arbol Rojo - Negro con llave de tipo entero y valor entero
	 */
	@Before
	public void setupEscenario1( )
	{
		arbolRojoNegro1 = new RedBlackBST<Integer, Integer>();
		arbolRojoNegro1.put(1, 1000);
		arbolRojoNegro1.put(2, 2000);
		arbolRojoNegro1.put(3, 3000);
		arbolRojoNegro1.put(4, 4000);
		arbolRojoNegro1.put(5, 5000);

	}

	/**
	 * Escenario 2: Crea un Arbol Rojo - Negro con llave de tipo entero y valor string
	 */
	@Before
	public void setupEscenario2( )
	{
		arbolRojoNegro2 = new RedBlackBST<Integer, String>();
		arbolRojoNegro2.put(1, "Hola");
		arbolRojoNegro2.put(2, "Elina");
		arbolRojoNegro2.put(3, "O");
		arbolRojoNegro2.put(4, "Carlos");
		arbolRojoNegro2.put(5, "Buen");
		arbolRojoNegro2.put(6, "Dia");
	}

	/**
	 * Escenario 3: Crea un Arbol Rojo - Negro con llave de tipo string y valor string
	 */
	@Before
	public void setupEscenario3( )
	{
		arbolRojoNegro3 = new RedBlackBST<String, String>();
	}

	/**
	 * Prueba 1: Verifica que el tamanio de los arboles Rojo - Negro si corresponda adecdamente con el de los escenarios 1 y 2
	 */
	@Test
	void testSize() 
	{
		setupEscenario1();
		assertEquals(5, arbolRojoNegro1.size());

		setupEscenario2();
		assertEquals(6, arbolRojoNegro2.size());
	}

	/**
	 * Prueba 2: Verifica que el arbol Rojo - Negro se encuentre vacio
	 */
	@Test
	void testIsEmpty() 
	{
		setupEscenario3();
		assertEquals(0, arbolRojoNegro3.size());
	}
	
	/**
	 * Prueba 3: Verifica que el arbol Rojo - Negro se encuentre vacio
	 */
	@Test
	void testIsNotEmpty() 
	{
		setupEscenario1();
        assertTrue(!arbolRojoNegro1.isEmpty());
        
        setupEscenario2();
        assertTrue(!arbolRojoNegro2.isEmpty());
	}

	/**
	 * Prueba 4: Verifica que el arbol Rojo - Negro el valor obtenido de un llave si corresponda con el valor correspondiente
	 */
	@Test
	void testGet() 
	{
		setupEscenario1();
		int valor1 = arbolRojoNegro1.get(1);
		assertEquals(1000, valor1);
		int valor2 = arbolRojoNegro1.get(4);
		assertEquals(4000, valor2);

		setupEscenario2();
		String valor3 = arbolRojoNegro2.get(1);
		assertEquals("Hola", valor3);
		String valor4 = arbolRojoNegro2.get(3);
		assertEquals("O", valor4);
		String valor5 = arbolRojoNegro2.get(6);
		assertEquals("Dia", valor5);
	}

	/**
	 * Prueba 5: Verifica que el arbol Rojo - Negro si contenga ese elemento
	 */
	@Test
	void testContains() 
	{
		setupEscenario1();
		assertEquals(true, arbolRojoNegro1.contains(3));
		assertEquals(false, arbolRojoNegro1.contains(10));
		assertEquals(true, arbolRojoNegro1.contains(1));

		setupEscenario2();
		assertEquals(false, arbolRojoNegro2.contains(9));
		assertEquals(true, arbolRojoNegro2.contains(6));
		assertEquals(false, arbolRojoNegro2.contains(12));
	}

	/**
	 * Prueba 6: Verifica que el arbol Rojo - Negro si se pueda ingresar elementos con la llave y el valor de acuerdo al tipo de dato establecido
	 */
	@Test
	void testPut() 
	{
		setupEscenario1();
		arbolRojoNegro1.put(6, 6000);
		arbolRojoNegro1.put(7, 7000);
		assertEquals(7, arbolRojoNegro1.size());

		setupEscenario2();
		arbolRojoNegro2.put(7, "Cinco");
		arbolRojoNegro2.put(8, "En");
		arbolRojoNegro2.put(9, "El");
		arbolRojoNegro2.put(10, "Taller");
		assertEquals(10, arbolRojoNegro2.size());

		setupEscenario3();
		arbolRojoNegro3.put("Juan", "Cinco Hijos");
		assertEquals(1, arbolRojoNegro3.size());
	}

	/**
	 * Prueba 7: Verifica que el arbol Rojo - Negro si este eliminando el elemento minimo
	 */
	@Test
	void testDeleteMin()
	{
		setupEscenario1();
		arbolRojoNegro1.deleteMin();
		assertEquals(4, arbolRojoNegro1.size());

		setupEscenario2();
		arbolRojoNegro2.deleteMin();
		arbolRojoNegro2.deleteMin();
		assertEquals(4, arbolRojoNegro2.size());
	}

	/**
	 * Prueba 8: Verifica que el arbol Rojo - Negro si este eliminando el elemento maximo
	 */
	@Test
	void testDeleteMax() 
	{
		setupEscenario1();
		arbolRojoNegro1.deleteMax();
		assertEquals(4, arbolRojoNegro1.size());

		setupEscenario2();
		arbolRojoNegro2.deleteMax();
		arbolRojoNegro2.deleteMax();
		assertEquals(4, arbolRojoNegro2.size());
	}

	/**
	 * Prueba 9: Verifica que el arbol Rojo - Negro si este eliminando el elemento indicado por la llave
	 */
	@Test
	void testDelete() 
	{
		setupEscenario1();
		arbolRojoNegro1.delete(1);
		arbolRojoNegro1.delete(3);
		assertEquals(3, arbolRojoNegro1.size());

		setupEscenario2();
		arbolRojoNegro2.delete(1);
		arbolRojoNegro2.delete(3);
		arbolRojoNegro2.delete(6);
		arbolRojoNegro2.delete(2);
		assertEquals(2, arbolRojoNegro2.size());

		setupEscenario3();
		arbolRojoNegro3.put("Juan", "Cinco Hijos");
		arbolRojoNegro3.delete("Juan");
		assertEquals(0, arbolRojoNegro3.size());
	}

	/**
	 * Prueba 10: Verifica que el arbol Rojo - Negro si tenga la altura correcta
	 */
	@Test
	void testHeight() 
	{
		setupEscenario1();
		assertEquals(2, arbolRojoNegro1.height());
		
		setupEscenario2();
		assertEquals(2, arbolRojoNegro2.height());
	}

	/**
	 * Prueba 11: Verifica que el elemento minimo si sea correcto con el que se establecio en el escenario en el arbol Rojo - Negro
	 */
	@Test
	void testMin() 
	{
		setupEscenario1();
		int valor1 = arbolRojoNegro1.min();
		assertEquals(1, valor1);
		
		setupEscenario2();
		int valor2 = arbolRojoNegro2.min();
		assertEquals(1, valor2);
	}

	/**
	 * Prueba 12:Verifica que el elemento maximo si sea correcto con el que se establecio en el escenario en el arbol Rojo - Negro
	 */
	@Test
	void testMax() 
	{
		setupEscenario1();
		int valor1 = arbolRojoNegro1.max();
		assertEquals(5, valor1);
		
		setupEscenario2();
		int valor2 = arbolRojoNegro2.max();
		assertEquals(6, valor2);
	}
}
