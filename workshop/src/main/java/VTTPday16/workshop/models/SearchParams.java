package VTTPday16.workshop.models;


//record defines an immutable data class, fields cannot be changed
//automatically provides useful methods like toString,equals,hashCode,constuctor
//no need for getters
public record SearchParams (String query, int limit, String rating) {
    
}
