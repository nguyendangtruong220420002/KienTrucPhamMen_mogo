//package test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import com.mongodb.reactivestreams.client.MongoDatabase;
//
//import dao.RestaurantDAO;
//import entities.Product;
//import entity.Restaurant;
//import utils.DBConnection;
//
//public class RestaurantTest {
//private static RestaurantDAO restaurantDAO;
//
//@BeforeAll
//static void setup() {
//	DBConnection connection = new DBConnection();
//	MongoDatabase database = connection.getInstance().getDatabase();
//	restaurantDAO= new RestaurantDAO(database);
//}
//
////@Test
////void find() {
////	String Restaurasnnt_id = "1234";
////	Restaurant restaurant = restaurantDAO.find("1234");		
////	assertEquals(355L, restaurant.getProductId());
////}
//
////@Test
////void addProduct() {
////	Product product = new Product(325l, "Brand name 325", "Category name 325", "Product Name 325" , Arrays.asList("yellow"),2022, 11999.99d);
////	boolean result = productDAO.addProduct(product);
////	assertEquals(true, result);
////}
//
