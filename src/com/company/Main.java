package com.company;

public class Main {

    public static void main(String[] args) {

        NFCTagParser parser = new NFCTagParser();
        NFCTag tag = new NFCTag();
        parser.fillWithURLPayload(tag, args[0]);

        for (String key : tag.keySet()) {
            System.out.println(key + ": " +tag.get(key));
        }
    }
}
