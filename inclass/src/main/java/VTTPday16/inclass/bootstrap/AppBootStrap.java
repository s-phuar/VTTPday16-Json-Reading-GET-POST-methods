package VTTPday16.inclass.bootstrap;

import java.io.Reader;
import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import VTTPday16.inclass.service.HttpbinService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

@Component
public class AppBootStrap implements CommandLineRunner{ // CommandLineRunner will run this first before application starts

    @Autowired
    private HttpbinService httpBinSvc;


    //... multi-line string
    @Override
    public void run(String... args){ 

        //testing Httpbinservice
        // httpBinSvc.get();
        // httpBinSvc.getJokes();
        // httpBinSvc.getWithQueryParams();
        // httpBinSvc.postForm();
        httpBinSvc.postJson();


//         //Example json text, we will be reading and writing Json
//             // String fred = """
//             // {
//             //     "name":"fred",
//             //     "email":"fred@gmail.com",
//             //     "age": 50,
//             //     "married": true,
//             //     "address": {
//             //         "street" : "1 bedrock ave",
//             //         "postcode" : "abc123"
//             //         },
//             //     "hobbies": ["skiing", "reading", "jogging"]
//             // }           
//             // """;

//         JsonObjectBuilder objectBuilder = Json.createObjectBuilder(); //returns an object builder object when called
//         objectBuilder = objectBuilder //chaining
//                         .add("name", "fred")
//                         .add("email", "fred@gmail.com")
//                         .add("age", 50)
//                         .add("married", true); //do not add build() here

//         JsonObject address = Json.createObjectBuilder()
//                         .add("street", "1 bedrock ave")
//                         .add("postcode", "abcd1234")
//                         .build();    //Json object is immutable, once built cannot be changed for security reasons


//         // objectBuilder = objectBuilder.add("addr", addr); if we want to add immediately


//         JsonArray hobbies = Json.createArrayBuilder()
//                         .add("skiiing")
//                         .add("jogging")
//                         .add("reading")
//                         .build();

//         // objectBuilder = objectBuilder.add("hobbies", hobbies); if we want to add immediately


//         JsonObject fred = objectBuilder
//                         .add("address", address) //add immutable address
//                         .add("hobbies", hobbies) //add immutable hobbies
//                         .build(); //finally build fred object


//         // JsonObject -> String
//         System.out.printf("Fred as JSON: \n%s\n", fred.toString());

//         System.out.printf("Name: %s\n", fred.getString("name"));
//         System.out.printf("Age: %d\n", fred.getInt("age")); 
//         JsonObject tmpObj = fred.getJsonObject("address");
//         System.out.printf("Street: %s\n", tmpObj.getString("street"));
//         JsonArray tmpArr = fred.getJsonArray("hobbies");
//         System.out.printf("Hobbies[1]: %s\n", tmpArr.getString(1));

//         // String -> JsonObject
//         String data = """ //sample string
//             {
//                 "name": "barney",
//                 "email": "barney@gmail.com",
//                 "age": 50, 
//                 "married": true,
//                 "address": {
//                 "street": "1 bedrock ave",
//                 "postcode": "abc123"
//                 },
//                 "hobbies": [ "skiing", "reading", "jogging" ]
//             }
//         """;

//         Reader reader = new StringReader(data);
//         JsonReader jsonReader = Json.createReader(reader);
//         JsonObject barney = jsonReader.readObject();

//         System.out.printf(">>> barney\n\t:%s\n", barney.toString());

//         String arrData = "[ 123, 245, 789 ]";
//         reader = new StringReader(arrData);
//         jsonReader = Json.createReader(reader);
        
//         JsonArray numList = jsonReader.readArray();
//         for (int i = 0; i < numList.size(); i++)
//             System.out.printf("%d: %d\n", i, numList.getInt(i));





    }

    
}
