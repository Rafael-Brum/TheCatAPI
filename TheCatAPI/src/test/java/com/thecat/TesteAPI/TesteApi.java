package com.thecat.TesteAPI;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
public class TesteApi extends MassaDeDados {



	@BeforeClass
	public static void urlBase() {
		RestAssured.baseURI = "https://api.thecatapi.com/v1/";
	}

	@Test
	public void cadastro() {

		//Dado que
		Response response = given().contentType("application/json").body(corpoCadastro).
				//Quando
				when().post(urlCadastro);
		
		validacao(response);

	}

	@Test
	public void votacao() {
		//Dado que
		Response response = 
				given().contentType("application/json")
				.body(corpoVotacao).
				//Quando
				when().post("votes/");
		
		validacao(response);
		
		String id = response.jsonPath().getString("id");
		System.out.println("ID => " + id);
		vote_id = id;
	}

	@Test
	public void deletaVotacao() {
		votacao();
		deletaVoto();
	}

	private void deletaVoto() {
		String url = "votes/{vode_id}";

		//Dado que
		Response response = 
				given().contentType("application/json").
				header("x-api-key","f0ff4554-72ec-4b5e-acc6-1b1a4f37bf53").
				pathParam("vote_id", vote_id).
				//Quando
				when().delete(url);
		
		validacao(response);

	}

	@Test
	public void executaFavoritaDeletar() {
		favoritar();
		deletarFavorito();
	}

	private void favoritar() {
		String url = "favourites";

		//Dado que
		Response response = 
				given().contentType("application/json").
				header("x-api-key","f0ff4554-72ec-4b5e-acc6-1b1a4f37bf53").
				body(corpoFavorita).
				//Quando
				when().post(url);
		
		validacao(response);
		String id = response.jsonPath().getString("id");
		System.out.println("ID => " + id);
		favourite_id = id;

	}

	private void deletarFavorito() {
		String url = "favourites/{favourite_id}";

		//Dado que
		Response response = 
				given().contentType("application/json").
				header("x-api-key","f0ff4554-72ec-4b5e-acc6-1b1a4f37bf53").
				pathParam("favourite_id", favourite_id).
				//Quando
				when().delete(url);

		validacao(response);
	}

	public void validacao(Response response) {
		//Exibir
		System.out.println("RETORNO API => " + response.body().asString());
		//Então
		response.then().statusCode(200).body("message", containsString("SUCCESS"));
		System.out.println("-----------------------------------------------------------");
	}

}
