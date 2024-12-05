package VTTPday16.workshop.service;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTPday16.workshop.models.SearchParams;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

//go the giphy developers
//using your api, search and display gifs according to search term and quantity

@Service
public class HttpBinService {
    
    //looking for image url , retrieve via landing page query GET method
    public static final String GET_URL = "https://api.giphy.com/v1/gifs/search";

    //how do we set API key here?
        //set using railway environment
   @Value("${giphy.api-key}")
   private String apiKey;

    // https://api.giphy.com/v1/gifs/search?
    //       api_key=abc123&
    //       q=polar bear&
    //       limit=3

    public List<String> search(SearchParams params){
        //build the url  with query param 
        String url = UriComponentsBuilder  
                .fromUriString(GET_URL)
                .queryParam("api_key", apiKey)
                .queryParam("q", params.query()) //using the new SearchParams record return class
                .queryParam("limit", params.limit())
                .queryParam("rating", params.rating())
                .toUriString();

        //consturct the request, payload will hold nothing
        RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        try{
            //create template for sending request
            RestTemplate template = new RestTemplate();
            //consider the below
                //String payload = template.getForObject(url, String.class);
            //call the URL
            ResponseEntity<String> resp = template.exchange(req, String.class);
            //get the payload
            String payload = resp.getBody();
            return getImages(payload);



        }catch (Exception ex){
            ex.printStackTrace();
            return List.of(); //returns an immutable empty List, cannot add/remove/modify elements in List after creation
        }
}

    private List<String> getImages(String json){ //insert payload body (String) and read it as Json
        //parse into json reader
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject result = reader.readObject();
        //new
        JsonArray data = result.getJsonArray("data"); //grab the array value under data key

        List<String> images = new LinkedList<>();
        for(int i = 0; i < data.size(); i++){
            JsonObject imgData = data.getJsonObject(i); //isolate and return each object in the array
            //for each object in the array, grab object images, object fixed_height, string url
            String imageUrl = imgData.getJsonObject("images") //isolate and return object value associated with images
                    .getJsonObject("fixed_height") //isolate the return object value associated with fixed_height
                    .getString("url");

            images.add(imageUrl);
        }
        return images; //return List of image urls
    }



}
