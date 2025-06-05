package org.example.citatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;

public class AITranslationGenerationUnit {

    private static String useAIGeneration(String json) {
        String apiKey = Dotenv.load().get("OPENROUTER_API_KEY");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JsonNode rootNode = mapper.readTree(response.body().string());
                    String content = rootNode
                            .path("choices")
                            .get(0)
                            .path("message")
                            .path("content")
                            .asText();
                    return content;
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println(response.body().string());
                }
            } else {
                System.out.println("Error: " + response.code() + " - " + response.message());
                System.out.println("Error details: " + response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateText() {
        String json = """
                {
                                     "model": "deepseek/deepseek-chat-v3-0324:free",
                                     "messages": [
                                         {
                                             "role": "system",
                                             "content": "Ты — Придумыватель цитат. Придумывай цитаты по запросу, отправляй только её содержимое, никаких меток, только текст"
                                         },
                                         {
                                             "role": "user",
                                             "content": [
                                                 {
                                                     "type": "text",
                                                     "text": "Придумай Цитату"
                                                 },
                                             ]
                                         }
                                     ]
                                 }
                                 """;
        return useAIGeneration(json);
    }

    public static String translateText(String text, String lang) {
        String json = String.format("""
                {
                    "model": "deepseek/deepseek-chat-v3-0324:free",
                    "messages": [
                        {
                            "role": "system",
                            "content": "Ты — профессиональный переводчик на язык %s (Можешь использовать алфавит только этого языка). Переведи следующий текст без лишних слов, только сам перевод."
                        },
                        {
                            "role": "user",
                            "content": [
                                {
                                    "type": "text",
                                    "text": "%s"
                                },
                            ]
                        }
                    ]
                }
                """, lang, text);

        return useAIGeneration(json);
    }
}
//deepseek/deepseek-chat-v3-0324:free
//google/gemini-2.0-flash-exp:free