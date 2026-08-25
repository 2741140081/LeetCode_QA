package com.mangareader.service;

import com.mangareader.model.entity.Manga;

import java.io.IOException;

public interface MangaDownloadService {
    void downloadManga(Manga manga, int threadCount) throws IOException;
}
