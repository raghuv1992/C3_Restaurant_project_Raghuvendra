import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);


    @BeforeEach
    public void setup()
    {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("10:30:00");
        LocalTime currentTime1 = LocalTime.parse("12:30:00");
        LocalTime currentTime2 = LocalTime.parse("21:59:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime,currentTime1,currentTime2);
        assertTrue(spiedRestaurant.isRestaurantOpen());
        assertTrue(spiedRestaurant.isRestaurantOpen());
        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currentTime = LocalTime.parse("10:00:00");
        LocalTime currentTime1 = LocalTime.parse("22:00:00");
        LocalTime currentTime2 = LocalTime.parse("23:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime,currentTime1,currentTime2);
        assertFalse(spiedRestaurant.isRestaurantOpen());
        assertFalse(spiedRestaurant.isRestaurantOpen());
        assertFalse(spiedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>TDD way Total order Cost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void given_name_of_items_should_return_order_value()
    {
        List<String> items = new ArrayList<String>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");
        double order_value = Restaurant.getOrderValue(items);
        assertTrue(order_value > 0);
        assertEquals(388.00,order_value);
    }

    //<<<<<<<<<<<<<<<<<<<<TDD way Total order Cost>>>>>>>>>>>>>>>>>>>>>>>>>>
}