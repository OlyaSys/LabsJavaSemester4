package sample;

import entity.Cars;
import entity.Masters;
import entity.Services;
import entity.Works;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class JSON {
    public static String doGETRequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doPOSTRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String doDELETERequest(String url) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        if (!body.isEmpty()) {
            return body;
        }
        return "";
    }

    public static ObservableList<JSONObject> getListOfJSONObject(String url) throws IOException {
        JSONArray array = new JSONArray(doGETRequest(url));

        ObservableList<JSONObject> objectList = FXCollections.observableArrayList();

        JSONObject object;
        for (int i = 0; i < array.length(); i++) {
            object = array.getJSONObject(i);
            objectList.add(object);
        }

        return objectList;
    }

    public static Works getWork(JSONObject jsonObject) {
        Integer newId = (Integer) jsonObject.get("id");
        String newDate = (String) jsonObject.get("dateWork");

        JSONObject master = jsonObject.getJSONObject("masters");
        JSONObject car = jsonObject.getJSONObject("cars");
        JSONObject service = jsonObject.getJSONObject("services");

        return new Works(newId, newDate, master.getInt("id"), car.getInt("id"), service.getInt("id"));
    }

    public static Cars getCar(JSONObject jsonObject) {
        Integer newId = (Integer) jsonObject.get("id");
        String newNumber = (String) jsonObject.get("number");
        String newColor = (String) jsonObject.get("color");
        String newMark = (String) jsonObject.get("mark");
        Integer newForeign = (Integer) jsonObject.get("foreign");

        return new Cars(newId, newNumber, newColor, newMark, newForeign);
    }

    public static Masters getMaster(JSONObject jsonObject) {
        Integer newId = (Integer) jsonObject.get("id");
        String newName = (String) jsonObject.get("nameMaster");

        return new Masters(newId, newName);
    }

    public static Services getService(JSONObject jsonObject) {
        Integer newId = (Integer) jsonObject.get("id");
        String newName = (String) jsonObject.get("name");
        Integer newCostOur = (Integer) jsonObject.get("costOur");
        Integer newCostForeign = (Integer) jsonObject.get("costForeign");

        return new Services(newId, newName, newCostOur, newCostForeign);
    }

    public static JSONObject getId(String url, TextField textId) throws IOException {
        int id = Integer.parseInt(textId.getText());
        return new JSONObject(JSON.doGETRequest(url + id));
    }
}
