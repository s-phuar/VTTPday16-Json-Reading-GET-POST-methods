package VTTPday16.workshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import VTTPday16.workshop.models.SearchParams;
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

        SearchParams params = new SearchParams(form.getFirst("query"),
                Integer.parseInt(form.getFirst("limit")),
                form.getFirst("rating"));

        List<String> images = httpBinSvc.search(params);

        model.addAttribute("query", params.query());
        model.addAttribute("images", images);

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
        //httpBinSvc.search(query, limit, rating);
        //Svc will create the payload and build request according to the information
            //using api key 
        //send the request via RestTemplate
        //return the List of image urls back to controller
        //cotroller will pass image src to view
    
}
