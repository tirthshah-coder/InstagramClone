package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("aNqtmxf9UXxNZ2YFH0QwjDG5DcGVululpc23NgOu")
                .clientKey("reOkbCvuYimHco8v6OkCzRH522YbLNhyfYzXaEVV")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
