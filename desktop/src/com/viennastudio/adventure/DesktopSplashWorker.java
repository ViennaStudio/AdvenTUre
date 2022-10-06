package com.viennastudio.adventure;

import com.viennastudio.adventure.screens.SplashWorker;

import java.awt.*;

public class DesktopSplashWorker implements SplashWorker {
    @Override
    public void closeSplashScreen() {
        SplashScreen splashScreen = SplashScreen.getSplashScreen();
        if (splashScreen != null) {
            splashScreen.close();
        }
    }
}
