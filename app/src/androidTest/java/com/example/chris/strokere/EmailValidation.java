package com.example.chris.strokere;


import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidation {



    //email validation used in account/registering pages
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(emailValidator("name@email.com"));
    }

}
