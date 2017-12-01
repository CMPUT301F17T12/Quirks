package cmput301f17t12.quirks;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import cmput301f17t12.quirks.Activities.AddQuirkActivity;
import cmput301f17t12.quirks.Activities.LoginActivity;
import cmput301f17t12.quirks.Activities.QuirksActivity;
import cmput301f17t12.quirks.Enumerations.Day;
import cmput301f17t12.quirks.Models.Quirk;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by root on 11/13/17.
 */

public class MainActivityTest {
    Quirk Quirktest;
    String quirky;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity loginActivity;

    @Before
    public void initQuirk(){
        loginActivity = mActivityRule.getActivity();
    }

    @Test
    public void addNewEventTest() {
        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intenttesting"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_newevent)).perform(click());
        onView(withId(R.id.comment_edittext)).perform(typeText("intenttesting"), closeSoftKeyboard());
        onView(withId(R.id.save_button)).perform(click());
        Intents.release();
    }

    @Test
    public void FilteringEventbyComment(){
        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intenttesting"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_home)).perform(click());
        onView(withId(R.id.filterValue)).perform(replaceText("intenttesting"),closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("By Comment"))).perform(click());
        onView(withId(R.id.applyFilterButton)).perform(click());
        Intents.release();
    }

    @Test
    public void FilteringEventbyType(){
        Intents.init();
        onView(withId(R.id.loginUser)).perform(typeText("intenttesting"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.action_home)).perform(click());
        onView(withId(R.id.filterValue)).perform(replaceText("Type"),closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("By Type"))).perform(click());
        onView(withId(R.id.applyFilterButton)).perform(click());
        Intents.release();
    }

}
