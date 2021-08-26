package com.notes.demo;

import com.notes.NotesApplication;
import com.notes.service.NotesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tiny
 * @date 2021/8/26 17:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NotesApplication.class)
public class NotesTestDemo {

    @Autowired
    private NotesService notesService;

    @Test
    public void demo(){
        System.out.println(notesService);
    }
}
