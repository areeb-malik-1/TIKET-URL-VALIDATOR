package com.tiket.testbase;

import com.tiket.model.Environment;
import com.tiket.model.Platform;

public class Config {
    public static final Platform PLATFORM = Platform.valueOf(System.getProperty("platform"));
    public static final Environment ENV = Environment.PROD;
}
