package org.example.btl.game;

public class SkinManager {
    private static int skinIndex = 0;

    public static int getSkinIndex() {
        return skinIndex;
    }

    public static void setSkinIndex(int skinIndex) {
        SkinManager.skinIndex = skinIndex;
    }
}
