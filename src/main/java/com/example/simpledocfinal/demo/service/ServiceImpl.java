package com.example.simpledocfinal.demo.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ServiceImpl implements Service
{


    @Override
    public JSONObject[] indexingData(String query) throws IOException, ParseException
    {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Directory index = new RAMDirectory();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter w = new IndexWriter(index, config);

        //Reading file and addDoc
        File file=new File("C:\\Users\\prane\\Downloads\\demo (2)\\demo\\src\\main\\resources\\Products.txt");    //creates a new file instance
        FileReader fr=new FileReader(file);   //reads the file
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
        //StringBuffer sb= new StringBuffer();
        while(br.readLine()!=null)
        {
            String tem = br.readLine().toString();
            String h = tem.replace('{',' ').trim().replace('}',' ').trim();
            //System.out.println(h+"this is h");
            String[] b = h.split(",");
            for(int i=0;i<b.length;i++)
            {
//                System.out.println(b[i]);
            }
            addDoc1(w,b[0],b[1],b[2]);

        }

//        addDoc1(w,"iphone10","Made by apple have ios","100929");
//        addDoc1(w,"iphone11","Made by apple have ios","100929");
//        addDoc1(w,"iphone12","Made by apple have ios","100929");
//        addDoc1(w,"iphone13","Made by apple have ios","100929");
//        addDoc1(w,"samsaung","Made by korean","30000");
//        addDoc1(w,"oneplus","china made","50000");


        w.close();
        String querystr =  query;

        //Query q = new QueryParser("title", analyzer).parse(querystr);
        String[] strarr = new String[2];
        strarr[0] = "title";
        strarr[1]  = "description";
        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(strarr, analyzer);
        Query q = queryParser.parse(querystr);
        //search
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        //print
        System.out.println("Found " + hits.length + " hits.");
        JSONObject[] jsonObjectarr = new JSONObject[hits.length];


        for(int i=0;i<hits.length;++i)
        {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("title") + "\t" + d.get("description") + "\t"+d.get("price"));
            //String[] arr = {d.get("title").substring(1,d.get("title").length()-1), d.get("description").substring(1,d.get("description").length()-1),d.get("price").substring(1,d.get("price").length()-1)};
            JSONObject jSOnObject = new JSONObject();
            jSOnObject.put("title",d.get("title").substring(1,d.get("title").length()-1));
            jSOnObject.put("description",d.get("description").substring(1,d.get("description").length()-1));
            jSOnObject.put("price",d.get("price").substring(1,d.get("price").length()-1));

            jsonObjectarr[i] = jSOnObject;
        }
        String ret = "hits is "+hits.length;
        return jsonObjectarr;
    }
    private static void addDoc1(IndexWriter w,String title,String description,String price) throws IOException
    {
        Document doc = new Document();
        doc.add(new TextField("title",title, Field.Store.YES));
        doc.add(new TextField("description",description,Field.Store.YES));
        doc.add(new StringField("price",price,Field.Store.YES));
        w.addDocument(doc);
    }
}
