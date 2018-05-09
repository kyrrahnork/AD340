package com.example.user.ad340app;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.user.ad340app.SharedPreferencesHelper;

@RunWith(MockitoJUnitRunner.class)
public class SharedPrefsTest {

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor ;


    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private String visitorName = "blahckish";

    @Before
    public void initMocks() {

        // Create a mocked SharedPreferences.
        mMockSharedPreferencesHelper = createMockSharedPreference();

    }

    @Test
    public void sharedPreferences_SaveAndReadEntry() {

        // Save the personal information to SharedPreferences
        boolean success = mMockSharedPreferencesHelper.saveEntry(visitorName);

        assertThat("SharedPreferenceEntry.save... returns true",
                success, is(true));

        assertEquals(visitorName, mMockSharedPreferencesHelper.getEntry());

    }

    /**
     * Creates a mocked SharedPreferences object for successful read/write
     */
    private SharedPreferencesHelper createMockSharedPreference() {

        // Mocking reading the SharedPreferences as if mMockSharedPreferences was previously written
        // correctly.
        when(mMockSharedPreferences.getString(eq("visitorName"), anyString()))
                .thenReturn(visitorName);

        // Mocking a successful commit.
        when(mMockEditor.commit()).thenReturn(true);

        // Return the MockEditor when requesting it.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        return new SharedPreferencesHelper(mMockSharedPreferences);
    }
}