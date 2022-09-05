package com.splb.service.sorting;

import com.splb.model.dao.constant.Fields;
import com.splb.model.entity.Applicant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class SortApplicantImplTest {

    public static final String LAST_NAME = "byLastName";
    public static final String CITY = "byCity";
    public static final String REGION = "byRegion";
    public static final String STATUS = "byStatus";
    private List<Applicant> list;


    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        Applicant applicantOne = new Applicant();
        Applicant applicantTwo = new Applicant();
        Applicant applicantThree = new Applicant();
        applicantOne.setLastName("Ame");
        applicantOne.setCity("Warsaw");
        applicantOne.setRegion("KRegion");
        applicantOne.setEnrollStatus(0);
        applicantTwo.setLastName("Wame");
        applicantTwo.setCity("Antananarivo");
        applicantTwo.setRegion("Hawaii");
        applicantTwo.setEnrollStatus(2);
        applicantThree.setLastName("Fame");
        applicantThree.setCity("Janeiro");
        applicantThree.setRegion("Sumatra");
        applicantThree.setEnrollStatus(1);
        list.add(applicantOne);
        list.add(applicantTwo);
        list.add(applicantThree);
    }

    @Test
    void getSortedLisTest() {
        Sort<Applicant> sortedList = new SortApplicantImpl();
        List<Applicant> list1 = sortedList.getSortedList(Fields.ASC, LAST_NAME, list);
        List<Applicant> list2 = sortedList.getSortedList(Fields.ASC, CITY, list);
        List<Applicant> list3 = sortedList.getSortedList(Fields.TYPE, REGION, list);
        List<Applicant> list4 = sortedList.getSortedList(Fields.TYPE, STATUS, list);

        List<String> exp1 = new ArrayList<>(3);
        list1.forEach(el -> exp1.add(el.getLastName()));
        List<String> exp2 = new ArrayList<>(3);
        list2.forEach(el -> exp2.add(el.getCity()));
        List<String> exp3 = new ArrayList<>(3);
        list3.forEach(el -> exp3.add(el.getRegion()));
        List<Integer> exp4 = new ArrayList<>(3);
        list3.forEach(el -> exp4.add(el.getEnrollStatus()));

        Assert.assertEquals(exp1, Arrays.asList("Wame", "Fame", "Ame"));
        Assert.assertEquals(exp2, Arrays.asList("Antananarivo", "Janeiro", "Warsaw"));
        Assert.assertEquals(exp3, Arrays.asList("Hawaii", "Sumatra", "KRegion"));
        Assert.assertEquals(exp4, Arrays.asList(2, 1, 0));
    }
}