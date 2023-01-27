import io.restassured.specification.RequestSpecification;
import org.example.StringGenerator;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TrelloTest {

    String url = "https://api.trello.com";
    private static String key = "0fa07573fccbd796566da4f79787fb34";
    private static String token = "ATTA29d7900bbb9996ffa0c26205ad9e06b0fe83fed0dba6b2a0e1f0b0ab0406d203615EB261";

    private static String boardName;
    private static String boardId;
    private static String cardName;
    private static String listId;
    private static String cardId;

    public RequestSpecification requestSpecification(){
        return given()
                .baseUri(url)
                .queryParam("token", "ATTA29d7900bbb9996ffa0c26205ad9e06b0fe83fed0dba6b2a0e1f0b0ab0406d203615EB261")
                .queryParam("key", "0fa07573fccbd796566da4f79787fb34");
    }
    @Test
    public void createNewBoard() {
        boardName = StringGenerator.randomStringCreator();
        boardId = given().
                header("Content-Type", "application/json").
                header("Accept", "application/json").
                queryParam("key", key).
                queryParam("token", token).
                body("{\"name\":\"" + boardName +   "\"}").
        when().
                post("https://api.trello.com/1/boards/").
        then().
                statusCode(200).
        extract().
                response().getBody().jsonPath().getString("id");

        System.out.println(boardId);
        createList(boardId);
    }

    public void createList(String boardId){
        listId = given().
                    header("Content-Type", "application/json").
                    header("Accept", "application/json").
                    queryParam("key", key).
                    queryParam("token", token).
                    body("{\"name\":\"" + "Test List" + "\",\"idBoard\":\""+boardId+"\"}").
                when().
                    post("https://api.trello.com/1/lists").
                then().
                    statusCode(200).
                extract().
                    response().getBody().jsonPath().getString("id");
    }



    @Test
    public void createNewCard() {
        cardName = StringGenerator.randomStringCreator();
        System.out.println(listId);
        given().
                header("Content-Type", "application/json").
                header("Accept", "application/json").
                queryParam("key", key).
                queryParam("token", token).
                body("{\"name\":\"" + cardName + "\",\"idList\":\""+ this.listId +"\"}").
        when().
                post("https://api.trello.com/1/cards/").
        then().
                statusCode(200);

    }

    @Test
    public void createSecondCard() {
        cardName = StringGenerator.randomStringCreator();
        cardId = given().
                header("Content-Type", "application/json").
                header("Accept", "application/json").
                queryParam("key", key).
                queryParam("token", token).
        body("{\"name\":\"" + cardName + "\",\"idList\":\""+ this.listId +"\"}").
                when().
                post("https://api.trello.com/1/cards/").
        then().
                statusCode(200).
        extract().
                response().getBody().jsonPath().getString("id");

    }

    @Test
    public void deleteSecondaryCard(){
        given().
                header("Content-Type", "application/json").
                header("Accept", "application/json").
                queryParam("key", key).
                queryParam("token", token).
                //body("{\"id\":\"" + "63d45c2772efed7ce34ba8c5" + "\"}").
        when().
                delete("https://api.trello.com/1/cards/"+cardId).
        then().
                statusCode(200);

    }

    @Test
    public void deleteBoard(){
        given().
                header("Content-Type", "application/json").
                header("Accept", "application/json").
                queryParam("key", key).
                queryParam("token", token).
                //body("{\"id\":\"" + "63d45c2772efed7ce34ba8c5" + "\"}").
                        when().
                delete("https://api.trello.com/1/boards/"+boardId).
                then().
                statusCode(200);

    }

}
