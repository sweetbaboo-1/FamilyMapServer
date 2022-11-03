package Server;

import Request.EventRequest;
import Request.PersonRequest;
import Result.EventResult;
import Result.PersonResult;
import Service.EventService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        EventResult result;
        try {
            System.out.println("Attempting to request events");
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String token = exchange.getRequestHeaders().get("Authorization").get(0);
                EventRequest request = new EventRequest(token);
                EventService service = new EventService(request);
                result = service.execute();

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
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
