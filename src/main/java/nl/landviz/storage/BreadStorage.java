package nl.landviz.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class BreadStorage {
    private static BreadStorage breadStorage = new BreadStorage();

    private Map<String, GuildBreadStorage> data = new HashMap<>();

    private BreadStorage() {

    }

    public void loadBread(String storageDir) {
        File folder = new File(storageDir);
        File[] scores = folder.listFiles();

        for (File guildScore : scores) {
            try {
                String fileContents = "";
                Scanner reader = new Scanner(guildScore);

                while(reader.hasNextLine()) {
                    fileContents += reader.nextLine();
                }

                reader.close();

                JSONObject obj = new JSONObject(fileContents);

                String fileName = guildScore.getName();
                String guildId = fileName.substring(0, fileName.length() - 5);

                this.data.put(guildId, new GuildBreadStorage(obj));
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to read file " + guildScore.getAbsolutePath());
            }
        }
        
    }

    public void saveBread(String storageDir) {
        for (Map.Entry<String, GuildBreadStorage> entry : this.data.entrySet()) {
            String filePath = storageDir + "/" + entry.getKey() + ".json";
            File file = new File(filePath);

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter writer = new FileWriter(filePath);
                writer.write(entry.getValue().toJson().toString());
                writer.close();
            } catch (IOException exception) {
                System.out.println("Unable to write to file " + filePath);
            }
        }
    }

    public static BreadStorage getInstance() {
        return breadStorage;
    }

    private void createGuildStorage(String guildId) {
        this.data.put(guildId, new GuildBreadStorage());
    }

    public void modifyBread(String guildId, String userId, int amount) {
        if (!this.data.containsKey(guildId)) {
            this.createGuildStorage(guildId);
        }

        this.data.get(guildId).modifyBread(userId, amount);
    }

    public int getBread(String guildId, String userId) {
        if (!this.data.containsKey(guildId)) {
            return 0;
        }
        
        return this.data.get(guildId).getBreadAmount(userId);
    }

    public String[] getTopList(String guildId) {
        return this.data.get(guildId).getTopList();
    }
}
