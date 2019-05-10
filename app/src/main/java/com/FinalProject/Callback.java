package com.FinalProject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Callback {
          void  onLoginCallback(boolean isLoggedIn, boolean isAdmin, long userId){};
          void  shiftsCallback(ArrayList<Parent> shifts){};
          void  minDate(String minDate){};
          void  maxDate(String maxDate){};
          void  shiftGeneratedKey(String key){};

}
