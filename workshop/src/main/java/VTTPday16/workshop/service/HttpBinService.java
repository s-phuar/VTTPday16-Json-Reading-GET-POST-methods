package VTTPday16.workshop.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class HttpBinService {
    
    //looking for image url , retrieve via landing page query GET method
    public static final String GET_URL = "https://api.giphy.com/v1/gifs/search";
    public static final String API_KEY =  "EfVCfprodIZ47Hfw6ff49vnaF0E61BeM";


    public JsonObject getImageJson(String query, String limit, String rating){
    //build the url  with query param 
    String url = UriComponentsBuilder  
            .fromUriString(GET_URL)
            .queryParam("api_key", API_KEY)
            .queryParam("q", query)
            .queryParam("limit", limit)
            .queryParam("rating", rating)
            .toUriString();

    //consturct the request, payload will hold nothing
    RequestEntity<Void> req = RequestEntity
            .get(url)
            .accept(MediaType.APPLICATION_JSON)
            .build();

    try{
        //create template for sending request
        RestTemplate template = new RestTemplate();
        //call the URL
        ResponseEntity<String> resp = null;

        resp = template.exchange(req, String.class);
        String payload = resp.getBody();

        //parse into json reader
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        //new
        JsonArray data = result.getJsonArray("data");
        List<String> images = new LinkedList<>();
        for(int i = 0; i < data.size(); i++){
            JsonObject imgData = data.getJsonObject(i);
            String imageUrl = imgData.getJsonObject("images").getJsonObject("fixed_height").getString("url");
            images.add(imageUrl);
        }

        return result;


    }catch (Exception ex){
        ex.printStackTrace();
    }

    return null;

    }

    // private List<String> getImages(String Json){

    // }



}
