package com.mangareader.service;

import java.io.IOException;

public interface MangaDownloadService {
    void downloadManga(String mangaName, String mangaUrl, int threadCount) throws IOException;
}
