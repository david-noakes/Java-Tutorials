package com.tutorial.boreas;

public class MovieDTO {

    private String movie;
    private String directedBy;
    private String genres;
    private String runTime;
    
    
    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public MovieDTO(String title, String director, String genre, String id) {
        super();
        this.movie = title;
        this.directedBy = director;
        this.genres = genre;
        this.runTime = id;
    }

    public MovieDTO() {
        movie = "";
        directedBy = "";
        genres = "";
        runTime = "0";
    }

}
