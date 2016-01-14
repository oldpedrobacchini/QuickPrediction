package com.example.pedrobacchini.quickprediction;

import android.support.test.runner.AndroidJUnit4;

import com.example.pedrobacchini.quickprediction.model.Wind;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class WindTest {

    @Test(expected = IllegalArgumentException.class)
    public void WindWithNegativeSpeed() {
        Wind wind = new Wind(-2,10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WindWithNegativeDag() {
        Wind wind = new Wind(10,-2);
    }

}
