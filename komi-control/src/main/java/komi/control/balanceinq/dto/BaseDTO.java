package komi.control.balanceinq.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseDTO {
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
