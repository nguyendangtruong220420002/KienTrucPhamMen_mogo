package demo;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.Document;

import org.bson.types.ObjectId;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.reactivestreams.client.MongoDatabase;

import connect.DBConnection;
import dao.RestaurantDAO;
import entity.Address;
import entity.Grade;
import entity.Restaurant;

public class App {
	public static void main(String[] args) throws InterruptedException {
		// Kết nối đến cơ sở dữ liệu MongoDB
		DBConnection connect = new DBConnection();
		// Lấy tham chiếu đến cơ sở dữ liệu
		MongoDatabase db =connect.getInstance().getDatabase();
		// Thực hiện các thao tác với restaurantDAO ở đây
		RestaurantDAO restaurantDAO = new RestaurantDAO(db);
		System.out.println("Connet thanh cong");
	
		
		
		
		 List<Restaurant> restaurants5top = restaurantDAO.getAvg();
		
		// Kiểm tra xem restaurants5top có null không
		 if (restaurants5top != null) {
		     // Nếu danh sách nhà hàng không null, truy cập vào danh sách và thực hiện các thao tác khác
		     for (Restaurant foundRestaurant : restaurants5top) {
		         System.out.println(foundRestaurant);
		     }
		 } else {
		     // Nếu danh sách nhà hàng là null, in ra thông báo
		     System.out.println("Khong co du lieu truy van  aggregate.");
		 }
		  
		 
		 /*
			//them
			Address address = new Address("14511", Arrays.asList(-2.5, 4.0),"North Conduit Avenue", "11436");
			Grade grade = new Grade("2014-12-23", "A", 11);
			List<Grade> grades = Arrays.asList(grade);

			Restaurant restaurant = new Restaurant(null, "12453", "Haha", "Queens","American",address, grades);
			restaurantDAO.addRestaurantd(restaurant);
			((Iterable<Double>) restaurant).forEach(p -> System.out.println(p));
			
			*/
			
			/*
			// tim nha hang
			  List<Restaurant> restaurants = restaurantDAO.getRetaurants("Brooklyn","Jewish/Kosher");
			
		       // In ra danh sách các nhà hàng
			  for (Restaurant foundRestaurant : restaurants) {
		            System.out.println(foundRestaurant);
		        }
			  */

		 
		  // Tìm một nhà hàng theo ID
	        String restaurantId = "1234";
	        Restaurant restaurant = restaurantDAO.find(restaurantId);
	        System.out.println("Restaurant found by ID:");
	        System.out.println(restaurant);

	        // Thêm một nhà hàng mới
	        Restaurant newRestaurant = new Restaurant();
	        newRestaurant.setName("New Restaurant");
	        newRestaurant.setBorough("Manhattan");
	        newRestaurant.setCuisine("Italian");
	        boolean isAdded = restaurantDAO.addRestaurant(newRestaurant);
	        if (isAdded) {
	            System.out.println("New restaurant added successfully.");
	        } else {
	            System.out.println("Failed to add new restaurant.");
	        }

	       
	       
	        // Xóa một nhà hàng theo ID
	        String restaurantToDeleteId = "1234";
	        boolean isDeleted = restaurantDAO.deleteRestaurantById(restaurantToDeleteId);
	        if (isDeleted) {
	            System.out.println("Restaurant with ID " + restaurantToDeleteId + " deleted successfully.");
	        } else {
	            System.out.println("Failed to delete restaurant with ID " + restaurantToDeleteId + ".");
	        }

		System.out.println("Ket Thuc!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
	}


}
