package com.mszlu.blog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.dao.mapper.ArticleBodyMapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.mapper.ArticleTagMapper;
import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.dao.pojo.ArticleBody;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.service.TagsService;
import com.mszlu.blog.service.ThreadService;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.Archive;
import com.mszlu.blog.vo.ArticleBodyVo;
import com.mszlu.blog.vo.ArticleMessage;
import com.mszlu.blog.vo.ArticleTag;
import com.mszlu.blog.vo.ArticleVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;
import com.mszlu.blog.vo.UserVo;
import com.mszlu.blog.vo.params.ArticlePara;
import com.mszlu.blog.vo.params.PageParams;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagsService tagService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    @Autowired
    private SysUserService sysUserService;

    @Override
    public Result listArticle(PageParams pageParams) {
    	Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
    	IPage<Article> articleIPage = articleMapper.listArticle(page, pageParams.getCategoryId(),
    			                        pageParams.getTagId(),
    			                        pageParams.getMonth(), 
    			                        pageParams.getYear());
    	List<Article> records = articleIPage.getRecords();
    	return Result.success(copyList(records, true, true));
    }
    
    
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        /**
//         * 1、分页查询article数据库表
//         */
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        if(pageParams.getCategoryId() != null) {
//        	queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if(pageParams.getTagId() != null){
//        	LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        	articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//        	List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//        	for(ArticleTag articleTag : articleTags) {
//        		articleIdList.add(articleTag.getArticleId());
//        	}
//        	if(articleIdList.size() > 0){
//        		queryWrapper.in(Article::getId, articleIdList);
//        	}
//        }
//        //是否置顶进行排序,        
//        //时间倒序进行排列相当于order by create_data desc
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
//        List<Article> records = articlePage.getRecords();
//        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
//        List<ArticleVo> articleVoList =copyList(records,true,true);
//        return Result.success(articleVoList);
//    }
    

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {

        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor, boolean isBody, boolean isCategory) {

        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody, isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;
    //"eop的作用是对应copyList，集合之间的copy分解成集合元素之间的copy
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor, boolean isBody, boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));        //BeanUtils.copyProperties用法   https://blog.csdn.net/Mr_linjw/article/details/50236279
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口都需要标签和作者信息
        if(isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        
        if(isAuthor) {
            //拿到作者id
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        
        if(isBody) {
        	Long bodyId = article.getBodyId();
        	articleVo.setBody(findArticleBodyById(bodyId));
        }
        
        if(isCategory) {
        	Long categoryId = article.getCategoryId();
        	articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        
        return articleVo;

    }


	@Override
	public Result hotArticles(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getViewCounts);
		queryWrapper.select(Article::getId, Article::getTitle);
		queryWrapper.last("limit "+limit);
		List<Article> articles = articleMapper.selectList(queryWrapper);

		return Result.success(copyList(articles, false, false));
	}

	@Override
	public Result newArticles(int limit) {
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.orderByDesc(Article::getCreateDate);
		queryWrapper.select(Article::getId, Article::getTitle);
		queryWrapper.last("limit "+limit);
		List<Article> articles = articleMapper.selectList(queryWrapper);

		return Result.success(copyList(articles, false, false));
	}

	@Override
	public Result listArchives() {
		List<Archive> archives = articleMapper.listArchives();
		return Result.success(archives);
	}

	@Autowired
	private ThreadService threadService;
	@Override
	public Result findArticleById(Long articleId) {
		
		Article article = this.articleMapper.selectById(articleId);
		ArticleVo articleVo = copy(article,true,true,true,true);
		
		//查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        // 更新 增加了此次接口的 耗时 如果一旦更新出问题，不能影响 查看文章的操作
        //线程池  可以把更新操作 扔到线程池中去执行，和主线程就不相关了
		
		threadService.updateArticleViewCount(articleMapper,article);
		
		return Result.success(articleVo);
	}
	
	@Autowired
	private ArticleBodyMapper articleBodyMapper;
	
	private ArticleBodyVo findArticleBodyById(Long bodyId) {
		ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
		ArticleBodyVo articleBodyVo = new ArticleBodyVo();
		articleBodyVo.setContent(articleBody.getContent());
		return articleBodyVo;
	}

	@Override
	public Result publish(ArticlePara articlePara) {
		SysUser sysUser = UserThreadLocal.get();
		
		//article info
		Article article = new Article();
		boolean isEdit = false;
		if (articlePara.getId() != null) {
			article = new Article();
			article.setId(articlePara.getId());
			article.setSummary(articlePara.getSummary());
			article.setTitle(articlePara.getTitle());
			article.setCategoryId(Long.parseLong(articlePara.getCategory().getId()));
			articleMapper.updateById(article);
			isEdit=true;
		} 
		else 
		{
			article = new Article();
			article.setId(articlePara.getId());
			article.setSummary(articlePara.getSummary());
			article.setTitle(articlePara.getTitle());
			article.setCategoryId(Long.parseLong(articlePara.getCategory().getId()));
			article.setAuthorId(sysUser.getId());
			article.setWeight(Article.Article_Common);
			article.setViewCounts(0);
			article.setCreateDate(System.currentTimeMillis());
			article.setCommentCounts(0);
			this.articleMapper.insert(article);
		}
		//tag
		List<TagVo> tags = articlePara.getTags();
		if(tags != null) {
			for(TagVo tag: tags) {
			Long articleId = article.getId();
			if(isEdit) {
				LambdaQueryWrapper<ArticleTag> queryWrapper = Wrappers.lambdaQuery();
				queryWrapper.eq(ArticleTag::getArticleId, articleId);
				articleTagMapper.delete(queryWrapper);
				}
			ArticleTag articleTag = new ArticleTag();
			articleTag.setTagId(Long.parseLong(tag.getId()));
			articleTag.setArticleId(articleId);
			articleTagMapper.insert(articleTag);
			}
		}
		
		//body
		ArticleBody articleBody = new ArticleBody();
		if(isEdit) {
			articleBody.setArticleId(article.getId());
			articleBody.setContent(articlePara.getBody().getContent());
			articleBody.setContentHtml(articlePara.getBody().getContentHtml());
			LambdaUpdateWrapper<ArticleBody> updateWrapper = Wrappers.lambdaUpdate();
			updateWrapper.eq(ArticleBody::getArticleId, article.getId());
			articleBodyMapper.update(articleBody, updateWrapper);
		} else {
			articleBody.setArticleId(article.getId());
			articleBody.setContent(articlePara.getBody().getContent());
			articleBody.setContentHtml(articlePara.getBody().getContentHtml());
			articleBodyMapper.insert(articleBody);
			
			article.setBodyId(articleBody.getId());
			articleMapper.updateById(article);
		}
		
		
		Map<String,String> map = new HashMap<>();
		map.put("id",article.getId().toString());
  //if is Edit, then need to sent msg to rocketMQ, ask to update cache.
		if(isEdit) {
			ArticleMessage articleMessage = new ArticleMessage();
			articleMessage.setArticleId(article.getId());
			rocketMQTemplate.convertAndSend("blog-update-article", articleMessage);
		}
		
		return Result.success(map);
	}
	
}

