package com.gradu.infou.Service;

import com.gradu.infou.Auth.Service.AuthService;
import com.gradu.infou.Auth.Utils.JwtUtil;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.NoticeListReqDto;
import com.gradu.infou.Domain.Dto.Service.NoticeListResDto;
import com.gradu.infou.Domain.Entity.InfouDocument;
import com.gradu.infou.Domain.Entity.NoticeBookmarkDocument;
import com.gradu.infou.Domain.Entity.NoticeDocument;
import com.gradu.infou.Domain.Entity.User;
import com.gradu.infou.Repository.InfouRepository;
import com.gradu.infou.Repository.NoticeBookmarkRepository;
import com.gradu.infou.Repository.NoticeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeBookmarkRepository noticeBookmarkRepository;
    private final UserService userService;
    public NoticeListResDto noticeDocumentsList(HttpServletRequest request, String type, String tag, Pageable pageable){
        Page<NoticeDocument> noticeDocumentList=null;
        User user = userService.findUserByRequest(request);
        List<String> noticeList = user.getSelectNotice();

        if(type==null||type.isBlank()){
            noticeDocumentList = noticeRepository.findAllByType(noticeList.get(0), pageable);
        }
        else if(tag==null||tag.isBlank()){
            noticeDocumentList = noticeRepository.findAllByType(type, pageable);
        }
        else{
            noticeDocumentList = noticeRepository.findAllByTypeAndTagsContaining(type, tag, pageable);
        }

        NoticeListResDto noticeListResDto = NoticeListResDto.toNoticeListResDto(noticeList, noticeDocumentList);

        return noticeListResDto;
    }

    @Transactional(readOnly = true)
    public Page<NoticeBookmarkDocument> BookmarkList(HttpServletRequest request, String tag, Pageable pageable){
        User user = userService.findUserByRequest(request);
        Page<NoticeBookmarkDocument> res=null;
        log.info(user.getId().toString());
        if(tag==null||tag.isBlank()){
            res = noticeBookmarkRepository.findAllByUserId(user.getId().toString(), pageable);
        }
        else{
            res = noticeBookmarkRepository.findAllByUserIdAndAndNoticeTagsContaining(user.getId().toString(), tag, pageable);
        }
        log.info("res: "+String.valueOf(res.getTotalElements()));

        res.forEach(e->log.info(e.getId()));
        return res;
    }

    public void addBookmak(HttpServletRequest request, String noticeId){
        User user = userService.findUserByRequest(request);
        NoticeDocument noticeDocument = noticeRepository.findById(noticeId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOTICE_NOT_FOUND));
        noticeBookmarkRepository.findByUserIdAndNoticeDocumentId(user.getId().toString(), noticeDocument.getId()).ifPresent(e -> {
            throw new BaseException(BaseResponseStatus.BOOKMARK_NOTICE_EXIST);
        });
        NoticeBookmarkDocument noticeBookmarkDocument = NoticeBookmarkDocument.toNoticeBookmark(user, noticeDocument);
        noticeBookmarkRepository.save(noticeBookmarkDocument);
    }

    public Page<NoticeDocument> searchNotice(String keyword, String type, Pageable pageable){
        Page<NoticeDocument> res = noticeRepository.findAllByTitleContainingAndType(keyword, type, pageable);
        return res;
    }
}
