package com.ssafy.myname.vito;

import com.ssafy.myname.vito.auth.Auth;
import com.ssafy.myname.vito.batch.GetTranscribe;
import com.ssafy.myname.vito.batch.PostTranscribe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

//@SpringBootApplication
public class MainRunner {

    private final Auth auth;

//    @Autowired
    public MainRunner(Auth auth) {
        this.auth = auth;
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(MainRunner.class, args);
    }

    @PostConstruct
    public void run() throws Exception {
        //인증 토큰 요청
        String token = auth.getToken();

        //POST 요청
        PostTranscribe postTranscribe = new PostTranscribe();
        String transcribeToken = postTranscribe.transcribe(token);

        //GET 요청
        GetTranscribe getTranscribe = new GetTranscribe();
        String transcription;
        Thread.sleep(5000);

        while ((transcription = getTranscribe.getTranscription(transcribeToken, token)) == null) {
            //전사 작업이 완료되지 않은 경우 1초 동안 대기
            Thread.sleep(1000);
        }
        //전사 결과 출력
        //return을 통해 결과 출력 필요 시 사용할 것.
        System.out.println(transcription);
    }
}