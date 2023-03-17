package com.mumy.basicboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {

        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);
        int endNumber = Math.min(totalPages, startNumber + BAR_LENGTH);

        return IntStream.range(startNumber,endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }


}
