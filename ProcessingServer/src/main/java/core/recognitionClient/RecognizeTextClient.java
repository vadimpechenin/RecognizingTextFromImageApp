package core.recognitionClient;

import classes.RecognitionDocument;
import core.recognitionClient.handlers.TextRecognitionHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс для отправки на сервер распознавания запроса на распознавания текста с pdf документа
 */
public class RecognizeTextClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 10000;
    private static final int RequestCodeLength = 3;
    private static final int RequestBodyLength = 8;
    private static final int ResponseHeaderLength = 10;
    private final String requestHeaderMask;
    private final TextRecognitionHandler textRecognitionHandler;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;

    public RecognizeTextClient(){
        requestHeaderMask = String.format("%%0%dd%%0%dd", RequestCodeLength, RequestBodyLength);
        textRecognitionHandler = new TextRecognitionHandler();
    }

    private boolean connect() {
        try {
            clientSocket = new Socket(SERVER_IP, SERVER_PORT);
            in = clientSocket.getInputStream();
            out = clientSocket.getOutputStream();
        } catch (IOException ignored) {
            clientSocket = null;
        }
        return clientSocket != null;
    }
    private void disconnect() {
        if(clientSocket != null) {
            try{
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ignored) {
            }
        }
        clientSocket = null;
    }
    private String writeRequestReadResponse(int requestCode, String request) throws IOException {
        byte[] requestBody = request.getBytes(StandardCharsets.UTF_8);
        String requestHeaderStr = String.format(requestHeaderMask, requestCode, requestBody.length);
        byte[] requestHeader = requestHeaderStr.getBytes(StandardCharsets.UTF_8);
        byte[] responseHeader = new byte[ResponseHeaderLength];
        byte[] responseBody;
        String response;

        out.write(requestHeader);
        out.write(requestBody);
        out.flush();

        readNBytes(responseHeader, ResponseHeaderLength);
        String responseHeaderStr = new String(responseHeader, StandardCharsets.UTF_8);
        int responseBodyLength = Integer.parseInt(responseHeaderStr);
        if(responseBodyLength != 0 ) {
            responseBody = new byte[responseBodyLength];
            readNBytes(responseBody, responseBodyLength);
            response = new String(responseBody, StandardCharsets.UTF_8);
        }else {
            response = "";
        }

        return response;
    }
    private void readNBytes(byte[] b, int len) throws IOException {
        Objects.requireNonNull(b);
        if (len < 0 || len > b.length)
            throw new IndexOutOfBoundsException();
        int n = 0;
        while (n < len) {
            int count = in.read(b, n, len - n);
            if (count < 0)
                break;
            n += count;
        }
    }
    private String executeOperation(int operationCode, String operationParameters) {
        String response = null;
        if(connect()) {
            try{
                response = writeRequestReadResponse(operationCode, operationParameters);
            } catch (IOException ignored) {
            }
        }
        disconnect();
        return response;
    }

    public boolean recognitionText(List<byte[]> inputDocument, RecognitionDocument calculateResult) {
        String parameters = textRecognitionHandler.getRequestParameters(inputDocument);
        //Заглушка
        try(FileInputStream fin=new FileInputStream("D://JAVA//Programms//RecognizingTextFromImageApp//Data//text//slaids.docx"))
        {
            System.out.printf("File size: %d bytes \n", fin.available());
            byte[] buffer = new byte[fin.available()];
            // считаем файл в буфер
            fin.read(buffer, 0, fin.available());
           /* int i=-1;
            while((i=fin.read())!=-1){

                System.out.print((char)i);
            }*/
            calculateResult.setValue(buffer);
            return true;
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
            return false;
        }

      /*  String response = executeOperation(CalculateServerRequestCode.RECOGNIZE_TEXT, parameters);
        if(!textRecognitionHandler.parseResponse(response)) return false;
        calculateResult.setValue(textRecognitionHandler.getInfos());
        return textRecognitionHandler.getResult();*/
    }
}
