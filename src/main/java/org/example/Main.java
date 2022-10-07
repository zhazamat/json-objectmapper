package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.posts.Post;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;

public class Main {
    public static  void run(){
        String baseUrl = "https://jsonplaceholder.typicode.com/posts";
        HttpUrl.Builder httpUrl = HttpUrl.parse(baseUrl).newBuilder();

        httpUrl.addQueryParameter("seeing","7");
        String url = httpUrl.build().toString();

        Request request=new Request.Builder()
                .url(url)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        Response response;
        {
            try {
                response = call.execute();
                ObjectMapper objectMapper=new ObjectMapper();
                List<Post> posts=objectMapper.readValue(response.body().string(), new TypeReference<List<Post>>() {});
                System.out.println("---------------------Homework 1---------------------------");
                var split = posts.stream()
                        .collect(partitioningBy(post1 -> post1.getUserId()%2==0));
                System.out.println("------------ Even USER ID -----------");
                split.get(Boolean.TRUE).forEach(System.out::println);

                System.out.println("------------------------Homework 3-------------------------");
              /*  int count=0,sum=0;
                List<Integer>bodyEvenLength=new ArrayList<>();
                    for(Post post1:posts){
                    count++;
                  if(post1.getBody().length()%2==0){
                      sum+=post1.getBody().length();
                      bodyEvenLength.add(post1.getBody().length());
                  }
                }
                bodyEvenLength.forEach(x-> System.out.print(x+"  "));
               System.out.println(count);
               System.out.println(sum);*/
                var posts1 = posts.stream()
                        .filter(post -> post.getBody().length()%2==0)
                        .mapToDouble(post -> post.getBody().length())
                        .sum();
                System.out.println("Even body length of Sum: "+ posts1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public static void main(String[] args) {
      run();

    }
}
