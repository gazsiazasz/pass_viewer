package com.company;

class NFCTagParser {
    void fillWithURLPayload(NFCTag tagInfo, String payloadStr) {
        String rest = null;
        tagInfo.put("URL", payloadStr);
        if (payloadStr.startsWith("ciscoconnect://")) {
            tagInfo.put("Scheme", "ciscoconnect");
            rest = payloadStr.substring(15);
        } else if (payloadStr.startsWith("linksyssmartwifi://")) {
            tagInfo.put("Scheme", "linksyssmartwifi");
            rest = payloadStr.substring(19);
        }
        if (rest != null) {
            int n = rest.indexOf("/");
            if (n > 0) {
                String host = rest.substring(0, n);
                tagInfo.put("Host", host);
                if (host.equals("svp")) {
                    int m = n + 1;
                    n = rest.indexOf(47, m);
                    if (n > 0) {
                        tagInfo.put("SSID", Obfuscation.deobfuscate(rest.substring(m, n)));
                        tagInfo.put("Password", Obfuscation.deobfuscate(rest.substring(n + 1)));
                    }
                }
            }
        }
    }
}