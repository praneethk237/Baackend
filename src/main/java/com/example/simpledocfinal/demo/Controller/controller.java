package com.example.simpledocfinal.demo.Controller;

import com.example.simpledocfinal.demo.service.ServiceImpl;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class controller
{

    @GetMapping(value="/index")
    public JSONObject[] index(@RequestParam("query") String query)
    {
        ServiceImpl serviceImpl = new ServiceImpl();
        //String ret = null;
        JSONObject[] jsonObjectarr= new JSONObject[100];
        try {
            jsonObjectarr = serviceImpl.indexingData(query);
        } catch (IOException e) {
            System.out.println("something wrong with io");
        } catch (ParseException e)
        {
            System.out.println("parsing exception");
        }
        return jsonObjectarr;
    }
}
