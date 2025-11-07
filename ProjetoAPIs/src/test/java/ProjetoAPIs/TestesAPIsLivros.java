package ProjetoAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TestesAPIsLivros {

@BeforeClass
public static void setup() {
RestAssured.baseURI = "http://localhost";
RestAssured.port = 3000;
}

@Test
public void criarLivro() {

given()
.contentType(ContentType.JSON)
.body("{\r\n"
+ " \"nome\": \"Verity\",\r\n"
+ " \"autor\": \"Collen Hoover\",\r\n"
+ " \"paginas\": 321\r\n"
+ "}")
.when()
.post("/livros")
.then()
.statusCode(201);

}

@Test
public void listarTodosOsLivros() {

given()
.when()
.get("/livros")
.then()
.statusCode(200);

}
@Test
public void atualizarLivro() {

given()
.contentType(ContentType.JSON)
.body("{\r\n"
+ " \"nome\": \"livro teste\",\r\n"
+ " \"autor\": \"Paulo Coelho\",\r\n"
+ " \"paginas\": 208\r\n"
+ "}")
.when()
.put("/livros/1")
.then()
.statusCode(200);

}
@Test
public void deletarLivro() {
String idLivro ="1";
given()
.when()
.delete("/livros/" + idLivro)
.then()
.statusCode(204);
given()
.when()
.delete("/livros/" + idLivro)
.then()
.statusCode(404);

}
}
