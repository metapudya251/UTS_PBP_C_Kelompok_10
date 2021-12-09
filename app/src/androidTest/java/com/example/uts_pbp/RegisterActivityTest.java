package com.example.uts_pbp;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.uts_pbp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void registerActivityTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutUsername),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("dummy"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutEmail),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("dummy"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutPassword),
                                0),
                        1),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("dummy"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton4.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText4 = onView(
                allOf(withText("dummy"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("dummy@gmail"));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        onView(isRoot()).perform(waitFor(3000));

        ViewInteraction textInputEditText7 = onView(
                allOf(withText("dummy@gmail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText7.perform(replaceText("dummy@gmail.com"));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnRegister), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.linearLayout),
                                        childAtPosition(
                                                withId(R.id.mainLayout),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton6.perform(click());

        onView(isRoot()).perform(waitFor(3000));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static ViewAction waitFor (long delay) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for" + delay + "milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }
}
