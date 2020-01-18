package com.openclassrooms.realestatemanager;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.activities.main.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ConnectionTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.READ_EXTERNAL_STORAGE");

    @Test
    public void connectionTestAndDBDataShowedWithSucess() {

        //in case of error turn javadoc below to code
        /**
         // Added a sleep statement to match the app's execution delay.
         // The recommended way to handle such scenarios is to use Espresso idling resources:
         // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
         try {
         Thread.sleep(7000);
         } catch (InterruptedException e) {
         e.printStackTrace();
         }

         ViewInteraction loginButton = onView(
         allOf(withId(R.id.login_button), withText("Continuer avec Facebook"),
         childAtPosition(
         childAtPosition(
         withClassName(is("android.widget.LinearLayout")),
         1),
         0),
         isDisplayed()));
         loginButton.perform(click());
         **/


        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //second activity is showed and city text of first house is chalon sur saone
        ViewInteraction textView = onView(
                allOf(withId(R.id.house_location_city), withText("Roubaix"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Roubaix")));
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
}
