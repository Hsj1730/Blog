package com.hsjnb.service;

import com.hsjnb.NotFoundException;
import com.hsjnb.dao.BlogRepository;
import com.hsjnb.po.Blog;
import com.hsjnb.po.Type;
import com.hsjnb.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * █████▒█      ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Joe
 * @version : 1.0
 * @date : Created in 2020/07/21 15:44
 * @description :
 */

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll((Specification<Blog>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates =  new ArrayList<>();
            if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle() + "%"));
            }
            if (blog.getTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
            }
            if (blog.isRecommend()) {
                predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
            return null;
        },pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return null;
    }

    @Transactional
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        return null;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b == null) {
            throw new NotFoundException("不存在该博客");
        }
        BeanUtils.copyProperties(blog,b);
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
