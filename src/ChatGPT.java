import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPT {
	/*
	 * the class defines a constant string OPENAI_API_URL that specifies the URL of the OpenAI API endpoint 
	 * to use for generating text completions, and a private member variable OPENAI_API_KEY that stores 
	 * the API key needed to authenticate API requests
	 */
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final String OPENAI_API_KEY;
    /*
     * constructor
     */
    public ChatGPT(String apiKey) {
        OPENAI_API_KEY = apiKey;
    }
    /*
     * Method called generateCompletion that takes a prompt string as input and returns a string
     * with the generated completion. 
     */
    public String generateCompletion(String prompt) throws IOException, JSONException {
    	
        URL url = new URL(OPENAI_API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        con.setDoOutput(true);

        /**
         * {
         *     "model": "gpt-3.5-turbo",
         *     "messages": [{"role": "user", "content": "1+1"}]
         * }
         */
        
        JSONObject json = new JSONObject();
        json.put("model", "gpt-3.5-turbo");
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", prompt);
        JSONArray messages = new JSONArray();
        messages.put(message);
        json.put("messages", messages);

        String jsonString = json.toString();
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(jsonString);
        out.close();
        
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }

        in.close();
        //System.out.println(response);

        /**
         * {
         *     "id": "chatcmpl-6y3k1msRWQYepbPwcBHHrIEXucwsj",
         *     "object": "chat.completion",
         *     "created": 1679772081,
         *     "model": "gpt-3.5-turbo-0301",
         *     "usage": {
         *         "prompt_tokens": 11,
         *         "completion_tokens": 1,
         *         "total_tokens": 12
         *     },
         *     "choices": [
         *         {
         *             "message": {
         *                 "role": "assistant",
         *                 "content": "2"
         *             },
         *             "finish_reason": "stop",
         *             "index": 0
         *         }
         *     ]
         * }
         */

        JSONObject jsonResponse = new JSONObject(response.toString());
        JSONArray choices = jsonResponse.getJSONArray("choices");
        String text = choices.getJSONObject(0).getJSONObject("message").getString("content");

        return text;
    }
}