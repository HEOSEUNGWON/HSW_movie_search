package com.example.searchactivity;

import java.net.URL;

public class MovieData {
    private String poster;
    private String movieName;
    private double grade;

    private String release;


    private String movieURL;

    public MovieData(String poster, String movieName, double grade, String release, String movieURL){
        this.poster = poster;
        this.movieName = movieName;
        this.release = release;
        this.grade = grade;
        this.movieURL = movieURL;
    }

    public String getPoster()
    {
        return this.poster;
    }

    public String getMovieName()
    {
        return this.movieName;
    }

    public double getGrade()
    {
        return this.grade;
    }

    public String getRelease()
    {
        return this.release;
    }

    public String getMovieURL()
    {
        return this.movieURL;
    }
}
