package nl.landviz.helpers;

public class BreadChannelHelper {
    private static String[] breadChannelIdentifiers =  {"🍞", "bread"};

    public static boolean isBread(String channelName) {
        channelName = channelName.toLowerCase();
        
        boolean isBread = false;

        for (int i = 0; i < breadChannelIdentifiers.length; i++) {
            if (channelName.contains(breadChannelIdentifiers[i])) {
                isBread = true;
                break;
            }
        }

        return isBread;
    }
}
