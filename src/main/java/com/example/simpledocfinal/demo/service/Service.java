package com.example.simpledocfinal.demo.service;

import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONObject;

import java.io.IOException;

public interface Service
{
    public JSONObject[] indexingData(String query) throws IOException, ParseException;

}
