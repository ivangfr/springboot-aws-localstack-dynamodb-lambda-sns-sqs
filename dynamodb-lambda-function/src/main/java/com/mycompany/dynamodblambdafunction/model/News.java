package com.mycompany.dynamodblambdafunction.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class News {

    String id;
    String title;
    String publishedAt;
}
