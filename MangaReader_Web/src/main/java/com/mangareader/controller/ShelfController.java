package com.mangareader.controller;

import com.mangareader.model.common.BusinessException;
import com.mangareader.model.common.Result;
import com.mangareader.model.dto.FolderCreateRequest;
import com.mangareader.model.dto.MangaMoveRequest;
import com.mangareader.model.vo.ShelfFolderVO;
import com.mangareader.model.vo.ShelfMangaVO;
import com.mangareader.security.JwtUtils;
import com.mangareader.service.ShelfService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 书架管理控制器
 *
 * @author marks
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/shelf")
@RequiredArgsConstructor
public class ShelfController {

    private final ShelfService shelfService;
    private final JwtUtils jwtUtils;

    /**
     * 获取当前用户所有文件夹
     */
    @GetMapping("/folders")
    public Result<List<ShelfFolderVO>> getFolders(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        return Result.ok(shelfService.getFolders(userId));
    }

    /**
     * 新建文件夹
     */
    @PostMapping("/folder")
    public Result<ShelfFolderVO> createFolder(@Valid @RequestBody FolderCreateRequest body,
                                               HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        ShelfFolderVO folder = shelfService.createFolder(userId, body.getFolderName());
        return Result.ok("文件夹创建成功", folder);
    }

    /**
     * 重命名文件夹
     */
    @PutMapping("/folder/{folderId}")
    public Result<Void> renameFolder(@PathVariable Long folderId,
                                     @RequestBody Map<String, String> body,
                                     HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        String newName = body.get("folderName");
        if (!StringUtils.hasText(newName)) {
            throw new BusinessException(400, "文件夹名称不能为空");
        }
        shelfService.renameFolder(userId, folderId, newName);
        return Result.ok("重命名成功", null);
    }

    /**
     * 删除文件夹（内漫画移至未分类）
     */
    @DeleteMapping("/folder/{folderId}")
    public Result<Void> deleteFolder(@PathVariable Long folderId,
                                     HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        shelfService.deleteFolder(userId, folderId);
        return Result.ok("文件夹已删除", null);
    }

    /**
     * 获取书架漫画列表（支持 ?folderId= 筛选，?uncategorized=true 查未分类）
     */
    @GetMapping("/mangas")
    public Result<List<ShelfMangaVO>> getMangas(
            @RequestParam(required = false) Long folderId,
            @RequestParam(required = false, defaultValue = "false") boolean uncategorized,
            HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        if (uncategorized) {
            return Result.ok(shelfService.getUncategorizedMangas(userId));
        }
        return Result.ok(shelfService.getShelfMangas(userId, folderId));
    }

    /**
     * 添加漫画到书架
     */
    @PostMapping("/manga")
    public Result<Void> addManga(@Valid @RequestBody MangaMoveRequest body,
                                  HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        shelfService.addMangaToShelf(userId, body.getMangaId(), body.getFolderId());
        return Result.ok("已添加到书架", null);
    }

    /**
     * 移动漫画到指定文件夹
     */
    @PutMapping("/manga/{mangaId}/folder")
    public Result<Void> moveManga(@PathVariable Long mangaId,
                                   @RequestBody Map<String, Long> body,
                                   HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        Long folderId = body.get("folderId");
        shelfService.moveMangaToFolder(userId, mangaId, folderId);
        return Result.ok("移动成功", null);
    }

    /**
     * 从书架移除漫画
     */
    @DeleteMapping("/manga/{mangaId}")
    public Result<Void> removeManga(@PathVariable Long mangaId,
                                     HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        shelfService.removeMangaFromShelf(userId, mangaId);
        return Result.ok("已从书架移除", null);
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            throw new BusinessException(401, "未登录");
        }
        return jwtUtils.getUserIdFromToken(bearerToken.substring(7));
    }
}
