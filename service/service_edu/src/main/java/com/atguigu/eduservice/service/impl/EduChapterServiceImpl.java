package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuilException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;


    // 课程大纲列表, 根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        // 1 根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);

        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2 查询小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        // 3 变量查询章节list结合进行封装
        // 遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            // 每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            // eduChapter 对象值复制到ChapterVo中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            // 把chapterVo放到最终list集合
            finalList.add(chapterVo);

            // 创建集合， 用于封装章节的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            // 4 变量查询小节list集合，进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                // 得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                // 判断：小节里面chapterid和章节里面id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);

                    videoVoList.add(videoVo);
                }
            }
            // 把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVoList);
        }
        return finalList;
    }

    // 5 删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据chapterid章节id 查询小节表， 如果查询有数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);

        // 判断
        if (count > 0) {    // 查询出小节不进行删除
            throw new GuilException(20001, "不能删除");
        }else { // 不能查询数据，进行删除
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    // 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);

        baseMapper.delete(wrapper);
    }
}
