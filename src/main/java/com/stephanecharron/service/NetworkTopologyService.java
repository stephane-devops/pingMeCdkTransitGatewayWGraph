package com.stephanecharron.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stephanecharron.model.NetworkTopology;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NetworkTopologyService {
    public static NetworkTopology get (String filename){
        Gson gson = new Gson();
        try {
            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(filename)),
                    new TypeToken<NetworkTopology>() {
                    }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
