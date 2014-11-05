package com.tutorial.boreas;


import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class MovieView {

    
   private List<MovieDTO> movieList;
    
   public List<MovieDTO> getMovieList() {
       return movieList;
   }
    
   @PostConstruct
   public void init() {
       movieList = new ArrayList<MovieDTO>();
        
       movieList.add(new MovieDTO("The Lord of the Rings: The Two Towers", "Peter Jackson", "Fantasy, Epic", "179"));
       movieList.add(new MovieDTO("The Amazing Spider-Man 2", "Marc Webb", "Action", "142"));
       movieList.add(new MovieDTO("Iron Man 3", "Shane Black", "Action", "109"));
       movieList.add(new MovieDTO("Thor: The Dark World", "Alan Taylor", "Action, Fantasy", "112"));
       movieList.add(new MovieDTO("Avatar", "James Cameron", "Science Fiction", "160"));
       movieList.add(new MovieDTO("The Lord of the Rings: The Fellowship of the Ring", "Peter Jackson", "Fantasy, Epic", "165"));
       movieList.add(new MovieDTO("Divergent", "Neil Burger", "Action", "140"));
       movieList.add(new MovieDTO("The Hobbit: The Desolation of Smaug", "Peter Jackson", "Fantasy", "161"));
       movieList.add(new MovieDTO("Rio 2", "Carlos Saldanha", "Comedy", "101"));
       movieList.add(new MovieDTO("Captain America: The Winter Soldier", "Joe Russo", "Action", "136"));
       movieList.add(new MovieDTO("Fast Five", "Justin Lin", "Action", "132"));
       movieList.add(new MovieDTO("The Lord of the Rings: The Return of the King", "Peter Jackson", "Fantasy, Epic", "200"));
        
   }
  
    public MovieView() {
        // TODO Auto-generated constructor stub
    }

}
