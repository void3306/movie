package com.movie.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.movie.api.mapper.FilmMapper;
import com.movie.api.model.entity.Film;
import com.movie.api.service.FilmService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@CacheConfig(cacheNames = "film")
public class FilmServiceImpl implements FilmService {

    @Resource
    private FilmMapper filmMapper;

    @Value("${server.url}")
    private String url;

    @Override
    public void save(Film film) {
        film.setHot(0);
        filmMapper.insert(film);
    }

    @CacheEvict
    @Override
    public void deleteById(String id) {
        filmMapper.deleteById(id);
    }

    @Cacheable
    @Override
    public List<Film> findAll() {
        List<Film> films = filmMapper.selectList(null);
        for (Film film : films) {
            film.setCover(url + film.getCover());
        }
        return films;
    }

    @Override
    public List<Film> findByRegionAndType(String region, String type) {
        QueryWrapper<Film> wrapper = new QueryWrapper<>();
        if (!region.equals("全部")) {
            wrapper.in("region", region);
        }
        if (!type.equals("全部")) {
            wrapper.in("type", type);
        }
        List<Film> films = filmMapper.selectList(wrapper);
        for (Film film : films) {
            film.setCover(url + film.getCover());
        }
        return films;
    }

    @Override
    public List<Film> findHots(Integer limit) {
        QueryWrapper<Film> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("hot").last("limit " + limit);
        List<Film> films = filmMapper.selectList(wrapper);
        for (Film film : films) {
            film.setCover(url + film.getCover());
        }
        return films;
    }

    @Cacheable
    @Override
    public List<Film> findLikeName(String name) {
        QueryWrapper<Film> wrapper = new QueryWrapper<>();
        wrapper.like("name", name);
        List<Film> films = filmMapper.selectList(wrapper);
        for (Film film : films) {
            film.setCover(url + film.getCover());
        }
        return films;
    }

    @Cacheable
    @Override
    public Film findById(String id) {
        Film film = filmMapper.selectById(id);
        film.setCover(url+film.getCover());
        return film;
    }

    @CacheEvict
    @Override
    public Film update(Film film) {
        filmMapper.updateById(film);
        film.setCover(url+film.getCover());
        return film;
    }

}
