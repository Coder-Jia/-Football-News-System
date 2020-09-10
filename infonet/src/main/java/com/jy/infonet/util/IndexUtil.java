package com.jy.infonet.util;

import com.jy.infonet.entity.News;
import com.jy.infonet.service.NewsService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * lucene索引util
 */
@Component
public class IndexUtil {
    @Autowired
    private NewsService newsService;
    @Value("${lucene.folder.dir}")
    private String LUCENE_FOLDER_DIR;

    Logger logger = LoggerFactory.getLogger(getClass());
    
    //生成索引库
    public void createIndexText(){
        try{
            //1、采集数据
            List<News> newsList = newsService.getNews();
            //2、创建文档对象
            List<Document> docList = new ArrayList<>(); //文档集合
            for (News mynews:newsList){
                //文档对象
                Document document = new Document();
                //创建域对象,放入文档对象中  key:value的形式
                /**
                 * 分析id:
                 * 是否分词：否(N)，因为主键分词后无意义
                 * 是否索引：是(Y)，如果根据id主键查询，就必须索引
                 * 是否存储：是(Y)，因为主键id比较特殊，，可以指定唯一的一条数据，在业务上一般有重要使用，所以存储，存储后才可以得到id具体的内容
                 * NYY,因此使用StringField
                 */
                document.add(new StringField("newsId", String.valueOf(mynews.getNewsId()), Field.Store.YES));
                /**
                 * 分析title:
                 * 是否分词：是(Y)，因为需要查询，并且分词后有意义，因此需要分词
                 * 是否索引：是(Y)，如果需要根据标题进行查询
                 * 是否存储：是(Y)，需要页面需要展示商品名称，所以需要存储
                 * YYY,因此使用TextField
                 */
                document.add(new TextField("newsTitle", mynews.getNewsTitle(), Field.Store.YES));
                /**
                 * 分析src_cover:
                 * 是否分词：否（N），因为不索引，所以不分词
                 * 是否索引：否(N)，因为不需要根据图片地址进行查询
                 * 是否存储：是（Y），页面需要进行显示图片
                 * NNY,因此使用StoredField
                 */
                document.add(new StoredField("newsCover", mynews.getNewsCover()));
                /**
                 * 分析src_content:
                 * 是否分词：否（N），因为不索引，所以不分词
                 * 是否索引：否(N)，因为不需要根据内容地址进行查询
                 * 是否存储：是（Y），页面需要进行显示资讯内容
                 * NNY,因此使用StoredField
                 */
                document.add(new StoredField("newsContent", mynews.getNewsContent()));
                /**
                 * 分析date:
                 * 是否分词：否（N），因为不索引，所以不分词
                 * 是否索引：否(N)，因为不需要根据日期进行查询
                 * 是否存储：是（Y），页面需要进行显示发布日期
                 * NNY,因此使用StoredField
                 */
                document.add(new StoredField("newsDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mynews.getNewsDate())));
                /**
                 * 分析briefIntro:
                 * 是否分词：是(Y)，因为需要查询，并且分词后有意义，因此需要分词
                 * 是否索引：是(Y)，如果需要根据简介进行查询
                 * 是否存储：是(Y)，需要页面需要显示资讯简介，所以需要存储
                 * YYY,因此使用TextField
                 */
                document.add(new TextField("newsIntro", mynews.getNewsIntro(), Field.Store.YES));
                /**
                 * 分析typeId:
                 * 是否分词：否(N)，因为是种类id，是唯一标识，分词无意义，所以不分词
                 * 是否索引：是(N)，不需要根据种类编号查询
                 * 是否存储：是(Y)，页面需要使用种类id来做标识
                 * NYY,因此使用StringField
                 */
                document.add(new StringField("typeId", String.valueOf(mynews.getTypeId()), Field.Store.YES));
                /**
                 * 这里没有，但是对于价格这种，分析：
                 * 是否分词：是(Y)，lucene底层算法规定，如果根据价格范围进行查询，必须进行分词
                 * 是否索引：是(Y)，需要进行价格范围查询，必须分词
                 * 是否存储：是（Y），因为页面需要显示价格
                 * 写法：比如价格类型是int
                 * document.add(new IntPoint("price", mynews.getprice());//存值
                 * document.add(new StoredField("price", mynews.getprice());//存储
                 */
                //将文档对象放入文档集合中
                docList.add(document);
            }
            //3、创建分词器 ,标准分词器StandardAnalyzer对英文分词效果好，对中文是单字分词，也就死一个字为一个词
            Analyzer analyzer = new IKAnalyzer();
            //4、创建Directory对象，目录对象表示索引库的位置
            Directory dir = FSDirectory.open(Paths.get(LUCENE_FOLDER_DIR));
            //5、创建IndexWriterConfig输出流初始化对象，这个对象中指定切分词指定的分词器
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //6、创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
            IndexWriter indexWriter = new IndexWriter(dir, config);
            //7、写入文档到索引库
            for(Document doc:docList) {
                indexWriter.addDocument(doc);
            }
            //8、释放资源
            indexWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //根据关键词搜索索引库,分页返回,一次返回十条
    public Map<String,Object> IndexTextSearch(String keyword,int currentPage){
        Map<String,Object> map = new HashMap<>();
        int start = (currentPage-1)*10;
        int end = currentPage*10;
        try{
            //1、创建分词器(对搜索关键词进行分词使用)
            //注意：分词器要和创建索引的时候的使用的分词器一模一样
            Analyzer analyzer = new IKAnalyzer();
            //需求：标题、简要介绍域的都要搜索出来
            //查询的多个域名
            String[] Fields = {"newsTitle","newsIntro"};
            //设置影响排序的权重，这款i设置域的权重
            Map<String,Float> boots = new HashMap<>();
            boots.put("newsTitle", 10000000000f);
            //2、创建查询对象
            //从多个域查询对象
            MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(Fields, analyzer, boots);
            //3、设置搜索关键词keyword
            Query query = multiFieldQueryParser.parse(keyword);
            //4、创建Directory目录对象，指定索引库的位置
            Directory dir = FSDirectory.open(Paths.get(LUCENE_FOLDER_DIR));
            //5、创建输入流对象
            IndexReader indexReader = DirectoryReader.open(dir);
            //6、创建搜索对象
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //7、搜索并返回结果
            TopDocs topDocs = indexSearcher.search(query,end);//第二个参数：返回多少条数据。这里返回前10条对象
            //获取查询的结果集的总数，打印
            System.out.println("===查询的结果集的总数======"+topDocs.totalHits);
            //8、获取结果集
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            List<News>  newsList = new ArrayList<>();
            //9、遍历结果集
            if(scoreDocs!=null) {
                for(int i=start;(i<end&&i<topDocs.totalHits);++i) {
                    //获取查询到的文档唯一标识:文档id，这个id是lucene在创建文档的时候自动分配的
                    int docId = scoreDocs[i].doc;
                    //通过 文档id 读取文档
                    Document doc = indexSearcher.doc(docId);
                    News news = new News();
                    news.setNewsId(Integer.parseInt(doc.get("newsId")));
                    news.setNewsTitle(doc.get("newsTitle"));
                    news.setNewsDate(Timestamp.valueOf(doc.get("newsDate")));//将String类型转化为时间戳Timestamp
                    news.setNewsCover("/infonet/cover/"+doc.get("newsCover"));
                    news.setNewsContent(doc.get("newsContent"));
                    news.setNewsIntro(doc.get("newsIntro"));
                    news.setTypeId(Integer.parseInt(doc.get("typeId")));
                    newsList.add(news);
                }
            }
            //10、关闭流，释放资源
            indexReader.close();
            int count = (int)topDocs.totalHits;
            int totalPage = count%10==0?count/10:count/10+1;
            map.put("totalPage",totalPage);
            map.put("newsList",newsList);
            return map;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //添加索引到索引库
    public boolean insertIndex(News mynews){
        try{
            //文档对象
            Document document = new Document();
            //创建域对象,放入文档对象中  key:value的形式
            document.add(new StringField("newsId", String.valueOf(mynews.getNewsId()), Field.Store.YES));
            document.add(new TextField("newsTitle", mynews.getNewsTitle(), Field.Store.YES));
            document.add(new StoredField("newsCover", mynews.getNewsCover()));
            document.add(new StoredField("newsContent", mynews.getNewsContent()));
            document.add(new StoredField("newsDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mynews.getNewsDate())));
            document.add(new TextField("newsIntro", mynews.getNewsIntro(), Field.Store.YES));
            document.add(new StringField("typeId", String.valueOf(mynews.getTypeId()), Field.Store.YES));
            //3、创建分词器 ,标准分词器StandardAnalyzer对英文分词效果好，对中文是单字分词，也就死一个字为一个词
            Analyzer analyzer = new IKAnalyzer();
            //4、创建Directory对象，目录对象表示索引库的位置
            Directory dir = FSDirectory.open(Paths.get(LUCENE_FOLDER_DIR));
            //5、创建IndexWriterConfig输出流初始化对象，这个对象中指定切分词指定的分词器
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //6、创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
            IndexWriter indexWriter = new IndexWriter(dir, config);
            //7、写入文档到索引库
            indexWriter.addDocument(document);
            //8、释放资源
            indexWriter.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //从索引库中删除索引
    public boolean deleteIndex(int newsId){
        try {
            //3、创建分词器 ,标准分词器StandardAnalyzer对英文分词效果好，对中文是单字分词，也就死一个字为一个词
            Analyzer  analyzer = new IKAnalyzer();

            //4、创建Directory对象，目录对象表示索引库的位置
            Directory dir = FSDirectory.open(Paths.get(LUCENE_FOLDER_DIR));

            //5、创建IndexWriterConfig输出流初始化对象，这个对象中指定切分词指定的分词器
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            //6、创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
            IndexWriter indexWriter = new IndexWriter(dir, config);

            //根据条件删除
            indexWriter.deleteDocuments(new Term("newsId",String.valueOf(newsId)));

            //8、释放资源
            indexWriter.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //更新索引库记录
    public boolean updateIndex(int newsId,News mynews){
        try {
            //需要变更成的内容
            Document document = new Document();
            //创建域对象,放入文档对象中  key:value的形式
            document.add(new StringField("newsId", String.valueOf(mynews.getNewsId()), Field.Store.YES));
            document.add(new TextField("newsTitle", mynews.getNewsTitle(), Field.Store.YES));
            document.add(new StoredField("newsCover", mynews.getNewsCover()));
            document.add(new StoredField("newsContent", mynews.getNewsContent()));
            document.add(new StoredField("newsDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mynews.getNewsDate())));
            document.add(new TextField("newsIntro", mynews.getNewsIntro(), Field.Store.YES));
            document.add(new StringField("typeId", String.valueOf(mynews.getTypeId()), Field.Store.YES));
            //3、创建分词器 ,标准分词器StandardAnalyzer对英文分词效果好，对中文是单字分词，也就死一个字为一个词
            Analyzer  analyzer = new IKAnalyzer();
            //4、创建Directory对象，目录对象表示索引库的位置
            Directory dir = FSDirectory.open(Paths.get(LUCENE_FOLDER_DIR));
            //5、创建IndexWriterConfig输出流初始化对象，这个对象中指定切分词指定的分词器
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            //6、创建IndexWriter输出流对象，指定输出的位置和使用的config初始化对象
            IndexWriter indexWriter = new IndexWriter(dir, config);
            //修改。第一个参数：修改条件(Term对象，键值对的形式)；第二个参数：修改成的内容
            indexWriter.updateDocument(new Term("newsId",String.valueOf(newsId)), document);
            /** 虽然不 commit，也会生效，但建议做提交操作，*/
            indexWriter.commit();
            //8、释放资源
            indexWriter.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            logger.info("更新资讯{}到索引库异常:"+e.getMessage(),newsId);
            return false;
        }
    }
}
