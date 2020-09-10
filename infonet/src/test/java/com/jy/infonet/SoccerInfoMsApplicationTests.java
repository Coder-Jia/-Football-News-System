package com.jy.infonet;

import com.jy.infonet.service.NewsService;
import com.jy.infonet.task.LikeSchedule;
import com.jy.infonet.util.ImageUtil;
import com.jy.infonet.util.IndexUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SoccerInfoMsApplicationTests {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	NewsService service;
	@Autowired
	IndexUtil index;

	@Test
	void contextLoads() {
		boolean b = ImageUtil.getInstance().deleteImage("E:\\workspace_idea\\infonet\\infonet\\src\\main\\resources\\static\\1b891691-4ec3-4eed-828a-96a396e785a");
		logger.info("=========="+b);
	}

	@Test
	void contextLoads2() {
		/*//浏览量
		redisTemplate.opsForHash().increment(newsBrowseCounts,String.valueOf(327),1);//+1
		System.out.println(redisTemplate.opsForHash().get(newsBrowseCounts, String.valueOf(327)));
		//评论总数+1
		redisTemplate.opsForHash().increment(newsCommentCounts,String.valueOf(327),1);//+1
		redisTemplate.opsForHash().increment(newsCommentCounts,String.valueOf(327),-1);//-1
		System.out.println(redisTemplate.opsForHash().get(newsCommentCounts, String.valueOf(327)));
		//点赞总数+1
		System.out.println("=========="+redisTemplate.opsForHash().increment(newsLikeCounts, String.valueOf(327), 1));
		System.out.println(redisTemplate.opsForHash().get(newsLikeCounts, String.valueOf(327)));
		//用户点赞与否
		redisTemplate.opsForHash().put(news_like_user,String.valueOf(327),36);
		System.out.println(redisTemplate.opsForHash().get(news_like_user, String.valueOf(327)));*/
		//System.out.println(redisTemplate.opsForHash().get(news_like_user, String.valueOf(327)));
	}

	@Test
	void contextLoads3() {
		int j = 1;
		for(int i=0;i<7;i++){
			if (j==1){
				service.countNewsLike(30+i,279,1);
			}else{
				service.countNewsLike(36,279,-1);
			}
		}
		for(int i=0;i<100;i++){
			if (j==1){
				service.countBrowse(279);
			}
		}
		service.countNewsLike(36,330,-1);
	}

	@Test
	void contextLoads5() {
		int j = 1;
	}

	@Autowired
	LikeSchedule likeSchedule;

	@Test
	void contextLoads4() {
		//likeSchedule.saveBrowseDataToMySQL();
	}

	@Test//生成索引库
	void contextLoads6() {
		index.createIndexText();
	}

	@Test
	void contextLoads7() {
		System.out.println("============");
		System.out.println(service.getNewsWithExample(1));
	}




}
