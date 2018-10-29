package com.olamide.mybakingapp;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.olamide.mybakingapp.R;
import com.olamide.mybakingapp.activity.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BasicBakingAppTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    /**
     * This test will check if a random recipe is clickable
     */
    @Test
    public void mainActivityBasicTest() throws InterruptedException
    {

        Thread.sleep(7000);
        onView(withId(R.id.rv_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(getRandom(), click()));

//        onView(withId(R.id.tv_ingredient)).check(matches(withId(R.id.tv_ingredient)));
    }




    /**
     * This test will check if the ingredients text view is clickable
     */
    @Test
    public void ingredientsAndStepsActivityBasicTest() throws InterruptedException {

        mainActivityBasicTest();
        onView(withId(R.id.tv_ingredient)).check(matches(withId(R.id.tv_ingredient)));
        onView(withId(R.id.tv_ingredient)).perform(click());

    }

    /**
     * This test will check if the first recipe step is clickable
     */
    @Test
    public void StepsFragmentBasicTest() throws InterruptedException {

        mainActivityBasicTest();
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }


    public int  getRandom()
    {
        Random rand = new Random();
        return rand.nextInt(3);
    }



}

