package projetoAPIsCarros;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestesAPIsCarros {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3001;
	}


	@Test
	public void criarCarro() {
		given().contentType(ContentType.JSON)
				.body("{\r\n" + " \"marca\": \"Peugeot\",\r\n" + " \"modelo\": \"4008\",\r\n" + " \"ano\": 2022,\r\n"
						+ " \"cor\": \"Branco\",\r\n" + " \"preco\": 25000\r\n" + "}")
				.when().post("/carros").then().statusCode(201)
				.body("id", notNullValue())
				.body("marca", equalTo("Peugeot"))
				.body("modelo", equalTo("4008"))
				.body("ano", equalTo(2022))
				.body("cor", equalTo("Branco"))
				.body("preco", equalTo(25000));;
	}

	@Test
	public void listarTodosOsCarros() {
		given().when().get("/carros").then().statusCode(200);
	}

	@Test
	public void listarUmCarro() {
		given().when().get("/carros/2").then().statusCode(200);
	}

	@Test
	public void atualizarCarro() {
		given().contentType(ContentType.JSON)
				.body("{\r\n" + " \"marca\": \"Renault\",\r\n" + " \"modelo\": \"Megane\",\r\n" + " \"ano\": 2015,\r\n"
						+ " \"cor\": \"Cinza\",\r\n" + " \"preco\": 11500\r\n" + "}")
				.when().put("/carros/4").then().statusCode(200);
	}

	@Test
	public void apagarCarro() {
		String idCarro = "12";
		given().when().delete("/carros/" + idCarro).then().statusCode(200);
		given().when().delete("/carros/" + idCarro) // valida que o carro foi mesmo apagado
				.then().statusCode(404);
	}

	// Validações adicionais

	@Test
	public void pesquisarCarroInexistente() {
		given().when().get("/carros/999").then().statusCode(404).body("mensagem", equalTo("Carro não encontrado"));
	}

	@Test
	public void criarCarroComDadosIncompletos() {
		given().contentType(ContentType.JSON).body("{\r\n" + " \"ano\": 2019,\r\n"
				+ " \"cor\": \"Preto\",\r\n" + " \"preco\": 14000\r\n" + "}").when().post("/carros").then()
				.statusCode(201); // devia dar erro 404, mas ele cria na mesma
	}

	@Test
	public void criarCarroComValoresInválidos() {
		given().contentType(ContentType.JSON)
				.body("{\r\n" + " \"marca\": \"Batatas\",\r\n" + " \"modelo\": \"XPTO2\",\r\n" + " \"ano\": \"dois mil e treze\",\r\n"
						+ " \"cor\": \"&/()\",\r\n" + " \"preco\": Caro\r\n" + "}")
				.when().post("/carros").then().statusCode(400); // devia dar erro 404, mas ele cria na mesma
	}

}
