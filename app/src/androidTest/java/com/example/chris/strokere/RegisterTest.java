package com.example.chris.strokere;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.firebase.auth.FirebaseAuth.getInstance;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterTest {

    private FirebaseUser user;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    /**
     * Resgisters a user on the app (Using a randomised email address)
     */
    @Test
    public void registerTest() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(signedIn()) {
            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.Settings), withText("Settings"), isDisplayed()));
            appCompatButton2.perform(click());
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatButton3 = onView(
                    allOf(withId(R.id.btnAccountS), withText("My Account"), isDisplayed()));
            appCompatButton3.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatButton4 = onView(
                    allOf(withId(R.id.logoutBtn), withText("Logout"),
                            withParent(allOf(withId(R.id.hiddenEmail),
                                    withParent(withId(android.R.id.content)))),
                            isDisplayed()));
            appCompatButton4.perform(click());
        }

        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
            StringBuilder sb1 = new StringBuilder();
            Random random1 = new Random();
            for (int i = 0; i < 6; i++) {
                char c1 = chars1[random1.nextInt(chars1.length)];
                sb1.append(c1);
            }
            String random_string = sb1.toString();
            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.registerBtnM), withText("Register"), isDisplayed()));
            appCompatButton.perform(click());


            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.firstNameR), isDisplayed()));
            appCompatEditText2.perform(replaceText("Register"), closeSoftKeyboard());

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.surnameR), isDisplayed()));
            appCompatEditText3.perform(replaceText("Test"), closeSoftKeyboard());

            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.emailR), isDisplayed()));
            appCompatEditText4.perform(replaceText(random_string + "@" + random_string + ".com"), closeSoftKeyboard());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.passR), isDisplayed()));
            appCompatEditText5.perform(replaceText("testing"), closeSoftKeyboard());

            ViewInteraction appCompatEditText6 = onView(
                    allOf(withId(R.id.passConfirmR), isDisplayed()));
            appCompatEditText6.perform(replaceText("testing"), closeSoftKeyboard());

            ViewInteraction appCompatButton2 = onView(
                    allOf(withId(R.id.registerBtnM), withText("REGISTER"), isDisplayed()));
            appCompatButton2.perform(click());

            // Added a sleep statement to match the app's execution delay.
            // The recommended way to handle such scenarios is to use Espresso idling resources:
            // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    /**
     * determines whether a patient is signed into the app
     * @return
     */
    public boolean signedIn()
        {
            if (user != null) {
                return true;
            } else {
                return false;
            }
        }

}
