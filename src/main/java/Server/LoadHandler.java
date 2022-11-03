package Server;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult result;
        try {
            System.out.println("Attempting to load the database with the given info");
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                LoadRequest loadRequest = gson.fromJson(reqBody, LoadRequest.class);
                LoadService loadService = new LoadService(loadRequest);
                result = loadService.execute();
                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(result, resBody);
                resBody.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        }
        exchange.close();
    }
}
