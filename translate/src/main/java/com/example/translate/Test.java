package com.example.translate;

import com.example.translate.model.Root;
import com.google.gson.Gson;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test {

    private static final String JSON_URL = "http://10.62.119.235/sketch-api/code?id=bd802ab1-0c33-4145-94f5-2cfd81889078&platform=ios&material_id=5b95646b-f8b9-481b-a769-19282f9b05cb&page=4&target=middle";

    public static void main(String[] args) {
        System.out.println("hello");
        ddd();
        requestJson();
    }

    private static void requestJson() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(JSON_URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Gson gson = new Gson();
                Root root = gson.fromJson(response.body().charStream(), Root.class);
                System.out.println(root.layout.type);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    private static void ddd() {
        Document document = DocumentHelper.createDocument();
        Element bookstore = document.addElement("bookstore");

        Element book = bookstore.addElement("book");
        book.addAttribute("id", "1");

        Element book_name = book.addElement("name");
        book_name.setText("fsdfsa");

        //设置xml 生成格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setIndent(true);

        File file = new File("E:\\Android\\Project\\AutoTransUI\\dom4j_bookstore.xml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}