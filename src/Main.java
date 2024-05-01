import java.io.*;
import java.util.*;

public class Main {

    static int rotorCounter1 = 0;
    static int rotorCounter2 = 0;
    static int rotorCounter3 = 0;

    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);

        HashMap<Character, Character> hashMap = new HashMap<>();
        HashMap<Character, Character> rotorMap1 = new HashMap<>();
        HashMap<Character, Character> rotorMap2 = new HashMap<>();
        HashMap<Character, Character> rotorMap3 = new HashMap<>();


        String rotor1 = "";
        String rotor2 ="";
        String rotor3 = "";

        FileReader fin = new FileReader("C:\\Users\\nima\\Downloads\\EnigmaFile.txt");
        Scanner src = new Scanner(fin);

        String line;
        String date = "";

        String inputDate = input.nextLine();

        date += "Date: " + inputDate;

        while (src.hasNextLine()) {
            line = src.nextLine();
            if (line.equals(date)){
                line = src.nextLine();

                boolean t = false;
                for (int i = 0; i < line.length() - 1; i++) {
                    if (line.charAt(i) == '[' || t) {
                        t = true;
                        if ((line.charAt(i) <= 122 && line.charAt(i) >= 97) && (line.charAt(i + 1) <= 122
                                && line.charAt(i + 1) >= 97)) {
                            Enigma.makePlugBoard(line.charAt(i), line.charAt(i + 1), hashMap);
                        }
                    }
                }
                t = false;
                line = src.nextLine();
                for (int i = 0; i < line.length() - 1; i++) {
                    if (line.charAt(i) == '[' || t) {
                        if (t) {
                            rotor1 += line.charAt(i);
                        }
                        t = true;
                    }
                }
                Enigma.string1 = rotor1;
                Enigma.rotor(rotor1, rotorMap1);

                t = false;
                line = src.nextLine();
                for (int i = 1; i < line.length() - 1; i++) {
                    if (line.charAt(i) == '[' || t) {
                        if (t) {
                            rotor2 += line.charAt(i);
                        }
                        t = true;
                    }
                }
                Enigma.string2 = rotor2;
                Enigma.rotor(rotor2, rotorMap2);

                t = false;
                line = src.nextLine();
                for (int i = 1; i < line.length() - 1; i++) {
                    if (line.charAt(i) == '[' || t) {
                        if (t) {
                            rotor3 += line.charAt(i);
                        }
                        t = true;
                    }
                }
                Enigma.string3 = rotor3;
                Enigma.rotor(rotor3, rotorMap3);
                fin.close();
                break;
            }
        }

        String inputPhrase = input.nextLine();
        StringBuilder result = new StringBuilder();
        char c;

        for (int i = 0; i < inputPhrase.length(); i++) {
            c = inputPhrase.charAt(i);

            c = Enigma.plugBoard(hashMap,c);

            c = Enigma.getValue(rotorMap3, c);
            c = Enigma.getValue(rotorMap2, c);
            c = Enigma.getValue(rotorMap1, c);

            c = Enigma.reflector(c);

            c = Enigma.returnMap(rotorMap1).get(c);
            c = Enigma.returnMap(rotorMap2).get(c);
            c = Enigma.returnMap(rotorMap3).get(c);

            result.append(Enigma.plugBoard(hashMap, c));

            if(rotorCounter3 < 26){
                Enigma.string3 = Enigma.rotorRotation(Enigma.string3);
                Enigma.rotor(Enigma.string3, rotorMap3);
                rotorCounter3++;
            }
            else if(rotorCounter2 < 26) {
                Enigma.string2 = Enigma.rotorRotation(Enigma.string2);
                Enigma.rotor(Enigma.string2, rotorMap2);
                rotorCounter2++;
            }
            else if(rotorCounter1 < 26) {
                Enigma.string1 = Enigma.rotorRotation(Enigma.string1);
                Enigma.rotor(Enigma.string1, rotorMap1);
                Enigma.rotorRotation(String.valueOf(rotor1));
                rotorCounter1++;
            }
        }
        System.out.println(result);
    }
}

class Enigma {


    static String string1 ="";
    static String string2 ="";
    static String string3 ="";

    public static Character getKey(Map<Character, Character> map, Character value) {
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Character getValue(Map<Character, Character> map, Character key) {
        for (Map.Entry<Character, Character> entry : map.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    static String rotorRotation(String rotor){
        String string = "";
        string += rotor.charAt(rotor.length() - 1);
        string += rotor.substring(0, rotor.length() - 1);
        return string;
    }

    static void makePlugBoard(char c1, char c2, HashMap<Character, Character> inputItem) {
        inputItem.put(c1, c2);
    }

    public static char plugBoard(Map<Character, Character> map, char c) {
        if (getValue(map, c) == null) {
            if (getKey(map, c) == null) return c;
            else return getKey(map, c);

        } else return getValue(map, c);
    }

    public static void rotor(String s, HashMap<Character, Character> map) {
        for (int i = 0; i < s.length(); i++) {
            map.put(((char) (i + 97)), s.charAt(i));
        }

    }

    public static Map<Character, Character> returnMap(HashMap<Character, Character> map) {
        Map<Character, Character> reMap = new HashMap();
        for (int i = 0; i < map.size(); i++) {
            reMap.put(map.get((char) (97 + i)), (char) (97 + i));
        }
        return reMap;
    }

    static Character reflector(char c) {
        int result = 122 - ((int) c - 97);
        return (char) result;
    }
}
