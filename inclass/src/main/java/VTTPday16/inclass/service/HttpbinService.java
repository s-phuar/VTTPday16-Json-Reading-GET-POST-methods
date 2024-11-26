package VTTPday16.inclass.service;

import java.io.StringReader;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;



//content-type: the mediatype we are sending
//accept: the meidatype we are accepting

@Service
public class HttpbinService {
    
    public static final String GET_URL = "https://httpbin.org/get";
    public static final String JOKES_URL = "https://official-joke-api.appspot.com/jokes/ten";
    public static final String POST_URL = "https://httpbin.org/post";

    public void getJokes(){
        //build(configure) the request
            //GET /jokes/ten
            //Accet: application/json
        RequestEntity<Void> req = RequestEntity //whats in the request body? Void!
                .get(JOKES_URL) //if we want to add on stuff on top of JOKES_URL, use UriStringBuilder
                .accept(MediaType.APPLICATION_JSON)
                .build();
                
        //create RestTemplate for sending request
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try{
            //make the call
            resp = template.exchange(req,String.class);
            //extract the payload from body
            String payload = resp.getBody();
            JsonReader reader = Json.createReader(new StringReader(payload));
            JsonArray result = reader.readArray();

            //loop through and print out setup and punchline
            for(int i = 0; i < result.size(); i++){
                JsonObject joke = result.getJsonObject(i);
                System.out.printf("SETUP: $s\nPUNCHLINE: %s\n\n"
                        , joke.getString("setup"), joke.getString("punchline"));
                }
        }catch(Exception ex){
            //handle error
            ex.printStackTrace();
        }
    }


    public void getWithQueryParams(){

        String url = UriComponentsBuilder  
                .fromUriString(GET_URL)
                .queryParam("name", "fred") //hardcoded with fred
                .queryParam("email", "fred@gmail.com") //hardcoded with fred@gmail.com
                .toUriString();

        System.out.printf("URL with query params: \n\t%s\n", url);

        RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();
    }

    public void postJson(){
        //if we want to send Json payload via HTTP POST, to POST_URL

        //creating Json payload
        JsonObject json = Json.createObjectBuilder()
                .add("name", "apple")
                .add("quantity", 10)
                .build();

        //building request for Json payload
        RequestEntity<String> req = RequestEntity
                .post(POST_URL) //specifies this is a POST request and sets the target url
                //intechangeable
                    // .contentType(MediaType.APPLICATION_JSON)
                    // .accept(MediaType.APPLICATION_JSON)
                .header("Accept", "application/json") //we are expecting a Json response
                .header("Content-Type", "application/json") //the body of our request contains Json
                .body(json.toString(), String.class); //convert Json into Json string repsentation
        
        //sending the request
        RestTemplate template = new RestTemplate();
        try {
            //we send 'req', and expected to receive 'resp' as String.class
            ResponseEntity<String> resp = template.exchange(req, String.class); 
            String payload = resp.getBody();
            System.out.printf(">> %s\n", payload); // the response payload will be printed
         } catch (Exception ex) {
            ex.printStackTrace();
         }

    }

    public void postForm(){
        //2. if we want to send payload with only strings, create the form
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("name", "apple");
        form.add("quantity", "%d".formatted(3));

        //create a request
        RequestEntity<MultiValueMap<String, String>> req = RequestEntity
                .post(POST_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(form, MultiValueMap.class); //don't need build

        //sending the request
        RestTemplate template = new RestTemplate();
        try{
            ResponseEntity<String> resp = template.exchange(req, String.class);
            String payload = resp.getBody(); //payload is in the form dictated under .accept above
            System.out.printf(">> %s\n", payload);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public JsonObject get(){

        //Configure request
            //perform .get to /get
            //Accept: application/json
            //X-Myheader: random uuid
        RequestEntity<Void> req = RequestEntity
                .get(GET_URL) //making a request
                .accept(MediaType.APPLICATION_JSON) //media type
                .header("X-UUID", UUID.randomUUID().toString())
                .build();

        //new instance of RestTemplate
        RestTemplate template = new RestTemplate();
        
        //make call to the URL
        ResponseEntity<String> resp = null;

        //200 - 300 OK
        //400, 500 - exception
        //very imporatnt to use try catch block
        try{
        resp = template.exchange(req, String.class);
        String payload = resp.getBody();

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        
        JsonObject headers = result.getJsonObject("headers");

        System.out.printf(">>> header: %s\n", headers.toString());
        System.out.printf(">>> X-UUID: %s\n", headers.getString("X-Uuid")); //check response(?), its uses lowercase
        

        // System.out.printf(">>> payload \n\t%s\n", payload);

        return headers;

        }catch (Exception e){
            System.out.printf(">>> error: %s\n", e.getMessage());
        }

        return null;
    }

}
