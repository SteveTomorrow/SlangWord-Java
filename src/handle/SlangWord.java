package handle;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class SlangWord {
    private TreeMap<String, List<String>> map = new TreeMap<>();
    private static SlangWord obj = new SlangWord();// Early, instance will be created at load time
    private int sizeMap;
    private String FILE_SLANGWORD = "slang.txt";
    private String FILE_ORIGINAL_SLANGWORD = "slangorigin.txt";
    private String FILE_HISTORY = "history.txt";

    private SlangWord() {
        try {
            String current = new File(".").getCanonicalPath();
            System.out.println("Current dir:" + current);
            FILE_SLANGWORD = current + "\\" + FILE_SLANGWORD;
            FILE_ORIGINAL_SLANGWORD = current + "\\" + FILE_ORIGINAL_SLANGWORD;
            FILE_HISTORY = current + "\\" + FILE_HISTORY;
            readFile(FILE_SLANGWORD);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static SlangWord getInstance() {
        if (obj == null) {
            synchronized (SlangWord.class) {
                if (obj == null) {
                    obj = new SlangWord();// instance will be created at request time
                }
            }
        }
        return obj;
    }

    void resetHistory (String file){
        try {
            PrintWriter printWriter = new PrintWriter(new File(file));
            StringBuilder stringBuilder = new StringBuilder();
            String s[][] = new String[map.size()][3];
            // System.out.println(stringBuilder.toString());
            printWriter.write(stringBuilder.toString());
            printWriter.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
    void saveFile(String file) {
        try {
            PrintWriter printWriter = new PrintWriter(new File(file));
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("Slang`Definition\n");
            String s[][] = new String[map.size()][3];
            Set<String> keySet = map.keySet();
            Object[] keyArray = keySet.toArray();
            for (int i = 0; i < map.size(); i++) {
                Integer in = i + 1;
                s[i][0] = in.toString();
                s[i][1] = (String) keyArray[i];
                List<String> definition = map.get(keyArray[i]);
                stringBuilder.append(s[i][1] + "`" + definition.get(0));
                for (int j = 1; j < definition.size(); j++) {
                    stringBuilder.append("|" + definition.get(j));
                }
                stringBuilder.append("\n");
            }
            // System.out.println(stringBuilder.toString());
            printWriter.write(stringBuilder.toString());
            printWriter.close();


            // Update the map with the latest data
            readFile(file);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    void readFile(String file) throws Exception {
        map.clear();
        String slag = null;
        Scanner scanner = new Scanner(new File(file));
        scanner.useDelimiter("`");
        scanner.next();
        String temp = scanner.next();
        String[] part = temp.split("\n");
        int i = 0;
        int flag = 0;
        sizeMap = 0;
        while (scanner.hasNext()) {
            List<String> definitionList = new ArrayList<String>();
            slag = part[1].trim();
            temp = scanner.next();
            part = temp.split("\n");
            if (map.containsKey(slag)) {
                definitionList = map.get(slag);
            }
            if (part[0].contains("|")) {
                System.out.println(part[0]);
                String[] d = (part[0]).split("\\|");
                for (int ii = 0; ii < d.length; ii++)
                    System.out.println(d[ii]);
                Collections.addAll(definitionList, d);
                sizeMap += d.length - 1;
            } else {
                definitionList.add(part[0]);
            }
            // map.put(slag.trim(), definitionList);
            map.put(slag, definitionList);
            i++;
            sizeMap++;
        }
        scanner.close();
    }


    public String[][] getData() {
        String s[][] = new String[sizeMap][3];
        Set<String> slagListSet = map.keySet();
        Object[] slagList = slagListSet.toArray();
        int index = 0;
        for (int i = 0; i < sizeMap; i++) {
            s[i][0] = String.valueOf(i);
            s[i][1] = (String) slagList[index];
            List<String> definitionList = map.get(slagList[index]);
            s[i][2] = definitionList.get(0);
            System.out.println(s[i][0] + "\t" + s[i][1] + "\t" + s[i][2]);
            for (int j = 1; j < definitionList.size(); j++) {
                if (i < sizeMap)
                    i++;
                s[i][0] = String.valueOf(i);
                s[i][1] = (String) slagList[index];
                s[i][2] = definitionList.get(j);
                System.out.println(s[i][0] + "\t" + s[i][1] + "\t" + s[i][2]);
            }
            index++;
        }
        return s;
    }

    public String[][] getDefinition(String key) {
        List<String> listDefinition = map.get(key);
        if (listDefinition == null)
            return null;
        int size = listDefinition.size();
        String s[][] = new String[size][3];
        for (int i = 0; i < size; i++) {
            s[i][0] = String.valueOf(i);
            s[i][1] = key;
            s[i][2] = listDefinition.get(i);
        }
        return s;
    }

    public void set(String oldSlang, String newSlang, String oldValue, String newValue) {
        System.out.println(oldValue + "\t" + newValue);
        List<String> definition = map.get(oldSlang);
        int index = definition.indexOf(oldValue);
        definition.set(index, newValue);

        // Cập nhật từ tiếng lóng
        List<String> slangList = new ArrayList<>(map.remove(oldSlang));
        slangList.set(index, newSlang);
        map.put(newSlang, slangList);

        // Cập nhật định nghĩa
        List<String> definitionList = new ArrayList<>(definition);
        map.put(newSlang, definitionList);

        this.saveFile(FILE_SLANGWORD);
        System.out.println("Size of map: " + sizeMap);
    }




    public void setslang(String slag, String oldValue, String newValue) {
        System.out.println(oldValue + "\t" + newValue);
        List<String> slang = map.get(slag);
        int index = slang.indexOf(oldValue);
        slang.set(index, newValue);
        this.saveFile(FILE_SLANGWORD);
        System.out.println("Size of map: " + sizeMap);
    }

    public void saveHistory(String slag, String definition) throws Exception {
        // String file = "history.txt";
        File file1 = new File(FILE_HISTORY);
        FileWriter fr = new FileWriter(file1, true);
        fr.write(slag + "`" + definition + "\n");
        fr.close();
    }

    public String[][] readHistory() {
        List<String> historySlag = new ArrayList<>();
        List<String> historyDefinition = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(FILE_HISTORY));
            scanner.useDelimiter("`");
            String temp = scanner.next();
            String[] part = scanner.next().split("\n");
            historySlag.add(temp);
            historyDefinition.add(part[0]);
            while (scanner.hasNext()) {
                temp = part[1];
                part = scanner.next().split("\n");
                historySlag.add(temp);
                historyDefinition.add(part[0]);
            }
            scanner.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int size = historySlag.size();
        String s[][] = new String[size][3];
        for (int i = 0; i < size; i++) {
            s[size - i - 1][0] = String.valueOf(size - i);
            s[size - i - 1][1] = historySlag.get(i);
            s[size - i - 1][2] = historyDefinition.get(i);
        }
        return s;
    }

    public String[][] findDefinition(String query) {
        // Get all slang contain key
        List<String> keyList = new ArrayList<>();
        List<String> definitionList = new ArrayList<>();
        for (Entry<String, List<String>> entry : map.entrySet()) {
            List<String> definition = entry.getValue();
            for (int i = 0; i < definition.size(); i++) {
                if (definition.get(i).toLowerCase().contains(query.toLowerCase())) {
                    keyList.add(entry.getKey());
                    definitionList.add(definition.get(i));
                }
            }
        }
        int size = keyList.size();
        String s[][] = new String[size][3];

        for (int i = 0; i < size; i++) {
            s[i][0] = String.valueOf(i);
            s[i][1] = keyList.get(i);
            s[i][2] = definitionList.get(i);
        }
        return s;
    }

    public void reset() {
        try {
            readFile(FILE_ORIGINAL_SLANGWORD);
            this.saveFile(FILE_SLANGWORD);
            this.resetHistory(FILE_HISTORY);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void delete(String slag, String value) {
        List<String> definitionList = map.get(slag);
        int index = definitionList.indexOf(value);
        if (definitionList.size() == 1) {
            map.remove(slag);
        } else {
            definitionList.remove(index);
            map.put(slag, definitionList);
        }
        sizeMap--;
        this.saveFile(FILE_SLANGWORD);
    }

    public void addNew(String slag, String definition) {
        List<String> definitionList = new ArrayList<>();
        definitionList.add(definition);
        sizeMap++;
        map.put(slag, definitionList);
        this.saveFile(FILE_SLANGWORD);
    }

    public void addDuplicate(String slag, String defitnition) {
        List<String> definitionList = map.get(slag);
        definitionList.add(defitnition);
        sizeMap++;
        map.put(slag, definitionList);
        this.saveFile(FILE_SLANGWORD);
    }

    public void addOverwrite(String slag, String definition) {
        List<String> definitionList = map.get(slag);
        definitionList.set(0, definition);
        map.put(slag, definitionList);
        this.saveFile(FILE_SLANGWORD);
    }

    public boolean checkSlang(String slag) {
        for (String keyIro : map.keySet()) {
            if (keyIro.equals(slag))
                return true;
        }
        return false;
    }

    public String[] random() {
        // Random a number
        int minimun = 0;
        int maximun = map.size() - 1;
        int rand = randInt(minimun, maximun);
        // Get slang difinition
        String s[] = new String[2];
        int index = 0;
        for (String key : map.keySet()) {
            // System.out.println(key);
            if (index == rand) {
                s[0] = key;
                s[1] = map.get(key).get(0);
                break;
            }
            index++;
        }
        return s;
    }

    public static int randInt(int minimum, int maximum) {
        return (minimum + (int) (Math.random() * maximum));
    }

    public String[] quiz(int type) {
        String s[] = new String[6];
        if (type == 1) {
            // Random a number
            String[] slangRandom = this.random();
            s[0] = slangRandom[0];
            int rand = randInt(1, 4);
            s[rand] = slangRandom[1];
            s[5] = slangRandom[1];
            for (int i = 1; i <= 4; i++) {
                if (rand == i)
                    continue;
                else {
                    String[] slangRand = this.random();
                    while (slangRand[0] == s[0]) {
                        slangRand = this.random();
                    }
                    s[i] = slangRand[1];
                }
            }
        } else {
            // Random a number
            String[] slangRandom = this.random();
            s[0] = slangRandom[1];
            int rand = randInt(1, 4);
            s[rand] = slangRandom[0];
            s[5] = slangRandom[0];
            for (int i = 1; i <= 4; i++) {
                if (rand == i)
                    continue;
                else {
                    String[] slangRand = this.random();
                    while (slangRand[0] == s[0]) {
                        slangRand = this.random();
                    }
                    s[i] = slangRand[0];
                }
            }
        }

        return s;
    }
}
