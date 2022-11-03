package Server;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                // deserialize the exchange
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());

                // create a request
                RegisterRequest request = (RegisterRequest) gson.fromJson(reqBody, RegisterRequest.class);

                // call the service method
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);

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
                gson.toJson(result, resBody);
                resBody.close();
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
