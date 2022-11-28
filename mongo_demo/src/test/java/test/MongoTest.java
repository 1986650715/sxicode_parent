package test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

public class MongoTest {
    //查询所有数据
    @Test
    public void test1(){
        //创建操作mongoDb的客户端
        MongoClient mongoClient = new MongoClient("110.41.19.90");

        MongoDatabase commentdb = mongoClient.getDatabase("commentdb");
//        commentdb.createCollection("comment");

        MongoCollection<Document> comment = commentdb.getCollection("comment");

        FindIterable<Document> documents = comment.find();

        for(Document document: documents){
            System.out.println("_id"+document.get("_id"));
            System.out.println("name"+document.get("name"));
        }
        mongoClient.close();
    }
}
