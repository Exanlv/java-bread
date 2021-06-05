package nl.landviz.helpers;

import java.math.BigInteger;

public class IsFrenchHelper {
    public static boolean isFrench(String nickname) {
        return nickname.contains("🇫🇷");
    }

    public static String getUserGuid(long guildId, long userId) {
        return String.valueOf(
            new BigInteger(
                Long.toBinaryString(guildId) + Long.toBinaryString(userId),
                2
            )
        );
    }
}
