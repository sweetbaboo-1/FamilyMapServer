package Server;

import Request.EventIDRequest;
import Request.PersonIDRequest;
import Request.PersonRequest;
import Result.PersonIDResult;
import Result.PersonResult;
import Service.EventIDService;
import Service.PersonIDService;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;

public class PersonIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        PersonIDResult result;
        try {
            System.out.println("Attempting to fulfill a person/personID command");
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String token = exchange.getRequestHeaders().get("Authorization").get(0);
                String url = exchange.getRequestURI().toString();
                String[] path = url.split("/");
                String id = null;
                if (path.length > 2) {
                    id = path[2];
                    PersonIDRequest request = new PersonIDRequest(id, token);
                    PersonIDService service = new PersonIDService(request);
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
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        }
        exchange.close();
    }
}
