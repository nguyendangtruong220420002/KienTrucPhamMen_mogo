package dao;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import entity.Restaurant;


public class RestaurantDAO {
 @SuppressWarnings("unused")
private MongoCollection<Restaurant> restaurantCollection;
 
	public RestaurantDAO(MongoDatabase database) {
		// TODO Auto-generated constructor stub
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		this.restaurantCollection = database.getCollection("Restaurants", Restaurant.class).withCodecRegistry(pojoCodecRegistry);
		

//		// Tạo chỉ mục văn bản cho trường "borough" và "cuisine"
//				Document index = new Document()
//				    .append("borough", "text")
//				    .append("cuisine", "text");
//
//				IndexOptions indexOptions = new IndexOptions()
//				    .name("borough_cuisine_text_index");
//
//				restaurantCollection.createIndex(index, indexOptions);
	}
	//db.restaurants.aggregate([{ $unwind: "$grades" },{ $group: { _id: "$restaurant_id", avgScore: { $avg: "$grades.score" }}}, { $sort: { avgScore: -1 } },{ $limit: 5 }])
    public List<Restaurant> getAvg(){
    	AtomicReference<List<Restaurant>> alr = new AtomicReference<>();
    	CountDownLatch latch = new CountDownLatch(1);
    	AggregatePublisher<Restaurant> publisher = restaurantCollection.aggregate(Arrays.asList(
    			Aggregates.unwind("$grades"),
    			Aggregates.group("$restaurant_id", Accumulators.avg("avgScore", "$grades.score")),
    			Aggregates.sort(Sorts.descending("avgScore")),
    			Aggregates.limit(5)), Restaurant.class);
    	
    	publisher.subscribe(new Subscriber<Restaurant>() {
    		
			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				s.request(Integer.MAX_VALUE);
			}

			@Override
			public void onNext(Restaurant t) {
				// TODO Auto-generated method stub
				alr.get().add(t);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
                latch.countDown();
				System.err.println("Đa xay ra loi: " + t.getMessage());
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				latch.countDown();
				System.out.println("AVG thanh cong");
			}
		});
    	 try {
    	        latch.await(); // Đợi cho tất cả các dữ liệu được xử lý
    	    } catch (InterruptedException e) {
    	        e.printStackTrace();
    	    }

    	    return alr.get();
		
    }
    public Restaurant find(String id) {
		AtomicReference<Restaurant> result = new AtomicReference<>();
		CountDownLatch latch = new CountDownLatch(1);
		Publisher<Restaurant> publisher = restaurantCollection.find(Filters.eq("_id", id)).first();
		Subscriber<Restaurant> subscriber = new Subscriber<Restaurant>() {

			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				s.request(1);
			}

			@Override
			public void onNext(Restaurant t) {
				// TODO Auto-generated method stub
				result.set(t);
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				latch.countDown();
			}
		};
		publisher.subscribe(subscriber);
		try {
			latch.await();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result.get();
		
	}
    public boolean deleteRestaurantById(String id) {
        final AtomicBoolean result = new AtomicBoolean(false);
        final CountDownLatch latch = new CountDownLatch(1);

        Publisher<DeleteResult> pub = restaurantCollection.deleteOne(Filters.eq("_id", id));

        Subscriber<DeleteResult> sub = new Subscriber<DeleteResult>() {
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            public void onNext(DeleteResult t) {
                result.set(t.getDeletedCount() > 0);
            }

            public void onError(Throwable t) {
                t.printStackTrace();
            }

            public void onComplete() {
                latch.countDown();
            }
        };

        pub.subscribe(sub);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

    public boolean addRestaurant(Restaurant restaurant) {
		AtomicBoolean result = new AtomicBoolean(false);
		CountDownLatch latch = new CountDownLatch(1);
		Publisher<InsertOneResult> publisher = restaurantCollection.insertOne(restaurant);
		Subscriber<InsertOneResult> subscriber = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				// TODO Auto-generated method stub
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
				// TODO Auto-generated method stub
				System.out.println("Restauran : " + t.getInsertedId());
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stub
				System.out.println("Completed");
				latch.countDown();
			}
		};
		publisher.subscribe(subscriber);
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result.get();
		
	}
  //db.Restaurants.find({ borough: 'Brooklyn',cuisine: 'Jewish/Kosher'})
  	public List<Restaurant> getRetaurants(String borough, String cuisine) {
  		// TODO Auto-generated method stub
  		List<Restaurant> restaurants = new ArrayList<>();
  		AtomicBoolean err = new AtomicBoolean(false);
  		CountDownLatch latch = new CountDownLatch(1);
  		// Tạo một bộ lọc văn bản từ borough và cuisine
  		Bson filter = Filters.and(
  				Filters.eq("borough", borough),
  				Filters.eq("cuisine", cuisine)
  				);
  		
  		Publisher<Restaurant> publisher = restaurantCollection.find(filter);		// Truy vấn cơ sở dữ liệu với bộ lọc văn bản
  		// Xử lý kết quả trả về từ cơ sở dữ liệu
  		Subscriber<Restaurant> subscriber  = new Subscriber<Restaurant>() {
  			@Override
  			public void onSubscribe(Subscription s) {
  				// TODO Auto-generated method stub
  				s.request(Integer.MAX_VALUE);
  			}

  			@Override
  			public void onNext(Restaurant t) {
  				// TODO Auto-generated method stub
  				restaurants.add(t);
  			}

  			@Override
  			public void onError(Throwable t) {
  				// TODO Auto-generated method stub
  				err.set(true);
  				latch.countDown();
  				 System.err.println("Đa xay ra loi: " + t.getMessage());
  			}

  			@Override
  			public void onComplete() {
  				// TODO Auto-generated method stub
  				latch.countDown();
  				System.out.println("Tim Co trong mongodb");
  				
  			}
  		};
  		publisher.subscribe(subscriber);	// Đăng ký Subscriber và chờ kết quả
  		try {
  			latch.await();
  		} catch (InterruptedException e) {
  			// TODO: handle exception
  			e.printStackTrace();
  		}
  		// Nếu có lỗi, ném ngoại lệ
  		if(err.get()) {
  			throw new RuntimeException(" Khong tìm thay khi nap vao ");
  		}
  		return restaurants;
  	}



}
