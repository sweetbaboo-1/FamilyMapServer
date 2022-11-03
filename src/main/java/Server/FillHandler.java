package Server;

import Request.FillRequest;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import Service.FillService;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Trying to process fill request");
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // deserialize the exchange
                String urlPath = exchange.getRequestURI().toString();
                String[] path = urlPath.split("/");
                int generations = Integer.MIN_VALUE;
                String username;

                if (path.length > 3) {
                    generations = Integer.parseInt(path[3]);
                }
                username = path[2];

                // create a request
                FillRequest request = new FillRequest(username, generations);

                // call the service method
                FillService service = new FillService(request);
                FillResult result = service.fill();

                /*
                if result.success== true httpOK
                else httpBAD
                headers first, then response body
                ALWAYS WRITE BACK THE RESULT
                 */

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // serialize the result into a json string and write it back to the server
                Writer resBody = new OutputStreamWriter(exchange.getResponseBody());
                Gson gson = new Gson();
                gson.toJson(result, resBody);
                resBody.close();
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
