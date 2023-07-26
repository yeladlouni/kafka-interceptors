import org.apache.kafka.clients.producer.*;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ExampleProducerInterceptor implements ProducerInterceptor<String, String> {
    ScheduledExecutorService executorService =
            Executors.newSingleThreadScheduledExecutor();

    static AtomicLong numSent = new AtomicLong(0);
    static AtomicLong numAcked = new AtomicLong(0);
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        System.out.println("onSend");
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        System.out.println("onAcknowledgement");
    }

    @Override
    public void close() {
        executorService.shutdownNow();
    }

    @Override
    public void configure(Map<String, ?> configs) {
        executorService.scheduleAtFixedRate(ExampleProducerInterceptor::run,
                10000, 10000, TimeUnit.MILLISECONDS);
    }

    public static void run() {
        System.out.println(numSent.getAndSet(0));
        System.out.println(numAcked.getAndSet(0));
    }

}
