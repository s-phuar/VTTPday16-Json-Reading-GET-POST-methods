package VTTPday16.workshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTPday16.workshop.service.HttpBinService;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Controller
@RequestMapping
public class HttpBinController {

    @Autowired
    private HttpBinService httpBinSvc;
    
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam MultiValueMap<String, String> form, Model model){
        String query = form.getFirst("query");
        String limit = form.getFirst("limit");
        String rating = form.getFirst("rating");
  
        JsonObject imgJson = httpBinSvc.getImageJson(query, limit, rating);
        // //extract image url from json
        // JsonArray arryObj = imgJson.getJsonArray("data");
        // JsonObject imgObj = arryObj.getJsonObject(1).getJsonObject("image").getJsonObject("fixed_height");
        // String imgSrc = imgObj.getString("url");

        model.addAttribute("url", imgSrc);

        return "image";
    }




    // receive data from index.html form
        //query
            //apt apt apt
        //limit
            //3
        //rating
            //g

    //pass these information to service
        //httpBinSvc.method(query, limit, rating);
        //Svc will create the payload and build request according to the information
            //we need to get api key somehow
        //send the request via RestTemplate
        //return the image src back to controller
            //maybe we receive it as a Json? need to use AppBootStrap to convert to string before passing to controller?
            //probably not
        //cotroller will pass image src to view
    
}
