package com.shaw.test;

import com.shaw.log.Logger;
import com.shaw.log.LoggerFactory;
import com.shaw.note.AutoBody;
import com.shaw.utils.NoteUtils;
import com.shaw.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        Test test = new Test();

        NoteUtils noteUtils = new NoteUtils();
        noteUtils.initNote(test, "studentId", "studentName");

        Student student = test.getStudent();
        if (student != null)
            logger.info(student);

        List<Class<?>> classes = NoteUtils.getClasses("com.shaw.log");

        for (Class clas :classes) {
            System.out.println(clas.getName());
        }

    }

}
