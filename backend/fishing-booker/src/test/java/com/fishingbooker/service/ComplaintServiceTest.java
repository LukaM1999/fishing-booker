package com.fishingbooker.service;

import com.fishingbooker.model.Complaint;
import com.fishingbooker.model.Review;
import com.fishingbooker.service.impl.ComplaintServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class ComplaintServiceTest {

    @Autowired
    @InjectMocks
    private ComplaintServiceImpl complaintService;

    //@Test(expected = ObjectOptimisticLockingFailureException.class)
    public void updateReviewOptimisticLockTest() throws Throwable {
        Complaint complaint = new Complaint();
        complaint.setId(1L);
        complaint.setReservationId(2L);
        complaint.setComplaint("test");
        complaint.setReviewed(true);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");// izmenjen ucitan objekat
                //try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                complaintService.updateComplaint(complaint); // bacice ObjectOptimisticLockingFailureException

            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                try { Thread.sleep(10); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                complaintService.updateComplaint(complaint); // bacice ObjectOptimisticLockingFailureException
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}
